/*----------------------------------------------------------------------------*/
/* Copyright (c) 2019 FIRST. All Rights Reserved.                             */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package frc.robot.subsystems;

import com.revrobotics.CANEncoder;
import com.revrobotics.CANSparkMax;
import com.revrobotics.CANSparkMax.IdleMode;

import edu.wpi.first.wpilibj2.command.SubsystemBase;
import frc.lib.motors.MotorControllerFactory;
import frc.lib.motors.Motors;
import static frc.robot.Constants.ClimberConstants.TRANSVERSE_CLIMB_MOTOR;

public class Transverse extends SubsystemBase {
  /**
   * Creates a new Transverse.
   */
  
  private CANSparkMax transverseMotor;
  private CANEncoder transverseEncoder;
  private static Transverse INSTANCE = new Transverse();
  private Transverse() {
    transverseMotor = MotorControllerFactory.createSparkMax(TRANSVERSE_CLIMB_MOTOR, Motors.Neo550);
    transverseMotor.setIdleMode(IdleMode.kBrake);
    transverseEncoder = new CANEncoder(transverseMotor);
    transverseEncoder.setPositionConversionFactor(42);
    transverseMotor.setOpenLoopRampRate(0.75);
  }

  
  public void setTransverseMotor(double setSpeed) {
    transverseMotor.set(setSpeed);
  }

  public double getTransverseRaw() { return transverseEncoder.getPosition();}

  public void stopTransverseMotor() { setTransverseMotor(0);}


  @Override
  public void periodic() {
    // This method will be called once per scheduler run
  }

  public static Transverse getInstance() {return INSTANCE;}
}
