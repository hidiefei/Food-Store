package com.example.me.firebase;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.firebase.database.DatabaseReference;


    public class Var {
        DatabaseReference mData;
        public static final String KEY_USER_INFO = "userInfo";
        public static final String KEY_ID = "id_Product";
        private static final String KEY_EMAIL = "Key_Email";


        public static SharedPreferences get(Context context) {
            return PreferenceManager.getDefaultSharedPreferences(context);
        }

        public static void save(Context context, String key, Object value) {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
            if (value == null) {
                edit.putString(key, null);
            } else {
                Class<?> fieldType = value.getClass();
                if (fieldType.equals(Integer.class) || fieldType.equals(int.class)) {
                    edit.putInt(key, (Integer) value);
                } else if (fieldType.equals(Long.class) || fieldType.equals(long.class)) {
                    edit.putLong(key, (Long) value);
                } else if (fieldType.equals(Float.class) || fieldType.equals(float.class)) {
                    edit.putFloat(key, (Float) value);
                } else if (fieldType.equals(Boolean.class) || fieldType.equals(boolean.class)) {
                    edit.putBoolean(key, (Boolean) value);
                } else if (fieldType.equals(String.class)) {
                    edit.putString(key, (String) value);
                }
            }
            edit.commit();
        }

    public static void saveId (Context context, String key, String value) {
        SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
        if (value == null) {
            edit.putString(key, null);
        } else {
            edit.putString(key,value);
        }
        edit.commit();
    }
        public static void saveEmail (Context context, String key, String value) {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
            if (value == null) {
                edit.putString(key, null);
            } else {
                edit.putString(key,value);
            }
            edit.commit();
        }
    public static String getEmail (Context context) {
        String email = Var.get(context).getString(Var.KEY_EMAIL,null);
        return email;
    }

        public static String getid (Context context) {
            String id = Var.get(context).getString(Var.KEY_ID,null);
            return id;
        }
        public static void remove(Context context, String key) {
            SharedPreferences.Editor edit = PreferenceManager.getDefaultSharedPreferences(context).edit();
            edit.remove(key);
            edit.commit();
        }




}
