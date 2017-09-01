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

package com.sephora.happyshop.service;

import android.content.Context;

import com.google.common.collect.Lists;
import com.sephora.happyshop.data.Category;
import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.data.source.LoadDataCallback;
import com.sephora.happyshop.data.source.ProductsDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

/**
 * Created by Tun Lin on 8/29/17.
 */
public class ProductManagerTest {

    private static List<Category> CATEGORIES;
    private static List<Product> PRODUCTS;

    @Mock
    private Context context;

    @Mock
    private ProductsDataSource productsDataSource;

    @Mock
    private LoadDataCallback<List<Category>> loadCategoryCallback;

    @Mock
    private LoadDataCallback<List<Product>> loadProductsCallback;

    @Captor
    private ArgumentCaptor<LoadDataCallback<List<Category>>> loadCategoryCallbackCaptor;

    @Captor
    private ArgumentCaptor<LoadDataCallback<List<Product>>> loadProductsCallbackCaptor;

    private ProductManager productManager;

    @Before
    public void setupTasksRepository() {

        MockitoAnnotations.initMocks(this);
        productManager = ProductManager.getInstance(productsDataSource);

        CATEGORIES = Lists.newArrayList(
            new Category("category_1"),
            new Category("category_2"),
            new Category("category_2"));

        PRODUCTS = Lists.newArrayList(
            new Product(1, "Name1", "Category1", 10.00, "image_url1", "Description1", false),
            new Product(2, "Name2", "Category2", 11.00, "image_url2", "Description2", false),
            new Product(3, "Name2", "Category2", 12.00, "image_url3", "Description3", true));
    }

    @After
    public void destroyRepositoryInstance() {
        ProductManager.destroyInstance();
    }

    @Test
    public void getProductCategories_data_loaded() throws Exception {

        productManager.getProductCategories(loadCategoryCallback);
        verify(productsDataSource).getProductCategories(loadCategoryCallbackCaptor.capture());

        loadCategoryCallbackCaptor.getValue().onDataLoaded(CATEGORIES);
        verify(productsDataSource).getProductCategories(any(LoadDataCallback.class));

        ArgumentCaptor<List> productsArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(loadCategoryCallback).onDataLoaded(productsArgumentCaptor.capture());
        assertTrue(productsArgumentCaptor.getValue().size() == 3);
    }

    @Test
    public void getProductCategories_data_not_available() throws Exception {

        productManager.getProductCategories(loadCategoryCallback);
        verify(productsDataSource).getProductCategories(loadCategoryCallbackCaptor.capture());

        loadCategoryCallbackCaptor.getValue().onDataNotAvailable();
        verify(productsDataSource).getProductCategories(any(LoadDataCallback.class));

        verify(loadCategoryCallback).onDataNotAvailable();

    }

    @Test
    public void getProductsByCategory() throws Exception {

        String category = "category_1";
        int page = 1;
        productManager.getProductsByCategory(category, page, loadProductsCallback);

        // TODO issue with bolts task callback thread
        /*
        verify(productsDataSource).getProductsByCategory(
            eq(category),
            eq(page),
            loadProductsCallbackCaptor.capture());

        loadProductsCallbackCaptor.getValue().onDataLoaded(PRODUCTS);
        */
    }

    @Test
    public void getProduct() throws Exception {

    }
}