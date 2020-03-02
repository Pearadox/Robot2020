package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;


public class IntakeHome extends CommandBase {
  private final Intake intake;
  private double intakeRotation;
  private double minRotation = 0.5;

  public IntakeHome(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    intakeRotation = intake.getIntakeEncoder();
    intake.setIntakeArm(-0.5);
  }

  @Override
  public boolean isFinished() {
    // TODO: Make this return true when this Command no longer needs to run execute()
    if (intakeRotation < minRotation) {
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  public void end(boolean interrupted) {
    intake.setIntakeArm(0);
  }
}
