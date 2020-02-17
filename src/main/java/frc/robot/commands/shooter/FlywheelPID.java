package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;

import static frc.robot.Constants.FlywheelConstants.*;


public class FlywheelPID extends CommandBase {
  private final Flywheel flywheelSubsystem;

  private double currentRPM;
  private double lastRPM = 0;
  private double errorRPM;
  private double kP;
  private double kD;
  private double targetRPM;
  private double feedForward = 0.0023;
  private double accelPercent = 0.7;

  public FlywheelPID(Flywheel flywheelSubsystem) {
    this.flywheelSubsystem = flywheelSubsystem;
    addRequirements(flywheelSubsystem);
    if (!SmartDashboard.containsKey("flywheelRPM")) {
      SmartDashboard.putNumber("flywheelRPM", 0);
    }
    if (!SmartDashboard.containsKey("HoodToggle")) {
      SmartDashboard.putString("HoodToggle", "HoodTriangle");
    }
  }

  @Override
  public void initialize() {
    kP = flykP;
    kD = flykD;
    targetRPM = flywheelSubsystem.getFlyTargetRPM();
  }

  @Override
  public void execute() {
    currentRPM = flywheelSubsystem.getFlywheelRPM();
    errorRPM = currentRPM - lastRPM;
    double setVoltage = feedForward * targetRPM + kP * (targetRPM - currentRPM) - kD * errorRPM;
//    flywheelSubsystem.setAccelMotor(12.0 * accelPercent);
    flywheelSubsystem.setFlywheelMotor(setVoltage);
    lastRPM = currentRPM;
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
