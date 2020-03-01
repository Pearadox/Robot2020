package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.FlywheelConstants.*;

import frc.lib.motors.MotorConfiguration;
import frc.lib.motors.MotorControllerFactory;
import frc.lib.motors.Motors;

public class Flywheel extends SubsystemBase {

  private final CANSparkMax leftMotor = MotorControllerFactory.createSparkMax(LEFT_FLY_MOTOR, Motors.Neo.setIdleMode(true));
  private final CANSparkMax rightMotor = MotorControllerFactory.createSparkMax(RIGHT_FLY_MOTOR, Motors.Neo.setIdleMode(true).setInverted(true));

  private final WPI_TalonSRX hoodFlyMotor = MotorControllerFactory.createTalonSRX(
    HOOD_FLY_MOTOR, Motors.Snowblower.withFeedbackDevice(new MotorConfiguration.FeedbackSensor(
        FeedbackDevice.QuadEncoder, 1)));

  private final CANEncoder leftEncoder = leftMotor.getEncoder();
  private final CANEncoder rightEncoder = rightMotor.getEncoder();

  public boolean enabled;

  private double flywheelSetpoint;
  private double kFF;
  private double kP;
  private double kD;
  private double lastError;
  private double hoodSetpoint;
  private double voltageSetpoint;

  private Flywheel() {
    hoodFlyMotor.setSelectedSensorPosition(0);
  }

  private static Flywheel INSTANCE = new Flywheel();

  public static Flywheel getInstance() {
    return INSTANCE;
  }


  public double getRawHoodAngle() {
    return hoodFlyMotor.getSelectedSensorPosition() / 8618.5;
  }

  // total teeth/pinion teeth
  public double getHoodAngle() {
    return getRawHoodAngle() * 404/20; 
  } 

  public void setSetpoint(double setpoint) {
    SmartDashboard.putNumber("TargetRPM", setpoint);
    this.flywheelSetpoint = setpoint;
  }

  public double getRPM() {
    return rightEncoder.getVelocity();
  }

  private void FlywheelPIDLoop() {
    if (!enabled) {
      rightMotor.set(0);
      leftMotor.set(0);
      return;
    }

    double output = SmartDashboard.getNumber("Target Voltage", voltageSetpoint);

    // flywheelSetpoint = SmartDashboard.getNumber("TargetRPM", flywheelSetpoint);
    // kFF = SmartDashboard.getNumber("Flywheel FF", kFF);
    // kP = SmartDashboard.getNumber("Flywheel P", kP);
    // kD = SmartDashboard.getNumber("Flywheel D", kD);

    // double error = flywheelSetpoint - getRPM();
    // double output = kFF * flywheelSetpoint + kP * error + kD * (lastError - error);
    // lastError = error;
    // SmartDashboard.putNumber("Flywheel Voltage", output);
    // rightMotor.setVoltage(output);
    // leftMotor.setVoltage(output);

    rightMotor.setVoltage(output);
    leftMotor.setVoltage(output);
  }

  private void HoodLoop() {
    if (!enabled) { return; }
    hoodSetpoint = SmartDashboard.getNumber("Target Hood", hoodSetpoint);
    
    if (getHoodAngle() > hoodSetpoint - 0.3 && getHoodAngle() < hoodSetpoint + 0.3) { stopHood(); return; }
    if (getHoodAngle() < hoodSetpoint) {
      if (getHoodAngle() >= 57) { 
        stopHood();
        return; 
      }
      hoodFlyMotor.setVoltage(7);
    } else {
      if (getHoodAngle() <= 0) { 
        stopHood();
        return; 
      }
      hoodFlyMotor.setVoltage(-7);
    }
  }

  public void setHood(double degrees) {
    SmartDashboard.putNumber("Target Hood", degrees);
  }

  public void stopHood() {
    hoodFlyMotor.setVoltage(0);
  }

  public void hoodBack() {
    hoodFlyMotor.setVoltage(-3);
  }

  public void setVoltage(double voltage) {
    SmartDashboard.putNumber("Target Voltage", voltage);
  }

  @Override
  public void periodic() {
    if (!SmartDashboard.containsKey("TargetRPM")) { SmartDashboard.putNumber("TargetRPM", flywheelSetpoint); }
    if (!SmartDashboard.containsKey("Flywheel FF")) { SmartDashboard.putNumber("Flywheel FF", kFF); }
    if (!SmartDashboard.containsKey("Flywheel P")) { SmartDashboard.putNumber("Flywheel P", kP); }
    if (!SmartDashboard.containsKey("Flywheel D")) { SmartDashboard.putNumber("Flywheel D", kD); }
    if (!SmartDashboard.containsKey("Target Hood")) { SmartDashboard.putNumber("Target Hood", hoodSetpoint); }
    if (!SmartDashboard.containsKey("Target Voltage")) { SmartDashboard.putNumber("Target Voltage", voltageSetpoint); }

    SmartDashboard.putNumber("Flywheel RPM", getRPM());
    SmartDashboard.putNumber("Hood Angle", getHoodAngle());

    FlywheelPIDLoop();
  
    HoodLoop();
  }
}