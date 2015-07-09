package com.example.studentprojectwork;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class Co_LoginActivity extends Activity {
    TextView Tv_forus, TV_forpw;
    EditText ET_email, ET_password;

    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_create_product = "http://192.168.163.1/student/getuserlogin.php";
    private static final String TAG_SUCCESS = "success";

    public static String emailname, dbuname;
    public static String userpassword;
    Boolean loginfailed = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.ui_loginpage);

        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.ui_loginpage);

        Tv_forus = (TextView) findViewById(R.id.forgetUs);
        TV_forpw = (TextView) findViewById(R.id.forgetPassword);
        ET_email = (EditText) findViewById(R.id.ET_email);
        ET_password = (EditText) findViewById(R.id.ET_Password);

        Tv_forus.setPaintFlags(Tv_forus.getPaintFlags()
                | Paint.UNDERLINE_TEXT_FLAG);
        TV_forpw.setPaintFlags(TV_forpw.getPaintFlags()
                | Paint.UNDERLINE_TEXT_FLAG);
    }

    public void openhome(View v) {

        try {
            new LoginUser().execute();
        } catch (Exception e) {

        }

    }

    public void signup(View v) {

        Intent in = new Intent(Co_LoginActivity.this, Co_NewRegister.class);
        startActivity(in);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.co_login, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent = new Intent(Co_LoginActivity.this,
                Co_PrefsActivity.class);
        startActivity(intent);
        return true;
    }

    /**
     * Background Async Task to Create new product
     */
    class LoginUser extends AsyncTask<String, String, String> {

        /**
         * Before starting background thread Show Progress Dialog
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Co_LoginActivity.this);
            pDialog.setMessage("Validating User..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        /**
         * Creating product
         */
        protected String doInBackground(String... args) {
            emailname = ET_email.getText().toString();
            dbuname = emailname;
            userpassword = ET_password.getText().toString();
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("emailname", emailname));
            params.add(new BasicNameValuePair("uspass", userpassword));
            // getting JSON Object
            // Note that create product url accepts POST method
            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);
            try {
                Log.d("Create Response", json.toString());
            } catch (Exception e) {

            }
            // check log cat fro response


            // check for success tag
            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product
                    loginfailed = false;
                    Intent i = new Intent(getApplicationContext(), Co_ProjectIndex.class);
                    startActivity(i);
                    // closing this screen
                } else {
                    loginfailed = true;
                }
            } catch (JSONException exc) {
            }

            return null;
        }

        /**
         * After completing background task Dismiss the progress dialog
         * *
         */
        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            if (loginfailed == true) {
                Toast msg = Toast.makeText(Co_LoginActivity.this, " Invalid Login Details Please SignUp", Toast.LENGTH_LONG);
                msg.setGravity(Gravity.CENTER, 0, 0);
                msg.show();
            } else {
            }

            pDialog.dismiss();
        }

    }
}
