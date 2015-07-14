<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>

<nav id="header_top" class="navbar navbar-inverse navbar-fixed-top">
    <div class="container-fluid">
        <!-- Brand and toggle get grouped for better mobile display -->
        <div class="navbar-header">
            <button type="button" class="navbar-toggle collapsed" data-toggle="collapse"
            data-target="#bs-example-navbar-collapse-1">
                <span class="sr-only">
                    Toggle navigation
                </span>
                <span class="icon-bar">
                </span>
                <span class="icon-bar">
                </span>
                <span class="icon-bar">
                </span>
            </button>
            <a class="navbar-brand" href="#">
                Brand
            </a>
        </div>
        <!-- Collect the nav links, forms, and other content for toggling -->
        <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
            <form class="navbar-form navbar-left hide" role="search">
                <div class="form-group">
                    <input type="text" class="form-control" placeholder="Search">
                </div>
                <button type="submit" class="btn btn-default">
                    Submit
                </button>
            </form>
            <ul class="nav navbar-nav navbar-right">
                <li class="dropdown ">
                    <a class="dropdown-toggle" role="button" data-toggle="dropdown" href="#">
                        <i class="fa fa-user"></i> ${nickname} <i class="icon-caret-down"></i>
                        <span class="caret">
                        </span>
                    </a>
                    <ul class="dropdown-menu" role="menu">
                        <li>
                            <a href="#">
                                Action
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                Another action
                            </a>
                        </li>
                        <li>
                            <a href="#">
                                Something else here
                            </a>
                        </li>
                        <li class="divider">
                        </li>
                        <li class="dropdown-header">
                            Nav header
                        </li>
                        <li>
                            <a href="#">
                                Separated link
                            </a>
                        </li>
                        <li>
                            <a tabindex="-1" href="<%=request.getContextPath()%>/adminlogout">
                                退出
                            </a>
                        </li>
                    </ul>
                </li>
            </ul>
        </div>
    </div>
</nav>

