package com.sephora.happyshop.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

import com.sephora.happyshop.Injection;
import com.sephora.happyshop.R;
import com.sephora.happyshop.ui.category.CategoryContract;
import com.sephora.happyshop.ui.category.CategoryFragment;
import com.sephora.happyshop.ui.category.CategoryPresenter;
import com.sephora.happyshop.util.ActivityUtils;

public class MainActivity extends AppCompatActivity {

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