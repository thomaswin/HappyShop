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

package com.sephora.happyshop.ui.product;

import android.support.annotation.NonNull;

import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.data.source.LoadDataCallback;
import com.sephora.happyshop.service.ProductManager;

import java.util.concurrent.Callable;

import bolts.Task;

/**
 * Created by Thomas Win on 29/8/17.
 */

public class ProductDetailPresenter implements ProductDetailContract.Presenter {

    private final ProductManager productManager;
    private final ProductDetailContract.View productDetailView;

    public ProductDetailPresenter(ProductManager productManager, ProductDetailContract.View productDetailView) {

        this.productManager = productManager;
        this.productDetailView = productDetailView;
        this.productDetailView.setPresenter(this);

    }
    @Override
    public void start() {

    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void getProduct(String productID) {

        productDetailView.setLoadingIndicator(true);

        productManager.getProduct(productID, new LoadDataCallback<Product>() {
            @Override
            public void onDataLoaded(final Product data) {

                Task.call(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {

                        if (productDetailView.isActive()) {

                            productDetailView.setLoadingIndicator(false);

                            if (data == null) {
                                productDetailView.showNoProduct();
                            } else {
                                productDetailView.showProduct(data);
                            }
                        }
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
            }

            @Override
            public void onDataNotAvailable() {
                Task.call(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {

                        if (productDetailView.isActive()) {
                            productDetailView.setLoadingIndicator(false);
                            productDetailView.showLoadingProductError();
                        }
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
            }
        });

    }

    @Override
    public void addToCart(@NonNull Product requestedProduct) {

    }
}
