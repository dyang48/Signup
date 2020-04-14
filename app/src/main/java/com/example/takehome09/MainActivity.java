package com.example.takehome09;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseAuthException;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

    FirebaseDatabase database= FirebaseDatabase.getInstance();
    DatabaseReference userReference = database.getReference("message");


    EditText questionField;
    EditText answerField;
    private FirebaseAuth mAuth;
    private FirebaseAuth.AuthStateListener authStateListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        userReference=database.getReference("Messages");

        questionField = (EditText) findViewById(R.id.question_field);
        answerField = (EditText) findViewById(R.id.answer_field);
        mAuth = FirebaseAuth.getInstance();
        authStateListener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                FirebaseUser user = mAuth.getCurrentUser();
                if (user!= null){
                    userReference=database.getReference(user.getUid());
                }
                else {
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            }
        };


    }

    @Override
    protected void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(authStateListener);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mAuth.removeAuthStateListener(authStateListener);
    }

    public void sendToFirebase(View view) {
        Button button1 = (Button) findViewById(R.id.sendto);
        button1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                userReference = database.getReference("Messages");
                userReference.child(questionField.getText().toString()).setValue(answerField.getText().toString());

            }
        });


    }

    public void logOut(View view){
        mAuth.signOut();
    }




}
