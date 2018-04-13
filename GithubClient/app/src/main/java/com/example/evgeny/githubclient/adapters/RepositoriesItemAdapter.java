package com.example.evgeny.githubclient.adapters;

import com.example.evgeny.githubclient.BR;
import com.example.evgeny.githubclient.R;
import com.example.evgeny.githubclient.databinding.RepositoryListItemBinding;
import com.example.evgeny.githubclient.model.blogic.RepositoryData;
import com.squareup.picasso.Picasso;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evgeny on 13.04.18.
 */

public class RepositoriesItemAdapter extends RecyclerView.Adapter<RepositoriesItemAdapter.RepositoryViewHolder> {

    private List<RepositoryData> itemList = new ArrayList<>();
    private List<RepositoryListItemBinding> itemBindingList = new ArrayList<>();

    public RepositoriesItemAdapter() {

    }

    public void setItems(List<RepositoryData> itemList) {
        this.itemList = itemList;
        itemBindingList.removeAll(itemBindingList);
        for (RepositoryData item : itemList) {
            itemBindingList.add(null);
        }
        notifyDataSetChanged();
    }

    @Override
    public RepositoryViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        RepositoryListItemBinding binding = RepositoryListItemBinding.inflate(inflater, viewGroup, false);

        if (binding != null) {
            RepositoryViewHolder holder = new RepositoryViewHolder(binding.getRoot());
            return holder;
        } else {
            return null;
        }
    }

    @Override
    public void onBindViewHolder(final RepositoryViewHolder universalViewHolder, final int position) {
        if (itemList == null) {
            return;
        }
        final RepositoryData repositoryData = itemList.get(position);
        final RepositoryListItemBinding binding = universalViewHolder.getBinding();

        if (repositoryData != null && repositoryData.getOwnerData() != null) {
            Picasso.get().load(repositoryData.getOwnerData().getAvatar()).placeholder(R.drawable.ic_profile).into(binding.profileImageView);
        }

        binding.setVariable(BR.model, repositoryData);

        itemBindingList.set(position, binding);
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public RepositoryData getItem(int position) {
        return itemList.get(position);
    }

    public static class RepositoryViewHolder extends UniversalViewHolder<RepositoryListItemBinding> {

        public RepositoryViewHolder(View itemView) {
            super(itemView);
        }
    }
}
