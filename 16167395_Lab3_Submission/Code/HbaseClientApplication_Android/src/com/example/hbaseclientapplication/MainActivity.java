package com.example.hbaseclientapplication;

import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.Button;
import android.os.Build;

public class MainActivity extends ActionBarActivity implements OnClickListener {

	Button createTableButton;
	Button insertTableButton;
	Button retrieveTableButton;
	Button deleteTableButton;
	Uri uri;
	Intent intent;
	Intent chooser;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		createTableButton = (Button) findViewById(R.id.createTableButton);
		insertTableButton = (Button) findViewById(R.id.insertTableButton);
		retrieveTableButton = (Button) findViewById(R.id.retrieveTableButton);
		deleteTableButton = (Button) findViewById(R.id.deleteTableButton);

		createTableButton.setOnClickListener(this);
		insertTableButton.setOnClickListener(this);
		retrieveTableButton.setOnClickListener(this);
		deleteTableButton.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		// TODO Auto-generated method stub

		if (v.getId() == R.id.createTableButton) {

			uri = Uri
					.parse("http://134.193.136.127:8080/HbaseWS/jaxrs/generic/hbaseCreate/RakeshTable3/GeoLocation:Date:Accelerometer");

			intent = new Intent(Intent.ACTION_VIEW, uri);

			chooser = Intent.createChooser(intent, "Open Using");

			startActivity(chooser);

		} else if (v.getId() == R.id.insertTableButton) {

			uri = Uri
					.parse("http://134.193.136.127:8080/HbaseWS/jaxrs/generic/hbaseInsert/RakeshTable3/-home-cloudera-sensor.txt/GeoLocation:Date:Accelerometer");

			intent = new Intent(Intent.ACTION_VIEW, uri);

			chooser = Intent.createChooser(intent, "Open Using");

			startActivity(chooser);

		} else if (v.getId() == R.id.retrieveTableButton) {
			uri = Uri
					.parse("http://134.193.136.127:8080/HbaseWS/jaxrs/generic/hbaseRetrieveAll/RakeshTable3");

			intent = new Intent(Intent.ACTION_VIEW, uri);

			chooser = Intent.createChooser(intent, "Open Using");

			startActivity(chooser);

		} else if (v.getId() == R.id.deleteTableButton) {
			uri = Uri
					.parse("http://134.193.136.127:8080/HbaseWS/jaxrs/generic/hbasedeletel/RakeshTable3");

			intent = new Intent(Intent.ACTION_VIEW, uri);

			chooser = Intent.createChooser(intent, "Open Using");

			startActivity(chooser);

		}
	}
}
