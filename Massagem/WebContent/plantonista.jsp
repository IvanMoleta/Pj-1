<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@ include file="header.html"%>

		<script src="./js/plantonista.js"></script>
		<script src="./js/jquery.multi-select.js" type="text/javascript"></script>
		<link href="./css/multiselect.css" media="screen" rel="stylesheet" type="text/css">
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
					<select id="ComboMasso">
					</select>
				 	<br/><br/>
					<div id="carregaMeses">
						<table id="trocaPlantonista"  class="table table-striped table-bordered">
						<tr>
							<td><input type="checkbox" name="Janeiro" value="1"/>  <b>Janeiro</b></td>
							<td><input type="checkbox" name="Fevereiro" value="2"/>  <b>Fevereiro</b></td>
							<td><input type="checkbox" name="Março" value="3"/>  <b>Março</b></td>
							<td><input type="checkbox" name="Abril" value="4"/>  <b>Abril</b></td>
						</tr>
						<tr>
							<td><input type="checkbox" name="Maio" value="5"/>  <b>Maio</b></td>
							<td><input type="checkbox" name="Junho" value="6"/>  <b>Junho</b></td>
							<td><input type="checkbox" name="Julho" value="7"/>  <b>Julho</b></td>
							<td><input type="checkbox" name="Agosto" value="8"/>  <b>Agosto</b></td>
						</tr>
						<tr>
							<td><input type="checkbox" name="Setembro" value="9"/>  <b>Setembro</b></td>
							<td><input type="checkbox" name="Outubro" value="10"/>  <b>Outubro</b></td>
							<td><input type="checkbox" name="Novembro" value="11"/> <b>Novembro</b></td>
							<td><input type="checkbox" name="Dezembro" value="12"/>  <b>Dezembro</b></td>
						</tr>
					</table>
					<div><b><i>* os meses assinalados correspondem aos meses em que o Massoterapeuta atenderá no plantão.</i></b></div>
				  </div>
				</div>
			</div>
		</div>
	</body>
</html>