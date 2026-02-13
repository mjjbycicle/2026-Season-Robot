/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.constants;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.numbers.N2;
import edu.wpi.first.units.measure.Angle;

import static edu.wpi.first.units.Units.*;

/**
 * @param scoringDistanceToRPM       meters -> RPM
 * @param scoringDistanceToHoodAngle meters -> encoder angle
 * @param scoringDistanceToTOF       meters -> seconds
 */
public record ShooterLookupTable(InterpolatingDoubleTreeMap scoringDistanceToRPM,
                                 InterpolatingDoubleTreeMap scoringDistanceToHoodAngle,
                                 InterpolatingDoubleTreeMap scoringDistanceToTOF) {

    private static final Vector<N2> i_hat = VecBuilder.fill(1, 0);

    public ShooterState calculateShooterStateStationary(Pose2d robotPose, Translation2d hubTranslation) {
        Translation2d robotTranslation = robotPose.getTranslation();
        Rotation2d robotRotation = robotPose.getRotation();
        Translation2d robotToHubTranslation = hubTranslation.minus(robotTranslation);
        Rotation2d fieldRelativeHubDirection = robotToHubTranslation.getAngle();
        Rotation2d neededTurretRotation = fieldRelativeHubDirection.minus(robotRotation);
        double distanceMeters = robotToHubTranslation.getNorm();
        double shooterRPM = scoringDistanceToRPM.get(distanceMeters);
        double hoodAngle = scoringDistanceToHoodAngle.get(distanceMeters);
        return new ShooterState(Rotations.per(Minute).of(shooterRPM), Rotations.of(hoodAngle), neededTurretRotation.getMeasure());
    }

    public ShooterState calculateIdealShooterStateSOTF(Pose2d robotPose, Translation2d hubTranslation, ChassisSpeeds chassisSpeeds) {
        Translation2d robotTranslation = robotPose.getTranslation();
        Angle robotRotation = robotPose.getRotation().getMeasure();
        Translation2d robotToHubTranslation = hubTranslation.minus(robotTranslation);
        Vector<N2> robotToHub = VecBuilder.fill(robotToHubTranslation.getX(), robotToHubTranslation.getY());
        Vector<N2> robotVelocity = VecBuilder.fill(chassisSpeeds.vxMetersPerSecond, chassisSpeeds.vyMetersPerSecond);
        double distanceMeters = robotToHubTranslation.getNorm();
        double shotTOF = scoringDistanceToTOF.get(distanceMeters);
        double averageHorizontalVelocityMetersPerSecond = distanceMeters / shotTOF;
        double a = (Math.pow(robotVelocity.norm(), 2) - Math.pow(averageHorizontalVelocityMetersPerSecond, 2));
        double b = -2 * (robotToHub.dot(robotVelocity));
        double c = Math.pow(robotToHub.norm(), 2);
        double discriminant = Math.pow(b, 2) - 4 * a * c;
        double res1 = (-b + Math.sqrt(discriminant)) / (2 * a);
        double res2 = (-b - Math.sqrt(discriminant)) / (2 * a);
        double realTOF;
        if (res1 < 0) {
            if (res2 < 0) {
                return null;
            } else {
                realTOF = res2;
            }
        } else {
            if (res2 < 0) {
                realTOF = res1;
            } else {
                realTOF = Math.min(res1, res2);
            }
        }
        Vector<N2> robotDisplacementFinal = robotVelocity.times(realTOF);
        Vector<N2> shootingDistanceFinal = robotToHub.minus(robotDisplacementFinal);
        Angle fieldRelativeTurretDirection = Radians.of(shootingDistanceFinal.dot(i_hat) / shootingDistanceFinal.norm());
        Angle robotRelativeTurretDirection = fieldRelativeTurretDirection.minus(robotRotation);
        double shootingDistanceMagnitudeMeters = shootingDistanceFinal.norm();
        double shootingRPM = scoringDistanceToRPM.get(shootingDistanceMagnitudeMeters);
        double shootingHoodAngle = scoringDistanceToHoodAngle.get(shootingDistanceMagnitudeMeters);
        return new ShooterState(Rotations.per(Minute).of(shootingRPM), Rotations.of(shootingHoodAngle), robotRelativeTurretDirection);
    }

    public ShooterState convergeShooterStateSOTF(Pose2d robotPose, Translation2d hubTranslation, ChassisSpeeds chassisSpeeds, int maxIterations) {
        Translation2d robotTranslation = robotPose.getTranslation();
        Rotation2d robotRotation = robotPose.getRotation();
        Translation2d robotDisplacement = new Translation2d(0, 0);
        Translation2d robotVelocity = new Translation2d(chassisSpeeds.vxMetersPerSecond, chassisSpeeds.vyMetersPerSecond);
        for (int i = 0; i < maxIterations; i++) {
            Translation2d robotTranslationAfterDisplacement = robotTranslation.plus(robotDisplacement);
            Translation2d robotDisplacedToHubTranslation = hubTranslation.minus(robotTranslationAfterDisplacement);
            double newDistance = robotDisplacedToHubTranslation.getNorm();
            double newTOF = scoringDistanceToTOF.get(newDistance);
            robotDisplacement = robotVelocity.times(newTOF);
        }
        Translation2d finalRobotToHubTranslation = hubTranslation.minus(robotTranslation.plus(robotDisplacement));
        Rotation2d neededTurretRotation = finalRobotToHubTranslation.getAngle().minus(robotRotation);
        double distanceMeters = finalRobotToHubTranslation.getNorm();
        double shooterRPM = scoringDistanceToRPM.get(distanceMeters);
        double hoodAngle = scoringDistanceToHoodAngle.get(distanceMeters);
        return new ShooterState(Rotations.per(Minute).of(shooterRPM), Rotations.of(hoodAngle), neededTurretRotation.getMeasure());
    }
}
