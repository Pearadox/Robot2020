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
public class HoodBackward extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "FieldCanBeLocal"})
    private final Flywheel flywheel;

    /**
     * Creates a new ExampleCommand.
     *
     * @param flywheelSubsystem The subsystem used by this command.
     */
    public double degrees;
    public double lastDegree = 0;
    public double degreeError;
    private double minAngle = 0;
    public HoodBackward(Flywheel flywheel)
    {
        this.flywheel = flywheel;
        addRequirements(flywheel);
    }

    @Override
    public void initialize() {
    }
   
    @Override
    public void execute() {
        degrees = flywheel.getHoodAngle();
        SmartDashboard.putNumber("HoodDegree", degrees);
        if (degrees <= 1) {
            flywheel.setHoodFlyMotor(0.5);
        }
        else {
            flywheel.setHoodFlyMotor(1);
        }
    }

    @Override
    public boolean isFinished() {
        return degrees <= minAngle;
    }
}