package frc.robot.subsystems;

import static frc.robot.Constants.IntakeConstants.*;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;

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
  private CANPIDController pidController;
  private double DEADBAND = 1.5;
  private boolean intakePosition; // true = up, false = down
  public double kP, kI, kD, kIz, kFF, kMaxOutput, kMinOutput;

  private Intake() {
    // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
    //       in the constructor or in the robot coordination class, such as RobotContainer.
    //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
    //       such as SpeedControllers, Encoders, DigitalInputs, etc.
    intakeArm = MotorControllerFactory.createSparkMax(ARM_INTAKE_MOTOR, Motors.Neo550);
    intakeArm.setSmartCurrentLimit(27,35);
    intakeArm.setOpenLoopRampRate(0.5);
    pidController = intakeArm.getPIDController();
    intakeTopRoller = MotorControllerFactory.createSparkMax(TOP_INTAKE_MOTOR, Motors.Neo550);
    intakeBotRoller = MotorControllerFactory.createSparkMax(BOT_INTAKE_MOTOR, Motors.Neo550);
    intakeEncoder = intakeArm.getEncoder();
    if (!SmartDashboard.containsKey("IntakePosition")) {
      SmartDashboard.putBoolean("IntakePosition", intakePosition);
    }

    if (!SmartDashboard.containsKey("amplitude")){SmartDashboard.putNumber("amplitude", 0.025);}
    if (!SmartDashboard.containsKey("IntakeEncoder")) {SmartDashboard.putNumber("IntakeEncoder", 0);}
    if (!SmartDashboard.containsKey("IntakeAngle")) {SmartDashboard.putNumber("IntakeAngle", 0);}
    if (!SmartDashboard.containsKey("SetIntakeRotation")) {SmartDashboard.putNumber("SetIntakeRotation", 0);}
    if (!SmartDashboard.containsKey("IntakekP")) {SmartDashboard.putNumber("IntakekP", 0);}

    // kP = 0.1; 
    // kI = 1e-4;
    // kD = 1; 
    // kIz = 0; 
    // kFF = 0; 
    // kMaxOutput = 1; 
    // kMinOutput = -1;

    // // set PID coefficients
    // pidController.setP(kP);
    // pidController.setI(kI);
    // pidController.setD(kD);
    // pidController.setIZone(kIz);
    // pidController.setFF(kFF);
    // pidController.setOutputRange(kMinOutput, kMaxOutput);

    // // display PID coefficients on SmartDashboard
    // SmartDashboard.putNumber("P Gain", kP);
    // SmartDashboard.putNumber("I Gain", kI);
    // SmartDashboard.putNumber("D Gain", kD);
    // SmartDashboard.putNumber("I Zone", kIz);
    // SmartDashboard.putNumber("Feed Forward", kFF);
    // SmartDashboard.putNumber("Max Output", kMaxOutput);
    // SmartDashboard.putNumber("Min Output", kMinOutput);
    // SmartDashboard.putNumber("Set Rotations", 0);
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
  public double getIntakeRotation() {
    return intakeEncoder.getPosition(); // 81:1 Ratio
  }
  
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
    SmartDashboard.putNumber("IntakeEncoder", getIntakeRotation());
    double setRotation = SmartDashboard.getNumber("SetIntakeRotation", 0);
    double output = 0;
    if (setRotation < getIntakeRotation() - DEADBAND) {
      output = -0.1 + (getIntakeRotation() - setRotation) * SmartDashboard.getNumber("IntakekP", 0);
      output = output < -0.4 ? -0.4 : output;
      setIntakeArm(output);
    }
    else if (setRotation > getIntakeRotation() + DEADBAND) {
      setIntakeArm(0.1);
    }
    else {
      setIntakeArm(-0.07);
    }
    // double p = SmartDashboard.getNumber("P Gain", 0);
    // double i = SmartDashboard.getNumber("I Gain", 0);
    // double d = SmartDashboard.getNumber("D Gain", 0);
    // double iz = SmartDashboard.getNumber("I Zone", 0);
    // double ff = SmartDashboard.getNumber("Feed Forward", 0);
    // double max = SmartDashboard.getNumber("Max Output", 0);
    // double min = SmartDashboard.getNumber("Min Output", 0);
    // double rotations = SmartDashboard.getNumber("Set Rotations", 0);
    // if((p != kP)) { pidController.setP(p); kP = p; }
    // if((i != kI)) { pidController.setI(i); kI = i; }
    // if((d != kD)) { pidController.setD(d); kD = d; }
    // if((iz != kIz)) { pidController.setIZone(iz); kIz = iz; }
    // if((ff != kFF)) { pidController.setFF(ff); kFF = ff; }
    // if((max != kMaxOutput) || (min != kMinOutput)) { 
    //   pidController.setOutputRange(min, max); 
    //   kMinOutput = min; kMaxOutput = max; 
    // }
    // pidController.setReference(rotations, ControlType.kPosition);
    
    // SmartDashboard.putNumber("SetPoint", rotations);
    // SmartDashboard.putNumber("ProcessVariable", intakeEncoder.getPosition());
  }

  public static Intake getInstance() {
    return INSTANCE;
  }
}
