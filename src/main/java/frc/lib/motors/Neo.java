package frc.lib.motors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import edu.wpi.first.wpilibj.SpeedController;

public class Neo extends MotorConfiguration {

  Neo(boolean coast, boolean inverted, SpeedController master) {
    brushed = false;
    stallLimit = 60;
    freeLimit = 80;
    stallThreshold = 30;
    peakCurrentTime = 1000;
    this.coast = coast;
    this.inverted = inverted;
    this.master = master;
  }

  @Override
  public MotorConfiguration setIdleMode(boolean coast) {
    return new Neo(coast, inverted, master);
  }

  @Override
  public MotorConfiguration setInverted(boolean inverted) {
    return new Neo(coast, inverted, master);
  }

  @Override
  public MotorConfiguration withMaster(SpeedController master) {
    return new Neo(coast, inverted, master);
  }

  @Override
  public MotorConfiguration withFeedbackDevice(FeedbackDevice device) {
    throw new UnsupportedOperationException("Feedback devices only apply to CTRE controllers");
  }
}