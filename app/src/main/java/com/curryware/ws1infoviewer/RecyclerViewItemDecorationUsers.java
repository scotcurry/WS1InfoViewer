package com.curryware.ws1infoviewer;


import android.graphics.Rect;
import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public class RecyclerViewItemDecorationUsers extends RecyclerView.ItemDecoration {

    private int verticalSpaceHeight = 0;

    public RecyclerViewItemDecorationUsers(int verticalSpaceHeightParam) {
        this.verticalSpaceHeight = verticalSpaceHeightParam;
    }

    @Override
    public void getItemOffsets(Rect outRect, View view, RecyclerView parent, RecyclerView.State state) {
        super.getItemOffsets(outRect, view, parent, state);

        outRect.bottom = verticalSpaceHeight;
    }
}
