package practicaltest01var03.eim.systems.cs.pub.ro.practicaltest01var03;

import android.content.Context;
import android.content.Intent;
import android.util.Log;

/**
 * Created by student on 30.03.2018.
 */

public class ProcessingThread extends Thread {

    private Context context = null;
    private boolean isRunning = true;
    private String data = null;

    public ProcessingThread(Context context, String data) {
        this.context = context;
        this.data = data;
    }

    @Override
    public void run() {
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has started!");
        while (isRunning) {
            sendMessage();
            sleep();
        }
        Log.d(Constants.PROCESSING_THREAD_TAG, "Thread has stopped!");
    }

    private void sendMessage() {
        Intent intent = new Intent();
        intent.setAction(Constants.actionType);
        intent.putExtra(Constants.BROADCAST_RECEIVER_EXTRA,
                data);
        context.sendBroadcast(intent);
    }

    private void sleep() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException interruptedException) {
            interruptedException.printStackTrace();
        }
    }

    public void stopThread() {
        isRunning = false;
    }
}