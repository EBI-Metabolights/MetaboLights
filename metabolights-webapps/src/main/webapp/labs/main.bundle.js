webpackJsonp([1],{

/***/ "../../../../../src async recursive":
/***/ (function(module, exports) {

function webpackEmptyContext(req) {
	throw new Error("Cannot find module '" + req + "'.");
}
webpackEmptyContext.keys = function() { return []; };
webpackEmptyContext.resolve = webpackEmptyContext;
module.exports = webpackEmptyContext;
webpackEmptyContext.id = "../../../../../src async recursive";

/***/ }),

/***/ "../../../../../src/app/app.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/app.component.html":
/***/ (function(module, exports) {

module.exports = "<router-outlet></router-outlet>"

/***/ }),

/***/ "../../../../../src/app/app.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AppComponent = (function () {
    function AppComponent() {
    }
    return AppComponent;
}());
AppComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'app-root',
        template: __webpack_require__("../../../../../src/app/app.component.html"),
        styles: [__webpack_require__("../../../../../src/app/app.component.css")]
    })
], AppComponent);

//# sourceMappingURL=app.component.js.map

/***/ }),

/***/ "../../../../../src/app/app.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__("../../../platform-browser/@angular/platform-browser.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("../../../forms/@angular/forms.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__ng_bootstrap_ng_bootstrap__ = __webpack_require__("../../../../@ng-bootstrap/ng-bootstrap/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__auth_guard_service__ = __webpack_require__("../../../../../src/app/auth-guard.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__auth_service__ = __webpack_require__("../../../../../src/app/auth.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__app_routes__ = __webpack_require__("../../../../../src/app/app.routes.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__app_component__ = __webpack_require__("../../../../../src/app/app.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__page_not_found_page_not_found_component__ = __webpack_require__("../../../../../src/app/page-not-found/page-not-found.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__login_login_component__ = __webpack_require__("../../../../../src/app/login/login.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__shared_shared_module__ = __webpack_require__("../../../../../src/app/shared/shared.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__work_space_work_space_module__ = __webpack_require__("../../../../../src/app/work-space/work-space.module.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




// Angular2 Bootstrap import

// Auth services


// Router import

// Root level application routes import 






var AppModule = (function () {
    function AppModule() {
    }
    return AppModule;
}());
AppModule = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__angular_core__["b" /* NgModule */])({
        declarations: [
            __WEBPACK_IMPORTED_MODULE_9__app_component__["a" /* AppComponent */],
            __WEBPACK_IMPORTED_MODULE_10__page_not_found_page_not_found_component__["a" /* PageNotFoundComponent */],
            __WEBPACK_IMPORTED_MODULE_11__login_login_component__["a" /* LoginComponent */]
        ],
        imports: [
            __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["a" /* BrowserModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["a" /* FormsModule */],
            __WEBPACK_IMPORTED_MODULE_12__shared_shared_module__["a" /* SharedModule */],
            __WEBPACK_IMPORTED_MODULE_13__work_space_work_space_module__["a" /* WorkSpaceModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["b" /* ReactiveFormsModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_http__["a" /* HttpModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_http__["b" /* JsonpModule */],
            __WEBPACK_IMPORTED_MODULE_4__ng_bootstrap_ng_bootstrap__["a" /* NgbModule */].forRoot(),
            __WEBPACK_IMPORTED_MODULE_7__angular_router__["a" /* RouterModule */].forRoot(__WEBPACK_IMPORTED_MODULE_8__app_routes__["a" /* routes */])
        ],
        providers: [
            __WEBPACK_IMPORTED_MODULE_5__auth_guard_service__["a" /* AuthGuard */],
            __WEBPACK_IMPORTED_MODULE_6__auth_service__["a" /* AuthService */]
        ],
        bootstrap: [__WEBPACK_IMPORTED_MODULE_9__app_component__["a" /* AppComponent */]]
    })
], AppModule);

//# sourceMappingURL=app.module.js.map

/***/ }),

/***/ "../../../../../src/app/app.routes.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__page_not_found_page_not_found_component__ = __webpack_require__("../../../../../src/app/page-not-found/page-not-found.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__login_login_component__ = __webpack_require__("../../../../../src/app/login/login.component.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return routes; });


var routes = [
    { path: 'login', component: __WEBPACK_IMPORTED_MODULE_1__login_login_component__["a" /* LoginComponent */] },
    { path: '', redirectTo: 'workspace/dashboard', pathMatch: 'full' },
    { path: 'workspace/project', redirectTo: 'workspace/projects', pathMatch: 'full' },
    { path: 'workspace', redirectTo: 'workspace/dashboard', pathMatch: 'full' },
    { path: '**', component: __WEBPACK_IMPORTED_MODULE_0__page_not_found_page_not_found_component__["a" /* PageNotFoundComponent */] }
];
//# sourceMappingURL=app.routes.js.map

/***/ }),

/***/ "../../../../../src/app/auth-guard.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__auth_service__ = __webpack_require__("../../../../../src/app/auth.service.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AuthGuard; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var AuthGuard = (function () {
    function AuthGuard(authService, router) {
        this.authService = authService;
        this.router = router;
    }
    AuthGuard.prototype.canActivate = function (route, state) {
        var url = state.url;
        return this.checkLogin(url);
    };
    AuthGuard.prototype.canActivateChild = function (route, state) {
        return this.canActivate(route, state);
    };
    AuthGuard.prototype.checkLogin = function (url) {
        if (localStorage.getItem('jwt') != null) {
            this.authService.isLoggedIn = true;
            return true;
        }
        // Store the attempted URL for redirecting
        this.authService.redirectUrl = url;
        // Navigate to the login page with extras
        this.router.navigate(['/login']);
        return false;
    };
    return AuthGuard;
}());
AuthGuard = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["c" /* Injectable */])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__auth_service__["a" /* AuthService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */]) === "function" && _b || Object])
], AuthGuard);

var _a, _b;
//# sourceMappingURL=auth-guard.service.js.map

/***/ }),

/***/ "../../../../../src/app/auth.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__common_headers__ = __webpack_require__("../../../../../src/app/common/headers.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__common_globals__ = __webpack_require__("../../../../../src/app/common/globals.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__work_space_dashboard_dashboard__ = __webpack_require__("../../../../../src/app/work-space/dashboard/dashboard.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_rxjs_add_operator_map__ = __webpack_require__("../../../../rxjs/add/operator/map.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_6_rxjs_add_operator_map__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AuthService; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var AuthService = (function () {
    function AuthService(router, http) {
        this.router = router;
        this.http = http;
        this.isLoggedIn = false;
    }
    AuthService.prototype.login = function (body) {
        var _this = this;
        this.http.post(__WEBPACK_IMPORTED_MODULE_2__common_globals__["a" /* LabsURL */]['authenticate'], body, { headers: __WEBPACK_IMPORTED_MODULE_1__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            localStorage.setItem('jwt', response.headers.get("jwt"));
            localStorage.setItem('user', response.headers.get("user"));
            _this.isLoggedIn = true;
            _this.router.navigate(['workspace', 'dashboard']);
        }, function (error) {
            alert(error.text());
            console.log(error.text());
        });
    };
    AuthService.prototype.logout = function () {
        localStorage.removeItem('jwt');
        localStorage.removeItem('user');
        this.isLoggedIn = false;
        this.router.navigate(['login']);
    };
    AuthService.prototype.initializeWorkSpace = function () {
        var _this = this;
        var body = {
            "jwt": localStorage.getItem("jwt"),
            "user": localStorage.getItem("user")
        };
        return this.http.post(__WEBPACK_IMPORTED_MODULE_2__common_globals__["a" /* LabsURL */]['initialise'], body, { headers: __WEBPACK_IMPORTED_MODULE_1__common_headers__["a" /* contentHeaders */] })
            .map(function (response) {
            var body = JSON.parse(response.json().content);
            var dashBoard = new __WEBPACK_IMPORTED_MODULE_5__work_space_dashboard_dashboard__["a" /* Dashboard */]().deserialize(body);
            _this.dashBoard = dashBoard;
            return dashBoard;
        }, function (error) {
            alert(error.text());
        });
    };
    return AuthService;
}());
AuthService = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["c" /* Injectable */])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_3__angular_router__["b" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__angular_router__["b" /* Router */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_4__angular_http__["c" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4__angular_http__["c" /* Http */]) === "function" && _b || Object])
], AuthService);

var _a, _b;
//# sourceMappingURL=auth.service.js.map

/***/ }),

/***/ "../../../../../src/app/common/globals.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LabsURL; });
var environment = "dev";
<<<<<<< HEAD
var domain = "http://localhost:8080/metabolights/";
if (environment == "prod") {
    domain = "https://www.ebi.ac.uk/metabolights/";
}
else if (environment == "dev") {
    domain = "http://wwwdev.ebi.ac.uk/metabolights/";
=======
var domain = "//localhost:8080/metabolights/";
if (environment == "prod") {
    domain = "//www.ebi.ac.uk/metabolights/";
}
else if (environment == "dev") {
    domain = "//wwwdev.ebi.ac.uk/metabolights/";
>>>>>>> development_backup
}
var LabsURL = {};
LabsURL['domain'] = domain;
var webservice = domain + "webservice/";
// Application
LabsURL['authenticate'] = webservice + 'labs/authenticate';
LabsURL['studiesList'] = webservice + 'study/list';
// Workspace
LabsURL['initialise'] = webservice + 'labs-workspace/initialise';
LabsURL['createProject'] = webservice + 'labs-workspace/createProject';
LabsURL['deleteProject'] = webservice + 'labs-workspace/deleteProject';
LabsURL['settings'] = webservice + 'labs-workspace/settings';
LabsURL['download'] = webservice + 'labs-workspace/downloadFile';
// Project
LabsURL['projectContent'] = webservice + 'labs-project/content';
LabsURL['projectDetails'] = webservice + 'labs-project/details';
LabsURL['submitProject'] = webservice + 'labs-project/submitProject';
LabsURL['editProjectDetails'] = webservice + 'labs-project/editDetails';
LabsURL['delete'] = webservice + 'labs-project/deleteFiles';
// Tools
LabsURL['convertMZMLToISA'] = webservice + 'labs-project/convertMZMLToISA';
LabsURL['convertNMRMLToISA'] = webservice + 'labs-project/convertNMRMLToISA';
LabsURL['getJobLogs'] = webservice + 'labs-project/getJobLogs';
//# sourceMappingURL=globals.js.map

/***/ }),

/***/ "../../../../../src/app/common/headers.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return contentHeaders; });

var contentHeaders = new __WEBPACK_IMPORTED_MODULE_0__angular_http__["d" /* Headers */]();
contentHeaders.append('Accept', 'application/json');
contentHeaders.append('Content-Type', 'application/json');
//# sourceMappingURL=headers.js.map

/***/ }),

/***/ "../../../../../src/app/login/login.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".card-container.card {\n    max-width: 350px;\n    padding: 40px 20px 15px 20px;\n    font-weight: lighter;\n}\n\np{\n    font-weight: lighter;\n}\n\n/*\n * Card component\n */\n.card {\n    background-color: #F7F7F7;\n    /* just in case there no content*/\n    margin: 0 auto;\n    /* shadows and rounded borders */\n    border-radius: 2px;\n    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);\n}\n\n.profile-img-card {\n    width: 96px;\n    height: 96px;\n    margin: 0 auto 10px;\n    display: block;\n    border-radius: 50%;\n}\n\n/*\n * Form styles\n */\n.profile-name-card {\n    font-size: 16px;\n    font-weight: bold;\n    text-align: center;\n    margin: 10px 0 0;\n    min-height: 1em;\n}\n\n.reauth-email {\n    display: block;\n    color: #404040;\n    line-height: 2;\n    margin-bottom: 10px;\n    font-size: 14px;\n    text-align: center;\n    overflow: hidden;\n    text-overflow: ellipsis;\n    white-space: nowrap;\n    box-sizing: border-box;\n}\n\n.form-signin #inputEmail,\n.form-signin #inputPassword {\n    direction: ltr;\n    height: 44px;\n    font-size: 16px;\n}\n\n.form-signin input[type=email],\n.form-signin input[type=password],\n.form-signin input[type=text],\n.form-signin button {\n    width: 100%;\n    display: block;\n    margin-bottom: 10px;\n    z-index: 1;\n    position: relative;\n    box-sizing: border-box;\n}\n\n.form-signin .form-control:focus {\n    border-color: rgb(104, 145, 162);\n    outline: 0;\n    box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgb(104, 145, 162);\n}\n\n.btn.btn-signin {\n    /*background-color: #4d90fe; */\n    background-color: rgb(104, 145, 162);\n    /* background-color: linear-gradient(rgb(104, 145, 162), rgb(12, 97, 33));*/\n    padding: 0px;\n    font-weight: 700;\n    font-size: 14px;\n    height: 36px;\n    border-radius: 3px;\n    border: none;\n    transition: all 0.218s;\n}\n\n.btn.btn-signin:hover,\n.btn.btn-signin:active,\n.btn.btn-signin:focus {\n    background-color: rgb(12, 97, 33);\n}\n\n.forgot-password {\n    color: rgb(104, 145, 162);\n}\n\n.forgot-password:hover,\n.forgot-password:active,\n.forgot-password:focus{\n    color: rgb(12, 97, 33);\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/login/login.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"page-wrapper bg1 vc\">\n    <div class=\"container\">\n        <div class=\"row\">\n            <div class=\"col-xs-12 col-sm-12 col-md-8 col-lg-8\">\n                <br>\n                <h1>Online staging area to facilitate cloud based data analysis and submissions</h1>\n                <br>\n                <br>\n                <div class=\"row\">\n                    <div class=\"col-md-5\">\n                        <div class=\"media\">\n                            <i class=\"fa fa-3x fa-database text-primary\"></i>\n                          &emsp;\n                          <div class=\"media-body\">\n                            <h5 class=\"mt-0\">Online staging</h5>\n                            <p>Secure and fast</p>\n                          </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-5\">\n                        <div class=\"media\">\n                          <i class=\"fa fa-3x fa-file text-primary\"></i>&emsp;\n                          <div class=\"media-body\">\n                            <h5 class=\"mt-0\">File format conversions</h5>\n                            <p>Open / Standard formats to ISA</p>\n                          </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-12\">&nbsp;</div>\n                    <div class=\"col-md-5\">\n                        <div class=\"media\">\n                          <i class=\"fa fa-3x fa-binoculars text-primary\"></i>&emsp;\n                          <div class=\"media-body\">\n                            <h5 class=\"mt-0\">Data analysis</h5>\n                            <p>Galaxy workflows and re-analysis pipelines</p>\n                          </div>\n                        </div>\n                    </div>\n                    <div class=\"col-md-5\">\n                        <div class=\"media\">\n                          <i class=\"fa fa-3x fa-upload text-primary\"></i>&emsp;\n                          <div class=\"media-body\">\n                            <h5 class=\"mt-0\">Submit your data</h5>\n                            <p>MetaboLights study submissions</p>\n                          </div>\n                        </div>\n                    </div>\n                </div>\n                <!-- <small class=\"text-muted\">\n                    PROJECTS\n                </small><br><br>\n                <img src=\"assets/img/metaboflow.png\" height=\"50\" alt=\"\">\n                <br><br> -->\n            </div>\n            <div class=\"col-xs-12 col-sm-12 col-md-4 col-lg-4\">\n                <div class=\"card card-container\"><br>\n                    <img id=\"profile-img\" class=\"profile-img-card\" src=\"assets/img/user.png\" /><br>\n                    <h4 class=\"text-md-center\"><img height=\"30\" src=\"assets/img/logo.png\" />&nbsp;MetaboLights Labs <sup class=\"text-danger\"><small>BETA</small></sup></h4>\n                    <form class=\"form-signin\" role=\"form\" (submit)=\"login($event, email.value, secret.value)\">\n                        <span id=\"reauth-email\" class=\"reauth-email\"></span>\n                        <input type=\"email\" #email class=\"form-control\" id=\"useremail\" placeholder=\"Email address\">\n                        <input type=\"password\" #secret class=\"form-control\" id=\"password\" placeholder=\"Password\">\n                        <button class=\"btn btn-primary\" type=\"submit\">Sign in</button>\n                    </form>\n                </div>\n                <div class=\"clearfix\">\n                   &nbsp;\n                </div>\n                 <p class=\"text-center\">Not Registered Yet? <a target=\"_blank\" href=\"http://wwwdev.ebi.ac.uk/metabolights/newAccount\">Register here</a></p>\n            </div>\n        </div>\n    </div>\n</div>"

/***/ }),

/***/ "../../../../../src/app/login/login.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__auth_service__ = __webpack_require__("../../../../../src/app/auth.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LoginComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var LoginComponent = (function () {
    function LoginComponent(router, authService) {
        this.router = router;
        this.authService = authService;
    }
    LoginComponent.prototype.ngOnInit = function () {
        if (this.authService.isLoggedIn) {
            this.router.navigate(['workspace/dashboard']);
        }
    };
    LoginComponent.prototype.login = function (event, email, secret) {
        event.preventDefault();
        var body = JSON.stringify({ email: email, secret: secret });
        this.authService.login(body);
    };
    return LoginComponent;
}());
LoginComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'app-login',
        template: __webpack_require__("../../../../../src/app/login/login.component.html"),
        styles: [__webpack_require__("../../../../../src/app/login/login.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* Router */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */]) === "function" && _b || Object])
], LoginComponent);

var _a, _b;
//# sourceMappingURL=login.component.js.map

/***/ }),

/***/ "../../../../../src/app/page-not-found/page-not-found.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/page-not-found/page-not-found.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"page-wrapper vc\">\n\t<span class=\"text-center\">\n\t\t<img src=\"assets/img/magnifying-glass.png\" alt=\"\">\n\t\t<div class=\"clearfix\">&nbsp;</div>\n\t\t<h1 class=\"main-message\">\n\t\t  404: Page Not Found Error\n\t\t</h1>\n\t\t<div class=\"clearfix\">&nbsp;</div>\n\t\t<p class=\"text-muted\">If the problem persists, please <a href=\"mailto:metabolights-help@ebi.ac.uk\"> report here</a>.</p>\n\t</span>\n</div>"

/***/ }),

/***/ "../../../../../src/app/page-not-found/page-not-found.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_forms__ = __webpack_require__("../../../forms/@angular/forms.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return PageNotFoundComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var PageNotFoundComponent = (function () {
    function PageNotFoundComponent(formBuilder) {
        this.formBuilder = formBuilder;
    }
    PageNotFoundComponent.prototype.ngOnInit = function () {
        this.radioGroupForm = this.formBuilder.group({
            'model': 1
        });
    };
    return PageNotFoundComponent;
}());
PageNotFoundComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'app-page-not-found',
        template: __webpack_require__("../../../../../src/app/page-not-found/page-not-found.component.html"),
        styles: [__webpack_require__("../../../../../src/app/page-not-found/page-not-found.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_forms__["f" /* FormBuilder */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_forms__["f" /* FormBuilder */]) === "function" && _a || Object])
], PageNotFoundComponent);

var _a;
//# sourceMappingURL=page-not-found.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/shared.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_common__ = __webpack_require__("../../../common/@angular/common.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__top_nav_top_nav_component__ = __webpack_require__("../../../../../src/app/shared/top-nav/top-nav.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__side_nav_side_nav_component__ = __webpack_require__("../../../../../src/app/shared/side-nav/side-nav.component.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SharedModule; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};





