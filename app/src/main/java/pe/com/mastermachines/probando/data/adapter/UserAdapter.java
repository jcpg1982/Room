package pe.com.mastermachines.probando.data.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

import pe.com.mastermachines.probando.R;
import pe.com.mastermachines.probando.data.entity.UserEntity;

/**
 * @author Anonymous on 9/05/2018.
 * @version 1.0
 */
public class UserAdapter extends RecyclerView.Adapter<UserAdapter.userViewHolder> {

    class userViewHolder extends RecyclerView.ViewHolder {
        private final TextView wordItemView;

        private userViewHolder(View itemView) {
            super(itemView);
            wordItemView = itemView.findViewById(R.id.textView);
        }
    }

    private final LayoutInflater mInflater;
    private List<UserEntity> mUsers; // Cached copy of words

    public UserAdapter(Context context) { mInflater = LayoutInflater.from(context); }

    @Override
    public userViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View itemView = mInflater.inflate(R.layout.recyclerview_item, parent, false);
        return new userViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(userViewHolder holder, int position) {
        UserEntity current = mUsers.get(position);
        holder.wordItemView.setText(current.getName()+" "+current.getFullname());
        //holder.wordItemView.setText(current.getFullname());
    }

    public void setUsers(List<UserEntity> userEntities){
        mUsers = userEntities;
        notifyDataSetChanged();
    }

    // getItemCount() is called many times, and when it is first called,
    // mWords has not been updated (means initially, it's null, and we can't return null).
    @Override
    public int getItemCount() {
        if (mUsers != null)
            return mUsers.size();
        else return 0;
    }
}