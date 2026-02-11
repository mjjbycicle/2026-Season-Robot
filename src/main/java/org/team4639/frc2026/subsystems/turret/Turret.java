/* Copyright (c) 2025-2026 FRC 4639. */

package org.team4639.frc2026.subsystems.turret;

import com.ctre.phoenix6.SignalLogger;
import edu.wpi.first.math.MathUtil;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj2.command.sysid.SysIdRoutine;
import org.littletonrobotics.junction.Logger;
import org.team4639.frc2026.RobotState;
import org.team4639.lib.tunable.TunableNumber;

import static edu.wpi.first.units.Units.Volts;

public class Turret extends SubsystemBase {
    private final RobotState state;
    private final TurretIO turretIO;
    private final EncoderIO leftEncoderIO, rightEncoderIO;
    private final TurretIOInputsAutoLogged turretInputs = new TurretIOInputsAutoLogged();
    private final EncoderIOInputsAutoLogged
            leftEncoderInputs = new EncoderIOInputsAutoLogged(),
            rightEncoderInputs = new EncoderIOInputsAutoLogged();

    private final double IDLE_TURRET_ROTATION = 0;
    private double SCORING_TURRET_ROTATION = 0;
    private double PASSING_TURRET_ROTATION = 0;

    private final double initialTurretRotation;
    private final double initialMotorRotation;

    private final SysIdRoutine sysIdRoutine;

    public enum WantedState {
        IDLE,
        SCORING,
        PASSING
    }

    public enum SystemState {
        IDLE,
        SCORING,
        PASSING
    }

    private WantedState wantedState = WantedState.IDLE;
    private SystemState systemState = SystemState.IDLE;

    public Turret(TurretIO turretIO, EncoderIO leftEncoderIO, EncoderIO rightEncoderIO, RobotState state) {
        this.turretIO = turretIO;
        this.leftEncoderIO = leftEncoderIO;
        this.rightEncoderIO = rightEncoderIO;
        this.state = state;
        turretIO.updateInputs(turretInputs);
        leftEncoderIO.updateInputs(leftEncoderInputs);
        rightEncoderIO.updateInputs(rightEncoderInputs);
        initialTurretRotation = getTurretRotation();
        initialMotorRotation = turretInputs.motorPositionRotations;

        sysIdRoutine = new SysIdRoutine(
                new SysIdRoutine.Config(
                        null,
                        null,
                        null,
                        sysIdState -> SignalLogger.writeString("SysIdTurret_State", sysIdState.toString())
                ),
                new SysIdRoutine.Mechanism(
                        voltage -> turretIO.setVoltage(voltage.in(Volts)),
                        null,
                        this
                )
        );
    }

    @Override
    public void periodic() {
        turretIO.updateInputs(turretInputs);
        leftEncoderIO.updateInputs(leftEncoderInputs);
        rightEncoderIO.updateInputs(rightEncoderInputs);
        Logger.processInputs("Turret", turretInputs);
        Logger.processInputs("Left Encoder", leftEncoderInputs);
        Logger.processInputs("Right Encoder", rightEncoderInputs);

        SystemState newState = handleStateTransitions();
        if (newState != systemState) {
            Logger.recordOutput("Turret/SystemState", newState);
            systemState = newState;
        }

        if (DriverStation.isDisabled()) {
            systemState = SystemState.IDLE;
        }

        switch (systemState) {
            case IDLE:
                handleIdle();
                break;
            case SCORING:
                handleScoring();
                break;
            case PASSING:
                handlePassing();
                break;
        }

        updateGains();
    }

    private SystemState handleStateTransitions() {
        return switch (wantedState) {
            case IDLE -> SystemState.IDLE;
            case SCORING -> SystemState.SCORING;
            case PASSING -> SystemState.PASSING;
        };
    }

