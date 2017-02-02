
package ch.rts.mobile.le.jeu.model;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Questions {

    @SerializedName("version")
    @Expose
    private String version;
    @SerializedName("name")
    @Expose
    private String name;
    @SerializedName("questions")
    @Expose
    private List<Question> questions = null;

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Question> getQuestions() {
        return questions;
    }

    public void setQuestions(List<Question> questions) {
        this.questions = questions;
    }

}
