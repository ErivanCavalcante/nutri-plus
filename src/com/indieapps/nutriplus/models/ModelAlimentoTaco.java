package com.indieapps.nutriplus.models;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Observable;

import com.indieapps.nutriplus.AlimentoTaco;
//import com.j256.ormlite.dao.GenericRawResults;
//import com.j256.ormlite.stmt.QueryBuilder;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.util.Log;
import dao.AlimentoTacoDao;

public class ModelAlimentoTaco extends Observable
{
	//Lista d todos os alimentos
	ArrayList<AlimentoTaco> listaAlimento = new ArrayList<AlimentoTaco>();
	
	//Conexao com o banco de dados
	SQLiteCon con;
	//Operacoes no banco
	AlimentoTacoDao dataBaseOpr;
	
	public ModelAlimentoTaco() 
	{
	}
	
	public boolean carregaListaBanco()
	{		
		try 
		{
			List<AlimentoTaco> list =  dataBaseOpr.queryForAll();
			Log.d("Debug Sql", "Load taco com sucesso.");
			
			for(AlimentoTaco a : list)
			{
				listaAlimento.add(a);
				//Log.d("Debug Sql", "nome = " + a.getNome());
			}
			
			setChanged();
			notifyObservers("update_lista");
			
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
			TableUtils.clearTable(con.getConnectionSource(), AlimentoTaco.class);
			
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
			List<AlimentoTaco> list = dataBaseOpr.query(dataBaseOpr.queryBuilder().where().like("nome", "%" + nome + "%").prepare());
			
			listaAlimento.clear();
			
			for(AlimentoTaco a : list)
			{
				listaAlimento.add(a);
			}
			
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
	
	public void adicionaObjeto( AlimentoTaco p )
	{
		try 
		{
			if(dataBaseOpr.create(p) >= 0)
				Log.d("Debug Sql", "Taco inserido com sucesso.");
			
			listaAlimento.add(p);
			
			setChanged();
			notifyObservers("update_lista");
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void removeObjeto( AlimentoTaco p )
	{
		try 
		{
			//deleta no banco
			dataBaseOpr.delete(p);
			
			listaAlimento.remove(p);
			
			setChanged();
			notifyObservers("update_lista");
		} 
		catch (SQLException e) 
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}	
	}
	
	public boolean atualizaObjeto(AlimentoTaco novo)
	{
		
		try 
		{
			dataBaseOpr.update(novo);
			//Atualiza na lista
			for(AlimentoTaco a : listaAlimento)
			{
				if(a.getId() == novo.getId())
				{
					a.atualiza(novo);
					break;
				}
			}
			
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
	
	public ArrayList<AlimentoTaco> getListaAlimentos()
	{
		return listaAlimento;
	}
	
	public AlimentoTaco getAlimento( int pos )
	{
		return ( pos > listaAlimento.size() - 1 )? null : listaAlimento.get(pos);
	}
	
	public void openDataBase(Context ct)
	{
		try 
		{
			closeDataBase();
			
			con = new SQLiteCon(ct, "db.sqlite");
			dataBaseOpr = new AlimentoTacoDao(con.getConnectionSource());
			
			Log.d("Debug Sql", "Conexao e dao taco criada com sucesso.");
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
