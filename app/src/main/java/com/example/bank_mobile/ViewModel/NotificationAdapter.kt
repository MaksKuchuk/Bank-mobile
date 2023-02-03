package com.example.bank_mobile.ViewModel

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.bank_mobile.Model.Domain.Notification
import com.example.bank_mobile.R
import com.example.bank_mobile.databinding.NotificationItemBinding

class NotificationAdapter : RecyclerView.Adapter<NotificationAdapter.NotificationHolder>() {
    val notificationList = ArrayList<Notification>()

    class NotificationHolder(item: View) : RecyclerView.ViewHolder(item) {
        val binding = NotificationItemBinding.bind(item)

        fun bind(notif: Notification) = with(binding) {
            desc.text = notif.description
            stat.text = "Status: " + notif.status
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NotificationHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.notification_item, parent, false)
        return NotificationHolder(view)
    }

    override fun onBindViewHolder(holder: NotificationHolder, position: Int) {
        holder.bind(notificationList[position])
    }

    override fun getItemCount(): Int {
        return notificationList.size
    }
}
