/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

/**
 * Add your docs here.
 */

public class MPPoint {
    public double pos;
    public double acc;
    public double vel;
    public double hea;
    
    public MPPoint(double position, double acceleration, double velocity, double heading) {
        pos = position;
        acc = acceleration;
        vel = velocity;
        hea = heading;
    }
}
