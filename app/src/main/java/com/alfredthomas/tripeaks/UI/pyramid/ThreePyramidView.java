package com.alfredthomas.tripeaks.UI.pyramid;

import android.content.Context;
import com.alfredthomas.tripeaks.Settings;
import com.alfredthomas.tripeaks.UI.PlayingCardView;

/*
         0        1        2
       3  4     5  6     7  8
      9 10 11 12 13 14 15 16 17
    18 19 20 21 22 23 24 25 26 27
 */
public class ThreePyramidView extends PyramidBase {

    final int[] leftPyramidParent = new int[]{-1,-1,-1,-1,0,-1,1,-1,2,-1,3,4,-1,5,6,-1,7,8,-1,9,10,11,12,13,14,15,16,17};
    final int[] rightPyramidParent = new int[]{-1,-1,-1,0,-1,1,-1,2,-1,3,4,-1,5,6,-1,7,8,-1,9,10,11,12,13,14,15,16,17,-1};
    final int[] pyramidRowSize = new int[]{3,6,9,10};

    final int[] leftDiamondParent = new int[]{-1,-1,-1,-1,0,-1,1,-1,2,-1,3,4,-1,5,6,-1,7,8,9,10,12,13,15,16,18,20,22};
    final int[] rightDiamondParent = new int[]{-1,-1,-1,0,-1,1,-1,2,-1,3,4,-1,5,6,-1,7,8,-1,10,11,13,14,16,17,19,21,23};
    final int[] diamondRowSize = new int[]{3,6,9,6,3};

    public ThreePyramidView(Context context)
    {
        this(context,false);
    }

    public ThreePyramidView(Context context,boolean diamond)
    {
        super(context);
        this.diamond = diamond;
        if (diamond) {
            leftParent = leftDiamondParent;
            rightParent = rightDiamondParent;
            rowSize = diamondRowSize;
        } else {
            leftParent = leftPyramidParent;
            rightParent = rightPyramidParent;
            rowSize = pyramidRowSize;
        }
        peaks = 3;
    }





//    @Override
//    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
//        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
//        int padding = Settings.getPadding();
//        int width = MeasureSpec.getSize(widthMeasureSpec);
//        int height = MeasureSpec.getSize(heightMeasureSpec);
//
//
//
//        int cardWidth = Settings.getCardWidth();
//        int cardHeight = Settings.getCardHeight();
//        int halfWidth = cardWidth/2;
//        int halfHeight = cardHeight/2;
//
//
//        int xoffset = (width-(cardWidth*10)- (padding *2))/2;
//        int y = (height-(cardHeight*2) - halfHeight- (padding *2))/2;
//
//        int halfPadding = 3;
//        int midPadding = 2;
////        int index = 0;
//        for(int i =0; i<4; i++)
//        {
//            int x = xoffset+padding + (halfPadding * halfWidth);
//
//            for(int j =0; j<getRowSize(i); j++)
//            {
//                PlayingCardView view = cards.get(j+getStartIndex(i));
//                measureView(view,x,y,cardWidth,cardHeight);
//                x+=cardWidth;
//                if((j+1)%(i+1) ==0)
//                    x+=(midPadding*cardWidth);
//            }
//            y+=halfHeight;
//            halfPadding--;
//            midPadding= Math.max(midPadding-1,0);
//        }
//
//
//        //layout like a pyramid
//    }
}
