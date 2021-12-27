package com.example.bagunic.notice;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bagunic.R;

import java.util.List;

public class NoticeAdapter extends RecyclerView.Adapter<NoticeAdapter.VersionVH >{

    List<com.example.bagunic.notice.NoticeVersions> noticeVersionsList;

    public NoticeAdapter(List<com.example.bagunic.notice.NoticeVersions> noticeVersionsList) {
        this.noticeVersionsList = noticeVersionsList;
    }

    @NonNull
    @Override
    public VersionVH onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.rownotice,parent,false);
        return new VersionVH(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VersionVH holder, int position) {

        com.example.bagunic.notice.NoticeVersions noticeVersions = noticeVersionsList.get(position);
        holder.codeNameTxt.setText(noticeVersions.getCodeName());
        holder.versionTxt.setText(noticeVersions.getVersion());
        holder.apiLevelTxt.setText(noticeVersions.getApiLevel());
        holder.descriptionTxt.setText(noticeVersions.getDescription());

        boolean isExpandable = noticeVersionsList.get(position).isExpandable();
        holder.expandableLayout.setVisibility(isExpandable ? View.VISIBLE : View.GONE);

    }

    @Override
    public int getItemCount() {
        return noticeVersionsList.size();
    }

    public class VersionVH extends RecyclerView.ViewHolder {

        TextView codeNameTxt, versionTxt, apiLevelTxt, descriptionTxt;
        LinearLayout linearLayout;
        RelativeLayout expandableLayout;

        public VersionVH(@NonNull View itemView) {
            super(itemView);

            codeNameTxt = itemView.findViewById(R.id.code_name);
            versionTxt = itemView.findViewById(R.id.version);
            apiLevelTxt = itemView.findViewById(R.id.api_level);
            descriptionTxt = itemView.findViewById(R.id.description);
            linearLayout = itemView.findViewById(R.id.linear_layout);
            expandableLayout = itemView.findViewById(R.id.expandable_layout);

            linearLayout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    com.example.bagunic.notice.NoticeVersions noticeVersions = noticeVersionsList.get(getAdapterPosition());
                    noticeVersions.setExpandable(!noticeVersions.isExpandable());
                    notifyItemChanged(getAdapterPosition());
                }
            });


        }
    }
}





































