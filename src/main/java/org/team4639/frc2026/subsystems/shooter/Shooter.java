/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.shooter;

import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.littletonrobotics.junction.Logger;
import org.team4639.frc2026.RobotState;

public class Shooter extends SubsystemBase {
    private final RobotState state;
    private final ShooterIO io;
    private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

    private final double PASSING_RPM = 0;
    private final double IDLE_VOLTAGE = 0;
    private double SCORING_RPM = 0;

    public enum WantedState {
        OFF,
        IDLE,
        SCORING,
        PASSING
    }

    public enum SystemState {
        OFF,
        IDLE,
        SCORING,
        PASSING
    }

    private WantedState wantedState = WantedState.OFF;
    private SystemState systemState = SystemState.OFF;

    public Shooter(ShooterIO io, RobotState state) {
        this.io = io;
        this.state = state;
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        Logger.processInputs("Shooter", inputs);
        SystemState newState = handleStateTransitions();
        if (newState != systemState) {
            Logger.recordOutput("Shooter/SystemState", newState.toString());
            systemState = newState;
        }

        if (DriverStation.isDisabled()) {
            systemState = SystemState.OFF;
        }

        switch (systemState) {
            case OFF:
                handleOff();
                break;
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

    private SystemState handleStateTransitions() {
        return switch (wantedState) {
            case SCORING -> SystemState.SCORING;
            case PASSING -> SystemState.PASSING;
            case IDLE -> SystemState.IDLE;
            default -> SystemState.OFF;
        };
    }

    private void handleOff() {
        io.setVoltage(0);
    }

    private void handleScoring() {
        io.setRPM(this.SCORING_RPM);
    }

    private void handlePassing() {
        io.setRPM(PASSING_RPM);
    }

    private void handleIdle() {
        io.setVoltage(IDLE_VOLTAGE);
    }

    public void setWantedState(WantedState wantedState) {
        this.wantedState = wantedState;
    }

    public void setWantedState(WantedState wantedState, double scoringRPM) {
        setWantedState(wantedState);
        this.SCORING_RPM = scoringRPM;
    }
}
