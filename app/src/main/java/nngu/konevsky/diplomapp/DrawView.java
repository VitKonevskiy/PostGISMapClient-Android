package nngu.konevsky.diplomapp;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.Log;
import android.view.MotionEvent;
import android.view.ScaleGestureDetector;
import android.view.View;

import nngu.konevsky.diplomapp.pojo.Line;

/**
 * Created by User on 08.04.2018.
 */

public class DrawView extends View
{
    private final String LOG_TAG = "MYTAG";

    private float startX;
    private float startY;
    private float amountX;
    private float amountY;
    private ScaleGestureDetector mScaleDetector;
    private float mScaleFactor = 2.f;

    public Paint paint;
    public boolean allowPaint = false;
    public CustomMap drowedMap;

    public DrawView(Context context, CustomMap drowedMap) {
        super(context);
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mScaleDetector = new ScaleGestureDetector(context, new ScaleListener());
        this.drowedMap = drowedMap;
    }

    private class ScaleListener
            extends ScaleGestureDetector.SimpleOnScaleGestureListener {
        @Override
        public boolean onScale(ScaleGestureDetector detector) {
            Log.d("MYTAG", "inOnScale!");
            mScaleFactor *= detector.getScaleFactor();


            // Don't let the object get too small or too large.
            mScaleFactor = Math.max(0.1f, Math.min(mScaleFactor, 5.0f));
            invalidate();
            return true;
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // Let the ScaleGestureDetector inspect all events.
        mScaleDetector.onTouchEvent(event);
        if(event.getActionMasked() == MotionEvent.ACTION_DOWN)
        {
            startX = event.getX();
            startY = event.getY();
        }
        if(event.getActionMasked() == MotionEvent.ACTION_MOVE)
        {
        }
        if(event.getActionMasked() == MotionEvent.ACTION_UP) {
            amountX = event.getX() - startX;
            amountY = event.getY() - startY;
            invalidate();
        }
        return true;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(amountX, amountY);
        paint.setColor(Color.RED);
        canvas.scale(mScaleFactor, mScaleFactor);
        if(allowPaint) {
            drawObjectLine(paint, canvas, drowedMap);
            Log.d(LOG_TAG,"painting Done");
        }
        canvas.restore();
    }

    private void drawObjectLine(Paint paint, Canvas canvas, CustomMap customMap)
    {
        for (Line line: customMap.lines)
            for(int i=0;i<line.points.size()-1;i++)
            {
                canvas.drawLine(
                        line.points.get(i).x - customMap.xStart,
                        line.points.get(i).y - 562900,//customMap.yStart,
                        line.points.get(i+1).x - customMap.xStart,
                        line.points.get(i+1).y - 562900, //customMap.yStart,
                        paint);
            }
    }
}
