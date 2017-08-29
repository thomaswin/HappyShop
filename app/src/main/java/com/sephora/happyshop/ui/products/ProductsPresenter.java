package com.sephora.happyshop.ui.products;

import android.support.annotation.NonNull;

import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.service.ProductManager;
import com.sephora.happyshop.data.source.LoadDataCallback;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;

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
        loadProducts(category, page, forceUpdate || firstLoad, true);
        firstLoad = false;
    }


    private void loadProducts(String category,
                              Integer page,
                              boolean forceUpdate,
                              final boolean showLoadingUI) {

        if (showLoadingUI) {
            productsView.setLoadingIndicator(true);
        }
        if (forceUpdate) {
            productManager.refreshProducts();
        }


        // EspressoIdlingResource.increment(); // App is busy until further notice
        productManager.getProductsByCategory(category, page, new LoadDataCallback<List<Product>>() {
            @Override
            public void onDataLoaded(List<Product> data) {

                final List<Product> productsToShow = new ArrayList<>();
                for (Product product : data) {
                    productsToShow.add(product);
                }

                Task.call(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {

                        if (productsView.isActive()) {
                            if (showLoadingUI) {
                                productsView.setLoadingIndicator(false);
                            }

                            if (productsToShow.isEmpty()) {
                                productsView.showNoProducts();
                            } else {
                                productsView.showProducts(productsToShow);
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

                        if (productsView.isActive()) {
                            productsView.showLoadingProductsError();
                        }
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);

            }
        });
    }
}
