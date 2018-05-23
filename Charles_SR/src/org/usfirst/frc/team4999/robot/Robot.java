/*----------------------------------------------------------------------------*/
/* Copyright (c) 2017-2018 FIRST. All Rights Reserved.                        */
/* Open Source Software - may be modified and shared by FRC teams. The code   */
/* must be accompanied by the FIRST BSD license file in the root directory of */
/* the project.                                                               */
/*----------------------------------------------------------------------------*/

package org.usfirst.frc.team4999.robot;

import edu.wpi.first.wpilibj.GenericHID.Hand;
import edu.wpi.first.wpilibj.IterativeRobot;
import edu.wpi.first.wpilibj.SpeedControllerGroup;
import edu.wpi.first.wpilibj.VictorSP;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.drive.DifferentialDrive;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

/**
 * The VM is configured to automatically run this class, and to call the
 * functions corresponding to each mode, as described in the IterativeRobot
 * documentation. If you change the name of this class or the package after
 * creating this project, you must also update the build.properties file in the
 * project.
 */
public class Robot extends IterativeRobot {
	private VictorSP leftFront, leftBack, rightFront, rightBack;
	private XboxController xbox; 
	private LogitechF310 gamepad; 
	private DifferentialDrive drive;
	
	private double throttle=1;
	
	private final double THROTTLE_DIFF = .1;
	
	/**
	 * This function is run when the robot is first started up and should be
	 * used for any initialization code.
	 */
	@Override
	public void robotInit() {
		leftFront = new VictorSP(1);
		leftBack = new VictorSP(2);
		rightFront = new VictorSP(3);
		rightBack = new VictorSP(4);
		xbox = new XboxController(1);
		gamepad = new LogitechF310(2);
		drive = new DifferentialDrive(new SpeedControllerGroup(leftFront, leftBack), new SpeedControllerGroup(rightFront, rightBack));
	}

	/**
	 * This autonomous (along with the chooser code above) shows how to select
	 * between different autonomous modes using the dashboard. The sendable
	 * chooser code works with the Java SmartDashboard. If you prefer the
	 * LabVIEW Dashboard, remove all of the chooser code and uncomment the
	 * getString line to get the auto name from the text box below the Gyro
	 *
	 * <p>You can add additional auto modes by adding additional comparisons to
	 * the switch structure below with additional strings. If using the
	 * SendableChooser make sure to add them to the chooser code above as well.
	 */
	@Override
	public void autonomousInit() {
	}

	/**
	 * This function is called periodically during autonomous.
	 */
	@Override
	public void autonomousPeriodic() {
		

	}

	/**
	 * This function is called periodically during operator control.
	 */
	@Override
	public void teleopPeriodic() {
		SmartDashboard.putNumber( "Throttle", throttle);
		if(xbox.getXButton()) {
			double m_r=gamepad.getY(Hand.kLeft);
			double t_r=gamepad.getX(Hand.kRight);
			m_r = Utils.map(m_r, -1, 1, -throttle , throttle);
			t_r = Utils.map(t_r, -1, 1, -throttle, throttle);
			drive.arcadeDrive(m_r, t_r, true); 
		} else {
			drive.arcadeDrive(xbox.getY(Hand.kLeft), xbox.getX(Hand.kRight), true);
			if(xbox.getAButtonPressed() && throttle >= 0+THROTTLE_DIFF) {
				throttle -= THROTTLE_DIFF;
			}
			if(xbox.getBButtonPressed() && throttle <= 1-THROTTLE_DIFF) {
				throttle += THROTTLE_DIFF;
			}
		}
	}

	/**
	 * This function is called periodically during test mode.
	 */
	@Override
	public void testPeriodic() {
	}
}
