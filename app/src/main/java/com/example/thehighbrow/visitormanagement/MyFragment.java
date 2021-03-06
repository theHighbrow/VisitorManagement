package com.example.thehighbrow.visitormanagement;

import android.content.Context;
import android.os.Bundle;
import android.os.Environment;
import android.provider.ContactsContract;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import static android.view.View.VISIBLE;

public class MyFragment extends Fragment {
    RecyclerView recyclerView ;
    RecyclerView.Adapter adapter;
    ProgressBar progressBar;
    DatabaseReference databaseVisitor;
    ArrayList<Visitor> visitors;
    String TAG="adminActivity";
    ArrayList<DayVisitor> dayVisitors;
    ArrayList<Lead> leads;
    ArrayList<Vendor> vendors;
    ArrayList<Courier> couriers;



    @Override
    public void onStart() {
        super.onStart();

    }



    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v= null;
        Bundle bundle=getArguments();
        int i = bundle.getInt("pos");
        Date c = Calendar.getInstance().getTime();
        SimpleDateFormat date = new SimpleDateFormat("dd-");
        SimpleDateFormat month = new SimpleDateFormat("MM-");
        SimpleDateFormat year = new SimpleDateFormat("yyyy");
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String formattedDate = dateFormat.format(c);
        String dates = date.format(c);
        String months = month.format(c);
        String years = year.format(c);

        if (i==0){
            v = inflater.inflate(R.layout.visitor_fragment,container,false);
            databaseVisitor = FirebaseDatabase.getInstance().getReference("Noida Sec1");

            //databaseVisitor = FirebaseDatabase.getInstance().getReference("Noida Sec1/"+dates+months+years+"/visitor");

            recyclerView= v.findViewById(R.id.recyclerView);
            Context context = getContext();
            visitors = new ArrayList<Visitor>();
            Log.e(TAG, "onCreate: above llm");
            LinearLayoutManager mlm = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mlm);
            mlm.setReverseLayout(true);
            mlm.setStackFromEnd(true);
            adapter=new VisitorAdapter(visitors);
            recyclerView.setAdapter(adapter);
            Log.e(TAG, "onCreate: below llm");


            final View finalV = v;
            databaseVisitor.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    progressBar = finalV.findViewById(R.id.progressBar);
                    progressBar.setVisibility(VISIBLE);


