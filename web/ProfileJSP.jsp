<%-- 
    Document   : ProfileJSP
    Created on : Dec 18, 2020, 1:21:19 PM
    Author     : User
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
﻿<html>
<head>
    <meta charset="utf-8" />
    <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1" />
    <meta name="description" content="" />
    <meta name="author" content="" />
    <!--[if IE]>
        <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
        <![endif]-->
    <title>Bootstrap user profile template</title>
    <!-- BOOTSTRAP STYLE SHEET -->
    
    <link href="css/grayscale.css" rel="stylesheet">
 
    <link href="css/bootstrap.css" rel="stylesheet" />
    <!-- FONT-AWESOME STYLE SHEET FOR BEAUTIFUL ICONS -->
    <link href="css/font-awesome.css" rel="stylesheet" />
     <!-- CUSTOM STYLE CSS -->
    <style type="text/css">
               .btn-social {
            color: white;
            opacity: 0.8;
        }

            .btn-social:hover {
                color: white;
                opacity: 1;
                text-decoration: none;
            }

        .btn-facebook {
            background-color: #3b5998;
        }

        .btn-twitter {
            background-color: #00aced;
        }

        .btn-linkedin {
            background-color: #0e76a8;
        }

        .btn-google {
            background-color: #c32f10;
        }
    </style>
</head>
<body style=" background: url(../img/bg_.jpg) no-repeat bottom center scroll;">
    <div class="navbar navbar-default navbar-static-top">
        <div class="container">
            <div class="navbar-header">
                <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-collapse">
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                    <span class="icon-bar"></span>
                </button>
                <a class="navbar-brand" href="#">Design Bootstrap</a>
            </div>
            <div class="navbar-collapse collapse">
                <ul class="nav navbar-nav ">
                    <li><a href="#">HOME</a></li>
                    <li><a href="#">ABOUT</a></li>
                    <li><a href="#">SERVICES</a></li>
                    <li><a href="#">CONTACT</a></li>
                </ul>
            </div>

        </div>
    </div>
    <!-- NAVBAR CODE END -->


    <div class="container">
        <section style="padding-bottom: 50px; padding-top: 50px;">
            <div class="row">
                <div class="col-md-4">
                    <img src="data:image/png;base64,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" class="img-rounded img-responsive" />
                    <br />
                    <br />
                      <form id="detailForm" class="form col-md-12 center-block" method="POST">
                        <label>Registered User Email(Username)</label>
                        <input type="text" class="form-control" name="email" placeholder="Email" value="${user.getEmail()}">
                        <label>Registered Name</label>
                        <input type="text" class="form-control"  name="name" placeholder="Name" value="${user.getName()}">
                        <label>Registered Phone</label>
                        <input type="text" class="form-control" name="phone" placeholder="Phonee Number" value="${user.getPhone()}">
                        <label>Address</label>
                        <textarea type="text" class="form-control" name="address" placeholder="address" value="${user.getAddress()}"></textarea>
                        <br>
                        <button type="submit" class="btn btn-success btn-lg btn-block">Update Details</button> 
                      </form>
                    <br /><br/>
                </div>
                <div class="col-md-8">
                    <div class="alert alert-info">
                        <h2>Welcome ${user.name} </h2>
                        <h4>You online E-Banking </h4>
                        <p>
                            Change your personal information here like Email, Phone Number, Address, Password, etc.
                        </p>
                    </div>
                    <div >
                        <a href="#" class="btn btn-social btn-facebook">
                            <i class="fa fa-facebook"></i>&nbsp; Facebook</a>
                        <a href="#" class="btn btn-social btn-google">
                            <i class="fa fa-google-plus"></i>&nbsp; Google</a>
                        <a href="#" class="btn btn-social btn-twitter">
                            <i class="fa fa-twitter"></i>&nbsp; Twitter </a>
                        <a href="#" class="btn btn-social btn-linkedin">
                            <i class="fa fa-linkedin"></i>&nbsp; Linkedin </a>
                    </div>
                    <div class="form-group col-md-8">
                          <form id="passwordForm" class="form col-md-12 center-block" method="POST">
                        <h3>Change YOur Password</h3>
                        <br />
                        <label>Enter Old Password</label>
                        <input type="password" id="passwordInput" name="password" class="form-control">
                        <label>Enter New Password</label>
                        <input type="password" id="newpasswordInput" name="newpasswordInput" class="form-control">
                        <label>Confirm New Password</label>
                        <input type="password" id="passwordRetypedInput" name="passwordRetypedInput" class="form-control" />
                        <br>
                         <button type="submit" class="btn btn-warning btn-lg btn-block">Update Password</button>  
                         </form>
                    </div>
                </div>
            </div>
            <!-- ROW END -->


        </section>
        <!-- SECTION END -->
    </div>
    <!-- CONATINER END -->

    <!-- REQUIRED SCRIPTS FILES -->
    <!-- CORE JQUERY FILE -->
    <script src="js/jquery-1.11.1.js"></script>
    <!-- REQUIRED BOOTSTRAP SCRIPTS -->
    <script src="js/bootstrap.js"></script>
    <script>
                    $("#passwordForm").submit(function(e){
                        e.preventDefault();
                        var form = this;
                        var password = $("#newpasswordInput").val();
                        var password_r = $("#passwordRetypedInput").val();
                       
                        if(password==(password_r)){
                            form.submit(); 
                        }else{
                            alert("Both password must be matched!");
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