    public double getTurretRotation() {
        double leftEncoderRotations = leftEncoderInputs.positionRotations;
        double rightEncoderRotations = rightEncoderInputs.positionRotations;
        double leftMultiple = 1.0 / (Constants.SHARED_GEAR_TEETH / Constants.LEFT_ENCODER_GEAR_TEETH);
        double rightMultiple = 1.0 / (Constants.SHARED_GEAR_TEETH / Constants.RIGHT_ENCODER_GEAR_TEETH);
        double leftDrive = leftEncoderRotations * leftMultiple;
        double rightDrive = rightEncoderRotations * rightMultiple;
        int iterations = 0;
        double closestLeft = leftDrive, closestRight = rightDrive;
        while (!MathUtil.isNear(leftDrive, rightDrive, 0.01) && iterations < 100) {
            if (leftDrive < rightDrive) leftDrive += leftMultiple;
            else rightDrive += rightMultiple;
            iterations++;
            if (Math.abs(leftDrive - rightDrive) < Math.abs(closestLeft - closestRight)) {
                closestLeft = leftDrive;
                closestRight = rightDrive;
            }
        }
        return (closestLeft + closestRight) / Constants.SHARED_GEAR_TO_TURRET_GEAR_RATIO;
    }

    public double getMotorDeltaRotations(double turretDeltaRotations) {
        return turretDeltaRotations / Constants.TURRET_TO_MOTOR_GEAR_RATIO;
    }

    public double getMotorRotations(double targetTurretRotations) {
        double turretDeltaRotations = targetTurretRotations - initialTurretRotation;
        return initialMotorRotation + getMotorDeltaRotations(turretDeltaRotations);
    }

    public double getNearestTurretRotation(double clampedRotation) {
        if (clampedRotation < 0.4 && clampedRotation > -0.4) {
            return clampedRotation;
        }
        double currentTurretRotation = getTurretRotation();
        if (currentTurretRotation * clampedRotation > 0) return clampedRotation;
        if (currentTurretRotation < 0 && clampedRotation > 0.4) {
            return clampedRotation - 1;
        } else if (currentTurretRotation > 0 && clampedRotation < -0.4) {
            return clampedRotation + 1;
        } else {
            return clampedRotation;
        }
    }

    private void handleIdle() {
        turretIO.setRotorRotation(getMotorRotations(IDLE_TURRET_ROTATION));
    }

    private void handleScoring() {
        double nearestTurretRotation = getNearestTurretRotation(SCORING_TURRET_ROTATION);
        turretIO.setRotorRotation(getMotorRotations(nearestTurretRotation));
    }

    private void handlePassing() {
        double nearestTurretRotation = getNearestTurretRotation(PASSING_TURRET_ROTATION);
        turretIO.setRotorRotation(getMotorRotations(nearestTurretRotation));
    }

    public double getTurretSetpoint() {
        return switch (systemState) {
            case IDLE -> IDLE_TURRET_ROTATION;
            case SCORING -> SCORING_TURRET_ROTATION;
            case PASSING -> PASSING_TURRET_ROTATION;
        };
    }

    public double getRotorSetpoint() {
        return getMotorRotations(getTurretSetpoint());
    }

    public boolean atSetpoint() {
        return MathUtil.isNear(getRotorSetpoint(), turretInputs.motorPositionRotations, Constants.ROTOR_ROTATION_TOLERANCE);
    }

    private void updateGains() {
        if (!org.team4639.frc2026.Constants.tuningMode) return;
        boolean shouldUpdate = false;
        for (TunableNumber n : PIDs.tunableNumbers) {
            if (n.hasChanged()) {
                shouldUpdate = true;
                break;
            }
        }
        if (shouldUpdate) {
            turretIO.applyNewGains();
        }
    }

    public Command sysIdQuasistatic(SysIdRoutine.Direction direction) {
        return sysIdRoutine.quasistatic(direction);
    }

    public Command sysIdDynamic(SysIdRoutine.Direction direction) {
        return sysIdRoutine.dynamic(direction);
    }
}
