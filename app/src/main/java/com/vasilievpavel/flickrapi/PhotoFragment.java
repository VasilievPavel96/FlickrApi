package com.vasilievpavel.flickrapi;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;

/**
 * Created by pavel on 06.05.2017.
 */

public class PhotoFragment extends Fragment {
    private ImageView photoImage;
    public static final String ARGUMENT_POSITION = "ARG_POSITION";

    public static PhotoFragment newInstance(int pos) {
        Bundle args = new Bundle();
        args.putInt(ARGUMENT_POSITION, pos);
        PhotoFragment fragment = new PhotoFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_photo, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        photoImage = (ImageView) view.findViewById(R.id.photoImage);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        int pos = getArguments().getInt(ARGUMENT_POSITION);
        final Photo photo = PhotoManager.getInstance().getPhotoList().get(pos);
        if (photo.bitmap == null) {
            Glide.with(getActivity()).load(photo.downloadUrl).asBitmap().into(new BitmapImageViewTarget(photoImage){
                @Override
                protected void setResource(Bitmap resource) {
                    photo.bitmap=resource;
                    super.setResource(resource);
                }
            });
        } else {
            photoImage.setImageBitmap(photo.bitmap);
        }
    }
}
