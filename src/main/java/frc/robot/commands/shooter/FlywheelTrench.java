package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Flywheel;

public class FlywheelTrench extends SequentialCommandGroup {
  public FlywheelTrench() {
    // TODO: Add your sequential commands in the super() call, e.g.
    //           super(new FooCommand(), new BarCommand());
    super(new FlywheelPID(Flywheel.getInstance(), 5700));
  }
}