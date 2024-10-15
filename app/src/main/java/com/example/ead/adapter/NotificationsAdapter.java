package com.example.ead.adapter;

import android.app.Notification;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ead.R;
import com.example.ead.models.NotificationModel;

import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Locale;

public class NotificationsAdapter extends RecyclerView.Adapter<NotificationsAdapter.NotificationViewHolder> {

    private List<NotificationModel> notificationList;

    public NotificationsAdapter(List<NotificationModel> notificationList) {
        this.notificationList = notificationList;
    }


    public NotificationViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new NotificationViewHolder(view);
    }

    public void onBindViewHolder(NotificationViewHolder holder, int position) {
        NotificationModel notification = notificationList.get(position);
        holder.notificationMessage.setText(notification.getMessage());
        // Format the date before setting it to the TextView
        SimpleDateFormat dateFormat = new SimpleDateFormat("MMM dd, yyyy", Locale.getDefault());
        String formattedDate = dateFormat.format(notification.getCreatedAt());

        holder.notificationDate.setText(formattedDate);

    }

    public int getItemCount() {
        return notificationList.size();
    }

    public static class NotificationViewHolder extends RecyclerView.ViewHolder {
         TextView notificationMessage, notificationDate;

        public NotificationViewHolder(View itemView) {
            super(itemView);

            notificationMessage = itemView.findViewById(R.id.tvMessage);
            notificationDate = itemView.findViewById(R.id.notificationDate);
        }
    }
}
