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

<jsp:include page="/WEB-INF/jsp/tiles/head.jsp"/>

<body>

<jsp:include page="/WEB-INF/jsp/tiles/custodian/header.jsp"/>

<div class="container">

    <!-- Main hero unit for a primary marketing message or call to action -->
    <div class="hero-unit">
        <h1>Third Party Administrative Portal</h1>
        <p>This is a template that exemplifies a minimal set of facilities needed to manage the Third Party sandbox. You may review the retail customers in
        in the sandbox, and look at the BatchLists that have been created as the result of notifications having been received from the DataCustodian.
  

            <h2>Becoming A Third Party</h2>
            <p>Green Button Third Parties are simply applications that support the 
              patterns of interactions required for communication with Certified Green Button DataCustodians.
              Each Data Custodian will require registration of their supported Third Parties. That process is, for now,
              manual although the Green Button Community is committed to supporting dynamic registration in the near future.

            <h2>Developers</h2>
            <p>Third Party developers will need to familiarize themselves with the <a href="http://energyos.github.io/OpenESPI-GreenButton-API-Documentation/">general XML format of Green Button Data</a>.</p>

            <h2>Community</h2>
            <p>Please consider following the discussions of the <a href="http://www.greenbuttondata.org/community">Green Button Community</a> so that you may stay current with issues involved in being a Green Button Third Party.
        </div>
    </div>

    <hr>

    <jsp:include page="/WEB-INF/jsp/tiles/footer.jsp"/>

</div>

</body>
</html>
