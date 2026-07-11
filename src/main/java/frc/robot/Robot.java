package frc.robot;

import com.ctre.phoenix6.hardware.TalonFX;
import edu.wpi.first.wpilibj.TimedRobot;
import edu.wpi.first.wpilibj.Timer;
import edu.wpi.first.wpilibj.XboxController;
import edu.wpi.first.wpilibj.smartdashboard.SmartDashboard;

public class Robot extends TimedRobot {

    TalonFX motor1 = new TalonFX(1);

    TalonFX motor2 = new TalonFX(2);

    XboxController controller = new XboxController(0);

    Timer timer = new Timer();

    boolean motor2On = false;

    boolean lastB = false;


    @Override
    public void autonomousInit() {
        timer.restart();
    }

    @Override
    public void autonomousPeriodic() {

        double secondsElapsed = timer.get();


        int segment = (int) (secondsElapsed / 3);


        boolean shouldGoForward = (segment % 2 == 0);


        double speed;
        if (shouldGoForward) {
            speed = 0.5;
        } else {
            speed = -0.5;
        }

        motor1.set(speed);
        motor2.set(speed);
    }


    @Override
    public void teleopInit() {

        motor2On = false;

        timer.stop();
    }

    @Override
    public void teleopPeriodic() {

        boolean aButtonHeld = controller.getAButton();

        if (aButtonHeld) {
            motor1.set(0.5);
        } else {
            motor1.set(0);
        }


        boolean bButtonHeld = controller.getBButton();


        boolean bWasJustPressed = bButtonHeld && !lastB;

        if (bWasJustPressed) {

            motor2On = !motor2On;


            timer.restart();
        }

        lastB = bButtonHeld;



        boolean tenSecondsHavePassed = timer.hasElapsed(10);

        if (motor2On && tenSecondsHavePassed) {
            motor2On = false;
        }



        if (motor2On) {
            motor2.set(0.5);
        } else {
            motor2.set(0);
        }

        SmartDashboard.putBoolean("Motor2 Toggled", motor2On);
    }
}