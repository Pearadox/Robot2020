package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.subsystems.Flywheel;

/**
 * An example command that uses an example subsystem.
 */

public class HoodForward extends CommandBase
{
    @SuppressWarnings({"PMD.UnusedPrivateField", "PMD.SingularField", "FieldCanBeLocal"})
    private final Flywheel flywheelSubsystem;

    /**
     * Creates a new ExampleCommand.
     *
     * @param flywheelSubsystem The subsystem used by this command.
     */
    public double degrees = 0;
    public double degreeError = 0;
    public double lastDegree = 0;
    public HoodForward(Flywheel flywheelSubsystem)
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
        degreeError = degrees - lastDegree;
        flywheelSubsystem.setHoodFlyMotor(-1);
        lastDegree = degrees;
    }

    @Override
    public boolean isFinished() {
        return lastDegree <= 0.5;
    }
}