package com.tsums.forkr;

import android.app.Application;

import com.tsums.forkr.core.DaggerForkrComponent;
import com.tsums.forkr.core.ForkrComponent;
import com.tsums.forkr.core.ForkrCoreModule;

/**
 * Created by trevor on 1/16/16.
 */
public class ForkrApp extends Application {

    public ForkrComponent getComponent () {
        return component;
    }

    private ForkrComponent component;

    @Override
    public void onCreate () {
        super.onCreate();
        component = DaggerForkrComponent.builder().forkrCoreModule(new ForkrCoreModule(this)).build();
    }
}
