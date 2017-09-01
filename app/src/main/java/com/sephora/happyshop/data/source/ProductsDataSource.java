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

package com.sephora.happyshop.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;

import com.sephora.happyshop.data.Category;
import com.sephora.happyshop.data.Product;

import java.util.List;

public interface ProductsDataSource {

    void getProductCategories(@NonNull LoadDataCallback<List<Category>> callback);

    void getProductsByCategory(@NonNull String name,
                               @Nullable Integer page,
                               @NonNull LoadDataCallback<List<Product>> callback);

    void getProduct(@NonNull int productId,
                    @NonNull LoadDataCallback<Product> callback);

}

