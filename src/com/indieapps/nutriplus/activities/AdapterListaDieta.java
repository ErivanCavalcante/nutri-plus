package com.indieapps.nutriplus.activities;

import java.util.ArrayList;

import com.indieapps.nutriplus.AlimentoCardapio;
import com.indieapps.nutriplus.R;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;



public class AdapterListaDieta extends BaseExpandableListAdapter
{
	LayoutInflater inf;
	ArrayList< ArrayList<AlimentoCardapio> > cardapio;
	String []chaves = {"Cafe da Manha", "Lanche da Manha", "Almoço", "Lanche da Tarde", "Jantar", "Lanche da Noite"};
	
	public AdapterListaDieta(Context ct, ArrayList< ArrayList<AlimentoCardapio> > cardapio) 
	{
		inf = LayoutInflater.from(ct);
		this.cardapio = cardapio;
	}
	
	@Override
	public Object getChild(int groupPos, int childPos) 
	{
		return cardapio.get(groupPos).get(childPos);
	}

	@Override
	public long getChildId(int groupPos, int childPos) 
	{
		return childPos;
	}

	@Override
	public View getChildView(int groupPos, int childPos, boolean lastCuild, View view, ViewGroup arg4) 
	{
		AlimentoCardapio a = cardapio.get(groupPos).get(childPos);
		View v;
		
		if(view == null)
		{
			v = inf.inflate(R.layout.list_item_dieta, null);
		}
		else
		{
			v = view;
		}
		
		TextView nome = (TextView)v.findViewById(R.id.txtNomeAlimento);
		TextView qtd = (TextView)v.findViewById(R.id.txtQtdAlimento);
		
		nome.setText(a.getAlimento().getNome());
		qtd.setText("" + a.getQtd());
		
		return v;
	}

	@Override
	public int getChildrenCount(int groupPos) 
	{
		return cardapio.get(groupPos).size();
	}

	@Override
	public Object getGroup(int groupPos) 
	{
		return chaves[groupPos];
	}

	@Override
	public int getGroupCount() 
	{
		return chaves.length;
	}

	@Override
	public long getGroupId(int pos) 
	{
		return pos;
	}

	@Override
	public View getGroupView(int groupPos, boolean isExpanded, View view, ViewGroup arg3) 
	{
		try
		{
			if(view == null)
			{
				view = inf.inflate(R.layout.list_group_dieta, null);
			}
			
			TextView texto = (TextView)view.findViewById(R.id.txtNome);
			
			texto.setText(chaves[groupPos]);
					
		}
		catch(NullPointerException ex)
		{
			ex.printStackTrace();
		}
		
		return view;
	}

	@Override
	public boolean hasStableIds() 
	{
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isChildSelectable(int arg0, int arg1) 
	{
		// TODO Auto-generated method stub
		return true;
	}
	
}