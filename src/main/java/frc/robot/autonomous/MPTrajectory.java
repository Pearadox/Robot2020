/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.autonomous;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import edu.wpi.first.wpilibj.DriverStation;

/**
 * Add your docs here.
 */
public class MPTrajectory {
  public List<MPPoint> leftTrajectory;
  public List<MPPoint> rightTrajectory;

  static boolean empty = false;

  public MPTrajectory(String fileName) throws IOException {
    this(new File("/home/lvuser/deploy/paths/" + fileName + "_left.csv"),
        new File("/home/lvuser/deploy/paths/" + fileName + "_right.csv"));
  }

  public MPTrajectory(File leftFile, File rightFile) throws IOException {
    leftTrajectory = new ArrayList<>();
    rightTrajectory = new ArrayList<>();

    BufferedReader leftReader = new BufferedReader(new FileReader(leftFile));
    BufferedReader rightReader = new BufferedReader(new FileReader(rightFile));

    String raw = leftReader.readLine();
    String[] line;
    if (raw == null) {
      empty = true;
      DriverStation.reportError("File Empty", true);
    }
    while (raw != null) {
      line = raw.split(",");
      double pos = Double.parseDouble(line[0]);
      double vel = Double.parseDouble(line[1]);
      double acc = Double.parseDouble(line[2]);
      double hea = Double.parseDouble(line[3]);
      leftTrajectory.add(new MPPoint(pos, vel, acc, hea));
      raw = leftReader.readLine();
    }
    leftReader.close();

    raw = rightReader.readLine();

    while (raw != null) {
      line = raw.split(",");
      double pos = Double.parseDouble(line[0]);
      double vel = Double.parseDouble(line[1]);
      double acc = Double.parseDouble(line[2]);
      double hea = Double.parseDouble(line[3]);
      rightTrajectory.add(new MPPoint(pos, vel, acc, hea));
      raw = rightReader.readLine();
    }
    rightReader.close();
  }

  public static boolean isRawEmpty() {
    return empty;
  }
}
