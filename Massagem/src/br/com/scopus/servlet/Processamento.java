package br.com.scopus.servlet;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import br.com.scopus.bean.DadosAgendamentoBean;
import br.com.scopus.bean.DadosPerguntasOpcaoBean;
import br.com.scopus.bean.DadosRespostasBean;
import br.com.scopus.bean.HorarioBean;
import br.com.scopus.consultas.Persistencia;
import br.com.scopus.validacao.Regras;

@WebServlet("/Processamento")
public class Processamento extends HttpServlet
{
	private static final long serialVersionUID = 1L;

	public Processamento()
	{
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(false);

		if (request.getParameter("id") == null)
		{
			session.setAttribute("error", "ErroID");
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		else
		{
			String retornoJson = "";

			Persistencia objPers = new Persistencia();

			/**
			 * Monta a combo de Locais quando a aplicação carregada
			 */
			if (request.getParameter("id").equals("1"))
			{
				retornoJson = new Gson().toJson(objPers.getLocal());
			}
			//Atualiza tabela com agendamentos
			else if (request.getParameter("id").equals("2"))
			{
				if (request.getParameter("cmbLocal") != null)
				{
					String local = request.getParameter("cmbLocal");
					char ckbTurno = (request.getParameter("ckbTurnoM").equals("true")) ? 'M' : 'T';

					String dataConvertida = converteDataMysql(request.getParameter("data"));

					//dados para montar cabecalho tabela
					ArrayList<HorarioBean> lista = objPers.getHorario(ckbTurno, true);

					ArrayList<DadosAgendamentoBean> listaAgendamento = objPers.getDadosAgendamento(dataConvertida, local, lista.get(0).getIdHorario());

					int mensagemMasso = objPers.getMensagemPlantonista(ckbTurno,local);

					if (listaAgendamento.size() > 0)
					{
						String arrayDados[][] = new String[11][listaAgendamento.size()+1];
						arrayDados[0][0] = "Horário";

						int i = 0;

						for(DadosAgendamentoBean x : listaAgendamento)
						{
							arrayDados[0][++i] = x.getNomeFuncionarioMassoterapeuta() + "_" + x.getMatriculaMassoterapeuta();
						}
						//Fim montagem cabeçalho

						//montar corpo tabela
						lista = objPers.getHorario(ckbTurno, false);

						int linha = 1;
						int coluna = 0;
						for (HorarioBean d : lista)
						{
							listaAgendamento = objPers.getDadosAgendamento(dataConvertida, local, d.getIdHorario());
							
							//System.out.println(listaAgendamento);

							arrayDados[linha][0] = listaAgendamento.get(0).getIdHorario() + "_" + listaAgendamento.get(0).getDescricaoHorario();

							for(DadosAgendamentoBean x : listaAgendamento)
							{
								if (x.getNomeFuncionario().equals(""))
								{
									if(x.getIdStatusAgendamento().equals("5"))
									{
										arrayDados[linha][++coluna] = "Indisponivel";
									}
									else
									{
										arrayDados[linha][++coluna] = "";
									}
								}
								else
								{
									arrayDados[linha][++coluna] = x.getMatriculaFuncionario() + " " + x.getNomeFuncionario();
								}
								
								//System.out.println(x.getNomeFuncionario());
							}

							linha++;
							coluna = 0;
						}
						//fim montagem corpo tabela

						retornoJson = new Gson().toJson(arrayDados);
					}
					//Não tem pré-agendamento feito
					else if (mensagemMasso == 0)
					{
						retornoJson = new Gson().toJson("temPlantonista");	
					}
					else
					{
						retornoJson = new Gson().toJson("erro");
					}
				}
			}
			/**
			 * Método que monta a tela utilizada pelo Massoterapeuta para mostrar os agendamentos
			 */
			else if (request.getParameter("id").equals("3"))
			{
				String idMassoterapeuta = request.getParameter("idMassoterapeuta");

				int validaMasso = objPers.getValidaMassoterapeuta(idMassoterapeuta);

				if (validaMasso == 0)
				{
					retornoJson = new Gson().toJson("codInvalido");
				}
				else
				{
					retornoJson = new Gson().toJson(objPers.getDadosMassagem(idMassoterapeuta));
				}
			}
			/**
			 * Método que monta a tela utilizada pelo Massoterapeuta para mostrar os agendamentos
			 */
			else if (request.getParameter("id").equals("3"))
			{
				String idMassoterapeuta = request.getParameter("idMassoterapeuta");

				int validaMasso = objPers.getValidaMassoterapeuta(idMassoterapeuta);

				if (validaMasso == 0)
				{
					retornoJson = new Gson().toJson("codInvalido");
				}
				else
				{
					retornoJson = new Gson().toJson(objPers.getDadosMassagem(idMassoterapeuta));
				}
			}
			/**
			 * Método que grava agendamento na base de dados
			 */
			else if (request.getParameter("id").equals("4"))
			{
				String array[] = request.getParameter("idTd").split("_");
				String idHorario = array[0];
				String matriculaMassoterapeuta = array[1];

				String matriculaFuncionario = request.getParameter("matricula");

				String dataConvertida = converteDataMysql(request.getParameter("data"));

				Regras regras = new Regras();
				String arrayDatasSemanaAtual[] = regras.getIntervaloDatas(dataConvertida);

				String retRegraHorario = objPers.validaRegraHorario(dataConvertida, idHorario);

				String dataHora = objPers.getDataHoraAgendamento(matriculaFuncionario, arrayDatasSemanaAtual);

				if (!dataHora.equals(""))
				{
					dataHora = converteDataMysqlHora(dataHora);
				}

				String erro = "";
				int retValidacaoRegra = -1;

				if (retRegraHorario.equals("S"))
				{
					retValidacaoRegra = objPers.validaAgendamento(arrayDatasSemanaAtual, matriculaFuncionario);

					if (retValidacaoRegra == 0)
					{
						/**
						 * Controle de concorrencia
						 * 
						 * Se um usuário estiver com a tela aberta antes de outro, pode haver conflito de marcação
						 * Usuário pode alterar agendamento usando ferramentas como firebug.
						 */
						int retRegraFirebug = objPers.validacaoAgendamento(idHorario, dataConvertida, matriculaMassoterapeuta);

						if (retRegraFirebug == 0)
						{
							objPers.gravaDadosAgendamento(idHorario, matriculaMassoterapeuta, matriculaFuncionario, dataConvertida);
						}
						else
						{
							erro = "existeAgendamento";
						}

						//objPers.gravaDadosAgendamento(idHorario, matriculaMassoterapeuta, matriculaFuncionario, dataConvertida);
					}
					else
					{
						erro = "regraAgendamento_" + dataHora;
					}
				}
				else
				{
					erro = "regraHorario";
				}

				retornoJson = (retValidacaoRegra == 0 && retRegraHorario.equals("S")) ? new Gson().toJson("sucesso") : new Gson().toJson(erro);
			}
			else if (request.getParameter("id").equals("5"))
			{
				retornoJson = new Gson().toJson(objPers.getPlantonista());
			}
			else if (request.getParameter("id").equals("6"))
			{
				String idFuncionario = request.getParameter("idFuncionario");
				String erro = "";

				int retValidador = objPers.getValidaPlantonista(idFuncionario);

				retornoJson = (retValidador == 0) ? new Gson().toJson("sucesso") : new Gson().toJson(erro);
			}
			else if (request.getParameter("id").equals("7"))
			{
				retornoJson = new Gson().toJson(objPers.getMasso());
			}
			else if (request.getParameter("id").equals("8"))
			{
				int idFuncionarioMassoterapeuta = Integer.parseInt(request.getParameter("cmbMasso"));
				String dataConvertida = converteDataMysql(request.getParameter("date"));
				String idHorario = "";

				retornoJson = new Gson().toJson(objPers.updateCancelamentoDia(idFuncionarioMassoterapeuta, dataConvertida,idHorario));
			}
			else if (request.getParameter("id").equals("9"))
			{
				String idFuncionarioMassoterapeuta = request.getParameter("cmbMasso");
				String dataConvertida = converteDataMysql(request.getParameter("date"));

				retornoJson = new Gson().toJson(objPers.updateReversao(idFuncionarioMassoterapeuta, dataConvertida));
			}
			else if (request.getParameter("id").equals("10"))
			{
				session.invalidate();
				retornoJson = new Gson().toJson("");
			}
			else if (request.getParameter("id").equals("12"))
			{
				String idHorario = request.getParameter("idHorario");
				String matriculaMassoterapeuta = request.getParameter("matriculaMassoterapeuta");
				String data = converteDataMysql(request.getParameter("data"));

				int ret = -1;

				ret = objPers.salvaDadosCancelamento(idHorario, matriculaMassoterapeuta, data);

				if (ret == 1)
				{
					ret = objPers.gravaCancelamento(idHorario, matriculaMassoterapeuta, data);
				}

				retornoJson = (ret == 1) ? new Gson().toJson("sucesso") : new Gson().toJson("erro");
			}
			else if (request.getParameter("id").equals("13"))
			{
				retornoJson = new Gson().toJson(objPers.getPlantonista());
			}
			else if (request.getParameter("id").equals("14"))
			{
				retornoJson = new Gson().toJson(objPers.mostraPlantonista(request.getParameter("idFuncionario")));
			}
			else if (request.getParameter("id").equals("15"))
			{
				String idFuncionario = request.getParameter("idFuncionario");
				String idMeses = request.getParameter("idMeses");
				String tipoAcao = request.getParameter("tipoAcao");

				int temp = -1;
				String erro = "";

				int retValidador = objPers.getValidaPlantonista(idFuncionario);

				//Não tem agendamento. Pode alterar para plantonista
				if (retValidador == 0)
				{
					temp = gravaDadosPlantonista(tipoAcao, objPers, idFuncionario, idMeses);

					if (temp == 1)
					{
						erro = "Não foi possível alterar o status do Plantonista.";
					}
					else
					{
						erro = "regraPlantonista";
					}
				}

				retornoJson = (temp == 1) ? new Gson().toJson("sucesso") : new Gson().toJson(erro);
			}
			/*else if (request.getParameter("id").equals("16"))
			{
				ArrayList<ArrayList<String>> nodes = new ArrayList<ArrayList<String>>();
				ArrayList<String> nodeList = new ArrayList<String>();

				ArrayList<DadosCategoriaBean> dadosCategoria = objPers.getCategoria();

				for (DadosCategoriaBean dcb : dadosCategoria)
				{
					nodeList.add(String.valueOf(dcb.getIdCategoria()));
					nodeList.add(dcb.getDescricaoCategoria());

					ArrayList<DadosPerguntasBean> dadosPerguntas = objPers.getPerguntas(dcb.getIdCategoria());
					for (DadosPerguntasBean dpb : dadosPerguntas)
					{
						nodeList.add(String.valueOf(dpb.getIdPerguntas()));
						nodeList.add(dpb.getPerguntas());
					}

					nodes.add(nodeList);
					nodeList = new ArrayList<String>();
				}
				retornoJson = new Gson().toJson(nodes);
			}*/
			else if (request.getParameter("id").equals("17"))
			{
				int idAgendamento = (int)session.getAttribute("idAgendamento");
				
				int ret = 0;

				String score = request.getParameter("score");
				String obs = request.getParameter("obs");
				String comparecimento = request.getParameter("comparecimento");
				ret = objPers.gravarAvaliacao(idAgendamento, score, obs, comparecimento);

				if (ret != 1)
				{
					//rollback();
				}
				
				retornoJson = new Gson().toJson("sucesso");
			}
			//Verfica se existe avaliação de massagem pendente de ser respondida
			else if (request.getParameter("id").equals("18"))
			{
				int idFuncionario = (int)session.getAttribute("idFuncionario");
				int ret = objPers.getAvaliacaoPendente(idFuncionario);

				session.setAttribute("idAgendamento", ret);

				retornoJson = new Gson().toJson(ret);
			}
			else if (request.getParameter("id").equals("19"))
			{
				String idHorario = request.getParameter("idHorario");
				String data = converteDataMysql(request.getParameter("date"));
				int retorno = objPers.ValidaCancelamento(data,idHorario);
								
				retornoJson = (retorno == 1) ? new Gson().toJson("sucesso") : new Gson().toJson("erro");									
			}
			//FeedBack
			else if (request.getParameter("id").equals("20"))
			{
				//int idFuncionario = Integer.parseInt(request.getParameter("idFuncionario"));
				String idMassoterapeuta = request.getParameter("idMassoterapeuta");
				String inicio = (!request.getParameter("inicio").equals("")) ? inicio = converteMysqlData(request.getParameter("inicio")) : "";
				String fim = (!request.getParameter("fim").equals("")) ? fim = converteMysqlData(request.getParameter("fim")) : "";
				retornoJson = new Gson().toJson(objPers.relatorioFeedBack(idMassoterapeuta,inicio,fim));					
			}
			//Questionário - Perguntas e respostas com estrelas
			else if (request.getParameter("id").equals("21"))
			{
				int idFuncionarioMassoterapeuta = Integer.parseInt(request.getParameter("cmbMasso"));
				String dataAgendamento = converteDataMysql(request.getParameter("date"));

				retornoJson = new Gson().toJson(objPers.selecionaHorario(idFuncionarioMassoterapeuta, dataAgendamento));		
			}
			//Permissões
			else if (request.getParameter("id").equals("22"))
			{
				retornoJson = new Gson().toJson(objPers.getFuncionario());
			}
			//Pergunta e respostas(sim, não e não tenho opinião)
			else if (request.getParameter("id").equals("23"))
			{
				ArrayList<ArrayList<String>> nodes = new ArrayList<ArrayList<String>>();
				ArrayList<String> nodeList = new ArrayList<String>();

				ArrayList<DadosPerguntasOpcaoBean> dadosCategoria = objPers.getPerguntasOpcao();

				for (DadosPerguntasOpcaoBean dcb : dadosCategoria)
				{
					nodeList.add(String.valueOf(dcb.getIdPerguntas()));
					nodeList.add(dcb.getPerguntas());

					ArrayList<DadosRespostasBean> dadosRespostas = objPers.getRespostasOpcao(dcb.getIdPerguntas());
					for (DadosRespostasBean dpb : dadosRespostas)
					{
						nodeList.add(String.valueOf(dpb.getIdRespostas()));
						nodeList.add(dpb.getRespostas());
					}

					nodes.add(nodeList);
					nodeList = new ArrayList<String>();
				}
				retornoJson = new Gson().toJson(nodes);			
			}
			else if (request.getParameter("id").equals("24"))
			{
				ArrayList<ArrayList<String>> nodes = new ArrayList<ArrayList<String>>();
				ArrayList<String> nodeList = new ArrayList<String>();

				ArrayList<DadosPerguntasOpcaoBean> dadosCategoria = objPers.getPerguntasRadio();

				for (DadosPerguntasOpcaoBean dcb : dadosCategoria)
				{
					nodeList.add(String.valueOf(dcb.getIdPerguntas()));
					nodeList.add(dcb.getPerguntas());

					ArrayList<DadosRespostasBean> dadosRespostas = objPers.getRespostasRadio(dcb.getIdPerguntas());
					for (DadosRespostasBean dpb : dadosRespostas)
					{
						nodeList.add(String.valueOf(dpb.getIdRespostas()));
						nodeList.add(dpb.getRespostas());
					}

					nodes.add(nodeList);
					nodeList = new ArrayList<String>();
				}
				retornoJson = new Gson().toJson(nodes);	
			}
			//Resposta checkbox
			else if (request.getParameter("id").equals("25"))
			{
				ArrayList<ArrayList<String>> nodes = new ArrayList<ArrayList<String>>();
				ArrayList<String> nodeList = new ArrayList<String>();

				ArrayList<DadosPerguntasOpcaoBean> dadosCategoria = objPers.getPerguntasCheckbox();

				for (DadosPerguntasOpcaoBean dcb : dadosCategoria)
				{
					nodeList.add(String.valueOf(dcb.getIdPerguntas()));
					nodeList.add(dcb.getPerguntas());

					ArrayList<DadosRespostasBean> dadosRespostas = objPers.getRespostasCheckbox(dcb.getIdPerguntas());
					for (DadosRespostasBean dpb : dadosRespostas)
					{
						nodeList.add(String.valueOf(dpb.getIdRespostas()));
						nodeList.add(dpb.getRespostas());
					}

					nodes.add(nodeList);
					nodeList = new ArrayList<String>();
				}
				retornoJson = new Gson().toJson(nodes);	
			}
			else if (request.getParameter("id").equals("26"))
			{
				int idFuncionario = Integer.parseInt(request.getParameter("idFuncionario"));
				int permissao = Integer.parseInt(request.getParameter("permissao"));
				String ret = objPers.updatePermissao(idFuncionario,permissao);
				retornoJson = new Gson().toJson(ret);
			}
			else if (request.getParameter("id").equals("27"))
			{
				int idFuncionarioMassoterapeuta = Integer.parseInt(request.getParameter("cmbMasso"));
				String dataAgendamento = converteDataMysql(request.getParameter("date"));
				String[] valor = request.getParameter("value").split(" - ");
				String idHorario = valor[0];
				
				String temFuncionario = objPers.validaFuncionarioCancelamento(dataAgendamento, idHorario, idFuncionarioMassoterapeuta);
				
				if(temFuncionario.equals(""))
				{	
					retornoJson = new Gson().toJson(objPers.updateCancelamento(idFuncionarioMassoterapeuta, dataAgendamento, idHorario));
				}
				else
				{
					//logica para enviar email
					retornoJson = new Gson().toJson(objPers.updateCancelamento(idFuncionarioMassoterapeuta, dataAgendamento, idHorario));
				}
			}
			else if (request.getParameter("id").equals("28"))
			{
				retornoJson = new Gson().toJson(objPers.relatorioCampanha());					
			}
						
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(retornoJson);
		}
	}

	private String converteDataMysql(String dataFormatoBrasileiro)
	{
		String dataConvertida = null;

		try
		{
			dataConvertida = new SimpleDateFormat("yyyy/MM/dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dataFormatoBrasileiro));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return (dataConvertida);
	}

	private String converteDataMysqlHora(String horaData)
	{
		String dataConvertHora = null;

		try
		{
			dataConvertHora = new SimpleDateFormat("dd-MM-yyyy' às' HH:mm").format(new SimpleDateFormat("yyyy-MM-dd 'às' HH:mm").parse(horaData));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}
		return (dataConvertHora);
	}

	private String converteMysqlData(String dataFormatoBrasileiro)
	{
		String dataConvertida = null;

		try
		{
			dataConvertida = new SimpleDateFormat("yyyy-MM-dd").format(new SimpleDateFormat("dd/MM/yyyy").parse(dataFormatoBrasileiro));
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		return (dataConvertida);
	}

	private int gravaDadosPlantonista(String tipoAcao, Persistencia objPers, String idFuncionario, String idMeses)
	{
		int ret = -1;

		if (tipoAcao.equals("I"))
		{
			ret = objPers.inserePlantonista(idFuncionario, idMeses);
		}
		else if (tipoAcao.equals("D"))
		{
			ret = objPers.deletaPlantonista(idFuncionario, idMeses);
		}

		return (ret);
	}
}