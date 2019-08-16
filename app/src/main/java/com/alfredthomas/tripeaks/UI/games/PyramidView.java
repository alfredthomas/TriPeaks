package com.alfredthomas.tripeaks.UI.games;

import android.content.Context;
import com.alfredthomas.tripeaks.Settings;

public class PyramidView extends GameBase{


    public PyramidView(Context context)
    {
        super(context);
    }

    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);



        int cardWidth = Settings.getCardWidth();
        int halfCardWidth = cardWidth/2;
        int cardHeight = Settings.getCardHeight();
        int halfCardHeight = cardHeight/2;

        int padding = Settings.getPadding();
        int y = getYOffset(heightMeasureSpec);

        for(int i = 0; i<rowSize.length;i++)
        {
            int rowOffset = (halfCardWidth * (rowSize.length - 1 - i));
            int x = padding + getXOffset(widthMeasureSpec)+ rowOffset;

            for(int j = getStartIndex(i); j<(rowSize[i]+getStartIndex(i));j++) {

                //accounting for peak
                if(j!=getStartIndex(i) && peaks>1 && getRowPos(j)%(rowSize[i]/peaks)==0 &&  i<2 )
                    x+=Math.abs(2-i)*cardWidth;

                measureView(cards.get(j),x,y,cardWidth,cardHeight);
                x+=cardWidth;
            }
            y+=halfCardHeight;
        }



    }

}
