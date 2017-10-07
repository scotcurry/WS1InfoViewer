package com.curryware.ws1infoviewer;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

public class RecyclerViewAdapterUsers extends RecyclerView.Adapter<RecyclerViewHolderUsers> {

    List<RecyclerViewUser> ws1Users;
    private Context context;

    public RecyclerViewAdapterUsers(Context context, List<RecyclerViewUser> users) {
        this.ws1Users = users;
        this.context = context;
    }

    @Override
    public RecyclerViewHolderUsers onCreateViewHolder(ViewGroup parent, int viewType) {

        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_user_listitem, null);
        RecyclerViewHolderUsers recyclerViewHolderUsers = new RecyclerViewHolderUsers(layoutView);
        return recyclerViewHolderUsers;
    }

    @Override
    public void onBindViewHolder(RecyclerViewHolderUsers holder, int position) {

        holder.userName.setText(ws1Users.get(position).getUserName());
        holder.emailAddress.setText(ws1Users.get(position).getEmailAddress());
    }

    @Override
    public int getItemCount() {
        return this.ws1Users.size();
    }
}
