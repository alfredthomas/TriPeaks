package com.alfredthomas.tripeaks.UI.pyramid;

import android.content.Context;

public class OnePyramidView extends PyramidBase {
    final int[] leftPyramidParent = new int[]{-1,-1,0,-1,1,2,-1,3,4,5,-1,6,7,8,9,-1,10,11,12,13,14,-1,15,16,17,18,19,20};
    final int[] rightPyramidParent = new int[]{-1,0,-1,1,2,-1,3,4,5,-1,6,7,8,9,-1,10,11,12,13,14,-1,15,16,17,18,19,20,-1};
    final int[] pyramidRowSize =  new int[]{1,2,3,4,5,6,7};

    final int[] leftDiamondParent = new int[]{-1,-1,0,-1,1,2,-1,3,4,5,-1,6,7,8,9,10,11,12,13,15,16,17,19,20,22};
    final int[] rightDiamondParent = new int[]{-1,0,-1,1,2,-1,3,4,5,-1,6,7,8,9,-1,11,12,13,14,16,17,18,20,21,23};
    final int[] diamondRowSize = new int[]{1,2,3,4,5,4,3,2,1};

    public OnePyramidView(Context context)
    {
        this(context,false);

    }

    public OnePyramidView(Context context, boolean diamond) {
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
        peaks = 1;
    }
}
