package dingshi.com.tworecycleview.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * Created by wangqi on 2016/7/16.
 */
public class WQRecycleAdapter extends RecyclerView.Adapter<WQViewHolder> {
    private int layoutId;
    private List<? extends Object> data;
    public Context context;
    private OnItemClickListner onItemClickListner;//单击事件
    private OnItemLongClickListner onItemLongClickListner;//长按单击事件
    private boolean clickFlag = true;//单击事件和长单击事件的屏蔽标识

    /**
     * @param context  //上下文
     * @param layoutId //布局id
     * @param data     //数据源
     */
    public WQRecycleAdapter(Context context, int layoutId, List<? extends Object> data) {
        this.layoutId = layoutId;
        this.data = data;
        this.context = context;
    }


    @Override
    public WQViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(layoutId, parent, false);
        final WQViewHolder holder = new WQViewHolder(v, context);
        //单击事件回调
        v.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (onItemClickListner == null)
                    return;
                if (clickFlag) {
                    onItemClickListner.onItemClickListner(v, holder.getLayoutPosition());
                }
                clickFlag = true;
            }
        });
        //单击长按事件回调
        v.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (onItemLongClickListner == null)
                    return false;
                onItemLongClickListner.onItemLongClickListner(v, holder.getLayoutPosition());
                clickFlag = false;
                return false;
            }
        });
        return holder;
    }

    @Override
    public void onBindViewHolder(WQViewHolder holder, int position) {
        if (mCallBack != null)
            mCallBack.convert(holder, data.get(position), position);
    }

    @Override
    public int getItemCount() {
        int count = data.size();
        return count;
    }


    public void setOnItemClickListner(OnItemClickListner onItemClickListner) {
        this.onItemClickListner = onItemClickListner;
    }
    public interface OnItemClickListner {
        void onItemClickListner(View v, int position);
    }
    public void setOnItemLongClickListner(OnItemLongClickListner onItemLongClickListner) {
        this.onItemLongClickListner = onItemLongClickListner;
    }



    public interface OnItemLongClickListner {
        void onItemLongClickListner(View v, int position);
    }

    CallBack mCallBack;

    public void setCallBack(CallBack CallBack) {
        this.mCallBack = CallBack;
    }

    public interface CallBack {
        <T extends Object> void convert(WQViewHolder holder, T bean, int position);
    }

}
