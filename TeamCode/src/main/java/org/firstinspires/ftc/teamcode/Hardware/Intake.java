package org.firstinspires.ftc.teamcode.Hardware;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Intake {


        public DcMotor rightS;
        public DcMotor leftS;

        ElapsedTime time = new ElapsedTime();
        private LinearOpMode opMode;


        private static final double PICKUP = 1.0;
        private static final double IDLE = 0;


        public Intake(LinearOpMode opMode)
        {
            time.reset();
            this.opMode = opMode;
            rightS = opMode.hardwareMap.dcMotor.get("RIn");
            leftS = opMode.hardwareMap.dcMotor.get("LIn");

            rightS.setDirection(DcMotor.Direction.REVERSE);
            leftS.setDirection(DcMotor.Direction.FORWARD);

            rightS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);
            leftS.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.FLOAT);

            rightS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);
            leftS.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

            rightS.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
            leftS.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        }


        //is to be called in OpMode
        public void In (double runTime)
        {
            time.reset();

            while(time.seconds() < runTime){
                rightS.setPower(PICKUP);
                leftS.setPower(PICKUP);
            }

            rightS.setPower(IDLE);
            leftS.setPower(IDLE);

        }

        public void intake_on()
        {
            rightS.setPower(PICKUP);
            leftS.setPower(PICKUP);
        }

        public void intake_off()
        {
            rightS.setPower(IDLE);
            leftS.setPower(IDLE);
        }
        public void intake_reverse()
        {
            rightS.setPower(-PICKUP);
            leftS.setPower(-PICKUP);

        }


    }

