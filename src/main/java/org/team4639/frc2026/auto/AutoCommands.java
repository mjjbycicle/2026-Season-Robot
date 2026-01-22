/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.auto;

import choreo.Choreo;
import choreo.auto.AutoFactory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import org.team4639.frc2026.subsystems.drive.Drive;

public class AutoCommands {
    private AutoFactory autoFactory;
    private Drive driveSubsystem;

    public AutoCommands(Drive driveSubsystem) {
        this.driveSubsystem = driveSubsystem;
        autoFactory = new AutoFactory(
                driveSubsystem::getPose,
                driveSubsystem::setPose,
                driveSubsystem::followTrajectory,
                true,
                driveSubsystem);
    }

    public Command DriverStation_TrenchLine() {
        var trajectory = Choreo.loadTrajectory("DriverStation_TrenchLine");
        return Commands.sequence(
                Commands.runOnce(
                        () -> driveSubsystem.setPose(
                                trajectory.get().getInitialPose(true).get()),
                        driveSubsystem),
                Commands.runOnce(() -> autoFactory.trajectoryCmd("DriverStation_TrenchLine"), driveSubsystem));
    }

    public Command DriverStation_TrenchLine_DriverStation() {

        var trajectory = Choreo.loadTrajectory("DriverStation_TrenchLine_DriverStation");
        return Commands.sequence(
                Commands.runOnce(
                        () -> driveSubsystem.setPose(
                                trajectory.get().getInitialPose(true).get()),
                        driveSubsystem),
                Commands.runOnce(
                        () -> autoFactory.trajectoryCmd("DriverStation_TrenchLine_DriverStation"), driveSubsystem));
    }
}
