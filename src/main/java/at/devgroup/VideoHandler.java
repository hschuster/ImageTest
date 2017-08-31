package at.devgroup;

import org.opencv.core.Mat;
import org.opencv.video.BackgroundSubtractorMOG2;
import org.opencv.video.Video;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by schuster on 29.08.2017.
 */
public class VideoHandler extends OpenCvPrepper {

    //------------------------------------------------------------------------------------------------------------------
    // http://answers.opencv.org/question/66084/using-backgroundsubtractormog2-for-images/
    // https://stackoverflow.com/questions/35170032/implement-background-subtraction-in-opencv-java-for-video-input-from-webcam
    public void applyBackgroundSubtraction(String videoFileName) {

        VideoCapture capture = new VideoCapture(videoFileName);
        if (!capture.open(videoFileName)) {
            System.out.println("Cannot open file: " + videoFileName);
            return;
        }

        BackgroundSubtractorMOG2 backgroundSubtractorMOG = Video.createBackgroundSubtractorMOG2();

        JLabel vidpanel = createScreen();

        while (true) {
            Mat camImage = new Mat();
            capture.read(camImage);

            Mat fgMask = new Mat();
            backgroundSubtractorMOG.apply(camImage, fgMask, 0.1);

            Mat output = new Mat();
            camImage.copyTo(output, fgMask);

            ImageIcon image = new ImageIcon(convertMat2BufferedImage(output));
            vidpanel.setIcon(image);
            vidpanel.repaint();
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    public void doRead(String videoFileName) {

        VideoCapture videoCapture = new VideoCapture(videoFileName);
        if (!videoCapture.open(videoFileName)) {
            System.out.println("Cannot open file: " + videoFileName);
            return;
        }

        JLabel vidpanel = createScreen();

        Mat frame = new Mat();
        while (true) {
            if (videoCapture.read(frame)) {
                ImageIcon image = new ImageIcon(convertMat2BufferedImage(frame));
                vidpanel.setIcon(image);
                vidpanel.repaint();
            } else {
                break;
            }
        }
    }

    //------------------------------------------------------------------------------------------------------------------
    private JLabel createScreen() {
        JFrame jframe = new JFrame("VideoHandler");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel vidpanel = new JLabel();
        jframe.setContentPane(vidpanel);
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
        jframe.setVisible(true);
        return vidpanel;
    }

    //------------------------------------------------------------------------------------------------------------------
    public BufferedImage convertMat2BufferedImage(Mat m) {

        //Method converts a Mat to a Buffered Image
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if (m.channels() > 1) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels() * m.cols() * m.rows();
        byte[] b = new byte[bufferSize];
        m.get(0, 0, b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(), m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
    //------------------------------------------------------------------------------------------------------------------
}
