package com.arthursaveliev.messageview.sample;

import android.os.Bundle;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.transition.TransitionManager;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;
import com.arthursaveliev.messageview.MessageView;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

  LinearLayout linearLayout;
  MessageView messageView;


  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    TextView btnShow = findViewById(R.id.textView2);
    TextView btnHide = findViewById(R.id.textView5);
    linearLayout = findViewById(R.id.parent);
    messageView = findViewById(R.id.messageView);


    final List<View> messages = new ArrayList<>();
    for (int i = 0; i < 5; i++) {
      View v = LayoutInflater.from(MainActivity.this)
          .inflate(R.layout.layout_error, null, false);
      TextView txtError = v.findViewById(R.id.textView);
      txtError.setText("This is error " + i);
      messages.add(v);
    }
    messageView.setMessages(messages);

    btnShow.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        TransitionManager.beginDelayedTransition(linearLayout);
        messageView.show();
      }
    });
    btnHide.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {
        TransitionManager.beginDelayedTransition(linearLayout);
        messageView.hide();
      }
    });
  }

}
