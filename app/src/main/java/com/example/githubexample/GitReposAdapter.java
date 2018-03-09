package com.example.githubexample;

import android.content.Context;
import android.graphics.Typeface;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class GitReposAdapter extends RecyclerView.Adapter<GitReposAdapter.GitViewHolder>{

    private Context context;
    private ArrayList<WrapperGit> reposList;
    Onclick listener;

    public GitReposAdapter(Context context, ArrayList<WrapperGit> reposList) {
        this.context = context;
        this.reposList = reposList;
    }

    public void setListener(Onclick listener) {
        this.listener = listener;
    }

    @Override
    public GitViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v= LayoutInflater.from(context).inflate(R.layout.item_git_repo,parent,false);
        return new GitViewHolder(v);
    }

    @Override
    public void onBindViewHolder(GitViewHolder holder, final int position) {
        WrapperGit git=reposList.get(position);
        holder.txt_git_title.setText(git.getTitle());
        holder.txt_git_description.setText(git.getDescription());

        holder.txt_git_title.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Regular.ttf"));
        holder.txt_git_description.setTypeface(Typeface.createFromAsset(context.getAssets(),"fonts/Roboto-Regular.ttf"));

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v)
            {
                if(listener!=null)
                {
                    listener.onclick(position);
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return reposList.size();
    }

    class GitViewHolder extends RecyclerView.ViewHolder
    {
        TextView txt_git_title,txt_git_description;
        public GitViewHolder(View itemView)
        {
            super(itemView);
            txt_git_title=itemView.findViewById(R.id.txt_git_title);
            txt_git_description=itemView.findViewById(R.id.txt_git_description);

        }
    }

    interface Onclick
    {
        void onclick(int position);
    }
}
