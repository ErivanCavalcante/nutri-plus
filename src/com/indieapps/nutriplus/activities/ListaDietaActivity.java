package com.indieapps.nutriplus.activities;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;

import com.indieapps.nutriplus.AlimentoCardapio;
import com.indieapps.nutriplus.AlimentoTaco;
import com.indieapps.nutriplus.Paciente;
import com.indieapps.nutriplus.R;
import com.indieapps.nutriplus.ValidadorEditText;
import com.indieapps.nutriplus.models.ModelDieta;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemLongClickListener;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ExpandableListView;
import android.widget.Spinner;

public class ListaDietaActivity extends Activity implements Observer
{
	final int PEGA_ALIMENTO_TACO = 1;
	
	AdapterListaDieta adpDieta;
	ExpandableListView listDieta;
	EditText qtdCarbo, qtdLip, qtdProt, textNome, textQtd, textSaida;
	Spinner spRefeicao;
	
	ModelDieta modelDieta;
	Paciente paciente;
	AlimentoCardapio alimentoDieta;

	@Override
	protected void onCreate(Bundle savedInstanceState) 
	{
		super.onCreate(savedInstanceState);
		
		setContentView(R.layout.add_dieta);
		
		pegaReferencias();
		
		Intent it = getIntent();
		if(it != null)
		{
			try
			{
				paciente = (Paciente)it.getSerializableExtra("paciente");
			
				//Pega a posicao
				qtdCarbo.setText("" + paciente.getPerCho());
				qtdProt.setText("" + paciente.getPerPtn());
				qtdLip.setText("" + paciente.getPerLip());
				
				//Cria o model
				modelDieta = new ModelDieta(paciente);
				
				modelDieta.addObserver(this);
				
				//Cria o adp
				adpDieta = new AdapterListaDieta(this, modelDieta.getLista());
				
				//Seta o adp
				listDieta.setAdapter(adpDieta);
				
				modelDieta.openDataBase(getApplicationContext());
	
				//Carrega em uma nova thread
				modelDieta.carregaListaBanco();
				
				modelDieta.calculaAdequacao();
			}
			catch(NullPointerException ex)
			{
				ex.printStackTrace();
			}
			
			setaCallbacks();
		}
		
	}

	@Override
	public void onBackPressed() 
	{
		modelDieta.updatePaciente();
		Intent it = new Intent(this, ListaPacienteActivity.class);
		startActivity(it);
		finish();
	}
	
	@Override
	protected void onDestroy() 
	{
		//modelDieta.closeDataBase();
		modelDieta.deleteObserver(this);
		super.onDestroy();
	}
	
	@Override
	protected void onPause() 
	{
		modelDieta.closeDataBase();
		super.onPause();
	}
	
	@Override
	protected void onResume() 
	{
		modelDieta.openDataBase(getApplicationContext());
		super.onResume();
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) 
	{		
		if(resultCode == RESULT_OK)
		{
			if(requestCode == PEGA_ALIMENTO_TACO)
			{
				if(data == null)
					return;
				
				//Pega o alimento enviado da tablea taco
				alimentoDieta = new AlimentoCardapio();
		
				//Cria o alimento taco
				AlimentoTaco taco = new AlimentoTaco();
				taco.setId(data.getLongExtra("alim_id", 0));
				taco.setNome(data.getStringExtra("alim_nome"));
				taco.setQtdCarboidratos(data.getFloatExtra("alim_cho", 0));
				taco.setQtdLipidios(data.getFloatExtra("alim_lip", 0));
				taco.setQtdProteinas(data.getFloatExtra("alim_ptn", 0));
				taco.setQtdKcal(data.getFloatExtra("alim_kcal", 0));
				taco.setCalcio(data.getFloatExtra("alim_calcio", 0));
				taco.setFerro(data.getFloatExtra("alim_ferro", 0));
				taco.setSodio(data.getFloatExtra("alim_sodio", 0));
				taco.setPotassio(data.getFloatExtra("alim_potassio", 0));
				taco.setZinco(data.getFloatExtra("alim_zinco", 0));
				taco.setVitaminaC(data.getFloatExtra("alim_vitC", 0));
				
				//seta as referencias
				alimentoDieta.setDieta(paciente.getDieta());
				alimentoDieta.setAlimento(taco);
				
				textNome.setText(alimentoDieta.getAlimento().getNome());
			}
		}
	}
	
	@Override
	public void update(Observable observable, Object data) 
	{
		String d = (String)data;
		
		if(d.equals("update_list"))
		{
			adpDieta.notifyDataSetChanged();
		}
		else if(d.equals("update_adequacao"))
		{
			DecimalFormat format = new DecimalFormat("####.##");
			
			StringBuilder saida = new StringBuilder();
					saida.append("%Kcal = " + format.format(modelDieta.getValorAdqKcal() * 100) +
					 " %cho = " + format.format(modelDieta.getValorAdqCho() * 100) + "\n" +
					 " %lip = " + format.format(modelDieta.getValorAdqLip() * 100) + 
					 " %ptn: " + format.format(modelDieta.getValorAdqPtn() * 100) +
					 "\n\nCalcio = " + format.format(modelDieta.getSomaCalcio()) + "\n" +
					 "Ferro = " + format.format(modelDieta.getSomaFerro()) + "\n" +
					 "Sodio = " + format.format(modelDieta.getSomaSodio()) + "\n" +
					 "Potassio = " + format.format(modelDieta.getSomaPossaio()) + "\n" +
					 "Zinco = " + format.format(modelDieta.getSomaZinco()) + "\n" +
					 "Vitamina C = " + format.format(modelDieta.getSomaVitC()) + "\n");
			
			textNome.setText(saida.toString());
		}
	}
	
	
	public void pegaAlimentoTaco()
	{
		Intent it = new Intent(this, ListaTacoActivity.class);
		it.putExtra("pega_alimento", true);
		
		startActivityForResult(it, PEGA_ALIMENTO_TACO);
	}
	
