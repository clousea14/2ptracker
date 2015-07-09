package com.example.studentprojectwork;

import java.util.ArrayList;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class Co_NewRegister extends Activity {

	// Progress Dialog
	private ProgressDialog pDialog;

	JSONParser jsonParser = new JSONParser();
	EditText firstName,lastname;
	EditText email;
	EditText password;

	// url to create new product
	private static String url_create_product = "http://192.168.163.1/student/create_user.php";

	// JSON Node names
	private static final String TAG_SUCCESS = "success";

	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
//		setContentView(R.layout.ui_register);

		//Remove title bar
		this.requestWindowFeature(Window.FEATURE_NO_TITLE);

		//Remove notification bar
		this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

		//set content view AFTER ABOVE sequence (to avoid crash)
		this.setContentView(R.layout.ui_register);
		// Edit Text
		firstName = (EditText) findViewById(R.id.ET_firstName);
		lastname = (EditText) findViewById(R.id.ET_lastName);
		email = (EditText) findViewById(R.id.ET_RegEmail);
		password = (EditText) findViewById(R.id.ET_RegPassword);

		// Create button
		Button btnRegisterUser = (Button) findViewById(R.id.BTN_Reg_Register);

		// button click event
		btnRegisterUser.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View view) {
				if(!firstName.equals("") && !lastname.equals("") && !email.equals("") && !password.equals(""))
				{
					new CreateNewUser().execute();
				}
				else{
					Toast msg=Toast.makeText(Co_NewRegister.this,"Detail not Complete",Toast.LENGTH_LONG);
					msg.setGravity(Gravity.CENTER,0,0);
					msg.show();
				}


			}
		});
	}

	/**
	 * Background Async Task to Create new product
	 * */
	class CreateNewUser extends AsyncTask<String, String, String> {

		/**
		 * Before starting background thread Show Progress Dialog
		 * */
		@Override
		protected void onPreExecute() {
			super.onPreExecute();
			pDialog = new ProgressDialog(Co_NewRegister.this);
			pDialog.setMessage("Creating User..");
			pDialog.setIndeterminate(false);
			pDialog.setCancelable(false);
			pDialog.show();
		}

		/**
		 * Creating product
		 * */
		protected String doInBackground(String... args) {
			String first = firstName.getText().toString();
			String last = lastname.getText().toString();
			String emailname = email.getText().toString();
			String userpassword = password.getText().toString();


			// Building Parameters
			List<NameValuePair> params = new ArrayList<NameValuePair>();
			params.add(new BasicNameValuePair("finame", first));
			params.add(new BasicNameValuePair("laname", last));
			params.add(new BasicNameValuePair("email", emailname));
			params.add(new BasicNameValuePair("psname", userpassword));


			// getting JSON Object
			// Note that create product url accepts POST method
			JSONObject json = jsonParser.makeHttpRequest(url_create_product,
					"POST", params);
			Intent i = new Intent(getApplicationContext(), Co_ProjectIndex.class);
			startActivity(i);
//			try{
//				Log.d("Create Response", json.toString());
//			}catch(Exception e)
//			{
//				String k=e.toString();
//				Toast.makeText(Co_NewRegister.this, e.toString(), Toast.LENGTH_LONG).show();
//			}
			// check log cat fro response


			// check for success tag
//			try {
//				int success = json.getInt(TAG_SUCCESS);
//
//				if (success == 1) {
//					// successfully created product
//					Intent i = new Intent(getApplicationContext(), Co_ProjectEditPage.class);
//					startActivity(i);
//					
//					// closing this screen
//					finish();
//				} else {
//					// failed to create product
//				}
//			} catch (JSONException e) {
//				String k=e.toString();
//				Toast.makeText(Co_NewRegister.this, e.toString(), Toast.LENGTH_LONG).show();
//			}

			return null;
		}

		/**
		 * After completing background task Dismiss the progress dialog
		 * **/
		protected void onPostExecute(String file_url) {
			// dismiss the dialog once done
			pDialog.dismiss();
		}

	}
}
