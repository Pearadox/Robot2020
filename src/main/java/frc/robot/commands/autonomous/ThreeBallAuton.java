/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.autonomous;

import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.RunCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.RobotContainer;
import frc.robot.commands.intake.DeployIntake;
import frc.robot.commands.transportsystem.HopperIn;
import frc.robot.commands.transportsystem.TowerLoadIn;
import frc.robot.subsystems.BallHopper;
import frc.robot.subsystems.BallTower;
import frc.robot.subsystems.Drivetrain;
import frc.robot.subsystems.Flywheel;
import frc.robot.subsystems.Intake;

// NOTE:  Consider using this command inline, rather than writing a subclass.  For more
// information, see:
// https://docs.wpilib.org/en/latest/docs/software/commandbased/convenience-features.html
public class ThreeBallAuton extends SequentialCommandGroup {
  /**
   * Creates a new ThreeBallAuton.
   */
  public ThreeBallAuton() {
    addCommands(
      new DeployIntake(Intake.getInstance()).withTimeout(0.75),
      new InstantCommand(() -> Flywheel.getInstance().setVoltage(4.7)),
      new RunCommand(() -> Flywheel.getInstance().enabled = true).withTimeout(2),
      (new HopperIn(BallHopper.getInstance()).alongWith(
        new TowerLoadIn(BallTower.getInstance()))).withTimeout(6),
      new InstantCommand(() -> Flywheel.getInstance().enabled = false),
       new RunCommand(() -> Drivetrain.getInstance().arcadeDrive(.6, 0), Drivetrain.getInstance()).withTimeout(1)
    );
  }
}
