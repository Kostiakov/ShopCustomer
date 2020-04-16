<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
</head>
<body>
	<table>
		<tr>
			<th>Id</th>
			<th>Name</th>
			<th>Amount</th>
			<th>Price</th>
			<th>Calories</th>
			<th>Life Time</th>
			<c:forEach var="foundProduct" items="${foundProducts}">
				<tr>
					<td>${foundProduct.id}</td>
					<td>${foundProduct.name}</td>
					<td>${foundProduct.amount}</td>
					<td>${foundProduct.price}</td>
					<c:catch var="error1">
						<td>${foundProduct.calories}</td>
					</c:catch>
					<c:if test="${error1!=null}">
					</c:if>
					<c:catch var="error2">
						<td>${foundProduct.lifeTime}</td>
					</c:catch>
					<c:if test="${error2!=null}">
					</c:if>
				</tr>
			</c:forEach>
		</tr>
	</table>
	<p>
		<a href="${pageContext.request.contextPath}/customer/home">Back to start</a>
	</p>
</body>
</html>