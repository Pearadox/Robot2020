package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;


public class HoodTrench extends CommandBase {
  private final Flywheel flywheelSubsystem;

  private double targetHoodAngle = 50;
  public HoodTrench(Flywheel flywheelSubsystem) {
    this.flywheelSubsystem = flywheelSubsystem;
    addRequirements(flywheelSubsystem);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if (!SmartDashboard.getString("HoodToggle", "HoodFault").contains("HoodTrench")) {
      new HoodForward(flywheelSubsystem);
    }
  }

  @Override
  public boolean isFinished() {
    // TODO: Make this return true when this Command no longer needs to run execute()
    if (SmartDashboard.getString("HoodToggle", "HoodFault").contains("HoodTrench")) {
      return true;
    }
    if (SmartDashboard.getNumber("hoodDegree", 0) >= targetHoodAngle) {
      SmartDashboard.putString("HoodToggle", "HoodTrench");
      return true;
    }
    else {
      return false;
    }
  }

  @Override
  public void end(boolean interrupted) {

  }
}
