/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.hood;

import org.team4639.lib.tunable.TunableNumber;

import java.util.List;

public class PIDs {
    public static final TunableNumber hoodKp = new TunableNumber(0);
    public static final TunableNumber hoodKi = new TunableNumber(0);
    public static final TunableNumber hoodKd = new TunableNumber(0);
    public static final TunableNumber hoodKs = new TunableNumber(0);
    public static final TunableNumber hoodKv = new TunableNumber(0);
    public static final TunableNumber hoodKa = new TunableNumber(0);

    public static final List<TunableNumber> tunableNumbers = List.of(
            hoodKp,
            hoodKi,
            hoodKd,
            hoodKs,
            hoodKv,
            hoodKa
    );
}
