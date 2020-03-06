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

  JoystickButton opbtn1 = new JoystickButton(operatorJoystick, 1
  );
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
      btn10.whileHeld(new TowerLoadIn(ballTower).withTimeout(.5)
          .andThen(new HopperIn(ballHopper).alongWith(new TowerLoadIn(ballTower))))
          .whenReleased(() -> {
            ballTower.stopTower();
            ballHopper.stopHopperMotor();
          });
          
      btn9.whileHeld(new HopperOut(ballHopper).withTimeout(.5)
      .andThen(new HopperOut(ballHopper).alongWith(new TowerLoadOut(ballTower))))
      .whenReleased(() -> {
        ballTower.stopTower();
        ballHopper.stopHopperMotor();
      });

    } else {
      drivetrain.setDefaultCommand(new RunCommand(() -> drivetrain.arcadeDrive(
          Math.copySign(driverJoyStick.getRawAxis(1) * driverJoyStick.getRawAxis(1), -driverJoyStick.getRawAxis(1)),
          Math.copySign(driverJoyStick.getRawAxis(4) * driverJoyStick.getRawAxis(4), -driverJoyStick.getRawAxis(4))),
          drivetrain));
      new Button(() -> driverJoyStick.getRawAxis(3) > 0.5) {
      }.whileHeld(new TowerLoadIn(ballTower).withTimeout(.5)
          .andThen(new HopperIn(ballHopper).alongWith(new TowerLoadIn(ballTower)))).whenReleased(() -> {
            ballTower.stopTower();
            ballHopper.stopHopperMotor();
          });
    }

    // /*
    // Basic Run functionality
    // /*
    
    // btn2.whileHeld(new RunCommand(() -> {
    //   climber.setClimbMotor(-0.75);
    // }, climber)).whenReleased(
    //   () -> {
    //     climber.setClimbMotor(0);
    //   }, climber
    // );

    // btn3.whenPressed(new RunCommand(() -> {
    //   ballTower.setTowerMotor(0.75);
    // }, ballTower)).whenReleased(new InstantCommand(() -> {
    //   ballTower.stopTower();
    // }, ballTower));

    // btn4.whenPressed(new RunCommand(() -> {
    //   ballHopper.setHopperMotor(0.75);
    // }, ballHopper)).whenReleased(new InstantCommand(() -> {
    //   ballHopper.stopHopperMotor();
    // }, ballHopper));

    opbtn5.whenPressed(new RunCommand(() -> {
    transverse.setTransverseMotor(1.0);
    }, climber)).whenReleased(new InstantCommand(
    () -> {
    transverse.stopTransverseMotor();
    }, climber));

    // btn7.whenPressed(new RunCommand(() -> {
    //   flywheel.setHood(42);
    // }, drivetrain)).whenReleased(new InstantCommand(() -> {
    //   flywheel.setHood(42);
    // }, drivetrain));

    // btn8.whenPressed(new RunCommand(() -> {
    // drivetrain.frontLeftDrive(0.25);
    // }, drivetrain)).whenReleased(new InstantCommand(() -> {
    // drivetrain.frontLeftDrive(0);
    // }, drivetrain));

    btn7.whenPressed(new RunCommand(() -> {
      flywheel.setVoltage(-6);
    }, flywheel)).whenReleased(new InstantCommand(
      () -> {
        flywheel.setVoltage(0);
    }, flywheel));

    btn1.whenPressed(() -> flywheel.enabled = true)
         .whenReleased(() -> flywheel.enabled = false);
    
    btn11.whenPressed(new HoodBackCommand(flywheel)).whenReleased(() -> {flywheel.stopHood();});
    btn12.whileHeld(flywheel::hoodForward).whenReleased(() -> {flywheel.stopHood();});
    btn1.whenPressed(flywheel::zeroHood);

    btn10.whenPressed(() -> {flywheel.enabled = true;}).whenReleased(() -> {flywheel.enabled = false;});

    // btn11.whenPressed(new RunCommand(() -> {
    //   intake.setIntakeRoller(0.5, -0.5);
    // }, intake)).whenReleased(new InstantCommand(() -> {
    //   intake.stopIntakeRoller();
    // }, intake));

    // btn12.whenPressed(new RunCommand(() -> {
    //   intake.setIntakeArm(-.25);
    // }, intake)).whenReleased(intake::stopIntakeArm);

    // // /*
    // // Reverse Buttons
    // // */
    
    // opbtn3.whenPressed(new DeployIntake(intake)).whenReleased( 
    //   () -> {
    //     intake.setIntakeArm(0);
    //   }, intake
    //   );
    /*
    if (operatorJoystick.getRawButton(2)) {
      transverse.setTransverseMotor(operatorJoystick.getX());
    }

    opbtn3.whenPressed(new RunCommand(() -> {
      ballTower.setTowerMotor(-0.25);
    }, ballTower)).whenReleased(new InstantCommand(() -> {
      ballTower.setTowerMotor(0);
    }, ballTower));

    opbtn4.whenPressed(new RunCommand(() -> {
      ballHopper.setHopperMotor(-0.25);
    }, ballHopper)).whenReleased(new InstantCommand(() -> {
      ballHopper.setHopperMotor(0);
    }, ballHopper));

    opbtn5.whenPressed(new RunCommand(() -> {
    transverse.setTransverseMotor(-1.0);
    }, climber)).whenReleased(new InstantCommand(() -> {
    transverse.setTransverseMotor(0);
    }, climber));

    opbtn6.whileHeld(flywheel::hoodBack).whenReleased(flywheel::stopHood);

    opbtn7.whenPressed(new RunCommand(() -> {
    drivetrain.frontRightDrive(-0.25);
    }, drivetrain)).whenReleased(new InstantCommand(() -> {
    drivetrain.frontRightDrive(0);
    }, drivetrain));

    opbtn8.whenPressed(new RunCommand(() -> {
    drivetrain.frontLeftDrive(-0.25);
    }, drivetrain)).whenReleased(new InstantCommand(() -> {
    drivetrain.frontLeftDrive(0);
    }, drivetrain));

    opbtn11.whenPressed(new RunCommand(() -> {
      intake.setIntakeRoller(0.5, 0.5);
    }, intake)).whenReleased(new InstantCommand(() -> {
      intake.setIntakeRoller(0, 0);
    }, intake));

    opbtn12.whenPressed(new RunCommand(() -> {
      intake.setIntakeArm(.1);
    }, intake)).whenReleased(new InstantCommand(() -> {
      intake.setIntakeArm(0);
    }, intake));
    */

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
    // /*
    // Button Testing
    // /*


    // btn7.whenPressed(peariscope::peariscopeToggle);
    // btn8.whileHeld(peariscope::runBangBangPeariscope);
    // btn8.whenPressed(new RunCommand(() -> {
    //   flywheel.hoodForward();
    // }, flywheel)).whenReleased(new InstantCommand(() -> {
    //   flywheel.stopHood();
    // }, flywheel));
    // btn9.whenPressed(new RunCommand(() -> {
    //   flywheel.hoodBack();
    // }, flywheel)).whenReleased(new InstantCommand(() -> {
    //   flywheel.stopHood();
    // }, flywheel));
    

    // /*
    // Competition Buttons
    // */
    // /*
    //  btn6.whenPressed(new IntakeToggle(intake)); 
    //  btn7.whileHeld(new TransportLoadInSystem())
    //      .whenReleased( () -> {
    //         ballHopper.stopHopperMotor();
    //         ballTower.stopTower();
    //       });
    //  btn7.whenPressed(new RunCommand( () -> { flywheel.setFlywheelMotor(3); }, flywheel ))
    //     .whenReleased(new InstantCommand( () -> { flywheel.setFlywheelMotor(0);})); 
    //  btn8.whileHeld(new TransportLoadInSystem().alongWith(new FlywheelPID(flywheel, 2500)));
    //  btn9.whileHeld(new TransportLoadInSystem().alongWith(new FlywheelSector()));
    //  btn10.whileHeld(new TransportLoadInSystem().alongWith(new FlywheelTrench()));
    //  btn11.whileHeld(new IntakeRollers(intake));
    // opbtn3.whileHeld(new TowerLoadOut(ballTower)); 
    // opbtn4.whenPressed(new TowerLevelUp(ballTower)); 
    // opbtn5.whileHeld(new TransportLoadOutSystem());
    // opbtn6.whenPressed(new IntakeHome(intake));
    // opbtn7.whenPressed(new ClimbRelease(climber))
    //       .whenReleased( () -> { climber.setClimbMotor(0);}, climber);
    // opbtn9.whenPressed(new HoodSector());
    // opbtn10.whenPressed(new HoodTrench());
    // opbtn11.whileHeld(new TransportLoadInSystem()
          //  .alongWith(new FlywheelSector())); 
    // opbtn12.whileHeld(new TransportLoadInSystem()
    //        .alongWith(new FlywheelTrench()));
    //  */
    
    opbtn3.whenPressed(new HangClimb(climber)).whenReleased(
      () -> {
        climber.setClimbMotor(0);
      }, climber
    );
    opbtn4.whenPressed(new ClimbRelease(climber)).whenReleased(
    () -> {
    climber.setClimbMotor(0);
    }, climber
    );
    opbtn5.whenPressed(new RunCommand(() -> {
      transverse.setTransverseMotor(1.0);
      }, climber)).whenReleased(new InstantCommand(
      () -> {
      transverse.stopTransverseMotor();
      }, climber));
    opbtn6.whenPressed(new RunCommand(() -> {
      transverse.setTransverseMotor(-1.0);
      }, climber)).whenReleased(new InstantCommand(
      () -> {
      transverse.stopTransverseMotor();
      }, climber));

    opbtn12.whenPressed(new InstantCommand(
      () -> {
        intake.manual = false;
        intake.setIntakeRotation(intake.IntakeUp);
      }, intake));
    opbtn11.whenPressed(new InstantCommand(
      () -> {
        intake.manual = false;
        intake.setIntakeRotation(intake.IntakeDown);
      }, intake));
    opbtn2.whenPressed(() -> {intake.zeroIntakeArm();});
    opbtn8.whenPressed(new InstantCommand(
      () -> {
        intake.manual = true;
      }, intake));
    opbtn7.whenPressed(new InstantCommand(
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
      }, intake))
      .whenReleased(new InstantCommand(
        () -> {
          intake.setIntakeRoller(0,0);
        }
      ));
  }

  private void configureDefaultCommands() {
    // flywheel.setDefaultCommand(new InstantCommand(() -> flywheel.enabled = true)
    // .andThen(new RunCommand(
    // () -> {
    // flywheel.setHood(42);
    // flywheel.setVoltage(4.5);
    // }, flywheel
    // )));
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

  public Command getAutonomousCommand() throws IOException {
    return new InstantCommand(() -> flywheel.setVoltage(3.0)) // 4.5 SL
        .andThen(() -> flywheel.enabled = true).andThen(() -> flywheel.setHood(30)) // 41 SL
        .andThen(new RunCommand(() -> {
        }).withTimeout(4))
        .andThen((new TowerLoadIn(ballTower).withTimeout(1.5))
            .andThen(
                (new RunCommand(ballHopper::stopHopperMotor).withTimeout(0.25)).andThen(new TowerLoadIn(ballTower)))
            .alongWith(new HopperIn(ballHopper))
            // .alongWith(new InstantCommand(() -> intake.setIntakeRoller(.5, .5), intake))
            .withTimeout(7))
        // .andThen(() -> flywheel.enabled = false)
        .andThen(new InstantCommand(intake::stopIntakeRoller)).andThen(() -> {
          flywheel.setVoltage(0);
          flywheel.setHood(0);
        });
    // .andThen(new FollowPath(drivetrain, "RStoT"))
    // .withTimeout(5);
  }
}