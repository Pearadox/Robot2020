package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.ShooterMode;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Limelight;

public class LimelightDistance extends CommandBase {

  private Drivetrain drivetrain;
  private double target;

  public LimelightDistance(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
  }

  @Override
  public void initialize() {
    target = ShooterMode.SETTINGS.getOrDefault(ShooterMode.currentMode, null).distance;
  }

  @Override
  public void execute() {
    double error = -(target - Limelight.getInstance().getDistanceToTarget());

    double throttle = Math.copySign(0.05, error) + error / 3.0;
    drivetrain.arcadeDrive(throttle, 0);
  }

  @Override
  public void end(boolean interrupted) {

  }

  @Override
  public boolean isFinished() {
    return Limelight.getInstance().getDistanceToTarget() > target - 0.2 &&
        Limelight.getInstance().getDistanceToTarget() < target + 0.2;
  }
}
