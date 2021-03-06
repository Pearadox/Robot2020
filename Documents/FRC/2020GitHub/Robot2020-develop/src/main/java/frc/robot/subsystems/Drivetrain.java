/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*-----------------------------

-----------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.DrivetrainConstants.*;

import com.kauailabs.navx.frc.AHRS;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;
import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj2.command.SubsystemBase;



public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new Drivetrain.
   */ 
   
  private final CANSparkMax frontLeftMotor;
  private final CANSparkMax backLeftMotor;
  private final CANSparkMax frontRightMotor;
  private final CANSparkMax backRightMotor;

  private final CANEncoder frontLeftEncoder;
  private final CANEncoder backLeftEncoder;
  private final CANEncoder frontRightEncoder;
  private final CANEncoder backRightEncoder;

  private final AHRS gyro;

  private final DifferentialDriveOdometry odometry;

  
  /**
   * Creates a new drivetrain.
   */
  public Drivetrain() {
    frontLeftMotor = new CANSparkMax(FRONT_LEFT_MOTOR, MotorType.kBrushless);
    backLeftMotor = new CANSparkMax(BACK_LEFT_MOTOR, MotorType.kBrushless);

    frontRightMotor = new CANSparkMax(FRONT_RIGHT_MOTOR, MotorType.kBrushless);
    backRightMotor = new CANSparkMax(BACK_RIGHT_MOTOR1, MotorType.kBrushless);
    
    backLeftMotor.follow(frontLeftMotor);
    backRightMotor.follow(frontRightMotor);

    frontLeftEncoder = new CANEncoder(frontLeftMotor);
    backLeftEncoder = new CANEncoder(backLeftMotor);

    frontRightEncoder = new CANEncoder(frontRightMotor);
    backRightEncoder = new CANEncoder(backRightMotor);

    frontLeftEncoder.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);
    backLeftEncoder.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);

    frontRightEncoder.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);
    backRightEncoder.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);

    frontLeftEncoder.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);
    backLeftEncoder.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);

    frontRightEncoder.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);
    backRightEncoder.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);

    gyro = new AHRS(SPI.Port.kMXP);
    
    odometry = new DifferentialDriveOdometry(new Rotation2d(0));
  }

  /**
   * Drives the robot using joystick or controller input.
   * @param throttle throttle (foward positive)
   * @param twist twist (clockwise positive)
   * @param squareInputs square inputs
   */
  public void arcadeDrive(double throttle, double twist, boolean squareInputs) {
    if (squareInputs) {
      throttle = Math.copySign(throttle * throttle, throttle);
      twist = Math.copySign(twist * twist, twist);
    }

    throttle = throttle < THROTTLE_DEADBAND ? 0 : throttle;
    twist = twist < TWIST_DEADBAND ? 0 : twist;

    double leftOutput = throttle + twist;
    double rightOutput = throttle - twist;

    leftOutput = Math.abs(leftOutput) > MAX_OUTPUT 
      ? Math.copySign(MAX_OUTPUT, leftOutput) : leftOutput;
    rightOutput = Math.abs(rightOutput) > MAX_OUTPUT 
      ? Math.copySign(MAX_OUTPUT, rightOutput) : rightOutput;

    frontLeftMotor.set(leftOutput);
    frontRightMotor.set(rightOutput);
  }

  public void tankDrive(double leftOutput, double rightOutput) {
    leftOutput = Math.abs(leftOutput) < THROTTLE_DEADBAND ? 0.0d : leftOutput;
    rightOutput = Math.abs(rightOutput) < THROTTLE_DEADBAND ? 0.0d : rightOutput;
    leftOutput = Math.abs(leftOutput) > MAX_OUTPUT 
      ? Math.copySign(MAX_OUTPUT, leftOutput) : leftOutput;
    rightOutput = Math.abs(rightOutput) > MAX_OUTPUT 
      ? Math.copySign(MAX_OUTPUT, rightOutput) : rightOutput;

    frontLeftMotor.set(leftOutput);
    frontRightMotor.set(rightOutput);
  }

  /**
   * Sums the left encoder positions and averages them.
   * @return total positon of left encoders in meters
   */
  public double getLeftEncoders() {
    return (frontLeftEncoder.getPosition() 
          + backLeftEncoder.getPosition()) / 2;
  }

  /**
   * Averages and returns the right encoder positions.
   * @return total position of right encoders in meters
   */
  public double getRightEncoders() {
    return (frontRightEncoder.getPosition()
          + backRightEncoder.getPosition()) / 2;
  }

  /**
   * Returns the gyro angle from NavX. Negated because NavX is positive clockwise by default.
   * @return gyro angle from NavX
   */
  public Rotation2d getGyroAngle() {
    return new Rotation2d(Math.toRadians(-gyro.getYaw()));
  }

  public Pose2d getPosition() { 
    return odometry.getPoseMeters();
  }

  public void zeroGyro() {
    gyro.zeroYaw();
  }

  /**
   * Zeroes the encoders.
   */
  public void zeroEncoders() {
    frontLeftEncoder.setPosition(0);
    backLeftEncoder.setPosition(0);
    frontRightEncoder.setPosition(0);
    backRightEncoder.setPosition(0);
  }

  /**
   * Averages the velocities read by the encoders.
   * @return the velocity of the left side of the drivetrain in meters per second.
   */
  public double getLeftVelocity() {
    return (frontLeftEncoder.getVelocity() 
        + backLeftEncoder.getVelocity()) / 2;
  }
  
  /**
   * Averages the velocities read by the encoders.
   * @return the velocity of the left side of the drivetrain in meters per second.
   */
  public double getRightVelocity() {
    return (frontRightEncoder.getVelocity()
        + backRightEncoder.getVelocity()) / 2;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    odometry.update(getGyroAngle(), getLeftEncoders(), getRightEncoders());
  }
}
