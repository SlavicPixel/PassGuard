package com.upi.passguard;

import android.content.Context;
import android.content.SharedPreferences;

public class SessionManagement {
    SharedPreferences sharedPreferences;
    SharedPreferences.Editor editor;
    String SHARED_PREF_NAME = "session";
    String SESSION_KEY1 = "session_un";
    String SESSION_KEY2 = "session_pw";

    public SessionManagement(Context context){
        sharedPreferences = context.getSharedPreferences(SHARED_PREF_NAME, Context.MODE_PRIVATE);
        editor = sharedPreferences.edit();
    }

    public void saveSession(String SESSION_VALUE1, String SESSION_VALUE2){
        //save session of user whenever user is logged in
        editor.putString(SESSION_KEY1, SESSION_VALUE1).commit();
        editor.putString(SESSION_KEY2, SESSION_VALUE2).commit();
        editor.apply();
    }

    public String getSession(boolean preference){
        //return user whose session is saved
        if (preference) sharedPreferences.getString(SESSION_KEY1, "null");
        return sharedPreferences.getString(SESSION_KEY2, "null");
    }

    public void removeSession(){
        editor.clear();
    }
}
