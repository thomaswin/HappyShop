package com.sephora.happyshop;

import android.content.Context;
import android.support.annotation.NonNull;

import com.sephora.happyshop.service.ProductManager;
import com.sephora.happyshop.data.source.ApiEndpoint;

import static com.google.common.base.Preconditions.checkNotNull;

/**
 * Created by Thomas Win on 28/8/17.
 */

public class Injection {

    public static ProductManager provideProductsRepository(@NonNull Context context) {
        checkNotNull(context);
        return ProductManager.getInstance(ApiEndpoint.getInstance());
    }
}
