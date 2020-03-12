package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.VictorSPX;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;

import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.DriverStation;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.FlywheelConstants.*;

import frc.lib.motors.MotorConfiguration;
import frc.lib.motors.MotorControllerFactory;
import frc.lib.motors.Motors;

public class Flywheel extends SubsystemBase {
  private final CANSparkMax leftMotor = MotorControllerFactory.createSparkMax(LEFT_FLY_MOTOR, Motors.Neo.setIdleMode(true));
  private final CANSparkMax rightMotor = MotorControllerFactory.createSparkMax(RIGHT_FLY_MOTOR, Motors.Neo.setIdleMode(true).setInverted(true));

  private final CANEncoder leftEncoder = leftMotor.getEncoder();
  private final CANEncoder rightEncoder = rightMotor.getEncoder();


  public boolean enabled;

  private double flywheelSetpoint;
  private double kFF;
  private double kP;
  private double kD;
  private double lastError;
  private double hoodSetpoint;
  private double voltageSetpoint = 4.7;

  private static Flywheel INSTANCE = new Flywheel();

  public static Flywheel getInstance() {
    return INSTANCE;
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
      // rightMotor.setVoltage(0.4);
      // leftMotor.setVoltage(0.4);
      return;
    }

    double output = SmartDashboard.getNumber("Target Voltage", voltageSetpoint);
    SmartDashboard.putNumber("HoodCurrent", 0);

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
    if (!SmartDashboard.containsKey("Flywheel Enabled")) { SmartDashboard.putBoolean("Flywheel Enabled", false); }
    SmartDashboard.putNumber("Flywheel RPM", getRPM());

    if (DriverStation.getInstance().isFMSAttached()) {
      enabled = true;
    } else {
      enabled = SmartDashboard.getBoolean("Flywheel Enabled", false);
    }

    FlywheelPIDLoop();
  }
}