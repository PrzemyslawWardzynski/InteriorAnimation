package com.example.interioranimation;




import android.support.v4.view.GestureDetectorCompat;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import android.view.MotionEvent;

import android.view.ViewGroup;

import android.widget.ImageView;



public class MainActivity extends AppCompatActivity{
    private static final String DEBUG_TAG = "Gestures";
    private static int IMAGES_COUNT = 251;
    private static int SPEED = 4;


    private ViewGroup mainLayout;
    private ImageView img;

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





    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        int action = event.getActionMasked();

        switch (action) {
            case (MotionEvent.ACTION_DOWN):
                Log.d(DEBUG_TAG, "Action was DOWN");
                return true;
            case (MotionEvent.ACTION_MOVE):

                float orientation=0;
                if(event.getHistorySize() > 0 )
                    orientation =  event.getX() - event.getHistoricalX(event.getHistorySize()-1);
                    int jump = (int) Math.abs(orientation*SPEED);

                if(orientation <0 && (counter + jump)< IMAGES_COUNT )
                    counter += jump;

                if (orientation > 0 && (counter - jump) > 0)
                    counter -= jump;



                if(counter >= 100)
                    fileName = fileName.substring(0,fileName.length()-3) + counter;

                else if (counter >= 10 && counter <100)
                    fileName = fileName.substring(0,fileName.length()-3) +"0" + counter;
                else
                    fileName = fileName.substring(0,fileName.length()-3) + "00" + counter;

                Log.d(DEBUG_TAG,",NAME:" + orientation);
                int res = this.getResources().getIdentifier(fileName, "drawable", this.getPackageName());
                img.setImageResource(res);












                return true;

            default:
                return super.onTouchEvent(event);
        }
    }
}