var SharedModule = (function () {
    function SharedModule() {
    }
    return SharedModule;
}());
SharedModule = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["b" /* NgModule */])({
        imports: [
            __WEBPACK_IMPORTED_MODULE_2__angular_common__["d" /* CommonModule */],
            __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* RouterModule */]
        ],
        declarations: [__WEBPACK_IMPORTED_MODULE_3__top_nav_top_nav_component__["a" /* TopNavComponent */], __WEBPACK_IMPORTED_MODULE_4__side_nav_side_nav_component__["a" /* SideNavComponent */]],
        exports: [__WEBPACK_IMPORTED_MODULE_3__top_nav_top_nav_component__["a" /* TopNavComponent */], __WEBPACK_IMPORTED_MODULE_4__side_nav_side_nav_component__["a" /* SideNavComponent */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* RouterModule */]]
    })
], SharedModule);

//# sourceMappingURL=shared.module.js.map

/***/ }),

/***/ "../../../../../src/app/shared/side-nav/side-nav.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".sidebar {\n    border: medium none;\n    border-radius: 0;\n    bottom: 0;\n    left: 225px;\n    margin-left: -225px;\n    overflow-x: hidden;\n    overflow-y: auto;\n    padding-bottom: 40px;\n    position: fixed;\n    top: 55px;\n    transition: all 0.2s ease-in-out 0s;\n    width: 225px;\n    z-index: 3;\n    font-size: 14px;\n    background-color: #222;\n}\n.sidebar .list-group a.list-group-item {\n    background: #222 none repeat scroll 0 0;\n    border: 0 none;\n    border-radius: 0;\n    color: #999;\n    text-decoration: none;\n}\n.sidebar .list-group a.router-link-active, .sidebar .list-group a:hover {\n    background: #151515 none repeat scroll 0 0;\n    color: #fff;\n}\n.sidebar .sidebar-dropdown *:focus {\n    border: medium none;\n}\n.sidebar .sidebar-dropdown .panel-title {\n    font-size: 1rem;\n    height: 50px;\n    margin-bottom: 0;\n}\n.sidebar .sidebar-dropdown .panel-title a {\n    background: #222 none repeat scroll 0 0;\n    color: #999;\n    font-weight: 400;\n    text-decoration: none;\n}\n.sidebar .sidebar-dropdown .panel-title a span {\n    display: block;\n    padding: 1rem 1.5rem 0.75rem;\n    position: relative;\n}\n.sidebar .sidebar-dropdown .panel-title a:focus, .sidebar .sidebar-dropdown .panel-title a:hover {\n    color: #fff;\n    outline: 0 none;\n    outline-offset: -2px;\n}\n.sidebar .sidebar-dropdown .panel-title:hover {\n    background: #151515 none repeat scroll 0 0;\n}\n.sidebar .sidebar-dropdown .panel-collapse {\n    border: medium none;\n}\n.sidebar .sidebar-dropdown .panel-collapse .panel-body .list-group-item {\n    background-color: #222;\n    border: 0 solid transparent;\n    border-radius: 0;\n}\n.sidebar .sidebar-dropdown .panel-collapse .panel-body .list-group-item a {\n    color: #999;\n}\n.sidebar .sidebar-dropdown .panel-collapse .panel-body .list-group-item a:hover {\n    color: #fff;\n}\n.sidebar .sidebar-dropdown .panel-collapse .panel-body .list-group-item:hover {\n    background: #151515 none repeat scroll 0 0;\n}\n\n.sidebarPushRight{\n    left: 225px !important;\n    z-index:10;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/side-nav/side-nav.component.html":
/***/ (function(module, exports) {

module.exports = "<nav class=\"sidebar\">\n    <ul class=\"list-group\">\n        <a routerLink=\"/workspace/dashboard\" class=\"list-group-item\" routerLinkActive=\"router-link-active\">\n            <i class=\"fa fa-fw fa-dashboard\"></i>&nbsp;Dashboard\n        </a>\n        <a routerLink=\"/workspace/projects\" class=\"list-group-item\" routerLinkActive=\"router-link-active\">\n            <i class=\"fa fa-fw fa-folder\"></i>&nbsp;Projects\n        </a>\n    </ul>\n</nav>"

/***/ }),

/***/ "../../../../../src/app/shared/side-nav/side-nav.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SideNavComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var SideNavComponent = (function () {
    function SideNavComponent() {
    }
    SideNavComponent.prototype.ngOnInit = function () {
    };
    return SideNavComponent;
}());
SideNavComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'side-nav',
        template: __webpack_require__("../../../../../src/app/shared/side-nav/side-nav.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/side-nav/side-nav.component.css")]
    }),
    __metadata("design:paramtypes", [])
], SideNavComponent);

//# sourceMappingURL=side-nav.component.js.map

/***/ }),

/***/ "../../../../../src/app/shared/top-nav/top-nav.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".logo{\n\theight: 30px !important;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/shared/top-nav/top-nav.component.html":
/***/ (function(module, exports) {

module.exports = "<nav class=\"navbar fixed-top navbar-toggleable-md navbar-inverse bg-inverse\">\n    <button class=\"navbar-toggler navbar-toggler-right\" type=\"button\" data-toggle=\"collapse\" data-target=\"#navbarNavDropdown\" aria-controls=\"navbarNavDropdown\" aria-expanded=\"false\" aria-label=\"Toggle navigation\">\n        <span class=\"navbar-toggler-icon\"></span>\n    </button>\n    <a class=\"navbar-brand\" href=\"#\">\n        <img src=\"assets/img/logo.png\" class=\"logo\">&nbsp;MetaboLights Labs <sup class=\"text-muted\">Beta</sup>\n    </a>\n    <div id=\"navbarNavDropdown\" class=\"navbar-collapse collapse\">\n        <ul class=\"navbar-nav mr-auto\">\n            <!-- <li class=\"nav-item active\">\n                <a class=\"nav-link\" href=\"#\">Home <span class=\"sr-only\">(current)</span></a>\n            </li>\n            <li class=\"nav-item\">\n                <a class=\"nav-link\" href=\"#\">Features</a>\n            </li>\n            <li class=\"nav-item\">\n                <a class=\"nav-link\" href=\"#\">Pricing</a>\n            </li> -->\n        </ul>\n        <ul class=\"navbar-nav\">\n            <li class=\"nav-item dropdown\">\n                  <a class=\"nav-link dropdown-toggle\" id=\"supportedContentDropdown\" data-toggle=\"dropdown\" aria-haspopup=\"true\" aria-expanded=\"false\">\n                  <i class=\"fa fa-fw fa-user\"></i></a>\n                  <div class=\"dropdown-menu dropdown-menu-right\" aria-labelledby=\"supportedContentDropdown\">\n                    <a routerLink=\"/workspace/settings\" class=\"dropdown-item\"><i class=\"fa fa-fw fa-cogs\"></i>&nbsp;Settings</a>\n                    <a class=\"dropdown-item\" (click)=\"logOut()\" ><i class=\"fa fa-fw fa-sign-out\"></i>&nbsp;Log out</a>\n                </div>\n            </li>\n        </ul>\n    </div>\n</nav>"

/***/ }),

/***/ "../../../../../src/app/shared/top-nav/top-nav.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__auth_service__ = __webpack_require__("../../../../../src/app/auth.service.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return TopNavComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var TopNavComponent = (function () {
    function TopNavComponent(authService) {
        this.authService = authService;
    }
    TopNavComponent.prototype.ngOnInit = function () {
        this.dashBoard = this.authService.dashBoard;
    };
    TopNavComponent.prototype.logOut = function () {
        this.authService.logout();
    };
    return TopNavComponent;
}());
TopNavComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'top-nav',
        template: __webpack_require__("../../../../../src/app/shared/top-nav/top-nav.component.html"),
        styles: [__webpack_require__("../../../../../src/app/shared/top-nav/top-nav.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */]) === "function" && _a || Object])
], TopNavComponent);

var _a;
//# sourceMappingURL=top-nav.component.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/dashboard/dashboard.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".cp{\t\n\tbackground-color: #2B99FF;\n\tcolor: #fff;\n\tpadding: 20px;\n\n}\n\n.display-number{\n\tfont-size: 3.5em;\n\tfont-weight: bolder;\n}\n\n.tile_count .tile_stats_count {\n    margin-bottom: 10px;\n    border-bottom: 0;\n    padding: 15px;\n    border-radius: 4px;\n    padding-bottom: 10px;\n    border: 1px solid #E1E2E4;\n    box-shadow: 0 1px 2px 0 rgba(0,0,0,0.1);\n    -moz-box-shadow: 0 1px 2px 0 rgba(0,0,0,0.1);\n    -webkit-box-shadow: 0 1px 2px 0 rgba(0,0,0,0.1);\n}\n\n.tile_count .tile_stats_count .count {\n    font-size: 60px;\n}\n\n.tile_count .tile_stats_count .count {\n    line-height: 57px;\n    font-weight: 600;\n}\n\n.tile_count .tile_stats_count span {\n    font-size: 13px;\n}\n\n.tile_count .tile_stats_count .count_bottom i {\n    width: 12px;\n}\n\n.alert{\n\tborder-radius: 0px;\n}\n\n.npb{\n    padding-bottom: 0;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/work-space/dashboard/dashboard.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"content row\">\n\t<div class=\"col-md-9 mini-wrapper\">\n\t\t<div class=\"section\">\n\t\t\t<h3 class=\"title\"><i class=\"fa fa-dashboard\"></i>&nbsp;Dashboard</h3>\n\t\t</div>\n\t\t<div class=\"section ntp\" >\n\t\t\t<div class=\"row\">\n\t\t\t\t<div class=\"col-md-3\">\n\t\t\t\t\t<div routerLink=\"/workspace/projects\" class=\"tile_count\">\n\t\t\t\t\t\t<div class=\"tile_stats_count\">\n\t\t\t           \t\t<div class=\"blue pointer\">\n\t\t\t\t\t\t\t\t<div class=\"count text-primary\">{{ dashBoard.projects.length }}</div>\n\t\t\t\t\t\t\t\t<span class=\"count_top text-muted\"><i class=\"fa fa-folder\"></i> Total Projects</span>\n\t\t\t\t\t\t\t\t<!-- <span class=\"count_bottom\"><i class=\"green\">4% </i> From last Week</span> -->\n\t\t\t            \t</div>\n\t\t            \t</div>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n\t<div class=\"col-xs-12 col-md-3 grey np\">\n\t\t<div class=\"section npb\">\n\t\t\t<p class=\"text-muted\">\n\t\t\t\t<small class=\"text-primary\">\n\t\t\t\t\tWelcome <b>{{ dashBoard.user.firstName }}</b>!\n\t\t\t\t</small>\n\t\t\t</p>\n\t\t</div>\n\t\t<div class=\"section npb\">\n\t\t\t<p class=\"text-muted\">\n\t\t\t\t<small class=\"text-muted\">\n\t\t\t\t\tRecent activity:\n\t\t\t\t\t<br>\n\t\t\t\t\t<b>{{ dashBoard.updatedAt | date:'yyyy-MM-dd HH:mm:ss Z' }}</b>\n\t\t\t\t</small>\n\t\t\t</p>\n\t\t\t<p class=\"text-muted\">\n\t\t\t\t<small class=\"text-muted\">\n\t\t\t\t\t&nbsp;\n\t\t\t\t</small>\n\t\t\t</p>\n\t\t\t<p class=\"text-muted\">\n\t\t\t\t<small class=\"text-muted\">\n\t\t\t\t\t&nbsp;\n\t\t\t\t</small>\n\t\t\t</p>\n\t\t</div>\n\t\t<div class=\"section npb\">\n\t\t\t<p class=\"text-muted\">\n\t\t\t\t<small>\n\t\t\t\t\t<a routerLink=\"/workspace/settings\" class=\"pointer text-muted\"><i class=\"fa fa-cogs\"></i>&nbsp;Settings</a>\n\t\t\t\t</small>\n\t\t\t</p>\n\t\t</div>\n\t\t<div class=\"section npb\">\n\t\t\t<p class=\"text-danger\">\n\t\t\t\t<small>\n\t\t\t\t\t<a href=\"https://github.com/EBI-Metabolights/Metabolights-Labs/issues\" target=\"_blank\" class=\"pointer text-muted\"><i class=\"fa fa-bug\"></i>&nbsp;Report bug</a>\n\t\t\t\t</small>\n\t\t\t</p>\n\t\t</div>\n\t</div>\n</div>"

/***/ }),

/***/ "../../../../../src/app/work-space/dashboard/dashboard.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__auth_service__ = __webpack_require__("../../../../../src/app/auth.service.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DashboardComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var DashboardComponent = (function () {
    function DashboardComponent(authService, route) {
        this.authService = authService;
        this.route = route;
        this.dashBoard = null;
        this.dashBoard = this.route.snapshot.data['dashBoard'];
    }
    DashboardComponent.prototype.ngOnInit = function () {
    };
    return DashboardComponent;
}());
DashboardComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'app-dashboard',
        template: __webpack_require__("../../../../../src/app/work-space/dashboard/dashboard.component.html"),
        styles: [__webpack_require__("../../../../../src/app/work-space/dashboard/dashboard.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__auth_service__["a" /* AuthService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* ActivatedRoute */]) === "function" && _b || Object])
], DashboardComponent);

var _a, _b;
//# sourceMappingURL=dashboard.component.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/dashboard/dashboard.resolve.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__auth_service__ = __webpack_require__("../../../../../src/app/auth.service.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return DashboardResolve; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var DashboardResolve = (function () {
    function DashboardResolve(authService) {
        this.authService = authService;
    }
    DashboardResolve.prototype.resolve = function (route) {
        return this.authService.initializeWorkSpace();
    };
    return DashboardResolve;
}());
DashboardResolve = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["c" /* Injectable */])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */]) === "function" && _a || Object])
], DashboardResolve);

var _a;
//# sourceMappingURL=dashboard.resolve.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/dashboard/dashboard.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__user__ = __webpack_require__("../../../../../src/app/work-space/dashboard/user.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__project_project__ = __webpack_require__("../../../../../src/app/work-space/project/project.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Dashboard; });


var Dashboard = (function () {
    function Dashboard() {
        this.projects = [];
        this.createdAt = "";
        this.updatedAt = "";
        this.settings = { "galaxy": [] };
    }
    Dashboard.prototype.deserialize = function (input) {
        this.user = new __WEBPACK_IMPORTED_MODULE_0__user__["a" /* User */]().deserialize(input.owner);
        this.createdAt = input.createdAt;
        this.updatedAt = input.updatedAt;
        this.settings = JSON.parse(input.settings);
        for (var id in input.projects) {
            var indProject = input.projects[id];
            var project = new __WEBPACK_IMPORTED_MODULE_1__project_project__["a" /* Project */]().deserialize(indProject);
            this.projects.push(project);
        }
        return this;
    };
    return Dashboard;
}());

//# sourceMappingURL=dashboard.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/dashboard/user.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return User; });
var User = (function () {
    function User() {
    }
    User.prototype.deserialize = function (input) {
        this.email = input.email;
        this.userName = input.userName;
        this.joinDate = input.joinDate;
        this.firstName = input.firstName;
        this.lastName = input.lastName;
        this.address = input.address;
        this.affiliation = input.affiliation;
        this.affiliationUrl = input.affiliationUrl;
        this.role = input.role;
        this.orcid = input.orcid;
        return this;
    };
    return User;
}());

//# sourceMappingURL=user.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/project/directory.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Directory; });
var Directory = (function () {
    function Directory() {
        this.title = "";
        this.files = [];
        this.directories = [];
        this.timeStamp = "";
        this.level = 0;
        this.path = "";
    }
    Directory.prototype.deserialize = function (input) {
        this.title = input.title;
        this.files = input.files;
        this.directories = input.directories;
        this.timeStamp = input.timeStamp;
        this.level = input.level;
        this.path = input.path;
        return this;
    };
    return Directory;
}());

//# sourceMappingURL=directory.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/project/file.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return File; });
var File = (function () {
    function File() {
        this.title = "";
        this.timeStamp = "";
        this.index = 0;
    }
    File.prototype.deserialize = function (input) {
        this.title = input.title;
        this.timeStamp = input.timeStamp;
        this.index = input.fileIndex;
        return this;
    };
    return File;
}());

//# sourceMappingURL=file.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/project/job.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Job; });
var Job = (function () {
    function Job() {
        this.hide = false;
        this.mllprojectId = "";
        this.jobId = "";
        this.status = "";
        this.info = {};
        this.updatedAt = "";
        this.createdAt = "";
        this.log = {};
    }
    Job.prototype.deserialize = function (input) {
        this.mllprojectId = input.mllprojectId;
        this.jobId = input.jobId;
        this.status = input.status;
        this.info = input.info;
        this.updatedAt = input.updatedAt;
        this.createdAt = input.createdAt;
        this.hide = input.hide;
        return this;
    };
    return Job;
}());

