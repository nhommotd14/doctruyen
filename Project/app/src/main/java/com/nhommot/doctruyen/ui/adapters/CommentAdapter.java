package com.nhommot.doctruyen.ui.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.ValueEventListener;
import com.nhommot.doctruyen.R;
import com.nhommot.doctruyen.models.Comment;
import com.nhommot.doctruyen.models.User;
import com.nhommot.doctruyen.utils.FirebaseUtils;
import com.nhommot.doctruyen.utils.SharedPrefsUtils;
import com.squareup.picasso.Picasso;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentHolder>{

    private Context mContext;
    private List<Comment> mComments;

    public CommentAdapter(Context mContext, List<Comment> mComments) {
        this.mContext = mContext;
        this.mComments = mComments;
    }

    @Override
    public CommentHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.comment_item, parent, false);
        return new CommentHolder(view);
    }

    @Override
    public void onBindViewHolder(CommentHolder holder, int position) {
        holder.bindView(mComments.get(position));
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public class CommentHolder extends RecyclerView.ViewHolder {

        ImageView imgAvatar, imgDelete;
        TextView txtName, txtComment;

        public CommentHolder(View itemView) {
            super(itemView);
            imgAvatar = itemView.findViewById(R.id.imgAvatar);
            txtName = itemView.findViewById(R.id.txtName);
            txtComment = itemView.findViewById(R.id.txtComment);
            imgDelete = itemView.findViewById(R.id.imgDelete);
        }

        public void bindView(final Comment comment) {

            try{
                txtComment.setText(comment.getContent());
                FirebaseUtils.getUserRef().child(comment.getUserId())
                        .addValueEventListener(new ValueEventListener() {
                            @Override
                            public void onDataChange(DataSnapshot dataSnapshot) {
                                User user = dataSnapshot.getValue(User.class);
//                                txtName.setText(user.getFullName());
                                Picasso.with(mContext).load(user.getImgURL()).into(imgAvatar);
                            }

                            @Override
                            public void onCancelled(DatabaseError databaseError) {

                            }
                        });
                imgDelete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        FirebaseUtils.getCommentRef()
                                .child(comment.getBookId())
                                .child(comment.getCommentId()).removeValue();
                    }
                });
            }catch (Exception e){
                e.printStackTrace();
            }

        }
    }
}
