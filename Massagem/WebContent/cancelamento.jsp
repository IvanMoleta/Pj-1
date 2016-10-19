<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@ include file="header.html" %>

		<script src="./js/pt-brCalendar.js"></script>
		<script src="./js/cancelamento.js"></script>
	</head>

	<body>
		<c:choose>
			<c:when test="${sessionScope.idFuncionario == null}">
				<c:redirect url="/index.jsp" />
			</c:when>
		</c:choose>
		<%@ include file="menu.jsp" %>		

		<div class="container">
			<div class="jumbotron">
				<div class="form-group">
					<select id="cmbMasso" class="botaoMasso" ></select>
	
					<div class="col-sm-2">
						<input type="text" class="form-control" id="datepicker" readonly />
					</div>
	
					<input id="btncancelamento" type="checkbox" class="botaoCancelamento" style="margin-left:50px;"/>Cancelar agenda para o dia todo
									<br>
					<div class="container">
						<div class="jumbotron">
							<table id="carregaHorario" class="table table-striped table-bordered" >
							</table>
						</div>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>