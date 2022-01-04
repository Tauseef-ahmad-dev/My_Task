package com.example.maju.mazdor.Activites;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.maju.mazdor.Model.ChatMessage;
import com.example.maju.mazdor.R;
import com.firebase.ui.auth.AuthUI;
import com.firebase.ui.database.FirebaseListAdapter;
import com.github.library.bubbleview.BubbleTextView;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import hani.momanii.supernova_emoji_library.Actions.EmojIconActions;
import hani.momanii.supernova_emoji_library.Helper.EmojiconEditText;

import static com.example.maju.mazdor.Adapter.UserAdapter.KEY_NAME;
import static com.example.maju.mazdor.Adapter.UserAdapter.KEY_UID;

public class ChatActivity extends AppCompatActivity {


    private static int SIGN_IN_REQUEST_CODE = 1;
    private FirebaseListAdapter<ChatMessage> adapter;
    RelativeLayout activity_main;

    String UserPRoID = "";
    String UserProName = "";

    public final static String Database_Path = "JOBS"; // Root Database Name for Firebase Database.
    static String UID;
    ProgressDialog progressDialog ;
    private DatabaseReference mDatabase;
    private FirebaseAuth firebaseAuth;
    private StorageReference mStorage;

    TextView AcitveUserName;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);


        mStorage = FirebaseStorage.getInstance().getReference();
        mDatabase = FirebaseDatabase.getInstance().getReference(Database_Path);
        firebaseAuth = FirebaseAuth.getInstance();


//        if (firebaseAuth.getCurrentUser() != null) {
//            //profile ativity
//            finish();
//            startActivity(new Intent(getApplicationContext(), Home.class));
//        }
        UID = firebaseAuth.getCurrentUser().getUid();
        Log.v("Loc", UID);

        Toolbar toolbar = (Toolbar) findViewById(R.id.chattingToolbar);
        setSupportActionBar(toolbar);

        activity_main = (RelativeLayout)findViewById(R.id.activity_main);
//        bac_btn = (ImageView) findViewById(R.id.Back);
        AcitveUserName = (TextView) findViewById(R.id.userNameAct);
        AcitveUserName = (TextView) findViewById(R.id.userNameAct);




        try {

            Intent intent = getIntent();
            if (null != intent) {
                UserPRoID = intent.getStringExtra(KEY_UID);
                UserProName = intent.getStringExtra(KEY_NAME);

                //set username in toolbar
                AcitveUserName.setText(UserProName);
            }
            else {
                Toast.makeText(this, "Null ID", Toast.LENGTH_SHORT).show();
            }

            System.out.println(UserPRoID + "UserID");
            System.out.println(UserProName + "UsernName_");



        }
        catch (Exception e){
        }



                //Check if not sign-in then navigate Signin page
                if(firebaseAuth.getCurrentUser() == null)
                {
                    startActivityForResult(AuthUI.getInstance().createSignInIntentBuilder().build(),SIGN_IN_REQUEST_CODE);
                }
                else
                {
                  Snackbar.make(activity_main,"Welcome "+firebaseAuth.getCurrentUser().getEmail(),Snackbar.LENGTH_SHORT).show();
                    displayChatMessage();
                }

    }

    private void displayChatMessage() {

        ListView listOfMessage = (ListView)findViewById(R.id.list_of_message);
        adapter = new FirebaseListAdapter<ChatMessage>(this,ChatMessage.class,R.layout.received_msg_row,mDatabase)
        {
            @Override
            protected void populateView(View v, ChatMessage model, int position) {
                //Get references to the views of list_item.xml
                TextView JobTitle, JobRate,JobLocation, JobDescription;
                JobTitle = (TextView) v.findViewById(R.id.job_title);
                JobRate = (TextView) v.findViewById(R.id.job_rate);
                JobLocation = (TextView) v.findViewById(R.id.job_location);
                JobDescription = (TextView) v.findViewById(R.id.job_description);

                Log.e("TAG", "populateView: " +model.getJob_title());
                JobTitle.setText(model.getJob_title());
                JobRate.setText(model.getJob_rate());
                JobLocation.setText(model.getJob_location());
                JobDescription.setText("Click Here to See");


                JobDescription.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        AlertDialog.Builder alertbox = new AlertDialog.Builder(v.getRootView().getContext());
                        alertbox.setMessage(model.getJob_description());
                        alertbox.setTitle("Job Description");

                        alertbox.setNeutralButton("OK",
                                new DialogInterface.OnClickListener() {

                                    public void onClick(DialogInterface arg0,
                                                        int arg1) {

                                    }
                                });
                        alertbox.show();
                    }
                });

            }
        };
        listOfMessage.setAdapter(adapter);
    }
}
