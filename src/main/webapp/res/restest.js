/*
 * Web engineering course - University of L'Aquila
 * RESTful resources testing code
 *  
 */


"use strict";

function Restest(testall = true) {
    let bearer_token = null;
    let tests_ok_count = 0;
    let tests_error_count = 0;
    let token_waiting_list = [];
    let THIS = this;

    this.getErrors = function () {
        return tests_error_count;
    };

    this.getToken = function () {
        return bearer_token;
    };

    let setToken = function (token) {
        bearer_token = token;
        let tokenf = document.getElementById("token-field");
        if (tokenf) {
            tokenf.value = token;
        }
    };

    let extractTokenFromHeader = function (header) {
        return header.substring("Bearer".length).trim();
    };

    let sendRestRequest = function (method, url, callback, acceptType = null, payload = null, payloadType = null, token = null, async = true) {
        let xhr = new XMLHttpRequest();
        xhr.open(method, url, async);
        if (token !== null)
            xhr.setRequestHeader("Authorization", "Bearer " + token);
        if (payloadType !== null)
            xhr.setRequestHeader("Content-Type", payloadType);
        if (acceptType !== null)
            xhr.setRequestHeader("Accept", acceptType);
        if (async) {
            xhr.onreadystatechange = function () {
                if (xhr.readyState === 4) {
                    callback(xhr.responseText, xhr.status, xhr.getResponseHeader("Authorization"));
                }
            };
        }
        xhr.send(payload);
        if (!async) {
            callback(xhr.responseText, xhr.status, xhr.getResponseHeader("Authorization"));
    }
    };

    let handleLoginButton = function () {
        let u = document.getElementById("username-field").value;
        let p = document.getElementById("password-field").value;
        sendRestRequest(
                "post", "rest/auth/login",
                function (callResponse, callStatus, callAuthHeader) {
                    if (callStatus === 200) {
                        setToken(extractTokenFromHeader(callAuthHeader));
                    } else {
                        setToken(null);
                    }
                },
                null,
                "username=" + u + "&password=" + p, "application/x-www-form-urlencoded",
                null);

    };

    let handleLogoutButton = function () {
        sendRestRequest(
                "delete", "rest/auth/logout",
                function (callResponse, callStatus) {
                    if (callStatus === 204) {
                        setToken(null);
                    }
                },
                null, null, null, bearer_token);
    };

    let handleRefreshButton = function () {
        sendRestRequest(
                "get", "rest/auth/refresh",
                function (callResponse, callStatus, callAuthHeader) {
                    if (callStatus === 200) {
                        setToken(extractTokenFromHeader(callAuthHeader));
                    } else {
                        setToken(null);
                    }
                },
                null, null, null, bearer_token);

    };

    /////////////////////

    let init = function () {
        //bind login/logout/refresh buttons, if present
        let loginb = document.getElementById("login-button");
        if (loginb)
            loginb.addEventListener("click", function (e) {
                handleLoginButton();
                e.preventDefault();
            });
        let logoutb = document.getElementById("logout-button");
        if (logoutb)
            logoutb.addEventListener("click", function (e) {
                handleLogoutButton();
                e.preventDefault();
            });

        //modify the <a> links that need an authorization header
        let nl = document.querySelectorAll("a[href][data-rest-test-auth]");
        for (let i = 0; i < nl.length; ++i) {
            let anchor = nl.item(i);
            anchor.addEventListener("click", function (e) {
                sendRestRequest(
                        "get", anchor.href,
                        function (callResponse, callStatus) {
                            if (callStatus === 200) {
                                document.body.innerText = callResponse;
                            } else {
                                document.body.innerText = "Status code: " + callStatus;
                            }
                        },
                        null, null, null, bearer_token);
                e.preventDefault();
            });
        }
    };

    init();
}
