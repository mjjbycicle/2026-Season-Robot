/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.auto;

import org.team4639.frc2026.subsystems.drive.Drive;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import choreo.auto.AutoFactory;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class AutoCommands {
    private AutoFactory autoFactory;
    public AutoCommands(Drive driveSubsystem){
        autoFactory = new AutoFactory(
            driveSubsystem::getPose,
            driveSubsystem::setPose,
            driveSubsystem::followTrajectory,
            true,
             driveSubsystem);
    }
    public Command DriverStation_TrenchLine() {
        return autoFactory.trajectoryCmd("DriverStation_TrenchLine");
    }

    public Command DriverStation_TrenchLine_DriverStation() {
        return autoFactory.trajectoryCmd("DriverStation_TrenchLine_DriverStation");
    }
}
