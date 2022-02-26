package com.example.registor.activity;

import android.app.Activity;
import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.inputmethod.InputMethodManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.registor.R;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import static android.content.ContentValues.TAG;

public class MainActivity extends AppCompatActivity {

    ImageView backBtn, profileImage;
    AutoCompleteTextView birthDate, Userage, groupName, skill, formFilledDate;

    TextInputEditText groupNo, Cast, Name, FatherName, PhoneNumber, WhatsAppNumber, ReferancePersonName, ReferancePersonPhoneNumber, School, City, Address, Area, Education,
    village, email, fatherAge, fatherPhone, fatherWork, fatherEmail, yuvaSabha;

    MaterialButton registrationBtn;

    int mYEAR, mMONTH, mDATE, mYEAR1, mMONTH1, mDATE1;

    String UserId, groupno, groupname, profileUrl;

    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

    String[] groupNameList = new String[]{"1", "2", "3", "4", "5", "6", "7", "8", "9", "10"};
    String[] skillList = new String[]{"વક્રુત્વ", "નુત્ય", "ગાયન", "સંગીત વાદ્ય", "લેખન", "અબીનઈ", "કમ્પ્યુટર", "ચિત્ર", "કરાટે", "વર્તકથન", "અન્ય"};

    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        backBtn = findViewById(R.id.registrationBack);
        birthDate = findViewById(R.id.birthDate);

        registrationBtn = findViewById(R.id.registerbtn);

        Userage = findViewById(R.id.age);

        groupName = findViewById(R.id.groupName);
        groupNo = findViewById(R.id.groupNo);
        Name = findViewById(R.id.name);
        FatherName = findViewById(R.id.fatherName);
        PhoneNumber = findViewById(R.id.phonenumber);
        WhatsAppNumber = findViewById(R.id.whatsappnumber);
        Cast = findViewById(R.id.cast);
        ReferancePersonName = findViewById(R.id.reference);
        ReferancePersonPhoneNumber = findViewById(R.id.referencepersonnumber);
        School = findViewById(R.id.school);
        City = findViewById(R.id.city);
        Address = findViewById(R.id.address);
        Area = findViewById(R.id.area);
        Education = findViewById(R.id.std);
        skill = findViewById(R.id.skill);
        village = findViewById(R.id.village);
        email = findViewById(R.id.emailAddress);
        formFilledDate = findViewById(R.id.dateofsubmitted);
        fatherAge = findViewById(R.id.fatherAge);
        fatherPhone = findViewById(R.id.fatherNumber);
        fatherWork = findViewById(R.id.fatherbusiness);
        fatherEmail = findViewById(R.id.fatheremail);
        yuvaSabha = findViewById(R.id.fatheryuvasabha);
        profileImage = findViewById(R.id.Registration_profile_Image);

        UserId = firebaseDatabase.getReference().getKey();

        progressDialog = new ProgressDialog(this);
        progressDialog.setMessage("Please Wait....");

        profileImage.setOnClickListener(v -> {

            Intent intent = new Intent(Intent.ACTION_GET_CONTENT);
            intent.setType("image/*");
            startActivityForResult(intent, 1);

        });

        backBtn.setOnClickListener(v -> {

            startActivity(new Intent(MainActivity.this, MainScreen.class));
            finish();

        });

