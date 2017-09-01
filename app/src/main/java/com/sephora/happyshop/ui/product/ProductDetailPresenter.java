/*
 *
 *  *  Copyright 2017, Tun Lin
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *  http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

package com.sephora.happyshop.ui.product;

import android.support.annotation.NonNull;

import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.data.source.LoadDataCallback;
import com.sephora.happyshop.service.CartManager;
import com.sephora.happyshop.service.ProductManager;

/**
 * Created by Thomas Win on 29/8/17.
 */

public class ProductDetailPresenter implements ProductDetailContract.Presenter {

    private int productId;
    private final ProductManager productManager;
    private final CartManager cardManager;
    private final ProductDetailContract.View productDetailView;

    public ProductDetailPresenter(int productId,
                                  ProductManager productManager,
                                  CartManager cardManager,
                                  ProductDetailContract.View productDetailView) {
        this.productId = productId;

        this.productManager = productManager;
        this.cardManager = cardManager;
        this.productDetailView = productDetailView;
        this.productDetailView.setPresenter(this);

    }
    @Override
    public void start() {
        getProduct(productId);
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    private void getProduct(int productID) {

        productDetailView.setLoadingIndicator(true);

        productManager.getProduct(productID, new LoadDataCallback<Product>() {
            @Override
            public void onDataLoaded(final Product data) {

                if (productDetailView.isActive()) {

                    productDetailView.setLoadingIndicator(false);

                    if (data == null) {
                        productDetailView.showNoProduct();
                    } else {
                        productDetailView.showProduct(data);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (productDetailView.isActive()) {
                    productDetailView.setLoadingIndicator(false);
                    productDetailView.showLoadingProductError();
                }
            }
        });

    }

    @Override
    public void addToCart(@NonNull Product requestedProduct) {
        cardManager.addProductToCart(requestedProduct);
        productDetailView.showProductAddedToCart();
    }
}