//# sourceMappingURL=job.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/project/project.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".div-wrapper{\n\tdisplay: -webkit-box;\n\tdisplay: -ms-flexbox;\n\tdisplay: flex;\n\t-webkit-box-align: center;\n\t    -ms-flex-align: center;\n\t        align-items: center;\n  \t-webkit-box-pack: center;\n  \t    -ms-flex-pack: center;\n  \t        justify-content: center;\n\tmin-height: 50vh;\n\tpadding: 40px 40px;\n\t-webkit-box-orient: vertical;\n\t-webkit-box-direction: normal;\n\t    -ms-flex-direction: column;\n\t        flex-direction: column;\n\ttext-align: center;\n}\n\n.vh80{\n\tmin-height: 80vh !important;\n}\n\n.div-wrapper .spinner > h5{\n\tfont-weight: normal;\n\tletter-spacing: 1px;\n}\n\n.div-wrapper .spinner > small{\n\tfont-weight: lighter;\n}\n\n.alert-custom{\n\tmargin-top: 0 !important;\n\tmargin-bottom: 10px !important;\n}\n\n.spinner{\n\tposition: relative !important;\n    top: 0%;\n    left: 0%;\n    margin-top: 0px; \n    margin-left: 0px;\n    text-align: center;\n}\n\n.hide{\n\tdisplay: none;\n}\n\nul {\n  list-style-type: none;\n}\n\nul li{\n  padding: 5px 0;\n}\n\n.npt{\n\tpadding-top: 0;\n}\n\n.npl{\n\tpadding-left: 5px;\n}\n\n.logFileData{\n\toverflow-x: scroll;\n}\n\n.jcc{\n    -webkit-box-pack: center !important;\n        -ms-flex-pack: center !important;\n            justify-content: center !important;\n}\n\n.section-title{\n\tfloat: right;\n\tfont-size: 0.6em;\n\tcolor: #d8d8d8;\n\tmargin-right: 5px;\n\tborder: 1px solid #d8d8d8;\n\tborder-radius: 2px;\n\tpadding: 2px 4px;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/work-space/project/project.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"content row\" *ngIf=\"project!==undefined; else elseBlock\">\n\t<div class=\"col-md-9 mini-wrapper\">\n\t\t<div class=\"section\">\n\t\t\t<h3 class=\"title\">\n\t\t\t\t<b><img src=\"assets/img/project.png\" height=\"16\" class=\"logo\"> &nbsp;{{ project.title }}</b>\n\t\t\t\t<span class=\"pull-right\" *ngIf=\"project.study != null\">\n\t\t\t\t\t<a class=\"btn btn-sm btn-primary\" target=\"_blank\" [attr.href]=\"server + project.study\"> <i class=\"fa fa-external-link\"></i>&nbsp;{{ project.study }}</a>\n\t\t\t\t</span>\n\t\t\t</h3>\n\t\t</div>\n\t\t<div class=\"section npt\">\n\t\t\t<div *ngIf=\"files.length > 0 && !processing else noFilesBlock\">\n\t\t\t\t<div *ngIf=\"uploadInprogress\"  class=\"alert alert-danger alert-custom\">\n\t\t\t\t\t<strong>File(s) upload in progress!</strong>\n\t\t\t\t</div>\n\t\t\t\t<tree-view [directory]=\"projectStructure\"></tree-view>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n\t<div class=\"col-xs-12 col-md-3 grey np\">\n\t\t<div class=\"section\">\n\t\t  \t<small class=\"text-muted section-title\"><i>Import data</i></small>\n\t\t  \t<div class=\"btn-group-sm\" role=\"group\" aria-label=\"...\">\n\t\t\t\t<button class=\"btn btn-primary pointer\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Import files - Aspera web browser client. Note: Files uploaded through Aspera might take some time to appear in the project folder.\" (click)=\"showAsperaWaitingMessage(asperaMessage)\">\n\t\t  \t\t\t<i class=\"fa fa-download\"></i> Import Data\n\t\t  \t\t</button>\n\t\t\t\t<button class=\"btn btn-info pointer\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Import files - Aspera command line client\" (click)=\"open(content)\">\n\t\t  \t\t\t<i class=\"fa fa-terminal\"></i>\n\t\t  \t\t</button>\n\t\t  \t\t<a class=\"btn btn-default pointer\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Need help with setting up Aspera\" href=\"ftp://ftp.ebi.ac.uk/pub/databases/metabolights/documentation/MetaboLights%20Tutorial%20-%20FAQ.pdf\" target=\"_blank\">\n\t\t\t\t\t<i class=\"fa fa-question\"></i>\n\t\t\t\t</a>\n\t\t  \t</div>\n\t\t</div>\n\t\t<div class=\"section\" *ngIf=\"galaxyInstances && galaxyInstances.length > 0 && files.length > 0\">\n\t\t  \t<span class=\"tiny text-muted\"><label>Galaxy Workflows</label></span><br>\n\t\t  \t<span class=\"form-group\">\n\t\t  \t\t\n\t\t  \t\t<button (click)=\"openExportDataToGalaxyModal(exportToGalaxy)\" class=\"btn btn-success btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Export files to Galaxy instance\">\n\t\t  \t\t\t<i class=\"fa fa-upload\" aria-hidden=\"true\"></i>\n\t\t  \t\t</button>\n\t\t  \t</span>\n\t\t </div>\n\t\t <div class=\"section\" *ngIf=\"files.length > 0\">\n\t\t\t<small class=\"text-muted section-title\"><i>Tools</i></small>\n\t\t  \t<span class=\"form-group\">\n\t\t  \t\t<span class=\"tiny text-muted\"><label> File format conversions</label></span><br>\n\t\t  \t\t<button *ngIf=\"mzMLFiles?.length > 0\" (click)=\"openMzml2IsaModal(mzml2isa)\" class=\"btn btn-success btn-sm\" [ngClass]=\"mzMLFiles.length <= 0 ? 'disabled' : ''\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Parser to extract meta information from mzML mass spectrometry files and parse relevant information to a ISA-Tab structure\">\n\t\t  \t\t\t.mzML <i class=\"fa fa-arrow-circle-o-right\" aria-hidden=\"true\"></i> ISA\n\t\t  \t\t</button>\n\t\t  \t\t<button *ngIf=\"nmrMLFiles?.length > 0\" (click)=\"openNmrml2IsaModal(nmrml2isa)\" class=\"btn btn-success btn-sm\" [ngClass]=\"nmrMLFiles.length <= 0 ? 'hidden' : ''\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Parser to extract meta information from nmrML  spectrometry files and parse relevant information to a ISA-Tab structure\">\n\t\t  \t\t\t.nmrML <i class=\"fa fa-arrow-circle-o-right\" aria-hidden=\"true\"></i> ISA\n\t\t  \t\t</button>\n\t\t  \t</span>\n\t\t \t<br>\n\t\t\t<span class=\"tiny text-muted\"><label> Export</label></span><br>\n\t\t  \t<span class=\"form-group\">\n\t\t  \t\t<button *ngIf=\"project.study == null\" (click)=\"openSubmitAsStudyModal(submitAsStudy)\"  [ngClass]=\"isaTabDirectories.length > 0 ? '' : 'disabled'\" class=\"btn btn-primary btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Submit project as a study to MetaboLights Database\">\n\t\t  \t\t\t<i class=\"fa fa-university\" aria-hidden=\"true\"></i>  Export to MetaboLights\n\t\t  \t\t</button>\n\t\t\t\t{{ study }}\n\t\t  \t\t<button *ngIf=\"project.study != null\" (click)=\"openSubmitAsStudyModal(submitAsStudy)\"  [ngClass]=\"isaTabDirectories.length > 0 ? '' : 'disabled'\" class=\"btn btn-primary btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Submit project as a study to MetaboLights Database\">\n\t\t  \t\t\t<i class=\"fa fa-university\" aria-hidden=\"true\"></i>  Update {{ project.study }}\n\t\t  \t\t</button>\n\t\t  \t</span>\n\t\t</div>\n\t\t<div class=\"section\">\n\t\t\t<small class=\"text-muted section-title\"><i>Configuration</i></small>\n\t\t  \t<span class=\"form-group\">\n\t\t  \t\t<button class=\"btn btn-warning btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Edit project details\" (click)=\"openEditModal(editProjectDetails)\"><i class=\"fa fa-cogs\"></i></button>\n\t\t\t\t<!-- <button class=\"btn btn-default btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"View Project Log\" ><i class=\"fa fa-road\"></i></button>  -->\n\t\t\t\t<button *ngIf=\"project.jobs.length > 0\" [ngClass]=\"project.jobs.length <= 0 ? 'disabled' : ''\" (click)=\"openJobsModal(jobs)\" class=\"btn btn-primary btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"View Jobs\" >\n\t\t\t\t\t<i class=\"fa fa-tasks\" aria-hidden=\"true\"></i>&nbsp;Logs\n\t\t\t\t</button>\n\t\t\t\t<button *ngIf=\"files?.length > 0\" class=\"btn btn-danger btn-sm\" (click)=\"openDeleteConfirmationModal(confirmDeleteFiles)\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Delete file(s)\" ><i class=\"fa fa-trash\"></i></button> \n\t\t  \t</span>\n\t\t  \t<p class=\"alert-wrapper\" *ngFor=\"let alert of alerts\">\n\t\t\t\t<small><ngb-alert [type]=\"alert.type\" (close)=\"closeAlert(alert)\">\n\t\t\t\t\t<span [innerHTML]=\"alert.message\">\n\t\t\t\t\t</span>\n\t\t\t\t</ngb-alert></small>\n\t\t\t</p>\n\t\t</div>\n\t\t<div class=\"section\">\n\t\t  \t<div class=\"ml-card\">\n\t\t  \t\t<div class=\"ml-card-header\">\n\t\t  \t\t\tProject Details\n\t\t  \t\t</div>\n\t\t  \t\t<div class=\"ml-card-body\">\n\t\t  \t\t\t<small><i>TITLE: </i></small>\n\t\t  \t\t\t<p>{{ project.title }}</p>\n\t\t  \t\t\t<small><i>ID: </i></small>\n\t\t  \t\t\t<p><small>{{ project.id }}</small></p>\n\t\t  \t\t\t<small><i>Description: </i></small>\n\t\t  \t\t\t<p>{{ project.description }}</p>\n\t\t\t    \t<small><i>Created at: </i></small>\n\t\t  \t\t\t<p>{{ project.createdAt | date:'yyyy-MM-dd HH:mm:ss Z' }}</p>\n\t\t  \t\t\t<small><i>Updated at: </i></small>\n\t\t  \t\t\t<p>{{ project.updatedAt | date:'yyyy-MM-dd HH:mm:ss Z' }}</p>\n\t\t  \t\t\t<small><i>Status: </i></small>\n\t\t  \t\t\t<p *ngIf=\"project.isBusy\">\n\t\t  \t\t\t\t<i class=\"fa fa-lock\"></i>\n\t\t  \t\t\t</p>\n\t\t  \t\t\t<p *ngIf=\"!project.isBusy\">\n\t\t  \t\t\t\t<i class=\"fa fa-unlock\"></i>\n\t\t  \t\t\t</p>\n\t\t  \t\t</div>\n\t\t  \t</div>\n\t\t</div>\n\t\t<div class=\"section npb nbb\">\n\t\t\t<p class=\"text-danger\">\n\t\t\t\t<small>\n\t\t\t\t\t<a href=\"https://github.com/EBI-Metabolights/Metabolights-Labs/issues\" target=\"_blank\" class=\"pointer text-muted\"><i class=\"fa fa-bug\"></i>&nbsp;Report bug</a>\n\t\t\t\t</small>\n\t\t\t</p>\n\t\t</div>\n\t</div>\n</div>\n<ng-template #exportToGalaxy let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<div>\n\t\t<div class=\"modal-header\">\n\t\t\t<h5 class=\"modal-title\"><i class=\"fa fa-cogs\"></i>&emsp;Export files to galaxy </h5>\n\t\t\t<a class=\"close pull-right\" aria-label=\"Close\" (click)=\"d('Cross click')\">\n\t\t\t\t<span aria-hidden=\"true\">&times;</span>\n\t\t\t</a>\n\t\t</div>\n\t\t<div class=\"modal-body\">\n\t\t\t<div class=\"form-group\">\n\t\t\t\t<small><label>Select Galaxy Instance</label></small>\n\t\t\t\t<select class=\"form-control\" #selectedGalaxy (change)=\"onChangeGalaxyInstance($event, selectedGalaxy.value)\">\n\t\t\t\t\t<option value=\"\">Select galaxy instance</option>\n\t\t\t\t    <option *ngFor=\"let instance of galaxyInstances\" [value]=\"instance.url\">\n\t\t\t\t        {{ instance.name}}\n\t\t\t\t    </option>\n\t\t\t\t</select>\n\t\t\t</div>\n\t\t\t<div *ngIf=\"selectedGalaxy.value != '' && histories.length > 0\" class=\"form-group\">\n\t\t\t\t<small><label>Select History</label></small>\n\t\t\t\t<select class=\"form-control\" #selectedHistory (change)=\"onChangeGalaxyHistory($event, selectedHistory.value)\">\n\t\t\t\t\t<option value=\"\">Select history</option>\n\t\t\t\t    <option *ngFor=\"let history of histories\" [value]=\"history.id\">\n\t\t\t\t        {{history.name}}&nbsp;-&nbsp;({{history.id}})\n\t\t\t\t    </option>\n\t\t\t\t</select>\n\t\t\t</div>\n\t\t\t<div *ngIf=\"error != null\" class=\"alert alert-danger\">\n\t\t\t\t<strong>{{ error }}</strong> \n\t\t\t</div>\n\t\t</div>\n\t\t<div class=\"modal-footer\">\n\t\t\t<div *ngIf=\"processing\">\n\t\t\t\t<button class=\"btn btn-primary\" [disabled]=\"true\"><i class=\"fa fa-spinner fa-pulse fa-fw\"></i> Export</button>\n\t\t\t</div>\n\t\t\t<div *ngIf=\"!processing\">\n\t\t\t\t<button type=\"submit\" class=\"btn btn-primary\" [disabled]=\"selectedHistory == '' || selectedGalaxy == ''\"  (click)=\"exportFilesToGalaxy()\">Export</button>\n\t\t\t</div>\n\t\t\t\t\n\n\t\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Close</button>\n\t\t</div>\n\t</div>\n</ng-template>\n<ng-template #noFilesBlock>\n\t<div *ngIf=\"!processing\" class=\"content row\">\n\t\t<div class=\"col-md-12 mini-wrapper\">\n\t\t\t<div class=\"cloning_project text-center\">\n\t\t\t\t<img src=\"assets/img/safebox.png\" height=\"128\" class=\"logo\"><br><br>\n\t\t\t\t<div class=\"spinner\">\n\t\t\t\t\t<h6 class=\"text-center lighter\">\n\t\t\t\t\t\tNo files yet! Upload files using aspera? \n\t\t\t\t\t</h6>\n\t\t\t\t\t<small class=\"center\"><i><button class=\"btn btn-sm btn-primary\" (click)=\"showAsperaWaitingMessage(asperaMessage)\"><i class=\"fa fa-download\"></i> Import Data Now</button></i></small>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n\t<div *ngIf=\"processing\" class=\"content row\">\n\t\t<div class=\"div-wrapper col-md-12\">\n\t\t\t<img src=\"assets/img/waiting.png\" height=\"128\" class=\"logo\"><br><br>\n\t\t\t<div class=\"spinner\">\n\t\t\t\t<div class=\"bounce1\"></div>\n\t\t\t\t<div class=\"bounce2\"></div>\n\t\t\t\t<div class=\"bounce3\"></div>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</ng-template>\n<ng-template #elseBlock>\n\t<div class=\"content row\">\n\t\t<div class=\"col-md-12 mini-wrapper\">\n\t\t\t<div class=\"div-wrapper vh80\">\n\t\t\t\t<img src=\"assets/img/broken-link.png\" height=\"128\" class=\"logo\"><br>\n\t\t\t\t<div class=\"spinner\">\n\t\t\t\t\t<h6 class=\"text-center\">\n\t\t\t\t\t\tProject not found\n\t\t\t\t\t</h6><br>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</ng-template>\n<ng-template #mzml2isa let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<div class=\"modal-body\">\n\t\t<div *ngIf=\"isaTabDirectories.length == 0; else warningMZML2ISABlocl\" class=\"div-wrapper\">\n\t\t\t<img src=\"assets/img/file-conversion.png\" height=\"128\" class=\"logo\"><br>\n\t\t\t<h2>mzml2isa</h2>\n\t\t\t<small>\n\t\t\t\t<b>Parser to extract meta information from mzML mass spectrometry files and parse relevant information to a ISA-Tab structure</b>\n\t\t\t</small>\n\t\t\t<small class=\"center tiny text-muted\">Note: Conversion might take a while depending upon the size of the .mzML files.</small>\n\t\t\t<br>\n\t\t\t<div>\n\t\t\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Cancel</button>&nbsp;\n\t\t\t\t<span *ngIf=\"processing\">\n\t\t\t\t\t<button [disabled]=\"true\" (click)=\"convertMzml2isa()\" type=\"submit\" class=\"btn btn-success\"><i class=\"fa fa-spinner fa-pulse fa-fw\"></i> Convert .mzML to ISA</button>\n\t\t\t\t</span>\n\t\t\t\t<span *ngIf=\"!processing\">\n\t\t\t\t\t<button (click)=\"convertMzml2isa()\" type=\"submit\" class=\"btn btn-success\">Convert .mzML to ISA</button>\n\t\t\t\t</span>\n\t\t\t</div>\n\t\t</div>\n\t\t<ng-template #warningMZML2ISABlocl>\n\t\t\t<div class=\"div-wrapper\">\n\t\t\t\t<img src=\"assets/img/file-conversion.png\" height=\"128\" class=\"logo\"><br>\n\t\t\t\t<h2>mzml2isa</h2>\n\t\t\t\t<small class=\"text-danger\">\n\t\t\t\t\t<b>ISA-Tab files already exists</b>\n\t\t\t\t</small>\n\t\t\t\t<small class=\"center tiny text-danger\">Note: if you proceed existing ISA tab files will be replaced with new ones; any changes will be lost.</small>\n\t\t\t\t<br>\n\t\t\t\t<div>\n\t\t\t\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Cancel</button>&nbsp;\n\t\t\t\t\t<span *ngIf=\"processing\">\n\t\t\t\t\t\t<button [disabled]=\"true\" (click)=\"convertMzml2isa()\" type=\"submit\" class=\"btn btn-success\"><i class=\"fa fa-spinner fa-pulse fa-fw\"></i> Convert .mzML to ISA</button>\n\t\t\t\t\t</span>\n\t\t\t\t\t<span *ngIf=\"!processing\">\n\t\t\t\t\t\t<button (click)=\"convertMzml2isa()\" type=\"submit\" class=\"btn btn-success\">Convert .mzML to ISA</button>\n\t\t\t\t\t</span>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</ng-template>\n\t</div>\n</ng-template>\n<ng-template #nmrml2isa let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<div class=\"modal-body\">\n\t\t<div *ngIf=\"isaTabDirectories.length == 0; else warningNMRML2ISABlocl\" class=\"div-wrapper\">\n\t\t\t<img src=\"assets/img/file-conversion.png\" height=\"128\" class=\"logo\"><br>\n\t\t\t<h2>nmrml2isa</h2>\n\t\t\t<small>\n\t\t\t\t<b>Parser to extract meta information from nmrML files and parse relevant information to a ISA-Tab structure</b>\n\t\t\t</small>\n\t\t\t<small class=\"center tiny text-muted\">Note: Conversion might take a while depending upon the size of the .nmrML files.</small>\n\t\t\t<br>\n\t\t\t<div>\n\t\t\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Cancel</button>&nbsp;\n\t\t\t\t<span *ngIf=\"processing\">\n\t\t\t\t\t<button [disabled]=\"true\" (click)=\"convertNmrml2isa()\" type=\"submit\" class=\"btn btn-success\"><i class=\"fa fa-spinner fa-pulse fa-fw\"></i> Convert .nrmML to ISA</button>\n\t\t\t\t</span>\n\t\t\t\t<span *ngIf=\"!processing\">\n\t\t\t\t\t<button (click)=\"convertNmrml2isa()\" type=\"submit\" class=\"btn btn-success\">Convert .nrmML to ISA</button>\n\t\t\t\t</span>\n\t\t\t</div>\n\t\t</div>\n\t\t<ng-template #warningNMRML2ISABlocl>\n\t\t\t<div class=\"div-wrapper\">\n\t\t\t\t<img src=\"assets/img/file-conversion.png\" height=\"128\" class=\"logo\"><br>\n\t\t\t\t<h2>nmrml2isa</h2>\n\t\t\t\t<small class=\"text-danger\">\n\t\t\t\t\t<b>ISA-Tab files already exists</b>\n\t\t\t\t</small>\n\t\t\t\t<small class=\"center tiny text-danger\">Note: if you proceed existing ISA tab files will be replaced with new ones; any changes will be lost.</small>\n\t\t\t\t<br>\n\t\t\t\t<div>\n\t\t\t\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Cancel</button>&nbsp;\n\t\t\t\t\t<span *ngIf=\"processing\">\n\t\t\t\t\t\t<button [disabled]=\"true\" (click)=\"convertNmrml2isa()\" type=\"submit\" class=\"btn btn-success\"><i class=\"fa fa-spinner fa-pulse fa-fw\"></i> Convert .nrmML to ISA</button>\n\t\t\t\t\t</span>\n\t\t\t\t\t<span *ngIf=\"!processing\">\n\t\t\t\t\t\t<button (click)=\"convertNmrml2isa()\" type=\"submit\" class=\"btn btn-success\">Convert .nrmML to ISA</button>\n\t\t\t\t\t</span>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</ng-template>\n\t</div>\n</ng-template>\n<ng-template #submitAsStudy let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<div class=\"modal-body\">\n\t\t<div *ngIf=\"project.study != null\" class=\"div-wrapper\">\n\t\t\t<img src=\"assets/img/submit-as-study.png\" height=\"128\" class=\"logo\"><br>\n\t\t\t<h2>Update MetaboLights Study <br> {{ project.study }} </h2>\n\t\t\t<small>\n\t\t\t\t<b>Upon submission, you study will be updated, and you can use the MetaboLights online study editor for any further study updates. Thanks.</b>\n\t\t\t</small>\n\t\t\t<br>\n\t\t\t<div>\n\t\t\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Cancel</button>&nbsp;\n\t\t\t\t<span *ngIf=\"processing\">\n\t\t\t\t\t<button [disabled]=\"true\" (click)=\"submitProjectAsStudy()\" type=\"submit\" class=\"btn btn-success\"><i class=\"fa fa-spinner fa-pulse fa-fw\"></i> Agree and update study</button>\n\t\t\t\t</span>\n\t\t\t\t<span *ngIf=\"!processing\">\n\t\t\t\t\t<button (click)=\"submitProjectAsStudy()\" type=\"submit\" class=\"btn btn-success\">Agree and update study</button>\n\t\t\t\t</span>\n\t\t\t</div>\n\t\t</div>\n\t\t<div *ngIf=\"project.study == null\" class=\"div-wrapper\">\n\t\t\t<img src=\"assets/img/submit-as-study.png\" height=\"128\" class=\"logo\"><br>\n\t\t\t<h2>Submit as MetaboLights Study</h2>\n\t\t\t<small>\n\t\t\t\t<b>Upon submission, a MetaboLights Study Identifier will be assigned to the project, and you can use the MetaboLights online study editor for any further study updates. Thanks.</b>\n\t\t\t</small>\n\t\t\t<br>\n\t\t\t<div>\n\t\t\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Close</button>&nbsp;\n\t\t\t\t<span *ngIf=\"processing\">\n\t\t\t\t\t<button [disabled]=\"true\" (click)=\"submitProjectAsStudy()\" type=\"submit\" class=\"btn btn-success\"><i class=\"fa fa-spinner fa-pulse fa-fw\"></i> Agree and proceed</button>\n\t\t\t\t</span>\n\t\t\t\t<span *ngIf=\"!processing\">\n\t\t\t\t\t<button (click)=\"submitProjectAsStudy()\" type=\"submit\" class=\"btn btn-success\">Agree and proceed</button>\n\t\t\t\t</span>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</ng-template>\n<ng-template #jobs let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<div class=\"modal-header\">\n\t    <h5 class=\"modal-title\">Jobs</h5>\n\t</div>\n\t<div class=\"modal-body\">\n\t\t<div *ngFor=\"let job of project.jobs\" class=\"card\" [ngClass]=\"{'card-outline-warning': job.status == 'PEND'}\">\n\t\t  <div class=\"card-block\">\n\t\t    <h4 class=\"card-title\">Job ID: {{ job.jobId }}</h4>\n\t\t    <h6 class=\"card-subtitle mb-2 text-muted\"><i>{{ job.info.message }}</i></h6>\n\t\t    <span class=\"badge badge-default\">Status: <span [innerHTML]=\"job.status\"></span></span>\n\t\t\t<a class=\"pointer\" (click)=\"displayJobLogs(job)\"><span class=\"badge badge-primary\">\n\t\t    \t<i class=\"fa fa-eye\"></i>&nbsp;View Job Output\n\t\t    </span></a>\n\t\t    <a class=\"pointer float-right\" *ngIf=\"job.status != 'DONE'\" (click)=\"getJobStatus(job)\"><span class=\"badge badge-primary\">\n\t\t    \t<i class=\"fa fa-refresh\"></i>&nbsp;Update Status\n\t\t    </span></a>\n\t\t\t<div class=\"collapse logFileData\" [id]=\"'job_'+ job.jobId\">\n\t\t\t\t<hr>\n\t\t\t\t<small><b>Output</b></small>\n\t\t\t\t<div [innerHTML]=\"job.log.out\"></div>\n\t\t\t\t<hr>\n\t\t\t\t<small><b>Error log</b></small>\n\t\t\t\t<div [innerHTML]=\"job.log.err\"></div>\n\t\t\t</div>\n\t\t  </div>\n\t\t</div>\n\t</div>\n</ng-template>\n<ng-template #content let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<form>\n\t\t<div class=\"modal-header\">\n\t\t\t<h5 class=\"modal-title\"><i class=\"fa fa-terminal\"></i>&emsp;Aspera Command Line File Upload </h5>\n\t\t\t<a class=\"close pull-right\" aria-label=\"Close\" (click)=\"d('Cross click')\">\n\t\t\t\t<span aria-hidden=\"true\">&times;</span>\n\t\t\t</a>\n\t\t</div>\n\t\t<div class=\"modal-body\">\n\t\t\t<div class=\"row\">\n\t\t\t\t<div class=\"col-md-12\">\n\t\t\t\t\t<small>\n\t\t\t\t\t\t<span class=\"tiny\">Please note if you have already setup aspera for a MetaboLights Labs project, please ignore the first 2 steps.</span><br>\n\t\t\t\t\t\t<b>Step 1: Install Aspera ascp command line client</b><br>\n\t\t\t\t\t\tThe Aspera ascp command line client can be downloaded <i><a href=\"http://downloads.asperasoft.com/downloads\" target=\"_blank\">here</a></i><br><br>\n\t\t\t\t\t\t<b>Step 2: PIP Install - MetaboLightsLabs CLI Tool</b><br>\n\t\t\t\t\t\t<code>> pip install git+https://github.com/EBI-Metabolights/MetaboLightsLabs-PythonCLI</code><br>\n\t\t\t\t\t\tFor details on how to install PIP - <i><a href=\"https://pip.pypa.io/en/stable/installing/\" target=\"_blank\">Click here</a></i><br><br>\n\t\t\t\t\t\t<b>Step 3: Upload the files</b><br>\n\t\t\t\t\t\t<span class=\"tiny\">Copy the following command, replace the filesToUpload with your files/folders location (array if more than one) and execute from the command line.</span><br>\n\t\t\t\t\t\t<code>> uploadToMetaboLightsLabs.py -t {{token}} -i <b><code>< filesToUpload ></code></b> -p {{ project.id }} -s DEV</code><br><br>\n\t\t\t\t\t\t<!-- <b>Step 3: Navigate to the folder where the Aspera command line client program ascp is installed.</b><br>\n\t\t\t\t\t\tThe location of the 'ascp' program in the filesystem:<br>\n\t\t\t\t\t\t<span class=\"col-md-12\">\n\t\t\t\t\t\t\t<b>Mac:</b> on the desktop go cd /Applications/Aspera\\ Connect.app/Contents/Resources/ there you'll see the command line utilities where you're going to use 'ascp'.<br><br>\n\n\t\t\t\t\t\t\t<b>Windows:</b> the downloaded files are a bit hidden. For instance in Windows7 the ascp.exe is located in the users home directory in: AppData\\Local\\Programs\\Aspera\\Aspera Connect\\bin\\ascp.exe<br><br>\n\n\t\t\t\t\t\t\t<b>Linux:</b> should be in your user's home directory, cd /home/username/.aspera/connect/bin/ there you'll see the command line utilities where you're going to use 'ascp'.<br><br>\n\t\t\t\t\t\t</span> -->\n\t\t\t\t\t</small>\n\t\t\t\t\t<p class=\"tiny\">For assistance contact us (please mention your error log or screenshots) <a href=\"mailto:metabolights-help@ebi.ac.uk\">&nbsp;here</a></p>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t</form>\n</ng-template>\n<ng-template #projectLocked let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<div class=\"modal-body\">\n\t\t<div class=\"div-wrapper\">\n\t\t\t<img src=\"assets/img/waiting.png\" height=\"128\" class=\"logo\"><br><br>\n\t\t\t<div class=\"spinner\">\n\t\t\t\t<div class=\"bounce1\"></div>\n\t\t\t\t<div class=\"bounce2\"></div>\n\t\t\t\t<div class=\"bounce3\"></div>\n\t\t\t\t<br><br>\n\t\t\t\t<h5 class=\"text-center\">\n\t\t\t\t\tPlease wait while we clone study in to your project. \n\t\t\t\t</h5>\n\t\t\t\t<small class=\"center\">This might take a while depending upon the size of the study.</small>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</ng-template>\n<ng-template #projectStudyLocked let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<div class=\"modal-body\">\n\t\t<div class=\"div-wrapper\">\n\t\t\t<img src=\"assets/img/waiting.png\" height=\"128\" class=\"logo\"><br><br>\n\t\t\t<div class=\"spinner\">\n\t\t\t\t<div class=\"bounce1\"></div>\n\t\t\t\t<div class=\"bounce2\"></div>\n\t\t\t\t<div class=\"bounce3\"></div>\n\t\t\t\t<br><br>\n\t\t\t\t<h5 class=\"text-center\">\n\t\t\t\t\tPlease wait while we assign MetaboLights Study Id to your project. \n\t\t\t\t</h5>\n\t\t\t\t<small class=\"center\">This might take a while depending upon the size of the study.</small>\n\n\t\t\t</div>\n\t\t</div>\n\t</div>\n\t<div class=\"modal-footer  jcc\">\n\t\t<a routerLink=\"/workspace/dashboard\" class=\"list-group-item\" (click)=\"c('Close click')\" routerLinkActive=\"router-link-active\">\n            <i class=\"fa fa-fw fa-dashboard\"></i>&nbsp;Dashboard\n        </a>\n\t</div>\n</ng-template>\n<ng-template #asperaMessage let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<div class=\"modal-body\">\n\t\t<div class=\"div-wrapper\">\n\t\t\t<img src=\"assets/img/waiting.png\" height=\"128\" class=\"logo\"><br><br>\n\t\t\t<div class=\"spinner\">\n\t\t\t\t<br>\n\t\t\t\t<h5 class=\"text-center\">\n\t\t\t\t\tFiles uploaded through Aspera might take some time to appear in the project folder. \n\t\t\t\t</h5>\n\t\t\t\t<small class=\"center\">If the problem persist please contact us for more assistance</small>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n\t<div class=\"modal-footer  jcc\">\n\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Close</button>\n\t\t<button class=\"btn btn-success\" (click)=\"asperaUpload()\">Sure! Proceed</button>\n\t</div>\n</ng-template>\n<ng-template #editProjectDetails let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<form [formGroup]=\"editProjectDetailsForm\" (ngSubmit)=\"submitForm(editProjectDetailsForm.value)\">\n\t\t<div class=\"modal-header\">\n\t\t\t<h5 class=\"modal-title\"><i class=\"fa fa-cogs\"></i>&emsp;Settings </h5>\n\t\t\t<a class=\"close pull-right\" aria-label=\"Close\" (click)=\"d('Cross click')\">\n\t\t\t\t<span aria-hidden=\"true\">&times;</span>\n\t\t\t</a>\n\t\t</div>\n\t\t<div class=\"modal-body\">\n\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!editProjectDetailsForm.controls['title'].valid}\">\n\t\t\t\t<small><label>Title</label></small>\n\t\t\t\t<input class=\"form-control\" type=\"text\" [formControl]=\"editProjectDetailsForm.controls['title']\" >\n\t\t\t</div>\n\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!editProjectDetailsForm.controls['description'].valid}\">\n\t\t\t\t<small><label for=\"projectTitle\">Description (Optional)</label></small>\n\t\t\t\t<textarea rows=\"5\" class=\"form-control\" [formControl]=\"editProjectDetailsForm.controls['description']\"></textarea>\n\t\t\t</div>\n\t\t</div>\n\t\t<div class=\"modal-footer\">\n\t\t\t<button type=\"submit\" [disabled]=\"!editProjectDetailsForm.valid\" class=\"btn btn-primary\">Save</button>\n\t\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Close</button>\n\t\t</div>\n\t</form>\n</ng-template>\n<ng-template #confirmDeleteFiles let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<div class=\"modal-body\">\n\t\t<div class=\"div-wrapper\">\n\t\t\t<img src=\"assets/img/trash.png\" height=\"128\" class=\"logo\"><br><br>\n\t\t\t<div class=\"spinner\">\n\t\t\t\t<h5 class=\"text-center\">\n\t\t\t\t\tAre you sure ? <br><br>Do you want to delete the selected files ? \n\t\t\t\t</h5>\n\t\t\t\t<small class=\"center\">The files will be deleted permanantely and will be unavailable.</small>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n\t<div class=\"modal-footer\">\n\t\t<button type=\"submit\" (click)=\"deleteSelectedFiles()\" class=\"btn btn-danger\">Delete</button>\n\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Close</button>\n\t</div>\n</ng-template>"

