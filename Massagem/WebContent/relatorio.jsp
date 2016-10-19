<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@ include file="header.html" %>
		<link rel="stylesheet" type="text/css" href="./css/datatables.min.css"/>
		<link rel="stylesheet" type="text/css" href="./css/buttons.dataTables.min.css"/>	

		<script src="./js/relatorio.js"></script> 	
		<script type="text/javascript" src="./js/datatables.min.js"></script>
		<script type="text/javascript" src="./js/dataTables.buttons.min.js"></script>
		<script type="text/javascript" src="./js/buttons.flash.min.js"></script>
		<script type="text/javascript" src="./js/jszip.min.js"></script>
		<script type="text/javascript" src="./js/pdfmake.min.js"></script>
		<script type="text/javascript" src="./js/vfs_fonts.js"></script>
		<script type="text/javascript" src="./js/buttons.html5.min.js"></script>
		<script type="text/javascript" src="./js/buttons.print.min.js"></script>
		<script src="./js/pt-brCalendar.js"></script>
	</head>
		
	<body style="background-color: #eee">
		<c:choose>
			<c:when test="${sessionScope.idFuncionario == null}">
				<c:redirect url="/index.jsp" />
			</c:when>
		</c:choose>

		<c:set value="${sessionScope.matricula}" var="matricula" scope="session"></c:set>
		<%@ include file="menu.jsp" %>
		
		<div class="container">
			<div class="jumbotron">
				<div class="form-group">
					<label>Massoterapeuta</label>
					<select id="idMassoterapeuta">
					</select>
				</div>
				<div class="form-group">
					<label>Escolha uma data início</label>
					<input id="inicio" name="inicio" type="text" >
				</div>
				<div class="form-group">
					<label>Escolha uma data fim</label>
					<input id="fim" name="fim" type="text" >
				</div>
			<input id="enviar" type="button" value="Buscar" />
			</div>
			<div id="mostrarTabelaDados" style="display:none;">
			 	<div class="row">
			 		<div class="col-xs-12 form-group">
						<table id="tabelaDados" class="display"></table>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>