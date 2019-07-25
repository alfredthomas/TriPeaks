package com.alfredthomas.tripeaks.UI;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

public class ImprovedView extends ViewGroup
{
    public ImprovedView(Context context)
    {
        super(context);
    }

    public ImprovedView(Context context, AttributeSet attrs)
    {
        super(context, attrs);
    }

    public ImprovedView(Context context, AttributeSet attrs, int defStyle)
    {
        super(context, attrs, defStyle);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        this.layoutChildren();
    }

    public void layoutChildren()
    {
        int count = this.getChildCount();

        for (int i = 0; i < count; i++)
        {
            View child = this.getChildAt(i);

            layoutView(child);
        }
    }

    public void layoutView(View view)
    {
        LayoutParams params = (LayoutParams) view.getLayoutParams();
        view.layout(params.left, params.top, params.right, params.bottom);
    }

    public class LayoutParams extends ViewGroup.LayoutParams
    {
        public int left, right, top, bottom;

        public LayoutParams(int width, int height)
        {
            super(width, height);
        }

        public LayoutParams()
        {
            super(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        }
    }

    private void updateLayoutParams(View child, int left, int top, int width, int height)
    {
        LayoutParams params = (LayoutParams) child.getLayoutParams();

        params.height = height;
        params.width = width;
        params.bottom = top + height;
        params.right = left + width;
        params.left = left;
        params.top = top;
    }

    public void measureView(View child)
    {
        LayoutParams params = (LayoutParams) child.getLayoutParams();

        child.measure(MeasureSpec.makeMeasureSpec(params.width, MeasureSpec.EXACTLY), MeasureSpec.makeMeasureSpec(params.height, MeasureSpec.EXACTLY));
    }

    public void measureView(View child, int left, int top, int width, int height)
    {
        this.updateLayoutParams(child, left, top, width, height);
        this.measureView(child);
    }

    @Override
    protected boolean checkLayoutParams(ViewGroup.LayoutParams p)
    {
        return p instanceof LayoutParams;
    }

    @Override
    protected LayoutParams generateDefaultLayoutParams()
    {
        return new LayoutParams();
    }

    @Override
    protected LayoutParams generateLayoutParams(ViewGroup.LayoutParams p)
    {
        return new LayoutParams(p.width, p.height);
    }


}