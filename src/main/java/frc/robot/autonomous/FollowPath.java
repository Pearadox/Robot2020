/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import static frc.robot.Constants.MPConstants.*;

import frc.robot.subsystems.Drivetrain;

import java.io.IOException;
import java.util.List;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;


public class FollowPath extends CommandBase {
  /**
   * Creates a new FollowPath.
   */
  Drivetrain drivetrain;
  List<MPPoint> leftTrajectory;
  List<MPPoint> rightTrajectory;

  double startTime;
  double currentTime;
  double lastError;

  double kP = DEFAULT_KP;
  double kD = DEFAULT_KD;
  double kV = DEFAULT_KV;
  double kA = DEFAULT_KA;
  double kH = DEFAULT_KH;
  private double lastTime = 0.0d;
  int trajIndex;

  public FollowPath(Drivetrain drivetrain, String fileName) throws IOException {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
    MPTrajectory trajectory = new MPTrajectory(fileName);
    leftTrajectory = trajectory.leftTrajectory;
    rightTrajectory = trajectory.rightTrajectory;
    // Use addRequirements() here to declare subsystem dependencies.
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    drivetrain.zeroEncoders();
    drivetrain.zeroGyro();
    startTime = Timer.getFPGATimestamp();
    lastError = 0;
    kP = SmartDashboard.getNumber("MPkP", kP);
    kV = SmartDashboard.getNumber("MPkV", kV);
    kA = SmartDashboard.getNumber("MPkA", kA);
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentTime = Timer.getFPGATimestamp();
    trajIndex = (int) Math.round((currentTime - startTime) / 0.02);
    double desiredLPos = leftTrajectory.get(trajIndex).pos;
    double desiredLVel = leftTrajectory.get(trajIndex).vel;
    double desiredLAcc = leftTrajectory.get(trajIndex).acc;
    double desiredRPos = rightTrajectory.get(trajIndex).pos;
    double desiredRVel = rightTrajectory.get(trajIndex).vel;
    double desiredRAcc = rightTrajectory.get(trajIndex).acc;
    double desiredHea = leftTrajectory.get(trajIndex).hea;

    
    double currentLPos = drivetrain.getLeftDistance();
    double currentRPos = drivetrain.getRightDistance();
    double currentHea = drivetrain.getGyroAngle().getDegrees();
  
    double leftOutput = kV * desiredLVel
                      + kA * desiredLAcc
                      + kH * (currentHea - desiredHea)
                      + kP * (lastError);
    SmartDashboard.putNumber("MPLeftOutput", leftOutput);

    double rightOutput = kV * desiredRVel
                      + kA * desiredRAcc
                      + kH * (currentHea - desiredHea)
                      + kP * (lastError);
                      // + kD * (((desiredRPos - currentRPos) - lastError) / (currentTime - lastTime));
    
    lastError = desiredLPos - currentLPos;
    lastTime = currentTime;

    drivetrain.tankDrive(leftOutput, rightOutput);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return trajIndex >= leftTrajectory.size() - 1;
  }
}
