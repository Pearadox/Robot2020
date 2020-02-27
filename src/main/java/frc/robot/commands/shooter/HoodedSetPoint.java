/*----------------------------------------------------------------------------*/
/* Copyright (c) 2018-2019 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;

/**
 * An example command that uses an example subsystem.
 */

public class HoodedSetPoint extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "FieldCanBeLocal"})
    private final Flywheel flywheel;

    /**
     * Creates a new ExampleCommand.
     *
     * @param flywheel The subsystem used by this command.
     */
    public double degrees = 0;
    private double targetDegree;
    private final double DEADBAND = 0.05;
    private final double minAngle = 0.0;
    private final double maxAngle = 60.0;
    public HoodedSetPoint(Flywheel flywheel, double targetDegree)
    {
      this.flywheel = flywheel;
      addRequirements(flywheel);
      if (!SmartDashboard.containsKey("HoodDegree")) {
        SmartDashboard.putNumber("HoodDegree", SmartDashboard.getNumber("HoodDegree", minAngle));
      }
      this.targetDegree = targetDegree;
    }

    @Override
    public void initialize() {
      targetDegree = !(targetDegree < minAngle) ? targetDegree : minAngle;
    }
   
    @Override
    public void execute() {
      degrees = flywheel.getHoodAngle();

      if (degrees <= minAngle) {
        SmartDashboard.putNumber("HoodDegree", minAngle);
        flywheel.resetHoodEncoder();
      }
      else if (degrees >= maxAngle) {
        SmartDashboard.putNumber("HoodDegree", maxAngle);
      }
      else {
        SmartDashboard.putNumber("HoodDegree", degrees);
      }

      if (degrees < targetDegree - DEADBAND || degrees > targetDegree+ DEADBAND) {
        if (degrees <= targetDegree) {
          if (degrees <= (targetDegree - 2.5)) {
            flywheel.setHoodFlyMotor(1);
          }
          else {
            flywheel.setHoodFlyMotor(0.25);
          }
        }
          
        else if (degrees >= targetDegree) {
          if (degrees >= (targetDegree + 2.5)) {
            flywheel.setHoodFlyMotor(-1);
          }
          else {
            flywheel.setHoodFlyMotor(-0.25);
          }
        }
      }
    }

    @Override
    public void end(boolean interrupted) {
      flywheel.setHoodFlyMotor(0);
    }
    
    @Override
    public boolean isFinished() {
      if (degrees >= targetDegree - DEADBAND && degrees <= targetDegree + DEADBAND) {
        return true;
      }
      else {  
        return false;
      }
    }


}
