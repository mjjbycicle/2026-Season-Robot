/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.shooter;

public class ShooterIOSim implements ShooterIO {
    private double shooterRPM = 0.0;

    @Override
    public void updateInputs(ShooterIOInputs inputs) {
        inputs.leftRPM = shooterRPM;
        inputs.rightRPM = -shooterRPM;
    }

    @Override
    public void setRPM(double targetRPM) {
        shooterRPM = targetRPM;
    }
}
