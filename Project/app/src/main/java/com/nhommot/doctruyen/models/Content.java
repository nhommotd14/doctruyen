package com.nhommot.doctruyen.models;

/**
 * Created by Huy on 4/8/2018.
 */

public class Content {
    private int contentId;
    private String chapterId;
    private String src;

    public Content() {
    }

    public Content(int contentId, String chapterId, String src) {
        this.contentId = contentId;
        this.chapterId = chapterId;
        this.src = src;
    }

    public int getContentId() {
        return contentId;
    }

    public void setContentId(int contentId) {
        this.contentId = contentId;
    }

    public String getChapterId() {
        return chapterId;
    }

    public void setChapterId(String chapterId) {
        this.chapterId = chapterId;
    }

    public String getSrc() {
        return src;
    }

    public void setSrc(String src) {
        this.src = src;
    }
}
