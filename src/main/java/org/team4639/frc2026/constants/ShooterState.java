package org.team4639.frc2026.constants;

import edu.wpi.first.units.measure.Angle;
import edu.wpi.first.units.measure.AngularVelocity;

public record ShooterState(AngularVelocity shooterRPM, Angle hoodAngle, Angle turretAngle) {
}
