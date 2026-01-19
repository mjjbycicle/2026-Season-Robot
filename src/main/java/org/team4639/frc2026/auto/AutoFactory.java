package org.team4639.frc2026.auto;

import com.pathplanner.lib.auto.AutoBuilder;
import com.pathplanner.lib.path.PathPlannerPath;

import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;

public class AutoFactory {
    public static Command DS_TL(){
        try{
            PathPlannerPath path = PathPlannerPath.fromChoreoTrajectory("NewPath");
            return AutoBuilder.followPath(path);
        }
        catch (Exception E){
            return Commands.none();
        }
    }

    public static Command DS_TL_DS(){
        try{
            PathPlannerPath path = PathPlannerPath.fromChoreoTrajectory("NewPath2");
            return AutoBuilder.followPath(path);
        }
        catch (Exception E){
            return Commands.none();
        }
    }
}
