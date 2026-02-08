/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.rollers;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.StatusSignal;
import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.units.measure.Current;
import edu.wpi.first.units.measure.Temperature;
import edu.wpi.first.units.measure.Voltage;
import org.team4639.frc2026.Constants;

public class RollersIOTalonFX implements RollersIO {
    private final TalonFX motor;

    private final StatusSignal<Current> motorCurrent;
    private final StatusSignal<Voltage> motorVoltage;
    private final StatusSignal<Temperature> motorTemperature;

    public RollersIOTalonFX() {
        motor = new TalonFX(Constants.IDs.ROLLERS);
        motor.getConfigurator().apply(Configs.rollersMotorConfiguration);

        motorCurrent = motor.getTorqueCurrent();
        motorVoltage = motor.getMotorVoltage();
        motorTemperature = motor.getDeviceTemp();
    }

    @Override
    public void updateInputs(RollersIOInputs inputs) {
        BaseStatusSignal.refreshAll(motorCurrent, motorVoltage, motorTemperature);
        inputs.motorVoltage = this.motorVoltage.getValueAsDouble();
        inputs.motorTemperature = this.motorTemperature.getValueAsDouble();
        inputs.motorCurrent = this.motorCurrent.getValueAsDouble();
    }

    public void setVoltage(double volts) {
        motor.setVoltage(volts);
    }
}
