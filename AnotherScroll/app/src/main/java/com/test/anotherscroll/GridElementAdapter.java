package com.test.anotherscroll;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.Random;

/**
 * Created by agies on 7/6/16.
 */
public class GridElementAdapter extends RecyclerView.Adapter<GridElementAdapter.SimpleViewHolder> {
    final private Context context;
    final private int width;
    private final int peekWidth;
    private final Random random = new Random();

    public GridElementAdapter(Context context, int width, int peekWidth) {
        this.context = context;
        this.width = width;
        this.peekWidth = peekWidth;
    }

    @Override
    public SimpleViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        final View view = LayoutInflater.from(this.context).inflate(R.layout.grid_element, parent, false);
        view.setLayoutParams(new FrameLayout.LayoutParams(width - peekWidth, ViewGroup.LayoutParams.MATCH_PARENT));
        SimpleViewHolder holder = new SimpleViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(SimpleViewHolder holder, int position) {
        holder.view.setBackgroundColor(random.nextInt());
        holder.view.setPadding(position == 0 ? peekWidth : 0, 0, position == 3 ? peekWidth : 0, 0);
        holder.cardText.setText("---------- Card Text #" + position + " ----------");
        if (position != 0) {
            holder.layout.setAlpha(0.5f);
        }
    }

    @Override
    public int getItemCount() {
        return 4;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public class SimpleViewHolder extends RecyclerView.ViewHolder {
        final TextView cardText;
        final TextView bodyContent;
        final View view;
        final ViewGroup layout;

        public SimpleViewHolder(View itemView) {
            super(itemView);
            view = itemView;
            layout = (ViewGroup) itemView.findViewById(R.id.layout);
            cardText = (TextView) itemView.findViewById(R.id.cardView);
            bodyContent = (TextView) itemView.findViewById(R.id.bodyContent);
        }
    }
}
