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
    private final Flywheel flywheel;

    /**
     * Creates a new ExampleCommand.
     *
     * @param Flywheel The subsystem used by this command.
     */
    public double degrees = 0;
    public double degreeError = 0;
    public double lastDegree = 0;
    private double maxAngle = 60;
    public HoodForward(Flywheel flywheel)
    {
        this.flywheel = flywheel;
        addRequirements(flywheel);
        if (!SmartDashboard.containsKey("HoodDegree")) {
            SmartDashboard.putNumber("HoodDegree", SmartDashboard.getNumber("HoodDegree", 0));
        }
    }

    @Override
    public void initialize() {
    }
   
    @Override
    public void execute() {
        degrees = flywheel.getHoodAngle();
        SmartDashboard.putNumber("HoodDegree", degrees);
        if (degrees >= maxAngle -1) {
            flywheel.setHoodFlyMotor(-0.5);
        }
        else {
            flywheel.setHoodFlyMotor(-1);
        }
    }

    @Override
    public boolean isFinished() {
        return degrees >= maxAngle;
    }
}