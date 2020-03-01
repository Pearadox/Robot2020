package frc.lib.motors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.SpeedController;

public class Neo extends MotorConfiguration {

  Neo() { this(false, false, null, new FeedbackSensor(FeedbackDevice.IntegratedSensor, 42)); }

  Neo(boolean coast, boolean inverted, SpeedController master, FeedbackSensor feedbackDevice) {
    brushed = false;
    stallLimit = 60;
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
    return new Neo(coast, inverted, master, feedbackDevice);
  }

  @Override
  public MotorConfiguration setInverted(boolean inverted) {
    return new Neo(coast, inverted, master, feedbackDevice);
  }

  @Override
  public MotorConfiguration withMaster(SpeedController master) {
    return new Neo(coast, inverted, master, feedbackDevice);
  }

  @Override
  public MotorConfiguration withFeedbackDevice(FeedbackSensor device) {
    return new Neo(coast, inverted, master, device);
  }
}