package com.alfredthomas.tripeaks.card



class Deck(){
    val deck = arrayListOf<Card>()
    enum class Suit {Clubs,Diamonds,Hearts,Spades}
    enum class Rank {Ace,Two,Three,Four,Five,Six,Seven,Eight,Nine,Ten,Jack,Queen,King}



    init {
        //add all cards by suit and rank
        for(s in Suit.values())
        {
            for(r in Rank.values())
            {
                this.deck.add(Card(r,s))
            }
        }
    }

    //shuffle individual deck
    fun shuffle()
    {
        deck.shuffle()
    }

    //decks will contain 52 cards without jokers or 54 with
    fun getSize():Int
    {
        return 52
    }
}