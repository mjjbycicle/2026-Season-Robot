/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.shooter;

import org.team4639.lib.tunable.TunableNumber;

import java.util.List;

public class PIDs {
    public static final TunableNumber shooterKp = new TunableNumber(0);
    public static final TunableNumber shooterKi = new TunableNumber(0);
    public static final TunableNumber shooterKd = new TunableNumber(0);
    public static final TunableNumber shooterKs = new TunableNumber(0);
    public static final TunableNumber shooterKv = new TunableNumber(0);
    public static final TunableNumber shooterKa = new TunableNumber(0);

    public static final List<TunableNumber> tunableNumbers = List.of(
            shooterKp,
            shooterKi,
            shooterKd,
            shooterKs,
            shooterKv,
            shooterKa
    );
}
