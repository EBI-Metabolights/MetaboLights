webpackJsonp([1,5],{

/***/ 12:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__(11);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_http__ = __webpack_require__(22);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map__ = __webpack_require__(286);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_3_rxjs_add_operator_map__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__common_globals__ = __webpack_require__(62);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__common_headers__ = __webpack_require__(63);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__work_space_dashboard_dashboard__ = __webpack_require__(191);
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
        this.http.post(__WEBPACK_IMPORTED_MODULE_4__common_globals__["a" /* LabsURL */]['authenticate'], body, { headers: __WEBPACK_IMPORTED_MODULE_5__common_headers__["a" /* contentHeaders */] })
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
        return this.http.post(__WEBPACK_IMPORTED_MODULE_4__common_globals__["a" /* LabsURL */]['initialise'], body, { headers: __WEBPACK_IMPORTED_MODULE_5__common_headers__["a" /* contentHeaders */] })
            .map(function (response) {
            var body = JSON.parse(response.json().content);
            var dashBoard = new __WEBPACK_IMPORTED_MODULE_6__work_space_dashboard_dashboard__["a" /* Dashboard */]().deserialize(body);
            _this.dashBoard = dashBoard;
            return dashBoard;
        }, function (error) {
            alert(error.text());
            console.log(error.text());
        });
    };
    return AuthService;
}());
AuthService = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["c" /* Injectable */])(),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__angular_http__["b" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_http__["b" /* Http */]) === "function" && _b || Object])
], AuthService);

var _a, _b;
//# sourceMappingURL=auth.service.js.map

/***/ }),

/***/ 140:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__(11);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__auth_service__ = __webpack_require__(12);
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
        console.log(url);
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

/***/ 141:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__auth_service__ = __webpack_require__(12);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_router__ = __webpack_require__(11);
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
        template: __webpack_require__(270),
        styles: [__webpack_require__(253)]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_router__["b" /* Router */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */]) === "function" && _b || Object])
], LoginComponent);

var _a, _b;
//# sourceMappingURL=login.component.js.map

/***/ }),

/***/ 142:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
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
    function PageNotFoundComponent() {
    }
    PageNotFoundComponent.prototype.ngOnInit = function () {
    };
    return PageNotFoundComponent;
}());
PageNotFoundComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'app-page-not-found',
        template: __webpack_require__(271),
        styles: [__webpack_require__(254)]
    }),
    __metadata("design:paramtypes", [])
], PageNotFoundComponent);

//# sourceMappingURL=page-not-found.component.js.map

/***/ }),

/***/ 143:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__(11);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_common__ = __webpack_require__(4);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__top_nav_top_nav_component__ = __webpack_require__(190);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__side_nav_side_nav_component__ = __webpack_require__(189);
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
            __WEBPACK_IMPORTED_MODULE_2__angular_common__["c" /* CommonModule */],
            __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* RouterModule */]
        ],
        declarations: [__WEBPACK_IMPORTED_MODULE_3__top_nav_top_nav_component__["a" /* TopNavComponent */], __WEBPACK_IMPORTED_MODULE_4__side_nav_side_nav_component__["a" /* SideNavComponent */]],
        exports: [__WEBPACK_IMPORTED_MODULE_3__top_nav_top_nav_component__["a" /* TopNavComponent */], __WEBPACK_IMPORTED_MODULE_4__side_nav_side_nav_component__["a" /* SideNavComponent */], __WEBPACK_IMPORTED_MODULE_1__angular_router__["a" /* RouterModule */]]
    })
], SharedModule);

//# sourceMappingURL=shared.module.js.map

/***/ }),

/***/ 144:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__(11);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__auth_service__ = __webpack_require__(12);
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
        template: __webpack_require__(274),
        styles: [__webpack_require__(257)]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_2__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__auth_service__["a" /* AuthService */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* ActivatedRoute */]) === "function" && _b || Object])
], DashboardComponent);

var _a, _b;
//# sourceMappingURL=dashboard.component.js.map

/***/ }),

/***/ 145:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__auth_service__ = __webpack_require__(12);
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

/***/ 146:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__(11);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__auth_service__ = __webpack_require__(12);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__project_project__ = __webpack_require__(64);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__angular_forms__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__common_headers__ = __webpack_require__(63);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__common_globals__ = __webpack_require__(62);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__angular_http__ = __webpack_require__(22);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__ng_bootstrap_ng_bootstrap__ = __webpack_require__(35);
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
    function ProjectComponent(authService, route, router, modalService, http, fb) {
        this.authService = authService;
        this.route = route;
        this.router = router;
        this.modalService = modalService;
        this.http = http;
        this.fb = fb;
        this.files = [];
        this.alerts = [];
        this.MIN_CONNECT_VERSION = "3.6.0.0";
        this.CONNECT_AUTOINSTALL_LOCATION = "//d3gcli72yxqn2z.cloudfront.net/connect/v4";
        this.selectedFiles = [];
    }
    ProjectComponent.prototype.closeAlert = function (alert) {
        var index = this.alerts.indexOf(alert);
        this.alerts.splice(index, 1);
    };
    ProjectComponent.prototype.initForms = function () {
        this.editProjectDetailsForm = this.fb.group({
            'title': [this.project.title, __WEBPACK_IMPORTED_MODULE_4__angular_forms__["d" /* Validators */].required],
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
    ProjectComponent.prototype.selectAllFiles = function (e) {
        if (e.target.checked) {
            this.selectedFiles = this.files;
        }
        else {
            this.selectedFiles = [];
        }
    };
    ProjectComponent.prototype.deleteSelectedFiles = function () {
        var _this = this;
        if (this.selectedFiles.length <= 0) {
            alert("No selection provided");
        }
        var body = {};
        body["jwt"] = localStorage.getItem("jwt");
        body["user"] = localStorage.getItem("user");
        body["id"] = this.project.id;
        body["files"] = this.selectedFiles;
        this.http.post(__WEBPACK_IMPORTED_MODULE_6__common_globals__["a" /* LabsURL */]['delete'], body, { headers: __WEBPACK_IMPORTED_MODULE_5__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            _this.getProjectContent(_this.id);
            _this.selectedFiles = [];
            _this.deleteConfirmationModalRef.close();
            _this.alerts.push({
                id: 1,
                type: 'success',
                message: 'File(s) delete successful!',
            });
        }, function (error) {
            _this.alerts.push({
                id: 1,
                type: 'danger',
                message: 'Project update unsuccessful! Error',
            });
        });
    };
    ProjectComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.route.params.forEach(function (params) {
            _this.id = params['id'];
        });
        for (var i in this.authService.dashBoard.projects) {
            if (this.authService.dashBoard.projects[i].id == this.id) {
                this.project = this.authService.dashBoard.projects[i];
                this.initForms();
                this.token = (JSON.parse(this.project.asperaSettings).asperaURL).split("/")[0];
                this.projectIndex = i;
                break;
            }
        }
        if (this.project.isBusy) {
            this.openLoadingModal(this.projectLocked);
        }
        this.getProjectDetails();
        this.getProjectContent(this.id);
        $(function () {
            $('[data-toggle="tooltip"]').tooltip();
        });
    };
    ProjectComponent.prototype.submitForm = function (body) {
        var _this = this;
        body["jwt"] = localStorage.getItem("jwt");
        body["user"] = localStorage.getItem("user");
        body["id"] = this.project.id;
        this.http.post(__WEBPACK_IMPORTED_MODULE_6__common_globals__["a" /* LabsURL */]['editProjectDetails'], body, { headers: __WEBPACK_IMPORTED_MODULE_5__common_headers__["a" /* contentHeaders */] })
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
        console.log(asperaSettings);
        params["remote_user"] = asperaSettings.asperaUser;
        params["remote_password"] = asperaSettings.asperaSecret;
        params['remote_host'] = asperaSettings.asperaServer;
        params['fasp_port'] = 33001;
        params['target_rate_kbps'] = 45000;
        params['min_rate_kbps'] = 0;
        params['lock_policy'] = false;
        params['lock_target_rate'] = false;
        params['direction'] = "send";
        params['lock_min_rate'] = false;
        params['rate_policy'] = "fair";
        params['cipher'] = "aes-128";
        params['ssh_port'] = 22;
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
                console.log("Upload complete");
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
        this.http.post(__WEBPACK_IMPORTED_MODULE_6__common_globals__["a" /* LabsURL */]['projectDetails'], body, { headers: __WEBPACK_IMPORTED_MODULE_5__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            var updatedProject = new __WEBPACK_IMPORTED_MODULE_3__project_project__["a" /* Project */]().deserialize(JSON.parse(response.json().content));
            ;
            if (updatedProject.isBusy) {
                setTimeout(function () { _this.getProjectDetails(); }, 10000);
            }
            else {
                _this.authService.dashBoard.projects[_this.projectIndex] = updatedProject;
                _this.project = updatedProject;
                _this.loadingModalRef.close();
                _this.alerts.push({
                    id: 1,
                    type: 'success',
                    message: 'Project ' + updatedProject.title + ' cloning successful!',
                });
            }
        }, function (error) {
            //alert(error.text());
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
    ProjectComponent.prototype.openDeleteConfirmationModal = function (content) {
        var _this = this;
        if (this.selectedFiles.length <= 0) {
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
    ProjectComponent.prototype.getProjectContent = function (id) {
        var _this = this;
        var body = {
            "jwt": localStorage.getItem("jwt"),
            "user": localStorage.getItem("user"),
            "id": id
        };
        this.http.post(__WEBPACK_IMPORTED_MODULE_6__common_globals__["a" /* LabsURL */]['projectContent'], body, { headers: __WEBPACK_IMPORTED_MODULE_5__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            _this.files = [];
            var body = JSON.parse(response.json().content);
            for (var i in body) {
                var file = body[i];
                if (file.indexOf(".info") > -1 || file.indexOf(".log") > -1) {
                    console.log("Ignoring log and info files");
                }
                else {
                    _this.files.push(file);
                }
            }
        }, function (error) {
            alert(error.text());
            console.log(error.text());
        });
    };
    ProjectComponent.prototype.getDismissReason = function (reason) {
        if (reason === __WEBPACK_IMPORTED_MODULE_8__ng_bootstrap_ng_bootstrap__["d" /* ModalDismissReasons */].ESC) {
            return 'by pressing ESC';
        }
        else if (reason === __WEBPACK_IMPORTED_MODULE_8__ng_bootstrap_ng_bootstrap__["d" /* ModalDismissReasons */].BACKDROP_CLICK) {
            return 'by clicking on a backdrop';
        }
        else {
            return "with: " + reason;
        }
    };
    return ProjectComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_17" /* ViewChild */])('projectLocked'),
    __metadata("design:type", typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_0__angular_core__["K" /* ElementRef */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_0__angular_core__["K" /* ElementRef */]) === "function" && _a || Object)
], ProjectComponent.prototype, "projectLocked", void 0);
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* Input */])(),
    __metadata("design:type", Object)
], ProjectComponent.prototype, "alerts", void 0);
ProjectComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'app-project',
        template: __webpack_require__(275),
        styles: [__webpack_require__(258)]
    }),
    __metadata("design:paramtypes", [typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__auth_service__["a" /* AuthService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* ActivatedRoute */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["c" /* ActivatedRoute */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_8__ng_bootstrap_ng_bootstrap__["e" /* NgbModal */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_8__ng_bootstrap_ng_bootstrap__["e" /* NgbModal */]) === "function" && _e || Object, typeof (_f = typeof __WEBPACK_IMPORTED_MODULE_7__angular_http__["b" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_7__angular_http__["b" /* Http */]) === "function" && _f || Object, typeof (_g = typeof __WEBPACK_IMPORTED_MODULE_4__angular_forms__["e" /* FormBuilder */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_4__angular_forms__["e" /* FormBuilder */]) === "function" && _g || Object])
], ProjectComponent);

var _a, _b, _c, _d, _e, _f, _g;
//# sourceMappingURL=project.component.js.map

/***/ }),

