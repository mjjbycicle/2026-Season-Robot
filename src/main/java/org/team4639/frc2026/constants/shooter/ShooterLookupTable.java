/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.constants.shooter;

import edu.wpi.first.math.VecBuilder;
import edu.wpi.first.math.Vector;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Transform2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.numbers.N2;
import org.team4639.frc2026.Constants;

import static edu.wpi.first.units.Units.Minute;
import static edu.wpi.first.units.Units.Rotations;

/**
 * @param scoringDistanceToRPM       meters -> RPM
 * @param scoringDistanceToHoodAngle meters -> encoder angle
 * @param scoringDistanceToTOF       meters -> seconds
 */
public record ShooterLookupTable(InterpolatingDoubleTreeMap scoringDistanceToRPM,
                                 InterpolatingDoubleTreeMap scoringDistanceToHoodAngle,
                                 InterpolatingDoubleTreeMap scoringDistanceToTOF) {

    private static final Vector<N2> i_hat = VecBuilder.fill(1, 0);

    public ScoringState calculateShooterStateStationary(Pose2d robotPose, Translation2d hubTranslation) {
        Translation2d robotTranslation = robotPose.getTranslation();
        Rotation2d robotRotation = robotPose.getRotation();
        Translation2d robotToHubTranslation = hubTranslation.minus(robotTranslation);
        Rotation2d fieldRelativeHubDirection = robotToHubTranslation.getAngle();
        Rotation2d neededTurretRotation = fieldRelativeHubDirection.minus(robotRotation);
        double distanceMeters = robotToHubTranslation.getNorm();
        double shooterRPM = scoringDistanceToRPM.get(distanceMeters);
        double hoodAngle = scoringDistanceToHoodAngle.get(distanceMeters);
        return new ScoringState(Rotations.per(Minute).of(shooterRPM), Rotations.of(hoodAngle), neededTurretRotation.getMeasure());
    }

    public ScoringState convergeShooterStateSOTF(Pose2d robotPose, Translation2d hubTranslation, ChassisSpeeds chassisSpeeds, int maxIterations) {
        Pose2d turretPose = robotPose.transformBy(new Transform2d(Constants.SimConstants.originToTurretRotation.toTranslation2d(), new Rotation2d()));
        Translation2d robotTranslation = turretPose.getTranslation();
        Rotation2d robotRotation = turretPose.getRotation();
        Translation2d robotDisplacement = new Translation2d(0, 0);
        Translation2d robotVelocity = new Translation2d(chassisSpeeds.vxMetersPerSecond, chassisSpeeds.vyMetersPerSecond);
        Translation2d robotTangentialVelocityTranslation = Constants.SimConstants.originToTurretRotation.toTranslation2d()
                .rotateBy(robotRotation)
                .rotateBy(Rotation2d.fromDegrees(90))
                .times(chassisSpeeds.omegaRadiansPerSecond);
        robotTangentialVelocityTranslation.rotateBy(robotRotation);
        robotVelocity = robotVelocity.plus(robotTangentialVelocityTranslation);
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
        return new ScoringState(Rotations.per(Minute).of(shooterRPM), Rotations.of(hoodAngle), neededTurretRotation.getMeasure());
    }
}
