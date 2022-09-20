package org.firstinspires.ftc.teamcode.Hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

public class Telelib {
    // hannah: intake
    // krish: lift
    // shriya: claw
    //public DcMotor in;
    public DcMotor lArm;
    public DcMotor hArm;
    public DcMotor turret;

    public DcMotor fr;
    public DcMotor fl;
    public DcMotor br;
    public DcMotor bl;

    public Servo linac;
    public Servo wrist;

    public double ugh = 1;

    public void init() {
        //in = hardwareMap.dcMotor.get("in");
        lArm = hardwareMap.dcMotor.get("lArm");
        hArm = hardwareMap.dcMotor.get("hArm");
        turret = hardwareMap.dcMotor.get("turret");
        fr = hardwareMap.dcMotor.get("fr");
        fl = hardwareMap.dcMotor.get("fl");
        br = hardwareMap.dcMotor.get("br");
        bl = hardwareMap.dcMotor.get("bl");

        linac = hardwareMap.servo.get ("la");
        wrist = hardwareMap.servo.get("wrist");

        //in.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //in.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
    }

    public void arcadeDrive(){
        double left_stick_x = gamepad1.left_stick_x;
        double left_stick_y = gamepad1.left_stick_y;
        double right_stick_x = gamepad1.right_stick_x;
        boolean half_speed_a = gamepad1.a;
        boolean index = false;
        // ughhhhhh varible???
        if (half_speed_a && !index){
            ugh = .5;
            index = true;
        }
        else if (half_speed_a && index){
            ugh = 1;
            index = false;
        }
        if (left_stick_x > 0.05 || left_stick_x < -0.05 ||
                left_stick_y > 0.05 || left_stick_y < -0.05 || right_stick_x > 0.05|| right_stick_x < -0.05){
            fr.setPower(ugh * (left_stick_y + left_stick_x) - right_stick_x);
            fl.setPower(ugh * (left_stick_y - left_stick_x) + right_stick_x);
            br.setPower(ugh * (left_stick_y + left_stick_x) + right_stick_x);
            bl.setPower(ugh * (left_stick_y - left_stick_x) - right_stick_x);
        }
        else{
            kill();
        }

    }

    public void plunger(){
        boolean left_bumper = gamepad2.right_bumper;
        boolean right_bumper = gamepad2.right_bumper;

        if (right_bumper){
            linac.setPosition(1);
        }
        if(left_bumper){
            linac.setPosition(0);
        }
    }
    public void moveWrist(){
        double right_trigger = gamepad2.right_trigger;
        double left_trigger = gamepad2.left_trigger;

        if (right_trigger > 0.5){
            wrist.setPosition(1);
        }
        if (left_trigger > 0.5){
            wrist.setPosition(0);
        }
    }
    public void low_arm(){
        //pid
    }
    public void high_arm(){
        //pid
    }

    public void turnTurret(){
        //pid
        double left_stick_x = gamepad2.left_stick_x;
        if (left_stick_x > .5 || left_stick_x < -.5){
            turret.setPower(left_stick_x);
        }
    }
    public void kill(){
        fl.setPower(0);
        fr.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }

    /*public void intake() {
        boolean r_bumper = gamepad2.right_bumper;
        boolean l_bumper = gamepad2.left_bumper;
        if (r_bumper) {
            in.setPower(1);
        } else if (l_bumper) {
            in.setPower(-1);
        } else {
            in.setPower(0);

        }
    }*/
}