<%-- 
    Document   : LoginJSP.jsp
    Created on : Dec 18, 2020, 1:11:24 PM
    Author     : User
--%>

<%@page import="java.util.ArrayList"%>
<%@page import="com.ebank.beans.Transaction"%>
<%@page import="com.ebank.beans.User"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

<head>

    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <meta name="description" content="">
    <meta name="author" content="">

    <title>E Bank</title>

    
    <link href="css/bootstrap.min.css" rel="stylesheet">

  
    <link href="css/grayscale.css" rel="stylesheet">
 
    <link href="font-awesome/css/font-awesome.min.css" rel="stylesheet" type="text/css">
    <style>
        /*!
 * Start Bootstrap - Grayscale Bootstrap Theme (http://startbootstrap.com)
 * Code licensed under the Apache License v2.0.
 * For details, see http://www.apache.org/licenses/LICENSE-2.0.
 */

body {
    width: 100%;
    height: 100%;
    font-family: Lora,"Helvetica Neue",Helvetica,Arial,sans-serif;
    color: #fff;
    background-color: #000;
}

html {
    width: 100%;
    height: 100%;
}

h1,
h2,
h3,
h4,
h5,
h6 {
    margin: 0 0 35px;
    text-transform: uppercase;
    font-family: Montserrat,"Helvetica Neue",Helvetica,Arial,sans-serif;
    font-weight: 700;
    letter-spacing: 1px;
}

p {
    margin: 0 0 25px;
    font-size: 18px;
    line-height: 1.5;
}

@media(min-width:768px) {
    p {
        margin: 0 0 35px;
        font-size: 20px;
        line-height: 1.6;
    }
}

a {
    color: #42dca3;
    -webkit-transition: all .2s ease-in-out;
    -moz-transition: all .2s ease-in-out;
    transition: all .2s ease-in-out;
}

a:hover,
a:focus {
    text-decoration: none;
    color: #1d9b6c;
}

.light {
    font-weight: 400;
}

.navbar-custom {
    margin-bottom: 0;
    border-bottom: 1px solid rgba(255,255,255,.3);
    text-transform: uppercase;
    font-family: Montserrat,"Helvetica Neue",Helvetica,Arial,sans-serif;
    background-color: #000;
}

.navbar-custom .navbar-brand {
    font-weight: 700;
}

.navbar-custom .navbar-brand:focus {
    outline: 0;
}

.navbar-custom .navbar-brand .navbar-toggle {
    padding: 4px 6px;
    font-size: 16px;
    color: #fff;
}

.navbar-custom .navbar-brand .navbar-toggle:focus,
.navbar-custom .navbar-brand .navbar-toggle:active {
    outline: 0;
}

.navbar-custom a {
    color: #fff;
}

.navbar-custom .nav li a {
    -webkit-transition: background .3s ease-in-out;
    -moz-transition: background .3s ease-in-out;
    transition: background .3s ease-in-out;
}

.navbar-custom .nav li a:hover {
    outline: 0;
    color: rgba(255,255,255,.8);
    background-color: transparent;
}

.navbar-custom .nav li a:focus,
.navbar-custom .nav li a:active {
    outline: 0;
    background-color: transparent;
}

.navbar-custom .nav li.active {
    outline: 0;
}

.navbar-custom .nav li.active a {
    background-color: rgba(255,255,255,.3);
}

.navbar-custom .nav li.active a:hover {
    color: #fff;
}

@media(min-width:768px) {
    .navbar-custom {
        padding: 20px 0;
        border-bottom: 0;
        letter-spacing: 1px;
        background: 0 0;
        -webkit-transition: background .5s ease-in-out,padding .5s ease-in-out;
        -moz-transition: background .5s ease-in-out,padding .5s ease-in-out;
        transition: background .5s ease-in-out,padding .5s ease-in-out;
    }

    .navbar-custom.top-nav-collapse {
        padding: 0;
        border-bottom: 1px solid rgba(255,255,255,.3);
        background: #000;
    }
}

.intro {
    display: table;
    width: 100%;
    height: auto;
    padding: 100px 0;
    text-align: center;
    color: #fff;
    background: url(../img/bg_.jpg) no-repeat bottom center scroll;
    background-color: #000;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    background-size: cover;
    -o-background-size: cover;
}

.intro .intro-body {
    display: table-cell;
    vertical-align: middle;
}

.intro .intro-body .brand-heading {
    font-size: 40px;
}

