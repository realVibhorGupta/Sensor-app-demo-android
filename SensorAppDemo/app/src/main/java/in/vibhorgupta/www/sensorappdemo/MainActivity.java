package in.vibhorgupta.www.sensorappdemo;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements SensorEventListener{

    public static final int UPDATE_THRESHOLD = 500 ;
    private TextView mXValue,mYValue,mZValue ;
    private SensorManager sensorManager;
    private Sensor mAccelerometer;
    private static long mLastUpdate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mXValue= (TextView) findViewById(R.id.xaxis);
        mYValue= (TextView) findViewById(R.id.yaxis);
        mZValue= (TextView) findViewById(R.id.zaxis);

        sensorManager= (SensorManager) getSystemService(SENSOR_SERVICE);

        if(null == (mAccelerometer  =  sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER)))
        {
            finish();
        }


    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(MainActivity.this,mAccelerometer,SensorManager.SENSOR_DELAY_UI);
        mLastUpdate = System.currentTimeMillis();
    }

    @Override
    protected void onPause() {
        sensorManager.unregisterListener(MainActivity.this);
        super.onPause();

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {

        if(sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER)
        {
            long  actualTime = System.currentTimeMillis();

            if (actualTime - mLastUpdate > UPDATE_THRESHOLD)
            {
                mLastUpdate = actualTime ;
                float x = sensorEvent.values[0],y = sensorEvent.values[1],z = sensorEvent.values[2];

                mXValue.setText(String.valueOf(x));
                mYValue.setText(String.valueOf(y));
                mZValue.setText(String.valueOf(z));


            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {
        

    }
}
