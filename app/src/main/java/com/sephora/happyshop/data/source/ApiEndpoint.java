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

import com.sephora.happyshop.common.LogUtils;
import com.sephora.happyshop.data.Category;
import com.sephora.happyshop.data.Product;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Thomas Win on 28/8/17.
 */

public class ApiEndpoint implements ProductsDataSource {

    private static final String TAG = LogUtils.makeLogTag(ApiEndpoint.class);

    private static ApiEndpoint INSTANCE;

    public static ApiEndpoint getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new ApiEndpoint();
        }
        return INSTANCE;
    }

    private ApiEndpoint() {

    }

    @Override
    public void getProductCategories(@NonNull LoadDataCallback<List<Category>> callback) {
        // TODO check networking
        LogUtils.LOGD(TAG, "getProductCategories");

        // default categories
        List<Category> categories = new ArrayList<>();
        categories.add(new Category("Makeup"));
        categories.add(new Category("Tools"));
        categories.add(new Category("Skincare"));
        categories.add(new Category("Bath & Body"));
        categories.add(new Category("Nails"));
        categories.add(new Category("Men"));
        callback.onDataLoaded(categories);
    }

    @Override
    public void getProductsByCategory(@NonNull String categoryName,
                                      @Nullable Integer page,
                                      @NonNull final LoadDataCallback<List<Product>> callback) {

        try {
            OkHttpClient client = new OkHttpClient()
                .newBuilder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .readTimeout(10, TimeUnit.SECONDS)
                .build();

            String productUrl = "http://sephora-mobile-takehome-apple.herokuapp.com/api/v1/products.json?category=" + categoryName;
            if (page != null) {
                productUrl += "&page=" + Integer.toString(page);
            }


            LogUtils.LOGD(TAG, "Product URL : " + productUrl);

            Request request = new Request.Builder()
                .url(productUrl)
                .build();
            Response response = client.newCall(request).execute();
            String responseString = response.body().string();


            JSONObject jsonObject   = new JSONObject(responseString);
            JSONArray jsonArray     = jsonObject.getJSONArray("products");

            List<Product> products = new ArrayList<>();

            for (int i = 0; i < jsonArray.length(); i++) {
                
                JSONObject json     = jsonArray.getJSONObject(i);
                
                int id              = json.getInt("id");
                String name         = json.getString("name");
                String category     = json.getString("category");
                String imageUrl     = json.getString("img_url");
                Boolean underSale   = json.getBoolean("under_sale");
                double price        = json.getDouble("price");
                String description  = json.optString("description");

                Product product = new Product(id, name, category, price, imageUrl, description, underSale);
                products.add(product);
            }

            callback.onDataLoaded(products);
            return;

        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        callback.onDataNotAvailable();
    }

    @Override
    public void getProduct(@NonNull String productId, @NonNull LoadDataCallback<Product> callback) {

    }
}
