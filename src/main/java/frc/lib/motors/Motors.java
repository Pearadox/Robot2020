package frc.lib.motors;

import com.ctre.phoenix.motorcontrol.FeedbackDevice;
import com.revrobotics.CANSparkMax;
import edu.wpi.first.wpilibj.SpeedController;

public class Motors {

  public static final MotorConfiguration Neo = new Neo(false, false, null);
  public static final MotorConfiguration Neo550 = new Neo550(false, false, null);

}
