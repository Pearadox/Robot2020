package frc.lib.motors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.SpeedController;

public class Bag extends MotorConfiguration{
  Bag() {
    this(false, false, null, null);
  }

  Bag(boolean coast, boolean inverted, SpeedController master, FeedbackSensor feedbackDevice) {
    brushed = false;
    stallLimit = 30;
    freeLimit = 45;
    stallThreshold = 30;
    peakCurrentTime = 1000;
    this.coast = coast;
    this.inverted = inverted;
    this.master = master;
    this.feedbackDevice = feedbackDevice;
  }

  @Override
  public MotorConfiguration setIdleMode(boolean coast) {
    return new Bag(coast, inverted, master, feedbackDevice);
  }

  @Override
  public MotorConfiguration setInverted(boolean inverted) {
    return new Bag(coast, inverted, master, feedbackDevice);
  }

  @Override
  public MotorConfiguration withMaster(SpeedController master) {
    return new Bag(coast, inverted, master, feedbackDevice);
  }

  @Override
  public MotorConfiguration withFeedbackDevice(FeedbackSensor feedbackDevice) {
    if (feedbackDevice.device == FeedbackDevice.IntegratedSensor) {
      throw new IllegalArgumentException("Bag motor has no integrated encoder");
    }
    return new Bag(coast, inverted, master, feedbackDevice);
  }
}
