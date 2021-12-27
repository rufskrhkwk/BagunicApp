package com.example.bagunic.review;

import android.net.Uri;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagunic.R;

import java.util.ArrayList;

public class NoteAdapter extends RecyclerView.Adapter<NoteAdapter.ViewHolder>
        implements OnNoteItemClickListener {
    ArrayList<com.example.bagunic.review.Note> items = new ArrayList<com.example.bagunic.review.Note>();

    OnNoteItemClickListener listener;

    int layoutType = 0;

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(viewGroup.getContext());
        View itemView = inflater.inflate(R.layout.note_item, viewGroup, false);

        return new ViewHolder(itemView, this, layoutType);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int position) {
        com.example.bagunic.review.Note item = items.get(position);
        viewHolder.setItem(item);
        viewHolder.setLayoutType(layoutType);
    }

    @Override
    public int getItemCount() {
        return items.size();
    }

    public void addItem(com.example.bagunic.review.Note item) {
        items.add(item);
    }

    public void setItems(ArrayList<com.example.bagunic.review.Note> items) {
        this.items = items;
    }

    public com.example.bagunic.review.Note getItem(int position) {
        return items.get(position);
    }

    public void setOnItemClickListener(OnNoteItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public void onItemClick(ViewHolder holder, View view, int position) {
        if (listener != null) {
            listener.onItemClick(holder, view, position);
        }
    }

    public void switchLayout(int position) {
        layoutType = position;
    }


    static class ViewHolder extends RecyclerView.ViewHolder {
        LinearLayout layout1;
        LinearLayout layout2;

        ImageView moodImageView;
        ImageView moodImageView2;

        ImageView pictureExistsImageView;
        ImageView pictureImageView;

        ImageView weatherImageView;
        ImageView weatherImageView2;

        TextView contentsTextView, textView3;
        TextView contentsTextView2;

        TextView locationTextView;
        TextView locationTextView2;

        TextView dateTextView;
        TextView dateTextView2;
        ImageButton imageButton4;

        public ViewHolder(View itemView, final OnNoteItemClickListener listener, int layoutType) {
            super(itemView);

            layout1 = itemView.findViewById(R.id.layout1);
            layout2 = itemView.findViewById(R.id.layout2);

            moodImageView = itemView.findViewById(R.id.moodImageView);
            moodImageView2 = itemView.findViewById(R.id.moodImageView2);

            pictureExistsImageView = itemView.findViewById(R.id.pictureExistsImageView);
            pictureImageView = itemView.findViewById(R.id.pictureImageView);

            weatherImageView = itemView.findViewById(R.id.weatherImageView);
            weatherImageView2 = itemView.findViewById(R.id.weatherImageView2);

            contentsTextView = itemView.findViewById(R.id.contentsTextView);
            contentsTextView2 = itemView.findViewById(R.id.contentsTextView2);

            locationTextView = itemView.findViewById(R.id.locationTextView);
            locationTextView2 = itemView.findViewById(R.id.locationTextView2);

            dateTextView = itemView.findViewById(R.id.dateTextView);
            dateTextView2 = itemView.findViewById(R.id.dateTextView2);
            imageButton4 = itemView.findViewById(R.id.imageButton4);
            textView3 = itemView.findViewById(R.id.textView3);

            imageButton4.setOnClickListener(new View.OnClickListener() {
                private int i = 0;

                @Override
                public void onClick(View view) {
                    i += 1;
                    textView3.setText(i + "");

                }
            });

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int position = getAdapterPosition();

                    if (listener != null) {
                        listener.onItemClick(ViewHolder.this, view, position);
                    }
                }
            });

            setLayoutType(layoutType);
        }

        public void setItem(com.example.bagunic.review.Note item) {
            String mood = item.getMood();
            int moodIndex = Integer.parseInt(mood);
            setMoodImage(moodIndex);

            String picturePath = item.getPicture();
            if (picturePath != null && !picturePath.equals("")) {
                pictureExistsImageView.setVisibility(View.VISIBLE);
                pictureImageView.setVisibility(View.VISIBLE);
                pictureImageView.setImageURI(Uri.parse(picturePath));
                pictureExistsImageView.setImageURI(Uri.parse(picturePath));





            } else {
                pictureExistsImageView.setVisibility(View.VISIBLE);
                pictureImageView.setVisibility(View.VISIBLE);
                pictureImageView.setImageResource(R.drawable.t);


            }

            // set weather
            String weather = item.getWeather();
            int weatherIndex = Integer.parseInt(weather);
            setWeatherImage(weatherIndex);

            contentsTextView.setText(item.getContents());
            contentsTextView2.setText(item.getContents());

            locationTextView.setText(item.getAddress());
            locationTextView2.setText(item.getAddress());

            dateTextView.setText(item.getCreateDateStr());
            dateTextView2.setText(item.getCreateDateStr());
        }

        public void setMoodImage(int moodIndex) {
            switch (moodIndex) {
                case 0:
                    moodImageView.setImageResource(R.drawable.ee);
                    moodImageView2.setImageResource(R.drawable.ee);
                    break;
                case 1:
                    moodImageView.setImageResource(R.drawable.dd);
                    moodImageView2.setImageResource(R.drawable.dd);
                    break;
                case 2:
                    moodImageView.setImageResource(R.drawable.cc);
                    moodImageView2.setImageResource(R.drawable.cc);
                    break;
                case 3:
                    moodImageView.setImageResource(R.drawable.bb);
                    moodImageView2.setImageResource(R.drawable.bb);
                    break;
                case 4:
                    moodImageView.setImageResource(R.drawable.aa);
                    moodImageView2.setImageResource(R.drawable.aa);
                    break;
                default:
                    moodImageView.setImageResource(R.drawable.smile5_48);
                    moodImageView2.setImageResource(R.drawable.smile5_48);
                    break;
            }
        }

        public void setWeatherImage(int weatherIndex) {
            switch (weatherIndex) {
                case 0:
                    weatherImageView.setImageResource(R.drawable.love);
                    weatherImageView2.setImageResource(R.drawable.love);
                    break;
                case 1:
                    weatherImageView.setImageResource(R.drawable.love);
                    weatherImageView2.setImageResource(R.drawable.love);
                    break;
                case 2:
                    weatherImageView.setImageResource(R.drawable.love);
                    weatherImageView2.setImageResource(R.drawable.love);
                    break;
                case 3:
                    weatherImageView.setImageResource(R.drawable.love);
                    weatherImageView2.setImageResource(R.drawable.love);
                    break;
                case 4:
                    weatherImageView.setImageResource(R.drawable.love);
                    weatherImageView2.setImageResource(R.drawable.love);
                    break;
                case 5:
                    weatherImageView.setImageResource(R.drawable.love);
                    weatherImageView2.setImageResource(R.drawable.love);
                    break;
                case 6:
                    weatherImageView.setImageResource(R.drawable.love);
                    weatherImageView2.setImageResource(R.drawable.love);
                    break;
                default:
                    weatherImageView.setImageResource(R.drawable.love);
                    weatherImageView2.setImageResource(R.drawable.love);
                    break;
            }
        }

        public void setLayoutType(int layoutType) {
            if (layoutType == 0) {
                layout1.setVisibility(View.VISIBLE);
                layout2.setVisibility(View.GONE);
            } else if (layoutType == 1) {
                layout1.setVisibility(View.GONE);
                layout2.setVisibility(View.VISIBLE);
            }
        }

    }

}
