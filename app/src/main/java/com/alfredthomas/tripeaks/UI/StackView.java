package com.alfredthomas.tripeaks.UI;

import android.content.Context;
import android.view.View;
import com.alfredthomas.tripeaks.Settings;
import com.alfredthomas.tripeaks.card.Card;

import java.util.ArrayList;
import java.util.List;

public class StackView extends ImprovedView {
    List<PlayingCardView> cards = new ArrayList<>();
    PlayingCardView discard;
    int current;

    public StackView(Context context) {
        super(context);
        init();
    }
    private void init()
    {
        discard = new PlayingCardView(getContext());
        addView(discard);
        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                deal();
//                changeBackground();
            }
        });
    }
    public void setCards( List<Card> cardsList,int stackSize)
    {
        for(int i = 0;i<cardsList.size();i++)
        {
            if(i>= cards.size()) {
                cards.add(new PlayingCardView(getContext(), cardsList.get(i),false));
                addView(cards.get(i));
            }
            else
            {
                cards.get(i).setCard(cardsList.get(i),false);
            }
        }
        current = stackSize == -1?cardsList.size()-1:stackSize;
        if(stackSize == -1)
            deal();
    }


    public PlayingCardView getDiscard() {
        return discard;
    }

    public Card getDiscardCard()
    {
        return discard.card;
    }
    public int getCurrent()
    {
        return current;
    }

    public void deal()
    {

        if(current<0)
        {
            requestLayout();
            return;}
        ((GameView)getParent()).endStreak();
        setDiscard(cards.get(current).card);
        current--;
        requestLayout();
    }
    public void setDiscard(Card card)
    {
        discard.setCard(card,true);
        requestLayout();

    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        //offset by this much when faceUp next card

        float percentShown = 0.2f;
        int padding = Settings.getPadding();

        int width = MeasureSpec.getSize(widthMeasureSpec)-(2*padding);
        int height = MeasureSpec.getSize(heightMeasureSpec);



        int cardWidth = Settings.getCardWidth();
        int cardHeight = Settings.getCardHeight();

        int fractionWidth = Math.max(0,(int)(percentShown*cardWidth*(cards.size()-1)));

        int partialWidth = (int)((percentShown) * cardWidth);

        int xOffset = (width - (padding*2) - (cardWidth*2) - fractionWidth)/2;

        int x = padding + xOffset;
        int y = (height/2) - (cardHeight/2);

        for(int i = current+1; i<cards.size();i++)
        {
            measureView(cards.get(i),0,0,0,0);
            x+=partialWidth;
        }
        for(int i = 0; i<=current; i++)
        {
            measureView(cards.get(i), x, y, cardWidth, cardHeight);
            x += partialWidth;
        }

        x+= cardWidth;
        measureView(discard, x,  y, cardWidth, cardHeight);

    }


}
