package zy.library.ui.recyclerview.drag.adapter;

import android.app.Service;
import android.content.Context;
import android.os.Vibrator;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.Collections;
import java.util.List;

import zy.library.R;
import zy.library.ui.recyclerview.drag.callback.ItemDragHelperCallBack;
import zy.library.ui.recyclerview.drag.listener.OnItemDragListener;


/**
 * Created by zy on 2018/2/24.
 * recycleView 拖拽排序，且最后一项为添加按钮（示例共九位）
 * 可用于相册添加，上传图片
 */

public class ListDragAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<String> list;
    private ItemTouchHelper itemTouchHelper;

    public ListDragAdapter(Context context, List<String> listdata,final RecyclerView recyclerView) {
        this.mContext = context;
        this.list = listdata;



        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(new ItemDragHelperCallBack(new OnItemDragListener() {

            @Override
            public void onItemMove(int startPos, int endPos) {
//                //交换变换位置的集合数据
//                Collections.swap(list, startPos, endPos);
//                adapter.notifyItemMoved(startPos, endPos);
//                if (startPos == list.size()){
//                    return;
//                }

                if (endPos ==list.size() ) {
                    return;
                }

                if (startPos < endPos) {
                    //从上往下拖动，每滑动一个item，都将list中的item向下交换，向上滑同理。
                    for (int i = startPos; i < endPos; i++) {
                        Collections.swap(list, i, i + 1);//交换数据源两个数据的位置
                    }
                } else {
                    for (int i = startPos; i > endPos; i--) {
                        Collections.swap(list, i, i - 1);//交换数据源两个数据的位置
                    }
                }
                //更新视图
                notifyItemMoved(startPos, endPos);
            }
        }));
        //关联RecyclerView
        itemTouchHelper.attachToRecyclerView(recyclerView);
        setTouch(itemTouchHelper);
    }

    public List<String> getList() {
        return list;
    }

    public void setTouch(ItemTouchHelper itemTouchHelper) {
        this.itemTouchHelper = itemTouchHelper;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.item_list_drag, parent, false);
        return new MyDragViewHolder(view);
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, final int position) {
        MyDragViewHolder mHolder = (MyDragViewHolder) holder;

        if (position ==  list.size()) {
            mHolder.iv_add.setVisibility(View.VISIBLE);
        }else {
            mHolder.iv_add.setVisibility(View.INVISIBLE);

            mHolder.tvTag.setText(list.get(position));
        }

        mHolder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list.add("新增的");
                notifyDataSetChanged();
            }
        });

        mHolder.llItem.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                if (position == list.size())
                    return false;
                //获取系统震动服务
                Vibrator vib = (Vibrator) mContext.getSystemService(Service.VIBRATOR_SERVICE);
                vib.vibrate(100);

                itemTouchHelper.startDrag(holder);
                return false;
            }
        });

    }

    @Override
    public int getItemCount() {

        if (list.size() <9){

            return list.size()+1;
        }else {
            return list.size();
        }
    }

    static class MyDragViewHolder extends RecyclerView.ViewHolder {

        private TextView tvTag;
        private LinearLayout llItem;
        private ImageView iv_add;

        public MyDragViewHolder(View itemView) {
            super(itemView);
            tvTag = itemView.findViewById(R.id.tv_tag);
            llItem = itemView.findViewById(R.id.ll_item);
            iv_add = itemView.findViewById(R.id.iv_add);
        }
    }

}
