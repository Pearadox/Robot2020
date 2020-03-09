package frc.robot.commands.hood;

import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Hood;

public class ZeroThenSet extends SequentialCommandGroup {
  public ZeroThenSet(Hood hood, double angle) {
    addCommands(
        new ZeroHood(hood),
        new SetHood(hood, angle)
    );
  }
}
