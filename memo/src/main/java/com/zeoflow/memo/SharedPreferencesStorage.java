package com.zeoflow.memo;

import android.content.Context;
import android.content.SharedPreferences;

final class SharedPreferencesStorage implements IStorage
{

    private final SharedPreferences preferences;

    SharedPreferencesStorage(Context context, String tag)
    {
        preferences = context.getSharedPreferences(tag, Context.MODE_PRIVATE);
    }

    SharedPreferencesStorage(SharedPreferences preferences)
    {
        this.preferences = preferences;
    }

    @Override
    public <T> boolean put(String key, T value)
    {
        MemoUtils.checkNull("key", key);
        return getEditor().putString(key, String.valueOf(value)).commit();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <T> T get(String key)
    {
        return (T) preferences.getString(key, null);
    }

    @Override
    public boolean delete(String key)
    {
        return getEditor().remove(key).commit();
    }

    @Override
    public boolean contains(String key)
    {
        return preferences.contains(key);
    }

    @Override
    public boolean deleteAll()
    {
        return getEditor().clear().commit();
    }

    @Override
    public long count()
    {
        return preferences.getAll().size();
    }

    private SharedPreferences.Editor getEditor()
    {
        return preferences.edit();
    }

}
