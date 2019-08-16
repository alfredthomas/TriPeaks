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
    public static final int NEW_GAME_STACK_SIZE = Integer.MIN_VALUE;

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
        current = stackSize == NEW_GAME_STACK_SIZE?cardsList.size()-1:stackSize;
        if(stackSize == NEW_GAME_STACK_SIZE)
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

    public boolean hasCardsLeft()
    {
        return current>=0 || current == NEW_GAME_STACK_SIZE;
    }
    public void deal()
    {

        if(current<0)
        {
            requestLayout();
            return;}
        ((DashboardView)getParent()).endStreak();
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


        int padding = Settings.getPadding();

        int width = MeasureSpec.getSize(widthMeasureSpec)-(2*padding);
        int height = MeasureSpec.getSize(heightMeasureSpec);



        int cardWidth = Settings.getCardWidth();
        int cardHeight = Settings.getCardHeight();

        int fractionWidth = Math.max(0,(int)(Settings.discardPercentShown*cardWidth*(cards.size()-1)));

        int partialWidth = (int)((Settings.discardPercentShown) * cardWidth);

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
