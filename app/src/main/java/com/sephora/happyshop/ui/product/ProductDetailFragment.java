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

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sephora.happyshop.R;
import com.sephora.happyshop.common.LogUtils;
import com.sephora.happyshop.data.Product;

import java.text.NumberFormat;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import butterknife.Unbinder;

public class ProductDetailFragment extends Fragment implements ProductDetailContract.View {
    private static final String TAG = LogUtils.makeLogTag(ProductDetailFragment.class);

    private static final String ARG_PRODUCT_ID = "category_name";

    @BindView(R.id.productImageView)
    ImageView productImageView;

    @BindView(R.id.productName)
    TextView productName;

    @BindView(R.id.productPrice)
    TextView productPrice;

    @BindView(R.id.productSale)
    TextView productSale;

    @BindView(R.id.productDescription)
    TextView productDescription;

    private String productId;
    private ProgressDialog dialog;
    private ProductDetailContract.Presenter presenter;
    private Unbinder unbinder;

    public ProductDetailFragment() {
    }

    public static ProductDetailFragment newInstance(String productId) {

        ProductDetailFragment fragment = new ProductDetailFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PRODUCT_ID, productId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            productId = getArguments().getString(ARG_PRODUCT_ID);
        }

        presenter.start();
        presenter.getProduct(productId);

    }

    @Override
    public void onResume() {
        super.onResume();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view =  inflater.inflate(R.layout.fragment_product_detail, container, false);
        unbinder = ButterKnife.bind(this, view);


        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ProductDetailContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

        if (active) {
            dialog = new ProgressDialog(getActivity());
            dialog.setProgressStyle(ProgressDialog.STYLE_SPINNER);
            dialog.setIndeterminate(true);
            dialog.setMessage("Please wait...");
            dialog.setCancelable(false);
            dialog.show();
        } else {
            if (dialog != null) {
                dialog.dismiss();
            }
        }
    }

    @Override
    public void showProduct(Product product) {

        productName.setText(product.name);
        productDescription.setText(product.description);

        // Currency formatter
        Locale locale = new Locale("en", "SG");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String displayMoney = currencyFormatter.format(product.price);


        productPrice.setText(displayMoney);
        productSale.setText(product.underSale ?
            getActivity().getString(R.string.product_on_sale) : "");


        /*
        ImageLoader imageLoader = ImageLoader.getInstance();
        imageLoader.loadImage(product.imgUrl, new SimpleImageLoadingListener() {
            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                if (loadedImage != null) {
                    productImageView.setImageBitmap(loadedImage);
                } else {
                    Toast.makeText(getActivity(), "Cannot loaded", Toast.LENGTH_SHORT).show();
                }
            }
        }); */




    }

    @Override
    public void showLoadingProductError() {
        showMessage(getString(R.string.product_error_loading));
    }

    @Override
    public void showNoProduct() {
        showMessage(getString(R.string.product_not_found));
    }

    @OnClick(R.id.addCardButton)
    public void onAddCartClicked(View view) {
        Toast.makeText(getContext(), "Added", Toast.LENGTH_SHORT).show();
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

}
