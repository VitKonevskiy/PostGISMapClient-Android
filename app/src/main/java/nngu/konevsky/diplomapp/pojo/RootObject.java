
package nngu.konevsky.diplomapp.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class RootObject {

    @SerializedName("Xmin")
    @Expose
    public int xMin;

    @SerializedName("Ymin")
    @Expose
    public int yMin;

    @SerializedName("Id")
    @Expose
    public int id;

    @SerializedName("Id2")
    @Expose
    public int id2;

    @SerializedName("ItemCount")
    @Expose
    public int itemCount;

    @SerializedName("Point")
    @Expose
    public Point point = null;

    @SerializedName("Line")
    @Expose
    public Line line = null;
}
