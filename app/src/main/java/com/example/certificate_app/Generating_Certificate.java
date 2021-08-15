package com.example.certificate_app;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.firestore.DocumentChange;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;

public class Generating_Certificate extends AppCompatActivity {

    ArrayList<emplist> arrayList;
    RecyclerView recview;
    myadapter adapter;
    FirebaseFirestore db;
    ProgressDialog progressDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_generating__certificate);

        progressDialog=new ProgressDialog(this);
        progressDialog.setCancelable(false);
        progressDialog.setMessage("Fetching Data");
        progressDialog.show();
        recview=(RecyclerView)findViewById(R.id.recyclerViewdiff);
        recview.setHasFixedSize(true);
        recview.setLayoutManager(new LinearLayoutManager(this));



        db=FirebaseFirestore.getInstance();
        arrayList= new ArrayList<emplist>();

        adapter= new myadapter(Generating_Certificate.this,arrayList);

        recview.setAdapter(adapter);

        EventChangeListener();

        Toast.makeText(this, "Fetching DATA", Toast.LENGTH_SHORT).show();


        // processsearch();

    }

    private void EventChangeListener() {

        db.collection("users")
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        if(error!=null){

                            if (progressDialog.isShowing())
                                progressDialog.dismiss();

                            Toast.makeText(Generating_Certificate.this, "Error "+error.getMessage(), Toast.LENGTH_SHORT).show();
                            return;
                        }
                        for (DocumentChange dc : value.getDocumentChanges()){

                            if (dc.getType()==DocumentChange.Type.ADDED){

                                arrayList.add(dc.getDocument().toObject(emplist.class));
                            }

                            adapter.notifyDataSetChanged();
                            if (progressDialog.isShowing())
                                progressDialog.dismiss();
                        }

                    }
                });
    }
}