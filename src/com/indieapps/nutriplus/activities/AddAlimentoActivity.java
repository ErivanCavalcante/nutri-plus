package com.indieapps.nutriplus.activities;
import com.indieapps.nutriplus.R;
import com.indieapps.nutriplus.ValidadorEditText;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

public class AddAlimentoActivity extends Activity
{
	EditText textNome;
	EditText textKcal;
	EditText textProteina;
	EditText textLipidios;
	EditText textCarboidrato;
	EditText edtCalcio;
	EditText edtFerro;
	EditText edtSodio;
	EditText edtPotassio;
	EditText edtZinco;
	EditText edtVitC;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.add_alimento);
		
		textNome = (EditText)findViewById(R.id.textNomeAlim);
		textKcal = (EditText)findViewById(R.id.textKcal);
		textProteina = (EditText)findViewById(R.id.textProteinaAlim);
		textLipidios = (EditText)findViewById(R.id.textLipidioAlim);
		textCarboidrato = (EditText)findViewById(R.id.textCarbAlim);
		edtCalcio = (EditText)findViewById(R.id.edtCalcio);
		edtFerro = (EditText)findViewById(R.id.edtFerro);
		edtSodio = (EditText)findViewById(R.id.edtSodio);
		edtPotassio = (EditText)findViewById(R.id.edtPotassio);
		edtZinco = (EditText)findViewById(R.id.edtZinco);
		edtVitC = (EditText)findViewById(R.id.edtVitaminaC);
		
		validaEditText();
	}
	
    private void validaEditText() 
    {
		textNome.addTextChangedListener(new ValidadorEditText() 
		{
			@Override
			public void validar(String texto)
			{
				if(texto.length() > 30)
				{
					textNome.setError("O nome deve ter no maximo 30 caracteres.");
					return;
				}
			}
		});
	}

	@Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inf = getMenuInflater();
        inf.inflate( R.menu.menu_taco_add, menu );

        return true;
    }

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		if(item.getItemId() == R.id.menuTacoAdd)
		{
			clickAddAlimento();
		}
			
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	public void onBackPressed() 
	{
		setResult(RESULT_CANCELED);
		finish();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle save) 
	{
		super.onSaveInstanceState(save);
		
		save.putString("nome", textNome.getText().toString());
		save.putString("kcal", textKcal.getText().toString());
		save.putString("cho", textCarboidrato.getText().toString());
		save.putString("lip", textLipidios.getText().toString());
		save.putString("ptn", textProteina.getText().toString());
		save.putString("ca", textProteina.getText().toString());
		save.putString("fe", textProteina.getText().toString());
		save.putString("na", textProteina.getText().toString());
		save.putString("k", textProteina.getText().toString());
		save.putString("zn", textProteina.getText().toString());
		save.putString("vitC", textProteina.getText().toString());
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle save) 
	{
		super.onRestoreInstanceState(save);
		
		textNome.setText(save.getString("nome"));
		textKcal.setText(save.getString("kcal"));
		textCarboidrato.setText(save.getString("cho"));
		textLipidios.setText(save.getString("lip"));
		textProteina.setText(save.getString("ptn"));
		edtCalcio.setText(save.getString("ca"));
		edtFerro.setText(save.getString("fe"));
		edtSodio.setText(save.getString("na"));
		edtPotassio.setText(save.getString("k"));
		edtZinco.setText(save.getString("zn"));
		edtVitC.setText(save.getString("vitC"));
	}
	
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
