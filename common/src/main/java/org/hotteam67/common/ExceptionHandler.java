package org.hotteam67.common;



import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

class ErrorDisplay {

    public static void showError(Context context, Thread thread, Throwable e) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(context);

        alertDialogBuilder.setTitle("Error in [" + thread.getName() + "]");
        alertDialogBuilder.setMessage(e.getMessage());

        alertDialogBuilder.setPositiveButton("OK", null);
        alertDialogBuilder.show();
    }

}

public class ExceptionHandler implements Thread.UncaughtExceptionHandler {

    private final Activity context;

    public ExceptionHandler(Activity context) {
        this.context = context;
    }

    @Override
    public void uncaughtException(Thread thread, Throwable throwable) {

        ErrorDisplay.showError(context, thread, throwable);

        Constants.Log("Attempting to display error dialog");

        Intent intent = new Intent(context, ErrorDisplay.class);
        intent.putExtra("error", "error");
        context.startActivity(intent);
        Toast.makeText(context, "Error: " + throwable.getMessage(), Toast.LENGTH_LONG).show();
        context.finish();


        android.os.Process.killProcess(android.os.Process.myPid());
        System.exit(10);

    }

}
