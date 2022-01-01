package com.example.apicalling.MainPackage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.keval.mypractise.databinding.AdapterProfileBinding;

import java.util.ArrayList;

public class AdapterProfile extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    ArrayList<ModelClass> data =new ArrayList<>();
    AdapterProfileBinding binding;
    private Context context;

    public AdapterProfile(ArrayList<ModelClass> data, Context context) {
        this.data = data;
        this.context = context;
    }



    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(context),R.layout.adapter_profile,parent,false);
        return new ItemViewHolder(binding.getRoot(),binding);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
      final ItemViewHolder mViewHolder=(ItemViewHolder)holder;
       binding.tvName.setText(data.get(position).getName());
       binding.tvSurname.setText(data.get(position).getSurname());
       binding.ivImg.setImageResource(data.get(position).getImage());
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    private class ItemViewHolder extends RecyclerView.ViewHolder {
        AdapterProfileBinding binding;

        public ItemViewHolder(@NonNull View itemView, AdapterProfileBinding binding) {
            super(itemView);
        }
    }
}
