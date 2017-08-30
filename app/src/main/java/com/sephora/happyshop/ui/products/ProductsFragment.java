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

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sephora.happyshop.R;
import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.ui.product.ProductDetailActivity;

import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Preconditions.checkNotNull;

public class ProductsFragment extends Fragment implements ProductsContract.View {

    private static final String TAG = ProductsFragment.class.getSimpleName();

    private static final String ARG_CATEGORY_NAME = "category_name";

    private String category;
    private List<Product> products = new ArrayList<>();

    private ProductsContract.Presenter presenter;

    private ProductViewAdapter viewAdapter;
    private GridLayoutManager layoutManager;
    private SwipeRefreshLayout swipeRefreshLayout;

    public ProductsFragment() {

    }

    public static ProductsFragment newInstance(String category) {

        ProductsFragment fragment = new ProductsFragment();
        Bundle args = new Bundle();
        args.putString(ARG_CATEGORY_NAME, category);
        fragment.setArguments(args);

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            category = getArguments().getString(ARG_CATEGORY_NAME);
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        if (products.isEmpty()) {
            presenter.start();
            presenter.loadProducts(category, 1, true);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_product_list, container, false);

        Context context = view.getContext();

        swipeRefreshLayout = view.findViewById(R.id.refresh_layout);
        RecyclerView recyclerView = view.findViewById(R.id.product_list);
        swipeRefreshLayout.setEnabled(false);

        layoutManager = new GridLayoutManager(context, 2);
        recyclerView.setLayoutManager(layoutManager);

        viewAdapter = new ProductViewAdapter(getContext(), products, new OnProductsFragmentListener() {
            @Override
            public void onProductSelected(Product item) {
                showProductDetailsUi(item.id);
            }
        });

        recyclerView.setAdapter(viewAdapter);

        recyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                if (swipeRefreshLayout.isRefreshing()) {
                    return;
                }

                int visibleItemCount    = layoutManager.getChildCount();
                int totalItemCount      = layoutManager.getItemCount();
                int pastVisibleItems    = layoutManager.findFirstVisibleItemPosition();
                if (pastVisibleItems + visibleItemCount >= totalItemCount) {
                    int page = products.size() / 10;
                    if (page == 0) {
                        presenter.loadProducts(category, 1, false);
                    } else {
                        presenter.loadProducts(category, page, false);
                    }

                }
            }
        });
        return view;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(ProductsContract.Presenter presenter) {
        this.presenter = checkNotNull(presenter);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        presenter.result(requestCode, resultCode);
    }

    @Override
    public void setLoadingIndicator(final boolean active) {
        if (getView() == null) {
            return;
        }

        swipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                swipeRefreshLayout.setRefreshing(active);
            }
        });
        swipeRefreshLayout.isRefreshing();
    }

    @Override
    public void showLoadingProductsError() {
        showMessage(getString(R.string.product_error_loading));
    }

    @Override
    public void showNoProducts() {
        showMessage(getString(R.string.product_not_found));
    }

    @Override
    public void showProducts(List<Product> data) {
        int position = products.size();
        products.addAll(data);
        viewAdapter.notifyItemChanged(position);
        showMessage("Product updated.." + data.size() + "/" + products.size());
    }

    @Override
    public void showProductDetailsUi(int productId) {
        Intent intent = ProductDetailActivity.getIntent(getContext(), Integer.toString(productId));
        startActivity(intent);
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    public interface OnProductsFragmentListener {
        void onProductSelected(Product item);
    }

}
