package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.FlywheelConstants.*;

import frc.lib.motors.MotorConfiguration;
import frc.lib.motors.MotorControllerFactory;
import frc.lib.motors.Motors;
import frc.robot.Constants.FlywheelConstants;

public class Flywheel extends SubsystemBase {

  private final CANSparkMax leftMotor = MotorControllerFactory.createSparkMax(LEFT_FLY_MOTOR, Motors.Neo.setIdleMode(true));
  private final CANSparkMax rightMotor = MotorControllerFactory.createSparkMax(RIGHT_FLY_MOTOR, Motors.Neo.setIdleMode(true).setInverted(true));

  private final WPI_TalonSRX hoodFlyMotor = MotorControllerFactory.createTalonSRX(
    HOOD_FLY_MOTOR, Motors.Snowblower.withFeedbackDevice(new MotorConfiguration.FeedbackSensor(
        FeedbackDevice.QuadEncoder, 1)));

  private final CANEncoder leftEncoder = leftMotor.getEncoder();
  private final CANEncoder rightEncoder = rightMotor.getEncoder();
  private final DigitalInput hoodSwitch;

  public boolean enabled;

  private double flywheelSetpoint = 0;
  private double kFF = FlywheelConstants.flykFF;
  private double kP = FlywheelConstants.flykP;
  private double kD = FlywheelConstants.flykD;
  private double hoodSetpoint = 0;
  private double voltageSetpoint = 4.7;

  private Flywheel() {
    hoodFlyMotor.setSelectedSensorPosition(0);
    hoodSwitch = new DigitalInput(9);

    if (!SmartDashboard.containsKey("TargetRPM")) { SmartDashboard.putNumber("TargetRPM", flywheelSetpoint); }
    if (!SmartDashboard.containsKey("Flywheel FF")) { SmartDashboard.putNumber("Flywheel FF", kFF); }
    if (!SmartDashboard.containsKey("Flywheel P")) { SmartDashboard.putNumber("Flywheel P", kP); }
    if (!SmartDashboard.containsKey("Flywheel D")) { SmartDashboard.putNumber("Flywheel D", kD); }
    if (!SmartDashboard.containsKey("Target Voltage")) { SmartDashboard.putNumber("Target Voltage", voltageSetpoint); }
    if (!SmartDashboard.containsKey("Flywheel Left RPM")) { SmartDashboard.putNumber("Flywheel Left RPM", leftEncoder.getVelocity()); }
    if (!SmartDashboard.containsKey("Flywheel Right RPM")) { SmartDashboard.putNumber("Flywheel Right RPM", rightEncoder.getVelocity()); }

    if (!SmartDashboard.containsKey("HoodCurrent")) { SmartDashboard.putNumber("HoodCurrent", getHoodCurrent()); }
    if (!SmartDashboard.containsKey("Target Hood")) { SmartDashboard.putNumber("Target Hood", hoodSetpoint); }
    if (!SmartDashboard.containsKey("HoodAngle")) { SmartDashboard.putNumber("HoodAngle", getHoodAngle()); }
    // if (!SmartDashboard.containsKey("HoodSwitch")) { SmartDashboard.putBoolean("HoodSwitch", getHoodSwitch(); }
  }

  public void setSetpoint(double setpoint) {
    SmartDashboard.putNumber("TargetRPM", setpoint);
    this.flywheelSetpoint = setpoint;
  }

  public double getRPM() {
    return (rightEncoder.getVelocity() + leftEncoder.getVelocity())/2;
  }

  public void setVoltage(double voltage) {
    SmartDashboard.putNumber("Target Voltage", voltage);
    voltageSetpoint = voltage;
  }

  private void FlywheelPIDLoop() {
    if (!enabled) {
      rightMotor.set(0);
      leftMotor.set(0);
      // rightMotor.setVoltage(0.4);
      // leftMotor.setVoltage(0.4);
      return;
    }

    double output = SmartDashboard.getNumber("Target Voltage", voltageSetpoint);
    
    // flywheelSetpoint = SmartDashboard.getNumber("TargetRPM", flywheelSetpoint);
    // kFF = SmartDashboard.getNumber("Flywheel FF", kFF);
    // kP = SmartDashboard.getNumber("Flywheel P", kP);
    // kD = SmartDashboard.getNumber("Flywheel D", kD);

    // double error = flywheelSetpoint - getRPM();
    // double output = kFF * flywheelSetpoint + kP * error + kD * (lastError - error);
    // double lastError = error;
    // SmartDashboard.putNumber("Flywheel Voltage", output);

    rightMotor.setVoltage(output);
    leftMotor.setVoltage(output);
  }

  // public boolean getHoodSwitch() {
  //   return hoodSwitch.get();
  // }

  public double getRawHoodAngle() {
    return hoodFlyMotor.getSelectedSensorPosition() / 8618.5;
  }

  // total teeth/pinion teeth
  public double getHoodAngle() {
    return getRawHoodAngle() * 404/20; 
  } 

  public void setHood(double degrees) {
    SmartDashboard.putNumber("Target Hood", degrees);
  }

  public void stopHood() {
    hoodFlyMotor.setVoltage(0);
  }

  public void hoodForward() {
    hoodFlyMotor.setVoltage(6.0);
  }

  public void hoodBack() {
    hoodFlyMotor.setVoltage(-6.0);
  }

  public double getHoodCurrent() {
    return hoodFlyMotor.getSupplyCurrent();
  }

  public void zeroHood() {
    hoodFlyMotor.setSelectedSensorPosition(0);
  }

  private void HoodLoop() {
    if (!enabled) { return; }
    if (getHoodCurrent() < 5) {
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
    else {
      stopHood();
    }
  }

  @Override
  public void periodic() {
    SmartDashboard.putNumber("Flywheel RPM", getRPM());
    SmartDashboard.putNumber("Flywheel Left RPM", leftEncoder.getVelocity());
    SmartDashboard.putNumber("Flywheel Right RPM", rightEncoder.getVelocity());

    SmartDashboard.putNumber("Hood Angle", getHoodAngle());
    // SmartDashboard.putBoolean("HoodSwitch", getHoodSwitch());
    SmartDashboard.putNumber("HoodCurrent", getHoodCurrent());

    FlywheelPIDLoop();
    
    // if (!getHoodSwitch()) {
    //   hoodFlyMotor.setSelectedSensorPosition(0);
    // }

    HoodLoop();
  }

  private static Flywheel INSTANCE = new Flywheel();

  public static Flywheel getInstance() {
    return INSTANCE;
  }
}