package frc.lib.motors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.SpeedController;

public class Snowblower extends MotorConfiguration {
  Snowblower() { this(false, false, null, null); }

  Snowblower(boolean coast, boolean inverted, SpeedController master, FeedbackSensor feedbackDevice) {
    brushed = true;
    stallLimit = 60;
    freeLimit = 80;
    stallThreshold = 30;
    peakCurrentTime = 1000;
    this.coast = coast;
    this.inverted = inverted;
    this.master = master;
    this.feedbackDevice = feedbackDevice;
  }

  @Override
  public MotorConfiguration setIdleMode(boolean coast) {
    return new Snowblower(coast, inverted, master, feedbackDevice);
  }

  @Override
  public MotorConfiguration setInverted(boolean inverted) {
    return new Snowblower(coast, inverted, master, feedbackDevice);
  }

  @Override
  public MotorConfiguration withMaster(SpeedController master) {
    return new Snowblower(coast, inverted, master, feedbackDevice);
  }

  @Override
  public MotorConfiguration withFeedbackDevice(FeedbackSensor feedbackDevice) {
    if (feedbackDevice.device == FeedbackDevice.IntegratedSensor) {
      throw new IllegalArgumentException("Snowblower motor has no integrated encoder");
    }
    return new Snowblower(coast, inverted, master, feedbackDevice);
  }
}
