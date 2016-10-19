package br.com.scopus.conexao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Conexao
{
	public Connection abreConn()
	{
		Connection conn = null;

		try
		{
			String driverName = "com.mysql.jdbc.Driver";
			Class.forName(driverName);

			String url = "jdbc:mysql://localhost:3306/massagem?useSSL=true";

			//Server PROD
			String username = "";
			String password = "";

			conn = DriverManager.getConnection(url, username, password);
		}
		catch (ClassNotFoundException|SQLException e)
		{
			System.out.println(e.getMessage());
		}

		return (conn);
	}

	public void close(Conexao c)
	{
		close(c);
	}
}