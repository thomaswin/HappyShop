package com.sephora.happyshop.ui.products;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sephora.happyshop.R;
import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.ui.products.ProductsFragment.OnProductsFragmentListener;

import java.util.List;

public class ProductViewAdapter extends RecyclerView.Adapter<ProductViewAdapter.ViewHolder> {

    private static final String TAG = ProductViewAdapter.class.getSimpleName();

    private final List<Product> products;
    private final OnProductsFragmentListener listener;

    public ProductViewAdapter(List<Product> items, ProductsFragment.OnProductsFragmentListener listener) {
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

        holder.item = products.get(position);
        holder.productIDView.setText("Product ID : " + products.get(position).id);
        holder.nameView.setText(products.get(position).name);

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
        public final TextView productIDView;
        public final TextView nameView;
        public Product item;

        public ViewHolder(View view) {
            super(view);

            this.view = view;
            productIDView   = view.findViewById(R.id.id);
            nameView        = view.findViewById(R.id.content);
        }

        @Override
        public String toString() {
            return super.toString() + " '" + nameView.getText() + "'";
        }
    }
}
