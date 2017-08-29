package at.devgroup;

import org.opencv.core.Mat;
import org.opencv.videoio.VideoCapture;

import javax.swing.*;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;

/**
 * Created by schuster on 29.08.2017.
 */
public class VideoHandler extends OpenCvPrepper {

    //------------------------------------------------------------------------------------------------------------------
    public void doRead(String videoFileName) {

        VideoCapture videoCapture = new VideoCapture(videoFileName);
        if( ! videoCapture.open(videoFileName)) {
            System.out.println("Cannot open file: " + videoFileName);
            return;
        }

        JFrame jframe = new JFrame("VideoHandler");
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        JLabel vidpanel = new JLabel();
        jframe.setContentPane(vidpanel);
        jframe.setExtendedState(JFrame.MAXIMIZED_BOTH);
/*
        jframe.setSize(800, 600);
*/
        jframe.setVisible(true);

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
    public BufferedImage convertMat2BufferedImage(Mat m) {

        //Method converts a Mat to a Buffered Image
        int type = BufferedImage.TYPE_BYTE_GRAY;
        if ( m.channels() > 1 ) {
            type = BufferedImage.TYPE_3BYTE_BGR;
        }
        int bufferSize = m.channels()*m.cols()*m.rows();
        byte [] b = new byte[bufferSize];
        m.get(0,0,b); // get all the pixels
        BufferedImage image = new BufferedImage(m.cols(),m.rows(), type);
        final byte[] targetPixels = ((DataBufferByte) image.getRaster().getDataBuffer()).getData();
        System.arraycopy(b, 0, targetPixels, 0, b.length);
        return image;
    }
    //------------------------------------------------------------------------------------------------------------------
}
