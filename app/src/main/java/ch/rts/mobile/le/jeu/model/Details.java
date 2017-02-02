
package ch.rts.mobile.le.jeu.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Details {

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

}
