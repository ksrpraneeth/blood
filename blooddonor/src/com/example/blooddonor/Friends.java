package com.example.blooddonor;

import java.io.InputStream;
import java.util.ArrayList;

import Utils.Utils;

import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;

import android.support.v4.app.ListFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

/**
 * A simple {@link android.support.v4.app.Fragment} subclass.
 * 
 */
public class Friends extends ListFragment {
	ListView list;
	ArrayList<String> ContactNames = new ArrayList<String>();
	ArrayList<String> PhoneNumber = new ArrayList<String>();

	public Friends() {
		// Required empty public constructor
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		// Inflate the layout for this fragment
		return inflater.inflate(R.layout.fragment_friends, container, false);

	}

	@Override
	public void onActivityCreated(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onActivityCreated(savedInstanceState);
		list = getListView();
		list.setScrollingCacheEnabled(true);
		list.setFastScrollEnabled(true);
		ContactsFetch fetch = new ContactsFetch(getActivity());
		fetch.execute();
		ContactNames = fetch.getContactNames();
		PhoneNumber = fetch.getPhoneNumbers();
		list.setAdapter(new CustomListAdapter(getActivity(), ContactNames,
				PhoneNumber));
	}
}

class CustomListAdapter extends BaseAdapter {

	Context context;
	ArrayList<String> contactNames = new ArrayList<String>();
	ArrayList<String> phoneNumber = new ArrayList<String>();
	private static LayoutInflater inflater = null;

	public CustomListAdapter(Context context, ArrayList<String> contactNames,
			ArrayList<String> phoneNumber) {
		// TODO Auto-generated constructor stub

		this.context = context;
		inflater = (LayoutInflater) context
				.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		this.contactNames = contactNames;
		this.phoneNumber = phoneNumber;
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return contactNames.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	public class Holder {
		TextView tv;
		ImageView img;
	}

	@Override
	public View getView(final int position, View convertView, ViewGroup parent) {
		// TODO Auto-generated method stub

		final Holder holder = new Holder();
		View rowView = convertView;
		rowView = inflater.inflate(R.layout.contactlistitem, null);
		holder.tv = (TextView) rowView.findViewById(R.id.contactname);
		holder.img = (ImageView) rowView.findViewById(R.id.contactimage);
		holder.tv.setText(contactNames.get(position));

		holder.img.setImageBitmap(Utils.getFacebookPhoto(context,
				phoneNumber.get(position)));
		/*new AsyncTask<String, Void, Bitmap>() {

			@Override
			protected Bitmap doInBackground(String... params) {
				return ;
			}

			@Override
			protected void onPostExecute(Bitmap result) {
				super.onPostExecute(result);

			}
		}.execute();

		rowView.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

			}
		});*/

		return rowView;
	}

}