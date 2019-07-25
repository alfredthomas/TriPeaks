package com.alfredthomas.tripeaks.card

import android.content.Context
import android.graphics.Color

import com.alfredthomas.tripeaks.R

class Card(val rank: Deck.Rank, val suit: Deck.Suit)
{
    fun getColor(): Int {
        if(suit == Deck.Suit.Spades || suit == Deck.Suit.Clubs)
            return Color.BLACK
        return Color.RED
    }
    fun getRank(context: Context): String{
        return context.resources.getStringArray(R.array.rank)[rank.ordinal]
    }
    fun getSuit(context: Context): String{
        return context.resources.getStringArray(R.array.suit)[suit.ordinal]
    }

}