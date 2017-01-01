package com.koleng.jirayut.servicesimple;

import android.Manifest;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import com.koleng.jirayut.servicesimple.services.DrugNotifyService;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity implements DatePickerDialog.OnDateSetListener, Button.OnClickListener {

    private Context context = this;
    private static String SCHEDULE = "schedule";
    private DatePickerDialog datePickerDialog;
    private Calendar calendar;
    private TextView textView;
    private EditText editPhone;
    private Button btnCall;
    private View rootView;
    private static int REQUEST_CODE_CALL_PHONE = 10001;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        rootView = findViewById(R.id.root_view);
        editPhone = (EditText) findViewById(R.id.editPhone);
        btnCall = (Button) findViewById(R.id.btnCall);
        btnCall.setOnClickListener(this);

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
        nextDay.set(Calendar.YEAR, year);
        nextDay.set(Calendar.MONTH, month);
        nextDay.set(Calendar.DAY_OF_MONTH, dayOfMonth);

        long diff = nextDay.getTimeInMillis() - calendar.getTimeInMillis();
        long days = diff / (24 * 60 * 60 * 1000);

        Snackbar.make(rootView, year + "/" + month + "/" + dayOfMonth + " diff=" + days, Snackbar.LENGTH_LONG).setAction("Action", null).show();
    }


    @Override
    public void onClick(View v) {
        int id = v.getId();
        switch (id) {
            case R.id.btnCall: {
                ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CALL_PHONE}, REQUEST_CODE_CALL_PHONE);
                break;
            }
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == REQUEST_CODE_CALL_PHONE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                this.CallPhone(editPhone.getText().toString());
            } else {
                Snackbar.make(rootView, "Permission denied.", Snackbar.LENGTH_LONG).setAction("Action", null).show();
            }
            return;
        }
    }

    private void CallPhone(String number) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        intent.setData(Uri.parse("tel:" + number));
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            // TODO: Consider calling
            //    ActivityCompat#requestPermissions
            // here to request the missing permissions, and then overriding
            //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
            //                                          int[] grantResults)
            // to handle the case where the user grants the permission. See the documentation
            // for ActivityCompat#requestPermissions for more details.
            return;
        }
        startActivity(intent);
    }
}
