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
public class HoodBack extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "FieldCanBeLocal"})
    private final Flywheel flywheelSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param flywheelSubsystem The subsystem used by this command.
     */
    public double degrees;
    public double lastDegree = 0;
    public double degreeError;
    public HoodBack(Flywheel flywheelSubsystem)
    {
        this.flywheelSubsystem = flywheelSubsystem;
        addRequirements(flywheelSubsystem);
        if (!SmartDashboard.containsKey("hoodDegree")) {
            SmartDashboard.putNumber("hoodDegree", SmartDashboard.getNumber("hoodDegree", 0));
        }
        flywheelSubsystem.hoodEncoder.setPosition(0);
    }

    @Override
    public void initialize() {
    }
   
    @Override
    public void execute() {
        degrees = flywheelSubsystem.getHoodAngle();
        SmartDashboard.putNumber("hoodDegree", degrees);
        degreeError = lastDegree - degrees;
        flywheelSubsystem.setHoodFlyMotor(1);
        lastDegree = degrees;
    }

    @Override
    public boolean isFinished() {
        return degreeError <= 0.5;
    }
}