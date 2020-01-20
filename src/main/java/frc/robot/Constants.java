/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.kinematics.DifferentialDriveKinematics;
import edu.wpi.first.wpilibj.util.Units;

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
    public static final int MASTER_RIGHT_MOTOR = 0;
    public static final int SLAVE_RIGHT_MOTOR1 = 1;
    public static final int SLAVE_RIGHT_MOTOR2 = 2;
    
    public static final int MASTER_LEFT_MOTOR = 3;
    public static final int SLAVE_LEFT_MOTOR1 = 4;
    public static final int SLAVE_LEFT_MOTOR2 = 5;

    public static final double DRIVE_BASE_WIDTH = 0.0d; // Meters 
    public static final double WHEEL_DIAMETER = Units.inchesToMeters(6.0d); // Meters 
    
    public static final double DISTANCE_PER_REVOLUTION = WHEEL_DIAMETER * Math.PI;
    public static final int PULSES_PER_REVOLUTION = 0;
    public static final double DISTANCE_PER_PULSE = DISTANCE_PER_REVOLUTION / PULSES_PER_REVOLUTION;
    public static final double SECONDS_PER_MINUTE = 60.0d;

    public static final DifferentialDriveKinematics KINEMATICS = 
        new DifferentialDriveKinematics(DRIVE_BASE_WIDTH);

    public static final double THROTTLE_DEADBAND = 0.1d;
    public static final double TWIST_DEADBAND = 0.1d;
    public static final double MAX_OUTPUT = 1.0d;

    public static final double MAX_SPEED = 0.0d; // Meters per second
    public static final double MAX_ACCELERATION = 0.0d; // Meters per second^2
  }

  public static final class RamseteConstants {
    public static final double B = 0.0d;
    public static final double ZETA = 0.0d;

    public static final double kS = 0.0d; // Volts
  }
  
  public static final class MPConstants {
    public static final double kV = 0;
    public static final double kA = 0;
    public static final double kH = 0;
    public static final double kP = 0;
    public static final double kD = 0;
  }
}
