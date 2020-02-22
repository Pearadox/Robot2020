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
public class Motors {
    public static MotorConfiguration Neo550;
    public static MotorConfiguration BigNeo;
    public static MotorConfiguration Snowblower;
    public static MotorConfiguration Falcon500;
    public static MotorConfiguration BagMotor;
    
    {
        Neo550 = new MotorConfiguration();
        Neo550.brushless = true;
        Neo550.integratedEncoder = true;
        Neo550.stallAmpLimit = 20;
        Neo550.freeAmpLimit = 35;

        BigNeo = new MotorConfiguration();
        BigNeo.brushless = true;
        BigNeo.integratedEncoder = true;
        BigNeo.stallAmpLimit = 60;
        BigNeo.freeAmpLimit = 80;

        Snowblower = new MotorConfiguration();
        Snowblower.brushless = false;
        Snowblower.integratedEncoder = false;
        Snowblower.stallAmpLimit = 70;
        Snowblower.freeAmpLimit = 80;

        Falcon500 = new MotorConfiguration();
        Falcon500.brushless = true;
        Falcon500.integratedEncoder = true;
        Falcon500.isFalcon500 = true;
        Falcon500.stallAmpLimit = 40;
        Falcon500.freeAmpLimit = 60;

        BagMotor = new MotorConfiguration();
        BagMotor.brushless = false;
        BagMotor.integratedEncoder = false;
        BagMotor.stallAmpLimit = 50;
        BagMotor.freeAmpLimit = 80;
    }
}
