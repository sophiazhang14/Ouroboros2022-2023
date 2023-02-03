package org.firstinspires.ftc.teamcode.Hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.telemetry;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Manipulator {
    public Servo wrist;
    public Servo claw;
    public Servo stop;

    public LinearOpMode opMode;
    public DcMotor hArm;
    public DcMotor lArm;
    public DcMotor lArm2;
    public DcMotor turret;
    public double acceleration;
    public double velocity;

    public Manipulator(LinearOpMode opMode) {
        this.opMode = opMode;
        lArm = opMode.hardwareMap.dcMotor.get("lArm");
        lArm2 = opMode.hardwareMap.dcMotor.get("lArm2");
        hArm = opMode.hardwareMap.dcMotor.get("hArm");
        claw = opMode.hardwareMap.servo.get("claw");
        wrist = opMode.hardwareMap.servo.get("wrist");
        stop = opMode.hardwareMap.servo.get("stop");
        opMode.telemetry.addLine("claw init");


        lArm2.setDirection(DcMotorSimple.Direction.FORWARD);
        hArm.setDirection(DcMotorSimple.Direction.REVERSE);
        lArm.setDirection(DcMotorSimple.Direction.FORWARD);

        hArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lArm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

       // opMode.telemetry.addLine(lArm.toString());
    //    opMode.telemetry.addLine(lArm2.toString());
      //  opMode.telemetry.addLine(hArm.toString());
       // opMode.telemetry.update();

    }

    public void resetEncoders(){
        lArm.setMode(DcMotor.RunMode.RESET_ENCODERS);

        lArm2.setMode(DcMotor.RunMode.RESET_ENCODERS);

        hArm.setMode(DcMotor.RunMode.RESET_ENCODERS);

        hArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lArm2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void setSize(double pos){
        stop.setPosition(pos);
        opMode.telemetry.addData("stop pos :: ", stop.getPosition());
        opMode.telemetry.update();
    }
    public void setClaw(double pos){
        wrist.setPosition(pos);
    }
    public void setWrist(double pos) {
        claw.setPosition(pos);
    }
    public double feedForward(double positionEnc, double velocityEncPerSec, double accelEncPerSecSquared, double ks, double kcos, double kv, double ka) {
        //double rad = positionDeg * (Math.PI/180);
        double rad = positionEnc *(360/1000); //1000 is the place holder for the number of encoder ticks per rev
                                                // after divide until its __ number of degrees per encoder tick
        return ks * Math.signum(velocityEncPerSec)
                + kcos * Math.cos(rad)
                + kv * velocityEncPerSec
                + ka * accelEncPerSecSquared;
    }

    public void highPositionPID (double position, double cap, double timeOut, double kP, double kI, double kD){
        ElapsedTime time = new ElapsedTime();
        double RADIANS_AT_START = 0 * (Math.PI/180); // zero is place holder for the angle in degrees at the starting pos of hArm
        double power = 0;
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

        resetEncoders();
        double diff = Math.abs(-hArm.getCurrentPosition() - position);
        time.reset();
        while (Math.abs(-hArm.getCurrentPosition() - position) > 2 && time.seconds() < timeOut){
            opMode.telemetry.addData("p :: ", p *kP);
            opMode.telemetry.addData("i :: ", i * kI);
            opMode.telemetry.addData("d :: ", d * kD);
            opMode.telemetry.addData("time", time.seconds());
            opMode.telemetry.addData("encoder diff", Math.abs(hArm.getCurrentPosition()) - position);
            opMode.telemetry.addData("encoder", -hArm.getCurrentPosition());
            opMode.telemetry.addData("encoder desired", position);
            opMode.telemetry.addData("power", power);
            opMode.telemetry.update();
            changeT = time.seconds() - prevT;
            p = hArm.getCurrentPosition() - position;
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
            pos = hArm.getCurrentPosition() - pos;
            velocity = pos/changeT;
            acceleration = (velocity * velocity - lastV * lastV)/(2 * pos);
            lastV = velocity;
            hArm.setPower(power);// + feedForward(0, velocity, acceleration, 0, 0, 0, 0));
        }
        hArm.setPower(0);
    }
    public void lowPositionPID (double position, double cap, double timeOut, double kP, double kI, double kD){
        ElapsedTime time = new ElapsedTime();
        hArm.setPower(.3);
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
        resetEncoders();
        time.reset();
        while (Math.abs(-lArm.getCurrentPosition() - position) > 1 && time.seconds() < timeOut) {
            changeT = time.seconds() - prevT;
            p = lArm.getCurrentPosition() - position;
            i = prevI + p * changeT;
            d = (p - prevP) / changeT;

            prevP = p;
            prevI = i;
            prevT = prevT - time.seconds();

            power = p * kP + i * kI + d * kD;

            if (Math.abs(p) > 0.1) {
                prevI = 0;
            } // why need????

            if (Math.abs(power) > cap) {
                power = Math.signum(power) * cap;
            }
            pos = lArm.getCurrentPosition() - pos;
            velocity = pos / changeT;
            acceleration = (velocity * velocity - lastV * lastV) / (2 * pos);
            lastV = velocity;
            lArm.setPower(power /*+ feedForward(0, velocity, acceleration, 0, 0, 0, 0)*/);
            lArm2.setPower(power /*+ feedForward(0, velocity, acceleration, 0, 0, 0, 0)*/);
            opMode.telemetry.addData("time", time.seconds());
            opMode.telemetry.addData("encoder diff", Math.abs(Math.abs(lArm.getCurrentPosition()) - position));
            opMode.telemetry.addData("encoder", lArm.getCurrentPosition());
            opMode.telemetry.addData("power", power);
            opMode.telemetry.addData("p :: ", p *kP);
            opMode.telemetry.addData("i :: ", i * kI);
            opMode.telemetry.addData("d :: ", d * kD);
            opMode.telemetry.update();
        }
        lArm.setPower(0);
        lArm2.setPower(0);
    }

    public void moveLArm(double power){
        lArm.setPower(power);
        lArm2.setPower(power);
    }
    public void moveHArm(double power){
        hArm.setPower(power);
    }

    public void holdLArm(){
        lArm.setTargetPosition(lArm.getCurrentPosition());
        lArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }

    public void holdHArm(){
        hArm.setTargetPosition(hArm.getCurrentPosition());
        hArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
    }
}