package br.com.scopus.consultas;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import br.com.scopus.bean.DadosAgendamentoBean;
import br.com.scopus.bean.DadosCategoriaBean;
import br.com.scopus.bean.DadosMassagemBean;
import br.com.scopus.bean.DadosMassoterapeutasBean;
import br.com.scopus.bean.DadosPerguntasBean;
import br.com.scopus.bean.DadosPerguntasOpcaoBean;
import br.com.scopus.bean.DadosRespostasBean;
import br.com.scopus.bean.HorarioBean;
import br.com.scopus.bean.PlantonistaBean;
import br.com.scopus.bean.UsuarioBean;
import br.com.scopus.conexao.Conexao;

public class Persistencia
{
	Connection objConn = null;

	/**
	 * Método que monta a combo de locais (COP, MEGA, ...)
	 *
	 * @param Conexão com BD
	 *
	 * @return ArrayList com retorno da consulta do Bando de dados
	 *
	 * @author Bruno S Filho
	 *
	 * @since 04/2016 - V. 1.0
	 */
	public Map<Integer, String> getLocal()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	idLocal, ");
		sb.append("	descricaoLocal ");
		sb.append("FROM ");
		sb.append(" local");

		PreparedStatement pst;
		ResultSet rs;

		Map<Integer, String> lista = new HashMap<Integer, String>();

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				lista.put(rs.getInt("idLocal"), rs.getString("descricaoLocal"));
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (lista);
	}
	
	public ArrayList<HorarioBean> getHorario(char ckbTurno, boolean cabecalho)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	idHorario, ");
		sb.append("	descricaoHorario, ");
		sb.append("	turnoHorario ");
		sb.append("FROM ");
		sb.append(" horario ");
		sb.append("WHERE ");
		sb.append(" turnoHorario = ? ");

		if (cabecalho)
		{
			sb.append("LIMIT 1 ");
		}

		PreparedStatement pst;
		ResultSet rs = null;

		ArrayList<HorarioBean> lista = new ArrayList<HorarioBean>();

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, String.valueOf(ckbTurno));

			rs = pst.executeQuery();

			while (rs.next())
			{
				HorarioBean horarioBean = new HorarioBean();
				horarioBean.setIdHorario(rs.getInt("idHorario"));
				horarioBean.setTurnoHorario(rs.getString("turnoHorario"));
				horarioBean.setDescricaoHorario(rs.getString("descricaoHorario"));

				lista.add(horarioBean);
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (lista);
	}

	public ArrayList<DadosAgendamentoBean> getDadosAgendamento(String data, String local, int idHorario)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	h.idHorario, ");
		sb.append("	h.descricaoHorario, ");
		sb.append(" fm.idFuncionario as idFuncionarioMassoterapeuta, ");
		sb.append(" fm.matricula as matriculaMassoterapeuta, ");
		sb.append(" fm.nomeFuncionario as nomeFuncionarioMassoterapeuta, ");
		sb.append(" f.idFuncionario, ");
		sb.append(" f.matricula as matriculaFuncionario, ");
		sb.append(" case when (f.nomeFuncionario is null) then '' else f.nomeFuncionario end as nomeFuncionario, ");
		sb.append("	a.idStatusAgendamento ");
		sb.append("FROM");
		sb.append(" agendamento as a ");
		sb.append(" join funcionarios as fm ");
		sb.append(" on a.idFuncionarioMassoterapeuta = fm.idFuncionario ");
		sb.append(" left join funcionarios as f ");
		sb.append(" on a.idFuncionario = f.idFuncionario ");
		sb.append(" join horario as h ");
		sb.append(" on a.idHorario = h.idHorario ");
		sb.append("WHERE");
		sb.append(" a.dataAgendamento = ? ");
		sb.append(" and fm.idLocal = ? ");
		sb.append(" and a.idHorario = ? ");
		sb.append(" and a.idStatusAgendamento in (1,2,4,5) ");
		sb.append("ORDER BY");
		sb.append(" fm.nomeFuncionario");

		PreparedStatement pst;
		ResultSet rs = null;

		ArrayList<DadosAgendamentoBean> arrayList = new ArrayList<DadosAgendamentoBean>();
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, data);
			pst.setString(2, local);
			pst.setInt(3, idHorario);

			rs = pst.executeQuery();
			while (rs.next())
			{
				DadosAgendamentoBean dadosAgendamentoBean = new DadosAgendamentoBean();
				dadosAgendamentoBean.setIdHorario(rs.getInt("idHorario"));
				dadosAgendamentoBean.setDescricaoHorario(rs.getString("descricaoHorario"));
				dadosAgendamentoBean.setIdFuncionarioMassoterapeuta(rs.getInt("idFuncionarioMassoterapeuta"));
				dadosAgendamentoBean.setMatriculaMassoterapeuta(rs.getString("matriculaMassoterapeuta"));
				dadosAgendamentoBean.setNomeFuncionarioMassoterapeuta(rs.getString("nomeFuncionarioMassoterapeuta"));
				dadosAgendamentoBean.setIdFuncionario(rs.getInt("idFuncionario"));
				dadosAgendamentoBean.setMatriculaFuncionario(rs.getString("matriculaFuncionario"));
				dadosAgendamentoBean.setNomeFuncionario(rs.getString("nomeFuncionario"));
				dadosAgendamentoBean.setIdStatusAgendamento(rs.getString("idStatusAgendamento"));

				arrayList.add(dadosAgendamentoBean);
			}
			

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	
	/**
	 * Método que consulta a base de dados e retorna os agendamentos marcados para um massoterapeuta em um dia determinado
	 *
	 * @param Conexao com BD
	 *
	 * @return ArrayList com retorno da consulta do Bando de dados
	 *
	 * @author Leticia G Dolny
	 *
	 * @since 04/2016 - V. 1.0
	 */
	public ArrayList<DadosMassagemBean> getDadosMassagem(String idMassoterapeuta)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" fm.nomeFuncionario, ");
		sb.append("	concat_ws(' - ', h.descricaoHorario, IFNULL(f.nomeFuncionario, 'Vago'), f.ramalFuncionario) as dadosCliente, ");
		sb.append("	replace(h.descricaoHorario, ':', '') as descricaoHorarioFormatado ");
		sb.append("FROM ");
		sb.append(" agendamento as a ");
		sb.append(" left join funcionarios as f on a.idFuncionario = f.idFuncionario ");
		sb.append(" left join horario as h on a.idHorario = h.idHorario ");
		sb.append(" join funcionarios as fm on a.idFuncionarioMassoterapeuta = fm.idFuncionario ");
		sb.append("WHERE ");
		sb.append(" a.dataAgendamento = CURDATE() ");
		sb.append(" and a.idFuncionarioMassoterapeuta = ? ");
		sb.append(" and a.idStatusAgendamento != 3 ");

		ArrayList<DadosMassagemBean> arrayList = new ArrayList<DadosMassagemBean>();

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, idMassoterapeuta);

			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosMassagemBean dadosMassagemBean = new DadosMassagemBean();
				dadosMassagemBean.setNomeMassoteerapeuta(rs.getString("nomeFuncionario"));
				dadosMassagemBean.setDadosCliente(rs.getString("dadosCliente"));
				dadosMassagemBean.setDescricaoHorarioFormatado(rs.getString("descricaoHorarioFormatado"));

				arrayList.add(dadosMassagemBean);
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}

	public int validacaoAgendamento(String idHorario, String dataConvertida, String matriculaMassoterapeuta)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" count(*) as total ");
		sb.append("FROM ");
		sb.append(" agendamento ");
		sb.append("WHERE ");
		sb.append(" idHorario = ? ");
		sb.append(" and dataAgendamento = ? ");
		sb.append(" and idFuncionarioMassoterapeuta = (select idFuncionario from funcionarios where matricula = ?) ");
		sb.append(" and idStatusAgendamento = 1 ");

		PreparedStatement pst;
		ResultSet rs;

		int temp = 0;
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());

			pst.setString(1, idHorario);
			pst.setString(2, dataConvertida);
			pst.setString(3, matriculaMassoterapeuta);

			rs = pst.executeQuery();

			while (rs.next())
			{
				temp = Integer.parseInt(rs.getString("total"));
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	public int gravaDadosAgendamento(String idHorario, String matriculaMassoterapeuta, String matriculaFuncionario, String dataConvertida)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append("	agendamento ");
		sb.append("SET");
		sb.append(" idFuncionario = (select idFuncionario from funcionarios where matricula = ?), ");
		sb.append(" idStatusAgendamento = 1 ");
		sb.append("WHERE ");
		sb.append(" dataAgendamento = ? ");
		sb.append(" and idHorario = (select idHorario from horario where idHorario = ?) ");
		sb.append(" and idFuncionarioMassoterapeuta = (select idFuncionario from funcionarios where matricula = ?) ");
		sb.append(" and exists (select idFuncionario from funcionarios where matricula = ?) ");

		PreparedStatement pst;

		int temp = 0;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, matriculaFuncionario);
			pst.setString(2, dataConvertida);
			pst.setString(3, idHorario);
			pst.setString(4, matriculaMassoterapeuta);
			pst.setString(5, matriculaFuncionario);

			temp = pst.executeUpdate();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	/**
	 * Método que implementa a regra de negócio:
	 *
	 * 	Só pode haver 1 massagem agendada por semana por usuário
	 *
	 * @param objConn
	 * @param arrayDatasSemanaAtual - intervalo da semana (segunda a sexta)
	 * @param nomeFuncionario
	 *
	 * @return 0, se não tiver agendamento marcado; 1, se já tiver agendamento. Então não pode agendar novo horário.
	 */
	public int validaAgendamento(String[] arrayDatasSemanaAtual, String matriculaFuncionario)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	count(*) as total ");
		sb.append("FROM ");
		sb.append(" agendamento ");
		sb.append("WHERE ");
		sb.append(" dataAgendamento between ? and ? ");
		sb.append(" and idFuncionario = (select idFuncionario from funcionarios where matricula = ?) ");
		sb.append(" and idStatusAgendamento in (1,2) ");

		PreparedStatement pst;
		ResultSet rs;

		int temp = 0;
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, arrayDatasSemanaAtual[0]);
			pst.setString(2, arrayDatasSemanaAtual[1]);
			pst.setString(3, matriculaFuncionario);

			rs = pst.executeQuery();

			while (rs.next())
			{
				temp = Integer.parseInt(rs.getString("total"));
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	public ArrayList<PlantonistaBean> getPlantonista()
	{
		ArrayList<PlantonistaBean> arrayList = new ArrayList<PlantonistaBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	idFuncionario, ");
		sb.append(" nomeFuncionario ");
		//sb.append(" ,plantonista ");
		sb.append("FROM ");
		sb.append(" funcionarios ");
		sb.append("WHERE ");
		sb.append(" tipoFuncionario = 'M' ");
		sb.append(" and StatusFuncionario = 'A' ");
		sb.append("ORDER BY ");
		sb.append(" nomeFuncionario");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				PlantonistaBean plantonistaBean = new PlantonistaBean();
				plantonistaBean.setIdFuncionario(rs.getInt("idFuncionario"));
				plantonistaBean.setNomeFuncionario(rs.getString("nomeFuncionario"));
				//plantonistaBean.setPlantonista(rs.getString("plantonista"));

				arrayList.add(plantonistaBean);
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}

	public int getValidaPlantonista(String idFuncionarioMassoterapeuta)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	count(idAgendamento) as agendamento ");
		sb.append("FROM ");
		sb.append(" agendamento ");
		sb.append("WHERE ");
		sb.append(" idFuncionarioMassoterapeuta = ? ");
		sb.append(" AND idStatusAgendamento in (1) ");
		sb.append(" AND dataAgendamento >= curdate() ");

		PreparedStatement pst;
		ResultSet rs;

		int temp = 0;
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, idFuncionarioMassoterapeuta);

			rs = pst.executeQuery();

			while (rs.next())
			{
				temp = Integer.parseInt(rs.getString("agendamento"));
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	/*public String updatePlantonista(String idFuncionario, String plantonista)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append("	funcionarios as f ");
		sb.append(" join agendamento as a ");
		sb.append("	on a.idFuncionarioMassoterapeuta = f.idFuncionario ");
		sb.append("SET ");
		sb.append(" f.plantonista = ?, ");
		sb.append(" a.idStatusAgendamento = CASE WHEN a.idStatusAgendamento = 4 then 3 else 4 END ");
		sb.append("WHERE ");
		sb.append(" f.idFuncionario = ? ");

		PreparedStatement pst;
		String ret = "";

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, plantonista);
			pst.setString(2, idFuncionario);

			int temp = pst.executeUpdate();

			ret = (temp == 2) ? "sucesso" : "erro";

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (ret);
	}*/

	public int getValidaAgendamento(String idFuncionarioMassoterapeuta)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	count(idAgendamento) as agendamento ");
		sb.append("FROM ");
		sb.append(" agendamento ");
		sb.append("WHERE ");
		sb.append(" idFuncionarioMassoterapeuta = ? ");
		sb.append(" AND idStatusAgendamento in (1) ");

		PreparedStatement pst;
		ResultSet rs;

		int temp = 0;
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, idFuncionarioMassoterapeuta);

			rs = pst.executeQuery();

			while (rs.next())
			{
				temp = Integer.parseInt(rs.getString("agendamento"));
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	public Map<Integer, String> getMasso()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	idFuncionario, ");
		sb.append(" nomeFuncionario ");
		sb.append("FROM ");
		sb.append(" funcionarios ");
		sb.append("WHERE ");
		sb.append(" tipoFuncionario = 'M' ");
		sb.append(" and StatusFuncionario = 'A' ");
		//sb.append(" and plantonista = 'N' ");
		sb.append("ORDER BY ");
		sb.append(" nomeFuncionario");

		PreparedStatement pst;
		ResultSet rs;
		Map<Integer, String> listaCancelamento = new LinkedHashMap<Integer, String>();

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				listaCancelamento.put(rs.getInt("idFuncionario"), rs.getString("nomeFuncionario"));
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (listaCancelamento);
	}

	public String updateCancelamento(int idFuncionarioMassoterapeuta, String dataAgendamento, String idHorario)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append("	agendamento ");
		sb.append("SET ");
		sb.append(" idStatusAgendamento = 5, ");
		sb.append(" idFuncionario = null ");
		sb.append("WHERE ");
		sb.append(" idFuncionarioMassoterapeuta = ? ");
		sb.append(" and dataAgendamento = ? ");
		
		if(idHorario != "")
		{
			sb.append(" and idHorario = ? ");			
		}
		
		sb.append(" and idStatusAgendamento in (1,4) ");

		PreparedStatement pst;
		String ret = "";

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionarioMassoterapeuta);
			pst.setString(2, dataAgendamento);
			
			if(idHorario != "")
			{
				pst.setString(3, idHorario);			
			}

			int temp = pst.executeUpdate();

			ret = (temp > 0) ? "sucesso" : "erro";

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (ret);
	}
	public String updateCancelamentoDia(int idFuncionarioMassoterapeuta, String dataAgendamento, String idHorario)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append("	agendamento ");
		sb.append("SET ");
		sb.append(" idStatusAgendamento = 3, ");
		sb.append(" idFuncionario = null ");
		sb.append("WHERE ");
		sb.append(" idFuncionarioMassoterapeuta = ? ");
		sb.append(" and dataAgendamento = ? ");
		
		if(idHorario != "")
		{
			sb.append(" and idHorario = ? ");			
		}
		
		sb.append(" and idStatusAgendamento in (1,4) ");

		PreparedStatement pst;
		String ret = "";

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionarioMassoterapeuta);
			pst.setString(2, dataAgendamento);
			
			if(idHorario != "")
			{
				pst.setString(3, idHorario);			
			}

			int temp = pst.executeUpdate();

			ret = (temp > 0) ? "sucesso" : "erro";

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (ret);
	}
	public String updateReversao(String idFuncionarioMassoterapeuta, String dataAgendamento)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append("	agendamento ");
		sb.append("SET ");
		sb.append(" idStatusAgendamento = 4 ");
		sb.append("WHERE ");
		sb.append(" idFuncionarioMassoterapeuta = ? ");
		sb.append(" and dataAgendamento = ? ");
		sb.append(" and idStatusAgendamento = 3 ");

		PreparedStatement pst;
		String ret = "";

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, idFuncionarioMassoterapeuta);
			pst.setString(2, dataAgendamento);

			int temp = pst.executeUpdate();

			ret = (temp > 0) ? "sucesso" : "erro";

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (ret);
	}

	public int getValidaMassoterapeuta(String idMassoterapeuta)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" count(*) as total ");
		sb.append("FROM ");
		sb.append(" funcionarios ");
		sb.append("WHERE ");
		sb.append(" idFuncionario = ? ");
		sb.append(" and tipoFuncionario = 'M' ");

		PreparedStatement pst;
		ResultSet rs;

		int total = 0;
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, idMassoterapeuta);
			rs = pst.executeQuery();

			while (rs.next())
			{
				total = Integer.parseInt(rs.getString("total"));
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (total);
	}

	public ArrayList<UsuarioBean> validaLogin(String matricula, String senha)
	{
		ArrayList<UsuarioBean> arrayList = new ArrayList<UsuarioBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" f.idFuncionario, ");
		sb.append(" f.matricula, ");
		sb.append(" f.nomeFuncionario, ");
		sb.append(" f.ramalFuncionario, ");
		sb.append(" pf.idPermissao ");
		sb.append("FROM ");
		sb.append(" funcionarios as f ");
		sb.append(" join permissaofuncionario as pf ");
		sb.append(" on f.idFuncionario = pf.idFuncionario ");
		sb.append("WHERE ");
		sb.append(" f.matricula = ? and ");
		sb.append(" f.senha = md5(?) ");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, matricula);
			pst.setString(2, senha);
			rs = pst.executeQuery();

			while (rs.next())
			{
				UsuarioBean usuarioBean = new UsuarioBean();
				usuarioBean.setIdFuncionario(rs.getInt("idFuncionario"));
				usuarioBean.setNomeFuncionario(rs.getString("nomeFuncionario"));
				usuarioBean.setMatricula(rs.getInt("matricula"));
				usuarioBean.setRamalFuncionario(rs.getInt("ramalFuncionario"));
				usuarioBean.setIdPermissao(rs.getInt("idPermissao"));

				arrayList.add(usuarioBean);
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return(arrayList);
	}

	public int validaPrimeiroAcesso(String matricula)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" count(*) as total ");
		sb.append("FROM ");
		sb.append(" funcionarios ");
		sb.append("WHERE ");
		sb.append(" matricula = ? ");
		sb.append(" and senha = '' ");

		PreparedStatement pst;
		ResultSet rs;

		int temp = 0;
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, matricula);
			rs = pst.executeQuery();

			while (rs.next())
			{
				temp = Integer.parseInt(rs.getString("total"));
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	public int atualizaSenha(String matricula, String senha)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append("	funcionarios ");
		sb.append("SET ");
		sb.append(" senha = md5(?) ");
		sb.append("WHERE ");
		sb.append(" matricula = ? ");

		PreparedStatement pst;

		int temp = 0;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, senha);
			pst.setString(2, matricula);

			temp = pst.executeUpdate();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	public int salvaDadosCancelamento(String idHorario, String matriculaMassoterapeuta, String data)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO cancelamentos ");
		sb.append("	(idFuncionario, idFuncionarioMassoterapeuta, dataAgendamento, idHorario) ");
		sb.append("SELECT ");
		sb.append(" a.idFuncionario, idFuncionarioMassoterapeuta, dataAgendamento, idHorario ");
		sb.append("FROM ");
		sb.append(" funcionarios as f join agendamento as a ");
		sb.append(" on f.idFuncionario = a.idFuncionarioMassoterapeuta ");
		sb.append("WHERE ");
		sb.append(" f.matricula = ? ");
		sb.append(" and a.idHorario = ? ");
		sb.append(" and dataAgendamento = ? ");

		PreparedStatement pst;

		int temp = 0;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, matriculaMassoterapeuta);
			pst.setString(2, idHorario);
			pst.setString(3, data);

			temp = pst.executeUpdate();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	public int gravaCancelamento(String idHorario, String matriculaMassoterapeuta, String data)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append(" funcionarios as f join agendamento as a ");
		sb.append(" on f.idFuncionario = a.idFuncionarioMassoterapeuta ");
		sb.append("SET ");
		sb.append(" a.idFuncionario = null, ");
		sb.append(" a.idStatusAgendamento = 4 ");
		sb.append("WHERE ");
		sb.append(" f.matricula = ? ");
		sb.append(" and a.idHorario = ? ");
		sb.append(" and dataAgendamento = ? ");

		PreparedStatement pst;

		int temp = 0;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, matriculaMassoterapeuta);
			pst.setString(2, idHorario);
			pst.setString(3, data);

			temp = pst.executeUpdate();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	public String validaRegraHorario(String dataConvertida, String idHorario)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" case when ");
		sb.append("		(select concat(?, ' ', (select descricaoHorario from horario where idHorario = ?)) < now()) ");
		sb.append(" then 'N' else 'S' end as regra; ");

		PreparedStatement pst;
		ResultSet rs;

		String temp = "";
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, dataConvertida);
			pst.setString(2, idHorario);
			rs = pst.executeQuery();

			while (rs.next())
			{
				temp = rs.getString("regra");
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	public String getDataHoraAgendamento(String matriculaFuncionario, String[] arrayDatasSemanaAtual)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" concat(dataAgendamento,' às ',h.descricaoHorario) as dataHora ");
		sb.append("FROM ");
		sb.append(" agendamento as a ");
		sb.append(" join horario as h ");
		sb.append(" on h.idHorario = a.idHorario ");
		sb.append("WHERE ");
		sb.append(" idStatusAgendamento in (1,2) ");
		sb.append(" and idFuncionario = (select idFuncionario from funcionarios where matricula = ?) ");
		sb.append(" and dataAgendamento between ? and ? ");
		sb.append("ORDER BY dataAgendamento >= curdate() ");

		PreparedStatement pst;
		ResultSet rs;

		String temp = "";
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, matriculaFuncionario);
			pst.setString(2, arrayDatasSemanaAtual[0]);
			pst.setString(3, arrayDatasSemanaAtual[1]);

			rs = pst.executeQuery();

			while (rs.next())
			{
				temp = rs.getString("dataHora");
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return (temp);
	}

	public ArrayList<PlantonistaBean> mostraPlantonista(String idFuncionario)
	{
		ArrayList<PlantonistaBean> arrayList = new ArrayList<PlantonistaBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	f.idFuncionario, ");
		sb.append(" p.idMeses, ");
		sb.append(" m.meses ");
		sb.append("FROM ");
		sb.append(" funcionarios as f ");
		sb.append(" join plantonista as p ");
		sb.append(" on f.idFuncionario = p.idFuncionario ");
		sb.append(" join meses as m ");
		sb.append(" on m.idMeses = p.idMeses ");
		sb.append("WHERE ");
		sb.append(" f.tipoFuncionario = 'M' ");
		sb.append(" and f.StatusFuncionario = 'A' ");
		sb.append(" and p.idFuncionario = ? ");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, idFuncionario);
			rs = pst.executeQuery();

			while (rs.next())
			{
				PlantonistaBean plantonistaBean = new PlantonistaBean();
				plantonistaBean.setIdFuncionario(rs.getInt("idFuncionario"));
				plantonistaBean.setIdMeses(rs.getInt("idMeses"));
				plantonistaBean.setMeses(rs.getString("meses"));

				arrayList.add(plantonistaBean);
			}
			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		return(arrayList);
	}

	public int inserePlantonista(String idFuncionario, String idMeses)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO plantonista ");
		sb.append("	(idFuncionario, idMeses) ");
		sb.append("VALUES ");
		sb.append(" (?, ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, idFuncionario);
			pst.setString(2, idMeses);

			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	public int deletaPlantonista( String idFuncionario, String idMeses)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("DELETE FROM plantonista ");
		sb.append("WHERE idFuncionario = ? AND idMeses = ? ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, idFuncionario);
			pst.setString(2, idMeses);

			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	/*public int alteraStatusPlantonista(String plantonista, String idFuncionario)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE funcionarios ");
		sb.append("SET plantonista = ? ");
		sb.append("WHERE idFuncionario = ? ");

		PreparedStatement pst;

		int temp = 0;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, plantonista);
			pst.setString(2, idFuncionario);

			temp = pst.executeUpdate();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}*/

	public int getMensagemPlantonista(int local, String turno)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	count(*) as total ");
		sb.append("FROM");
		sb.append(" local as l ");
		sb.append(" join funcionarios as f ");
		sb.append(" on l.idLocal = f.idLocal ");
		sb.append(" join agendamento as a ");
		sb.append(" on f.idFuncionario = a.idFuncionarioMassoterapeuta ");
		sb.append(" join horario as h ");
		sb.append(" on a.idHorario = h.idHorario ");
		sb.append("WHERE");
		sb.append(" f.statusFuncionario = 'A' ");
		sb.append(" and l.idLocal = ? ");
		sb.append(" and f.turnoHorario = ? ");
		sb.append(" and a.idStatusAgendamento = 5 ");

		PreparedStatement pst;
		ResultSet rs;

		int temp = 0;
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, local);
			pst.setString(2, turno);			

			rs = pst.executeQuery();

			while (rs.next())
			{
				temp = Integer.parseInt(rs.getString("total"));
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	
	public int ValidaCancelamento(String data, String idHorario)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" case when ");
		sb.append("		(select concat(?, ' ', (select descricaoHorario from horario where idHorario = ?)) > now()) ");
		sb.append(" then 1 else 2 end as regra; ");

		PreparedStatement pst;
		ResultSet rs;
	
		int temp = -1;
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, data);
			pst.setString(2, idHorario);
			rs = pst.executeQuery();

			while (rs.next())
			{
				temp = rs.getInt("regra");
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return (temp);
	}
	
	public int validaQuestionario(int idFuncionario)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" count(distinct(idFuncionario)) as total ");
		sb.append("FROM ");
		sb.append(" questionario ");
		sb.append("WHERE ");
		sb.append(" idFuncionario = ? ");

		PreparedStatement pst;
		ResultSet rs;
	
		int temp = -1;
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			rs = pst.executeQuery();

			while (rs.next())
			{
				temp = rs.getInt("total");
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}
		
		return (temp);
	}
	public ArrayList<DadosCategoriaBean> getCategoria()
	{
		ArrayList<DadosCategoriaBean> arrayList = new ArrayList<DadosCategoriaBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" idCategoria, ");
		sb.append(" descricaoCategoria ");
		sb.append("FROM ");
		sb.append(" categoria ");
		sb.append("ORDER BY ");
		sb.append(" descricaoCategoria ");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosCategoriaBean dadosCategoriaBean = new DadosCategoriaBean();
				dadosCategoriaBean.setIdCategoria(rs.getInt("idCategoria"));
				dadosCategoriaBean.setDescricaoCategoria(rs.getString("descricaoCategoria"));

				arrayList.add(dadosCategoriaBean);
			}
			
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}

	public ArrayList<DadosPerguntasBean> getPerguntas(int idCategoria)
	{
		ArrayList<DadosPerguntasBean> arrayList = new ArrayList<DadosPerguntasBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" idPerguntas, ");
		sb.append(" perguntas ");
		sb.append("FROM ");
		sb.append(" perguntas ");
		sb.append("WHERE ");
		sb.append(" idCategoria = ? ");
		sb.append(" and questionario = 'S' ");
		sb.append(" and idTipoPergunta = 2 ");
		sb.append("ORDER BY ");
		sb.append(" idPerguntas");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idCategoria);
			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosPerguntasBean dadosPerguntasBean = new DadosPerguntasBean();
				dadosPerguntasBean.setIdPerguntas(rs.getInt("idPerguntas"));
				dadosPerguntasBean.setPerguntas(rs.getString("perguntas"));

				arrayList.add(dadosPerguntasBean);
			}

			objConn.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	public ArrayList<DadosPerguntasBean> getPerguntasOpcao(int idCategoria)
	{
		ArrayList<DadosPerguntasBean> arrayList = new ArrayList<DadosPerguntasBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" idPerguntas, ");
		sb.append(" perguntas ");
		sb.append("FROM ");
		sb.append(" perguntas ");
		sb.append("WHERE ");
		sb.append(" idCategoria = ? ");
		sb.append(" and questionario = 'S' ");
		sb.append(" and idTipoPergunta = 2 ");
		sb.append("ORDER BY ");
		sb.append(" idPerguntas");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idCategoria);
			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosPerguntasBean dadosPerguntasBean = new DadosPerguntasBean();
				dadosPerguntasBean.setIdPerguntas(rs.getInt("idPerguntas"));
				dadosPerguntasBean.setPerguntas(rs.getString("perguntas"));

				arrayList.add(dadosPerguntasBean);
			}

			objConn.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	public ArrayList<DadosPerguntasBean> getPerguntasQuestionario(int idCategoria)
	{
		ArrayList<DadosPerguntasBean> arrayList = new ArrayList<DadosPerguntasBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" idPerguntas, ");
		sb.append(" perguntas ");
		sb.append("FROM ");
		sb.append(" perguntas ");
		sb.append("WHERE ");
		sb.append(" idCategoria = ? ");
		sb.append(" and questionario = 'S' ");
		sb.append(" and idTipoPergunta = 2 ");
		sb.append("ORDER BY ");
		sb.append(" perguntas");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idCategoria);
			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosPerguntasBean dadosPerguntasBean = new DadosPerguntasBean();
				dadosPerguntasBean.setIdPerguntas(rs.getInt("idPerguntas"));
				dadosPerguntasBean.setPerguntas(rs.getString("perguntas"));

				arrayList.add(dadosPerguntasBean);
			}

			objConn.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	public ArrayList<DadosPerguntasOpcaoBean> getPerguntasOpcao()
	{
		ArrayList<DadosPerguntasOpcaoBean> arrayList = new ArrayList<DadosPerguntasOpcaoBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" idPerguntas, ");
		sb.append(" perguntas ");
		sb.append("FROM ");
		sb.append(" perguntas ");
		sb.append("WHERE ");
		sb.append(" idPerguntas = 12 ");
