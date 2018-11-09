package com.example.layoutnest;

import android.content.Context;
import android.icu.util.Calendar;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Layout;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private final int WC = ViewGroup.LayoutParams.WRAP_CONTENT;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);







        //土台のレイアウト
        LinearLayout baseLayout = new LinearLayout(this);
        baseLayout.setOrientation(LinearLayout.VERTICAL);
        setContentView(baseLayout);

        //土台のレイアウトのマージン(高さ)を設定する
        LinearLayout.LayoutParams params =(LinearLayout.LayoutParams) new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        params.setMargins(0,convertPxToDp(this,120),0,0);
        //セット
        baseLayout.setLayoutParams(params);

        //レイアウトを入れ子にする
        LinearLayout linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        baseLayout.addView(linearLayout);

        //上段の曜日を表示
        TextView sunday = new TextView(this);
        sunday.setText("日");
        sunday.setGravity(Gravity.CENTER);
        sunday.setBackgroundResource(R.drawable.text_layout);



        TextView monday = new TextView(this);
        monday.setText("月");
        TextView tuesday = new TextView(this);
        tuesday.setText("火");
        TextView wednesday = new TextView(this);
        wednesday.setText("水");
        TextView thursday = new TextView(this);
        thursday.setText("木");
        TextView friday = new TextView(this);
        friday.setText("金");
        TextView saturday = new TextView(this);
        saturday.setText("土");


        //xmlファイルで言うところのlayout_weight?
        LinearLayout.LayoutParams param1 = new LinearLayout.LayoutParams(WC,WC);
        param1.weight = 1.0f;

        //なんか知らんけど弟2引き数にこいつ入れたらlayout_weight = 1と同じ効果になるっぽい
        linearLayout.addView(sunday,param1);
        linearLayout.addView(monday,param1);
        linearLayout.addView(tuesday,param1);
        linearLayout.addView(wednesday,param1);
        linearLayout.addView(thursday,param1);
        linearLayout.addView(friday,param1);
        linearLayout.addView(saturday,param1);

        //レイアウトを入れ子にする
        linearLayout = new LinearLayout(this);
        linearLayout.setOrientation(LinearLayout.HORIZONTAL);
        baseLayout.addView(linearLayout);


        Month month = new Month();
        //現在の年、月取得
        int year_now = month.cal.get(Calendar.YEAR);
        int month_now = month.cal.get(Calendar.MONTH) + 1;


        month.cal.set(Calendar.YEAR,year_now);
        month.cal.set(Calendar.MONTH,month_now - 1);
        month.cal.set(Calendar.DATE,1);

        //Calendarインスタンスを生成。先月の月をセット
        Calendar last_month_cal = Calendar.getInstance();
        last_month_cal.set(year_now,month_now - 2,1);

        //当月の最終日
        int max_day = month.cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //前月の最終日
        int last_month_max_day =last_month_cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        //1日の曜日
        int start_index = month.cal.get(Calendar.DAY_OF_WEEK);
        //今月の1日の曜日までを先月の末尾日を表示して埋めるための数字
        last_month_max_day = last_month_max_day - start_index + 1;

        TextView day;

        //1週目の1日までを先月の末尾日で埋める
        for(int i = 1; i < start_index; i++) {

            day = new TextView(this);
            day.setText(String.valueOf(last_month_max_day));
            linearLayout.addView(day,param1);
            last_month_max_day++;

        }

        for(int i = 1; i <= max_day;i++) {

            day = new TextView(this);
            day.setText(String.valueOf(i));
            linearLayout.addView(day,param1);
            month.cal.set(Calendar.DATE,i);

            //土曜日なら次の列へ
            if(Calendar.SATURDAY == month.cal.get(Calendar.DAY_OF_WEEK)) {

                linearLayout = new LinearLayout(this);
                linearLayout.setOrientation(LinearLayout.HORIZONTAL);
                baseLayout.addView(linearLayout);


            }


        }

        //当月の最終日が土曜日じゃない時、土曜日まで次の月の日付で埋める
        if (month.cal.get(Calendar.DAY_OF_WEEK) != 7) {

            for (int count = month.cal.get(Calendar.DAY_OF_WEEK); count <7; count++) {


                int i = 1;
                day = new TextView(this);
                day.setText(String.valueOf(i));
                linearLayout.addView(day,param1);
                i++;

            }
        }

    }

    //pxをdpに置換
    private static int convertPxToDp(Context context, int px){
        float d = context.getResources().getDisplayMetrics().density;
        return (int)((px / d) + 0.5);
    }
}
