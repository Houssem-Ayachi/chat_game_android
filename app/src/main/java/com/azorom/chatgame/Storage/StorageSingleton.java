package com.azorom.chatgame.Storage;

import android.content.Context;

import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

public class StorageSingleton {
    RxDataStore<Preferences> dataStore;
    public static StorageSingleton instance = new StorageSingleton();
    private StorageSingleton(){}
    public void setStorage(Context context){
        this.dataStore = new RxPreferenceDataStoreBuilder(context, "cache")
                .build();
    }

    public RxDataStore<Preferences> getStorage(){
        return this.dataStore;
    }
}
