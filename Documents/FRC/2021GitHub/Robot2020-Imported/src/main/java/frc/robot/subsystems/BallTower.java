package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.TowerConstants.*;
import edu.wpi.first.wpilibj.DigitalInput;
import frc.lib.motors.MotorControllerFactory;
import frc.lib.motors.Motors;

public class BallTower extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

  private VictorSPX towerMotor;
  private DigitalInput towerTop;
  private DigitalInput towerBot;
  private int towerLevel;
  private boolean top = false;
  private boolean bot = false;

  private BallTower() {
    // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
    //       in the constructor or in the robot coordination class, such as RobotContainer.
    //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
    //       such as SpeedControllers, Encoders, DigitalInputs, etc.
    towerMotor = MotorControllerFactory.createVictorSPX(TOWER_MOTOR, Motors.Bag);
    towerTop = new DigitalInput(TOWER_TOP);
    towerBot = new DigitalInput(TOWER_BOTTOM);

    if (!SmartDashboard.containsKey("Tower Top")){
      SmartDashboard.putBoolean("Tower Top", top);
    }  
    if (!SmartDashboard.containsKey("Tower Bottom")){
      SmartDashboard.putBoolean("Tower Bottom", bot);
    }
  }
  /**
   * Tower Motors
   */
  public void setTowerMotor(double setSpeed) {
    towerMotor.set(ControlMode.PercentOutput, setSpeed);
  }
  public void inTower(double setSpeed) {
    setTowerMotor(setSpeed);
  }
  public void outTower(double setSpeed) {
    setTowerMotor(-setSpeed);
  }

    /**
   * Tower Stop
   */
  public void stopTower() { 
    setTowerMotor(0);
  }
  /**
   * Returns the Singleton instance of this BallTowerSubsystem. This static method
   * should be used -- {@code BallTowerSubsystem.getInstance();} -- by external
   * classes, rather than the constructor to get the instance of this class.
   */

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  private static BallTower INSTANCE = new BallTower();
  public static BallTower getInstance() {
    return INSTANCE;
  }
}