/***/ }),

/***/ "../../../../../src/app/work-space/project/project.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__auth_service__ = __webpack_require__("../../../../../src/app/auth.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__project_project__ = __webpack_require__("../../../../../src/app/work-space/project/project.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__project_job__ = __webpack_require__("../../../../../src/app/work-space/project/job.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__project_directory__ = __webpack_require__("../../../../../src/app/work-space/project/directory.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__project_file__ = __webpack_require__("../../../../../src/app/work-space/project/file.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__angular_forms__ = __webpack_require__("../../../forms/@angular/forms.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__common_headers__ = __webpack_require__("../../../../../src/app/common/headers.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__common_globals__ = __webpack_require__("../../../../../src/app/common/globals.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__ng_bootstrap_ng_bootstrap__ = __webpack_require__("../../../../@ng-bootstrap/ng-bootstrap/index.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProjectComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};












var ProjectComponent = (function () {
    function ProjectComponent(authService, route, router, modalService, http, jsonp, fb) {
        this.authService = authService;
        this.route = route;
        this.router = router;
        this.modalService = modalService;
        this.http = http;
        this.jsonp = jsonp;
        this.fb = fb;
        this.server = "";
        this.processing = false;
        this.uploadInprogress = false;
        this.files = [];
        this.mzMLFiles = [];
        this.nmrMLFiles = [];
        this.histories = [];
        this.selectedHistory = "";
        this.selectedGalaxy = "";
        this.error = null;
        this.isaTabDirectories = [];
        this.processedFolders = [];
        this.selectedDirectories = [];
        this.fileIndex = 1;
        this.alerts = [];
        this.MIN_CONNECT_VERSION = "3.6.0.0";
        this.CONNECT_AUTOINSTALL_LOCATION = "//d3gcli72yxqn2z.cloudfront.net/connect/v4";
        this.selectedFiles = [];
        this.projectStructure = new __WEBPACK_IMPORTED_MODULE_5__project_directory__["a" /* Directory */]();
    }
    ProjectComponent.prototype.closeAlert = function (alert) {
        var index = this.alerts.indexOf(alert);
        this.alerts.splice(index, 1);
    };
    ProjectComponent.prototype.initForms = function () {
        this.editProjectDetailsForm = this.fb.group({
            'title': [this.project.title, __WEBPACK_IMPORTED_MODULE_7__angular_forms__["e" /* Validators */].required],
            'description': this.project.description
        });
    };
    ProjectComponent.prototype.updateSelectedFilesList = function (e, index, filename) {
        if (e.target.checked) {
            this.selectedFiles[index] = filename;
        }
        else {
            this.selectedFiles.splice(index, 1);
        }
    };
    // selectAllFiles(e){
    //   if(e.target.checked){
    //     this.selectedFiles = this.files;
    //   }else{
    //     this.selectedFiles = [];
    //   }
    // }
    ProjectComponent.prototype.deleteSelectedFiles = function () {
        var _this = this;
        if (this.selectedFiles.length <= 0 && this.selectedDirectories.length <= 0) {
            return alert("No selection provided");
        }
        var filesToDelete = [];
        filesToDelete = this.selectedFiles.filter(function (n) { return n != undefined; });
        var body = {};
        body["jwt"] = localStorage.getItem("jwt");
        body["user"] = localStorage.getItem("user");
        body["id"] = this.project.id;
        body["files"] = [];
        if (this.selectedDirectories.length > 0) {
            body["directories"] = [];
        }
        filesToDelete.forEach(function (file) {
            if (file != undefined) {
                body["files"].push(file.title.replace("/", ""));
            }
        });
        this.selectedDirectories.forEach(function (sDirectory) {
            body["files"].push(sDirectory.path);
        });
        this.http.post(__WEBPACK_IMPORTED_MODULE_9__common_globals__["a" /* LabsURL */]['delete'], body, { headers: __WEBPACK_IMPORTED_MODULE_8__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            _this.deleteConfirmationModalRef.close();
            _this.alerts.push({
                id: 1,
                type: 'success',
                message: 'File(s) delete successful!',
            });
            _this.ngOnInit();
        }, function (error) {
            _this.alerts.push({
                id: 1,
                type: 'danger',
                message: 'Project update unsuccessful! Error',
            });
        });
    };
    ProjectComponent.prototype.openExportDataToGalaxyModal = function (content) {
        var _this = this;
        this.error = null;
        if (this.selectedFiles.length <= 0) {
            alert("Please select files to export");
        }
        else {
            this.exportToGalaxyModalRef = this.modalService.open(content);
            this.exportToGalaxyModalRef.result.then(function (result) {
                _this.closeResult = "Closed with: " + result;
            }, function (reason) {
                _this.closeResult = "Dismissed " + _this.getDismissReason(reason);
            });
        }
    };
    ProjectComponent.prototype.getGalaxyHistories = function (galaxyUrl) {
        var _this = this;
        this.error = null;
        this.galaxyInstances.forEach(function (instance) {
            if (instance.url == galaxyUrl) {
                _this.selectedGalaxyInstance = instance;
            }
        });
        this.histories = [];
        this.jsonp.get(this.selectedGalaxyInstance.url + "api/histories?key=" + this.selectedGalaxyInstance.apikey + "&callback=JSONP_CALLBACK").map(function (res) {
            _this.histories = res.json();
        }).subscribe(function (response) {
            _this.error = response;
        });
    };
    ProjectComponent.prototype.onChangeGalaxyInstance = function (event, galaxy) {
        this.selectedGalaxy = galaxy;
        this.getGalaxyHistories(galaxy);
    };
    ProjectComponent.prototype.onChangeGalaxyHistory = function (event, history) {
        this.selectedHistory = history;
    };
    ProjectComponent.prototype.exportFilesToGalaxy = function () {
        var _this = this;
        if (this.selectedFiles.length <= 0) {
            alert("Please select files to export");
        }
        else {
            this.processing = true;
            var fileInputs_1 = "";
            this.selectedFiles.forEach(function (file) {
                if (fileInputs_1 == "") {
                    fileInputs_1 = __WEBPACK_IMPORTED_MODULE_9__common_globals__["a" /* LabsURL */]['download'] + "?apikey=" + _this.token + "&path=" + _this.project.id + file.title;
                }
                else {
                    fileInputs_1 = fileInputs_1 + "\\n" + __WEBPACK_IMPORTED_MODULE_9__common_globals__["a" /* LabsURL */]['download'] + "?apikey=" + _this.token + "&path=" + _this.project.id + file.title;
                }
            });
            var body = new FormData();
            body.append('tool_id', 'upload1');
            body.append('history_id', this.selectedHistory);
            body.append('inputs', '{"file_count":1,"file_type":"auto","files_0|to_posix_lines":"False","files_0|url_paste":"' + fileInputs_1 + '"}');
            var contentHeaders_1 = new __WEBPACK_IMPORTED_MODULE_10__angular_http__["d" /* Headers */]();
            contentHeaders_1.append('Content-Type', 'application/x-www-form-urlencoded');
            this.http.post(this.selectedGalaxyInstance.url + "api/tools?key=" + this.selectedGalaxyInstance.apikey, body)
                .subscribe(function (response) {
                _this.exportToGalaxyModalRef.close();
                _this.selectedFiles = [];
                _this.processing = false;
                _this.alerts.push({
                    id: 1,
                    type: 'success',
                    message: 'Files exported to galaxy successfully',
                });
            }, function (error) {
                console.log(error);
                if (error.status == 0) {
                    _this.alerts.push({
                        id: 1,
                        type: 'success',
                        message: 'Files exported to galaxy successfully <br><a target="_blank" href="' + _this.selectedGalaxyInstance.url + 'history/view_multiple">view in galaxy</a',
                    });
                }
                _this.exportToGalaxyModalRef.close();
                _this.selectedFiles = [];
                _this.processing = false;
                _this.processing = false;
            });
        }
    };
    ProjectComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.forEach(function (params) {
            _this.id = params['id'];
        });
        this.files = [];
        for (var i in this.authService.dashBoard.projects) {
            if (this.authService.dashBoard.projects[i].id == this.id) {
                this.project = this.authService.dashBoard.projects[i];
                this.initForms();
                this.token = (JSON.parse(this.project.asperaSettings).asperaURL).split("/")[0];
                this.projectIndex = i;
                break;
            }
        }
        this.server = __WEBPACK_IMPORTED_MODULE_9__common_globals__["a" /* LabsURL */]['domain'];
        if (this.project !== undefined && this.project.isBusy) {
            this.openLoadingProjectModal(this.projectStudyLocked);
            this.getProjectDetails();
        }
        this.getProjectContent(this.id);
        $(function () {
            $('[data-toggle="tooltip"]').tooltip();
        });
        if (this.authService.dashBoard.settings['galaxy']) {
            this.galaxyInstances = JSON.parse(this.authService.dashBoard.settings['galaxy']);
        }
    };
    ProjectComponent.prototype.submitForm = function (body) {
        var _this = this;
        body["jwt"] = localStorage.getItem("jwt");
        body["user"] = localStorage.getItem("user");
        body["id"] = this.project.id;
        this.http.post(__WEBPACK_IMPORTED_MODULE_9__common_globals__["a" /* LabsURL */]['editProjectDetails'], body, { headers: __WEBPACK_IMPORTED_MODULE_8__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            var project = new __WEBPACK_IMPORTED_MODULE_3__project_project__["a" /* Project */]().deserialize(JSON.parse(response.json().content));
            _this.project = project;
            //this.authService.dashBoard.projects.push(project);
            _this.editingModalRef.close();
            _this.initForms();
            _this.alerts.push({
                id: 1,
                type: 'success',
                message: 'Project ' + _this.project.title + ' update successful!',
            });
        }, function (error) {
            _this.alerts.push({
                id: 1,
                type: 'danger',
                message: 'Project update unsuccessful! Error',
            });
            console.log(error.text());
        });
    };
    ProjectComponent.prototype.asperaUpload = function () {
        this.asperaWeb = new AW4.Connect({ sdkLocation: this.CONNECT_AUTOINSTALL_LOCATION, minVersion: this.MIN_CONNECT_VERSION });
        var connectInstaller = new AW4.ConnectInstaller({ sdkLocation: this.CONNECT_AUTOINSTALL_LOCATION });
        var statusEventListener = function (eventType, data) {
            if (eventType === AW4.Connect.EVENT.STATUS && data == AW4.Connect.STATUS.INITIALIZING) {
                connectInstaller.showLaunching();
            }
            else if (eventType === AW4.Connect.EVENT.STATUS && data == AW4.Connect.STATUS.FAILED) {
                connectInstaller.showDownload();
            }
            else if (eventType === AW4.Connect.EVENT.STATUS && data == AW4.Connect.STATUS.OUTDATED) {
                connectInstaller.showUpdate();
            }
            else if (eventType === AW4.Connect.EVENT.STATUS && data == AW4.Connect.STATUS.RUNNING) {
                connectInstaller.connected();
            }
        };
        this.asperaWeb.addEventListener(AW4.Connect.EVENT.STATUS, statusEventListener);
        this.asperaWeb.initSession();
        this.asperaMessageModalRef.close();
        this.asperaWeb.showSelectFileDialog({
            success: (function (dataTransferObj) {
                this.buildUploadSpec(dataTransferObj);
            }).bind(this),
            error: function (error) {
                alert(error);
                console.error(error);
            }
        });
    };
    ProjectComponent.prototype.showAsperaWaitingMessage = function (content) {
        this.asperaMessageModalRef = this.modalService.open(content);
    };
    // Note: Files uploaded through Aspera might take some time to appear in the project folder. 
    ProjectComponent.prototype.buildUploadSpec = function (dataTransferObj) {
        var transferSpecs = [{
                "aspera_connect_settings": {
                    // allow_dialogs is true by default.
                    // Added for clarity.
                    "allow_dialogs": true,
                    "back_link": location.href
                },
                "transfer_spec": {}
            }];
        var params = {};
        var asperaSettings = JSON.parse(this.project.asperaSettings);
        params["remote_user"] = asperaSettings.asperaUser;
        params["remote_password"] = asperaSettings.asperaSecret;
        params['remote_host'] = "hx-fasp-1.ebi.ac.uk"; //asperaSettings.asperaServer;
        // params['fasp_port'] = 33001;
        params['target_rate_kbps'] = 45000;
        params['min_rate_kbps'] = 0;
        params['lock_policy'] = false;
        params['lock_target_rate'] = false;
        params['direction'] = "send";
        params['lock_min_rate'] = false;
        params['rate_policy'] = "fair";
        params['cipher'] = "aes-128";
        params['ssh_port'] = 33001;
        transferSpecs[0]["transfer_spec"] = params;
        transferSpecs[0]["transfer_spec"]['paths'] = [];
        var files = dataTransferObj.dataTransfer.files;
        for (var i = 0, length = files.length; i < length; i += 1) {
            // Local path
            var pathSet = { src: files[i].name };
            var srcPath = pathSet.src || '';
            var destPath = '';
            var paths = transferSpecs[0]["transfer_spec"]['paths'];
            if (!paths) {
                transferSpecs[0]["transfer_spec"]['paths'] = [];
            }
            (transferSpecs[0]["transfer_spec"]['paths']).push({
                'source': srcPath,
                'destination': destPath
            });
        }
        transferSpecs[0]["transfer_spec"]['destination_root'] = "/dev/userSpace/" + asperaSettings.asperaURL;
        var finalConfig = {};
        finalConfig['transfer_specs'] = transferSpecs;
        var requestId = this.asperaWeb.startTransfers(finalConfig, { success: function (data) {
                console.log("Upload started");
            } });
    };
    ProjectComponent.prototype.getProjectDetails = function () {
        var _this = this;
        var body = {
            "jwt": localStorage.getItem("jwt"),
            "user": localStorage.getItem("user"),
            "id": this.id
        };
        var updatedProject = null;
        this.http.post(__WEBPACK_IMPORTED_MODULE_9__common_globals__["a" /* LabsURL */]['projectDetails'], body, { headers: __WEBPACK_IMPORTED_MODULE_8__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            var updatedProject = new __WEBPACK_IMPORTED_MODULE_3__project_project__["a" /* Project */]().deserialize(JSON.parse(response.json().content));
            ;
            if (updatedProject.isBusy) {
                setTimeout(function () { _this.getProjectDetails(); }, 5000);
            }
            else {
                _this.authService.dashBoard.projects[_this.projectIndex] = updatedProject;
                _this.project = updatedProject;
                if (_this.loadingProjectModalRef) {
                    _this.loadingProjectModalRef.close();
                }
                if (_this.loadingModalRef) {
                    _this.loadingModalRef.close();
                }
                _this.alerts.push({
                    id: 1,
                    type: 'success',
                    message: 'Project ' + updatedProject.title + ' cloning successful!',
                });
            }
        }, function (error) {
            alert(error.text());
            console.log(error.text());
        });
    };
    ProjectComponent.prototype.open = function (content) {
        this.modalService.open(content);
    };
    ProjectComponent.prototype.openEditModal = function (content) {
        var _this = this;
        this.editingModalRef = this.modalService.open(content);
        this.editingModalRef.result.then(function (result) {
            _this.closeResult = "Closed with: " + result;
        }, function (reason) {
            _this.closeResult = "Dismissed " + _this.getDismissReason(reason);
        });
    };
    ProjectComponent.prototype.openMzml2IsaModal = function (content) {
        this.mzml2isaModalRef = this.modalService.open(content);
    };
    ProjectComponent.prototype.openNmrml2IsaModal = function (content) {
        this.nmrml2isaModalRef = this.modalService.open(content);
    };
    ProjectComponent.prototype.openSubmitAsStudyModal = function (content) {
        this.submitAsStudyModalRef = this.modalService.open(content);
    };
    ProjectComponent.prototype.openJobsModal = function (content) {
        if (this.project.jobs.length > 0) {
            this.jobsModalRef = this.modalService.open(content);
        }
    };
    ProjectComponent.prototype.openDeleteConfirmationModal = function (content) {
        var _this = this;
        if (this.selectedFiles.length <= 0 && this.selectedDirectories.length <= 0) {
            alert("No selection provided");
            return;
        }
        this.deleteConfirmationModalRef = this.modalService.open(content);
        this.deleteConfirmationModalRef.result.then(function (result) {
            _this.closeResult = "Closed with: " + result;
        }, function (reason) {
            _this.closeResult = "Dismissed " + _this.getDismissReason(reason);
        });
    };
    ProjectComponent.prototype.openLoadingModal = function (content) {
        var _this = this;
        this.loadingModalRef = this.modalService.open(content, { backdrop: "static" });
        this.loadingModalRef.result.then(function (result) {
            _this.closeResult = "Closed with: " + result;
        }, function (reason) {
            _this.closeResult = "Dismissed " + _this.getDismissReason(reason);
        });
    };
    ProjectComponent.prototype.openLoadingProjectModal = function (content) {
        var _this = this;
        this.loadingProjectModalRef = this.modalService.open(content, { backdrop: "static" });
        this.loadingProjectModalRef.result.then(function (result) {
            _this.closeResult = "Closed with: " + result;
        }, function (reason) {
            _this.closeResult = "Dismissed " + _this.getDismissReason(reason);
        });
    };
    ProjectComponent.prototype.submitProjectAsStudy = function (isUpdate) {
        var _this = this;
        this.processing = true;
        var body = {
            "jwt": localStorage.getItem("jwt"),
            "user": localStorage.getItem("user"),
            "project_id": this.id
        };
        this.http.post(__WEBPACK_IMPORTED_MODULE_9__common_globals__["a" /* LabsURL */]['submitProject'], body, { headers: __WEBPACK_IMPORTED_MODULE_8__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            _this.processing = false;
            if (_this.submitAsStudyModalRef != undefined) {
                _this.submitAsStudyModalRef.close();
            }
            var respJob = JSON.parse(response.json().content);
            var projectJob = new __WEBPACK_IMPORTED_MODULE_4__project_job__["a" /* Job */]().deserialize(respJob);
            _this.alerts.push({
                id: _this.alerts.length + 1,
                type: 'success',
                message: 'Job Satus: ' + projectJob.status + ' | ID: ' + projectJob.jobId,
            });
            _this.openLoadingProjectModal(_this.projectStudyLocked);
            _this.project.isBusy = true;
        }, function (error) {
            _this.processing = false;
            console.log(error.text());
        });
    };
    ProjectComponent.prototype.displayJobLogs = function (job) {
        var body = {
            "jwt": localStorage.getItem("jwt"),
            "user": localStorage.getItem("user"),
            "id": this.id
        };
        if (job != undefined) {
            body["jobId"] = job.jobId;
            if (Object.keys(job.log).length == 0) {
                this.http.post(__WEBPACK_IMPORTED_MODULE_9__common_globals__["a" /* LabsURL */]['getJobLogs'], body, { headers: __WEBPACK_IMPORTED_MODULE_8__common_headers__["a" /* contentHeaders */] })
                    .subscribe(function (response) {
                    job.log = JSON.parse((JSON.parse(response.json().content).log).replace(/\\n\\n/g, "</p><p>").replace(/\\n/g, "<br/>"));
                    $("#job_" + job.jobId).collapse('toggle');
                }, function (error) {
                    console.log(error.text());
                });
            }
            else {
                $("#job_" + job.jobId).collapse('toggle');
            }
        }
    };
    ProjectComponent.prototype.getJobStatus = function (job) {
        if (this.mzMLFiles.length > 0) {
            this.convertMzml2isa(job);
        }
        else {
            this.convertNmrml2isa(job);
        }
    };
    ProjectComponent.prototype.convertNmrml2isa = function (job) {
        var _this = this;
        this.processing = true;
        var body = {
            "jwt": localStorage.getItem("jwt"),
            "user": localStorage.getItem("user"),
            "id": this.id
        };
        if (job != undefined) {
            body["jobId"] = job.jobId;
        }
        this.http.post(__WEBPACK_IMPORTED_MODULE_9__common_globals__["a" /* LabsURL */]['convertNMRMLToISA'], body, { headers: __WEBPACK_IMPORTED_MODULE_8__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            _this.processing = false;
            if (_this.nmrml2isaModalRef != undefined) {
                _this.nmrml2isaModalRef.close();
            }
            var respJob = JSON.parse(response.json().content);
            var projectJob = new __WEBPACK_IMPORTED_MODULE_4__project_job__["a" /* Job */]().deserialize(respJob);
            if (job == undefined) {
                _this.alerts.push({
                    id: _this.alerts.length + 1,
                    type: 'success',
                    message: 'Job Submitted Successful | ID: ' + projectJob.jobId + " ( STATUS: " + projectJob.status + ")",
                });
                _this.project.jobs.push(projectJob);
            }
            else {
                _this.alerts.push({
                    id: _this.alerts.length + 1,
                    type: 'success',
                    message: 'Job Satus: ' + projectJob.status + ' | ID: ' + projectJob.jobId,
                });
                job.status = projectJob.status;
                job = projectJob;
            }
            _this.getProjectContent(_this.id);
        }, function (error) {
            _this.processing = false;
            console.log(error.text());
        });
    };
    ProjectComponent.prototype.convertMzml2isa = function (job) {
        var _this = this;
        this.processing = true;
        var body = {
            "jwt": localStorage.getItem("jwt"),
            "user": localStorage.getItem("user"),
            "id": this.id
        };
        if (job != undefined) {
            body["jobId"] = job.jobId;
        }
        this.http.post(__WEBPACK_IMPORTED_MODULE_9__common_globals__["a" /* LabsURL */]['convertMZMLToISA'], body, { headers: __WEBPACK_IMPORTED_MODULE_8__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            _this.processing = false;
            if (_this.mzml2isaModalRef != undefined) {
                _this.mzml2isaModalRef.close();
            }
            var respJob = JSON.parse(response.json().content);
            var projectJob = new __WEBPACK_IMPORTED_MODULE_4__project_job__["a" /* Job */]().deserialize(respJob);
            if (job == undefined) {
                _this.alerts.push({
                    id: _this.alerts.length + 1,
                    type: 'success',
                    message: 'Job Submitted Successful | ID: ' + projectJob.jobId + " ( STATUS: " + projectJob.status + ")",
                });
                _this.project.jobs.push(projectJob);
            }
            else {
                _this.alerts.push({
                    id: _this.alerts.length + 1,
                    type: 'success',
                    message: 'Job Satus: ' + projectJob.status + ' | ID: ' + projectJob.jobId,
                });
                job.status = projectJob.status;
                job = projectJob;
            }
            _this.getProjectContent(_this.id);
        }, function (error) {
            _this.processing = false;
            console.log(error.text());
        });
    };
    ProjectComponent.prototype.getProjectContent = function (id) {
        var _this = this;
        var body = {
            "jwt": localStorage.getItem("jwt"),
            "user": localStorage.getItem("user"),
            "id": id
        };
        this.processing = true;
        this.http.post(__WEBPACK_IMPORTED_MODULE_9__common_globals__["a" /* LabsURL */]['projectContent'], body, { headers: __WEBPACK_IMPORTED_MODULE_8__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            _this.processing = false;
            _this.selectedFiles = [];
            _this.processedFolders = [];
            _this.projectStructure = new __WEBPACK_IMPORTED_MODULE_5__project_directory__["a" /* Directory */]();
            _this.files = [];
            var body = JSON.parse(response.json().content);
            for (var i in body) {
                var file = body[i];
                if (file.indexOf(".info") > -1 || file.indexOf(".log") > -1) {
                    //console.log("Ignoring log and info files");
                }
                else {
                    _this.files.push(file);
                }
                if (file.indexOf(".mzML") > -1 || file.indexOf(".mzml") > -1 || file.indexOf(".imzML") > -1) {
                    _this.mzMLFiles.push(file);
                }
                if (file.indexOf(".nmrML") > -1 || file.indexOf(".nmrml") > -1) {
                    _this.nmrMLFiles.push(file);
                }
                if (file.indexOf(".aspx") > -1) {
                    _this.uploadInprogress = true;
                }
            }
            _this.renderRichFileStructure();
            _this.checkForValidISATabFiles();
        }, function (error) {
            alert(error.text());
            console.log(error.text());
        });
    };
    ProjectComponent.prototype.checkForValidISATabFiles = function () {
        var allFilesExist = [false, false, false];
        this.projectStructure.files.forEach(function (file) {
            if (file.title.indexOf("i_") == 1) {
                allFilesExist[0] = true;
            }
            else if (file.title.indexOf("s_") == 1) {
                allFilesExist[1] = true;
            }
            else if (file.title.indexOf("a_") == 1) {
                allFilesExist[2] = true;
            }
        });
        if (allFilesExist.indexOf(false) == -1) {
            this.isaTabDirectories.push(this.projectStructure);
        }
    };
    ProjectComponent.prototype.renderRichFileStructure = function () {
        this.projectStructure = new __WEBPACK_IMPORTED_MODULE_5__project_directory__["a" /* Directory */]();
        this.projectStructure.title = "root";
        this.projectStructure.level = 0;
        this.projectStructure = this.recursivelyDigFolder("/", this.projectStructure, 1);
    };
    ProjectComponent.prototype.recursivelyDigFolder = function (folderPath, directory, depth) {
        var _this = this;
        this.files.forEach(function (file) {
            if (file.indexOf(folderPath) == 0 && file != folderPath) {
                if ((file.split("/").length - 1 <= depth) && file.indexOf(".") == -1) {
                    if (_this.processedFolders.indexOf(file) == -1) {
                        _this.processedFolders.push(file);
                        var subDirectory = new __WEBPACK_IMPORTED_MODULE_5__project_directory__["a" /* Directory */]();
                        subDirectory.title = file.split("/").slice(-1)[0];
                        subDirectory.path = file;
                        subDirectory.level = depth;
                        directory.directories.push(_this.recursivelyDigFolder(file, subDirectory, depth + 1));
                    }
                }
                else {
                    if ((file.split("/").length - 1) <= depth) {
                        var subFile = new __WEBPACK_IMPORTED_MODULE_6__project_file__["a" /* File */]();
                        subFile.title = file;
                        subFile.index = _this.fileIndex;
                        _this.fileIndex = _this.fileIndex + 1;
                        directory.files.push(subFile);
                    }
                }
            }
        });
        return directory;
    };
    ProjectComponent.prototype.getDismissReason = function (reason) {
        if (reason === __WEBPACK_IMPORTED_MODULE_11__ng_bootstrap_ng_bootstrap__["d" /* ModalDismissReasons */].ESC) {
            return 'by pressing ESC';
        }
        else if (reason === __WEBPACK_IMPORTED_MODULE_11__ng_bootstrap_ng_bootstrap__["d" /* ModalDismissReasons */].BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        }
        else {
            return "with: " + reason;
        }
    };
    return ProjectComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_19" /* ViewChild */])('projectLocked'),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* ElementRef */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* ElementRef */]) === "function" && _a || Object)
], ProjectComponent.prototype, "projectLocked", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_19" /* ViewChild */])('projectStudyLocked'),
    __metadata("design:type", typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* ElementRef */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* ElementRef */]) === "function" && _b || Object)
], ProjectComponent.prototype, "projectStudyLocked", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", Object)
], ProjectComponent.prototype, "alerts", void 0);
ProjectComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'app-project',
        template: __webpack_require__("../../../../../src/app/work-space/project/project.component.html"),
        styles: [__webpack_require__("../../../../../src/app/work-space/project/project.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_2__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__auth_service__["a" /* AuthService */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* ActivatedRoute */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */]) === "function" && _e || Object, typeof (_f = typeof __WEBPACK_IMPORTED_MODULE_11__ng_bootstrap_ng_bootstrap__["e" /* NgbModal */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_11__ng_bootstrap_ng_bootstrap__["e" /* NgbModal */]) === "function" && _f || Object, typeof (_g = typeof __WEBPACK_IMPORTED_MODULE_10__angular_http__["c" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_10__angular_http__["c" /* Http */]) === "function" && _g || Object, typeof (_h = typeof __WEBPACK_IMPORTED_MODULE_10__angular_http__["e" /* Jsonp */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_10__angular_http__["e" /* Jsonp */]) === "function" && _h || Object, typeof (_j = typeof __WEBPACK_IMPORTED_MODULE_7__angular_forms__["f" /* FormBuilder */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_7__angular_forms__["f" /* FormBuilder */]) === "function" && _j || Object])
], ProjectComponent);

