package org.firstinspires.ftc.teamcode.Auto;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;

import android.graphics.Bitmap;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.ArrayList;

public class Vision {

    LinearOpMode opMode;
    public static final String vuKey =
            "AdzMYbL/////AAABmflzIV+frU0RltL/ML+2uAZXgJiI" +
            "Werfe92N/AeH7QsWCOQqyKa2G+tUDcgvg8uE8QjHeBZPcpf5hAwlC5qCfvg76eBoaa2b" +
            "MMZ73hmTiHmr9fj3XmF4LWWZtDC6pWTFrzRAUguhlvgnck6Y4jjM16Px5TqgWYuWnpcxNM" +
            "HMyOXdnHLlyysyE64PVzoN7hgMXgbi2K8+pmTXvpV2OeLCag8fAj1Tgdm/kKGr0TX86aQsC2" +
            "RVjToZXr9QyAeyODi4l1KEFmGwxEoteNU8yqNbBGkPXGh/+IIm6/s/KxCJegg8qhxZDgO8110F" +
            "RzwA5a6EltfxAMmtO0G8BB9SSkApxkcSzpyI0k2LxWof2YZG6x4H";
    public VuforiaLocalizer vuforia;

    public Vision(LinearOpMode opMode) {
        this.opMode = opMode;

        // variable allows image to show up on robot controller phone
        int cameraMonitorViewId = opMode.hardwareMap.appContext.getResources()
                .getIdentifier("cameraMonitorViewId", "id",
                        opMode.hardwareMap.appContext.getPackageName());

        VuforiaLocalizer.Parameters parameters = new VuforiaLocalizer.Parameters(cameraMonitorViewId);

        // original vuforia license key
        parameters.vuforiaLicenseKey = vuKey;
        // hardware mapping of webcam device
        parameters.cameraName = opMode.hardwareMap.get(WebcamName.class, "Webcam 1");

        // start vuforia engine
        vuforia = ClassFactory.getInstance().createVuforia(parameters);

        // set RGB format to 565
        Vuforia.setFrameFormat(PIXEL_FORMAT.RGB565, true);

        // allowing the frame to only be 4 images at a time
        vuforia.setFrameQueueCapacity(1);
        opMode.telemetry.addLine("Vision init");
        opMode.telemetry.update();
    }

    public Bitmap getBitmap() throws InterruptedException {
        // method to actually capture frame
        VuforiaLocalizer.CloseableFrame frame = vuforia.getFrameQueue().take();
        Image rgb = frame.getImage(1);

        long numImages = frame.getNumImages();

        // go through all images taken in frame and find ones that match correct format
        for (int i = 0; i < numImages; i++) {
            int fmt = frame.getImage(i).getFormat();

            if (fmt == PIXEL_FORMAT.RGB565) {
                rgb = frame.getImage(i);
                break;

            } else {
                opMode.telemetry.addLine("Didn't find correct rgb format");
                opMode.telemetry.update();

            }

        }

        // create bitmap
        Bitmap bm = Bitmap.createBitmap(rgb.getWidth(), rgb.getHeight(), Bitmap.Config.RGB_565);
        bm.copyPixelsFromBuffer(rgb.getPixels());

        frame.close();

        opMode.telemetry.addLine("Got Bitmap");
        opMode.telemetry.addData("width", rgb.getWidth());
        opMode.telemetry.addData("height", rgb.getHeight());
        opMode.telemetry.update();

        opMode.sleep(500);

        return bm;
    }
    public int senseBlue() throws InterruptedException {
        ArrayList <Integer> r = new ArrayList<>();
        ArrayList <Integer> g = new ArrayList<>();
        ArrayList <Integer> b = new ArrayList<>();
        Bitmap bit = getBitmap();
        int pix = 0;
        double red = 0;
        double green = 0;
        double blue = 0;
        int index = 0;
        for (int x = 0; x < 0; x++){
            for (int y = 0; y < 0; y++){
                pix = bit.getPixel(x, y);
                red += red(pix);
                green += green(pix);
                blue += blue(pix);
                index ++;
                //r.add(red(pix));
                //g.add(green(pix));
                //b.add(blue(pix));
            }
        }
        red /= index;
        green /= index;
        blue /= index;
        /*double red = 0;
        for (int i : r){
            red += i;
        }
        red /= (double) r.size();
        double green = 0;
        for (int i : g){
            green += i;
        }
        green /= (double) g.size();
        double blue = 0;
        for (int i : b){
            blue += i;
        }
        blue /= (double) b.size();*/
        if (red < 30 || blue < 30 || green < 30){
            return 1;
        }
        else if (red(pix) < 0 || green(pix) < 0 || blue(pix) < 0){
            return 2;
        }
        return 3;
    }

    public String visionShriya() throws InterruptedException {
        Bitmap bitmap = getBitmap();
        int pix = bitmap.getPixel(bitmap.getWidth()/2, bitmap.getHeight()/2);

        opMode.telemetry.addData("red", red(pix));
        opMode.telemetry.addData("green", green(pix));
        opMode.telemetry.addData("green", green(pix));
        opMode.telemetry.update();
        opMode.sleep(1000);

        if (red(pix) < 30 && green(pix) < 30 && blue(pix) < 30) {
            return "black";
        } else if (red(pix) < 50 && green(pix) > 200 && blue(pix) < 50) {
            return "green";
        } else if (red(pix) > 200 && green(pix) < 50 && blue(pix) < 50) {
            return "red";
        }

        return "not found";
    }
    public int senseRed() throws InterruptedException {
        // copy paste
        return 0;
    }
}
