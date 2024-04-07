package com.azorom.chatgame.Storage;

import android.content.Context;

import androidx.datastore.preferences.core.MutablePreferences;
import androidx.datastore.preferences.core.Preferences;
import androidx.datastore.preferences.core.PreferencesKeys;
import androidx.datastore.preferences.rxjava2.RxPreferenceDataStoreBuilder;
import androidx.datastore.rxjava2.RxDataStore;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class Storage {

    Context context;

    public Storage(Context context){
        this.context = context;
    }

    public void saveKey(String accessKey){
        RxDataStore<Preferences> dataStore =
                new RxPreferenceDataStoreBuilder(context, "cache")
                        .build();
        Preferences.Key<String> key = PreferencesKeys.stringKey("accessKey");
        Single<Preferences> updateResult = dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePrefs = prefsIn.toMutablePreferences();
            mutablePrefs.set(key, accessKey);
            return Single.just(mutablePrefs);
        });
    }

    public String getKey(){
        RxDataStore<Preferences> dataStore =
                new RxPreferenceDataStoreBuilder(context, "cache")
                        .build();
        Preferences.Key<String> key = PreferencesKeys.stringKey("s");
        Flowable<Boolean> accessKeyExistsF =
                dataStore.data()
                        .map(prefs -> prefs.contains(key));
        if(!accessKeyExistsF.blockingFirst()){
            return "nothing";
        }
        Flowable<String> accessKey =
                dataStore.data()
                        .map(prefs -> prefs.get(key));
        return accessKey.blockingFirst();
    }

    public void removeKey(){
        RxDataStore<Preferences> dataStore =
                new RxPreferenceDataStoreBuilder(context.getApplicationContext(), "cache")
                        .build();
        Preferences.Key<String> key = PreferencesKeys.stringKey("accessKey");
        Single<Preferences> updateResult = dataStore.updateDataAsync(prefsIn -> {
            MutablePreferences mutablePrefs = prefsIn.toMutablePreferences();
            mutablePrefs.set(key, "");
            return Single.just(mutablePrefs);
        });
    }
}
