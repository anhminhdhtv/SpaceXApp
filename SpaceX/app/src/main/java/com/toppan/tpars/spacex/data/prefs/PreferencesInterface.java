package com.toppan.tpars.spacex.data.prefs;

import java.util.List;

public interface PreferencesInterface {
    void setRemovedItemId(String id);
    List<String> getRemovedItemId();
}
