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

package com.sephora.happyshop.ui.category;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.Snackbar;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sephora.happyshop.R;
import com.sephora.happyshop.data.Category;
import com.sephora.happyshop.ui.custom.EmptyRecyclerView;

import java.util.ArrayList;
import java.util.List;

public class CategoryFragment extends Fragment implements CategoryContract.View {

    private static final String TAG = CategoryFragment.class.getSimpleName();

    private CategoryContract.Presenter presenter;
    private CategoryViewAdapter viewAdapter;
    private EmptyRecyclerView recyclerView;
    private List<Category> categories = new ArrayList<>();
    private OnCategoryFragmentListener listener;

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
        getActivity().setTitle(R.string.title_categories);
    }

    @Override
    public void onStart() {
        super.onStart();
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

        Context context = view.getContext();
        recyclerView = view.findViewById(R.id.list);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));

        viewAdapter = new CategoryViewAdapter(categories, new OnCategoryFragmentListener() {
            @Override
            public void onCategorySelected(Category item) {
                listener.onCategorySelected(item);
            }
        });
        recyclerView.setAdapter(viewAdapter);

        View emptyView = view.findViewById(R.id.empty_view);
        recyclerView.setEmptyView(emptyView);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof  OnCategoryFragmentListener)
            listener = (OnCategoryFragmentListener) context;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
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
    public void showCategory(List<Category> data) {
        categories.clear();
        categories.addAll(data);
        viewAdapter.notifyDataSetChanged();
    }

    @Override
    public void showNoCategory() {
        showMessage(getString(R.string.category_error_not_found));
    }

    @Override
    public void showLoadingCategoriesError() {
        showMessage(getString(R.string.category_error_retrieve));
    }

    private void showMessage(String message) {
        Snackbar.make(getView(), message, Snackbar.LENGTH_SHORT).show();
    }

    public interface OnCategoryFragmentListener {
        void onCategorySelected(Category item);
    }
}
