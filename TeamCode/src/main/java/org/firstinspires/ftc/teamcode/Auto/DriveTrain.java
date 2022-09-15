package org.firstinspires.ftc.teamcode.Auto;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.util.ElapsedTime;

public class DriveTrain {
    //sophia
    public DcMotor br;
    public DcMotor bl;
    public DcMotor fr;
    public DcMotor fl;

    public LinearOpMode opMode;

    public DriveTrain(){
        this.opMode = opMode;

        br = opMode.hardwareMap.dcMotor.get("br");
        bl = opMode.hardwareMap.dcMotor.get("bl");
        fr = opMode.hardwareMap.dcMotor.get("fr");
        fl = opMode.hardwareMap.dcMotor.get("fl");

        // how to know what direction and power to reset to? - sophia
    }
    public void resetEncoders(){
        //how to set power and direction? - sophia
    }
    public void encoderMove(double power, double distance, boolean direction, double runtime){

        ElapsedTime time = new ElapsedTime();

        // reset encoders, explain what happen if don't - sophia
        resetEncoders();
        time.reset();

        double dis = 0;

        while (time.seconds() < runtime && opMode.opModeIsActive() && dis < distance * (Math.PI/90)){
            dis = (bl.getCurrentPosition() + br.getCurrentPosition() + fl.getCurrentPosition() + fr.getCurrentPosition())/4;
            if (!opMode.opModeIsActive())    {
                kill();
            }
            if (direction) {
                br.setPower(power);
                bl.setPower(power);
                fr.setPower(power);
                fl.setPower(power);
            }
            else{
                br.setPower(-power);
                bl.setPower(-power);
                fr.setPower(-power);
                fl.setPower(-power);
            }
        }
        kill();
    }

    public void turn(){

    }

    public void vision(){

    }

    public void kill(){
        br.setPower(0);
        bl.setPower(0);
        fr.setPower(0);
        fl.setPower(0);
    }
}
