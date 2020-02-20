package frc.robot.subsystems;


import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import com.revrobotics.CANSparkMaxLowLevel.MotorType;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import static frc.robot.Constants.ClimberConstants.*;

public class Climber extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

  /**
   * The Singleton instance of this ClimberSubsystem. External classes should
   * use the {@link #getInstance()} method to get the instance.
   */

  private CANSparkMax climbMotor;
  private CANSparkMax transverseMotor;
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
    climbMotor = new CANSparkMax(CLIMB_MOTOR, CANSparkMaxLowLevel.MotorType.kBrushed);
    transverseMotor = new CANSparkMax(TRANSVERSE_CLIMB_MOTOR, MotorType.kBrushed);
    climbMotor.setIdleMode(CANSparkMax.IdleMode.kBrake);
  }

  public void setClimbMotor(double setSpeed) {
    climbMotor.set(setSpeed);
  }

  public void setTransverseMotor(double setSpeed) {
    transverseMotor.set(setSpeed);
  }

  /**
   * Returns the Singleton instance of this ClimberSubsystem. This static method
   * should be used -- {@code ClimberSubsystem.getInstance();} -- by external
   * classes, rather than the constructor to get the instance of this class.
   */
  public static Climber getInstance() {
    return INSTANCE;
  }

}

