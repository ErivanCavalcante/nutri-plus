package com.indieapps.nutriplus.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Observable;

import com.indieapps.nutriplus.AlimentoCardapio;
import com.indieapps.nutriplus.Paciente;
import com.indieapps.nutriplus.PacienteDieta;
import com.j256.ormlite.dao.ForeignCollection;

import android.content.Context;
import android.util.Log;
import dao.AlimentoCardapioDao;
import dao.PacienteDao;
import dao.PacienteDietaDao;

public class ModelDieta extends Observable
{
	//Paciente a qual pertence a dieta
	Paciente paciente;	
	
	//Conexao
	SQLiteCon con;
	PacienteDao pacienteDao;
	AlimentoCardapioDao cardapioDao;
	PacienteDietaDao dietaDao;
	
	
	float gramasCho, gramasLip, gramasPtn;
	
	float valorAdqCho, valorAdqLip, valorAdqPtn, valorAdqKcal, somaCalcio, somaFerro, somaSodio, somaPossaio, 
			somaZinco, somaVitC;

	
	//Lista de itens
	ArrayList< ArrayList<AlimentoCardapio> > listaDieta =  new ArrayList< ArrayList<AlimentoCardapio> >();
	
	
	
	public ModelDieta(Paciente paciente) 
	{
		this.paciente = paciente;
	
		//Cria as listas
		listaDieta.add(new ArrayList<AlimentoCardapio>());
		listaDieta.add(new ArrayList<AlimentoCardapio>());
		listaDieta.add(new ArrayList<AlimentoCardapio>());
		listaDieta.add(new ArrayList<AlimentoCardapio>());
		listaDieta.add(new ArrayList<AlimentoCardapio>());
		listaDieta.add(new ArrayList<AlimentoCardapio>());
	}
	
	
	public float getValorAdqCho() {
		return valorAdqCho;
	}

	public void setValorAdqCho(float valorAdqCho) {
		this.valorAdqCho = valorAdqCho;
	}

	public float getValorAdqLip() {
		return valorAdqLip;
	}

	public void setValorAdqLip(float valorAdqLip) {
		this.valorAdqLip = valorAdqLip;
	}

	public float getValorAdqPtn() {
		return valorAdqPtn;
	}

	public void setValorAdqPtn(float valorAdqPtn) {
		this.valorAdqPtn = valorAdqPtn;
	}

	public float getValorAdqKcal() {
		return valorAdqKcal;
	}


	public void setValorAdqKcal(float valorAdqKcal) {
		this.valorAdqKcal = valorAdqKcal;
	}

	
	public float getSomaCalcio() {
		return somaCalcio;
	}


	public void setSomaCalcio(float somaCalcio) {
		this.somaCalcio = somaCalcio;
	}


	public float getSomaFerro() {
		return somaFerro;
	}


	public void setSomaFerro(float somaFerro) {
		this.somaFerro = somaFerro;
	}


	public float getSomaSodio() {
		return somaSodio;
	}


	public void setSomaSodio(float somaSodio) {
		this.somaSodio = somaSodio;
	}


	public float getSomaPossaio() {
		return somaPossaio;
	}


	public void setSomaPossaio(float somaPossaio) {
		this.somaPossaio = somaPossaio;
	}


	public float getSomaZinco() {
		return somaZinco;
	}


	public void setSomaZinco(float somaZinco) {
		this.somaZinco = somaZinco;
	}


	public float getSomaVitC() {
		return somaVitC;
	}


	public void setSomaVitC(float somaVitC) {
		this.somaVitC = somaVitC;
	}


	public ArrayList<ArrayList<AlimentoCardapio>> getLista() {
		return listaDieta;
	}