.intro .intro-body .intro-text {
    font-size: 18px;
}

@media(min-width:768px) {
    .intro {
        height: 100%;
        padding: 0;
    }

    .intro .intro-body .brand-heading {
        font-size: 100px;
    }

    .intro .intro-body .intro-text {
        font-size: 26px;
    }
}

.btn-circle {
    width: 70px;
    height: 70px;
    margin-top: 15px;
    padding: 7px 16px;
    border: 2px solid #fff;
    border-radius: 100%!important;
    font-size: 40px;
    color: #fff;
    background: 0 0;
    -webkit-transition: background .3s ease-in-out;
    -moz-transition: background .3s ease-in-out;
    transition: background .3s ease-in-out;
}

.btn-circle:hover,
.btn-circle:focus {
    outline: 0;
    color: #fff;
    background: rgba(255,255,255,.1);
}

.btn-circle i.animated {
    -webkit-transition-property: -webkit-transform;
    -webkit-transition-duration: 1s;
    -moz-transition-property: -moz-transform;
    -moz-transition-duration: 1s;
}

.btn-circle:hover i.animated {
    -webkit-animation-name: pulse;
    -moz-animation-name: pulse;
    -webkit-animation-duration: 1.5s;
    -moz-animation-duration: 1.5s;
    -webkit-animation-iteration-count: infinite;
    -moz-animation-iteration-count: infinite;
    -webkit-animation-timing-function: linear;
    -moz-animation-timing-function: linear;
}

@-webkit-keyframes pulse {    
    0% {
        -webkit-transform: scale(1);
        transform: scale(1);
    }

    50% {
        -webkit-transform: scale(1.2);
        transform: scale(1.2);
    }

    100% {
        -webkit-transform: scale(1);
        transform: scale(1);
    }
}

@-moz-keyframes pulse {    
    0% {
        -moz-transform: scale(1);
        transform: scale(1);
    }

    50% {
        -moz-transform: scale(1.2);
        transform: scale(1.2);
    }

    100% {
        -moz-transform: scale(1);
        transform: scale(1);
    }
}

.content-section {
    padding-top: 100px;
}

.bgb {
    width: 100%;
    padding: 50px 0;
    color: #fff;
    background: url(../img/downloads-bg.jpg) no-repeat center center scroll;
    background-color: #000;
    -webkit-background-size: cover;
    -moz-background-size: cover;
    background-size: cover;
    -o-background-size: cover;
}

#map {
    width: 100%;
    height: 200px;
    margin-top: 100px;
}

@media(min-width:767px) {
    .content-section {
        padding-top: 250px;
    }

    .download-section {
        padding: 100px 0;
    }

    #map {
        height: 400px;
        margin-top: 250px;
    }
}

.btn {
    border-radius: 0;
    text-transform: uppercase;
    font-family: Montserrat,"Helvetica Neue",Helvetica,Arial,sans-serif;
    font-weight: 400;
    -webkit-transition: all .3s ease-in-out;
    -moz-transition: all .3s ease-in-out;
    transition: all .3s ease-in-out;
}

.btn-default {
    border: 1px solid #42dca3;
    color: #42dca3;
    background-color: transparent;
}

.btn-default:hover,
.btn-default:focus {
    border: 1px solid #42dca3;
    outline: 0;
    color: #000;
    background-color: #42dca3;
}

ul.banner-social-buttons {
    margin-top: 0;
}

@media(max-width:1199px) {
    ul.banner-social-buttons {
        margin-top: 15px;
    }
}

@media(max-width:767px) {
    ul.banner-social-buttons li {
        display: block;
        margin-bottom: 20px;
        padding: 0;
    }

    ul.banner-social-buttons li:last-child {
        margin-bottom: 0;
    }
}

footer {
    padding: 50px 0;
}

footer p {
    margin: 0;
}

::-moz-selection {
    text-shadow: none;
    background: #fcfcfc;
    background: rgba(255,255,255,.2);
}

::selection {
    text-shadow: none;
    background: #fcfcfc;
    background: rgba(255,255,255,.2);
}

img::selection {
    background: 0 0;
}

img::-moz-selection {
    background: 0 0;
}

body {
    webkit-tap-highlight-color: rgba(255,255,255,.2);
}

        
    </style>
</head>