var _a, _b, _c, _d, _e, _f, _g, _h, _j;
//# sourceMappingURL=project.component.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/project/project.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__project_job__ = __webpack_require__("../../../../../src/app/work-space/project/job.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Project; });

var Project = (function () {
    function Project() {
        this.settings = "";
        this.projectLocation = "";
        this.asperaSettings = "";
        this.study = "";
        this.id = "";
        this.title = "";
        this.description = "";
        this.updatedAt = "";
        this.createdAt = "";
        this.isBusy = false;
        this.jobs = [];
    }
    Project.prototype.deserialize = function (input) {
        this.settings = input.settings;
        this.projectLocation = input.projectLocation;
        this.asperaSettings = input.asperaSettings;
        this.title = input.title;
        this.study = input.studyId;
        this.description = input.description;
        this.id = input.id;
        this.createdAt = input.createdAt;
        this.updatedAt = input.updatedAt;
        this.isBusy = input.busy;
        for (var id in input.jobs) {
            var job = input.jobs[id];
            var projectJob = new __WEBPACK_IMPORTED_MODULE_0__project_job__["a" /* Job */]().deserialize(job);
            this.jobs.push(projectJob);
        }
        return this;
    };
    return Project;
}());

//# sourceMappingURL=project.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/project/tree-view.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__project_directory__ = __webpack_require__("../../../../../src/app/work-space/project/directory.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__project_project_component__ = __webpack_require__("../../../../../src/app/work-space/project/project.component.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return TreeView; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};
var __param = (this && this.__param) || function (paramIndex, decorator) {
    return function (target, key) { decorator(target, key, paramIndex); }
};



