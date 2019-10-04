package com.indieapps.nutriplus.models;

import java.sql.SQLException;

import com.indieapps.nutriplus.AlimentoCardapio;
import com.indieapps.nutriplus.AlimentoTaco;
import com.indieapps.nutriplus.Paciente;
import com.indieapps.nutriplus.PacienteDieta;
import com.j256.ormlite.android.apptools.OrmLiteSqliteOpenHelper;
import com.j256.ormlite.support.ConnectionSource;
import com.j256.ormlite.table.TableUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

public class SQLiteCon extends OrmLiteSqliteOpenHelper
{
	final static int VERSAO_DB = 6;
	
	public SQLiteCon(Context ct, String nomeDB) 
	{
		super(ct, nomeDB, null, VERSAO_DB);
	}
	
	@Override
	public void onCreate(SQLiteDatabase db, ConnectionSource cs) 
	{
		try 
		{
			TableUtils.createTable(cs, Paciente.class);
			TableUtils.createTable(cs, AlimentoCardapio.class);
			TableUtils.createTable(cs, PacienteDieta.class);
			TableUtils.createTable(cs, AlimentoTaco.class);
			
			Log.d("Debug Sql", "Tabela criada com sucesso.");
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		
	}

	@Override
	public void onUpgrade(SQLiteDatabase db, ConnectionSource cs, int oldVersion, int newVersion) 
	{
		try 
		{
			TableUtils.dropTable(cs, Paciente.class, true);
			TableUtils.dropTable(cs, AlimentoTaco.class, true);
			TableUtils.dropTable(cs, AlimentoCardapio.class, true);
			TableUtils.dropTable(cs, PacienteDieta.class, true);
			
			Log.d("Debug Sql", "Upgrade.");
			
			onCreate(db, cs);
		} 
		catch (SQLException e) 
		{
			e.printStackTrace();
		}
	}
	
	@Override
	public void close() 
	{
		super.close();
	}

}
