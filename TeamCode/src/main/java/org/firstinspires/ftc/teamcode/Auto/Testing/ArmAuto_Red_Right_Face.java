package org.firstinspires.ftc.teamcode.Auto.Testing;

import com.qualcomm.robotcore.eventloop.opmode.Autonomous;
import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;

import org.firstinspires.ftc.teamcode.Auto.DriveTrain;
import org.firstinspires.ftc.teamcode.Auto.Vision;
import org.firstinspires.ftc.teamcode.Hardware.Manipulator;
import org.firstinspires.ftc.teamcode.Hardware.Turret;
import org.firstinspires.ftc.teamcode.Loop;

@Autonomous(group = "auto", name = "ArmAuto_Red_Right_Face")
public class ArmAuto_Red_Right_Face extends LinearOpMode{
    Manipulator manip_thd;
    DriveTrain dt;
    Vision vis;
    Turret turret;

    Thread hold_hArm = new Thread(new Runnable(){
        @Override
        public void run(){
            manip_thd.holdHArm();
        }
    });

    Thread hold_lArm = new Thread(new Runnable(){
        @Override
        public void run(){
            manip_thd.holdLArm();
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

    Thread thd_wrist_parallel = new Thread(new Runnable(){
        @Override
        public void run() {
            manip_thd.setWrist(.5);
        }});
   Thread thd_turret_hold = new Thread(new Runnable(){
        @Override
        public void run() {
            turret.threading_hold();
        }});

    Loop loop_lHoldM = new Loop();
    Loop loop_hold = new Loop();
    Loop loop_lHold = new Loop();
    Loop loop_open = new Loop();
    Loop loop_close = new Loop();
    Loop loop_wrist = new Loop();


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
        loop_wrist.add(thd_wrist_parallel);

        waitForStart();

        loop_close.run();
        int sense = vis.senseBlueLeft();
        manip_thd.setSize(0);
        manip_thd.setSize(1);

        dt.encoderMove(.5, 8, 2, false, false);

        loop_wrist.run();

        // moving arm to mid junction position
        manip_thd.highPositionPID(-200, 1, .75, .0009, .00001, .001);
        manip_thd.highPositionPID(-200, .7, .75, .0009, .00001, .001);
        manip_thd.highPositionPID(-200, .7, .75, .0009, .00001, .001);

        // hold arm
        manip_thd.moveHArm(.2);
        manip_thd.holdHArm();
        sleep(1000);

        // let go of cone
        loop_close.end();
        manip_thd.setClaw(.5);
        manip_thd.setWrist(0);

        // move arm back up
        manip_thd.highPositionPID(200, 1, .75, .0009, .00001, .001);
        manip_thd.highPositionPID(200, 1, .75, .0009, .00001, .001);



        // park
        dt.encoderMove(.5,3,1,false, false);

        if (sense == 3) {
            dt.encoderMove(.5, 10, 5, true, true);
            //dt.encoderMove(.5, 12, 3, false, false);
        } else if (sense == 2) {
            //dt.encoderMove(.5, 1, 3, true, false);
            //dt.encoderMove(.5, 12, 3, false, false);
        } else {
            dt.encoderMove(.5, 14.5, 3, true, false);
            //dt.encoderMove(.5, 12, 3, false, false);
        }
    }
}
