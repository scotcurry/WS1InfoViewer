package com.curryware.ws1infoviewer;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

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
    public void onBindViewHolder(RecyclerViewHolderUsers holder, final int position) {

        String userActive = "true";
        String userFirstName = ws1Users.get(position).getUserFirstName();
        String userLastName = ws1Users.get(position).getUserLastName();
        String userFullNameConcat = userFirstName.concat(" ");
        userFullNameConcat = userFullNameConcat.concat(userLastName);
        final String userFullName = userFullNameConcat;

        if (ws1Users.get(position).getUserActive())
            userActive = "false";

        holder.userName.setText(ws1Users.get(position).getUserName());
        holder.emailAddress.setText(ws1Users.get(position).getEmailAddress());
        holder.userLocation.setText(ws1Users.get(position).getUserLocation());
        holder.userImage.setImageResource(ws1Users.get(position).getImageId());
        holder.userID.setText(ws1Users.get(position).getUserID());
        holder.userDomain.setText(ws1Users.get(position).gerUserDomain());
        holder.userFullName.setText(userFullName);
        holder.userActive.setText(userActive);
        holder.userUPN.setText(ws1Users.get(position).getUserUPN());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, UserDetailActivity.class);
                intent.putExtra("USER_NAME", ws1Users.get(position).getUserName());
                intent.putExtra("USER_FULL_NAME", userFullName);
                intent.putExtra("USER_DOMAIN", ws1Users.get(position).gerUserDomain());
                intent.putExtra("USER_ID", ws1Users.get(position).getUserID());
                intent.putExtra("USER_UPN", ws1Users.get(position).getUserUPN());
                context.startActivity(intent);
            }
        });
    }

    @Override
    public int getItemCount() {
        return this.ws1Users.size();
    }
}
