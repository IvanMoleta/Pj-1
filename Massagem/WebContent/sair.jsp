<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
	<head>
		<%@ include file="header.html" %>

		<script>
			$(document).ready(function()
			{
				$.ajax({
					url : 'Processamento',
					type: "post",
					dataType: "json",
					data: { id : "10" },
					success : function(json)
					{
						$(location).attr('href', '/Massagem/index.jsp')
					}
				});
			});
		</script>
	</head>
	<body>
	</body>
</html>