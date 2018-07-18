package app;

import android.annotation.SuppressLint;
import android.app.Application;
import android.util.Log;

import com.leadinsource.prudentcook.BuildConfig;

import timber.log.Timber;

public class PrudentCookApp extends Application {
    @Override
    public void onCreate() {
        super.onCreate();

        if(BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new CrashReportingTree());
        }
    }

    private static class CrashReportingTree extends Timber.Tree {

        @SuppressLint("LogNotTimber")
        @Override
        protected void log(int priority, String tag, String message,
                           Throwable t)  {
            if(priority == Log.VERBOSE || priority == Log.DEBUG) {
                return;
            }
            // can be changed to some crash reporting tool later
            Log.e(tag, message);
        }
    }
}