        birthDate.setOnClickListener(v -> {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

            Calendar calendar = Calendar.getInstance();

            mYEAR = calendar.get(Calendar.YEAR);
            mMONTH = calendar.get(Calendar.MONTH);
            mDATE = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {

                birthDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                int age = mYEAR - year;

                Log.e("TAG", "onDateSet: " + age);

                if (age <= 0) {

                    birthDate.setError("You can't choose current year or future year ");

                } else if (age > 0) {

                    birthDate.setError(null);
                    Userage.setText(new StringBuilder().append(age));

                }

            }, mYEAR, mMONTH, mDATE);

            datePickerDialog.show();

        });

        formFilledDate.setOnClickListener(v -> {

            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(v.getApplicationWindowToken(), 0);

            Calendar calendar = Calendar.getInstance();

            mYEAR1 = calendar.get(Calendar.YEAR);
            mMONTH1 = calendar.get(Calendar.MONTH);
            mDATE1 = calendar.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> {

                formFilledDate.setText(dayOfMonth + "/" + (month + 1) + "/" + year);

                int age = mYEAR1 - year;

                Log.e("TAG", "onDateSet: " + age);

                if (age <= 0) {

                    formFilledDate.setError("You can't choose current year or future year ");

                } else if (age > 0) {

                    formFilledDate.setError(null);

                }

            }, mYEAR1, mMONTH1, mDATE1);

            datePickerDialog.show();

        });

        registrationBtn.setOnClickListener(v -> {

            groupname = groupName.getText().toString();


            if (groupname.equals("1") && profileUrl != null) {

                progressDialog.show();

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileImage/1/" + Name.getText().toString());

                storageReference.putFile(Uri.parse(profileUrl)).addOnSuccessListener(taskSnapshot -> {

                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

                        Map<String, Object> user = new HashMap<>();
                        user.put("groupNo", groupNo.getText().toString());
                        user.put("name", Name.getText().toString());
                        user.put("fatherName", FatherName.getText().toString());
                        user.put("phoneNumber", PhoneNumber.getText().toString());
                        user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                        user.put("birthDate", birthDate.getText().toString());
                        user.put("age", Userage.getText().toString());
                        user.put("cast", Cast.getText().toString());
                        user.put("referancePersonName", ReferancePersonName.getText().toString());
                        user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                        user.put("school", School.getText().toString());
                        user.put("city", City.getText().toString());
                        user.put("address", Address.getText().toString());
                        user.put("area", Area.getText().toString());
                        user.put("education", Education.getText().toString());
                        user.put("profileImage", uri.toString());
                        user.put("skill", skill.getText().toString());
                        user.put("village", village.getText().toString());
                        user.put("email", email.getText().toString());
                        user.put("formfilledDate", formFilledDate.getText().toString());
                        user.put("fatherAge", fatherAge.getText().toString());
                        user.put("fatherNumber", fatherPhone.getText().toString());
                        user.put("fatherWork", fatherWork.getText().toString());
                        user.put("fatherEmail", fatherEmail.getText().toString());
                        user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("1");
                        databaseReference.push().setValue(user);

                        groupName.setText("");
                        groupNo.setText("");
                        Name.setText("");
                        FatherName.setText("");
                        PhoneNumber.setText("");
                        WhatsAppNumber.setText("");
                        birthDate.setText("");
                        Userage.setText("");
                        Cast.setText("");
                        ReferancePersonName.setText("");
                        ReferancePersonPhoneNumber.setText("");
                        School.setText("");
                        City.setText("");
                        Address.setText("");
                        Area.setText("");
                        Education.setText("");
                        skill.setText("");
                        village.setText("");
                        email.setText("");
                        formFilledDate.setText("");
                        fatherAge.setText("");
                        fatherPhone.setText("");
                        fatherWork.setText("");
                        fatherEmail.setText("");
                        yuvaSabha.setText("");
                        profileImage.setImageDrawable(null);

                        Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }).addOnFailureListener(e -> {

                        progressDialog.dismiss();

                        Toast.makeText(this, "Failed to upload data", Toast.LENGTH_SHORT).show();

                    });

                });

            }
            if (groupname.equals("1") && profileUrl == null) {

                Map<String, Object> user = new HashMap<>();
                user.put("groupNo", groupNo.getText().toString());
                user.put("name", Name.getText().toString());
                user.put("fatherName", FatherName.getText().toString());
                user.put("phoneNumber", PhoneNumber.getText().toString());
                user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                user.put("birthDate", birthDate.getText().toString());
                user.put("age", Userage.getText().toString());
                user.put("cast", Cast.getText().toString());
                user.put("referancePersonName", ReferancePersonName.getText().toString());
                user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                user.put("school", School.getText().toString());
                user.put("city", City.getText().toString());
                user.put("address", Address.getText().toString());
                user.put("area", Area.getText().toString());
                user.put("education", Education.getText().toString());
                user.put("skill", skill.getText().toString());
                user.put("village", village.getText().toString());
                user.put("email", email.getText().toString());
                user.put("formfilledDate", formFilledDate.getText().toString());
                user.put("fatherAge", fatherAge.getText().toString());
                user.put("fatherNumber", fatherPhone.getText().toString());
                user.put("fatherWork", fatherWork.getText().toString());
                user.put("fatherEmail", fatherEmail.getText().toString());
                user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("1");
                databaseReference.push().setValue(user);

                groupName.setText("");
                groupNo.setText("");
                Name.setText("");
                FatherName.setText("");
                PhoneNumber.setText("");
                WhatsAppNumber.setText("");
                birthDate.setText("");
                Userage.setText("");
                Cast.setText("");
                ReferancePersonName.setText("");
                ReferancePersonPhoneNumber.setText("");
                School.setText("");
                City.setText("");
                Address.setText("");
                Area.setText("");
                Education.setText("");
                skill.setText("");
                village.setText("");
                email.setText("");
                formFilledDate.setText("");
                fatherAge.setText("");
                fatherPhone.setText("");
                fatherWork.setText("");
                fatherEmail.setText("");
                yuvaSabha.setText("");


            }

            if (groupname.equals("2") && profileUrl != null) {

                progressDialog.show();

                Log.e(TAG, "onCreate2: ");

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileImage/2/" + Name.getText().toString());

                storageReference.putFile(Uri.parse(profileUrl)).addOnSuccessListener(taskSnapshot -> {

                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

                        Map<String, Object> user = new HashMap<>();
                        user.put("groupNo", groupNo.getText().toString());
                        user.put("name", Name.getText().toString());
                        user.put("fatherName", FatherName.getText().toString());
                        user.put("phoneNumber", PhoneNumber.getText().toString());
                        user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                        user.put("birthDate", birthDate.getText().toString());
                        user.put("age", Userage.getText().toString());
                        user.put("cast", Cast.getText().toString());
                        user.put("referancePersonName", ReferancePersonName.getText().toString());
                        user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                        user.put("school", School.getText().toString());
                        user.put("city", City.getText().toString());
                        user.put("address", Address.getText().toString());
                        user.put("area", Area.getText().toString());
                        user.put("education", Education.getText().toString());
                        user.put("profileImage", uri.toString());
                        user.put("skill", skill.getText().toString());
                        user.put("village", village.getText().toString());
                        user.put("email", email.getText().toString());
                        user.put("formfilledDate", formFilledDate.getText().toString());
                        user.put("fatherAge", fatherAge.getText().toString());
                        user.put("fatherNumber", fatherPhone.getText().toString());
                        user.put("fatherWork", fatherWork.getText().toString());
                        user.put("fatherEmail", fatherEmail.getText().toString());
                        user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("2");
                        databaseReference.push().setValue(user);

                        groupName.setText("");
                        groupNo.setText("");
                        Name.setText("");
                        FatherName.setText("");
                        PhoneNumber.setText("");
                        WhatsAppNumber.setText("");
                        birthDate.setText("");
                        Userage.setText("");
                        Cast.setText("");
                        ReferancePersonName.setText("");
                        ReferancePersonPhoneNumber.setText("");
                        School.setText("");
                        City.setText("");
                        Address.setText("");
                        Area.setText("");
                        Education.setText("");
                        skill.setText("");
                        village.setText("");
                        email.setText("");
                        formFilledDate.setText("");
                        fatherAge.setText("");
                        fatherPhone.setText("");
                        fatherWork.setText("");
                        fatherEmail.setText("");
                        yuvaSabha.setText("");
                        profileImage.setImageDrawable(null);

                        Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }).addOnFailureListener(e -> {

                        progressDialog.dismiss();

                        Toast.makeText(this, "Failed to upload data", Toast.LENGTH_SHORT).show();

                    });

                });

            } else if (groupname.equals("2") && profileUrl == null) {

                Map<String, Object> user = new HashMap<>();
                user.put("groupNo", groupNo.getText().toString());
                user.put("name", Name.getText().toString());
                user.put("fatherName", FatherName.getText().toString());
                user.put("phoneNumber", PhoneNumber.getText().toString());
                user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                user.put("birthDate", birthDate.getText().toString());
                user.put("age", Userage.getText().toString());
                user.put("cast", Cast.getText().toString());
                user.put("referancePersonName", ReferancePersonName.getText().toString());
                user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                user.put("school", School.getText().toString());
                user.put("city", City.getText().toString());
                user.put("address", Address.getText().toString());
                user.put("area", Area.getText().toString());
                user.put("education", Education.getText().toString());
                user.put("skill", skill.getText().toString());
                user.put("village", village.getText().toString());
                user.put("email", email.getText().toString());
                user.put("formfilledDate", formFilledDate.getText().toString());
                user.put("fatherAge", fatherAge.getText().toString());
                user.put("fatherNumber", fatherPhone.getText().toString());
                user.put("fatherWork", fatherWork.getText().toString());
                user.put("fatherEmail", fatherEmail.getText().toString());
                user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("2");
                databaseReference.push().setValue(user);

                groupName.setText("");
                groupNo.setText("");
                Name.setText("");
                FatherName.setText("");
                PhoneNumber.setText("");
                WhatsAppNumber.setText("");
                birthDate.setText("");
                Userage.setText("");
                Cast.setText("");
                ReferancePersonName.setText("");
                ReferancePersonPhoneNumber.setText("");
                School.setText("");
                City.setText("");
                Address.setText("");
                Area.setText("");
                Education.setText("");
                skill.setText("");
                village.setText("");
                email.setText("");
                formFilledDate.setText("");
                fatherAge.setText("");
                fatherPhone.setText("");
                fatherWork.setText("");
                fatherEmail.setText("");
                yuvaSabha.setText("");
                profileImage.setImageDrawable(null);

            }

            if (groupname.equals("3") && profileUrl != null) {

                progressDialog.show();

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileImage/3/" + Name.getText().toString());

                storageReference.putFile(Uri.parse(profileUrl)).addOnSuccessListener(taskSnapshot -> {

                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

                        Map<String, Object> user = new HashMap<>();
                        user.put("groupNo", groupNo.getText().toString());
                        user.put("name", Name.getText().toString());
                        user.put("fatherName", FatherName.getText().toString());
                        user.put("phoneNumber", PhoneNumber.getText().toString());
                        user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                        user.put("birthDate", birthDate.getText().toString());
                        user.put("age", Userage.getText().toString());
                        user.put("cast", Cast.getText().toString());
                        user.put("referancePersonName", ReferancePersonName.getText().toString());
                        user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                        user.put("school", School.getText().toString());
                        user.put("city", City.getText().toString());
                        user.put("address", Address.getText().toString());
                        user.put("area", Area.getText().toString());
                        user.put("education", Education.getText().toString());
                        user.put("profileImage", uri.toString());
                        user.put("skill", skill.getText().toString());
                        user.put("village", village.getText().toString());
                        user.put("email", email.getText().toString());
                        user.put("formfilledDate", formFilledDate.getText().toString());
                        user.put("fatherAge", fatherAge.getText().toString());
                        user.put("fatherNumber", fatherPhone.getText().toString());
                        user.put("fatherWork", fatherWork.getText().toString());
                        user.put("fatherEmail", fatherEmail.getText().toString());
                        user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("3");
                        databaseReference.push().setValue(user);

                        groupName.setText("");
                        groupNo.setText("");
                        Name.setText("");
                        FatherName.setText("");
                        PhoneNumber.setText("");
                        WhatsAppNumber.setText("");
                        birthDate.setText("");
                        Userage.setText("");
                        Cast.setText("");
                        ReferancePersonName.setText("");
                        ReferancePersonPhoneNumber.setText("");
                        School.setText("");
                        City.setText("");
                        Address.setText("");
                        Area.setText("");
                        Education.setText("");
                        skill.setText("");
                        village.setText("");
                        email.setText("");
                        formFilledDate.setText("");
                        fatherAge.setText("");
                        fatherPhone.setText("");
                        fatherWork.setText("");
                        fatherEmail.setText("");
                        yuvaSabha.setText("");
                        profileImage.setImageDrawable(null);

                        Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }).addOnFailureListener(e -> {

                        progressDialog.dismiss();

                        Toast.makeText(this, "Failed to upload data", Toast.LENGTH_SHORT).show();

                    });

                });

            } else if (groupname.equals("3") && profileUrl == null) {


                Map<String, Object> user = new HashMap<>();
                user.put("groupNo", groupNo.getText().toString());
                user.put("name", Name.getText().toString());
                user.put("fatherName", FatherName.getText().toString());
                user.put("phoneNumber", PhoneNumber.getText().toString());
                user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                user.put("birthDate", birthDate.getText().toString());
                user.put("age", Userage.getText().toString());
                user.put("cast", Cast.getText().toString());
                user.put("referancePersonName", ReferancePersonName.getText().toString());
                user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                user.put("school", School.getText().toString());
                user.put("city", City.getText().toString());
                user.put("address", Address.getText().toString());
                user.put("area", Area.getText().toString());
                user.put("education", Education.getText().toString());
                user.put("skill", skill.getText().toString());
                user.put("village", village.getText().toString());
                user.put("email", email.getText().toString());
                user.put("formfilledDate", formFilledDate.getText().toString());
                user.put("fatherAge", fatherAge.getText().toString());
                user.put("fatherNumber", fatherPhone.getText().toString());
                user.put("fatherWork", fatherWork.getText().toString());
                user.put("fatherEmail", fatherEmail.getText().toString());
                user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("3");
                databaseReference.push().setValue(user);

                groupName.setText("");
                groupNo.setText("");
                Name.setText("");
                FatherName.setText("");
                PhoneNumber.setText("");
                WhatsAppNumber.setText("");
                birthDate.setText("");
                Userage.setText("");
                Cast.setText("");
                ReferancePersonName.setText("");
                ReferancePersonPhoneNumber.setText("");
                School.setText("");
                City.setText("");
                Address.setText("");
                Area.setText("");
                Education.setText("");
                skill.setText("");
                village.setText("");
                email.setText("");
                formFilledDate.setText("");
                fatherAge.setText("");
                fatherPhone.setText("");
                fatherWork.setText("");
                fatherEmail.setText("");
                yuvaSabha.setText("");
                profileImage.setImageDrawable(null);

            }

            if (groupname.equals("4") && profileUrl != null) {

                progressDialog.show();

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileImage/4/" + Name.getText().toString());

                storageReference.putFile(Uri.parse(profileUrl)).addOnSuccessListener(taskSnapshot -> {

                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

                        Map<String, Object> user = new HashMap<>();
                        user.put("groupNo", groupNo.getText().toString());
                        user.put("name", Name.getText().toString());
                        user.put("fatherName", FatherName.getText().toString());
                        user.put("phoneNumber", PhoneNumber.getText().toString());
                        user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                        user.put("birthDate", birthDate.getText().toString());
                        user.put("age", Userage.getText().toString());
                        user.put("cast", Cast.getText().toString());
                        user.put("referancePersonName", ReferancePersonName.getText().toString());
                        user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                        user.put("school", School.getText().toString());
                        user.put("city", City.getText().toString());
                        user.put("address", Address.getText().toString());
                        user.put("area", Area.getText().toString());
                        user.put("education", Education.getText().toString());
                        user.put("profileImage", uri.toString());
                        user.put("skill", skill.getText().toString());
                        user.put("village", village.getText().toString());
                        user.put("email", email.getText().toString());
                        user.put("formfilledDate", formFilledDate.getText().toString());
                        user.put("fatherAge", fatherAge.getText().toString());
                        user.put("fatherNumber", fatherPhone.getText().toString());
                        user.put("fatherWork", fatherWork.getText().toString());
                        user.put("fatherEmail", fatherEmail.getText().toString());
                        user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("4");
                        databaseReference.push().setValue(user);

                        groupName.setText("");
                        groupNo.setText("");
                        Name.setText("");
                        FatherName.setText("");
                        PhoneNumber.setText("");
                        WhatsAppNumber.setText("");
                        birthDate.setText("");
                        Userage.setText("");
                        Cast.setText("");
                        ReferancePersonName.setText("");
                        ReferancePersonPhoneNumber.setText("");
                        School.setText("");
                        City.setText("");
                        Address.setText("");
                        Area.setText("");
                        Education.setText("");
                        skill.setText("");
                        village.setText("");
                        email.setText("");
                        formFilledDate.setText("");
                        fatherAge.setText("");
                        fatherPhone.setText("");
                        fatherWork.setText("");
                        fatherEmail.setText("");
                        yuvaSabha.setText("");
                        profileImage.setImageDrawable(null);

                        Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }).addOnFailureListener(e -> {

                        progressDialog.dismiss();

                        Toast.makeText(this, "Failed upload data", Toast.LENGTH_SHORT).show();

                    });

                });

            } else if (groupname.equals("4") && profileUrl == null) {

                Map<String, Object> user = new HashMap<>();
                user.put("groupNo", groupNo.getText().toString());
                user.put("name", Name.getText().toString());
                user.put("fatherName", FatherName.getText().toString());
                user.put("phoneNumber", PhoneNumber.getText().toString());
                user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                user.put("birthDate", birthDate.getText().toString());
                user.put("age", Userage.getText().toString());
                user.put("cast", Cast.getText().toString());
                user.put("referancePersonName", ReferancePersonName.getText().toString());
                user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                user.put("school", School.getText().toString());
                user.put("city", City.getText().toString());
                user.put("address", Address.getText().toString());
                user.put("area", Area.getText().toString());
                user.put("education", Education.getText().toString());
                user.put("skill", skill.getText().toString());
                user.put("village", village.getText().toString());
                user.put("email", email.getText().toString());
                user.put("formfilledDate", formFilledDate.getText().toString());
                user.put("fatherAge", fatherAge.getText().toString());
                user.put("fatherNumber", fatherPhone.getText().toString());
                user.put("fatherWork", fatherWork.getText().toString());
                user.put("fatherEmail", fatherEmail.getText().toString());
                user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("4");
                databaseReference.push().setValue(user);

                groupName.setText("");
                groupNo.setText("");
                Name.setText("");
                FatherName.setText("");
                PhoneNumber.setText("");
                WhatsAppNumber.setText("");
                birthDate.setText("");
                Userage.setText("");
                Cast.setText("");
                ReferancePersonName.setText("");
                ReferancePersonPhoneNumber.setText("");
                School.setText("");
                City.setText("");
                Address.setText("");
                Area.setText("");
                Education.setText("");
                skill.setText("");
                village.setText("");
                email.setText("");
                formFilledDate.setText("");
                fatherAge.setText("");
                fatherPhone.setText("");
                fatherWork.setText("");
                fatherEmail.setText("");
                yuvaSabha.setText("");
                profileImage.setImageDrawable(null);

            }

            if (groupname.equals("5") && profileUrl != null) {

                progressDialog.show();

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileImage/5/" + Name.getText().toString());

                storageReference.putFile(Uri.parse(profileUrl)).addOnSuccessListener(taskSnapshot -> {

                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

                        Map<String, Object> user = new HashMap<>();
                        user.put("groupNo", groupNo.getText().toString());
                        user.put("name", Name.getText().toString());
                        user.put("fatherName", FatherName.getText().toString());
                        user.put("phoneNumber", PhoneNumber.getText().toString());
                        user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                        user.put("birthDate", birthDate.getText().toString());
                        user.put("age", Userage.getText().toString());
                        user.put("cast", Cast.getText().toString());
                        user.put("referancePersonName", ReferancePersonName.getText().toString());
                        user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                        user.put("school", School.getText().toString());
                        user.put("city", City.getText().toString());
                        user.put("address", Address.getText().toString());
                        user.put("area", Area.getText().toString());
                        user.put("education", Education.getText().toString());
                        user.put("profileImage", uri.toString());
                        user.put("skill", skill.getText().toString());
                        user.put("village", village.getText().toString());
                        user.put("email", email.getText().toString());
                        user.put("formfilledDate", formFilledDate.getText().toString());
                        user.put("fatherAge", fatherAge.getText().toString());
                        user.put("fatherNumber", fatherPhone.getText().toString());
                        user.put("fatherWork", fatherWork.getText().toString());
                        user.put("fatherEmail", fatherEmail.getText().toString());
                        user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("5");
                        databaseReference.push().setValue(user);

                        groupName.setText("");
                        groupNo.setText("");
                        Name.setText("");
                        FatherName.setText("");
                        PhoneNumber.setText("");
                        WhatsAppNumber.setText("");
                        birthDate.setText("");
                        Userage.setText("");
                        Cast.setText("");
                        ReferancePersonName.setText("");
                        ReferancePersonPhoneNumber.setText("");
                        School.setText("");
                        City.setText("");
                        Address.setText("");
                        Area.setText("");
                        Education.setText("");
                        skill.setText("");
                        village.setText("");
                        email.setText("");
                        formFilledDate.setText("");
                        fatherAge.setText("");
                        fatherPhone.setText("");
                        fatherWork.setText("");
                        fatherEmail.setText("");
                        yuvaSabha.setText("");
                        profileImage.setImageDrawable(null);

                        Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }).addOnFailureListener(e -> {

                        progressDialog.dismiss();

                        Toast.makeText(this, "Failed to upload data", Toast.LENGTH_SHORT).show();

                    });

                });

            } else if (groupname.equals("5") && profileUrl == null) {

                Map<String, Object> user = new HashMap<>();
                user.put("groupNo", groupNo.getText().toString());
                user.put("name", Name.getText().toString());
                user.put("fatherName", FatherName.getText().toString());
                user.put("phoneNumber", PhoneNumber.getText().toString());
                user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                user.put("birthDate", birthDate.getText().toString());
                user.put("age", Userage.getText().toString());
                user.put("cast", Cast.getText().toString());
                user.put("referancePersonName", ReferancePersonName.getText().toString());
                user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                user.put("school", School.getText().toString());
                user.put("city", City.getText().toString());
                user.put("address", Address.getText().toString());
                user.put("area", Area.getText().toString());
                user.put("education", Education.getText().toString());
                user.put("skill", skill.getText().toString());
                user.put("village", village.getText().toString());
                user.put("email", email.getText().toString());
                user.put("formfilledDate", formFilledDate.getText().toString());
                user.put("fatherAge", fatherAge.getText().toString());
                user.put("fatherNumber", fatherPhone.getText().toString());
                user.put("fatherWork", fatherWork.getText().toString());
                user.put("fatherEmail", fatherEmail.getText().toString());
                user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("5");
                databaseReference.push().setValue(user);

                groupName.setText("");
                groupNo.setText("");
                Name.setText("");
                FatherName.setText("");
                PhoneNumber.setText("");
                WhatsAppNumber.setText("");
                birthDate.setText("");
                Userage.setText("");
                Cast.setText("");
                ReferancePersonName.setText("");
                ReferancePersonPhoneNumber.setText("");
                School.setText("");
                City.setText("");
                Address.setText("");
                Area.setText("");
                Education.setText("");
                skill.setText("");
                village.setText("");
                email.setText("");
                formFilledDate.setText("");
                fatherAge.setText("");
                fatherPhone.setText("");
                fatherWork.setText("");
                fatherEmail.setText("");
                yuvaSabha.setText("");
                profileImage.setImageDrawable(null);

            }

            if (groupname.equals("6") && profileUrl != null) {

                progressDialog.show();

                Log.e(TAG, "onCreate2: ");

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileImage/6/" + Name.getText().toString());

                storageReference.putFile(Uri.parse(profileUrl)).addOnSuccessListener(taskSnapshot -> {

                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

                        Map<String, Object> user = new HashMap<>();
                        user.put("groupNo", groupNo.getText().toString());
                        user.put("name", Name.getText().toString());
                        user.put("fatherName", FatherName.getText().toString());
                        user.put("phoneNumber", PhoneNumber.getText().toString());
                        user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                        user.put("birthDate", birthDate.getText().toString());
                        user.put("age", Userage.getText().toString());
                        user.put("cast", Cast.getText().toString());
                        user.put("referancePersonName", ReferancePersonName.getText().toString());
                        user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                        user.put("school", School.getText().toString());
                        user.put("city", City.getText().toString());
                        user.put("address", Address.getText().toString());
                        user.put("area", Area.getText().toString());
                        user.put("education", Education.getText().toString());
                        user.put("profileImage", uri.toString());
                        user.put("skill", skill.getText().toString());
                        user.put("village", village.getText().toString());
                        user.put("email", email.getText().toString());
                        user.put("formfilledDate", formFilledDate.getText().toString());
                        user.put("fatherAge", fatherAge.getText().toString());
                        user.put("fatherNumber", fatherPhone.getText().toString());
                        user.put("fatherWork", fatherWork.getText().toString());
                        user.put("fatherEmail", fatherEmail.getText().toString());
                        user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("6");
                        databaseReference.push().setValue(user);

                        groupName.setText("");
                        groupNo.setText("");
                        Name.setText("");
                        FatherName.setText("");
                        PhoneNumber.setText("");
                        WhatsAppNumber.setText("");
                        birthDate.setText("");
                        Userage.setText("");
                        Cast.setText("");
                        ReferancePersonName.setText("");
                        ReferancePersonPhoneNumber.setText("");
                        School.setText("");
                        City.setText("");
                        Address.setText("");
                        Area.setText("");
                        Education.setText("");
                        skill.setText("");
                        village.setText("");
                        email.setText("");
                        formFilledDate.setText("");
                        fatherAge.setText("");
                        fatherPhone.setText("");
                        fatherWork.setText("");
                        fatherEmail.setText("");
                        yuvaSabha.setText("");
                        profileImage.setImageDrawable(null);

                        Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }).addOnFailureListener(e -> {

                        progressDialog.dismiss();

                        Toast.makeText(this, "Failed to upload data", Toast.LENGTH_SHORT).show();

                    });

                });

            } else if (groupname.equals("6") && profileUrl == null) {

                Map<String, Object> user = new HashMap<>();
                user.put("groupNo", groupNo.getText().toString());
                user.put("name", Name.getText().toString());
                user.put("fatherName", FatherName.getText().toString());
                user.put("phoneNumber", PhoneNumber.getText().toString());
                user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                user.put("birthDate", birthDate.getText().toString());
                user.put("age", Userage.getText().toString());
                user.put("cast", Cast.getText().toString());
                user.put("referancePersonName", ReferancePersonName.getText().toString());
                user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                user.put("school", School.getText().toString());
                user.put("city", City.getText().toString());
                user.put("address", Address.getText().toString());
                user.put("area", Area.getText().toString());
                user.put("education", Education.getText().toString());
                user.put("skill", skill.getText().toString());
                user.put("village", village.getText().toString());
                user.put("email", email.getText().toString());
                user.put("formfilledDate", formFilledDate.getText().toString());
                user.put("fatherAge", fatherAge.getText().toString());
                user.put("fatherNumber", fatherPhone.getText().toString());
                user.put("fatherWork", fatherWork.getText().toString());
                user.put("fatherEmail", fatherEmail.getText().toString());
                user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("6");
                databaseReference.push().setValue(user);

                groupName.setText("");
                groupNo.setText("");
                Name.setText("");
                FatherName.setText("");
                PhoneNumber.setText("");
                WhatsAppNumber.setText("");
                birthDate.setText("");
                Userage.setText("");
                Cast.setText("");
                ReferancePersonName.setText("");
                ReferancePersonPhoneNumber.setText("");
                School.setText("");
                City.setText("");
                Address.setText("");
                Area.setText("");
                Education.setText("");
                skill.setText("");
                village.setText("");
                email.setText("");
                formFilledDate.setText("");
                fatherAge.setText("");
                fatherPhone.setText("");
                fatherWork.setText("");
                fatherEmail.setText("");
                yuvaSabha.setText("");
                profileImage.setImageDrawable(null);

            }

            if (groupname.equals("7") && profileUrl != null) {

                progressDialog.show();

                Log.e(TAG, "onCreate2: ");

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileImage/7/" + Name.getText().toString());

                storageReference.putFile(Uri.parse(profileUrl)).addOnSuccessListener(taskSnapshot -> {

                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

                        Map<String, Object> user = new HashMap<>();
                        user.put("groupNo", groupNo.getText().toString());
                        user.put("name", Name.getText().toString());
                        user.put("fatherName", FatherName.getText().toString());
                        user.put("phoneNumber", PhoneNumber.getText().toString());
                        user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                        user.put("birthDate", birthDate.getText().toString());
                        user.put("age", Userage.getText().toString());
                        user.put("cast", Cast.getText().toString());
                        user.put("referancePersonName", ReferancePersonName.getText().toString());
                        user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                        user.put("school", School.getText().toString());
                        user.put("city", City.getText().toString());
                        user.put("address", Address.getText().toString());
                        user.put("area", Area.getText().toString());
                        user.put("education", Education.getText().toString());
                        user.put("profileImage", uri.toString());
                        user.put("skill", skill.getText().toString());
                        user.put("village", village.getText().toString());
                        user.put("email", email.getText().toString());
                        user.put("formfilledDate", formFilledDate.getText().toString());
                        user.put("fatherAge", fatherAge.getText().toString());
                        user.put("fatherNumber", fatherPhone.getText().toString());
                        user.put("fatherWork", fatherWork.getText().toString());
                        user.put("fatherEmail", fatherEmail.getText().toString());
                        user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("7");
                        databaseReference.push().setValue(user);

                        groupName.setText("");
                        groupNo.setText("");
                        Name.setText("");
                        FatherName.setText("");
                        PhoneNumber.setText("");
                        WhatsAppNumber.setText("");
                        birthDate.setText("");
                        Userage.setText("");
                        Cast.setText("");
                        ReferancePersonName.setText("");
                        ReferancePersonPhoneNumber.setText("");
                        School.setText("");
                        City.setText("");
                        Address.setText("");
                        Area.setText("");
                        Education.setText("");
                        skill.setText("");
                        village.setText("");
                        email.setText("");
                        formFilledDate.setText("");
                        fatherAge.setText("");
                        fatherPhone.setText("");
                        fatherWork.setText("");
                        fatherEmail.setText("");
                        yuvaSabha.setText("");
                        profileImage.setImageDrawable(null);

                        Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }).addOnFailureListener(e -> {

                        progressDialog.dismiss();

                        Toast.makeText(this, "Failed to upload data", Toast.LENGTH_SHORT).show();

                    });

                });

            } else if (groupname.equals("7") && profileUrl == null) {

                Map<String, Object> user = new HashMap<>();
                user.put("groupNo", groupNo.getText().toString());
                user.put("name", Name.getText().toString());
                user.put("fatherName", FatherName.getText().toString());
                user.put("phoneNumber", PhoneNumber.getText().toString());
                user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                user.put("birthDate", birthDate.getText().toString());
                user.put("age", Userage.getText().toString());
                user.put("cast", Cast.getText().toString());
                user.put("referancePersonName", ReferancePersonName.getText().toString());
                user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                user.put("school", School.getText().toString());
                user.put("city", City.getText().toString());
                user.put("address", Address.getText().toString());
                user.put("area", Area.getText().toString());
                user.put("education", Education.getText().toString());
                user.put("skill", skill.getText().toString());
                user.put("village", village.getText().toString());
                user.put("email", email.getText().toString());
                user.put("formfilledDate", formFilledDate.getText().toString());
                user.put("fatherAge", fatherAge.getText().toString());
                user.put("fatherNumber", fatherPhone.getText().toString());
                user.put("fatherWork", fatherWork.getText().toString());
                user.put("fatherEmail", fatherEmail.getText().toString());
                user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("7");
                databaseReference.push().setValue(user);

                groupName.setText("");
                groupNo.setText("");
                Name.setText("");
                FatherName.setText("");
                PhoneNumber.setText("");
                WhatsAppNumber.setText("");
                birthDate.setText("");
                Userage.setText("");
                Cast.setText("");
                ReferancePersonName.setText("");
                ReferancePersonPhoneNumber.setText("");
                School.setText("");
                City.setText("");
                Address.setText("");
                Area.setText("");
                Education.setText("");
                skill.setText("");
                village.setText("");
                email.setText("");
                formFilledDate.setText("");
                fatherAge.setText("");
                fatherPhone.setText("");
                fatherWork.setText("");
                fatherEmail.setText("");
                yuvaSabha.setText("");
                profileImage.setImageDrawable(null);

            }

            if (groupname.equals("8") && profileUrl != null) {

                progressDialog.show();

                Log.e(TAG, "onCreate2: ");

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileImage/8/" + Name.getText().toString());

                storageReference.putFile(Uri.parse(profileUrl)).addOnSuccessListener(taskSnapshot -> {

                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

                        Map<String, Object> user = new HashMap<>();
                        user.put("groupNo", groupNo.getText().toString());
                        user.put("name", Name.getText().toString());
                        user.put("fatherName", FatherName.getText().toString());
                        user.put("phoneNumber", PhoneNumber.getText().toString());
                        user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                        user.put("birthDate", birthDate.getText().toString());
                        user.put("age", Userage.getText().toString());
                        user.put("cast", Cast.getText().toString());
                        user.put("referancePersonName", ReferancePersonName.getText().toString());
                        user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                        user.put("school", School.getText().toString());
                        user.put("city", City.getText().toString());
                        user.put("address", Address.getText().toString());
                        user.put("area", Area.getText().toString());
                        user.put("education", Education.getText().toString());
                        user.put("profileImage", uri.toString());
                        user.put("skill", skill.getText().toString());
                        user.put("village", village.getText().toString());
                        user.put("email", email.getText().toString());
                        user.put("formfilledDate", formFilledDate.getText().toString());
                        user.put("fatherAge", fatherAge.getText().toString());
                        user.put("fatherNumber", fatherPhone.getText().toString());
                        user.put("fatherWork", fatherWork.getText().toString());
                        user.put("fatherEmail", fatherEmail.getText().toString());
                        user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("8");
                        databaseReference.push().setValue(user);

                        groupName.setText("");
                        groupNo.setText("");
                        Name.setText("");
                        FatherName.setText("");
                        PhoneNumber.setText("");
                        WhatsAppNumber.setText("");
                        birthDate.setText("");
                        Userage.setText("");
                        Cast.setText("");
                        ReferancePersonName.setText("");
                        ReferancePersonPhoneNumber.setText("");
                        School.setText("");
                        City.setText("");
                        Address.setText("");
                        Area.setText("");
                        Education.setText("");
                        skill.setText("");
                        village.setText("");
                        email.setText("");
                        formFilledDate.setText("");
                        fatherAge.setText("");
                        fatherPhone.setText("");
                        fatherWork.setText("");
                        fatherEmail.setText("");
                        yuvaSabha.setText("");
                        profileImage.setImageDrawable(null);

                        Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }).addOnFailureListener(e -> {

                        progressDialog.dismiss();

                        Toast.makeText(this, "Failed to upload data", Toast.LENGTH_SHORT).show();

                    });

                });

            } else if (groupname.equals("8") && profileUrl == null) {

                Map<String, Object> user = new HashMap<>();
                user.put("groupNo", groupNo.getText().toString());
                user.put("name", Name.getText().toString());
                user.put("fatherName", FatherName.getText().toString());
                user.put("phoneNumber", PhoneNumber.getText().toString());
                user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                user.put("birthDate", birthDate.getText().toString());
                user.put("age", Userage.getText().toString());
                user.put("cast", Cast.getText().toString());
                user.put("referancePersonName", ReferancePersonName.getText().toString());
                user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                user.put("school", School.getText().toString());
                user.put("city", City.getText().toString());
                user.put("address", Address.getText().toString());
                user.put("area", Area.getText().toString());
                user.put("education", Education.getText().toString());
                user.put("skill", skill.getText().toString());
                user.put("village", village.getText().toString());
                user.put("email", email.getText().toString());
                user.put("formfilledDate", formFilledDate.getText().toString());
                user.put("fatherAge", fatherAge.getText().toString());
                user.put("fatherNumber", fatherPhone.getText().toString());
                user.put("fatherWork", fatherWork.getText().toString());
                user.put("fatherEmail", fatherEmail.getText().toString());
                user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("8");
                databaseReference.push().setValue(user);

                groupName.setText("");
                groupNo.setText("");
                Name.setText("");
                FatherName.setText("");
                PhoneNumber.setText("");
                WhatsAppNumber.setText("");
                birthDate.setText("");
                Userage.setText("");
                Cast.setText("");
                ReferancePersonName.setText("");
                ReferancePersonPhoneNumber.setText("");
                School.setText("");
                City.setText("");
                Address.setText("");
                Area.setText("");
                Education.setText("");
                skill.setText("");
                village.setText("");
                email.setText("");
                formFilledDate.setText("");
                fatherAge.setText("");
                fatherPhone.setText("");
                fatherWork.setText("");
                fatherEmail.setText("");
                yuvaSabha.setText("");
                profileImage.setImageDrawable(null);

            }

            if (groupname.equals("9") && profileUrl != null) {

                progressDialog.show();

                Log.e(TAG, "onCreate2: ");

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileImage/9/" + Name.getText().toString());

                storageReference.putFile(Uri.parse(profileUrl)).addOnSuccessListener(taskSnapshot -> {

                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

                        Map<String, Object> user = new HashMap<>();
                        user.put("groupNo", groupNo.getText().toString());
                        user.put("name", Name.getText().toString());
                        user.put("fatherName", FatherName.getText().toString());
                        user.put("phoneNumber", PhoneNumber.getText().toString());
                        user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                        user.put("birthDate", birthDate.getText().toString());
                        user.put("age", Userage.getText().toString());
                        user.put("cast", Cast.getText().toString());
                        user.put("referancePersonName", ReferancePersonName.getText().toString());
                        user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                        user.put("school", School.getText().toString());
                        user.put("city", City.getText().toString());
                        user.put("address", Address.getText().toString());
                        user.put("area", Area.getText().toString());
                        user.put("education", Education.getText().toString());
                        user.put("profileImage", uri.toString());
                        user.put("skill", skill.getText().toString());
                        user.put("village", village.getText().toString());
                        user.put("email", email.getText().toString());
                        user.put("formfilledDate", formFilledDate.getText().toString());
                        user.put("fatherAge", fatherAge.getText().toString());
                        user.put("fatherNumber", fatherPhone.getText().toString());
                        user.put("fatherWork", fatherWork.getText().toString());
                        user.put("fatherEmail", fatherEmail.getText().toString());
                        user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("9");
                        databaseReference.push().setValue(user);

                        groupName.setText("");
                        groupNo.setText("");
                        Name.setText("");
                        FatherName.setText("");
                        PhoneNumber.setText("");
                        WhatsAppNumber.setText("");
                        birthDate.setText("");
                        Userage.setText("");
                        Cast.setText("");
                        ReferancePersonName.setText("");
                        ReferancePersonPhoneNumber.setText("");
                        School.setText("");
                        City.setText("");
                        Address.setText("");
                        Area.setText("");
                        Education.setText("");
                        skill.setText("");
                        village.setText("");
                        email.setText("");
                        formFilledDate.setText("");
                        fatherAge.setText("");
                        fatherPhone.setText("");
                        fatherWork.setText("");
                        fatherEmail.setText("");
                        yuvaSabha.setText("");
                        profileImage.setImageDrawable(null);

                        Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }).addOnFailureListener(e -> {

                        progressDialog.dismiss();

                        Toast.makeText(this, "Failed to upload data", Toast.LENGTH_SHORT).show();

                    });

                });

            } else if (groupname.equals("9") && profileUrl == null) {

                Map<String, Object> user = new HashMap<>();
                user.put("groupNo", groupNo.getText().toString());
                user.put("name", Name.getText().toString());
                user.put("fatherName", FatherName.getText().toString());
                user.put("phoneNumber", PhoneNumber.getText().toString());
                user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                user.put("birthDate", birthDate.getText().toString());
                user.put("age", Userage.getText().toString());
                user.put("cast", Cast.getText().toString());
                user.put("referancePersonName", ReferancePersonName.getText().toString());
                user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                user.put("school", School.getText().toString());
                user.put("city", City.getText().toString());
                user.put("address", Address.getText().toString());
                user.put("area", Area.getText().toString());
                user.put("education", Education.getText().toString());
                user.put("skill", skill.getText().toString());
                user.put("village", village.getText().toString());
                user.put("email", email.getText().toString());
                user.put("formfilledDate", formFilledDate.getText().toString());
                user.put("fatherAge", fatherAge.getText().toString());
                user.put("fatherNumber", fatherPhone.getText().toString());
                user.put("fatherWork", fatherWork.getText().toString());
                user.put("fatherEmail", fatherEmail.getText().toString());
                user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("9");
                databaseReference.push().setValue(user);

                groupName.setText("");
                groupNo.setText("");
                Name.setText("");
                FatherName.setText("");
                PhoneNumber.setText("");
                WhatsAppNumber.setText("");
                birthDate.setText("");
                Userage.setText("");
                Cast.setText("");
                ReferancePersonName.setText("");
                ReferancePersonPhoneNumber.setText("");
                School.setText("");
                City.setText("");
                Address.setText("");
                Area.setText("");
                Education.setText("");
                skill.setText("");
                village.setText("");
                email.setText("");
                formFilledDate.setText("");
                fatherAge.setText("");
                fatherPhone.setText("");
                fatherWork.setText("");
                fatherEmail.setText("");
                yuvaSabha.setText("");
                profileImage.setImageDrawable(null);

            }

            if (groupname.equals("10") && profileUrl != null) {

                progressDialog.show();

                Log.e(TAG, "onCreate2: ");

                FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();

                StorageReference storageReference = FirebaseStorage.getInstance().getReference().child("profileImage/10/" + Name.getText().toString());

                storageReference.putFile(Uri.parse(profileUrl)).addOnSuccessListener(taskSnapshot -> {

                    storageReference.getDownloadUrl().addOnSuccessListener(uri -> {

                        Map<String, Object> user = new HashMap<>();
                        user.put("groupNo", groupNo.getText().toString());
                        user.put("name", Name.getText().toString());
                        user.put("fatherName", FatherName.getText().toString());
                        user.put("phoneNumber", PhoneNumber.getText().toString());
                        user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                        user.put("birthDate", birthDate.getText().toString());
                        user.put("age", Userage.getText().toString());
                        user.put("cast", Cast.getText().toString());
                        user.put("referancePersonName", ReferancePersonName.getText().toString());
                        user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                        user.put("school", School.getText().toString());
                        user.put("city", City.getText().toString());
                        user.put("address", Address.getText().toString());
                        user.put("area", Area.getText().toString());
                        user.put("education", Education.getText().toString());
                        user.put("profileImage", uri.toString());
                        user.put("skill", skill.getText().toString());
                        user.put("village", village.getText().toString());
                        user.put("email", email.getText().toString());
                        user.put("formfilledDate", formFilledDate.getText().toString());
                        user.put("fatherAge", fatherAge.getText().toString());
                        user.put("fatherNumber", fatherPhone.getText().toString());
                        user.put("fatherWork", fatherWork.getText().toString());
                        user.put("fatherEmail", fatherEmail.getText().toString());
                        user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                        DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("10");
                        databaseReference.push().setValue(user);

                        groupName.setText("");
                        groupNo.setText("");
                        Name.setText("");
                        FatherName.setText("");
                        PhoneNumber.setText("");
                        WhatsAppNumber.setText("");
                        birthDate.setText("");
                        Userage.setText("");
                        Cast.setText("");
                        ReferancePersonName.setText("");
                        ReferancePersonPhoneNumber.setText("");
                        School.setText("");
                        City.setText("");
                        Address.setText("");
                        Area.setText("");
                        Education.setText("");
                        skill.setText("");
                        village.setText("");
                        email.setText("");
                        formFilledDate.setText("");
                        fatherAge.setText("");
                        fatherPhone.setText("");
                        fatherWork.setText("");
                        fatherEmail.setText("");
                        yuvaSabha.setText("");
                        profileImage.setImageDrawable(null);

                        Toast.makeText(this, "Data uploaded successfully", Toast.LENGTH_SHORT).show();

                        progressDialog.dismiss();

                    }).addOnFailureListener(e -> {

                        progressDialog.dismiss();

                        Toast.makeText(this, "Failed to upload data", Toast.LENGTH_SHORT).show();

                    });

                });

            } else if (groupname.equals("10") && profileUrl == null) {

                Map<String, Object> user = new HashMap<>();
                user.put("groupNo", groupNo.getText().toString());
                user.put("name", Name.getText().toString());
                user.put("fatherName", FatherName.getText().toString());
                user.put("phoneNumber", PhoneNumber.getText().toString());
                user.put("whatsappNumber", WhatsAppNumber.getText().toString());
                user.put("birthDate", birthDate.getText().toString());
                user.put("age", Userage.getText().toString());
                user.put("cast", Cast.getText().toString());
                user.put("referancePersonName", ReferancePersonName.getText().toString());
                user.put("referancePersonNumber", ReferancePersonPhoneNumber.getText().toString());
                user.put("school", School.getText().toString());
                user.put("city", City.getText().toString());
                user.put("address", Address.getText().toString());
                user.put("area", Area.getText().toString());
                user.put("education", Education.getText().toString());
                user.put("skill", skill.getText().toString());
                user.put("village", village.getText().toString());
                user.put("email", email.getText().toString());
                user.put("formfilledDate", formFilledDate.getText().toString());
                user.put("fatherAge", fatherAge.getText().toString());
                user.put("fatherNumber", fatherPhone.getText().toString());
                user.put("fatherWork", fatherWork.getText().toString());
                user.put("fatherEmail", fatherEmail.getText().toString());
                user.put("fatherYuvaSabha", yuvaSabha.getText().toString());

                DatabaseReference databaseReference = firebaseDatabase.getReference("Users").child("10");
                databaseReference.push().setValue(user);

                groupName.setText("");
                groupNo.setText("");
                Name.setText("");
                FatherName.setText("");
                PhoneNumber.setText("");
                WhatsAppNumber.setText("");
                birthDate.setText("");
                Userage.setText("");
                Cast.setText("");
                ReferancePersonName.setText("");
                ReferancePersonPhoneNumber.setText("");
                School.setText("");
                City.setText("");
                Address.setText("");
                Area.setText("");
                Education.setText("");
                skill.setText("");
                village.setText("");
                email.setText("");
                formFilledDate.setText("");
                fatherAge.setText("");
                fatherPhone.setText("");
                fatherWork.setText("");
                fatherEmail.setText("");
                yuvaSabha.setText("");
                profileImage.setImageDrawable(null);

            }

        });

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, groupNameList);
        groupName.setAdapter(adapter);

        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, skillList);
        skill.setAdapter(adapter1);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {

        if (resultCode == Activity.RESULT_OK && requestCode == 1) {

            Uri uri = data.getData();

            profileUrl = String.valueOf(uri);

            profileImage.setImageURI(Uri.parse(profileUrl));

        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        startActivity(new Intent(MainActivity.this, MainScreen.class));
        finish();

    }
}