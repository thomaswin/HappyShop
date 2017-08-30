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

package com.sephora.happyshop.service;

import com.sephora.happyshop.common.LogUtils;
import com.sephora.happyshop.common.logger.Log;
import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.data.source.LoadDataCallback;

import java.util.List;

import io.realm.Realm;
import io.realm.RealmResults;
import io.realm.Sort;


/**
 * Created by Thomas Win on 29/8/17.
 */

public class CartManager {

    private static final String TAG = LogUtils.makeLogTag(CartManager.class);

    private static CartManager INSTANCE = null;

    public static CartManager getInstance() {

        if (INSTANCE == null) {
            INSTANCE = new CartManager();
        }
        return INSTANCE;
    }

    public void addProductToCard(Product product) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        Product realProduct = realm.copyToRealm(product);
        Log.d(TAG, "Product in Real : " + realProduct.toString());

        realm.commitTransaction();
        getProductInCart(new LoadDataCallback<List<Product>>() {
            @Override
            public void onDataLoaded(List<Product> data) {
                Log.d(TAG, "Products : " + data.toString());

            }

            @Override
            public void onDataNotAvailable() {
                Log.d(TAG, "onDataNotAvailable");
            }
        });
    }

    public void getProductInCart(LoadDataCallback<List<Product>> callback) {
        Realm realm = Realm.getDefaultInstance();
        RealmResults<Product> result = realm.where(Product.class)
                .findAllAsync();
        result = result.sort("id", Sort.DESCENDING);
        callback.onDataLoaded(realm.copyFromRealm(result));
    }

    public void clearCart() {
        Realm realm = Realm.getDefaultInstance();
        realm.executeTransaction(new Realm.Transaction() {
            @Override
            public void execute(Realm realm) {
                RealmResults<Product> result = realm.where(Product.class)
                        .findAll();
                result.deleteAllFromRealm();
            }
        });
    }
}