package com.sephora.happyshop.ui.products;

import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.ui.BasePresenter;
import com.sephora.happyshop.ui.BaseView;

import java.util.List;

/**
 * Created by Thomas Win on 28/8/17.
 */

public class ProductsContract {

    interface View extends BaseView<Presenter> {
        boolean isActive();
        void setLoadingIndicator(boolean active);

        void showLoadingProductsError();
        void showNoProducts();

        void showProducts(List<Product> products);
        void showProductDetailsUi(int productId);

    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);
        void loadProducts(String category, Integer page, boolean forceUpdate);
    }
}
