package at.devgroup;

import org.opencv.core.Mat;
import org.opencv.core.MatOfDMatch;
import org.opencv.core.MatOfKeyPoint;
import org.opencv.features2d.DescriptorExtractor;
import org.opencv.features2d.DescriptorMatcher;
import org.opencv.features2d.FeatureDetector;
import org.opencv.imgcodecs.Imgcodecs;

public class ImageComparator extends OpenCvPrepper{

    // -----------------------------------------------------------------------------------------------------------------
    public void detectAndSaveInFile(String inputFile1, String inputFile2, String outputFilePath) throws Exception {

        // Process..
        MatOfDMatch differences = doCompare(inputFile1, inputFile2);
        System.out.println(differences);

        // Output...
        // Marking found dots...
/*        Mat out = Imgcodecs.imread(inputFile2, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Imgproc.drawContours(out, differences, -1, new Scalar(255, 255, 255), 2);
        writeMatToFile(outputFilePath, out);*/
    }

    // -----------------------------------------------------------------------------------------------------------------
    // https://stackoverflow.com/questions/15572357/compare-the-similarity-of-two-images-with-opencv
    //
    public MatOfDMatch doCompare(String inputFile1, String inputFile2) throws Exception {

        //Load images to compare
        Mat img1 = Imgcodecs.imread(inputFile1, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);
        Mat img2 = Imgcodecs.imread(inputFile2, Imgcodecs.CV_LOAD_IMAGE_GRAYSCALE);

        MatOfKeyPoint keypoints1 = new MatOfKeyPoint();
        MatOfKeyPoint keypoints2 = new MatOfKeyPoint();
        Mat descriptors1 = new Mat();
        Mat descriptors2 = new Mat();

        //Definition of ORB keypoint detector and descriptor extractors
        FeatureDetector detector = FeatureDetector.create(FeatureDetector.ORB);
        DescriptorExtractor extractor = DescriptorExtractor.create(DescriptorExtractor.ORB);

        //Detect keypoints
        detector.detect(img1, keypoints1);
        detector.detect(img2, keypoints2);
        //Extract descriptors
        extractor.compute(img1, keypoints1, descriptors1);
        extractor.compute(img2, keypoints2, descriptors2);

        //Definition of descriptor matcher
        DescriptorMatcher matcher = DescriptorMatcher.create(DescriptorMatcher.BRUTEFORCE_HAMMING);

        //Match points of two images
        MatOfDMatch matches = new MatOfDMatch();
        matcher.match(descriptors1,descriptors2 ,matches);

        return matches;
    }

    // -----------------------------------------------------------------------------------------------------------------
}
