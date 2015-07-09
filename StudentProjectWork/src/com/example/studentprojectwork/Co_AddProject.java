package com.example.studentprojectwork;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
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
import android.widget.CalendarView;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

public class Co_AddProject extends Activity {
    ListView lv;
    Button addproj, cancel, done, addmore, submit, begindate, enddate;
    TextView tv_username;
    EditText prjtitle;
    TextView Tv_Logout;
    private ProgressDialog pDialog;
    JSONParser jsonParser = new JSONParser();
    private static String url_create_product = "http://192.168.163.1/student/update_product.php";
    private static final String TAG_SUCCESS = "success";
    ArrayAdapter<String> adapter, adapter2;
    Spinner status;
    JSONObject jo;
    JSONArray ja;
    //
    int n;
    int i;
    ArrayList<String> ss = new ArrayList<String>();
    //    CalendarView cal, cal1;
//    TableRow tblrw1;
    String selectbegindate = "";
    String selectbegindate1 = "";
    String select_enddate;
    String selectedtask = "";
    private ProgressDialog pDialog1;

    private static String url_create_product1 = "http://192.168.163.1/student/insertprjdetail.php";

    private static final String TAG_SUCCESS1 = "success";

    String title1;
    String statusname1;
    String begindate1;
    String enddate1;
    String taskname1;
   
    /** This integer will uniquely define the dialog to be used for displaying date picker.*/
    


    private int pYear;
    private int pMonth;
    private int pDay;
    String st = "a";

    /**
     * This integer will uniquely define the dialog to be used for displaying date picker.
     */
    static final int DATE_DIALOG_ID = 0;

