package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class LimelightAlign extends CommandBase {

  private Drivetrain drivetrain;

  public LimelightAlign(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {

  }

  @Override
  public void execute() {
    double twist = Math.copySign(0.05, Limelight.getInstance().getYawToTarget()) + Limelight.getInstance().getYawToTarget();
    drivetrain.arcadeDrive(0, twist);
  }

  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    return Limelight.getInstance().getYawToTarget() < 0.05;
  }
}
