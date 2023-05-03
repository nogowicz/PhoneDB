package com.example.phonedb;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class PhoneListAdapter extends RecyclerView.Adapter<PhoneListAdapter.PhoneViewHolder>{
    private List<Phone> mPhoneList;
    private OnItemClickListener mListener;

    public PhoneListAdapter(Context context) {
        this.mPhoneList = null;
    }

    public interface OnItemClickListener {
        void onItemClick(Phone phone);
    }

    public void setOnItemClickListener(OnItemClickListener listener) {
        mListener = listener;
    }

    @NonNull
    @Override
    public PhoneViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.phone_item, parent, false);
        return new PhoneViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PhoneViewHolder holder, int position) {
        Phone phone = mPhoneList.get(position);
        holder.tvModel.setText(phone.getModel());
        holder.tvProducer.setText(phone.getProducer());

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mListener != null) {
                    mListener.onItemClick(phone);
                }
            }
        });
    }

    @Override
    public int getItemCount() {
        if (mPhoneList != null)
            return mPhoneList.size();
        return 0;
    }

    public void setPhoneList(List<Phone> elementList) {
        this.mPhoneList = elementList;
        notifyDataSetChanged();
    }

    public Phone getPhoneAtPosition(int position) {
        return mPhoneList.get(position);
    }


    static class PhoneViewHolder extends RecyclerView.ViewHolder {
        TextView tvModel, tvProducer;

        public PhoneViewHolder(@NonNull View itemView) {
            super(itemView);
            tvModel = itemView.findViewById(R.id.model);
            tvProducer = itemView.findViewById(R.id.producer);
        }
    }
}
