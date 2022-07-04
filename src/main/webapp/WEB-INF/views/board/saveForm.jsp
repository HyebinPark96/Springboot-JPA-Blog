<!-- Ctrl+Shift+F 정렬(한 줄 띄어쓰기 자주되면 HTML files - Editor - Line width 200으로 수정) -->
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>

<!-- 상대경로로 찾기 -->
<%@include file="../layout/header.jsp"%>
<div class="container">
		<form action="/auth/loginProc" method="post">
			<div class="form-group">
				<label for="title">Title</label> 
				<input type="text" class="form-control" placeholder="Enter title" id="title">
			</div>
		
			<div class="form-group">
			  <label for="comment">Content:</label>
			  <textarea class="form-control summernote" rows="5" id="content"></textarea>
			</div>
	</form>
	<button id="btn-save" class="btn btn-primary">글쓰기완료</button>
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