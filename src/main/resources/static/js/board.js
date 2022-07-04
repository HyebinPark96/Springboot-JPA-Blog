let index = {
	init:function(){
		$("#btn-save").on("click", ()=>{ // function(){}가 아닌 ()=>{}를 쓰는 이유 : this를 바인딩하기 위해서!! 
			this.save(); // function(){}을 썼다면 this는 window를 가르킨다. 
		}); // on("1","2") : 파라미터 1번 이벤트 발생시 파라미터 2번을 수행하라
	},
	
	save: function(){
		// alert("user의 save() 함수 호출됨");
		let data = {
			title: $("#title").val(),
			content: $("#content").val()
		};
		
		// console.log(data);
		
		// ajax 호출 시 default가 비동기 호출
		// ajax 통신을 이용해서 3개의 데이터를 json으로 변경하여 INSERT 요청!!
		// ajax가 통신을 성공하고 서버가 json을 리턴해주면 자동으로 자바 오브젝트로 변환해주네요 
		$.ajax({
			type: "POST",
			url: "/api/board",
			data: JSON.stringify(data), // http body 데이터 자바스크립트의 data 객체를 Java가 알아듣도록 변경
			contentType: "application/json; charset=utf-8", // body데이터가 어떤 타입인지 (MIME)
			dataType: "json" // 요청을 서버로 해서 응답이 왔을 때 기본적으로 모든 것이 문자열(일부 생긴게 json이라면 => javascript 오브젝트로 변경하여 아래 함수의 파라미터로 전달됨)
		}).done(function(resp){
			// 성공한 경우 호출
			alert("글쓰기가 완료되었습니다.");
			location.href="/";
		}).fail(function(error){
			// 실패한 경우 호출
			alert(JSON.stringify(error));
		}); 
	}, 

}

index.init();









