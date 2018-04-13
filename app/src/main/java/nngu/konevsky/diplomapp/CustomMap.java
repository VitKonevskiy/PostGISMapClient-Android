package nngu.konevsky.diplomapp;

import java.util.ArrayList;
import java.util.List;

import nngu.konevsky.diplomapp.pojo.Line;
import nngu.konevsky.diplomapp.pojo.Point;

/**
 * Created by User on 08.04.2018.
 */

public class CustomMap {
    public int xStart;
    public int yStart;
    public List<Point> points;
    public List<Line> lines;

    public CustomMap()
    {
        xStart = 0;
        yStart = 0;
        points = new ArrayList<Point>();
        lines = new ArrayList<Line>();
    }

    public void clear()
    {
        if (lines!=null)
            lines.clear();
        if (points!=null)
            points.clear();
    }
}
