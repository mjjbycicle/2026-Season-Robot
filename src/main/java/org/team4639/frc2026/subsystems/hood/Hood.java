/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.hood;

import edu.wpi.first.math.Pair;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;
import org.team4639.frc2026.RobotState;
import org.team4639.lib.util.FullSubsystem;

public class Hood extends FullSubsystem {
    private final RobotState state;
    private final HoodIO io;
    private final HoodIOInputsAutoLogged inputs = new HoodIOInputsAutoLogged();

    private final double PASSING_HOOD_ANGLE = 0;
    private final double IDLE_HOOD_ANGLE = 0;
    private double SCORING_HOOD_ANGLE = 0;

    public enum WantedState {
        IDLE,
        SCORING,
        PASSING
    }

    public enum SystemState {
        IDLE,
        SCORING,
        PASSING
    }

    private WantedState wantedState = WantedState.IDLE;
    private SystemState systemState = SystemState.IDLE;

    public Hood(HoodIO io, RobotState state) {
        this.io = io;
        this.state = state;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Hood", inputs);

        SystemState newState = handleStateTransitions();
        if (newState != systemState) {
            Logger.recordOutput("Hood/SystemState", newState.toString());
            systemState = newState;
        }

        if (DriverStation.isDisabled()) {
            systemState = SystemState.IDLE;
        }

        switch (systemState) {
            case IDLE:
                handleIdle();
                break;
            case SCORING:
                handleScoring();
                break;
            case PASSING:
                handlePassing();
                break;
        }
    }

    @Override
    public void periodicAfterScheduler() {
        RobotState.getInstance().setHoodStates(new Pair<Hood.WantedState,Hood.SystemState>(wantedState, systemState));
        RobotState.getInstance().accept(inputs);
    }

    private SystemState handleStateTransitions() {
        return switch (wantedState) {
            case IDLE -> SystemState.IDLE;
            case SCORING -> SystemState.SCORING;
            case PASSING -> SystemState.PASSING;
        };
    }

    private void handleIdle() {
        io.setSetpointDegrees(IDLE_HOOD_ANGLE);
    }

    private void handleScoring() {
        io.setSetpointDegrees(SCORING_HOOD_ANGLE);
    }

    private void handlePassing() {
        io.setSetpointDegrees(PASSING_HOOD_ANGLE);
    }

    public void setWantedState(WantedState wantedState) {
        this.wantedState = wantedState;
    }

    public void setWantedState(WantedState wantedState, double scoringAngle) {
        setWantedState(wantedState);
        this.SCORING_HOOD_ANGLE = scoringAngle;
    }
}
