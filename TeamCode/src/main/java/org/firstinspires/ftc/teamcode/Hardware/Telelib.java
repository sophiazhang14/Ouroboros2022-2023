package org.firstinspires.ftc.teamcode.Hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;

import org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion;

public class Telelib extends OpMode {
    // hannah: intake
    // krish: lift
    // shriya: claw
    //public DcMotor in;
    public DcMotor lArm;
    public DcMotor lArm2;
    public DcMotor hArm;
    public DcMotor turret;

    public DcMotor fr;
    public DcMotor fl;
    public DcMotor br;
    public DcMotor bl;

    public Servo claw;
    //public Servo wrist;
    public Servo twist;

    public double ugh = 0.5;

    public void init() {
        lArm = hardwareMap.dcMotor.get("lArm");
        lArm2 = hardwareMap.dcMotor.get("lArm2");
        hArm = hardwareMap.dcMotor.get("hArm");
        turret = hardwareMap.dcMotor.get("turret");
        fr = hardwareMap.dcMotor.get("fr");
        fl = hardwareMap.dcMotor.get("fl");
        br = hardwareMap.dcMotor.get("br");
        bl = hardwareMap.dcMotor.get("bl");

        //wrist = hardwareMap.servo.get("wrist");
        twist = hardwareMap.servo.get("twist");
        claw = hardwareMap.servo.get("claw");

        //in.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lArm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //in.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);
    }


    @Override
    public void loop() {

    }

    public void arcadeDrive(){
        double left_stick_x = gamepad1.left_stick_x;
        double left_stick_y = gamepad1.left_stick_y;
        double right_stick_x = gamepad1.right_stick_x;
        boolean half_speed_a = gamepad1.a;
        boolean index = false;
        // ughhhhhh varible???
        if (half_speed_a && index) {
            ugh = 1;
            index = false;
        }
        if (half_speed_a && !index){
            ugh = .5;
            index = true;
        }
        if (left_stick_x > 0.1 || left_stick_x < -0.1 ||
                left_stick_y > 0.1 || left_stick_y < -0.1 || right_stick_x > 0.1|| right_stick_x < -0.1){
            fr.setPower(ugh * (left_stick_y + left_stick_x) - right_stick_x);
            fl.setPower(ugh * (left_stick_y - left_stick_x) + right_stick_x);
            br.setPower(ugh * (left_stick_y + left_stick_x) + right_stick_x);
            bl.setPower(ugh * (left_stick_y - left_stick_x) - right_stick_x);
        }
        else{
            kill();
        }

    }

    public void claw(){
        boolean left_bumper = gamepad2.left_bumper;
        boolean right_bumper = gamepad2.right_bumper;

        if (right_bumper){
            claw.setPosition(1);
        }
        else if(left_bumper){
            claw.setPosition(0);
        }
    }
    public void moveWrist() {
        double right_trigger = gamepad2.right_trigger;
        double left_trigger = gamepad2.left_trigger;

        if (right_trigger > 0.5) {
            //wrist.setPosition(1);
        }
        if (left_trigger > 0.5) {
            //wrist.setPosition(0);
        }
    }
        public void twist(){
          boolean left_arrow = gamepad2.dpad_left;
          boolean right_arrow = gamepad2.dpad_right;

          if (left_arrow){
              twist.setPosition(1);
          }
            if (right_arrow){
                twist.setPosition(0);
            }
    }
    public void low_arm(){
        double left_stick_y = gamepad2.left_stick_y;
        if (left_stick_y > .05 || left_stick_y < -.05){
            lArm.setPower(gamepad2.left_stick_y * .35);
            lArm2.setPower(gamepad2.left_stick_y * .35);
        } else {
            lArm2.setPower(0);
            lArm.setPower(0);
        }
    }

    public void high_arm(){
        double right_stick_y = gamepad2.right_stick_y;
        if (right_stick_y > .05 || right_stick_y < -.05){
            hArm.setPower(gamepad2.right_stick_y * .5);
        } else {
            hArm.setPower(0);
        }
    }

    public void turnTurret(){
        //pid
        double right_trigger = gamepad1.right_trigger;
        double left_trigger = gamepad1.left_trigger;
        if (left_trigger > .5){
            turret.setPower(-1 * left_trigger);
        }
        else if (right_trigger > .5){
            turret.setPower(right_trigger * .25);
        }
    }
    public void kill(){
        fl.setPower(0);
        fr.setPower(0);
        br.setPower(0);
        bl.setPower(0);
    }
}