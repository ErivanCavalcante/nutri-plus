package com.indieapps.nutriplus.activities;
import android.app.*;
import android.os.*;
import android.widget.*;
import android.widget.AdapterView.OnItemClickListener;

import java.util.*;

import com.indieapps.nutriplus.AlimentoTaco;
import com.indieapps.nutriplus.R;
import com.indieapps.nutriplus.models.ModelAlimentoTaco;

import android.view.*;
import android.content.*;

public class ListaTacoActivity extends Activity implements Observer
{
	//Intent result
	static final int ADD_OBJETO = 1;
	static final int UPDATE_OBJETO = 2;
	
	ModelAlimentoTaco modelTaco;
	
	EditText textProcurar;
	ListView listTaco;
	AdapterListaTaco adpTaco;
	
	//Procurar alimento na taco e retornar
	boolean procura_alimento = false;
	
	@Override
	protected void onCreate(Bundle savedInstanceState)
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.lista_alimentos_taco);
		
		modelTaco = new ModelAlimentoTaco();
		
		//Seta o mvc
		modelTaco.addObserver(this);
		
		//Pega a lista
		listTaco = (ListView)findViewById(R.id.listAlimentosTaco);
		textProcurar = (EditText)findViewById(R.id.textProcurar);
		
		
		//cira o adpter
		adpTaco = new AdapterListaTaco(this, modelTaco.getListaAlimentos());
		
		listTaco.setAdapter(adpTaco);
		
		//Testa se eh pra retornar algo
		Intent it = getIntent();
		if(it != null)
		{
			procura_alimento = it.getBooleanExtra("pega_alimento", false);
		}
		
		modelTaco.openDataBase(getApplicationContext());
		
		//Click na lista
		listTaco.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int posicao, long id) 
			{
				//Se eh pra pegar um alimento
				if(procura_alimento)
				{
					mandaAlimentoCardapio(posicao);
					
					return;
				}
				
				FragmentManager fm = getFragmentManager();
				PopupItemTaco pop = new PopupItemTaco(modelTaco.getAlimento(posicao), posicao);
				pop.show(fm, "pop");
			}
		});
		
		
		modelTaco.carregaListaBanco();
	}
	
	protected void mandaAlimentoCardapio(int posicao) 
	{
		//Pega o alimento da taco
		AlimentoTaco taco = modelTaco.getAlimento(posicao);
		
		//Cria a intent pra retornar
		Intent it = new Intent(this, ListaDietaActivity.class);
		//Coloca os dados do alimento
		it.putExtra("alim_id", taco.getId());
		it.putExtra("alim_nome", taco.getNome());
		it.putExtra("alim_cho", taco.getQtdCarboidratos());
		it.putExtra("alim_lip", taco.getQtdLipidios());
		it.putExtra("alim_ptn", taco.getQtdProteinas());
		it.putExtra("alim_kcal", taco.getQtdKcal());
		it.putExtra("alim_calcio", taco.getCalcio());
		it.putExtra("alim_ferro", taco.getFerro());
		it.putExtra("alim_sodio", taco.getSodio());
		it.putExtra("alim_potassio", taco.getPotassio());
		it.putExtra("alim_zinco", taco.getZinco());
		it.putExtra("alim_vitC", taco.getVitaminaC());
		
		//manda o resultado pra outra activity
		setResult(RESULT_OK, it);
		
		finish();
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu)
	{
		if(procura_alimento)
			return super.onCreateOptionsMenu(menu);
		
		//Infla o menu
		MenuInflater inf = getMenuInflater();
		inf.inflate(R.menu.menu_taco, menu);
		
		return true;
	}

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		if(item.getItemId() == R.id.menuListaPacientes)
		{
			Intent it = new Intent(this, ListaPacienteActivity.class);
			startActivity(it);
			finish();
		}
		else if(item.getItemId() == R.id.menuAddAlimento)
		{
			clickAddAlimento();
		}
		else if(item.getItemId() == R.id.menuSobre)
		{
			//Vai pra lista taco
			Intent it = new Intent(this, SobreActivity.class);
			startActivity(it);
			finish();
		}
		else if(item.getItemId() == R.id.menuSair)
		{
			AlertDialog.Builder b = new AlertDialog.Builder(this);
			b.setTitle("Confirmar Ação")
			.setMessage("Deseja relamente Sair?")
			.setPositiveButton("Sim", new DialogInterface.OnClickListener()
			{
				@Override
				public void onClick(DialogInterface p1, int p2)
				{
					System.exit(0);
				}
			})
			.setNegativeButton("Não", null)
			.show();
		}
		
		return super.onMenuItemSelected(featureId, item);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if(requestCode == ADD_OBJETO && resultCode == RESULT_OK)
		{
			if(data == null)
			{
				Toast.makeText(getApplicationContext(), "Erro ao adicionar alimento.", Toast.LENGTH_SHORT).show();
				return;
			}
				
				Bundle b = data.getExtras();
				AlimentoTaco a;
				a = new AlimentoTaco();
				
				a.setNome(b.getString("nome"));
				a.setQtdKcal(b.getFloat("kcal"));
				a.setQtdCarboidratos(b.getFloat("carbo"));
				a.setQtdLipidios(b.getFloat("lip"));
				a.setQtdProteinas(b.getFloat("prot"));
				a.setCalcio(b.getFloat("calcio"));
				a.setFerro(b.getFloat("ferro"));
				a.setSodio(b.getFloat("sodio"));
				a.setPotassio(b.getFloat("potassio"));
				a.setZinco(b.getFloat("zinco"));
				a.setVitaminaC(b.getFloat("vitC"));
				
				modelTaco.adicionaObjeto(a);
				
				Toast.makeText(getApplicationContext(), "Alimento adicionado com sucesso.", Toast.LENGTH_SHORT).show();
		}
		else if(requestCode == UPDATE_OBJETO && resultCode == RESULT_OK)
		{
			if(data == null)
				return;
			
			Bundle b = data.getExtras();
			AlimentoTaco a;
			
			//Erro a instancia eh reconstruida entao nao existe nada na lista
			int pos = b.getInt("posicao");
			a = modelTaco.getAlimento(pos);
			
			a.setNome(b.getString("nome"));
			a.setQtdKcal(b.getFloat("kcal"));
			a.setQtdCarboidratos(b.getFloat("carbo"));
			a.setQtdLipidios(b.getFloat("lip"));
			a.setQtdProteinas(b.getFloat("prot"));
			a.setCalcio(b.getFloat("calcio"));
			a.setFerro(b.getFloat("ferro"));
			a.setSodio(b.getFloat("sodio"));
			a.setPotassio(b.getFloat("potassio"));
			a.setZinco(b.getFloat("zinco"));
			a.setVitaminaC(b.getFloat("vitC"));
			
			if(!modelTaco.atualizaObjeto(a))
				return;
			adpTaco.notifyDataSetChanged();
		}
	}
	
	protected void onPause() 
	{
		modelTaco.closeDataBase();
		super.onPause();
	}
	
	@Override
	protected void onResume() 
	{
		modelTaco.openDataBase(getApplicationContext());
		super.onResume();
	}
	
	@Override
	protected void onDestroy() 
	{
		//modelTaco.closeDataBase();
		modelTaco.deleteObserver(this);
		super.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle save) 
	{
		super.onSaveInstanceState(save);
		
		save.putString("procurar", textProcurar.getText().toString());
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle save) 
	{
		super.onRestoreInstanceState(save);
		
		textProcurar.setText(save.getString("procurar"));
	}
	
	public void clickAddAlimento()
	{
		if(modelTaco.getListaAlimentos().size() >= 100)
    	{
    		Toast.makeText(getApplicationContext(), "Maximo de 100 alimentos na lista.", Toast.LENGTH_SHORT).show();
    		return;
    	}
		
		//Adiciona alimento
		Intent it = new Intent(this, AddAlimentoActivity.class);
		startActivityForResult(it, ADD_OBJETO);
	}
	
	@Override
	public void onBackPressed() 
	{
		setResult(RESULT_CANCELED);
		finish();
	}

	@Override
	//MVC
	public void update(Observable observable, Object data) 
	{
		String d = (String)data;
		
		if(d.equals("update_lista"))
		{
			adpTaco.notifyDataSetChanged();
			//Log.d("MEssage", "chamou a funcao");
		}
	}
	
	public void clickProcurar(View v)
	{
		//String txt = textProcurar.getText().toString();
		
		modelTaco.procuraListaBanco(textProcurar.getText().toString());
	}
	
	protected void removeAlimentoLista(AlimentoTaco alimento) 
	{
		modelTaco.removeObjeto(alimento);
	}
	
	public static class PopupItemTaco extends DialogFragment
	{
		final int EDITAR = 0;
		final int DELETAR = 1;
		
		//Alimento em questao
		AlimentoTaco alimento;
		int posicao;
		
		public PopupItemTaco(AlimentoTaco a, int posicao) 
		{
			this.posicao = posicao;
			this.alimento = a;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
			View v = inflater.inflate(R.layout.popup_menu_taco, container);
			
			getDialog().setTitle(alimento.getNome());
			
			ListView list = (ListView)v.findViewById(R.id.listMenu);
			list.setOnItemClickListener(new OnItemClickListener() 
			{
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) 
				{
					//Editar
					if(pos == EDITAR)
					{
						Intent it = new Intent(getActivity(), AtualizaAlimentoActivity.class);
						it.putExtra("posicao", posicao);
						it.putExtra("kcal", alimento.getQtdKcal());
						it.putExtra("nome", alimento.getNome());
						it.putExtra("carbo", alimento.getQtdCarboidratos());
						it.putExtra("lip", alimento.getQtdLipidios());
						it.putExtra("prot", alimento.getQtdProteinas());
						
						getActivity().startActivityForResult(it, UPDATE_OBJETO);
						
						dismiss();
					}
					//Remover
					else
					{
						((ListaTacoActivity)getActivity()).removeAlimentoLista(alimento);
						dismiss();
					}
				}
			});
			
			String []str = {"Editar", "Deletar"};
			ArrayAdapter<String> adp = new ArrayAdapter<String>(getActivity(), android.R.layout.simple_list_item_1, str);
			
			list.setAdapter(adp);
			
			return v;
		}
		
		@Override
		public void onHiddenChanged(boolean hidden) 
		{
			super.onHiddenChanged(hidden);
			
			if(hidden)
				dismiss();
		}
	}
}
