package com.example.interioranimation;




import android.graphics.Path;
import android.graphics.PathMeasure;
import android.support.v4.view.GestureDetectorCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.GestureDetector;
import android.view.MotionEvent;

import android.view.View;
import android.view.ViewGroup;

import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;


public class MainActivity extends AppCompatActivity{
    private static final String DEBUG_TAG = "Gestures";
    private static int IMAGES_COUNT = 251;
    private static int SPEED = 4;


    private ViewGroup mainLayout;
    private ImageView img;
    private ImageButton ib;
    private ImageButton ib2;
    private ImageButton ib3;
    private PathMeasure pm1;
    private PathMeasure pm2;
    private PathMeasure pm3;

    private int counter;
    private String fileName = "anim0000";
    private GestureDetectorCompat mDetector;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        img = findViewById(R.id.imageView);


        int res = getResources().getIdentifier(fileName, "drawable", this.getPackageName());
        img.setImageResource(res);

        mDetector = new GestureDetectorCompat(this, new MyGestureListener());

        ib = findViewById(R.id.imageButton);
        ib2 = findViewById(R.id.imageButton2);
        ib3 = findViewById(R.id.imageButton3);


        Path tvPath = new Path();
        tvPath.moveTo(235, 332);
        tvPath.lineTo(137, 332);
        tvPath.close();
        pm1 = new PathMeasure(tvPath, false);

        Path vasePath = new Path();
        vasePath.moveTo(671, 238);
        vasePath.lineTo(861, 238);
        vasePath.close();
        pm2 = new PathMeasure(vasePath, false);

        Path wdPath = new Path();
        wdPath.moveTo(471, 90);
        wdPath.lineTo(540, 90);
        wdPath.close();
        pm3 = new PathMeasure(wdPath, false);


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
            //Log.d(DEBUG_TAG,"onDown: " + event.toString());
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1, MotionEvent event2,
                               float velocityX, float velocityY) {
            //fling(velocityX);
           // Log.d(DEBUG_TAG, "onFling: " + velocityX);
            return true;
        }



        @Override
        public boolean onScroll(MotionEvent event1, MotionEvent event2, float distanceX,
                                float distanceY) {

            scroll(distanceX);
            track(pm1, ib, 1.8f,0, 127); //~1.62 distance/frames
            track(pm2, ib2,2.0f,170,250); //~2.34 distance/frames
            track(pm3, ib3,2.3f,220,250); // exact wartosc distance/frames


            //Log.d(DEBUG_TAG, "onScroll: " + distanceX + " " + event2.toString());
            return true;
        }
    }


    public void scroll(float distanceX) {
        float orientation = 0;
            orientation = distanceX;
        int jump = (int) Math.abs(orientation * 1);

        if (orientation > 0 && (counter + jump) < IMAGES_COUNT)
            counter += jump;

        if (orientation < 0 && (counter - jump) > 0)
            counter -= jump;


        if (counter >= 100)
            fileName = fileName.substring(0, fileName.length() - 3) + counter;

        else if (counter >= 10 && counter < 100)
            fileName = fileName.substring(0, fileName.length() - 3) + "0" + counter;
        else
            fileName = fileName.substring(0, fileName.length() - 3) + "00" + counter;

        int res = this.getResources().getIdentifier(fileName, "drawable", this.getPackageName());

        Log.d(DEBUG_TAG,"Counter:" + counter + "   X:" + ib3.getX() + ib3.getY());


        img.setImageResource(res);
    }

    public void fling(float velocityX) {

    }

    public void track(PathMeasure pm, ImageButton ib, float frame2posRatio, int firstFrame, int lastFrame){


        boolean isInFrame = counter >= firstFrame && counter <= lastFrame;

        float fSegmentLen = pm.getLength() / ((lastFrame-firstFrame));
        float afP[] = {0f, 0f};

        if (isInFrame) {
            pm.getPosTan(fSegmentLen * (counter-firstFrame)/frame2posRatio, afP, null);
            //Log.d(DEBUG_TAG," " + afP[0]);
            ib.setX( (int)(afP[0]));
        }
        if(!isInFrame) {
            ib.setVisibility(View.INVISIBLE);
            ib.setEnabled(false);
        }
        else {
            ib.setVisibility(View.VISIBLE);
            ib.setEnabled(true);
        }
    }

    public void popupMessage(View view){

        switch(view.getId()){
            case R.id.imageButton: Toast.makeText(this,"Telewizor", Toast.LENGTH_SHORT).show(); break;
            case R.id.imageButton2: Toast.makeText(this,"Waza",Toast.LENGTH_SHORT).show(); break;
            case R.id.imageButton3: Toast.makeText(this,"Szafa",Toast.LENGTH_SHORT).show(); break;
            default: Log.d(DEBUG_TAG,"ID:" + view.getId());
        }


    }


}




