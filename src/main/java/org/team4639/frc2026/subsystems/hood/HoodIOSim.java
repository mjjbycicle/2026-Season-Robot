/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.hood;

import static org.team4639.frc2026.subsystems.hood.Constants.*;

import edu.wpi.first.math.MathUtil;

public class HoodIOSim implements HoodIO {
    private double encoderMeasurement = 0;

    @Override
    public void setSetpointDegrees(double setpointDegrees) {
        double rotation = setpointDegrees * ENCODER_ROTATIONS_PER_DEGREE;
        rotation = MathUtil.clamp(rotation, HOOD_ENCODER_MIN_ROTATION, HOOD_ENCODER_MAX_ROTATION);
        encoderMeasurement = rotation;
    }

    @Override
    public void updateInputs(HoodIOInputs inputs) {
        inputs.pivotPositionDegrees = encoderMeasurement / ENCODER_ROTATIONS_PER_DEGREE;
    }
}
