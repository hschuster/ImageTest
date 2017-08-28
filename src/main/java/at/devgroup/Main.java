package at.devgroup;

import org.opencv.core.*;
import org.opencv.imgproc.Imgproc;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class Main {

    {
        // todo: Wie im JAR ansprechen? Einfach im selben Ordner zum JAR dazukopieren?
        System.load(new File("src/main/resources/libs/x64/opencv_java330.dll").getAbsolutePath());
    }

    // -----------------------------------------------------------------------------------------------------------------
    public static void main(String[] args) throws Exception {
        Main main = new Main();
        main.toTest();
    }

    // -----------------------------------------------------------------------------------------------------------------
    private Mat toTest() throws Exception {

        File input = new File("src/test/resources/ImageTest.png");
        BufferedImage image = ImageIO.read(input);

        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);


        Mat grey = new Mat();
        // Make it greyscale
        Imgproc.cvtColor(mat, grey, Imgproc.COLOR_RGBA2GRAY);

        // init contours arraylist
        List<MatOfPoint> contours = new ArrayList<MatOfPoint>(200);

        //blur
        Imgproc.GaussianBlur(grey, grey, new Size(9,9), 10);
        Imgproc.threshold(grey, grey, 80, 150, Imgproc.THRESH_BINARY);


        // because findContours modifies the image I back it up
        Mat greyCopy = new Mat();
        grey.copyTo(greyCopy);


        Imgproc.findContours(greyCopy, contours, new Mat(), Imgproc.RETR_TREE, Imgproc.CHAIN_APPROX_NONE);
        // Now I have my controus pefectly

        //a list for only selected contours
        List<MatOfPoint> SelectedContours = new ArrayList<MatOfPoint>(400);

        for(int i=0;i<contours.size();i++)
        {
            SelectedContours.add(contours.get(i));
        }

        Imgproc.drawContours(grey, SelectedContours, -1, new Scalar(255,0,0,255), 1);



        byte[] data1 = new byte[grey.rows() * grey.cols() * (int)(grey.elemSize())];
        grey.get(0, 0, data1);
        BufferedImage image1 = new BufferedImage(grey.cols(),grey.rows(), BufferedImage.TYPE_BYTE_GRAY);
        image1.getRaster().setDataElements(0, 0, grey.cols(), grey.rows(), data1);

        File ouptut = new File("target/ImageTest_GreyScale.jpg");
        ImageIO.write(image1, "jpg", ouptut);
        
        
        return grey;
    }

    // -----------------------------------------------------------------------------------------------------------------
}
