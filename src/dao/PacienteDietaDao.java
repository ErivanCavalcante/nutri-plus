package dao;

import java.sql.SQLException;

import com.indieapps.nutriplus.PacienteDieta;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class PacienteDietaDao extends BaseDaoImpl<PacienteDieta, Integer> 
{
	public PacienteDietaDao(ConnectionSource connectionSource) throws SQLException 
	{
		super(PacienteDieta.class);
		setConnectionSource(connectionSource);
		initialize();
	}
}
