package com.indieapps.nutriplus.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

public class AtualizaAlimentoActivity extends AddAlimentoActivity
{
	int posicao = 0;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
	
		Intent it = getIntent();
		
		//se for uma tela pra atualizar um alimento
		//usa essas variaveis
		if(it != null)
		{
			posicao = it.getIntExtra("posicao", 0);
				
			textNome.setText(it.getStringExtra("nome"));
			textKcal.setText("" + it.getFloatExtra("kcal", 0));
			textProteina.setText("" + it.getFloatExtra("prot", 0));
			textLipidios.setText("" + it.getFloatExtra("lip", 0));
			textCarboidrato.setText("" + it.getFloatExtra("carbo", 0));
			edtCalcio.setText("" + it.getFloatExtra("calcio", 0));
			edtFerro.setText("" + it.getFloatExtra("ferro", 0));
			edtSodio.setText("" + it.getFloatExtra("sodio", 0));
			edtPotassio.setText("" + it.getFloatExtra("potassio", 0));
			edtZinco.setText("" + it.getFloatExtra("zinco", 0));
			edtVitC.setText("" + it.getFloatExtra("vitC", 0));
			//Log.d("Debug", "posicao = " + posicao);
		}
	}
	
	@Override
	public void clickAddAlimento() 
	{
		//Testa se pode add
		if(textNome.getText().length() == 0 || textNome.getText().length() > 30 || textProteina.getText().length() == 0 
				|| textLipidios.getText().length() == 0 || textCarboidrato.getText().length() == 0
				|| textKcal.getText().length() == 0 || edtCalcio.getText().length() == 0 || edtFerro.getText().length() == 0 
				|| edtSodio.getText().length() == 0 || edtPotassio.getText().length() == 0 
				|| edtZinco.getText().length() == 0 || edtVitC.getText().length() == 0)
		{
			Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		//Adiciona o alimento
		Bundle a = new Bundle();
		
		//Se for atualização passa pra proxima activity
		a.putInt("posicao", posicao);
		
		a.putString("nome", textNome.getText().toString());
		a.putFloat("kcal", Float.parseFloat( textKcal.getText().toString() ));
		a.putFloat("prot", Float.parseFloat( textProteina.getText().toString() ));
		a.putFloat("lip", Float.parseFloat( textLipidios.getText().toString() ));
		a.putFloat("carbo", Float.parseFloat( textCarboidrato.getText().toString() ));
		a.putFloat("calcio", Float.parseFloat( edtCalcio.getText().toString() ));
		a.putFloat("ferro", Float.parseFloat( edtFerro.getText().toString() ));
		a.putFloat("sodio", Float.parseFloat( edtSodio.getText().toString() ));
		a.putFloat("potassio", Float.parseFloat( edtPotassio.getText().toString() ));
		a.putFloat("zinco", Float.parseFloat( edtZinco.getText().toString() ));
		a.putFloat("vitC", Float.parseFloat( edtVitC.getText().toString() ));
		
		Intent it = new Intent(this, ListaTacoActivity.class);
		it.putExtras(a);
		
		setResult(Activity.RESULT_OK, it);
	
		finish();
	}
}
