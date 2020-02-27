/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableEntry;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.robot.subsystems.Drivetrain;

public class Peariscope extends SubsystemBase {
  /**
   * Creates a new Limelight.
   */
  private Drivetrain drivetrain;
  private NetworkTable peariscope;

  private double currentHeading;
  private double lastHeading;
  private double errorHeading;
  private double currentXPct;
  private double currentYPct;
  private double[] empty =  new double[] {0};

  public Peariscope(Drivetrain drivetrain) {
    this.drivetrain = drivetrain;
    peariscope = NetworkTableInstance.getDefault().getTable("Peariscope");
    SmartDashboard.putBoolean("TurnLeft", false);
    SmartDashboard.putBoolean("TurnRight", false);
  }

  public double[] getXListPercent() {
    double[] x_list_pct = peariscope.getEntry("x_list_pct").getDoubleArray(empty);
    return x_list_pct;
  }

  public void runBangBangPeariscope() {
    double[] x_list_pct = getXListPercent();
    if (x_list_pct.length == 1) {
      double x = x_list_pct[0]; 
      if (x > 15 & x < 80) {
        //We need to turn left
        SmartDashboard.putBoolean("TurnRight", true);
        drivetrain.arcadeDrive(0, -0.25, false);
      }
      else if (x < -15 & x > -80) {
        //We need to turn right
        SmartDashboard.putBoolean("TurnLeft", true);
        drivetrain.arcadeDrive(0, 0.25, false);
      }
      else {
        //Do nothing
        

        SmartDashboard.putBoolean("TurnLeft", false);
        SmartDashboard.putBoolean("TurnRight", false);
      }
    }
  }

  public void setPeariscopeOn() {
    if (peariscope.getEntry("led_grn").getDouble(0) < 255) {
      peariscope.getEntry("led_grn").setDouble(255);
    }
  }

  public void setPeariscopeOff() {
    if (peariscope.getEntry("led_grn").getDouble(0) > 0) {
      peariscope.getEntry("led_grn").setDouble(0);
      peariscope.getEntry("led_blu").setDouble(0);
      peariscope.getEntry("led_red").setDouble(0);
    }
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }
}
