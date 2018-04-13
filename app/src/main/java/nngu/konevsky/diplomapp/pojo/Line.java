
package nngu.konevsky.diplomapp.pojo;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Line {

    @SerializedName("Points")
    @Expose
    public List<Point> points = null;
}
