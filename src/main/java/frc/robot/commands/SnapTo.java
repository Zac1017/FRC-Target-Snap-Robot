package frc.robot.commands;

import java.util.function.Supplier;

import com.ctre.phoenix6.hardware.Pigeon2;

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
    private Supplier<Rotation2d> heading;

    private final Pigeon2 kPigeon = new Pigeon2(1);

    private final double kRotationP;

    public SnapTo(SwerveDrive swerve, Translation2d targetTranslation2d) {
        this.swerve = swerve;
        this.robotPose2d = swerve.getRawOdometeryPose();
        this.robotTranslation2d = robotPose2d.getTranslation();
        this.targetTranslation2d = targetTranslation2d;
        this.heading = () -> getHeading();
        this.kRotationP = 0.0;

        addRequirements(swerve);
    }

    public Rotation2d getHeading() {
        return kPigeon.getRotation2d();
    }
    public Translation2d getTranslationFromTarget() {
        return new Translation2d(
            targetTranslation2d.getX() - robotTranslation2d.getX(),
            targetTranslation2d.getY() - robotTranslation2d.getY());
    }

    public Rotation2d getAngleToTarget() {
        double targetAngle = Math.toDegrees(Math.atan2(getTranslationFromTarget().getY(), getTranslationFromTarget().getX()));
        double angle = targetAngle - (heading.get()).getDegrees();

        while (angle > 180) {
            angle -= 360;
        }

        while (angle < 180) {
            angle += 360;
        }

        return new Rotation2d(angle);
    }

    public Translation2d getRelativeTranslation() {
        double headingInRadians = Math.toRadians((heading.get()).getDegrees());

        return new Translation2d(
            getTranslationFromTarget().getX() * Math.cos(headingInRadians)
            + getTranslationFromTarget().getY() * Math.sin(headingInRadians),
            
            -getTranslationFromTarget().getX() * Math.sin(headingInRadians)
            + getTranslationFromTarget().getY() * Math.cos(headingInRadians)
        );
    }

    public void snapSwerve() {
        swerve.drive(new Translation2d(0, 0), 
        Math.toRadians(getAngleToTarget().getDegrees()) * kRotationP, 
        true, 
        true
        );
    }

    @Override
    public void execute() {
        snapSwerve();
    }



}