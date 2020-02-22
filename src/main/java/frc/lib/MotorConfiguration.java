/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.lib;

/**
 * Add your docs here.
 */
public class MotorConfiguration {
    public boolean brushless;
    public boolean integratedEncoder;
    public boolean isFalcon500 = false;
    public boolean coast = false;
    public int stallAmpLimit;
    public int freeAmpLimit;

    public MotorConfiguration() {

    }

    public MotorConfiguration(MotorConfiguration config) {
        brushless = config.brushless;
        integratedEncoder = config.integratedEncoder;
        isFalcon500 = config.isFalcon500;
        coast = config.coast;
        stallAmpLimit = config.stallAmpLimit;
        freeAmpLimit = config.freeAmpLimit;
    }

    
}