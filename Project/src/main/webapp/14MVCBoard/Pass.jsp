<%@ page language="java" contentType="text/html; charset=EUC-KR"
    pageEncoding="EUC-KR"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="EUC-KR">
<title>Insert title here</title>
<script type="text/javascript">
	function validateForm(form) {
		if (form.pass.value == ""){
			alert("��й�ȣ�� �Է��ϼ���");
			form.pass.focus();
			return false;
		}
	}
</script>
</head>
<body>
	<h2>����÷���� �Խ��� - ��й�ȣ ����(Pass)</h2>
	<!-- 
		�۾��� �������� �����Ͽ� �ش� �������� ���鶧 ��й�ȣ �����ÿ��� 
		÷�������� �ʿ� �����Ƿ� enctype�� �����ؾ� �Ѵ�. ���� �������� ������
		request���尴ü�� ������ ������ �����Ƿ� �����ؾ� �Ѵ�.
	 -->
	<form name="writeFrm" method="post" action="../mvcboard/pass.do"
		onsubmit="return validateForm(this);">
		
	<!-- 
		�ش� ��û������ �Ѿ�� �Ķ���ʹ� ��Ʈ�ѷ����� ���� �� request������ 
		�����Ͽ� View���� Ȯ���� �� ������, EL�� �̿��ϸ� �ش� �������� 
		param���尴ü�� ��� ���� �޾ƿ� �� �ִ�.
		
		* hidden�ڽ��� ����ϴ� ��� ������������ ����ó�� �Ǳ� ������ ����
		����� �ԷµǾ����� ȭ������ Ȯ���� �� ����. ���� �����ڸ�带 ����ϰų�
		type�� ���������� ��� ������ �� ���� ����� �ԷµǾ����� �ݵ�� 
		Ȯ���ؾ� �Ѵ�.
	 -->
	<input type="hidden" name="idx" value="${ param.idx }" />
	<input type="hidden" name="mode" value="${ param.mode }" />
	<table border="1" width="90%">
		<tr>
			<td>��й�ȣ</td>
			<td>
				<input type="password" name="pass" style="width:100px;" />
			</td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<button type="submit">�����ϱ�</button>		
				<button type="reset">reset</button>		
				<button type="button" onclick="location.href='../mvcboard/list.do';">��� �ٷΰ���</button>
			</td>
		</tr>		
	</table>
	</form>
</body>
</html>