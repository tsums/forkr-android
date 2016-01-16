package com.tsums.forkr.core;

import com.tsums.forkr.MainActivity;

import dagger.Component;

/**
 * Created by trevor on 1/16/16.
 */
@Component(modules = ForkrCoreModule.class)
public interface ForkrComponent {

    void inject(MainActivity mainActivity);

}
