package com.example.mirodone.flickrbrowser;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

public class PhotoDetailActivity extends BaseActiviy {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_photo_detail);
      activateToolbar(true);

      Intent intent = getIntent();
      Photo photo = (Photo) intent.getSerializableExtra(PHOTO_TRANSFER);
      if(photo != null){
          TextView photoTitle = findViewById(R.id.photo_title);
          photoTitle.setText("Title: " + photo.getTitle());

          TextView photoTags = findViewById(R.id.photo_tags);
          photoTags.setText("Title: " + photo.getTags());

          TextView photoAuthor = findViewById(R.id.photo_author);
          photoAuthor.setText("Title: " + photo.getAuthor());

          ImageView photoImage = findViewById(R.id.photo_image);
          Picasso.get().load(photo.getLink())
                  .error(R.drawable.placeholder)
                  .placeholder(R.drawable.placeholder)
                  .into(photoImage);
      }

    }

}
