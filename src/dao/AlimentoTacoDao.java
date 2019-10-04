package dao;

import java.sql.SQLException;

import com.indieapps.nutriplus.AlimentoTaco;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class AlimentoTacoDao extends BaseDaoImpl<AlimentoTaco, Integer>
{
	public AlimentoTacoDao(ConnectionSource connectionSource) throws SQLException 
	{
		super(AlimentoTaco.class);
		setConnectionSource(connectionSource);
		initialize();
	}

}
