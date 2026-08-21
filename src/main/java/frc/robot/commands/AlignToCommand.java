package frc.robot.commands;

import java.util.function.BooleanSupplier;

import edu.wpi.first.math.geometry.Pose3d;
import frc.robot.Constants;
import frc.robot.subsystems.drive.SwerveDrive;
import frc.robot.util.LimelightHelpers;

public class AlignToCommand {
    
    BooleanSupplier left;
    SwerveDrive swerve;

    public AlignToCommand(SwerveDrive swerve, BooleanSupplier left) {
        this.swerve = swerve;
        this.left = left;
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






}


