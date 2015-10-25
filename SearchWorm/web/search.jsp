<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%--
  Created by IntelliJ IDEA.
  User: abhin
  Date: 10/25/2015
  Time: 10:57 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="css/searchworm.css" />" rel="stylesheet">
    <title>Search Worm</title>

</head>

<body>

<!--Content for Header-->
<div id="header">

    <nav class="navbar navbar-default">
        <div class="container-fluid">
            <div class="navbar-header">
                <a class="navbar-brand" href="#">
                    <img src="images/logo.png" class="header-img"/>

                    <p class="header-text">SearchWorm</p>
                </a>
            </div>
            <ul class="nav navbar-nav navbar-right">
                <li><a href="upload.html">Upload Books</a></li>
                <li><a href="index.html">Search Books</a></li>
            </ul>
        </div>
    </nav>

</div>

<!--Content for Main Container-->
<div id="mainContainer">

    <div class="row">
        <div class="col-12 col-lg-12 col-sm-12 col-md-12">&nbsp;</div>
    </div>
    <div class="row">
        <div class="col-12 col-lg-12 col-sm-12 col-md-12">&nbsp;</div>
    </div>
    <div class="row">
        <div class="col-12 col-lg-12 col-sm-12 col-md-12">&nbsp;</div>
    </div>
    <div class="row">
        <div class="col-12 col-lg-12 col-sm-12 col-md-12">&nbsp;</div>
    </div>
    <div class="row">
        <div class="col-12 col-lg-12 col-sm-12 col-md-12">&nbsp;</div>
    </div>
    <div class="row">
        <div class="col-12 col-lg-12 col-sm-12 col-md-12">&nbsp;</div>
    </div>
    <div class="row">
        <div class="col-12 col-lg-12 col-sm-12 col-md-12">&nbsp;</div>
    </div>

    <div class="row">

        <div class="col-1 col-lg-1 col-sm-1 col-md-1"></div>

        <div class="col-10 col-lg-10 col-sm-10 col-md-10">

            <!--Beginning of Panel-->
            <div class="panel panel-default">
                <div class="panel-body">
                    <p class="lead">What do you want to search today</p>

                    <form action="search.jsp" method="get">
                    <div class="input-group">
                        <input type="search" class="form-control" id="searchBox" placeholder="Type to Search!"
                               name='query' value=''>

                        <div class="input-group-btn">
                            <button type="submit" class="btn btn-success"><i class="glyphicon glyphicon-search"></i>&nbsp;&nbsp;&nbsp;&nbsp;Search
                                Books
                            </button>
                        </div>
                    </div>
                    </form>
                </div>
            </div>
            <!--End of Panel-->

        </div>

        <div class="col-1 col-lg-1 col-sm-1 col-md-1"></div>

    </div>


</div>

<!--Content for Footer-->
<div id="footer">

</div>


<!--Javascripts Included-->
<script href="<c:url value="js/jquery-2.1.1.min.js" />"></script>
<script href="<c:url value="js/bootstrap.min.js" />"></script>
</body>
</html>