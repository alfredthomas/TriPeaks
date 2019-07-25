package com.alfredthomas.tripeaks.UI.pyramid;

import android.content.Context;

public class TwoPyramidView extends PyramidBase {
    final int[] leftPyramidParent = new int[]{-1,-1,-1,0,-1,1,-1,2,3,-1,4,5,-1,6,7,8,9,10,11,-1,12,13,14,15,16,17,18};
    final int[] rightPyramidParent = new int[]{-1,-1,0,-1,1,-1,2,3,-1,4,5,-1,6,7,8,9,10,11,-1,12,13,14,15,16,17,18,-1};
    final int[] pyramidRowSize = new int[]{2,4,6,7,8};

    final int[] leftDiamondParent = new int[]{-1,-1,-1,0,-1,1,-1,2,3,-1,4,5,-1,6,7,8,9,10,11,12,13,14,15,16,17,19,20,22,23,25,27};
    final int[] rightDiamondParent = new int[]{-1,-1,0,-1,1,-1,2,3,-1,4,5,-1,6,7,8,9,10,11,-1,13,14,15,16,17,18,20,21,23,24,26,28};
    final int[] diamondRowSize = new int[]{2,4,6,7,6,4,2};

    public TwoPyramidView (Context context)
    {
        this(context,false);
    }

    public TwoPyramidView(Context context,boolean diamond)
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
        this.peaks =  2;
    }
}
