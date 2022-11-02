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

    public DriveTrain(LinearOpMode opMode){
        this.opMode = opMode;

        br = opMode.hardwareMap.dcMotor.get("br");
        bl = opMode.hardwareMap.dcMotor.get("bl");
        fr = opMode.hardwareMap.dcMotor.get("fr");
        fl = opMode.hardwareMap.dcMotor.get("fl");

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
    public void moveTest(int seconds){
        ElapsedTime time = new ElapsedTime();
        time.reset();
        while(time.seconds() < seconds) {
            br.setPower(.5);
            bl.setPower(.5);
            fr.setPower(.5);
            fl.setPower(.5);
        }
    }
    public void encoderMove(double power, double distance, double runtime, boolean strafe, boolean strafeRight){

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
            if (!strafe) {
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
