// Copyright (c) FIRST and other WPILib contributors.
// Open Source Software; you can modify and/or share it under the terms of
// the WPILib BSD license file in the root directory of this project.

package frc.robot;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Map;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.kinematics.ChassisSpeeds;
import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.networktables.NetworkTable;
import edu.wpi.first.networktables.NetworkTableInstance;
import edu.wpi.first.wpilibj.RobotBase;
import edu.wpi.first.wpilibj.Filesystem;
import edu.wpi.first.wpilibj.shuffleboard.BuiltInWidgets;
import edu.wpi.first.wpilibj.shuffleboard.Shuffleboard;
import edu.wpi.first.wpilibj.shuffleboard.ShuffleboardTab;
import edu.wpi.first.wpilibj.shuffleboard.WidgetType;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;
import edu.wpi.first.wpilibj.smartdashboard.SendableChooser;
import edu.wpi.first.wpilibj2.command.Command;
import edu.wpi.first.wpilibj2.command.Commands;
import edu.wpi.first.wpilibj2.command.InstantCommand;
import edu.wpi.first.wpilibj2.command.button.CommandXboxController;
import edu.wpi.first.wpilibj2.command.button.Trigger;

import frc.robot.commands.DefaultDriveCommand;

import frc.robot.subsystems.drive.SwerveDrive;
import frc.robot.util.LimelightHelpers;
import frc.robot.util.PigeonWrapper;
import frc.robot.util.Translation2dSupplier;


public class RobotContainer {
	
	private final PigeonWrapper pigeon = new PigeonWrapper(0);


	private final CommandXboxController operatorController = new CommandXboxController(2);
	
	private final SwerveDrive swerve;


	private SendableChooser<Command> chooser = new SendableChooser<Command>();

    public RobotContainer() {
		this.swerve = new SwerveDrive(pigeon);


		configureBindings();

	}

  	private static double nonInvSquare(double axis) {
		double deadbanded = MathUtil.applyDeadband(axis, Constants.Operator.kStickDeadband);
		double squared = Math.abs(deadbanded) * deadbanded;
		return squared;
	}

    private void configureBindings() {
		
		// swerve.setDefaultCommand(new DefaultDriveCommand(swerve,
		// 		() -> nonInvSquare(-operatorController.getY()),
		// 		() -> nonInvSquare(-operatorController.getLeftX()),
		// 		() -> nonInvSquare(-operatorController.getRightX()),
		// 		() -> operatorController.getLeftTrigger(),
		// 		() -> operatorController.getRightTrigger(),
		// 		() -> true));





  
}
}