<%@ taglib uri="http://www.springframework.org/security/tags" prefix="sec" %>
<nav class="navbar navbar-default" role="navigation">
  <!-- Brand and toggle get grouped for better mobile display -->
  <div class="navbar-header">
    <button type="button" class="navbar-toggle" data-toggle="collapse" data-target=".navbar-ex1-collapse">
      <span class="sr-only">Toggle navigation</span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
      <span class="icon-bar"></span>
    </button>
    <a class="navbar-brand" href="/home">GNG Social</a>
  </div>

  <sec:authentication property="principal.userId" var="userId"/>
  <!-- Collect the nav links, forms, and other content for toggling -->
  <div class="collapse navbar-collapse navbar-ex1-collapse">
    <ul class="nav navbar-nav">
      <li><a href="#">Messages</a></li>
      <li><a href="/user-friends?userId=${userId}">Friends</a></li>
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Menu<b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="profile?userId=${userId}">Profile</a></li>
          <li><a href="#">Events</a></li>
          <li><a href="#">Friend Requests</a></li>
          <li><a href="#">Settings</a></li>
        </ul>
      </li>
    </ul>
    <form class="navbar-form navbar-left" role="search">
      <div class="form-group">
        <input id="search-friends-input" type="text" class="form-control col-md-2" placeholder="Search">
      </div>
    </form>
    <ul class="nav navbar-nav navbar-right">
      <li class="dropdown">
        <a href="#" class="dropdown-toggle" data-toggle="dropdown">Menu <b class="caret"></b></a>
        <ul class="dropdown-menu">
          <li><a href="#">Settings</a></li>
          <li><a href="#">Privacy</a></li>
          <li class="divider"></li>
          <li><a href="j_spring_security_logout">Logout</a></li>
        </ul>
      </li>
    </ul>
  </div><!-- /.navbar-collapse -->
</nav>
<div class="row" id="search-list"></div>