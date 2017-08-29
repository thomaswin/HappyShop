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
