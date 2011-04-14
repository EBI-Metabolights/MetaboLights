<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>

	<h6><FONT color="blue"> Hint: userName/password"</FONT></h6> 

	<form:form action="login.html" commandName="login">
		<table>
			<tr>
				<td>User Name:<FONT color="red"><form:errors path="userName" /></FONT></td>
			</tr>
			<tr>
				<td><form:input path="userName" /></td>
			</tr>
			<tr>
				<td>Password:<FONT color="red"><form:errors path="password" /></FONT></td>
			</tr>
			<tr>
				<td><form:password path="password" /></td>
			</tr>

			<tr>
				<td><input type="submit" value="Submit" /></td>
			</tr>
		</table>

	</form:form>
