package at.devgroup;

import org.opencv.core.Mat;
import org.opencv.core.MatOfPoint;
import org.opencv.core.Scalar;
import org.opencv.core.Size;
import org.opencv.imgproc.Imgproc;

import java.util.ArrayList;
import java.util.List;

public class DotDetector extends OpenCvPrepper {

    //------------------------------------------------------------------------------------------------------------------
    public void detectAndSaveInFile(String inputFilePath, String outputFilePath) throws Exception {

        // Input...
        Mat inpMat = readImageIntoMat(inputFilePath);

        // Process..
        Mat greyMat = doDetect(inpMat);

        // Output...
        writeMatToFile(outputFilePath, greyMat);
    }

    //------------------------------------------------------------------------------------------------------------------
    // https://stackoverflow.com/questions/14058366/opencv-dominoes-circular-spots-disks-detection
    //
    public Mat doDetect(Mat inpMat) throws Exception {

        // Convert to grey scale...
        Mat greyMat = new Mat();
        Imgproc.cvtColor(inpMat, greyMat, Imgproc.COLOR_RGBA2GRAY);

        // Blur...
        Imgproc.GaussianBlur(greyMat, greyMat, new Size(9,9), 10);
        Imgproc.threshold(greyMat, greyMat, 80, 150, Imgproc.THRESH_BINARY);

        List<MatOfPoint> foundContours = new ArrayList<>();
        List<MatOfPoint> selectedContours = new ArrayList<>();

        // Backup because findContours modifies the image...
        Mat greyCopy = new Mat();
        greyMat.copyTo(greyCopy);
        Imgproc.findContours(greyCopy, foundContours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);

        for(MatOfPoint mop : foundContours) {
            System.out.println("Found contour: " + mop);
            // Bild selbst wird auch als Kontur erkannt. Daher filtern.
            // Willkürlicher Wert. Im Testimage haben alle Dots 16 Rows...
            if(mop.rows() < 32) {
                selectedContours.add(mop);
            }
        }

        // Marking found dots...
        Imgproc.drawContours(greyMat, selectedContours, -1, new Scalar(255, 255, 255), 2);

        return greyMat;
    }

    //------------------------------------------------------------------------------------------------------------------
}
