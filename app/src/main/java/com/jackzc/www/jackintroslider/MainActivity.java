package com.jackzc.www.jackintroslider;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private TextView[] dots;
    private LinearLayout dotsLayout;
    private ViewPager pager;
    private IntroManager introManager;
    private ViewPageAdapter adapter;
    private Button btn_next, btn_skip;
    private int[] layouts;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        introManager = new IntroManager(this);
        if (!introManager.Check()) {
            introManager.setFirst(false);
            Intent intent = new Intent(MainActivity.this, Main2.class);
            startActivity(intent);
            finish();
        }
        setContentView(R.layout.activity_main);

        btn_next = (Button) findViewById(R.id.btn_next);
        btn_skip = (Button) findViewById(R.id.btn_skip);

        // Cover all system icons on status bar
        // getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_FULLSCREEN);

        pager = (ViewPager) findViewById(R.id.view_pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);

        layouts = new int[]{
                R.layout.activity_screen1,
                R.layout.activity_screen2,
                R.layout.activity_screen3,
                R.layout.activity_screen4
        };

        addBottomDots(0);
        //changeStatusBarColor();

        adapter = new ViewPageAdapter();
        pager.setAdapter(adapter);
        pager.addOnPageChangeListener(listener);

        btn_skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        btn_next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                int current_activity = getItem(+1);
                if (current_activity < layouts.length) {
                    pager.setCurrentItem(current_activity);
                } else {
                    Intent intent = new Intent(MainActivity.this, Main2.class);
                    startActivity(intent);
                    finish();
                }
            }
        });
    }

    private void addBottomDots(int position) {

        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.Dot_Active);
        int[] colorInactive = getResources().getIntArray(R.array.Dot_Inactive);
        dotsLayout.removeAllViews();
        for (int i = 0; i < dots.length; i++) {
            dots[i] = new TextView(this);
            if (Build.VERSION.SDK_INT >= 24) {
                dots[i].setText(Html.fromHtml("&#8226;", Html.FROM_HTML_MODE_LEGACY)); // for 24 api and more
            } else {
                dots[i].setText(Html.fromHtml("&#8226;")); // or for older api
            }
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInactive[position]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0) {
            dots[position].setTextColor(colorActive[position]);
        }

    }

    private int getItem(int i) {
        return pager.getCurrentItem() + i;
    }

    ViewPager.OnPageChangeListener listener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {
            addBottomDots(position);
            if (position == layouts.length - 1) {
                btn_next.setText("PROCEED");
                btn_skip.setVisibility(View.GONE);
            } else {
                btn_next.setText("NEXT");
                btn_skip.setVisibility(View.VISIBLE);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };

    private void changeStatusBarColor() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.TRANSPARENT);
        }
    }

    public class ViewPageAdapter extends PagerAdapter {

        private LayoutInflater inflater;

        @Override
        public int getCount() {
            return layouts.length;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = inflater.inflate(layouts[position], container, false);
            container.addView(v);
            return v;
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View) object;
            container.removeView(v);
        }
    }
}
