package com.koleng.jirayut.servicesimple;

import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.DatePicker;
import android.widget.TextView;

import com.koleng.jirayut.servicesimple.services.DrugNotifyService;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener {

    private Context context = this;
    private static String SCHEDULE = "schedule";
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private TextView textView;
    private View rootView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rootView = findViewById(R.id.root_view);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //start service
                datePickerDialog.show();
                //Snackbar.make(view, "Started service", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
        });

        textView = (TextView) findViewById(R.id.textView);

        calendar = Calendar.getInstance();
        datePickerDialog = new DatePickerDialog(context, this, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH));

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

    @Override
    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {

        Calendar nextDay = Calendar.getInstance();
        nextDay.set(Calendar.YEAR ,year );
        nextDay.set(Calendar.MONTH , month);
        nextDay.set(Calendar.DAY_OF_MONTH , dayOfMonth);

        long diff = nextDay.getTimeInMillis() - calendar.getTimeInMillis();
        long days = diff / (24 * 60 * 60 * 1000);

        Snackbar.make(rootView, year+"/"+month+"/"+dayOfMonth+" diff="+days, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }
}
