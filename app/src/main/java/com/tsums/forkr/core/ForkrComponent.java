package com.tsums.forkr.core;

import com.tsums.forkr.activities.LoginActivity;
import com.tsums.forkr.activities.MainActivity;

import dagger.Component;

/**
 * Created by trevor on 1/16/16.
 */
@Component(modules = ForkrCoreModule.class)
public interface ForkrComponent {

    void inject(LoginActivity activity);

}
