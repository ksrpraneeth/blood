package Utils;

import java.io.InputStream;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.provider.ContactsContract;
import android.provider.ContactsContract.PhoneLookup;

public class Utils {
	public static Bitmap getFacebookPhoto(Context context, String phoneNumber) {
		Uri phoneUri = Uri.withAppendedPath(PhoneLookup.CONTENT_FILTER_URI,
				Uri.encode(phoneNumber));
		Uri photoUri = null;
		ContentResolver cr = context.getContentResolver();
		Cursor contact = cr.query(phoneUri,
				new String[] { ContactsContract.Contacts._ID }, null, null,
				null);

		if (contact.moveToFirst()) {
			long userId = contact.getLong(contact
					.getColumnIndex(ContactsContract.Contacts._ID));
			photoUri = ContentUris.withAppendedId(
					ContactsContract.Contacts.CONTENT_URI, userId);

		} else {
			Bitmap defaultPhoto = BitmapFactory.decodeResource(
					context.getResources(),
					android.R.drawable.ic_menu_report_image);
			return defaultPhoto;
		}
		if (photoUri != null) {
			InputStream input = ContactsContract.Contacts
					.openContactPhotoInputStream(cr, photoUri);
			if (input != null) {
				return BitmapFactory.decodeStream(input);
			}
		} else {
			Bitmap defaultPhoto = BitmapFactory.decodeResource(
					context.getResources(),
					android.R.drawable.ic_menu_report_image);
			return defaultPhoto;
		}
		Bitmap defaultPhoto = BitmapFactory
				.decodeResource(context.getResources(),
						android.R.drawable.ic_menu_report_image);
		return defaultPhoto;
	}
}
