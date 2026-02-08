/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.shooter;

import org.team4639.lib.util.LoggedTunableNumber;

public class PIDs {
    public static final LoggedTunableNumber shooterKp = new LoggedTunableNumber("Shooter/kP").initDefault(0);
    public static final LoggedTunableNumber shooterKi = new LoggedTunableNumber("Shooter/kI").initDefault(0);
    public static final LoggedTunableNumber shooterKd = new LoggedTunableNumber("Shooter/kD").initDefault(0);
    public static final LoggedTunableNumber shooterKs = new LoggedTunableNumber("Shooter/kS").initDefault(0);
    public static final LoggedTunableNumber shooterKv = new LoggedTunableNumber("Shooter/kV").initDefault(0);
    public static final LoggedTunableNumber shooterKa = new LoggedTunableNumber("Shooter/kA").initDefault(0);
}
