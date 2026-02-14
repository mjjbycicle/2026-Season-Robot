/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.hood;

public class Constants {
    public static final double PIVOT_TO_ENCODER_GEAR_RATIO = 324.0 / 30;
    public static final double ENCODER_ROTATIONS_PER_DEGREE = PIVOT_TO_ENCODER_GEAR_RATIO / 360.0;
    public static final double HOOD_MIN_ANGLE_DEGREES = 20;
    public static final double HOOD_ENCODER_ROTATION_AT_MIN_ANGLE = 0.02;

    public static final double HOOD_ENCODER_MIN_ROTATION = 0;
    public static final double HOOD_ENCODER_MAX_ROTATION = 1;
}
