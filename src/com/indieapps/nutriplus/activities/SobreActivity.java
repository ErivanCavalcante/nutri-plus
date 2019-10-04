package com.indieapps.nutriplus.activities;

import com.indieapps.nutriplus.R;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

public class SobreActivity extends Activity
{
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.view_sobre);
	}
	
	@Override
	public void onBackPressed() 
	{
		Intent it = new Intent(this, ListaPacienteActivity.class);
		startActivity(it);
		finish();
		super.onBackPressed();
	}
	
	
}
