package com.example.ejazacal;

import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.content.res.Configuration;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class MainActivity extends AppCompatActivity {
    private EditText editTextDate, editTextDate2, hawafz, frq ;
    private TextView resultt;
    private Button button;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        // Force the activity to run in English
        Configuration config = getResources().getConfiguration();
        Locale.setDefault(Locale.ENGLISH);
        config.locale = Locale.ENGLISH;
        getResources().updateConfiguration(config, getResources().getDisplayMetrics());

        setContentView(R.layout.activity_main);
        // Rest of your activity code...
        editTextDate = findViewById(R.id.editTextDate);
        editTextDate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog();
            }
        });
        editTextDate2 = findViewById(R.id.editTextDate2);
        hawafz = findViewById(R.id.hawafz);
        frq = findViewById(R.id.frq);
        resultt = findViewById(R.id.textres1);
        editTextDate2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDatePickerDialog2();
            }

        });

        button = findViewById(R.id.button);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String dateString = editTextDate.getText().toString();
                DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
                int dayInt = 0;
                int monthint = 0;
                int yearint =0;
                try {
                    Date date = dateFormat.parse(dateString);
                    DateFormat dayFormat = new SimpleDateFormat("dd");
                    String day = dayFormat.format(date);
                     dayInt = Integer.parseInt(day);
                    DateFormat monthformat = new SimpleDateFormat("MM");
                    String month = monthformat.format(date);
                    monthint = Integer.parseInt(month);

                    DateFormat yearformat = new SimpleDateFormat("yyyy");
                    String year = yearformat.format(date);
                    yearint = Integer.parseInt(year);
                    // day 1 calculations

                } catch (ParseException e) {
                    e.printStackTrace();
                }

                // day2 ===============
                String dateString2 = editTextDate2.getText().toString();
                DateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
                String outputdate = "";
                int dayInt2 = 0;
                int monthint2 = 0 ;
                int yearint2 =0;
                try {
                    Date date = dateFormat.parse(dateString2);
                    DateFormat dayFormat = new SimpleDateFormat("dd");
                    String day = dayFormat.format(date);
                     dayInt2 = Integer.parseInt(day);
                    DateFormat monthformat = new SimpleDateFormat("MM");
                    String month = monthformat.format(date);
                    monthint2 = Integer.parseInt(month);

                    DateFormat yearformat = new SimpleDateFormat("yyyy");
                    String year = yearformat.format(date);
                    yearint2 = Integer.parseInt(year);
                    // day 1 calculations

                } catch (ParseException e) {
                    e.printStackTrace();
                }
                int totaldays = 0;
                int months = 0;
                int realfrq = 0;
                String hawafzText = hawafz.getText().toString();
                String frqText = frq.getText().toString();

                if (hawafzText.isEmpty() || frqText.isEmpty()) {
                    // Handle empty input case
                    Toast.makeText(MainActivity.this, "الرجاء ملىء كافة الحقول", Toast.LENGTH_SHORT).show();
                    return;
                }

                try {
                    int hawafzreal = Integer.parseInt(hawafzText);
                    int frq = Integer.parseInt(frqText);

                    LocalDate date1 = LocalDate.of(yearint, monthint, dayInt);
                    LocalDate date2 = LocalDate.of(yearint2, monthint2, dayInt2);

// Calculate the number of days between the two dates
                    long daysBetween = ChronoUnit.DAYS.between(date1, date2);
                    int daysBetweenInt = (int) daysBetween;
                    months = monthint + monthint2;
                    totaldays = daysBetweenInt;
                    int realdays = 0 ;
                    if(monthint == monthint2 && totaldays >= 4) {
                         realdays = totaldays - 4;
                    }
                    else if ( monthint < monthint2 && dayInt < 26 && dayInt2 > 4 ) {
                        realdays = totaldays - 8;
                    }

                    else {
                        realdays = totaldays - 4;
                    }
                    if (realdays < 0) {
                        realdays = 0;
                    }
                    int realhawafz = hawafzreal / 30;
                    int totalmoney =0 ;
                    int dayhw = 0 ;
                    if(monthint == monthint2){
                        dayhw = 14;
                    }
                    else if (monthint2 > monthint){
                        dayhw = 28;
                    }
                    if (realdays > dayhw){
                     realfrq = frq * (2 / 3);
                      totalmoney = (hawafzreal) + (realfrq * totaldays);
                    }
                    else{
                         realfrq = 2500;
                         totalmoney = (realdays * 2 * realhawafz) + (realfrq * totaldays);
                    }

                    outputdate = String.valueOf(totalmoney);
                    resultt.setText(outputdate);
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    // Handle invalid input case
                    Toast.makeText(MainActivity.this, "Invalid input", Toast.LENGTH_SHORT).show();
                }
               // Toast.makeText(MainActivity.this   , outputdate, Toast.LENGTH_SHORT).show();
            }
        });

    }

    public void handleClick(View view) {
        String text = editTextDate2.getText().toString();
        Toast.makeText(this, text, Toast.LENGTH_SHORT).show();
    }
    public void showDatePickerDialog() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    // Month is 0-based, so add 1
                    String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    editTextDate.setText(selectedDate);
                }
            };
    // date listener 2

    public void showDatePickerDialog2() {
        DatePickerDialog datePickerDialog = new DatePickerDialog(this, dateSetListener2,
                Calendar.getInstance().get(Calendar.YEAR),
                Calendar.getInstance().get(Calendar.MONTH),
                Calendar.getInstance().get(Calendar.DAY_OF_MONTH));
        datePickerDialog.show();
    }

    private DatePickerDialog.OnDateSetListener dateSetListener2 =
            new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    // Month is 0-based, so add 1
                    String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    editTextDate2.setText(selectedDate);
                }
            };


}