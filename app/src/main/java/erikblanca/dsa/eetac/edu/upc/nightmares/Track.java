package erikblanca.dsa.eetac.edu.upc.nightmares;

/**
 * Created by erikb on 1/13/2019.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class Track {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("singer")
    @Expose
    private String singer;
    @SerializedName("title")
    @Expose
    private String title;

    public Track(Integer id, String singer, String title) {
        this.id = id;
        this.singer = singer;
        this.title = title;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSinger() {
        return singer;
    }

    public void setSinger(String singer) {
        this.singer = singer;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


}
