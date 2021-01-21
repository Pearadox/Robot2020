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
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.FlywheelConstants.*;

public class Flywheel extends SubsystemBase {
  /**
   * Creates a new Flywheel.
   */
  private final CANSparkMax leftFlyMotor;
  private final CANSparkMax rightFlyMotor;
  private final CANSparkMax accelFlyMotor;
  private final CANSparkMax snowFlyMotor;

  CANEncoder leftFlyEncoder;
  CANEncoder rightFlyEncoder;
  CANEncoder snowEncoder;

  public Flywheel() {
    leftFlyMotor = CANSparkMax(LEFT_FLY_MOTOR, MotorType.kBrushless);
    rightFlyMotor = CANSparkMax(RIGHT_FLY_MOTOR, MotorType.kBrushless);
    // accelFlyMotor = CANSparkMax(ACCEL_FLY_MOTOR, MotorType.kBrushless);

    snowEncoder = CANEncoder(snowFlyMotor, EncoderType.kQuadrature, 8192);
    leftFlyEncoder = CANEncoder(leftFlyMotor);
    rightFlyEncoder = CANEncoder(rightFlyMotor);

    leftFlyMotor.setSmartCurrentLimit(80);
    rightFlyMotor.setSmartCurrentLimit(80);
    // accelFlyMotor.setSmartCurrentLimit(80);
  }

  public double getRPM() {
    return (leftFlyEncoder.getVelocity() + rightFlyEncoder.getVelocity()) / 2;
  }

  public double getRawAngle() {
    return snowEncoder.getPosition();
  }

  public double getHoodAngle() {
    return getRawAngle() * (392/18);
  }
  
  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
