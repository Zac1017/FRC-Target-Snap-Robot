package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.MathUtil;
import edu.wpi.first.math.geometry.Pose3d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.Constants;
import frc.robot.subsystems.drive.SwerveDrive;
import frc.robot.util.LimelightHelpers;

public class AlignToCommand extends Command {
    
    BooleanSupplier left;
    SwerveDrive swerve;

    public AlignToCommand(SwerveDrive swerve, BooleanSupplier left) {
        this.swerve = swerve;
        this.left = left;
        addRequirements(swerve);
    }

    public double getDistanceToAprilTag() {
        Pose3d tag = LimelightHelpers.getTargetPose3d_RobotSpace("limelight");
        return Math.hypot(tag.getX(), tag.getY());
    }

    public double getAngleToAprilTag() {
        Pose3d tag = LimelightHelpers.getTargetPose3d_RobotSpace("limelight");
        return Math.toDegrees(Math.atan2(tag.getY(), tag.getX()));
    }

    // public double getLateralDistanceFromPipe() {
    //     double distance = getDistanceToAprilTag();
    //     return Math.sqrt(Math.pow(distance, 2) - Math.pow(Constants.kPipeFromAprilTagCenter, 2));
    // }

    public boolean hasTarget() {
        return LimelightHelpers.getTV("limelight");
    }

    @Override
    public void execute() {
        if (!hasTarget()) {
            return;
        }

        Pose3d tag = LimelightHelpers.getTargetPose3d_RobotSpace("limelight");

        double x = tag.getX();
        double y = tag.getY();

        double xError = x - Constants.kDesiredDistanceFromAprilTag;
        
        double targetLateralOffset = left.getAsBoolean() ? -Constants.kPipeFromAprilTagCenter : Constants.kPipeFromAprilTagCenter;
        
        double yError = y - targetLateralOffset;

        double xSpeed = MathUtil.clamp(xError * Constants.PIDConstants.Drive.kDriveVelocityP, -Constants.Chassis.kMaxSpeed, Constants.Chassis.kMaxSpeed );
        double ySpeed = MathUtil.clamp(yError * Constants.PIDConstants.Drive.kDriveVelocityP, -Constants.Chassis.kMaxSpeed, Constants.Chassis.kMaxSpeed);

        Translation2d translation = new Translation2d(xSpeed, ySpeed);

        double angleError = Math.toRadians(getAngleToAprilTag()) * Constants.PIDConstants.Drive.kDriveVelocityP;

        swerve.drive(translation, angleError, false, false);
    }

//TODO: Measure distance from limelight to bumper

    



}


