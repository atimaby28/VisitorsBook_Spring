<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ include file="/WEB-INF/views/include/header.jsp" %>
    <script type="text/javascript">
	$(document).ready(function() {
		
		//회원 목록
		$.ajax({
			url:'${root}/admin/visitor',  
			type:'GET',
			contentType:'application/json;charset=utf-8',
			dataType:'json',
			success:function(visitors) {
				makeList(visitors);
			},
			error:function(xhr, status, error){
				console.log("상태값 : " + xhr.status + "\tHttp 에러메시지 : " + xhr.responseText);
			},
			statusCode: {
				500: function() {
					alert("서버에러.");
					// location.href = "/error/e500";
				},
				404: function() {
					alert("페이지없다.");
					// location.href = "/error/e404";
				}
			}	
		});
		
		$("#registerBtn").click(function() {
			let registerinfo = JSON.stringify({
				"visitorName" : $("#visitorname").val(), 
				"visitorId" : $("#visitorid").val(), 
				"visitorPwd" : $("#visitorpwd").val(), 
				"email" : $("#email").val()
			   });
			$.ajax({
				url:'${root}/admin/visitor',  
				type:'POST',
				contentType:'application/json;charset=utf-8',
				dataType:'json',
				data: registerinfo,
				success:function(visitors) {
					$("#visitorname").val('');
					$("#visitorid").val('');
					$("#visitorpwd").val('');
					$("#email").val('');
					$("#visitorRegModal").modal("hide");
					makeList(visitors);
				},
				error:function(xhr,status,msg){
					console.log("상태값 : " + status + " Http에러메시지 : "+msg);
				}
			});
		});
		
		$(document).on("dblclick", "tr.view", function() {
			let vid = $(this).attr("data-id");
			$.ajax({
				url:'${root}/admin/visitor/' + vid,  
				type:'GET',
				contentType:'application/json;charset=utf-8',
				success:function(visitor) {
					$("#vid").text(visitor.visitorId);
					$("#vname").text(visitor.visitorName);
					$("#vemail").text(visitor.email);
					$("#vjoindate").text(visitor.joinDate);
					$("#visitorViewModal").modal();
				},
				error:function(xhr,status,msg){
					console.log("상태값 : " + status + " Http에러메시지 : "+msg);
				}				
			});			
		});
		
		// 회워 정보 수정 보기.
		$(document).on("click", ".modiBtn", function() {
			let mid = $(this).parents("tr").attr("data-id");
			$("#view_" + mid).css("display", "none");
			$("#mview_" + mid).css("display", "");
		});
		
		// 회워 정보 수정 실행.
		$(document).on("click", ".modifyBtn", function() {
			let mid = $(this).parents("tr").attr("data-id");
			let modifyinfo = JSON.stringify({
						"visitorId" : mid, 
						"visitorPwd" : $("#visitorpwd" + mid).val(), 
						"email" : $("#email" + mid).val()
						//"address" : $("#address" + mid).val()
					   });
			$.ajax({
				url:'${root}/admin/visitor',  
				type:'PUT',
				contentType:'application/json;charset=utf-8',
				dataType:'json',
				data: modifyinfo,
				success:function(visitors) {
					makeList(visitors);
				},
				error:function(xhr,status,msg){
					console.log("상태값 : " + status + " Http에러메시지 : "+msg);
				}
			});
		});
		
		// 회워 정보 수정 취소.
		$(document).on("click", ".cancelBtn", function() {
			let mid = $(this).parents("tr").attr("data-id");
			$("#view_" + mid).css("display", "");
			$("#mview_" + mid).css("display", "none");
		});
		
		// 회워 탈퇴.
		$(document).on("click", ".delBtn", function() {
			if(confirm("정말 삭제?")) {
				let delid = $(this).parents("tr").attr("data-id");
				$.ajax({
					url:'${root}/admin/visitor/' + delid,  
					type:'DELETE',
					contentType:'application/json;charset=utf-8',
					dataType:'json',
					success:function(visitors) {
						makeList(visitors);
					},
					error:function(xhr,status,msg){
						console.log("상태값 : " + status + " Http에러메시지 : "+msg);
					}
				});
			}
		});
	});
	
	function makeList(visitors) {
		$("#visitorlist").empty();
		$(visitors).each(function(index, visitor) {
			/*
			let str = "<tr id=\"view_" + visitor.visitorId + "\" class=\"view\" data-id=\"" + visitor.visitorId + "\">"
			+ "	<td>" + visitor.visitorId + "</td>"
			+ "	<td>" + visitor.visitorPwd + "</td>"
			+ "	<td>" + visitor.visitorName + "</td>"
			+ "	<td>" + visitor.email + "</td>"
			+ "	<td>" + visitor.joinDate + "</td>"
			+ "	<td><button type=\"button\" class=\"modiBtn btn btn-outline-primary btn-sm\">수정</button> "
			+ "		<button type=\"button\" class=\"delBtn btn btn-outline-danger btn-sm\">삭제</button></td>"
			+ "</tr>"
			+ "<tr id=\"mview_" + visitor.visitorId + "\" data-id=\"" + visitor.visitorId + "\" style=\"display: none;\">"
			+ "	<td>" + visitor.visitorId + "</td>"
			+ "	<td><input type=\"text\" name=\"visitorpwd\" id=\"visitorpwd" + visitor.visitorId + "\" value=\"" + visitor.visitorPwd + "\"></td>"
			+ "	<td>" + visitor.visitorName + "</td>"
			+ "	<td><input type=\"text\" name=\"email\" id=\"email" + visitor.visitorId + "\" value=\"" + visitor.email + "\"></td>"
			+ "	<td>" + visitor.joinDate + "</td>"
			+ "	<td><button type=\"button\" class=\"modifyBtn btn btn-primary btn-sm\">수정</button> "
			+ "		<button type=\"button\" class=\"cancelBtn btn btn-danger btn-sm\">취소</button></td>"
			+ "</tr>";
			*/
			let str = `
			<tr id="view_${'${visitor.visitorId}'}" class="view" data-id="${'${visitor.visitorId}'}">
				<td>${'${visitor.visitorId}'}</td>
				<td>${'${visitor.visitorPwd}'}</td>
				<td>${'${visitor.visitorName}'}</td>
				<td>${'${visitor.email}'}</td>
				<td>${'${visitor.joinDate}'}</td>
				<td>
					<button type="button" class="modiBtn btn btn-outline-primary btn-sm">수정</button>
					<button type="button" class="delBtn btn btn-outline-danger btn-sm">삭제</button>
				</td>
			</tr>
			<tr id="mview_${'${visitor.visitorId}'}" data-id="${'${visitor.visitorId}'}" style="display: none;">
				<td>${'${visitor.visitorId}'}</td>
				<td><input type="text" name="visitorpwd" id="visitorpwd${'${visitor.visitorId}'}" value="${'${visitor.visitorPwd}'}"></td>
				<td>${'${visitor.visitorName}'}</td>
				<td><input type="text" name="email" id="email${'${visitor.visitorId}'}" value="${'${visitor.email}'}"></td>
				<td>${'${visitor.joinDate}'}</td>
				<td>
					<button type="button" class="modifyBtn btn btn-primary btn-sm">수정</button>
					<button type="button" class="cancelBtn btn btn-danger btn-sm">취소</button>
				</td>
			</tr>
			`;
			$("#visitorlist").append(str);
		});//each
	}
	</script>	  

	<div class="container text-center mt-3">
        <div class="col-lg-12 mx-auto">
            <h2 class="p-3 mb-3 shadow bg-light"><mark class="pink">회원목록</mark></h2>
			<div class="mb-3 text-right"><button type="button" class="modiBtn btn btn-outline-info" data-toggle="modal" data-target="#visitorRegModal">등록</button></div>
		  	<table class="table table-hover">
		  		<colgroup>
		            <col width="120">
		            <col width="120">
		            <col width="120">
		            <col width="170">
		            <!-- <col width="*"> -->
		            <col width="120">
		            <col width="130">
		        </colgroup>
		    	<thead>
			      	<tr>
			        	<th class="text-center">아이디</th>
			        	<th class="text-center">비밀번호</th>
			        	<th class="text-center">이름</th>
			        	<th class="text-center">이메일</th>
			        	<!-- <th class="text-center">주소</th> -->
			        	<th class="text-center">가입일</th>
			        	<th class="text-center">수정/삭제</th>
			      	</tr>
		    	</thead>
		    	<tbody id="visitorlist"></tbody>
			</table>
		</div>
		
		<!-- 회원 등록 모달 -->
		<div class="modal" id="visitorRegModal">
		  <div class="modal-dialog">
		    <div class="modal-content">
		
		      <!-- Modal Header -->
		      <div class="modal-header">
		        <h4 class="modal-title">회원등록</h4>
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		      </div>
		
		      <!-- Modal body -->
		      <div class="modal-body">
		        <form id="visitorform" method="post" action="">
					<div class="form-group" align="left">
						<label for="name">이름</label>
						<input type="text" class="form-control" id="visitorname" name="visitorName" placeholder="">
					</div>
					<div class="form-group" align="left">
						<label for="">아이디</label>
						<input type="text" class="form-control" id="visitorid" name="visitorId" placeholder="">
					</div>
					<div class="form-group" align="left">
						<label for="">비밀번호</label>
						<input type="password" class="form-control" id="visitorpwd" name="visitorPwd" placeholder="">
					</div>
					<div class="form-group" align="left">
						<label for="email">이메일</label><br>
						<input type="text" class="form-control" id="email" name="email" placeholder="">
					</div>
					<!-- <div class="form-group" align="left">
						<label for="">주소</label>
						<input type="text" class="form-control" id="address" name="address" placeholder="">
					</div> -->
					<div class="form-group" align="center">
						<button type="button" class="btn btn-primary" id="registerBtn">회원가입</button>
						<button type="reset" class="btn btn-warning">초기화</button>
					</div>
				</form>
		      </div>
		    </div>
		  </div>
		</div>
		
		<!-- 회원 정보 모달 -->
		<div class="modal" id="visitorViewModal">
		  <div class="modal-dialog">
		    <div class="modal-content">
		
		      <!-- Modal Header -->
		      <div class="modal-header">
		        <h4 class="modal-title">회원정보</h4>
		        <button type="button" class="close" data-dismiss="modal">&times;</button>
		      </div>
		
		      <!-- Modal body -->
		      <div class="modal-body">
		       	<table class="table table-bordered">
		            <colgroup>
		                <col width="120">
		                <col width="*">
		                <col width="120">
		                <col width="*">
		            </colgroup>
		            <tbody>
		            <tr>
		                <th class="text-center">ID</th>
		                <td class="text-left" id="vid"></td>
		                <th class="text-center">회원명</th>
		                <td class="text-left" id="vname"></td>
		            </tr>
		            <tr>
		            	<th class="text-center">이메일</th>
		                <td class="text-left" id="vemail"></td>
		                <th class="text-center">가입일</th>
		                <td class="text-left" id="vjoindate"></td>
		            </tr>
		            <!-- <tr>
		                <th class="text-center">주소</th>
		                <td class="text-left" colspan="3" id="vaddress"></td>
		            </tr> -->
		            </tbody>
		        </table>
		      </div>
		    </div>
		  </div>
		</div>
	</div>
</body>
</html>