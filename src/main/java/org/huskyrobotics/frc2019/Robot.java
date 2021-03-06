/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.huskyrobotics.frc2019;

import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.command.Command;
import edu.wpi.first.wpilibj.command.Scheduler;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import org.huskyrobotics.frc2019.subsystems.*;
import org.huskyrobotics.frc2019.subsystems.cargo.*;
import org.huskyrobotics.frc2019.subsystems.hatch.*;
import org.huskyrobotics.frc2019.subsystems.climber.*;
import org.huskyrobotics.frc2019.subsystems.superstructure.*;
import org.huskyrobotics.frc2019.subsystems.drive.*;
import org.huskyrobotics.frc2019.subsystems.drive.FalconLibStuff.FalconDrive;
//import org.huskyrobotics.frc2019.subsystems.hatch.*;
import org.huskyrobotics.frc2019.commands.UseDrive;
//import org.huskyrobotics.frc2019.subsystems.cargo.*;
import org.huskyrobotics.frc2019.inputs.*;
import org.huskyrobotics.frc2019.inputs.Encoder.EncoderMode;
import org.huskyrobotics.frc2019.subsystems.superstructure.*;
/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the TimedRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.gradle file in the
 * project.
 */
public class Robot extends TimedRobot {
  public static OI m_oi;
  public static PivotArm m_Arm;
  public static CargoIO m_Sputnik;
  public static HatchIO m_Kennedy;
  public static IsaiahFlipper m_Armstrong;
  //private VisionController Limelight;
  public static FalconDrive m_Drive;
  public UseDrive m_DriveTrain;
  
  Command m_autonomousCommand;
  SendableChooser<Command> m_chooser = new SendableChooser<>();

  /**
   * This function is run when the robot is first started up and should be
   * used for any initialization code.
   */
  @Override
  public void robotInit() {
    m_oi = new OI(0, 1);
    m_Arm = new PivotArm(RobotMap.kPivotMaster, EncoderMode.QuadEncoder);
    m_Sputnik = new CargoIO(RobotMap.kCargo);
    m_Kennedy = new HatchIO(RobotMap.kHatchMotor);
    // chooser.addOption("My Auto", new MyAutoCommand());
    SmartDashboard.putData("Auto mode", m_chooser);
  }

  /**
   * This function is called every robot packet, no matter the mode. Use
   * this for items like diagnostics that you want ran during disabled,
   * autonomous, teleoperated and test.
   *
   * <p>This runs after the mode specific periodic functions, but before
   * LiveWindow and SmartDashboard integrated updating.
   */
  @Override
  public void robotPeriodic() {
  }

  /**
   * This function is called once each time the robot enters Disabled mode.
   * You can use it to reset any subsystem information you want to clear when
   * the robot is disabled.
   */
  @Override
  public void disabledInit() {
  }

  @Override
  public void disabledPeriodic() {
    Scheduler.getInstance().run();
  }

  /**
   * This autonomous (along with the chooser code above) shows how to select
   * between different autonomous modes using the dashboard. The sendable
   * chooser code works with the Java SmartDashboard. If you prefer the
   * LabVIEW Dashboard, remove all of the chooser code and uncomment the
   * getString code to get the auto name from the text box below the Gyro
   *
   * <p>You can add additional auto modes by adding additional commands to the
   * chooser code above (like the commented example) or additional comparisons
   * to the switch structure below with additional strings & commands.
   */
  @Override
  public void autonomousInit() {
    m_autonomousCommand = m_chooser.getSelected();

    /*
     * String autoSelected = SmartDashboard.getString("Auto Selector",
     * "Default"); switch(autoSelected) { case "My Auto": autonomousCommand
     * = new MyAutoCommand(); break; case "Default Auto": default:
     * autonomousCommand = new ExampleCommand(); break; }
     */

    // schedule the autonomous command (example)
    if (m_autonomousCommand != null) {
      m_autonomousCommand.start();
    }
  }

  /**
   * This function is called periodically during autonomous.
   */
  @Override
  public void autonomousPeriodic() {
    Scheduler.getInstance().run();
    
    m_DriveTrain.start();
  }

  @Override
  public void teleopInit() {
    // This makes sure that the autonomous stops running when
    // teleop starts running. If you want the autonomous to
    // continue until interrupted by another command, remove
    // this line or comment it out.
    if (m_autonomousCommand != null) {
      m_autonomousCommand.cancel();
    }
  }

  /**
   * This function is called periodically during operator control.
   */
  @Override
  public void teleopPeriodic() {
    Scheduler.getInstance().run();
    m_oi.periodic();

    m_Arm.periodic();

    m_Sputnik.setCargoAxis(m_oi.getCargoAxis());
    
    m_Kennedy.periodic();
    if(m_oi.getHatchToggle()) {
      m_Kennedy.setHatchToggle();
    }

    m_Armstrong.periodic();

    m_DriveTrain.start();
    // m_Drive.curvatureDrive(m_oi.getRobotForward(), m_oi.getRobotTwist(), false);
  }

  /**
   * This function is called periodically during test mode.
   */
  @Override
  public void testPeriodic() {
    Scheduler.getInstance().run();
    m_oi.periodic();

    m_Arm.periodic();

    m_Sputnik.setCargoAxis(m_oi.getCargoAxis());
    
    m_Kennedy.periodic();
    if(m_oi.getHatchToggle()) {
      m_Kennedy.setHatchToggle();
    }

    m_Armstrong.periodic();

    m_DriveTrain.start();
  }
}
