package org.usfirst.frc.team4999.robot;

import edu.wpi.first.wpilibj.Sendable;
import edu.wpi.first.wpilibj.smartdashboard.SendableBuilder;

public class PIDcontroller implements Sendable{
	private double Kp, Ki, Kd;
	private double iZone;
	
	private double setpoint;
	private double ierror = 0;
	private double lasterror; 
	private long lasttime;
	
	private boolean enabled = true;
	
	public PIDcontroller(double Kp, double Ki, double Kd, double iZone, double setpoint) {
		this.Kp = Kp;
		this.Ki = Ki;
		this.Kd = Kd;
		this.iZone = iZone;
		this.setpoint = setpoint;
		lasttime = System.currentTimeMillis();
	}
	
	public void setSetpoint(double setpoint) {
		this.setpoint = setpoint;
	}
	
	public double calculate(double input) {
		double error = setpoint - input;
		
		double outp = error * Kp;
		
		if(Math.abs(error)< iZone) {
			ierror += error;
		} else {
			ierror = 0;
		}
		
		double outi = ierror * Ki;
		
		double errordif = error - lasterror; 
		lasterror = error; 
		errordif /= System.currentTimeMillis() - lasttime;
		lasttime = System.currentTimeMillis();
		
		double outd = errordif * Kd;
		return outp + outi + outd;
	}

	public double getKp() {
		return Kp;
	}
	
	public void setKp(double newKp) {
		Kp = newKp;
	}
	
	public double getKi() {
		return Ki;
	}
	
	public void setKi(double newKi) {
		Ki = newKi;
	}
	
	public double getKd() {
		return Kd;
	}
	
	public void setKd(double newKd) {
		Kd = newKd;
	}
	
	public double getiZone() {
		return iZone;
	}
	
	public void setiZone(double newiZone) {
		iZone = newiZone;
	}
	
	public double getsetpoint() {
		return setpoint;
	}
	
	public void setsetpoint(double newsetpoint) {
		setpoint = newsetpoint;
	}
	
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setName(String name) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public String getSubsystem() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void setSubsystem(String subsystem) {
		
	}

	@Override
	public void initSendable(SendableBuilder builder) {
		builder.setSmartDashboardType("PIDController");
		builder.addDoubleProperty("p", this::getKp, this::setKp);
		builder.addDoubleProperty("i", this::getKi, this::setKi);
		builder.addDoubleProperty("d", this::getKd, this::setKd);
		builder.addDoubleProperty("f", this::getiZone, this::setiZone);
		builder.addDoubleProperty("setpoint", this::getsetpoint, this::setsetpoint);
		builder.addBooleanProperty("enabled", null, null);
	}
}