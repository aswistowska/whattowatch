package org.swistowski.agata.whattowatch2;

public class MovieVideo implements java.io.Serializable {

    private String mVideoKey;
    private String mVideoName;

    public MovieVideo(String videoKey, String videoName) {
        mVideoKey = videoKey;
        mVideoName = videoName;
    }

    public String getVideoKey() {
        return mVideoKey;
    }

    public String getVideoName() {
        return mVideoName;
    }
}
