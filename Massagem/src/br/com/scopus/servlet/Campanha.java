package br.com.scopus.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.google.gson.Gson;

import br.com.scopus.consultas.Persistencia;


@WebServlet("/Campanha")
public class Campanha extends HttpServlet
{
	private static final long serialVersionUID = 1L;

    public Campanha()
    {
        super();
        // TODO Auto-generated constructor stub
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
			
			if (request.getParameter("id").equals("1"))
			{
				int idFuncionario = (int)session.getAttribute("idFuncionario");
				
				int ret = 0;
				String erro = "";
				int validaQuestionario = objPers.validaQuestionario(idFuncionario);

				if(validaQuestionario == 0)
				{
					String[] arrayRespostas = request.getParameterValues("arrayRespostas[]");
					int resposta31 = Integer.parseInt(request.getParameter("resposta31"));
					int idRespostas = Integer.parseInt(request.getParameter("resposta3"));
					int idRespostas2 = Integer.parseInt(request.getParameter("resposta2"));
					int idRespostas4 = Integer.parseInt(request.getParameter("resposta41"));
					int idRespostas5 = Integer.parseInt(request.getParameter("resposta51"));
					String[] arrayRespostas1 = request.getParameterValues("arrayRespostas1[]");
					String[] arrayRespostas2 = request.getParameterValues("arrayRespostas2[]");
					String[] arrayRespostasAV3 = request.getParameterValues("arrayRespostasAV3[]");
					String[] arrayRespostas4 = request.getParameterValues("arrayRespostas4[]");
					String[] arrayRespostas5 = request.getParameterValues("arrayRespostas5[]");
					String[] arrayRespostas6 = request.getParameterValues("arrayRespostas6[]");
					String obs = request.getParameter("obs");
					//Pergunta 6
					ret = objPers.gravarQuestionarioPergunta(idFuncionario,idRespostas);
					//Pergunta 1
					ret = objPers.gravarQuestionarioPergunta1(idFuncionario,idRespostas2);
					//Pergunta 2
					for (int i = 0; i < arrayRespostas.length; i++)
					{
						ret = objPers.gravarQuestionarioPergunta2(idFuncionario,arrayRespostas[i]);
					}
					//Pergunta 3
						ret = objPers.gravarQuestionarioPergunta3(idFuncionario,resposta31);
					//Pergunta 4
					ret = objPers.gravarQuestionarioPergunta4(idFuncionario,idRespostas4);
					//Pergunta 5
					ret = objPers.gravarQuestionarioPergunta5(idFuncionario,idRespostas5);
					//AV- Avaliação Masso - Pergunta 1
					for (int i = 0; i < arrayRespostas1.length; i++)
					{
						ret = objPers.gravarQuestionarioPerguntaAV1(idFuncionario,arrayRespostas1[i],i);
					}
					//AV - Pergunta 2
					for (int i = 0; i < arrayRespostas2.length; i++)
					{
						ret = objPers.gravarQuestionarioPerguntaAV2(idFuncionario,arrayRespostas2[i],i);
					}
					//AV - Pergunta 3
					for (int i = 0; i < arrayRespostasAV3.length; i++)
					{
						ret = objPers.gravarQuestionarioPerguntaAV3(idFuncionario,arrayRespostasAV3[i],i);
					}
					//AV - Pergunta 4
					for (int i = 0; i < arrayRespostas4.length; i++)
					{
						ret = objPers.gravarQuestionarioPerguntaAV4(idFuncionario,arrayRespostas4[i],i);
					}
					//AV - Pergunta 5
					for (int i = 0; i < arrayRespostas5.length; i++)
					{
						ret = objPers.gravarQuestionarioPerguntaAV5(idFuncionario,arrayRespostas5[i],i);
					}
					//AV - Pergunta 6
					for (int i = 0; i < arrayRespostas6.length; i++)
					{
						ret = objPers.gravarQuestionarioPerguntaAV6(idFuncionario,arrayRespostas6[i],i);
					}
					//Pergunta 7
					ret = objPers.gravarQuestionarioPerguntaAV7(idFuncionario,obs);
				}
				else
				{
					erro = "validaQuestionario";
				}
				retornoJson = (validaQuestionario == 0) ?new Gson().toJson("sucesso"): new Gson().toJson(erro);;
			}
			response.setContentType("application/json");
			response.setCharacterEncoding("UTF-8");
			response.getWriter().write(retornoJson);
		}
	}

}
