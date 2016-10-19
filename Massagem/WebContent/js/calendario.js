var natDays = [[1, 1, 'uk'],[4, 21, 'uk'],[5, 1, 'uk'],[7, 9, 'uk'],[11, 2, 'uk'],[11, 15, 'uk'],[12, 25, 'uk'],[10, 12, 'uk']];

$(document).ready(function()
{
	$("#datepicker").datepicker(
	{
		minDate: 0,
		maxDate: '+7D',
		dateFormat: 'dd/mm/yy',
		beforeShowDay: noWeekendsOrHolidays
	});

	$('#datepicker').datepicker("setDate", new Date());

	var dateMin = new Date();
	var weekDays = AddWeekDays(3);

	dateMin.setDate(dateMin.getDate() + weekDays);

	function noWeekendsOrHolidays(date)
	{
		var noWeekend = $.datepicker.noWeekends(date);

		if (noWeekend[0])
		{
			return nationalDays(date);
		}
		else
		{
			return noWeekend;
		}
	}

	function nationalDays(date)
	{
		for (i = 0; i < natDays.length; i++)
		{
			if (date.getMonth() == natDays[i][0] - 1 && date.getDate() == natDays[i][1])
			{
				return [false, natDays[i][2] + '_day'];
			}
		}
		return [true, ''];
	}

	function AddWeekDays(weekDaysToAdd)
	{
		var daysToAdd = 0;
		var mydate = new Date();
		var day = mydate.getDay();

		weekDaysToAdd = weekDaysToAdd - (5 - day);

		if ((5 - day) < weekDaysToAdd || weekDaysToAdd == 1)
		{
			daysToAdd = (5 - day) + 2 + daysToAdd;
		}
		else
		{ // (5-day) >= weekDaysToAdd
			daysToAdd = (5 - day) + daysToAdd;
		}

		while (weekDaysToAdd != 0)
		{
			var week = weekDaysToAdd - 5;

			if (week > 0)
			{
				daysToAdd = 7 + daysToAdd;
				weekDaysToAdd = weekDaysToAdd - 5;
			}
			else
			{ // week < 0
				daysToAdd = (5 + week) + daysToAdd;
				weekDaysToAdd = weekDaysToAdd - (5 + week);
			}
		}

		return daysToAdd;
	}
});