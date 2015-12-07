<%--
  Created by IntelliJ IDEA.
  User: abhin
  Date: 10/25/2015
  Time: 10:23 PM
  To change this template use File | Settings | File Templates.
--%>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link href="<c:url value="css/bootstrap.min.css" />" rel="stylesheet">
    <link href="<c:url value="css/searchworm.css" />" rel="stylesheet">
    <title>Upload Books</title>
    <style>
        label.myLabel input[type="file"] {
            position: fixed;
            top: -1000px;
        }
    </style>
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
                <li><a href="/upload">Upload Books</a></li>
                <li><a href="/search">Search Books</a></li>
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
                    <p class="lead">Upload Book</p>

                    <form action="/upload" method="post" enctype="multipart/form-data">
                        <div class="input-group">
                            <label class="myLabel">
                                <input type="file" name="file" size="50" multiple/>
                                <span class="btn-lg btn-success"><i class="glyphicon glyphicon-upload"></i>&nbsp;&nbsp;&nbsp;&nbsp;Choose files</span>
                            </label>
                            <br/>
                            <label class="myLabel">
                                <input type="submit" value="Upload" class="btn-lg btn-success">
                            </label>
                        </div>
                    </form>
                </div>
            </div>
            <!--End of Panel-->
        </div>
        <div class="col-1 col-lg-1 col-sm-1 col-md-1"></div>
    </div>
</div>

<!--Javascript Included-->
<script href="<c:url value="js/jquery-2.1.1.min.js" />"></script>
<script href="<c:url value="js/bootstrap.min.js" />"></script>
</body>
</html>