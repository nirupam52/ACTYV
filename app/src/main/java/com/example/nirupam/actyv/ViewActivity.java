package com.example.nirupam.actyv;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import static com.example.nirupam.actyv.R.*;

public class ViewActivity extends AppCompatActivity implements View.OnClickListener {

    private RecyclerView activityRecyclerView;
    private DatabaseReference databaseReference;
    private FirebaseAuth firebaseAuth;
    private FloatingActionButton addActivityFAB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_view);

        firebaseAuth = FirebaseAuth.getInstance();
         FirebaseUser user = firebaseAuth.getCurrentUser();

        //checking for user login
        if(user == null){
            finish();
            startActivity(new Intent(this,LoginActivity.class));
        }

        databaseReference = FirebaseDatabase.getInstance().getReference().child("activities of : " + user.getUid());
        databaseReference.keepSynced(true);

        addActivityFAB = (FloatingActionButton) findViewById(id.add_act_btn);
        addActivityFAB.setOnClickListener(this);

        activityRecyclerView = (RecyclerView) findViewById(id.act_recy);
        activityRecyclerView.setHasFixedSize(true);
        activityRecyclerView.setLayoutManager(new LinearLayoutManager(this));

        NotificationScheduler.scheduleNotifications(this);



    }

    //creating the MENU
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.menu,menu);
        return true;
    }

    //handling menu item selection
    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        switch(item.getItemId()){

            case R.id.menu_settings:
                startActivity(new Intent(getBaseContext(),SettingsActivity.class));
                return true;
            case R.id.menu_logout:
                firebaseAuth.signOut();
                finish();
                startActivity(new Intent(this,LoginActivity.class));

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    protected void onStart(){
        super.onStart();
        FirebaseRecyclerAdapter<ActivityTemplate, ActivityViewHolder> firebaseRecyclerAdapter = new FirebaseRecyclerAdapter<ActivityTemplate, ActivityViewHolder>
                (ActivityTemplate.class, layout.activity_view,ActivityViewHolder.class,databaseReference) {
            //creates individual card views
            @Override
            public ActivityViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(layout.activity_card_view, parent, false);
                return new ActivityViewHolder(view);
            }
            //when each viewHolder is created, inflate each holder as a activity_card_view


            //binds data to the view
            @Override
            protected void populateViewHolder(ActivityViewHolder viewHolder, ActivityTemplate model, int position) {
                viewHolder.setTimestamp(model.getFormattedTimestamp());
                viewHolder.setContent(model.getActivity());

            }
        };

        activityRecyclerView.setAdapter(firebaseRecyclerAdapter);
    }

    //clickListener for the ADD FAB
    @Override
    public void onClick(View view) {
        if(view == addActivityFAB){
            startActivity(new Intent(this, AddActivity.class));
        }
    }

    public static class ActivityViewHolder extends RecyclerView.ViewHolder{

        //@Override
        // public ActivityViewHolder onActivityViewHolder()

        View view;

        public ActivityViewHolder(View itemView){
            super(itemView);
            view = itemView;
        }

        public void setTimestamp(String timestamp){
            TextView activity_timetsamp  = (TextView)view.findViewById(id.act_timestamp);

            activity_timetsamp.setText(timestamp);
        }

        public void setContent(String content){
            TextView activity_content = (TextView)view.findViewById(id.act_content);
            activity_content.setText(content);
        }

    }


}
