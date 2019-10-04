package dao;

import java.sql.SQLException;

import com.indieapps.nutriplus.AlimentoCardapio;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class AlimentoCardapioDao extends BaseDaoImpl<AlimentoCardapio, Integer> 
{
	public AlimentoCardapioDao(ConnectionSource connectionSource) throws SQLException 
	{
		super(AlimentoCardapio.class);
		setConnectionSource(connectionSource);
		initialize();
	}
}
