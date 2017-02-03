
package ch.rts.mobile.le.jeu.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Question implements Parcelable {

    @SerializedName("text")
    @Expose
    private String text;
    @SerializedName("answers")
    @Expose
    private List<Answer> answers = null;
    @SerializedName("details")
    @Expose
    private Details details;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public Details getDetails() {
        return details;
    }

    public void setDetails(Details details) {
        this.details = details;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.text);
        dest.writeTypedList(this.answers);
        dest.writeParcelable(this.details, flags);
    }

    public Question() {
    }

    protected Question(Parcel in) {
        this.text = in.readString();
        this.answers = in.createTypedArrayList(Answer.CREATOR);
        this.details = in.readParcelable(Details.class.getClassLoader());
    }

    public static final Parcelable.Creator<Question> CREATOR = new Parcelable.Creator<Question>() {
        @Override
        public Question createFromParcel(Parcel source) {
            return new Question(source);
        }

        @Override
        public Question[] newArray(int size) {
            return new Question[size];
        }
    };
}
