package org.firstinspires.ftc.teamcode.Hardware;

import static com.sun.tools.javac.jvm.ByteCodes.error;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Turret {

    LinearOpMode opMode;
    private DcMotor turret;

    public Turret(LinearOpMode opMode1) {
        opMode = opMode1;
        turret = opMode.hardwareMap.dcMotor.get("turret");
    }

    public void positionPID (double goal, double runtime, double kP, double kI, double kD) {
        /*ElapsedTime time = new ElapsedTime();
        ElapsedTime smallTime = new ElapsedTime();
        
        double error;
        double lastError = 0;
        double beforeTime = 0;
        double p;
        double i = 0;
        double d;

        goal *= (Math.PI/90);

        time.reset();
        smallTime.reset();

        while (lastError > 2 && time.seconds() < runtime && opMode.opModeIsActive()){
            smallTime.reset();

            error = goal - turret.getCurrentPosition();

            p = error * kP;
            i += (error * (time.seconds() - beforeTime)) * kI;
            d = ((error - lastError) / smallTime.seconds()) * kD;

            turret.setPower(p + i + d);

            lastError = error;
        }
        turret.setPower(0);*/
    }

    public void turnPID(double goal, boolean isRight, double kP, double kI, double kD, double timeOutMS) {

    }
}
