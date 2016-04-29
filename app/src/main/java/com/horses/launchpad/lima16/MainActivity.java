package com.horses.launchpad.lima16;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;

import com.firebase.client.ChildEventListener;
import com.firebase.client.DataSnapshot;
import com.firebase.client.Firebase;
import com.firebase.client.FirebaseError;
import com.firebase.client.ValueEventListener;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import butterknife.BindColor;
import butterknife.BindView;
import butterknife.ButterKnife;
import fr.castorflex.android.smoothprogressbar.SmoothProgressBar;
import io.paperdb.Paper;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.toolbar)
    protected Toolbar toolbar;

    @BindView(R.id.recycler)
    protected RecyclerView recycler;

    @BindView(R.id.message)
    protected EditText message;

    @BindView(R.id.progress)
    protected SmoothProgressBar progress;

    @BindView(R.id.send)
    protected ImageView send;

    @BindColor(R.color.colorPrimary)
    protected int primary;

    private List<MessageEntity> messages;
    private ChatAdapter adapter;
    private PersonEntity personEntity;
    private Firebase firebase;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ButterKnife.bind(this);

        /** Firebase reference */
        firebase = new Firebase("https://launchpad-lima.firebaseio.com/").child("room").child("horses");

        setSupportActionBar(toolbar);

        messages = new ArrayList<>();

        personEntity = Paper.book().read("person");

        adapter = new ChatAdapter(messages, personEntity);

        recycler.setLayoutManager(new LinearLayoutManager(this));
        recycler.setAdapter(adapter);

        send.getDrawable().setColorFilter(Color.GRAY, PorterDuff.Mode.SRC_ATOP);
        send.setOnClickListener(null);
        message.setEnabled(false);

        firebase.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                progress.setVisibility(View.GONE);
                send.getDrawable().setColorFilter(primary, PorterDuff.Mode.SRC_ATOP);
                send.setOnClickListener(onClickListener);
                message.setEnabled(true);
            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });

        /** Puts item in list */
        firebase.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(DataSnapshot dataSnapshot, String s) {

                MessageEntity messageEntity = dataSnapshot.getValue(MessageEntity.class);

                messages.add(messageEntity);

                recycler.scrollToPosition(messages.size() - 1);
                adapter.notifyItemInserted(messages.size() - 1);
            }

            @Override
            public void onChildChanged(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onChildRemoved(DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(DataSnapshot dataSnapshot, String s) {

            }

            @Override
            public void onCancelled(FirebaseError firebaseError) {

            }
        });
    }

    private View.OnClickListener onClickListener = v -> {

        if(message.getText().toString().isEmpty()) return;

        Calendar calendar = Calendar.getInstance();

        MessageEntity messageEntity = new MessageEntity();
        messageEntity.setEntity(personEntity);
        messageEntity.setDate(calendar.getTime());
        messageEntity.setMessage(message.getText().toString());

        /** Send item to firebase */
        firebase.push().setValue(messageEntity);
        message.setText("");
    };
}
