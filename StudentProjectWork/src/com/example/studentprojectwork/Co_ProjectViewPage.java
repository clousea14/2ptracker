package com.example.studentprojectwork;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Co_ProjectViewPage extends Activity {
    ListView lv, LV_task;
    TextView username;
    EditText ET_projectname, ET_begin, ET_end;
    Button edit,add;
    Spinner SP_InProgress;
    ArrayAdapter<String> adapter, adapter1, adapter2;
    ProgressDialog pDialog, pDialog1;
    JSONParser jsonParser = new JSONParser();
    private static String url_create_produc = "http://192.168.163.1/student/getdetail.php";
    private static String url_create_produc1 = "http://192.168.163.1/student/selectedPrj.php";
    private static final String TAG_SUCCESS = "success";
    JSONObject jo, jo1;
    JSONArray ja, ja1;
    ArrayList<String> ar_prjlist = new ArrayList<String>();
    ArrayList<String> ar_statusname = new ArrayList<String>();
    ArrayList<String> ar_statustask = new ArrayList<String>();
    String title;
    String t1 = "";
    String begin = "";
    String end = "";
    String taskname = "";
    String status = "";
    public static int pos = 0;
    ArrayList<String> tasklist = new ArrayList<String>();
    String part1;
    String[] parts;
    String string;
    Boolean dummy = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
//        setContentView(R.layout.ui_projectview);
        //Remove title bar
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);

        //Remove notification bar
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //set content view AFTER ABOVE sequence (to avoid crash)
        this.setContentView(R.layout.ui_projectview);


        edit = (Button) findViewById(R.id.BTN_Edit);
        add = (Button) findViewById(R.id.BTN_ProjectCount);
        lv = (ListView) findViewById(R.id.listView1);
        ET_projectname = (EditText) findViewById(R.id.TV_Projectname);
        SP_InProgress = (Spinner) findViewById(R.id.SP_InProgress);
        LV_task = (ListView) findViewById(R.id.listView2);
        ET_begin = (EditText) findViewById(R.id.ET_begin);
        ET_end = (EditText) findViewById(R.id.ET_end);
        username = (TextView) findViewById(R.id.TV_Username);
        username.setText(Co_LoginActivity.dbuname);
        ET_projectname.setEnabled(false);
        new prjList().execute();

        try {
            if (pos == 0) {
                title = "Project Progress Tracker";
                ParticularPrjDetail run = new ParticularPrjDetail();
                run.execute();
            }
        } catch (Exception e) {
//            Toast msg = Toast.makeText(Co_ProjectViewPage.this, "Invalid project name", Toast.LENGTH_LONG);
//            msg.setGravity(Gravity.CENTER, 0, 0);
//            msg.show();
        }

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                pos = position;
                title = adapter.getItem(position).toString();
                ParticularPrjDetail run = new ParticularPrjDetail();
                run.execute();
            }
        });

    }

    //    public void edit(View v)
//    {
//        Intent in=new Intent(Co_ProjectViewPage.this,Co_ProjectEditPage.class);
//        startActivity(in);
//    }
    public void logout(View v) {
        Toast msg = Toast.makeText(Co_ProjectViewPage.this, "Loading", Toast.LENGTH_LONG);
        msg.setGravity(Gravity.CENTER, 0, 0);
        msg.show();
        Intent in = new Intent(Co_ProjectViewPage.this, Co_LoginActivity.class);
        startActivity(in);
    }

    public void addnew(View v) {
        Toast msg = Toast.makeText(Co_ProjectViewPage.this, "Loading", Toast.LENGTH_LONG);
        msg.setGravity(Gravity.CENTER, 0, 0);
        msg.show();
        Intent in = new Intent(Co_ProjectViewPage.this, Co_AddProject.class);
        startActivity(in);
    }

    class prjList extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Co_ProjectViewPage.this);
            pDialog.setMessage("Loading project..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(false);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            String user_detail = Co_LoginActivity.dbuname;
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("user_detail", user_detail));

            JSONObject json = jsonParser.makeHttpRequest(url_create_produc,
                    "POST", params);
