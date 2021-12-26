package com.example.lostandfoundpro.Adapter;

import android.app.Activity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.lostandfoundpro.Model.Comments;
import com.example.lostandfoundpro.Model.Users;
import com.example.lostandfoundpro.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CommentsAdapter extends RecyclerView.Adapter<CommentsAdapter.CommentsViewHolder> {

    private Activity context;
    private List<Users> usersList;
    private List<Comments> commentsList;
    private FirebaseFirestore firestore;
    private FirebaseAuth auth;

//    List<Users> usersList
    public CommentsAdapter(Activity context , List<Comments> commentsList){
        this.context = context;
        this.commentsList = commentsList;
//        this.usersList = usersList;
    }

    @NonNull
    @Override
    public CommentsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.each_comment , parent , false);
        firestore = FirebaseFirestore.getInstance();
        auth = FirebaseAuth.getInstance();
        return new CommentsViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentsViewHolder holder, int position) {

        Comments comments = commentsList.get(position);
        holder.setmComment(comments.getComment());

//        Users users = usersList.get(position);
//        holder.setmUserName(users.getUsername());
//        holder.setCircleImageView(users.getImgUri());

        String userId = comments.getUser();
        firestore.collection("ProfileDetails").document(userId).get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()){
                    String username = task.getResult().getString("username");
                    String images = task.getResult().getString("imgUri");

                    holder.setCircleImageView(images);
                    holder.setmUserName(username);
                }else {
                    Toast.makeText(context,task.getException().toString(),Toast.LENGTH_LONG).show();
                }
            }
        });



    }

    @Override
    public int getItemCount() {
        return commentsList.size();
    }

    public class CommentsViewHolder extends RecyclerView.ViewHolder{

        TextView mComment , mUserName;
        CircleImageView circleImageView;
        View mView;

        public CommentsViewHolder(@NonNull View itemView) {
            super(itemView);
            mView = itemView;
        }

        public void setmComment(String comment){
            mComment = mView.findViewById(R.id.comment_tv);
            mComment.setText(comment);
        }
        public void setmUserName(String userName){
            mUserName = mView.findViewById(R.id.comment_user);
            mUserName.setText(userName);
        }
        public void setCircleImageView(String profilePic){
            circleImageView = mView.findViewById(R.id.comment_Profile_pic);
            Glide.with(context).load(profilePic).into(circleImageView);
        }
    }
}
