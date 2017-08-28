package at.devgroup;

import org.junit.Test;

/**
 * Created by schuster on 28.08.2017.
 */
public class DotDetectorTest {


    //------------------------------------------------------------------------------------------------------------------
    @Test
    public void testDoDetect() throws Exception {

        DotDetector dotDetector = new DotDetector();
        dotDetector.detectAndSaveInFile("src/test/resources/ImageTest.png", "target/ImageTest_GreyScale.png");
    }

    //------------------------------------------------------------------------------------------------------------------
}