/***/ 147:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_forms__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__project_project__ = __webpack_require__(64);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__auth_service__ = __webpack_require__(12);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__common_headers__ = __webpack_require__(63);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_router__ = __webpack_require__(11);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__ = __webpack_require__(35);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__common_globals__ = __webpack_require__(62);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__angular_http__ = __webpack_require__(22);
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
            'title': ["", __WEBPACK_IMPORTED_MODULE_1__angular_forms__["d" /* Validators */].required],
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
        console.log(this.modalRef);
        this.modalRef.result.then(function (result) {
            _this.closeResult = "Closed with: " + result;
        }, function (reason) {
            _this.closeResult = "Dismissed " + _this.getDismissReason(reason);
        });
    };
    ProjectsComponent.prototype.submitForm = function (body) {
        var _this = this;
        if (body.cloneProject == true) {
            if (body.studyId == "" || body.studyId == null) {
                alert('Please provide a valid MetaboLights Study ID');
                return;
            }
            else {
                this.cloningProject = true;
            }
        }
        body["jwt"] = localStorage.getItem("jwt");
        body["user"] = localStorage.getItem("user");
        this.http.post(__WEBPACK_IMPORTED_MODULE_7__common_globals__["a" /* LabsURL */]['createProject'], body, { headers: __WEBPACK_IMPORTED_MODULE_4__common_headers__["a" /* contentHeaders */] })
            .subscribe(function (response) {
            _this.cloningProject = false;
            var project = new __WEBPACK_IMPORTED_MODULE_2__project_project__["a" /* Project */]().deserialize(JSON.parse(response.json().content));
            _this.setSelected(project);
            _this.authService.dashBoard.projects.push(project);
            _this.projects = _this.authService.dashBoard.projects;
            _this.modalRef.close();
            _this.alerts.push({
                id: 1,
                type: 'success',
                message: 'Project ' + project.title + ' creation successful!',
            });
            _this.initForms();
        }, function (error) {
            _this.alerts.push({
                id: 1,
                type: 'danger',
                message: 'Project creation unsuccessful! Error',
            });
            console.log(error.text());
        });
        //this.authService.initializeWorkSpace();
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
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["M" /* Input */])(),
    __metadata("design:type", Object)
], ProjectsComponent.prototype, "alerts", void 0);
ProjectsComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'app-projects',
        template: __webpack_require__(276),
        styles: [__webpack_require__(259)]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_8__angular_http__["b" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_8__angular_http__["b" /* Http */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__auth_service__["a" /* AuthService */]) === "function" && _b || Object, typeof (_c = typeof __WEBPACK_IMPORTED_MODULE_5__angular_router__["b" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_5__angular_router__["b" /* Router */]) === "function" && _c || Object, typeof (_d = typeof __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__["e" /* NgbModal */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_6__ng_bootstrap_ng_bootstrap__["e" /* NgbModal */]) === "function" && _d || Object, typeof (_e = typeof __WEBPACK_IMPORTED_MODULE_1__angular_forms__["e" /* FormBuilder */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_forms__["e" /* FormBuilder */]) === "function" && _e || Object])
], ProjectsComponent);

var _a, _b, _c, _d, _e;
//# sourceMappingURL=projects.component.js.map

/***/ }),

/***/ 148:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__auth_service__ = __webpack_require__(12);
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
    function SettingsComponent(authService) {
        this.authService = authService;
    }
    SettingsComponent.prototype.ngOnInit = function () {
        this.user = this.authService.dashBoard.user;
    };
    return SettingsComponent;
}());
SettingsComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'app-settings',
        template: __webpack_require__(277),
        styles: [__webpack_require__(260)]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */]) === "function" && _a || Object])
], SettingsComponent);

var _a;
//# sourceMappingURL=settings.component.js.map

/***/ }),

/***/ 149:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
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
        template: __webpack_require__(278),
        styles: [__webpack_require__(261)]
    }),
    __metadata("design:paramtypes", [])
], WorkSpaceComponent);

//# sourceMappingURL=work-space.component.js.map

/***/ }),

