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
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj.SPI;
import edu.wpi.first.wpilibj.geometry.Pose2d;
import edu.wpi.first.wpilibj.geometry.Rotation2d;
import edu.wpi.first.wpilibj.kinematics.DifferentialDriveOdometry;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.motors.Motors;
import frc.lib.motors.MotorControllerFactory;



public class Drivetrain extends SubsystemBase {
  /**
   * Creates a new Drivetrain.
   */ 
   
  public final CANSparkMax frontLeftMotor;
  public final CANSparkMax backLeftMotor;
  public final CANSparkMax frontRightMotor;
  public final CANSparkMax backRightMotor;

  private final CANEncoder frontLeftEncoder;
  private final CANEncoder backLeftEncoder;
  private final CANEncoder frontRightEncoder;
  private final CANEncoder backRightEncoder;

  private final AHRS gyro;

  private final DifferentialDriveOdometry odometry;

  
  /**
   * Creates a new drivetrain.
   */
  private static Drivetrain INSTANCE = new Drivetrain();

  private Drivetrain() {
    frontLeftMotor = MotorControllerFactory.createSparkMax(FRONT_LEFT_MOTOR, Motors.Neo.setInverted(true));
    backLeftMotor = MotorControllerFactory.createSparkMax(BACK_LEFT_MOTOR, Motors.Neo.withMaster(frontLeftMotor));
    frontLeftMotor.setSmartCurrentLimit(80);
    backLeftMotor.setSmartCurrentLimit(80);
    frontLeftMotor.setOpenLoopRampRate(.5);
    backLeftMotor.setOpenLoopRampRate(.5);
    // backLeftMotor.setIdleMode(IdleMode.kCoast);

    frontRightMotor = MotorControllerFactory.createSparkMax(FRONT_RIGHT_MOTOR, Motors.Neo.setInverted(false));
    backRightMotor = MotorControllerFactory.createSparkMax(BACK_RIGHT_MOTOR, Motors.Neo.withMaster(frontRightMotor));
    frontRightMotor.setSmartCurrentLimit(80);
    backRightMotor.setSmartCurrentLimit(80);
    frontRightMotor.setOpenLoopRampRate(.5);
    backRightMotor.setOpenLoopRampRate(.5);
    
    // backRightMotor.setIdleMode(IdleMode.kCoast);
    
    frontLeftEncoder = new CANEncoder(frontLeftMotor);
    backLeftEncoder = new CANEncoder(backLeftMotor);

    frontRightEncoder = new CANEncoder(frontRightMotor);
    backRightEncoder = new CANEncoder(backRightMotor);

    SmartDashboard.putNumber("rightEncoderTicks", 0 );
    SmartDashboard.putNumber("rightbackEncoderTicks",0);
    SmartDashboard.putNumber("frontLeftCurrent", frontLeftMotor.getOutputCurrent());
    SmartDashboard.putNumber("backLeftCurrent", backLeftMotor.getOutputCurrent());
    SmartDashboard.putNumber("frontRightCurrent", frontRightMotor.getOutputCurrent());
    SmartDashboard.putNumber("backRightCurrent", backRightMotor.getOutputCurrent());

    SmartDashboard.putNumber("frontLeftTemp", frontLeftMotor.getMotorTemperature());
    SmartDashboard.putNumber("backLeftTemp", backLeftMotor.getMotorTemperature());
    SmartDashboard.putNumber("frontRightTemp", frontRightMotor.getMotorTemperature());
    SmartDashboard.putNumber("backRightTemp", backRightMotor.getMotorTemperature());

    // frontLeftEncoder.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);
    // backLeftEncoder.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);

    // frontRightEncoder.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);
    // backRightEncoder.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);

    // frontLeftEncoder.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);
    // backLeftEncoder.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);

    // frontRightEncoder.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);
    // backRightEncoder.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);

    gyro = new AHRS(SPI.Port.kMXP);
    
    odometry = new DifferentialDriveOdometry(new Rotation2d(0));
  }
  //Set Speeds
  public void frontLeftDrive(double setSpeed) {
    frontLeftMotor.set(setSpeed);
  }
  public void frontRightDrive(double setSpeed) {
    frontRightMotor.set(setSpeed);
  }

  /**
   * Drives the robot using joystick or controller input.
   * @param throttle throttle (foward positive)
   * @param twist twist (clockwise positive)
   */

  // Arcade Drive command
  public void arcadeDrive(double throttle, double twist) {
    

    double leftOutput = throttle + twist;
    double rightOutput = throttle - twist;

    leftOutput = Math.abs(leftOutput) > MAX_OUTPUT
        ? Math.copySign(MAX_OUTPUT, leftOutput) : leftOutput;
    rightOutput = Math.abs(rightOutput) > MAX_OUTPUT
        ? Math.copySign(MAX_OUTPUT, rightOutput) : rightOutput;

    frontLeftMotor.set(leftOutput);
    frontRightMotor.set(rightOutput);
  }

  // Tank Drive command
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

  public double getRightDistance() {
    return (getRightEncoders() * 6 * Math.PI)/ (12 * 11.48); // EncoderTicks * WHEEL DIAMETER * PI / (inch/feet) * (Neo Rotations per wheel rotation)
  }

  public double getLeftDistance() {
    return (getLeftEncoders() * 6 * Math.PI)/ (12 * 11.48); // EncoderTicks * WHEEL DIAMETER * PI / (inch/feet) * (Neo Rotations per wheel rotation)
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
    SmartDashboard.putNumber("rightEncoderTicks", frontRightEncoder.getPosition() );
    SmartDashboard.putNumber("rightbackEncoderTicks",backRightEncoder.getPosition());
    odometry.update(getGyroAngle(), getLeftEncoders(), getRightEncoders());
    
    SmartDashboard.putNumber("frontLeftCurrent", frontLeftMotor.getOutputCurrent());
    SmartDashboard.putNumber("backLeftCurrent", backLeftMotor.getOutputCurrent());
    SmartDashboard.putNumber("frontRightCurrent", frontRightMotor.getOutputCurrent());
    SmartDashboard.putNumber("backRightCurrent", backRightMotor.getOutputCurrent());    

    SmartDashboard.putNumber("frontLeftTemp", frontLeftMotor.getMotorTemperature());
    SmartDashboard.putNumber("backLeftTemp", backLeftMotor.getMotorTemperature());
    SmartDashboard.putNumber("frontRightTemp", frontRightMotor.getMotorTemperature());
    SmartDashboard.putNumber("backRightTemp", backRightMotor.getMotorTemperature());
  }

  public static Drivetrain getInstance() {return INSTANCE;}
}
