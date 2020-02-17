package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.commands.shooter.FlywheelPID;
import frc.robot.subsystems.Flywheel;


public class FlywheelSector extends CommandBase {
  private final Flywheel flywheelSubsystem;

  public FlywheelSector(Flywheel flywheelSubsystem) {
    this.flywheelSubsystem = flywheelSubsystem;
    addRequirements(flywheelSubsystem);
  }

  @Override
  public void initialize() {
    flywheelSubsystem.setFlyTargetRPM(2700);
  }

  @Override
  public void execute() {
    new FlywheelPID(flywheelSubsystem);
  }

  @Override
  public boolean isFinished() {
    // TODO: Make this return true when this Command no longer needs to run execute()
    return false;
  }

  @Override
  public void end(boolean interrupted) {
  }
}
