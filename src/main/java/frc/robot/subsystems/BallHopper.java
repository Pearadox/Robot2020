package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.motors.MotorControllerFactory;
import frc.lib.motors.Motors;

import static frc.robot.Constants.TransportConstants.*;

public class BallHopper extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

  /**
   * The Singleton instance of this BallTransportSubsystem. External classes should
   * use the {@link #getInstance()} method to get the instance.
   */
  private final static BallHopper INSTANCE = new BallHopper();

  /**
   * Creates a new instance of this BallTransportSubsystem.
   * This constructor is private since this class is a Singleton. External classes
   * should use the {@link #getInstance()} method to get the instance.
   */
  private VictorSPX transportMotor;

  private BallHopper() {
    // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
    //       in the constructor or in the robot coordination class, such as RobotContainer.
    //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
    //       such as SpeedControllers, Encoders, DigitalInputs, etc.
    transportMotor = MotorControllerFactory.createVictorSPX(HOPPER_MOTOR, Motors.Bag);
  }
  
  public void setTransportMotor(double setSpeed) {
    transportMotor.set(ControlMode.PercentOutput, setSpeed);
  }

  public void inTransport(double setSpeed) {
    setTransportMotor(setSpeed);
  }

  public void outTransport(double setSpeed) {
    setTransportMotor(-setSpeed);
  }
  /**
   * Returns the Singleton instance of this BallTransportSubsystem. This static method
   * should be used -- {@code BallTransportSubsystem.getInstance();} -- by external
   * classes, rather than the constructor to get the instance of this class.
   */
  public static BallHopper getInstance() {
    return INSTANCE;
  }
}

