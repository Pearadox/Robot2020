package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.TowerConstants.*;

public class BallTower extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

  /**
   * The Singleton instance of this BallTowerSubsystem. External classes should
   * use the {@link #getInstance()} method to get the instance.
   */
  private final static BallTower INSTANCE = new BallTower();

  /**
   * Creates a new instance of this BallTowerSubsystem.
   * This constructor is private since this class is a Singleton. External classes
   * should use the {@link #getInstance()} method to get the instance.
   */
  private VictorSPX towerMotor;

  public BallTower() {
    // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
    //       in the constructor or in the robot coordination class, such as RobotContainer.
    //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
    //       such as SpeedControllers, Encoders, DigitalInputs, etc.
    towerMotor = new VictorSPX(TOWER_MOTOR);
  }

  public void setTowerMotor(double setSpeed) {
    towerMotor.set(ControlMode.PercentOutput, setSpeed);
  }

  public void inTower(double setSpeed) {
    setTowerMotor(setSpeed);
  }

  public void outTower(double setSpeed) {
    setTowerMotor(-setSpeed);
  }

  public void stopTower() { setTowerMotor(0); }
  /**
   * Returns the Singleton instance of this BallTowerSubsystem. This static method
   * should be used -- {@code BallTowerSubsystem.getInstance();} -- by external
   * classes, rather than the constructor to get the instance of this class.
   */
  public static BallTower getInstance() {
    return INSTANCE;
  }
}

