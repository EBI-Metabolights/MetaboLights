<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

			<c:if test="${not empty message}">
			   <div class="messageBox">
                  <c:out value="${message}" />
               </div>
			</c:if>

               <div style="margin-bottom:40px; padding-left:150px; padding-top:55px">
                  <img src="img/litehouseLogo.png" ></img>
               </div>
            
               <div id="text_header">
                   Metabolights: a database for metabolomics experiments and derived information
                </div>
                
               <div id="text-fullwidth">
                    Metabolights is a database for Metabolomics experiments and derived information.  The database is cross-species, cross-technique and covers metabolite structures and their reference spectra as well as their biological roles, locations and concentrations, and experimental data from metabolic experiments.  
               </div>
