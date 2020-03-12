package frc.robot;

import java.util.HashMap;
import java.util.Map;

public enum ShooterMode {
  TARGET_ZONE, INITIATION_LINE;

  public static ShooterMode currentMode = INITIATION_LINE;

  public static class ShooterSettings {
    public final double voltage;
    public final double hoodAngleDeg;
    public final double distance;

    public ShooterSettings(double volt, double angle, double distance) {
      voltage = volt;
      hoodAngleDeg =  angle;
      this.distance = distance;
    }
  }

  public static final Map<ShooterMode, ShooterSettings> SETTINGS =
      new HashMap<>();

  static {
    SETTINGS.put(TARGET_ZONE, new ShooterSettings(Constants.FlywheelConstants.TARGET_ZONE_VOLTAGE,
        Constants.HoodConstants.TARGET_ZONE_ANGLE_DEG, 0.8));

    SETTINGS.put(INITIATION_LINE, new ShooterSettings(Constants.FlywheelConstants.INITIATION_LINE_VOLTAGE,
        Constants.HoodConstants.INITIATION_LINE_ANGLE_DEG, 2.85));

  }
}