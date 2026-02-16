/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.hood;

public class HoodIOSim implements HoodIO {
    private double setpoint = 0;

    @Override
    public void setSetpointDegrees(double setpointDegrees) {
        setpoint = setpointDegrees;
    }

    @Override
    public void updateInputs(HoodIOInputs inputs) {
        inputs.pivotPositionDegrees = setpoint;
    }
}
