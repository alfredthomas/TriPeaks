package com.alfredthomas.tripeaks.UI.gamechooser;

import android.app.Activity;
import android.content.Context;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.ViewGroup;
import com.alfredthomas.tripeaks.GameType;

public class GameChooserAdapter extends RecyclerView.Adapter {
    GameType[] gameTypes;
    public GameChooserAdapter(GameType[] games)
    {
        gameTypes = games;
    }

    @Override
    public int getItemViewType(int position) {
        return super.getItemViewType(position);
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        GameChooserViewHolder gameChooserViewHolder = new GameChooserViewHolder(new GameChooserView(viewGroup.getContext()));
        gameChooserViewHolder.gameChooserView.setLayoutParams(getDefaultCellSize(viewGroup));
        return gameChooserViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
        ((GameChooserViewHolder)viewHolder).gameChooserView.setData(gameTypes[i]);
    }

    @Override
    public int getItemCount() {
        return gameTypes.length;
    }

    //do some magic and figure out cell height based on orientation
    private static ViewGroup.LayoutParams getDefaultCellSize(ViewGroup parent)
    {
        int rowHeight;
        boolean isTablet = isTablet(parent.getContext());
        if(parent.getContext().getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
            //8 cells visible at a time for portrait phone, 15 for tablet portrait
            rowHeight = parent.getHeight() / (isTablet? 10:6);
        }
        else
        {
            // 5 cells for landscape or undefined phone, 10 cells for tablet landscape
            rowHeight = parent.getHeight() / (isTablet?6:3);
        }
        return new ViewGroup.LayoutParams(parent.getMeasuredWidth(), rowHeight);
    }

    //from https://forums.xamarin.com/discussion/106774/how-to-properly-detect-if-an-android-device-is-a-phone-or-a-tablet
    private static boolean isTablet(Context context)
    {
        Display display = ((Activity)context).getWindowManager().getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        display.getMetrics(displayMetrics);

        //get height and width inches
        double wInches = displayMetrics.widthPixels / (double)displayMetrics.densityDpi;
        double hInches = displayMetrics.heightPixels / (double)displayMetrics.densityDpi;

        //a^2 + b^2 = c^2
        double screenDiagonal = Math.sqrt(Math.pow(wInches, 2) + Math.pow(hInches, 2));
        //using 7 inches or greater as a tablet
        return (screenDiagonal >= 7.0);

    }
}
