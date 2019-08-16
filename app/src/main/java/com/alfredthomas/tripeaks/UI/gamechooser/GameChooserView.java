package com.alfredthomas.tripeaks.UI.gamechooser;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.view.Gravity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import com.alfredthomas.tripeaks.GameActivity;
import com.alfredthomas.tripeaks.GameType;
import com.alfredthomas.tripeaks.R;
import com.alfredthomas.tripeaks.UI.ImprovedView;

public class GameChooserView extends ImprovedView {
    TextView nameTV;
    TextView descTV;
    TextView difficultyTV;
    ImageView imageView;
    GameType gameType;
    public GameChooserView(final Context context) {
        super(context);
        nameTV = new TextView(context);
        descTV = new TextView(context);
        difficultyTV = new TextView(context);
        imageView = new ImageView(context);
        setBackgroundColor(Color.BLACK);

        nameTV.setTextAppearance(context,R.style.TextAppearance_AppCompat_Title_Inverse);
        descTV.setTextAppearance(context,R.style.TextAppearance_AppCompat_Medium_Inverse);
        difficultyTV.setTextAppearance(context,R.style.TextAppearance_AppCompat_Medium_Inverse);
        imageView.setAdjustViewBounds(true);
        imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);


//        difficultyTV.setBackgroundColor(Color.GREEN);
        //nameTV.setGravity(Gravity.CENTER_VERTICAL);
        difficultyTV.setGravity(Gravity.CENTER_HORIZONTAL);
        descTV.setGravity(Gravity.CENTER_VERTICAL);

        addView(nameTV);
        addView(descTV);
        addView(difficultyTV);
        addView(imageView);

        this.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, GameActivity.class);
                intent.putExtra(context.getString(R.string.gamemode),gameType);
                context.startActivity(intent);
            }
        });
    }

    public void setData(GameType gameType)
    {
        this.gameType = gameType;
        this.nameTV.setText(gameType.getName());
        this.descTV.setText(gameType.getDescription());
        this.difficultyTV.setText(getResources().getStringArray(R.array.difficulty)[gameType.getDifficulty()]);

        imageView.setImageDrawable(getResources().getDrawable(getResources().getIdentifier(gameType.getImage(),"drawable",getContext().getPackageName())));
    }

    //

    /*  Layout like so:
        ____________________________________________________
       |                                |                   |
       |  NAME           (DIFFICULTY)   |                   |
       |  DESC                          |       IMG         |
       |                                |                   |
       |________________________________|___________________|
     */
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int eighthWidth = MeasureSpec.getSize(widthMeasureSpec)/8;
        int halfHeight = MeasureSpec.getSize(heightMeasureSpec)/2;

        measureView(nameTV,0,0,eighthWidth*3,halfHeight);
        measureView(difficultyTV,eighthWidth*3,0,eighthWidth*2,halfHeight);
        measureView(descTV,0,halfHeight,eighthWidth*5,halfHeight);

        measureView(imageView,eighthWidth*5,0,eighthWidth*3,halfHeight*2);
    }
}
