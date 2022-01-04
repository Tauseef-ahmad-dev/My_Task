package com.example.maju.mazdor;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maju.mazdor.Model.Employee_details;
import com.example.maju.mazdor.Model.ProfileDet;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;

import de.hdodenhof.circleimageview.CircleImageView;

public class Signup extends AppCompatActivity implements View.OnClickListener {

    //public final static String Storage_Path = "Employee_profile/"; // Folder path for Firebase Storage.
    public final static String Database_Path = "Employee"; // Root Database Name for Firebase Database.
    EditText ImageName ;                // Creating EditText.
    ImageView SelectImage;
    private Uri FilePathUri;                // Creating URI.
    int Image_Request_Code = 7;    // Image request code for onActivityResult()
    static String UID;
    String imageUrl;

    ProgressDialog progressDialog ;
    private EditText Eemail;
    private EditText Epass;
    private Button Reg_coch;
    private CircleImageView uploadimg;
    EditText fname,phone,em_NIc,em_DEs;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private StorageReference mStorage;

    String naMe,paS,phonE,emaIl,desgin,nic;
    ImageView Imgupload;

    TextView LoginRoot;
    Button Reg;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Database_Path);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            //profile ativity
            finish();
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
        }

        progressDialog = new ProgressDialog(this);


        Eemail = (EditText) findViewById(R.id.userEmail);
        Epass = (EditText) findViewById(R.id.userPass);
        Reg_coch = (Button) findViewById(R.id.reg);
        fname = (EditText) findViewById(R.id.username);
        phone = (EditText) findViewById(R.id.userPhon);
        em_NIc = (EditText) findViewById(R.id.NIC);
        em_DEs = (EditText) findViewById(R.id.Des_emp);

        uploadimg = (CircleImageView) findViewById(R.id.profilePic);
        Imgupload = (ImageView) findViewById(R.id.uplobtn);
        LoginRoot = (TextView) findViewById(R.id.loginRoot);


        LoginRoot.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Signup.this,Login.class);
                startActivity(intent);
            }
        });

        //uploadimage calling
        Imgupload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent();
                intent.setType("image/*");
                intent.setAction(Intent.ACTION_GET_CONTENT);
                startActivityForResult(Intent.createChooser(intent, "Please Select Image"), Image_Request_Code);
            }
        });

        Reg_coch.setOnClickListener(this);

    }

    //open camera activty func and fb app
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Image_Request_Code && resultCode == RESULT_OK && data != null && data.getData() != null) {
            FilePathUri = data.getData();
            try {
                // Getting selected image into Bitmap.
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), FilePathUri);
                // Setting up bitmap selected image into ImageView.
                uploadimg.setImageBitmap(bitmap);
                // After selecting image change choose button above text.
                Toast.makeText(this, "Image Selected", Toast.LENGTH_SHORT).show();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        //open fb intent
        //callbackManager.onActivityResult(requestCode, resultCode, data);
    }



    //Registration func()
    private void Authemployee(){

        //progressDialog.setCancelable(false);


        naMe = fname.getText().toString().trim();
        phonE = phone.getText().toString().trim();
        emaIl = Eemail.getText().toString().trim();
        paS = Epass.getText().toString().trim();
        desgin = em_DEs.getText().toString().trim();
        nic= em_NIc.getText().toString().trim();

        if (TextUtils.isEmpty(naMe)){
            fname.setError("Required");
            return;
        }
        if (TextUtils.isEmpty(phonE)){
            phone.setError("Required");
            return;

        }
        if (TextUtils.isEmpty(emaIl)){
            Eemail.setError("Required");
            return;

        }
        if (TextUtils.isEmpty(paS)){
            Epass.setError("Required");
            return;

        }

        if (TextUtils.isEmpty(desgin)){
            em_DEs.setError("Required");
            return;

        }
        if (TextUtils.isEmpty(nic)){
            em_NIc.setError("Required");
            return;
        }


        progressDialog.setMessage("Please Wait");
        progressDialog.show();

            firebaseAuth.createUserWithEmailAndPassword(emaIl, paS)
                    .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {

                            SharedPreferencesUtils sharedPreferencesUtils = new SharedPreferencesUtils();

//                            sharedPreferencesUtils.setUserName(Signup.this,fname.getText().toString().trim());
//                            sharedPreferencesUtils.setUserEmail(Signup.this,Eemail.getText().toString().trim());
//                            sharedPreferencesUtils.setUserPhone(Signup.this,phone.getText().toString().trim());
//                            sharedPreferencesUtils.setUserName(Signup.this,fname.getText().toString().trim());


                            try {
                                FirebaseUser mFirebaseUser = firebaseAuth.getCurrentUser();
                                if(mFirebaseUser != null) {
                                    Log.e("TAG", "onComplete: "+mFirebaseUser.getUid());
                                    UID = mFirebaseUser.getUid(); //Do what you need to do with the id
                                    UploadImageFileToFirebaseStorage();
                                }
//                                UID = firebaseAuth.getCurrentUser().getUid();
//                                UploadImageFileToFirebaseStorage();
                            }
                            catch (Exception e) {
                                Toast.makeText(getApplicationContext(), "Exception applied", Toast.LENGTH_LONG).show();
                                Log.e("eee", e.getMessage());
                                Log.e("ddd","dsd"+ e);
                                progressDialog.dismiss();
                            }

                        }
                    }).addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(Signup.this, "error"+e, Toast.LENGTH_SHORT).show();
                    Log.e("TAG", "onFailure: "+e );
                }
            });

    }

    //upload image and data in firebase
    public void UploadImageFileToFirebaseStorage() {

        if (FilePathUri != null){
            StorageReference riversRef = mStorage.child("images/"+UID+"/profile.jpg");
            /// MAIN FOLDER MAIN JA K UID KA FOLDER HOGA THEN IMAGE HOGI:
            riversRef.putFile(FilePathUri)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            Log.e("TAG", "onSuccesshehe: ");
//                            Uri downUrl = taskSnapshot.getDownloadUrl();
//                            // Log.d("downUrl" , downUrl.toString());
//                            imageUrl = downUrl.toString();
                           // Validation();
                            SaveData();
                            proFile();

                        }

                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {

                            Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });

        }else {

            Toast.makeText(Signup.this , " Image Uploading Error" , Toast.LENGTH_SHORT).show();

        }
    }

    public void SaveData(){

        Log.e("TAG", "SaveDatahehe: " );

        Employee_details emp_details  = new Employee_details(naMe , phonE , emaIl , paS, desgin, nic , imageUrl ,UID);
        mDatabase.child(UID).setValue(emp_details, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                Log.e("TAG", "onComplete: " );
                if (databaseReference.equals(databaseError)){
                    progressDialog.dismiss();
                    Toast.makeText(Signup.this , "Error in Saving" , Toast.LENGTH_SHORT).show();
                }else {
                    progressDialog.dismiss();
                    Intent i = new Intent(Signup.this , MainActivity.class);
                    startActivity(i);
                }

            }
        });

    }


    public void proFile(){
        ProfileDet profileDet  = new ProfileDet(naMe , phonE , emaIl , desgin, nic , imageUrl ,UID);
        mDatabase.child("PROFILE").push().setValue(profileDet, new DatabaseReference.CompletionListener() {
            @Override
            public void onComplete(DatabaseError databaseError, DatabaseReference databaseReference) {
                if (databaseReference.equals(databaseError)){
                    Toast.makeText(Signup.this , "put error" , Toast.LENGTH_SHORT).show();
                }else {
                    Toast.makeText(Signup.this , "Flag: True" , Toast.LENGTH_SHORT).show();
                }

            }
        });
    }

    @Override
    public void onClick(View view) {
        if(view == Reg_coch){

            Authemployee();

        }

    }
}
