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




class GameActivity : AppCompatActivity() {

    var gameType:String? = null
    var diamond:Boolean = false
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        when(intent.extras)
        {
            null ->gameType = savedInstanceState?.getString(getString(R.string.gamemode).toString())
            else ->{
                gameType = intent.extras?.get(getString(R.string.gamemode).toString()) as String
            }
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

        val point:Point = Point()
        windowManager.defaultDisplay.getSize(point)
        Settings.calculateCardSize(point.x,resources.displayMetrics.heightPixels-getStatusBar(),resources.displayMetrics.density,pyramidView.peaks,diamond)

        val gameView = GameView(this,pyramidView)

        setContentView(gameView)
    }
    fun getStatusBar():Int{
        val resource = resources.getIdentifier("status_bar_height", "dimen", "android")
        return resources.getDimensionPixelSize(resource)

    }


    override fun onSaveInstanceState(outState: Bundle?, outPersistentState: PersistableBundle?) {
        super.onSaveInstanceState(outState, outPersistentState)
        outState?.putString(getString(R.string.gamemode),gameType)
    }
}
