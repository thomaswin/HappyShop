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

package com.sephora.happyshop.ui.product;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import com.sephora.happyshop.Injection;
import com.sephora.happyshop.R;
import com.sephora.happyshop.common.LogUtils;
import com.sephora.happyshop.ui.ActivityBase;
import com.sephora.happyshop.util.ActivityUtils;

public class ProductDetailActivity extends ActivityBase {

    private static final String TAG = LogUtils.makeLogTag(ProductDetailActivity.class);

    public static final String EXTRA_PRODUCT_ID = "PRODUCT_ID";
    private String productId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_product_detail);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);

        if (getIntent() != null) {
            productId = getIntent().getStringExtra(EXTRA_PRODUCT_ID);
        }

        ProductDetailFragment fragment = ProductDetailFragment.newInstance(productId);
        ActivityUtils.addFragmentToActivity(
            getSupportFragmentManager(), fragment, R.id.contentPanel);

        new ProductDetailPresenter(Injection.provideProductsRepository(getApplicationContext()), fragment);

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        int id = item.getItemId();
        if (item != null && id == android.R.id.home) {
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public static Intent getIntent(Context context, String productId) {
        Intent intent = new Intent(context, ProductDetailActivity.class);
        intent.putExtra(ProductDetailActivity.EXTRA_PRODUCT_ID, productId);
        return intent;
    }
}
