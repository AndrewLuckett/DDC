package andrew.DDC.front;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    /**
    private SharedPreferences prefs;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        prefs = this.getSharedPreferences("scoredata", Context.MODE_PRIVATE);


        refresh();

        final Intent i = new Intent(MainActivity.this, Game.class);
        Button btn = findViewById(R.id.goconvert);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("id",0);
                startActivity(i);
                finish();
            }
        });

        btn = findViewById(R.id.goadd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                i.putExtra("id",1);
                startActivity(i);
                finish();
            }
        });

        btn = findViewById(R.id.resetconvert);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset(0);
            }
        });

        btn = findViewById(R.id.resetadd);
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                reset(1);
            }
        });

    }

    public void refresh() {
        int highscore = prefs.getInt("highscore_convert", 0); //0 is the default value

        TextView scorebox = findViewById(R.id.highscoreconvert);
        scorebox.setText("High Score: " + highscore + ".");

        highscore = prefs.getInt("highscore_add", 0); //0 is the default value

        scorebox = findViewById(R.id.highscoreadd);
        scorebox.setText("High Score: " + highscore + ".");
    }

    public void reset(final int game){
        final AlertDialog.Builder dlgAlert  = new AlertDialog.Builder(this);
        dlgAlert.setMessage("Are you sure you want to reset your score?");
        dlgAlert.setTitle("Reset");
        dlgAlert.setPositiveButton("Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        SharedPreferences.Editor editor = prefs.edit();
                        if (game == 0) {
                            editor.putInt("highscore_convert", 0);
                        }else if(game == 1){
                            editor.putInt("highscore_add", 0);
                        }
                        editor.apply();
                        refresh();
                    }
                });

        dlgAlert.setNegativeButton("No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });

        dlgAlert.setCancelable(true);
        dlgAlert.create().show();


        refresh();
    }
**/
}