package org.firstinspires.ftc.teamcode.Auto;

import static android.graphics.Color.blue;
import static android.graphics.Color.green;
import static android.graphics.Color.red;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;

import com.qualcomm.robotcore.eventloop.opmode.LinearOpMode;
import com.qualcomm.robotcore.hardware.Servo;
import com.vuforia.Image;
import com.vuforia.PIXEL_FORMAT;
import com.vuforia.Vuforia;

import org.firstinspires.ftc.robotcore.external.ClassFactory;
import org.firstinspires.ftc.robotcore.external.hardware.camera.WebcamName;
import org.firstinspires.ftc.robotcore.external.navigation.VuforiaLocalizer;

import java.util.ArrayList;
import java.util.ServiceConfigurationError;

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
        //opMode.telemetry.addData("stop position: ",stop.getPosition());
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

        //opMode.telemetry.addLine("Got Bitmap");
        //opMode.telemetry.addData("width", rgb.getWidth());
        //opMode.telemetry.addData("height", rgb.getHeight());
        //opMode.telemetry.update();

        //opMode.sleep(500);

        return bm;
    }

    public Bitmap toGrayscale(Bitmap bmpOriginal)
    {
        int width, height;
        height = bmpOriginal.getHeight();
        width = bmpOriginal.getWidth();

        Bitmap bmpGrayscale = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);
        Canvas c = new Canvas(bmpGrayscale);
        Paint paint = new Paint();
        ColorMatrix cm = new ColorMatrix();
        cm.setSaturation(0);
        ColorMatrixColorFilter f = new ColorMatrixColorFilter(cm);
        paint.setColorFilter(f);
        c.drawBitmap(bmpOriginal, 0, 0, paint);
        return bmpGrayscale;
    }

    public int senseBlueLeft() throws InterruptedException {
        // finger and a half/finger width away from bar
        int location = 1;
        ArrayList<Integer> elementY = new ArrayList<>();
        Bitmap bitmap = getBitmap();

        // top left = (0,0)
        elementY.add(1);
        for (int y = 0; y < bitmap.getHeight(); y++) {
            for (int x = 50; x < bitmap.getWidth(); x++) {
                int pixel = bitmap.getPixel(x, y);
                if (red(pixel) < 30 && green(pixel) < 30 && blue(pixel) < 30) {
                    elementY.add(y);
                }
            }
        }
        int yAverage = 0;
        int total = 0;
        for (int i : elementY) {
            total += i;
        }
        yAverage = total/elementY.size();

        if (yAverage < 40) {
            opMode.telemetry.addData("return", "one");
            opMode.telemetry.update();
            opMode.sleep(2000);
            location = 1;
        } else if (yAverage < 90 && yAverage > 40) {
            opMode.telemetry.addData("return", "two");
            opMode.telemetry.update();
            opMode.sleep(2000);
            location = 2;
        } else if (yAverage > 90) {
            opMode.telemetry.addData("return", "three");
            opMode.telemetry.update();
            opMode.sleep(2000);
            location = 3;
        }

        //opMode.telemetry.addData("Position", location);
        //opMode.telemetry.addData("y avg", yAverage);
        //opMode.telemetry.update();

        opMode.sleep(1000);

        return location;
    }
    public String senseBlue() throws InterruptedException {
    //You find the X using a for loop, we do the same for Y. You keep af
        Bitmap bit = toGrayscale(getBitmap());
        int pix = 0;
        //double red = 0;
        //double green = 0;
        //double blue = 0;
        //int index = 0;
        // x = 160-180
        // 640 width
        // 480 height
        int addY = 0;
        int index = 0;
        for (int x = 0; x < bit.getWidth(); x++) {
            for (int y = 0; y < bit.getHeight(); y++) {
                pix = bit.getPixel(x, y);
                //red += red(pix);
                //green += green(pix);
                //blue += blue(pix);
                //index++;
                //opMode.telemetry.addData("red", red(pix));
                //opMode.telemetry.addData("blue", blue(pix));
                //opMode.telemetry.update();
                //opMode.sleep(10);
                if (red(pix) < 70 && blue(pix) < 70 && green(pix) < 70) {
                    //opMode.telemetry.addData("red", red(pix));
                    //opMode.telemetry.addData("blue", blue(pix));
                    opMode.telemetry.addData("x position ", x);
                    opMode.telemetry.addData("y position ", y);
                    opMode.telemetry.update();
                    opMode.sleep(10);
                    addY += y;
                    index++;
                }
            }
            if(index == 0){

                return "one";
            }
            addY = addY/index;
            opMode.telemetry.addData("addY", addY);
            opMode.telemetry.update();
            opMode.sleep(2000);
            if(addY < 20){
                    opMode.telemetry.addData("return", "two");
                    opMode.telemetry.update();
                    opMode.sleep(2000);
                    return "two";
                }
            else if(addY < 75){
                    opMode.telemetry.addData("return", "three");
                    opMode.telemetry.update();
                    opMode.sleep(2000);
                    return "three";
                }
            else {
                    opMode.telemetry.addData("return", "one");
                    opMode.telemetry.update();
                    opMode.sleep(2000);
                    return "one";
                }
        }
        //red /= index;
        //green /= index;
        //blue /= index;
        //This code checks which parking space to go to by reading the y-coordinate of the black pixels.
        opMode.telemetry.addLine("you failed! Press the init button to try again!");
        opMode.telemetry.update();

        return "three";
    }
    /*public String visionShriya() throws InterruptedException {
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
    } */
}
