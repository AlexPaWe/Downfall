package de.weidemeier.alexander.downfall;

import android.app.Activity;
import android.widget.Toast;

/**
 * Created by thebige on 12.01.17.
 */

public class Messanger {


    /**
     * Displays the message in a short displayed toast on the screen.
     */
    public void displayMessage(Activity activity) {
        Toast.makeText(activity, "You did it!", Toast.LENGTH_SHORT).show();
    }
}
