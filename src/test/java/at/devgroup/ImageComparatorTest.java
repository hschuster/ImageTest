package at.devgroup;

import org.junit.Test;

/**
 * Created by schuster on 28.08.2017.
 */
public class ImageComparatorTest {


    //------------------------------------------------------------------------------------------------------------------
    @Test
    public void testDoDetect01() throws Exception {

        ImageComparator ic = new ImageComparator();
        ic.detectAndSaveInFile("src/test/resources/ImageTest01.jpg","src/test/resources/ImageTest02.jpg", "target/DotDetector_Diff.png");
    }

    //------------------------------------------------------------------------------------------------------------------
}
