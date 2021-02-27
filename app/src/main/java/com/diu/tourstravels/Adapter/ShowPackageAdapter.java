package com.diu.tourstravels.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.diu.tourstravels.Activity.MainActivity;
import com.diu.tourstravels.Activity.PackageDetailsActivity;
import com.diu.tourstravels.Model.ModelPackage;
import com.diu.tourstravels.Model.ModelUser;
import com.diu.tourstravels.R;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static com.diu.tourstravels.R.drawable.ic_like_icon;
import static com.diu.tourstravels.R.drawable.ic_not_like_icon;

public class ShowPackageAdapter extends RecyclerView.Adapter<ShowPackageAdapter.ViewHolder> implements Filterable {

    private final Context context;
    List<ModelPackage> packages;
    List<ModelUser> users;
    ModelUser user;
    MainActivity activity;
    List<ModelPackage> allPackages;

    public ShowPackageAdapter(Context context, List<ModelPackage> packages, List<ModelUser> users, MainActivity activity) {
        this.context = context;
        this.packages = packages;
        allPackages= new ArrayList<>(packages);
        this.users = users;
        this.activity = activity;

    }

    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {

        View view = LayoutInflater.from(context).inflate(R.layout.gridview,viewGroup,false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder (@NonNull ViewHolder viewHolder, final int i) {

        Glide
                .with(context)
                .load(packages.get(i).getmThumbUrl())
                .into(viewHolder.imageView);


        if(packages.get(i).hasUserLiked)
        {
            viewHolder.like.setBackgroundResource(ic_like_icon);
        }
        else
        {
            viewHolder.like.setBackgroundResource(ic_not_like_icon);
        }


        viewHolder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                activity.likeHelper(packages.get(i));
            }
        });


        viewHolder.counter.setText(packages.get(i).like_counter+"");
        viewHolder.image_title.setText("Package: "+packages.get(i).getPackageName());
        viewHolder.user_name.setText("Location: "+packages.get(i).getPlaceName());


        viewHolder.cardView.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(context, PackageDetailsActivity.class);
                intent.putExtra("packageName",packages.get(i).getPackageName());
                intent.putExtra("imageThumb",packages.get(i).getmThumbUrl());
                intent.putExtra("adult",packages.get(i).getAdult());
                intent.putExtra("child",packages.get(i).getChild());
                intent.putExtra("description",packages.get(i).getDescription());
                intent.putExtra("stayAmount",packages.get(i).getStayAmount());
                intent.putExtra("foodAmount",packages.get(i).getFoodAmount());
                intent.putExtra("busAmount",packages.get(i).getBusAmount());
                intent.putExtra("trainAmount",packages.get(i).getTrainAmount());
                intent.putExtra("airlinesAmount",packages.get(i).getAirLinesAmount());
                intent.putExtra("days",packages.get(i).getDays());
                intent.putExtra("place",packages.get(i).getPlaceName());
                intent.putExtra("like_counter",packages.get(i).like_counter+"");
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                context.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return packages.size();
    }

    @Override
    public Filter getFilter() {
        return filter;
    }

    Filter filter= new Filter() {
        @Override
        protected FilterResults performFiltering(CharSequence charSequence) {

            List<ModelPackage> filteredPackagesList= new ArrayList<>();
            if(charSequence==null || charSequence.length()==0){
                filteredPackagesList.addAll(allPackages);
            }
            else {
                String searchChr = charSequence.toString().toLowerCase().trim();
                for(ModelPackage modelPackage:allPackages){
                    if(modelPackage.getDescription().toLowerCase().contains(searchChr)){
                        filteredPackagesList.add(modelPackage);
                    }
                }
            }

            FilterResults filterResults= new FilterResults();
            filterResults.values= filteredPackagesList;
            return filterResults;
        }

        @Override
        protected void publishResults(CharSequence charSequence, FilterResults filterResults) {

            packages.clear();
            packages.addAll((Collection<? extends ModelPackage>) filterResults.values);
            notifyDataSetChanged();

        }
    };

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView user_name,image_title,counter;
        ImageView imageView;
        CardView cardView;
        Button like;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            user_name=itemView.findViewById(R.id.tv_name);
            image_title=itemView.findViewById(R.id.title);
            imageView=itemView.findViewById(R.id.im_images);
            cardView=itemView.findViewById(R.id.cardView);
            like=itemView.findViewById(R.id.btn_like);
            counter=itemView.findViewById(R.id.tv_like_counter);
        }
    }

    public void setValue(ModelPackage modelPackage,ModelUser user)
    {
        packages.add(0,modelPackage);
        allPackages.add(0,modelPackage);
        users.add(0,user);
        notifyDataSetChanged();
    }

}
