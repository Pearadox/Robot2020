package frc.robot.commands.shooter;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.SequentialCommandGroup;
import frc.robot.ShooterMode;
import frc.robot.commands.hood.SetHood;
import frc.robot.subsystems.Hood;

public class ChangeMode extends SequentialCommandGroup {
  public ChangeMode(ShooterMode mode) {
    var settings = ShooterMode.SETTINGS.getOrDefault(mode, null);
    if (ShooterMode.currentMode == mode) {
      addCommands(new InstantCommand());
      return;
    }
    ShooterMode.currentMode = mode;
    addCommands(
        new SetHood(Hood.getInstance(), settings.hoodAngleDeg),
        new InstantCommand(() -> SmartDashboard.putNumber("Target Voltage", settings.voltage))
    );
  }
}
