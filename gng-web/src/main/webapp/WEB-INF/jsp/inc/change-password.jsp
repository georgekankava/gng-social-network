<%--
  Created by IntelliJ IDEA.
  User: georgekankava
  Date: 24.03.17
  Time: 11:45 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<div id="messageLabel"></div>
<form ng-submit="submit()">
    <h3>Change current password</h3>
    <table class="table table-condensed">
        <tr>
            <td>
                Current Password
            </td>
            <td>
                <input type="text" class="form-control" id="currentPassword" placeholder="Current Password">
            </td>
        </tr>
        <tr>
            <td>
                New Password
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
</form>
