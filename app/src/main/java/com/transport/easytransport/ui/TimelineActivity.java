package com.transport.easytransport.ui;

import android.app.Activity;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.transport.easytransport.R;

import org.qap.ctimelineview.TimelineRow;
import org.qap.ctimelineview.TimelineViewAdapter;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.ThreadLocalRandom;

import androidx.appcompat.app.AppCompatActivity;

import static android.graphics.Color.BLACK;
import static android.graphics.Color.BLUE;
import static android.graphics.Color.GREEN;
import static android.graphics.Color.RED;
import static android.graphics.Color.YELLOW;

public class TimelineActivity extends AppCompatActivity {
    // Create Timeline rows List
    private ArrayList<TimelineRow> timelineRowsList = new ArrayList<>();
    ArrayAdapter<TimelineRow> myAdapter;
    String ListTitle[]={"Bắt đầu","Ngã ba Chương Dương","Cao Đăng Xây dựng 2","Siêu thị Nguyễn Kiêm", "Đại học SPKT","Công an quạn 9","Chợ chiều", " Khu CNC","Siếu Tiên", "Bến xe Buýt ĐHQG","KTX khu B"};



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.timeline_activity);


        for (int i = 0; i < 11; i++) {
            //add the new row to the list
            timelineRowsList.add(createRandomTimelineRow(ListTitle[i],i));
        }

        //Create the Timeline Adapter
        myAdapter = new TimelineViewAdapter(this, 0, timelineRowsList,
                //if true, list will be sorted by date
                true);


        //Get the ListView and Bind it with the Timeline Adapter
        ListView myListView = (ListView) findViewById(R.id.timeline_listView);
        myListView.setAdapter(myAdapter);


        //if you wish to handle list scrolling
        myListView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int currentVisibleItemCount;
            private int currentScrollState;
            private int currentFirstVisibleItem;
            private int totalItem;
            private LinearLayout lBelow;


            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                // TODO Auto-generated method stub
                this.currentScrollState = scrollState;
                this.isScrollCompleted();
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                // TODO Auto-generated method stub
                this.currentFirstVisibleItem = firstVisibleItem;
                this.currentVisibleItemCount = visibleItemCount;
                this.totalItem = totalItemCount;


            }

            private void isScrollCompleted() {
                if (totalItem - currentFirstVisibleItem == currentVisibleItemCount
                        && this.currentScrollState == SCROLL_STATE_IDLE) {

                    ////on scrolling to end of the list, add new rows
                    for (int i = 0; i < 11; i++) {
                        myAdapter.add(createRandomTimelineRow(ListTitle[i],i));
                    }

                }
            }


        });

        //if you wish to handle the clicks on the rows
        AdapterView.OnItemClickListener adapterListener = new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                TimelineRow row = timelineRowsList.get(position);
                Toast.makeText(TimelineActivity.this, row.getTitle(), Toast.LENGTH_SHORT).show();
            }
        };
        myListView.setOnItemClickListener(adapterListener);
    }

    private TimelineRow createRandomTimelineRow(String t,int id) {

        // Create new timeline row (pass your Id)
        TimelineRow myRow = new TimelineRow(id);

        //to set the row Date (optional)
        //myRow.setDate(getRandomDate());
        //to set the row Title (optional)
        myRow.setTitle(t);
        //to set the row Description (optional)
        if(id==0){
        myRow.setDescription("Đi bộ ");}
        else{
            myRow.setDescription("Trạm xe Buýt");
        }
        //to set the row bitmap image (optional)
        //myRow.setImage(BitmapFactory.decodeResource(getResources(), R.drawable.img_0 + getRandomNumber(0, 10)));
        //to set row Below Line Color (optional)
        if(id==0){
        myRow.setBellowLineColor(GREEN);}
        else{
            myRow.setBellowLineColor(BLUE);
        }
        //to set row Below Line Size in dp (optional)
        myRow.setBellowLineSize(10);
        //to set row Image Size in dp (optional)
        //myRow.setImageSize();
        //to set background color of the row image (optional)
        if(id==0){
        myRow.setBackgroundColor(YELLOW);}
        else {
            myRow.setBackgroundColor(RED);
        }
        //to set the Background Size of the row image in dp (optional)
        myRow.setBackgroundSize(30);
        //to set row Date text color (optional)
        myRow.setDateColor(BLUE);
        //to set row Title text color (optional)
        myRow.setTitleColor(BLUE);
        //to set row Description text color (optional)
        myRow.setDescriptionColor(BLACK);

        return myRow;
    }


    //Random Methods
//    public int getRandomColor() {
//        Random rnd = new Random();
//        int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
//        ;
//        return color;
//    }

//    public int getRandomNumber(int min, int max) {
//        return min + (int) (Math.random() * max);
//    }
//
//
//    public Date getRandomDate() {
//        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//        Date startDate = null;
//        Date endDate = new Date();
//        try {
//            startDate = sdf.parse("25/11/2018");
//            long random = ThreadLocalRandom.current().nextLong(startDate.getTime(), endDate.getTime());
//            endDate = new Date(random);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return endDate;
//    }

}
