package com.upi.passguard;

import android.content.Context;
import java.io.File;

public class Utils {

    public boolean dbExist(Context context, String fname){
        File dbFile = context.getDatabasePath(fname);
        return dbFile.exists();
    }
}
