var staytime = 6000;

function errorMessage(msg)
{
	$().toastmessage('showToast',
	{
		text     : msg,
		sticky   : false,
		position : 'top-right',
		inEffectDuration: 600,
		stayTime: staytime,
		type : 'error'
	});
}

function noticeMessage(msg)
{
	$().toastmessage('showToast',
	{
		text     : msg,
		sticky   : false,
		position : 'top-right',
		type     : 'notice',
		inEffectDuration: 600,
		stayTime: staytime
	});
}

function successMessage(msg)
{
	$().toastmessage('showToast',
	{
		text     : msg,
		sticky   : false,
		position : 'top-right',
		inEffectDuration: 600,
		stayTime: staytime,
		type : 'success'
	});
}