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

package com.sephora.happyshop.ui.products;

import android.support.annotation.NonNull;

import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.data.source.LoadDataCallback;
import com.sephora.happyshop.service.ProductManager;
import com.sephora.happyshop.util.EspressoIdlingResource;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Thomas Win on 28/8/17.
 */

public class ProductsPresenter implements ProductsContract.Presenter {

    private final ProductManager productManager;
    private final ProductsContract.View productsView;
    private boolean firstLoad;

    public ProductsPresenter(@NonNull ProductManager productManager,
                             @NonNull ProductsContract.View productsView) {

        this.productManager = checkNotNull(productManager);
        this.productsView = checkNotNull(productsView);

        this.productsView.setPresenter(this);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void start() {

    }

    @Override
    public void loadProducts(String category, Integer page, boolean forceUpdate) {

        productsView.setLoadingIndicator(true);

        EspressoIdlingResource.increment(); // App is busy until further notice
        productManager.getProductsByCategory(category, page, new LoadDataCallback<List<Product>>() {
            @Override
            public void onDataLoaded(List<Product> data) {

                if (!EspressoIdlingResource.getIdlingResource().isIdleNow()) {
                    EspressoIdlingResource.decrement(); // Set app as idle.
                }

                final List productsToShow = new ArrayList<>();
                for (Product product : data) {
                    productsToShow.add(product);
                }

                if (productsView.isActive()) {
                    productsView.setLoadingIndicator(false);

                    if (productsToShow.isEmpty()) {
                        productsView.showNoProducts();
                    } else {
                        productsView.showProducts(productsToShow);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {

                if (productsView.isActive()) {
                    productsView.setLoadingIndicator(false);
                    productsView.showLoadingProductsError();
                }
            }
        });
    }

}
