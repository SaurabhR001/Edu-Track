package com.example.edutrack;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class studEdit extends AppCompatActivity {

    EditText admission_no, dob, cc_code, tg_code;
    Button subBtn;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_edit);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

//        admission_no = findViewById(R.id.admission_no1);
//        dob = findViewById(R.id.date_of_birth);
//        cc_code = findViewById(R.id.cc_code);
//        tg_code = findViewById(R.id.tg_code);

        subBtn = findViewById(R.id.submit);

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                // can create checkField later


                if (valid) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    DocumentReference df = fStore.collection("Users").document(user.getUid());
                    Map<String,Object> userInfo = new HashMap<>();
                    userInfo.put("BoB",admission_no.getText().toString());
                    userInfo.put("AdmissionNumber",admission_no.getText().toString());
                    userInfo.put("DoB",dob.getText().toString());
                    userInfo.put("CC_Code",cc_code.getText().toString());
                    userInfo.put("TG_Code",tg_code.getText().toString());

                    df.update(userInfo);
                    startActivity(new Intent(getApplicationContext(),MainActivity.class));
                    finish();

                }
            }
        });
    }
}