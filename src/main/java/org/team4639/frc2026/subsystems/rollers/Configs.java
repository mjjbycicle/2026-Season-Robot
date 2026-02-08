/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.rollers;

import com.ctre.phoenix6.configs.TalonFXConfiguration;

public class Configs {
    public static final TalonFXConfiguration rollersMotorConfiguration = createRollersMotorConfig();

    public static final double CURRENT_LIMIT_AMPS = 50;

    public static TalonFXConfiguration createRollersMotorConfig() {
        TalonFXConfiguration config = new TalonFXConfiguration();

        config.CurrentLimits.StatorCurrentLimit = CURRENT_LIMIT_AMPS;
        config.CurrentLimits.SupplyCurrentLimit = CURRENT_LIMIT_AMPS;

        return config;
    }
}
