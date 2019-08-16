package com.alfredthomas.tripeaks.UI.games;

import android.content.Context;
import android.view.View;
import com.alfredthomas.tripeaks.R;
import com.alfredthomas.tripeaks.Settings;
import com.alfredthomas.tripeaks.UI.DashboardView;
import com.alfredthomas.tripeaks.UI.ImprovedView;
import com.alfredthomas.tripeaks.UI.PlayingCardView;
import com.alfredthomas.tripeaks.UI.StackView;
import com.alfredthomas.tripeaks.card.Card;
import com.alfredthomas.tripeaks.card.Deck;

import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

public abstract class GameBase extends ImprovedView {
    boolean won = false;
//    boolean lose = false;
    WeakReference<StackView> stackViewWeakReference;
    int[] leftParent;
    int[] rightParent;
    int[] rowSize;
    int peaks;
    List<PlayingCardView> cards = new ArrayList<>();

    //ABSTRACT METHODS

    //CONSTRUCTORS


    public GameBase(Context context) {
        super(context);
    }

    //REUSED METHODS
    public void init(int peaks,int[] leftParent, int[] rightParent, int[] rowSize)
    {
        this.peaks = peaks;
        this.leftParent = leftParent;
        this.rightParent = rightParent;
        this.rowSize = rowSize;
    }
    /**weak reference set to maintain discard status */
    public void setStackViewWeakReference(final StackView stackView)
    {
        stackViewWeakReference = new WeakReference<>(stackView);
        stackViewWeakReference.get().setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                ((StackView)(v)).deal();
                checkLose();
            }
        });
    }

    public String getWinText() {

        return getResources().getQuantityString(R.plurals.youwin, stackViewWeakReference.get().getCurrent()+1,stackViewWeakReference.get().getCurrent()+1);
    }
    private void showEndGame()
    {
        ((DashboardView)getParent()).showGameEnd();
    }

    public void setCards(List<Card> cardsList, List<Integer> visibility, boolean[] flipStatus)
    {
        won = false;

        for(int i = 0; i< cardsList.size();i++)
        {
            //index of the last row
            boolean flipped = flipStatus == null? getStartIndex(rowSize.length-1)<=i:flipStatus[i];
//            boolean flipped = true;
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
//            Integer[] nums = new Integer[]{0,3,4,9,10,11};
//            Set<Integer> set = new HashSet<>(Arrays.asList(nums));
//            if(!set.contains(i))
//                cardView.setVisibility(INVISIBLE);
            cardView.setOnClickListener(new View.OnClickListener() {
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
                            if(won)
                                addWinBonus();
                        }
                        else {
                            flipleft(index);
                            flipright(index);
                        }
                        updateScore(index);
                        checkLose();
                        requestLayout();
                    }

                }
            });
        }
        checkWin();
    }
    public void updateScore(int index)
    {
        if (index < rowSize[0])
        {
            ((DashboardView)this.getParent()).peakCleared();
        }
        else
        {
            ((DashboardView)this.getParent()).cardCleared();
        }
    }
    public void addWinBonus()
    {
        ((DashboardView)this.getParent()).winBonus();
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
    public void checkLose()
    {
        if(stackViewWeakReference.get().hasCardsLeft())
            return;

        for(PlayingCardView card:cards)
        {
            if(card.getVisibility()==VISIBLE && card.isFaceUp() && canDiscard(card.getCard()))
                return;
        }
        showEndGame();
    }
    public void checkWin() {
        //assume at least 1 child
        won = cards.get(0).getVisibility()==INVISIBLE;
        for(int i = 1; i<rowSize[0]; i++) {
            won = won && cards.get(i).getVisibility()==INVISIBLE;
        }
        if(won) {
            showEndGame();
        }
    }
    private boolean inRange(int i, int arraySize)
    {
        return !(i<0 || i>= arraySize);
    }
    private boolean isMirrored()
    {
        return this instanceof MirroredBase;
    }

    public int getRowSize(int row){
        return inRange(row,rowSize.length)?rowSize[row]:0;
    }
    /** returns starting index of specified row*/
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
    /**returns position of card in current row*/
    public int getRowPos(int index)
    {
        if(!inRange(index,cards.size()))
        {
            return 0;
        }
        return index-getStartIndex(getRow(index));
    }
    /**check left parent and flip if necessary*/
    public void flipleft(int index)
    {
        //cards in rows 1 and 2 potentially don't have left parents
        if(!leftgone(index))
            return;

        if(leftParent[index]!=-1)
            cards.get(leftParent[index]).flip();

    }
    /**check right parent and flip if necessary*/
    public void flipright(int index)
    {
        if(!rightgone(index))
            return;

        if(rightParent[index]!=-1)
            cards.get(rightParent[index]).flip();
    }

    /**check if left neightbor is hidden or doesn't exist*/
    public boolean leftgone(int index)
    {
        return this.leftParent[index] !=-1 && ((isMirrored() && this.rightParent[index-1] != this.leftParent[index])||(inRange(index-1,cards.size())&&this.rightParent[index-1] == this.leftParent[index] && cards.get(index-1).getVisibility()==INVISIBLE));
    }

    /**check if right neightbor is hidden or doesn't exist*/
    public boolean rightgone(int index)
    {
        return this.rightParent[index] !=-1 && ((isMirrored() && (!inRange(index+1,cards.size())|| this.leftParent[index+1] != this.rightParent[index])) || (inRange(index+1,cards.size())&&this.leftParent[index+1] == this.rightParent[index] && cards.get(index+1).getVisibility()==INVISIBLE));
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
    public boolean getWon()
    {
        return won;
    }
    public int getYOffset(int heightMeasureSpec)
    {
        return (MeasureSpec.getSize(heightMeasureSpec) - ((Settings.getCardHeight()/2)*(rowSize.length+1)))/2;
    }
    public int getXOffset(int widthMeasureSpec)
    {
        return (MeasureSpec.getSize(widthMeasureSpec) - (Settings.getPadding() *2) - (Settings.getCardWidth()*(rowSize[getMaxRow()])))/2;
    }
    public int getMaxRow()
    {
        int max = -1;
        int index = 0;
        for(int i = 0; i< rowSize.length; i++)
        {
            if(rowSize[i]>max)
            {
                max = rowSize[i];
                index=i;
            }
        }
        return index;
    }

}
