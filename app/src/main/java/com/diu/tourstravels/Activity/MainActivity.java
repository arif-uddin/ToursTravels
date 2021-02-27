package com.diu.tourstravels.Activity;

import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.StaggeredGridLayoutManager;

import com.diu.tourstravels.Adapter.ShowPackageAdapter;
import com.diu.tourstravels.Model.ModelLike;
import com.diu.tourstravels.Model.ModelPackage;
import com.diu.tourstravels.Model.ModelUser;
import com.diu.tourstravels.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    private  Menu menu;
    FirebaseAuth firebaseAuth;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    RecyclerView recyclerView;
    ShowPackageAdapter showPackageAdapter;
    public List<ModelPackage> packages;
    public List<ModelUser> users;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Tours & Travels Packages");
        SplashActivity.getInstance().finish();

        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        databaseReference = FirebaseDatabase.getInstance().getReference();

        packages = new ArrayList<>();
        users = new ArrayList<>();


        recyclerView = findViewById(R.id.recyclerView_home);
        recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL));
        recyclerView.addItemDecoration(new GridSpacingItemDecoration(2, dpToPx(5),true));
        ((DefaultItemAnimator) recyclerView.getItemAnimator()).setSupportsChangeAnimations(false);
        showPackageAdapter = new ShowPackageAdapter(getApplicationContext(),packages,users,MainActivity.this);
        recyclerView.setAdapter(showPackageAdapter);

        if(firebaseUser==null){
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }


        Query query = FirebaseDatabase.getInstance().getReference().child("packages");
        query.orderByKey().limitToFirst(100).addChildEventListener(new QueryForPackages());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {


        MenuInflater menuInflater=getMenuInflater();
        menuInflater.inflate(R.menu.threedotmenu,menu);
        this.menu= menu;
        updateMenuTitles();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        int id=item.getItemId();

        switch (id){
            case R.id.itemLogOut:
                firebaseAuth.signOut();
                Intent intent3= new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent3);
                finish();
                break;
            case R.id.itemBookingList:
                Intent intent= new Intent(MainActivity.this, BookingListActivity.class);
                startActivity(intent);
                break;
            case R.id.itemPaymentReceipt:
                Intent intent2= new Intent(MainActivity.this, ReceiptListActivity.class);
                startActivity(intent2);
                break;

            case R.id.itemAbout:
                Intent intent21= new Intent(MainActivity.this, About.class);
                startActivity(intent21);
                break;

            case R.id.action_search:
                item=menu.findItem(R.id.action_search);
                SearchView searchView= (SearchView) item.getActionView();
                searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                    @Override
                    public boolean onQueryTextSubmit(String query) {
                        return false;
                    }

                    @Override
                    public boolean onQueryTextChange(String newText) {
                        showPackageAdapter.getFilter().filter(newText);
                        return false;
                    }
                });

        }

        return true;
    }

    private void updateMenuTitles() {
        final MenuItem usernameMenu = menu.findItem(R.id.itemLogOut);

        Query query = FirebaseDatabase.getInstance().getReference().child("Users");
        query.orderByKey().equalTo(firebaseAuth.getCurrentUser().getUid()).addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                ModelUser modelUser=dataSnapshot.getValue(ModelUser.class);
                usernameMenu.setTitle("Log Out ("+modelUser.getUsername()+")");

            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });




    }

    @Override
    protected void onResume() {
        super.onResume();
        if(firebaseUser==null)
        {
            Intent intent = new Intent(MainActivity.this,LoginActivity.class);
            startActivity(intent);
            finish();
        }
    }


    class QueryForPackages implements ChildEventListener
    {

        @Override
        public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
            final ModelPackage modelPackage = dataSnapshot.getValue(ModelPackage.class);
            /*showImagesAdapter.setValue(modelImage,);
            showImagesAdapter.notifyDataSetChanged();*/
            DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
            databaseReference.child("users/" + modelPackage.getUserID()).addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    ModelUser  modelUser= dataSnapshot.getValue(ModelUser.class);
                    showPackageAdapter.setValue(modelPackage,modelUser);
                    showPackageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
            showPackageAdapter.notifyDataSetChanged();

            Query query2 = FirebaseDatabase.getInstance().getReference().child("likes");
            query2.orderByChild("imageKey").equalTo(modelPackage.getmKey()).addChildEventListener(new ChildEventListener() {
                @Override
                public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                    ModelLike modelLike = dataSnapshot.getValue(ModelLike.class);
                    modelPackage.addLike();
                    if(modelLike.getUserID().equals(FirebaseAuth.getInstance().getUid()))
                    {
                        modelPackage.hasUserLiked=true;
                        modelPackage.like_Key = dataSnapshot.getKey();
                    }
                    showPackageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

                    ModelLike modelLike = dataSnapshot.getValue(ModelLike.class);
                    modelPackage.removeLike();
                    if(modelLike.getUserID().equals(FirebaseAuth.getInstance().getUid()))
                    {
                        modelPackage.hasUserLiked=false;
                        modelPackage.like_Key = null;
                    }
                    showPackageAdapter.notifyDataSetChanged();
                }

                @Override
                public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }

        @Override
        public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

        }

        @Override
        public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

        }

        @Override
        public void onCancelled(@NonNull DatabaseError databaseError) {

        }

    }

    public void likeHelper(ModelPackage modelPackage)
    {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference();
        if(!modelPackage.hasUserLiked)
        {
            modelPackage.hasUserLiked=true;
            ModelLike modelLike = new ModelLike(firebaseUser.getUid(),modelPackage.getmKey());
            String key = databaseReference.child("likes").push().getKey();
            databaseReference.child("likes").child(key).setValue(modelLike);
            modelPackage.like_Key=key;
        }
        else
        {
            modelPackage.hasUserLiked=false;
            if(modelPackage.like_Key!=null)
            {
                databaseReference.child("likes").child(modelPackage.like_Key).removeValue();
            }
        }

    }

    private int dpToPx(int dp) {
        Resources r = getResources();
        return Math.round(TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dp, r.getDisplayMetrics()));
    }

}