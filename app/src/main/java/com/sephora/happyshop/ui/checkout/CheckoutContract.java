package com.sephora.happyshop.ui.checkout;

import android.support.annotation.NonNull;

import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.ui.BasePresenter;
import com.sephora.happyshop.ui.BaseView;

import java.util.List;

/**
 * Created by Tun Lin on 8/30/17.
 */

public class CheckoutContract {

    public interface View extends BaseView<Presenter> {

        boolean isActive();
        void setLoadingIndicator(boolean active);

        void showLoadingCartError();
        void showNoCartInfo();
        void showCartInfo(List<Product> productList);

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);
        void getCartInfo();
        void clearCart();
        void makePayment(@NonNull double total);
    }
}
