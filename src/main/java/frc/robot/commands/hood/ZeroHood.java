/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.hood;

import edu.wpi.first.wpilibj2.command.CommandBase;
import edu.wpi.first.wpilibj2.command.Subsystem;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Hood;

public class ZeroHood extends CommandBase {
  /**
   * Creates a new HoodBackCommand.
   */
  Hood hood;
  public ZeroHood(Hood hood) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.hood = hood;
    addRequirements( hood);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    hood.motor.setVoltage(-8);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    if (!interrupted) {
      hood.motor.setSelectedSensorPosition(0);
    }
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return !hood.hoodSwitch.get();
  }
}
