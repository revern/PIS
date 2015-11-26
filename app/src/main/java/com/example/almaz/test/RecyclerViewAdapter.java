package com.example.almaz.test;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import java.util.List;


public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.ViewHolder> {

    private List<Integer> records;
    public Context mContext;

    public RecyclerViewAdapter(Context context, List<Integer> records) {
        this.records = records;
        mContext=context;
    }

    @Override
    public RecyclerViewAdapter.ViewHolder onCreateViewHolder(ViewGroup viewGroup, int viewType) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.items, viewGroup, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(RecyclerViewAdapter.ViewHolder holder, int position) {
        final ImageView imageView = holder.icon;
        imageView.setImageResource(records.get(position));
//        imageView.setImageURI(Uri.parse(records.get(position).toURI().toString()));
        Log.d("URI", records.get(position).toString());
    }

    @Override
    public int getItemCount() {
        return records.size();
    }


    class ViewHolder extends RecyclerView.ViewHolder {
        private ImageView icon;
        public ViewHolder(View itemView) {
            super(itemView);
            icon = (ImageView) itemView.findViewById(R.id.recyclerViewItemIcon);
        }
    }

}
