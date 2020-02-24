package frc.robot.commands.shooter;


import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Flywheel;

public class FlywheelSector extends SequentialCommandGroup {
  public FlywheelSector() {
    // TODO: Add your sequential commands in the super() call, e.g.
    //           super(new FooCommand(), new BarCommand());
    super(new FlywheelPID(Flywheel.getInstance(), 2500));
  }
}