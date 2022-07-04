<!-- Ctrl+Shift+F 정렬(한 줄 띄어쓰기 자주되면 HTML files - Editor - Line width 200으로 수정) -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 상대경로로 찾기 -->
<%@include file="layout/header.jsp"%>
<div class="container">

	<c:forEach var="board"  items="${boards.content}"> <!-- items="" : BoardController의 index() 메소드가 boards 데이터를 index.jsp로 전송한다. 전송받은 데이터를 사용할 수 있다.-->
		<div class="card m-2">
			<div class="card-body">
				<h4 class="card-title">${board.title}</h4> <!-- @Data 어노테이션이 있으므로 board.getTitle() 호출한 것 -->
				<a href="#" class="btn btn-primary">상세보기</a>
			</div>
		</div>
	</c:forEach>
	
	<ul class="pagination  justify-content-center">
	
		<c:choose>
			<c:when test="${boards.first}">
				 <li class="page-item disabled"><a class="page-link" href="?page=${boards.number - 1}">Previous</a></li>
			</c:when>
			
			<c:otherwise>
				<li class="page-item"><a class="page-link" href="?page=${boards.number - 1}">Previous</a></li>
			</c:otherwise>
		</c:choose>
	  
		<c:forEach var="i" begin="1" end="${boards.totalPages}">
            <li class="page-item"><a class="page-link" href="?page=${i-1}">${i}</a></li>
        </c:forEach>
	  
	  	<c:choose>
			<c:when test="${boards.last}">
				  <li class="page-item disabled"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
			</c:when>
			
			<c:otherwise>
				 <li class="page-item"><a class="page-link" href="?page=${boards.number + 1}">Next</a></li>
			</c:otherwise>
		</c:choose>
		
		 
		</ul>
	
</div>

<!-- 상대경로로 찾기 -->
<%@include file="layout/footer.jsp"%>