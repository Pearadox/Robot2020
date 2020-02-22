/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lib;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;
import com.revrobotics.CANError;

/**
 * Add your docs here.
 */
public class TalonSRXFactory {

    public static WPI_TalonSRX createTalonSRX(int canID, MotorConfiguration config, boolean coast) {
        if (config.brushless) throw new IllegalArgumentException("TalonSRX is brushed only");
        var motor = new WPI_TalonSRX(canID);
        motor.configFactoryDefault();
        motor.setNeutralMode(config.coast ? NeutralMode.Coast : NeutralMode.Brake);
        motor.configPeakCurrentDuration(config.stallAmpLimit, 2000);
        motor.configContinuousCurrentLimit(config.freeAmpLimit);
        motor.setInverted(false);
        return motor;
    }

    public static WPI_TalonSRX createTalonSRXWithEncoder(int canID, MotorConfiguration config, boolean coast, FeedbackDevice feedbackDevice, int CPR) {
        var motor = createTalonSRX(canID, config, coast);
        motor.configSelectedFeedbackSensor(feedbackDevice);
        motor.configSelectedFeedbackCoefficient(1.0d / CPR);
        return motor;
    }

    public static WPI_TalonSRX createInvertedTalonSRX(int canID, MotorConfiguration config, boolean coast) {
        var motor = createTalonSRX(canID, config, coast);
        motor.setInverted(true);
        return motor;
    }

    public static WPI_TalonSRX createInvertedTalonSRXWithEncoder(int canID, MotorConfiguration config, boolean coast, FeedbackDevice feedbackDevice, int CPR) {
        var motor = createInvertedTalonSRX(canID, config, coast);
        motor.configSelectedFeedbackSensor(feedbackDevice);
        motor.configSelectedFeedbackCoefficient(1.0d / CPR);
        return motor;
    }

    public static WPI_VictorSPX createVictorSPX(int canID, MotorConfiguration config, boolean caost) {
        if (config.brushless) throw new IllegalStateException("VictorSPX is brushed only");
        var motor = new WPI_VictorSPX(canID);
        motor.configFactoryDefault();
        motor.setNeutralMode(config.coast ? NeutralMode.Coast : NeutralMode.Brake);
        motor.setInverted(false);
        return motor;
    }
}
