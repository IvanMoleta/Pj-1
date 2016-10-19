package br.com.scopus.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import br.com.scopus.consultas.Persistencia;

@WebServlet("/Cadastramento")
public class Cadastramento extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public Cadastramento()
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

		int retPrimeiroAcesso = new Persistencia().validaPrimeiroAcesso(request.getParameter("matricula"));

		if (retPrimeiroAcesso == 0)
		{
			session.setAttribute("senhaJaAlterada", "senhaJaAlterada");
			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
		else
		{
			int ret = new Persistencia().atualizaSenha(request.getParameter("matricula"), request.getParameter("password"));

			if (ret == 1)
			{
				session.setAttribute("senhaDifinida", "senhaDifinida");
			}
			else
			{
				session.setAttribute("erroDefinirSenha", "erroDefinirSenha");
			}

			response.sendRedirect(request.getContextPath() + "/index.jsp");
		}
	}
}