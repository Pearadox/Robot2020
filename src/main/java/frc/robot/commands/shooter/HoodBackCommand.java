/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;

public class HoodBackCommand extends CommandBase {
  /**
   * Creates a new HoodBackCommand.
   */
  Flywheel flywheel;
  public HoodBackCommand(Flywheel flywheel) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.flywheel = flywheel;
    addRequirements(flywheel);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    flywheel.hoodBack();
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    flywheel.stopHood();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (flywheel.getHoodCurrent() > 1.5) {
      return true;
    }
    else if (flywheel.getHoodSwitch()) {
      flywheel.zeroHood();
      return true;
    }
    else {
      return false;
    }
  }
}
