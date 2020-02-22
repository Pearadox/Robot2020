/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lib;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

/**
 * Add your docs here.
 */
public class SparkMaxFactory {

    public static CANSparkMax createSparkMax(int canID, MotorConfiguration config, boolean coast) {
        if (config.isFalcon500) { throw new IllegalArgumentException("Falcon 500s must be TalonFX"); }
        var motor = new CANSparkMax(canID, config.brushless ? MotorType.kBrushless : MotorType.kBrushed);
        motor.restoreFactoryDefaults();
        motor.setSmartCurrentLimit(config.stallAmpLimit, config.freeAmpLimit, 30);
        motor.setIdleMode(config.coast ? IdleMode.kCoast : IdleMode.kBrake);
        motor.setInverted(false);
        return motor;
    }

    public static CANSparkMax createInvertedSparkMax(int canID, MotorConfiguration config, boolean coast) {
        var motor = createSparkMax(canID, config, coast);
        motor.setInverted(true);
        return motor;
    }

    public static CANSparkMax createSlaveSparkMax(int canID, MotorConfiguration config, CANSparkMax master, boolean coast) {
        var motor = createSparkMax(canID, config, coast);
        motor.follow(master);
        return motor;
    }
}
