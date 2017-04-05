package fr.wcs.alarmtest2;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.icu.util.Calendar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;

public class MainActivity extends AppCompatActivity {

    AlarmManager alarmManager;
    TimePicker alarmTimePicker;
    TextView textViewYourAlarm;
    Context context;
    PendingIntent myPendingIntent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        this.context = this;
        alarmTimePicker = (TimePicker) findViewById(R.id.timePicker);
        alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
        textViewYourAlarm = (TextView) findViewById(R.id.textViewYourAlarm);
        final Calendar calendar = Calendar.getInstance();

        Button buttonStart = (Button) findViewById(R.id.buttonStart);
        Button buttonStop = (Button) findViewById(R.id.buttonStop);

        final Intent intent = new Intent(this.context, Alarm_Receiver.class);

        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                calendar.set(Calendar.HOUR_OF_DAY, alarmTimePicker.getHour());
                calendar.set(Calendar.MINUTE, alarmTimePicker.getMinute());

                int hour = alarmTimePicker.getHour();
                int minute = alarmTimePicker.getMinute();

                String hourString = String.valueOf(hour);
                String minuteString = String.valueOf(minute);

                if (minute < 10){
                    minuteString = "0"+String.valueOf(minute);
                }

                set_alarm_text("Votre alarme est programmé pour " + hourString + ":" + minuteString);

                myPendingIntent = PendingIntent.getBroadcast(MainActivity.this, 0,
                        intent, PendingIntent.FLAG_UPDATE_CURRENT);

                alarmManager.set(AlarmManager.RTC_WAKEUP, calendar.getTimeInMillis(), myPendingIntent);
            }
        });

        buttonStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                set_alarm_text("Alarme off");
                alarmManager.cancel(myPendingIntent);
            }
        });
    }

    private void set_alarm_text(String output) {
        textViewYourAlarm.setText(output);
    }
}
