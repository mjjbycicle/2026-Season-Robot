/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.shooter;

public class Shooter {
    private final ShooterIO io;
    private final ShooterIOInputsAutoLogged inputs = new ShooterIOInputsAutoLogged();

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

    public Shooter(ShooterIO io, )
}
