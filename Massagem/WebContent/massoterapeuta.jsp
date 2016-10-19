<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page session="true" %>

<!DOCTYPE html>
<html lang="pt-br">
	<head>
		<%@ include file="header.html" %>

		<script src="./js/massoterapeuta.js"></script>
	</head>

	<body>
		<div class="container">
			<div class="jumbotron">
				<input type="text" id="date" style="margin-left: 20px;" tabindex="1" readonly style="margin-left: 9px;" />
				<div style="margin-left: 20px" id="Horarios" ></div>

				<input id="erro" value="" style="margin-left: 20px; display:none;" />
				<audio id="myAudio">
 					<source src="audio/voz.mp3">
 				</audio>
			</div>
		</div>
	</body>
</html>