package org.team4639.frc2026.subsystems.hood;

public class Constants {
    public static final double PIVOT_TO_ENCODER_GEAR_RATIO = 12.0 / 56 * 15 / 30;
    public static final double ENCODER_ROTATIONS_PER_DEGREE = PIVOT_TO_ENCODER_GEAR_RATIO / 360.0;
    public static final double PIVOT_TO_MOTOR_GEAR_RATIO = PIVOT_TO_ENCODER_GEAR_RATIO * 30 / 324;
    public static final double MOTOR_ROTATIONS_PER_DEGREE = PIVOT_TO_MOTOR_GEAR_RATIO / 360;
    public static final double MOTOR_DEGREES_PER_ROTATION = 1.0 / MOTOR_ROTATIONS_PER_DEGREE;

    public static final double HOOD_ENCODER_MIN_ROTATION = 0;
    public static final double HOOD_ENCODER_MAX_ROTATION = 1;
}
