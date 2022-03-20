package com.example.sqlitedatabase;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.fragment.app.DialogFragment;

import java.util.List;

public class CongViecAdapter extends BaseAdapter {

    private MainActivity context;
    private int layout;
    private List<CongViec> congViecList;

    public CongViecAdapter(MainActivity context, int layout, List<CongViec> congViecList) {
        this.context = context;
        this.layout = layout;
        this.congViecList = congViecList;
    }

    @Override
    public int getCount() {
        return congViecList.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    private class ViewHolder{
        TextView tvCongViec;
        ImageView imgDelete, imgEdit;
    }
    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null){
            holder = new ViewHolder();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(layout,null);
            holder.tvCongViec = view.findViewById(R.id.tvCongViec);
            holder.imgDelete  = view.findViewById(R.id.imgDelete);
            holder.imgEdit    = view.findViewById(R.id.imgEdit);
            //duyệt ánh xạ vào holder
            view.setTag(holder);
        }else {
            holder = (ViewHolder) view.getTag();
        }

        // class CongViec
        CongViec congViec = congViecList.get(i);

        holder.tvCongViec.setText(congViec.getTenCV());

        holder.imgEdit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogSua(congViec.getTenCV(), congViec.getIdCV());
            }
        });

        holder.imgDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                context.DialogXoa(congViec.getTenCV(), congViec.getIdCV());
            }
        });

        return view;
    }
}