var TreeView = (function () {
    function TreeView(parent) {
        this.parent = parent;
    }
    TreeView.prototype.isDirectorySelected = function (directory) {
        var _this = this;
        var allSelected = true;
        if (directory.files.length == 0) {
            if (directory.directories.length > 0) {
                directory.directories.forEach(function (subDirectory) {
                    allSelected = _this.isDirectorySelected(subDirectory);
                });
            }
            else if (this.parent.selectedDirectories.indexOf(directory) == -1) {
                allSelected = false;
            }
        }
        else {
            directory.files.forEach(function (file) {
                if (_this.parent.selectedFiles.indexOf(file) == -1) {
                    allSelected = false;
                }
            });
        }
        return allSelected;
    };
    TreeView.prototype.toogleDirectory = function (event) {
        event.target.nextElementSibling.classList.toggle("hide");
    };
    TreeView.prototype.isISAFile = function (file) {
        if (file.title.indexOf("s_") == 1) {
            return true;
        }
        else if (file.title.indexOf("i_") == 1) {
            return true;
        }
        else if (file.title.indexOf("a_") == 1) {
            return true;
        }
        return false;
    };
    TreeView.prototype.updateSelectedDirectoryFilesList = function (e, directory) {
        if (e.target.checked) {
            this.parent.selectedDirectories.push(directory);
            this.addFilesRecursively(directory);
        }
        else {
            var selectedDirectoryIndex_1 = -1;
            var index_1 = 0;
            this.parent.selectedDirectories.forEach(function (pdirectory) {
                if (pdirectory.title === directory.title) {
                    selectedDirectoryIndex_1 = index_1;
                }
                index_1 = index_1 + 1;
            });
            if (selectedDirectoryIndex_1 > -1) {
                this.parent.selectedDirectories.splice(selectedDirectoryIndex_1, 1);
            }
            this.removeFilesRecursively(directory);
        }
    };
    TreeView.prototype.updateSelectedFilesList = function (e, index, filename) {
        if (e.target.checked) {
            this.parent.selectedFiles[index] = filename;
        }
        else {
            this.parent.selectedFiles[index] = null;
        }
    };
    TreeView.prototype.addFilesRecursively = function (directory) {
        var _this = this;
        if (directory.directories.length > 0) {
            directory.directories.forEach(function (directory) {
                _this.addFilesRecursively(directory);
                _this.parent.selectedDirectories.push(directory);
            });
        }
        directory.files.forEach(function (file) {
            _this.parent.selectedFiles[file.index - 1] = file;
        });
    };
    TreeView.prototype.removeFilesRecursively = function (directory) {
        var _this = this;
        if (directory.directories.length > 0) {
            directory.directories.forEach(function (directory) {
                _this.removeFilesRecursively(directory);
            });
        }
        var selectedDirectoryIndex = -1;
        var index = 0;
        this.parent.selectedDirectories.forEach(function (pdirectory) {
            if (pdirectory.title === directory.title) {
                selectedDirectoryIndex = index;
            }
            index = index + 1;
        });
        if (selectedDirectoryIndex > -1) {
            this.parent.selectedDirectories.splice(selectedDirectoryIndex, 1);
        }
        directory.files.forEach(function (file) {
            _this.parent.selectedFiles[file.index - 1] = null;
        });
    };
    return TreeView;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__project_directory__["a" /* Directory */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__project_directory__["a" /* Directory */]) === "function" && _a || Object)
], TreeView.prototype, "directory", void 0);
TreeView = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'tree-view',
        styles: [__webpack_require__("../../../../../src/app/work-space/project/project.component.css")],
        template: "\n  <span *ngIf=\"directory.level > 0\">\n    <input [checked]=\"isDirectorySelected(directory)\" (change)=\"updateSelectedDirectoryFilesList($event, directory)\" type=\"checkbox\">&nbsp;\n    <img src=\"assets/img/folder.png\" height=\"16\" class=\"logo\">\n  </span>\n  <a class=\"pointer\" (click)=\"toogleDirectory($event)\" *ngIf=\"directory.level > 0\">\n    {{ directory.title }}\n  </a>\n  <ul [ngClass]=\"{'hide' :  directory.level > 0, 'npl' :  directory.level == 0}\">\n    <li *ngFor=\"let subDirectory of directory.directories\">\n      <tree-view [directory]=\"subDirectory\"></tree-view>\n    </li>\n    <li *ngFor=\"let file of directory.files; let i = index;\">\n      <a><input [checked]=\"parent.selectedFiles.indexOf(file) > -1\" (change)=\"updateSelectedFilesList($event, file.index - 1 , file)\" type=\"checkbox\">\n          <span *ngIf=\"isISAFile(file); else elseBlock\">\n            <img src=\"assets/img/isa.png\" height=\"16\" class=\"logo\"/>\n          </span>\n          <ng-template #elseBlock>\n            <img src=\"assets/img/file.png\" height=\"16\" class=\"logo\"/>\n          </ng-template>\n          &nbsp;{{ file.title.split(\"/\").slice(-1)[0] }}</a>\n    </li>\n  </ul>\n  "
    }),
    __param(0, __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["f" /* Inject */])(__WEBPACK_IMPORTED_MODULE_2__project_project_component__["a" /* ProjectComponent */])),
    __metadata("design:paramtypes", [typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__project_project_component__["a" /* ProjectComponent */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__project_project_component__["a" /* ProjectComponent */]) === "function" && _b || Object])
], TreeView);

var _a, _b;
//# sourceMappingURL=tree-view.component.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/projects/projects.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".p-wrapper{\n    background: #F5F5F6;\n    border-radius: 3px;\n    border: 1px solid #E1E2E4;\n    box-shadow: 0 1px 2px 0 rgba(0,0,0,0.1);\n    -moz-box-shadow: 0 1px 2px 0 rgba(0,0,0,0.1);\n    -webkit-box-shadow: 0 1px 2px 0 rgba(0,0,0,0.1);\n    padding: 15px;\n    color: #898E95;\n    min-width: 180px;\n    margin: 0 20px 20px 20px;\n}\n\n.p-wrapper h1{\n    font-size: 18px;\n    font-weight: 0.9em;\n    text-overflow: ellipsis;\n}\n\n\n.p-wrapper h5{\n    font-size: 16px;\n    margin-bottom: 0px;\n}\n\n.p-wrapper .tiny{\n    font-weight: lighter;\n    font-size: 10px;\n    margin-bottom: 10px;\n}\n\n.p-wrapper:hover{\n    border: 1px solid #E1E2E4;\n    cursor: pointer;\n    box-shadow: 0 1px 2px 0 rgba(0,0,0,0.9);\n    -moz-box-shadow: 0 1px 2px 0 rgba(0,0,0,0.9);\n    -webkit-box-shadow: 0 1px 2px 0 rgba(0,0,0,0.9);\n}\n\n.selectedProject{\n    border: 1px solid #E1E2E4;\n    cursor: pointer;\n    color: #000;\n    box-shadow: 0 1px 2px 0 rgba(0,0,0,0.9);\n    -moz-box-shadow: 0 1px 2px 0 rgba(0,0,0,0.9);\n    -webkit-box-shadow: 0 1px 2px 0 rgba(0,0,0,0.9);\n}\n\n.cloning_project{\n\tdisplay: -webkit-box;\n\tdisplay: -ms-flexbox;\n\tdisplay: flex;\n\t-webkit-box-align: center;\n\t    -ms-flex-align: center;\n\t        align-items: center;\n  \t-webkit-box-pack: center;\n  \t    -ms-flex-pack: center;\n  \t        justify-content: center;\n\tmin-height: 50vh;\n\tpadding: 40px 40px;\n\t-webkit-box-orient: vertical;\n\t-webkit-box-direction: normal;\n\t    -ms-flex-direction: column;\n\t        flex-direction: column;\n\ttext-align: center;\n}\n\n.cloning_project .spinner > h5{\n\tfont-weight: normal;\n\tletter-spacing: 1px;\n}\n\n.cloning_project .spinner > small{\n\tfont-weight: lighter;\n}\n\n.spinner{\n\tposition: relative !important;\n    top: 0%;\n    left: 0%;\n    margin-top: 0px; \n    margin-left: 0px;\n    text-align: center;\n}\n\n.md{\n\tmargin-top: 10px;\n\tpadding-bottom: 2px;\n}\n\n.bt{\n    border-top: 1px solid #f1f1f4;\n    margin-top: 10px;\n    padding-top: 10px;\n}\n\n.jcc{\n    -webkit-box-pack: center !important;\n        -ms-flex-pack: center !important;\n            justify-content: center !important;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/work-space/projects/projects.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"content row\">\n\t<div class=\"col-md-9 mini-wrapper\">\n\t\t<div class=\"section\">\n\t\t\t<h3 class=\"title\"><i class=\"fa fa-cubes\"></i> Projects</h3>\n\t\t</div>\n\t\t<div class=\" section ntp\" *ngIf=\"projects.length > 0\">\n\t\t\t<div class=\"row\">\n\t\t\t\t<div *ngFor=\"let project of projects\">\n\t\t\t\t\t<div class=\"col-3\" [class.selectedProject]=\"project.id === selectedProject.id\" class=\"p-wrapper\"  (dblclick)=\"onSelect(project)\" (click)=\"setSelected(project)\">\n\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t<h1><i class=\"fa fa-folder-o fa-2x\"></i></h1>\n\t\t\t\t\t\t\t<h5><a>{{ project.title | slice:0:18 }}</a></h5>\n\t\t\t\t\t\t\t<h6 class=\"md\">\n\t\t\t\t\t\t\t\t<small class=\"tiny\">\n\t\t\t\t\t\t\t\t\t<span class=\"pull-left\">\n\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-info-circle\"></i>&nbsp;{{ getId(project.id) }}\n\t\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t\t<span class=\"pull-right\">\n\t\t\t\t\t\t\t\t\t\t<span *ngIf=\"project.isBusy == true\">\n\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-lock fa-2x\"></i>\n\t\t\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t</small>\n\t\t\t\t\t\t\t</h6>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<div *ngIf=\"projects.length == 0\">\n\t\t\t<div class=\"cloning_project\">\n\t\t\t\t<img src=\"assets/img/start.png\" height=\"128\" class=\"logo\"><br>\n\t\t\t\t<div class=\"spinner\">\n\t\t\t\t\t<h6 class=\"text-center\">\n\t\t\t\t\t\tNo projects found! Create a new project to get started? \n\t\t\t\t\t</h6><br>\n\t\t\t\t\t<small class=\"center\"><i><a href=\"http://www.ebi.ac.uk/metabolights/contact\" target=\"_blank\" class=\"btn btn-sm btn-primary\">Need Help</a></i></small>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n\t<div class=\"col-xs-12 col-md-3 grey np\">\n\t\t<div class=\"section\">\n\t\t\t<!-- \n\t\t\t\t<span class=\"right\">\n\t\t  \t\t\t<label>Manage Projects</label>\n\t\t  \t\t</span> \n\t\t  \t-->\n\t\t  \t<span class=\"form-group\">\n\t\t  \t\t<button (click)=\"open(content)\" class=\"btn btn-success btn-sm form-control\">\n\t\t  \t\t\t<i class=\"fa fa-plus\"></i> Create New Project\n\t\t  \t\t</button>\n\t\t  \t</span>\n\t\t</div>\n\t\t<div class=\"section npb\" *ngIf=\"selectedProject\">\n\t\t  \t<div class=\"ml-card\">\n\t\t  \t\t<div class=\"ml-card-header\">\n\t\t  \t\t\tProject Details\n\t\t  \t\t</div>\n\t\t  \t\t<div class=\"ml-card-body\">\n\t\t  \t\t\t<small><i>TITLE: </i></small>\n\t\t  \t\t\t<p>{{ selectedProject.title }}</p>\n\t\t  \t\t\t<small><i>ID: </i></small>\n\t\t  \t\t\t<p><small>{{ selectedProject.id }}</small></p>\n\t\t  \t\t\t<small><i>Description: </i></small>\n\t\t  \t\t\t<p>{{ selectedProject.description }}</p>\n\t\t  \t\t\t<small><i>Created at: </i></small>\n\t\t  \t\t\t<p>{{ selectedProject.createdAt | date:'yyyy-MM-dd HH:mm:ss Z' }}</p>\n\t\t  \t\t\t<small><i>Updated at: </i></small>\n\t\t  \t\t\t<p>{{ selectedProject.updatedAt | date:'yyyy-MM-dd HH:mm:ss Z' }}</p>\n\t\t  \t\t\t<span class=\"form-group nmb bt\">\n\t\t\t\t  \t\t<p>\n\t\t\t\t  \t\t\t<a class=\"text-danger pointer\" (click)=\"openDeleteConfirmationModal(confirmDeleteProject)\">\n\t\t\t\t\t  \t\t\t<i class=\"fa fa-trash\"></i> Delete Project\n\t\t\t\t\t  \t\t</a>\n\t\t\t\t  \t\t</p>\n\t\t\t\t  \t</span>\n\t\t  \t\t</div>\n\t\t  \t</div>\n\t\t  \t<p *ngFor=\"let alert of alerts\">\n\t\t\t\t<small><ngb-alert [type]=\"alert.type\" (close)=\"closeAlert(alert)\">{{ alert.message }}</ngb-alert></small>\n\t\t\t</p>\n\t\t</div>\n\t\t<div class=\"section npb nbb\">\n\t\t\t<p class=\"text-danger\">\n\t\t\t\t<small>\n\t\t\t\t\t<a href=\"https://github.com/EBI-Metabolights/Metabolights-Labs/issues\" target=\"_blank\" class=\"pointer text-muted\"><i class=\"fa fa-bug\"></i>&nbsp;Report bug</a>\n\t\t\t\t</small>\n\t\t\t</p>\n\t\t</div>\n\t</div>\n</div>\n<ng-template #confirmDeleteProject let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<div class=\"modal-body\">\n\t\t<div class=\"div-wrapper\">\n\t\t\t<img src=\"assets/img/trash.png\" height=\"128\" class=\"logo\"><br><br>\n\t\t\t<div class=\"spinner\">\n\t\t\t\t<h6 class=\"text-center\">\n\t\t\t\t\tAre you sure ? <br><br>Do you want to delete the selected project\n\t\t\t\t</h6>\n\t\t\t\t<h6 class=\"text-danger\">\"{{ selectedProject.title }}\"</h6>\n\t\t\t\t<small class=\"center\">The project and its contents will be deleted permanantely and will be unavailable.</small>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n\t<div class=\"modal-footer jcc\">\n\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">No! Go back</button>\n\t\t<button type=\"submit\" (click)=\"deleteSelectedProject()\" class=\"btn btn-danger\">Delete project permanently!</button>\n\t</div>\n</ng-template> \n<ng-template #waiting let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<div class=\"modal-body\">\n\t\t<div class=\"div-wrapper\">\n\t\t\t<img src=\"assets/img/waiting.png\" height=\"128\" class=\"logo\"><br><br>\n\t\t\t<div class=\"spinner\">\n\t\t\t\t<div class=\"bounce1\"></div>\n\t\t\t\t<div class=\"bounce2\"></div>\n\t\t\t\t<div class=\"bounce3\"></div>\n\t\t\t\t<br><br>\n\t\t\t\t<h5 class=\"text-center\">\n\t\t\t\t\tPlease wait while we create your project. \n\t\t\t\t</h5>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</ng-template>\n<ng-template #content let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<form [formGroup]=\"createProjectForm\" (ngSubmit)=\"submitForm(createProjectForm.value, waiting)\">\n\t\t<div *ngIf=\"cloningProject\">\n\t\t\t<div class=\"modal-body\">\n\t\t\t\t<div class=\"cloning_project\">\n\t\t\t\t\t<img src=\"assets/img/waiting.png\" height=\"128\" class=\"logo\"><br><br>\n\t\t\t\t\t<div class=\"spinner\">\n\t\t\t\t\t\t<div class=\"bounce1\"></div>\n\t\t\t\t\t\t<div class=\"bounce2\"></div>\n\t\t\t\t\t\t<div class=\"bounce3\"></div>\n\t\t\t\t\t\t<br><br>\n\t\t\t\t\t\t<h5 class=\"text-center\">\n\t\t\t\t\t\t\tPlease wait while we clone study in to your project. \n\t\t\t\t\t\t</h5>\n\t\t\t\t\t\t<small class=\"center\">This might take a while depending upon the size of the study.</small>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<div *ngIf=\"!cloningProject\">\n\t\t\t<div class=\"modal-header\">\n\t\t\t\t<h5 class=\"modal-title\"><i class=\"fa fa-plus\"></i> Create Project </h5>\n\t\t\t\t<a class=\"close pull-right\" aria-label=\"Close\" (click)=\"d('Cross click')\">\n\t\t\t\t\t<span aria-hidden=\"true\">&times;</span>\n\t\t\t\t</a>\n\t\t\t</div>\n\t\t\t<div class=\"modal-body\">\t\t\t\n\t\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!createProjectForm.controls['title'].valid}\">\n\t\t\t\t\t<small><label>Title</label></small>\n\t\t\t\t\t<input class=\"form-control\" type=\"text\" [formControl]=\"createProjectForm.controls['title']\" >\n\t\t\t\t</div>\n\t\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!createProjectForm.controls['description'].valid}\">\n\t\t\t\t\t<small><label for=\"projectTitle\">Description (Optional)</label></small>\n\t\t\t\t\t<textarea rows=\"5\" class=\"form-control\" [formControl]=\"createProjectForm.controls['description']\"></textarea>\n\t\t\t\t</div>\n\t\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!createProjectForm.controls['cloneProject'].valid}\">\n\t\t\t\t\t<div class=\"checkbox\">\n\t\t\t\t\t\t<small><label><input #someCheckbox type=\"checkbox\" [formControl]=\"createProjectForm.controls['cloneProject']\">&nbsp;Clone existing <a href=\"http://www.metabolights.org\" target=\"_blank\">Metabolights study</a></label></small>\n\t\t\t\t\t</div>\n\t\t\t\t\t<input [formControl]=\"createProjectForm.controls['studyId']\" class=\"form-control\" [attr.disabled]=\"createProjectForm.controls['cloneProject'].value === false || null\" type=\"text\" placeholder=\"Study Id. Ex: MTBLS1\">\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t\t<div class=\"modal-footer\">\n\t\t\t\t<button type=\"submit\" [disabled]=\"!createProjectForm.valid\" class=\"btn btn-primary\">Create</button>\n\t\t\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Close</button>\n\t\t\t</div>\n\t\t</div>\n\t</form>\n</ng-template>        "

