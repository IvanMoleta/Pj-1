$(document).ready(function()
{
	$("#date").val($.datepicker.formatDate('dd', new Date()));
	
	$(window).load(function(event)
	{
		var id = event.target.id;

		if ($("#" + id + "").text() == "")
		{
			var idMassoterapeuta = prompt("MASSOTERAPEUTA: ");

			if (idMassoterapeuta != null)
			{
				carregaDados(idMassoterapeuta);
			}
		}
	});
	
	setInterval(function()
	{
		horaMassoterapeuta();	
		$(location).attr('href', 'massoterapeuta.jsp');
	}, 60000);
});


function carregaDados(idMassoterapeuta)
{
	$.ajax({
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: 
		{ 
			id : "3", 
			idMassoterapeuta : idMassoterapeuta 
		},
		success : function(json)
		{
			if (json == "")
			{
				$("#erro").val('Agenda cancelada.');
				$("#erro").show();
			}
			else if (json == "codInvalido")
			{
				$("#erro").val('Código inválido.');
				$("#erro").show();
			}
			else
			{
				$('#date').val($('#date').val() + ' - '+json[0].nomeMassoteerapeuta);
				for(i = 0; i < json.length; i++)
				{
					$('#Horarios').append('<input value="'+json[i].dadosCliente+'" tabindex="'+(i+2)+'" readonly /><br />');
				}
			}
		}
	});
}

function horaMassoterapeuta()
{
	var data = new Date();
	var hora = data.getHours();

	if ((hora >= 8 && hora <= 12 ) || (hora >= 13 && hora <= 17))
	{
		alertaMasso();
	}
}

function alertaMasso()
{
	var data = new Date();
	var minuto = data.getMinutes();
	var vid = document.getElementById("myAudio");

	if (minuto == 15 || minuto == 35 || minuto == 55)
	{
		vid.play();
	}
}