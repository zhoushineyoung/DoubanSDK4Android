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

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.app.ActivityOptions;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.transition.Explode;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewAnimationUtils;
import android.view.Window;
import android.widget.Button;
import android.widget.Toast;

import com.google.gson.reflect.TypeToken;
import com.ryeeeeee.doubansdk4android.Douban;
import com.ryeeeeee.doubansdk4android.api.RequestException;
import com.ryeeeeee.doubansdk4android.api.shuo.Shuo;
import com.ryeeeeee.doubansdk4android.api.user.UserApi;
import com.ryeeeeee.doubansdk4android.api.user.UserInfo;
import com.ryeeeeee.doubansdk4android.api.user.UserListener;
import com.ryeeeeee.doubansdk4android.auth.IAuthListener;
import com.ryeeeeee.doubansdk4android.auth.oauth.ErrorResponse;
import com.ryeeeeee.doubansdk4android.auth.oauth.Scope;
import com.ryeeeeee.doubansdk4android.exception.DoubanException;
import com.ryeeeeee.doubansdk4android.util.JsonUtil;
import com.ryeeeeee.doubansdk4android.util.LogUtil;
import com.ryeeeeee.doubanx.R;

import java.util.List;

import static android.app.ActivityOptions.makeSceneTransitionAnimation;


public class MainActivity extends ActionBarActivity {
    private final static String TAG = "DoubanX";

    private Button mAuthButton;
    private Button mUserButton;
    private Button mAnimationButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        LogUtil.setLogEnabled(true);

        unitTest();

        Douban.init(this, "0abda2e1d3262fea2038e8a579728fbe", "9196f7a84f90c966",
                "http://ryeeeeee.com");

        mAuthButton = (Button) this.findViewById(R.id.button_auth);
        mAuthButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LogUtil.i(TAG, "认证 ...");
                Douban.authorize(Scope.getAllScopeByString(), new IAuthListener() {
                    @Override
                    public void onAuthSuccess(String userId) {
                        Toast.makeText(MainActivity.this, userId, Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(MainActivity.this, HomeActivity.class));
                    }

                    @Override
                    public void onAuthFailure(ErrorResponse response) {
                        Toast.makeText(MainActivity.this, response.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onError(DoubanException exception) {
                        Toast.makeText(MainActivity.this, exception.toString(), Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onCancel() {
                        Toast.makeText(MainActivity.this, "取消授权", Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinish() {
                        LogUtil.i(TAG, "onFinish");
                    }
                });
            }
        });

        mUserButton = (Button) this.findViewById(R.id.button_current_user);
        mUserButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                UserApi.getCurrentUserInfo(new UserListener<UserInfo>() {
                    @Override
                    public void onSuccess(UserInfo userInfo) {
                        Toast.makeText(MainActivity.this, "获取认证用户成功！", Toast.LENGTH_SHORT).show();
                        LogUtil.d(TAG, userInfo.toString());
                    }

                    @Override
                    public void onFailure(RequestException exception) {
                        Toast.makeText(MainActivity.this, "获取认证用户失败！", Toast.LENGTH_SHORT).show();
                    }

                });
            }
        });

        mAnimationButton = (Button) this.findViewById(R.id.button_animation);
        mAnimationButton.setOnClickListener(new View.OnClickListener() {
            @TargetApi(Build.VERSION_CODES.LOLLIPOP)
            @Override
            public void onClick(View view) {
                if(mAnimationButton.getVisibility() == View.VISIBLE) {
                    int cx = (mAnimationButton.getLeft() + mAnimationButton.getRight()) / 2;
                    int cy = (mAnimationButton.getTop() + mAnimationButton.getBottom()) / 2;

                    // get the initial radius for the clipping circle
                    int initialRadius = mAnimationButton.getWidth();

                    // create the animation (the final radius is zero)
                    Animator anim = ViewAnimationUtils.createCircularReveal(mAnimationButton, cx, cy, initialRadius, 0);
                    anim.setDuration(3000);

                    // make the view invisible when the animation is done
                    anim.addListener(new AnimatorListenerAdapter() {
                        @Override
                        public void onAnimationEnd(Animator animation) {
                            super.onAnimationEnd(animation);
                            mAnimationButton.setVisibility(View.INVISIBLE);
                        }
                    });
                    // start the animation
                    anim.start();

                } else {
                    // get the center for the clipping circle
                    int cx = (mAnimationButton.getLeft() + mAnimationButton.getRight()) / 2;
                    int cy = (mAnimationButton.getTop() + mAnimationButton.getBottom()) / 2;

                    // get the final radius for the clipping circle
                    int finalRadius = Math.max(mAnimationButton.getWidth(), mAnimationButton.getHeight());

                    // create the animator for this view (the start radius is zero)
                    Animator anim =
                            ViewAnimationUtils.createCircularReveal(mAnimationButton, cx, cy, 0, finalRadius);

                    // make the view visible and start the animation
                    mAnimationButton.setVisibility(View.VISIBLE);
                    anim.start();
                }
            }
        });



    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void unitTest() {
        List<String> list = Scope.convertScopeString2List(Scope.getAllScopeByString());
        for(String scope: list){
            LogUtil.d(TAG, "scope:" + scope);
        }
    }
}
