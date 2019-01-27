package com.example.tanvigupta.userlogindemo;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.facebook.login.widget.LoginButton;
import com.google.firebase.auth.FirebaseAuth;
import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

public class MainActivity extends AppCompatActivity {
    private FirebaseAuth auth;
    private TextView LoggedInId;
    private CircleImageView LoggedInPhoto;
    private FirebaseAuth.AuthStateListener listener;


    private String emailId,photoUrl;

    @Override
    protected void onStart() {
        super.onStart();
        auth.addAuthStateListener(listener);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        LoggedInId=findViewById(R.id.LoggedInId);
        LoggedInPhoto=findViewById(R.id.LoggedInPhoto);

        Button button=findViewById(R.id.signout);
        auth=FirebaseAuth.getInstance();
        Intent intent=getIntent();
        emailId=intent.getStringExtra("emailId");
        photoUrl=intent.getStringExtra("profilePic");
        LoggedInId.setText(emailId);
        Picasso.get().load(photoUrl).into(LoggedInPhoto);
        listener=new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                if(firebaseAuth.getCurrentUser()==null){
                    startActivity(new Intent(MainActivity.this,LoginActivity.class));
                }
            }
        };

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                auth.signOut();
            }
        });
    }

}
