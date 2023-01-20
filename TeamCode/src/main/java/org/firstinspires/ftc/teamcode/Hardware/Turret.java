package org.firstinspires.ftc.teamcode.Hardware;

import static com.sun.tools.javac.jvm.ByteCodes.error;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;
import com.qualcomm.robotcore.hardware.HardwareMap;
import com.qualcomm.robotcore.util.ElapsedTime;

public class Turret {

    LinearOpMode opMode;
    private DcMotor turret;

    public Turret(LinearOpMode opMode1) {
        opMode = opMode1;

        turret = opMode.hardwareMap.dcMotor.get("turret");

        turret.setDirection(DcMotorSimple.Direction.FORWARD);

        turret.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);
    }

    public void positionPID (double position, double cap, double timeOut, double kP, double kI, double kD) {
        ElapsedTime time = new ElapsedTime();
        double RADIANS_AT_START = 0 * (Math.PI/180); // zero is place holder for the angle in degrees at the starting pos of hArm
        double power = 0;
        double p = 0;
        double i = 0;
        double d = 0;
        double changeT = 0;
        double prevP = 0;
        double prevI = 0;
        double prevD = 0;
        double prevT = 0;
        double pos = 0;
        double lastV = 0;

        resetEncoders();
        double diff = Math.abs(-turret.getCurrentPosition() - position);
        time.reset();
        while (Math.abs(-turret.getCurrentPosition() - position) > 3 && time.seconds() < timeOut){
            opMode.telemetry.addData("p :: ", p *kP);
            opMode.telemetry.addData("i :: ", i * kI);
            opMode.telemetry.addData("d :: ", d * kD);
            opMode.telemetry.addData("time", time.seconds());
            opMode.telemetry.addData("encoder diff", Math.abs(turret.getCurrentPosition()) - position);
            opMode.telemetry.addData("encoder", -turret.getCurrentPosition());
            opMode.telemetry.addData("encoder desired", position);
            opMode.telemetry.addData("power", power);
            opMode.telemetry.update();
            changeT = time.seconds() - prevT;
            p = turret.getCurrentPosition() - position;
            i = prevI + p * changeT;
            d = (p - prevP)/changeT;

            prevP = p;
            prevI = i;
            prevT = prevT - time.seconds();

            power = p * kP + i * kI + d * kD;

            if(Math.abs(p) > 0.1){
                prevI = 0;
            } // why need????

            if (Math.abs(power) > cap){
                power = Math.signum(power) * cap;
            }
            pos = turret.getCurrentPosition() - pos;
            turret.setPower(power);// + feedForward(0, velocity, acceleration, 0, 0, 0, 0));
        }
        turret.setPower(0);
    }

    private void resetEncoders() {
        turret.setMode(DcMotor.RunMode.RESET_ENCODERS);

        turret.setMode(DcMotor.RunMode.RUN_WITHOUT_ENCODER);
    }

    public void turnPID(double goal, boolean isRight, double kP, double kI, double kD, double timeOutMS) {

    }
}
