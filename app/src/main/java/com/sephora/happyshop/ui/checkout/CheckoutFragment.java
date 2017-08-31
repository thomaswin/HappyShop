package com.sephora.happyshop.ui.checkout;


import android.content.Context;
import android.content.DialogInterface;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.SimpleImageLoadingListener;
import com.sephora.happyshop.R;
import com.sephora.happyshop.common.LogUtils;
import com.sephora.happyshop.common.logger.Log;
import com.sephora.happyshop.data.Product;
import com.sephora.happyshop.ui.custom.EmptyRecyclerView;

import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class CheckoutFragment extends Fragment implements
    CheckoutContract.View {

    private static final String TAG = LogUtils.makeLogTag(CheckoutFragment.class);

    @BindView(R.id.empty_view)
    View emptyView;

    @BindView(R.id.totalTextView)
    TextView totalTextView;

    private EmptyRecyclerView productListView;
    private LinearLayoutManager layoutManager;
    private ProductPaymentAdaper productAdapter;
    private List<Product> products = new ArrayList<>();
    private CheckoutContract.Presenter presenter;

    public CheckoutFragment() {

    }

    public static CheckoutFragment newInstance() {
        CheckoutFragment fragment = new CheckoutFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
        getActivity().setTitle(R.string.title_cart);

        setHasOptionsMenu(true);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_checkout, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.action_clear_cart) {
            presenter.clearCart();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_checkout, container, false);
        ButterKnife.bind(this, view);

        productListView = view.findViewById(R.id.productListView);
        productListView.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(getActivity());
        productListView.setLayoutManager(layoutManager);

        productAdapter = new ProductPaymentAdaper(products);
        productListView.setAdapter(productAdapter);
        productListView.addItemDecoration(new ProductDividerItemDecoration(getActivity()));
        productListView.setEmptyView(emptyView);
        return view;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(CheckoutContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {
    }

    @Override
    public void showLoadingCartError() {
    }

    @Override
    public void showNoCartInfo() {
    }

    @Override
    public void showCartInfo(List<Product> data) {
        Log.d(TAG, "showCartInfo : " + data.toString());

        products.clear();
        products.addAll(data);

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                productAdapter.notifyDataSetChanged();

                double total = 0;
                for (Product product : products) {
                    total += product.price;
                }

                Locale locale = new Locale("en", "SG");
                NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
                String displayMoney = currencyFormatter.format(total);
                totalTextView.setText(displayMoney);
            }
        });
    }

    @OnClick(R.id.checkOutButton)
    public void checkOutClicked(View view) {

        getActivity().runOnUiThread(new Runnable() {
            @Override
            public void run() {
                AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
                builder.setMessage("Thanks you for supporting us.")
                    .setTitle("Payment Completed !!")
                    .setPositiveButton("Continue", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {
                            presenter.clearCart();
                        }
                    });
                builder.create().show();
            }
        });
    }

    public class ProductPaymentAdaper extends RecyclerView.Adapter<ProductPaymentAdaper.ViewHolder> {

        private final List<Product> productList;

        public ProductPaymentAdaper(List<Product> productList) {
            this.productList = productList;
        }

        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_product, parent, false);
            ViewHolder viewHolder = new ViewHolder(view);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            Product product = productList.get(position);
            String name = product.name;

            Locale locale = new Locale("en", "SG");
            NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(locale);
            String displayMoney = currencyFormatter.format(product.price);

            holder.nameView.setText(name);
            holder.priceView.setText(displayMoney);

            ImageLoader imageLoader = ImageLoader.getInstance();
            imageLoader.loadImage(product.imgUrl, new SimpleImageLoadingListener() {
                @Override
                public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {
                    if (loadedImage != null) {
                        holder.productView.setImageBitmap(loadedImage);
                    } else {
                        Toast.makeText(getContext(), "Cannot loaded", Toast.LENGTH_SHORT).show();
                    }
                }
            });
            holder.itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    Toast.makeText(getContext(), "Long click", Toast.LENGTH_SHORT).show();
                    return true;
                }
            });
        }

        @Override
        public int getItemCount() {
            return productList.size();
        }

        class ViewHolder extends RecyclerView.ViewHolder {

            public TextView nameView;
            public TextView priceView;
            public ImageView productView;

            public ViewHolder(View itemView) {
                super(itemView);
                nameView = (TextView) itemView.findViewById(R.id.productNameTextview);
                priceView = (TextView) itemView.findViewById(R.id.priceTextView);
                productView = (ImageView) itemView.findViewById(R.id.productImageView);
            }
        }
    }

    public class ProductDividerItemDecoration extends RecyclerView.ItemDecoration {
        private Drawable divider;

        public ProductDividerItemDecoration(Context context) {
            divider = context.getResources().getDrawable(R.drawable.line_divider);
        }

        @Override
        public void onDrawOver(Canvas c, RecyclerView parent, RecyclerView.State state) {
            int left = parent.getPaddingLeft();
            int right = parent.getWidth() - parent.getPaddingRight();

            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                View child = parent.getChildAt(i);

                RecyclerView.LayoutParams params = (RecyclerView.LayoutParams) child.getLayoutParams();

                int top = child.getBottom() + params.bottomMargin;
                int bottom = top + divider.getIntrinsicHeight();

                divider.setBounds(left, top, right, bottom);
                divider.draw(c);
            }
        }
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }
}
