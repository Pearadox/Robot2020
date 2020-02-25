package frc.lib.motors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.SpeedController;

public class MiniCIM extends MotorConfiguration {
  MiniCIM() { this(false, false, null, new FeedbackSensor(FeedbackDevice.IntegratedSensor, 42)); }

  MiniCIM(boolean coast, boolean inverted, SpeedController master, FeedbackSensor feedbackDevice) {
    brushed = true;
    stallLimit = 89;
    freeLimit = 3;
    stallThreshold = 89;
    peakCurrentTime = 1000;
    this.coast = coast;
    this.inverted = inverted;
    this.master = master;
    this.feedbackDevice = feedbackDevice;
  }

  @Override
  public MotorConfiguration setIdleMode(boolean coast) {
    return new MiniCIM(coast, inverted, master, feedbackDevice);
  }

  @Override
  public MotorConfiguration setInverted(boolean inverted) {
    return new MiniCIM(coast, inverted, master, feedbackDevice);
  }

  @Override
  public MotorConfiguration withMaster(SpeedController master) {
    return new MiniCIM(coast, inverted, master, feedbackDevice);
  }

  @Override
  public MotorConfiguration withFeedbackDevice(FeedbackSensor feedbackDevice) {
    return new MiniCIM(coast, inverted, master, feedbackDevice);
  }
}