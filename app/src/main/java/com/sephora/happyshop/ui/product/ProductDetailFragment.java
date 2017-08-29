package com.sephora.happyshop.ui.product;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sephora.happyshop.R;
import com.sephora.happyshop.data.Product;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProductDetailFragment extends Fragment implements ProductDetailContract.View {

    public ProductDetailFragment() {
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_product_detail, container, false);
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ProductDetailContract.Presenter presenter) {

    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showProduct(Product product) {

    }

    @Override
    public void showLoadingProductError() {

    }

    @Override
    public void showNoProduct() {

    }

}
