package nhommot.com.firebase.model;

/**
 * Created by Nguyen Dung on 4/15/2018.
 */

public class Comment {
    private String id;
    private String content;

    public Comment() {
    }

    public Comment(String id, String content) {
        this.id = id;
        this.content = content;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
