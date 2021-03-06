package com.example.lasyaboddapati.moneymatters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Map;


public class Register extends Activity {
    String sname;
    String susername;
    //String spassword;
    //String cspassowrd;
    String semail;
    ArrayList<String> userlist;

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        final EditText name=(EditText)findViewById(R.id.name);
        final EditText username=(EditText)findViewById(R.id.username);
        //final EditText password=(EditText)findViewById(R.id.password);
        //final EditText cpassword=(EditText)findViewById(R.id.cpassword);
        final EditText email=(EditText)findViewById(R.id.email);

        Firebase.setAndroidContext(getApplicationContext());
        final Firebase userscloud=new Firebase("https://crackling-inferno-5209.firebaseio.com/");

        //For getting UserList


        userscloud.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot snapshot) {
                Map<String, Object> usersmap = (Map<String, Object>) snapshot.getValue();
                userlist = new ArrayList<String>();
                for (String key : usersmap.keySet()) {

                    userlist.add(key);
                }
                //userlist.remove(user);
                for (String str : userlist) {
                    Log.d("user", str);
                }


            }
            @Override
            public void onCancelled(FirebaseError firebaseError) {
                System.out.println("The read failed: " + firebaseError.getMessage());
            }
        });

        Button reg=(Button)findViewById(R.id.regis);
        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                sname=name.getText().toString();
                susername=username.getText().toString();
                //spassword=password.getText().toString();
                //cspassowrd=cpassword.getText().toString();
                semail=email.getText().toString();

                if(sname.isEmpty() || susername.isEmpty() /*|| spassword.isEmpty() || cspassowrd.isEmpty() */|| semail.isEmpty() )
                {
                    Toast.makeText(getApplicationContext(),
                            "Please enter all the values", Toast.LENGTH_SHORT).show();
                }

                /*else if(!spassword.equals(cspassowrd))
                {
                    Toast.makeText(getApplicationContext(),
                            "Both Passwords don't match", Toast.LENGTH_SHORT).show();
                }*/
                else if(userlist.contains(susername))
                {
                    Toast.makeText(getApplicationContext(),
                            "User already exists! Enter different username", Toast.LENGTH_SHORT).show();
                }
                else {

                    Firebase.setAndroidContext(getApplicationContext());
                    final Firebase myFirebaseRef = new Firebase("https://crackling-inferno-5209.firebaseio.com/");
                    myFirebaseRef.child(susername).setValue(true);
                    myFirebaseRef.child(susername).child("Credit").setValue("true");

                    myFirebaseRef.child(susername).child("Debts").setValue("true");
                    myFirebaseRef.child(susername).child("Notifications").setValue("true");
                    myFirebaseRef.child(susername).child("SentNotifications").setValue("true");

                    myFirebaseRef.child(susername).child("Friends").setValue("true");
                    myFirebaseRef.child(susername).child("PersonalInfo").setValue("true");
                    myFirebaseRef.child(susername).child("PersonalInfo").child("Name").setValue(sname);
                    //myFirebaseRef.child(susername).child("PersonalInfo").child("Password").setValue(spassword);
                    myFirebaseRef.child(susername).child("PersonalInfo").child("Email").setValue(semail);
                    Toast.makeText(getApplicationContext(),
                            "Successfully Registered!", Toast.LENGTH_SHORT).show();


                    SharedPreferences sharedPref = getSharedPreferences("Credentials", Context.MODE_PRIVATE);
                    SharedPreferences.Editor editor = sharedPref.edit();
                    editor.putString("Username", susername);
                    editor.commit();
                    Intent homeIntent = new Intent(Register.this, Home.class);
                    startActivity(homeIntent);
                    finish();
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if(id == R.id.action_uninstall) {
            Intent uninstall = new Intent(Intent.ACTION_DELETE);
            uninstall.setData(Uri.parse("package:" + this.getPackageName()));
            startActivity(uninstall);
        } else if (id == R.id.action_help) {
            Intent help = new Intent(this, Help.class);
            startActivity(help);
        }
        return super.onOptionsItemSelected(item);
    }
}
