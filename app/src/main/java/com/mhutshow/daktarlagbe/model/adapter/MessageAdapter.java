package com.mhutshow.daktarlagbe.model.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mhutshow.daktarlagbe.R;
import com.mhutshow.daktarlagbe.model.Message;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class MessageAdapter extends FirestoreRecyclerAdapter<Message,MessageAdapter.MessageHolder> {

    public MessageAdapter(@NonNull FirestoreRecyclerOptions<Message> options) {
        super(options);
    }

    @Override
    protected void onBindViewHolder(@NonNull MessageHolder holder, int position, @NonNull Message model) {
        if(model.getUserSender().equals(getCurrentUser().getEmail()+"") ){
            //holder.text.setTextSize(20);
            //holder.text.setBackgroundColor(0xC0C0C0);
           // CoordinatorLayout.LayoutParams  lllp= (CoordinatorLayout.LayoutParams) holder.text.getLayoutParams();
           // lllp.gravity= Gravity.LEFT;
            //holder.text.setLayoutParams(lllp);
            //holder.text.setBackground(holder.text.getContext().getResources().getDrawable(R.drawable.rounded_message2));
            holder.text2.setText(model.getMessage());
            holder.text2.setPadding(35,35,35,35);
        }
        else {
            holder.text.setText(model.getMessage());
            holder.text.setPadding(35,35,35,35);
        }

    }

    @Override
    public MessageHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.message_chat_item,parent,false);
        return new MessageHolder(v);
    }

    class MessageHolder extends RecyclerView.ViewHolder{

        TextView text;
        TextView text2;

        public MessageHolder(View itemView){
            super(itemView);
            text = itemView.findViewById(R.id.message_item_text);
            text2 = itemView.findViewById(R.id.message_item_text2);
        }
    }

    protected FirebaseUser getCurrentUser(){ return FirebaseAuth.getInstance().getCurrentUser(); }
}

