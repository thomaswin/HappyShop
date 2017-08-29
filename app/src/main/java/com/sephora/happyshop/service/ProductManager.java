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

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sephora.happyshop.data.Category;
import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.data.source.LoadDataCallback;
import com.sephora.happyshop.data.source.ProductsDataSource;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.Callable;

import bolts.Task;

import static com.google.common.base.Preconditions.checkNotNull;


/**
 * Created by Thomas Win on 28/8/17.
 */

public class ProductManager implements ProductsDataSource {

    private static ProductManager INSTANCE = null;

    private final ProductsDataSource productsDataSource;

    Map<String, Product> cachedProducts;

    boolean cacheIsDirty = false;

    public static ProductManager getInstance(ProductsDataSource productsRmoteDataSource) {

        if (INSTANCE == null) {
            INSTANCE = new ProductManager(productsRmoteDataSource);
        }
        return INSTANCE;
    }

    private ProductManager(@NonNull ProductsDataSource tasksRemoteDataSource) {
        productsDataSource = checkNotNull(tasksRemoteDataSource);
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    @Override
    public void getProductCategories(@NonNull final LoadDataCallback<List<Category>> callback) {
        productsDataSource.getProductCategories(new LoadDataCallback<List<Category>>() {
            @Override
            public void onDataLoaded(List<Category> data) {
                callback.onDataLoaded(new ArrayList<>(data));
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    @Override
    public void getProductsByCategory(@NonNull final String name,
                                      @Nullable final Integer page,
                                      @NonNull final LoadDataCallback<List<Product>> callback) {
        checkNotNull(callback);

        if (cachedProducts != null && !cacheIsDirty) {
            callback.onDataLoaded(new ArrayList<>(cachedProducts.values()));
            return;
        }


        Task.callInBackground(new Callable<Void>() {
            @Override
            public Void call() throws Exception {

                productsDataSource.getProductsByCategory(name, page, new LoadDataCallback<List<Product>>() {
                    @Override
                    public void onDataLoaded(List<Product> data) {
                        refereshCache(data);
                        callback.onDataLoaded(new ArrayList<>(cachedProducts.values()));
                    }

                    @Override
                    public void onDataNotAvailable() {
                        callback.onDataNotAvailable();

                    }
                });
                return null;
            }
        });
    }

    @Override
    public void getProduct(@NonNull String productId, @NonNull final LoadDataCallback<Product> callback) {
        checkNotNull(callback);


        if (cachedProducts != null && !cachedProducts.isEmpty()) {
            Product cachedProduct = cachedProducts.get(productId);
            if (cachedProduct != null) {
                callback.onDataLoaded(cachedProduct);
                return;
            }
        }
        productsDataSource.getProduct(productId, new LoadDataCallback<Product>() {
            @Override
            public void onDataLoaded(Product product) {
                if (cachedProducts == null) {
                    cachedProducts = new LinkedHashMap<>();
                }
                cachedProducts.put(Integer.toString(product.id), product);
                callback.onDataLoaded(product);
            }

            @Override
            public void onDataNotAvailable() {
                callback.onDataNotAvailable();
            }
        });
    }

    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    public void getClearAllProducts() {

    }

    public void refreshProducts() {
        cacheIsDirty = true;
    }

    private void refereshCache(List<Product> products) {
        if (cachedProducts == null) {
            cachedProducts = new LinkedHashMap<>();
        }
        cachedProducts.clear();
        for (Product product : products) {
            cachedProducts.put(Integer.toString(product.id), product);
        }
        cacheIsDirty = false;
    }
}
