package com.indieapps.nutriplus.activities;

import com.indieapps.nutriplus.Paciente;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

public class AtualizaPacienteActivity extends AddPacienteActivity
{
	//Paciente paciente;
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
			paciente = (Paciente)it.getSerializableExtra("obj");
			posicao = it.getIntExtra("posicao", 0);
			
			if(paciente == null)
			{
				Log.d("Debug", "Nao existe paciente");
				return;
			}
			
			Log.d("Debug", "Referencia depois " + paciente);
			
			textNome.setText(paciente.getNome());
			textIdade.setText("" + paciente.getIdade());
			spSexo.setSelection(paciente.getSexo());
			textPeso.setText("" + paciente.getPeso());
			textAltura.setText("" + paciente.getAltura());
			textImc.setText("" + paciente.getImc());
			spCond.setSelection(paciente.getCondFisica());
			textNee.setText("" + paciente.getNee());
		}
	}
	
	@Override
	public void clickAddPaciente() 
	{
		if(textNome.getText().toString().equals("") || textIdade.getText().toString().equals("") 
			|| textAltura.getText().toString().equals("") || textPeso.getText().toString().equals(""))
		{
			Toast.makeText(this, "Preencha todos os campos.", Toast.LENGTH_SHORT).show();
			return;
		}
		
		Intent it = new Intent(this, ListaTacoActivity.class);
		it.putExtra("obj", paciente);
		it.putExtra("posicao", posicao);
		
		setResult(Activity.RESULT_OK, it);
		
		finish();
	}
}
