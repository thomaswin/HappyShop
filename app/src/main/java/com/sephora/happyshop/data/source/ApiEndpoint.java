package com.sephora.happyshop.data.source;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.Log;

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

    private static final String TAG = ApiEndpoint.class.getSimpleName();

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

            Log.i(TAG, "Product URL : " + productUrl);

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
