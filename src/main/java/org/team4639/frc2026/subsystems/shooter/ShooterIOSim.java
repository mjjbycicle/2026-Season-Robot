package org.team4639.frc2026.subsystems.shooter;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.controller.PIDController;
import edu.wpi.first.math.system.plant.DCMotor;
import edu.wpi.first.math.system.plant.LinearSystemId;
import edu.wpi.first.wpilibj.simulation.FlywheelSim;

public class ShooterIOSim implements ShooterIO {

    private final FlywheelSim flywheelSim = new FlywheelSim(
            LinearSystemId.createFlywheelSystem(
                    DCMotor.getNeoVortex(2),
                    0.00117 * 3,
                    1
            ),
            DCMotor.getNeoVortex(2),
            0.025
    );

    private final PIDController flywheelFeedback = new PIDController(1.5, 0, 0.5);

    private double leftAppliedVolts = 0.0;
    private double rightAppliedVolts = 0.0;

    private static final double ROLLER_RADIUS_METERS = 0.0508;

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.leftVoltage = leftAppliedVolts;
        inputs.rightVoltage = rightAppliedVolts;
        inputs.leftRPM = flywheelSim.getAngularVelocityRPM();
        inputs.rightRPM = flywheelSim.getAngularVelocityRPM();
    }

    @Override
    public void setVoltage(double appliedVolts) {
        this.leftAppliedVolts = appliedVolts;
        this.rightAppliedVolts = appliedVolts;
        flywheelSim.setInputVoltage(appliedVolts);
    }

    @Override
    public void setRPM(double targetRPM) {
        leftAppliedVolts = flywheelFeedback.calculate(flywheelSim.getAngularVelocityRPM(), targetRPM);
        leftAppliedVolts = MathUtil.clamp(leftAppliedVolts, -12, 12);
        rightAppliedVolts = flywheelSim.getAngularVelocityRPM();
        rightAppliedVolts = leftAppliedVolts + rightAppliedVolts;
        flywheelSim.setInputVoltage(rightAppliedVolts);
    }
}