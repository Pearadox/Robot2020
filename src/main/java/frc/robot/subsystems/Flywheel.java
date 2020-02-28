/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;

import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import frc.lib.motors.MotorConfiguration;
import frc.lib.motors.Motors;
import frc.lib.motors.MotorControllerFactory;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.FlywheelConstants.*;

public class Flywheel extends SubsystemBase {
  /**
   * Creates a new Flywheel.
   */
  private final CANSparkMax leftFlyMotor;
  private final CANSparkMax rightFlyMotor;
//  private final CANSparkMax accelFlyMotor;
  public final WPI_TalonSRX hoodFlyMotor;

  CANEncoder leftFlyEncoder;
  CANEncoder rightFlyEncoder;

  private static Flywheel INSTANCE = new Flywheel();

  private Flywheel() {
    // leftFlyMotor = MotorControllerFactory.createSparkMax(LEFT_FLY_MOTOR, Motors.Neo.setIdleMode(true));
    // rightFlyMotor = MotorControllerFactory.createSparkMax(RIGHT_FLY_MOTOR, Motors.Neo.setIdleMode(true).setInverted(true));

    leftFlyMotor = new CANSparkMax(LEFT_FLY_MOTOR, MotorType.kBrushless);
    rightFlyMotor = new CANSparkMax(RIGHT_FLY_MOTOR, MotorType.kBrushless);
    leftFlyMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    rightFlyMotor.setIdleMode(CANSparkMax.IdleMode.kCoast);
    leftFlyMotor.setInverted(false);
    rightFlyMotor.setInverted(true);
    leftFlyMotor.setSmartCurrentLimit(80,5700,6000);
    rightFlyMotor.setSmartCurrentLimit(80,5700,6000);

    hoodFlyMotor = MotorControllerFactory.createTalonSRX(
        HOOD_FLY_MOTOR, Motors.Snowblower.withFeedbackDevice(new MotorConfiguration.FeedbackSensor(
            FeedbackDevice.QuadEncoder, 1)));
    
    leftFlyEncoder = new CANEncoder(leftFlyMotor);
    rightFlyEncoder = new CANEncoder(rightFlyMotor);
    leftFlyEncoder.setVelocityConversionFactor(-1);
    rightFlyEncoder.setVelocityConversionFactor(-1);

    hoodFlyMotor.setSelectedSensorPosition(0);
    
    if (!SmartDashboard.containsKey("FlywheelRPM")) {
      SmartDashboard.putNumber("FlywheelRPM", getFlywheelRPM());
    }
    
    if (!SmartDashboard.containsKey("TargetRPM")) {
      SmartDashboard.putNumber("TargetRPM", 0);
    }

    if (!SmartDashboard.containsKey("HoodTarget")) {
      SmartDashboard.putNumber("HoodTarget", 0);
    }

    if (!SmartDashboard.containsKey("HoodDegree")) {
      SmartDashboard.putNumber("HoodDegree", 0);
    }
  }

  // Set Motor Speeds
  public void setFlywheelMotor (double setVoltage) {
    leftFlyMotor.setVoltage(setVoltage);
    rightFlyMotor.setVoltage(setVoltage);
  }
  public void leftFlyDrive(double setVoltage) {
    leftFlyMotor.setVoltage(setVoltage);
  }
  public void rightFlyDrive(double setVoltage) {
    rightFlyMotor.setVoltage(setVoltage);
  }
  public void setHoodFlyMotor(double setPercent) {
    hoodFlyMotor.set(ControlMode.PercentOutput, setPercent);
  }

  // Flywheel PID methods
  public double getFlywheelRPM() {
    return (leftFlyEncoder.getVelocity() + rightFlyEncoder.getVelocity()) / 2;
  }

  //  Hood Angle Calculation method
  public double getRawHoodAngle() {
    return hoodFlyMotor.getSelectedSensorPosition() / 8618.5;
  }
  public double getHoodAngle() {
    return getRawHoodAngle() * 404/20; 
  } // total teeth/pinion teeth

  // reset methods Encoder
  public void zeroFlywheel() {
    leftFlyEncoder.setPosition(0);
    rightFlyEncoder.setPosition(0);
  }
  public void zeroHoodEncoder () {
    hoodFlyMotor.setSelectedSensorPosition(0);
  }
  public void zeroFlywheelSubsystem() {
    zeroHoodEncoder();
    zeroFlywheel();
  }

  // Stop Methods
  public void stopFlywheel () {
    setFlywheelMotor(0);
  }
  public void stopHood () { setHoodFlyMotor(0);}

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("FlywheelRPM", getFlywheelRPM());
  }


  public static Flywheel getInstance() {return INSTANCE;}
}