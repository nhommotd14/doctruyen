package nhommot.com.firebase.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import nhommot.com.firebase.R;
import nhommot.com.firebase.model.Comment;

/**
 * Created by Nguyen Dung on 4/16/2018.
 */

public class CustomAdapter extends ArrayAdapter<Comment> {
    private Context context;
    private int resource;
    private List<Comment> arrComment;

    public CustomAdapter(Context context, int resource, ArrayList<Comment> arrComment) {
        super(context, resource, arrComment);
        this.context = context;
        this.resource = resource;
        this.arrComment = arrComment;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder viewHolder;
        convertView = LayoutInflater.from(context).inflate(R.layout.recycler_item,parent,false);
        viewHolder = new ViewHolder();
        viewHolder.tvId=(TextView)convertView.findViewById(R.id.tvId);
        viewHolder.tvContent=(TextView)convertView.findViewById(R.id.tvContent);
        Comment comment = arrComment.get(position);
        viewHolder.tvId.setText(String.valueOf(comment.getId()));
        viewHolder.tvContent.setText(String.valueOf(comment.getContent()));
        return convertView;
    }

    public class ViewHolder{
        TextView tvId, tvContent;
    }
}

