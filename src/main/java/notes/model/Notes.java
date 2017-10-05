package notes.model;

/**
 * заметка
 */
public class Notes {

    private String data;

    private String time;

    private String text;

    public Notes() {
    }

    public Notes(String data, String time, String text) {
        this.data = data;
        this.time = time;
        this.text = text;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }
}
