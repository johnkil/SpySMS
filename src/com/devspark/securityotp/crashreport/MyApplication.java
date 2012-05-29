package com.devspark.securityotp.crashreport;

import org.acra.ACRA;
import org.acra.ErrorReporter;
import org.acra.annotation.ReportsCrashes;

import android.app.Application;

@ReportsCrashes(formKey = "dG1oUWkyNDZwa014OVIzVFAyOVd3QVE6MQ") 
public class MyApplication extends Application {
	
	@Override
    public void onCreate() {
        // The following line triggers the initialization of ACRA
		
		ACRA.init(this);
        ErrorReporter.getInstance().checkReportsOnApplicationStart();
        
        super.onCreate();
    }
	
}