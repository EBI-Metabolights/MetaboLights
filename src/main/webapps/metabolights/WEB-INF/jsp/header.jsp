<%@taglib uri="http://www.springframework.org/tags" prefix="spring"%>
 
<h3><spring:message code="label.title"/></h3>
 
<!--
One thing that we must note here is that in header.jsp file, we have specified two links to select language. 
The link sets a request parameter ?lang= when user click on this link. Note that spring identifies this request
parameter by using LocaleChangeInterceptor interceptor and change the local accordingly. 
Also note that while configuring LocaleChangeInterceptor in spring-servlet.xml file, we have specified property "paramName" with value "lang".
Thus the Spring framework will look for a parameter called "lang" from request. 
 --> 

<span style="float: right">
    <a href="?lang=en">en</a>
    |
    <a href="?lang=de">de</a>
    |
    <a href="?lang=nl">nl</a>
    |
    <a href="?lang=es">es</a>

</span>