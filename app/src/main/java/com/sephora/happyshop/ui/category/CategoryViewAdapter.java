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

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sephora.happyshop.R;
import com.sephora.happyshop.data.Category;
import com.sephora.happyshop.ui.category.CategoryFragment.OnCategoryFragmentListener;

import java.util.List;

public class CategoryViewAdapter extends RecyclerView.Adapter<CategoryViewAdapter.ViewHolder> {

    private final List<Category> categoryList;
    private final CategoryFragment.OnCategoryFragmentListener listener;

    public CategoryViewAdapter(List<Category> items, OnCategoryFragmentListener listener) {
        categoryList = items;
        this.listener = listener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
            .inflate(R.layout.fragment_category, parent, false);
        View cateogyrView = view.findViewById(R.id.categoryView);
        return new ViewHolder(cateogyrView);
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, int position) {

        holder.category = categoryList.get(position);
        holder.nameView.setText(categoryList.get(position).name);
        holder.imageView.setImageResource(categoryList.get(position).resource);

        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (null != listener) {
                    listener.onCategorySelected(holder.category);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public final View view;
        public final TextView nameView;
        public final ImageView imageView;

        public Category category;

        public ViewHolder(View view) {
            super(view);

            this.view = view;
            nameView    = (TextView) view.findViewById(R.id.categoryName);
            imageView   = (ImageView) view.findViewById(R.id.categoryImageView);
        }
    }
}
