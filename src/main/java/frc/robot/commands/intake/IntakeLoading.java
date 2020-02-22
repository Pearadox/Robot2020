package frc.robot.commands.intake;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Intake;


public class IntakeLoading extends CommandBase {
  private final Intake intake;
  private double intakeRotation;

  public IntakeLoading(Intake intake) {
    this.intake = intake;
    addRequirements(intake);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    intakeRotation = intake.getIntakeRotation();
    if (intakeRotation >= -2.0) {
      intake.setIntakeArm(-0.5);
    }
    else {
      intake.setIntakeArm(0.5);
    }
  }

  @Override
  public boolean isFinished() {
    // TODO: Make this return true when this Command no longer needs to run execute()
    if (intakeRotation >= -1.55 && intakeRotation <= -1.45) {
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
