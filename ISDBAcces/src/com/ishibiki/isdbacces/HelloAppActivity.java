package com.ishibiki.isdbacces;

import android.app.Activity;
import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;


public class HelloAppActivity extends Activity {
	private EditText name = null;
	private EditText tel = null;
	private EditText content = null;
	private DatabaseHelper dbhelper = null;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_hello_app);
		name = (EditText) this.findViewById(R.id.edit1);
		tel = (EditText) this.findViewById(R.id.edit2);
		content = (EditText) this.findViewById(R.id.edit3);
		dbhelper = new DatabaseHelper(this);
	}

	public void doSave(View view) {
		Editable s_name = name.getText();
		Editable s_tel = tel.getText();
		Editable s_content = content.getText();
		SQLiteDatabase db= dbhelper.getWritableDatabase();
		ContentValues values = new ContentValues();
		values.put(DatabaseHelper.NAME, s_name.toString());
		values.put(DatabaseHelper.TEL, s_tel.toString());
		values.put(DatabaseHelper.CONTENT, s_content.toString());
		db.insert(DatabaseHelper.TABLE_NAME,null,values);
		name.setText("");
		tel.setText("");
		content.setText("");
		Toast toast = Toast.makeText(this, "saved",
			Toast.LENGTH_SHORT);
		toast.show();
	}

	public void doDel(View view) {
		Editable s_name = name.getText();
		SQLiteDatabase db = dbhelper.getWritableDatabase();
		String findset = DatabaseHelper.NAME + " =?";
		String[] params = {s_name.toString()};
		content.setText("");
		Toast toast = Toast.makeText(this, "delete", Toast.LENGTH_SHORT);
		toast.show();
	}

	public void doFind(View view) {
		Editable s_name = name.getText();
		SQLiteDatabase db = dbhelper.getReadableDatabase();
		String findset = DatabaseHelper.NAME + "=?";
		String[] params = {s_name.toString()};
		Cursor c = db.query(DatabaseHelper.TABLE_NAME, DatabaseHelper.COL_ARR,
				findset, params, null,null,null,null);
		if (c.moveToFirst()) {
			name.setText(c.getString(1));
			tel.setText(c.getString(2));
			content.setText(c.getString(3));
		}
		else {
			Toast toast = Toast.makeText(this, "not find '" + s_name + "'.",
					Toast.LENGTH_SHORT);
			toast.show();
		}

	}

}
