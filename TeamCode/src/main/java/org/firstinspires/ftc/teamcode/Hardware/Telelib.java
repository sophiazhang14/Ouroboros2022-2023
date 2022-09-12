package org.firstinspires.ftc.teamcode.Hardware;

import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad1;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.gamepad2;
import static org.firstinspires.ftc.robotcore.external.BlocksOpModeCompanion.hardwareMap;

import com.qualcomm.robotcore.hardware.DcMotor;
import com.qualcomm.robotcore.hardware.DcMotorSimple;

public class Telelib {
    // hannah: intake
    // krish: lift
    // shriya: claw
    public DcMotor in;

    public Telelib() {
        in = hardwareMap.dcMotor.get("in");

        in.setZeroPowerBehavior(DcMotor.ZeroPowerBehavior.BRAKE);

        in.setDirection(DcMotorSimple.Direction.FORWARD);

    }

    public void intake() {
        boolean r_bumper = gamepad2.right_bumper;
        boolean l_bumper = gamepad2.left_bumper;
        if (r_bumper) {
            in.setPower(1);
        } else if (l_bumper) {
            in.setPower(-1);
        } else {
            in.setPower(0);

        }
    }
}