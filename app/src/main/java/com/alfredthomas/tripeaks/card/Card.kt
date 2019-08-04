package com.alfredthomas.tripeaks.card

import android.content.Context
import android.graphics.Color
import android.os.Parcel
import android.os.Parcelable

import com.alfredthomas.tripeaks.R

class Card(val rank: Deck.Rank, val suit: Deck.Suit) : Parcelable {
    fun getColor(): Int {
        if (suit == Deck.Suit.Spades || suit == Deck.Suit.Clubs)
            return Color.BLACK
        return Color.RED
    }

    fun getRank(context: Context): String {
        return context.resources.getStringArray(R.array.rank)[rank.ordinal]
    }

    fun getSuit(context: Context): String {
        return context.resources.getStringArray(R.array.suit)[suit.ordinal]
    }

    constructor(source: Parcel) : this(
        Deck.Rank.values()[source.readInt()],
        Deck.Suit.values()[source.readInt()]
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeInt(rank.ordinal)
        writeInt(suit.ordinal)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Card> = object : Parcelable.Creator<Card> {
            override fun createFromParcel(source: Parcel): Card = Card(source)
            override fun newArray(size: Int): Array<Card?> = arrayOfNulls(size)
        }
    }
}