    /**
     * Callback received when the user "picks" a date in the dialog
     */
    private DatePickerDialog.OnDateSetListener pDateSetListener =
            new DatePickerDialog.OnDateSetListener() {

                public void onDateSet(DatePicker view, int year,
                                      int monthOfYear, int dayOfMonth) {
                    pYear = year;
                    pMonth = monthOfYear;
                    pDay = dayOfMonth;

                    if(st.equals("b"))
                    {
                    	selectbegindate=begindate.getText().toString();
                    	updateDisplay2();
                    	
                    	
                    	 begindate.setText(
                                 new StringBuilder()
                                         // Month is 0 based so add 1
                                         .append(pDay).append(":")
                                         .append(pMonth + 1).append(":")
                                         .append(pYear).append(" "));
                    	
                    	select_enddate=begindate.getText().toString();
                    	isDateAfter1(selectbegindate,select_enddate);
                    }if(st.equals("c")){
                    	selectbegindate=begindate.getText().toString();
                    	updateDisplay2();
                    	
                    	
                    	 enddate.setText(
                                 new StringBuilder()
                                         // Month is 0 based so add 1
                                         .append(pDay).append(":")
                                         .append(pMonth + 1).append(":")
                                         .append(pYear).append(" "));
                    	
                    	select_enddate=enddate.getText().toString();
                    	isDateAfter(selectbegindate,select_enddate);
                    	
                    }
                    	
                    	
				
                    

                }
            };
   

   
    private void updateDisplay() {
        begindate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pDay).append(":")
                        .append(pMonth + 1).append(":")
                        .append(pYear).append(" "));
        enddate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pDay).append(":")
                        .append(pMonth + 1).append(":")
                        .append(pYear).append(" "));

    }
    
    private void updateDisplay1() {
        begindate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pDay).append(":")
                        .append(pMonth + 1).append(":")
                        .append(pYear).append(" "));
        
    }
    private void updateDisplay2() {
        enddate.setText(
                new StringBuilder()
                        // Month is 0 based so add 1
                        .append(pDay).append(":")
                        .append(pMonth + 1).append(":")
                        .append(pYear).append(" "));
        
        
    }

    

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        this.setContentView(R.layout.ui_addproject);

        begindate = (Button) findViewById(R.id.Btn_begin);
        enddate = (Button) findViewById(R.id.btn_end);
        prjtitle = (EditText) findViewById(R.id.ET_prjtitle);
        tv_username = (TextView) findViewById(R.id.Tv_Uname);
        Tv_Logout = (TextView) findViewById(R.id.Tv_Logout);
        status = (Spinner) findViewById(R.id.spinner1);
        lv = (ListView) findViewById(R.id.LV_tasklist);
        submit = (Button) findViewById(R.id.BT_submit);
        

        lv.setVisibility(View.GONE);
     

        tv_username.setText(Co_LoginActivity.dbuname);
        begindate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	st="b";
                showDialog(DATE_DIALOG_ID);
              
            }
        });
        enddate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            	st="c";
                showDialog(DATE_DIALOG_ID);
        

            }
        });
        /** Get the current date */
        final Calendar cal = Calendar.getInstance();
        pYear = cal.get(Calendar.YEAR);
        pMonth = cal.get(Calendar.MONTH);
        pDay = cal.get(Calendar.DAY_OF_MONTH);



        load_spinneritem();
        /** Display the current date in the TextView */
        updateDisplay();
        selectbegindate=begindate.getText().toString();
        selectbegindate1=selectbegindate;
        select_enddate=enddate.getText().toString();
        enddate.setText(selectbegindate);
        Tv_Logout.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                Intent in = new Intent(Co_AddProject.this, Co_LoginActivity.class);
                startActivity(in);
                return false;
            }
        });
    lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {

        @Override
        public void onItemClick(AdapterView<?> adpterView, View view,
                                int position, long id) {

            for (int i = 0; i < lv.getChildCount(); i++) {
                if (position == i) {
                    lv.getChildAt(i).setBackgroundColor(Color.BLUE);

                    if (selectedtask.equals("")) {

                        selectedtask = (String) (lv
                                .getItemAtPosition(position));
                    } else {
                        selectedtask = selectedtask + ", "
                                + (String) (lv.getItemAtPosition(position));
                    }
                }

            }
        }
    });

    }

    /**
     * Create a new dialog for date picker
     */
    @Override
    protected Dialog onCreateDialog(int id) {
        switch (id) {
            case DATE_DIALOG_ID:
                return new DatePickerDialog(this,
                        pDateSetListener,
                        pYear, pMonth, pDay);

        }
        return null;
    }

    public void logout(View v) {
        Toast msg = Toast.makeText(getBaseContext(), " Loading ", Toast.LENGTH_LONG);
        msg.setGravity(Gravity.CENTER, 0, 0);
        msg.show();
        Intent in = new Intent(Co_AddProject.this, Co_LoginActivity.class);
        startActivity(in);
    }

    public void cancel(View v) {
        Intent in = new Intent(Co_AddProject.this, Co_ProjectViewPage.class);
        Co_ProjectViewPage.pos = 0;
        startActivity(in);
    }

    public void load_spinneritem() {
        ArrayList<String> ss1 = new ArrayList<String>();
        ss1.add("Not Started");
        ss1.add("InProgress");
        ss1.add("Completed");
        ss1.add("Abandoned");
        adapter2 = new ArrayAdapter<String>(Co_AddProject.this,
                android.R.layout.simple_spinner_item, ss1);
        status.setAdapter(adapter2);
    }

    public void addtask(View v) {

        new Taskname().execute();

        lv.setVisibility(View.VISIBLE);
        

    }

    class Taskname extends AsyncTask<String, String, String> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            pDialog = new ProgressDialog(Co_AddProject.this);
            pDialog.setMessage("Loading task..");
            pDialog.setIndeterminate(false);
            pDialog.setCancelable(true);
            pDialog.show();
        }

        protected String doInBackground(String... args) {

            String task_name = "task_name";
            // Building Parameters
            List<NameValuePair> params = new ArrayList<NameValuePair>();
            params.add(new BasicNameValuePair("task_name", task_name));

            JSONObject json = jsonParser.makeHttpRequest(url_create_product,
                    "POST", params);
//            Intent in=new Intent(Co_AddProject.this,Co_ProjectViewPage.class);
//            startActivity(in);
            try {

                String s = json.toString();
                String requiredString = s.substring(s.indexOf("[") + 1,
                        s.indexOf("]"));
                String t = "[" + requiredString + "]";
                ja = new JSONArray(t);
                n = ja.length();
                for (i = 0; i <= n; i++) {
                    // GET INDIVIDUAL JSON OBJECT FROM JSON ARRAY
                    jo = ja.getJSONObject(i);
                    // RETRIEVE EACH JSON OBJECT'S FIELDS
                    String t1 = jo.getString("task_name");
                    ss.add(t1);

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
            adapter = new ArrayAdapter<String>(Co_AddProject.this,
                    android.R.layout.simple_list_item_1, ss);
            lv.setAdapter(adapter);
            pDialog.dismiss();
        }

    }
    public void isDateAfter(String startDate, String endDate) {
        try {
            String myFormatString = "dd:MM:yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate)) {
            	enddate.setText(
                        new StringBuilder()
                                // Month is 0 based so add 1
                                .append(pDay).append(":")
                                .append(pMonth + 1).append(":")
                                .append(pYear).append(" "));
//                enddate.setText(select_enddate);

            } else {
                enddate.setText(selectbegindate);
                Toast msg = Toast.makeText(Co_AddProject.this, "Your end date is before an start date", Toast.LENGTH_LONG);
                msg.setGravity(Gravity.CENTER, 0, 0);
                msg.show();
            }


        } catch (Exception e) {

            Toast.makeText(Co_AddProject.this, "Invalid Date", Toast.LENGTH_LONG).show();
        }
    }
    public void isDateAfter1(String startDate, String endDate) {
        try {
            String myFormatString = "dd:MM:yyyy"; // for example
            SimpleDateFormat df = new SimpleDateFormat(myFormatString);
            Date date1 = df.parse(endDate);
            Date startingDate = df.parse(startDate);

            if (date1.after(startingDate)) {
            	begindate.setText(
                        new StringBuilder()
                                // Month is 0 based so add 1
                                .append(pDay).append(":")
                                .append(pMonth + 1).append(":")
                                .append(pYear).append(" "));
//                enddate.setText(select_enddate);

            } else {
                begindate.setText(selectbegindate1);
                Toast msg = Toast.makeText(Co_AddProject.this, "Invalid Begin Date", Toast.LENGTH_LONG);
                msg.setGravity(Gravity.CENTER, 0, 0);
                msg.show();
            }


        } catch (Exception e) {

            Toast.makeText(Co_AddProject.this, "Invalid Date", Toast.LENGTH_LONG).show();
        }
    }
  public void submit(View v) {
  title1 = prjtitle.getText().toString();
  statusname1 = status.getSelectedItem().toString();
  begindate1 = begindate.getText().toString();
  enddate1 = enddate.getText().toString();
  taskname1 = selectedtask;

  if (!title1.equals("") && !statusname1.equals("")
          && !begindate1.equals("") && !enddate1.equals("")
          && !taskname1.equals("")) {
      new InsertProject().execute();

  } else {
      Toast msg = Toast.makeText(Co_AddProject.this,
              "Please Complete the Detail", Toast.LENGTH_LONG);
      msg.setGravity(Gravity.CENTER, 0, 0);
      msg.show();

  }
}
  /**
 * Background Async Task to Create new product
 */
