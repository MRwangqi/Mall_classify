package dingshi.com.tworecycleview;

/**
 * @author wangqi
 * @since 2017/11/19 下午6:43
 */


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

public class AbsoluteRecycleView extends RecyclerView {
    public AbsoluteRecycleView(Context context) {
        super(context);
    }

    public AbsoluteRecycleView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        heightMeasureSpec = MeasureSpec.makeMeasureSpec(Integer.MAX_VALUE >> 2, MeasureSpec.AT_MOST);
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
