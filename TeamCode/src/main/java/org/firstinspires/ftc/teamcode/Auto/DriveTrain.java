package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.Servo;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DriveTrain {
    //sophia
    public DcMotor br;
    public DcMotor bl;
    public DcMotor fr;
    public DcMotor fl;
    public Servo stop;

    public LinearOpMode opMode;

    public DriveTrain(LinearOpMode opMode){
        this.opMode = opMode;

        br = opMode.hardwareMap.dcMotor.get("br");
        bl = opMode.hardwareMap.dcMotor.get("bl");
        fr = opMode.hardwareMap.dcMotor.get("fr");
        fl = opMode.hardwareMap.dcMotor.get("fl");


        fr.setDirection(DcMotorSimple.Direction.FORWARD);
        fl.setDirection(DcMotorSimple.Direction.REVERSE);
        br.setDirection(DcMotorSimple.Direction.FORWARD);
        bl.setDirection(DcMotorSimple.Direction.REVERSE);

        fr.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        fl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        br.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        bl.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
        // how to know what direction and power to reset to? - sophia
    }
    public void resetEncoders(){
        fl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fr.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        bl.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        br.setMode(DcMotor.RunMode.STOP_AND_RESET_ENCODER);

        fl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        fr.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        bl.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);

        br.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
        //how to set power and direction? - sophia
    }

    public double getStrafeEncoders(){
        return Math.abs(-br.getCurrentPosition() + (-fl.getCurrentPosition()) + bl.getCurrentPosition() + fr.getCurrentPosition())/4;
    }
    public double getEncoders(){
        return Math.abs((bl.getCurrentPosition() + br.getCurrentPosition() + fl.getCurrentPosition() + fr.getCurrentPosition())/4);
    }

    public void encoderMove(double power, double distance, double runtime, boolean strafe, boolean strafeRight){

        ElapsedTime time = new ElapsedTime();

        // reset encoders, explain what happen if don't - sophia
        resetEncoders();
        time.reset();

        double dis = 0;
        while (time.seconds() < runtime && opMode.opModeIsActive() && dis < distance * 105){
            if(strafe){
                dis = getStrafeEncoders();
            }
            else{
                dis = getEncoders();
            }
            if (!opMode.opModeIsActive()){
                kill();
            }
            if (!strafe && !strafeRight) {
                br.setPower(power);
                bl.setPower(power);
                fr.setPower(power);
                fl.setPower(power);
            }
            else{
                if(strafeRight){
                    br.setPower(power);
                    bl.setPower(-power);
                    fr.setPower(-power);
                    fl.setPower(power);
                }
                else{
                    br.setPower(-power);
                    bl.setPower(power);
                    fr.setPower(power);
                    fl.setPower(-power);
                }
            }
        }
        kill();
        opMode.telemetry.addData("distance", dis);
        opMode.telemetry.update();
        opMode.sleep(6000);

    }
    public void turnPID(){

    }

    public void turn(){

    }

    public void kill(){
        br.setPower(0);
        bl.setPower(0);
        fr.setPower(0);
        fl.setPower(0);
    }
}
