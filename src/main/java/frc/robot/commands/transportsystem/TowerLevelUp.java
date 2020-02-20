/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.transportsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallTower;

public class TowerLevelUp extends CommandBase {
  /**
   * Creates a new TransportLevels.
   */

  BallTower ballTower;
  int lastLevel = 0;
  int currentLevel = 0;
  public TowerLevelUp(BallTower ballTower) {
    // Use addRequirements() here to declare subsystem dependencies.
    this.ballTower = ballTower;
    addRequirements(ballTower);
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    lastLevel = ballTower.getTowerLevel();
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentLevel = ballTower.getTowerLevel();
    if (currentLevel != lastLevel + 1) {
      ballTower.setTowerMotor(0.5);
    }
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    ballTower.setTowerMotor(0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if (lastLevel == 3) {
      return true;
    }
    else if (currentLevel == lastLevel + 1) {
      return true;
    }
    else { return false; }
  }
}
