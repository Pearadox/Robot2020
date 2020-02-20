package frc.robot.subsystems;


import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.TransportConstants.*;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;

public class BallTransport extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

  /**
   * The Singleton instance of this BallTransportSubsystem. External classes should
   * use the {@link #getInstance()} method to get the instance.
   */
  private final static BallTransport INSTANCE = new BallTransport();

  /**
   * Creates a new instance of this BallTransportSubsystem.
   * This constructor is private since this class is a Singleton. External classes
   * should use the {@link #getInstance()} method to get the instance.
   */
  private VictorSPX transportMotor;
  private TalonSRX hopperMotor;

  private BallTransport() {
    // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
    //       in the constructor or in the robot coordination class, such as RobotContainer.
    //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
    //       such as SpeedControllers, Encoders, DigitalInputs, etc.
    transportMotor = new VictorSPX(TRANSPORT_MOTOR);
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
  public static BallTransport getInstance() {
    return INSTANCE;
  }
}

