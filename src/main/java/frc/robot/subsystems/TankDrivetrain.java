// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.subsystems;

//Command Imports
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.SubsystemBase;
import edu.wpi.first.wpilibj.AnalogGyro;
//WPIlib Imports
import edu.wpi.first.wpilibj.DigitalInput;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

//Rev Imports
import com.revrobotics.spark.SparkMax;

import com.revrobotics.spark.SparkBase.ControlType;
import com.revrobotics.spark.SparkBase.PersistMode;
import com.revrobotics.spark.SparkBase.ResetMode;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Supplier;

import org.ejml.dense.row.linsol.qr.LinearSolverQrHouseCol_MT_FDRM;

import com.revrobotics.RelativeEncoder;

import com.revrobotics.spark.SparkClosedLoopController;
import com.revrobotics.spark.SparkLowLevel.MotorType;
import com.revrobotics.spark.config.MAXMotionConfig.MAXMotionPositionMode;
import com.revrobotics.spark.config.SparkBaseConfig.IdleMode;
import com.revrobotics.spark.config.SparkMaxConfig;

//Math Imports
import edu.wpi.first.math.util.Units;
import edu.wpi.first.util.sendable.SendableRegistry;
import edu.wpi.first.wpilibj.Joystick;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.motorcontrol.PWMSparkMax;

//Java Imports

public class TankDrivetrain extends SubsystemBase {
  HashMap<String, SparkMax> driveMotors = new HashMap<String, SparkMax>();
  private final SparkMax leftFront = new SparkMax(1, MotorType.kBrushless);
  private final SparkMax rightFront = new SparkMax(2, MotorType.kBrushless);
  private final SparkMax leftBack = new SparkMax(3, MotorType.kBrushless);
  private final SparkMax rightBack = new SparkMax(4, MotorType.kBrushless);
  
  private double kP = 0.05;
  Pigeon1

  



  //private final DifferentialDrive m_robotDrive;

  /** Creates a new ExampleSubsystem. */
  public TankDrivetrain() {
    driveMotors.put("lf", leftFront);
    driveMotors.put("rf", rightFront);
    driveMotors.put("lb", leftBack);
    driveMotors.put("rb", rightBack);

    zeroRelativeEncoder("lf");
    zeroRelativeEncoder("rf");
    zeroRelativeEncoder("lb");
    zeroRelativeEncoder("rb");
    configureLeft();
    configureRight();

    //m_robotDrive = new DifferentialDrive(leftFront::set,rightFront::set);
  }
  /**
   * Example command factory method.
   *
   * @return a command
   */
  public Command exampleMethodCommand() {
    // Inline construction of command goes here.
    // Subsystem::RunOnce implicitly requires `this` subsystem.
    return runOnce(
        () -> {
          /* one-time action goes here */
        });
  }

  public RelativeEncoder getRelativeEncoder(String sparkMax) {
    return driveMotors.get(sparkMax).getEncoder();
  }
  public void zeroRelativeEncoder(String sparkMax) {
    driveMotors.get(sparkMax).getEncoder().setPosition(0);
  }

  public void setSpeed(String sparkMax, double speed) {
    driveMotors.get(sparkMax).set(speed);
  }


  public Command setAllCommand(Supplier<Double> speedLeftSupplier, Supplier<Double> speedRightSupplier) {
    return run(() -> setAll(speedLeftSupplier, speedRightSupplier));
  }
  public void setAll(Supplier<Double> speedLeftSupplier, Supplier<Double> speedRightSupplier) {
    double speedLeft = speedLeftSupplier.get();
    double speedRight = speedRightSupplier.get();

    speedLeft = Math.abs(speedLeft) < 0.05 ? 0 : speedLeft;
    speedRight = Math.abs(speedRight) < 0.05 ? 0 : speedRight;

    driveMotors.get("lf").set(speedLeft);
    driveMotors.get("rf").set(speedRight);
    driveMotors.get("lb").set(speedLeft);
    driveMotors.get("rb").set(speedRight); 
  }
  private void configureLeft() {
    SparkMaxConfig config = new SparkMaxConfig();

    config
      .inverted(false)
      .idleMode(IdleMode.kBrake);
      

    driveMotors.get("lf").configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    driveMotors.get("lb").configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
 
  }

  private void configureRight() {
    SparkMaxConfig config = new SparkMaxConfig();

    config
      .inverted(true)
      .idleMode(IdleMode.kBrake);

    driveMotors.get("rf").configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
    driveMotors.get("rb").configure(config, ResetMode.kResetSafeParameters, PersistMode.kPersistParameters);
  }


  /**
   * An example method querying a boolean state of the subsystem (for example, a digital sensor).
   *
   * @return value of some boolean subsystem state, such as a digital sensor.
   */
  public boolean exampleCondition() {
    // Query some boolean state, such as a digital sensor.
    return false;
  }

  @Override
  public void periodic() {
    // This method will be called once per scheduler run
    
  }

  @Override
  public void simulationPeriodic() {
    // This method will be called once per scheduler run during simulation
  }
}
