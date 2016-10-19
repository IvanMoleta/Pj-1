<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@ include file="header.html" %>
		<link rel="stylesheet" href="./css/font-awesome.min.css">
		<link rel="stylesheet" href="./css/agendamento.css">

		<script src="./js/pt-brCalendar.js"></script>
		<script src="./js/agendamento.js"></script>
		<script src="./js/calendario.js"></script>
	</head>

	<body style="background-color: #eee">
		<c:choose>
			<c:when test="${sessionScope.idFuncionario == null}">
				<c:redirect url="/index.jsp" />
			</c:when>
		</c:choose>

		<c:set value="${sessionScope.matricula}" var="matricula" scope="session"></c:set>

		<%@ include file="menu.jsp" %>
		<input id='mv' type='hidden' value='${sessionScope.matricula}'/>
		<input id='perm' type='hidden' value='${sessionScope.permissao}'/>
		<div class="container">
			<div class="form-group">
				<label for="firstname" class="col-sm-2 control-label">Data:</label>
				<div class="col-sm-1" style="width: 0.555%">
					<input type="button" class="botaovoltar" id="botaovoltar" style="width: 0.555%" value="<"/>
				</div>
				<div class="col-sm-2">
					<input type="text" class="form-control" style="text-align: center" id="datepicker" value="12/04/2016" readonly />
				</div>
				<div class="col-sm-2">
					<input type="button" class="botaonext" id="botaonext" value=">"/>
				</div>
				<div class="opcoes">
					<select id="local"></select>
					<label><input type="radio" id="ckbTurnoM" name="ckbTurno" value="M" checked class="ckbManha">  Manhã</label>
					<label><input type="radio" id="ckbTurnoT" name="ckbTurno" value="T" class="ckbTarde">  Tarde</label>
				</div>
				<br />
			</div>

			<div style="margin-top: 70px;">
				<table id="tabelaDados" class="table table-striped table-bordered" style="text-align:center">
				<thead>
					<tr class="first tableCab" id="cabecalhoTabela"></tr>
				</thead>
				<tbody id="corpoTabela"></tbody>
				</table>
			</div>
		</div>
	</body>
</html>