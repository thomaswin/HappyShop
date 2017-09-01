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


import com.google.common.collect.Lists;
import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.data.source.LoadDataCallback;
import com.sephora.happyshop.service.ProductManager;

import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static junit.framework.Assert.assertTrue;
import static org.mockito.Matchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

/**
 * Created by Tun Lin on 8/29/17.
 */
public class ProductsPresenterTest {

    private static List<Product> PRODUCTS;

    @Mock
    private ProductManager productManager;

    @Mock
    private ProductsContract.View productsView;

    @Captor
    private ArgumentCaptor<LoadDataCallback<List<Product>>> loadProductsCallbackCaptor;

    @Captor
    private ArgumentCaptor<ArrayList<Product>> showProductsArgumentCaptor;

    private ProductsPresenter productsPresenter;

    @Before
    public void setupProductsPresenter() {

        MockitoAnnotations.initMocks(this);

        productsPresenter = new ProductsPresenter(productManager, productsView);

        when(productsView.isActive()).thenReturn(true);

        PRODUCTS = Lists.newArrayList(
            new Product(1, "Name1", "Category1", 10.00, "image_url1", "Description1", false),
            new Product(2, "Name2", "Category2", 11.00, "image_url2", "Description2", false),
            new Product(3, "Name2", "Category2", 12.00, "image_url3", "Description3", true));
    }

    @Test
    public void createPresenter_setsThePresenterToView() {
        productsPresenter = new ProductsPresenter(productManager, productsView);
        verify(productsView).setPresenter(productsPresenter);
    }

    @Test
    public void loadAllProducts_From_ProductManager_And_LoadIntoView_No_Product() {

        String category = "hair";
        Integer page = 1;

        productsPresenter.loadProducts(category, page, true);

        verify(productManager).getProductsByCategory(
            eq(category),
            eq(page),
            loadProductsCallbackCaptor.capture());
        loadProductsCallbackCaptor.getValue().onDataLoaded(Collections.EMPTY_LIST);

        InOrder inOrder = inOrder(productsView);
        inOrder.verify(productsView).setLoadingIndicator(true);
        inOrder.verify(productsView).setLoadingIndicator(false);

        verify(productsView).showNoProducts();

    }

    @Test
    public void loadAllProducts_From_ProductManager_And_LoadIntoView_Products() {

        String category = "hair";
        Integer page = 1;

        productsPresenter.loadProducts(category, page, true);

        verify(productManager).getProductsByCategory(
            eq(category),
            eq(page),
            loadProductsCallbackCaptor.capture());

        loadProductsCallbackCaptor.getValue().onDataLoaded(PRODUCTS);

        InOrder inOrder = inOrder(productsView);
        inOrder.verify(productsView).setLoadingIndicator(true);
        inOrder.verify(productsView).setLoadingIndicator(false);

        ArgumentCaptor<List> showProductsArgumentCaptor = ArgumentCaptor.forClass(List.class);
        verify(productsView).showProducts(showProductsArgumentCaptor.capture());
        assertTrue(showProductsArgumentCaptor.getValue().size() == 3);
    }

    @Test
    public void unavailableProducts_ShowsError() {
        String category = "hair";
        Integer page = 1;

        productsPresenter.loadProducts(category, page, true);

        verify(productManager).getProductsByCategory(
            eq(category),
            eq(page),
            loadProductsCallbackCaptor.capture());
        loadProductsCallbackCaptor.getValue().onDataNotAvailable();

        verify(productsView).showLoadingProductsError();
    }
}