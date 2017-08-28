package at.devgroup;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class DotDetector {

    {
        // todo: Wie im JAR ansprechen? Einfach im selben Ordner zum JAR dazukopieren?
        System.load(new File("src/main/resources/libs/x64/opencv_java330.dll").getAbsolutePath());
    }

    // -----------------------------------------------------------------------------------------------------------------
    public void detectAndSaveInFile(String inputFilePath, String outputFilePath) throws Exception {

        // Input...
        Mat inpMat = readImageIntoMat(inputFilePath);

        // Process..
        Mat greyMat = doDetect(inpMat);

        // Output...
        writeMatToFile(outputFilePath, greyMat);
    }

    // -----------------------------------------------------------------------------------------------------------------
    // https://stackoverflow.com/questions/14058366/opencv-dominoes-circular-spots-disks-detection
    //
    public Mat doDetect(Mat inpMat) throws Exception {

        // Convert to grey scale...
        Mat greyMat = new Mat();
        Imgproc.cvtColor(inpMat, greyMat, Imgproc.COLOR_RGBA2GRAY);

        //blur...
        Imgproc.GaussianBlur(greyMat, greyMat, new Size(9,9), 10);
        Imgproc.threshold(greyMat, greyMat, 80, 150, Imgproc.THRESH_BINARY);

        List<MatOfPoint> contours = new ArrayList<MatOfPoint>();

        // Backup because findContours modifies the image...
        Mat greyCopy = new Mat();
        greyMat.copyTo(greyCopy);
        Imgproc.findContours(greyCopy, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);

        // Marking found dots...
        Imgproc.drawContours(greyMat, contours, -1, new Scalar(255,0,0,255), 1);

        return greyMat;
    }

    // -----------------------------------------------------------------------------------------------------------------
    private void writeMatToFile(String outputFilePath, Mat grey) throws IOException {

        byte[] data1 = new byte[grey.rows() * grey.cols() * (int)(grey.elemSize())];
        grey.get(0, 0, data1);
        BufferedImage image1 = new BufferedImage(grey.cols(),grey.rows(), BufferedImage.TYPE_BYTE_GRAY);
        image1.getRaster().setDataElements(0, 0, grey.cols(), grey.rows(), data1);

        File ouptut = new File(outputFilePath);
        ImageIO.write(image1, "png", ouptut);
    }

    // -----------------------------------------------------------------------------------------------------------------
    private Mat readImageIntoMat(String inputFilePath) throws IOException {

        File input = new File(inputFilePath);
        BufferedImage image = ImageIO.read(input);

        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }

    // -----------------------------------------------------------------------------------------------------------------
}
