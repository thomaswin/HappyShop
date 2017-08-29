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

import com.sephora.happyshop.data.source.LoadDataCallback;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;

/**
 * Created by Tun Lin on 8/29/17.
 */
public class ProductManagerTest {

    @Mock
    private Context mContext;

    @Mock
    private LoadDataCallback loadDataCallback;

    @Captor
    private ArgumentCaptor<LoadDataCallback> mTasksCallbackCaptor;

    @Test
    public void getProductCategories() throws Exception {

    }

    @Test
    public void getProductsByCategory() throws Exception {

    }

    @Test
    public void getProduct() throws Exception {

    }

    @Test
    public void getClearAllProducts() throws Exception {

    }

    @Test
    public void refreshProducts() throws Exception {

    }

}