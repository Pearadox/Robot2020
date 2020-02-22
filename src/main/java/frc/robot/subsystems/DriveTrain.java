/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import static frc.robot.Constants.DriveTrainConstants;

import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class DriveTrain extends SubsystemBase {
  /**
   * Creates a new DriveTrain.
   */
  CANSparkMax masterLeftMotor;
  CANSparkMax slaveLeftMotor1;
  CANSparkMax slaveLeftMotor2;

  CANSparkMax masterRightMotor;
  CANSparkMax slaveRightMotor1;
  CANSparkMax slaveRightMotor2;
  public DriveTrain() {
    masterLeftMotor = new CANSparkMax(DriveTrainConstants.masterLeftMotor_ID, MotorType.kBrushless);
    slaveLeftMotor1 = new CANSparkMax(DriveTrainConstants.slaveLeftMotor1_ID, MotorType.kBrushless);
    slaveLeftMotor2 = new CANSparkMax(DriveTrainConstants.slaveLeftMotor2_ID, MotorType.kBrushless);
    masterRightMotor = new CANSparkMax(DriveTrainConstants.masterRightMotor_ID, MotorType.kBrushless);
    slaveRightMotor1 = new CANSparkMax(DriveTrainConstants.slaveRightMotor1_ID, MotorType.kBrushless);
    slaveRightMotor2 = new CANSparkMax(DriveTrainConstants.slaveRightMotor2_ID, MotorType.kBrushless);
    
  }


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
