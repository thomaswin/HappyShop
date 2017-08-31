package com.sephora.happyshop.ui.checkout;

import android.support.annotation.NonNull;

import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.data.source.LoadDataCallback;
import com.sephora.happyshop.service.CartManager;

import java.util.Collections;
import java.util.List;

/**
 * Created by Tun Lin on 8/30/17.
 */

public class CheckoutPresenter implements CheckoutContract.Presenter {

    private final CartManager manager;
    private final CheckoutContract.View checkoutView;

    public CheckoutPresenter(CartManager manager, CheckoutContract.View checkoutView) {
        this.manager = manager;
        this.checkoutView = checkoutView;
        this.checkoutView.setPresenter(this);
    }


    @Override
    public void start() {
        getCartInfo();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void getCartInfo() {
        checkoutView.setLoadingIndicator(true);
        manager.getProductInCart(new LoadDataCallback<List<Product>>() {
            @Override
            public void onDataLoaded(final List<Product> data) {
                if (checkoutView.isActive()) {
                    checkoutView.setLoadingIndicator(false);

                    if (data == null) {
                        checkoutView.showNoCartInfo();
                    } else {
                        checkoutView.showCartInfo(data);
                    }
                }
            }

            @Override
            public void onDataNotAvailable() {
                if (checkoutView.isActive()) {
                    checkoutView.setLoadingIndicator(false);
                    checkoutView.showLoadingCartError();
                }
            }
        });
    }

    @Override
    public void clearCart() {
        manager.clearCart();
        checkoutView.showCartInfo(Collections.EMPTY_LIST);
    }

    @Override
    public void makePayment(@NonNull double total) {

    }

}
