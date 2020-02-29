package frc.robot.autonomous;


import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.subsystems.Drivetrain;

import java.io.IOException;

public class TLine extends SequentialCommandGroup {
  public TLine(Drivetrain drivetrain) throws IOException {
    // TODO: Add your sequential commands in the super() call, e.g.
    //           super(new FooCommand(), new BarCommand());
    super(new FollowPath(drivetrain, "TLine"));
  }
}