                    for (DataSnapshot visitorSnapshot : dataSnapshot.getChildren()) {
                        Log.e(TAG, "onDataChange: adding visitor to visitors");
                        if (visitorSnapshot.hasChild("visitor")) {
                            for (DataSnapshot dataSnapshot1 : visitorSnapshot.child("visitor").getChildren()) {
                                Visitor visitor = dataSnapshot1.getValue(Visitor.class);
                                visitors.add(visitor);
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);

                                Log.e("MainActivity", "onDataChange: added visitor to visitors");
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Problem fetching databse", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (i==1){

            v = inflater.inflate(R.layout.day_fragment,container,false);
            databaseVisitor = FirebaseDatabase.getInstance().getReference("Noida Sec1");
            recyclerView= v.findViewById(R.id.recyclerViewDay);
            Context context = getContext();
            dayVisitors = new ArrayList<DayVisitor>();
            Log.e(TAG, "onCreate: above llm");
            LinearLayoutManager mlm = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mlm);
            mlm.setReverseLayout(true);
            mlm.setStackFromEnd(true);
            adapter = new DayAdapter(dayVisitors);
            recyclerView.setAdapter(adapter);
            Log.e(TAG, "onCreate: below llm");
            final View finalV = v;
            databaseVisitor.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressBar = finalV.findViewById(R.id.progressBar);
                    progressBar.setVisibility(VISIBLE);
                    for (DataSnapshot visitorSnapshot : dataSnapshot.getChildren()) {
                        if (visitorSnapshot.hasChild("dayVisitor")){
                            for (DataSnapshot dataSnapshot1:visitorSnapshot.child("dayVisitor").getChildren()){

                                Log.e(TAG, "onDataChange: adding visitor to visitors");
                                DayVisitor dayVisitor = dataSnapshot1.getValue(DayVisitor.class);
                                dayVisitors.add(dayVisitor);
                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);
                                Log.e("MainActivity", "onDataChange: added visitor to visitors exiting");
                            }
                        }
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Problem fetching databse", Toast.LENGTH_SHORT).show();
                }
            });
        }
        else if (i==2){
            v = inflater.inflate(R.layout.visitor_fragment,container,false);
            databaseVisitor = FirebaseDatabase.getInstance().getReference("Noida Sec1");
            recyclerView= v.findViewById(R.id.recyclerView);
            final Context context = getContext();

            leads = new ArrayList<Lead>();

            Log.e(TAG, "onCreate: above llm");
            LinearLayoutManager mlm = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mlm);
            mlm.setReverseLayout(true);
            mlm.setStackFromEnd(true);
            MyFragment myFragment ;
            adapter = new LeadAdapter(leads);

            recyclerView.setAdapter(adapter);
            Log.e(TAG, "onCreate: below llm");
            final View finalV = v;
            databaseVisitor.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    progressBar = finalV.findViewById(R.id.progressBar);
                    progressBar.setVisibility(VISIBLE);
                    for (DataSnapshot visitorSnapshot : dataSnapshot.getChildren()) {
                        if (visitorSnapshot.hasChild("91lead")){
                            for (DataSnapshot dataSnapshot1: visitorSnapshot.child("91lead").getChildren()){

                                Log.e(TAG, "onDataChange: adding visitor to visitors");
                                Lead lead = dataSnapshot1.getValue(Lead.class);
                                leads.add(lead);

                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);

                                Log.e("MainActivity", "onDataChange: added visitor to visitors");
                            }


                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Problem fetching databse", Toast.LENGTH_SHORT).show();

                }
            });

        }
        else if (i==3){

            v = inflater.inflate(R.layout.visitor_fragment,container,false);
            databaseVisitor = FirebaseDatabase.getInstance().getReference("Noida Sec1");
            recyclerView= v.findViewById(R.id.recyclerView);
            Context context = getContext();

            vendors = new ArrayList<Vendor>();

            Log.e(TAG, "onCreate: above llm");
            LinearLayoutManager mlm = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mlm);
            mlm.setReverseLayout(true);
            mlm.setStackFromEnd(true);
            MyFragment myFragment ;

            adapter = new VendorAdapter(vendors);

            recyclerView.setAdapter(adapter);
            Log.e(TAG, "onCreate: below llm");


            final View finalV = v;
            databaseVisitor.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    progressBar = finalV.findViewById(R.id.progressBar);
                    progressBar.setVisibility(VISIBLE);


                    for (DataSnapshot visitorSnapshot : dataSnapshot.getChildren()) {
                        if (visitorSnapshot.hasChild("vendor")){
                            for (DataSnapshot dataSnapshot1: visitorSnapshot.child("vendor").getChildren()){
                                Log.e(TAG, "onDataChange: adding visitor to visitors");

                                Vendor vendor = dataSnapshot1.getValue(Vendor.class);
                                vendors.add(vendor);

                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);

                                Log.e("MainActivity", "onDataChange: added visitor to visitors");

                            }
                        }

                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Problem fetching databse", Toast.LENGTH_SHORT).show();

                }
            });

        }
        else if (i==4){

            v = inflater.inflate(R.layout.visitor_fragment,container,false);
            databaseVisitor = FirebaseDatabase.getInstance().getReference("Noida Sec1");
            recyclerView= v.findViewById(R.id.recyclerView);
            Context context = getContext();

            couriers = new ArrayList<Courier>();

            Log.e(TAG, "onCreate: above llm");
            LinearLayoutManager mlm = new LinearLayoutManager(getActivity());
            recyclerView.setLayoutManager(mlm);
            mlm.setReverseLayout(true);
            mlm.setStackFromEnd(true);
            MyFragment myFragment ;

            adapter = new CourierAdapter(couriers);

            recyclerView.setAdapter(adapter);
            Log.e(TAG, "onCreate: below llm");


            final View finalV = v;
            databaseVisitor.addValueEventListener(new ValueEventListener() {


                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                    progressBar = finalV.findViewById(R.id.progressBar);
                    progressBar.setVisibility(VISIBLE);


                    for (DataSnapshot visitorSnapshot : dataSnapshot.getChildren()) {
                        if (visitorSnapshot.hasChild("courier")){
                            for (DataSnapshot dataSnapshot1:visitorSnapshot.child("courier").getChildren()){
                                Log.e(TAG, "onDataChange: adding visitor to visitors");

                                Courier courier = dataSnapshot1.getValue(Courier.class);
                                couriers.add(courier);

                                adapter.notifyDataSetChanged();
                                progressBar.setVisibility(View.GONE);

                                Log.e("MainActivity", "onDataChange: added visitor to visitors");

                            }
                        }
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                    Toast.makeText(getActivity(), "Problem fetching databse", Toast.LENGTH_SHORT).show();

                }
            });


        }
        else if(i==5){
            v = inflater.inflate(R.layout.csv_layout,container,false);
            databaseVisitor = FirebaseDatabase.getInstance().getReference("Noida Sec1");
            Button downloadbtn = v.findViewById(R.id.dbtn);

            downloadbtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {



                    databaseVisitor.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            for (DataSnapshot visitorSnapshot : dataSnapshot.getChildren()) {
                                //for (DataSnapshot dataSnapshot1:visitorSnapshot.child("08-02-2019").getChildren()) {
                                    String text = visitorSnapshot.toString();
                                    exporttxt(text);
                               // }
                            }
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                            Toast.makeText(getActivity(), "Problem fetching databse", Toast.LENGTH_SHORT).show();

                        }
                    });



                }

                private void exporttxt(String text) {

                        if(Environment.getExternalStorageState().equalsIgnoreCase("mounted"))//Check if Device Storage is present
                        {
                            try {
                                File root = new File(Environment.getExternalStorageDirectory(), "VisitorsDatabase");//You might want to change this to the name of your app. (This is a folder that will be created to store all of your txt files)
                                if (!root.exists()) {
                                    root.mkdirs();
                                }
                                File myTxt = new File(root, "database.json"); //You might want to change the filename
                                FileWriter writer = new FileWriter(myTxt);
                                    Log.e(TAG, "exporttxt: "+text );
                                    writer.append(text);//Writing the text
                                writer.flush();
                                writer.close();
                                Toast.makeText(getContext(), "File exported", Toast.LENGTH_SHORT).show();
                            } catch (IOException e) {
                                e.printStackTrace();
                                Toast.makeText(getContext(), "Error: "+e.getMessage(), Toast.LENGTH_SHORT).show();
                            }
                        }
                        else
                            Toast.makeText(getContext(), "Can't access device storage!", Toast.LENGTH_SHORT).show();

                }
            });

        }
        return v;

    }

}
