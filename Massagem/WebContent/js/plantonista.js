$(document).ready(function()
{
	comboPlantonista();
	Plantonista();
	
	$('#ComboMasso').on('change', function() 
	{
		carregaDados();
	});
});

function comboPlantonista()
{
	$.ajax({
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: { id : "13"},
		success : function(json)
		{
			for(var i = 0 ; i < json.length ; i++)
			{
				$('#ComboMasso').append('<option value='+json[i].idFuncionario+'>'+json[i].nomeFuncionario+' </option>');
			}

			carregaDados();
		}
	});	
}

function carregaDados()
{
	$.ajax({
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: 
		{
			id : "14", 
			idFuncionario : $("#ComboMasso").val()
		},
		success : function(json)
		{
			for (var i = 0; i < json.length ; i ++)
			{
				$('#trocaPlantonista input[value='+json[i].idMeses+']').prop("checked", true);				
			}

			$('#ComboMasso').on('change', function() 
			{
				for (var i = 0; i < json.length ; i ++)
				{
					$('#trocaPlantonista input[value='+json[i].idMeses+']').prop("checked", false);	
				}
			});
			//bloqueiaMes();
		}
	})
};

function Plantonista()
{
	$("#trocaPlantonista input[type=checkbox]").on('change',function ()
	{
		if(this.checked)
		{
			alteraDadosPlantonista("I", this.value);
		}	
		else
		{
			alteraDadosPlantonista("D", this.value);
		}
	})
};

function alteraDadosPlantonista(tipoAcao, valor)
{
	$.ajax(
	{
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data:
		{
			id : "15",
			idFuncionario : $("#ComboMasso").val(),
			tipoAcao: tipoAcao,
			idMeses : valor
		},
		success : function(json)
		{
			if (json == "sucesso")
			{
				successMessage('Dados gravados com sucesso.');
			}
			else
			{
				errorMessage('Erro ao efetuar a atualização: Massoterapeuta possui agendamentos marcados.');
			}

			carregaDados();
		}
	});
}

function bloqueiaMes()
{
	var data = new Date;
	var mes = data.getMonth()+1;

	for (var i = 0; i <= mes; i++)
	{
		if (i != mes) 
		{
			$('input:checkbox[value='+i+']').prop("disabled", false);
		}
		else 
		{
			$('input:checkbox[value='+i+']').prop("disabled", true);
		}
	}
}