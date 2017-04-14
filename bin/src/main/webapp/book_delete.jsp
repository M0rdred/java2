<?xml version="1.0" encoding="UTF-8" ?>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="mik" uri="/WEB-INF/SzorzoTabla.tld"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
<title>Insert title here</title>
<style>
table {
	border: solid 1px;
}

td {
	border: solid 1px;
}
</style>
</head>
<body>
	<form action="book_delete" method="get">
		<table>
			<tr>
				<td>Könyv azonosítója</td>
				<td><c:out value="${book.id }" /></td>
			</tr>
			<tr>
				<td>Könyv címe</td>
				<td><c:out value="${book.title }" /></td>
			</tr>
			<tr>
				<td>Könyv szerzője</td>
				<td><c:out value="${book.author }" /></td>
			</tr>
			<tr>
				<td>Könyv leírása</td>
				<td><c:out value="${book.description }" /></td>
			</tr>
			<tr>
				<td>Kiadás éve</td>
				<td><c:out value="${book.pubYear }" /></td>
			</tr>
		</table>
		<button type="submit" formmethod="post">Törlés</button>
		<br />
	</form>
	<a href="book_list">Könyvek listája</a>
</body>
</html>