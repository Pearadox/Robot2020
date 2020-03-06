package frc.robot.commands.transportsystem;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.BallTower;


public class TowerLoadIn extends CommandBase {
  private final BallTower ballTower;

  public TowerLoadIn(BallTower ballTower) {
    this.ballTower = ballTower;
    addRequirements(ballTower);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    ballTower.inTower(0.5);
  }

  @Override
  public boolean isFinished() {
    // TODO: Make this return true when this Command no longer needs to run execute()
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    ballTower.setTowerMotor(0);
  }
}
