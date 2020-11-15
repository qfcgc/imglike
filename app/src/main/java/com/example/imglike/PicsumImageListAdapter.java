package com.example.imglike;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.*;
@Deprecated
public class PicsumImageListAdapter extends RecyclerView.Adapter<PicsumImageListAdapter.ImageViewHolder> {
    private final LinkedList<PicsumImageDataWrapper> mImageList;
    private final LayoutInflater mInflater;
    private final List<ImageViewHolder> holders = new LinkedList<>();
    private final MainActivity mainActivity;

    public PicsumImageListAdapter(MainActivity context,
                                  LinkedList<PicsumImageDataWrapper> imageList) {
        this.mainActivity = context;
        mInflater = LayoutInflater.from(context);
        this.mImageList = imageList;
    }

    @NonNull
    @Override
    public ImageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View mItemView = mInflater.inflate(R.layout.image_layout,
                parent, false);
        ImageViewHolder imageViewHolder = new ImageViewHolder(mItemView, mainActivity, this);
        holders.add(imageViewHolder);
        return imageViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ImageViewHolder holder, int position) {
        PicsumImageDataWrapper mCurrent = mImageList.get(position);
        holder.imageItemView.setImageBitmap(mCurrent.getPicsumImageData().getData());
        holder.setImages(mImageList);
        holder.setPicsumImageDataWrapper(mCurrent);
        holder.reloadLikedState();
    }

    @Override
    public int getItemCount() {
        return mImageList.size();
    }

    @Override
    public void onViewRecycled(@NonNull ImageViewHolder holder) {
        holder.foo();
    }

    public List<ImageViewHolder> getHolders() {
        return holders;
    }

    static class ImageViewHolder extends RecyclerView.ViewHolder {
        public final ImageView imageItemView;
        public final ImageView likeView;
        final PicsumImageListAdapter mAdapter;
        private PicsumImageDataWrapper picsumImageDataWrapper;
        public List<PicsumImageDataWrapper> images;
        private MainActivity mainActivity;

        public void foo() {
            if (picsumImageDataWrapper != null && picsumImageDataWrapper.getPicsumImageData() != null) {
                System.out.println(picsumImageDataWrapper.getPicsumImageData().toString());
            }
        }

        public ImageViewHolder(View itemView, MainActivity mainActivity, PicsumImageListAdapter adapter) {
            super(itemView);
            this.imageItemView = itemView.findViewById(R.id.image);
            this.imageItemView.setOnClickListener(this::imageClickListener);
            this.mainActivity = mainActivity;
            this.likeView = itemView.findViewById(R.id.image_like);
            this.mAdapter = adapter;
            this.images = Collections.emptyList();

            /*imageItemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mainActivity.launchSecondActivity(v);
                }
            });*/

            this.likeView.setOnClickListener(v -> {
                if (!picsumImageDataWrapper.getSaveLikedState().getLiked()) {
                    picsumImageDataWrapper.getSaveLikedState().setLiked(true);
                    likeView.setImageResource(R.drawable.ic_like_enable);
                } else {
                    picsumImageDataWrapper.getSaveLikedState().setLiked(false);
                    likeView.setImageResource(R.drawable.ic_like_disable);
                }
            });
        }

        public void setImages(List<PicsumImageDataWrapper> images) {
            this.images = images;
        }

        public void setPicsumImageDataWrapper(PicsumImageDataWrapper picsumImageDataWrapper) {
            this.picsumImageDataWrapper = picsumImageDataWrapper;
        }

        /**
         * @implSpec uses {@link #picsumImageDataWrapper} so it must be set
         */
        public void reloadLikedState() {
            if (picsumImageDataWrapper.getSaveLikedState().getLiked()) {
                picsumImageDataWrapper.getSaveLikedState().setLiked(true);
                likeView.setImageResource(R.drawable.ic_like_enable);
//                likedCachedValue = true;
            } else {
                picsumImageDataWrapper.getSaveLikedState().setLiked(false);
                likeView.setImageResource(R.drawable.ic_like_disable);
//                likedCachedValue = false;
            }
        }

        private void imageClickListener(View v)
        {
//            ActivityOptions options
//                    = ActivityOptions.makeSceneTransitionAnimation(mainActivity,
//                    new Pair<>(itemView.findViewById(R.id.image),
//                            mainActivity.getString(R.string.image_transition)),
//                    new Pair<>(itemView.findViewById(R.id.image_like), mainActivity.getString(R.string.image_like_transition))
//            );

            Bundle bundle = new Bundle();
            Intent intent = new Intent(mainActivity, ImageActivity.class);
            intent.putExtra("imageId", picsumImageDataWrapper.getPicsumImageData().getImageId());
            intent.putExtra("width", picsumImageDataWrapper.getPicsumImageData().getWidth());
            intent.putExtra("hmac", picsumImageDataWrapper.getPicsumImageData().getHmac());
            intent.putExtra("liked", picsumImageDataWrapper.getSaveLikedState().getLiked());
            mainActivity.startActivityForResult(intent, 10, bundle);
        }
    }
}
