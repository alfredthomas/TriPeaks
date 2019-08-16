package com.alfredthomas.tripeaks;

import android.graphics.Color;

public final class Settings {
    public static float aspectRatio = 3f/2f;
    private static int cardWidth;
    private static int cardHeight;
    private static final int deckSize = 52;

    public static final int background = Color.parseColor("#c2b280");

    private static final int paddingVal = 5;
    private static int padding;

    public static final int gameScreenPercent = 7;
    public static final int discardScreenPercent = 2;
    public static final float discardPercentShown = 0.2f;

    public static int playingCardBack = R.drawable.rainbowcardback;
    public static int[] backs = new int[]{R.drawable.camocardback,R.drawable.rainbowcardback,R.drawable.squarecardback,R.drawable.watercardback};

    public static int getCardWidth() {
        return cardWidth;
    }

    public static int getCardHeight() {
        return cardHeight;
    }

    public static int getPadding() {
        return padding;
    }

    public static void calculateCardSize(int screenWidth,int screenHeight, float density, GameType gameType)
    {
        padding = (int)((float)paddingVal*density);

        int maxCardsWide = 0;
        float maxCardsHigh = 0.5f;
        int cardCount = 0;
        int[] rowSize = gameType.getRowSize();
        for(int i = 0; i<rowSize.length; i++)
        {
            maxCardsHigh+=0.5f;
            maxCardsWide = Math.max(maxCardsWide,rowSize[i]);
            cardCount+=rowSize[i];
        }

        screenHeight = screenHeight - (2*padding);
        int discardPile = (int)((deckSize- cardCount - 2)*discardPercentShown + 3);
        int smallestHeight = Math.min((int)((screenHeight*(gameScreenPercent /10f))/maxCardsHigh),(int)(screenHeight*(discardScreenPercent/10f)));

        maxCardsWide = Math.max(discardPile,maxCardsWide);

        cardWidth = Math.min((screenWidth - (2*padding))/maxCardsWide,(int)(smallestHeight/aspectRatio));
        cardHeight = (int)(aspectRatio*cardWidth);
    }




}
