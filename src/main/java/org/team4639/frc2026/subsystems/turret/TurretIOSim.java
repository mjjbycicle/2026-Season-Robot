/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.turret;

public class TurretIOSim implements TurretIO {
    private double rotorRotations = 0;

    @Override
    public void updateInputs(TurretIO.TurretIOInputs inputs) {
        inputs.motorPositionRotations = rotorRotations;
    }

    @Override
    public void setRotorRotationSetpoint(double rotation) {
        rotorRotations = rotation;
    }
}
