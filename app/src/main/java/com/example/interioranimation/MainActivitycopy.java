package com.example.interioranimation;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.widget.ImageView;


public class MainActivitycopy extends AppCompatActivity{

    private static int IMAGES_COUNT = 251;
    private ImageView img;
    private int counter;
    private String fileName = "anim0000";
    private Context ctx;
    private GestureDetectorCompat mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        img = findViewById(R.id.imageView);

        int res = getResources().getIdentifier(fileName, "drawable", this.getPackageName());
        img.setImageResource(res);



        mDetector = new GestureDetectorCompat(this, new MyGestureListener());
    }

    @Override
    public boolean onTouchEvent(MotionEvent event){
        this.mDetector.onTouchEvent(event);
        return super.onTouchEvent(event);
    }

    class MyGestureListener extends GestureDetector.SimpleOnGestureListener {
        private static final String DEBUG_TAG = "Gestures";

        @Override
        public boolean onDown(MotionEvent event) {
            Log.d(DEBUG_TAG, "onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            Log.d(DEBUG_TAG, "onFling: " + velocityX);


            if(velocityX < 0 && counter + 5< IMAGES_COUNT )
                counter+=5;
            if (velocityX > 0 && counter > 5)
                counter-=5;
            if(counter >= 100)  fileName = fileName.substring(0,fileName.length()-3) + counter;
            else if (counter >= 10)  fileName = fileName.substring(0,fileName.length()-2) + counter;
            else  fileName = fileName.substring(0,fileName.length()-1) + counter;

            int res = ctx.getResources().getIdentifier(fileName, "drawable", ctx.getPackageName());
            img.setImageResource(res);

            Log.d(DEBUG_TAG,"fileName:" + fileName);

            return true;
        }
    }

}
