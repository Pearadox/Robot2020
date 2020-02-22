package frc.robot.subsystems;


import static frc.robot.Constants.IntakeConstants.*;

import com.ctre.phoenix.motorcontrol.ControlMode;
import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.TalonSRX;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMaxLowLevel;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.motors.MotorControllerFactory;
import frc.lib.motors.Motors;

public class Intake extends SubsystemBase {

// Any variables/fields used in the constructor must appear before the "INSTANCE" variable
// so that they are initialized before the constructor is called.

  /**
   * The Singleton instance of this Intake. External classes should
   * use the {@link #getInstance()} method to get the instance.
   */
  private final static Intake INSTANCE = new Intake();

  /**
   * Creates a new instance of this Intake.
   * This constructor is private since this class is a Singleton. External classes
   * should use the {@link #getInstance()} method to get the instance.
   */
  private TalonSRX intakeArm;
  private CANSparkMax intakeTopRoller;
  private CANSparkMax intakeBotRoller;
  private boolean intakePosition; // true = up, false = down

  private Intake() {
    // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
    //       in the constructor or in the robot coordination class, such as RobotContainer.
    //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
    //       such as SpeedControllers, Encoders, DigitalInputs, etc.
    intakeArm = MotorControllerFactory.createTalonSRX(ARM_INTAKE_MOTOR, Motors.Snowblower);
    intakeTopRoller = MotorControllerFactory.createSparkMax(TOP_INTAKE_MOTOR, Motors.Neo550);
    intakeBotRoller = MotorControllerFactory.createSparkMax(TOP_INTAKE_MOTOR, Motors.Neo550);
    intakeArm.configSelectedFeedbackSensor(FeedbackDevice.QuadEncoder);
    if (!SmartDashboard.containsKey("IntakePosition")) {
      SmartDashboard.putBoolean("IntakePosition", intakePosition);;;;;;;;;;;;;;
    }
    intakeArm.setSelectedSensorPosition(0);
  }

  public void setIntakeArm(double setSpeed) {
    intakeArm.set(ControlMode.PercentOutput, setSpeed);
  }

  public void setIntakeRoller(double topSpeed, double botSpeed) {
    intakeTopRoller.set(topSpeed);
    intakeBotRoller.set(botSpeed);
  }

  public void topIntakeRoller (double setSpeed) {
    intakeTopRoller.set(setSpeed);
  }

  public void botIntakeRoller (double setSpeed) {
    intakeBotRoller.set(setSpeed);
  }

  public double getIntakeRotation() {
    return intakeArm.getSelectedSensorPosition() / 8618.5;
  }

  public void setIntakePosition(boolean intakePosition) {
    this.intakePosition = intakePosition;
  }

  public boolean getIntakePosition() {
    return intakePosition;
  }

  /**
   * Returns the Singleton instance of this Intake. This static method
   * should be used -- {@code Intake.getInstance();} -- by external
   * classes, rather than the constructor to get the instance of this class.
   */

  @Override
  public void periodic() {
    intakePosition = getIntakeRotation() > 1.5;
    SmartDashboard.putBoolean("IntakePosition", intakePosition);

  }

  public static Intake getInstance() {
    return INSTANCE;
  }
}
