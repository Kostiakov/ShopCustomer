<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
</head>
<body>
	<table>
		<tr>
			<th>Name</th>
			<th>Amount</th>
			<th>Delete</th>
			<c:forEach var="tempCartProduct" items="${cartProducts}">
				<c:url var="deleteLink" value="/customer/deleteProduct">
					<c:param name="productName" value="${tempCartProduct.name}"></c:param>
				</c:url>
				<tr>
					<td>${tempCartProduct.name}</td>
					<td>${tempCartProduct.amount}</td>
					<td><a href="${deleteLink}">Delete</a></td>
				</tr>
			</c:forEach>
		</tr>
	</table>
	<p>
		<a href="${pageContext.request.contextPath}/customer/home">Back to start</a>
	</p>
</body>
</html>