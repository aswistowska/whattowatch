package org.swistowski.agata.whattowatch2;

public class MovieReview implements java.io.Serializable {

    private String mAuthor;
    private String mContent;

    public MovieReview (String author, String content) {

        mAuthor = author;
        mContent = content;
    }

    public String getAuthor() {
        return mAuthor;
    }

    public String getContent() {
        return mContent;
    }

}
