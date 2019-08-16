package com.alfredthomas.tripeaks.UI.games;

import android.content.Context;
import com.alfredthomas.tripeaks.Settings;
import com.alfredthomas.tripeaks.UI.DashboardView;

public class HourGlassView extends MirroredBase {
    public HourGlassView(Context context) {
        super(context);
    }

    @Override
    public void updateScore(int index) {
        ((DashboardView)this.getParent()).cardCleared();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);

        int cardWidth = Settings.getCardWidth();
        int halfCardWidth = cardWidth/2;
        int cardHeight = Settings.getCardHeight();
        int halfCardHeight = cardHeight/2;

        int padding = Settings.getPadding();
        int y = getYOffset(heightMeasureSpec);

        for(int i = 0; i<rowSize.length;i++)
        {
            int rowOffset = (halfCardWidth * Math.min(i,rowSize.length-1-i));
            int x = padding + getXOffset(widthMeasureSpec)+ rowOffset;

            for(int j = getStartIndex(i); j<(rowSize[i]+getStartIndex(i));j++) {

                //accounting for peak
                if(j!=getStartIndex(i) && peaks>1 && getRowPos(j)%(rowSize[i]/peaks)==0 &&  Math.abs(getMid()-i)<=1 )
                    x+=(2-Math.abs(getMid()-i))*cardWidth;

                measureView(cards.get(j),x,y,cardWidth,cardHeight);
                x+=cardWidth;
            }
            y+=halfCardHeight;
        }
    }

}
