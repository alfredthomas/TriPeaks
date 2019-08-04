package com.alfredthomas.tripeaks

import android.graphics.Point
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import com.alfredthomas.tripeaks.UI.GameView
import com.alfredthomas.tripeaks.UI.pyramid.*
import android.R.attr.top
import android.graphics.Rect
import android.opengl.ETC1.getHeight
import android.view.Display
import android.os.Build
import android.view.ViewGroup
import android.view.WindowManager
import com.alfredthomas.tripeaks.card.Card
import com.alfredthomas.tripeaks.card.Deck
import java.util.ArrayList


class GameActivity : AppCompatActivity() {

    var gameType:String? = null
    var diamond:Boolean = false
    var gameView:GameView? = null
    var deck: Deck? = null
    var stackSize = -1
    var pyramidVisibility:List<Int>? = null
    var discard: Card? = null
    var flipStatus: BooleanArray? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when(intent.extras)
        {
            null ->{gameType = savedInstanceState?.getString(getString(R.string.gamemode).toString())}
            else ->{
                gameType = intent.extras?.get(getString(R.string.gamemode).toString()) as String
            }
        }
        if(savedInstanceState!=null) {
            deck = savedInstanceState.getParcelable(getString(R.string.deck).toString())
            stackSize = savedInstanceState.getInt(getString(R.string.discardstacksize).toString(), -1)
            pyramidVisibility = savedInstanceState.getIntegerArrayList(getString(R.string.pyramidVISIBILITY).toString())
            discard = savedInstanceState.getParcelable(getString(R.string.discard))
            flipStatus = savedInstanceState.getBooleanArray(getString(R.string.flipstatus).toString())

        }

        val pyramidView:PyramidBase

        when(gameType){
            getString(R.string.onepyramidgame) -> pyramidView = OnePyramidView(this)
            getString(R.string.twopyramidgame) -> pyramidView = TwoPyramidView(this)
            getString(R.string.fourpyramidgame) -> pyramidView = FourPyramidView(this)
            getString(R.string.onediamondgame) -> {pyramidView = OnePyramidView(this,true)
                                                    diamond = true}
            getString(R.string.twodiamondgame) -> {pyramidView = TwoPyramidView(this,true)
                                                     diamond = true}
            getString(R.string.threediamondgame) -> {pyramidView = ThreePyramidView(this,true)
                                                    diamond = true}
            getString(R.string.fourdiamondgame) -> {pyramidView = FourPyramidView(this,true)
                                                    diamond = true}

            else -> pyramidView = ThreePyramidView(this)
        }

        val point = Point()
        windowManager.defaultDisplay.getSize(point)
        Settings.calculateCardSize(point.x,resources.displayMetrics.heightPixels-getStatusBar(),resources.displayMetrics.density,pyramidView.peaks,diamond)


        gameView = GameView(this,pyramidView,deck,pyramidVisibility,flipStatus,stackSize,discard)


        setContentView(gameView)
    }
    fun getStatusBar():Int{
        val resource = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resource)

    }


    override fun onSaveInstanceState(outState: Bundle?) {
        super.onSaveInstanceState(outState)
        outState?.putString(getString(R.string.gamemode),gameType)
        outState?.putParcelable(getString(R.string.deck).toString(),gameView?.deck)
        outState?.putIntegerArrayList(getString(R.string.pyramidVISIBILITY),gameView?.pyramidVisibility as ArrayList<Int>)
        outState?.putParcelable(getString(R.string.discard).toString(),gameView?.discard)
        outState?.putInt(getString(R.string.discardstacksize).toString(),gameView!!.discardSize)
        outState?.putBooleanArray(getString(R.string.flipstatus).toString(),gameView?.flipStatus as BooleanArray)
    }
}
