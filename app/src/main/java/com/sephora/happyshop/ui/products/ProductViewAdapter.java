/*
 *
 *  *  Copyright 2017, Tun Lin
 *  *
 *  *  Licensed under the Apache License, Version 2.0 (the "License");
 *  *  you may not use this file except in compliance with the License.
 *  *  You may obtain a copy of the License at
 *  *
 *  *  http://www.apache.org/licenses/LICENSE-2.0
 *  *
 *  *  Unless required by applicable law or agreed to in writing, software
 *  *  distributed under the License is distributed on an "AS IS" BASIS,
 *  *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  *  See the License for the specific language governing permissions and
 *  *  limitations under the License.
 *
 */

package com.sephora.happyshop.ui.products;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sephora.happyshop.R;
import com.sephora.happyshop.common.logger.Log;
import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.ui.products.ProductsFragment.OnProductsFragmentListener;
import com.squareup.picasso.Picasso;

import java.text.NumberFormat;
import java.util.List;
import java.util.Locale;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ViewHolder> {

    private static final String TAG = ProductViewAdapter.class.getSimpleName();

    private Context context;
    private final List<Product> products;
    private final OnProductsFragmentListener listener;

    public ProductViewAdapter(Context context, List<Product> items, ProductsFragment.OnProductsFragmentListener listener) {
        this.context = context;
        products = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_product, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        Product product = products.get(position);
        holder.item = product;
        holder.nameView.setText(product.name);

        // Currency formatter
        Locale locale = new Locale("en", "SG");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
        String displayMoney = currencyFormatter.format(product.price);

        Log.d(TAG, product.toString());

        holder.priceView.setText(displayMoney);
        holder.onSaleView.setText(product.underSale ? context.getString(R.string.product_on_sale) : "");
        holder.productImageView.setImageBitmap(null);

        Picasso.with(context)
            .load(product.imgUrl)
            .placeholder(R.drawable.place_holder)
            .error(R.drawable.place_holder)
            .into(holder.productImageView);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onProductSelected(holder.item);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return products.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;

        public final TextView nameView;
        public final ImageView productImageView;
        public final TextView priceView;
        public final TextView onSaleView;

        public Product item;

        public ViewHolder(View view) {
            super(view);

            this.view           = view;
            nameView            = (TextView) view.findViewById(R.id.productName);
            productImageView    = (ImageView) view.findViewById(R.id.productImageView);
            priceView           = (TextView) view.findViewById(R.id.priceTextView);
            onSaleView          = (TextView) view.findViewById(R.id.saleTextView);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameView.getText() + "'";
        }
    }
}
