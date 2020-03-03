package frc.robot.subsystems;

import static frc.robot.Constants.IntakeConstants.*;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

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
  private CANSparkMax intakeArm;
  private CANSparkMax intakeTopRoller;
  private CANSparkMax intakeBotRoller;
  public CANEncoder intakeEncoder;
  private boolean intakePosition; // true = up, false = down

  private Intake() {
    // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
    //       in the constructor or in the robot coordination class, such as RobotContainer.
    //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
    //       such as SpeedControllers, Encoders, DigitalInputs, etc.
    intakeArm = MotorControllerFactory.createSparkMax(ARM_INTAKE_MOTOR, Motors.Neo550);
    intakeTopRoller = MotorControllerFactory.createSparkMax(TOP_INTAKE_MOTOR, Motors.Neo550);
    intakeBotRoller = MotorControllerFactory.createSparkMax(BOT_INTAKE_MOTOR, Motors.Neo550);

    if (!SmartDashboard.containsKey("IntakePosition")) {
      SmartDashboard.putBoolean("IntakePosition", intakePosition);
    }

   
    
    if (!SmartDashboard.containsKey("amplitude")){SmartDashboard.putNumber("amplitude", 0.025);}
  }

  /**
   * SetSpeed Methods
   */
  public void setIntakeArm(double setSpeed) {
    intakeArm.set(setSpeed);
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

  /**
   * Encoder Methods
   */
  // public double getIntakeAngle() {
  //   return intakeEncoder.getPosition() / 81.0; // 81:1 Ratio
  // }
  
  /**
   * Intake Position Methods
   */
  public void setIntakePosition (boolean intakePosition) {
    this.intakePosition = intakePosition;
  }

  public double calculateHoldOutput(double angle){
    double amplitude = SmartDashboard.getNumber("amplitude", 0.025);
    double equation = amplitude * Math.sin(angle*Math.PI/180);
    return equation;
  }

  
  public boolean getIntakePosition() {
    return intakePosition;
  }

  public void stopIntakeArm() { setIntakeArm(0);}
  public void stopIntakeRoller() { setIntakeRoller(0,0);}
  /**
   * Returns the Singleton instance of this Intake. This static method
   * should be used -- {@code Intake.getInstance();} -- by external
   * classes, rather than the constructor to get the instance of this class.
   */

  @Override
  public void periodic() {
   // intakePosition = getIntakeAngle() >= 50;
   // SmartDashboard.putBoolean("IntakePosition", intakePosition);
    //SmartDashboard.putNumber("IntakeEncoder", getIntakeAngle());
  }

  public static Intake getInstance() {
    return INSTANCE;
  }
}
