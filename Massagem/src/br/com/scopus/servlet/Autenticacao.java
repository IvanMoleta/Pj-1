package br.com.scopus.servlet;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.taglibs.standard.lang.jstl.BooleanLiteral;

import br.com.scopus.bean.UsuarioBean;
import br.com.scopus.consultas.Persistencia;

@WebServlet("/Autenticacao")
public class Autenticacao extends HttpServlet
{
	private static final long serialVersionUID = 1L;
       
    public Autenticacao()
    {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		HttpSession session = request.getSession(true);

		int retPrimeiroAcesso = new Persistencia().validaPrimeiroAcesso(request.getParameter("matricula"));

		if (retPrimeiroAcesso == 1)
		{
			session.setAttribute("errorPrimeiroAcesso", "ErroPrimeiroAcesso");
			session.setAttribute("matricula", request.getParameter("matricula"));
		    response.sendRedirect(request.getContextPath() + "/cadastrar.jsp");
		}
		else
		{
			ArrayList<UsuarioBean> dadosUsuario = new Persistencia().validaLogin(request.getParameter("matricula"), request.getParameter("password"));

			if (dadosUsuario.size() == 0)
			{
				session.setAttribute("erroAutenticacao", "erroAutenticacao");
			    response.sendRedirect(request.getContextPath() + "/index.jsp");
			}
			else
			{
				session.setAttribute("idFuncionario", dadosUsuario.get(0).getIdFuncionario());
				session.setAttribute("matricula", dadosUsuario.get(0).getMatricula());
				session.setAttribute("nomeFuncionario", dadosUsuario.get(0).getNomeFuncionario());
				session.setAttribute("ramalFuncionario", dadosUsuario.get(0).getRamalFuncionario());
			    session.setAttribute("permissao", dadosUsuario.get(0).getIdPermissao());
				response.sendRedirect(request.getContextPath() + "/agendamento.jsp");
			}
		}
	}
}