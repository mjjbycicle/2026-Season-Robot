package org.team4639.frc2026.subsystems.shooter;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkFlex;
import com.revrobotics.spark.config.SparkBaseConfig;
import com.revrobotics.spark.config.SparkFlexConfig;
import edu.wpi.first.units.measure.AngularVelocity;
import edu.wpi.first.units.measure.Voltage;
import org.team4639.frc2026.util.PortConfiguration;

import static edu.wpi.first.units.Units.RevolutionsPerSecond;
import static edu.wpi.first.units.Units.Volts;

public class ShooterIOSparkFlex implements ShooterIO {
    private final SparkFlex leftShooter;
    private final SparkFlex rightShooter;

    private final SparkClosedLoopController closedLoopController;

    public static final SparkFlexConfig shooterConfig = new SparkFlexConfig();
    public static final SparkFlexConfig leaderConfig = new SparkFlexConfig();
    public static final SparkFlexConfig followerConfig = new SparkFlexConfig();

    private Voltage targetVoltage = Volts.of(0);

    private AngularVelocity targetVelocity = RevolutionsPerSecond.of(0);

    public ShooterIOSparkFlex(PortConfiguration ports) {
        shooterConfig.idleMode(SparkBaseConfig.IdleMode.kCoast);
        shooterConfig.smartCurrentLimit(40);
        shooterConfig.secondaryCurrentLimit(80);
    }
}
