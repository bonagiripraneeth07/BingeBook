package com.example.bingebook;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.androidnetworking.AndroidNetworking;
import com.androidnetworking.common.Priority;
import com.androidnetworking.error.ANError;
import com.androidnetworking.interfaces.JSONObjectRequestListener;
import com.androidnetworking.interfaces.StringRequestListener;

import org.json.JSONException;
import org.json.JSONObject;

public class Login extends AppCompatActivity {
    int UserId=0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        Button signinbutton,signupbutton;
        signinbutton= findViewById(R.id.loginButton);
        signupbutton = findViewById(R.id.signupbutton);
        EditText usernameEditText,passwordEditText;
        usernameEditText =findViewById(R.id.usernameEditText);
        passwordEditText=findViewById(R.id.passwordEditText);

        signinbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = usernameEditText.getText().toString();

                String password = passwordEditText.getText().toString();
                System.out.println(userName + " " + password );

                //api backend fetch
// Posting data
        JSONObject jsonObject = new JSONObject();
        try {
           // jsonObject.put("userid", 2);
            jsonObject.put("username", userName);
            jsonObject.put("password", password);
           // jsonObject.put("genre", "fiction");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://spring-boot-render-zs6y.onrender.com/login")
                .addJSONObjectBody(jsonObject) // Attach the JSON payload
                .setTag("POST_REQUEST")
                .setPriority(Priority.MEDIUM)
                .build()
//                .getAsJSONObject(new JSONObjectRequestListener() {
//                    @Override
//                    public void onResponse(JSONObject response ) {
//                        // Handle successful response
//                        Log.d("API_RESPONSE", "Response: "  );
//                    }
//
//                    @Override
//                    public void onError(ANError anError) {
//                        // Handle error
//                        Log.e("API_ERROR", "Error: " + anError.getMessage());
//                    }
//                });
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // Handle primitive response (e.g., "2")
                        Log.d("API_RESPONSE", "Response: " + response);
                        try {

                            if ( Integer.parseInt(response)!=404){
                                  UserId = Integer.parseInt(response);
                                Toast.makeText(Login.this, "User found" + UserId, Toast.LENGTH_SHORT).show();
                                reDirect(UserId,userName);
                            }else{
                                Toast.makeText(Login.this, "User not foud", Toast.LENGTH_SHORT).show();
                            }
                            // If you expect an integer
                            Log.d("API_RESPONSE", "Parsed result: " + UserId);
                        } catch (NumberFormatException e) {
                            Log.e("API_ERROR", "Error parsing response to integer");
                        }
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("API_ERROR", "Error: " + anError.getMessage());

                    }
                });





            }
        });

        signupbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                  Intent  i  = new Intent(Login.this, Register.class);


                startActivity(i);
            }
        });

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
    public void reDirect( int id,String username){
        Intent i ;
        if (id!=0){
            SharedPreferences sharedPreferences = getSharedPreferences("UserCredentials", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("username",username);
            editor.putInt("UserId",id);
            editor.apply();
            i = new Intent(Login.this,Home.class);
            System.out.println(UserId);
            startActivity(i);
        }else {
            Toast.makeText(Login.this, "User not found", Toast.LENGTH_SHORT).show();
        }

    }
}
