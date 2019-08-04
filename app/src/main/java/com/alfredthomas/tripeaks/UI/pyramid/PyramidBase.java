package com.alfredthomas.tripeaks.UI.pyramid;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;
import com.alfredthomas.tripeaks.R;
import com.alfredthomas.tripeaks.Settings;
import com.alfredthomas.tripeaks.UI.GameView;
import com.alfredthomas.tripeaks.UI.ImprovedView;
import com.alfredthomas.tripeaks.UI.PlayingCardView;
import com.alfredthomas.tripeaks.UI.StackView;
import com.alfredthomas.tripeaks.card.Card;
import com.alfredthomas.tripeaks.card.Deck;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class PyramidBase extends ImprovedView{
    TextView winText;
    boolean won = false;
    WeakReference<StackView> stackViewWeakReference;
    boolean diamond = false;
    List<PlayingCardView> cards = new ArrayList<>();

    int[] leftParent;
    int[] rightParent;
    int[] rowSize;
    int peaks;



    //AbstractMethods


    PyramidBase(Context context)
    {
        super(context);
    }
    public void setStackViewWeakReference(StackView stackView)
    {
        stackViewWeakReference = new WeakReference<>(stackView);
    }

    private void setWon() {

        winText.setText(getResources().getQuantityString(R.plurals.youwin, stackViewWeakReference.get().getCurrent()+1,stackViewWeakReference.get().getCurrent()+1));
    }


    public void setCards(List<Card> cardsList, List<Integer> visibility,boolean[] flipStatus)
    {
        won = false;

        if(winText == null)
        {
            winText = new TextView(getContext());
            winText.setGravity(Gravity.CENTER);
            winText.setTextSize(10f*getResources().getDisplayMetrics().density);
            addView(winText);
        }
        for(int i = 0; i< cardsList.size();i++)
        {
            //index of the last row
            boolean flipped = flipStatus == null? getStartIndex(rowSize.length-1)<=i:flipStatus[i];
            PlayingCardView cardView;
            if(i>= cards.size())
            {
                cardView = new PlayingCardView(getContext());
                addView(cardView);
                cards.add(cardView);
            }
            else
            {
                cardView = cards.get(i);
            }
            final int index = i;
            cardView.setCard(cardsList.get(i),flipped);
            if(visibility == null || visibility.isEmpty())
                cardView.setVisibility(VISIBLE);
            else
                cardView.setVisibility(visibility.get(i));
            cardView.setOnClickListener(new OnClickListener() {
                @Override
                public void onClick(View v) {
                    PlayingCardView view = ((PlayingCardView)v);
                    if(view.isFaceUp()&&canDiscard(view.getCard()))
                    {
                        stackViewWeakReference.get().setDiscard(view.getCard());
                        v.setVisibility(INVISIBLE);
                        if (index < rowSize[0])
                        {
                            checkWin();
                            ((GameView)PyramidBase.this.getParent()).peakCleared();
                        }
                        else {
                            flipleft(index);
                            flipright(index);
                            ((GameView)PyramidBase.this.getParent()).cardCleared();

                        }
                        requestLayout();
                    }
                }
            });

        }
    }
    public List<Integer> getCardVisibility()
    {
        List<Integer> visibility = new ArrayList<>();
        for(int i = 0; i< cards.size();i++)
        {
            visibility.add(cards.get(i).getVisibility());
        }
        return visibility;
    }
    public boolean[] getFlipStatus()
    {
        boolean[] flipStatus = new boolean[cards.size()];
        for(int i = 0; i< cards.size();i++)
        {
            flipStatus[i] = cards.get(i).isFaceUp();
        }
        return flipStatus;
    }
    private boolean canDiscard(Card card)
    {
        Card discard = stackViewWeakReference.get().getDiscard().getCard();
        int first = ((discard.getRank().ordinal()+1)+ Deck.Rank.values().length)% Deck.Rank.values().length;
        int second = ((discard.getRank().ordinal()-1)+Deck.Rank.values().length)% Deck.Rank.values().length;
        return Deck.Rank.values()[first]==card.getRank()||Deck.Rank.values()[second]==card.getRank();
    }
    public int getMid()
    {
        return rowSize.length/2;
    }

    public int getRow(int index)
    {
        for(int i = 0; i<rowSize.length; i++)
        {
            index -= rowSize[i];
            if(index<0)
                return i;
        }

        return -1;
    }

    public void checkWin() {
        //assume at least 1 child
        won = cards.get(0).getVisibility()==INVISIBLE;
        for(int i = 1; i<rowSize[0]; i++) {
            won = won && cards.get(i).getVisibility()==INVISIBLE;
        }
        if(won) {
            ((GameView) getParent()).peakCleared();
            setWon();
        }
        requestLayout();

    }
    private boolean inRange(int i, int arraySize)
    {
        return !(i<0 || i>= arraySize);

    }
    public int getRowSize(int row){
        return inRange(row,rowSize.length)?rowSize[row]:0;
    }

    public int getStartIndex(int row)
    {
        int index = 0;
        if(!inRange(row,rowSize.length))
            return index;
        for( int i = 0; i<row; i++)
        {
            index+=rowSize[i];
        }
        return index;
    }
    public int getRowPos(int index)
    {
        if(!inRange(index,cards.size()))
        {
            return 0;
        }
        return index-getStartIndex(getRow(index));
    }
    //check left parent and flip if necessary
    public void flipleft(int index)
    {
        //cards in rows 1 and 2 potentially don't have left parents
        if(!leftgone(index))
            return;

        if(leftParent[index]!=-1)
            cards.get(leftParent[index]).flip();

    }
    //check right parent and flip if necessary
    public void flipright(int index)
    {
        if(!rightgone(index))
            return;

        if(rightParent[index]!=-1)
            cards.get(rightParent[index]).flip();
    }
    public boolean leftgone(int index)
    {
        return this.leftParent[index] !=-1 && ((diamond && getRow(index)>getMid() && this.rightParent[index-1] != this.leftParent[index]) || (inRange(index-1,cards.size())&&this.rightParent[index-1] == this.leftParent[index] && cards.get(index-1).getVisibility()==INVISIBLE));
    }

    //check if right neightbor if hidden or doesn't exist
    public boolean rightgone(int index)
    {
        return this.rightParent[index] !=-1 && ((diamond && getRow(index)>getMid() && (!inRange(index+1,cards.size())|| this.leftParent[index+1] != this.rightParent[index])) || (inRange(index+1,cards.size())&&this.leftParent[index+1] == this.rightParent[index] && cards.get(index+1).getVisibility()==INVISIBLE));
    }

    public int getMaxCards()
    {
        int size = 0;
        for (int row:rowSize)
        {
            size+=row;
        }
        return size;
    }

    public int getPeaks()
    {
        return peaks;
    }
    @Override
    public void onMeasure(int widthMeasureSpec, int heightMeasureSpec)
    {
        super.onMeasure(widthMeasureSpec,heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        if(won)
        {
            measureView(winText,0,0,width,height);
            return;
        }
        measureView(winText,0,0,0,0);
        int cardWidth = Settings.getCardWidth();
        int halfCardWidth = cardWidth/2;
        int cardHeight = Settings.getCardHeight();
        int halfCardHeight = cardHeight/2;

        int padding = Settings.getPadding();
        int y = getYOffset(heightMeasureSpec);

        for(int i = 0; i<rowSize.length;i++)
        {
            int rowOffset = diamond?(halfCardWidth * Math.abs(getMid() - i)):(halfCardWidth * (rowSize.length - 1 - i));
            int x = padding + getXOffset(widthMeasureSpec,diamond)+ rowOffset;

            for(int j = getStartIndex(i); j<(rowSize[i]+getStartIndex(i));j++) {

                //accounting for peak
                if(j!=getStartIndex(i) && peaks>1 && getRowPos(j)%(rowSize[i]/peaks)==0 &&  i<2 )
                    x+=Math.abs(2-i)*cardWidth;

                if(diamond && i>getMid())
                    measureView(cards.get(j),(int)cards.get(getStartIndex(getMid()-(i-getMid()))+j-getStartIndex(i)).getX(),y,cardWidth,cardHeight);

                else
                    measureView(cards.get(j),x,y,cardWidth,cardHeight);
                x+=cardWidth;
            }
            y+=halfCardHeight;
        }



    }

    public int getYOffset(int heightMeasureSpec)
    {
        return (MeasureSpec.getSize(heightMeasureSpec) - ((Settings.getCardHeight()/2)*(rowSize.length+1)))/2;
    }
    public int getXOffset(int widthMeasureSpec,boolean diamond)
    {
        int maxWidthRow = diamond?getMid():rowSize.length-1;
        return (MeasureSpec.getSize(widthMeasureSpec) - (Settings.getPadding() *2) - (Settings.getCardWidth()*(rowSize[maxWidthRow])))/2;
    }


}
