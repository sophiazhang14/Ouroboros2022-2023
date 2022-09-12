package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Lift {
    //Knock Knock
    //Who's there?
    //Why
    //why?
    //Why is Krish so cool and whatnot?
    public DcMotor liftMotor;

    ElapsedTime time = new ElapsedTime();
    private LinearOpMode opMode;
    private static double lifting = 1.0;
    private static double idle = 0;
    public Lift(LinearOpMode opMode) {
        opMode = this.opMode;

        liftMotor = opMode.hardwareMap.dcMotor.get("liftM");

        liftMotor.setDirection(DcMotor.Direction.FORWARD);

        liftMotor.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        liftMotor.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        liftMotor.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }
    public void in(double runTime) {
        time.reset();
        resetEncoders();

        while (time.seconds() < runTime) {
            liftMotor.setPower(lifting);
            liftMotor.setPower(idle);

        }
    }

        public void lift_up()
        {
            liftMotor.setPower(lifting);
        }

        public void lift_off()
        {
            liftMotor.setPower(idle);
        }


    private void resetEncoders() {
    }
}
