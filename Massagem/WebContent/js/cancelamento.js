$(document).ready(function()
{
	situacaoHorario();
	
	//Monta combo massoterapeuta
	$.ajax(
	{
		url : 'Processamento',
		type: "post",
		dataType: "json",
		data: { id : "7" },
		success : function(json)
		{
			var options = "";
			$.each(json, function(key, value)
			{
				options += '<option value="' + key + '">' + value + '</option>';

			});

			$("#cmbMasso").html(options);

			$("#cmbMasso").append($("#cmbMasso option").remove().sort(function(a, b)
			{
				var at = $(a).text(), bt = $(b).text();
				return (at > bt)?1:((at < bt)?-1:0);
			}));

			$("#cmbMasso").val($("#cmbMasso option:first").val());
			
			carregaHorario();
		}
	});
	
	$('#datepicker').datepicker
	({
		inline: true,
		altField: '#datepicker_value',
		minDate: 0,
		beforeShowDay: $.datepicker.noWeekends,
		onSelect: function()
		{
			carregaHorario();
		}
	});
	
	$.datepicker.formatDate('dd/mm/yy', new Date());
	$('#datepicker').datepicker("setDate", new Date());

	$("#btncancelamento").click(function(event)
	{
		if ($("#datepicker").val() == "")
		{
			errorMessage('Favor selecionar uma data.');
		}
		else
		{
			var c = confirm("Deseja efetuar o cancelamento?");
		
			if (c)
			{
				$.ajax({
					url : 'Processamento',
					type: "post",
					dataType: "json",
					data:
					{
						id : "8",
						cmbMasso : $("#cmbMasso").val(),
						date : $("#datepicker").val()
					},
					success : function(json)
					{
						if (json == "sucesso")
						{
							carregaHorario();
							successMessage('Cancelamento efetuado com sucesso.');
						}
						else
						{
							errorMessage('Dados não localizados.');
						}
					}
				});
			}
		}
	});

	$("#btncancelaHorario").change(function()
	{
		if(this.checked)
		{
			$('#carregaHorario').fadeIn('slow');
			carregaHorario();
		}
		else
		{
			$('#carregaHorario').fadeOut('slow');
		}
	});
	
	$( "#cmbMasso" ).change(function()
	{
		carregaHorario();
	});	
	
	function carregaHorario()
	{
		$.ajax({
			url : 'Processamento',
			type: "post",
			dataType: "json",
			data: 
			{
				id : "21", 
				cmbMasso : $("#cmbMasso").val(),
				date : $("#datepicker").val()
			},
			success : function(json)
			{		
				var temp = "";
				var botao = '';
				for(i = 0; i < json.length; i++)
				{					
					temp += "<tr>";		
					
					temp += "<td style='width: 15px;'><input id='horario' type='checkbox' value='"+json[i].idHorario+ "'/></td><td>"+json[i].dataAgendamento+"</td>";
						
					temp += "</tr>";
				}
								
				$("#carregaHorario").html(temp);
				
			}
		})
	}
	
	function situacaoHorario()
	{		
		$(document).on('change', '#carregaHorario input[type=checkbox]' , function()
		{
			if(this.checked)
			{
				alteraHorario(this.value);
			}	
		});
	}
	
	function alteraHorario(valor)
	{
		var con = confirm("Deseja efetuar o cancelamento ?" );
		
		if (con)
		{
			$.ajax({
				url : 'Processamento',
				type: "post",
				dataType: "json",
				data:
				{
					id : "27",
					cmbMasso : $("#cmbMasso").val(),
					date : $("#datepicker").val(),
					value : valor
				},
				success : function(json)
				{
					if (json == "sucesso")
					{
						successMessage('Cancelamento efetuado com sucesso.');
						carregaHorario();
					}
					else
					{
						errorMessage('Dados não localizados.');
					}
				}
			});
		}
	}	
});