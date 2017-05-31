<%--
  Created by IntelliJ IDEA.
  User: georgekankava
  Date: 22.05.17
  Time: 5:40 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="messageLabel"></div>
<h3>Privacy</h3>
<table ng-controller="PrivacyController" class="table table-condensed">
    <tr>
        <td>
            Include yourself in search
        </td>
        <td>
            <div id="testId" class="btn-group" data-toggle="buttons">
                <label id="includeInSearchYes" ng-click="participateYes()" class="btn btn-primary active">
                    <input type="radio" name="options" autocomplete="off" checked> Yes
                </label>
                <label id="includeInSearchNo" ng-click="participateNo()" class="btn btn-primary">
                    <input type="radio" name="options" autocomplete="off"> No
                </label>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            Who can add you as a friend?
        </td>
        <td>
            <input type="text" class="form-control" id="newPassword" placeholder="New Password">
        </td>
    </tr>
    <tr>
        <td>
            Confirm New Password
        </td>
        <td>
            <input type="text" class="form-control" id="confirmNewPassword" placeholder="Confirm New Password">
        </td>
    </tr>
    <tr>
        <td>
            <input type="submit" class="btn btn-default" style="margin-top: 5px" value="Submit">
        </td>
    </tr>
</table>
