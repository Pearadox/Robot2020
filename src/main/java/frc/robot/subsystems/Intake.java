package frc.robot.subsystems;

import static frc.robot.Constants.IntakeConstants.*;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANPIDController;
import com.revrobotics.CANSparkMax;
import com.revrobotics.ControlType;
import com.revrobotics.CANPIDController.ArbFFUnits;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.motors.MotorControllerFactory;
import frc.lib.motors.Motors;
import frc.robot.RobotContainer;

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
  public CANEncoder IntakeTopEncoder;
  private CANPIDController pidController;
  private double DEADBAND = 1.5;
  private double intakekP = -(1.0/15.0);
  private boolean intakePosition; // true = up, false = down
  public double kP, kI, kD, kIz, kFF, karbFF,kMaxOutput, kMinOutput;
  private double setRotation = 0;
  public boolean manual = false;
  public double IntakeUp = 2.0;
  public double IntakeDown = 15.0;
  private double kIntakeSpeed = 0.6;

  private Intake() {
    // TODO: Set the default command, if any, for this subsystem by calling setDefaultCommand(command)
    //       in the constructor or in the robot coordination class, such as RobotContainer.
    //       Also, you can call addChild(name, sendableChild) to associate sendables with the subsystem
    //       such as SpeedControllers, Encoders, DigitalInputs, etc.
    intakeArm = MotorControllerFactory.createSparkMax(ARM_INTAKE_MOTOR, Motors.Neo550);
    intakeArm.setSmartCurrentLimit(40);
    // intakeArm.setOpenLoopRampRate(0.5);
    // pidController = intakeArm.getPIDController();
    intakeTopRoller = MotorControllerFactory.createSparkMax(TOP_INTAKE_MOTOR, Motors.Neo550);
    intakeBotRoller = MotorControllerFactory.createSparkMax(BOT_INTAKE_MOTOR, Motors.Neo550);
    intakeEncoder = intakeArm.getEncoder();
    IntakeTopEncoder = intakeTopRoller.getEncoder();
    if (!SmartDashboard.containsKey("IntakePosition")) {
      SmartDashboard.putBoolean("IntakePosition", intakePosition);
    }

    if (!SmartDashboard.containsKey("amplitude")){SmartDashboard.putNumber("amplitude", 0.025);}
    if (!SmartDashboard.containsKey("IntakeEncoder")) {SmartDashboard.putNumber("IntakeEncoder", 0);}
    if (!SmartDashboard.containsKey("IntakeAngle")) {SmartDashboard.putNumber("IntakeAngle", 0);}
    if (!SmartDashboard.containsKey("SetIntakeRotation")) {SmartDashboard.putNumber("SetIntakeRotation", 0);}
    if (!SmartDashboard.containsKey("IntakekP")) {SmartDashboard.putNumber("IntakekP", intakekP);}
    SmartDashboard.putNumber("IntakeSetRotation", setRotation);
    SmartDashboard.putNumber("IntakeSpeed", kIntakeSpeed);
    // set PID coefficients
    // kP = 0.0025; //What we calculated by saying it we wanted 0.4 to be the result when we need to move 15 rotations (0.4/15 = 0.0027)
    // kI = 0; //adjust with smart dashboard if needed
    // kD = 0; //adjust with smart dashboard if needed
    // kIz = 0; //adjust with smart dashboard if needed
    // kFF = 0;  //Maybe needs to be -0.1? I'm not sure
    // karbFF = -0.07;
    // kMaxOutput = 0.1;
    // kMinOutput = -0.4;
    // set PID coefficients
    // pidController.setP(kP);
    // pidController.setI(kI);
    // pidController.setD(kD);
    // pidController.setIZone(kIz);
    // pidController.setFF(kFF);
    // pidController.setOutputRange(kMinOutput, kMaxOutput);

    // display PID coefficients on SmartDashboard
    // SmartDashboard.putNumber("P Gain", kP);
    // SmartDashboard.putNumber("I Gain", kI);
    // SmartDashboard.putNumber("D Gain", kD);
    // SmartDashboard.putNumber("I Zone", kIz);
    // SmartDashboard.putNumber("Feed Forward", kFF);
    // SmartDashboard.putNumber("Max Output", kMaxOutput);
    // SmartDashboard.putNumber("Min Output", kMinOutput);
    // SmartDashboard.putNumber("Set Rotations", 0);
    // SmartDashboard.putNumber("arbFF", karbFF);
  }

  /**
   * SetSpeed Methods
   */
  public void setIntakeArm(double setSpeed) {
    intakeArm.set(setSpeed);
  }

  public void setIntakeArmV(double setVoltage) {
    intakeArm.setVoltage(setVoltage);
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

  public void setIntakeRotation(double setRotation) {
    this.setRotation = setRotation;
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

  public void zeroIntakeArm() {intakeEncoder.setPosition(0);}
  /**
   * Returns the Singleton instance of this Intake. This static method
   * should be used -- {@code Intake.getInstance();} -- by external
   * classes, rather than the constructor to get the instance of this class.
   */

  @Override
  public void periodic() {
   // intakePosition = getIntakeAngle() >= 50;
   // SmartDashboard.putBoolean("IntakePosition", intakePosition);
   double IntakeSpeed = SmartDashboard.getNumber("IntakeSpeed", kIntakeSpeed);
  
   SmartDashboard.putNumber("IntakeEncoder", getIntakeRotation());
    if (manual) {
      if (RobotContainer.getOperatorJoystick().getRawButton(8)) {
        setIntakeArmV(-1*12.0);
        setIntakeRoller(0, 0);
      }
      else if (RobotContainer.getOperatorJoystick().getRawButton(7)) {
        setIntakeArmV(0.1 * 12.0);
        setIntakeRoller(IntakeSpeed, IntakeSpeed);
      }
      else {
        setIntakeArmV(-0.07 * 12.0);
      }
    }
    else {  
      double output = 0;
      if (setRotation < getIntakeRotation() - DEADBAND) {
        output = -0.1 + (getIntakeRotation() - setRotation) * SmartDashboard.getNumber("IntakekP", intakekP);
        output = output < -1 ? -1 : output;
        setIntakeArmV(output * 12.0);
      }
      else if (setRotation > getIntakeRotation() + DEADBAND) {
        setIntakeArmV(0.1 * 12.0);
      }
      else {
        setIntakeArmV(-0.07 * 12.0);
      }

      if (getIntakeRotation() < 7) {
        setIntakeRoller(0, 0);
      }
      else {
        setIntakeRoller(IntakeSpeed, IntakeSpeed);
      }
    }

    // double p = SmartDashboard.getNumber("P Gain", kP);
    // double i = SmartDashboard.getNumber("I Gain", kI);
    // double d = SmartDashboard.getNumber("D Gain", kD);
    // double iz = SmartDashboard.getNumber("I Zone", kIz);
    // double ff = SmartDashboard.getNumber("Feed Forward", kFF);
    // double max = SmartDashboard.getNumber("Max Output", 0);
    // double min = SmartDashboard.getNumber("Min Output", 0);
    // double rotations = SmartDashboard.getNumber("Set Rotations", 0);
    // double arbFF = SmartDashboard.getNumber("arbFF", -0.07);
    // if((p != kP)) { pidController.setP(p); kP = p; }
    // if((i != kI)) { pidController.setI(i); kI = i; }
    // if((d != kD)) { pidController.setD(d); kD = d; }
    // if((iz != kIz)) { pidController.setIZone(iz); kIz = iz; }
    // if((ff != kFF)) { pidController.setFF(ff); kFF = ff; }
    // if((max != kMaxOutput) || (min != kMinOutput)) { 
    //   pidController.setOutputRange(min, max); 
    //   kMinOutput = min; kMaxOutput = max; 
    // }

    // pidController.setReference(rotations, ControlType.kPosition, 0, arbFF, ArbFFUnits.kVoltage);
    
    // SmartDashboard.putNumber("SetPoint", rotations);
    // SmartDashboard.putNumber("ProcessVariable", intakeEncoder.getPosition());
  }

  public static Intake getInstance() {
    return INSTANCE;
  }
}
