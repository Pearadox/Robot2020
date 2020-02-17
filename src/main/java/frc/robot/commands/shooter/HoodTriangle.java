package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;


public class HoodTriangle extends CommandBase {
  private final Flywheel flywheel;

  private double targetHoodAngle = 5;
  public HoodTriangle(Flywheel flywheel) {
    this.flywheel = flywheel;
    addRequirements(flywheel);
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    if (!SmartDashboard.getString("HoodToggle", "HoodFault").contains("HoodTriangle")) {
      new HoodBack(flywheel);
    }
  }

  @Override
  public boolean isFinished() {
    // TODO: Make this return true when this Command no longer needs to run execute()
    if (SmartDashboard.getString("HoodToggle", "HoodFault").contains("HoodTriangle")) {
      return true;
    }
    if (SmartDashboard.getNumber("hoodDegree", 0) >= targetHoodAngle) {
      SmartDashboard.putString("HoodToggle", "HoodTriangle");
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
