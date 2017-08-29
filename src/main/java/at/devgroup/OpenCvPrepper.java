package at.devgroup;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;

/**
 * Created by schuster on 29.08.2017.
 */
public abstract class OpenCvPrepper {

    //------------------------------------------------------------------------------------------------------------------
    // Static initializer...
    {
        // todo: Wie im JAR ansprechen? Einfach im selben Ordner zum JAR dazukopieren?
        String osBit = System.getProperty("sun.arch.data.model");
        if (osBit.equals("64"))
            System.load(new File("src/main/resources/libs/x64/opencv_java330.dll").getAbsolutePath());

        if (osBit.equals("32"))
            System.load(new File("src/main/resources/libs/x32/opencv_java330.dll").getAbsolutePath());
    }
    //------------------------------------------------------------------------------------------------------------------
    protected void writeMatToFile(String outputFilePath, Mat grey) throws IOException {

        byte[] data1 = new byte[grey.rows() * grey.cols() * (int)(grey.elemSize())];
        grey.get(0, 0, data1);
        BufferedImage image1 = new BufferedImage(grey.cols(),grey.rows(), BufferedImage.TYPE_BYTE_GRAY);
        image1.getRaster().setDataElements(0, 0, grey.cols(), grey.rows(), data1);

        File ouptut = new File(outputFilePath);
        ImageIO.write(image1, "png", ouptut);
    }

    //------------------------------------------------------------------------------------------------------------------
    protected Mat readImageIntoMat(String inputFilePath) throws IOException {

        File input = new File(inputFilePath);
        BufferedImage image = ImageIO.read(input);

        byte[] data = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        Mat mat = new Mat(image.getHeight(), image.getWidth(), CvType.CV_8UC3);
        mat.put(0, 0, data);
        return mat;
    }

    //------------------------------------------------------------------------------------------------------------------
}