/***/ }),

/***/ "../../../../../src/app/work-space/projects/projects.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_forms__ = __webpack_require__("../../../forms/@angular/forms.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__project_project__ = __webpack_require__("../../../../../src/app/work-space/project/project.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__auth_service__ = __webpack_require__("../../../../../src/app/auth.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__common_headers__ = __webpack_require__("../../../../../src/app/common/headers.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__ = __webpack_require__("../../../../@ng-bootstrap/ng-bootstrap/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__common_globals__ = __webpack_require__("../../../../../src/app/common/globals.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProjectsComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







//import { ProjectsService } from './projects.service';


var ProjectsComponent = (function () {
    function ProjectsComponent(http, authService, router, modalService, fb) {
        this.http = http;
        this.authService = authService;
        this.router = router;
        this.modalService = modalService;
        this.fb = fb;
        this.cloningProject = false;
        this.alerts = [];
        this.initForms();
    }
    ProjectsComponent.prototype.initForms = function () {
        this.createProjectForm = this.fb.group({
            'title': ["", __WEBPACK_IMPORTED_MODULE_1__angular_forms__["e" /* Validators */].required],
            'description': "",
            'cloneProject': false,
            'studyId': ""
        });
    };
    ProjectsComponent.prototype.closeAlert = function (alert) {
        var index = this.alerts.indexOf(alert);
        this.alerts.splice(index, 1);
    };
    ProjectsComponent.prototype.ngOnInit = function () {
        this.projects = this.authService.dashBoard.projects;
        this.selectedProject = this.projects[0];
    };
    ProjectsComponent.prototype.getId = function (uuid) {
        return uuid.split("-")[0];
    };
    ProjectsComponent.prototype.onSelect = function (project) {
        this.router.navigate(['/workspace/project', project.id]);
    };
    ProjectsComponent.prototype.setSelected = function (project) {
        this.selectedProject = project;
    };
    ProjectsComponent.prototype.open = function (content) {
        var _this = this;
        this.modalRef = this.modalService.open(content);
        this.modalRef.result.then(function (result) {
            _this.closeResult = "Closed with: " + result;
        }, function (reason) {
            _this.closeResult = "Dismissed " + _this.getDismissReason(reason);
        });
    };
    ProjectsComponent.prototype.openDeleteConfirmationModal = function (content) {
        var _this = this;
        this.deleteConfirmationModalRef = this.modalService.open(content);
        this.deleteConfirmationModalRef.result.then(function (result) {
            _this.closeResult = "Closed with: " + result;
        }, function (reason) {
            _this.closeResult = "Dismissed " + _this.getDismissReason(reason);
        });
    };
    ProjectsComponent.prototype.deleteSelectedProject = function () {
        var _this = this;
        var body = {};
        body["jwt"] = localStorage.getItem("jwt");
        body["user"] = localStorage.getItem("user");
        body["project_id"] = this.selectedProject.id;
        body["files"] = [];
        this.http.post(__WEBPACK_IMPORTED_MODULE_7__common_globals__["a" /* LabsURL */]['deleteProject'], body, { headers: __WEBPACK_IMPORTED_MODULE_4__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            _this.deleteConfirmationModalRef.close();
            var selectedProjectIndex = 0;
            var projectIndex = 0;
            _this.authService.dashBoard.projects.forEach(function (aProject) {
                if (aProject.id == _this.selectedProject.id) {
                    selectedProjectIndex = projectIndex;
                    return;
                }
                projectIndex = projectIndex + 1;
            });
            _this.authService.dashBoard.projects.splice(selectedProjectIndex, 1);
            _this.projects = _this.authService.dashBoard.projects;
            _this.alerts.push({
                id: 1,
                type: 'success',
                message: 'Project deleted successfully!',
            });
            _this.ngOnInit();
        }, function (error) {
            _this.alerts.push({
                id: 1,
                type: 'danger',
                message: 'Project delete unsuccessful! Error',
            });
        });
    };
    ProjectsComponent.prototype.submitForm = function (body, waiting) {
        var _this = this;
        this.waitingModalRef = this.modalService.open(waiting, { backdrop: "static" });
        if (body.cloneProject == true) {
            if (body.studyId == "" || body.studyId == null) {
                alert('Please provide a valid MetaboLights Study ID');
                this.waitingModalRef.close();
                return;
            }
            else {
                var studies_1;
                this.http.get(__WEBPACK_IMPORTED_MODULE_7__common_globals__["a" /* LabsURL */]['studiesList'], { headers: __WEBPACK_IMPORTED_MODULE_4__common_headers__["a" /* contentHeaders */] })
                    .subscribe(function (response) {
                    studies_1 = response.json().content;
                    var studyExists = false;
                    studies_1.forEach(function (study) {
                        if (study === body.studyId.toUpperCase()) {
                            studyExists = true;
                        }
                    });
                    if (studyExists) {
                        _this.cloningProject = true;
                        _this.submitCreateRequest(body);
                    }
                    else {
                        alert("Invalid study identifier. Note: Cloning is currently supported only for public studies");
                        _this.waitingModalRef.close();
                        return;
                    }
                }, function (error) {
                    console.log(error);
                });
            }
        }
        else {
            this.submitCreateRequest(body);
        }
    };
    ProjectsComponent.prototype.submitCreateRequest = function (body) {
        var _this = this;
        body["jwt"] = localStorage.getItem("jwt");
        body["user"] = localStorage.getItem("user");
        this.http.post(__WEBPACK_IMPORTED_MODULE_7__common_globals__["a" /* LabsURL */]['createProject'], body, { headers: __WEBPACK_IMPORTED_MODULE_4__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            _this.cloningProject = false;
            var project = new __WEBPACK_IMPORTED_MODULE_2__project_project__["a" /* Project */]().deserialize(JSON.parse(response.json().content));
            _this.setSelected(project);
            _this.authService.dashBoard.projects.push(project);
            _this.projects = _this.authService.dashBoard.projects;
            _this.alerts.push({
                id: 1,
                type: 'success',
                message: 'Project ' + project.title + ' creation successful!',
            });
            _this.initForms();
            _this.modalRef.close();
            _this.waitingModalRef.close();
        }, function (error) {
            _this.alerts.push({
                id: 1,
                type: 'danger',
                message: 'Project creation unsuccessful! Error',
            });
            console.log(error.text());
        });
    };
    ProjectsComponent.prototype.getDismissReason = function (reason) {
        if (reason === __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__["d" /* ModalDismissReasons */].ESC) {
            return 'by pressing ESC';
        }
        else if (reason === __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__["d" /* ModalDismissReasons */].BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        }
        else {
            return "with: " + reason;
        }
    };
    return ProjectsComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", Object)
], ProjectsComponent.prototype, "alerts", void 0);
ProjectsComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'app-projects',
        template: __webpack_require__("../../../../../src/app/work-space/projects/projects.component.html"),
        styles: [__webpack_require__("../../../../../src/app/work-space/projects/projects.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_8__angular_http__["c" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_8__angular_http__["c" /* Http */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__auth_service__["a" /* AuthService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_5__angular_router__["b" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5__angular_router__["b" /* Router */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__["e" /* NgbModal */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__["e" /* NgbModal */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_1__angular_forms__["f" /* FormBuilder */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_forms__["f" /* FormBuilder */]) === "function" && _e || Object])
], ProjectsComponent);

var _a, _b, _c, _d, _e;
//# sourceMappingURL=projects.component.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/projects/projects.service.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return ProjectsService; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ProjectsService = (function () {
    function ProjectsService(router, http) {
        this.router = router;
        this.http = http;
    }
    ProjectsService.prototype.getProjects = function (body) {
    };
    return ProjectsService;
}());
ProjectsService = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["c" /* Injectable */])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__angular_http__["c" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_http__["c" /* Http */]) === "function" && _b || Object])
], ProjectsService);

var _a, _b;
//# sourceMappingURL=projects.service.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/settings/settings.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".div-wrapper{\n\tdisplay: -webkit-box;\n\tdisplay: -ms-flexbox;\n\tdisplay: flex;\n\t-webkit-box-align: center;\n\t    -ms-flex-align: center;\n\t        align-items: center;\n  \t-webkit-box-pack: center;\n  \t    -ms-flex-pack: center;\n  \t        justify-content: center;\n\tmin-height: 50vh;\n\tpadding: 40px 40px;\n\t-webkit-box-orient: vertical;\n\t-webkit-box-direction: normal;\n\t    -ms-flex-direction: column;\n\t        flex-direction: column;\n\ttext-align: center;\n}\n\n.pb10{\n\tpadding-bottom: 10px;\n}\n\n.vh80{\n\tmin-height: 80vh !important;\n}\n\n.div-wrapper .spinner > h5{\n\tfont-weight: normal;\n\tletter-spacing: 1px;\n}\n\n.div-wrapper .spinner > small{\n\tfont-weight: lighter;\n}\n\n.spinner{\n\tposition: relative !important;\n    top: 0%;\n    left: 0%;\n    margin-top: 0px; \n    margin-left: 0px;\n    text-align: center;\n}\n\nul {\n  list-style-type: none;\n}\n\nul li{\n  padding: 5px 0;\n}\n\n.npt{\n\tpadding-top: 0;\n}\n\n.npl{\n\tpadding-left: 5px;\n}\n\n.logFileData{\n\toverflow-x: scroll;\n}\n\n.jcc{\n    -webkit-box-pack: center !important;\n        -ms-flex-pack: center !important;\n            justify-content: center !important;\n}\n\n.section-title{\n\tfloat: right;\n\tfont-size: 0.6em;\n\tcolor: #d8d8d8;\n\tmargin-right: 5px;\n\tborder: 1px solid #d8d8d8;\n\tborder-radius: 2px;\n\tpadding: 2px 4px;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/work-space/settings/settings.component.html":
/***/ (function(module, exports) {

module.exports = "<div class=\"content\">\n\t<div class=\"mini-wrapper\">\n\t\t<div class=\"col-md-12\">\n\t\t\t<div class=\"row\">\n\t\t\t\t<div class=\"col-md-6\">\n\t\t\t\t\t<div class=\"section\">\n\t\t\t\t\t\t<h3 class=\"title\">Personal details</h3>\n\t\t\t\t\t\t<br>\n\t\t\t\t\t\t<div class=\"list-group\">\n\t\t\t\t\t\t  <a class=\"list-group-item list-group-item-action flex-column align-items-start\">\n\t\t\t\t\t\t    <div class=\"d-flex w-100 justify-content-between\">\n\t\t\t\t\t\t      <small>First Name</small>\n\t\t\t\t\t\t      <h5 class=\"mb-1\">{{ user.firstName }}</h5>\n\t\t\t\t\t\t    </div>\n\t\t\t\t\t\t  </a>\n\t\t\t\t\t\t  <a class=\"list-group-item list-group-item-action flex-column align-items-start\">\n\t\t\t\t\t\t    <div class=\"d-flex w-100 justify-content-between\">\n\t\t\t\t\t\t      <small>Last Name</small>\n\t\t\t\t\t\t      <h5 class=\"mb-1\">{{ user.lastName }}</h5>\n\t\t\t\t\t\t    </div>\n\t\t\t\t\t\t  </a>\n\t\t\t\t\t\t  <a class=\"list-group-item list-group-item-action flex-column align-items-start\">\n\t\t\t\t\t\t    <div class=\"d-flex w-100 justify-content-between\">\n\t\t\t\t\t\t      <small>Email</small>\n\t\t\t\t\t\t      <h5 class=\"mb-1\">{{ user.email }}</h5>\n\t\t\t\t\t\t    </div>\n\t\t\t\t\t\t  </a>\n\t\t\t\t\t\t  <a class=\"list-group-item list-group-item-action flex-column align-items-start\">\n\t\t\t\t\t\t    <div class=\"d-flex w-100 justify-content-between\">\n\t\t\t\t\t\t      <small>Address</small>\n\t\t\t\t\t\t      <h5 class=\"mb-1\">{{ user.address }}</h5>\n\t\t\t\t\t\t    </div>\n\t\t\t\t\t\t  </a>\n\t\t\t\t\t\t  <a class=\"list-group-item list-group-item-action flex-column align-items-start\">\n\t\t\t\t\t\t    <div class=\"d-flex w-100 justify-content-between\">\n\t\t\t\t\t\t      <small>Affiliation</small>\n\t\t\t\t\t\t      <h5 class=\"mb-1\">{{ user.affiliation }}</h5>\n\t\t\t\t\t\t    </div>\n\t\t\t\t\t\t  </a>\n\t\t\t\t\t\t  <a class=\"list-group-item list-group-item-action flex-column align-items-start\">\n\t\t\t\t\t\t    <div class=\"d-flex w-100 justify-content-between\">\n\t\t\t\t\t\t      <small>ORCID Id</small>\n\t\t\t\t\t\t      <h5 class=\"mb-1\">{{user.orcid ? user.orcid : '-'}}</h5>\n\t\t\t\t\t\t    </div>\n\t\t\t\t\t\t  </a>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t\t<div class=\"col-md-6\">\n\t\t\t\t\t<div class=\"section\">\n\t\t\t\t\t\t<h3 class=\"title\">Galaxy Workflow Settings\n\t\t\t\t\t\t\t<span class=\"pull-right\">\n\t\t\t\t\t\t\t\t<button (click)=\"openGalaxyModal(addGalaxyInstance)\" type=\"button\" class=\"btn btn-success btn-sm\"> <i class=\"fa fa-plus\"></i> Add</button>\n\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t</h3>\n\t\t\t\t\t\t<br>\n\t\t\t\t\t\t<div *ngIf=\"settings.galaxy == undefined || galaxyInstances.length == 0;else instancesExist\" class=\"text-center\">\n\t\t\t\t\t\t\t<small class=\"text-muted\">\n\t\t\t\t\t\t\t\tGet started now with workflows. Add your Galaxy instance <span class=\"text-primary\" (click)=\"openGalaxyModal(addGalaxyInstance)\">here</span>\n\t\t\t\t\t\t\t</small>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<ng-template #instancesExist>\n\t\t\t\t\t\t\t<li class=\"list-group pb10\" *ngFor=\"let instance of galaxyInstances\">\n\t\t\t\t\t\t\t\t<a class=\"list-group-item\">\n\t\t\t\t\t\t\t\t\t<span class=\"list-group-item-text\">\n\t\t\t\t\t\t\t\t\t\t<h4 class=\"list-group-item-heading\">{{ instance.name }} \n\t\t\t\t\t\t\t\t\t\t\t<span>\n\t\t\t\t\t\t\t\t\t\t\t\t<small>&nbsp;\n\t\t\t\t\t\t\t\t\t\t\t\t\t<a (click)=\"openEditGalaxyModal(editGalaxyInstance, instance)\"><i class=\"fa fa-pencil text-primary\"></i></a>\n\t\t\t\t\t\t\t\t\t\t\t\t\t<a (click)=\"openDeleteGalaxyModal(deleteGalaxyInstance, instance)\"><i class=\"fa fa-trash text-danger\"></i></a>\n\t\t\t\t\t\t\t\t\t\t\t\t</small>\n\t\t\t\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t\t\t</h4>\n\t\t\t\t\t\t\t\t\t\t<small>\n\t\t\t\t\t\t\t\t\t\t\t<b>URL:</b> {{ instance.url }}\n\t\t\t\t\t\t\t\t\t\t</small>\n\t\t\t\t\t\t\t\t\t\t<br>\n\t\t\t\t\t\t\t\t\t\t<small>\n\t\t\t\t\t\t\t\t\t\t\t<b>API Key:</b> {{ instance.apikey }}\n\t\t\t\t\t\t\t\t\t\t</small>\n\t\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t</li>\n\t\t\t\t\t\t</ng-template>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</div>     \n<ng-template #addGalaxyInstance let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<form [formGroup]=\"addGalaxyDetailsForm\" (ngSubmit)=\"submitForm(addGalaxyDetailsForm.value)\">\n\t\t<div class=\"modal-header\">\n\t\t\t<h5 class=\"modal-title\"><i class=\"fa fa-plus\"></i>&emsp;Galaxy details </h5>\n\t\t\t<a class=\"close pull-right\" aria-label=\"Close\" (click)=\"d('Cross click')\">\n\t\t\t\t<span aria-hidden=\"true\">&times;</span>\n\t\t\t</a>\n\t\t</div>\n\t\t<div class=\"modal-body\">\n\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!addGalaxyDetailsForm.controls['title'].valid}\">\n\t\t\t\t<small><label>Name*</label></small>\n\t\t\t\t<input class=\"form-control\" type=\"text\" [formControl]=\"addGalaxyDetailsForm.controls['title']\" >\n\t\t\t</div>\n\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!addGalaxyDetailsForm.controls['url'].valid}\">\n\t\t\t\t<small><label>URL*</label></small>\n\t\t\t\t<input class=\"form-control\" type=\"text\" [formControl]=\"addGalaxyDetailsForm.controls['url']\" >\n\t\t\t</div>\n\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!addGalaxyDetailsForm.controls['apikey'].valid}\">\n\t\t\t\t<small><label>API Key*</label></small>\n\t\t\t\t<input class=\"form-control\" type=\"text\" [formControl]=\"addGalaxyDetailsForm.controls['apikey']\" >\n\t\t\t</div>\n\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!addGalaxyDetailsForm.controls['description'].valid}\">\n\t\t\t\t<small><label for=\"projectTitle\">Description (Optional)</label></small>\n\t\t\t\t<textarea rows=\"5\" class=\"form-control\" [formControl]=\"addGalaxyDetailsForm.controls['description']\"></textarea>\n\t\t\t</div>\n\t\t\t<div class=\"form-group\">\n\t\t\t\t<small><i>* required</i></small>\n\t\t\t\t<span *ngIf=\"addGalaxyDetailsForm.error\">\n\t\t\t\t\t{{ addGalaxyDetailsForm.error }}\n\t\t\t\t</span>\n\t\t\t</div>\n\t\t</div>\n\t\t<div class=\"modal-footer\">\n\t\t\t<button type=\"submit\" [disabled]=\"!addGalaxyDetailsForm.valid\" class=\"btn btn-primary\">Add</button>\n\t\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Close</button>\n\t\t</div>\n\t</form>\n</ng-template> \n<ng-template #editGalaxyInstance let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<form [formGroup]=\"addGalaxyDetailsForm\" (ngSubmit)=\"submitForm(addGalaxyDetailsForm.value)\">\n\t\t<div class=\"modal-header\">\n\t\t\t<h5 class=\"modal-title\"><i class=\"fa fa-pencil\"></i>&emsp;Edit Galaxy details </h5>\n\t\t\t<a class=\"close pull-right\" aria-label=\"Close\" (click)=\"d('Cross click')\">\n\t\t\t\t<span aria-hidden=\"true\">&times;</span>\n\t\t\t</a>\n\t\t</div>\n\t\t<div class=\"modal-body\">\n\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!addGalaxyDetailsForm.controls['title'].valid}\">\n\t\t\t\t<small><label>Name*</label></small>\n\t\t\t\t<input class=\"form-control\" type=\"text\" [formControl]=\"addGalaxyDetailsForm.controls['title']\" >\n\t\t\t</div>\n\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!addGalaxyDetailsForm.controls['url'].valid}\">\n\t\t\t\t<small><label>URL*</label></small>\n\t\t\t\t<input [attr.disabled]=\"''\" class=\"form-control\" type=\"text\" [formControl]=\"addGalaxyDetailsForm.controls['url']\" >\n\t\t\t</div>\n\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!addGalaxyDetailsForm.controls['apikey'].valid}\">\n\t\t\t\t<small><label>API Key*</label></small>\n\t\t\t\t<input class=\"form-control\" type=\"text\" [formControl]=\"addGalaxyDetailsForm.controls['apikey']\" >\n\t\t\t</div>\n\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!addGalaxyDetailsForm.controls['description'].valid}\">\n\t\t\t\t<small><label for=\"projectTitle\">Description (Optional)</label></small>\n\t\t\t\t<textarea rows=\"5\" class=\"form-control\" [formControl]=\"addGalaxyDetailsForm.controls['description']\"></textarea>\n\t\t\t</div>\n\t\t\t<div class=\"form-group\">\n\t\t\t\t<small><i>* required</i></small>\n\t\t\t\t<span *ngIf=\"addGalaxyDetailsForm.error\">\n\t\t\t\t\t{{ addGalaxyDetailsForm.error }}\n\t\t\t\t</span>\n\t\t\t</div>\n\t\t</div>\n\t\t<div class=\"modal-footer\">\n\t\t\t<button type=\"submit\" [disabled]=\"!addGalaxyDetailsForm.valid\" class=\"btn btn-primary\">Update</button>\n\t\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Close</button>\n\t\t</div>\n\t</form>\n</ng-template> \n<ng-template #deleteGalaxyInstance let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<form [formGroup]=\"addGalaxyDetailsForm\" (ngSubmit)=\"submitDeleteForm(addGalaxyDetailsForm.value)\">\n\t\t<div class=\"modal-header\">\n\t\t\t<h5 class=\"modal-title\"><i class=\"text-danger fa fa-trash\"></i>&emsp;Delete Galaxy Instance</h5>\n\t\t\t<a class=\"close pull-right\" aria-label=\"Close\" (click)=\"d('Cross click')\">\n\t\t\t\t<span aria-hidden=\"true\">&times;</span>\n\t\t\t</a>\n\t\t</div>\n\t\t<div class=\"modal-body\">\n\t\t\t<div class=\"div-wrapper\">\n\t\t\t\t<img src=\"assets/img/trash.png\" height=\"128\" class=\"logo\"><br><br>\n\t\t\t\t<div class=\"spinner\">\n\t\t\t\t\t<h5 class=\"text-center\">\n\t\t\t\t\t\tAre you sure you want to delete this galaxy instance ? \n\t\t\t\t\t</h5>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<div class=\"modal-footer\">\n\t\t\t<button type=\"submit\" [disabled]=\"!addGalaxyDetailsForm.valid\" class=\"btn btn-primary\">Delete!</button>\n\t\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Close</button>\n\t\t</div>\n\t</form>\n</ng-template>   "

/***/ }),

