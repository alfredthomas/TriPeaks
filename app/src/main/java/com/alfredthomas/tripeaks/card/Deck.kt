package com.alfredthomas.tripeaks.card

import android.os.Parcel
import android.os.Parcelable


class Deck(var deck: ArrayList<Card>) : Parcelable {
    enum class Suit { Clubs, Diamonds, Hearts, Spades }

    enum class Rank { Ace, Two, Three, Four, Five, Six, Seven, Eight, Nine, Ten, Jack, Queen, King }

    init {
        if (deck.isNullOrEmpty()) {
            deck = arrayListOf()
            //add all cards by suit and rank
            for (s in Suit.values()) {
                for (r in Rank.values()) {
                    this.deck.add(Card(r, s))
                }
            }
        }
    }

    //shuffle individual deck
    fun shuffle() {
        deck.shuffle()
    }

    //decks will contain 52 cards without jokers or 54 with
    fun getSize(): Int {
        return 52
    }

    constructor(source: Parcel) : this(
    source.createTypedArrayList(Card.CREATOR)
    )

    override fun describeContents() = 0

    override fun writeToParcel(dest: Parcel, flags: Int) = with(dest) {
        writeTypedList(deck)
    }

    companion object {
        @JvmField
        val CREATOR: Parcelable.Creator<Deck> = object : Parcelable.Creator<Deck> {
            override fun createFromParcel(source: Parcel): Deck = Deck(source)
            override fun newArray(size: Int): Array<Deck?> = arrayOfNulls(size)
        }
    }
}