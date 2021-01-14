<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp"%>

<div class="container">

	<h2>회원 목록</h2>
	<p>User List</p>
	<table class="table table-striped">
		<thead>
			<tr>
				<th>No</th>
				<th>Name</th>
				<th>Email</th>
				<th>Role</th>
				<th>CreateDate</th>
			</tr>
		</thead>
		<tbody>
			<c:forEach var="user" items="${users}">
				<tr id="info-${user.id}">
					<td>${user.id}</td>
					<td>${user.username}</td>
					<td>${user.email}</td>
					<td>${user.userRole}</td>
					<td>${user.createDate}</td>
					<td><c:if test="${sessionScope.principal.id == user.id}">
							<i onclick="deleteUser(${user.id})"
								class="material-icons ml-auto">delete</i>
						</c:if></td>
					<td><c:if test="${sessionScope.principal.userRole == 'admin'}">
							<button onclick="deleteAdmin(${user.id})"
								class="btn btn-danger">관리자 삭제</button>
						</c:if></td>
				</tr>
			</c:forEach>
		</tbody>
	</table>

</div>

<script>
function deleteUser(id){
	$.ajax({
		url : "/testWeb/user?cmd=delete&id="+id
	}).done(function(result) {
		location.href="index.jsp";
		alert("회원 삭제가 완료되었습니다.")
	});
	
}

function deleteAdmin(id){
	$.ajax({
		url : "/testWeb/user?cmd=deleteAdmin&id="+id
	}).done(function(result) {
		location.reload();
		alert("회원 삭제가 완료되었습니다.")
	});
}
</script>

</body>
</html>