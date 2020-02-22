/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lib.motors;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * Add your docs here.
 */
public class SparkMaxFactory {
    public static CANSparkMax createSparkMax(int canID, MotorConfiguration config) {
        var motor = new CANSparkMax(canID, config.brushed ? MotorType.kBrushed : MotorType.kBrushless);
        motor.setIdleMode(config.coast ? IdleMode.kCoast : IdleMode.kBrake);
        motor.setInverted(config.inverted);
        motor.setSmartCurrentLimit(config.stallLimit, config.freeLimit, config.stallThreshold);
        if (config.master != null) {
            if (!(config.master instanceof CANSparkMax)) {
                throw new IllegalArgumentException("Master must be another Spark MAX");
            }
            motor.follow((CANSparkMax) config.master);
        }
        return motor;
    }
}
