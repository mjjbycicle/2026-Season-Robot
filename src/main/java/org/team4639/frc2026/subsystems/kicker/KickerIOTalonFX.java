package org.team4639.frc2026.subsystems.kicker;

import com.ctre.phoenix6.configs.TalonFXConfiguration;
import com.ctre.phoenix6.controls.VelocityVoltage;
import com.ctre.phoenix6.controls.VoltageOut;
import com.ctre.phoenix6.hardware.TalonFX;
import org.team4639.frc2026.util.PortConfiguration;

public class KickerIOTalonFX implements KickerIO {
    private final TalonFX kickerMotor;

    private final TalonFXConfiguration config = new TalonFXConfiguration();

    private final VoltageOut voltageControl = new VoltageOut(0);
    private final VelocityVoltage velocityControl = new VelocityVoltage(0);

    private final double KICKER_KICK_VOLTAGE = 0;
    private final double KICKER_KICK_VELOCITY_RPM = 0;

    public KickerIOTalonFX(PortConfiguration ports) {

    }
}
