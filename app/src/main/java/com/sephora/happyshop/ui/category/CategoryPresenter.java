package com.sephora.happyshop.ui.category;

import android.support.annotation.NonNull;

import com.sephora.happyshop.data.Category;
import com.sephora.happyshop.data.source.LoadDataCallback;
import com.sephora.happyshop.service.ProductManager;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import bolts.Task;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Thomas Win on 29/8/17.
 */

public class CategoryPresenter implements CategoryContract.Presenter {

    private final ProductManager productManager;
    private final CategoryContract.View categoryView;

    private List<Category> productCategories = new ArrayList<>();

    public CategoryPresenter(@NonNull ProductManager productManager, @NonNull CategoryContract.View categoryView) {
        this.productManager    = checkNotNull(productManager);
        this.categoryView      = checkNotNull(categoryView);

        this.categoryView.setPresenter(this);
    }

    @Override
    public void start() {
        loadCategories();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void loadCategories() {

    }

    @Override
    public void openProductCategory(@NonNull Category requestedCategory) {
        categoryView.setLoadingIndicator(true);
        productManager.getProductCategories(new LoadDataCallback<List<Category>>() {
            @Override
            public void onDataLoaded(final List<Category> data) {

                final List<Category> categoriesToShow = new ArrayList<>();
                for (Category category : data) {
                    categoriesToShow.add(category);
                }

                Task.call(new Callable<Void>() {
                    @Override
                    public Void call() throws Exception {

                        if (categoryView.isActive()) {
                            categoryView.setLoadingIndicator(false);


                            if (productCategories.isEmpty()) {
                                categoryView.showNoCategory();

                            } else {
                                categoryView.showCategory(categoriesToShow);
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
                        if (categoryView.isActive()) {
                            categoryView.showLoadingCategoriesError();
                        }
                        return null;
                    }
                }, Task.UI_THREAD_EXECUTOR);
            }
        });
    }
}