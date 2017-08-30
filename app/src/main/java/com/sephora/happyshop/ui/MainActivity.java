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
import android.support.v4.app.FragmentTransaction;
import android.view.Menu;
import android.view.MenuItem;

import com.sephora.happyshop.Injection;
import com.sephora.happyshop.R;
import com.sephora.happyshop.data.Category;
import com.sephora.happyshop.ui.category.CategoryContract;
import com.sephora.happyshop.ui.category.CategoryFragment;
import com.sephora.happyshop.ui.category.CategoryPresenter;
import com.sephora.happyshop.ui.checkout.CheckoutFragment;
import com.sephora.happyshop.ui.products.ProductsFragment;
import com.sephora.happyshop.ui.products.ProductsPresenter;
import com.sephora.happyshop.util.ActivityUtils;

public class MainActivity extends ActivityBase implements CategoryFragment.OnCategoryFragmentListener {

    private static final String TAG = MainActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setOnNavigationItemSelectedListener(onNavigationItemSelectedListener);

        changeToProductPage();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        return super.onOptionsItemSelected(item);
    }

    private BottomNavigationView.OnNavigationItemSelectedListener onNavigationItemSelectedListener
        = new BottomNavigationView.OnNavigationItemSelectedListener() {

        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
            switch (item.getItemId()) {
                case R.id.navigation_home:
                    changeToProductPage();
                    return true;
                case R.id.navigation_favourite:
                    changeToFavouritePage();
                    return true;
                case R.id.navigation_cart:
                    changeToCheckOutPage();
                    return true;
            }
            return false;
        }
    };

    private void changeToProductPage() {

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.productName);
        if (fragment == null) {

            fragment = CategoryFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(), fragment, R.id.contentPanel);

        } else if (!(fragment instanceof CategoryFragment)){

            fragment = CategoryFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                getSupportFragmentManager(), fragment, R.id.contentPanel);

        }
        new CategoryPresenter(Injection.provideProductsRepository(getApplicationContext()),
            (CategoryContract.View) fragment);
    }

    private void changeToFavouritePage() {

    }

    private void changeToCheckOutPage() {

        CheckoutFragment fragment = CheckoutFragment.newInstance("", "");
        ActivityUtils.replaceFragmentToActivity(
            getSupportFragmentManager(),
            fragment,
            R.id.contentPanel);
    }

    @Override
    public void onCategorySelected(Category item) {

        ProductsFragment productsFragment = ProductsFragment.newInstance(item.name);
        new ProductsPresenter(
            Injection.provideProductsRepository(getApplicationContext()),
            productsFragment);

        getSupportFragmentManager()
            .beginTransaction()
            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
            .add(R.id.contentPanel, productsFragment)
            .addToBackStack("product")
            .commit();
    }
}
