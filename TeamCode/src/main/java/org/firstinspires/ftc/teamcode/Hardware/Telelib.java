package org.firstinspires.ftc.teamcode.Hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.linearOpMode;

import com.qualcomm.robotcore.eventloop.opmode.OpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;
import org.firstinspires.ftc.teamcode.ThreadHandler;
import static android.os.SystemClock.sleep;

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

    public Servo wrist;
    //public Servo wrist;
    public Servo claw;
    public Servo stop;

    private boolean e = true;
    private boolean tfwrist = true;
    public double ugh = .75;
    private boolean lastHold;
    private boolean pastHold1;
    int hIndex = 1;
    private boolean halfToggle;
    private double half;

    ThreadHandler th_wrist;
    ThreadHandler th_claw;
    ThreadHandler th_low_arm;
    ThreadHandler th_high_arm;

    public void init() {
        th_claw = new ThreadHandler();
        th_high_arm = new ThreadHandler();
        th_low_arm = new ThreadHandler();
        th_wrist = new ThreadHandler();

        halfToggle = false;
        half = 1;

        lastHold = true;
        pastHold1 = true;


        lArm = hardwareMap.dcMotor.get("lArm");
        lArm2 = hardwareMap.dcMotor.get("lArm2");
        hArm = hardwareMap.dcMotor.get("hArm");
        turret = hardwareMap.dcMotor.get("turret");
        fr = hardwareMap.dcMotor.get("fr");
        fl = hardwareMap.dcMotor.get("fl");
        br = hardwareMap.dcMotor.get("br");
        bl = hardwareMap.dcMotor.get("bl");

        claw = hardwareMap.servo.get("claw");
        wrist = hardwareMap.servo.get("wrist");
        stop = hardwareMap.servo.get("stop");



        //in.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        hArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lArm.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        lArm2.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        //in.setDirection(DcMotorSimple.Direction.FORWARD);
        hArm.setDirection(DcMotorSimple.Direction.REVERSE);
        lArm.setDirection(DcMotorSimple.Direction.FORWARD);
        lArm2.setDirection(DcMotorSimple.Direction.FORWARD);
        fr.setDirection(DcMotorSimple.Direction.REVERSE);
        fl.setDirection(DcMotorSimple.Direction.FORWARD);
        br.setDirection(DcMotorSimple.Direction.REVERSE);
        bl.setDirection(DcMotorSimple.Direction.FORWARD);

        resetEncoders();
        stop.setPosition(0);
    }

    public void resetEncoders() {
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        hArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lArm.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        lArm2.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        hArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        lArm2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

    }

    @Override
    public void loop() {

    }
    Thread half_speed = new Thread(new Runnable() {
        @Override
        public void run() {
            ElapsedTime time = new ElapsedTime();
            time.reset();
            while(time.milliseconds() < 300){

            }
            half = .3;
            halfToggle = true;
        }
    });

    Thread full_speed = new Thread(new Runnable() {
        @Override
        public void run() {
            ElapsedTime time = new ElapsedTime();
            time.reset();
            while(time.milliseconds() < 300){

            }
            half = 1;
            halfToggle = false;
        }
    });

    Thread claw_open_thread = new Thread(new Runnable() {
        @Override
        public void run() {
            ElapsedTime time = new ElapsedTime();
            time.reset();
            while (time.milliseconds() < 10) {
            }
            wrist.setPosition(1);
            //.5
            sleep(700);
        }
    });
    Thread claw_close_thread = new Thread(new Runnable() {
        @Override
        public void run() {
            ElapsedTime time = new ElapsedTime();
            time.reset();
            while (time.milliseconds() < 10) {
            }
            wrist.setPosition(0.5);
            //1
            sleep(700);
        }
    });

    Thread wrist_open_thread = new Thread(new Runnable() {
        @Override
        public void run() {
            ElapsedTime time = new ElapsedTime();
            time.reset();
            while (time.milliseconds() < 350) {
            }
            claw.setPosition(.5); //.65
            sleep(700);
        }
    });

    Thread wrist_open_cont_thread = new Thread(new Runnable() {
        @Override
        public void run() {
            while (gamepad2.left_trigger > .5) {
                wrist.setPosition(wrist.getPosition() + .05);
                sleep(1);
            }
        }
    });



    Thread wrist_close_thread = new Thread(new Runnable() {
        @Override
        public void run() {
            ElapsedTime time = new ElapsedTime();
            time.reset();
            while (time.milliseconds() < 350) {
            }
            claw.setPosition(0); //.3
            sleep(700);
        }
    });

    Thread low_arm_thread = new Thread(new Runnable() {
        @Override
        public void run() {
            double left_stick_y = gamepad2.left_stick_y;
            ElapsedTime time = new ElapsedTime();
            time.reset();
            while (time.milliseconds() < 350) {
            }
            boolean hold1 = Math.abs(left_stick_y) <= 0.2;
            if (hold1) {
                lArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lArm2.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                lArm2.setPower(.95);
                lArm.setPower(.95);
                //potential problem- if statement keeps resetting target position. A potential solution would be by standardizing our if statements and/or moving the code
                if (!pastHold1) {
                    lArm.setTargetPosition(lArm.getCurrentPosition());
                    lArm2.setTargetPosition(lArm2.getCurrentPosition());
                }
            } else {
                lArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                lArm2.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                if (left_stick_y > .1) {
                    lArm.setPower(0.4);
                    lArm2.setPower(0.4);
                } else if (left_stick_y < -.1) {
                    lArm.setPower(-.4);
                    lArm2.setPower(-0.4);
                }
                else if(left_stick_y > -.1 && left_stick_y < 0){
                    lArm.setPower(-.1);
                    lArm2.setPower(-.1);
                }
                else{
                    lArm.setPower(.1);
                    lArm2.setPower(.1);
                }
            }
            pastHold1 = hold1;
        }
    });

    Thread low_arm_thread_down = new Thread(new Runnable() {
        @Override
        public void run() {
            lArm.setPower(.4);
            lArm2.setPower(.4);
            sleep(200);
            lArm.setPower(-.6);
            lArm2.setPower(-.6);
            sleep(1000);

        }
    });

    Thread mid_junction_thread = new Thread(new Runnable() {
        @Override
        public void run() {
            resetEncoders();
            while (lArm2.getCurrentPosition() > -180) {
                lArm2.setPower(-.8);
                lArm.setPower(-.8);
            }
            while (lArm2.getCurrentPosition() > -210) {
                lArm2.setPower(-.4);
                lArm.setPower(-.4);
            }
            while (hArm.getCurrentPosition() > 150 || hArm.getCurrentPosition() < 130) {
                lArm.setPower(-.5);
                if (hArm.getCurrentPosition() < 130) {
                    hArm.setPower(.3);
                } else if (hArm.getCurrentPosition() > 150) {
                    hArm.setPower(-.3);
                }

            }
            hArm.setPower(0);

        }
    });



    Thread high_arm_thread = new Thread(new Runnable() {
        @Override
        public void run() {
            double right_stick_y = gamepad2.right_stick_y;
            boolean hold = Math.abs(right_stick_y) <= 0.2;
            ElapsedTime time = new ElapsedTime();
            time.reset();
            while (time.milliseconds() < 350) {
            }
            if (hold) {
                hArm.setMode(DcMotor.RunMode.RUN_TO_POSITION);
                if (gamepad2.b) {
                    hArm.setPower(-1);
                } else {
                    hArm.setPower(1);
                }
                if (!lastHold) {
                    hArm.setTargetPosition(hArm.getCurrentPosition());
                }

            } else {
                hArm.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
                if (right_stick_y > .1) {
                    hArm.setPower(.6);
                } else if (right_stick_y < -.1) {
                    hArm.setPower(-.6);
                } else if (right_stick_y > -.1 && right_stick_y < 0) {
                    hArm.setPower(-.1);
                } else {
                    hArm.setPower(.1);
                }
            }
            lastHold = hold;
        }
    });

    public void arcadeDrive(){
        double left_stick_x = gamepad1.left_stick_x;
        double left_stick_y = gamepad1.left_stick_y;
        double right_stick_x = gamepad1.right_stick_x;
        double half_speed_right_trigger = gamepad1.right_trigger;
        boolean full_speed_b = gamepad1.b;
        boolean index = false;
        // ughhhhhh varible???
        if (half_speed_right_trigger > .1) {
            ugh = .2;
        }
        else{
            ugh = .75;
        }

        if (left_stick_x > 0.1 || left_stick_x < -0.1 ||
                left_stick_y > 0.1 || left_stick_y < -0.1 || right_stick_x > 0.1|| right_stick_x < -0.1){
            fr.setPower(ugh * ((left_stick_y + left_stick_x) + right_stick_x));
            fl.setPower(ugh * ((left_stick_y - left_stick_x) - right_stick_x));
            br.setPower(ugh * ((left_stick_y - left_stick_x) + right_stick_x));
            bl.setPower(ugh * ((left_stick_y + left_stick_x) - right_stick_x));
        }
        else{
            fl.setPower(0);
            fr.setPower(0);
            br.setPower(0);
            bl.setPower(0);
        }

    }

    public void claw(){
        boolean right_bumper = gamepad2.right_bumper;

        if(right_bumper && e){
            th_claw.queue(claw_open_thread);
            e = false;
        }
        else if (right_bumper && !e){
            th_claw.queue(claw_close_thread);
            e = true;
        }
    }

    public void wrist(){
        boolean left_bumper = gamepad2.left_bumper;

        if(left_bumper && tfwrist){
            th_wrist.queue(wrist_open_thread);
            tfwrist = false;
        }
        else if(!left_bumper && !tfwrist){
            th_wrist.queue(wrist_close_thread);
            tfwrist = true;
        }
    }

    public void high_arm(){
        if (Math.abs(gamepad2.right_stick_y) > .05) {
            //th_high_arm.queue(high_arm_thread);
            hArm.setPower(gamepad2.right_stick_y *.5);
        }
        else {
            hArm.setPower(0);
        }
    }
    boolean noarmpower = true;
    public void low_arm(){
        if(Math.abs(gamepad2.left_stick_y)> .05){
            //th_low_arm.queue(low_arm_thread);
            lArm.setPower(gamepad2.left_stick_y *.7);
            lArm2.setPower(gamepad2.left_stick_y *.7);
            noarmpower = true;

        } else if (noarmpower){
            lArm.setPower(0);
            lArm2.setPower(0);

        }

        if (gamepad2.dpad_down) {
            th_low_arm.queue(low_arm_thread_down);
            noarmpower = true;
        } else if (gamepad2.dpad_up) {
            noarmpower = false;
            th_low_arm.queue(mid_junction_thread);
        }

    }

    public void turnTurret(){
        double left_stick_x = gamepad2.left_stick_x;
        if (left_stick_x > .5) {
            turret.setPower(-.25);
        }
        else if (left_stick_x < -.5){
            turret.setPower(.25);
        }
        else
        {
            turret.setPower(0);
        }
    }

    public void kill(){
        fl.setPower(0);
        fr.setPower(0);
        br.setPower(0);
        bl.setPower(0);
        lArm.setPower(0);
        lArm2.setPower(0);
        hArm.setPower(0);
    }

    public void telemetry() {
        telemetry.addData("lowarm power: ", lArm.getPower());
        telemetry.addData("lowarm pos: ", lArm.getCurrentPosition());
        telemetry.addLine();

        telemetry.addData("lowarm2 power: ", lArm2.getPower());
        telemetry.addData("lowarm2 pos: ", lArm2.getCurrentPosition());
        telemetry.addLine();

        telemetry.addData("higharm power: ", hArm.getPower());
        telemetry.addData("higharm pos: ", hArm.getCurrentPosition());
        telemetry.addLine();

        telemetry.addData("wrist pos: ", claw.getPosition());
        telemetry.addData("claw pos: ", wrist.getPosition());
        telemetry.addData("stop pos: ", stop.getPosition());
        telemetry.addLine();
        telemetry.addData("fl: ", fl.getPower());
        telemetry.addData("fr: ", fr.getPower());
        telemetry.addData("bl: ", bl.getPower());
        telemetry.addData("br: ", br.getPower());
        telemetry.addData("halfSpeed: ", ugh == .2);

    }
}