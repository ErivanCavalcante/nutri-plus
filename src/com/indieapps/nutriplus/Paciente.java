package com.indieapps.nutriplus;

import java.io.Serializable;

import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * Created by Erivan on 16/05/2016.
 */
@DatabaseTable(tableName = "pacientes")
public class Paciente implements Serializable
{
    /**
	 * 
	 */
	private static final long serialVersionUID = 1656478450121438075L;
	
	public static final int SEXO_MASCULINO = 0;
	public static final int SEXO_FEMININO = 1;

	public static final int COND_SEDENTARIO = 0;
	public static final int COND_POUCO_ATIVO = 1;
	public static final int COND_ATIVO = 2;
	public static final int COND_MUITO_ATIVO = 3;
	
	@DatabaseField(generatedId = true, columnName = "_id")
    long id;
	
	@DatabaseField(canBeNull = false)
	String nome;
	
	@DatabaseField
	int idade;
	
	@DatabaseField
    int sexo;
	
	@DatabaseField
    float peso;
	
	@DatabaseField
    float altura;
	
	@DatabaseField
    float imc;
	
	@DatabaseField
    int condFisica;
	
	@DatabaseField
    float nee;
    
	//Porcentagens pra adequar
	@DatabaseField
	int perCho;
	
	@DatabaseField
	int perLip; 
	
	@DatabaseField
	int perPtn;
	
	@DatabaseField(foreign = true, foreignAutoRefresh = true)
	PacienteDieta dieta;
	
	public Paciente() 
	{
		// TODO Auto-generated constructor stub
	}
	
	public Paciente(String nome, int idade, int sexo, float peso, float altura, float imc, int condFisica, 
						float nee) 
	{
		this.nome = nome;
		this.idade = idade;
		this.sexo = sexo;
		this.peso = peso;
		this.altura = altura;
		this.imc = imc;
		this.condFisica = condFisica;
		this.nee = nee;
	}
	
	public void atualiza(Paciente p)
	{
		nome = p.getNome();
		idade = p.getIdade();
		sexo = p.getSexo();
		peso = p.getPeso();
		altura = p.getAltura();
		imc = p.getImc();
		condFisica = p.getCondFisica();
		nee = p.getNee();
		perCho = p.getPerCho();
		perLip = p.getPerLip();
		perPtn = p.getPerPtn();
		dieta = p.getDieta();
	}
	
    public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

    //Lista do cardapio
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}
	
    public float getNee() {
        return nee;
    }

	public void setNee(float nee) {
        this.nee = nee;
    }

    public int getCondFisica() {
        return condFisica;
    }

    public void setCondFisica(int condFisica) {
        this.condFisica = condFisica;
    }

    public float getImc() {
        return imc;
    }

    public void setImc(float imc) {
        this.imc = imc;
    }

    public float getAltura() {
        return altura;
    }

    public void setAltura(float altura) {
        this.altura = altura;
    }

    public float getPeso() {
        return peso;
    }

    public void setPeso(float peso) {
        this.peso = peso;
    }

    public int getSexo() {
        return sexo;
    }

    public void setSexo(int sexo) {
        this.sexo = sexo;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
    
    public int getPerCho() {
		return perCho;
	}

	public void setPerCho(int perCho) {
		this.perCho = perCho;
	}


	public int getPerLip() {
		return perLip;
	}

	public void setPerLip(int perLip) {
		this.perLip = perLip;
	}

	public int getPerPtn() {
		return perPtn;
	}

	public void setPerPtn(int perPtn) {
		this.perPtn = perPtn;
	}

	public PacienteDieta getDieta() {
		return dieta;
	}

	public void setDieta(PacienteDieta dieta) {
		this.dieta = dieta;
	}

}
