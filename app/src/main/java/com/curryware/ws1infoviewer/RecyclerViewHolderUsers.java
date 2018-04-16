package com.curryware.ws1infoviewer;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

public class RecyclerViewHolderUsers extends RecyclerView.ViewHolder {

    public TextView userName;
    public TextView emailAddress;
    public ImageView userImage;
    public TextView userLocation;
    public TextView userID;
    public TextView userDomain;
    public TextView userActive;
    public TextView userFullName;
    public TextView userUPN;
    public RelativeLayout parentLayout;

    // The key to this entire class is that it contains the parent view that can handle the OnClickListener
    // in the
    public RecyclerViewHolderUsers(View itemView) {
        super (itemView);
        userName = itemView.findViewById(R.id.recycler_user_name);
        emailAddress = itemView.findViewById(R.id.recycler_user_email_address);
        userImage = itemView.findViewById(R.id.user_role_image);
        parentLayout = itemView.findViewById(R.id.user_view_parent);
        userLocation = itemView.findViewById(R.id.recycler_location);
        userID = itemView.findViewById(R.id.recycler_userID);
        userDomain = itemView.findViewById(R.id.recycler_userDomain);
        userFullName = itemView.findViewById(R.id.recycler_full_name);
        userActive = itemView.findViewById(R.id.recycler_active);
        userUPN = itemView.findViewById(R.id.recycler_user_upn);
    }
}
