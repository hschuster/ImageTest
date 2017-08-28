package at.devgroup;

import org.junit.Test;

/**
 * Created by schuster on 28.08.2017.
 */
public class DotDetectorTest {


    //------------------------------------------------------------------------------------------------------------------
    @Test
    public void testDoDetect01() throws Exception {

        DotDetector dotDetector = new DotDetector();

        dotDetector.detectAndSaveInFile("src/test/resources/ImageTest01.jpg", "target/ImageTest01_Detected.png");
        dotDetector.detectAndSaveInFile("src/test/resources/ImageTest02.jpg", "target/ImageTest02_Detected.png");
    }

    //------------------------------------------------------------------------------------------------------------------
}
