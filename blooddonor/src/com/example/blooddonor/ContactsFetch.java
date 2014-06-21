package com.example.blooddonor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import DatabaseHandler.DBhandler;
import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteConstraintException;
import android.os.AsyncTask;
import android.provider.ContactsContract;
import beans.Contact;

class ContactsFetch extends AsyncTask<String, String, String> {
	String phone;
	String name;
	DBhandler db;
	Context context;
	ArrayList<String> contactNames;
	ArrayList<String> phoneNumbers;

	public ContactsFetch(Context context) {
		// TODO Auto-generated constructor stub
		this.context = context;

	}

	@Override
	protected void onPreExecute() {
		// TODO Auto-generated method stub
		super.onPreExecute();
		contactNames = new ArrayList<String>();
		phoneNumbers = new ArrayList<String>();
		db = new DBhandler(context.getApplicationContext());
		List<Contact> contactslist = db.getAllContacts();

		sortAndFillArraylistFromDB(contactslist);

		// contactsTotal.setText(contactNames.size() + " People");

	}

	@Override
	protected String doInBackground(String... params) {

		readContacts();

		return null;
	}

	@Override
	protected void onPostExecute(String result) {
		// TODO Auto-generated method stub
		super.onPostExecute(result);
		db = new DBhandler(context.getApplicationContext());
		List<Contact> contactslist = db.getAllContacts();

		sortAndFillArraylistFromDB(contactslist);

	}

	private void sortAndFillArraylistFromDB(List<Contact> contactslist) {
		// TODO Auto-generated method stub
		Map<String, String> unsortMap = new HashMap<String, String>();
		for (Contact cn : contactslist) {
			unsortMap.put(cn.get_phone_number(), cn.get_name());
			String log = " Name: " + cn.get_name() + " ,Phone: "+ cn.get_phone_number();
		//	System.out.println(log);
		}
		Map<String, String> sortedMap = sortByComparator(unsortMap);

		fetchArray(sortedMap);
	}

	@SuppressWarnings({ "unchecked", "rawtypes" })
	private Map<String, String> sortByComparator(Map<String, String> unsortMap) {

		List list = new LinkedList(unsortMap.entrySet());

		// sort list based on comparator
		Collections.sort(list, new Comparator() {
			public int compare(Object o1, Object o2) {
				return ((Comparable) ((Map.Entry) (o1)).getValue())
						.compareTo(((Map.Entry) (o2)).getValue());
			}
		});

		// put sorted list into map again
		// LinkedHashMap make sure order in which keys were inserted
		Map<String, String> sortedMap = new LinkedHashMap<String, String>();
		for (Iterator it = list.iterator(); it.hasNext();) {
			Map.Entry<String, String> entry = (Map.Entry<String, String>) it
					.next();
			sortedMap.put(entry.getKey(), entry.getValue());
		}
		return sortedMap;
	}

	public void fetchArray(Map<String, String> map) {
		for (Map.Entry entry : map.entrySet()) {
		//	System.out.println("Key : " + entry.getKey() + " Value : "	+ entry.getValue());

			contactNames.add(entry.getValue().toString());
			phoneNumbers.add(entry.getKey().toString());
		}

	}

	public ArrayList<String> getContactNames() {
		return this.contactNames;
	}

	public ArrayList<String> getPhoneNumbers() {
		return this.phoneNumbers;
	}

	public void readContacts() throws SQLiteConstraintException {
		ContentResolver cr = context.getContentResolver();
		Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI, null,
				null, null, null);

		if (cur.getCount() > 0) {

			while (cur.moveToNext()) {
				String id = cur.getString(cur
						.getColumnIndex(ContactsContract.Contacts._ID));
				name = cur
						.getString(cur
								.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
				if (Integer
						.parseInt(cur.getString(cur
								.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
				//	System.out.println("name : " + name + ", ID : " + id);

					// get the phone number
					Cursor pCur = cr.query(
							ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
							null,
							ContactsContract.CommonDataKinds.Phone.CONTACT_ID
									+ " = ?", new String[] { id }, null);
					while (pCur.moveToNext()) {
						phone = pCur
								.getString(pCur
										.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
						//System.out.println("phone" + phone);
					}
					pCur.close();

	

				}
				

				if (phone == null) {
					continue;
				}
				try {
					db.addContact(new Contact(id,name, phone));
				} catch (SQLiteConstraintException e) {
					System.out
							.println("caught exception during adding contact");
				}

			}

		}

		// llllllllll

	}

}