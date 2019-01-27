package com.example.tanvigupta.userlogindemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextClock;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class SignupActivity extends AppCompatActivity {

    private EditText name,email_id,passwordcheck;
    private FirebaseAuth auth;
    private static final String TAG="";
    private ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        TextView signinpage=findViewById(R.id.sign_in_page);
        signinpage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(SignupActivity.this,LoginActivity.class);
                startActivity(intent);

            }
        });

        auth=FirebaseAuth.getInstance();
        email_id=findViewById(R.id.input_email);
        passwordcheck=findViewById(R.id.input_password);
        progressBar=findViewById(R.id.progressbar);
        Button signup=findViewById(R.id.button_signup);

        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email=email_id.getText().toString();
                String password=passwordcheck.getText().toString();

                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Enter email",Toast.LENGTH_SHORT).show();
                    return;
                }

                if(TextUtils.isEmpty(password)){
                    Toast.makeText(getApplicationContext(),"Enter password",Toast.LENGTH_SHORT).show();
                    return;


                }
                progressBar.setVisibility(View.VISIBLE);

                auth.createUserWithEmailAndPassword(email,password).addOnCompleteListener(SignupActivity.this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        progressBar.setVisibility(View.GONE);

                        if(task.isSuccessful()){
                            Log.d(TAG,"createuserwithemailpasswordsuccessful");
                            FirebaseUser user=auth.getCurrentUser();
                            Intent intent=new Intent(SignupActivity.this,MainActivity.class);
                            startActivity(intent);
                            finish();


                        }
                        else{
                            Log.w(TAG, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignupActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();

                        }
                    }
                });
            }
        });

    }
}
