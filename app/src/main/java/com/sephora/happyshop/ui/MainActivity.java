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

package com.sephora.happyshop.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.view.Menu;
import android.view.MenuItem;

import com.sephora.happyshop.Injection;
import com.sephora.happyshop.R;
import com.sephora.happyshop.ui.category.CategoryContract;
import com.sephora.happyshop.ui.category.CategoryFragment;
import com.sephora.happyshop.ui.category.CategoryPresenter;
import com.sephora.happyshop.util.ActivityUtils;

public class MainActivity extends ActivityBase {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener);

        Fragment categoryFragment = getSupportFragmentManager().findFragmentById(R.id.content);
        if (categoryFragment == null) {
            categoryFragment = CategoryFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(), categoryFragment, R.id.content);
        }

        new CategoryPresenter(Injection.provideProductsRepository(getApplicationContext()),
            (CategoryContract.View) categoryFragment);

        /*
        ProductsFragment productsFragment =
            (ProductsFragment) getSupportFragmentManager().findFragmentById(R.id.content);

        if (productsFragment == null) {
            productsFragment = ProductsFragment.newInstance("Skincare");
            ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(), productsFragment, R.id.content);
        }

        ProductsPresenter productsPresenter = new ProductsPresenter(
            Injection.provideProductsRepository(getApplicationContext()),
            productsFragment);
        */

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }


    // - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - - -
    private BottomNavigationView.OnNavigationItemSelectedListener mOnNavigationItemSelectedListener
        = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_favourite:
                    // textMessage.setText(R.string.title_favourite);
                    return true;
                case R.id.navigation_categories:
                    // textMessage.setText(R.string.title_categories);
                    return true;
                case R.id.navigation_whishlist:
                    // textMessage.setText(R.string.title_wishlist);
                    return true;
            }
            return false;
        }

    };


}