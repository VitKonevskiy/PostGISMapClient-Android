
package nngu.konevsky.diplomapp.pojo;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Point {

    @SerializedName("X")
    @Expose
    public Integer x;

    @SerializedName("Y")
    @Expose
    public Integer y;

}
