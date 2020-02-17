/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.EncoderType;
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
  private final CANSparkMax hoodFlyMotor;

  CANEncoder leftFlyEncoder;
  CANEncoder rightFlyEncoder;
  public CANEncoder hoodEncoder;

  public double targetFlyRPM = 0;

  public Flywheel() {
    leftFlyMotor = new CANSparkMax(LEFT_FLY_MOTOR, MotorType.kBrushless);
    rightFlyMotor = new CANSparkMax(RIGHT_FLY_MOTOR, MotorType.kBrushless);
    hoodFlyMotor = new CANSparkMax(HOOD_FLY_MOTOR, MotorType.kBrushed);
    // accelFlyMotor = CANSparkMax(ACCEL_FLY_MOTOR, MotorType.kBrushless);

    leftFlyEncoder = new CANEncoder(leftFlyMotor);
    rightFlyEncoder = new CANEncoder(rightFlyMotor);
    hoodEncoder = new CANEncoder(hoodFlyMotor, EncoderType.kQuadrature, 8192);

    leftFlyMotor.setSmartCurrentLimit(80);
    rightFlyMotor.setSmartCurrentLimit(80);
    // accelFlyMotor.setSmartCurrentLimit(80);

    if (!SmartDashboard.containsKey("FlywheelRPM")) {
      SmartDashboard.putNumber("FlywheelRPM", getFlyTargetRPM());
    }
  }

  // Set Motor Speeds
  public void setFlywheelMotor (double setVoltage) {
    leftFlyMotor.setVoltage(setVoltage);
    rightFlyMotor.setVoltage(setVoltage);
  }

  public void setHoodFlyMotor(double setPercent) {
    hoodFlyMotor.set(setPercent);
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
    return hoodEncoder.getPosition();
  }

  public double getHoodAngle() {
    return (getRawHoodAngle() * 392/18) + 5; // Offset of 5 Degrees
  } // total teeth/pinion teeth

  // reset methods Encoder
  public void resetFlywheel () {
    leftFlyEncoder.setPosition(0);
    rightFlyEncoder.setPosition(0);
  }

//  public void resetAccel () {
//    accelFlyEncoder.setPosition(0);
//  }

  public void resetSnowBlower () {
    hoodEncoder.setPosition(0);
  }

  public void resetFlywheelSubsystem () {
    resetSnowBlower();
//    resetAccel();
    resetFlywheel();
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("FlywheelRPM", getFlyTargetRPM());
  }
}