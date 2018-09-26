package com.arthursaveliev.messageview.sample;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import com.arthursaveliev.messageview.MessageBar;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{


  private MessageBar messageBar;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setContentView(R.layout.activity_main);

    Button button = findViewById(R.id.button);

    button.setOnClickListener(new View.OnClickListener() {
      @Override public void onClick(View view) {

        List<View> messages = new ArrayList<>();
        for (int i = 0; i < 5; i++) {
          View v = LayoutInflater.from(MainActivity.this).inflate(R.layout.layout_error, null, false);
          TextView txtError = v.findViewById(R.id.textView);
          txtError.setText("This is error " + i);
          txtError.setOnClickListener(MainActivity.this);
          messages.add(v);
        }

         messageBar = MessageBar.build(MainActivity.this)
            .addMessages(messages)
            .setBackgroundColor(getResources().getColor(R.color.default_action_color))
            .create();
        messageBar.show();
      }
    });
  }

  @Override public void onClick(View view) {
    if (messageBar != null) {
      MessageBar.dismiss(MainActivity.this);
    }
  }
}
