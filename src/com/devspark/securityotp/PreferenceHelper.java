package com.devspark.securityotp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.util.Log;

/**
 * 
 * @author e.shishkin
 * 
 */
public class PreferenceHelper {
    public static final String TAG = PreferenceHelper.class.getSimpleName();

    private static final String PREF_ID = "ID";
    private static final String PREF_MASK_INDEX = "MASK_INDEX";
    private static final String PREF_INSTALL = "INSTALL";

    private final SharedPreferences mPrefs;

    /**
     * Default constructor for PreferenceHelper
     * 
     * @param context
     */
    public PreferenceHelper(Context context) {
        mPrefs = PreferenceManager.getDefaultSharedPreferences(context);
    }

    /**
     * Save new generated id in shared preference.
     * 
     * @param id
     */
    public void saveId(String id) {
        Log.i(TAG, "Save id=" + id);
        Editor mEditor = mPrefs.edit();
        mEditor.putString(PREF_ID, id);
        mEditor.commit();
    }

    /**
     * Get saved id from shared preference.
     * 
     * @return id
     */
    public String getId() {
        String id = mPrefs.getString(PREF_ID, null);
        return id;
    }

    /**
     * Save current use mask index.
     * 
     * @param index
     */
    public void saveMaskIndex(int index) {
        Log.i(TAG, "Save maskIndex=" + index);
        Editor mEditor = mPrefs.edit();
        mEditor.putInt(PREF_MASK_INDEX, index);
        mEditor.commit();
    }

    /**
     * Get saved maskIndex from shared preference.
     * 
     * @return maskIndex or -1 if maskIndex preference does not exist
     */
    public int getMaskIndex() {
        int maskIndex = mPrefs.getInt(PREF_MASK_INDEX, -1);
        return maskIndex;
    }

    /**
     * Save install flag in shared preference.
     * 
     * @param install
     */
    public void saveInstall(boolean install) {
        Editor mEditor = mPrefs.edit();
        mEditor.putBoolean(PREF_INSTALL, install);
        mEditor.commit();
    }

    /**
     * Get saved install flag from shared preference.
     * 
     * @return install
     */
    public boolean isInstall() {
        boolean install = mPrefs.getBoolean(PREF_INSTALL, false);
        return install;
    }

}
