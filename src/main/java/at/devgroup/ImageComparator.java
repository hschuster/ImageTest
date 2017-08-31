package at.devgroup;

import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;

import static org.opencv.core.Core.absdiff;

public class    ImageComparator extends OpenCvPrepper{

    // -----------------------------------------------------------------------------------------------------------------
    public void detectAndSaveInFile(String inputFile1, String inputFile2, String outputFilePath) throws Exception {



        Mat img1 = Imgcodecs.imread(inputFile1, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Mat img2 = Imgcodecs.imread(inputFile2, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Mat diff = new Mat();
        absdiff(img1, img2, diff);
        Imgcodecs.imwrite(outputFilePath, diff);


    }

    // todo: https://stackoverflow.com/questions/9998195/how-to-find-the-differences-between-frames-using-opencv - Scheint einfacherer Weg zu sein...
    // todo: https://stackoverflow.com/questions/15572357/compare-the-similarity-of-two-images-with-opencv

    // -----------------------------------------------------------------------------------------------------------------
}