/***/ 174:
/***/ (function(module, exports) {

function webpackEmptyContext(req) {
	throw new Error("Cannot find module '" + req + "'.");
}
webpackEmptyContext.keys = function() { return []; };
webpackEmptyContext.resolve = webpackEmptyContext;
module.exports = webpackEmptyContext;
webpackEmptyContext.id = 174;


/***/ }),

/***/ 175:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__(183);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__(187);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__(196);




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["a" /* enableProdMode */])();
}
__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */]);
//# sourceMappingURL=main.js.map

/***/ }),

/***/ 186:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var AppComponent = (function () {
    function AppComponent() {
        this.title = 'app works!';
    }
    return AppComponent;
}());
AppComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_10" /* Component */])({
        selector: 'app-root',
        template: __webpack_require__(269),
        styles: [__webpack_require__(252)]
    })
], AppComponent);

//# sourceMappingURL=app.component.js.map

/***/ }),

/***/ 187:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__(24);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_http__ = __webpack_require__(22);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__ng_bootstrap_ng_bootstrap__ = __webpack_require__(35);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__angular_router__ = __webpack_require__(11);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__app_routes__ = __webpack_require__(188);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__app_component__ = __webpack_require__(186);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__login_login_component__ = __webpack_require__(141);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__page_not_found_page_not_found_component__ = __webpack_require__(142);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__shared_shared_module__ = __webpack_require__(143);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__work_space_work_space_module__ = __webpack_require__(194);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__auth_guard_service__ = __webpack_require__(140);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__auth_service__ = __webpack_require__(12);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};




// Angular Bootstrap import

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
            __WEBPACK_IMPORTED_MODULE_7__app_component__["a" /* AppComponent */],
            __WEBPACK_IMPORTED_MODULE_9__page_not_found_page_not_found_component__["a" /* PageNotFoundComponent */],
            __WEBPACK_IMPORTED_MODULE_8__login_login_component__["a" /* LoginComponent */]
        ],
        imports: [
            __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["a" /* BrowserModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["a" /* FormsModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_http__["a" /* HttpModule */],
            __WEBPACK_IMPORTED_MODULE_10__shared_shared_module__["a" /* SharedModule */],
            __WEBPACK_IMPORTED_MODULE_11__work_space_work_space_module__["a" /* WorkSpaceModule */],
            __WEBPACK_IMPORTED_MODULE_4__ng_bootstrap_ng_bootstrap__["a" /* NgbModule */].forRoot(),
            __WEBPACK_IMPORTED_MODULE_5__angular_router__["a" /* RouterModule */].forRoot(__WEBPACK_IMPORTED_MODULE_6__app_routes__["a" /* routes */])
        ],
        providers: [
            __WEBPACK_IMPORTED_MODULE_12__auth_guard_service__["a" /* AuthGuard */],
            __WEBPACK_IMPORTED_MODULE_13__auth_service__["a" /* AuthService */]
        ],
        bootstrap: [__WEBPACK_IMPORTED_MODULE_7__app_component__["a" /* AppComponent */]]
    })
], AppModule);

//# sourceMappingURL=app.module.js.map

/***/ }),

/***/ 188:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__login_login_component__ = __webpack_require__(141);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__page_not_found_page_not_found_component__ = __webpack_require__(142);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return routes; });


var routes = [
    { path: 'login', component: __WEBPACK_IMPORTED_MODULE_0__login_login_component__["a" /* LoginComponent */] },
    { path: '', redirectTo: 'workspace/dashboard', pathMatch: 'full' },
    { path: '**', component: __WEBPACK_IMPORTED_MODULE_1__page_not_found_page_not_found_component__["a" /* PageNotFoundComponent */] }
];
//# sourceMappingURL=app.routes.js.map

/***/ }),

/***/ 189:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
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
        template: __webpack_require__(272),
        styles: [__webpack_require__(255)]
    }),
    __metadata("design:paramtypes", [])
], SideNavComponent);

//# sourceMappingURL=side-nav.component.js.map

/***/ }),

/***/ 190:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__auth_service__ = __webpack_require__(12);
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
        template: __webpack_require__(273),
        styles: [__webpack_require__(256)]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__auth_service__["a" /* AuthService */]) === "function" && _a || Object])
], TopNavComponent);

var _a;
//# sourceMappingURL=top-nav.component.js.map

/***/ }),

/***/ 191:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__user__ = __webpack_require__(192);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__project_project__ = __webpack_require__(64);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Dashboard; });


var Dashboard = (function () {
    function Dashboard() {
        this.projects = [];
        this.createdAt = "";
        this.updatedAt = "";
    }
    Dashboard.prototype.deserialize = function (input) {
        this.user = new __WEBPACK_IMPORTED_MODULE_0__user__["a" /* User */]().deserialize(input.owner);
        this.createdAt = input.createdAt;
        this.updatedAt = input.updatedAt;
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

/***/ 192:
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

/***/ 193:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_router__ = __webpack_require__(11);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_http__ = __webpack_require__(22);
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
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_router__["b" /* Router */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_2__angular_http__["b" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_2__angular_http__["b" /* Http */]) === "function" && _b || Object])
], ProjectsService);

var _a, _b;
//# sourceMappingURL=projects.service.js.map

/***/ }),

/***/ 194:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(0);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__ng_bootstrap_ng_bootstrap__ = __webpack_require__(35);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_common__ = __webpack_require__(4);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_forms__ = __webpack_require__(9);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__work_space_component__ = __webpack_require__(149);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__dashboard_dashboard_component__ = __webpack_require__(144);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__project_project_component__ = __webpack_require__(146);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_7__angular_router__ = __webpack_require__(11);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_8__shared_shared_module__ = __webpack_require__(143);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_9__work_space_routes__ = __webpack_require__(195);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_10__settings_settings_component__ = __webpack_require__(148);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_11__auth_service__ = __webpack_require__(12);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_12__projects_projects_service__ = __webpack_require__(193);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_13__dashboard_dashboard_resolve__ = __webpack_require__(145);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_14__projects_projects_component__ = __webpack_require__(147);
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
            __WEBPACK_IMPORTED_MODULE_2__angular_common__["c" /* CommonModule */],
            __WEBPACK_IMPORTED_MODULE_8__shared_shared_module__["a" /* SharedModule */],
            __WEBPACK_IMPORTED_MODULE_1__ng_bootstrap_ng_bootstrap__["a" /* NgbModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_forms__["a" /* FormsModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_forms__["c" /* ReactiveFormsModule */],
            __WEBPACK_IMPORTED_MODULE_7__angular_router__["a" /* RouterModule */].forChild(__WEBPACK_IMPORTED_MODULE_9__work_space_routes__["a" /* WorkSpaceRoutes */])
        ],
        declarations: [__WEBPACK_IMPORTED_MODULE_4__work_space_component__["a" /* WorkSpaceComponent */], __WEBPACK_IMPORTED_MODULE_5__dashboard_dashboard_component__["a" /* DashboardComponent */], __WEBPACK_IMPORTED_MODULE_6__project_project_component__["a" /* ProjectComponent */], __WEBPACK_IMPORTED_MODULE_10__settings_settings_component__["a" /* SettingsComponent */], __WEBPACK_IMPORTED_MODULE_14__projects_projects_component__["a" /* ProjectsComponent */]],
        exports: [
            __WEBPACK_IMPORTED_MODULE_7__angular_router__["a" /* RouterModule */]
        ],
        providers: [
            __WEBPACK_IMPORTED_MODULE_11__auth_service__["a" /* AuthService */],
            __WEBPACK_IMPORTED_MODULE_12__projects_projects_service__["a" /* ProjectsService */],
            __WEBPACK_IMPORTED_MODULE_1__ng_bootstrap_ng_bootstrap__["b" /* NgbActiveModal */],
            __WEBPACK_IMPORTED_MODULE_1__ng_bootstrap_ng_bootstrap__["c" /* NgbAlertConfig */],
            __WEBPACK_IMPORTED_MODULE_13__dashboard_dashboard_resolve__["a" /* DashboardResolve */]
        ]
    })
], WorkSpaceModule);

//# sourceMappingURL=work-space.module.js.map

/***/ }),

