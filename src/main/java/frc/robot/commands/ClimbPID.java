// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot.commands;

import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj2.command.CommandBase;
import frc.robot.Constants.DriveConstants;
import frc.robot.subsystems.Drive;

public class ClimbPID extends CommandBase {
  /** Creates a new ClimbPID. */
  Drive m_drive;

  double leftGoal;
  double rightGoal;
  double inch_goal;

  double onTargetCount = 0;

  double stop_threshold_inches;
  double stop_threshold_revs;
  double count_threshold;

  public ClimbPID(Drive drive, double inches) {
    // Use addRequirements() here to declare subsystem dependencies.
    m_drive = drive;
    addRequirements(m_drive);
    inch_goal = inches;
    stop_threshold_inches = 2;
    stop_threshold_revs = m_drive.inchesToRevs(stop_threshold_inches);
    count_threshold = 5;
  }

  // Called when the command is initially scheduled.
  @Override
  public void initialize() {
    m_drive.zeroEncoders();
    m_drive.m_leftMotorPID.setP(DriveConstants.CLIMB_kP);
    m_drive.m_leftMotorPID.setI(DriveConstants.CLIMB_kI);
    m_drive.m_leftMotorPID.setD(DriveConstants.CLIMB_kD);
    m_drive.m_leftMotorPID.setOutputRange(DriveConstants.CLIMB_MIN_OUTPUT, DriveConstants.CLIMB_MAX_OUTPUT);

    m_drive.m_rightMotorPID.setP(DriveConstants.CLIMB_kP);
    m_drive.m_rightMotorPID.setI(DriveConstants.CLIMB_kI);
    m_drive.m_rightMotorPID.setD(DriveConstants.CLIMB_kD);
    m_drive.m_rightMotorPID.setOutputRange(DriveConstants.CLIMB_MIN_OUTPUT, DriveConstants.CLIMB_MAX_OUTPUT);
    leftGoal = m_drive.inchesToRevs(inch_goal);
    rightGoal = m_drive.inchesToRevs(inch_goal);
    // m_drive.motorDistanceTwo(leftGoal, rightGoal);
    System.out.println("CLIMB PID INIT");

  }

  // Called every time the scheduler runs while the command is scheduled.
  @Override
  public void execute() {
    m_drive.motorDistanceTwo(leftGoal, rightGoal);
    SmartDashboard.putNumber("Goal to travel to", leftGoal);
  }

  // Called once the command ends or is interrupted.
  @Override
  public void end(boolean interrupted) {
    m_drive.stop();
  }

  // Returns true when the command should end.
  @Override
  public boolean isFinished() {

    // if ((m_drive.getLeftRevs() > leftGoal + stop_threshold_revs)
    // && (m_drive.getLeftRevs() < leftGoal - stop_threshold_revs) &&
    // (m_drive.getRightRevs() > rightGoal - stop_threshold_revs)
    // && (m_drive.getRightRevs() < rightGoal + stop_threshold_revs)) {
    // onTargetCount++;
    // // return true;
    // } else {
    // onTargetCount = 0;
    // return false;
    // }
    // return onTargetCount > count_threshold;

    if (m_drive.getLeftRevs() >= 9 && m_drive.getRightRevs() >= 9) {
      System.out.println("CLIMBPID END");

      return true;
    } else {
      return false;
    }
  }
}
