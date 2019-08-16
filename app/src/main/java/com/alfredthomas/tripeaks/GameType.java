package com.alfredthomas.tripeaks;

import android.content.Context;
import android.os.Parcel;
import android.os.Parcelable;
import com.alfredthomas.tripeaks.UI.games.DiamondView;
import com.alfredthomas.tripeaks.UI.games.GameBase;
import com.alfredthomas.tripeaks.UI.games.HourGlassView;
import com.alfredthomas.tripeaks.UI.games.PyramidView;

public class GameType implements Parcelable {
    String type;
    String name;
    String description;
    int difficulty;
    int peaks;

    int[] leftParent;
    int[] rightParent;
    int[] rowSize;
    String image;


    public GameBase createGame(Context context)
    {
        GameBase game;
        switch (type)
        {
            case "pyramid":game= new PyramidView(context);break;
            case "diamond":game= new DiamondView(context);break;
            case "hourglass":game= new HourGlassView(context);break;
            default:game= new PyramidView(context);break;
        }
        game.init(peaks,leftParent,rightParent,rowSize);
        return game;
    }

    public String getType() {
        return type;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public int getDifficulty() {
        return difficulty;
    }

    public int getPeaks() {
        return peaks;
    }

    public int[] getLeftParent() {
        return leftParent;
    }

    public int[] getRightParent() {
        return rightParent;
    }

    public int[] getRowSize() {
        return rowSize;
    }

    public String getImage() {
        return image;
    }


    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(this.type);
        dest.writeString(this.name);
        dest.writeString(this.description);
        dest.writeInt(this.difficulty);
        dest.writeInt(this.peaks);
        dest.writeIntArray(this.leftParent);
        dest.writeIntArray(this.rightParent);
        dest.writeIntArray(this.rowSize);
        dest.writeString(this.image);
    }

    public GameType() {
    }

    protected GameType(Parcel in) {
        this.type = in.readString();
        this.name = in.readString();
        this.description = in.readString();
        this.difficulty = in.readInt();
        this.peaks = in.readInt();
        this.leftParent = in.createIntArray();
        this.rightParent = in.createIntArray();
        this.rowSize = in.createIntArray();
        this.image = in.readString();
    }

    public static final Parcelable.Creator<GameType> CREATOR = new Parcelable.Creator<GameType>() {
        @Override
        public GameType createFromParcel(Parcel source) {
            return new GameType(source);
        }

        @Override
        public GameType[] newArray(int size) {
            return new GameType[size];
        }
    };
}
