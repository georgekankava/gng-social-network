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
            <div class="btn-group" data-toggle="buttons">
                <label id="includeInSearchYes" ng-click="participateYes()" class="btn btn-primary">
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
            <div class="btn-group" data-toggle="buttons">
                <label id="publicLookupStrategy" ng-click="publicLookupStrategy()" class="btn btn-primary">
                    <input type="radio" name="options" autocomplete="off" checked> Everyone
                </label>
                <label id="friendsOfFriendsLookupStrategy" ng-click="friendsOfFriendsLookupStrategy()" class="btn btn-primary">
                    <input type="radio" name="options" autocomplete="off"> Friends Of Friends
                </label>
            </div>
        </td>
    </tr>
    <tr>
        <td>
            Who can see my friends list?
        </td>
        <td>
            <div class="btn-group" data-toggle="buttons">
                <label id="publicViewListStrategy" ng-click="publicViewStrategy()" class="btn btn-primary">
                    <input type="radio" name="options" autocomplete="off" checked> Everyone
                </label>
                <label id="friendsViewListStrategy" ng-click="friendsViewStrategy()" class="btn btn-primary">
                    <input type="radio" name="options" autocomplete="off"> Friends
                </label>
                <label id="onlyMeViewStrategy" ng-click="onlyMeViewStrategy()" class="btn btn-primary">
                    <input type="radio" name="options" autocomplete="off"> Only me
                </label>

            </div>
        </td>
    </tr>
    <tr>
        <td>
            Who can see my posts?
        </td>
        <td>
            <div class="btn-group" data-toggle="buttons">
                <label id="publicPostViewListStrategy" ng-click="changePostViewStrategy('PUBLIC')" class="btn btn-primary">
                    <input type="radio" name="options" autocomplete="off" checked> Everyone
                </label>
                <label id="friendsPostViewListStrategy" ng-click="changePostViewStrategy('FRIENDS')" class="btn btn-primary">
                    <input type="radio" name="options" autocomplete="off"> Friends
                </label>
                <label id="onlyMePostViewStrategy" ng-click="changePostViewStrategy('PRIVATE')" class="btn btn-primary">
                    <input type="radio" name="options" autocomplete="off"> Only me
                </label>

            </div>
        </td>
    </tr>
</table>
