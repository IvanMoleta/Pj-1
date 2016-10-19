package br.com.scopus.validacao;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class Regras
{
	public String[] getIntervaloDatas(String data)
	{
		String arrayDatasSemanaAtual[] = new String[2];

		Date date = null;

		try
		{
			date = new SimpleDateFormat("yyyy/MM/dd").parse(data);
		}
		catch (ParseException e)
		{
			e.printStackTrace();
		}

		SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.WEEK_OF_YEAR, Integer.parseInt(new SimpleDateFormat("w").format(date)));

		cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
		arrayDatasSemanaAtual[0] = sdf.format(cal.getTime()); 

		cal.set(Calendar.DAY_OF_WEEK, Calendar.FRIDAY);
		arrayDatasSemanaAtual[1] = sdf.format(cal.getTime());

		return (arrayDatasSemanaAtual);
	}
}