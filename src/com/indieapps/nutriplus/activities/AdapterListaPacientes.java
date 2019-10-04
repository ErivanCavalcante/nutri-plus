package com.indieapps.nutriplus.activities;

import java.util.ArrayList;

import com.indieapps.nutriplus.Paciente;

import android.R;
import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class AdapterListaPacientes extends BaseAdapter 
{
	//Ponteiro pra lista
	ArrayList<Paciente> listaPaciente;
	
	LayoutInflater inf;
	
	public AdapterListaPacientes(Context ct, ArrayList<Paciente> lista) 
	{
		inf = LayoutInflater.from(ct);
		listaPaciente = lista;
	}
	
	@Override
	public int getCount() 
	{
		return listaPaciente.size();
	}

	@Override
	public Object getItem(int arg0) 
	{
		return listaPaciente.get(arg0);
	}

	@Override
	public long getItemId(int arg0) 
	{
		return arg0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup arg2) 
	{
		Paciente p = listaPaciente.get(pos);
		View lay;
		
		if(view == null)
		{
			lay = inf.inflate(com.indieapps.nutriplus.R.layout.list_item_paciente, null);
		}
		else
		{
			lay = view;
		}
		
		//Ajusta os valores
		ImageView img = (ImageView)lay.findViewById(com.indieapps.nutriplus.R.id.imgSexo);
		//Coloca a imagem d acordo com o sexo
		if(p.getSexo() == Paciente.SEXO_MASCULINO)
		{
			img.setImageResource(com.indieapps.nutriplus.R.drawable.boy_paciente);
		}
		else
		{
			img.setImageResource(com.indieapps.nutriplus.R.drawable.girl_paciente);
		}
		
		TextView txtNome = (TextView)lay.findViewById(com.indieapps.nutriplus.R.id.txtNomeAlimento);
		txtNome.setText(p.getNome());
		
		TextView txtIdade = (TextView)lay.findViewById(com.indieapps.nutriplus.R.id.txtIdade);
		txtIdade.setText("" + p.getIdade());
		
		return lay;
	}

}
