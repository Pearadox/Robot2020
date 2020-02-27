package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;

import static frc.robot.Constants.FlywheelConstants.*;


public class FlywheelPID extends CommandBase {
  private final Flywheel flywheel;

  private double currentRPM;
  private double lastRPM = 0;
  private double errorRPM;
  private double kP;
  private double kD;
  private double targetRPM;
  private double feedForward = 0.0023;
  // private double accelPercent = 0.7;

public Runnable alongWith;

  public FlywheelPID(Flywheel flywheel, double targetRPM) {
    this.flywheel = flywheel;
    addRequirements(flywheel);
    this.targetRPM = targetRPM;
  }

  @Override
  public void initialize() {
    kP = flykP;
    kD = flykD;
    errorRPM = 0;
  }

  @Override
  public void execute() {
    currentRPM = flywheel.getFlywheelRPM();
    errorRPM = currentRPM - lastRPM;
    double setVoltage = feedForward * targetRPM + kP * (targetRPM - currentRPM) + kD * errorRPM;
//    flywheelSubsystem.setAccelMotor(12.0 * accelPercent);
    flywheel.setFlywheelMotor(setVoltage);
    lastRPM = currentRPM;
  }

  @Override
  public boolean isFinished() {
    // TODO: Make this return true when this Command no longer needs to run execute()
    return false;
  }

  @Override
  public void end(boolean interrupted) {
    flywheel.stopFlywheel();
  }
}
