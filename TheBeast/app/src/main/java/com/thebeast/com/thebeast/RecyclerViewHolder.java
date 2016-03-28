package com.thebeast.com.thebeast;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by loganpatino on 2/28/16.
 */
public class RecyclerViewHolder extends RecyclerView.ViewHolder {

    private ImageView sportIcon;
    private TextView locationText;
    private TextView timeText;
    private TextView teamSizeText;

    public RecyclerViewHolder(View itemView) {
        super(itemView);
        sportIcon = (ImageView)itemView.findViewById(R.id.sportIcon);
        locationText = (TextView)itemView.findViewById(R.id.locationText);
        timeText = (TextView)itemView.findViewById(R.id.timeText);
        teamSizeText = (TextView)itemView.findViewById(R.id.teamSizeText);
    }

    public void setSportIcon(String sport) {
        switch (sport) {
            case "Basketball":
                sportIcon.setImageResource(R.drawable.basketball);
                break;
            case "Football":
                sportIcon.setImageResource(R.drawable.football);
                break;
            case "Soccer":
                sportIcon.setImageResource(R.drawable.soccer);
                break;
            case "Volleyball":
                sportIcon.setImageResource(R.drawable.volleyball);
                break;
        }
    }

    public void setLocationText(String location) {
        locationText.setText(location);
    }

    public void setTimeText(int timeInMins) {
        String timeString = Utility.getTime(timeInMins);
        timeText.setText(timeString);
    }

    public void setTeamSizeText(int teamSize) {
        String sizes = teamSize + "v" + teamSize;
        teamSizeText.setText(sizes);
    }
}
