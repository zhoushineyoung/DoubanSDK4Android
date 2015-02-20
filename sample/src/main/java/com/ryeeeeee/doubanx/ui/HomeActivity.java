/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2015. Ryeeeeee
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.ryeeeeee.doubanx.ui;

import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ryeeeeee.doubansdk4android.api.shuo.ShuoApi;
import com.ryeeeeee.doubanx.R;

/**
 * @author Ryeeeeee
 * @since 2015-02-16
 */
public class HomeActivity extends BaseActivity {

    private final static String TAG = "HomeActivity";

    private RecyclerView mRecyclerView;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        FrameLayout frameLayout = (FrameLayout) this.findViewById(R.id.content_frame);
        RelativeLayout relativeLayout = (RelativeLayout) getLayoutInflater().inflate(R.layout.activity_home,
                frameLayout, false);

        frameLayout.addView(relativeLayout);

        mRecyclerView = (RecyclerView) this.findViewById(R.id.recycler_view);
        mRecyclerView.setHasFixedSize(true);

        mLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerAdapter(new String[]{"adf", "adfasfa", "adfaawer","rqwer","eoka","ajf","q9r0", "q8dkjbfkas", "9ueqre"});
        mRecyclerView.setAdapter(mAdapter);

        ShuoApi.getTimeline(-1,-1,-1,-1);
    }


    private class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.ViewHolder> {

        private String[] mDataSet;
        private int mLastPosition = -1;

        RecyclerAdapter(String[] dataSet) {
            mDataSet = dataSet;
        }

        public class ViewHolder extends RecyclerView.ViewHolder {
            public CardView mCardView;
            public ViewHolder(CardView cardView) {
                super(cardView);
                mCardView = cardView;
            }
        }

        @Override
        public RecyclerAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            CardView cardView = (CardView) LayoutInflater.from(parent.getContext()).inflate(R.layout.home_card_item, parent, false);

            ViewHolder viewHolder = new ViewHolder(cardView);
            return viewHolder;
        }

        @Override
        public void onBindViewHolder(RecyclerAdapter.ViewHolder holder, int position) {
            if (position > mLastPosition) {
                Animation animation = AnimationUtils.loadAnimation(HomeActivity.this, R.anim.card_from_bottom_to_top);
                holder.mCardView.startAnimation(animation);
            }

            mLastPosition = position;
            //holder.mCardView.setText(mDataSet[position]);
        }

        @Override
        public int getItemCount() {
            return mDataSet.length;
        }
    }

}