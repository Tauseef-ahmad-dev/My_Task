package com.example.maju.mazdor.Adapter;

import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;
import androidx.recyclerview.widget.RecyclerView;

import com.example.maju.mazdor.Model.ChatMessage;
import com.example.maju.mazdor.R;
import com.google.firebase.auth.FirebaseAuth;

import java.util.List;


public class MessagesAdapter extends RecyclerView.Adapter<MessagesAdapter.ViewHolder> {
    public static final int ITEM_TYPE_SENT = 0;
    public static final int ITEM_TYPE_RECEIVED = 1;
    public static String UID;


    private List<ChatMessage> mMessagesList;
    private Context mContext;

    private FirebaseAuth firebaseAuth  = FirebaseAuth.getInstance();

    // Provide a reference to the views for each data item
    // Complex data items may need more than one view per item, and
    // you provide access to all the views for a data item in a view holder
    public class ViewHolder extends RecyclerView.ViewHolder {
        // each data item is just a string in this case

        public TextView JobTitle;
        public TextView JobRate;
        public TextView JobLocation;
        public TextView JobDescription;

        public View layout;

        public ViewHolder(View v) {
            super(v);
            layout = v;
            JobTitle = (TextView) v.findViewById(R.id.job_title);
            JobRate = (TextView) v.findViewById(R.id.job_rate);
            JobLocation = (TextView)v.findViewById(R.id.job_location);
            JobDescription = (TextView)v.findViewById(R.id.job_description);

        }
    }



    public void add(int position, ChatMessage message) {
        mMessagesList.add(position, message);
        notifyItemInserted(position);
    }

    public void remove(int position) {
        mMessagesList.remove(position);
        notifyItemRemoved(position);
    }

    // Provide a suitable constructor (depends on the kind of dataset)
    public MessagesAdapter(List<ChatMessage> myDataset, Context context) {
        mMessagesList = myDataset;
        mContext = context;

    }
    // Provide a suitable constructor (depends on the kind of dataset)


//    @Override
//    public int getItemViewType(int position) {
//        UID = firebaseAuth.getCurrentUser().getUid();
//
//        if (mMessagesList.get(position).getSenderId().equals(UID)){
//            return ITEM_TYPE_SENT;
//        } else {
//            return ITEM_TYPE_RECEIVED;
//        }
//    }


    // Create new views (invoked by the layout manager)
    @Override
    public MessagesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = null;
        if (viewType == ITEM_TYPE_SENT) {
            v = LayoutInflater.from(mContext).inflate(R.layout.sent_msg_row, null);
        } else if (viewType == ITEM_TYPE_RECEIVED) {
            v = LayoutInflater.from(mContext).inflate(R.layout.received_msg_row, null);
        }
        return new ViewHolder(v); // view holder for header items
    }

    // Replace the contents of a view (invoked by the layout manager)
    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        // - get element from your dataset at this position
        // - replace the contents of the view with that element
        ChatMessage msg = mMessagesList.get(position);
        holder.JobLocation.setText(msg.getJob_location());
        holder.JobDescription.setText("Click Here");
        holder.JobTitle.setText(msg.getJob_title());
        holder.JobRate.setText(msg.getJob_rate());
//        holder.RecivernAme.setText(msg.getMessageReceiverName());



    }

    // Return the size of your dataset (invoked by the layout manager)
    @Override
    public int getItemCount() {
        return mMessagesList.size();
    }
}