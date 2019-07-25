package com.alfredthomas.tripeaks.UI.pyramid;

import android.content.Context;

public class FourPyramidView extends PyramidBase {
    final int[] leftPyramidParent = new int[]{-1,-1,-1,-1,-1,0,-1,1,-1,2,-1,3,-1,4,5,-1,6,7,-1,8,9,-1,10,11,-1,12,13,14,15,16,17,18,19,20,21,22,23};
    final int[] rightPyramidParent = new int[] {-1,-1,-1,-1,0,-1,1,-1,2,-1,3,-1,4,5,-1,6,7,-1,8,9,-1,10,11,-1,12,13,14,15,16,17,18,19,20,21,22,23,-1};
    final int[] pyramidRowSize =new int[]{4,8,12,13};
    final int[] leftDiamondParent = new int[]{-1,-1,-1,-1,-1,0,-1,1,-1,2,-1,3,-1,4,5,-1,6,7,-1,8,9,-1,10,11,12,13,15,16,18,19,21,22,24,26,28,30};
    final int[] rightDiamondParent = new int[]{-1,-1,-1,-1,0,-1,1,-1,2,-1,3,-1,4,5,-1,6,7,-1,8,9,-1,10,11,-1,13,14,16,17,19,20,22,23,25,27,29,31};
    final int[] diamondRowSize = new int[]{4,8,12,8,4};

    public FourPyramidView(Context context)
    {
        this(context,false);
    }
    public FourPyramidView(Context context, boolean diamond)
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
        peaks = 4;

    }
}
