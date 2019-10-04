package com.indieapps.nutriplus.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.indieapps.nutriplus.AlimentoCardapio;
import com.indieapps.nutriplus.Paciente;
import com.indieapps.nutriplus.PacienteDieta;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import dao.AlimentoCardapioDao;
import dao.PacienteDao;
import dao.PacienteDietaDao;
//import android.util.Log;


public class ModelPaciente extends Observable
{
	//Banco de dados e seu dao
	SQLiteCon con;
	PacienteDao pacienteDao;
	PacienteDietaDao pacienteDietaDao;
	AlimentoCardapioDao alimentoCardapioDao;
	
	ArrayList<Paciente> listaPaciente = new ArrayList<Paciente>();
	
	public ModelPaciente() 
	{
	}
	
	public boolean carregaListaBanco()
	{
		try 
		{
			List<Paciente> list = pacienteDao.queryForAll();
			
			listaPaciente.clear();
			
			listaPaciente.addAll(list);
			
			setChanged();
			notifyObservers("update_lista");
			
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public boolean limpaListaBanco()
	{
		//Limpas as tableas
		try 
		{
			TableUtils.clearTable(con.getConnectionSource(), AlimentoCardapio.class);
			TableUtils.clearTable(con.getConnectionSource(), PacienteDieta.class);
			TableUtils.clearTable(con.getConnectionSource(), Paciente.class);
			
			listaPaciente.clear();
			
			//Norifica a lista pra s atualizar
			setChanged();
			notifyObservers("update_lista");
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return true;
	}
	
	public void procuraListaBanco(String nome) 
	{	
		try 
		{	
			List<Paciente> list = pacienteDao.query(pacienteDao.queryBuilder().where().like("nome", "%" + nome + "%").prepare());
			
			listaPaciente.clear();
			
			listaPaciente.addAll(list);
			
			//Norifica a lista pra s atualizar
			setChanged();
			notifyObservers("update_lista");
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	//Ao inserir o objeto deve-se primeiro criar a lista da dieta
	//e seta todas as referencias usadas pelo paciente
	public void adicionaObjeto( Paciente p )
	{
		try 
		{
			//Cria a dieta
			PacienteDieta dieta = new PacienteDieta();
			//dieta.setListaCardapio(new ArrayList<AlimentoCardapio>());
			pacienteDietaDao.create(dieta);
			
			p.setDieta(dieta);
			if(pacienteDao.create(p) < 0)
			{
				//Toast.makeText(ct, "Erro ao inserir paciente.", Toast.LENGTH_SHORT).show();
			}
			
			//insere na lista
			listaPaciente.add(p);
			
			//Norifica a lista pra s atualizar
			setChanged();
			notifyObservers("update_lista");
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeObjeto( Paciente p )
	{
		try 
		{
			if(p.getDieta() != null)
			{
				if(p.getDieta().getListaCardapio() != null)
				{
					//Deleta toda a lista do cardapio
					alimentoCardapioDao.delete(p.getDieta().getListaCardapio());
				}
				
				//deleta a dieta
				pacienteDietaDao.delete(p.getDieta());
			}
			
			//deleta o paciente
			pacienteDao.delete(p);
			
			listaPaciente.remove(p);
			
			setChanged();
			notifyObservers("update_lista");
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public boolean atualizaObjeto(int pos, Paciente novo)
	{
		try 
		{
			//Salva no banco de dados
			pacienteDao.update(novo);
			
			//salva na lista
			Paciente atual = getPaciente(pos);
			
			atual.atualiza(novo);
			
			setChanged();
			notifyObservers("update_lista");
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//Log.d("Debug", "Atualizou Objeto...");
		return true;
	}
	
	public ArrayList<Paciente> getListaPacientes()
	{
		return listaPaciente;
	}
	
	public Paciente getPaciente( int pos )
	{
		return ( pos > listaPaciente.size() - 1 )? null : listaPaciente.get(pos);
	}
	
	//Calculos
	public void calculaNee(Paciente paciente)
	{
		//Teste antes do calculo
		if( (paciente.getIdade() >= 0 && paciente.getIdade() < 3) || 
			 paciente.getAltura() <= 0 || paciente.getPeso() <= 0 )
			return;
		
		//De aordo com o sexo
		if(paciente.getSexo() == Paciente.SEXO_MASCULINO)
		{
			//De acordo com a idade
			if(paciente.getIdade() >= 3 && paciente.getIdade() <= 8)
			{
				float nee = (float) (88.5 - 61.9 * paciente.getIdade() + 
						(26.7 * paciente.getPeso() + 903.0 * paciente.getAltura()) + 20.0);
				
				paciente.setNee(nee);
			}
			else if(paciente.getIdade() >= 9 && paciente.getIdade() <= 18)
			{
				float af = 1.00f;
				
				switch(paciente.getCondFisica())
				{
					case Paciente.COND_SEDENTARIO:
						af = 1.00f;
						break;
					case Paciente.COND_POUCO_ATIVO:
						af = 1.13f;
						break;
					case Paciente.COND_ATIVO:
						af = 1.26f;
						break;
					case Paciente.COND_MUITO_ATIVO:
						af = 1.42f;
						break;
				}
				
				float nee = (float) (88.5 - 61.9 * paciente.getIdade() + 
						af * (26.7 * paciente.getPeso() + 903.0) + 25.0);
				
				paciente.setNee(nee);
			}
			else if(paciente.getIdade() >= 19)
			{
				//De acordo com o imc
				//IMC normal
				if(paciente.getImc() >= 18.5 && paciente.getImc() < 25)
				{
					float af = 1.00f;
					
					switch(paciente.getCondFisica())
					{
						case Paciente.COND_SEDENTARIO:
							af = 1.00f;
							break;
						case Paciente.COND_POUCO_ATIVO:
							af = 1.11f;
							break;
						case Paciente.COND_ATIVO:
							af = 1.25f;
							break;
						case Paciente.COND_MUITO_ATIVO:
							af = 1.48f;
							break;
					}
					
					float nee = (float) (662 - 9.53 * paciente.getIdade() + 
							af * (15.91 * paciente.getPeso() + 539.6 * paciente.getAltura()));
					
					paciente.setNee(nee);
				}
				//Sobre peso e gordo
				else if(paciente.getImc() >= 25)
				{
					float af = 1.00f;
					
					switch(paciente.getCondFisica())
					{
						case Paciente.COND_SEDENTARIO:
							af = 1.00f;
							break;
						case Paciente.COND_POUCO_ATIVO:
							af = 1.12f;
							break;
						case Paciente.COND_ATIVO:
							af = 1.29f;
							break;
						case Paciente.COND_MUITO_ATIVO:
							af = 1.59f;
							break;
					}
					
					float nee = (float) (1086 - 10.1 * paciente.getIdade() + 
							af * (13.7 * paciente.getPeso() + 416.0 * paciente.getAltura()));
					
					paciente.setNee(nee);
				}
					
			}
		}
		else
		{
			//De acordo com a idade
			if(paciente.getIdade() >= 3 && paciente.getIdade() <= 8)
			{
				
			}
			else if(paciente.getIdade() >= 9 && paciente.getIdade() <= 18)
			{
				float af = 1.00f;
				
				switch(paciente.getCondFisica())
				{
					case Paciente.COND_SEDENTARIO:
						af = 1.00f;
						break;
					case Paciente.COND_POUCO_ATIVO:
						af = 1.16f;
						break;
					case Paciente.COND_ATIVO:
						af = 1.31f;
						break;
					case Paciente.COND_MUITO_ATIVO:
						af = 1.56f;
						break;
				}
				
				float nee = (float) (135.3 - 30.8 * paciente.getIdade() + 
						af * (10.0 * paciente.getPeso() + 934.0) + 25.0);
				
				paciente.setNee(nee);
			}
			else if(paciente.getIdade() >= 19)
			{
				//De acordo com o imc
				//IMC normal
				if(paciente.getImc() >= 18.5 && paciente.getImc() < 25)
				{
					float af = 1.00f;
					
					switch(paciente.getCondFisica())
					{
						case Paciente.COND_SEDENTARIO:
							af = 1.00f;
							break;
						case Paciente.COND_POUCO_ATIVO:
							af = 1.14f;
							break;
						case Paciente.COND_ATIVO:
							af = 1.27f;
							break;
						case Paciente.COND_MUITO_ATIVO:
							af = 1.45f;
							break;
					}
					
					//Falta pegar esses dados
					/*
					float nee = (float) (662 - 9.53 * paciente.getIdade() + 
							af * (15.91 * paciente.getPeso() + 539.6 * paciente.getAltura()));
					
					paciente.setNee(nee);
					*/
				}
				//Sobre peso e gordo
				else if(paciente.getImc() >= 25)
				{
					float af = 1.00f;
					
					switch(paciente.getCondFisica())
					{
						case Paciente.COND_SEDENTARIO:
							af = 1.00f;
							break;
						case Paciente.COND_POUCO_ATIVO:
							af = 1.16f;
							break;
						case Paciente.COND_ATIVO:
							af = 1.27f;
							break;
						case Paciente.COND_MUITO_ATIVO:
							af = 1.44f;
							break;
					}
					
					float nee = (float) (448 - 7.95 * paciente.getIdade() + 
							af * (11.4 * paciente.getPeso() + 619.0 * paciente.getAltura()));
					
					paciente.setNee(nee);
				}
					
			}
		}
		
		setChanged();
		notifyObservers("nee");
	}
	
	public void calculaImc(Paciente paciente)
	{
		if(paciente.getAltura() <= 0 || paciente.getPeso() <= 0)
			return;
		
		float imc = paciente.getPeso() / (paciente.getAltura() * paciente.getAltura());
				
		paciente.setImc(imc);
		
		setChanged();
		notifyObservers("imc");
	}
	
	public void openDataBase(Context ct)
	{
		try 
		{
			closeDataBase();
			
			con = new SQLiteCon(ct, "db.sqlite");
			pacienteDao = new PacienteDao(con.getConnectionSource());
			pacienteDietaDao = new PacienteDietaDao(con.getConnectionSource());
			alimentoCardapioDao = new AlimentoCardapioDao(con.getConnectionSource());
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
