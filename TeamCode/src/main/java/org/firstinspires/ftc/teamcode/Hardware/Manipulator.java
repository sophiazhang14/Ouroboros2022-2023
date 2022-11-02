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
    public double acceleration;
    public double velocity;

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
    public double feedForward(double positionDeg, double velocityEncPerSec, double accelEncPerSecSquared, double ks, double kcos, double kv, double ka) {
        double rad = positionDeg * (Math.PI/180);
        return ks * Math.signum(velocityEncPerSec)
                + kcos * Math.cos(rad)
                + kv * velocityEncPerSec
                + ka * accelEncPerSecSquared;
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
        double pos = 0;
        double lastV = 0;

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
            pos = highArm.getCurrentPosition() - pos;
            velocity = pos/changeT;
            acceleration = (velocity * velocity - lastV * lastV)/(2 * pos);
            lastV = velocity;
            highArm.setPower(power + feedForward(0, velocity, acceleration, 0, 0, 0, 0));
        }
        highArm.setPower(0);
    }
    public void lowPositionPID (double position, double cap, double timeOut, double kP, double kI, double kD){
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
        double pos = 0;
        double lastV = 0;

        while (Math.abs(lowArm1.getCurrentPosition()) - position > 0.2 && time.seconds() < timeOut){
            changeT = time.seconds() - prevT;
            p = lowArm1.getCurrentPosition() - position;
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
            pos = lowArm1.getCurrentPosition() - pos;
            velocity = pos/changeT;
            acceleration = (velocity * velocity - lastV * lastV)/(2 * pos);
            lastV = velocity;
            lowArm1.setPower(power + feedForward(0, velocity, acceleration, 0, 0, 0, 0));
            lowArm2.setPower(power + feedForward(0, velocity, acceleration, 0, 0, 0, 0));
        }
        lowArm1.setPower(0);
        lowArm1.setPower(0);
    }
}