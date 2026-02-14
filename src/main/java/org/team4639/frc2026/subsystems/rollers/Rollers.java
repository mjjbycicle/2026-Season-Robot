/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.rollers;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import org.team4639.frc2026.subsystems.rollers.RollersIO.RollersIOInputs;

public class Rollers extends SubsystemBase {
    private final RollersIO io;
    private final RollersIOInputs inputs;

    public Rollers(RollersIO io) {
        this.io = io;
        this.inputs = new RollersIOInputsAutoLogged();
    }

    @Override
    public void periodic() {
        io.updateInputs(inputs);
        SmartDashboard.putNumber("Rollers Voltage", getMotorVoltage());
    }

    public double getMotorVoltage() {
        return inputs.motorVoltage;
    }

    public double getMotorTemperature() {
        return inputs.motorTemperature;
    }

    public double getMotorCurrent() {
        return inputs.motorCurrent;
    }
}
