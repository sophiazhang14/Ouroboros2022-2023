package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class TempMan {
    public LinearOpMode opMode;
    public DcMotor hArm;
    public DcMotor lArm;
    public DcMotor lArm2;
    public Servo wrist;

    public TempMan(LinearOpMode opMode) {
        this.opMode = opMode;
        lArm = opMode.hardwareMap.dcMotor.get("lArm");
        lArm2 = opMode.hardwareMap.dcMotor.get("lArm2");
        hArm = opMode.hardwareMap.dcMotor.get("hArm");
        wrist = opMode.hardwareMap.servo.get ("wrist");

        lArm2.setDirection(DcMotorSimple.Direction.REVERSE);
        hArm.setDirection(DcMotorSimple.Direction.REVERSE);
        lArm.setDirection(DcMotorSimple.Direction.REVERSE);

        hArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lArm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

    }

    public void resetEncoders() {
        lArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lArm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        hArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        hArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lArm2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void setClaw(double pos){
        wrist.setPosition(pos);
    }
    public void testHArm(double pow, double timeout){
        ElapsedTime time = new ElapsedTime();

        time.reset();
        while(time.seconds() < timeout) {
            hArm.setPower(pow);
        }
        hArm.setPower(0);
    }
    public void tempLArm(int distance, double power){
        lArm.setTargetPosition(distance);
        lArm2.setTargetPosition(distance);
        lArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lArm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lArm.setPower(power);
        lArm2.setPower(power);
    }
    public void tempLArmHold(double holdPow){
        lArm.setTargetPosition(lArm.getCurrentPosition());
        lArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lArm.setPower(holdPow);
        lArm2.setTargetPosition(lArm.getCurrentPosition());
        lArm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        lArm2.setPower(holdPow);
    }
    public void tempHArm(int distance, double power) {
        hArm.setTargetPosition(distance);
        hArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hArm.setPower(power);
    }
    public void tempHArmHold(double holdPow){
        hArm.setTargetPosition(hArm.getCurrentPosition());
        hArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
        hArm.setPower(holdPow);
    }
}
