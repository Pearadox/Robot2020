/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;

public class DeployIntake extends CommandBase {
  /**
   * Creates a new DeployIntake.
   */
  private final Intake intake;
  
  public DeployIntake(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    intake.setIntakeArm(.2);
    intake.setIntakeRoller(.3, 0);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    intake.setIntakeArm(0.0);
    intake.setIntakeRoller(0, 0);
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    if(intake.getIntakeRotation() > 7)
    {
      intake.setIntakeRotation(Intake.getInstance().IntakeDown);
      return true;

    }
    else
    {
      return false;
    }
  }
}
