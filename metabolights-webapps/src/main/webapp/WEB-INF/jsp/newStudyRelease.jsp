<%@taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%--
  ~ EBI MetaboLights - http://www.ebi.ac.uk/metabolights
  ~ Cheminformatics and Metabolism group
  ~
  ~ European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
  ~
  ~ Last modified: 05/30/14 2:25 PM
  ~ Modified by:   cmartin
  ~
  ~
  ~ ©, EMBL, European Bioinformatics Institute, 2014.
  ~
  ~ Licensed under the Apache License, Version 2.0 (the "License");
  ~ you may not use this file except in compliance with the License.
  ~ You may obtain a copy of the License at
  ~
  ~    http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~ Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
  --%>
<link rel="stylesheet" href="${pageContext.request.contextPath}/cssrl/iconfont/font_style.css" type="text/css"/>
<div class="col-md-12">
    <p class="row">
    <h3>New Study Release Mechanism</h3><br>
    <% String agent = request.getHeader ("user-agent");
        String userOS = "";
        try {
            if (agent.toLowerCase().indexOf("mac") >= 0) {
                userOS = "mac";
            } else {
                userOS = "non-mac";
            }
        }catch (Exception e){}
    %>
    <%
        String protocol = "http";
        if(userOS.equals("mac")){
            protocol = "ftp";
        }
    %>
    <div class="well nbr">
        <p>
            Owing to the large volume of study submission, manual curation for every study is no longer practical. For this reason we have introduced a mechanism where a MetaboLights
            curator can, at their discretion and subject to passing our new autonomous validation pipeline, make a study public without a manual curatorial review.
        </p>
    </div>
    <p><a href="mailto:example@example.com?subject=New%20Study%20Release%20Pipeline">Give us feedback about this new mechanism</a>
    </p>
    <br>

</div>

<script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>