<body id="page-top" data-spy="scroll" data-target=".navbar-fixed-top">

    <!-- Navigation -->
    <nav class="navbar navbar-custom navbar-fixed-top" role="navigation">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-main-collapse">
                    <i class="fa fa-bars"></i>
                </button>
                <a class="navbar-brand page-scroll" href="/bank/index">
                    <i class="fa fa-play-circle"></i>  <span class="light">E</span> Bank
                </a>
            </div>

            <!-- Collect the nav links, forms, and other content for toggling -->
            <div class="collapse navbar-collapse navbar-right navbar-main-collapse">
                <ul class="nav navbar-nav">
                    <!-- Hidden li included to remove active class from about link when scrolled up past about section -->
                    <li class="hidden">
                        <a href="#page-top"></a>
                    </li>
                    
                 
                         <li style="margin-top:15px;margin-left:5px;">
                              <c:choose>
                                    <c:when test="${user!=null}">
                        
                             <a class="page-scroll" href="#topup">Top Up</a>
                                   </c:when>   
                                   <c:otherwise></c:otherwise>
                              </c:choose>
                         </li>
                         <li style="margin-top:15px;margin-left:5px;">
                               <c:choose>
                                    <c:when test="${user!=null}">
                        
                             <a class="page-scroll" href="#transfer">Transfer</a>
                                   </c:when>   
                                   <c:otherwise></c:otherwise>
                              </c:choose>
                          
                        </li>
                         <li style="margin-top:15px;margin-left:5px;">
                              <c:choose>
                                    <c:when test="${user!=null}">
                        
                                        <a class="page-scroll" href="#withdraw">Withdraw</a>
                                   </c:when>   
                                   <c:otherwise></c:otherwise>
                              </c:choose>
                          
                        </li> 
                          
                    
                  
                 
                    <li style="margin-top:15px;margin-left:5px;">
                       
                        <c:choose>
                            <c:when test="${user!=null}">
                               <a href="/bank/profile">${user.getName()}</a>
                       
                            </c:when>    
                            <c:otherwise>
