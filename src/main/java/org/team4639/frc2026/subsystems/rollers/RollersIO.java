/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.rollers;

import org.littletonrobotics.junction.AutoLog;

public interface RollersIO {

    default void setVoltage(double volts) {}

    default void updateInputs(RollersIOInputs inputs) {}

    @AutoLog
    public class RollersIOInputs {
        public double motorVoltage;
        public double motorTemperature;
        public double motorCurrent;
    }
}
