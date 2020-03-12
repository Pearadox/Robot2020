package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.util.Units;
import edu.wpi.first.wpilibj2.command.SubsystemBase;

import static frc.robot.Constants.LimelightConstants.*;

public class Limelight extends SubsystemBase {

  private NetworkTable table = NetworkTableInstance.getDefault().getTable("limelight");

  private Limelight() {}

  private static Limelight INSTANCE = new Limelight();

  public static Limelight getInstance() {
    return INSTANCE;
  }

  public boolean hasTarget() {
    return table.getEntry("tv").getDouble(0) != 0;
  }

  public void lightOn() {
    table.getEntry("ledMode").setNumber(3);
  }

  public void lightOff() {
    table.getEntry("ledMode").setNumber(1);
  }

  public double getYawToTarget() {
    if (!hasTarget()) { return -1; }
    return Units.degreesToRadians(table.getEntry("tx").getDouble(-180.0/Math.PI)); // - 180 / Pi deg = -1 rad
  }

  public double getDistanceToTarget() {
    if (!hasTarget()) { return -1; }
    double vertAngle = table.getEntry("ty").getDouble(Double.NaN);
    if (Double.isNaN(vertAngle)) { return -1; }
    double totalAngle = LIMELIGHT_ANGLE + Units.degreesToRadians(vertAngle);
    return HEIGHT_DIFFERENCE / Math.tan(totalAngle);
  }
}
