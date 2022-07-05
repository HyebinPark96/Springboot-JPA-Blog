<!-- Ctrl+Shift+F 정렬(한 줄 띄어쓰기 자주되면 HTML files - Editor - Line width 200으로 수정) -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 상대경로로 찾기 -->
<%@include file="../layout/header.jsp"%>
<div class="container">
		<form action="/auth/loginProc" method="post">
		<input type="hidden" id="id" value="${board.id}" />
			<div class="form-group">
				<input value="${board.title}" type="text" class="form-control" placeholder="Enter title" id="title">
			</div>
		
			<div class="form-group">
			  <textarea class="form-control summernote" rows="5" id="content">${board.content}</textarea>
			</div>
	</form>
	<button id="btn-update" class="btn btn-primary">글수정완료</button>
</div>

<script>
	$('.summernote').summernote({
		tabsize: 2,
		height: 300
	});
</script>

<!-- blog는 컨텍스트패스라고 프로젝트  진입점이고, 이걸 붙여야 해당 프로젝트를 찾울수있어요. -->
<!-- 정적파일은 static이 기본경로라서 static 생략해도 프로젝트 진입점에서 바로 찾을 수 있게 기본세팅이  되어있어요. -->
<script src="/js/board.js"></script>

<!-- 상대경로로 찾기 -->
<%@include file="../layout/footer.jsp"%>