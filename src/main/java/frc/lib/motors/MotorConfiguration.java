package frc.lib.motors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.SpeedController;

public abstract class MotorConfiguration {
  public static class FeedbackSensor {
    public final FeedbackDevice device;
    public final int CPR;

    public FeedbackSensor(FeedbackDevice device, int CPR) {
      this.device = device;
      this.CPR = CPR;
    }
  }

  boolean brushed;
  boolean coast;
  boolean inverted;
  FeedbackSensor feedbackDevice;
  int feedbackCPR;
  SpeedController master;
  int stallLimit;
  int freeLimit;
  int stallThreshold;
  int peakCurrentTime;

  public abstract MotorConfiguration setIdleMode(boolean coast);

  public abstract MotorConfiguration setInverted(boolean inverted);

  public abstract MotorConfiguration withMaster(SpeedController master);

  public abstract MotorConfiguration withFeedbackDevice(FeedbackSensor feedbackDevice);
}
