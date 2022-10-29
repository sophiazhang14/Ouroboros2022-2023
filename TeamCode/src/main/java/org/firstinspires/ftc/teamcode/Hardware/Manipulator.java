package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Manipulator {
    public Servo wrist;
    public LinearOpMode opMode;
    public DcMotor highArm;
    public DcMotor lowArm1;
    public DcMotor lowArm2;
    public Manipulator(LinearOpMode opMode) {
        this.opMode = opMode;
        opMode.hardwareMap.servo.get("wrist");
        opMode.hardwareMap.dcMotor.get("highArm");
        opMode.hardwareMap.dcMotor.get("lowArm1");
        opMode.hardwareMap.dcMotor.get("lowArm2");
        opMode.telemetry.addLine("claw init");
    }
    public void setWrist(double pos) {
        wrist.setPosition(pos);
    }
    public void highPositionPID (double position, double cap, double timeOut, double kP, double kI, double kD){
        ElapsedTime time = new ElapsedTime();
        double power;
        double p = 0;
        double i = 0;
        double d = 0;
        double changeT = 0;
        double prevP = 0;
        double prevI = 0;
        double prevD = 0;
        double prevT = 0;

        while (Math.abs(highArm.getCurrentPosition() - position) > 0.2 && time.seconds() < timeOut){
            changeT = time.seconds() - prevT;
            p = highArm.getCurrentPosition() - position;
            i = prevI + p * changeT;
            d = (p - prevP)/changeT;

            prevP = p;
            prevI = i;
            prevT = prevT - time.seconds();

            power = p * kP + i * kI + d * kD;

            if(Math.abs(p) > 0.1){
                prevI = 0;
            } // why need????

            if (Math.abs(power) > cap){
                power = Math.signum(power) * cap;
            }
            highArm.setPower(power);
        }
        highArm.setPower(0);
    }
}
