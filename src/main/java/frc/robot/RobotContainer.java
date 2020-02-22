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
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.button.JoystickButton;
import frc.robot.commands.climber.ClimbDown;
import frc.robot.commands.climber.ClimbUp;
import frc.robot.commands.intake.IntakeRollers;
import frc.robot.commands.intake.IntakeToggle;
import frc.robot.commands.shooter.*;
import frc.robot.commands.transportsystem.TransportInSystem;
import frc.robot.commands.transportsystem.TransportLoadInSystem;
import frc.robot.subsystems.*;

/**
 * This class is where the bulk of the robot should be declared. Since
 * Command-based is a "declarative" paradigm, very little robot logic should
 * actually be handled in the {@link Robot} periodic methods (other than the
 * scheduler calls). Instead, the structure of the robot (including subsystems,
 * commands, and button mappings) should be declared here.
 */
public class RobotContainer {
  // The robot's subsystems and commands are defined here...
  private Joystick driverJoyStick = new Joystick(0);
  private Joystick operatorJoystick = new Joystick(1);
  BallHopper ballTransport = BallHopper.getInstance();
  BallTower ballTower = new BallTower();
  Climber climber = Climber.getInstance();
  Intake intake = Intake.getInstance();
  Drivetrain drivetrain = new Drivetrain();
  Flywheel flywheel = new Flywheel();

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
  JoystickButton btn6 = new JoystickButton(driverJoyStick, 6);
  JoystickButton btn7 = new JoystickButton(driverJoyStick, 7);
  JoystickButton btn8 = new JoystickButton(driverJoyStick, 8);
  JoystickButton btn9 = new JoystickButton(driverJoyStick, 9);
  JoystickButton btn10 = new JoystickButton(driverJoyStick, 10);
  JoystickButton btn11 = new JoystickButton(driverJoyStick, 11);
  JoystickButton btn12 = new JoystickButton(driverJoyStick, 12);

  JoystickButton opbtn7 = new JoystickButton(operatorJoystick, 7);
  JoystickButton opbtn8 = new JoystickButton(operatorJoystick, 8);
  JoystickButton opbtn9 = new JoystickButton(operatorJoystick, 9);
  JoystickButton opbtn10 = new JoystickButton(operatorJoystick, 10);
  JoystickButton opbtn11 = new JoystickButton(operatorJoystick, 11);
  JoystickButton opbtn12 = new JoystickButton(operatorJoystick, 12);

  private void configureButtonBindings() {
    btn6.whenPressed(new IntakeToggle(intake));
    btn7.whenPressed(new TransportLoadInSystem());
    btn8.whileHeld(new TransportInSystem().alongWith(new FlywheelTriangle()));
    btn9.whileHeld(new TransportInSystem().alongWith(new FlywheelSector()));
    btn10.whileHeld(new TransportInSystem().alongWith(new FlywheelTrench()));
    btn11.whileHeld(new IntakeRollers(intake));

    opbtn7.whileHeld(new ClimbUp(climber));
    opbtn8.whileHeld(new ClimbDown(climber));
    opbtn9.whenPressed(new HoodSector());
    opbtn10.whenPressed(new HoodTrench());
    opbtn11.whileHeld(new TransportInSystem().alongWith(new FlywheelSector()));
    opbtn12.whileHeld(new TransportInSystem().alongWith(new FlywheelTrench()));
  }

  private void configureDefaultCommands() {
    if (operatorJoystick.getRawButton(2)) {
      drivetrain.setDefaultCommand(new RunCommand(
      () -> {
        drivetrain.arcadeDrive(operatorJoystick.getY(), operatorJoystick.getZ(), true);
      }, drivetrain));
    }
    else {
      drivetrain.setDefaultCommand(new RunCommand(
      () -> {
        drivetrain.arcadeDrive(driverJoyStick.getY(), driverJoyStick.getZ(), true);
      }, drivetrain));
    }
    
  }

  /**
   * Use this to pass the autonomous command to the main {@link Robot} class.
   *
   * @return the command to run in autonomous
   */
  public Command getAutonomousCommand() {
    return null;
  }
}
