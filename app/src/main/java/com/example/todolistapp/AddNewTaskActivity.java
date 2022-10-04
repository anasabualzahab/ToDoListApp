package com.example.todolistapp;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.RetryPolicy;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class AddNewTaskActivity extends AppCompatActivity {


    EditText    agentName , addNewTitle, addNewDescription ;
    Button sendDataButton, dateButton;

    private DatePickerDialog datePickerDialog;

    String [] types = {"دعم فني" , "تطوير"};
    String taskType;

    AutoCompleteTextView autoCompleteTextView;

    ArrayAdapter<String> adapterTypes;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_task);

        setTitle("Add New Task");
        initDatePicker();

//=========================================================================================

        agentName = findViewById(R.id.editTextEnterAgnetName);
        addNewTitle = findViewById(R.id.editTextTitle);
        addNewDescription = findViewById(R.id.editTextDescription);

        sendDataButton = findViewById(R.id.buttonSendData);
        dateButton = findViewById(R.id.datePickerButton);

        autoCompleteTextView = findViewById(R.id.auto_complete_txt);
        adapterTypes = new ArrayAdapter<String>(this , R.layout.menu_item , types);
        autoCompleteTextView.setAdapter(adapterTypes);
//=========================================================================================

        autoCompleteTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                String type = adapterView.getItemAtPosition(i).toString();
                taskType= type;
                Toast.makeText(getApplicationContext(), "Item " + type,Toast.LENGTH_LONG).show();
            }
        });

        dateButton.setText(getToDayDate());

        sendDataButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                addStudentData();

            }
        });

    }

    private String getToDayDate() {

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month+1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day , month, year);
    }

    private void initDatePicker(){

        DatePickerDialog.OnDateSetListener dateSetListener  = new DatePickerDialog.OnDateSetListener(){

            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {

                month = month+1;
                String date= makeDateString (day, month , year);

                System.out.println("this is the date " + date);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT ;
        datePickerDialog = new DatePickerDialog(this , style , dateSetListener  , year, month , day);


    }

    private String makeDateString(int day, int month, int year) {
        return month + " " + day + " " + year ;
    }

    public void openDatePicker(View view) {

        datePickerDialog.show();

    }
    public void addStudentData(){
        String newAgentName = agentName.getText().toString();
        String newtitle = addNewTitle.getText().toString();
        String newDescription = addNewDescription.getText().toString();

        StringRequest stringRequest = new StringRequest(Request.Method.POST,
                "https://script.google.com/macros/s/AKfycbxfrVqKNhE8R4rHJdKxqLTeFfri7OCKtRdpu3FfX0iV2imsH2lP8KFc-OzZ9S27B09w/exec"
                , new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                Toast.makeText(AddNewTaskActivity.this, "You did it", Toast.LENGTH_LONG).show();
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Toast.makeText(AddNewTaskActivity.this, "nooooo", Toast.LENGTH_LONG).show();
            }
        }){

            @Override
            protected Map <String  ,String > getParams() {

                Map <String , String > params = new HashMap<>();

                params.put("action" , "addStudent");
                params.put("vId" , "");
                params.put("vAddedDate" , "");
                params.put("vAgent_Id" , "");
                params.put("vAgent" , newAgentName);
                params.put("vTitle" , newtitle);
                params.put("vDescription" , newDescription);
                params.put("vEndDate" , "0");
                params.put("vType" , "0");
                params.put("vNeedTwoDepartments" , "0");

                return params ;

            }

        };

        int socketTimeOut = 5000 ;

        RetryPolicy retryPolicy = new DefaultRetryPolicy(socketTimeOut ,
                0 , DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);

        stringRequest.setRetryPolicy(retryPolicy);

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);

    }

}