package com.azorom.chatgame.Storage;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class Storage {

    public Storage(){
    }

    public void saveKey(String accessKey){
        Preferences.Key<String> key = PreferencesKeys.stringKey("accessKey");
        Single<Preferences> updateResult = StorageSingleton.instance.getStorage()
                .updateDataAsync(prefsIn -> {
                    MutablePreferences mutablePrefs = prefsIn.toMutablePreferences();
                    mutablePrefs.set(key, accessKey);
            return Single.just(mutablePrefs);
        });
    }

    public String getKey(){
        Preferences.Key<String> key = PreferencesKeys.stringKey("accessKey");
        Flowable<Boolean> accessKeyExistsF =
                StorageSingleton.instance.getStorage().data()
                        .map(prefs -> prefs.contains(key));
        if(!accessKeyExistsF.blockingFirst()){
            return "";
        }
        Flowable<String> accessKey =
                StorageSingleton.instance.getStorage().data()
                        .map(prefs -> prefs.get(key));
        return accessKey.blockingFirst();
    }

    public void removeKey(){
        Preferences.Key<String> key = PreferencesKeys.stringKey("accessKey");
        Single<Preferences> updateResult = StorageSingleton.instance.getStorage()
                .updateDataAsync(prefsIn -> {
                    MutablePreferences mutablePrefs = prefsIn.toMutablePreferences();
                    mutablePrefs.set(key, "");
            return Single.just(mutablePrefs);
        });
        updateResult.blockingGet();
    }
}
