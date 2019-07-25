package com.alfredthomas.tripeaks.UI;

import android.content.Context;
import android.view.View;
import android.widget.Button;
import com.alfredthomas.tripeaks.GameActivity;
import com.alfredthomas.tripeaks.R;
import com.alfredthomas.tripeaks.Settings;
import com.alfredthomas.tripeaks.UI.pyramid.*;
import com.alfredthomas.tripeaks.card.Card;
import com.alfredthomas.tripeaks.card.Deck;

import java.util.ArrayList;

public class GameView extends ImprovedView {
    Deck deck;
    PyramidBase pyramidView;
    StackView stackView;
    Button newGameButton;
    Button backButton;

    public GameView(Context context, PyramidBase pyramidView)
    {
        super(context);
        setBackgroundColor(Settings.background);
        stackView = new StackView(context);
        this.pyramidView = pyramidView;
//        pyramidView = new ThreePyramidView(context);
//        pyramidView = new FourPyramidView(context);
//        pyramidView = new OnePyramidView(context);
//        pyramidView = new TwoPyramidView(context);
        pyramidView.setStackViewWeakReference(stackView);

        addView(stackView);
        addView(pyramidView);

        backButton = new Button(context);
        backButton.setText(R.string.mainmenu);
        backButton.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
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

        newGame();
    }

    private void newGame()
    {
        if(deck == null)
            deck = new Deck();
        deck.shuffle();

        ArrayList<Card> cards = deck.getDeck();
        pyramidView.setCards(cards.subList(0,pyramidView.getMaxCards()));
        stackView.setCards(cards.subList(pyramidView.getMaxCards(),cards.size()));

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        int height = MeasureSpec.getSize(heightMeasureSpec);
        int width = MeasureSpec.getSize(widthMeasureSpec);

        int y = 0;

        float heightDiv = height/10f;

        measureView(newGameButton,(width/3)*2,0,width/3,(int)(heightDiv));

        measureView(backButton,0,0,width/3,(int)(heightDiv));

        y+=heightDiv;
        measureView(pyramidView,0,y,width,(int)(heightDiv*Settings.pyramidScreenPercent));

        y+=(heightDiv*Settings.pyramidScreenPercent);
        measureView(stackView,0,y,width,(int)(heightDiv*Settings.discardScreenPercent));

    }
}