<!--                               <a href="/bank/login">Login</a>
                       -->
                            </c:otherwise>
                        </c:choose>
                    </li>
                     
                </ul>
            </div>
            <!-- /.navbar-collapse -->
        </div>
        <!-- /.container -->
    </nav>

    <!-- Intro Header -->
    <header class="intro">
        <div  style="background: url(../img/bg_.jpg) no-repeat bottom center scroll;" class="intro-body">
            <div style="margin-top: 100px;" class="container">
                <div style="margin-top:-30px;display:flex;" class="row">
                    <div style="width: 70%;margin-left: auto; margin-right: auto;border: none;" class="col-md-8 col-md-offset-2">
                       
                           <%       
   if (session!=null && session.getAttribute("Username")!=null) {
       String suname = session.getAttribute("Username").toString();
         User currentUser = (User)session.getAttribute("user");
        ArrayList<Transaction> listTrx=(ArrayList<Transaction>)session.getAttribute("listTrx");
   %>
<!--       user has logged in-->

<div style="min-height: 120%;padding:10px;color: #515151;background:#fff;border-radius: 10px;box-shadow: inset 0 3px 5px rgba(0, 0, 0, .125);overflow: scroll;">
    <h3 style="margin-bottom:2px;">My Balance</h3>
<p style="font-weight: 800;color: cadetblue;border-bottom:1px solid #989898;margin-bottom: 20px;padding-bottom: 10px;">RM <%=(currentUser.getBalance())%></p>


<h4 style="margin-bottom:2px;">Transaction Record</h4>

<%if(listTrx.size()>0){
    
    
        for(int i=0;i<listTrx.size();i++){
            Transaction trx= listTrx.get(i);
            if(trx.getType().equals("Withdraw") || trx.getType().equals("TransferOut")){
        
              %>
                     <div style="border-bottom:1px solid #e0e0e0;height: 45px;"><p style="" class="trxRecordRow"><span style="float:left;"><%=(trx.getTS())%></span>
    <span style="color:#B72305;float:right;">- <%=(trx.getAmount())%></span></p>
                         <p style="position:absolute;margin-top:20px;font-style: italic;font-size: 16px;"><%=(trx.getRemarks())%></p></div>
    <%
            }else if(trx.getType().equals("TopUp") || trx.getType().equals("TransferIn ")){
                     %>
                     <div style="border-bottom:1px solid #e0e0e0;height: 45px;"><p style="" class="trxRecordRow"><span style="float:left;"><%=(trx.getTS())%></span>
    <span style="color:#29B364;float:right;">+ <%=(trx.getAmount())%></span></p>
                         <p style="position:absolute;margin-top:20px;font-style: italic;font-size: 16px;"><%=(trx.getRemarks())%></p></div>
    <%
         
            }else{
            
           } 
        }
    
    }else{
    
    
    %>
    <h3>No Transaction Record Yet</h3>
    <%
}%>

</div>
   <%
   } else {
   %> 
                
                        <div style="box-shadow: none !important;background:transparent;border: none;" class="modal-content">
                                <div class="modal-header">
                                    <button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button>
                                    <p style="color:#fff;" class="intro-text"> <b style=" font-size: 40px !important;" class="brand-heading">E-Bank</b><br>Online E-Banking Management System<br></p>
                        
                                    <% if("POST".equalsIgnoreCase(request.getMethod())){%>
                                    <div class="alert alert-danger" role="alert">${error}</div>
                                    <%}%>
                                </div>

                                <div class="modal-body">
                                    <form id="loginForm" action="index" class="form col-md-12 center-block" method="POST">
                                      <div class="form-group">
                                        <input id="emailInput" type="text" name="email" class="form-control input-lg" placeholder="Email">
                                      </div>
                                      <div class="form-group">
                                        <input type="password" name="password" class="form-control input-lg" placeholder="Password">
                                      </div>
                                      <div class="form-group">
                                        <button type="submit" class="btn btn-primary btn-lg btn-block">Sign In</button>
                                        <span class="pull-right"><a style="color: #00aced;text-decoration: underline;" href="/bank/signup">Register</a></span>
<!--                                        <span style="float: left;"><a href="#">Need help?</a></span>-->
                                      </div>
                                    </form>
                                </div>
                               
                            </div>
                          
            
   <%
   }
   %>            
                        
                        
                        
                        
                        <a href="#contact" class="btn btn-circle page-scroll">
                            <i class="fa fa-angle-double-down animated"></i>
                        </a>
                    </div>
                </div>
            </div>
        </div>
    </header>
 

    <!-- Download Section -->
   

    <!-- Contact Section -->
    <section style="background: url(../img/bg_.jpg) no-repeat bottom center scroll;" id="contact" class="container content-section text-center">
        <div class="row">
            <div class="col-lg-8 col-lg-offset-2">
                <h2>Contact E-Bank</h2>
                <p>Please contact us if you have any inquiry on E-Bank site</p>
                <p><a href="mailto:feedback@startbootstrap.com">feedback@ebank.com</a>
                </p>
                <ul class="list-inline banner-social-buttons">
                    <li>
                        <a href="https://facebook.com" class="btn btn-default btn-lg"><i class="fa fa-facebook fa-fw"></i> <span class="network-name">Facebook</span></a>
                    </li>
                    <li>
                        <a href="https://instagram.com" class="btn btn-default btn-lg"><i class="fa fa-instagram fa-fw"></i> <span class="network-name">Instagram</span></a>
                    </li>
                    <li>
                        <a href="https://google.com" class="btn btn-default btn-lg"><i class="fa fa-google-plus fa-fw"></i> <span class="network-name">Google+</span></a>
                    </li>
                </ul>
            </div>
        </div>
    </section>

    
    <!-- Footer -->
    <footer>
        <div class="container text-center">
            <p>Copyright &copy; E-Bank 2020</p>
        </div>
    </footer>

    <!-- jQuery -->
    <script src="js/jquery.js"></script>

    <!-- Bootstrap Core JavaScript -->
    <script src="js/bootstrap.min.js"></script>

    <!-- Plugin JavaScript -->
    <script src="js/jquery.easing.min.js"></script>

   
    <!-- Custom Theme JavaScript -->
    <script src="js/grayscale.js"></script>
    
    	<script src="//ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
		<script src="js/bootstrap.min.js"></script>
                <script>
                    $("#loginForm").submit(function(e){
                        e.preventDefault();
                        var form = this;
                        var email = $("#emailInput").val();
                        if(isEmail(email)){
                            form.submit(); 
                        }else{
                            alert("Please enter a valid email");
                        }
                    });
                    function isEmail(email) {
                        console.log(email);
                        var regex = /^([a-zA-Z0-9_.+-])+\@(([a-zA-Z0-9-])+\.)+([a-zA-Z0-9]{2,4})+$/;
                        return regex.test(email);
                  }
                </script>

</body>

</html>

