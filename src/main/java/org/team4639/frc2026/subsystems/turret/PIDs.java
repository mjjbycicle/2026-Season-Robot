/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.turret;

import org.team4639.lib.util.LoggedTunableNumber;

public class PIDs {
    public static final LoggedTunableNumber turretKp = new LoggedTunableNumber("Turret/kP").initDefault(0);
    public static final LoggedTunableNumber turretKi = new LoggedTunableNumber("Turret/kI").initDefault(0);
    public static final LoggedTunableNumber turretKd = new LoggedTunableNumber("Turret/kD").initDefault(0);
    public static final LoggedTunableNumber turretKs = new LoggedTunableNumber("Turret/kS").initDefault(0);
    public static final LoggedTunableNumber turretKv = new LoggedTunableNumber("Turret/kV").initDefault(0);
    public static final LoggedTunableNumber turretKa = new LoggedTunableNumber("Turret/kA").initDefault(0);
}
