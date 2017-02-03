
package ch.rts.mobile.le.jeu.model;

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details implements Parcelable {

    @SerializedName("title")
    @Expose
    private String title;
    @SerializedName("archiveQuery")
    @Expose
    private String archiveQuery;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getArchiveQuery() {
        return archiveQuery;
    }

    public void setArchiveQuery(String archiveQuery) {
        this.archiveQuery = archiveQuery;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.title);
        dest.writeString(this.archiveQuery);
    }

    public Details() {
    }

    protected Details(Parcel in) {
        this.title = in.readString();
        this.archiveQuery = in.readString();
    }

    public static final Parcelable.Creator<Details> CREATOR = new Parcelable.Creator<Details>() {
        @Override
        public Details createFromParcel(Parcel source) {
            return new Details(source);
        }

        @Override
        public Details[] newArray(int size) {
            return new Details[size];
        }
    };
}
