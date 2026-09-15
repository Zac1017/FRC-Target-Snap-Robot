package frc.robot.commands;

import edu.wpi.first.math.geometry.Pose2d;
import edu.wpi.first.math.geometry.Rotation2d;
import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.SwerveDrive;

public class SnapTo extends Command {
    private final SwerveDrive swerve;
    private Pose2d robotPose2d;
    private Translation2d robotTranslation2d;
    private Translation2d targetTranslation2d;
    private Rotation2d heading;

    public SnapTo(SwerveDrive swerve, Translation2d targetTranslation2d) {
        this.swerve = swerve;
        this.robotPose2d = swerve.getRawOdometeryPose();
        this.robotTranslation2d = robotPose2d.getTranslation();
        this.targetTranslation2d = targetTranslation2d;
        this.heading = swerve.getYaw();

        addRequirements(swerve);
    }

    public Translation2d getTranslationFromTarget() {
        return new Translation2d(
            targetTranslation2d.getX() - robotTranslation2d.getX(),
            targetTranslation2d.getY() - robotTranslation2d.getY());
    }

    public Rotation2d getAngleToTarget() {
        double targetAngle = Math.toDegrees(Math.atan2(getTranslationFromTarget().getY(), getTranslationFromTarget().getX()));
        double angle = targetAngle - heading.getDegrees();

        while (angle > 180) {
            angle -= 360;
        }

        while (angle < 180) {
            angle += 360;
        }

        return new Rotation2d(angle);
    }

    public Translation2d getRelativeTranslation() {
        double headingInRadians = Math.toRadians(heading.getDegrees());

        return new Translation2d(
            getTranslationFromTarget().getX() * Math.cos(headingInRadians)
            + getTranslationFromTarget().getY() * Math.sin(headingInRadians),
            
            -getTranslationFromTarget().getX() * Math.sin(headingInRadians)
            + getTranslationFromTarget().getY() * Math.cos(headingInRadians)
        );
    }



}