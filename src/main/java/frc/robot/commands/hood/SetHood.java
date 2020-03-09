package frc.robot.commands.hood;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Hood;

public class SetHood extends CommandBase {

  private Hood hood;
  private double angle;

  public SetHood(Hood hood, double angle) {
    this.hood = hood;
    addRequirements(hood);
  }

  @Override
  public void initialize() {
    if (angle > 55) {
      angle = 55;
    }
    if (angle <= 0) {
      new ZeroHood(hood).schedule();
    }
  }

  @Override
  public void execute() {
    hood.motor.setVoltage(
        Math.copySign(6, (angle - hood.getHoodAngle())) + 0.2 * (angle - hood.getHoodAngle())
    );
  }

  @Override
  public void end(boolean interrupted) {
    hood.motor.setVoltage(0);
  }

  @Override
  public boolean isFinished() {
    return hood.getHoodAngle() > angle - 0.5 || hood.getHoodAngle() < angle + 0.5;
  }
}
