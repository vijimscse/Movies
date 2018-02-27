package com.udacity.movies.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;

/**
 * 
 * @author Vijayalakshmi
 * Preference Manager to get/set shared preferences
 */
public class PreferenceManager {
	private static final String SHARED_PREFS = "App_Sh_Prefs";
	private static SharedPreferences mSharedPrefs;
	private static PreferenceManager sInstance;
	private static Editor mEditor;
	
	/**
	 * Returns singleton synchronized instance of PreferenceManager
	 * @return Instance of PreferenceManeger
	 */
	public synchronized static PreferenceManager getInstance() {
		if (mSharedPrefs == null || mEditor == null) {
			throw new IllegalStateException("PreferenceManager : Members are not initialised!!! ");
		}

		if (sInstance == null) {
			sInstance = new PreferenceManager();
		}
		
		return sInstance;
	}
	
	
	/**
	 * Initializes Preference Members
	 * @param ctx
	 */
	public static void init(Context ctx) {
		mSharedPrefs = ctx.getSharedPreferences(SHARED_PREFS, Context.MODE_PRIVATE);
		mEditor = mSharedPrefs.edit();		
	}

	/**
	 * Returns byte value for given preference key
	 * @return byte
	 */
	public byte getByte(String key, int defValue) {
		return (byte)mSharedPrefs.getInt(key, defValue);
	}
	
	/**
	 * Adds byte-value to Preference
	 * @param key key
	 * @param value value to store
	 */
	public void setByte(String key, byte value) {
		mEditor.putInt(key, value);
		mEditor.commit();
	}
	
	/**
	 * Returns boolean value for given preference key
	 * @param key key
	 * @return boolean
	 */
	public boolean getBoolean(String key, boolean defValue) {
		return mSharedPrefs.getBoolean(key, defValue);
	}
	
	/**
	 * Adds boolean-value to Preference
	 * @param key key
	 * @param value value to store
	 */
	public void setBoolean(String key, boolean value) {
		mEditor.putBoolean(key, value);
		mEditor.commit();
	}
		
	/**
	 * Returns integer value for given preference key
	 * @param key key
	 * @return int
	 */
	public int getInt(String key, int defValue) {
		return mSharedPrefs.getInt(key, defValue);
	}
	
	/**
	 * Adds integer to Preference
	 * @param key key
	 * @param value value
	 */
	public void setInt(String key, int value) {
		mEditor.putInt(key, value);
		mEditor.commit();
	}
	
	/**
	 * Returns long value for given preference key
	 * @param key key
	 * @return long value
	 */
	public long getLong(String key, long defValue) {
		return mSharedPrefs.getLong(key, defValue);
	}
	
	/**
	 * Adds long-value to Preference
	 * @param key key
	 * @param value value
	 */
	public void setLong(String key, long value) {
		mEditor.putLong(key, value);
		mEditor.commit();
	}
	
	/**
	 * Returns string value for given preference key
	 * @param key key
	 * @return string
	 */
	public String getString(String key, String defString) {
		return mSharedPrefs.getString(key, defString);
	}
		
	/**
	 * Adds string-value to Preference
	 */
	public void setString(String key, String value) {
		mEditor.putString(key, value);
		mEditor.commit();
	}

	public void clear() {
		if (mSharedPrefs != null) {
			mSharedPrefs.edit().clear().commit();
		}
	}
}
