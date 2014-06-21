package com.example.blooddonor;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import android.os.Bundle;
import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.text.InputType;
import android.view.Menu;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

@SuppressLint("SimpleDateFormat")
public class Register extends Activity implements OnClickListener {
	EditText name;
	TextView dob, lastdate;
	Spinner bloodgroup;
	Button register, dobcal, lastcal;
	String test1;
	List<String> bloodgrouplist;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_register);
		name = (EditText) findViewById(R.id.name);
		dob = (TextView) findViewById(R.id.dob);

		lastdate = (TextView) findViewById(R.id.lastdate);

		bloodgroup = (Spinner) findViewById(R.id.bloodgroup);
		register = (Button) findViewById(R.id.register);
		register.setOnClickListener(this);
		dobcal = (Button) findViewById(R.id.dobcal);
		dobcal.setOnClickListener(this);
		lastcal = (Button) findViewById(R.id.lastcal);
		lastcal.setOnClickListener(this);

		bloodgrouplist = new ArrayList<String>();
		bloodgrouplist.add("B+");
		bloodgrouplist.add("O+");
		ArrayAdapter<String> adapterbacklink;
		adapterbacklink = new ArrayAdapter<String>(this,
				android.R.layout.simple_spinner_item, bloodgrouplist);
		bloodgroup.setAdapter(adapterbacklink);

	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.register, menu);
		return true;
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub
		if (v.getId() == R.id.register) {
			if (name.getText().toString().trim().length() < 1) {
				Toast.makeText(this, "Enter Name", Toast.LENGTH_SHORT).show();
			} else if (dob.getText().toString().trim().length() < 1) {
				Toast.makeText(this, "Enter Date of Birth", Toast.LENGTH_SHORT)
						.show();
			} else if (lastdate.getText().toString().trim().length() < 1) {
				Toast.makeText(this, "Enter Last date of donation",
						Toast.LENGTH_SHORT).show();
			}

			else {
				Intent i = new Intent(this, MainActivity.class);
				startActivity(i);
			}

		} else if (v.getId() == R.id.dobcal) {

			calendarfrom(dob);

		} else if (v.getId() == R.id.lastcal) {
			calendarfrom(lastdate);
		}

	}

	public void calendarfrom(final TextView tv) {
		Calendar c;
		String dateInString;

		// Process to get Current Date
		c = Calendar.getInstance();
		int mYear, mMonth, mDay;
		mYear = c.get(Calendar.YEAR);
		mMonth = c.get(Calendar.MONTH);
		mDay = c.get(Calendar.DAY_OF_MONTH);

		// Launch Date Picker Dialog
		DatePickerDialog dpd = new DatePickerDialog(this,
				new DatePickerDialog.OnDateSetListener() {

					@Override
					public void onDateSet(DatePicker view, int year,
							int monthOfYear, int dayOfMonth) {
						// Display Selected date in textbox
						test1 = dayOfMonth + "-" + (monthOfYear + 1) + "-"
								+ year;

						tv.setText(test1);

					}
				}, mYear, mMonth, mDay);
		if (test1 != null) {
			SimpleDateFormat sdf = new SimpleDateFormat("dd-mm-yyyy");
			Calendar cal = Calendar.getInstance();
			try {
				cal.setTime(sdf.parse(test1));

				sdf = new SimpleDateFormat("dd-mm-yyyy");
				Date resultdate = new Date();
				dateInString = sdf.format(resultdate);
				System.out.println("String date:" + dateInString);
				long test3 = (long) (Long.valueOf(c.getTimeInMillis()) + 568024668000.0);
				dpd.getDatePicker().setMinDate(test3);
			} catch (ParseException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

		}

		dpd.show();

	}

}
