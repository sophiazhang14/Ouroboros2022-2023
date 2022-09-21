package org.firstinspires.ftc.teamcode.Hardware;

public class Turret {
    public void turnPID (double goal, boolean isRight, double kP, double kI, double kD, double timeOutMS) {

        runtime.reset();
        sensors.angles = sensors.gyro.getAngularOrientation();

        while (opMode.opModeIsActive() && runtime.milliseconds() <= timeOutMS && goal - sensors.getGyroYaw() > 1 ) {

            sensors.angles = sensors.gyro.getAngularOrientation();

            error = goal - sensors.getGyroYaw();
            proportional = error * kP;
            time = runtime.milliseconds();
            integral += ((time - prevTime) * error) * kI;
            derive = ((error - prevError) / (time - prevTime)) * kD;
            power = proportional + integral + derive;

            turn(power, isRight);

            prevTime = runtime.milliseconds();
            prevError = goal - sensors.getGyroYaw();


        }
}
