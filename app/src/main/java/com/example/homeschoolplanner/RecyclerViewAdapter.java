package com.example.homeschoolplanner;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;


import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.UsersAdapterVh> {
    private List<ChildModel> childModelList;
    private Context context;
    private SelectedChild listener;


    public RecyclerViewAdapter(List<ChildModel> childModelList, SelectedChild listener) {
        this.childModelList = childModelList;
        this.listener = listener;
    }

    @NonNull
    @Override
    public RecyclerViewAdapter.UsersAdapterVh onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        context = parent.getContext();
        return new UsersAdapterVh(LayoutInflater.from(context).inflate(R.layout.row_users, null));
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewAdapter.UsersAdapterVh holder, int position) {
        ChildModel childModel = childModelList.get(position);
        //child name
        String childN = childModel.getChildName();
        //initial letter of child name
        String childI = childModel.getChildName().substring(0,1);
        holder.childName.setText(childN);
        holder.childInitial.setText(childI);
    }

    public interface SelectedChild{
        void onClick(View v, int position);
    }

    @Override
    public int getItemCount() {
        return childModelList.size();
    }


    public class UsersAdapterVh extends RecyclerView.ViewHolder implements View.OnClickListener{
        TextView childInitial;
        TextView childName;
        ImageView ivIcon;
        public UsersAdapterVh(@NonNull View itemView) {
            super(itemView);
            childInitial =itemView.findViewById(R.id.childInitial);
            childName = itemView.findViewById(R.id.childName);
            ivIcon = itemView.findViewById(R.id.ivIcon);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            //listener.onClick(v, getAbsoluteAdapterPosition());
        }
    }
}
