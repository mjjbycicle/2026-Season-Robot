/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.hood;

import static org.team4639.frc2026.subsystems.hood.Constants.*;

import com.ctre.phoenix6.BaseStatusSignal;
import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.PositionVoltage;
import com.ctre.phoenix6.hardware.CANcoder;
import com.ctre.phoenix6.hardware.TalonFX;
import com.ctre.phoenix6.signals.FeedbackSensorSourceValue;
import com.ctre.phoenix6.signals.NeutralModeValue;
import edu.wpi.first.math.MathUtil;
import org.team4639.frc2026.util.PhoenixUtil;
import org.team4639.frc2026.util.PortConfiguration;
import org.team4639.lib.util.Phoenix6Factory;

public class HoodIOTalonFX implements HoodIO {
    private final TalonFX hoodMotor;
    private final CANcoder hoodEncoder;

    private final TalonFXConfiguration config = new TalonFXConfiguration();

    private final PositionVoltage request = new PositionVoltage(0);

    public HoodIOTalonFX(PortConfiguration ports) {
        hoodMotor = Phoenix6Factory.createDefaultTalon(ports.HoodMotorID);
        hoodEncoder = Phoenix6Factory.createCANcoder(ports.HoodEncoderID);

        config.Feedback.FeedbackSensorSource = FeedbackSensorSourceValue.RemoteCANcoder;
        config.Feedback.FeedbackRemoteSensorID = hoodEncoder.getDeviceID();
        config.CurrentLimits.SupplyCurrentLimit = 20.0;
        config.CurrentLimits.SupplyCurrentLimitEnable = true;
        config.CurrentLimits.StatorCurrentLimitEnable = true;
        config.CurrentLimits.StatorCurrentLimit = 80;
        config.Audio.BeepOnConfig = false;
        config.MotorOutput.NeutralMode = NeutralModeValue.Brake;

        applyNewGains();
    }

    @Override
    public void setSetpointDegrees(double setpointDegrees) {
        double rotation = setpointDegrees * ENCODER_ROTATIONS_PER_DEGREE;
        rotation = MathUtil.clamp(rotation, HOOD_ENCODER_MIN_ROTATION, HOOD_ENCODER_MAX_ROTATION);
        hoodMotor.setControl(request.withPosition(rotation));
    }

    @Override
    public void updateInputs(HoodIOInputs inputs) {
        inputs.hoodMotorConnected = BaseStatusSignal.refreshAll(
                        hoodMotor.getMotorVoltage(),
                        hoodMotor.getSupplyCurrent(),
                        hoodMotor.getDeviceTemp(),
                        hoodMotor.getPosition(),
                        hoodMotor.getVelocity())
                .isOK();
        inputs.pivotVoltage = hoodMotor.getMotorVoltage().getValueAsDouble();
        inputs.pivotCurrent = hoodMotor.getSupplyCurrent().getValueAsDouble();
        inputs.pivotTemperature = hoodMotor.getDeviceTemp().getValueAsDouble();
        inputs.pivotPositionDegrees = hoodEncoder.getPosition().getValueAsDouble() / ENCODER_ROTATIONS_PER_DEGREE;
        inputs.pivotVelocityDegrees = hoodEncoder.getVelocity().getValueAsDouble() / ENCODER_ROTATIONS_PER_DEGREE;
    }

    @Override
    public void setVoltage(double volts){
        hoodMotor.setVoltage(volts);
    }

    public void updateGains() {
        config.Slot0.kP = PIDs.hoodKp.get();
        config.Slot0.kI = PIDs.hoodKi.get();
        config.Slot0.kD = PIDs.hoodKd.get();
        config.Slot0.kS = PIDs.hoodKs.get();
        config.Slot0.kV = PIDs.hoodKv.get();
        config.Slot0.kA = PIDs.hoodKa.get();
    }

    @Override
    public void applyNewGains() {
        updateGains();
        PhoenixUtil.tryUntilOk(5, () -> hoodMotor.getConfigurator().apply(config));
    }
}
