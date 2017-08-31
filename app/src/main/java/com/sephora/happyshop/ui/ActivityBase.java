/*
 *  Copyright 2017, Tun Lin
 *
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.sephora.happyshop.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;

import com.github.pwittchen.reactivenetwork.library.rx2.ReactiveNetwork;
import com.sephora.happyshop.common.LogUtils;
import com.sephora.happyshop.common.logger.Log;
import com.sephora.happyshop.common.logger.LogWrapper;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

/**
 * Created by Tun Lin on 8/29/17.
 */

public class ActivityBase extends AppCompatActivity {

    private static final String TAG = LogUtils.makeLogTag(ActivityBase.class);
    private Snackbar snakbar;
    public Boolean isConnected;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected  void onStart() {
        super.onStart();
        initializeLogging();
        checkConnectivity();
    }

    @Override
    protected void onStop() {
        super.onStop();

    }

    private void checkConnectivity() {

        ReactiveNetwork.observeInternetConnectivity()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(new Consumer<Boolean>() {
            @Override
            public void accept(Boolean aBoolean) throws Exception {
                isConnected = aBoolean;
                if (!aBoolean) {
                    snakbar = Snackbar.make(findViewById(android.R.id.content),
                        "No connection", Snackbar.LENGTH_INDEFINITE);
                    snakbar.show();
                } else {
                    if (snakbar != null) {
                        snakbar.dismiss();
                        snakbar = null;
                    }
                }
            }
        });
    }


    @Override
    protected void onResume() {
        super.onResume();
    }

    public void initializeLogging() {
        LogWrapper logWrapper = new LogWrapper();
        Log.setLogNode(logWrapper);
        Log.i(TAG, "Ready");
    }

}
