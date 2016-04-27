package com.horses.launchpad.lima16;

import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.nostra13.universalimageloader.core.ImageLoader;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import de.hdodenhof.circleimageview.CircleImageView;

/**
 * @author Brian Salvattore
 */
public class ChatHolder extends RecyclerView.ViewHolder {

    public static ChatHolder initLeft(ViewGroup parent){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_left, parent, false);

        return new ChatHolder(view);
    }

    public static ChatHolder initRight(ViewGroup parent){

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_right, parent, false);

        return new ChatHolder(view);
    }

    @BindView(R.id.message)
    protected TextView message;

    @BindView(R.id.profile)
    protected CircleImageView profile;

    @BindColor(R.color.colorPrimary)
    protected int primary;

    public ChatHolder(View itemView) {
        super(itemView);

        ButterKnife.bind(this, itemView);
    }

    public void setData(MessageEntity entity){

        message.setText(entity.getMessage());

        ImageLoader
                .getInstance()
                .displayImage(entity.getEntity().getPhoto(), profile, DisplayListener.optionsImageProfile, new DisplayListener());


        if(entity.getEntity().getPhoto().isEmpty()){

            TextDrawable textDrawable = TextDrawable.builder()
                    .beginConfig()
                        .textColor(primary)
                        .fontSize(30)
                        .bold()
                        .width(60)
                        .height(60)
                        .toUpperCase()
                    .endConfig()
                    .buildRound(entity.getEntity().getName().substring(0, 1), Color.WHITE);

            profile.setImageDrawable(textDrawable);
        }

    }
}
