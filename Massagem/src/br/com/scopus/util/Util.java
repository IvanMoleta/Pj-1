package br.com.scopus.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Properties;

public class Util
{
	public Properties getProp() throws IOException
	{
		Properties props = new Properties();
		
		//props.load(new FileInputStream("/var/lib/tomcat/webapps/Massagem/properties/properties.prop"));
		props.load(new FileInputStream("..\\..\\Desenvolvimento\\workspace\\Massagem\\WebContent\\properties\\properties.prop"));
		
		return (props);
	}

	public String generatePassword(String senha)
	{
		MessageDigest algorithm;
		byte messageDigest[] = null;

		try
		{
			algorithm = MessageDigest.getInstance("SHA-256");
			messageDigest = algorithm.digest(senha.getBytes("UTF-8"));
		}
		catch (NoSuchAlgorithmException|UnsupportedEncodingException e)
		{
			e.printStackTrace();
		}

		StringBuilder hexString = new StringBuilder();
		for (byte b : messageDigest)
		{
			hexString.append(String.format("%02X", 0xFF & b));
		}

		String senhaSha256 = hexString.toString();

		return (senhaSha256);
	}
}
