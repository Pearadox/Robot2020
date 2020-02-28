package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.motors.MotorControllerFactory;
import frc.lib.motors.Motors;

import static frc.robot.Constants.ClimberConstants.*;

public class Climber extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

  /**
   * The Singleton instance of this ClimberSubsystem. External classes should
   * use the {@link #getInstance()} method to get the instance.
   */

  private WPI_TalonSRX climbMotor;
  private CANSparkMax transverseMotor;
  private CANEncoder transverseEncoder;
  private final static Climber INSTANCE = new Climber();

  /**
   * Creates a new instance of this ClimberSubsystem.
   * This constructor is private since this class is a Singleton. External classes
   * should use the {@link #getInstance()} method to get the instance.
   */
  private Climber() {
    // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
    //       in the constructor or in the robot coordination class, such as RobotContainer.
    //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
    //       such as SpeedControllers, Encoders, DigitalInputs, etc.
    climbMotor = MotorControllerFactory.createTalonSRX(CLIMB_MOTOR, Motors.MiniCIM);
    transverseMotor = MotorControllerFactory.createSparkMax(TRANSVERSE_CLIMB_MOTOR, Motors.Neo550);
    transverseEncoder = new CANEncoder(transverseMotor);
    transverseEncoder.setPositionConversionFactor(42);
  }

  public void setClimbMotor(double setSpeed) {
    climbMotor.set(ControlMode.PercentOutput, setSpeed);
  }

  public void setTransverseMotor(double setSpeed) {
    transverseMotor.set(setSpeed);
  }

  public double getTransverseRaw() { return transverseEncoder.getPosition();}

  public void stopClimbMotor() { setClimbMotor(0);}

  public void stopTransverseMotor() { setTransverseMotor(0);}

  /**
   * Returns the Singleton instance of this ClimberSubsystem. This static method
   * should be used -- {@code ClimberSubsystem.getInstance();} -- by external
   * classes, rather than the constructor to get the instance of this class.
   */
  public static Climber getInstance() {
    return INSTANCE;
  }

}

