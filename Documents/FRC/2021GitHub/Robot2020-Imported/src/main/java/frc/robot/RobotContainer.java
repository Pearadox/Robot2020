/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot;

import edu.wpi.first.wpilibj.GenericHID;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.autonomous.FollowPath;
import frc.robot.autonomous.ThreeBallAuton;
import frc.robot.commands.climber.ClimbRelease;
import frc.robot.commands.climber.HangClimb;
import frc.robot.commands.drivetrain.JoystickDrive;
import frc.robot.commands.intake.DeployIntake;
import frc.robot.commands.shooter.HoodBackCommand;
import frc.robot.commands.transportsystem.*;
import frc.robot.subsystems.*;

import java.io.IOException;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private static Joystick driverJoyStick = new Joystick(0);
  private static Joystick operatorJoystick = new Joystick(1);
  BallHopper ballHopper = BallHopper.getInstance();
  BallTower ballTower = BallTower.getInstance();
  Climber climber = Climber.getInstance();
  Intake intake = Intake.getInstance();
  Drivetrain drivetrain = Drivetrain.getInstance();
  Flywheel flywheel = Flywheel.getInstance();
  Peariscope peariscope = new Peariscope(drivetrain);
  Transverse transverse = Transverse.getInstance();

  /**
   * The container for the robot. Contains subsystems, OI devices, and commands.
   */

  public RobotContainer() {
    // Configure the button bindings
    configureButtonBindings();
    configureDefaultCommands();
  }

  /**
   * Use this method to define your button->command mappings. Buttons can be
   * created by instantiating a {@link GenericHID} or one of its subclasses
   * ({@link edu.wpi.first.wpilibj.Joystick} or {@link XboxController}), and then
   * passing it to a {@link edu.wpi.first.wpilibj2.command.button.JoystickButton}.
   */

  JoystickButton btn1 = new JoystickButton(driverJoyStick, 1);
  JoystickButton btn2 = new JoystickButton(driverJoyStick, 2);
  JoystickButton btn3 = new JoystickButton(driverJoyStick, 3);
  JoystickButton btn4 = new JoystickButton(driverJoyStick, 4);
  JoystickButton btn5 = new JoystickButton(driverJoyStick, 5);
  JoystickButton btn6 = new JoystickButton(driverJoyStick, 6);
  JoystickButton btn7 = new JoystickButton(driverJoyStick, 7);
  JoystickButton btn8 = new JoystickButton(driverJoyStick, 8);
  JoystickButton btn9 = new JoystickButton(driverJoyStick, 9);
  JoystickButton btn10 = new JoystickButton(driverJoyStick, 10);
  JoystickButton btn11 = new JoystickButton(driverJoyStick, 11);
  JoystickButton btn12 = new JoystickButton(driverJoyStick, 12);

  JoystickButton opbtn1 = new JoystickButton(operatorJoystick, 1);
  JoystickButton opbtn2 = new JoystickButton(operatorJoystick, 2);
  JoystickButton opbtn3 = new JoystickButton(operatorJoystick, 3);
  JoystickButton opbtn4 = new JoystickButton(operatorJoystick, 4);
  JoystickButton opbtn5 = new JoystickButton(operatorJoystick, 5);
  JoystickButton opbtn6 = new JoystickButton(operatorJoystick, 6);
  JoystickButton opbtn7 = new JoystickButton(operatorJoystick, 7);
  JoystickButton opbtn8 = new JoystickButton(operatorJoystick, 8);
  JoystickButton opbtn9 = new JoystickButton(operatorJoystick, 9);
  JoystickButton opbtn10 = new JoystickButton(operatorJoystick, 10);
  JoystickButton opbtn11 = new JoystickButton(operatorJoystick, 11);
  JoystickButton opbtn12 = new JoystickButton(operatorJoystick, 12);

  private void configureButtonBindings() {
  // boolean xbox = false;
    drivetrain.setDefaultCommand(new JoystickDrive(drivetrain, true));
    btn10.whileHeld(new TowerLoadIn(ballTower).withTimeout(.5)
        .andThen(new HopperIn(ballHopper).alongWith(new TowerLoadIn(ballTower))))
        .whenReleased(() -> {
          ballTower.stopTower();
          ballHopper.stopHopperMotor();
        });

    // } else {
    //   drivetrain.setDefaultCommand(new RunCommand(() -> drivetrain.arcadeDrive(
    //       Math.copySign(driverJoyStick.getRawAxis(1) * driverJoyStick.getRawAxis(1), -driverJoyStick.getRawAxis(1)),
    //       Math.copySign(driverJoyStick.getRawAxis(4) * driverJoyStick.getRawAxis(4), -driverJoyStick.getRawAxis(4))),
    //       drivetrain));
    //   new Button(() -> driverJoyStick.getRawAxis(3) > 0.5) {
    //   }.whileHeld(new TowerLoadIn(ballTower).withTimeout(.5)
    //       .andThen(new HopperIn(ballHopper).alongWith(new TowerLoadIn(ballTower)))).whenReleased(() -> {
    //         ballTower.stopTower();
    //         ballHopper.stopHopperMotor();
    //       });
    // }
    
    btn1.whenPressed(() -> {
      flywheel.enabled = true;
      peariscope.enable = true;
    })
    .whenReleased(() -> {
      flywheel.enabled = false;
      peariscope.enable = false;
    });

    btn2.whenPressed(new DeployIntake(intake));

    btn7.whenPressed(new RunCommand(() -> {
      flywheel.setVoltage(-6);
    }, flywheel)).whenReleased(new InstantCommand(
      () -> {
        flywheel.setVoltage(0);
    }, flywheel));

    btn9.whileHeld(new HopperOut(ballHopper).withTimeout(.5).andThen(new HopperOut(ballHopper).alongWith(new TowerLoadOut(ballTower))))
        .whenReleased(() -> {
          ballTower.stopTower();
          ballHopper.stopHopperMotor();});

    btn11.whenPressed(new HoodBackCommand(flywheel)).whenReleased(() -> {flywheel.stopHood();});
    
    

    btn12.whileHeld(flywheel::hoodForward).whenReleased(() -> {flywheel.stopHood();});
    
    opbtn2.whenPressed(() -> {intake.zeroIntakeArm();});
    

    opbtn3.whenPressed(new HangClimb(climber)).whenReleased(
      () -> {
        climber.setClimbMotor(0);
      }, climber);

    // opbtn3.whenPressed(new DeployIntake(intake)).whenReleased( 
    //   () -> {
    //     intake.setIntakeArm(0);
    //   }, intake
    //   );

    opbtn4.whenPressed(new ClimbRelease(climber)).whenReleased(
    () -> {
    climber.setClimbMotor(0);
    }, climber);
    
    opbtn5.whenPressed(new RunCommand(() -> {
      transverse.setTransverseMotor(1.0);
      }, transverse)).whenReleased(new InstantCommand(
      () -> {
      transverse.stopTransverseMotor();
      }, transverse));

    opbtn6.whenPressed(new RunCommand(() -> {
      transverse.setTransverseMotor(-1.0);
      }, transverse)).whenReleased(new InstantCommand(
      () -> {
      transverse.stopTransverseMotor();
      }, transverse));

    opbtn7.whenPressed(new InstantCommand(
      () -> {
        intake.manual = true;
      }, intake));

    opbtn8.whenPressed(new InstantCommand(
      () -> {
        intake.manual = true;
      }, intake));

    opbtn9.whenPressed(new InstantCommand(
      () ->{
        intake.setIntakeRoller(-1, -1);
      }, intake));

    opbtn10.whileHeld(new InstantCommand(
      () -> {
        intake.setIntakeRoller(0.7, -0.7);
      }, intake)).whenReleased(new InstantCommand(
        () -> {
          intake.setIntakeRoller(0,0);
      }, intake));

    opbtn11.whenPressed(new InstantCommand(
      () -> {
        intake.manual = false;
        intake.setIntakeRotation(intake.IntakeDown);
      }, intake));

    opbtn12.whenPressed(new InstantCommand(
      () -> {
        intake.manual = false;
        intake.setIntakeRotation(intake.IntakeUp);
      }, intake));

    new Button(() -> operatorJoystick.getPOV() == 0) {}.whileHeld(new TowerLoadIn(ballTower));
    new Button(() -> operatorJoystick.getPOV() == 180) {}.whileHeld(new TowerLoadOut(ballTower));
    new Button(() -> operatorJoystick.getPOV() == 90) {}.whileHeld(new HopperOut(ballHopper));
    new Button(() -> operatorJoystick.getPOV() == 270) {}.whileHeld(new HopperIn(ballHopper));
  }

  private void configureDefaultCommands() {
    // intake.setDefaultCommand(new RunCommand(
    // () -> {
    // intake.setIntakeRoller(.3, .3);
    // }, intake));
  }

  public static Joystick getDriverJoystick() {
    return driverJoyStick;
  }

  public static Joystick getOperatorJoystick() {
    return operatorJoystick;
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */

  public Command getAutonomousCommand() {
    return new ThreeBallAuton();
  }
}