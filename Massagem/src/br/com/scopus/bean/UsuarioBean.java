package br.com.scopus.bean;

public class UsuarioBean
{
	int idFuncionario;
	int matricula;
	String nomeFuncionario;
	int ramalFuncionario;
	int idPermissao;

	public int getIdFuncionario()
	{
		return idFuncionario;
	}

	public void setIdFuncionario(int idFuncionario)
	{
		this.idFuncionario = idFuncionario;
	}

	public int getMatricula()
	{
		return matricula;
	}

	public void setMatricula(int matricula)
	{
		this.matricula = matricula;
	}

	public String getNomeFuncionario()
	{
		return nomeFuncionario;
	}

	public void setNomeFuncionario(String nomeFuncionario)
	{
		this.nomeFuncionario = nomeFuncionario;
	}

	public int getRamalFuncionario()
	{
		return ramalFuncionario;
	}

	public void setRamalFuncionario(int ramalFuncionario)
	{
		this.ramalFuncionario = ramalFuncionario;
	}

	public int getIdPermissao()
	{
		return idPermissao;
	}

	public void setIdPermissao(int idPermissao)
	{
		this.idPermissao = idPermissao;
	}
}