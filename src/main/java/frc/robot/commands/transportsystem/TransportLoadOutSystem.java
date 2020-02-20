package frc.robot.commands.transportsystem;

import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.BallTower;
import frc.robot.subsystems.BallTransport;

public class TransportLoadOutSystem extends ParallelCommandGroup {
  public TransportLoadOutSystem() {
    // TODO: Add your sequential commands in the super() call, e.g.
    //           super(new FooCommand(), new BarCommand());
    super(new TowerLoadOut(new BallTower()), new TransportOut(BallTransport.getInstance()));
  }
}