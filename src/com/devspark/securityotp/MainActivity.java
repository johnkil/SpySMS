package com.devspark.securityotp;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * Launch activity
 * @author johnkil
 *
 */
public class MainActivity extends Activity {
	
	private static final int ID_LENGTH = 8;
	
	private TextView idTxt;
	private Button generateBtn;
	
	private PreferenceHelper mPreferenceHelper;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main);
		
		idTxt = (TextView) findViewById(R.id.id_txt);
		generateBtn = (Button) findViewById(R.id.generate_btn);
		
		mPreferenceHelper = new PreferenceHelper(this);
		
		String mId = mPreferenceHelper.getId();
		if (mId == null) {
			generateId();
		}
		/*
		else {
			idTxt.setText(mId);
		}
		*/
		generateBtn.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				generateId();
			}
		});
	}
	
	/**
	 * Generate new id.
	 */
	private void generateId() {
		String[] maskArray = getResources().getStringArray(R.array.masks);
		int nextMaskIndex = mPreferenceHelper.getMaskIndex() + 1;
		if (nextMaskIndex >= maskArray.length) {
			nextMaskIndex = 0;
		}
		String mask = maskArray[nextMaskIndex];
		
		StringBuilder idBuilder = new StringBuilder();
		for (int i = 0; i < ID_LENGTH; i++) {
			if (mask.charAt(i) == '*') {
				idBuilder.append((int) (Math.random()*10));
			} else {
				idBuilder.append(mask.charAt(i));
			}
		}
		
		
		/*
		String fakeId = idBuilder.toString();
		// выставим 3-е число всегда 4 и 7-е 9
		idBuilder.replace(2, 3, "4");
		idBuilder.replace(6, 7, "9");
		*/
		
		String id = idBuilder.toString();
		mPreferenceHelper.saveId(id);
		mPreferenceHelper.saveMaskIndex(nextMaskIndex);
		
		idTxt.setText(id);
	}

}
