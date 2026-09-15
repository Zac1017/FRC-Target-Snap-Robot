package frc.robot.commands;

import edu.wpi.first.math.geometry.Translation2d;
import edu.wpi.first.wpilibj2.command.Command;
import frc.robot.subsystems.drive.SwerveDrive;

public class SnapTo extends Command {
    private final SwerveDrive swerve;
    private final Translation2d targetTranslation2d;

    public SnapTo(SwerveDrive swerve, Translation2d targetTranslation2d) {
        this.swerve = swerve;
        this.targetTranslation2d = targetTranslation2d;

        addRequirements(swerve);
    }

    

}