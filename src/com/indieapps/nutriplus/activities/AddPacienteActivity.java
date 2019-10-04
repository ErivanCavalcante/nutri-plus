package com.indieapps.nutriplus.activities;
import android.app.*;
import android.content.Intent;
import android.os.*;
//import android.util.Log;
import android.widget.*;
import android.widget.AdapterView.OnItemSelectedListener;

import java.util.*;

import com.indieapps.nutriplus.Paciente;
import com.indieapps.nutriplus.R;
import com.indieapps.nutriplus.ValidadorEditText;
import com.indieapps.nutriplus.models.ModelPaciente;

import android.view.*;

public class AddPacienteActivity extends Activity implements Observer
{
	EditText textNome, textIdade, textPeso, textAltura, textImc, textNee;
	Spinner spSexo, spCond;
	
	protected ModelPaciente modelPaciente;
	//Paciente atual
	protected Paciente paciente;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		// TODO: Implement this method
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.add_paciente);
		
		spSexo = (Spinner)findViewById(R.id.spSexo);
		spCond = (Spinner)findViewById(R.id.spCondFisica);
		textNome = (EditText)findViewById(R.id.textNomePac);
		textIdade = (EditText)findViewById(R.id.textIdade);
		textPeso = (EditText)findViewById(R.id.textPeso);
		textAltura = (EditText)findViewById(R.id.textAltura);
		textImc = (EditText)findViewById(R.id.textImc);
		textNee = (EditText)findViewById(R.id.textNee);
		
		modelPaciente = new ModelPaciente();
		
		paciente = new Paciente();
		
		modelPaciente.addObserver(this);
		
		ArrayList<String> str = new ArrayList<String>();
		str.add("Masculino");
		str.add("Feminino");
		
		ArrayList<String> strCond = new ArrayList<String>();
		strCond.add("Sedentario");
		strCond.add("Pouco Ativo");
		strCond.add("Ativo");
		strCond.add("Muito Ativo");
		
		ArrayAdapter<String> adp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, str);
		spSexo.setAdapter(adp);
		
		ArrayAdapter<String> adpCond = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, strCond);
		spCond.setAdapter(adpCond);
		
		spCond.setOnItemSelectedListener(new OnItemSelectedListener() 
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) 
			{
				paciente.setCondFisica(pos);
				
				modelPaciente.calculaNee(paciente);
				
				//Log.d("Debug", "Item selecionado spCond!!");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		spSexo.setOnItemSelectedListener(new OnItemSelectedListener() 
		{

			@Override
			public void onItemSelected(AdapterView<?> arg0, View arg1, int pos, long id) 
			{
				paciente.setSexo(pos);
				
				modelPaciente.calculaNee(paciente);
				
				//Log.d("Debug", "Item selecionado spSexo!!");
			}

			@Override
			public void onNothingSelected(AdapterView<?> arg0) {}
		});
		
		validarEditText();		
	}
	
	@Override
	public boolean onCreateOptionsMenu(Menu menu) 
	{
		MenuInflater inf = getMenuInflater();
        inf.inflate( R.menu.menu_add_paciente, menu );

        return true;
	}
	
	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		if(item.getItemId() == R.id.menuAddPaciente)
		{
			clickAddPaciente();
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
		save.putInt("sexo", spSexo.getSelectedItemPosition());
		save.putInt("cond", spCond.getSelectedItemPosition());
		save.putString("idade", textIdade.getText().toString());
		save.putString("peso", textPeso.getText().toString());
		save.putString("altura", textAltura.getText().toString());
		save.putString("imc", textImc.getText().toString());
		save.putString("nee", textNee.getText().toString());
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle save) 
	{
		super.onRestoreInstanceState(save);
		
		textNome.setText(save.getString("nome"));
		spSexo.setSelection(save.getInt("sexo"));
		spCond.setSelection(save.getInt("cond"));
		textIdade.setText(save.getString("idade"));
		textPeso.setText(save.getString("peso"));
		textAltura.setText(save.getString("altura"));
		textImc.setText(save.getString("imc"));
		textNee.setText(save.getString("nee"));
	}
	
	public void clickAddPaciente()
	{
		//Testa se pode add
		if(textNome.getText().toString().equals("") || textIdade.getText().toString().equals("") 
			|| textAltura.getText().toString().equals("") || textPeso.getText().toString().equals(""))
		{
			Toast.makeText(getApplicationContext(), "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent it = new Intent(getApplicationContext(), ListaTacoActivity.class);
		it.putExtra("obj", paciente);
		
		setResult(Activity.RESULT_OK, it);
		
		finish();
	}
	
	@Override
	public void update(Observable observable, Object data) 
	{
		String str = (String)data;
		
		if(str.equals("nee"))
		{
			textNee.setText("" + paciente.getNee());
		}
		else if(str.equals("imc"))
		{
			textImc.setText("" + paciente.getImc());
		}
	}
	
	
	
	
	
	
	
	public void validarEditText()
	{
		textNome.addTextChangedListener(new ValidadorEditText() 
		{
			@Override
			public void validar(String texto)
			{
				if(texto.length() > 30)
				{
					textNome.setError("O nome deve conter no maximo 30 caracteres.");
					return;
				}
				
				paciente.setNome(texto);
			}
			
		});
		textIdade.addTextChangedListener(new ValidadorEditText() 
		{
			@Override
			public void validar(String texto)
			{
				int valor = Integer.parseInt(texto);
				
				if(valor < 3)
					valor = 3;
				
				//textIdade.setText("" + valor);
				
				paciente.setIdade(valor);
				
				modelPaciente.calculaNee(paciente);
			}
			
		});
		textPeso.addTextChangedListener(new ValidadorEditText() 
		{
			@Override
			public void validar(String texto)
			{
				float valor = Float.parseFloat(texto);
				
				if(valor < 0)
					valor = 0;
				
				paciente.setPeso(valor);
				
				modelPaciente.calculaImc(paciente);
				
				modelPaciente.calculaNee(paciente);
			}
			
		});
		textAltura.addTextChangedListener(new ValidadorEditText() 
		{
			@Override
			public void validar(String texto)
			{
				float valor = Float.parseFloat(texto);
				
				if(valor < 0)
					valor = 0;
				
				paciente.setAltura(valor);
				
				modelPaciente.calculaImc(paciente);
				
				modelPaciente.calculaNee(paciente);
			}
			
		});
	}
}
