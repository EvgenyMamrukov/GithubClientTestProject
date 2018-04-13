package com.example.evgeny.githubclient.adapters;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * Created by Evgeny on 13.04.18.
 */

public class UniversalViewHolder<U extends ViewDataBinding> extends RecyclerView.ViewHolder {

    private U binding;
    private View.OnClickListener clickListener;

    public void setListener(View.OnClickListener clickListener) {
        this.clickListener = clickListener;
        itemView.setOnClickListener(clickListener);
    }

    public UniversalViewHolder(View itemView) {
        super(itemView);
        binding = DataBindingUtil.bind(itemView);
    }

    public U getBinding() {
        return binding;
    }

    public View.OnClickListener getClickListener() {
        return clickListener;
    }
}
