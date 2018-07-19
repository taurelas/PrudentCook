package com.leadinsource.prudentcook.mainactivity;

import android.databinding.DataBindingUtil;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.leadinsource.prudentcook.R;
import com.leadinsource.prudentcook.databinding.RvItemBinding;

import java.util.List;

import timber.log.Timber;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private final OnClickListener onClickListener;
    private final List<RVItem> items;

    interface OnClickListener {
        void onClick(RVItem item);
    }

    RecyclerViewAdapter(List<RVItem> items, OnClickListener onClickListener) {
        this.items = items;
        Timber.d("Items size = %s", items.size());
        this.onClickListener = onClickListener;

    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RvItemBinding binding = DataBindingUtil.inflate(inflater, R.layout.rv_item, parent, false );

        return new ViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Timber.d("binding position %s", position);
        holder.bind(items.get(position));
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {

        private RvItemBinding binding;

        ViewHolder(RvItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
            binding.getRoot().setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    onClickListener.onClick(items.get(getAdapterPosition()));
                }
            });
        }

        void bind(@NonNull RVItem item) {
           Timber.d("binding %s", item.getRecipeName());
           binding.setRvItem(item);
           binding.executePendingBindings();
        }
    }
}