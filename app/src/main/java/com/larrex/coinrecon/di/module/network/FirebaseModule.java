package com.larrex.coinrecon.di.module.network;

import com.google.firebase.auth.FirebaseAuth;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.components.ViewModelComponent;
import dagger.hilt.android.scopes.ViewModelScoped;
import dagger.hilt.migration.DisableInstallInCheck;

@Module
@InstallIn(ViewModelComponent.class)
public class FirebaseModule {

    @Provides
    public static FirebaseAuth firebaseAuth(){

        return FirebaseAuth.getInstance();
    }
}