public class InsertProject extends AsyncTask<String, String, String> {

    /**
     * Before starting background thread Show Progress Dialog
     */
    @Override
    protected void onPreExecute() {
        super.onPreExecute();
        pDialog1 = new ProgressDialog(Co_AddProject.this);
        pDialog1.setMessage("Inserting ProjectDetail..");
        pDialog1.setIndeterminate(false);
        pDialog1.setCancelable(true);
        pDialog1.show();
    }

    /**
     * Creating product
     */
    protected String doInBackground(String... args) {
        String userdetail=Co_LoginActivity.dbuname;
        List<NameValuePair> params = new ArrayList<NameValuePair>();
        params.add(new BasicNameValuePair("title", title1));
        params.add(new BasicNameValuePair("statusname1", statusname1));
        params.add(new BasicNameValuePair("begindate1", begindate1));
        params.add(new BasicNameValuePair("enddate1", enddate1));
        params.add(new BasicNameValuePair("taskname1", taskname1));
        params.add(new BasicNameValuePair("userdetail1",userdetail));


        // getting JSON Object
        // Note that create product url accepts POST method
        JSONObject json = jsonParser.makeHttpRequest(url_create_product1,
                "POST", params);
        Intent in = new Intent(Co_AddProject.this, Co_ProjectViewPage.class);
        startActivity(in);
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

                Intent i = new Intent(getApplicationContext(), Co_ProjectViewPage.class);
                startActivity(i);

                // closing this screen

            } else {
                // failed to create product
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
        pDialog1.dismiss();
    }

}
}


//        cal = (CalendarView) findViewById(R.id.calendarView1);
//        cal1 = (CalendarView) findViewById(R.id.calendarView2);

