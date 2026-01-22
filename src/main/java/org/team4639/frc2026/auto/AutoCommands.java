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
            /**
             * True: changes all poses to red side of coordinate field (if and only if the alliance is red **at the time of command creation**), 
             * ie same as alliance flipping all poses with blue side origin over to red side
             * <p>
             * False: does not flip it, pretends as though we *are* on the blue side even if we are on the red (which may be useful, see 2025 rewrite code)
             * 
             * An issue we had last year was that we would go on the field and before connecting to FMS the alliance would default to blue
             * which would load up the blue command and then we would be on red alliance and it would try to go on the other side of the field
             * 
             * Nothing wrong with it at the moment except, except
             */
            true,
             driveSubsystem);
    }
    public Command DriverStation_TrenchLine() {
        /**
         * When Choreo runs a path, by default it will try to make up for any errors in the path
         * based on the pose the drivetrain reports and the desired pose.
         * 
         * This causes issues when the drivetrain thinks it isn't at the right pose (for example, 
         * when the code starts and we are at (0, 0)) and its even more noticeable/worse/could break something
         * when it tries to go from blue alliance (0, 0) to red alliance trench line.
         * 
         * TLDR reset the pose at the beginning of auto to what the auto command thinks the right pose should be
         */
        return autoFactory.trajectoryCmd("DriverStation_TrenchLine");
    }

    public Command DriverStation_TrenchLine_DriverStation() {
        return autoFactory.trajectoryCmd("DriverStation_TrenchLine_DriverStation");
    }
}
