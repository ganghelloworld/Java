<%@page language="java" contentType="text/html"%>
<html>
<head><title>Hello World dynamic HTML</title></head>
<body>
Hello World!
<%
out.println("<br/>Your IP address is " + request.getRemoteAddr());
String userAgent = request.getHeader("user-agent");
String browser = "unknown";
out.print("<br/>and your browser is ");
if (userAgent != null) {
	if (userAgent.indexOf("MSIE") > -1) {
		browser = "MS Internet Explorer";
	}
	else if (userAgent.indexOf("Firefox") > -1) {
		browser = "Mozilla Firefox";
	}
	else
	{
		browser = "what?";
	}
}
out.println(browser);
%>
</body>
</html>
