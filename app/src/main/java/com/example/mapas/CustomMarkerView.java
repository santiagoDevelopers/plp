package com.example.mapas;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.Canvas;
import android.util.DisplayMetrics;
import android.widget.TextView;

import com.github.mikephil.charting.components.MarkerView;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.highlight.Highlight;
import com.github.mikephil.charting.utils.MPPointF;

public class CustomMarkerView extends MarkerView {
    private TextView tvContent;
    int widht;


    public CustomMarkerView (Context context, int layoutResource, int ancho) {
        super(context, layoutResource);
        // this markerview only displays a textview
        widht=ancho;
        tvContent = (TextView) findViewById(R.id.markerText);

    }

    // callbacks everytime the MarkerView is redrawn, can be used to update the
    // content (user-interface)
    @Override
    public void refreshContent(Entry e, Highlight highlight) {

        tvContent.setText((int)e.getY()+", día "+(int)e.getX()); // set the entry-value as the display text
    }

    public int getXOffset(float xpos) {
        // this will center the marker-view horizontally
        return -(getWidth()/2);
    }

    public int getYOffset(float ypos) {
        // this will cause the marker-view to be above the selected value
        return -getHeight();
    }
    @Override
    public void draw(Canvas canvas, float posx, float posy)
    {

        // Check marker position and update offsets.
        int w = getWidth();
        if((widht-posx-w) < w) {
            posx -= w;
        }

        // translate to the correct position and draw
        canvas.translate(posx, posy);
        draw(canvas);
        canvas.translate(-posx, -posy);
    }


}
