package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;


import edu.wpi.first.wpilibj.PWM;
import edu.wpi.first.wpilibj.Servo;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
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
  private final static Climber INSTANCE = new Climber();
  public double kServoPos = 0.5;
  private Servo climbServo;

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
    climbServo = new Servo(9);
    climbMotor.configOpenloopRamp(.25);
    
    if (!SmartDashboard.containsKey("ClimbVoltage")) {
      SmartDashboard.putNumber("ClimbVoltage", 0);
    }
    if (!SmartDashboard.containsKey("ServoPos")) {
      SmartDashboard.putNumber("ServoPos", kServoPos);
    }
  }

  public void setClimbMotor(double setSpeed) {
    climbMotor.set(ControlMode.PercentOutput, setSpeed);
  }

  public void setDisengageBrake() {
    climbServo.set(0.0);
  }

  public void setEngageBrake() {
    climbServo.set(0.5);
  }

  public void stopClimbMotor() { setClimbMotor(0);}

  public double getClimbCurrent() {
    return climbMotor.getSupplyCurrent();
  }

  /**
   * Returns the Singleton instance of this ClimberSubsystem. This static method
   * should be used -- {@code ClimberSubsystem.getInstance();} -- by external
   * classes, rather than the constructor to get the instance of this class.
   */

   @Override
   public void periodic() {
     SmartDashboard.putNumber("ClimbVoltage", climbMotor.getBusVoltage());
     SmartDashboard.putNumber("ClimbCurrent", getClimbCurrent());
     double ServoPos = SmartDashboard.getNumber("ServoPos", kServoPos);
     climbServo.set(ServoPos);
   }
  public static Climber getInstance() {
    return INSTANCE;
  }

}

