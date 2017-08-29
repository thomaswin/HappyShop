package com.sephora.happyshop.ui.product;

import android.support.annotation.NonNull;

import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.ui.BasePresenter;
import com.sephora.happyshop.ui.BaseView;

/**
 * Created by Thomas Win on 29/8/17.
 */

public class ProductDetailContract {


    interface View extends BaseView<Presenter> {

        boolean isActive();
        void setLoadingIndicator(boolean active);

        void showLoadingProductError();
        void showNoProduct();

        void showProduct(Product product);

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);
        void getProduct(String productID);
        void addToCart(@NonNull Product requestedProduct);
    }
}
