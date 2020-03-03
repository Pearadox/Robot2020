/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.wpilibj.PowerDistributionPanel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

public class PDP extends SubsystemBase {
  /**
   * Creates a new PDP.
   */
  PowerDistributionPanel PDP;
  public PDP() {
    PDP = new PowerDistributionPanel();
    if (!SmartDashboard.containsKey("ClimberCurrent")) {
      SmartDashboard.putNumber("ClimberCurrent", 0);
    }
    
    /*
    0 : Front Right Drivetrain
    1 : Back Right Drivetrain
    2 : Intake Arm
    3 : Climber
    4 : Transverse
    5 : Peariscope
    6 : Wheel of Fortune
    7 : Ball Tower
    8 : Ball Transport
    9 : Intake Roller Top
    10: Intake Roller Bot
    11: Flywheel Hood
    12: Left Flywheel
    13: Right Flywheel
    14: Front Left Drivetrain
    15: Back Left Drivetrain
    */
  }

  public double getClimberCurrent() {
    return PDP.getCurrent(3);
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    SmartDashboard.putNumber("ClimberCurrent", getClimberCurrent());
  }
}
