package com.indieapps.nutriplus.activities;

//import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.indieapps.nutriplus.AlimentoCardapio;
import com.indieapps.nutriplus.Paciente;
import com.indieapps.nutriplus.R;
import com.indieapps.nutriplus.models.ModelPaciente;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.DialogFragment;
import android.app.FragmentManager;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

public class ListaPacienteActivity extends Activity implements Observer
{
	//Intent result
	static final int ADD_OBJETO = 1;
	static final int UPDATE_OBJETO = 2;
	static final int ADD_DIETA = 3;
	
	//Uso da lista
	EditText textPesquisa;
	ListView listPaciente;
	AdapterListaPacientes adpPacientes;
	
	ModelPaciente modelPaciente;
	
    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        
        setContentView(R.layout.lista_paciente);
        //Cria o model
        modelPaciente = new ModelPaciente();
        
        modelPaciente.addObserver(this);
        
        //Pega a referencia
        listPaciente = (ListView)findViewById( R.id.listPacientes );
        textPesquisa = (EditText)findViewById( R.id.textPesquisa );
        
        //Cria o adaptador
        adpPacientes = new AdapterListaPacientes(getApplicationContext(), modelPaciente.getListaPacientes());
        
        modelPaciente.openDataBase(getApplicationContext());
        
        //Seta o adp
        listPaciente.setAdapter( adpPacientes );
        listPaciente.setOnItemClickListener(new OnItemClickListener() 
		{
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1, int posicao, long id) 
			{
				FragmentManager fm = getFragmentManager();
				PopupItemPaciente pop = new PopupItemPaciente( modelPaciente.getPaciente(posicao), posicao);
				pop.show(fm, "pop");
			}
		});
        
      //Carrega a lista em outra thread
      modelPaciente.carregaListaBanco();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater inf = getMenuInflater();
        inf.inflate( R.menu.menu_principal, menu );

        return true;
    }

	@Override
	public boolean onMenuItemSelected(int featureId, MenuItem item)
	{
		if(item.getItemId() == R.id.menuTaco)
		{
			//Vai pra lista taco
			Intent it = new Intent(this, ListaTacoActivity.class);
			startActivity(it);
			finish();
		}
		else if(item.getItemId() == R.id.menuAddPaciente)
		{
			clickAddPaciente();
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

    public void clickAddPaciente()
    {
    	if(modelPaciente.getListaPacientes().size() >= 100)
    	{
    		Toast.makeText(getApplicationContext(), "Maximo de 100 pacientes na lista.", Toast.LENGTH_SHORT).show();
    		return;
    	}
    	
    	//Inicia o add paciente
		Intent it = new Intent(this, AddPacienteActivity.class);
		startActivityForResult(it, ADD_OBJETO);
    }
    
    @Override
    public void onBackPressed() 
    {
    	//modelPaciente.salvaListaBanco();
    	finish();
    }
    
    @Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{
		if(requestCode == ADD_OBJETO && resultCode == RESULT_OK)
		{
			if(data == null)
			{
				Toast.makeText(getApplicationContext(), "Erro ao adicionar paciente.", Toast.LENGTH_SHORT).show();
				return;
			}
				
			//Pega o paciente
			Paciente a = (Paciente)data.getSerializableExtra("obj");
		
			modelPaciente.adicionaObjeto(a);
			
			Toast.makeText(getApplicationContext(), "Paciente adicionado com sucesso.", Toast.LENGTH_SHORT).show();
		}
		else if(requestCode == UPDATE_OBJETO && resultCode == RESULT_OK)
		{
			if(data == null)
				return;
			
			Paciente novo = (Paciente)data.getSerializableExtra("obj");
			int pos = data.getIntExtra("posicao", 0);
			
			modelPaciente.atualizaObjeto(pos, novo);
		}
	}

	@Override
	public void update(Observable observable, Object data) 
	{
		String d = (String)data;
		
		if(d.equals("update_lista"))
		{
			adpPacientes.notifyDataSetChanged();
			Log.d("Debug", "Observable set Changed..." + d);
		}
	}
	
	@Override
	protected void onPause() 
	{
		modelPaciente.closeDataBase();
		super.onPause();
	}
	
	@Override
	protected void onResume() 
	{
		modelPaciente.openDataBase(getApplicationContext());
		super.onResume();
	}
	
	@Override
	protected void onDestroy() 
	{
		//modelPaciente.closeDatabase();
		modelPaciente.deleteObserver(this);
		super.onDestroy();
	}
	
	@Override
	protected void onSaveInstanceState(Bundle outState) 
	{
		super.onSaveInstanceState(outState);
		
		outState.putString("text_pesquisa", textPesquisa.getText().toString());
	}
	
	@Override
	protected void onRestoreInstanceState(Bundle savedInstanceState) 
	{
		super.onRestoreInstanceState(savedInstanceState);
		
		if(textPesquisa != null)
		{
			textPesquisa.setText(savedInstanceState.getString("text_pesquisa"));
		}
	}
	
	public void clickProcurarLista(View v)
	{
		modelPaciente.procuraListaBanco(textPesquisa.getText().toString());
	}
	
	public void removePacienteLista(Paciente p)
	{
		modelPaciente.removeObjeto(p);
	}
	
	
	
	public static class PopupItemPaciente extends DialogFragment
	{
		final int DIETA = 0;
		final int EDITAR = 1;
		final int DELETAR = 2;
		
		Paciente paciente;
		int posicao;
		
		public PopupItemPaciente(Paciente p, int posicao) 
		{
			this.paciente = p;
			this.posicao = posicao;
		}
		
		@Override
		public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) 
		{
			View v = inflater.inflate(R.layout.popup_menu_taco, container);
			
			getDialog().setTitle(paciente.getNome());
			
			ListView list = (ListView)v.findViewById(R.id.listMenu);
			list.setOnItemClickListener(new OnItemClickListener() 
			{
				@Override
				public void onItemClick(AdapterView<?> arg0, View arg1, int pos, long id) 
				{
					if(pos == DIETA)
					{
						Intent it = new Intent(getActivity(), ListaDietaActivity.class);
						if(paciente == null)
							Log.d("Debug", "Paciente antes d enviar eh nulo");
						
						it.putExtra("paciente", paciente);
						it.putExtra("posicao", posicao);
						startActivity(it);
						dismiss();
						getActivity().finish();
					}
					//Editar
					else if(pos == EDITAR)
					{
						Intent it = new Intent(getActivity(), AtualizaPacienteActivity.class);
						it.putExtra("obj", paciente);
						it.putExtra("posicao", posicao);
						
						Log.d("Debug", "Referencia antes " + paciente);
						
						getActivity().startActivityForResult(it, UPDATE_OBJETO);
						
						dismiss();
					}
					//Remover
					else
					{
						AlertDialog.Builder b = new AlertDialog.Builder(getActivity());
						b.setTitle("Deltar Paciente")
						.setMessage("Deseja relamente deletar?")
						.setPositiveButton("Sim", new DialogInterface.OnClickListener()
						{
							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								((ListaPacienteActivity)getActivity()).removePacienteLista(paciente);
							}
						})
						.setNegativeButton("Nao", new DialogInterface.OnClickListener()
						{

							@Override
							public void onClick(DialogInterface p1, int p2)
							{
								dismiss();
							}
						})
						.show();
						
						dismiss();
					}
				}
			});
			
			String []str = {"Dieta", "Editar", "Deletar"};
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
