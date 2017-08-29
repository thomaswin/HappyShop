package com.sephora.happyshop.ui.category;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sephora.happyshop.R;
import com.sephora.happyshop.data.Category;
import com.sephora.happyshop.ui.category.dummy.DummyContent;

import java.util.List;

public class CategoryFragment extends Fragment implements CategoryContract.View {

    private static final String TAG = CategoryFragment.class.getSimpleName();

    private CategoryContract.Presenter presenter;
    private CategoryViewAdapter viewAdapter;

    public CategoryFragment() {

    }

    public static CategoryFragment newInstance() {

        CategoryFragment fragment = new CategoryFragment();
        Bundle args = new Bundle();

        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            // TODO get data from arguments
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.start();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category_list, container, false);


        if (view instanceof RecyclerView) {
            Context context = view.getContext();
            RecyclerView recyclerView = (RecyclerView) view;
            recyclerView.setLayoutManager(new LinearLayoutManager(context));

            viewAdapter = new CategoryViewAdapter(DummyContent.ITEMS, new OnCategoryFragmentListener() {
                @Override
                public void onCategorySelected(DummyContent.DummyItem item) {
                    showCategoryDetailUi("");
                }
            });
            recyclerView.setAdapter(viewAdapter);
        }
        return view;
    }

    @Override
    public boolean isActive() {
        return isAdded();
    }

    @Override
    public void setPresenter(CategoryContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showCategory(List<Category> categories) {

    }

    @Override
    public void showNoCategory() {
        showMessage("No category found !!!");
    }

    @Override
    public void showLoadingCategoriesError() {
        showMessage("Category retrieve error!!!");
    }

    @Override
    public void showCategoryDetailUi(@NonNull String categoryName) {

    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_LONG).show();
    }

    public interface OnCategoryFragmentListener {
        void onCategorySelected(DummyContent.DummyItem item);
    }
}
