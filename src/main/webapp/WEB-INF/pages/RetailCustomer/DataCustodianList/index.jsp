<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security" uri="http://www.springframework.org/security/tags" %>
<%--
  ~ Copyright 2013 EnergyOS.org
  ~
  ~    Licensed under the Apache License, Version 2.0 (the "License");
  ~    you may not use this file except in compliance with the License.
  ~    You may obtain a copy of the License at
  ~
  ~        http://www.apache.org/licenses/LICENSE-2.0
  ~
  ~    Unless required by applicable law or agreed to in writing, software
  ~    distributed under the License is distributed on an "AS IS" BASIS,
  ~    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
  ~    See the License for the specific language governing permissions and
  ~    limitations under the License.
  --%>

<!DOCTYPE html>
<html lang="en">

<jsp:include page="../../tiles/head.jsp"/>

<body>

<script type="text/javascript">
    $(function() {
        $("form input[type=radio]").on("click", function() {
            $("input[name=Data_custodian_URL]").val($(this).data("data-custodian-url"));
            $("input[type=submit]").removeAttr("disabled")
        });
    });
</script>

<jsp:include page="../../tiles/header.jsp"/>
<security:authentication var="principal" property="principal" />

<div class="container">
    <div class="row">
        <div class="span12">
            <h2>Data Custodian List</h2>


            <form method="POST" action="<c:url value='/RetailCustomer/${principal.id}/ScopeSelection'/>">
                <table class="table table-striped">
                    <thead>
                    <tr>
                        <th>Name</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach var="dataCustodian" items="${dataCustodianList}">
                        <tr>
                            <td>
                                <label>
                                    <input type="radio" name="Data_custodian" value="${dataCustodian.id}" data-data-custodian-url="${dataCustodian.url}" class="data-custodian" />
                                    <c:out value="${dataCustodian.description}"/>
                                </label>
                            </td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
                <input type="hidden" name="Data_custodian_URL"/>
                <input type="submit" name="next" value="Next" disabled="true" class="btn btn-primary">
            </form>
        </div>
    </div>

    <hr>

    <jsp:include page="../../tiles/footer.jsp"/>

</div>

</body>
</html>
