package dingshi.com.tworecycleview;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import dingshi.com.tworecycleview.adapter.WQRecycleAdapter;
import dingshi.com.tworecycleview.adapter.WQViewHolder;

public class MainActivity extends AppCompatActivity {
    RecyclerView leftRecycle;
    RecyclerView rightRecycle;

    WQRecycleAdapter leftAdapter;
    WQRecycleAdapter rightAdapter;


    List<String> leftList = new ArrayList<>();

    List<String> rightList = new ArrayList<>();

    List<String> detailsList = new ArrayList<>();


    int currentPosition = 0;

    @RequiresApi(api = Build.VERSION_CODES.M)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initData();

        leftRecycle = (RecyclerView) findViewById(R.id.main_left_recycle);
        rightRecycle = (RecyclerView) findViewById(R.id.main_right_recycle);
        leftRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        rightRecycle.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));

        leftAdapter = new WQRecycleAdapter(this, R.layout.item_main_left, leftList);
        leftRecycle.setAdapter(leftAdapter);

        rightAdapter = new WQRecycleAdapter(this, R.layout.item_main_right, rightList);
        rightRecycle.setAdapter(rightAdapter);


        leftAdapter.setCallBack(new WQRecycleAdapter.CallBack() {
            @Override
            public <T> void convert(WQViewHolder holder, T bean, int position) {
                LinearLayout layout = (LinearLayout) holder.getView(R.id.item_main_left_layout);
                TextView type = (TextView) holder.getView(R.id.item_main_left_type);
                type.setText((String) bean);
                if (position == currentPosition) {
                    layout.setBackgroundColor(0xffffffff);
                    type.setTextColor(getResources().getColor(R.color.colorAccent));
                } else {
                    layout.setBackgroundColor(0xffeeeeee);
                    type.setTextColor(0xff444444);
                }
            }
        });

        leftAdapter.setOnItemClickListner(new WQRecycleAdapter.OnItemClickListner() {
            @Override
            public void onItemClickListner(View v, int position) {
                Log.i("leftAdapter", "scrollToPositionWithOffset-->" + position);
                LinearLayoutManager llm = ((LinearLayoutManager) rightRecycle.getLayoutManager());

                llm.scrollToPositionWithOffset(position, 0);

            }
        });


        rightAdapter.setCallBack(new WQRecycleAdapter.CallBack() {
            @Override
            public <T> void convert(WQViewHolder holder, T bean, int position) {
                holder.setText(R.id.item_main_right_type, (String) bean);
                RecyclerView detailsRecycle = (RecyclerView) holder.getView(R.id.item_main_right_recycle);
                updateDetailsRecycle(detailsRecycle);
            }
        });


        rightRecycle.setOnScrollChangeListener(new View.OnScrollChangeListener() {
            @Override
            public void onScrollChange(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
                LinearLayoutManager rightManager = (LinearLayoutManager) rightRecycle.getLayoutManager();

                LinearLayoutManager leftManager = ((LinearLayoutManager) leftRecycle.getLayoutManager());

                /**
                 * 获取第一个item为第几个position
                 */
                currentPosition = rightManager.findFirstVisibleItemPosition();


                /**
                 * 这地方需要进行判断，如果右边的Recycle在移动的时候，左边的RecycleView也是需要进行移动的
                 * 左边的recycleview有可能会不可见，这时候，我们必须去判断一下，左边最后的一个item是不是
                 * 小于右边滑动的位置，或左边第一个item是不是大于右边滑动的位置
                 */
                if (leftManager.findFirstVisibleItemPosition() > currentPosition) {
                    leftManager.scrollToPositionWithOffset(currentPosition, 0);
                } else if (leftManager.findLastVisibleItemPosition() < currentPosition) {
                    leftManager.scrollToPositionWithOffset(currentPosition, 0);
                }

                /**
                 * 判断右边是否滑动到最后一个item，是的话，也将左边移动到最后一个item
                 * canScrollVertically(1)表示是否能向上滚动，false表示已经滚动到底部
                 */
                if (!rightRecycle.canScrollVertically(1)) {
                    currentPosition = rightList.size() - 1;

                    Log.i("tag", currentPosition + "-------");
                }


                leftAdapter.notifyDataSetChanged();
            }
        });
    }


    /**
     * 更新详情列表
     *
     * @param detailsRecycle
     */
    public void updateDetailsRecycle(RecyclerView detailsRecycle) {
        WQRecycleAdapter detailsAdapter = new WQRecycleAdapter(this, R.layout.item_main_details, detailsList);
        detailsRecycle.setLayoutManager(new GridLayoutManager(this, 3));
        detailsRecycle.setAdapter(detailsAdapter);
    }


    /**
     * 初始化数据源
     */
    private void initData() {
        for (int i = 0; i < 20; i++) {
            leftList.add("" + i);
            rightList.add("" + i);
        }
        for (int i = 0; i < 9; i++) {
            detailsList.add("");
        }
    }


}
