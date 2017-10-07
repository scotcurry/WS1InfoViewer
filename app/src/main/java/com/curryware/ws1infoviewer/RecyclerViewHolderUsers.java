package com.curryware.ws1infoviewer;


import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class RecyclerViewHolderUsers extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView userName;
    public TextView emailAddress;

    public RecyclerViewHolderUsers(View itemView) {
        super (itemView);
        itemView.setOnClickListener(this);
        userName = itemView.findViewById(R.id.recycler_user_name);
        emailAddress = itemView.findViewById(R.id.recycler_user_email_address);
    }

    @Override
    public void onClick(View view) {
        Toast.makeText(view.getContext(), "Clicked Position: " + getAdapterPosition(), Toast.LENGTH_LONG).show();
    }
}
