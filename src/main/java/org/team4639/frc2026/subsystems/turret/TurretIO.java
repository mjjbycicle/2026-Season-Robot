/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.turret;

import org.littletonrobotics.junction.AutoLog;

public interface TurretIO {

    default void setRotorRotationSetpoint(double rotation) {}

    default void updateInputs(TurretIOInputs inputs) {}

    default void applyNewGains() {}

    @AutoLog
    class TurretIOInputs {
        public boolean turretMotorConnected = true;
        public double motorVoltage;
        public double motorCurrent;
        public double motorTemperature;
        public double motorVelocity;
        public double motorPositionRotations;
    }
}
