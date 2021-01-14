<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="../layout/header.jsp" %>


<div class="container">
	<form action="/testWeb/user?cmd=login" method="post">
		<div class="form-group">
			<input type="text" name="username" id="username" class="form-control" placeholder="Enter Username" required/>
		</div>
		
		<div class="form-group">
			<input type="password" name="password" class="form-control" placeholder="Enter Password" required/>
		</div>

		<br/>
		<button type="submit" class="btn btn-primary">로그인</button>
	</form>
</div>
</body>
</html>