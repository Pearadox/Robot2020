/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lib.motors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.IMotorController;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.ctre.phoenix.motorcontrol.can.WPI_VictorSPX;

/**
 * Add your docs here.
 */
public class TalonSRXFactory {

    public static WPI_TalonSRX createTalonSRX(int canID, MotorConfiguration config) {
        if (!config.brushed) { throw new IllegalArgumentException("Cannot run brushless motors off a Talon SRX"); }
        var motor = new WPI_TalonSRX(canID);
        motor.setNeutralMode(config.coast ? NeutralMode.Coast : NeutralMode.Brake);
        motor.configSelectedFeedbackSensor(config.feedbackDevice);
        if (config.master != null) {
            if (!(config.master instanceof IMotorController)) {
                throw new IllegalArgumentException("Master must be a CTRE Motor Controller");
            }
            motor.follow((IMotorController) config.master);
        }
        return motor;
    }
}
