package frc.lib.motors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.SpeedController;

public abstract class MotorConfiguration {
  public boolean brushed;
  public boolean coast;
  public boolean inverted;
  public FeedbackDevice feedbackDevice;
  public SpeedController master;
  public int stallLimit;
  public int freeLimit;
  public int stallThreshold;
  public int peakCurrentTime;

  public abstract MotorConfiguration setIdleMode(boolean coast);

  public abstract MotorConfiguration setInverted(boolean inverted);

  public abstract MotorConfiguration withMaster(SpeedController master);

  public abstract MotorConfiguration withFeedbackDevice(FeedbackDevice device);
}
