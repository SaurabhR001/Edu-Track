package com.example.edutrack;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

import java.util.HashMap;
import java.util.Map;

public class StudProfileSetup extends AppCompatActivity {

    EditText firstName, lastName, fatherName, motherName, dob, address, admissionNo, CCcode, TGcode;
    Button subBtn;
    TextView disName;
    boolean valid = true;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stud_profile_setup);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        userId = fAuth.getCurrentUser().getUid();

        disName = findViewById(R.id.usrname);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        fatherName = findViewById(R.id.father_name);
        motherName = findViewById(R.id.mother_name);
        dob = findViewById(R.id.dob);
        address = findViewById(R.id.address);
        admissionNo = findViewById(R.id.admission_no);
        CCcode = findViewById(R.id.cc_code);
        TGcode = findViewById(R.id.tg_code);

        subBtn = findViewById(R.id.btn_sub);


        DocumentReference documentReference = fStore.collection("Users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                disName.setText(documentSnapshot.getString("FullName"));
            }
        });

        // put the checkbox code here

        subBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkField(firstName);
                checkField(lastName);
                checkField(fatherName);
                checkField(motherName);
                checkField(dob);
                checkField(address);
                checkField(admissionNo);
                checkField(CCcode);
                checkField(TGcode);

                if (valid) {
                    FirebaseUser user = fAuth.getCurrentUser();
                    Toast.makeText(StudProfileSetup.this, "Profile Created", Toast.LENGTH_SHORT).show();
                    DocumentReference df = fStore.collection("Users").document(user.getUid());
                    Map<String,Object> userInfo = new HashMap<>();
                    userInfo.put("First Name", firstName.getText().toString());
                    userInfo.put("Last Name", lastName.getText().toString());
                    userInfo.put("Father's Name", fatherName.getText().toString());
                    userInfo.put("Mother's Name", motherName.getText().toString());
                    userInfo.put("DOB", dob.getText().toString());
                    userInfo.put("Address", address.getText().toString());
                    userInfo.put("Admission Number", admissionNo.getText().toString());
                    userInfo.put("CC Code", CCcode.getText().toString());
                    userInfo.put("TG Code", TGcode.getText().toString());

                    df.update(userInfo);
                    startActivity(new Intent(getApplicationContext(), StudentDashboard.class));
                    finish();
                }
            }
        });


    }

    public boolean checkField(EditText textField){
        if(textField.getText().toString().isEmpty()){
            textField.setError("Error");
            valid = false;
        }else {
            valid = true;
        }

        return valid;
    }
}