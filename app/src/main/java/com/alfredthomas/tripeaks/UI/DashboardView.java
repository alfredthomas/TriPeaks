package com.alfredthomas.tripeaks.UI;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Color;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.alfredthomas.tripeaks.GameActivity;
import com.alfredthomas.tripeaks.GameType;
import com.alfredthomas.tripeaks.R;
import com.alfredthomas.tripeaks.Settings;
import com.alfredthomas.tripeaks.UI.games.*;
import com.alfredthomas.tripeaks.card.Card;
import com.alfredthomas.tripeaks.card.Deck;

import java.util.ArrayList;
import java.util.List;

public class DashboardView extends ImprovedView {
    Deck deck;
    GameBase gameBase;
    StackView stackView;
    Button newGameButton;
    Button backButton;
    TextView scoreView;
    int score = 0;
    int currentStreak = 0;
    int highScore = 0;
    String gameMode;
    public AlertDialog dialog;


    public DashboardView(Context context)
    {
        super(context);
    }
    public DashboardView(Context context, GameType gameType, Deck deck, List<Integer> pyramidViewVisibility, boolean[] flipStatus, int stackSize, Card discard)
    {
        super(context);
        this.gameMode = gameType.getName();
        scoreView = new TextView(context);
        scoreView.setGravity(Gravity.CENTER);
        addView(scoreView);
        loadHighScore();
        updateScore();

        setBackgroundColor(Settings.background);

//        setBackgroundColor(Color.argb(255,0,115,54));
        stackView = new StackView(context);
        this.gameBase = gameType.createGame(context);
        gameBase.setStackViewWeakReference(stackView);

        addView(stackView);
        addView(gameBase);

        backButton = new Button(context);
        backButton.setText(R.string.mainmenu);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                endActivity();
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
    private void endActivity()
    {
        saveHighScore();
        ((GameActivity)(DashboardView.this.getContext())).finish();
    }
    private void newGame()
    {
        saveHighScore();
        score = 0;
        updateScore();
        if(deck == null)
            deck = new Deck(new ArrayList<Card>());
        deck.shuffle();
        setCards(null,null,StackView.NEW_GAME_STACK_SIZE);
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
    public void cardCleared()
    {
        currentStreak++;
        score+=currentStreak;
        updateScore();
    }
    public void peakCleared()
    {
        cardCleared();
        score+=15;
        updateScore();
    }

    public void winBonus()
    {
        score+=50;
        updateScore();
    }
    private void setCards(List<Integer> cardViewVisibility,boolean[] flipStatus,int stackSize) {
        ArrayList<Card> cards = deck.getDeck();

        gameBase.setCards(cards.subList(0, gameBase.getMaxCards()),cardViewVisibility,flipStatus);
        stackView.setCards(cards.subList(gameBase.getMaxCards(), cards.size()),stackSize);
    }
    public void showGameEnd()
    {
        final AlertDialog.Builder popup= new AlertDialog.Builder(getContext());
        final TextView gameEndText = new TextView(getContext());
        gameEndText.setGravity(Gravity.CENTER);
        gameEndText.setTextSize(10f*getResources().getDisplayMetrics().density);
        if(gameBase.getWon())
        {
            gameEndText.setText(gameBase.getWinText());
        }
        else
        {
            gameEndText.setText(R.string.youlose);
        }
        popup.setView(gameEndText);

        popup.setNeutralButton(R.string.mainmenu, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                endActivity();
            }
        });
        popup.setNegativeButton(R.string.close, null);
        popup.setPositiveButton(R.string.newgame, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                newGame();
            }
        });
        dialog = popup.create();

        dialog.show();
        LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams)dialog.getButton(DialogInterface.BUTTON_NEUTRAL).getLayoutParams();
//        lp.gravity = Gravity.CENTER;
        lp.weight=3;

        dialog.getButton(DialogInterface.BUTTON_NEUTRAL).setLayoutParams(lp);
        dialog.getButton(DialogInterface.BUTTON_NEGATIVE).setLayoutParams(lp);
        dialog.getButton(DialogInterface.BUTTON_POSITIVE).setLayoutParams(lp);


    }
    public Deck getDeck()
    {
        return deck;
    }
    public List<Integer> getPyramidVisibility()
    {
        return gameBase.getCardVisibility();
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
        return gameBase.getFlipStatus();
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
        measureView(gameBase,0,y,width,(int)(heightDiv*Settings.gameScreenPercent));

        y+=(heightDiv*Settings.gameScreenPercent);
        measureView(stackView,0,y,width,(int)(heightDiv*Settings.discardScreenPercent));

    }
}
