package frc.robot.commands.drivetrain;

import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.RobotContainer;
import frc.robot.subsystems.Drivetrain;

import static frc.robot.Constants.DrivetrainConstants.*;
import static frc.robot.Constants.DrivetrainConstants.MAX_OUTPUT;


public class JoystickDrive extends CommandBase {
  private final Drivetrain drivetrain;
  private boolean squareInputs;
  public JoystickDrive(Drivetrain drivetrain, boolean squareInputs) {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
    this.squareInputs = squareInputs;
  }

  @Override
  public void initialize() {
  }

  @Override
  public void execute() {
    boolean rawOperator = RobotContainer.getOperatorJoystick().getRawButton(1);
    boolean sniperMode = RobotContainer.getDriverJoystick().getRawButton(1);
    double throttle =  rawOperator ?  -RobotContainer.getOperatorJoystick().getY() : -RobotContainer.getDriverJoystick().getY();
    double twist = rawOperator ?  RobotContainer.getOperatorJoystick().getZ() : RobotContainer.getDriverJoystick().getZ();

    if (squareInputs) {
      throttle = Math.copySign(throttle * throttle, throttle);
      twist = Math.copySign(twist * twist, twist);
    }

    throttle = Math.abs(throttle) < THROTTLE_DEADBAND ? 0 : throttle;
    twist = Math.abs(twist) < TWIST_DEADBAND ? 0 : twist;
    if (rawOperator || sniperMode) {
      drivetrain.arcadeDrive(throttle * 0.25, twist * 0.25);
    }
    else {
      drivetrain.arcadeDrive(throttle, twist);
    }

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
