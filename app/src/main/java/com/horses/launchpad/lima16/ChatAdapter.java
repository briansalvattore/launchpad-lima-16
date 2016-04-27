package com.horses.launchpad.lima16;

import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.List;

/**
 * @author Brian Salvattore
 */
public class ChatAdapter extends RecyclerView.Adapter<ChatHolder> {

    private List<MessageEntity> list;
    private PersonEntity personEntity;

    public ChatAdapter(List<MessageEntity> list, PersonEntity personEntity) {
        this.list = list;
        this.personEntity = personEntity;
    }

    @Override
    public ChatHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        switch (viewType){
            case 0 :
                return ChatHolder.initLeft(parent);
            case 1 :
                return ChatHolder.initRight(parent);
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(ChatHolder holder, int position) {
        holder.setData(list.get(position));
    }

    @Override
    public int getItemCount() {

        return list.size();
    }

    @Override
    public int getItemViewType(int position) {

        if(list.get(position).getEntity().getId().equals(personEntity.getId())){
            return 1;
        }

        return super.getItemViewType(position);
    }
}
