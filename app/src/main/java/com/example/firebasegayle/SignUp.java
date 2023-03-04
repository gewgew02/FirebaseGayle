package com.example.firebasegayle;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {
    private Button buttonSignUp;
    final String TAG = "FIRESTORE";
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        EditText firstname = findViewById(R.id.first_name);
        EditText lastname = findViewById(R.id.last_name);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        buttonSignUp = findViewById(R.id.buttonSignUp);
        buttonSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String firstnameInput = firstname.getText().toString();
                String lastnameInput = lastname.getText().toString();
                String emailInput = email.getText().toString();
                String passInput = password.getText().toString();

                if(!firstnameInput.isEmpty() && !lastnameInput.isEmpty() && !emailInput.isEmpty() && !passInput.isEmpty()){
                    addUser(firstnameInput, lastnameInput, emailInput, passInput);
                    Finish();
                } else{
                    Toast.makeText(SignUp.this, "All fields required!"
                            , Toast.LENGTH_SHORT).show();
                }
            }
            public void addUser(String firstnameInput, String lastnameInput, String email, String password){
                // Create a new user with a first and last name
                Map<String, Object> user = new HashMap<>();
                user.put("firstname", firstnameInput);
                user.put("lastname", lastnameInput);
                user.put("email", email);
                user.put("password", password);

                // Add a new document with a generated ID
                db.collection("users").document(email)
                        .set(user)
                        .addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Log.d(TAG, "DocumentSnapshot added with ID: " + email);
                                Toast.makeText(SignUp.this, "Successfully Added"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        })
                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(SignUp.this, "Error adding document" + e
                                        , Toast.LENGTH_SHORT).show();
                                Log.w(TAG, "Error adding document", e);
                            }
                        });
            }

            public void Finish(){
                Intent intent = new Intent(getApplication(), Login.class);
                startActivity(intent);
                Toast.makeText(SignUp.this, "Successfully Added"
                        , Toast.LENGTH_SHORT).show();
            }
        });

    }
}