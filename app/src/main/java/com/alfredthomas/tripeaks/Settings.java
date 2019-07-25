package com.alfredthomas.tripeaks;

import android.graphics.Color;

public final class Settings {
    public static float aspectRatio = 3f/2f;
    private static int cardWidth;
    private static int cardHeight;

    public static final int background = Color.parseColor("#c2b280");

    private static float maxCardsHigh= 5;
    private static int maxCardsWide=10;

    private static final int[] cardsWide= {7,8,10,13};
    private static final int[] cardsWideDiamond= {5,7,9,12};

    private static final float[] cardsHigh= {4f,3f,2.5f,2.5f};
    private static final float[] cardsHighDiamond= {5f,4f,3f,3f};

    private static final int paddingVal = 5;
    private static int padding;

    public static final int pyramidScreenPercent = 7;
    public static final int discardScreenPercent = 2;

    public static int playingCardBack = R.drawable.camocardback;
    public static int[] backs = new int[]{R.drawable.camocardback,R.drawable.squarecardback,R.drawable.watercardback};

    public static int getCardWidth() {
        return cardWidth;
    }

    public static int getCardHeight() {
        return cardHeight;
    }

    public static int getPadding() {
        return padding;
    }

    public static void calculateCardSize(int screenWidth,int screenHeight, float density, int peaks, boolean diamond)
    {
        padding = (int)((float)paddingVal*density);
        maxCardsWide= diamond? cardsWideDiamond[peaks-1]:cardsWide[peaks-1];
        maxCardsHigh = diamond?cardsHighDiamond[peaks-1]:cardsHigh[peaks-1];
        screenHeight = screenHeight - (2*padding);
        int smallestHeight = Math.min((int)((screenHeight*(pyramidScreenPercent/10f))/maxCardsHigh),(int)(screenHeight*(discardScreenPercent/10f)));
        cardWidth = Math.min((screenWidth-(2*padding))/maxCardsWide,(int)(smallestHeight/aspectRatio));
        cardHeight = (int)(aspectRatio*cardWidth);
    }


}
