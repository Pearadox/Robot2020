package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Flywheel;

public class HoodTrench extends SequentialCommandGroup {
  public HoodTrench() {
    // TODO: Add your sequential commands in the super() call, e.g.
    //           super(new FooCommand(), new BarCommand());
    super(new HoodedSetPoint(Flywheel.getInstance(), 50),new InstantCommand(
        () -> {
          SmartDashboard.putString("HoodToggle", "HoodTench");
        }
    ));
  }
}