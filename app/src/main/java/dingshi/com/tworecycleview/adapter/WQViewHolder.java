package dingshi.com.tworecycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;


/**
* @author wangqi
* @since  2017/11/19 下午6:36
*/


public class WQViewHolder extends RecyclerView.ViewHolder {
    View convertView;
    Context context;

    public WQViewHolder(View itemView, Context context) {
        super(itemView);
        this.convertView = itemView;
        this.context = context;
    }

    public View getItemView() {
        return convertView;
    }

    public void setText(int id, String text) {
        TextView tx = (TextView) convertView.findViewById(id);
        tx.setText(text);
    }

    public void setText(int id, String text, final OnClickListener onClickListener) {
        TextView tx = (TextView) convertView.findViewById(id);
        tx.setText(text);
        tx.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onClickListener.onClickListner(v);
            }
        });
    }

    public View getView(int id) {
        return convertView.findViewById(id);
    }

    public interface OnClickListener {
        void onClickListner(View v);
    }

}
