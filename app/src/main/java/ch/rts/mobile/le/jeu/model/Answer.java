
package ch.rts.mobile.le.jeu.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Answer implements Parcelable {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("playUrn")
    @Expose
    private String playUrn;
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

    public String getPlayUrn() {
        return playUrn;
    }

    public void setPlayUrn(String playUrn) {
        this.playUrn = playUrn;
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeString(this.playUrn);
        dest.writeValue(this.isCorrect);
        dest.writeString(this.title);
    }

    public Answer() {
    }

    protected Answer(Parcel in) {
        this.text = in.readString();
        this.playUrn = in.readString();
        this.isCorrect = (Boolean) in.readValue(Boolean.class.getClassLoader());
        this.title = in.readString();
    }

    public static final Parcelable.Creator<Answer> CREATOR = new Parcelable.Creator<Answer>() {
        @Override
        public Answer createFromParcel(Parcel source) {
            return new Answer(source);
        }

        @Override
        public Answer[] newArray(int size) {
            return new Answer[size];
        }
    };
}
