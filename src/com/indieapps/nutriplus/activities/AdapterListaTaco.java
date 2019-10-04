package com.indieapps.nutriplus.activities;

import java.util.ArrayList;

import com.indieapps.nutriplus.AlimentoTaco;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class AdapterListaTaco extends BaseAdapter
{
	//Ponteiro pra lista
	ArrayList<AlimentoTaco> listaAlimentos;
	
	LayoutInflater inf;
	
	public AdapterListaTaco(Context ct, ArrayList<AlimentoTaco> lista) 
	{
		inf = LayoutInflater.from(ct);
		listaAlimentos = lista;
	}
		
	@Override
	public int getCount() 
	{
		return listaAlimentos.size();
	}

	@Override
	public Object getItem(int arg0)
	{
		return listaAlimentos.get(arg0);
	}

	@Override
	public long getItemId(int arg0) 
	{
		return arg0;
	}

	@Override
	public View getView(int pos, View view, ViewGroup arg2) 
	{
		AlimentoTaco p = listaAlimentos.get(pos);
		View lay;
		
		if(view == null)
		{
			lay = inf.inflate(com.indieapps.nutriplus.R.layout.list_item_taco, null);
		}
		else
		{
			lay = view;
		}
		
		//Ajusta os valores
		TextView txtNome = (TextView)lay.findViewById(com.indieapps.nutriplus.R.id.txtNomeAlimento);
		txtNome.setText(p.getNome());
		
		//Log.d("Debug layout", "nome = " + txtNome.getText());
		
		return lay;
	}

}
