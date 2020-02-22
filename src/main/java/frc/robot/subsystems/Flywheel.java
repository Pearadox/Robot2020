/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.NeutralMode;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

import frc.lib.MotorConfiguration;
import frc.lib.Motors;
import frc.lib.SparkMaxFactory;
import frc.lib.TalonSRXFactory;

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
  public final TalonSRX hoodFlyMotor;

  CANEncoder leftFlyEncoder;
  CANEncoder rightFlyEncoder;

  public double targetFlyRPM = 0;

  public Flywheel() {
    leftFlyMotor = SparkMaxFactory.createSparkMax(LEFT_FLY_MOTOR, Motors.BigNeo, true);
    rightFlyMotor = SparkMaxFactory.createInvertedSparkMax(RIGHT_FLY_MOTOR, Motors.BigNeo, true);
    hoodFlyMotor = TalonSRXFactory.createTalonSRXWithEncoder(HOOD_FLY_MOTOR, Motors.Snowblower, false, FeedbackDevice.QuadEncoder, 8192);
    // accelFlyMotor = CANSparkMax(ACCEL_FLY_MOTOR, MotorType.kBrushless);

    leftFlyEncoder = new CANEncoder(leftFlyMotor);
    rightFlyEncoder = new CANEncoder(rightFlyMotor);
    leftFlyEncoder.setVelocityConversionFactor(-1);
    rightFlyEncoder.setVelocityConversionFactor(-1);

    hoodFlyMotor.setSelectedSensorPosition(0);
    
    if (!SmartDashboard.containsKey("FlywheelRPM")) {
      SmartDashboard.putNumber("FlywheelRPM", getFlyTargetRPM());
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

  //  public  void setAccelFlyMotor (double setPercent) { accelFlyMotor.set(setPercent);}

  // Flywheel PID methods
  public void setFlyTargetRPM(double targetFlyRPM) {
    this.targetFlyRPM = targetFlyRPM;
  }

  public double getFlyTargetRPM() {
    return this.targetFlyRPM;
  }

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
  public void resetFlywheel () {
    leftFlyEncoder.setPosition(0);
    rightFlyEncoder.setPosition(0);
  }

//  public void resetAccel () {
//    accelFlyEncoder.setPosition(0);
//  }

  public void resetHoodEncoder () {
    hoodFlyMotor.setSelectedSensorPosition(0);
  }

  public void resetFlywheelSubsystem () {
    resetHoodEncoder();
//    resetAccel();
    resetFlywheel();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("FlywheelRPM", getFlyTargetRPM());
  }
}