	public void clickProcurar(View v)
	{
		pegaAlimentoTaco();
	}
	
	public void clickAddAlimento(View v)
	{
		//Adiciona se tem td esta corretamente 
		if(textNome.getText().length() == 0 || textQtd.getText().length() == 0)
			return;
		
		//Seta a qtd
		alimentoDieta.setQtd( Float.parseFloat(textQtd.getText().toString()) );
		alimentoDieta.setIdRefeicao( spRefeicao.getSelectedItemPosition() );
		
		//add o alimento a lista
		modelDieta.adicionaObjeto(alimentoDieta.getIdRefeicao(), alimentoDieta);
		
		textNome.setText("");
		textQtd.setText("");
		
		modelDieta.calculaAdequacao();
	}
	
	private void desejaRemover(final int pos, final int id) 
	{
		AlertDialog.Builder b = new AlertDialog.Builder(this);
		b.setTitle("Deletar Alimento")
		.setMessage("Deseja relamente deletar?")
		.setPositiveButton("Sim", new DialogInterface.OnClickListener()
		{
			@Override
			public void onClick(DialogInterface p1, int p2)
			{
				AlimentoCardapio a = modelDieta.getAlimento(pos, id);
				modelDieta.removeObjeto(pos, a);
				
				modelDieta.calculaAdequacao();
			}
		})
		.setNegativeButton("Não", null)
		.show();
	}
	
	private void pegaReferencias()
	{
		listDieta = (ExpandableListView)findViewById(R.id.listDieta);
		qtdCarbo = (EditText)findViewById(R.id.textCarboidratos);
		qtdLip = (EditText)findViewById(R.id.textLipidios);
		qtdProt = (EditText)findViewById(R.id.textProteinas);
		textSaida = (EditText)findViewById(R.id.textSaida);
		spRefeicao = (Spinner)findViewById(R.id.spRefeicao);
		textNome = (EditText)findViewById(R.id.textNomeAlimento);
		textQtd = (EditText)findViewById(R.id.textQtdAlimento);
	}
	
	private void setaCallbacks()
	{
		ArrayList<String> spItens = new ArrayList<String>();
		spItens.add("Cafe da Manhã");
		spItens.add("Lanche da Manhã");
		spItens.add("Almoço");
		spItens.add("Lanche da Tarde");
		spItens.add("Jantar");
		spItens.add("Lanche da Noite");
		
		ArrayAdapter<String> adpSp = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_item, spItens);
		spRefeicao.setAdapter(adpSp);
		
		qtdCarbo.addTextChangedListener(new ValidadorEditText() 
		{
			@Override
			public void validar(String texto) 
			{
				int valor = Integer.parseInt(texto);
				
				if(valor < 0)
				{
					qtdCarbo.setError("O valor deve ser maior do que 0!");
					return;
				}
				
				modelDieta.getPaciente().setPerCho(valor);
				modelDieta.calculaAdequacao();
			}
		});
		
		qtdLip.addTextChangedListener(new ValidadorEditText() 
		{
			@Override
			public void validar(String texto) 
			{
				int valor = Integer.parseInt(texto);
				
				if(valor < 0)
				{
					qtdCarbo.setError("O valor deve ser maior do que 0!");
					return;
				}
				
				modelDieta.getPaciente().setPerLip(valor);
				
				modelDieta.calculaAdequacao();
			}
		});
		
		qtdProt.addTextChangedListener(new ValidadorEditText() 
		{
			@Override
			public void validar(String texto) 
			{
				int valor = Integer.parseInt(texto);
				
				if(valor < 0)
				{
					qtdCarbo.setError("O valor deve ser maior do que 0!");
					return;
				}
				
				modelDieta.getPaciente().setPerPtn(valor);
				
				modelDieta.calculaAdequacao();
			}
		});
		
		listDieta.setOnItemLongClickListener(new OnItemLongClickListener() 
		{
			@Override
			public boolean onItemLongClick(AdapterView<?> parent, View arg1, int flatPos, long id) 
			{
				if(ExpandableListView.getPackedPositionType(id) == ExpandableListView.PACKED_POSITION_TYPE_CHILD)
				{
					//ExpandableListAdapter adp = ((ExpandableListView)parent).getExpandableListAdapter();
					long packedPos = ((ExpandableListView)parent).getExpandableListPosition(flatPos);
					int groupPos = ExpandableListView.getPackedPositionGroup(packedPos);
					int childPos = ExpandableListView.getPackedPositionChild(packedPos);
					
					desejaRemover(groupPos, childPos);
					
					return true;
				}
				// TODO Auto-generated method stub
				return false;
			}
		});
		
	}
}
