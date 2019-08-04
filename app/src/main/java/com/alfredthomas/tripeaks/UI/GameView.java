package com.alfredthomas.tripeaks.UI;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.alfredthomas.tripeaks.GameActivity;
import com.alfredthomas.tripeaks.R;
import com.alfredthomas.tripeaks.Settings;
import com.alfredthomas.tripeaks.UI.pyramid.*;
import com.alfredthomas.tripeaks.card.Card;
import com.alfredthomas.tripeaks.card.Deck;

import java.util.ArrayList;
import java.util.List;

public class GameView extends ImprovedView {
    Deck deck;
    PyramidBase pyramidView;
    StackView stackView;
    Button newGameButton;
    Button backButton;
    TextView scoreView;
    int score = 0;
    int currentStreak = 0;
    int highScore = 0;
    String gameMode;

    public GameView(Context context)
    {
        super(context);
    }
    public GameView(Context context, String gameMode, PyramidBase pyramidView, Deck deck,List<Integer> pyramidViewVisibility, boolean[] flipStatus, int stackSize, Card discard)
    {
        super(context);
        this.gameMode = gameMode;
        scoreView = new TextView(context);
        scoreView.setGravity(Gravity.CENTER);
        addView(scoreView);
        loadHighScore();
        updateScore();

        setBackgroundColor(Settings.background);
        stackView = new StackView(context);
        this.pyramidView = pyramidView;
        pyramidView.setStackViewWeakReference(stackView);

        addView(stackView);
        addView(pyramidView);

        backButton = new Button(context);
        backButton.setText(R.string.mainmenu);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                saveHighScore();
                ((GameActivity)(GameView.this.getContext())).finish();
            }
        });
        addView(backButton);

        newGameButton = new Button(context);
        newGameButton.setText(R.string.newgame);
        newGameButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                newGame();
            }
        });
        addView(newGameButton);

        this.deck = deck;
        if(deck == null || deck.getDeck().isEmpty())
            newGame();
        else {
            setCards(pyramidViewVisibility, flipStatus, stackSize);
            stackView.setDiscard(discard);
        }
    }

    private void newGame()
    {
        saveHighScore();
        score = 0;
        updateScore();
        if(deck == null)
            deck = new Deck(new ArrayList<Card>());
        deck.shuffle();
        setCards(null,null,-1);
    }

    public void cardCleared()
    {
        currentStreak++;
        score+=currentStreak;
        updateScore();
    }
    public void loadHighScore()
    {
        highScore = getContext().getSharedPreferences(getContext().getString(R.string.highscore),Context.MODE_PRIVATE).getInt(gameMode,0);
    }
    public void updateHighScore()
    {
        highScore = Math.max(highScore,score);
    }
    private void saveHighScore()
    {
        getContext().getSharedPreferences(getContext().getString(R.string.highscore),Context.MODE_PRIVATE).edit().putInt(gameMode,highScore).apply();
    }
    public int getScore()
    {
        return score;
    }
    public int getStreak()
    {
        return currentStreak;
    }
    public void setScore(int score,int streak)
    {
        this.score = score;
        this.currentStreak= streak;
        updateScore();
    }
    private void updateScore()
    {
        updateHighScore();
        scoreView.setText(getContext().getString(R.string.score,highScore,score));
    }
    public void endStreak()
    {
        currentStreak = 0;
    }
    public void peakCleared()
    {
        currentStreak++;
        score+=15;
        updateScore();
    }

    private void setCards(List<Integer> pyramidViewVisibility,boolean[] flipStatus,int stackSize) {
        ArrayList<Card> cards = deck.getDeck();

        pyramidView.setCards(cards.subList(0, pyramidView.getMaxCards()),pyramidViewVisibility,flipStatus);
        stackView.setCards(cards.subList(pyramidView.getMaxCards(), cards.size()),stackSize);
    }
    public Deck getDeck()
    {
        return deck;
    }
    public List<Integer> getPyramidVisibility()
    {
        return pyramidView.getCardVisibility();
    }
    public Card getDiscard()
    {
        return stackView.getDiscardCard();
    }
    public int getDiscardSize()
    {
        return stackView.getCurrent();
    }
    public boolean[] getFlipStatus()
    {
        return pyramidView.getFlipStatus();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int y = 0;

        float heightDiv = height/10f;

        measureView(backButton,0,0,width/3,(int)(heightDiv));
        measureView(scoreView,width/3,0,width/3,(int)(heightDiv));
        measureView(newGameButton,(width/3)*2,0,width/3,(int)(heightDiv));


        y+=heightDiv;
        measureView(pyramidView,0,y,width,(int)(heightDiv*Settings.pyramidScreenPercent));

        y+=(heightDiv*Settings.pyramidScreenPercent);
        measureView(stackView,0,y,width,(int)(heightDiv*Settings.discardScreenPercent));

    }
}
