package com.sephora.happyshop.ui.category;

import android.support.annotation.NonNull;

import com.sephora.happyshop.ui.BasePresenter;
import com.sephora.happyshop.ui.BaseView;
import com.sephora.happyshop.data.Category;

import java.util.List;

/**
 * Created by Thomas Win on 28/8/17.
 */

public class CategoryContract {

    public interface View extends BaseView<Presenter> {

        boolean isActive();
        void setLoadingIndicator(boolean active);

        void showCategory(List<Category> categories);
        void showNoCategory();
        void showLoadingCategoriesError();

        void showCategoryDetailUi(@NonNull String categoryName);
    }

    interface Presenter extends BasePresenter {

        void result(int requestCode, int resultCode);
        void loadCategories();
        void openProductCategory(@NonNull Category requestedCategory);
    }

}