/***/ 195:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__work_space_component__ = __webpack_require__(149);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__dashboard_dashboard_component__ = __webpack_require__(144);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__project_project_component__ = __webpack_require__(146);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__projects_projects_component__ = __webpack_require__(147);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__settings_settings_component__ = __webpack_require__(148);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__auth_guard_service__ = __webpack_require__(140);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_6__dashboard_dashboard_resolve__ = __webpack_require__(145);
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
            },
            {
                path: 'settings',
                component: __WEBPACK_IMPORTED_MODULE_4__settings_settings_component__["a" /* SettingsComponent */],
            },
            {
                path: 'project/:id',
                component: __WEBPACK_IMPORTED_MODULE_2__project_project_component__["a" /* ProjectComponent */],
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

/***/ 196:
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

/***/ 252:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(7)(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ 253:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(7)(false);
// imports


// module
exports.push([module.i, ".card-container.card {\n    max-width: 350px;\n    padding: 40px 40px;\n    font-weight: lighter;\n}\n\n.btn {\n    font-weight: 700;\n    height: 36px;\n    -moz-user-select: none;\n    -webkit-user-select: none;\n    -ms-user-select: none;\n        user-select: none;\n    cursor: default;\n}\n\n/*\n * Card component\n */\n.card {\n    background-color: #F7F7F7;\n    /* just in case there no content*/\n    padding: 20px 25px 30px;\n    margin: 0 auto 25px;\n    margin-top: 50px;\n    /* shadows and rounded borders */\n    border-radius: 2px;\n    box-shadow: 0px 2px 2px rgba(0, 0, 0, 0.3);\n}\n\n.profile-img-card {\n    width: 96px;\n    height: 96px;\n    margin: 0 auto 10px;\n    display: block;\n    border-radius: 50%;\n}\n\n/*\n * Form styles\n */\n.profile-name-card {\n    font-size: 16px;\n    font-weight: bold;\n    text-align: center;\n    margin: 10px 0 0;\n    min-height: 1em;\n}\n\n.reauth-email {\n    display: block;\n    color: #404040;\n    line-height: 2;\n    margin-bottom: 10px;\n    font-size: 14px;\n    text-align: center;\n    overflow: hidden;\n    text-overflow: ellipsis;\n    white-space: nowrap;\n    box-sizing: border-box;\n}\n\n.form-signin #inputEmail,\n.form-signin #inputPassword {\n    direction: ltr;\n    height: 44px;\n    font-size: 16px;\n}\n\n.form-signin input[type=email],\n.form-signin input[type=password],\n.form-signin input[type=text],\n.form-signin button {\n    width: 100%;\n    display: block;\n    margin-bottom: 10px;\n    z-index: 1;\n    position: relative;\n    box-sizing: border-box;\n}\n\n.form-signin .form-control:focus {\n    border-color: rgb(104, 145, 162);\n    outline: 0;\n    box-shadow: inset 0 1px 1px rgba(0,0,0,.075),0 0 8px rgb(104, 145, 162);\n}\n\n.btn.btn-signin {\n    /*background-color: #4d90fe; */\n    background-color: rgb(104, 145, 162);\n    /* background-color: linear-gradient(rgb(104, 145, 162), rgb(12, 97, 33));*/\n    padding: 0px;\n    font-weight: 700;\n    font-size: 14px;\n    height: 36px;\n    border-radius: 3px;\n    border: none;\n    transition: all 0.218s;\n}\n\n.btn.btn-signin:hover,\n.btn.btn-signin:active,\n.btn.btn-signin:focus {\n    background-color: rgb(12, 97, 33);\n}\n\n.forgot-password {\n    color: rgb(104, 145, 162);\n}\n\n.forgot-password:hover,\n.forgot-password:active,\n.forgot-password:focus{\n    color: rgb(12, 97, 33);\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ 254:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(7)(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ 255:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(7)(false);
// imports


// module
exports.push([module.i, ".tiny{\n\tfont-size: 0.02em;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ 256:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(7)(false);
// imports


// module
exports.push([module.i, ".logo{\n\theight: 30px !important;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ 257:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(7)(false);
// imports


// module
exports.push([module.i, ".cp{\t\n\tbackground-color: #2B99FF;\n\tcolor: #fff;\n\tpadding: 20px;\n\n}\n\n.display-number{\n\tfont-size: 3.5em;\n\tfont-weight: bolder;\n}\n\n.tile_count .tile_stats_count {\n    margin-bottom: 10px;\n    border-bottom: 0;\n    padding-top: 15px;\n    border-radius: 4px;\n    padding-bottom: 10px;\n    border: 1px solid #E1E2E4;\n    box-shadow: 0 1px 2px 0 rgba(0,0,0,0.1);\n    -moz-box-shadow: 0 1px 2px 0 rgba(0,0,0,0.1);\n    -webkit-box-shadow: 0 1px 2px 0 rgba(0,0,0,0.1);\n}\n\n.tile_count .tile_stats_count .count {\n    font-size: 60px;\n}\n\n.tile_count .tile_stats_count .count {\n    line-height: 57px;\n    font-weight: 600;\n}\n\n.tile_count .tile_stats_count span {\n    font-size: 13px;\n}\n\n.tile_count .tile_stats_count .count_bottom i {\n    width: 12px;\n}\n\n.alert{\n\tborder-radius: 0px;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ 258:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(7)(false);
// imports


// module
exports.push([module.i, ".cloning_project{\n\tdisplay: -webkit-box;\n\tdisplay: -ms-flexbox;\n\tdisplay: flex;\n\t-webkit-box-align: center;\n\t    -ms-flex-align: center;\n\t        align-items: center;\n  \t-webkit-box-pack: center;\n  \t    -ms-flex-pack: center;\n  \t        justify-content: center;\n\tmin-height: 50vh;\n\tpadding: 40px 40px;\n\t-webkit-box-orient: vertical;\n\t-webkit-box-direction: normal;\n\t    -ms-flex-direction: column;\n\t        flex-direction: column;\n\ttext-align: center;\n}\n\n.cloning_project .spinner > h5{\n\tfont-weight: normal;\n\tletter-spacing: 1px;\n}\n\n.cloning_project .spinner > small{\n\tfont-weight: lighter;\n}\n\n.spinner{\n\tposition: relative !important;\n    top: 0%;\n    left: 0%;\n    margin-top: 0px; \n    margin-left: 0px;\n    text-align: center;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ 259:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(7)(false);
// imports


// module
exports.push([module.i, ".cloning_project{\n\tdisplay: -webkit-box;\n\tdisplay: -ms-flexbox;\n\tdisplay: flex;\n\t-webkit-box-align: center;\n\t    -ms-flex-align: center;\n\t        align-items: center;\n  \t-webkit-box-pack: center;\n  \t    -ms-flex-pack: center;\n  \t        justify-content: center;\n\tmin-height: 50vh;\n\tpadding: 40px 40px;\n\t-webkit-box-orient: vertical;\n\t-webkit-box-direction: normal;\n\t    -ms-flex-direction: column;\n\t        flex-direction: column;\n\ttext-align: center;\n}\n\n.cloning_project .spinner > h5{\n\tfont-weight: normal;\n\tletter-spacing: 1px;\n}\n\n.cloning_project .spinner > small{\n\tfont-weight: lighter;\n}\n\n.spinner{\n\tposition: relative !important;\n    top: 0%;\n    left: 0%;\n    margin-top: 0px; \n    margin-left: 0px;\n    text-align: center;\n}\n\n.md{\n\tmargin-top: 10px;\n\tpadding-bottom: 2px;\n}", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ 260:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(7)(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ 261:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(7)(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ 269:
/***/ (function(module, exports) {

module.exports = "<router-outlet></router-outlet>"

/***/ }),

/***/ 270:
/***/ (function(module, exports) {

module.exports = "<div class=\"container\">\n    <div class=\"card card-container\">\n        <h4 class=\"text-center\">MetaboLights Labs</h4><br>\n        <img id=\"profile-img\" class=\"profile-img-card\" src=\"//ssl.gstatic.com/accounts/ui/avatar_2x.png\" />\n        <p id=\"profile-name\" class=\"profile-name-card\"></p>\n        <form class=\"form-signin\" role=\"form\" (submit)=\"login($event, email.value, secret.value)\">\n            <span id=\"reauth-email\" class=\"reauth-email\"></span>\n            <input type=\"email\" #email class=\"form-control\" id=\"useremail\" placeholder=\"Email address\">\n            <input type=\"password\" #secret class=\"form-control\" id=\"password\" placeholder=\"Password\">\n            <button class=\"btn btn-default blue\" type=\"submit\">Sign in</button>\n        </form>\n    </div>\n</div>"

/***/ }),

/***/ 271:
/***/ (function(module, exports) {

module.exports = "<div class=\"page-wrapper\">\n\t<div class=\"text-center\">\n\t\t<img src=\"assets/img/ng-logo.png\" height=\"150\" alt=\"\">\n\t\t<p class=\"message\">\n\t\t  404: Page Not Found error\n\t\t</p>\n\t</div>\n</div>"

/***/ }),

/***/ 272:
/***/ (function(module, exports) {

module.exports = "<nav class=\"sidebar\">\n    <ul class=\"list-group\">\n        <a routerLink=\"/workspace/dashboard\" class=\"list-group-item\" routerLinkActive=\"router-link-active\">\n            <i class=\"fa fa-fw fa-dashboard\"></i> Dashboard\n        </a>\n        <a routerLink=\"/workspace/projects\" class=\"list-group-item\" routerLinkActive=\"router-link-active\">\n            <i class=\"fa fa-fw fa-folder\"></i> Projects\n        </a>\n    </ul>\n</nav>"

/***/ }),

/***/ 273:
/***/ (function(module, exports) {

module.exports = "<nav class=\"navbar navbar-inverse navbar-fixed-top\" role=\"navigation\">\n    <div class=\"container-fluid\">\n        <!-- Brand and toggle get grouped for better mobile display -->\n        <div class=\"navbar-header\">\n            <button type=\"button\" class=\"navbar-toggle\" data-toggle=\"collapse\" data-target=\".navbar-ex1-collapse\">\n                <span class=\"sr-only\">Toggle navigation</span>\n                <span class=\"icon-bar\"></span>\n                <span class=\"icon-bar\"></span>\n                <span class=\"icon-bar\"></span>\n            </button>\n            <a class=\"navbar-brand\" routerLink=\"/\">\n                <img src=\"assets/img/logo.png\" class=\"logo\"> MetaboLights Labs <sup class=\"tiny lighter\">Beta</sup>\n            </a>\n        </div>\n\n        <!-- Collect the nav links, forms, and other content for toggling -->\n        <div class=\"collapse navbar-collapse navbar-ex1-collapse\">\n            <!-- <ul class=\"nav navbar-nav\">\n                <li class=\"active\"><a href=\"#\">Link</a></li>\n                <li><a href=\"#\">Link</a></li>\n            </ul>\n            <form class=\"navbar-form navbar-left\" role=\"search\">\n                <div class=\"form-group\">\n                    <input type=\"text\" class=\"form-control\" placeholder=\"Search\">\n                </div>\n                <button type=\"submit\" class=\"btn btn-default\">Submit</button>\n            </form> -->\n            <ul class=\"nav navbar-nav navbar-right\">\n                <li class=\"dropdown\">\n                    <a href=\"#\" class=\"dropdown-toggle\" data-toggle=\"dropdown\">{{ dashBoard.user.firstName }}\n                  <i class=\"fa fa-fw fa-user\"></i> <b class=\"caret\"></b></a>\n                    <ul class=\"dropdown-menu\">\n                        <li>\n                            <a routerLink=\"/workspace/settings\" class=\"dropdown-item\"><i class=\"fa fa-fw fa-cogs\"></i>&nbsp;Settings</a>\n                        </li>\n                        <li>\n                            <a class=\"dropdown-item\" (click)=\"logOut()\" ><i class=\"fa fa-fw fa-sign-out\"></i>&nbsp;Log out</a>\n                        </li>\n                    </ul>\n                </li>\n            </ul>\n        </div><!-- /.navbar-collapse -->\n    </div>\n</nav>"

/***/ }),

/***/ 274:
/***/ (function(module, exports) {

module.exports = "<div class=\"content\">\n\t<div class=\"col-md-12 mini-wrapper np\">\n\t\t<div class=\"section\">\n\t\t\t<h3 class=\"title\">Dashboard</h3>\n\t\t</div>\n\t\t<div class=\"section ntp\">\n\t\t\t<div class=\"tile_count\">\n           \t\t<a routerLink=\"/workspace/projects\">\n\t           \t\t<div class=\"col-md-2 col-sm-4 col-xs-6 tile_stats_count blue\">\n\t\t\t\t\t\t<div class=\"count\">{{ dashBoard.projects.length }}</div>\n\t\t\t\t\t\t<span class=\"count_top\"><i class=\"fa fa-folder\"></i> Total Projects</span>\n\t\t\t\t\t\t<!-- <span class=\"count_bottom\"><i class=\"green\">4% </i> From last Week</span> -->\n\t            \t</div>\n            \t</a>\n          \t</div>\n\t\t</div>\n\t</div>\n</div>        "

/***/ }),

/***/ 275:
/***/ (function(module, exports) {

module.exports = "<div class=\"content\">\n\t<div class=\"col-md-9 mini-wrapper np\">\n\t\t<div class=\"section\">\n\t\t\t<h3 class=\"title\"><b><i class=\"fa fa-folder-open\"></i>&nbsp;{{ project.title }}</b></h3>\n\t\t</div>\n\t\t<div *ngIf=\"files?.length > 0\" class=\"section ntp\">\n  \t\t\t<small>\n\t  \t\t\t<label>\n\t  \t\t\t\t<input (change)=\"selectAllFiles($event)\" type=\"checkbox\"> Select all\n\t  \t\t\t</label>\n\t  \t\t</small>\n\t\t  \t<div class=\"card\">\n\t\t\t  \t<ul class=\"list-group list-group-flush\" *ngFor=\"let file of files; let i = index;\">\n\t\t\t  \t\t<div class=\"checkbox\">\n\t\t\t\t\t\t<li class=\"list-group-item\">\n\t\t\t\t\t\t    <label>\n\t\t\t\t\t\t    \t\t<input [checked]=\"selectedFiles.indexOf(file) > -1\" (change)=\"updateSelectedFilesList($event, i, file)\" type=\"checkbox\"> \n\t\t\t\t\t\t\t    \t&nbsp;<small>{{ file }}</small>\n\t\t\t\t\t    \t</label>\n\t\t\t\t  \t\t</li>\n\t\t\t\t\t</div>\n\t\t\t  \t</ul>\n\t\t\t</div>\n\t\t</div>\n\t\t<div *ngIf=\"files?.length == 0\" class=\"section ntp\">\n\t\t\t<div class=\"cloning_project\">\n\t\t\t<img src=\"assets/img/empty.png\" class=\"logo\"><br><br>\n\t\t\t<div class=\"spinner\">\n\t\t\t\t<h6 class=\"text-center lighter\">\n\t\t\t\t\tNo files yet! Upload files using aspera? \n\t\t\t\t</h6>\n\t\t\t\t<small class=\"center\"><i><button class=\"btn btn-sm btn-primary\" (click)=\"asperaUpload()\">CLICK HERE </button></i></small>\n\t\t\t</div>\n\t\t</div>\n\t\t</div>\n\t</div>\n\t<div class=\"col-xs-12 col-md-3 grey np\">\n\t\t<div class=\"section\">\n\t\t  \t<small><label>File upload</label></small><br>\n\t\t  \t<div class=\"form-group\">\n\t\t\t  \t<div class=\"btn-group-sm\" role=\"group\" aria-label=\"...\">\n\t\t\t\t\t<button class=\"btn btn-primary\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Upload files - Aspera web browser client\" (click)=\"asperaUpload()\">\n\t\t\t  \t\t\t<i class=\"fa fa-upload\"></i> Aspera Upload\n\t\t\t  \t\t</button>\n\t\t\t\t\t<button class=\"btn btn-info\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Upload files - Aspera command line client\" (click)=\"open(content)\">\n\t\t\t  \t\t\t<i class=\"fa fa-terminal\"></i>\n\t\t\t  \t\t</button>\n\t\t\t  \t\t<a class=\"btn btn-default\" data-toggle=\"tooltip\" data-placement=\"left\" title=\"Need help with setting up Aspera\" href=\"ftp://ftp.ebi.ac.uk/pub/databases/metabolights/documentation/MetaboLights%20Tutorial%20-%20FAQ.pdf\" target=\"_blank\">\n\t\t\t\t\t\t<i class=\"fa fa-question\"></i>\n\t\t\t\t\t</a>\n\t\t\t  \t</div>\n\t\t  \t</div>\n\t\t</div>\n\t\t<div class=\"section\">\n\t\t  \t<span class=\"form-group\">\n\t\t  \t\t<button class=\"btn btn-default btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Edit project details\" (click)=\"openEditModal(editProjectDetails)\"><i class=\"fa fa-cogs\"></i></button>\n\t\t\t\t<button *ngIf=\"files?.length > 0\" class=\"btn btn-default btn-sm\" (click)=\"openDeleteConfirmationModal(confirmDeleteFiles)\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Delete file(s)\" ><i class=\"fa fa-trash\"></i></button> \n\t\t\t\t<button class=\"btn btn-default btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"View Project Log\" ><i class=\"fa fa-road\"></i></button> \n\t\t  \t</span>\n\t\t</div>\n\t\t<div class=\"section\" *ngIf=\"files?.length > 0\">\n\t\t\t<small><label> Tools </label></small><br>\n\t\t\t<span class=\"tiny\"><label> <i>File format conversions</i></label></span><br>\n\t\t  \t<span class=\"form-group\">\n\t\t  \t\t<button class=\"btn btn-success btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Create ISA Tab files\">\n\t\t  \t\t\t<i class=\"fa fa-cubes\" aria-hidden=\"true\"></i>\n\t\t  \t\t</button>\n\t\t  \t</span><br>\n\t\t  \t<span class=\"tiny\"><label> <i>Data Analysis</i></label></span><br>\n\t\t  \t<span class=\"form-group\">\n\t\t  \t\t<button class=\"btn btn-info btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Run Isotopologue Parameter Optimization (IPO) \">\n\t\t  \t\t\t<i class=\"fa fa-filter\" aria-hidden=\"true\"></i>\n\t\t  \t\t</button>\n\t\t  \t</span><br>\n\t\t\t<span class=\"tiny\"><label> <i>Export</i></label></span><br>\n\t\t  \t<span class=\"form-group\">\n\t\t  \t\t<button class=\"btn btn-warning btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Submit project as a study to MetaboLights Database\">\n\t\t  \t\t\t<i class=\"fa fa-university\" aria-hidden=\"true\"></i>\n\t\t  \t\t</button>\n\t\t  \t\t<button class=\"btn btn-warning btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Export as PDF file\">\n\t\t  \t\t\t<i class=\"fa fa-file-pdf-o\" aria-hidden=\"true\"></i>\n\t\t  \t\t</button>\n\t\t  \t\t<button class=\"btn btn-warning btn-sm\" data-toggle=\"tooltip\" data-placement=\"bottom\" title=\"Export as .zip file\">\n\t\t  \t\t\t<i class=\"fa fa-file-archive-o\" aria-hidden=\"true\"></i>\n\t\t  \t\t</button>\n\t\t  \t</span>\n\t\t  \t\n\t\t</div>\n\t\t<div class=\"section\">\n\t\t  \t<div class=\"ml-card\">\n\t\t  \t\t<div class=\"ml-card-header\">\n\t\t  \t\t\tProject Details\n\t\t  \t\t</div>\n\t\t  \t\t<div class=\"ml-card-body\">\n\t\t  \t\t\t<small><i>TITLE: </i></small>\n\t\t  \t\t\t<p>{{ project.title }}</p>\n\t\t  \t\t\t<small><i>ID: </i></small>\n\t\t  \t\t\t<p><small>{{ project.id }}</small></p>\n\t\t  \t\t\t<small><i>Description: </i></small>\n\t\t  \t\t\t<p>{{ project.description }}</p>\n\t\t\t    \t<small><i>Created at: </i></small>\n\t\t  \t\t\t<p>{{ project.createdAt | date:'yyyy-MM-dd HH:mm:ss Z' }}</p>\n\t\t  \t\t\t<small><i>Updated at: </i></small>\n\t\t  \t\t\t<p>{{ project.updatedAt | date:'yyyy-MM-dd HH:mm:ss Z' }}</p>\n\t\t  \t\t\t<small><i>Status: </i></small>\n\t\t  \t\t\t<p *ngIf=\"project.isBusy\">\n\t\t  \t\t\t\t<i class=\"fa fa-lock\"></i>\n\t\t  \t\t\t</p>\n\t\t  \t\t\t<p *ngIf=\"!project.isBusy\">\n\t\t  \t\t\t\t<i class=\"fa fa-unlock\"></i>\n\t\t  \t\t\t</p>\n\t\t  \t\t</div>\n\t\t  \t</div>\n\t\t</div>\n\t\t<p *ngFor=\"let alert of alerts\">\n\t\t\t<small><ngb-alert [type]=\"alert.type\" (close)=\"closeAlert(alert)\">{{ alert.message }}</ngb-alert></small>\n\t\t</p>\n\t</div>\n</div>\n<template #content let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<form>\n\t\t<div class=\"modal-header\">\n\t\t\t<h5 class=\"modal-title\"><i class=\"fa fa-terminal\"></i>&emsp;Aspera Command Line File Upload <a class=\"close pull-right\" aria-label=\"Close\" (click)=\"d('Cross click')\">\n\t\t\t\t<span aria-hidden=\"true\">&times;</span>\n\t\t\t</a></h5>\n\t\t</div>\n\t\t<div class=\"modal-body\">\n\t\t\t<div class=\"row\">\n\t\t\t\t<div class=\"col-md-12\">\n\t\t\t\t\t<small>\n\t\t\t\t\t\t<span class=\"tiny\">Please note if you have already setup aspera for a MetaboLights Labs project, please ignore the first 2 steps.</span><br>\n\t\t\t\t\t\t<b>Step 1: Install Aspera ascp command line client</b><br>\n\t\t\t\t\t\tThe Aspera ascp command line client can be downloaded <i><a href=\"http://downloads.asperasoft.com/downloads\" target=\"_blank\">here</a></i><br><br>\n\t\t\t\t\t\t<b>Step 2: PIP Install - MetaboLightsLabs CLI Tool</b><br>\n\t\t\t\t\t\t<code>> pip install git+https://github.com/EBI-Metabolights/MetaboLightsLabs-PythonCLI</code><br>\n\t\t\t\t\t\tFor details on how to install PIP - <i><a href=\"https://pip.pypa.io/en/stable/installing/\" target=\"_blank\">Click here</a></i><br><br>\n\t\t\t\t\t\t<b>Step 3: Upload the files</b><br>\n\t\t\t\t\t\t<span class=\"tiny\">Copy the following command, replace the filesToUpload with your files/folders location (array if more than one) and execute from the command line.</span><br>\n\t\t\t\t\t\t<code>> uploadToMetaboLightsLabs.py -t {{token}} -i <b>filesToUpload</b> -p {{ project.id }} -s DEV</code><br><br>\n\t\t\t\t\t\t<hr>\n\t\t\t\t\t\t<span class=\"tiny\">For assistance contact us (please mention your error messages or screenshots) <a href=\"mailto:metabolights-help@ebi.ac.uk\">&nbsp;here</a></span>\n\t\t\t\t\t\t<!-- <b>Step 3: Navigate to the folder where the Aspera command line client program ascp is installed.</b><br>\n\t\t\t\t\t\tThe location of the 'ascp' program in the filesystem:<br>\n\t\t\t\t\t\t<span class=\"col-md-12\">\n\t\t\t\t\t\t\t<b>Mac:</b> on the desktop go cd /Applications/Aspera\\ Connect.app/Contents/Resources/ there you'll see the command line utilities where you're going to use 'ascp'.<br><br>\n\n\t\t\t\t\t\t\t<b>Windows:</b> the downloaded files are a bit hidden. For instance in Windows7 the ascp.exe is located in the users home directory in: AppData\\Local\\Programs\\Aspera\\Aspera Connect\\bin\\ascp.exe<br><br>\n\n\t\t\t\t\t\t\t<b>Linux:</b> should be in your user's home directory, cd /home/username/.aspera/connect/bin/ there you'll see the command line utilities where you're going to use 'ascp'.<br><br>\n\t\t\t\t\t\t</span> -->\n\t\t\t\t\t</small>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t\t<div class=\"col-md-12\">\n\t\t\t\t&nbsp;\n\t\t\t</div>\n\t\t</div>\n\t</form>\n</template>\n<template #projectLocked let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<div class=\"modal-body\">\n\t\t<div class=\"cloning_project\">\n\t\t\t<img src=\"assets/img/waiting.png\" class=\"logo\"><br><br>\n\t\t\t<div class=\"spinner\">\n\t\t\t\t<div class=\"bounce1\"></div>\n\t\t\t\t<div class=\"bounce2\"></div>\n\t\t\t\t<div class=\"bounce3\"></div>\n\t\t\t\t<br><br>\n\t\t\t\t<h5 class=\"text-center\">\n\t\t\t\t\tPlease wait while we clone study in to your project. \n\t\t\t\t</h5>\n\t\t\t\t<small class=\"center\">This might take a while depending upon the size of the study.</small>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</template>\n<template #editProjectDetails let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n<form [formGroup]=\"editProjectDetailsForm\" (ngSubmit)=\"submitForm(editProjectDetailsForm.value)\">\n\t<div class=\"modal-header\">\n\t\t<h5 class=\"modal-title\"><i class=\"fa fa-cogs\"></i>&emsp;Settings <a class=\"close pull-right\" aria-label=\"Close\" (click)=\"d('Cross click')\">\n\t\t\t<span aria-hidden=\"true\">&times;</span>\n\t\t</a></h5>\n\t</div>\n\t<div class=\"modal-body\">\n\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!editProjectDetailsForm.controls['title'].valid}\">\n\t\t\t<small><label>Title</label></small>\n\t\t\t<input class=\"form-control\" type=\"text\" [formControl]=\"editProjectDetailsForm.controls['title']\" >\n\t\t</div>\n\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!editProjectDetailsForm.controls['description'].valid}\">\n\t\t\t<small><label for=\"projectTitle\">Description (Optional)</label></small>\n\t\t\t<textarea rows=\"5\" class=\"form-control\" [formControl]=\"editProjectDetailsForm.controls['description']\"></textarea>\n\t\t</div>\n\t</div>\n\t<div class=\"modal-footer\">\n\t\t<button type=\"submit\" [disabled]=\"!editProjectDetailsForm.valid\" class=\"btn btn-primary\">Save</button>\n\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Close</button>\n\t</div>\n</form>\n</template>\n<template #confirmDeleteFiles let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<div class=\"modal-body\">\n\t\t<div class=\"cloning_project\">\n\t\t\t<img src=\"assets/img/trash.png\" class=\"logo\"><br><br>\n\t\t\t<div class=\"spinner\">\n\t\t\t\t<h5 class=\"text-center\">\n\t\t\t\t\tAre you sure ? <br><br>Do you want to delete the selected files ? \n\t\t\t\t</h5>\n\t\t\t\t<small class=\"center\">The files will be deleted permanantely and will be unavailable.</small>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n\t<div class=\"modal-footer\">\n\t\t<button type=\"submit\" (click)=\"deleteSelectedFiles()\" class=\"btn btn-danger\">Delete</button>\n\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Close</button>\n\t</div>\n</template>"

/***/ }),

/***/ 276:
/***/ (function(module, exports) {

module.exports = "<div class=\"content\">\n\t<div class=\"col-md-9 mini-wrapper np\">\n\t\t<div class=\"section\">\n\t\t\t<h3 class=\"title\">Projects</h3>\n\t\t</div>\n\t\t<div class=\"section ntp\" *ngIf=\"projects.length > 0\">\n\t\t\t<div class=\"row\">\n\t\t\t\t<div *ngFor=\"let project of projects\">\n\t\t\t\t\t<div class=\"col-xs-6 col-sm-4\">\n\t\t\t\t\t\t<div [class.selectedProject]=\"project.id === selectedProject.id\" class=\"p-wrapper\"  (dblclick)=\"onSelect(project)\" (click)=\"setSelected(project)\">\n\t\t\t\t\t\t\t<h1><i class=\"fa fa-folder-o fa-2x\"></i></h1>\n\t\t\t\t\t\t\t<h5><a>{{ project.title }}</a></h5>\n\t\t\t\t\t\t\t<h6 class=\"md\">\n\t\t\t\t\t\t\t\t<small class=\"tiny\">\n\t\t\t\t\t\t\t\t\t<span class=\"pull-left\">\n\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-info-circle\"></i>&nbsp;{{ getId(project.id) }}\n\t\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t\t<span class=\"pull-right\">\n\t\t\t\t\t\t\t\t\t\t<span *ngIf=\"project.isBusy == true\">\n\t\t\t\t\t\t\t\t\t\t\t<i class=\"fa fa-lock fa-2x\"></i>\n\t\t\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t</small>\n\t\t\t\t\t\t\t</h6>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<div *ngIf=\"projects.length == 0\">\n\t\t\t<div class=\"cloning_project\">\n\t\t\t\t<img src=\"assets/img/start.png\" class=\"logo\"><br>\n\t\t\t\t<div class=\"spinner\">\n\t\t\t\t\t<h6 class=\"text-center\">\n\t\t\t\t\t\tNo projects found! Create a new project to get started? \n\t\t\t\t\t</h6><br>\n\t\t\t\t\t<small class=\"center\"><i><a href=\"http://www.ebi.ac.uk/metabolights/contact\" target=\"_blank\" class=\"btn btn-sm btn-primary\">Need Help</a></i></small>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n\t<div class=\"col-xs-12 col-md-3 grey np\">\n\t\t<div class=\"section\">\n\t\t\t\t<!-- \n\t\t\t\t\t<span class=\"right\">\n\t\t\t  \t\t\t<label>Manage Projects</label>\n\t\t\t  \t\t</span> \n\t\t\t  \t-->\n\t\t  \t<span class=\"form-group\">\n\t\t  \t\t<button (click)=\"open(content)\" class=\"btn btn-success btn-sm form-control\">\n\t\t  \t\t\t<i class=\"fa fa-plus\"></i> Create Project\n\t\t  \t\t</button>\n\t\t  \t</span>\n\t\t</div>\n\t\t<div class=\"section\" *ngIf=\"selectedProject\">\n\t\t  \t<div class=\"ml-card\">\n\t\t  \t\t<div class=\"ml-card-header\">\n\t\t  \t\t\tProject Details\n\t\t  \t\t</div>\n\t\t  \t\t<div class=\"ml-card-body\">\n\t\t  \t\t\t<small><i>TITLE: </i></small>\n\t\t  \t\t\t<p>{{ selectedProject.title }}</p>\n\t\t  \t\t\t<small><i>ID: </i></small>\n\t\t  \t\t\t<p><small>{{ selectedProject.id }}</small></p>\n\t\t  \t\t\t<small><i>Description: </i></small>\n\t\t  \t\t\t<p>{{ selectedProject.description }}</p>\n\t\t  \t\t\t<small><i>Created at: </i></small>\n\t\t  \t\t\t<p>{{ selectedProject.createdAt | date:'yyyy-MM-dd HH:mm:ss Z' }}</p>\n\t\t  \t\t\t<small><i>Updated at: </i></small>\n\t\t  \t\t\t<p>{{ selectedProject.updatedAt | date:'yyyy-MM-dd HH:mm:ss Z' }}</p>\n\t\t  \t\t</div>\n\t\t  \t</div>\n\t\t</div>\n\t\t<p *ngFor=\"let alert of alerts\">\n\t\t\t<small><ngb-alert [type]=\"alert.type\" (close)=\"closeAlert(alert)\">{{ alert.message }}</ngb-alert></small>\n\t\t</p>\n\t</div>\n</div>\n<template #content let-c=\"close\" let-d=\"dismiss\" ngbModalContainer>\n\t<form [formGroup]=\"createProjectForm\" (ngSubmit)=\"submitForm(createProjectForm.value)\">\n\t\t<div *ngIf=\"cloningProject\">\n\t\t\t<div class=\"modal-body\">\n\t\t\t\t<div class=\"cloning_project\">\n\t\t\t\t\t<img src=\"assets/img/waiting.png\" class=\"logo\"><br><br>\n\t\t\t\t\t<div class=\"spinner\">\n\t\t\t\t\t\t<div class=\"bounce1\"></div>\n\t\t\t\t\t\t<div class=\"bounce2\"></div>\n\t\t\t\t\t\t<div class=\"bounce3\"></div>\n\t\t\t\t\t\t<br><br>\n\t\t\t\t\t\t<h5 class=\"text-center\">\n\t\t\t\t\t\t\tPlease wait while we clone study in to your project. \n\t\t\t\t\t\t</h5>\n\t\t\t\t\t\t<small class=\"center\">This might take a while depending upon the size of the study.</small>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<div *ngIf=\"!cloningProject\">\n\t\t\t<div class=\"modal-header\">\n\t\t\t\t<h5 class=\"modal-title\"><i class=\"fa fa-plus\"></i> Create Project <a class=\"close pull-right\" aria-label=\"Close\" (click)=\"d('Cross click')\">\n\t\t\t\t\t<span aria-hidden=\"true\">&times;</span>\n\t\t\t\t</a></h5>\n\t\t\t</div>\n\t\t\t<div class=\"modal-body\">\t\t\t\n\t\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!createProjectForm.controls['title'].valid}\">\n\t\t\t\t\t<small><label>Title</label></small>\n\t\t\t\t\t<input class=\"form-control\" type=\"text\" [formControl]=\"createProjectForm.controls['title']\" >\n\t\t\t\t</div>\n\t\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!createProjectForm.controls['description'].valid}\">\n\t\t\t\t\t<small><label for=\"projectTitle\">Description (Optional)</label></small>\n\t\t\t\t\t<textarea rows=\"5\" class=\"form-control\" [formControl]=\"createProjectForm.controls['description']\"></textarea>\n\t\t\t\t</div>\n\t\t\t\t<div class=\"form-group\" [ngClass]=\"{'has-error':!createProjectForm.controls['cloneProject'].valid}\">\n\t\t\t\t\t<div class=\"checkbox\">\n\t\t\t\t\t\t<small><label><input #someCheckbox type=\"checkbox\" [formControl]=\"createProjectForm.controls['cloneProject']\">&nbsp;Clone existing <a href=\"http://www.metabolights.org\" target=\"_blank\">Metabolights study</a></label></small>\n\t\t\t\t\t</div>\n\t\t\t\t\t<input [formControl]=\"createProjectForm.controls['studyId']\" class=\"form-control\" [attr.disabled]=\"createProjectForm.controls['cloneProject'].value === false || null\" type=\"text\" placeholder=\"Study Id. Ex: MTBLS1\">\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t\t<div class=\"modal-footer\">\n\t\t\t\t<button type=\"submit\" [disabled]=\"!createProjectForm.valid\" class=\"btn btn-primary\">Create</button>\n\t\t\t\t<button type=\"button\" class=\"btn btn-secondary\" (click)=\"c('Close click')\">Close</button>\n\t\t\t</div>\n\t\t</div>\n\t</form>\n</template>        "

/***/ }),

/***/ 277:
/***/ (function(module, exports) {

module.exports = "<div class=\"content\">\n\t<div class=\" placeholders\">\n\t\t<h3>Settings</h3>\n\t\t<hr>\n\t\t<div class=\"col-xs-12 col-sm-4\">\n\t\t<div class=\"card\">\n\t\t\t<ul class=\"list-group list-group-flush\">\n\t\t\t\t<li class=\"list-group-item\">\n\t\t\t\t\t<small><label><b>First Name</b></label></small><br>\n\t\t\t\t\t{{ user.firstName }}\n\t\t\t\t</li>\n\t\t\t\t<li class=\"list-group-item\">\n\t\t\t\t\t<small><label><b>Last Name</b></label></small><br>\n\t\t\t\t\t{{ user.lastName }}\n\t\t\t\t</li>\n\t\t\t\t<li class=\"list-group-item\">\n\t\t\t\t\t<small><label><b>Email</b></label></small><br>\n\t\t\t\t\t{{ user.email }}\n\t\t\t\t</li>\n\t\t\t\t<li class=\"list-group-item\">\n\t\t\t\t\t<small><label><b>Address</b></label></small><br>\n\t\t\t\t\t{{ user.address }}\n\t\t\t\t</li>\n\t\t\t\t<li class=\"list-group-item\">\n\t\t\t\t\t<small><label><b>Affiliation</b></label></small><br>\n\t\t\t\t\t{{ user.affiliation }}\n\t\t\t\t</li>\n\t\t\t\t<li class=\"list-group-item\">\n\t\t\t\t\t<small><label><b>ORCID Id</b></label></small><br>\n\t\t\t\t\t<span>{{user.orcid ? user.orcid : '-'}}</span>\n\t\t\t\t</li>\n\t\t\t</ul>\n\t\t</div>\n\t\t</div>\n\t</div>\n</div>        "

/***/ }),

/***/ 278:
/***/ (function(module, exports) {

module.exports = "<top-nav></top-nav>\n<side-nav></side-nav>\n<section class=\"main-container\">\t\n\t<router-outlet></router-outlet>\n</section>\n"

/***/ }),

/***/ 322:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(175);


/***/ }),

/***/ 62:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return LabsURL; });
var environment = "dev";
var server = "http://localhost:8080/metabolights/webservice/";
if (environment == "prod") {
    server = "http://www.ebi.ac.uk/metabolights/webservice/";
}
else if (environment == "dev") {
    server = "http://wwwdev.ebi.ac.uk/metabolights/webservice/";
}
var LabsURL = {};
// Application
LabsURL['authenticate'] = server + 'labs/authenticate';
// Workspace
LabsURL['initialise'] = server + 'labs-workspace/initialise';
LabsURL['createProject'] = server + 'labs-workspace/createProject';
// Project
LabsURL['projectContent'] = server + 'labs-project/content';
LabsURL['projectDetails'] = server + 'labs-project/details';
LabsURL['editProjectDetails'] = server + 'labs-project/editDetails';
LabsURL['delete'] = server + 'labs-project/deleteFiles';
//# sourceMappingURL=globals.js.map

/***/ }),

/***/ 63:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_http__ = __webpack_require__(22);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return contentHeaders; });

var contentHeaders = new __WEBPACK_IMPORTED_MODULE_0__angular_http__["c" /* Headers */]();
contentHeaders.append('Accept', 'application/json');
contentHeaders.append('Content-Type', 'application/json');
//# sourceMappingURL=headers.js.map

/***/ }),

/***/ 64:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return Project; });
var Project = (function () {
    function Project() {
        this.settings = "";
        this.projectLocation = "";
        this.asperaSettings = "";
        this.id = "";
        this.title = "";
        this.description = "";
        this.updatedAt = "";
        this.createdAt = "";
        this.isBusy = false;
    }
    Project.prototype.deserialize = function (input) {
        this.settings = input.settings;
        this.projectLocation = input.projectLocation;
        this.asperaSettings = input.asperaSettings;
        this.title = input.title;
        this.description = input.description;
        this.id = input.id;
        this.createdAt = input.createdAt;
        this.updatedAt = input.updatedAt;
        this.isBusy = input.busy;
        return this;
    };
    return Project;
}());

//# sourceMappingURL=project.js.map

/***/ })

},[322]);
//# sourceMappingURL=main.bundle.js.map