package frc.robot.commands.transportsystem;


import edu.wpi.first.wpilibj2.command.ParallelCommandGroup;
import frc.robot.subsystems.BallTower;
import frc.robot.subsystems.BallHopper;

public class TransportInSystem extends ParallelCommandGroup {
  public TransportInSystem() {
    // TODO: Add your sequential commands in the super() call, e.g.
    //           super(new FooCommand(), new BarCommand());
    super(new TowerLevelUp(new BallTower()), new HopperIn(BallHopper.getInstance()));
  }
}