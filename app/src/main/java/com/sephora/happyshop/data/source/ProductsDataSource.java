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

    void getProduct(@NonNull String productId,
                    @NonNull LoadDataCallback<Product> callback);

}

