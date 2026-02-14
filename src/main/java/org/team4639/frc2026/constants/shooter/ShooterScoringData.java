/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.constants.shooter;

import edu.wpi.first.math.interpolation.InterpolatingDoubleTreeMap;

import java.util.AbstractMap;

public class ShooterScoringData {
    private static final InterpolatingDoubleTreeMap scoringDistanceToRPM = InterpolatingDoubleTreeMap.ofEntries(
            new AbstractMap.SimpleImmutableEntry<>(1.0, 0.0)
    ),
            scoringDistanceToHoodAngle = InterpolatingDoubleTreeMap.ofEntries(
                    new AbstractMap.SimpleImmutableEntry<>(1.0, 0.0)
            ),
            scoringDistanceToTOF = InterpolatingDoubleTreeMap.ofEntries(
                    new AbstractMap.SimpleImmutableEntry<>(1.0, 0.0)
            );

    public static final ShooterLookupTable shooterLookupTable = new ShooterLookupTable(
            scoringDistanceToRPM,
            scoringDistanceToHoodAngle,
            scoringDistanceToTOF
    );
}