	public boolean carregaListaBanco()
	{
		try 
		{
			//Pega o paciente do banco
			PacienteDieta dieta = dietaDao.queryForId((int)paciente.getDieta().getId());
			//Pega as colecoes
			ForeignCollection<AlimentoCardapio> list = dieta.getListaCardapio();
			
			for(AlimentoCardapio a : list)
			{
				listaDieta.get( a.getIdRefeicao() ).add(a); 
			}
			
			//Norifica a lista pra s atualizar
			setChanged();
			notifyObservers("update_list");
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean limpaListaBanco()
	{
		try 
		{
			//Pega o paciente do banco
			PacienteDieta dieta = dietaDao.queryForId((int)paciente.getDieta().getId());
			//Pega as colecoes
			ForeignCollection<AlimentoCardapio> list = dieta.getListaCardapio();
			
			cardapioDao.delete(list);
			
			//Limpa as listas
			for(ArrayList<AlimentoCardapio> l : listaDieta)
			{
				l.clear();
			}
			
			//Norifica a lista pra s atualizar
			setChanged();
			notifyObservers("update_list");
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		
		return true;
	}
	
	public boolean atualizaObjetoBanco( String pos, AlimentoCardapio p )
	{
		return true;
	}
	
	public void adicionaObjeto( int pos, AlimentoCardapio p )
	{
		try 
		{
			cardapioDao.create(p);
			
			//insere na lista
			listaDieta.get(pos).add(p);
			
			//Norifica a lista pra s atualizar
			setChanged();
			notifyObservers("update_list");
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
	}
	
	public void removeObjeto( int pos, AlimentoCardapio p  )
	{
		try 
		{
			cardapioDao.delete(p);
			
			//remove na lista
			listaDieta.get(pos).remove(p);
			
			//Norifica a lista pra s atualizar
			setChanged();
			notifyObservers("update_list");
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void updatePaciente()
	{
		try 
		{
			Log.d("Debug", "Valor qtd cho = " + paciente.getPerCho());
			pacienteDao.update(paciente);
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public Paciente getPaciente() {
		return paciente;
	}

	public void setPaciente(Paciente paciente) {
		this.paciente = paciente;
	}

	public ArrayList<AlimentoCardapio> getListaDieta(int pos) {
		return listaDieta.get(pos);
	}

	public AlimentoCardapio getAlimento( int p, int pos )
	{
		//Pega a lista d alimentos
		ArrayList<AlimentoCardapio> lista = listaDieta.get(p);
		
		return ( pos > lista.size() - 1 )? null : lista.get(pos);
	}
	
	public void atualizaQtdAlimentoTaco( AlimentoCardapio alimento, float novaQtd )
	{
		alimento.setQtd(novaQtd);
	}
	
	public void calculaAdequacao()
	{
		try
		{
			if(paciente.getPerCho() + paciente.getPerLip() + paciente.getPerPtn() != 100)
			{
				return;
			}
				
			float totalCho = 0.0000f, totalLip = 0.0000f, totalPtn = 0.0000f, totalKcal = 0.0000f;
			
			//Calcula a qtd de gramas necessaria pra adequar cada item
			gramasCho = paciente.getNee() * ( paciente.getPerCho() / 100.0000f ) / 4.0000f;
			gramasLip = paciente.getNee() * ( paciente.getPerLip() / 100.0000f ) / 9.0000f;
			gramasPtn = paciente.getNee() * ( paciente.getPerPtn() / 100.0000f ) / 4.0000f;
			
			//Soma as qtds
			for( ArrayList<AlimentoCardapio> l : listaDieta )
			{
				for(AlimentoCardapio al : l)
				{
					totalCho += al.getAlimento().getQtdCarboidratos() * ( al.getQtd() / 100.0000f );
					totalLip += al.getAlimento().getQtdLipidios() * ( al.getQtd() / 100.0000f );
					totalPtn += al.getAlimento().getQtdProteinas() * ( al.getQtd() / 100.0000f );
					totalKcal += al.getAlimento().getQtdKcal() * ( al.getQtd() / 100.0000f );
					
					somaCalcio += al.getAlimento().getCalcio();
					somaFerro += al.getAlimento().getFerro();
					somaSodio += al.getAlimento().getSodio();
					somaPossaio += al.getAlimento().getPotassio();
					somaZinco += al.getAlimento().getZinco();
					somaVitC += al.getAlimento().getVitaminaC();
				}
			}
			
			//Calcula as adequacoes
			valorAdqCho = totalCho / gramasCho;
			valorAdqLip = totalLip / gramasLip;
			valorAdqPtn = totalPtn / gramasPtn;
			valorAdqKcal = totalKcal / paciente.getNee();
			
			//Norifica a lista pra s atualizar
			setChanged();
			notifyObservers("update_adequacao");
		}
		catch(NullPointerException ex)
		{
			ex.printStackTrace();
		}
	}
	
	public void openDataBase(Context ct)
	{
		try 
		{
			closeDataBase();
			
			con = new SQLiteCon(ct, "db.sqlite");
			pacienteDao = new PacienteDao(con.getConnectionSource());
			cardapioDao = new AlimentoCardapioDao(con.getConnectionSource());
			dietaDao = new PacienteDietaDao(con.getConnectionSource());
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void closeDataBase()
	{
		//Se ja existe e esta aberto entao fecha pra da o novo inicio
		if(con != null)
		{
			if(con.isOpen())
			{
				con.close();
			}
		}
	}
}
