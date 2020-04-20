package andrew.DDC.game.front;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import andrew.DDC.R;

public class TdOverActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_td_over);

        Bundle b = getIntent().getExtras();

        TextView t = findViewById(R.id.gameover);
        t.setText(String.format(getString(R.string.over),b.getInt("score"),b.getInt("wave")));


        findViewById(R.id.okback).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i = new Intent(TdOverActivity.this, TdMenuActivity.class);
                startActivity(i);
                finish();
            }
        });
    }
}