/*		sb.append(" and questionario = 'S' ");
		sb.append(" and idTipoPergunta = 5 ");*/

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosPerguntasOpcaoBean dadosPerguntasOpcaoBean = new DadosPerguntasOpcaoBean();
				dadosPerguntasOpcaoBean.setIdPerguntas(rs.getInt("idPerguntas"));
				dadosPerguntasOpcaoBean.setPerguntas(rs.getString("perguntas"));

				arrayList.add(dadosPerguntasOpcaoBean);
			}

			objConn.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	public ArrayList<DadosPerguntasOpcaoBean> getPerguntasRadio()
	{
		ArrayList<DadosPerguntasOpcaoBean> arrayList = new ArrayList<DadosPerguntasOpcaoBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" idPerguntas, ");
		sb.append(" perguntas ");
		sb.append("FROM ");
		sb.append(" perguntas ");
		sb.append("WHERE ");
		sb.append(" idPerguntas = 11 ");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosPerguntasOpcaoBean dadosPerguntasOpcaoBean = new DadosPerguntasOpcaoBean();
				dadosPerguntasOpcaoBean.setIdPerguntas(rs.getInt("idPerguntas"));
				dadosPerguntasOpcaoBean.setPerguntas(rs.getString("perguntas"));

				arrayList.add(dadosPerguntasOpcaoBean);
			}

			objConn.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	public ArrayList<DadosPerguntasOpcaoBean> getPerguntas5()
	{
		ArrayList<DadosPerguntasOpcaoBean> arrayList = new ArrayList<DadosPerguntasOpcaoBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" idPerguntas, ");
		sb.append(" perguntas ");
		sb.append("FROM ");
		sb.append(" perguntas ");
		sb.append("WHERE ");
		sb.append(" idPerguntas = 5 ");
		sb.append(" and questionario = 'S' ");
		sb.append(" and idTipoPergunta = 2 ");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosPerguntasOpcaoBean dadosPerguntasOpcaoBean = new DadosPerguntasOpcaoBean();
				dadosPerguntasOpcaoBean.setIdPerguntas(rs.getInt("idPerguntas"));
				dadosPerguntasOpcaoBean.setPerguntas(rs.getString("perguntas"));

				arrayList.add(dadosPerguntasOpcaoBean);
			}

			objConn.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	public ArrayList<DadosPerguntasOpcaoBean> getPerguntasCheckbox()
	{
		ArrayList<DadosPerguntasOpcaoBean> arrayList = new ArrayList<DadosPerguntasOpcaoBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" idPerguntas, ");
		sb.append(" perguntas ");
		sb.append("FROM ");
		sb.append(" perguntas ");
		sb.append("WHERE ");
		sb.append(" idPerguntas = 10 ");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosPerguntasOpcaoBean dadosPerguntasOpcaoBean = new DadosPerguntasOpcaoBean();
				dadosPerguntasOpcaoBean.setIdPerguntas(rs.getInt("idPerguntas"));
				dadosPerguntasOpcaoBean.setPerguntas(rs.getString("perguntas"));

				arrayList.add(dadosPerguntasOpcaoBean);
			}

			objConn.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	public ArrayList<DadosRespostasBean> getRespostasOpcao(int idRespostas)
	{
		ArrayList<DadosRespostasBean> arrayList = new ArrayList<DadosRespostasBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" idRespostas, ");
		sb.append(" respostas ");
		sb.append("FROM ");
		sb.append(" respostas ");
		sb.append("WHERE ");
		sb.append(" idRespostas in (11,12,13) ");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosRespostasBean dadosRespostasBean = new DadosRespostasBean();
				dadosRespostasBean.setIdRespostas(rs.getInt("idRespostas"));
				dadosRespostasBean.setRespostas(rs.getString("respostas"));

				arrayList.add(dadosRespostasBean);
			}

			objConn.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	public ArrayList<DadosRespostasBean> getRespostasRadio(int idRespostas)
	{
		ArrayList<DadosRespostasBean> arrayList = new ArrayList<DadosRespostasBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" idRespostas, ");
		sb.append(" respostas ");
		sb.append("FROM ");
		sb.append(" respostas ");
		sb.append("WHERE ");
		sb.append(" idRespostas in (1,2,3,4,5) ");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosRespostasBean dadosRespostasBean = new DadosRespostasBean();
				dadosRespostasBean.setIdRespostas(rs.getInt("idRespostas"));
				dadosRespostasBean.setRespostas(rs.getString("respostas"));

				arrayList.add(dadosRespostasBean);
			}

			objConn.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	public ArrayList<DadosRespostasBean> getRespostasCheckbox(int idRespostas)
	{
		ArrayList<DadosRespostasBean> arrayList = new ArrayList<DadosRespostasBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" idRespostas, ");
		sb.append(" respostas ");
		sb.append("FROM ");
		sb.append(" respostas ");
		sb.append("WHERE ");
		sb.append(" idRespostas in (6,7,8,9,10) ");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosRespostasBean dadosRespostasBean = new DadosRespostasBean();
				dadosRespostasBean.setIdRespostas(rs.getInt("idRespostas"));
				dadosRespostasBean.setRespostas(rs.getString("respostas"));

				arrayList.add(dadosRespostasBean);
			}

			objConn.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	public ArrayList<DadosRespostasBean> getRespostas5()
	{
		ArrayList<DadosRespostasBean> arrayList = new ArrayList<DadosRespostasBean>();

		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" idRespostas, ");
		sb.append(" respostas ");
		sb.append("FROM ");
		sb.append(" respostas ");
		sb.append("WHERE ");
		sb.append(" idRespostas in (14,15,16,13) ");

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosRespostasBean dadosRespostasBean = new DadosRespostasBean();
				dadosRespostasBean.setIdRespostas(rs.getInt("idRespostas"));
				dadosRespostasBean.setRespostas(rs.getString("respostas"));

				arrayList.add(dadosRespostasBean);
			}

			objConn.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	public int gravarAvaliacao(int idAgendamento, String score, String obs, String comparecimento)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO avaliacao ");
		sb.append("	(idAgendamento, idPerguntas, idPontuacao, data, obs, comparecimento) ");
		sb.append("VALUES ");
		sb.append(" (?, 13, ?, now(), ?, ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idAgendamento);
			pst.setString(2, score);
			pst.setString(3, obs);
			pst.setString(4, comparecimento);
			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionario(int idFuncionario, int idPergunta, String score)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO questionario ");
		sb.append("	(idFuncionario, idPerguntas, idPontuacao, data) ");
		sb.append("VALUES ");
		sb.append(" (?, ?, ?, now()) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, idPergunta+1);
			pst.setString(3, score);
			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionarioPergunta2(int idFuncionario, String resposta)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO questionario ");
		sb.append("	(idFuncionario, idPerguntas, data, idRespostas) ");
		sb.append("VALUES ");
		sb.append(" (?, 10, now(), ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, Integer.parseInt(resposta));

			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionarioPergunta4(int idFuncionario, int idRespostas4)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO questionario ");
		sb.append("	(idFuncionario, idPerguntas, data, idRespostas) ");
		sb.append("VALUES ");
		sb.append(" (?, 8, now(), ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, idRespostas4);

			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionarioPergunta5(int idFuncionario, int idRespostas5)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO questionario ");
		sb.append("	(idFuncionario, idPerguntas, data, idRespostas) ");
		sb.append("VALUES ");
		sb.append(" (?, 7, now(), ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, idRespostas5);

			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionarioPergunta3(int idFuncionario, int resposta31)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO questionario ");
		sb.append("	(idFuncionario, idPerguntas, data, idRespostas) ");
		sb.append("VALUES ");
		sb.append(" (?, 9, now(), ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, resposta31);
			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionarioPergunta(int idFuncionario, int idRespostas)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO questionario ");
		sb.append("	(idFuncionario, idPerguntas, data, idRespostas) ");
		sb.append("VALUES ");
		sb.append(" (?, 12, now(), ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, idRespostas);
			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionarioPergunta1(int idFuncionario, int idRespostas2)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO questionario ");
		sb.append("	(idFuncionario, idPerguntas, data, idRespostas) ");
		sb.append("VALUES ");
		sb.append(" (?, 11, now(), ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, idRespostas2);
			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionarioPerguntaAV1(int idFuncionario, String resposta, int i)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO questionario ");
		sb.append("	(idFuncionario, idPerguntas, data, idRespostas, codigoFuncionario) ");
		sb.append("VALUES ");
		sb.append(" (?, 1, now(), ?, ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, Integer.parseInt(resposta));
			pst.setInt(3, i);
			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionarioPerguntaAV2(int idFuncionario, String resposta, int i)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO questionario ");
		sb.append("	(idFuncionario, idPerguntas, data, idRespostas, codigoFuncionario) ");
		sb.append("VALUES ");
		sb.append(" (?, 2, now(), ?, ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, Integer.parseInt(resposta));
			pst.setInt(3, i);
			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionarioPerguntaAV3(int idFuncionario, String resposta, int i)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO questionario ");
		sb.append("	(idFuncionario, idPerguntas, data, idRespostas, codigoFuncionario) ");
		sb.append("VALUES ");
		sb.append(" (?, 3, now(), ?, ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, Integer.parseInt(resposta));
			pst.setInt(3, i);
			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionarioPerguntaAV4(int idFuncionario, String resposta, int i)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO questionario ");
		sb.append("	(idFuncionario, idPerguntas, data, idRespostas, codigoFuncionario) ");
		sb.append("VALUES ");
		sb.append(" (?, 4, now(), ?, ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, Integer.parseInt(resposta));
			pst.setInt(3, i);
			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionarioPerguntaAV5(int idFuncionario, String resposta, int i)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO questionario ");
		sb.append("	(idFuncionario, idPerguntas, data, idRespostas, codigoFuncionario) ");
		sb.append("VALUES ");
		sb.append(" (?, 5, now(), ?, ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, Integer.parseInt(resposta));
			pst.setInt(3, i);
			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionarioPerguntaAV6(int idFuncionario, String resposta, int i)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO questionario ");
		sb.append("	(idFuncionario, idPerguntas, data, idRespostas, codigoFuncionario) ");
		sb.append("VALUES ");
		sb.append(" (?, 6, now(), ?, ?) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, Integer.parseInt(resposta));
			pst.setInt(3, i);
			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int gravarQuestionarioPerguntaAV7(int idFuncionario, String obs)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("INSERT INTO observacoes ");
		sb.append("	(idFuncionario, idPerguntas, observacoes, data) ");
		sb.append("VALUES ");
		sb.append(" (?, 14, ?, now()) ");

		PreparedStatement pst;
		int temp = -1;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setString(2, obs);
			
			temp = pst.executeUpdate();

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	public int getAvaliacaoPendente(int idFuncionario)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" a.idAgendamento ");
		sb.append("FROM ");
		sb.append(" agendamento as a ");
		sb.append(" left join avaliacao as av ");
		sb.append(" on av.idAgendamento = a.idAgendamento ");
		sb.append("WHERE ");
		sb.append(" idFuncionario = ? ");
		sb.append(" and a.idStatusAgendamento = 2 ");
		sb.append(" and av.idAvaliacao is null");

		PreparedStatement pst;
		ResultSet rs;

		int temp = 0;
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			rs = pst.executeQuery();

			while (rs.next())
			{
				temp = rs.getInt("idAgendamento");
			}

			objConn.close();
			
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}

	public ArrayList<ArrayList<String>> relatorioFeedBack(String idMassoterapeuta, String inicio, String fim)
	{
		System.out.println(inicio+" "+fim);
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	date_format(dataAgendamento, '%d/%m/%Y') as data, ");
		sb.append("	f.nomeFuncionario as funcionario, fm.nomeFuncionario as massoterapeuta, ");
		sb.append("	perguntas, descricaoCategoria, descricaoPontuacao, obs ");
		sb.append("FROM ");
		sb.append("	avaliacao as av ");
		sb.append("	join agendamento as a on av.idAgendamento = a.idAgendamento ");
		sb.append("	join funcionarios as f on a.idFuncionario = f.idFuncionario ");
		sb.append("	join funcionarios as fm on a.idFuncionarioMassoterapeuta = fm.idFuncionario ");
		sb.append("	join perguntas as pe on av.idPerguntas = pe.idPerguntas ");
		sb.append("	join categoria as c on pe.idCategoria = c.idCategoria ");
		sb.append("	join pontuacao as po on av.idPontuacao = po.idPontuacao ");
		sb.append("WHERE ");
		sb.append(" (1=1) ");

		if (!idMassoterapeuta.equals(""))
		{
			sb.append(" and a.idFuncionarioMassoterapeuta = "+idMassoterapeuta);
		}

		if (!inicio.equals("") || !fim.equals(""))
		{
			sb.append(" and a.dataAgendamento between '"+inicio+"' and '"+fim+"'");
		}
		PreparedStatement pst;
		ResultSet rs = null;

		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> l = null;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());

			rs = pst.executeQuery();

			while (rs.next())
			{
				l = new ArrayList<String>();
				l.add(rs.getString("data"));
				l.add(rs.getString("funcionario"));
				l.add(rs.getString("massoterapeuta"));
				l.add(rs.getString("perguntas"));
				l.add(rs.getString("descricaoPontuacao"));
				l.add(rs.getString("obs"));
				list.add(l);
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (list);
	}
	
	public ArrayList<DadosMassoterapeutasBean> getDadosMassoterapeutas()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	idFuncionario, ");
		sb.append("	nomeFuncionario as nomeFuncionarioMassoterapeuta ");
		sb.append("FROM ");
		sb.append(" funcionarios ");
		sb.append("WHERE ");
		sb.append(" tipoFuncionario = 'M' ");

		PreparedStatement pst;
		ResultSet rs = null;

		ArrayList<DadosMassoterapeutasBean> arrayList = new ArrayList<DadosMassoterapeutasBean>();
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();
			
			while (rs.next())
			{
				DadosMassoterapeutasBean dadosMassoterapeutasBean = new DadosMassoterapeutasBean();
				dadosMassoterapeutasBean.setIdFuncionario(rs.getInt("idFuncionario"));
				dadosMassoterapeutasBean.setNomeFuncionarioMassoterapeuta(rs.getString("nomeFuncionarioMassoterapeuta"));
				arrayList.add(dadosMassoterapeutasBean);
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	public Map<Integer, String> getFuncionario()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	idFuncionario, ");
		sb.append("	nomeFuncionario ");
		sb.append("FROM ");
		sb.append(" funcionarios ");
		sb.append("ORDER BY");
		sb.append(" nomeFuncionario");

		PreparedStatement pst;
		ResultSet rs;

		Map<Integer, String> lista = new HashMap<Integer, String>();

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			rs = pst.executeQuery();

			while (rs.next())
			{
				lista.put(rs.getInt("idFuncionario"), rs.getString("nomeFuncionario"));
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (lista);
	}
	public String updatePermissao(int permissao, int idFuncionario)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("UPDATE ");
		sb.append("	permissaofuncionario ");
		sb.append("SET ");
		sb.append(" idPermissao = ? ");
		sb.append("WHERE ");
		sb.append(" idFuncionario = ? ");

		PreparedStatement pst;
		String ret = "";
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setInt(1, idFuncionario);
			pst.setInt(2, permissao);

			int temp = pst.executeUpdate();
			ret = (temp == 1) ? "sucesso" : "erro";

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (ret);
	}
	public ArrayList<DadosAgendamentoBean> selecionaHorario(int idFuncionarioMassoterapeuta, String dataAgendamento)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	h.idHorario, concat( h.descricaoHorario,' - ', ");
		sb.append("	case when a.idStatusAgendamento = 5 || a.idStatusAgendamento = 3 ");
		sb.append("	then IFNULL(f.nomeFuncionario, 'Horario Cancelado pelo ADM.') ");
		sb.append("	else IFNULL(f.nomeFuncionario, 'Vago') end ) as dadosCliente ");
		sb.append("FROM ");
		sb.append(" agendamento as a ");
		sb.append(" left join funcionarios as f on a.idFuncionario = f.idFuncionario ");
		sb.append(" left join horario as h on a.idHorario = h.idHorario ");
		sb.append(" join funcionarios as fm on a.idFuncionarioMassoterapeuta = fm.idFuncionario ");
		sb.append("WHERE ");
		sb.append(" a.dataAgendamento = ? ");
		sb.append(" and a.idFuncionarioMassoterapeuta = ? ");

		ArrayList<DadosAgendamentoBean> arrayList = new ArrayList<DadosAgendamentoBean>();

		PreparedStatement pst;
		ResultSet rs;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());
			pst.setString(1, dataAgendamento);
			pst.setInt(2, idFuncionarioMassoterapeuta);
			
			rs = pst.executeQuery();

			while (rs.next())
			{
				DadosAgendamentoBean dadosAgendamentoBean = new DadosAgendamentoBean();
				dadosAgendamentoBean.setIdHorario(rs.getInt("idHorario"));
				dadosAgendamentoBean.setDataAgendamento(rs.getString("dadosCliente"));
				
				arrayList.add(dadosAgendamentoBean);
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (arrayList);
	}
	
	public String validaFuncionarioCancelamento( String dataAgendamento, String idHorario, int idMassoterapeuta)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append(" f.nomeFuncionario ");
		sb.append("FROM ");
		sb.append(" agendamento as a ");
		sb.append(" join funcionarios as f ");
		sb.append(" on a.idFuncionario = f.idFuncionario ");
		sb.append("WHERE ");
		sb.append(" a.dataAgendamento = ? ");
		sb.append(" and a.idHorario = ? ");
		sb.append(" and a.idFuncionarioMassoterapeuta = ? ");

		PreparedStatement pst;
		ResultSet rs;

		String temp = "";
		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());

			pst.setString(1, dataAgendamento);
			pst.setString(2, idHorario);
			pst.setInt(3, idMassoterapeuta);

			rs = pst.executeQuery();

			while (rs.next())
			{
				temp = rs.getString("nomeFuncionario");
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (temp);
	}
	
	public ArrayList<ArrayList<String>> relatorioCampanha()
	{
		StringBuilder sb = new StringBuilder();
		sb.append("SELECT ");
		sb.append("	date_format(q.data, '%d/%m/%Y') as data, f.nomeFuncionario as funcionario, ");
		sb.append("	per.perguntas as pergunta, m.nomeMassoterapeuta as massoterapeuta, r.respostas as resposta ");
		sb.append("FROM ");
		sb.append("	questionario as q ");
		sb.append("	left join funcionarios as f on q.idFuncionario = f.idFuncionario ");
		sb.append("	left join perguntas as per on q.idPerguntas = per.idPerguntas ");
		sb.append("	left join respostas as r on q.idRespostas = r.idRespostas ");
		sb.append("	left join massoterapeuta as m on m.codigoFuncionario = q.codigoFuncionario ");
		
		PreparedStatement pst;
		ResultSet rs = null;

		ArrayList<ArrayList<String>> list = new ArrayList<ArrayList<String>>();
		ArrayList<String> l = null;

		try
		{
			objConn = new Conexao().abreConn();
			pst = objConn.prepareStatement(sb.toString());

			rs = pst.executeQuery();

			while (rs.next())
			{
				l = new ArrayList<String>();
				l.add(rs.getString("data"));
				l.add(rs.getString("funcionario"));
				l.add(rs.getString("pergunta"));
				l.add(rs.getString("massoterapeuta"));
				l.add(rs.getString("resposta"));
				list.add(l);
			}

			pst.close();
			objConn.close();
		}
		catch (SQLException e)
		{
			e.printStackTrace();
		}

		return (list);
	}
}