package com.example.certificate_app;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.HashMap;
import java.util.Map;

public class Register_Screen extends AppCompatActivity {

    Button reg;
    ImageButton imageButton;
    EditText et1, et2, et3, et4, et5;
    StorageReference storageReference;
    FirebaseAuth fAuth;
    String email, password, details, inst, spin_val, userid;
    String disp_name;
    String img_url;
    private static final int PICK_IMAGE_REQUEST = 1;
    private Uri mImageUri;
    FirebaseFirestore firestore;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register__screen);

        storageReference = FirebaseStorage.getInstance().getReference();
        reg = (Button) findViewById(R.id.reg_bt);
        imageButton = (ImageButton) findViewById(R.id.imageButton);
        et1 = (EditText) findViewById(R.id.em_reg);
        et2 = (EditText) findViewById(R.id.pass_reg);
        et3 = (EditText) findViewById(R.id.det_reg);
        et4 = (EditText) findViewById(R.id.inst_reg);
        et5 = (EditText) findViewById(R.id.disp_Name);

        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                openFileChooser();
            }
        });

        fAuth = FirebaseAuth.getInstance();
        firestore = FirebaseFirestore.getInstance();

        if (fAuth.getCurrentUser() != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }

        reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                email = et1.getText().toString().trim();
                password = et2.getText().toString().trim();
                details = et3.getText().toString().trim();
                inst = et4.getText().toString().trim();
                disp_name = et5.getText().toString().trim();

                if (TextUtils.isEmpty(email)) {
                    et1.setError("Email is Required.");
                    return;
                }

                if (TextUtils.isEmpty(password)) {
                    et2.setError("Password is Required.");
                    return;
                }

                if (TextUtils.isEmpty(details)) {
                    et3.setError("Details is Required.");
                    return;
                }

                if (TextUtils.isEmpty(inst)) {
                    et4.setError("Institute is Required.");
                    return;
                }

                if (TextUtils.isEmpty(disp_name)) {
                    et5.setError("Name is Required.");
                    return;
                }

                if (password.length() < 6) {
                    et2.setError("Password Must be >= 6 Characters");
                    return;
                }


                if (mImageUri == null) {
                    Toast.makeText(Register_Screen.this, "PLS UPLOAD AN IMAGE", Toast.LENGTH_SHORT).show();
                }
                fAuth.createUserWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {

                            userid = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = firestore.collection("users").document(userid);
                            Map<String, Object> user = new HashMap<>();
                            user.put("details", details);
                            user.put("displayName", disp_name);
                            user.put("email", email);
                            user.put("instituteName", inst);
                            user.put("uid", userid);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register_Screen.this, "Succesful", Toast.LENGTH_SHORT).show();

                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register_Screen.this, "Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                            startstorageprocessimage();
                            startActivity(new Intent(getApplicationContext(), MainActivity.class));
                        } else {
                            //  Toast.makeText(Registeration.this, "Error ", Toast.LENGTH_SHORT).show();
                        }


                    }
                });

                // startActivity(new Intent(MainActivity.this,User_Main_Activity.class));
            }
        });
    }
        private void openFileChooser () {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent, PICK_IMAGE_REQUEST);
        }

        private void startstorageprocessimage () {

            if (mImageUri == null) {
                Toast.makeText(Register_Screen.this, "PLS UPLOAD AN IMAGE", Toast.LENGTH_SHORT).show();
            }

            final StorageReference reference = storageReference.child("image_files/" + System.currentTimeMillis() + ".jpeg");
            reference.putFile(mImageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                @Override
                public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                    reference.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                        @Override
                        public void onSuccess(Uri uri) {

                            DocumentReference documentReference = FirebaseFirestore.getInstance().collection("users").document(fAuth.getUid());
                            Map<String, Object> user = new HashMap<>();
                            user.put("imageUrl", uri.toString());
                            documentReference.update(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Toast.makeText(Register_Screen.this, "Photo Updated", Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                                @Override
                                public void onFailure(@NonNull Exception e) {
                                    Toast.makeText(Register_Screen.this, "Photo Updatation Failed", Toast.LENGTH_SHORT).show();
                                }
                            });
                        }
                    });

                }
            });

        }

        @Override
        protected void onActivityResult ( int requestCode, int resultCode, Intent data){
            super.onActivityResult(requestCode, resultCode, data);
            if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                    && data != null && data.getData() != null) {
                mImageUri = data.getData();
                Picasso.Builder builder = new Picasso.Builder(this);
                builder.listener(new Picasso.Listener() {
                    @Override
                    public void onImageLoadFailed(Picasso picasso, Uri uri, Exception exception) {
                        exception.printStackTrace();
                    }
                });
                builder.build().load(mImageUri).into(imageButton);
            }
        }
    }



