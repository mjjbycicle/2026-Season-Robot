/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class AutoFactory {
    // Looks good, just change the names. DS_TL can be a little confusing, so be as descriptive as possible
    // at least until we come up with a naming scheme for the paths. Same thing with the trajectory names
    // as much as possible try and have them be the same as the command name and both be easily
    // understandable.
    public static Command DriverStation_TrenchLine() {
        try {
            PathPlannerPath path = PathPlannerPath.fromChoreoTrajectory("DriverStation_TrenchLine");
            return AutoBuilder.followPath(path);
        } catch (Exception E) {
            return Commands.none();
        }
    }

    public static Command DriverStation_TrenchLine_DriverStation() {
        try {
            PathPlannerPath path = PathPlannerPath.fromChoreoTrajectory("DriverStation_TrenchLine_DriverStation");
            return AutoBuilder.followPath(path);
        } catch (Exception E) {
            return Commands.none();
        }
    }
}
