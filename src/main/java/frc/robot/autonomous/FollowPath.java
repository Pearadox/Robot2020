/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import java.io.IOException;
import java.util.List;
import static frc.robot.Constants.MPConstants;

import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Drivetrain;

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

  double kP = MPConstants.kP;
  double kD = MPConstants.kD;
  double kV = MPConstants.kV;
  double kA = MPConstants.kA;
  double kH = MPConstants.kH;
  private double lastTime = 0.0d;

  public FollowPath(Drivetrain drivetrain) throws IOException {
    this.drivetrain = drivetrain;
    addRequirements(drivetrain);
    String fileName = "asd";
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
  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    currentTime = Timer.getFPGATimestamp();
    int trajIndex = (int) Math.round((currentTime - startTime) / 0.02);
    double desiredLPos = leftTrajectory.get(trajIndex).pos;
    double desiredLVel = leftTrajectory.get(trajIndex).vel;
    double desiredLAcc = leftTrajectory.get(trajIndex).acc;
    double desiredRPos = rightTrajectory.get(trajIndex).pos;
    double desiredRVel = rightTrajectory.get(trajIndex).vel;
    double desiredRAcc = rightTrajectory.get(trajIndex).acc;
    double desiredHea = leftTrajectory.get(trajIndex).hea;

    double currentLPos = drivetrain.getLeftEncoders();
    double currentRPos = drivetrain.getRightEncoders();
    double currentHea = drivetrain.getGyroAngle().getDegrees();

    double leftOutput = kV * desiredLVel
                      + kA * desiredLAcc
                      + kH * desiredHea
                      + kP * (lastError)
                      + kD * (((desiredLPos - currentLPos) - lastError) / (currentTime - lastTime));
    
                      
    double rightOutput = kV * desiredRVel
                      + kA * desiredRAcc
              
                      + kP * (lastError)
                      + kD * (((desiredRPos - currentRPos) - lastError) / (currentTime - lastTime));
    
    lastError = desiredLPos - currentLPos;
    lastTime = currentTime;
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {
    return false;
  }
}
