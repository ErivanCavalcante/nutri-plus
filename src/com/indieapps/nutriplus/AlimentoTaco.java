package com.indieapps.nutriplus;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Erivan on 16/05/2016.
 */

@DatabaseTable(tableName="alimentos")
public class AlimentoTaco
{
	@DatabaseField(generatedId = true, columnName = "_id")
    long id = 0;
	
	@DatabaseField(canBeNull = false)
    String nome;
	
	@DatabaseField
    float qtdKcal = 0;
	
	@DatabaseField
    float qtdProteinas = 0;
	
	@DatabaseField
    float qtdCarboidratos = 0;
	
	@DatabaseField
    float qtdLipidios = 0;
	
	@DatabaseField
	float calcio = 0;
	
	@DatabaseField
	float ferro = 0;
	
	@DatabaseField
	float sodio = 0;
	
	@DatabaseField
	float potassio = 0;
	
	@DatabaseField
	float zinco = 0;
	
	@DatabaseField
	float vitaminaC = 0;
    
    public AlimentoTaco() 
    {
	}
    
    public AlimentoTaco(AlimentoTaco taco) 
    {
		id = taco.getId();
		nome = taco.getNome();
		qtdKcal = taco.getQtdKcal();
		qtdCarboidratos = taco.getQtdCarboidratos();
		qtdLipidios = taco.getQtdLipidios();
		qtdProteinas = taco.getQtdProteinas();
		calcio = taco.getCalcio();
		ferro = taco.getFerro();
		sodio = taco.getSodio();
		potassio = taco.getPotassio();
		zinco = taco.getZinco();
		vitaminaC = taco.getVitaminaC();
		
	}
    
    public void atualiza(AlimentoTaco alimento)
    {
    	nome = alimento.getNome();
    	qtdKcal = alimento.getQtdKcal();
    	qtdProteinas = alimento.getQtdProteinas();
    	qtdLipidios = alimento.getQtdLipidios();
    	qtdCarboidratos = alimento.getQtdCarboidratos();
    	calcio = alimento.getCalcio();
		ferro = alimento.getFerro();
		sodio = alimento.getSodio();
		potassio = alimento.getPotassio();
		zinco = alimento.getZinco();
		vitaminaC = alimento.getVitaminaC();
    }
    
	public long getId() {
		return id;
	}
	public void setId(long id) {
		this.id = id;
	}
	public String getNome() {
		return nome;
	}
	public void setNome(String nome) {
		this.nome = nome;
	}
	public float getQtdProteinas() {
		return qtdProteinas;
	}
	public void setQtdProteinas(float qtdProteinas) {
		this.qtdProteinas = qtdProteinas;
	}
	public float getQtdCarboidratos() {
		return qtdCarboidratos;
	}
	public void setQtdCarboidratos(float qtdCarboidratos) {
		this.qtdCarboidratos = qtdCarboidratos;
	}
	public float getQtdLipidios() {
		return qtdLipidios;
	}
	public void setQtdLipidios(float qtdLipidios) {
		this.qtdLipidios = qtdLipidios;
	}

	public float getQtdKcal() {
		return qtdKcal;
	}

	public void setQtdKcal(float qtdKcal) {
		this.qtdKcal = qtdKcal;
	}

	public float getCalcio() {
		return calcio;
	}

	public void setCalcio(float calcio) {
		this.calcio = calcio;
	}

	public float getFerro() {
		return ferro;
	}

	public void setFerro(float ferro) {
		this.ferro = ferro;
	}

	public float getSodio() {
		return sodio;
	}

	public void setSodio(float sodio) {
		this.sodio = sodio;
	}

	public float getPotassio() {
		return potassio;
	}

	public void setPotassio(float potassio) {
		this.potassio = potassio;
	}

	public float getZinco() {
		return zinco;
	}

	public void setZinco(float zinco) {
		this.zinco = zinco;
	}

	public float getVitaminaC() {
		return vitaminaC;
	}

	public void setVitaminaC(float vitaminaC) {
		this.vitaminaC = vitaminaC;
	}
    
}
