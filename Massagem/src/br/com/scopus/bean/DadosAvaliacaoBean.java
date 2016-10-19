package br.com.scopus.bean;

public class DadosAvaliacaoBean
{
	int idCategoria;
	String descricaoCategoria;
	DadosPerguntasBean[] perguntas;

	public int getIdCategoria()
	{
		return idCategoria;
	}

	public void setIdCategoria(int idCategoria)
	{
		this.idCategoria = idCategoria;
	}

	public String getDescricaoCategoria()
	{
		return descricaoCategoria;
	}

	public void setDescricaoCategoria(String descricaoCategoria)
	{
		this.descricaoCategoria = descricaoCategoria;
	}

	public DadosPerguntasBean[] getPerguntas()
	{
		return perguntas;
	}

	public void setPerguntas(DadosPerguntasBean[] perguntas)
	{
		this.perguntas = perguntas;
	}	
}