/***/ "../../../../../src/app/work-space/settings/settings.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__auth_service__ = __webpack_require__("../../../../../src/app/auth.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__("../../../forms/@angular/forms.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__common_headers__ = __webpack_require__("../../../../../src/app/common/headers.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_http__ = __webpack_require__("../../../http/@angular/http.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__common_globals__ = __webpack_require__("../../../../../src/app/common/globals.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__ = __webpack_require__("../../../../@ng-bootstrap/ng-bootstrap/index.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return SettingsComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var SettingsComponent = (function () {
    function SettingsComponent(authService, modalService, http, fb) {
        this.authService = authService;
        this.modalService = modalService;
        this.http = http;
        this.fb = fb;
    }
    SettingsComponent.prototype.ngOnInit = function () {
        this.user = this.authService.dashBoard.user;
        this.settings = this.authService.dashBoard.settings;
        if (this.settings['galaxy']) {
            this.galaxyInstances = JSON.parse(this.settings['galaxy']);
        }
        this.addGalaxyDetailsForm = this.fb.group({
            'title': ["", __WEBPACK_IMPORTED_MODULE_2__angular_forms__["e" /* Validators */].required],
            'url': ["", __WEBPACK_IMPORTED_MODULE_2__angular_forms__["e" /* Validators */].required],
            'apikey': ["", __WEBPACK_IMPORTED_MODULE_2__angular_forms__["e" /* Validators */].required],
            'description': "",
            'error': ''
        });
    };
    SettingsComponent.prototype.openGalaxyModal = function (content) {
        var _this = this;
        this.addGalaxyModalRef = this.modalService.open(content);
        this.addGalaxyModalRef.result.then(function (result) {
            _this.closeResult = "Closed with: " + result;
        }, function (reason) {
            _this.closeResult = "Dismissed " + _this.getDismissReason(reason);
        });
    };
    SettingsComponent.prototype.openEditGalaxyModal = function (content, galaxyDetails) {
        var _this = this;
        this.addGalaxyDetailsForm = this.fb.group({
            'title': [galaxyDetails.name, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["e" /* Validators */].required],
            'url': [galaxyDetails.url, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["e" /* Validators */].required],
            'apikey': [galaxyDetails.apikey, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["e" /* Validators */].required],
            'description': galaxyDetails.description
        });
        this.editGalaxyModalRef = this.modalService.open(content);
        this.editGalaxyModalRef.result.then(function (result) {
            _this.closeResult = "Closed with: " + result;
        }, function (reason) {
            _this.closeResult = "Dismissed " + _this.getDismissReason(reason);
        });
    };
    SettingsComponent.prototype.openDeleteGalaxyModal = function (content, galaxyDetails) {
        var _this = this;
        this.addGalaxyDetailsForm = this.fb.group({
            'title': [galaxyDetails.name, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["e" /* Validators */].required],
            'url': [galaxyDetails.url, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["e" /* Validators */].required],
            'apikey': [galaxyDetails.apikey, __WEBPACK_IMPORTED_MODULE_2__angular_forms__["e" /* Validators */].required],
            'description': galaxyDetails.description
        });
        this.deleteGalaxyModalRef = this.modalService.open(content);
        this.deleteGalaxyModalRef.result.then(function (result) {
            _this.closeResult = "Closed with: " + result;
        }, function (reason) {
            _this.closeResult = "Dismissed " + _this.getDismissReason(reason);
        });
    };
    SettingsComponent.prototype.submitForm = function (data) {
        var _this = this;
        var body = {};
        body["jwt"] = localStorage.getItem("jwt");
        body["user"] = localStorage.getItem("user");
        body["title"] = data.title;
        body["url"] = data.url;
        body["apikey"] = data.apikey;
        body["description"] = data.description;
        body["property"] = "galaxy";
        this.http.post(__WEBPACK_IMPORTED_MODULE_5__common_globals__["a" /* LabsURL */]['settings'], body, { headers: __WEBPACK_IMPORTED_MODULE_3__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            _this.authService.initializeWorkSpace().subscribe(function (data) {
                _this.user = _this.authService.dashBoard.user;
                _this.settings = _this.authService.dashBoard.settings;
                _this.galaxyInstances = JSON.parse(_this.settings['galaxy']);
            });
            if (_this.addGalaxyModalRef) {
                _this.addGalaxyModalRef.close();
            }
            if (_this.editGalaxyModalRef) {
                _this.editGalaxyModalRef.close();
            }
        }, function (error) {
        });
    };
    SettingsComponent.prototype.submitDeleteForm = function (data) {
        var _this = this;
        var body = {};
        body["jwt"] = localStorage.getItem("jwt");
        body["user"] = localStorage.getItem("user");
        body["url"] = data.url;
        body["delete"] = "deleteGalaxyInstance";
        body["property"] = "galaxy";
        this.http.post(__WEBPACK_IMPORTED_MODULE_5__common_globals__["a" /* LabsURL */]['settings'], body, { headers: __WEBPACK_IMPORTED_MODULE_3__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            _this.authService.initializeWorkSpace().subscribe(function (data) {
                _this.user = _this.authService.dashBoard.user;
                _this.settings = _this.authService.dashBoard.settings;
                _this.galaxyInstances = JSON.parse(_this.settings['galaxy']);
            });
            if (_this.deleteGalaxyModalRef) {
                _this.deleteGalaxyModalRef.close();
            }
        }, function (error) {
        });
    };
    SettingsComponent.prototype.getDismissReason = function (reason) {
        if (reason === __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__["d" /* ModalDismissReasons */].ESC) {
            return 'by pressing ESC';
        }
        else if (reason === __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__["d" /* ModalDismissReasons */].BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        }
        else {
            return "with: " + reason;
        }
    };
    return SettingsComponent;
}());
SettingsComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'app-settings',
        template: __webpack_require__("../../../../../src/app/work-space/settings/settings.component.html"),
        styles: [__webpack_require__("../../../../../src/app/work-space/settings/settings.component.css")]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__["e" /* NgbModal */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__["e" /* NgbModal */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_4__angular_http__["c" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4__angular_http__["c" /* Http */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* FormBuilder */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_forms__["f" /* FormBuilder */]) === "function" && _d || Object])
], SettingsComponent);

var _a, _b, _c, _d;
//# sourceMappingURL=settings.component.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/work-space.component.css":
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__("../../../../css-loader/lib/css-base.js")(false);
// imports


// module
exports.push([module.i, ".main-container{\n    min-height: 100vh - 60px;\n    margin: 50px 0 0 225px;\n    -ms-overflow-x: hidden;\n        overflow-x: hidden;\n    position: relative;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ "../../../../../src/app/work-space/work-space.component.html":
/***/ (function(module, exports) {

module.exports = "<top-nav></top-nav>\n<side-nav></side-nav>\n<section class=\"main-container\">\t\n\t<router-outlet></router-outlet>\n</section>\n"

/***/ }),

/***/ "../../../../../src/app/work-space/work-space.component.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return WorkSpaceComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var WorkSpaceComponent = (function () {
    function WorkSpaceComponent() {
    }
    WorkSpaceComponent.prototype.ngOnInit = function () {
    };
    return WorkSpaceComponent;
}());
WorkSpaceComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'app-work-space',
        template: __webpack_require__("../../../../../src/app/work-space/work-space.component.html"),
        styles: [__webpack_require__("../../../../../src/app/work-space/work-space.component.css")]
    }),
    __metadata("design:paramtypes", [])
], WorkSpaceComponent);

//# sourceMappingURL=work-space.component.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/work-space.module.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__ng_bootstrap_ng_bootstrap__ = __webpack_require__("../../../../@ng-bootstrap/ng-bootstrap/index.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_common__ = __webpack_require__("../../../common/@angular/common.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_forms__ = __webpack_require__("../../../forms/@angular/forms.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__work_space_component__ = __webpack_require__("../../../../../src/app/work-space/work-space.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__dashboard_dashboard_component__ = __webpack_require__("../../../../../src/app/work-space/dashboard/dashboard.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__project_project_component__ = __webpack_require__("../../../../../src/app/work-space/project/project.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__project_tree_view_component__ = __webpack_require__("../../../../../src/app/work-space/project/tree-view.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__angular_router__ = __webpack_require__("../../../router/@angular/router.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__shared_shared_module__ = __webpack_require__("../../../../../src/app/shared/shared.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__work_space_routes__ = __webpack_require__("../../../../../src/app/work-space/work-space.routes.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__settings_settings_component__ = __webpack_require__("../../../../../src/app/work-space/settings/settings.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__auth_service__ = __webpack_require__("../../../../../src/app/auth.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__projects_projects_service__ = __webpack_require__("../../../../../src/app/work-space/projects/projects.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__dashboard_dashboard_resolve__ = __webpack_require__("../../../../../src/app/work-space/dashboard/dashboard.resolve.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_15__projects_projects_component__ = __webpack_require__("../../../../../src/app/work-space/projects/projects.component.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return WorkSpaceModule; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};








// Router import








var WorkSpaceModule = (function () {
    function WorkSpaceModule() {
    }
    return WorkSpaceModule;
}());
WorkSpaceModule = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["b" /* NgModule */])({
        imports: [
            __WEBPACK_IMPORTED_MODULE_2__angular_common__["d" /* CommonModule */],
            __WEBPACK_IMPORTED_MODULE_9__shared_shared_module__["a" /* SharedModule */],
            __WEBPACK_IMPORTED_MODULE_1__ng_bootstrap_ng_bootstrap__["a" /* NgbModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_forms__["a" /* FormsModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_forms__["b" /* ReactiveFormsModule */],
            __WEBPACK_IMPORTED_MODULE_8__angular_router__["a" /* RouterModule */].forChild(__WEBPACK_IMPORTED_MODULE_10__work_space_routes__["a" /* WorkSpaceRoutes */])
        ],
        declarations: [__WEBPACK_IMPORTED_MODULE_4__work_space_component__["a" /* WorkSpaceComponent */], __WEBPACK_IMPORTED_MODULE_5__dashboard_dashboard_component__["a" /* DashboardComponent */], __WEBPACK_IMPORTED_MODULE_6__project_project_component__["a" /* ProjectComponent */], __WEBPACK_IMPORTED_MODULE_11__settings_settings_component__["a" /* SettingsComponent */], __WEBPACK_IMPORTED_MODULE_15__projects_projects_component__["a" /* ProjectsComponent */], __WEBPACK_IMPORTED_MODULE_7__project_tree_view_component__["a" /* TreeView */]],
        exports: [
            __WEBPACK_IMPORTED_MODULE_8__angular_router__["a" /* RouterModule */]
        ],
        providers: [
            __WEBPACK_IMPORTED_MODULE_12__auth_service__["a" /* AuthService */],
            __WEBPACK_IMPORTED_MODULE_13__projects_projects_service__["a" /* ProjectsService */],
            __WEBPACK_IMPORTED_MODULE_1__ng_bootstrap_ng_bootstrap__["b" /* NgbActiveModal */],
            __WEBPACK_IMPORTED_MODULE_1__ng_bootstrap_ng_bootstrap__["c" /* NgbAlertConfig */],
            __WEBPACK_IMPORTED_MODULE_14__dashboard_dashboard_resolve__["a" /* DashboardResolve */]
        ]
    })
], WorkSpaceModule);

//# sourceMappingURL=work-space.module.js.map

/***/ }),

/***/ "../../../../../src/app/work-space/work-space.routes.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__work_space_component__ = __webpack_require__("../../../../../src/app/work-space/work-space.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__dashboard_dashboard_component__ = __webpack_require__("../../../../../src/app/work-space/dashboard/dashboard.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__project_project_component__ = __webpack_require__("../../../../../src/app/work-space/project/project.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__projects_projects_component__ = __webpack_require__("../../../../../src/app/work-space/projects/projects.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__settings_settings_component__ = __webpack_require__("../../../../../src/app/work-space/settings/settings.component.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__auth_guard_service__ = __webpack_require__("../../../../../src/app/auth-guard.service.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__dashboard_dashboard_resolve__ = __webpack_require__("../../../../../src/app/work-space/dashboard/dashboard.resolve.ts");
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return WorkSpaceRoutes; });







var WorkSpaceRoutes = [
    {
        path: 'workspace',
        component: __WEBPACK_IMPORTED_MODULE_0__work_space_component__["a" /* WorkSpaceComponent */],
        canActivate: [__WEBPACK_IMPORTED_MODULE_5__auth_guard_service__["a" /* AuthGuard */]],
        children: [
            {
                path: 'dashboard',
                component: __WEBPACK_IMPORTED_MODULE_1__dashboard_dashboard_component__["a" /* DashboardComponent */],
                resolve: {
                    dashBoard: __WEBPACK_IMPORTED_MODULE_6__dashboard_dashboard_resolve__["a" /* DashboardResolve */]
                }
            },
            {
                path: 'projects',
                component: __WEBPACK_IMPORTED_MODULE_3__projects_projects_component__["a" /* ProjectsComponent */],
                resolve: {
                    dashBoard: __WEBPACK_IMPORTED_MODULE_6__dashboard_dashboard_resolve__["a" /* DashboardResolve */]
                }
            },
            {
                path: 'settings',
                component: __WEBPACK_IMPORTED_MODULE_4__settings_settings_component__["a" /* SettingsComponent */],
                resolve: {
                    dashBoard: __WEBPACK_IMPORTED_MODULE_6__dashboard_dashboard_resolve__["a" /* DashboardResolve */]
                }
            },
            {
                path: 'project/:id',
                component: __WEBPACK_IMPORTED_MODULE_2__project_project_component__["a" /* ProjectComponent */],
                resolve: {
                    dashBoard: __WEBPACK_IMPORTED_MODULE_6__dashboard_dashboard_resolve__["a" /* DashboardResolve */]
                }
            },
            {
                path: '',
                component: __WEBPACK_IMPORTED_MODULE_1__dashboard_dashboard_component__["a" /* DashboardComponent */],
            }
        ]
    }
];
//# sourceMappingURL=work-space.routes.js.map

/***/ }),

/***/ "../../../../../src/environments/environment.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
// The file contents for the current environment will overwrite these during build.
// The build system defaults to the dev environment which uses `environment.ts`, but if you do
// `ng build --env=prod` then `environment.prod.ts` will be used instead.
// The list of which env maps to which file can be found in `.angular-cli.json`.
// The file contents for the current environment will overwrite these during build.
var environment = {
    production: false
};
//# sourceMappingURL=environment.js.map

/***/ }),

/***/ "../../../../../src/main.ts":
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__("../../../core/@angular/core.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__("../../../platform-browser-dynamic/@angular/platform-browser-dynamic.es5.js");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__("../../../../../src/app/app.module.ts");
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__("../../../../../src/environments/environment.ts");




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["a" /* enableProdMode */])();
}
__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */]);
//# sourceMappingURL=main.js.map

/***/ }),

/***/ 1:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__("../../../../../src/main.ts");


/***/ })

},[1]);
//# sourceMappingURL=main.bundle.js.map