//        addproj = (Button) findViewById(R.id.BTN_addtask);
//        begindate = (Button) findViewById(R.id.Btn_begin);

//        cancel = (Button) findViewById(R.id.BT_cancel);
//        done = (Button) findViewById(R.id.BT_done);
//        addmore = (Button) findViewById(R.id.BT_addmoretask);
//

//        tblrw1 = (TableRow) findViewById(R.id.tablerow1);
//
//
//


//
//        begindate.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialog(DATE_DIALOG_ID);
//            }
//        });
///** Get the current date */
//        final Calendar cal = Calendar.getInstance();
//        pYear = cal.get(Calendar.YEAR);
//        pMonth = cal.get(Calendar.MONTH);
//        pDay = cal.get(Calendar.DAY_OF_MONTH);
//
//        /** Display the current date in the TextView */
//        updateDisplay();
//
//
////        //getting current date and time using Date class
////        DateFormat df = new SimpleDateFormat("dd : MM : yyyy");
////        Date dateobj = new Date();
////        System.out.println(df.format(dateobj));
////
////       /*getting current date time using calendar class
////        * An Alternative of above*/
////        Calendar calobj = Calendar.getInstance();
////        begindate.setText(df.format(calobj.getTime()));
//////        enddate.setText(df.format(calobj.getTime()));
////
////        cal.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
////
////            @Override
////            public void onSelectedDayChange(CalendarView view, int year,
////                                            int month, int dayOfMonth) {
////                // TODO Auto-generated method stub
////
////                selectbegindate = dayOfMonth + " : " + month + " : " + year;
////                Toast msg = Toast.makeText(getBaseContext(),
////                        "Begin Date is\n\n" + dayOfMonth + " : " + month
////                                + " : " + year, Toast.LENGTH_LONG);
////                msg.setGravity(Gravity.CENTER, 0, 0);
////                msg.show();
////                begindate.setText(selectbegindate);
////                tblrw1.setVisibility(View.GONE);
////                cal.setVisibility(View.GONE);
////                cal1.setVisibility(View.GONE);
////
////            }
////        });
////        cal1.setOnDateChangeListener(new CalendarView.OnDateChangeListener() {
////
////            @Override
////            public void onSelectedDayChange(CalendarView view, int year,
////                                            int month, int dayOfMonth) {
////                // TODO Auto-generated method stub
////
////
////                select_enddate = dayOfMonth + " : " + month + " : " + year;
////                tblrw1.setVisibility(View.GONE);
////                cal.setVisibility(View.GONE);
////                cal1.setVisibility(View.GONE);
//
////                Toast msg = Toast.makeText(getBaseContext(),
////                        "End Date is\n\n" + dayOfMonth + " : " + month
////                                + " : " + year, Toast.LENGTH_LONG);
////                msg.setGravity(Gravity.CENTER, 0, 0);
////                msg.show();
////
////            }
////        });
//

//
//    }
//    /** Updates the date in the TextView */
//    private void updateDisplay() {
//        begindate.setText(
//                new StringBuilder()
//                        // Month is 0 based so add 1
//                        .append(pMonth + 1).append("/")
//                        .append(pDay).append("/")
//                        .append(pYear).append(" "));
//    }
//
//    /** Displays a notification when the date is updated */
//    private void displayToast() {
//        Toast.makeText(this, new StringBuilder().append("Date choosen is ").append(begindate.getText().toString()),  Toast.LENGTH_SHORT).show();
//
//    }
//    /** Create a new dialog for date picker */
//    @Override
//    protected Dialog onCreateDialog(int id) {
//        switch (id) {
//            case DATE_DIALOG_ID:
//                return new DatePickerDialog(this,
//                        pDateSetListener,
//                        pYear, pMonth, pDay);
//        }
//        return null;
//    }

//

//

//

//
//    public void begindate(View v) {
//
//        cal.setEnabled(true);
//        tblrw1.setVisibility(View.VISIBLE);
//        cal.setVisibility(View.VISIBLE);
//        cal1.setEnabled(false);
//        cal1.setVisibility(View.GONE);
//
//    }
//
//    public void enddate(View v) {
//
//
//            cal.setEnabled(false);
//            tblrw1.setVisibility(View.VISIBLE);
//            cal.setVisibility(View.GONE);
//            cal1.setEnabled(true);
//            cal1.setVisibility(View.VISIBLE);
//
//
//
//    }
//

//

//

//
//    
//
//
//}
