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
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class Login extends AppCompatActivity {
    private Button buttonLogIn;
    final String TAG = "FIRESTORE";
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        EditText email = findViewById(R.id.email);
        EditText password = findViewById(R.id.password);

        buttonLogIn = findViewById(R.id.buttonLogIn);
        buttonLogIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String emailInput = email.getText().toString();
                String passInput = password.getText().toString();

                db.collection("users")
                        .whereEqualTo("email", emailInput)
                        //.whereEqualTo("email", emailLogin)
                        .get().addOnSuccessListener(queryDocumentSnapshots -> {
                            if (!queryDocumentSnapshots.isEmpty()) {
                                DocumentSnapshot documentSnapshot = queryDocumentSnapshots.getDocuments().get(0);
                                String userPassword = documentSnapshot.getString("password");

                                // Check if the password entered by the user matches the password in Firestore
                                if (Objects.equals(userPassword, String.valueOf(passInput))) {
                                    // User logged in successfully
                                    Intent intent = new Intent(getApplication(), Profile.class);
                                    startActivity(intent);
                                    Toast.makeText(Login.this, "Welcome!"
                                            , Toast.LENGTH_SHORT).show();
                                } else {
                                    // Incorrect password
                                    Toast.makeText(Login.this, "Invalid Password"
                                            , Toast.LENGTH_SHORT).show();
                                }
                            } else {
                                // User not found in Firestore
                                Toast.makeText(Login.this, "Check Inputs!"
                                        , Toast.LENGTH_SHORT).show();
                            }
                        })

                        .addOnFailureListener(new OnFailureListener() {
                            @Override
                            public void onFailure(@NonNull Exception e) {
                                Toast.makeText(Login.this, "Error" + e
                                        , Toast.LENGTH_SHORT).show();
                                Log.w("FIRESTORE", "Error finding user", e);
                            }
                        });
            }
        });
    }
}