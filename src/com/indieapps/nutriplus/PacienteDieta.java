package com.indieapps.nutriplus;

import java.io.Serializable;
import com.j256.ormlite.dao.ForeignCollection;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.field.ForeignCollectionField;
import com.j256.ormlite.table.DatabaseTable;

@DatabaseTable(tableName = "paciente_dieta")
public class PacienteDieta implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2582384580699584669L;

	@DatabaseField(generatedId = true, columnName = "_id")
	long id;
	
	//Foreing key
	@ForeignCollectionField(eager = false)
	ForeignCollection<AlimentoCardapio> listaCardapio;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public ForeignCollection<AlimentoCardapio> getListaCardapio() {
		return listaCardapio;
	}

	public void setListaCardapio(ForeignCollection<AlimentoCardapio> listaCardapio) {
		this.listaCardapio = listaCardapio;
	}
	
}
