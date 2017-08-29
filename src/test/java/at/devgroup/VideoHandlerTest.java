package at.devgroup;

import org.junit.Test;

/**
 * Created by schuster on 29.08.2017.
 */
public class VideoHandlerTest {

    //------------------------------------------------------------------------------------------------------------------
    @Test
    public void doRead() throws Exception {
        VideoHandler vh = new VideoHandler();
        vh.doRead("c:/temp/openCvVideoTest.mov");
    }
    //------------------------------------------------------------------------------------------------------------------

}