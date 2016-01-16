package com.tsums.forkr.core;

import android.content.Context;

import com.tsums.forkr.MainActivity;

import dagger.Module;

/**
 * Created by trevor on 1/16/16.
 */
@Module
public class ForkrCoreModule {

    private Context context;

    public ForkrCoreModule(Context context) {
        this.context = context;
    }

}
