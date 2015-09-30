package com.example.david.instagramclient;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.makeramen.roundedimageview.RoundedImageView;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by David on 6/23/2015.
 */
public class InstagramPhotosAdapter extends ArrayAdapter<InstagramPhoto> {
    //what data do we need from the activity?
    //Context, Data Source
    private static class ViewHolder {
        TextView caption;
        TextView likes;
        ImageView picture;
        TextView timePosted;
        TextView username;
        RoundedImageView riv;
        TextView comments;
    }



    public InstagramPhotosAdapter(Context context, List<InstagramPhoto> objects) {
        super(context, android.R.layout.simple_list_item_1, objects);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent){
        //Get the data item for this position
        InstagramPhoto photo = getItem(position);
        ViewHolder viewHolder;
        // Check if we are using a recycled view, if not we need to inflate
        if (convertView == null) {
            viewHolder = new ViewHolder();
            // create a new view from template
            //convertView = LayoutInflater.from(getContext()).inflate(R.layout.item_photo, parent, false);
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.item_photo, parent, false);
            viewHolder.caption = (TextView) convertView.findViewById(R.id.tvCaption);
            viewHolder.picture = (ImageView) convertView.findViewById(R.id.ivPhoto);
            viewHolder.likes = (TextView) convertView.findViewById(R.id.tvLikes);
            viewHolder.timePosted = (TextView)convertView.findViewById(R.id.tvTimePosted);
            viewHolder.username = (TextView)convertView.findViewById(R.id.tvUsername);
            viewHolder.riv = (RoundedImageView)convertView.findViewById(R.id.riv);
            viewHolder.comments = (TextView)convertView.findViewById(R.id.tvComments);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        // Lookup the view for populating the data (image, caption)
        //TextView tvCaption = (TextView) convertView.findViewById(R.id.tvCaption);
        //ImageView ivPhoto = (ImageView) convertView.findViewById(R.id.ivPhoto);
        ImageView iv = (ImageView)convertView.findViewById(R.id.ivLikeButton);
        iv.setImageResource(R.mipmap.ic_like_button);
        viewHolder.caption.setText(photo.caption);
        viewHolder.likes.setTextColor(Color.BLACK);
        viewHolder.likes.setText(photo.likesCount + " likes");
        viewHolder.likes.setTextColor(Color.BLUE);
        viewHolder.username.setText(photo.username);
        viewHolder.username.setTextColor(Color.BLUE);
        viewHolder.picture.setImageResource(0);
        viewHolder.timePosted.setText(photo.timePosted);
        viewHolder.riv.setImageResource(0);
        viewHolder.comments.setText(photo.comments);

        // Insert the model data into each of the view items
       // tvCaption.setText(photo.caption);
        // clear out the image view
        //ivPhoto.setImageResource(0); //clears out the imageview if it was recycled (right awa0)
        //Insert the image using picasso (send out async)
        Picasso.with(getContext()).load(photo.imageUrl).into(viewHolder.picture); // many ways to play around with this, look at Picasso documentation
        Picasso.with(getContext()).load(photo.userPhoto).into(viewHolder.riv);
        // Return the created item as a view
        return convertView;
    }
}
