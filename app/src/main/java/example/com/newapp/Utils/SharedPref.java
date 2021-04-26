package example.com.newapp.Utils;

import android.content.Context;
import android.content.SharedPreferences;

/*
    Bu class'da yapılan işlem; program için tutulması gereken gerekli bilgileri local'de tutuyoruz.
 */
/*
    The operation in this class; We keep the necessary information that should be kept for the program at local.
 */


public class SharedPref {
    private static SharedPref instance = null;

    private static SharedPreferences sharedPref;
    private static SharedPreferences.Editor editor;
    private static final String FILE_KEY = "DarkMode";
    private static final String DARKMODE = "default";
    private String ID = "1";
    private static final String TEAMS_SIZE = "0";
    private String SELECETED_TEAM ="";
    private String CONNECT_STATUS="full";
    private static final String IS_FIRST="true";

    private SharedPref() {
    }

    public static SharedPref getInstance(Context context) {
        if (instance == null) {
            instance = new SharedPref();
            sharedPref = context.getSharedPreferences(FILE_KEY, Context.MODE_PRIVATE);
            editor = sharedPref.edit();
        }
        return instance;
    }

    public String getDarkmode() {
        return sharedPref.getString(DARKMODE, DARKMODE);
    }

    public void setDarkmode(String type) {
        editor.putString(DARKMODE, type);
        editor.apply();
    }

    public String getID() {
        return ID;
    }

    public void setId(String ID) {
        this.ID = ID;
    }

    public String getTeamsSize() {
        return sharedPref.getString(TEAMS_SIZE, TEAMS_SIZE);
    }

    public void setTeamsSize(String size) {
        editor.putString(TEAMS_SIZE, size);
        editor.apply();
    }
    public String getSELECETED_TEAM() {
        return sharedPref.getString(SELECETED_TEAM, SELECETED_TEAM);
    }

    public void setSELECETED_TEAM(String team) {
        editor.putString(SELECETED_TEAM, team);
        editor.apply();
    }
    public String getCONNECT_STATUS() {
        return CONNECT_STATUS;
    }

    public void setCONNECT_STATUS(String CONNECT_STATUS) {
        this.CONNECT_STATUS = CONNECT_STATUS;
    }

    public String getIsFirst() {
        return sharedPref.getString(IS_FIRST, IS_FIRST);
    }

    public void setIsFirst(String isFirst) {
        editor.putString(IS_FIRST, isFirst);
        editor.apply();
    }



}