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
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.Button;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.autonomous.TLine;
import frc.robot.commands.drivetrain.JoystickDrive;
import frc.robot.commands.intake.IntakeRollers;
// import frc.robot.commands.shooter.*;
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
    boolean xbox = false;
    if (!xbox) {
      drivetrain.setDefaultCommand(new JoystickDrive(drivetrain, true));
      btn1.whileHeld(
      new TowerLoadIn(ballTower).withTimeout(.5)
        .andThen(new HopperIn(ballHopper).alongWith(new TowerLoadIn(ballTower)))
    ).whenReleased(
      () -> {
        ballTower.stopTower();
        ballHopper.stopHopperMotor();
      }
    );
    }  else {
      drivetrain.setDefaultCommand(
        new RunCommand( () ->
          drivetrain.arcadeDrive(
            Math.copySign(driverJoyStick.getRawAxis(1) *driverJoyStick.getRawAxis(1), -driverJoyStick.getRawAxis(1)), 
            Math.copySign(driverJoyStick.getRawAxis(4) *driverJoyStick.getRawAxis(4), -driverJoyStick.getRawAxis(4)))
        , drivetrain)
      );
      new Button(() -> driverJoyStick.getRawAxis(3) > 0.5) {}
      .whileHeld(
      new TowerLoadIn(ballTower).withTimeout(.5)
        .andThen(new HopperIn(ballHopper).alongWith(new TowerLoadIn(ballTower)))
    ).whenReleased(
      () -> {
        ballTower.stopTower();
        ballHopper.stopHopperMotor();
      }
    );
    }
    

    // /*
    //
    //
    // Basic Run functionality*
    // btn2.whileHeld(new RunCommand(() -> {
    //   climber.setClimbMotor(-1);
    // }, climber)).whenReleased(climber::stopClimbMotor);

    // btn3.whenPressed(new RunCommand(() -> {
    //   ballTower.setTowerMotor(0.75);
    // }, ballTower)).whenReleased(new InstantCommand(
    //     () -> {
    //       ballTower.stopTower();
    //     }, ballTower));

    // btn4.whenPressed(new RunCommand(() -> {
    //   ballHopper.setHopperMotor(0.75);
    // }, ballHopper)).whenReleased(new InstantCommand(
    //     () -> {
    //       ballHopper.stopHopperMotor();
    //     }, ballHopper));

    // btn5.whenPressed(new RunCommand(() -> {
    //   climber.setTransverseMotor(0.25);
    // }, climber)).whenReleased(new InstantCommand(
    //     () -> {
    //       climber.stopTransverseMotor();
    //     }, climber));

    // btn7.whenPressed(new RunCommand(() -> {
    //   drivetrain.frontRightDrive(0.25);
    // }, drivetrain)).whenReleased(new InstantCommand(() -> {
    //   drivetrain.frontRightDrive(0);
    // }, drivetrain));

    // btn8.whenPressed(new RunCommand(() -> {
    //   drivetrain.frontLeftDrive(0.25);
    // }, drivetrain)).whenReleased(new InstantCommand(() -> {
    //   drivetrain.frontLeftDrive(0);
    // }, drivetrain));

    // btn9.whenPressed(new RunCommand(() -> {
    // btn9.whenPressed(() -> flywheel.enabled = true).whenReleased(() -> flywheel.enabled = false);

    btn7.whenPressed(peariscope::peariscopeToggle);wq
    btn8.whileHeld(peariscope::runBangBangPeariscope);
    
    btn10.whenPressed(flywheel::hoodBack, flywheel).whenReleased(flywheel::stopHood);
    btn11.whenPressed(flywheel::hoodForward).whenReleased(flywheel::stopHood);

    // btn11.whenPressed(new RunCommand(() -> {
    //   intake.setIntakeRoller(0.5, -0.5);
    // }, intake)).whenReleased(new InstantCommand(
    //     () -> {
    //       intake.stopIntakeRoller();
    //     }, intake));

    // btn12.whenPressed(new RunCommand(() -> {
    //   intake.setIntakeArm(1);
    // }, intake)).whenReleased(intake::stopIntakeArm);

    // // /*
    // // Reverse Buttons
    // // */
    // opbtn2.whenPressed(new RunCommand(() -> {
    //   climber.setClimbMotor(-0.25);
    // }, climber)).whenReleased(new InstantCommand(() -> {
    //   climber.setClimbMotor(0);
    // }, climber));

    // opbtn3.whenPressed(new RunCommand(() -> {
    //   ballTower.setTowerMotor(-0.25);
    // }, ballTower)).whenReleased(new InstantCommand(() -> {
    //   ballTower.setTowerMotor(0);
    // }, ballTower));

    // opbtn4.whenPressed(new RunCommand(() -> {
    //   ballHopper.setHopperMotor(-0.25);
    // }, ballHopper)).whenReleased(new InstantCommand(() -> {
    //   ballHopper.setHopperMotor(0);
    // }, ballHopper));

    // opbtn5.whenPressed(new RunCommand(() -> {
    //   climber.setTransverseMotor(-0.25);
    // }, climber)).whenReleased(new InstantCommand(() -> {
    //   climber.setTransverseMotor(0);
    // }, climber));


    // opbtn7.whenPressed(new RunCommand(() -> {
    //   drivetrain.frontRightDrive(-0.25);
    // }, drivetrain)).whenReleased(new InstantCommand(() -> {
    //   drivetrain.frontRightDrive(0);
    // }, drivetrain));

    // opbtn8.whenPressed(new RunCommand(() -> {
    //   drivetrain.frontLeftDrive(-0.25);
    // }, drivetrain)).whenReleased(new InstantCommand(() -> {
    //   drivetrain.frontLeftDrive(0);
    // }, drivetrain));

    // opbtn11.whenPressed(new RunCommand(() -> {
    //   intake.setIntakeRoller(0.5, 0.5);
    // }, intake)).whenReleased(new InstantCommand(() -> {
    //   intake.setIntakeRoller(0, 0);
    // }, intake));

    // opbtn12.whenPressed(new RunCommand(() -> {
    //   intake.setIntakeArm(-1);
    // }, intake)).whenReleased(new InstantCommand(() -> {
    //   intake.setIntakeArm(0);
    // }, intake));

    /*
     * Diagnostic Buttons 
     * 2: Climber Motor 
     * 3: BallTower Motor 
     * 4: Hopper Motor 
     * 5: Transverse Motor 
     * 6: Hood Motor 
     * 7: Right Drivetrain Motor 
     * 8: Left Drivetrain Motor 
     * 9: Left Flywheel Motor 
     * 10: Right Flywheel Motor 
     * 11: Intake Rollers Motors 
     * 12: Intake Arm Motor
     */
    //
    //
    // */

    // btn9.whenPressed(
    //     new FlywheelPID(flywheel, SmartDashboard.getNumber("TargetRPM", 0)).alongWith(new RunCommand(() -> {
    //       if (SmartDashboard.getNumber("FlywheelRPM", 0) >= SmartDashboard.getNumber("TargetRPM", 0)) {
    //         new TransportLoadInSystem();
    //         new IntakeRollers(intake);
    //       }
    //     }, intake, ballTower, ballHopper)));

    // /*
    // Competition Buttons
    // */
    /*
     * btn6.whenPressed(new IntakeToggle(intake)); 
     * btn7.whenPressed(new TransportInSystem()); 
     * btn7.whenPressed(new RunCommand( () -> { flywheel.setFlywheelMotor(3); }, flywheel )).whenReleased(new InstantCommand(
     * () -> { flywheel.setFlywheelMotor(0); } )); 
     * btn8.whileHeld(new TransportLoadInSystem().alongWith(new FlywheelPID(flywheel, 2500)));
     * btn9.whileHeld(new TransportLoadInSystem().alongWith(new FlywheelSector()));
     * btn10.whileHeld(new TransportLoadInSystem().alongWith(new FlywheelTrench()));
     * btn11.whileHeld(new IntakeRollers(intake));
     * 
     * opbtn3.whileHeld(new TowerLoadOut(ballTower)); 
     * opbtn4.whenPressed(new TowerLevelUp(ballTower)); 
     * opbtn5.whileHeld(new TransportLoadOutSystem());
     * opbtn6.whenPressed(new IntakeHome(intake)); 
     * opbtn7.whileHeld(new ClimbUp(climber)); 
     * opbtn8.whileHeld(new ClimbDown(climber));
     * opbtn9.whenPressed(new HoodSector()); 
     * opbtn10.whenPressed(new HoodTrench());
     * opbtn11.whileHeld(new TransportLoadInSystem().alongWith(new FlywheelSector())); 
     * opbtn12.whileHeld(new TransportLoadInSystem().alongWith(new FlywheelTrench()));
     */
  }

  private void configureDefaultCommands() {
    
    flywheel.setDefaultCommand(new InstantCommand(() -> flywheel.enabled = true)
    .andThen(new RunCommand(
      () -> {
        // flywheel.setHood(42);
        flywheel.setVoltage(4.5);
      }, flywheel
    )));
    intake.setDefaultCommand(new RunCommand(
      () -> {
        intake.setIntakeRoller(.3, .3);
      }, intake
    ));

    
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

  public Command getAutonomousCommand() throws IOException {
    return
      new InstantCommand(() -> flywheel.setVoltage(3.5)) //4.5 SL
        .andThen(() -> flywheel.enabled = true)
        .andThen(() -> flywheel.setHood(30)) //41 SL
        .andThen(new RunCommand(() -> {})
        .withTimeout(4))
        .andThen((new TowerLoadIn(ballTower).withTimeout(1.5))
        .andThen((new RunCommand(ballHopper::stopHopperMotor).withTimeout(0.25))
        .andThen(new TowerLoadIn(ballTower)))
        .alongWith(new HopperIn(ballHopper))
        // .alongWith(new InstantCommand(() -> intake.setIntakeRoller(.5, .5), intake))
        .withTimeout(7))
        // .andThen(() -> flywheel.enabled = false)
        .andThen(new InstantCommand(intake::stopIntakeRoller))
        .andThen(() -> {
          flywheel.setVoltage(0);
          flywheel.setHood(0);
        });
  }
}
