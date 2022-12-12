package com.toppan.tpars.spacex.data.prefs;

import android.content.Context;
import android.content.SharedPreferences;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class AppPreferences implements PreferencesInterface {
    private static final String PREFS_NAME = "share_prefs";
    private static final String REMOVED_ID = "removed_id";
    private final SharedPreferences sharedPreferences;

    public AppPreferences(Context context) {
        sharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE);
    }

    @Override
    public List<String> getRemovedItemId() {
        List<String> idList = null;
        Set<String> idSet = sharedPreferences.getStringSet(REMOVED_ID, null);
        if (idSet != null) {
            idList = new ArrayList<>(idSet);
        }
        return idList;
    }

    @Override
    public void setRemovedItemId(String id) {
        List<String> idList = getRemovedItemId();
        if (idList == null) {
            idList = new ArrayList<>();
        }
        if (idList.contains(id)) {
            return;
        }
        idList.add(id);
        Set<String> idSet = new HashSet<>(idList);
        sharedPreferences.edit().putStringSet(REMOVED_ID, idSet).apply();
    }
}
