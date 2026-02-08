package org.team4639.frc2026.subsystems.kicker;

import org.littletonrobotics.junction.AutoLog;

public interface KickerIO {

    default void setVoltage(double appliedVolts) {}

    default void setRPM(double targetRPM) {}

    default void updateInputs() {}

    default void applyNewGains() {}

    @AutoLog
    class KickerOIInputs {
        public boolean motorConnected = true;
        public double motorVoltage;
        public double motorCurrent;
        public double motorVelocity;
        public double motorTemperature;
    }
}
