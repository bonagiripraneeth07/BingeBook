package com.example.bingebook;

import android.content.Intent;
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

public class Register extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register);
        EditText usernameEditText, passwordEditText, confirmPasswordEditText;
        Button signupButton;
        signupButton = findViewById(R.id.signupButton);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        confirmPasswordEditText = findViewById(R.id.confirmPasswordEditText);

        signupButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String userName = usernameEditText.getText().toString();
                String password = passwordEditText.getText().toString();
                String confirmPassword = confirmPasswordEditText.getText().toString();

                if (!password.isEmpty() && !userName.isEmpty()) {
                    if (password.equals(confirmPassword)) {
                        System.out.println(" yes yooo yooo  password is confirmed");
                        registerUser(userName,password);
                    } else {
                        System.out.println("check Your password");
                    }

                } else {
                    System.out.println("no yo empty password ");
                }
            }
        });


    }

    public void registerUser(String username ,String password) {
        AndroidNetworking.initialize(getApplicationContext());

// Posting data
        JSONObject jsonObject = new JSONObject();
        try {
           // jsonObject.put("userid", 2);
            jsonObject.put("username",username);
            jsonObject.put("password", password);
            //jsonObject.put("genre", "fiction");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        AndroidNetworking.post("https://spring-boot-render-zs6y.onrender.com/register")
                .addJSONObjectBody(jsonObject)
                .setTag("POST_REQUEST")
                .setPriority(Priority.MEDIUM)
                .build()
                .getAsString(new StringRequestListener() {
                    @Override
                    public void onResponse(String response) {
                        // Handle the string response
                        Log.d("API_RESPONSE", "Response: " + response);
                        Toast.makeText(Register.this, "Success: " + response, Toast.LENGTH_SHORT).show();
                        Intent i = new Intent(Register.this, Login.class);
                        startActivity(i);
                    }

                    @Override
                    public void onError(ANError anError) {
                        Log.e("API_ERROR", "Error: " + anError.getMessage());
                        Toast.makeText(Register.this, "Something went wrong", Toast.LENGTH_SHORT).show();
                    }
                });

    }
}
