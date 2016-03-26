package com.thebeast.com.thebeast;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

/**
 * Created by loganpatino on 2/28/16.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    protected TextView itemText;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        itemText = (TextView)itemView.findViewById(R.id.item_text);
    }

    public void setItemText(String text) {
        itemText.setText(text);
    }
}
