package br.com.scopus.bean;

public class PlantonistaBean
{
	public int idFuncionario;
	public String nomeFuncionario;
	public String plantonista;
	public int idMeses;
	public String meses;

	public String getPlantonista()
	{
		return plantonista;
	}

	public void setPlantonista(String plantonista)
	{
		this.plantonista = plantonista;
	}

	public int getIdFuncionario()
	{
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario)
	{
		this.idFuncionario = idFuncionario;
	}

	public String getNomeFuncionario()
	{
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario)
	{
		this.nomeFuncionario = nomeFuncionario;
	}

	public int getIdMeses()
	{
		return idMeses;
	}

	public void setIdMeses(int idMeses)
	{
		this.idMeses = idMeses;
	}

	public String getMeses()
	{
		return meses;
	}

	public void setMeses(String meses)
	{
		this.meses = meses;
	}
}