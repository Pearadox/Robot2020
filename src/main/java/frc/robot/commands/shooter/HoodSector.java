package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Flywheel;

public class HoodSector extends SequentialCommandGroup {
  public HoodSector() {
    // TODO: Add your sequential commands in the super() call, e.g.
    //           super(new FooCommand(), new BarCommand());
    super(new HoodedSetPoint(new Flywheel(), 25 ),new InstantCommand(
        () -> {
          SmartDashboard.putString("HoodToggle", "HoodSector");
        }));
  }
}