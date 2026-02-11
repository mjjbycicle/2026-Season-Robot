/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.turret;

import org.team4639.lib.tunable.TunableNumber;

import java.util.List;

public class PIDs {
    public static final TunableNumber turretKp = new TunableNumber(0);
    public static final TunableNumber turretKi = new TunableNumber(0);
    public static final TunableNumber turretKd = new TunableNumber(0);
    public static final TunableNumber turretKs = new TunableNumber(0);
    public static final TunableNumber turretKv = new TunableNumber(0);
    public static final TunableNumber turretKa = new TunableNumber(0);

    public static final List<TunableNumber> tunableNumbers = List.of(
            turretKp,
            turretKi,
            turretKd,
            turretKs,
            turretKv,
            turretKa
    );
}
