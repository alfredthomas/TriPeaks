package com.alfredthomas.tripeaks.UI;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.GradientDrawable;
import android.view.View;
import com.alfredthomas.tripeaks.R;
import com.alfredthomas.tripeaks.Settings;
import com.alfredthomas.tripeaks.card.Card;

public class PlayingCardView extends View {
    Card card;
    boolean faceUp = false;
    boolean hidden = false;
    Paint paint = new Paint();
    GradientDrawable gd = new GradientDrawable();

    public PlayingCardView(Context context){
        super(context);
    }
    public PlayingCardView(Context context, Card card)
    {
        super(context);
        setCard(card,true);

    }
    public PlayingCardView(Context context, Card card, boolean showing)
    {
        super(context);
        setCard(card,showing);
    }

    public Card getCard()
    {
        return card;
    }
    public boolean isFaceUp()
    {
        return faceUp;
    }

    public void setCard(Card card, boolean showing)
    {
        this.card = card;
        this.faceUp = showing;
        this.hidden = false;
        this.invalidate();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float density = getResources().getDisplayMetrics().density;
        int padding = Settings.getPadding();
        int x = getWidth();
        int y = getHeight();
        float textSize = .1f * x *density;



        if (card == null || !faceUp) {
            setBackgroundResource(Settings.playingCardBack);
            return;
        }
        gd.setColor(Color.WHITE);
        gd.setCornerRadius(8);
        gd.setStroke(3, Color.BLACK);
        this.setBackgroundDrawable(gd);


        paint.setTextSize(textSize);


        //draw card rank in top left and bottom right
        paint.setTextAlign(Paint.Align.LEFT);
        paint.setColor(card.getColor());
        canvas.drawText(card.getRank(getContext()), +padding , 0 + paint.getTextSize()+padding, paint);

        paint.setTextAlign(Paint.Align.RIGHT);
        canvas.drawText(card.getRank(getContext()), getWidth() - padding, getHeight()-2*padding, paint);


        //suit symbol in proper color
        paint.setTextAlign(Paint.Align.CENTER);
        //paint.setColor(card.getSuitColor());
        canvas.drawText(card.getSuit(getContext()),((float)getWidth()/2),((float)getHeight()/2)+(paint.getTextSize()/2),paint);
    }

    public void flip()
    {
        faceUp =!faceUp;
        invalidate();
    }
    public void discard()
    {
        this.hidden= true;
    }


    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
       // super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        int newMeasuredWidth = 0;
        int newMeasuredHeight = 0;
        if (hidden)
            return;
        if(width* Settings.aspectRatio <=height)
        {
            newMeasuredWidth = MeasureSpec.makeMeasureSpec(width,MeasureSpec.EXACTLY);
            newMeasuredHeight = MeasureSpec.makeMeasureSpec((int)(width* Settings.aspectRatio),MeasureSpec.EXACTLY);
        }
        else
        {
            newMeasuredHeight = MeasureSpec.makeMeasureSpec(height,MeasureSpec.EXACTLY);
            newMeasuredWidth = MeasureSpec.makeMeasureSpec((int)(height/ Settings.aspectRatio),MeasureSpec.EXACTLY);
        }


        super.onMeasure(newMeasuredWidth,newMeasuredHeight);

    }
}
