package com.alfredthomas.tripeaks

import android.content.Intent
import android.content.res.Resources
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Button
import com.alfredthomas.tripeaks.UI.gamechooser.GameChooserAdapter
import com.google.gson.Gson
import java.io.InputStream
import java.io.InputStreamReader
import java.lang.Exception

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

        val recyclerView = RecyclerView(this)
        recyclerView.layoutManager = LinearLayoutManager(this)

        val gameChooserAdapter = GameChooserAdapter(parseGames())
        recyclerView.adapter = gameChooserAdapter

        val divider = DividerItemDecoration(this,DividerItemDecoration.VERTICAL)
        divider.setDrawable(resources.getDrawable(R.drawable.horizontal_divider))

        recyclerView.addItemDecoration(divider)

        setContentView(recyclerView)

    }

    fun startGame(view: View)
    {
        val intent = Intent(this,GameActivity::class.java)
        intent.putExtra(getText(R.string.gamemode).toString(),(view as Button).text.toString())
        startActivity(intent)
    }

    fun parseGames():Array<GameType>
    {
        try {
            val inputStream = resources.openRawResource(R.raw.game_modes)
            val reader = InputStreamReader(inputStream)
            val gson = Gson()
            val gameTypes:JsonResult = gson.fromJson(reader,JsonResult::class.java)
            return gameTypes.game_modes
        }
        catch (e:Exception)
        {
            e.printStackTrace()
        }
        return emptyArray()
    }

}
