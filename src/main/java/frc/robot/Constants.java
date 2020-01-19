/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;

/**
 * The Constants class provides a convenient place for teams to hold robot-wide numerical or boolean
 * constants.  This class should not be used for any other purpose.  All constants should be
 * declared globally (i.e. public static).  Do not put anything functional in this class.
 *
 * <p>It is advised to statically import this class (or one of its inner classes) wherever the
 * constants are needed, to reduce verbosity.
 */
public final class Constants {
  public static final class DrivetrainConstants {
    public static final double DRIVE_BASE_WIDTH = 0.0d; // Meters per second
    public static final double WHEEL_DIAMETER = 0.0d; // Meters per second
    
    public static final double DISTANCE_PER_REVOLUTION = WHEEL_DIAMETER * Math.PI;
    public static final int PULSES_PER_REVOLUTION = 0;
    public static final double DISTANCE_PER_PULSE = DISTANCE_PER_REVOLUTION / PULSES_PER_REVOLUTION;
    public static final DifferentialDriveKinematics KINEMATICS = 
        new DifferentialDriveKinematics(DRIVE_BASE_WIDTH);

    public static final double MAX_SPEED = 0.0d; // Meters per second
    public static final double MAX_ACCELERATION = 0.0d; // Meters per second^2
  }

  public static final class RamseteConstants {
    public static final double kS = 0.0d; // Volts
  }
}
