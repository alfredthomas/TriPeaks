package com.alfredthomas.tripeaks

import android.graphics.Point
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.alfredthomas.tripeaks.UI.DashboardView
import com.alfredthomas.tripeaks.UI.StackView
import com.alfredthomas.tripeaks.UI.games.*
import com.alfredthomas.tripeaks.card.Card
import com.alfredthomas.tripeaks.card.Deck
import java.util.ArrayList


class GameActivity : AppCompatActivity() {

    var gameType:GameType? = null
    var dashboardView:DashboardView? = null
    var deck: Deck? = null
    var stackSize = StackView.NEW_GAME_STACK_SIZE
    var cardVisibility:List<Int>? = null
    var discard: Card? = null
    var flipStatus: BooleanArray? = null
    var score:Int = 0
    var streak:Int = 0
    var endGame:Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when(intent.extras)
        {
            null ->{gameType = savedInstanceState?.getParcelable<GameType>(getString(R.string.gamemode).toString())}
            else ->{
                gameType = intent.extras?.getParcelable<GameType>(getString(R.string.gamemode).toString())
            }
        }
        if(savedInstanceState!=null) {
            deck = savedInstanceState.getParcelable(getString(R.string.deck).toString())
            stackSize = savedInstanceState.getInt(getString(R.string.discardstacksize).toString(), StackView.NEW_GAME_STACK_SIZE)
            cardVisibility = savedInstanceState.getIntegerArrayList(getString(R.string.pyramidVISIBILITY).toString())
            discard = savedInstanceState.getParcelable(getString(R.string.discard))
            flipStatus = savedInstanceState.getBooleanArray(getString(R.string.flipstatus).toString())
            score = savedInstanceState.getInt(getString(R.string.currentscore).toString(), 0)
            streak = savedInstanceState.getInt(getString(R.string.currentstreak).toString(), 0)
            endGame = savedInstanceState.getBoolean(getString(R.string.endgame),false)

        }

        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        Settings.calculateCardSize(point.x,resources.displayMetrics.heightPixels-getStatusBar(),resources.displayMetrics.density,gameType)


        dashboardView = DashboardView(this,gameType,deck,cardVisibility,flipStatus,stackSize,discard)
        dashboardView?.setScore(score,streak)

        setContentView(dashboardView)

        if(endGame)
            dashboardView?.showGameEnd()
    }

    fun getStatusBar():Int{
        val resource = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resource)

    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putParcelable(getString(R.string.gamemode),gameType)
        outState?.putParcelable(getString(R.string.deck).toString(),dashboardView?.deck)
        outState?.putIntegerArrayList(getString(R.string.pyramidVISIBILITY),dashboardView?.pyramidVisibility as ArrayList<Int>)
        outState?.putParcelable(getString(R.string.discard).toString(),dashboardView?.discard)
        outState?.putInt(getString(R.string.discardstacksize).toString(),dashboardView!!.discardSize)
        outState?.putBooleanArray(getString(R.string.flipstatus).toString(),dashboardView?.flipStatus as BooleanArray)
        outState?.putInt(getString(R.string.currentscore).toString(),dashboardView!!.score)
        outState?.putInt(getString(R.string.currentstreak).toString(),dashboardView!!.streak)
        if(dashboardView?.dialog!=null)
            outState?.putBoolean(getString(R.string.endgame),dashboardView!!.dialog.isShowing)
    }

    override fun onDestroy() {
        super.onDestroy()
        if(dashboardView?.dialog!=null)
            dashboardView?.dialog?.dismiss()
    }
}
