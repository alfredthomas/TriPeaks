package com.alfredthomas.tripeaks

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import com.alfredthomas.tripeaks.R
import com.alfredthomas.tripeaks.UI.GameView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //val cardView = PlayingCardView(this,Card(Deck.Rank.Three,Deck.Suit.Clubs),false)
       // setContentView(cardView)
        //cardView.setCard(Card(Deck.Rank.Ace,Deck.Suit.Spades),true)
//        val deck = Deck(false)
//        val stackView = StackView(this,deck.deck.subList(0,10))
//        val pyramidView= PyramidView(this,deck.deck.subList(0,28));
//        setContentView(pyramidView)


        setContentView(R.layout.gameselectview)

    }

    fun startGame(view: View)
    {
        val intent = Intent(this,GameActivity::class.java)
        intent.putExtra(getText(R.string.gamemode).toString(),(view as Button).text.toString())
        startActivity(intent)
    }

}
