package dao;

import java.sql.SQLException;

import com.indieapps.nutriplus.Paciente;
import com.j256.ormlite.dao.BaseDaoImpl;
import com.j256.ormlite.support.ConnectionSource;

public class PacienteDao extends BaseDaoImpl<Paciente, Integer> 
{
	public PacienteDao(ConnectionSource connectionSource) throws SQLException 
	{
		super(Paciente.class);
		setConnectionSource(connectionSource);
		initialize();
	}
}
