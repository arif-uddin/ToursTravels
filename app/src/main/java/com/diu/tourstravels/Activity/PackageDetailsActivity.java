package com.diu.tourstravels.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.diu.tourstravels.Model.ModelPackage;
import com.diu.tourstravels.Model.ModelUser;
import com.diu.tourstravels.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.Objects;

public class PackageDetailsActivity extends AppCompatActivity {

    TextView likes,packageName,placeName,description,days,adult,child,stayAmount,foodAmount,busAmount,trainAmount,airlinesAmount;
    ImageView im_images;

    ModelPackage modelPackage;
    private String imageUrl;
    private String UserId;

    ArrayList<ModelUser> users;

    private DatabaseReference mDatabaseRef;
    private FirebaseAuth firebaseAuth;
    private FirebaseUser firebaseUser;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_package_details);
        Objects.requireNonNull(getSupportActionBar()).setTitle("Package Details");


        likes=findViewById(R.id.likes);
        packageName=findViewById(R.id.packageName);
        placeName=findViewById(R.id.placeName);
        description=findViewById(R.id.description);
        days=findViewById(R.id.days);
        adult=findViewById(R.id.adult);
        child=findViewById(R.id.child);
        stayAmount=findViewById(R.id.stayAmount);
        foodAmount=findViewById(R.id.foodAmount);
        busAmount=findViewById(R.id.busAmount);
        trainAmount=findViewById(R.id.trainAmount);
        airlinesAmount=findViewById(R.id.airlinesAmount);
        im_images=findViewById(R.id.im_images);

        mDatabaseRef = FirebaseDatabase.getInstance().getReference("comments");
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        modelPackage= new ModelPackage();


        imageUrl=getIntent().getExtras().getString("imageThumb");
        UserId=getIntent().getExtras().getString("userId");
        //details view of post
        Glide
                .with(getApplicationContext())
                .load(imageUrl)
                .into(im_images);

        description.setText("Description: "+getIntent().getExtras().getString("description"));
        placeName.setText("Location: "+getIntent().getExtras().getString("place"));
        packageName.setText("Package Name: "+getIntent().getExtras().getString("packageName"));
        days.setText("Days: "+getIntent().getExtras().getString("days"));
        adult.setText("Adult: "+getIntent().getExtras().getString("adult"));
        child.setText("Child: "+getIntent().getExtras().getString("child"));
        stayAmount.setText("Stay Cost [for given days]: "+getIntent().getExtras().getString("stayAmount")+" BDT");
        busAmount.setText("Bus Cost: "+getIntent().getExtras().getString("busAmount")+" BDT");
        trainAmount.setText("Train Cost: "+getIntent().getExtras().getString("trainAmount")+" BDT");
        airlinesAmount.setText("Air Fare: "+getIntent().getExtras().getString("airlinesAmount")+" BDT");
        foodAmount.setText("Food Cost: "+getIntent().getExtras().getString("foodAmount")+" BDT");
        likes.setText("Total Like(s): "+getIntent().getExtras().getString("like_counter"));

    }

    public void btnBuy(View view) {

        Intent intent=new Intent(PackageDetailsActivity.this,BuyActivity.class);
        intent.putExtra("packageName",getIntent().getExtras().getString("packageName"));
        intent.putExtra("place",getIntent().getExtras().getString("place"));
        intent.putExtra("days",getIntent().getExtras().getString("days"));
        intent.putExtra("adult",getIntent().getExtras().getString("adult"));
        intent.putExtra("child",getIntent().getExtras().getString("child"));
        intent.putExtra("stayAmount",getIntent().getExtras().getString("stayAmount"));
        intent.putExtra("foodAmount",getIntent().getExtras().getString("foodAmount"));
        intent.putExtra("imageThumb",getIntent().getExtras().getString("imageThumb"));
        intent.putExtra("busAmount",getIntent().getExtras().getString("busAmount"));
        intent.putExtra("trainAmount",getIntent().getExtras().getString("trainAmount"));
        intent.putExtra("airlinesAmount",getIntent().getExtras().getString("airlinesAmount"));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }
}