package frc.robot.subsystems;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.ctre.phoenix.motorcontrol.can.WPI_TalonSRX;
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.motors.MotorConfiguration;
import frc.lib.motors.MotorControllerFactory;
import frc.lib.motors.Motors;

import static frc.robot.Constants.HoodConstants.*;


public class Hood extends SubsystemBase {
  public final WPI_TalonSRX motor = MotorControllerFactory.createTalonSRX(
      HOOD_MOTOR_ID, Motors.Snowblower.withFeedbackDevice(new MotorConfiguration.FeedbackSensor(
          FeedbackDevice.QuadEncoder, 1)));

  public final DigitalInput hoodSwitch = new DigitalInput(HOOD_SWITCH_PORT);

  private double getRawHoodAngle() {
    return motor.getSelectedSensorPosition() / 8618.5;
  }

  // total teeth/pinion teeth
  public double getHoodAngle() {
    return getRawHoodAngle() * 404/20;
  }

  private Hood() {

  }

  private static Hood INSTANCE = new Hood();

  public static Hood getInstance() {
    return INSTANCE;
  }
}
