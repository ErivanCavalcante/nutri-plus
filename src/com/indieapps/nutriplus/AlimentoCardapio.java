package com.indieapps.nutriplus;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Erivan on 16/05/2016.
 */

//Um alimento do cardapio
@DatabaseTable(tableName = "alimento_cardapio")
public class AlimentoCardapio implements Serializable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 3529335225973457272L;

	@DatabaseField(generatedId = true, columnName = "_id")
	long id;
	
	@DatabaseField
	int idRefeicao;
   
	@DatabaseField
    float qtd;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
    AlimentoTaco alimento;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	PacienteDieta dieta;
    
	public AlimentoCardapio() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public AlimentoCardapio(PacienteDieta dieta, AlimentoTaco alimento, int idRefeicao, float qtd) 
	{
		this.dieta = dieta;
		this.alimento = alimento;
		this.idRefeicao = idRefeicao;
		this.qtd = qtd;
	}
	
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	
	public AlimentoTaco getAlimento() {
		return alimento;
	}
	public int getIdRefeicao() {
		return idRefeicao;
	}
	public void setIdRefeicao(int idRefeicao) {
		this.idRefeicao = idRefeicao;
	}
	public void setAlimento(AlimentoTaco alimento) {
		this.alimento = alimento;
	}
	
	public float getQtd() {
		return qtd;
	}
	
	public void setQtd(float qtd) {
		this.qtd = qtd;
	}
	
	public PacienteDieta getDieta() {
		return dieta;
	}
	
	public void setDieta(PacienteDieta dieta) {
		this.dieta = dieta;
	}
    
}
