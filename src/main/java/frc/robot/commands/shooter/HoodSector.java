package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;


public class HoodSector extends CommandBase {
  private final Flywheel flywheel;

  private double targetHoodAngle = 25;

  public HoodSector(Flywheel flywheel) {
    this.flywheel = flywheel;
    addRequirements(flywheel);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if (!SmartDashboard.getString("HoodToggle", "HoodFault").contains("HoodSector")) {
      if (SmartDashboard.getString("HoodToggle", "HoodFault").contains("HoodTrench")) {
        new HoodBack(flywheel);
      }
      else if (SmartDashboard.getString("HoodToggle", "HoodFault").contains("HoodTriangle")) {
        new HoodForward(flywheel);
      }
    }
  }

  @Override
  public boolean isFinished() {
    // TODO: Make this return true when this Command no longer needs to run execute()
    if (SmartDashboard.getString("HoodToggle", "HoodFault").contains("HoodSector")) {
      return true;
    }
    if (SmartDashboard.getNumber("hoodDegree", 0) >= targetHoodAngle) {
      SmartDashboard.putString("HoodToggle", "HoodSector");
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