//            Intent in=new Intent(Co_AddProject.this,Co_ProjectViewPage.class);
//            startActivity(in);
            try {

                String s = json.toString();
                String requiredString = s.substring(s.indexOf("[") + 1,
                        s.indexOf("]"));
                String t = "[" + requiredString + "]";
                ja = new JSONArray(t);
                int n = ja.length();
                ar_prjlist.clear();
                for (int i = 0; i <= n; i++) {
                    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                    jo = ja.getJSONObject(i);
                    // RETRIEVE EACH JSON OBJECT'S FIELDS
                    String t1 = jo.getString("project_name");
                    ar_prjlist.add(t1);

                }
                Log.d("Create Response", json.toString());

            } catch (Exception e) {

            }

            try {
                int success = json.getInt(TAG_SUCCESS);

                if (success == 1) {
                    // successfully created product

                    // Intent i = new Intent(getApplicationContext(),
                    // Co_ProjectIndex.class);
                    // startActivity(i);

                    // closing this screen

                } else {

                }
            } catch (JSONException exc) {

            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            adapter = new ArrayAdapter<String>(Co_ProjectViewPage.this,
                    android.R.layout.simple_list_item_1, ar_prjlist);
            lv.setAdapter(adapter);
            pDialog.dismiss();
        }

    }

    class ParticularPrjDetail extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog1 = new ProgressDialog(Co_ProjectViewPage.this);
            pDialog1.setMessage("Loading project..");
            pDialog1.setIndeterminate(false);
            pDialog1.setCancelable(false);
            pDialog1.show();
        }

        protected String doInBackground(String... args) {

            String prjname = title;
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("prjname", prjname));

            JSONObject json = jsonParser.makeHttpRequest(url_create_produc1,
                    "POST", params);
//            Intent in=new Intent(Co_AddProject.this,Co_ProjectViewPage.class);
//            startActivity(in);
            try {

                String s = json.toString();

                String requiredString = s.substring(s.indexOf("[") + 1,
                        s.indexOf("]"));
                String t = "[" + requiredString + "]";
                ja1 = new JSONArray(t);
                int n = ja1.length();
                for (int i = 0; i <= n; i++) {
                    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                    jo1 = ja1.getJSONObject(i);
                    // RETRIEVE EACH JSON OBJECT'S FIELDS
                    t1 = jo1.getString("project_name");
                    begin = jo1.getString("beginDate");
                    end = jo1.getString("endDate");
                    status = jo1.getString("status_name");
                    taskname = jo1.getString("taskName");
                    if (!status.equals("")) {
                        ar_statustask.clear();
                        ar_statusname.clear();
                        ar_statusname.add(status);
                        ar_statustask.add(taskname);
                    } else {
                        ar_statustask.clear();
                        ar_statusname.clear();
                        ar_statusname.add(status);
                        ar_statustask.add(taskname);
                    }


                }

                Log.d("Create Response", json.toString());

            } catch (Exception e) {

            }

            return null;
        }

        protected void onPostExecute(String file_url) {
            // dismiss the dialog once done
            ET_projectname.setText(t1);
            ET_begin.setText(begin);
            ET_end.setText(end);


            adapter1 = new ArrayAdapter<String>(Co_ProjectViewPage.this,
                    android.R.layout.simple_spinner_item, ar_statusname);
            SP_InProgress.setAdapter(adapter1);
            adapter2 = new ArrayAdapter<String>(Co_ProjectViewPage.this,
                    android.R.layout.simple_spinner_item, ar_statustask);



            string = ar_statustask.get(0).toString();


            if(string.contains(","))
            {
                ar_statustask.clear();
                String parts[] = string.split("\\,");
                for(int i=0;i<parts.length;i++)
                {
                    ar_statustask.add(parts[i]);
                }

                LV_task.setAdapter(adapter2);
                adapter2.notifyDataSetChanged();
            }
            else {
                LV_task.setAdapter(adapter2);
            }





            pDialog1.dismiss();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Co_ProjectViewPage.pos = 0;
    }
}
