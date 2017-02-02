
package ch.rts.mobile.le.jeu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("playUrn")
    @Expose
    private String playurn;
    @SerializedName("image")
    @Expose
    private String image;
    @SerializedName("isCorrect")
    @Expose
    private Boolean isCorrect;
    @SerializedName("title")
    @Expose
    private String title;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getPlayurn() {
        return playurn;
    }

    public void setPlayurn(String playurn) {
        this.playurn = playurn;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Boolean getIsCorrect() {
        return isCorrect;
    }

    public void setIsCorrect(Boolean isCorrect) {
        this.isCorrect = isCorrect;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

}
