package de.weidemeier.alexander.downfall;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.SurfaceHolder;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    private SnowDisplay snowDisplay;

    private SnowThread snowThread;

    private TextView informationView;

    private Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        snowDisplay = (SnowDisplay) findViewById(R.id.display);
        SurfaceHolder holder = snowDisplay.getHolder();

        informationView = (TextView) findViewById(R.id.textView_display_information);

        snowThread = new SnowThread(snowDisplay.getHolder(), this);

        button = (Button) findViewById(R.id.button_start_pause);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatementConcurrentModificationException
        if (id == R.id.action_settings) {
            return true;
        }

        if (id == R.id.whatsapp_share) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


    public void onButtonClicked(View view) {

        // shows information about the display on the information textView.
        SurfaceHolder holder = snowDisplay.getHolder();
        String infTxt = holder.getSurfaceFrame().width() + ":" + holder.getSurfaceFrame().height();
        informationView.setText(infTxt);

        if (!snowThread.isStarted()) {
            snowThread.start();
            button.setText("Pause");
        } else if (snowThread.isPaused()) {
            snowThread.onResume();
            button.setText("Pause");
        } else {
            snowThread.onPause();
            button.setText("Resume");
        }
    }


    @Override
    public void onPause() {
        super.onPause();
        if (snowThread.isStarted()) {
            snowThread.onPause();
            button.setText("Resume");
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        snowThread.kill();
        try {
            snowThread.join();
            Log.println(Log.INFO, "continuity", "Thread was successfully joined :)");
        } catch (InterruptedException e) {
            Log.w("Exception", "The InterruptedException was thrown while trying to join the snow" +
                    "thread!");
        }
    }
}
