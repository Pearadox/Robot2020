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
   
  private final CANSparkMax leftMasterMotor;
  private final CANSparkMax leftSlaveMotor1;
  private final CANSparkMax leftSlaveMotor2;
  private final CANSparkMax rightMasterMotor;
  private final CANSparkMax rightSlaveMotor1;
  private final CANSparkMax rightSlaveMotor2;

  private final CANEncoder leftMasterEncoder;
  private final CANEncoder leftSlaveEncoder1;
  private final CANEncoder leftSlaveEncoder2;
  private final CANEncoder rightMasterEncoder;
  private final CANEncoder rightSlaveEncoder1;
  private final CANEncoder rightSlaveEncoder2;

  private final AHRS gyro;

  private final DifferentialDriveOdometry odometry;

  
  
  public Drivetrain() {
    leftMasterMotor = new CANSparkMax(MASTER_LEFT_MOTOR, MotorType.kBrushless);
    leftSlaveMotor1 = new CANSparkMax(SLAVE_LEFT_MOTOR1, MotorType.kBrushless);
    leftSlaveMotor2 = new CANSparkMax(SLAVE_LEFT_MOTOR2, MotorType.kBrushless);

    rightMasterMotor = new CANSparkMax(MASTER_RIGHT_MOTOR, MotorType.kBrushless);
    rightSlaveMotor1 = new CANSparkMax(SLAVE_RIGHT_MOTOR1, MotorType.kBrushless);
    rightSlaveMotor2 = new CANSparkMax(SLAVE_RIGHT_MOTOR2, MotorType.kBrushless);
    
    leftSlaveMotor1.follow(leftMasterMotor);
    leftSlaveMotor2.follow(leftMasterMotor);
    rightSlaveMotor1.follow(rightMasterMotor);
    rightSlaveMotor2.follow(rightMasterMotor);

    leftMasterEncoder = new CANEncoder(leftMasterMotor);
    leftSlaveEncoder1 = new CANEncoder(leftSlaveMotor1);
    leftSlaveEncoder2 = new CANEncoder(leftSlaveMotor2);

    rightMasterEncoder = new CANEncoder(rightMasterMotor);
    rightSlaveEncoder1 = new CANEncoder(rightSlaveMotor1);
    rightSlaveEncoder2 = new CANEncoder(rightSlaveMotor2);

    leftMasterEncoder.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);
    leftSlaveEncoder1.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);
    leftSlaveEncoder2.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);

    rightMasterEncoder.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);
    rightSlaveEncoder1.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);
    rightSlaveEncoder2.setPositionConversionFactor(DISTANCE_PER_REVOLUTION);

    leftMasterEncoder.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);
    leftSlaveEncoder1.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);
    leftSlaveEncoder2.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);

    rightMasterEncoder.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);
    rightSlaveEncoder1.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);
    rightSlaveEncoder2.setVelocityConversionFactor(DISTANCE_PER_REVOLUTION / SECONDS_PER_MINUTE);

    gyro = new AHRS(SPI.Port.kMXP);
    
    odometry = new DifferentialDriveOdometry(new Rotation2d(0));
  }

  /**
   * Drives 
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

    leftOutput = leftOutput > MAX_OUTPUT ? MAX_OUTPUT : leftOutput;
    rightOutput = rightOutput > MAX_OUTPUT ? MAX_OUTPUT : rightOutput;

    leftMasterMotor.set(leftOutput);
    rightMasterMotor.set(rightOutput);
  }

  /**
   * 
   * @return total positon of left encoders in meters
   */
  public double getLeftEncoders() {
    return (leftMasterEncoder.getPosition() 
          + leftSlaveEncoder1.getPosition()
          + leftSlaveEncoder2.getPosition()) / 3;
  }

  /**
   * 
   * @return total position of right encoders in meters
   */
  public double getRightEncoders() {
    return (rightMasterEncoder.getPosition()
          + rightSlaveEncoder1.getPosition()
          + rightSlaveEncoder2.getPosition()) / 3;
  }

  public Rotation2d getGyroAngle() {
    return new Rotation2d(Math.toRadians(gyro.getYaw()));
  }

  public Pose2d getPosition() { 
    return odometry.getPoseMeters();
  }

  public void zeroGyro() {
    gyro.zeroYaw();
  }

  public void zeroEncoders() {
    leftMasterEncoder.setPosition(0);
    leftSlaveEncoder1.setPosition(0);
    leftSlaveEncoder2.setPosition(0);
    rightMasterEncoder.setPosition(0);
    rightSlaveEncoder1.setPosition(0);
    rightSlaveEncoder2.setPosition(0);
  }

  public double getLeftVelocity() {
    return (leftMasterEncoder.getVelocity() 
        + leftSlaveEncoder1.getVelocity() 
        + leftSlaveEncoder2.getVelocity()) / 3;
  }
  
  public double getRightVelocity() {
    return (rightMasterEncoder.getVelocity()
        + rightSlaveEncoder1.getVelocity()
        + rightSlaveEncoder2.getVelocity()) / 3;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    odometry.update(getGyroAngle(), getLeftEncoders(), getRightEncoders());
  }
}
