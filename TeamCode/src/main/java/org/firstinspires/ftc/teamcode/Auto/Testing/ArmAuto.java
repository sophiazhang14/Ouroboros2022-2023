package org.firstinspires.ftc.teamcode.Auto.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Auto.DriveTrain;
import org.firstinspires.ftc.teamcode.Auto.Vision;
import org.firstinspires.ftc.teamcode.Hardware.Manipulator;
import org.firstinspires.ftc.teamcode.Loop;

@Autonomous(group = "auto", name = "ArmAuto")
public class ArmAuto extends LinearOpMode{
    Manipulator manip_thd;
    DriveTrain dt;
    Vision vis;

    Thread hold_hArm = new Thread(new Runnable(){
        @Override
        public void run(){
            manip_thd.moveHArm(.3);
        }
    });

    Thread hold_lArm = new Thread(new Runnable(){
        @Override
        public void run(){
            manip_thd.moveLArm(-.5);
            //sleep(5000);
        }
    });

    Thread hold_lArm_more = new Thread(new Runnable(){
        @Override
        public void run(){
            manip_thd.moveLArm(-.65);
            //sleep(5000);
        }
    });

    Thread thd_open_claw = new Thread(new Runnable(){
        @Override
        public void run(){
            manip_thd.setClaw(.5);
        }
    });

    Thread thd_close_claw = new Thread(new Runnable(){
        @Override
        public void run(){
            manip_thd.setClaw(1);
        }
    });

    Loop loop_lHoldM = new Loop();
    Loop loop_hold = new Loop();
    Loop loop_lHold = new Loop();
    Loop loop_open = new Loop();
    Loop loop_close = new Loop();


    @Override
    public void runOpMode() throws InterruptedException {

        manip_thd = new Manipulator(this);
        //    Claw claw = new Claw(this);
        // Manipulator manipulator = new Manipulator(this);
        //Turret turret = new Turret(this);
        dt = new DriveTrain(this);
        vis = new Vision(this);
        //dt.encoderMove(.5, 3, 3, false, false);
        loop_hold.add(hold_hArm);
        loop_lHold.add(hold_lArm);
        loop_open.add(thd_open_claw);
        loop_close.add(thd_close_claw);

        waitForStart();
        //while(opModeIsActive()) {

        loop_close.run();
        int sense = vis.senseBlueLeft();
            //score pre load
            //getting to junction vertically
            // get around the pole dt.encoderMove(.5, 4, 2, true, false);
            dt.encoderMove(.5, 8, 2, false, false);
            // strafe to get to position dt.encoderMove(.5, 4, 2, true, true);
            // moving arm to mid junction position
            manip_thd.setWrist(0); // or 0
            //manip_thd.highPositionPID(-40, .75, 3, .0003, .0001, .0008);
            manip_thd.highPositionPID(-100, 1, 4, .0003, .00035, .0004);
            //loop_hold.run();

            //extra version 1
            /*loop_hold.run();
            sleep(100);
            manip_thd.lowPositionPID(45, 1, 2, .0003, .0001, .001);
            //sleep(200);
            loop_lHold.run();
            loop_hold.end();
            manip_thd.highPositionPID(10,.75,2,-.0003,-.0009,-.0009);
            loop_lHold.end();
            manip_thd.lowPositionPID(10,1,2,.0003,.0001,.001);
            loop_lHoldM.run();
            loop_hold.run();*/
/*
            dt.encoderMove(.75,8,2,true,true);

            //manip_thd.highPositionPID(-50, .7, 4, .0003, .00001, .001);
            //manip_thd.highPositionPID(-75, .7, .5, .0003, .0001, .0001);
            //manip_thd.highPositionPID(10,.7,3,.0003,.00001,.0001);
            sleep(1000);
            dt.encoderMove(-.25,2,1,false,false);
            //loop_hold.run();
            loop_open.run();
            loop_open.end();

            // getting to junction horizontally
            dt.encoderMove(.5, 5, 5, true, true);
            //loop_hold.end();
            loop_lHold.end();
            // move low arm down to score cone
            manip_thd.lowPositionPID(20, .2, 2, .0003, .00001, .001);
            sleep(200);
            // drop cone
            loop_open.run();
            loop_open.end();
            telemetry.addLine("move back to start position");
            telemetry.update();
            // move arm back to starting position
            //manip_thd.lowPositionPID(20, .2, 2, .0003, .00001, 001);
            //manip_thd.highPositionPID(50, .7, 3, .0003, .00001, .001);
            loop_hold.end();

            telemetry.addLine("park");
            telemetry.update();

            //manip.lowPositionPID(10,.7,2,.01,.01,.001);
//sleep(10000);*/
    }
}
