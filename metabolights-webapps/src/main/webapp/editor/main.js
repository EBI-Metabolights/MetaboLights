(window["webpackJsonp"] = window["webpackJsonp"] || []).push([["main"],{

/***/ "./src/$$_lazy_route_resource lazy recursive":
/*!**********************************************************!*\
  !*** ./src/$$_lazy_route_resource lazy namespace object ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

function webpackEmptyAsyncContext(req) {
	// Here Promise.resolve().then() is used instead of new Promise() to prevent
	// uncaught exception popping up in devtools
	return Promise.resolve().then(function() {
		var e = new Error("Cannot find module '" + req + "'");
		e.code = 'MODULE_NOT_FOUND';
		throw e;
	});
}
webpackEmptyAsyncContext.keys = function() { return []; };
webpackEmptyAsyncContext.resolve = webpackEmptyAsyncContext;
module.exports = webpackEmptyAsyncContext;
webpackEmptyAsyncContext.id = "./src/$$_lazy_route_resource lazy recursive";

/***/ }),

/***/ "./src/app/app-routing.module.ts":
/*!***************************************!*\
  !*** ./src/app/app-routing.module.ts ***!
  \***************************************/
/*! exports provided: AppRoutingModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppRoutingModule", function() { return AppRoutingModule; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _components_login_login_component__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./components/login/login.component */ "./src/app/components/login/login.component.ts");
/* harmony import */ var _components_console_console_component__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./components/console/console.component */ "./src/app/components/console/console.component.ts");
/* harmony import */ var _components_study_study_component__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./components/study/study.component */ "./src/app/components/study/study.component.ts");
/* harmony import */ var _components_errors_page_not_found_page_not_found_component__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./components/errors/page-not-found/page-not-found.component */ "./src/app/components/errors/page-not-found/page-not-found.component.ts");
/* harmony import */ var _components_errors_server_error_server_error_component__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./components/errors/server-error/server-error.component */ "./src/app/components/errors/server-error/server-error.component.ts");
/* harmony import */ var _auth_guard_service__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./auth-guard.service */ "./src/app/auth-guard.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};








var routes = [
    { path: 'login', component: _components_login_login_component__WEBPACK_IMPORTED_MODULE_2__["LoginComponent"] },
    { path: 'console', canActivate: [_auth_guard_service__WEBPACK_IMPORTED_MODULE_7__["AuthGuard"]], component: _components_console_console_component__WEBPACK_IMPORTED_MODULE_3__["ConsoleComponent"] },
    { path: '', redirectTo: 'console', pathMatch: 'full' },
    { path: 'study', redirectTo: 'console', pathMatch: 'full' },
    { path: 'study/:id', canActivate: [_auth_guard_service__WEBPACK_IMPORTED_MODULE_7__["AuthGuard"]], component: _components_study_study_component__WEBPACK_IMPORTED_MODULE_4__["StudyComponent"] },
    { path: 'study/:id/:tab', canActivate: [_auth_guard_service__WEBPACK_IMPORTED_MODULE_7__["AuthGuard"]], component: _components_study_study_component__WEBPACK_IMPORTED_MODULE_4__["StudyComponent"] },
    { path: 'error', component: _components_errors_server_error_server_error_component__WEBPACK_IMPORTED_MODULE_6__["ServerErrorComponent"] },
    { path: '**', component: _components_errors_page_not_found_page_not_found_component__WEBPACK_IMPORTED_MODULE_5__["PageNotFoundComponent"] }
];
var AppRoutingModule = /** @class */ (function () {
    function AppRoutingModule() {
    }
    AppRoutingModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["NgModule"])({
            imports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"].forRoot(routes)],
            exports: [_angular_router__WEBPACK_IMPORTED_MODULE_1__["RouterModule"]]
        })
    ], AppRoutingModule);
    return AppRoutingModule;
}());



/***/ }),

/***/ "./src/app/app.component.css":
/*!***********************************!*\
  !*** ./src/app/app.component.css ***!
  \***********************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "@import \"https://cdn.materialdesignicons.com/2.1.19/css/materialdesignicons.min.css\";\n\n.card-heading{\n\tpadding: 10px 20px; \n\tmin-height: 3em;\n\tbox-shadow: 0 1px 2px rgba(10, 10, 10, 0.1);\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvYXBwLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUEscUZBQXFGOztBQUVyRjtDQUNDLG1CQUFtQjtDQUNuQixnQkFBZ0I7Q0FDaEIsNENBQTRDO0NBQzVDIiwiZmlsZSI6InNyYy9hcHAvYXBwLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyJAaW1wb3J0IFwiaHR0cHM6Ly9jZG4ubWF0ZXJpYWxkZXNpZ25pY29ucy5jb20vMi4xLjE5L2Nzcy9tYXRlcmlhbGRlc2lnbmljb25zLm1pbi5jc3NcIjtcblxuLmNhcmQtaGVhZGluZ3tcblx0cGFkZGluZzogMTBweCAyMHB4OyBcblx0bWluLWhlaWdodDogM2VtO1xuXHRib3gtc2hhZG93OiAwIDFweCAycHggcmdiYSgxMCwgMTAsIDEwLCAwLjEpO1xufVxuIl19 */"

/***/ }),

/***/ "./src/app/app.component.html":
/*!************************************!*\
  !*** ./src/app/app.component.html ***!
  \************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div>\n\t<mtbls-loading></mtbls-loading>\n\t<router-outlet></router-outlet>\n</div>\n"

/***/ }),

/***/ "./src/app/app.component.ts":
/*!**********************************!*\
  !*** ./src/app/app.component.ts ***!
  \**********************************/
/*! exports provided: AppComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppComponent", function() { return AppComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var AppComponent = /** @class */ (function () {
    function AppComponent() {
    }
    AppComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-root',
            template: __webpack_require__(/*! ./app.component.html */ "./src/app/app.component.html"),
            styles: [__webpack_require__(/*! ./app.component.css */ "./src/app/app.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], AppComponent);
    return AppComponent;
}());



/***/ }),

/***/ "./src/app/app.module.ts":
/*!*******************************!*\
  !*** ./src/app/app.module.ts ***!
  \*******************************/
/*! exports provided: AppModule */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppModule", function() { return AppModule; });
/* harmony import */ var _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/platform-browser */ "./node_modules/@angular/platform-browser/fesm5/platform-browser.js");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_http__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/http */ "./node_modules/@angular/http/fesm5/http.js");
/* harmony import */ var _app_routing_module__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./app-routing.module */ "./src/app/app-routing.module.ts");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_4___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_4__);
/* harmony import */ var _angular_redux_router__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular-redux/router */ "./node_modules/@angular-redux/router/lib/es5/src/index.js");
/* harmony import */ var _angular_redux_router__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_router__WEBPACK_IMPORTED_MODULE_5__);
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _store__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./store */ "./src/app/store.ts");
/* harmony import */ var _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular/platform-browser/animations */ "./node_modules/@angular/platform-browser/fesm5/animations.js");
/* harmony import */ var _angular_material_progress_spinner__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! @angular/material/progress-spinner */ "./node_modules/@angular/material/esm5/progress-spinner.es5.js");
/* harmony import */ var _angular_material_autocomplete__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/material/autocomplete */ "./node_modules/@angular/material/esm5/autocomplete.es5.js");
/* harmony import */ var _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! @angular/material/datepicker */ "./node_modules/@angular/material/esm5/datepicker.es5.js");
/* harmony import */ var _angular_material_input__WEBPACK_IMPORTED_MODULE_12__ = __webpack_require__(/*! @angular/material/input */ "./node_modules/@angular/material/esm5/input.es5.js");
/* harmony import */ var _angular_material_chips__WEBPACK_IMPORTED_MODULE_13__ = __webpack_require__(/*! @angular/material/chips */ "./node_modules/@angular/material/esm5/chips.es5.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_14__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var _angular_material_icon__WEBPACK_IMPORTED_MODULE_15__ = __webpack_require__(/*! @angular/material/icon */ "./node_modules/@angular/material/esm5/icon.es5.js");
/* harmony import */ var _components_study_publications_publication_publication_component__WEBPACK_IMPORTED_MODULE_16__ = __webpack_require__(/*! ./components/study/publications/publication/publication.component */ "./src/app/components/study/publications/publication/publication.component.ts");
/* harmony import */ var _components_study_publications_publications_component__WEBPACK_IMPORTED_MODULE_17__ = __webpack_require__(/*! ./components/study/publications/publications.component */ "./src/app/components/study/publications/publications.component.ts");
/* harmony import */ var _components_study_release_date_release_date_component__WEBPACK_IMPORTED_MODULE_18__ = __webpack_require__(/*! ./components/study/release-date/release-date.component */ "./src/app/components/study/release-date/release-date.component.ts");
/* harmony import */ var _components_study_description_description_component__WEBPACK_IMPORTED_MODULE_19__ = __webpack_require__(/*! ./components/study/description/description.component */ "./src/app/components/study/description/description.component.ts");
/* harmony import */ var _components_study_people_person_person_component__WEBPACK_IMPORTED_MODULE_20__ = __webpack_require__(/*! ./components/study/people/person/person.component */ "./src/app/components/study/people/person/person.component.ts");
/* harmony import */ var _components_study_status_status_component__WEBPACK_IMPORTED_MODULE_21__ = __webpack_require__(/*! ./components/study/status/status.component */ "./src/app/components/study/status/status.component.ts");
/* harmony import */ var _components_study_people_people_component__WEBPACK_IMPORTED_MODULE_22__ = __webpack_require__(/*! ./components/study/people/people.component */ "./src/app/components/study/people/people.component.ts");
/* harmony import */ var _components_loading_loading_component__WEBPACK_IMPORTED_MODULE_23__ = __webpack_require__(/*! ./components/loading/loading.component */ "./src/app/components/loading/loading.component.ts");
/* harmony import */ var _components_study_title_title_component__WEBPACK_IMPORTED_MODULE_24__ = __webpack_require__(/*! ./components/study/title/title.component */ "./src/app/components/study/title/title.component.ts");
/* harmony import */ var _components_study_study_component__WEBPACK_IMPORTED_MODULE_25__ = __webpack_require__(/*! ./components/study/study.component */ "./src/app/components/study/study.component.ts");
/* harmony import */ var _app_component__WEBPACK_IMPORTED_MODULE_26__ = __webpack_require__(/*! ./app.component */ "./src/app/app.component.ts");
/* harmony import */ var _components_study_ontology_ontology_component__WEBPACK_IMPORTED_MODULE_27__ = __webpack_require__(/*! ./components/study/ontology/ontology.component */ "./src/app/components/study/ontology/ontology.component.ts");
/* harmony import */ var _components_study_design_descriptors_design_descriptors_component__WEBPACK_IMPORTED_MODULE_28__ = __webpack_require__(/*! ./components/study/design-descriptors/design-descriptors.component */ "./src/app/components/study/design-descriptors/design-descriptors.component.ts");
/* harmony import */ var _components_study_design_descriptors_design_descriptor_design_descriptor_component__WEBPACK_IMPORTED_MODULE_29__ = __webpack_require__(/*! ./components/study/design-descriptors/design-descriptor/design-descriptor.component */ "./src/app/components/study/design-descriptors/design-descriptor/design-descriptor.component.ts");
/* harmony import */ var _components_study_factors_factors_component__WEBPACK_IMPORTED_MODULE_30__ = __webpack_require__(/*! ./components/study/factors/factors.component */ "./src/app/components/study/factors/factors.component.ts");
/* harmony import */ var _components_study_factors_factor_factor_component__WEBPACK_IMPORTED_MODULE_31__ = __webpack_require__(/*! ./components/study/factors/factor/factor.component */ "./src/app/components/study/factors/factor/factor.component.ts");
/* harmony import */ var _components_study_protocols_protocols_component__WEBPACK_IMPORTED_MODULE_32__ = __webpack_require__(/*! ./components/study/protocols/protocols.component */ "./src/app/components/study/protocols/protocols.component.ts");
/* harmony import */ var _components_study_protocols_protocol_protocol_component__WEBPACK_IMPORTED_MODULE_33__ = __webpack_require__(/*! ./components/study/protocols/protocol/protocol.component */ "./src/app/components/study/protocols/protocol/protocol.component.ts");
/* harmony import */ var _components_shared_content_content_component__WEBPACK_IMPORTED_MODULE_34__ = __webpack_require__(/*! ./components/shared/content/content.component */ "./src/app/components/shared/content/content.component.ts");
/* harmony import */ var _angular_material_table__WEBPACK_IMPORTED_MODULE_35__ = __webpack_require__(/*! @angular/material/table */ "./node_modules/@angular/material/esm5/table.es5.js");
/* harmony import */ var _angular_material_sort__WEBPACK_IMPORTED_MODULE_36__ = __webpack_require__(/*! @angular/material/sort */ "./node_modules/@angular/material/esm5/sort.es5.js");
/* harmony import */ var _angular_material_select__WEBPACK_IMPORTED_MODULE_37__ = __webpack_require__(/*! @angular/material/select */ "./node_modules/@angular/material/esm5/select.es5.js");
/* harmony import */ var _angular_material_radio__WEBPACK_IMPORTED_MODULE_38__ = __webpack_require__(/*! @angular/material/radio */ "./node_modules/@angular/material/esm5/radio.es5.js");
/* harmony import */ var _components_study_assays_assays_component__WEBPACK_IMPORTED_MODULE_39__ = __webpack_require__(/*! ./components/study/assays/assays.component */ "./src/app/components/study/assays/assays.component.ts");
/* harmony import */ var _components_study_assays_assay_assay_component__WEBPACK_IMPORTED_MODULE_40__ = __webpack_require__(/*! ./components/study/assays/assay/assay.component */ "./src/app/components/study/assays/assay/assay.component.ts");
/* harmony import */ var _angular_material_button_toggle__WEBPACK_IMPORTED_MODULE_41__ = __webpack_require__(/*! @angular/material/button-toggle */ "./node_modules/@angular/material/esm5/button-toggle.es5.js");
/* harmony import */ var _components_study_files_files_component__WEBPACK_IMPORTED_MODULE_42__ = __webpack_require__(/*! ./components/study/files/files.component */ "./src/app/components/study/files/files.component.ts");
/* harmony import */ var _components_study_files_file_file_component__WEBPACK_IMPORTED_MODULE_43__ = __webpack_require__(/*! ./components/study/files/file/file.component */ "./src/app/components/study/files/file/file.component.ts");
/* harmony import */ var _components_study_metabolites_metabolites_component__WEBPACK_IMPORTED_MODULE_44__ = __webpack_require__(/*! ./components/study/metabolites/metabolites.component */ "./src/app/components/study/metabolites/metabolites.component.ts");
/* harmony import */ var _components_study_metabolites_maf_maf_component__WEBPACK_IMPORTED_MODULE_45__ = __webpack_require__(/*! ./components/study/metabolites/maf/maf.component */ "./src/app/components/study/metabolites/maf/maf.component.ts");
/* harmony import */ var _angular_material_paginator__WEBPACK_IMPORTED_MODULE_46__ = __webpack_require__(/*! @angular/material/paginator */ "./node_modules/@angular/material/esm5/paginator.es5.js");
/* harmony import */ var _components_shared_upload_upload_component__WEBPACK_IMPORTED_MODULE_47__ = __webpack_require__(/*! ./components/shared/upload/upload.component */ "./src/app/components/shared/upload/upload.component.ts");
/* harmony import */ var _components_shared_download_download_component__WEBPACK_IMPORTED_MODULE_48__ = __webpack_require__(/*! ./components/shared/download/download.component */ "./src/app/components/shared/download/download.component.ts");
/* harmony import */ var _pipes_reverse_pipe__WEBPACK_IMPORTED_MODULE_49__ = __webpack_require__(/*! ./pipes/reverse.pipe */ "./src/app/pipes/reverse.pipe.ts");
/* harmony import */ var _components_study_samples_samples_component__WEBPACK_IMPORTED_MODULE_50__ = __webpack_require__(/*! ./components/study/samples/samples.component */ "./src/app/components/study/samples/samples.component.ts");
/* harmony import */ var _components_login_login_component__WEBPACK_IMPORTED_MODULE_51__ = __webpack_require__(/*! ./components/login/login.component */ "./src/app/components/login/login.component.ts");
/* harmony import */ var _components_errors_server_error_server_error_component__WEBPACK_IMPORTED_MODULE_52__ = __webpack_require__(/*! ./components/errors/server-error/server-error.component */ "./src/app/components/errors/server-error/server-error.component.ts");
/* harmony import */ var _components_errors_page_not_found_page_not_found_component__WEBPACK_IMPORTED_MODULE_53__ = __webpack_require__(/*! ./components/errors/page-not-found/page-not-found.component */ "./src/app/components/errors/page-not-found/page-not-found.component.ts");
/* harmony import */ var _auth_guard_service__WEBPACK_IMPORTED_MODULE_54__ = __webpack_require__(/*! ./auth-guard.service */ "./src/app/auth-guard.service.ts");
/* harmony import */ var _components_console_console_component__WEBPACK_IMPORTED_MODULE_55__ = __webpack_require__(/*! ./components/console/console.component */ "./src/app/components/console/console.component.ts");
/* harmony import */ var _components_shared_nav_bar_nav_bar_component__WEBPACK_IMPORTED_MODULE_56__ = __webpack_require__(/*! ./components/shared/nav-bar/nav-bar.component */ "./src/app/components/shared/nav-bar/nav-bar.component.ts");
/* harmony import */ var ngx_wig__WEBPACK_IMPORTED_MODULE_57__ = __webpack_require__(/*! ngx-wig */ "./node_modules/ngx-wig/ngx-wig.umd.js");
/* harmony import */ var ngx_wig__WEBPACK_IMPORTED_MODULE_57___default = /*#__PURE__*/__webpack_require__.n(ngx_wig__WEBPACK_IMPORTED_MODULE_57__);
/* harmony import */ var _components_study_organisms_organisms_component__WEBPACK_IMPORTED_MODULE_58__ = __webpack_require__(/*! ./components/study/organisms/organisms.component */ "./src/app/components/study/organisms/organisms.component.ts");
/* harmony import */ var _components_study_organisms_organism_organism_component__WEBPACK_IMPORTED_MODULE_59__ = __webpack_require__(/*! ./components/study/organisms/organism/organism.component */ "./src/app/components/study/organisms/organism/organism.component.ts");
/* harmony import */ var _components_tour_tour_component__WEBPACK_IMPORTED_MODULE_60__ = __webpack_require__(/*! ./components/tour/tour.component */ "./src/app/components/tour/tour.component.ts");
/* harmony import */ var _components_shared_upload_ftp_ftp_component__WEBPACK_IMPORTED_MODULE_61__ = __webpack_require__(/*! ./components/shared/upload/ftp/ftp.component */ "./src/app/components/shared/upload/ftp/ftp.component.ts");
/* harmony import */ var _components_shared_upload_aspera_aspera_component__WEBPACK_IMPORTED_MODULE_62__ = __webpack_require__(/*! ./components/shared/upload/aspera/aspera.component */ "./src/app/components/shared/upload/aspera/aspera.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






















































// Auth services









var AppModule = /** @class */ (function () {
    function AppModule(ngRedux, devTools, ngReduxRouter) {
        var enhancers = (Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["isDevMode"])() && devTools.enhancer() != null) ? [devTools.enhancer()] : [];
        ngRedux.configureStore(_store__WEBPACK_IMPORTED_MODULE_7__["rootReducer"], _store__WEBPACK_IMPORTED_MODULE_7__["INITIAL_STATE"], [], enhancers);
    }
    AppModule = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["NgModule"])({
            declarations: [
                _app_component__WEBPACK_IMPORTED_MODULE_26__["AppComponent"],
                _components_study_study_component__WEBPACK_IMPORTED_MODULE_25__["StudyComponent"],
                _components_loading_loading_component__WEBPACK_IMPORTED_MODULE_23__["LoadingComponent"],
                _components_study_title_title_component__WEBPACK_IMPORTED_MODULE_24__["TitleComponent"],
                _components_study_description_description_component__WEBPACK_IMPORTED_MODULE_19__["DescriptionComponent"],
                _components_study_people_people_component__WEBPACK_IMPORTED_MODULE_22__["PeopleComponent"],
                _components_study_people_person_person_component__WEBPACK_IMPORTED_MODULE_20__["PersonComponent"],
                _components_study_publications_publications_component__WEBPACK_IMPORTED_MODULE_17__["PublicationsComponent"],
                _components_study_publications_publication_publication_component__WEBPACK_IMPORTED_MODULE_16__["PublicationComponent"],
                _components_study_status_status_component__WEBPACK_IMPORTED_MODULE_21__["StatusComponent"],
                _components_study_release_date_release_date_component__WEBPACK_IMPORTED_MODULE_18__["ReleaseDateComponent"],
                _components_study_ontology_ontology_component__WEBPACK_IMPORTED_MODULE_27__["OntologyComponent"],
                _components_study_design_descriptors_design_descriptors_component__WEBPACK_IMPORTED_MODULE_28__["DesignDescriptorsComponent"],
                _components_study_design_descriptors_design_descriptor_design_descriptor_component__WEBPACK_IMPORTED_MODULE_29__["DesignDescriptorComponent"],
                _components_study_factors_factors_component__WEBPACK_IMPORTED_MODULE_30__["FactorsComponent"],
                _components_study_factors_factor_factor_component__WEBPACK_IMPORTED_MODULE_31__["FactorComponent"],
                _components_study_protocols_protocols_component__WEBPACK_IMPORTED_MODULE_32__["ProtocolsComponent"],
                _components_study_protocols_protocol_protocol_component__WEBPACK_IMPORTED_MODULE_33__["ProtocolComponent"],
                _components_shared_content_content_component__WEBPACK_IMPORTED_MODULE_34__["ContentComponent"],
                _components_study_assays_assays_component__WEBPACK_IMPORTED_MODULE_39__["AssaysComponent"],
                _components_study_assays_assay_assay_component__WEBPACK_IMPORTED_MODULE_40__["AssayComponent"],
                _components_study_files_files_component__WEBPACK_IMPORTED_MODULE_42__["FilesComponent"],
                _components_study_files_file_file_component__WEBPACK_IMPORTED_MODULE_43__["FileComponent"],
                _components_study_metabolites_metabolites_component__WEBPACK_IMPORTED_MODULE_44__["MetabolitesComponent"],
                _components_study_metabolites_maf_maf_component__WEBPACK_IMPORTED_MODULE_45__["MafComponent"],
                _components_shared_upload_upload_component__WEBPACK_IMPORTED_MODULE_47__["UploadComponent"],
                _components_shared_download_download_component__WEBPACK_IMPORTED_MODULE_48__["DownloadComponent"],
                _pipes_reverse_pipe__WEBPACK_IMPORTED_MODULE_49__["ReversePipe"],
                _components_study_samples_samples_component__WEBPACK_IMPORTED_MODULE_50__["SamplesComponent"],
                _components_errors_page_not_found_page_not_found_component__WEBPACK_IMPORTED_MODULE_53__["PageNotFoundComponent"],
                _components_login_login_component__WEBPACK_IMPORTED_MODULE_51__["LoginComponent"],
                _components_errors_server_error_server_error_component__WEBPACK_IMPORTED_MODULE_52__["ServerErrorComponent"],
                _components_console_console_component__WEBPACK_IMPORTED_MODULE_55__["ConsoleComponent"],
                _components_shared_nav_bar_nav_bar_component__WEBPACK_IMPORTED_MODULE_56__["NavBarComponent"],
                _components_study_organisms_organisms_component__WEBPACK_IMPORTED_MODULE_58__["OrganismsComponent"],
                _components_study_organisms_organism_organism_component__WEBPACK_IMPORTED_MODULE_59__["OrganismComponent"],
                _components_tour_tour_component__WEBPACK_IMPORTED_MODULE_60__["TourComponent"],
                _components_shared_upload_ftp_ftp_component__WEBPACK_IMPORTED_MODULE_61__["FtpComponent"],
                _components_shared_upload_aspera_aspera_component__WEBPACK_IMPORTED_MODULE_62__["AsperaComponent"]
            ],
            imports: [
                _angular_platform_browser__WEBPACK_IMPORTED_MODULE_0__["BrowserModule"],
                _angular_http__WEBPACK_IMPORTED_MODULE_2__["HttpModule"],
                _angular_redux_store__WEBPACK_IMPORTED_MODULE_4__["NgReduxModule"],
                _angular_platform_browser_animations__WEBPACK_IMPORTED_MODULE_8__["BrowserAnimationsModule"],
                _angular_material_icon__WEBPACK_IMPORTED_MODULE_15__["MatIconModule"],
                _angular_material_input__WEBPACK_IMPORTED_MODULE_12__["MatInputModule"],
                _angular_material_chips__WEBPACK_IMPORTED_MODULE_13__["MatChipsModule"],
                _angular_material_progress_spinner__WEBPACK_IMPORTED_MODULE_9__["MatProgressSpinnerModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_6__["ReactiveFormsModule"],
                _angular_material_autocomplete__WEBPACK_IMPORTED_MODULE_10__["MatAutocompleteModule"],
                _angular_material_datepicker__WEBPACK_IMPORTED_MODULE_11__["MatDatepickerModule"],
                _angular_material__WEBPACK_IMPORTED_MODULE_14__["MatNativeDateModule"],
                _angular_material_table__WEBPACK_IMPORTED_MODULE_35__["MatTableModule"],
                _angular_material_sort__WEBPACK_IMPORTED_MODULE_36__["MatSortModule"],
                _angular_material_select__WEBPACK_IMPORTED_MODULE_37__["MatSelectModule"],
                _angular_material_radio__WEBPACK_IMPORTED_MODULE_38__["MatRadioModule"],
                _angular_material_button_toggle__WEBPACK_IMPORTED_MODULE_41__["MatButtonToggleModule"],
                _angular_material_paginator__WEBPACK_IMPORTED_MODULE_46__["MatPaginatorModule"],
                _app_routing_module__WEBPACK_IMPORTED_MODULE_3__["AppRoutingModule"],
                _angular_forms__WEBPACK_IMPORTED_MODULE_6__["FormsModule"],
                ngx_wig__WEBPACK_IMPORTED_MODULE_57__["NgxWigModule"],
                _angular_redux_router__WEBPACK_IMPORTED_MODULE_5__["NgReduxRouterModule"].forRoot()
            ],
            providers: [
                _auth_guard_service__WEBPACK_IMPORTED_MODULE_54__["AuthGuard"]
            ],
            bootstrap: [_app_component__WEBPACK_IMPORTED_MODULE_26__["AppComponent"]]
        }),
        __metadata("design:paramtypes", [_angular_redux_store__WEBPACK_IMPORTED_MODULE_4__["NgRedux"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_4__["DevToolsExtension"], _angular_redux_router__WEBPACK_IMPORTED_MODULE_5__["NgReduxRouter"]])
    ], AppModule);
    return AppModule;
}());



/***/ }),

/***/ "./src/app/auth-guard.service.ts":
/*!***************************************!*\
  !*** ./src/app/auth-guard.service.ts ***!
  \***************************************/
/*! exports provided: AuthGuard */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthGuard", function() { return AuthGuard; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _services_metabolights_auth_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./services/metabolights/auth.service */ "./src/app/services/metabolights/auth.service.ts");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_3___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_3__);
/* harmony import */ var _services_headers__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./services/headers */ "./src/app/services/headers.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var AuthGuard = /** @class */ (function () {
    function AuthGuard(authService, router, ngRedux) {
        this.authService = authService;
        this.router = router;
        this.ngRedux = ngRedux;
    }
    AuthGuard.prototype.canActivate = function (route, state) {
        var url = state.url;
        return this.checkLogin(url);
    };
    AuthGuard.prototype.canActivateChild = function (route, state) {
        return this.canActivate(route, state);
    };
    AuthGuard.prototype.checkLogin = function (url) {
        var user = JSON.parse(JSON.stringify(this.ngRedux.getState().status['user']));
        if (user == null) {
            var localUser = localStorage.getItem('user');
            if (localUser != null) {
                _services_headers__WEBPACK_IMPORTED_MODULE_4__["contentHeaders"].set('user_token', JSON.parse(localUser).apiToken);
                this.ngRedux.dispatch({ type: 'SET_USER', body: {
                        'user': localUser
                    } });
                // localStorage.removeItem('user');
                return true;
            }
        }
        else {
            _services_headers__WEBPACK_IMPORTED_MODULE_4__["contentHeaders"].set('user_token', user.apiToken);
            return true;
        }
        // Store the attempted URL for redirecting
        this.authService.redirectUrl = url;
        // Navigate to the login page with extras
        this.router.navigate(['/login']);
        return false;
    };
    AuthGuard = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_services_metabolights_auth_service__WEBPACK_IMPORTED_MODULE_2__["AuthService"], _angular_router__WEBPACK_IMPORTED_MODULE_1__["Router"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_3__["NgRedux"]])
    ], AuthGuard);
    return AuthGuard;
}());



/***/ }),

/***/ "./src/app/components/console/console.component.css":
/*!**********************************************************!*\
  !*** ./src/app/components/console/console.component.css ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".is-vertical-center {\n  display: flex;\n  align-items: center;\n}\n\n.panel-block:hover{\n\tbackground-color: white;\n}\n\n.logout-link{\n\tfont-size: 0.8em;\n\tfloat: right;\n\tpadding: 3px 10px;\n\tbackground-color: #3676D9;\n\tmargin: 1px;\n\tcolor: #fff;\n\tmargin-top: -2em;\n}\n\n.logout-link:hover{\n\tbackground-color: #1f58ad;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9jb25zb2xlL2NvbnNvbGUuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLGNBQWM7RUFDZCxvQkFBb0I7Q0FDckI7O0FBRUQ7Q0FDQyx3QkFBd0I7Q0FDeEI7O0FBRUQ7Q0FDQyxpQkFBaUI7Q0FDakIsYUFBYTtDQUNiLGtCQUFrQjtDQUNsQiwwQkFBMEI7Q0FDMUIsWUFBWTtDQUNaLFlBQVk7Q0FDWixpQkFBaUI7Q0FDakI7O0FBRUQ7Q0FDQywwQkFBMEI7Q0FDMUIiLCJmaWxlIjoic3JjL2FwcC9jb21wb25lbnRzL2NvbnNvbGUvY29uc29sZS5jb21wb25lbnQuY3NzIiwic291cmNlc0NvbnRlbnQiOlsiLmlzLXZlcnRpY2FsLWNlbnRlciB7XG4gIGRpc3BsYXk6IGZsZXg7XG4gIGFsaWduLWl0ZW1zOiBjZW50ZXI7XG59XG5cbi5wYW5lbC1ibG9jazpob3Zlcntcblx0YmFja2dyb3VuZC1jb2xvcjogd2hpdGU7XG59XG5cbi5sb2dvdXQtbGlua3tcblx0Zm9udC1zaXplOiAwLjhlbTtcblx0ZmxvYXQ6IHJpZ2h0O1xuXHRwYWRkaW5nOiAzcHggMTBweDtcblx0YmFja2dyb3VuZC1jb2xvcjogIzM2NzZEOTtcblx0bWFyZ2luOiAxcHg7XG5cdGNvbG9yOiAjZmZmO1xuXHRtYXJnaW4tdG9wOiAtMmVtO1xufVxuXG4ubG9nb3V0LWxpbms6aG92ZXJ7XG5cdGJhY2tncm91bmQtY29sb3I6ICMxZjU4YWQ7XG59Il19 */"

/***/ }),

/***/ "./src/app/components/console/console.component.html":
/*!***********************************************************!*\
  !*** ./src/app/components/console/console.component.html ***!
  \***********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"columns\" style=\"min-height: 100vh; margin: 40px 0;\">\n\t<div class=\"column is-half is-offset-one-quarter\">\n\t\t<!-- <a class=\"logout-link\" (click)=\"logOut()\">Logout</a> -->\n\t\t<nav class=\"panel\">\n\t\t\t<p class=\"panel-heading\">\n\t\t\t\tMy Studies ( {{ studies.length }} )\n\t\t\t</p>\n\t\t\t<div class=\"panel-block\">\n\t\t\t\t<p class=\"control\">\n\t\t\t\t\t<input [disabled]=\"loadingStudies\" (keyup)=\"applyFilter($event.target.value)\" class=\"input is-small\" type=\"text\" placeholder=\"search\">\n\t\t\t\t\t<!-- <span class=\"icon is-small is-left\">\n\t\t\t\t\t\t<i class=\"fas fa-search\" aria-hidden=\"true\"></i>\n\t\t\t\t\t</span> -->\n\t\t\t\t</p>\n\t\t\t</div>\n\t\t\t<!-- <p class=\"panel-tabs\">\n\t\t\t\t<a class=\"is-active\">all</a>\n\t\t\t\t<a>public</a>\n\t\t\t\t<a>private</a>\n\t\t\t\t<a>sources</a>\n\t\t\t\t<a>forks</a>\n\t\t\t</p> -->\n\t\t\t<a [routerLink]=\"['/create']\" class=\"panel-block\">\n\t\t\t\t<span class=\"has-text-link\">&nbsp;&nbsp;&nbsp;&nbsp;+ Create new study</span>\n\t\t\t</a>\n\t\t\t<div *ngFor=\"let study of filteredStudies\">\n\t\t\t\t<a [routerLink]=\"['/study', study.id]\" class=\"panel-block\">\n\t\t\t\t\t<span>\n\t\t\t\t\t\t<p class=\"has-text-link\"><b>{{ study.id }}: {{ study.title }}</b></p>\t\n\t\t\t\t\t\t<p><small>{{ study.description }}</small></p>\n\t\t\t\t\t</span>\n\t\t\t\t</a>\n\t\t\t</div>\n\t\t</nav>\t\n\t\t<p *ngIf=\"loadingStudies\" class=\"has-text-centered\">\n\t\t\t<img src=\"/assets/img/loading.svg\"><br>\n\t\t\tLoading studies\n\t\t</p>\n\t</div>\n</div>"

/***/ }),

/***/ "./src/app/components/console/console.component.ts":
/*!*********************************************************!*\
  !*** ./src/app/components/console/console.component.ts ***!
  \*********************************************************/
/*! exports provided: ConsoleComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ConsoleComponent", function() { return ConsoleComponent; });
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_http__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/http */ "./node_modules/@angular/http/fesm5/http.js");
/* harmony import */ var _services_metabolights_auth_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../services/metabolights/auth.service */ "./src/app/services/metabolights/auth.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var ConsoleComponent = /** @class */ (function () {
    function ConsoleComponent(router, authService, http, ngRedux) {
        this.router = router;
        this.authService = authService;
        this.http = http;
        this.ngRedux = ngRedux;
        this.studies = [];
        this.filteredStudies = [];
        this.loadingStudies = false;
    }
    ConsoleComponent.prototype.ngOnInit = function () {
        this.getUserStudies();
    };
    ConsoleComponent.prototype.ngAfterContentInit = function () {
        //this.getUserStudies();
    };
    ConsoleComponent.prototype.applyFilter = function (value) {
        var _this = this;
        if (value != '') {
            this.filteredStudies = this.studies.filter(function (s) {
                if (value != '') {
                    return _this.getString(s).indexOf(value) != -1;
                }
                else {
                    return true;
                }
            });
        }
    };
    ConsoleComponent.prototype.getString = function (s) {
        return s.id + ' ' + s.title + ' ' + s.description;
    };
    // Studies list
    ConsoleComponent.prototype.getUserStudies = function () {
        var _this = this;
        this.loadingStudies = true;
        this.authService.getUserStudies().subscribe(function (response) {
            _this.studies = JSON.parse(response.content);
            _this.filteredStudies = _this.studies;
            _this.loadingStudies = false;
        }, function (error) {
        });
    };
    ConsoleComponent.prototype.logOut = function () {
        this.authService.logout();
    };
    ConsoleComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_2__["Component"])({
            selector: 'mtbls-console',
            template: __webpack_require__(/*! ./console.component.html */ "./src/app/components/console/console.component.html"),
            styles: [__webpack_require__(/*! ./console.component.css */ "./src/app/components/console/console.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_0__["Router"], _services_metabolights_auth_service__WEBPACK_IMPORTED_MODULE_4__["AuthService"], _angular_http__WEBPACK_IMPORTED_MODULE_3__["Http"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["NgRedux"]])
    ], ConsoleComponent);
    return ConsoleComponent;
}());



/***/ }),

/***/ "./src/app/components/errors/page-not-found/page-not-found.component.css":
/*!*******************************************************************************!*\
  !*** ./src/app/components/errors/page-not-found/page-not-found.component.css ***!
  \*******************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".is-vertical-center {\n  display: flex;\n  align-items: center;\n}\n\n.display-message-wrapper{\n\tpadding: 5em;\n}\n\n.display-message-title{\n\tfont-size: 8em;\n\tdisplay: block;\n\tmargin-bottom: 0;\n\tline-height: 1em;\n}\n\n.display-message-content{\n\tfont-size: 1em;\n\ttext-transform:\tuppercase;\n\tletter-spacing: 0.5em;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9lcnJvcnMvcGFnZS1ub3QtZm91bmQvcGFnZS1ub3QtZm91bmQuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLGNBQWM7RUFDZCxvQkFBb0I7Q0FDckI7O0FBRUQ7Q0FDQyxhQUFhO0NBQ2I7O0FBRUQ7Q0FDQyxlQUFlO0NBQ2YsZUFBZTtDQUNmLGlCQUFpQjtDQUNqQixpQkFBaUI7Q0FDakI7O0FBRUQ7Q0FDQyxlQUFlO0NBQ2YsMEJBQTBCO0NBQzFCLHNCQUFzQjtDQUN0QiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvZXJyb3JzL3BhZ2Utbm90LWZvdW5kL3BhZ2Utbm90LWZvdW5kLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyIuaXMtdmVydGljYWwtY2VudGVyIHtcbiAgZGlzcGxheTogZmxleDtcbiAgYWxpZ24taXRlbXM6IGNlbnRlcjtcbn1cblxuLmRpc3BsYXktbWVzc2FnZS13cmFwcGVye1xuXHRwYWRkaW5nOiA1ZW07XG59XG5cbi5kaXNwbGF5LW1lc3NhZ2UtdGl0bGV7XG5cdGZvbnQtc2l6ZTogOGVtO1xuXHRkaXNwbGF5OiBibG9jaztcblx0bWFyZ2luLWJvdHRvbTogMDtcblx0bGluZS1oZWlnaHQ6IDFlbTtcbn1cblxuLmRpc3BsYXktbWVzc2FnZS1jb250ZW50e1xuXHRmb250LXNpemU6IDFlbTtcblx0dGV4dC10cmFuc2Zvcm06XHR1cHBlcmNhc2U7XG5cdGxldHRlci1zcGFjaW5nOiAwLjVlbTtcbn0iXX0= */"

/***/ }),

/***/ "./src/app/components/errors/page-not-found/page-not-found.component.html":
/*!********************************************************************************!*\
  !*** ./src/app/components/errors/page-not-found/page-not-found.component.html ***!
  \********************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"columns\">\n\t<div class=\"column is-half is-vertical-center\" style=\"height: 100vh; justify-content: right;\">\n\t\t<svg height=\"50vh\" viewBox=\"-16 1 511 511.99898\" width=\"511pt\" xmlns=\"http://www.w3.org/2000/svg\"><path d=\"m267.921875 385.722656c0 19.859375-16.097656 35.957032-35.957031 35.957032h-108.773438c-48.976562 0-88.828125-39.84375-88.828125-88.816407v-113.285156c0-19.859375 16.105469-35.957031 35.96875-35.957031 5.394531 0 10.507813 1.1875 15.097657 3.320312 12.324218 5.699219 20.859374 18.171875 20.859374 32.636719v113.285156c0 9.308594 7.582032 16.890625 16.902344 16.890625h108.773438c19.859375 0 35.957031 16.105469 35.957031 35.96875zm0 0\" fill=\"#35e298\"/><path d=\"m267.921875 385.722656c0 19.859375-16.097656 35.957032-35.957031 35.957032h-78.574219c-48.972656 0-88.828125-39.84375-88.828125-88.816407v-113.285156c0-14.464844 8.546875-26.9375 20.867188-32.636719 12.324218 5.699219 20.859374 18.171875 20.859374 32.636719v113.285156c0 9.308594 7.582032 16.890625 16.902344 16.890625h108.773438c19.859375 0 35.957031 16.105469 35.957031 35.96875zm0 0\" fill=\"#7af4ab\"/><path d=\"m309.6875 104.394531v386.808594c0 7.316406-5.9375 13.246094-13.257812 13.246094h-104.382813c-7.328125 0-13.257813-5.929688-13.257813-13.246094v-386.808594c0-18 7.371094-34.355469 19.230469-46.226562 11.855469-11.859375 28.226563-19.214844 46.214844-19.214844 5.195313 0 10.257813.613281 15.109375 1.769531 28.78125 6.867188 50.34375 32.867188 50.34375 63.671875zm0 0\" fill=\"#35e298\"/><path d=\"m309.6875 104.394531v386.808594c0 7.316406-5.9375 13.246094-13.257812 13.246094h-74.179688c-7.328125 0-13.257812-5.929688-13.257812-13.246094v-386.808594c0-18 7.367187-34.355469 19.226562-46.226562 8.4375-8.433594 19.15625-14.597657 31.125-17.445313 28.78125 6.867188 50.34375 32.867188 50.34375 63.671875zm0 0\" fill=\"#7af4ab\"/><path d=\"m443.75 109.691406v92.867188c0 40.144531-32.667969 72.820312-72.816406 72.820312h-58.980469c-16.277344 0-29.476563-13.207031-29.476563-29.484375 0-16.289062 13.199219-29.484375 29.476563-29.484375h58.980469c7.640625 0 13.855468-6.210937 13.855468-13.851562v-92.867188c0-16.289062 13.195313-29.484375 29.484376-29.484375 5.515624 0 10.679687 1.519531 15.097656 4.15625 8.617187 5.152344 14.378906 14.566407 14.378906 25.328125zm0 0\" fill=\"#35e298\"/><path d=\"m443.75 109.691406v92.867188c0 40.144531-32.667969 72.820312-72.816406 72.820312h-58.980469c-16.277344 0-29.476563-13.207031-29.476563-29.484375 0-16.289062 13.199219-29.484375 29.476563-29.484375h89.183594c7.640625 0 13.851562-6.210937 13.851562-13.851562v-92.867188c0-10.773437 5.765625-20.183594 14.382813-25.328125 8.617187 5.152344 14.378906 14.566407 14.378906 25.328125zm0 0\" fill=\"#7af4ab\"/><path d=\"m471.339844 174.988281c4.171875 0 7.550781-3.378906 7.550781-7.550781 0-4.167969-3.378906-7.546875-7.550781-7.546875h-20.042969v-35.558594h20.042969c4.171875 0 7.550781-3.378906 7.550781-7.546875 0-4.171875-3.378906-7.550781-7.550781-7.550781h-20.054688c-.214844-17.636719-12.800781-32.339844-29.464844-35.804687v-19.542969c0-4.167969-3.382812-7.550781-7.550781-7.550781s-7.550781 3.382812-7.550781 7.550781v19.542969c-16.8125 3.496093-29.480469 18.421874-29.480469 36.261718v30.992188h-21.148437c-4.167969 0-7.550782 3.378906-7.550782 7.550781 0 4.167969 3.382813 7.546875 7.550782 7.546875h21.148437v7.738281c0 4.175781 3.382813 7.546875 7.550781 7.546875 4.164063 0 7.546876-3.371094 7.546876-7.546875v-53.828125c0-12.097656 9.832031-21.929687 21.925781-21.9375h.015625c12.089844.003906 21.921875 9.839844 21.921875 21.9375v92.867188c0 35.988281-29.273438 65.269531-65.265625 65.269531h-53.695313v-43.871094h53.695313c11.800781 0 21.402344-9.601562 21.402344-21.398437v-8.839844c0-4.167969-3.382813-7.550781-7.546876-7.550781-4.167968 0-7.550781 3.382812-7.550781 7.550781v8.839844c0 3.472656-2.828125 6.300781-6.304687 6.300781h-53.695313v-23.519531h20.964844c4.171875 0 7.550781-3.378906 7.550781-7.550782 0-4.167968-3.378906-7.546874-7.550781-7.546874h-20.964844v-40.574219c1.667969.398437.527344.132812 28.074219.210937 4.167969 0 7.550781-3.378906 7.550781-7.546875 0-4.171875-3.382812-7.550781-7.550781-7.550781-27.792969.078125-26.4375-.179688-28.074219.210938-.558593-6.246094 2.679688-24.097657-8.835937-45.289063l15.929687-15.925781c2.949219-2.949219 2.949219-7.730469 0-10.679688-2.949219-2.949218-7.730469-2.949218-10.679687 0l-13.953125 13.953125c-2.515625-2.941406-5.28125-5.710937-8.28125-8.265625-3.179688-2.695312-7.941407-2.3125-10.652344.867188-2.695313 3.171875-2.3125 7.941406.867187 10.640625 13.027344 11.074219 20.507813 27.148437 20.507813 44.101562v386.808594c0 3.140625-2.558594 5.695313-5.710937 5.695313h-104.382813c-3.148437 0-5.707031-2.554688-5.707031-5.695313 0-78.242187 0-301.605469 0-386.808594 0-37.511719 35.367187-65.226562 71.746094-56.210937 4.046874 1.003906 8.144531-1.460938 9.152343-5.507813.996094-4.046875-1.472656-8.144531-5.519531-9.148437-3.257812-.808594-6.578125-1.378906-9.929688-1.726563v-24.25c0-4.171875-3.378906-7.550781-7.550781-7.550781-4.167969 0-7.546875 3.378906-7.546875 7.550781v24.238281c-12.363281 1.277344-23.816406 5.65625-33.574218 12.335938l-15.59375-15.59375c-2.949219-2.949219-7.730469-2.949219-10.675782 0-2.949218 2.949219-2.949218 7.726562 0 10.675781l14.722656 14.726563c-11.234374 11.71875-18.601562 27.15625-20.046874 44.265625h-19.050782c-4.167968 0-7.550781 3.382812-7.550781 7.550781 0 4.171875 3.382813 7.550781 7.550781 7.550781h18.769532v69.808594h-18.769532c-4.167968 0-7.550781 3.382813-7.550781 7.550781 0 4.171875 3.382813 7.550782 7.550781 7.550782h18.769532v69.8125h-18.769532c-4.167968 0-7.550781 3.378906-7.550781 7.550781 0 4.167969 3.382813 7.546875 7.550781 7.546875h18.769532v59.082031h-48.046876c-5.15625 0-9.355468-4.1875-9.355468-9.339844v-15.355469h19.34375c4.167968 0 7.550781-3.378906 7.550781-7.550781 0-4.167969-3.382813-7.546875-7.550781-7.546875h-19.34375v-49.433594h19.34375c4.167968 0 7.550781-3.382812 7.550781-7.550781 0-4.171875-3.382813-7.550781-7.550781-7.550781h-19.34375v-18.304688c0-.59375-.007813-1.179687-.027344-1.773437-.171875-4.167969-3.695312-7.378906-7.851562-7.238281-4.167969.164062-7.410157 3.675781-7.238282 7.84375.03125 1.222656.007813 6.269531.019532 114.460937 0 13.476563 10.972656 24.441407 24.453124 24.441407h48.046876v56.824218h-48.046876c-44.820312 0-81.28125-36.460937-81.28125-81.265625 0-3.105469 0-2.527343 0-113.285156 0-24.125 28.210938-37.039063 46.5-21.914063 3.21875 2.65625 7.972657 2.203126 10.628907-1.007812s2.203125-7.972656-1.007813-10.628906c-5.816406-4.808594-12.792968-7.996094-20.167968-9.296875v-19.429688c0-4.167969-3.378907-7.550781-7.546876-7.550781-4.171874 0-7.550781 3.382812-7.550781 7.550781v19.441407c-20.402343 3.59375-35.957031 21.429687-35.957031 42.835937v18.296875h-18.761719c-4.171875 0-7.550781 3.378906-7.550781 7.550781 0 4.167969 3.378906 7.550781 7.550781 7.550781h18.761719v59.777344h-18.761719c-4.171875 0-7.550781 3.378906-7.550781 7.550782 0 4.167968 3.378906 7.546874 7.550781 7.546874h18.761719c.367188 2.578126-2.941406 30.636719 16.945312 59.519532l-22.890624 21.148437c-3.0625 2.832031-3.253907 7.605469-.421876 10.667969 2.816407 3.050781 7.59375 3.265625 10.667969.421875l22.167969-20.480469c13.902344 14.652344 32.34375 24.953125 53.050781 28.625v20.246094c0 4.171875 3.382813 7.550781 7.550781 7.550781 4.167969 0 7.550782-3.378906 7.550782-7.550781v-18.792969c.585937.011719 1.171875.023438 1.761718.023438h48.046876v61.96875c0 11.46875 9.332031 20.800781 20.808593 20.800781h104.382813c11.476562 0 20.808594-9.332031 20.808594-20.800781v-208.269531h25.570312v17.550781c0 4.167969 3.378906 7.550781 7.550781 7.550781 4.167969 0 7.546875-3.382812 7.546875-7.550781v-17.550781c7.15625-.804688 31.640625 4.039062 57.9375-13.753907 27.015625 25.945313 26.644532 29.042969 32.171875 29.042969 6.667969 0 10.109375-8.121094 5.339844-12.886719l-25.800781-25.800781c10-9.941406 17.402344-22.492188 21.097656-36.527344h22.203125c4.171875 0 7.550781-3.382812 7.550781-7.550781 0-4.171875-3.378906-7.550781-7.550781-7.550781h-19.757813c.355469-5.351563.128907-5.554688.199219-32.914063zm0 0\"/><path d=\"m222.035156 104.242188c6.050782 0 11.003906-4.953126 11.003906-11.003907s-4.953124-11-11.003906-11c-6.050781 0-11 4.949219-11 11s4.949219 11.003907 11 11.003907zm0 0\"/><path d=\"m254.195312 130.6875c0 6.050781 4.953126 11.003906 11.003907 11.003906s11-4.953125 11-11.003906-4.949219-11-11-11-11.003907 4.949219-11.003907 11zm0 0\"/><path d=\"m211.035156 336.988281c0 6.050781 4.949219 11 11 11 6.050782 0 11.003906-4.949219 11.003906-11s-4.953124-11.003906-11.003906-11.003906c-6.050781 0-11 4.953125-11 11.003906zm0 0\"/><path d=\"m265.199219 385.4375c6.050781 0 11-4.949219 11-11s-4.949219-11.003906-11-11.003906-11.003907 4.953125-11.003907 11.003906 4.953126 11 11.003907 11zm0 0\"/><path d=\"m225.84375 172.050781c-6.050781 0-11.003906 4.953125-11.003906 11.003907 0 6.050781 4.953125 11 11.003906 11s11-4.949219 11-11c0-6.050782-4.949219-11.003907-11-11.003907zm0 0\"/><path d=\"m261.179688 256c0-6.050781-4.953126-11-11.003907-11s-11 4.949219-11 11 4.949219 11.003906 11 11.003906 11.003907-4.953125 11.003907-11.003906zm0 0\"/><path d=\"m225.84375 462.390625c6.050781 0 11-4.949219 11-11 0-6.054687-4.949219-11.003906-11-11.003906s-11.003906 4.949219-11.003906 11.003906c0 6.050781 4.953125 11 11.003906 11zm0 0\"/><path d=\"m272.179688 468.410156c6.050781 0 11.003906-4.949218 11.003906-11 0-6.050781-4.953125-11.003906-11.003906-11.003906-6.050782 0-11 4.953125-11 11.003906 0 6.050782 4.949218 11 11 11zm0 0\"/><path d=\"m70.039062 235.914062c6.054688 0 11.003907-4.953124 11.003907-11.003906 0-6.050781-4.949219-11-11.003907-11-6.050781 0-11 4.949219-11 11 0 6.050782 4.949219 11.003906 11 11.003906zm0 0\"/><path d=\"m89.097656 283.800781c0-6.050781-4.953125-11-11.003906-11s-11 4.949219-11 11 4.949219 11.003907 11 11.003907 11.003906-4.953126 11.003906-11.003907zm0 0\"/><path d=\"m50.984375 336.988281c0 6.050781 4.949219 11 11.003906 11 6.050781 0 11-4.949219 11-11s-4.949219-11.003906-11-11.003906-11.003906 4.953125-11.003906 11.003906zm0 0\"/><path d=\"m104.769531 391.277344c6.054688 0 11.003907-4.949219 11.003907-11.003906 0-6.050782-4.949219-11-11.003907-11-6.050781 0-11 4.949218-11 11 0 6.054687 4.949219 11.003906 11 11.003906zm0 0\"/><path d=\"m414.269531 127.785156c6.050781 0 11.003907-4.949218 11.003907-11 0-6.050781-4.953126-11.003906-11.003907-11.003906s-11 4.953125-11 11.003906c0 6.050782 4.949219 11 11 11zm0 0\"/><path d=\"m350.359375 234.421875c-6.050781 0-11 4.953125-11 11.003906s4.949219 11 11 11 11.003906-4.949219 11.003906-11-4.953125-11.003906-11.003906-11.003906zm0 0\"/></svg>\n</div>\n\t<div class=\"column is-half display-message-wrapper is-vertical-center\" style=\"height: 100vh;\">\n\t\t<div>\n\t\t\t<h1 class=\"display-message-title\">404</h1>\n\t\t\t<p class=\"display-message-content\">Page not found</p>\n\t\t\t<br>\n\t\t\t<a class=\"button is-primary is-outlined\">< Go back</a>\n\t\t</div>\n\t</div>\n</div>"

/***/ }),

/***/ "./src/app/components/errors/page-not-found/page-not-found.component.ts":
/*!******************************************************************************!*\
  !*** ./src/app/components/errors/page-not-found/page-not-found.component.ts ***!
  \******************************************************************************/
/*! exports provided: PageNotFoundComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PageNotFoundComponent", function() { return PageNotFoundComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var PageNotFoundComponent = /** @class */ (function () {
    function PageNotFoundComponent(ngRedux) {
        this.ngRedux = ngRedux;
    }
    PageNotFoundComponent.prototype.ngOnInit = function () {
        this.ngRedux.dispatch({ type: 'DISABLE_LOADING' });
    };
    PageNotFoundComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-page-not-found',
            template: __webpack_require__(/*! ./page-not-found.component.html */ "./src/app/components/errors/page-not-found/page-not-found.component.html"),
            styles: [__webpack_require__(/*! ./page-not-found.component.css */ "./src/app/components/errors/page-not-found/page-not-found.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["NgRedux"]])
    ], PageNotFoundComponent);
    return PageNotFoundComponent;
}());



/***/ }),

/***/ "./src/app/components/errors/server-error/server-error.component.css":
/*!***************************************************************************!*\
  !*** ./src/app/components/errors/server-error/server-error.component.css ***!
  \***************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvZXJyb3JzL3NlcnZlci1lcnJvci9zZXJ2ZXItZXJyb3IuY29tcG9uZW50LmNzcyJ9 */"

/***/ }),

/***/ "./src/app/components/errors/server-error/server-error.component.html":
/*!****************************************************************************!*\
  !*** ./src/app/components/errors/server-error/server-error.component.html ***!
  \****************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<p>\n  server-error works!\n</p>\n"

/***/ }),

/***/ "./src/app/components/errors/server-error/server-error.component.ts":
/*!**************************************************************************!*\
  !*** ./src/app/components/errors/server-error/server-error.component.ts ***!
  \**************************************************************************/
/*! exports provided: ServerErrorComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ServerErrorComponent", function() { return ServerErrorComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var ServerErrorComponent = /** @class */ (function () {
    function ServerErrorComponent() {
    }
    ServerErrorComponent.prototype.ngOnInit = function () {
    };
    ServerErrorComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-server-error',
            template: __webpack_require__(/*! ./server-error.component.html */ "./src/app/components/errors/server-error/server-error.component.html"),
            styles: [__webpack_require__(/*! ./server-error.component.css */ "./src/app/components/errors/server-error/server-error.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], ServerErrorComponent);
    return ServerErrorComponent;
}());



/***/ }),

/***/ "./src/app/components/loading/loading.component.css":
/*!**********************************************************!*\
  !*** ./src/app/components/loading/loading.component.css ***!
  \**********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".modal-background {\n    background-color: rgba(255, 255, 255, 0.95);\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9sb2FkaW5nL2xvYWRpbmcuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtJQUNJLDRDQUE0QztDQUMvQyIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvbG9hZGluZy9sb2FkaW5nLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyIubW9kYWwtYmFja2dyb3VuZCB7XG4gICAgYmFja2dyb3VuZC1jb2xvcjogcmdiYSgyNTUsIDI1NSwgMjU1LCAwLjk1KTtcbn1cbiJdfQ== */"

/***/ }),

/***/ "./src/app/components/loading/loading.component.html":
/*!***********************************************************!*\
  !*** ./src/app/components/loading/loading.component.html ***!
  \***********************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"modal\" [ngClass]=\"{'is-active': (isLoading | async)}\">\n  <div class=\"modal-background\"></div>\n  <div class=\"modal-card\">\n    <section class=\"modal-card-body has-text-centered\">\n      <img src=\"assets/img/loading.svg\"> <br>\n      <br>\n      <small>\n      \t<p class=\"has-text-info\">\n      \t\t<mat-icon>comment</mat-icon>{{ loadingInformation | async }}\n      \t</p>\n      </small>\n    </section>\n  </div>\n</div>"

/***/ }),

/***/ "./src/app/components/loading/loading.component.ts":
/*!*********************************************************!*\
  !*** ./src/app/components/loading/loading.component.ts ***!
  \*********************************************************/
/*! exports provided: LoadingComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LoadingComponent", function() { return LoadingComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var LoadingComponent = /** @class */ (function () {
    function LoadingComponent() {
    }
    LoadingComponent.prototype.ngOnInit = function () {
    };
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.status.loading; }),
        __metadata("design:type", Boolean)
    ], LoadingComponent.prototype, "isLoading", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.status.info; }),
        __metadata("design:type", String)
    ], LoadingComponent.prototype, "loadingInformation", void 0);
    LoadingComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-loading',
            template: __webpack_require__(/*! ./loading.component.html */ "./src/app/components/loading/loading.component.html"),
            styles: [__webpack_require__(/*! ./loading.component.css */ "./src/app/components/loading/loading.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], LoadingComponent);
    return LoadingComponent;
}());



/***/ }),

/***/ "./src/app/components/login/login.component.css":
/*!******************************************************!*\
  !*** ./src/app/components/login/login.component.css ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".vertical-center{\n\tdisplay: flex;\n\theight: 100vh;\n\tjustify-content: center;\n\talign-items: center;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9sb2dpbi9sb2dpbi5jb21wb25lbnQuY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0NBQ0MsY0FBYztDQUNkLGNBQWM7Q0FDZCx3QkFBd0I7Q0FDeEIsb0JBQW9CO0NBQ3BCIiwiZmlsZSI6InNyYy9hcHAvY29tcG9uZW50cy9sb2dpbi9sb2dpbi5jb21wb25lbnQuY3NzIiwic291cmNlc0NvbnRlbnQiOlsiLnZlcnRpY2FsLWNlbnRlcntcblx0ZGlzcGxheTogZmxleDtcblx0aGVpZ2h0OiAxMDB2aDtcblx0anVzdGlmeS1jb250ZW50OiBjZW50ZXI7XG5cdGFsaWduLWl0ZW1zOiBjZW50ZXI7XG59Il19 */"

/***/ }),

/***/ "./src/app/components/login/login.component.html":
/*!*******************************************************!*\
  !*** ./src/app/components/login/login.component.html ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"columns\">\n\t<div class=\"column is-one-third is-offset-one-third\">\n\t\t<div class=\"vertical-center\">\n\t\t\t<div class=\"card\" style=\"width: 100%\">\n\t\t\t\t<header class=\"card-header\">\n\t\t\t\t\t<p class=\"card-header-title\">\n\t\t\t\t\t\tMetaboLights Online Editor\n\t\t\t\t\t</p>\n\t\t\t\t</header>\n\t\t\t\t<div class=\"card-content\">\n\t\t\t\t\t<div class=\"content\">\n\t\t\t\t\t\t<div class=\"field\">\n\t\t\t\t\t\t\t<label class=\"label\">Email</label>\n\t\t\t\t\t\t\t<div class=\"control\">\n\t\t\t\t\t\t\t\t<input #email class=\"input is-success\" type=\"text\" value=\"\">\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\n\t\t\t\t\t\t<div class=\"field\">\n\t\t\t\t\t\t\t<label class=\"label\">Password</label>\n\t\t\t\t\t\t\t<div class=\"control\">\n\t\t\t\t\t\t\t\t<input #secret class=\"input is-success\" type=\"password\" value=\"\">\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\n\t\t\t\t\t\t<div class=\"field is-grouped\">\n\t\t\t\t\t\t\t<div class=\"control\">\n\t\t\t\t\t\t\t\t<button class=\"button is-link\" (click)=\"login($event, email.value, secret.value)\">Login</button>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<!-- <div class=\"control\">\n\t\t\t\t\t\t\t\t<button class=\"button is-text\">Cancel</button>\n\t\t\t\t\t\t\t</div> -->\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</div>"

/***/ }),

/***/ "./src/app/components/login/login.component.ts":
/*!*****************************************************!*\
  !*** ./src/app/components/login/login.component.ts ***!
  \*****************************************************/
/*! exports provided: LoginComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LoginComponent", function() { return LoginComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_metabolights_auth_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./../../services/metabolights/auth.service */ "./src/app/services/metabolights/auth.service.ts");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var LoginComponent = /** @class */ (function () {
    function LoginComponent(ngRedux, router, authService) {
        this.ngRedux = ngRedux;
        this.router = router;
        this.authService = authService;
    }
    LoginComponent.prototype.ngOnInit = function () {
        this.ngRedux.dispatch({ type: 'DISABLE_LOADING' });
    };
    LoginComponent.prototype.login = function (event, email, secret) {
        event.preventDefault();
        var body = JSON.stringify({ email: email, secret: secret });
        this.authService.login(body);
    };
    LoginComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-login',
            template: __webpack_require__(/*! ./login.component.html */ "./src/app/components/login/login.component.html"),
            styles: [__webpack_require__(/*! ./login.component.css */ "./src/app/components/login/login.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_redux_store__WEBPACK_IMPORTED_MODULE_2__["NgRedux"], _angular_router__WEBPACK_IMPORTED_MODULE_3__["Router"], _services_metabolights_auth_service__WEBPACK_IMPORTED_MODULE_1__["AuthService"]])
    ], LoginComponent);
    return LoginComponent;
}());



/***/ }),

/***/ "./src/app/components/shared/actions.ts":
/*!**********************************************!*\
  !*** ./src/app/components/shared/actions.ts ***!
  \**********************************************/
/*! exports provided: TOGGLE_LOADING, SET_LOADING_INFO, SET_CONFIGURATION, SET_TAB_INDEX, SET_USER, ENABLE_LOADING, DISABLE_LOADING */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TOGGLE_LOADING", function() { return TOGGLE_LOADING; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_LOADING_INFO", function() { return SET_LOADING_INFO; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_CONFIGURATION", function() { return SET_CONFIGURATION; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_TAB_INDEX", function() { return SET_TAB_INDEX; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_USER", function() { return SET_USER; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ENABLE_LOADING", function() { return ENABLE_LOADING; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DISABLE_LOADING", function() { return DISABLE_LOADING; });
var TOGGLE_LOADING = 'TOGGLE_LOADING';
var SET_LOADING_INFO = 'SET_LOADING_INFO';
var SET_CONFIGURATION = 'SET_CONFIGURATION';
var SET_TAB_INDEX = 'SET_TAB_INDEX';
var SET_USER = 'SET_USER';
var ENABLE_LOADING = 'ENABLE_LOADING';
var DISABLE_LOADING = 'DISABLE_LOADING';


/***/ }),

/***/ "./src/app/components/shared/content/content.component.css":
/*!*****************************************************************!*\
  !*** ./src/app/components/shared/content/content.component.css ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "p{\n\tmargin-bottom: 10px;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zaGFyZWQvY29udGVudC9jb250ZW50LmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7Q0FDQyxvQkFBb0I7Q0FDcEIiLCJmaWxlIjoic3JjL2FwcC9jb21wb25lbnRzL3NoYXJlZC9jb250ZW50L2NvbnRlbnQuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbInB7XG5cdG1hcmdpbi1ib3R0b206IDEwcHg7XG59Il19 */"

/***/ }),

/***/ "./src/app/components/shared/content/content.component.html":
/*!******************************************************************!*\
  !*** ./src/app/components/shared/content/content.component.html ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<p>\n\t<span *ngIf=\"displayContent.length > 0; else emptyMessage\" [innerHTML]='displayContent' class=\"fadeIn animated\"></span>\n\t<ng-template #emptyMessage>\n\t\t<p class=\"has-text-centered\">\n\t    \t<i>\n\t    \t\t<small>\n\t    \t\t\t<span [innerHTML]='message' class=\"has-text-grey-light fadeIn animated\"></span>\n\t    \t\t</small>\n\t    \t</i>\n    \t</p>\n    </ng-template>\n\t<span *ngIf=\"displayMoreOption; else hideContent\">\n\t\t&nbsp;<a (click)=\"toggleContent()\"><i><small><mat-icon>arrow_drop_down</mat-icon>more</small></i></a>\n\t</span>\n\t<ng-template #hideContent>\n\t\t<span *ngIf=\"displayContent.length > count\">\n\t\t\t&nbsp;<a (click)=\"toggleContent()\"><i><small><mat-icon>arrow_drop_up</mat-icon>hide</small></i></a>\n\t\t</span>\n\t</ng-template>\n</p>"

/***/ }),

/***/ "./src/app/components/shared/content/content.component.ts":
/*!****************************************************************!*\
  !*** ./src/app/components/shared/content/content.component.ts ***!
  \****************************************************************/
/*! exports provided: ContentComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ContentComponent", function() { return ContentComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var ContentComponent = /** @class */ (function () {
    function ContentComponent() {
        this.displayContent = '';
        this.displayMoreOption = false;
    }
    ContentComponent.prototype.ngOnInit = function () {
        if (this.message == '' || this.message == null) {
            this.message = 'This section is empty.';
        }
        if (this.count > 0) {
            if (this.content.length > this.count) {
                this.displayContent = this.content.slice(0, this.count);
                this.displayMoreOption = true;
            }
            else {
                this.displayContent = this.content;
            }
        }
    };
    ContentComponent.prototype.toggleContent = function () {
        this.displayMoreOption = !this.displayMoreOption;
        if (this.displayContent.length > this.count) {
            this.displayContent = this.content.slice(0, this.count);
        }
        else {
            this.displayContent = this.content;
        }
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", Object)
    ], ContentComponent.prototype, "content", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('count'),
        __metadata("design:type", Object)
    ], ContentComponent.prototype, "count", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('message'),
        __metadata("design:type", String)
    ], ContentComponent.prototype, "message", void 0);
    ContentComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-content',
            template: __webpack_require__(/*! ./content.component.html */ "./src/app/components/shared/content/content.component.html"),
            styles: [__webpack_require__(/*! ./content.component.css */ "./src/app/components/shared/content/content.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], ContentComponent);
    return ContentComponent;
}());



/***/ }),

/***/ "./src/app/components/shared/download/download.component.css":
/*!*******************************************************************!*\
  !*** ./src/app/components/shared/download/download.component.css ***!
  \*******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc2hhcmVkL2Rvd25sb2FkL2Rvd25sb2FkLmNvbXBvbmVudC5jc3MifQ== */"

/***/ }),

/***/ "./src/app/components/shared/download/download.component.html":
/*!********************************************************************!*\
  !*** ./src/app/components/shared/download/download.component.html ***!
  \********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<a target=\"_blank\" (click)=\"download()\" class=\"button is-small is-light\">\n\t<mat-icon>cloud_download</mat-icon> Download\n</a>"

/***/ }),

/***/ "./src/app/components/shared/download/download.component.ts":
/*!******************************************************************!*\
  !*** ./src/app/components/shared/download/download.component.ts ***!
  \******************************************************************/
/*! exports provided: DownloadComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DownloadComponent", function() { return DownloadComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_4___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_4__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var DownloadComponent = /** @class */ (function () {
    function DownloadComponent(fb, metabolightsService) {
        var _this = this;
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.domain = "";
        this.code = "";
        this.obfuscationCode.subscribe(function (value) {
            _this.code = value;
        });
    }
    DownloadComponent.prototype.ngOnInit = function () {
    };
    DownloadComponent.prototype.download = function () {
        if (this.code && this.code != null) {
            this.metabolightsService.downloadFile(this.file, this.code).subscribe(function (res) {
                toastr__WEBPACK_IMPORTED_MODULE_4__["success"]("File downloaded successfully", "Success", {
                    "timeOut": "2500",
                    "positionClass": "toast-top-center",
                    "preventDuplicates": true,
                    "extendedTimeOut": 0,
                    "tapToDismiss": false
                });
            }, function (err) {
                toastr__WEBPACK_IMPORTED_MODULE_4__["error"]("Error downloading the file", "Error", {
                    "timeOut": "2500",
                    "positionClass": "toast-top-center",
                    "preventDuplicates": true,
                    "extendedTimeOut": 0,
                    "tapToDismiss": false
                });
            });
        }
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", String)
    ], DownloadComponent.prototype, "file", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_2__["select"])(function (state) { return state.study.obfuscationCode; }),
        __metadata("design:type", Object)
    ], DownloadComponent.prototype, "obfuscationCode", void 0);
    DownloadComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-download',
            template: __webpack_require__(/*! ./download.component.html */ "./src/app/components/shared/download/download.component.html"),
            styles: [__webpack_require__(/*! ./download.component.css */ "./src/app/components/shared/download/download.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_1__["MetabolightsService"]])
    ], DownloadComponent);
    return DownloadComponent;
}());



/***/ }),

/***/ "./src/app/components/shared/nav-bar/nav-bar.component.css":
/*!*****************************************************************!*\
  !*** ./src/app/components/shared/nav-bar/nav-bar.component.css ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".navbar {\n    min-height: 1.25rem !important;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zaGFyZWQvbmF2LWJhci9uYXYtYmFyLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7SUFDSSwrQkFBK0I7Q0FDbEMiLCJmaWxlIjoic3JjL2FwcC9jb21wb25lbnRzL3NoYXJlZC9uYXYtYmFyL25hdi1iYXIuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbIi5uYXZiYXIge1xuICAgIG1pbi1oZWlnaHQ6IDEuMjVyZW0gIWltcG9ydGFudDtcbn1cbiJdfQ== */"

/***/ }),

/***/ "./src/app/components/shared/nav-bar/nav-bar.component.html":
/*!******************************************************************!*\
  !*** ./src/app/components/shared/nav-bar/nav-bar.component.html ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<nav class=\"navbar is-link is-fixed-top\" role=\"navigation\" aria-label=\"dropdown navigation\">\n  <div class=\"navbar-menu\">\n    <div class=\"navbar-start\">\n      <a (click)=\"backToMetabolights()\" class=\"navbar-item\">\n          \t<i class=\"material-icons\">\n\t\t\tarrow_back_ios\n\t\t\t</i>&nbsp;Back to MetaboLights\n      </a>\n    </div>\n\n    <div class=\"navbar-end\">\n    \t<a [routerLink]=\"['/console']\" class=\"navbar-item\">\n          \t<i class=\"material-icons\">\n\t\t\tlist\n\t\t\t</i>&nbsp;My studies\n      \t</a>\n      \t<a target=\"_blank\" [href]=\"domain + (studyIdentifier | async)\" class=\"navbar-item\">\n          \t<i class=\"material-icons\">\n\t\t\t       visibility\n\t\t\t       </i>&nbsp;Preview\n      \t</a>\n      \t<a (click)=\"logOut()\" class=\"navbar-item\">\n        \t<i class=\"material-icons\">\n\t\t\texit_to_app\n\t\t\t</i>&nbsp;Logout\n      \t</a>\n    </div>\n  </div>\n</nav>"

/***/ }),

/***/ "./src/app/components/shared/nav-bar/nav-bar.component.ts":
/*!****************************************************************!*\
  !*** ./src/app/components/shared/nav-bar/nav-bar.component.ts ***!
  \****************************************************************/
/*! exports provided: NavBarComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "NavBarComponent", function() { return NavBarComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_metabolights_auth_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../services/metabolights/auth.service */ "./src/app/services/metabolights/auth.service.ts");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var _services_globals__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./../../../services/globals */ "./src/app/services/globals.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var NavBarComponent = /** @class */ (function () {
    function NavBarComponent(authService, ngRedux) {
        this.authService = authService;
        this.ngRedux = ngRedux;
        this.domain = "";
    }
    NavBarComponent.prototype.ngOnInit = function () {
        this.domain = _services_globals__WEBPACK_IMPORTED_MODULE_3__["MetaboLightsWSURL"]['domain'];
    };
    NavBarComponent.prototype.logOut = function () {
        this.authService.logout();
    };
    NavBarComponent.prototype.backToMetabolights = function () {
        this.authService.logout();
        window.location.href = this.domain;
    };
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_2__["select"])(function (state) { return state.study.identifier; }),
        __metadata("design:type", String)
    ], NavBarComponent.prototype, "studyIdentifier", void 0);
    NavBarComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'nav-bar',
            template: __webpack_require__(/*! ./nav-bar.component.html */ "./src/app/components/shared/nav-bar/nav-bar.component.html"),
            styles: [__webpack_require__(/*! ./nav-bar.component.css */ "./src/app/components/shared/nav-bar/nav-bar.component.css")]
        }),
        __metadata("design:paramtypes", [_services_metabolights_auth_service__WEBPACK_IMPORTED_MODULE_1__["AuthService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_2__["NgRedux"]])
    ], NavBarComponent);
    return NavBarComponent;
}());



/***/ }),

/***/ "./src/app/components/shared/store.ts":
/*!********************************************!*\
  !*** ./src/app/components/shared/store.ts ***!
  \********************************************/
/*! exports provided: SHARED_INITIAL_STATE, sharedReducer */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SHARED_INITIAL_STATE", function() { return SHARED_INITIAL_STATE; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "sharedReducer", function() { return sharedReducer; });
/* harmony import */ var tassign__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tassign */ "./node_modules/tassign/lib/index.js");
/* harmony import */ var tassign__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(tassign__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _actions__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./actions */ "./src/app/components/shared/actions.ts");


var SHARED_INITIAL_STATE = {
    "loading": false,
    "info": '',
    'configuration': '',
    'user': null,
    'currentTabIndex': '0'
};
function toggleLoading(state) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { loading: !state.loading });
}
function enableLoading(state) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { loading: true });
}
function disableLoading(state) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { loading: false });
}
function setLoadingInfo(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { info: action.body.info });
}
function setCurrentTabIndex(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { currentTabIndex: action.body.currentTabIndex });
}
function setLoadingConfiguration(state, action) {
    var configurationValue = {};
    action.body.configuration.forEach(function (config) {
        if (config.name == 'Created With Configuration') {
            configurationValue['created_with'] = config.value;
        }
        else if (config.name == 'Last Opened With Configuration') {
            configurationValue['opened_with'] = config.value;
        }
    });
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { configuration: configurationValue });
}
function setCurrentUser(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { user: action.body.user });
}
function sharedReducer(state, action) {
    if (state === void 0) { state = SHARED_INITIAL_STATE; }
    switch (action.type) {
        case _actions__WEBPACK_IMPORTED_MODULE_1__["TOGGLE_LOADING"]: return toggleLoading(state);
        case _actions__WEBPACK_IMPORTED_MODULE_1__["ENABLE_LOADING"]: return enableLoading(state);
        case _actions__WEBPACK_IMPORTED_MODULE_1__["DISABLE_LOADING"]: return disableLoading(state);
        case _actions__WEBPACK_IMPORTED_MODULE_1__["SET_LOADING_INFO"]: return setLoadingInfo(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_1__["SET_CONFIGURATION"]: return setLoadingConfiguration(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_1__["SET_TAB_INDEX"]: return setCurrentTabIndex(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_1__["SET_USER"]: return setCurrentUser(state, action);
    }
    return state;
}


/***/ }),

/***/ "./src/app/components/shared/upload/aspera/aspera.component.css":
/*!**********************************************************************!*\
  !*** ./src/app/components/shared/upload/aspera/aspera.component.css ***!
  \**********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "button.is-fullwidth{\n\tpadding-top: 1rem;\n\tpadding-bottom: 1rem;\n\theight: auto !important;\n}\n\n.content-wrapper{\n\tmin-height: 40vh;\n}\n\n.vc{\n\tdisplay: flex;\n\talign-items: center;\n\tjustify-content: center;\n}\n\np{\n\tpadding: 10px 15px;\n}\n\ncode{\n\tdisplay: block;\n\tborder-radius: 4px;\n\tbackground-color: #333;\n\tpadding: 10px;\n\tletter-spacing: 0.1px;\n\tcolor: #ecf319;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zaGFyZWQvdXBsb2FkL2FzcGVyYS9hc3BlcmEuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtDQUNDLGtCQUFrQjtDQUNsQixxQkFBcUI7Q0FDckIsd0JBQXdCO0NBQ3hCOztBQUVEO0NBQ0MsaUJBQWlCO0NBQ2pCOztBQUVEO0NBQ0MsY0FBYztDQUNkLG9CQUFvQjtDQUNwQix3QkFBd0I7Q0FDeEI7O0FBRUQ7Q0FDQyxtQkFBbUI7Q0FDbkI7O0FBRUQ7Q0FDQyxlQUFlO0NBQ2YsbUJBQW1CO0NBQ25CLHVCQUF1QjtDQUN2QixjQUFjO0NBQ2Qsc0JBQXNCO0NBQ3RCLGVBQWU7Q0FDZiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc2hhcmVkL3VwbG9hZC9hc3BlcmEvYXNwZXJhLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyJidXR0b24uaXMtZnVsbHdpZHRoe1xuXHRwYWRkaW5nLXRvcDogMXJlbTtcblx0cGFkZGluZy1ib3R0b206IDFyZW07XG5cdGhlaWdodDogYXV0byAhaW1wb3J0YW50O1xufVxuXG4uY29udGVudC13cmFwcGVye1xuXHRtaW4taGVpZ2h0OiA0MHZoO1xufVxuXG4udmN7XG5cdGRpc3BsYXk6IGZsZXg7XG5cdGFsaWduLWl0ZW1zOiBjZW50ZXI7XG5cdGp1c3RpZnktY29udGVudDogY2VudGVyO1xufVxuXG5we1xuXHRwYWRkaW5nOiAxMHB4IDE1cHg7XG59XG5cbmNvZGV7XG5cdGRpc3BsYXk6IGJsb2NrO1xuXHRib3JkZXItcmFkaXVzOiA0cHg7XG5cdGJhY2tncm91bmQtY29sb3I6ICMzMzM7XG5cdHBhZGRpbmc6IDEwcHg7XG5cdGxldHRlci1zcGFjaW5nOiAwLjFweDtcblx0Y29sb3I6ICNlY2YzMTk7XG59Il19 */"

/***/ }),

/***/ "./src/app/components/shared/upload/aspera/aspera.component.html":
/*!***********************************************************************!*\
  !*** ./src/app/components/shared/upload/aspera/aspera.component.html ***!
  \***********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"field\">\n\t<div class=\"control\">\n\t\t<button (click)=\"openUploadModal()\" class=\"button is-outlined is-link is-fullwidth\">Aspera Upload</button>\n\t</div>\n\t<p class=\"help\">Aspera is a faster alternative to FTP and provides greater user control enabling individual transfer rates and bandwidth sharing to be set. (RECOMMENDED)</p>\n</div>\n<div class=\"modal\" [ngClass]=\"{'is-active': isAsperaUploadModalOpen}\">\n\t<div class=\"modal-background\"></div>\n\t<div class=\"modal-card\">\n\t\t<section class=\"modal-card-body\">\n\t\t\t<div class=\"tabs is-toggle is-fullwidth\">\n\t\t\t  <ul>\n\t\t\t     <li (click)=\"changeTab('plugin')\" [ngClass]=\"selectedTab == 'plugin' ? 'is-active' : ''\"><a>Aspera Connect web browser plug-in</a></li>\n\t\t\t    <li (click)=\"changeTab('commandline')\"  [ngClass]=\"selectedTab != 'plugin' ? 'is-active' : ''\"><a>Aspera Command line client</a></li>\n\t\t\t  </ul>\n\t\t\t</div>\n\t\t\t<span *ngIf=\"selectedTab == 'plugin'; else showCommandLine\">\n\t\t\t\t<div class=\"content-wrapper has-text-centered vc\">\n\t\t\t\t\t<div><br>\n\t\t\t\t\t\t<img src=\"assets/img/aspera.jpeg\" style=\"height: 100px\"><br><br>\n\t\t\t\t\t\t<button (click)=\"asperaUpload()\" class=\"button is-success\">Upload now</button>\n\t\t\t\t\t\t<br><br><br>\n\t\t\t\t\t\t<small class=\"has-text-grey\">\n\t\t\t\t\t\t\t<b><i>Install plugin from <a target=\"_blank\" href=\"https://downloads.asperasoft.com/en/downloads\">here</a></i></b>\n\t\t\t\t\t\t</small>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</span>\n\t\t\t<ng-template #showCommandLine>\n\t\t\t\t<div class=\"content-wrapper\">\n\t\t\t\t\t<code>\n\t\t\t\t\t\t$ ascp -QT -P 33001 -L- -l 300M < your_local_data_folder > mtblight@hx-fasp-1.ebi.ac.uk:{{ uploadLocation | async }}\n\t\t\t\t\t</code>\n\t\t\t\t\t<small>\n\t\t\t\t\t\t<br>\n\t\t\t\t\t\t<b>Step 1: Install Aspera ascp command line client</b><br>\n\t\t\t\t\t\tThe Aspera ascp command line client can be downloaded <i><a href=\"http://downloads.asperasoft.com/downloads\" target=\"_blank\">here</a></i>. Please select *Aspera Connect*. The ascp command line client is distributed as part of the aspera connect high-performance transfer browser plug-in and is free to use.<br><br>\n\t\t\t\t\t\t<b>Step 2: Navigate to the folder where the Aspera command line client program ascp is installed.</b><br>\n\t\t\t\t\t\tThe location of the 'ascp' program in the filesystem:<br>\n\t\t\t\t\t\t<span class=\"col-md-12\">\n\t\t\t\t\t\t\t<p><b>Mac:</b> on the desktop go <code>cd /Applications/Aspera\\ Connect.app/Contents/Resources/</code> there you'll see the command line utilities where you're going to use 'ascp'.</p>\n\t\t\t\t\t\t\t<p><b>Windows:</b> the downloaded files are a bit hidden. For instance in Windows7 the ascp.exe is located in the users home directory in: <code>AppData\\Local\\Programs\\Aspera\\Aspera Connect\\bin\\ascp.exe</code></p>\n\t\t\t\t\t\t\t<p><b>Linux:</b> should be in your user's home directory, <code>cd /home/username/.aspera/connect/bin/</code> there you'll see the command line utilities where you're going to use 'ascp'.</p>\n\t\t\t\t\t\t</span>\n\t\t\t\t\t</small>\n\t\t\t\t</div>\n\t\t\t</ng-template>\t\n\t\t</section>\n\t\t<footer class=\"modal-card-foot\">\n\t\t\t<div class=\"columns is-gapless full-width\">\n\t\t\t\t<div class=\"column is-full has-text-right\">\n\t\t\t\t\t<button (click)='closeUploadModal()' class=\"button is-default\">Close</button>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</footer>\n\t</div>\n</div>"

/***/ }),

/***/ "./src/app/components/shared/upload/aspera/aspera.component.ts":
/*!*********************************************************************!*\
  !*** ./src/app/components/shared/upload/aspera/aspera.component.ts ***!
  \*********************************************************************/
/*! exports provided: AsperaComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AsperaComponent", function() { return AsperaComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var AsperaComponent = /** @class */ (function () {
    function AsperaComponent(fb, metabolightsService) {
        var _this = this;
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.isAsperaUploadModalOpen = false;
        this.selectedTab = 'plugin';
        this.validationsId = 'upload';
        this.MIN_CONNECT_VERSION = "3.6.0.0";
        this.CONNECT_AUTOINSTALL_LOCATION = "//d3gcli72yxqn2z.cloudfront.net/connect/v4";
        this.uploadPath = '';
        this.uploadLocation.subscribe(function (value) {
            _this.uploadPath = value;
        });
        this.validations.subscribe(function (value) {
            _this.validation = value[_this.validationsId];
        });
    }
    AsperaComponent.prototype.ngOnInit = function () {
    };
    AsperaComponent.prototype.changeTab = function (id) {
        this.selectedTab = id;
    };
    AsperaComponent.prototype.openUploadModal = function () {
        this.isAsperaUploadModalOpen = true;
    };
    AsperaComponent.prototype.closeUploadModal = function () {
        this.isAsperaUploadModalOpen = false;
    };
    AsperaComponent.prototype.asperaUpload = function () {
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
    AsperaComponent.prototype.buildUploadSpec = function (dataTransferObj) {
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
        var asperaSettings = this.validation.aspera;
        params["remote_user"] = asperaSettings.user;
        params["remote_password"] = asperaSettings.secret;
        params['remote_host'] = asperaSettings.server;
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
        transferSpecs[0]["transfer_spec"]['destination_root'] = this.uploadPath;
        var finalConfig = {};
        finalConfig['transfer_specs'] = transferSpecs;
        var requestId = this.asperaWeb.startTransfers(finalConfig, { success: function (data) {
                console.log("Upload started");
            } });
    };
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_2__["select"])(function (state) { return state.study.uploadLocation; }),
        __metadata("design:type", Object)
    ], AsperaComponent.prototype, "uploadLocation", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_2__["select"])(function (state) { return state.study.validations; }),
        __metadata("design:type", Object)
    ], AsperaComponent.prototype, "validations", void 0);
    AsperaComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-aspera',
            template: __webpack_require__(/*! ./aspera.component.html */ "./src/app/components/shared/upload/aspera/aspera.component.html"),
            styles: [__webpack_require__(/*! ./aspera.component.css */ "./src/app/components/shared/upload/aspera/aspera.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_1__["MetabolightsService"]])
    ], AsperaComponent);
    return AsperaComponent;
}());



/***/ }),

/***/ "./src/app/components/shared/upload/ftp/ftp.component.css":
/*!****************************************************************!*\
  !*** ./src/app/components/shared/upload/ftp/ftp.component.css ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "button.is-fullwidth{\n\tpadding-top: 1rem;\n\tpadding-bottom: 1rem;\n\theight: auto !important;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zaGFyZWQvdXBsb2FkL2Z0cC9mdHAuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtDQUNDLGtCQUFrQjtDQUNsQixxQkFBcUI7Q0FDckIsd0JBQXdCO0NBQ3hCIiwiZmlsZSI6InNyYy9hcHAvY29tcG9uZW50cy9zaGFyZWQvdXBsb2FkL2Z0cC9mdHAuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbImJ1dHRvbi5pcy1mdWxsd2lkdGh7XG5cdHBhZGRpbmctdG9wOiAxcmVtO1xuXHRwYWRkaW5nLWJvdHRvbTogMXJlbTtcblx0aGVpZ2h0OiBhdXRvICFpbXBvcnRhbnQ7XG59Il19 */"

/***/ }),

/***/ "./src/app/components/shared/upload/ftp/ftp.component.html":
/*!*****************************************************************!*\
  !*** ./src/app/components/shared/upload/ftp/ftp.component.html ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"field\">\n\t<div class=\"control\">\n\t\t<button (click)=\"openUploadModal()\" class=\"button is-outlined is-link is-fullwidth\">Private FTP Upload</button>\n\t</div>\n\t<p class=\"help\">UNIX users - command line ftp or GUI FileZilla\n\t\t<br>\n\t\tWindows/MAC - GUI FileZilla</p>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isFTPUploadModalOpen}\">\n\t<div class=\"modal-background\"></div>\n\t<div class=\"modal-card\">\n\t\t<header class=\"modal-card-head\">\n        \t<p class=\"modal-card-title\">FTP Upload folders / files</p>\n      \t</header>\n\t\t<section class=\"modal-card-body\">\n\t\t\tUsing FTP Client:\n\t\t\t<table class=\"table is-bordered is-striped is-narrow is-hoverable is-fullwidth\">\n\t\t      <tbody>\n\t\t        <tr>\n\t\t         \t<th>User</th>\n\t\t         \t<td>{{ (validations | async)[this.validationsId]['ftp']['user'] }}</td>\n\t\t        </tr>\n\t\t        <tr>\n\t\t          <th>Password</th>\n\t\t          <td>{{ (validations | async)[this.validationsId]['ftp']['secret'] }}</td>\n\t\t        </tr>\n\t\t        <tr>\n\t\t          <th>Server</th>\n\t\t          <td>{{ (validations | async)[this.validationsId]['ftp']['server'] }}</td>\n\t\t        </tr>\n\t\t        <tr>\n\t\t          <th>Remote folder</th>\n\t\t          <td>{{ uploadLocation | async }}</td>\n\t\t        </tr>\n\t\t      </tbody>\n\t\t    </table>\n\t\t</section>\n\t\t<footer class=\"modal-card-foot\">\n\t\t\t<div class=\"columns is-gapless full-width\">\n\t\t\t\t<div class=\"column is-full has-text-right\">\n\t\t\t\t\t<button (click)='closeUploadModal()' class=\"button is-default\">Close</button>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</footer>\n\t</div>\n</div>"

/***/ }),

/***/ "./src/app/components/shared/upload/ftp/ftp.component.ts":
/*!***************************************************************!*\
  !*** ./src/app/components/shared/upload/ftp/ftp.component.ts ***!
  \***************************************************************/
/*! exports provided: FtpComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "FtpComponent", function() { return FtpComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var FtpComponent = /** @class */ (function () {
    function FtpComponent(fb, metabolightsService) {
        var _this = this;
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.isFTPUploadModalOpen = false;
        this.validationsId = 'upload';
        this.uploadPath = '';
        this.uploadLocation.subscribe(function (value) {
            _this.uploadPath = value;
        });
    }
    FtpComponent.prototype.ngOnInit = function () {
    };
    FtpComponent.prototype.openUploadModal = function () {
        this.isFTPUploadModalOpen = true;
    };
    FtpComponent.prototype.closeUploadModal = function () {
        this.isFTPUploadModalOpen = false;
    };
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_2__["select"])(function (state) { return state.study.uploadLocation; }),
        __metadata("design:type", Object)
    ], FtpComponent.prototype, "uploadLocation", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_2__["select"])(function (state) { return state.study.validations; }),
        __metadata("design:type", Object)
    ], FtpComponent.prototype, "validations", void 0);
    FtpComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-ftp',
            template: __webpack_require__(/*! ./ftp.component.html */ "./src/app/components/shared/upload/ftp/ftp.component.html"),
            styles: [__webpack_require__(/*! ./ftp.component.css */ "./src/app/components/shared/upload/ftp/ftp.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_1__["MetabolightsService"]])
    ], FtpComponent);
    return FtpComponent;
}());



/***/ }),

/***/ "./src/app/components/shared/upload/upload.component.css":
/*!***************************************************************!*\
  !*** ./src/app/components/shared/upload/upload.component.css ***!
  \***************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".upload-zone{\n\tmin-height: 10vh;\n\tbackground-color: #f1f1f4;\n\tborder: 2px dotted #cecece;\n\tborder-radius: 4px;\n\tdisplay: flex;\n\tjustify-content: center;\n\talign-items: center;\n}\n\n.upload-zone:hover{\n\tborder: 2px dotted #333;\n\tcursor: pointer;\n}\n\n.upload-zone p{\n\tpadding-top: 20vh;\n\ttext-align: center;\n\tcolor: #cecece;\n}\n\n.is-divider, .is-divider-vertical {\n    display: block;\n    position: relative;\n    border-top: .1rem solid #dbdbdb;\n    height: .1rem;\n    margin: 0 0 2rem 0;\n    text-align: center;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zaGFyZWQvdXBsb2FkL3VwbG9hZC5jb21wb25lbnQuY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0NBQ0MsaUJBQWlCO0NBQ2pCLDBCQUEwQjtDQUMxQiwyQkFBMkI7Q0FDM0IsbUJBQW1CO0NBQ25CLGNBQWM7Q0FDZCx3QkFBd0I7Q0FDeEIsb0JBQW9CO0NBQ3BCOztBQUVEO0NBQ0Msd0JBQXdCO0NBQ3hCLGdCQUFnQjtDQUNoQjs7QUFFRDtDQUNDLGtCQUFrQjtDQUNsQixtQkFBbUI7Q0FDbkIsZUFBZTtDQUNmOztBQUVEO0lBQ0ksZUFBZTtJQUNmLG1CQUFtQjtJQUNuQixnQ0FBZ0M7SUFDaEMsY0FBYztJQUNkLG1CQUFtQjtJQUNuQixtQkFBbUI7Q0FDdEIiLCJmaWxlIjoic3JjL2FwcC9jb21wb25lbnRzL3NoYXJlZC91cGxvYWQvdXBsb2FkLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyIudXBsb2FkLXpvbmV7XG5cdG1pbi1oZWlnaHQ6IDEwdmg7XG5cdGJhY2tncm91bmQtY29sb3I6ICNmMWYxZjQ7XG5cdGJvcmRlcjogMnB4IGRvdHRlZCAjY2VjZWNlO1xuXHRib3JkZXItcmFkaXVzOiA0cHg7XG5cdGRpc3BsYXk6IGZsZXg7XG5cdGp1c3RpZnktY29udGVudDogY2VudGVyO1xuXHRhbGlnbi1pdGVtczogY2VudGVyO1xufVxuXG4udXBsb2FkLXpvbmU6aG92ZXJ7XG5cdGJvcmRlcjogMnB4IGRvdHRlZCAjMzMzO1xuXHRjdXJzb3I6IHBvaW50ZXI7XG59XG5cbi51cGxvYWQtem9uZSBwe1xuXHRwYWRkaW5nLXRvcDogMjB2aDtcblx0dGV4dC1hbGlnbjogY2VudGVyO1xuXHRjb2xvcjogI2NlY2VjZTtcbn1cblxuLmlzLWRpdmlkZXIsIC5pcy1kaXZpZGVyLXZlcnRpY2FsIHtcbiAgICBkaXNwbGF5OiBibG9jaztcbiAgICBwb3NpdGlvbjogcmVsYXRpdmU7XG4gICAgYm9yZGVyLXRvcDogLjFyZW0gc29saWQgI2RiZGJkYjtcbiAgICBoZWlnaHQ6IC4xcmVtO1xuICAgIG1hcmdpbjogMCAwIDJyZW0gMDtcbiAgICB0ZXh0LWFsaWduOiBjZW50ZXI7XG59Il19 */"

/***/ }),

/***/ "./src/app/components/shared/upload/upload.component.html":
/*!****************************************************************!*\
  !*** ./src/app/components/shared/upload/upload.component.html ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<a (click)=\"openUploadModal()\" class=\"button is-light\" [ngClass]=\"size\">\n\t<mat-icon>cloud_upload</mat-icon> Upload\n</a>\n<div class=\"modal\" [ngClass]=\"{'is-active': isUploadModalOpen}\">\n\t<div class=\"modal-background\"></div>\n\t<div class=\"modal-card\">\n\t\t<header class=\"modal-card-head\">\n        \t<p class=\"modal-card-title\">Upload folders / files</p>\n      \t</header>\n\t\t<section class=\"modal-card-body\" v-if=\"validations\">\n\t\t\t<mtbls-aspera></mtbls-aspera>\n\t\t\t<mtbls-ftp></mtbls-ftp>\n\t\t</section>\n\t\t<footer class=\"modal-card-foot\">\n\t\t\t<div class=\"columns is-gapless full-width\">\n\t\t\t\t<div class=\"column is-full has-text-right\">\n\t\t\t\t\t<button (click)='closeUploadModal()' class=\"button is-default\">Close</button>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</footer>\n\t</div>\n</div>"

/***/ }),

/***/ "./src/app/components/shared/upload/upload.component.ts":
/*!**************************************************************!*\
  !*** ./src/app/components/shared/upload/upload.component.ts ***!
  \**************************************************************/
/*! exports provided: UploadComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UploadComponent", function() { return UploadComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var UploadComponent = /** @class */ (function () {
    function UploadComponent(fb, metabolightsService) {
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.mode = 'button';
        this.size = 'is-small';
        this.isUploadModalOpen = false;
    }
    UploadComponent.prototype.ngOnInit = function () {
    };
    UploadComponent.prototype.openUploadModal = function () {
        this.isUploadModalOpen = true;
    };
    UploadComponent.prototype.closeUploadModal = function () {
        this.isUploadModalOpen = false;
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('mode'),
        __metadata("design:type", String)
    ], UploadComponent.prototype, "mode", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('size'),
        __metadata("design:type", String)
    ], UploadComponent.prototype, "size", void 0);
    UploadComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-upload',
            template: __webpack_require__(/*! ./upload.component.html */ "./src/app/components/shared/upload/upload.component.html"),
            styles: [__webpack_require__(/*! ./upload.component.css */ "./src/app/components/shared/upload/upload.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_1__["MetabolightsService"]])
    ], UploadComponent);
    return UploadComponent;
}());



/***/ }),

/***/ "./src/app/components/study/actions.ts":
/*!*********************************************!*\
  !*** ./src/app/components/study/actions.ts ***!
  \*********************************************/
/*! exports provided: SET_STUDY_IDENTIFIER, SET_STUDY_TITLE, SET_STUDY_ABSTRACT, SET_STUDY_SUBMISSION_DATE, SET_STUDY_RELEASE_DATE, SET_STUDY_PUBLICATIONS, UPDATE_STUDY_PUBLICATIONS, SET_STUDY_PEOPLE, UPDATE_STUDY_PEOPLE, LOAD_VALIDATION_RULES, SET_STUDY_DESIGN_DESCRIPTORS, UPDATE_STUDY_DESIGN_DESCRIPTORS, SET_STUDY_FACTORS, SET_STUDY_ORGANISMS, SET_STUDY_PROTOCOLS, UPDATE_STUDY_PROTOCOLS, UPDATE_STUDY_FACTORS, SET_STUDY_SAMPLES_TABLE, SET_STUDY_PROCESS_SEQUENCE, ADD_STUDY_PROCESS_SEQUENCE, ADD_STUDY_ASSAY, ADD_MAF, UPDATE_MAF_DATA, ADD_STUDY_ASSAY_TABLE, SET_UPLOAD_LOCATION, SET_OBFUSCATION_CODE */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_STUDY_IDENTIFIER", function() { return SET_STUDY_IDENTIFIER; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_STUDY_TITLE", function() { return SET_STUDY_TITLE; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_STUDY_ABSTRACT", function() { return SET_STUDY_ABSTRACT; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_STUDY_SUBMISSION_DATE", function() { return SET_STUDY_SUBMISSION_DATE; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_STUDY_RELEASE_DATE", function() { return SET_STUDY_RELEASE_DATE; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_STUDY_PUBLICATIONS", function() { return SET_STUDY_PUBLICATIONS; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UPDATE_STUDY_PUBLICATIONS", function() { return UPDATE_STUDY_PUBLICATIONS; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_STUDY_PEOPLE", function() { return SET_STUDY_PEOPLE; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UPDATE_STUDY_PEOPLE", function() { return UPDATE_STUDY_PEOPLE; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "LOAD_VALIDATION_RULES", function() { return LOAD_VALIDATION_RULES; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_STUDY_DESIGN_DESCRIPTORS", function() { return SET_STUDY_DESIGN_DESCRIPTORS; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UPDATE_STUDY_DESIGN_DESCRIPTORS", function() { return UPDATE_STUDY_DESIGN_DESCRIPTORS; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_STUDY_FACTORS", function() { return SET_STUDY_FACTORS; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_STUDY_ORGANISMS", function() { return SET_STUDY_ORGANISMS; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_STUDY_PROTOCOLS", function() { return SET_STUDY_PROTOCOLS; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UPDATE_STUDY_PROTOCOLS", function() { return UPDATE_STUDY_PROTOCOLS; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UPDATE_STUDY_FACTORS", function() { return UPDATE_STUDY_FACTORS; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_STUDY_SAMPLES_TABLE", function() { return SET_STUDY_SAMPLES_TABLE; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_STUDY_PROCESS_SEQUENCE", function() { return SET_STUDY_PROCESS_SEQUENCE; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ADD_STUDY_PROCESS_SEQUENCE", function() { return ADD_STUDY_PROCESS_SEQUENCE; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ADD_STUDY_ASSAY", function() { return ADD_STUDY_ASSAY; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ADD_MAF", function() { return ADD_MAF; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "UPDATE_MAF_DATA", function() { return UPDATE_MAF_DATA; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ADD_STUDY_ASSAY_TABLE", function() { return ADD_STUDY_ASSAY_TABLE; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_UPLOAD_LOCATION", function() { return SET_UPLOAD_LOCATION; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SET_OBFUSCATION_CODE", function() { return SET_OBFUSCATION_CODE; });
var SET_STUDY_IDENTIFIER = 'SET_STUDY_IDENTIFIER';
var SET_STUDY_TITLE = 'SET_STUDY_TITLE';
var SET_STUDY_ABSTRACT = 'SET_STUDY_ABSTRACT';
var SET_STUDY_SUBMISSION_DATE = 'SET_STUDY_SUBMISSION_DATE';
var SET_STUDY_RELEASE_DATE = 'SET_STUDY_RELEASE_DATE';
var SET_STUDY_PUBLICATIONS = 'SET_STUDY_PUBLICATIONS';
var UPDATE_STUDY_PUBLICATIONS = 'UPDATE_STUDY_PUBLICATIONS';
var SET_STUDY_PEOPLE = 'SET_STUDY_PEOPLE';
var UPDATE_STUDY_PEOPLE = 'UPDATE_STUDY_PEOPLE';
var LOAD_VALIDATION_RULES = 'LOAD_VALIDATION_RULES';
var SET_STUDY_DESIGN_DESCRIPTORS = 'SET_STUDY_DESIGN_DESCRIPTORS';
var UPDATE_STUDY_DESIGN_DESCRIPTORS = 'UPDATE_STUDY_DESIGN_DESCRIPTORS';
var SET_STUDY_FACTORS = 'SET_STUDY_FACTORS';
var SET_STUDY_ORGANISMS = 'SET_STUDY_ORGANISMS';
var SET_STUDY_PROTOCOLS = 'SET_STUDY_PROTOCOLS';
var UPDATE_STUDY_PROTOCOLS = 'UPDATE_STUDY_PROTOCOLS';
var UPDATE_STUDY_FACTORS = 'UPDATE_STUDY_FACTORS';
var SET_STUDY_SAMPLES_TABLE = 'SET_STUDY_SAMPLES_TABLE';
var SET_STUDY_PROCESS_SEQUENCE = 'SET_STUDY_PROCESS_SEQUENCE';
var ADD_STUDY_PROCESS_SEQUENCE = 'ADD_STUDY_PROCESS_SEQUENCE';
var ADD_STUDY_ASSAY = 'ADD_STUDY_ASSAY';
var ADD_MAF = 'ADD_MAF';
var UPDATE_MAF_DATA = 'UPDATE_MAF_DATA';
var ADD_STUDY_ASSAY_TABLE = 'ADD_STUDY_ASSAY_TABLE';
var SET_UPLOAD_LOCATION = 'SET_UPLOAD_LOCATION';
var SET_OBFUSCATION_CODE = 'SET_OBFUSCATION_CODE';


/***/ }),

/***/ "./src/app/components/study/assays/assay/assay.component.css":
/*!*******************************************************************!*\
  !*** ./src/app/components/study/assays/assay/assay.component.css ***!
  \*******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "table {\n  -webkit-user-select: none;\n  -moz-user-select: none;\n  -o-user-select: none;\n  -ms-user-select: none;\n      user-select: none;\n}\n\ntable {\n  width: 100%;\n}\n\ntable td, table th {\n  vertical-align: middle;\n}\n\nth.mat-sort-header-sorted {\n  color: black;\n}\n\nth div{\n  display: flex;\n}\n\n.column-editor-card{\n  min-width: 80vw !important;\n  min-height: 80vh;\n}\n\n.columns.is-vcentered {\n  align-items: center;\n}\n\n.columns.is-hcentered {\n  justify-content: center;\n  text-align: center;\n}\n\n.mt20{\n  margin-top: 20px;\n}\n\n.mb0{\n  margin-bottom: 0px;\n}\n\n.tab-wrapper{\n  min-height: 80vh;\n}\n\n.modal-card-title {\n  color: #979797;\n  font-size: 1.2rem;\n}\n\n.modal-card-title .highlight {\n  color: #000;\n}\n\n.options-wrapper{\n  border-left: 1px solid #f1f1f3;\n  min-height: 70vh;\n  padding-top: 10px !important;\n}\n\n.options-title{\n  border-bottom: 1px solid #f1f1f3;\n  padding: 15px !important;\n  font-weight: bold;\n  font-size: 1.1em;\n}\n\n.options{\n  padding: 15px;\n}\n\n.wrapper{\n  width: 100%;\n  overflow: auto;\n}\n\n.mat-form-field-infix {\n  border-top: none;\n}\n\ntd.mat-cell, td.mat-footer-cell, th.mat-header-cell {\n\tpadding-left: 20px;\n\tpadding-right: 12px;\n  box-shadow: 0 0 1px #dbdbdb;\n}\n\n.mat-table-sticky {\n  background-color: #fdfdfd;\n}\n\n.dropdown-divider {\n  background-color: #dbdbdb;\n  border: none;\n  display: block;\n  height: 1px;\n  margin: 0;\n}\n\n.dropdown-content {\n padding-bottom: 0rem; \n padding-top: 0rem; \n border-radius: 2px;\n}\n\na.dropdown-item {\n  padding-right: 1rem; \n}\n\n.column{\n  padding: 0;\n}\n\n.addon-tag{\n  padding-left: 0;\n  padding-right: 0;\n  border-left: 1px dotted #eaeaea;\n}\n\n.columns{\n  margin-right: 0px;\n  margin-left: 0px;\n}\n\n.row-selector{\n\twidth: 10px;\n}\n\n.table-selector{\n\twidth: 10px;\n}\n\n.selected{\n\tbackground-color: #333;\n  color: #fff;\n}\n\n.row-options-wrapper{\n\tposition: relative;\n}\n\n.row-options{\n\tposition: absolute;\n  left: -23px;\n  top: 0px;\n  height: 48px;\n}\n\n.table-icon {\n  padding-right: 10px;\n  padding-top: 12px;\n}\n\n.button{\n  margin-left: 2px;\n  margin-right: 2px;\n}\n\n.menu-bar{\n  margin-top: 10px;\n  margin-bottom: 0 !important;\n  align-items: baseline;\n}\n\n.menu-bar:first-child { \n  margin-left: -5px;\n}\n\n.menu-bar:last-child { \n  margin-right: -5px;\n}\n\n.button.is-light {\n  border-radius: 2px;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zdHVkeS9hc3NheXMvYXNzYXkvYXNzYXkuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLDBCQUEwQjtFQUUxQix1QkFBdUI7RUFDdkIscUJBQXFCO0VBQ3JCLHNCQUFrQjtNQUFsQixrQkFBa0I7Q0FDbkI7O0FBRUQ7RUFDRSxZQUFZO0NBQ2I7O0FBRUQ7RUFDRSx1QkFBdUI7Q0FDeEI7O0FBRUQ7RUFDRSxhQUFhO0NBQ2Q7O0FBRUQ7RUFDRSxjQUFjO0NBQ2Y7O0FBRUQ7RUFDRSwyQkFBMkI7RUFDM0IsaUJBQWlCO0NBQ2xCOztBQUVEO0VBR0Usb0JBQW9CO0NBQ3JCOztBQUVEO0VBQ0Usd0JBQXdCO0VBQ3hCLG1CQUFtQjtDQUNwQjs7QUFFRDtFQUNFLGlCQUFpQjtDQUNsQjs7QUFFRDtFQUNFLG1CQUFtQjtDQUNwQjs7QUFFRDtFQUNFLGlCQUFpQjtDQUNsQjs7QUFFRDtFQUNFLGVBQWU7RUFDZixrQkFBa0I7Q0FDbkI7O0FBRUQ7RUFDRSxZQUFZO0NBQ2I7O0FBRUQ7RUFDRSwrQkFBK0I7RUFDL0IsaUJBQWlCO0VBQ2pCLDZCQUE2QjtDQUM5Qjs7QUFFRDtFQUNFLGlDQUFpQztFQUNqQyx5QkFBeUI7RUFDekIsa0JBQWtCO0VBQ2xCLGlCQUFpQjtDQUNsQjs7QUFFRDtFQUNFLGNBQWM7Q0FDZjs7QUFFRDtFQUNFLFlBQVk7RUFDWixlQUFlO0NBQ2hCOztBQUVEO0VBQ0UsaUJBQWlCO0NBQ2xCOztBQUVEO0NBQ0MsbUJBQW1CO0NBQ25CLG9CQUFvQjtFQUVuQiw0QkFBNEI7Q0FDN0I7O0FBRUQ7RUFDRSwwQkFBMEI7Q0FDM0I7O0FBRUQ7RUFDRSwwQkFBMEI7RUFDMUIsYUFBYTtFQUNiLGVBQWU7RUFDZixZQUFZO0VBQ1osVUFBVTtDQUNYOztBQUVEO0NBQ0MscUJBQXFCO0NBQ3JCLGtCQUFrQjtDQUNsQixtQkFBbUI7Q0FDbkI7O0FBRUQ7RUFDRSxvQkFBb0I7Q0FDckI7O0FBRUQ7RUFDRSxXQUFXO0NBQ1o7O0FBRUQ7RUFDRSxnQkFBZ0I7RUFDaEIsaUJBQWlCO0VBQ2pCLGdDQUFnQztDQUNqQzs7QUFFRDtFQUNFLGtCQUFrQjtFQUNsQixpQkFBaUI7Q0FDbEI7O0FBRUQ7Q0FDQyxZQUFZO0NBQ1o7O0FBRUQ7Q0FDQyxZQUFZO0NBQ1o7O0FBRUQ7Q0FDQyx1QkFBdUI7RUFDdEIsWUFBWTtDQUNiOztBQUVEO0NBQ0MsbUJBQW1CO0NBQ25COztBQUVEO0NBQ0MsbUJBQW1CO0VBQ2xCLFlBQVk7RUFDWixTQUFTO0VBQ1QsYUFBYTtDQUNkOztBQUVEO0VBQ0Usb0JBQW9CO0VBQ3BCLGtCQUFrQjtDQUNuQjs7QUFFRDtFQUNFLGlCQUFpQjtFQUNqQixrQkFBa0I7Q0FDbkI7O0FBRUQ7RUFDRSxpQkFBaUI7RUFDakIsNEJBQTRCO0VBQzVCLHNCQUFzQjtDQUN2Qjs7QUFFRDtFQUNFLGtCQUFrQjtDQUNuQjs7QUFFRDtFQUNFLG1CQUFtQjtDQUNwQjs7QUFFRDtFQUNFLG1CQUFtQjtDQUNwQiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvYXNzYXlzL2Fzc2F5L2Fzc2F5LmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyJ0YWJsZSB7XG4gIC13ZWJraXQtdXNlci1zZWxlY3Q6IG5vbmU7XG4gIC1raHRtbC11c2VyLXNlbGVjdDogbm9uZTtcbiAgLW1vei11c2VyLXNlbGVjdDogbm9uZTtcbiAgLW8tdXNlci1zZWxlY3Q6IG5vbmU7XG4gIHVzZXItc2VsZWN0OiBub25lO1xufVxuXG50YWJsZSB7XG4gIHdpZHRoOiAxMDAlO1xufVxuXG50YWJsZSB0ZCwgdGFibGUgdGgge1xuICB2ZXJ0aWNhbC1hbGlnbjogbWlkZGxlO1xufVxuXG50aC5tYXQtc29ydC1oZWFkZXItc29ydGVkIHtcbiAgY29sb3I6IGJsYWNrO1xufVxuXG50aCBkaXZ7XG4gIGRpc3BsYXk6IGZsZXg7XG59XG5cbi5jb2x1bW4tZWRpdG9yLWNhcmR7XG4gIG1pbi13aWR0aDogODB2dyAhaW1wb3J0YW50O1xuICBtaW4taGVpZ2h0OiA4MHZoO1xufVxuXG4uY29sdW1ucy5pcy12Y2VudGVyZWQge1xuICAtd2Via2l0LWJveC1hbGlnbjogY2VudGVyO1xuICAtbXMtZmxleC1hbGlnbjogY2VudGVyO1xuICBhbGlnbi1pdGVtczogY2VudGVyO1xufVxuXG4uY29sdW1ucy5pcy1oY2VudGVyZWQge1xuICBqdXN0aWZ5LWNvbnRlbnQ6IGNlbnRlcjtcbiAgdGV4dC1hbGlnbjogY2VudGVyO1xufVxuXG4ubXQyMHtcbiAgbWFyZ2luLXRvcDogMjBweDtcbn1cblxuLm1iMHtcbiAgbWFyZ2luLWJvdHRvbTogMHB4O1xufVxuXG4udGFiLXdyYXBwZXJ7XG4gIG1pbi1oZWlnaHQ6IDgwdmg7XG59XG5cbi5tb2RhbC1jYXJkLXRpdGxlIHtcbiAgY29sb3I6ICM5Nzk3OTc7XG4gIGZvbnQtc2l6ZTogMS4ycmVtO1xufVxuXG4ubW9kYWwtY2FyZC10aXRsZSAuaGlnaGxpZ2h0IHtcbiAgY29sb3I6ICMwMDA7XG59XG5cbi5vcHRpb25zLXdyYXBwZXJ7XG4gIGJvcmRlci1sZWZ0OiAxcHggc29saWQgI2YxZjFmMztcbiAgbWluLWhlaWdodDogNzB2aDtcbiAgcGFkZGluZy10b3A6IDEwcHggIWltcG9ydGFudDtcbn1cblxuLm9wdGlvbnMtdGl0bGV7XG4gIGJvcmRlci1ib3R0b206IDFweCBzb2xpZCAjZjFmMWYzO1xuICBwYWRkaW5nOiAxNXB4ICFpbXBvcnRhbnQ7XG4gIGZvbnQtd2VpZ2h0OiBib2xkO1xuICBmb250LXNpemU6IDEuMWVtO1xufVxuXG4ub3B0aW9uc3tcbiAgcGFkZGluZzogMTVweDtcbn1cblxuLndyYXBwZXJ7XG4gIHdpZHRoOiAxMDAlO1xuICBvdmVyZmxvdzogYXV0bztcbn1cblxuLm1hdC1mb3JtLWZpZWxkLWluZml4IHtcbiAgYm9yZGVyLXRvcDogbm9uZTtcbn1cblxudGQubWF0LWNlbGwsIHRkLm1hdC1mb290ZXItY2VsbCwgdGgubWF0LWhlYWRlci1jZWxsIHtcblx0cGFkZGluZy1sZWZ0OiAyMHB4O1xuXHRwYWRkaW5nLXJpZ2h0OiAxMnB4O1xuICAtd2Via2l0LWJveC1zaGFkb3c6IDAgMCAxcHggI2RiZGJkYjtcbiAgYm94LXNoYWRvdzogMCAwIDFweCAjZGJkYmRiO1xufVxuXG4ubWF0LXRhYmxlLXN0aWNreSB7XG4gIGJhY2tncm91bmQtY29sb3I6ICNmZGZkZmQ7XG59XG5cbi5kcm9wZG93bi1kaXZpZGVyIHtcbiAgYmFja2dyb3VuZC1jb2xvcjogI2RiZGJkYjtcbiAgYm9yZGVyOiBub25lO1xuICBkaXNwbGF5OiBibG9jaztcbiAgaGVpZ2h0OiAxcHg7XG4gIG1hcmdpbjogMDtcbn1cblxuLmRyb3Bkb3duLWNvbnRlbnQge1xuIHBhZGRpbmctYm90dG9tOiAwcmVtOyBcbiBwYWRkaW5nLXRvcDogMHJlbTsgXG4gYm9yZGVyLXJhZGl1czogMnB4O1xufVxuXG5hLmRyb3Bkb3duLWl0ZW0ge1xuICBwYWRkaW5nLXJpZ2h0OiAxcmVtOyBcbn1cblxuLmNvbHVtbntcbiAgcGFkZGluZzogMDtcbn1cblxuLmFkZG9uLXRhZ3tcbiAgcGFkZGluZy1sZWZ0OiAwO1xuICBwYWRkaW5nLXJpZ2h0OiAwO1xuICBib3JkZXItbGVmdDogMXB4IGRvdHRlZCAjZWFlYWVhO1xufVxuXG4uY29sdW1uc3tcbiAgbWFyZ2luLXJpZ2h0OiAwcHg7XG4gIG1hcmdpbi1sZWZ0OiAwcHg7XG59XG5cbi5yb3ctc2VsZWN0b3J7XG5cdHdpZHRoOiAxMHB4O1xufVxuXG4udGFibGUtc2VsZWN0b3J7XG5cdHdpZHRoOiAxMHB4O1xufVxuXG4uc2VsZWN0ZWR7XG5cdGJhY2tncm91bmQtY29sb3I6ICMzMzM7XG4gIGNvbG9yOiAjZmZmO1xufVxuXG4ucm93LW9wdGlvbnMtd3JhcHBlcntcblx0cG9zaXRpb246IHJlbGF0aXZlO1xufVxuXG4ucm93LW9wdGlvbnN7XG5cdHBvc2l0aW9uOiBhYnNvbHV0ZTtcbiAgbGVmdDogLTIzcHg7XG4gIHRvcDogMHB4O1xuICBoZWlnaHQ6IDQ4cHg7XG59XG5cbi50YWJsZS1pY29uIHtcbiAgcGFkZGluZy1yaWdodDogMTBweDtcbiAgcGFkZGluZy10b3A6IDEycHg7XG59XG5cbi5idXR0b257XG4gIG1hcmdpbi1sZWZ0OiAycHg7XG4gIG1hcmdpbi1yaWdodDogMnB4O1xufVxuXG4ubWVudS1iYXJ7XG4gIG1hcmdpbi10b3A6IDEwcHg7XG4gIG1hcmdpbi1ib3R0b206IDAgIWltcG9ydGFudDtcbiAgYWxpZ24taXRlbXM6IGJhc2VsaW5lO1xufVxuXG4ubWVudS1iYXI6Zmlyc3QtY2hpbGQgeyBcbiAgbWFyZ2luLWxlZnQ6IC01cHg7XG59XG5cbi5tZW51LWJhcjpsYXN0LWNoaWxkIHsgXG4gIG1hcmdpbi1yaWdodDogLTVweDtcbn1cblxuLmJ1dHRvbi5pcy1saWdodCB7XG4gIGJvcmRlci1yYWRpdXM6IDJweDtcbn1cbiJdfQ== */"

/***/ }),

/***/ "./src/app/components/study/assays/assay/assay.component.html":
/*!********************************************************************!*\
  !*** ./src/app/components/study/assays/assay/assay.component.html ***!
  \********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"card\" *ngIf=\"assayTable\">\n\t<header class=\"card-header\">\n\t\t<p class=\"card-header-title\">\n\t\t\tAssay\n\t\t</p>\n\t</header>\n\t<div class=\"card-content\">\n\t\t<div class=\"columns mb0 is-vcentered is-hcentered\">\n\t\t\t<div class=\"column is-12 is-paddingless\">\n\t\t\t\t<mat-form-field class=\"full-width bback\">\n\t\t\t\t\t<input (keydown)=\"onKeydown($event, $event.target.value)\" autocomplete=\"off\" matInput (keyup)=\"applyFilter($event.target.value)\" placeholder=\"Filter\">\n\t\t\t\t</mat-form-field>\n\t\t\t</div>\n\t\t</div>\n\t\t<div *ngIf=\"filters.length > 0\" class=\"field is-grouped is-grouped-multiline\">\n\t\t\t<div *ngFor=\"let filter of filters\" class=\"control\">\n\t\t\t\t<div class=\"tags has-addons\">\n\t\t\t\t\t<span class=\"tag is-link\">{{ filter }}</span>\n\t\t\t\t\t<a (click)=\"highlightFilteredRows(filter)\" class=\"tag addon-tag\"><mat-icon>control_camera</mat-icon></a>\n\t\t\t\t\t<a (click)=\"removeFilter(filter)\" class=\"tag is-delete\"></a>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<div class=\"menu-bar columns\">  \n\t\t\t<span class=\"column fadeIn\">\n\t\t\t\t<a (click)=\"addRow()\" class=\"button is-small is-light\">\n\t\t\t\t\t<mat-icon>add</mat-icon> Row\n\t\t\t\t</a>&nbsp;\n\t\t\t\t<mtbls-upload></mtbls-upload>&nbsp;\n\t\t\t\t<span *ngIf=\"assayTable['name']\">\n      \t\t\t\t<mtbls-download [value]=\"assayTable['name']\"></mtbls-download>\n      \t\t\t</span>\n\t\t\t\t<span *ngIf=\"selectedRows.length > 0\">\n\t\t\t\t\t<div class=\"dropdown is-hoverable\">\n\t\t\t\t\t\t<div class=\"dropdown-trigger\">\n\t\t\t\t\t\t\t<button class=\"button is-light is-small\" aria-haspopup=\"true\" aria-controls=\"dropdown-menu\"><mat-icon>file_copy</mat-icon>Copy</button>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div class=\"dropdown-menu\" id=\"dropdown-menu\" role=\"menu\">\n\t\t\t\t\t\t\t<div class=\"dropdown-content\">\n\t\t\t\t\t\t\t\t<a href=\"#\" class=\"dropdown-item\">\n\t\t\t\t\t\t\t\t\tPaste inplace\n\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t<hr class=\"dropdown-divider\">\n\t\t\t\t\t\t\t\t<a href=\"#\" class=\"dropdown-item\">\n\t\t\t\t\t\t\t\t\tPaste at the end\n\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t\t<a (click)=\"openDeleteModal()\" class=\"button is-small is-light\">\n\t\t\t\t\t\t<mat-icon>delete</mat-icon> Delete\n\t\t\t\t\t</a>\n\t\t\t\t</span>\n\t\t\t</span>\n\t\t\t<span class=\"column fadeIn has-text-right\">\n\t\t\t\t<mat-paginator [pageSizeOptions]=\"[500, 1000, 2000]\" showFirstLastButtons></mat-paginator>\n\t\t\t</span>\n\t\t</div>\n\t\t<div class=\"wrapper mat-elevation-z1\">\n\t\t\t<table class=\"mat-elevation-z1\" [dataSource]=\"tableDataSource\" matSort mat-table>\n\t\t\t\t<ng-container matColumnDef=\"Select\" sticky>\n\t\t\t\t\t<th (click)=\"deSelect()\" class=\"clickable table-selector\" mat-header-cell *matHeaderCellDef></th>\n\t\t\t\t\t<td (click)=\"rowClick(row, $event)\" class=\"clickable row-selector hover-highlight\" mat-cell *matCellDef=\"let row\">\n\t\t\t\t\t\t<span class=\"row-options-wrapper\">\n\t\t\t\t\t\t\t<span class=\"row-options hover-button\">\n\t\t\t\t\t\t\t\t<mat-icon class=\"table-icon\">aspect_ratio</mat-icon>\n\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t</span>\n\t\t\t\t\t</td>\n\t\t\t\t</ng-container>\n\t\t\t\t<ng-container *ngFor=\"let column of assayTable.columns\" [sticky]=\"column.sticky\" [matColumnDef]=\"column.columnDef\">\n\t\t\t\t\t<th class=\"clickable hover-highlight\" mat-header-cell *matHeaderCellDef> \n\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t<span (click)=\"headerClick(column, $event)\">{{ formatHeader(column.header) }} </span>\n\t\t\t\t\t\t\t<span mat-sort-header>&nbsp;</span>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</th>\n\t\t\t\t\t<td (dblclick)=\"editCell(row, column, $event)\" (click)=\"cellClick(row, column, $event)\" class=\"clickable\" [ngClass]=\"{'selected': isSelected(row, column)}\" mat-cell *matCellDef=\"let row\"> \n\t\t\t\t\t\t{{ row[column.header] }} \n\t\t\t\t\t</td>\n\t\t\t\t</ng-container>\n\t\t\t\t<tr mat-header-row *matHeaderRowDef=\"assayTable.displayedColumns\"></tr>\n\t\t\t\t<tr mat-row *matRowDef=\"let row; columns: assayTable.displayedColumns;\"></tr>\n\t\t\t</table>\n\t\t</div>\n\t</div>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isEditModalOpen}\">\n\t<form *ngIf=\"form\" [formGroup]=\"form\">\n\t\t<div class=\"modal-background\"></div>\n\t\t<div class=\"modal-card\">\n\t\t\t<div *ngIf=\"isFormBusy\" class=\"load-bar\">\n\t\t\t\t<div class=\"bar\"></div>\n\t\t\t\t<div class=\"bar\"></div>\n\t\t\t\t<div class=\"bar\"></div>\n\t\t\t</div>\n\t\t\t<section class=\"modal-card-body\">\n\t\t\t\t<div class=\"field is-horizontal\">\n\t\t\t\t\t<div class=\"field-body\">\n\t\t\t\t\t\t<div class=\"field\">\n\t\t\t\t\t\t\t<div class=\"control\">\n\t\t\t\t\t\t\t\t<div *ngIf=\"isCellTypeFile; else cellTypeProtocol\">\n\t\t\t\t\t\t\t\t\t<mat-form-field class=\"full-width\">\n\t\t\t\t\t\t\t\t\t  <mat-select formControlName=\"cell\" placeholder=\"{{ formatCellTitle(selectedCell['column']['columnDef']) }}\">\n\t\t\t\t\t\t\t\t\t    <mat-option *ngFor=\"let file of files\" [value]=\"file.file\">\n\t\t\t\t\t\t\t\t\t      {{file.file}}\n\t\t\t\t\t\t\t\t\t    </mat-option>\n\t\t\t\t\t\t\t\t\t  </mat-select>\n\t\t\t\t\t\t\t\t\t</mat-form-field>\n\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t<ng-template #cellTypeProtocol>\n\t\t\t\t\t\t\t\t\t<div *ngIf=\"isCellTypeProtocol; else cellInputField\">\n\t\t\t\t\t\t\t\t\t\t<!-- <mat-form-field class=\"full-width\">\n\t\t\t\t\t\t\t\t\t\t  <mat-select formControlName=\"cell\" placeholder=\"{{ formatCellTitle(selectedCell['column']['columnDef']) }}\">\n\t\t\t\t\t\t\t\t\t\t    <mat-option *ngFor=\"let file of files\" [value]=\"file.file\">\n\t\t\t\t\t\t\t\t\t\t      {{file.file}}\n\t\t\t\t\t\t\t\t\t\t    </mat-option>\n\t\t\t\t\t\t\t\t\t\t  </mat-select>\n\t\t\t\t\t\t\t\t\t\t</mat-form-field> -->\n\t\t\t\t\t\t\t\t\t\thi hello\n\t\t\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t\t\t<ng-template #cellInputField>\n\t\t\t\t\t\t\t\t\t\t<label>{{ formatCellTitle(selectedCell['column']['columnDef']) }}</label>\n\t\t\t\t\t\t\t\t\t\t<mat-form-field class=\"full-width\">\n\t\t\t\t\t\t\t\t\t\t\t<input class=\"no-scroll\" \n\t\t\t\t\t\t\t\t\t\t\tformControlName=\"cell\"\n\t\t\t\t\t\t\t\t\t\t\tmatInput>\n\t\t\t\t\t\t\t\t\t\t\t<!-- <mat-hint>{{ fieldValidation('doi').description }}</mat-hint> -->\n\t\t\t\t\t\t\t\t\t\t\t<!-- <mat-error\n\t\t\t\t\t\t\t\t\t\t\t*ngIf=\"form.get('title').errors &&\n\t\t\t\t\t\t\t\t\t\t\tform.get('title').dirty &&\n\t\t\t\t\t\t\t\t\t\t\tform.get('title').errors.title\">\n\t\t\t\t\t\t\t\t\t\t\t{{ form.get('title').errors.title.error }}\n\t\t\t\t\t\t\t\t\t\t\t</mat-error> -->\n\t\t\t\t\t\t\t\t\t\t</mat-form-field>\n\t\t\t\t\t\t\t\t\t</ng-template>\n\t\t\t\t\t\t\t\t</ng-template>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</section>\n\t\t\t<footer class=\"modal-card-foot buttons is-right\">\n\t\t\t\t<button *ngIf=\"!form.pristine\" [disabled]=\"!form.valid || isFormBusy\" (click)='save()' class=\"button is-info\">\n\t\t\t\t\t<mat-spinner [diameter]=\"20\" [strokeWidth]=\"3\" *ngIf=\"isFormBusy\"></mat-spinner>\n\t\t\t\t\tSave\n\t\t\t\t</button>\n\t\t\t\t<button *ngIf=\"form.pristine\" (click)='closeEditModal()' class=\"button is-info\">OK</button>\n\t\t\t\t<button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeEditModal()'>Cancel</button>\n\t\t\t</footer>\n\t\t</div>\n\t</form>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isDeleteModalOpen}\">\n\t<div class=\"modal-background\"></div>\n\t<div class=\"modal-card\">\n\t\t<section class=\"modal-card-body\">\n\t\t\t<p>Are you sure you want to delete the selected rows?</p>\n\t\t</section>\n\t\t<footer class=\"modal-card-foot\">\n\t\t\t<div class=\"columns is-gapless full-width\">\n\t\t\t\t<div class=\"column is-half\">\n\t\t\t\t\t<button (click)='closeDelete()' class=\"button is-info\">Cancel</button>\n\t\t\t\t</div>\n\t\t\t\t<div class=\"column is-half has-text-right\">\n\t\t\t\t\t<button (click)='deleteSelectedRows()' class=\"button is-danger\">OK! Delete Permanently</button>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</footer>\n\t</div>\n</div>"

/***/ }),

/***/ "./src/app/components/study/assays/assay/assay.component.ts":
/*!******************************************************************!*\
  !*** ./src/app/components/study/assays/assay/assay.component.ts ***!
  \******************************************************************/
/*! exports provided: AssayComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AssayComponent", function() { return AssayComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_2__);
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_5__);
/* harmony import */ var tassign__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! tassign */ "./node_modules/tassign/lib/index.js");
/* harmony import */ var tassign__WEBPACK_IMPORTED_MODULE_6___default = /*#__PURE__*/__webpack_require__.n(tassign__WEBPACK_IMPORTED_MODULE_6__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var AssayComponent = /** @class */ (function () {
    function AssayComponent(fb, metabolightsService, ngRedux) {
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.ngRedux = ngRedux;
        this.selectedRows = [];
        this.selectedColumns = [];
        this.selectedCells = [];
        this.filter = '';
        this.filters = [];
        this.lastRowSelection = null;
        this.lastColSelection = null;
        this.selectedColumn = null;
        this.selectedColumnValues = null;
        this.selectedCell = {};
        this.fileColumns = [];
        this.protocolColumns = [];
        this.files = [];
        this.isDeleteModalOpen = false;
        this.isEditModalOpen = false;
        this.isCellTypeFile = false;
        this.isCellTypeProtocol = false;
        this.validationsId = 'assays.assay';
    }
    AssayComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.tableDataSource = new _angular_material__WEBPACK_IMPORTED_MODULE_1__["MatTableDataSource"](this.assayTable['assayData'].rows);
        this.validations.assay.default_order.forEach(function (col) {
            if (col.dataType == 'file') {
                _this.fileColumns.push(col.header);
            }
            else if (col.dataType == 'protocol') {
                _this.protocolColumns.push(col.header);
            }
        });
        this.metabolightsService.getStudyFiles().subscribe(function (data) {
            _this.files = data.studyFiles;
        });
    };
    AssayComponent.prototype.ngAfterViewInit = function () {
        this.tableDataSource.sort = this.sort;
    };
    AssayComponent.prototype.onKeydown = function (event, filterValue) {
        var _this = this;
        if (event.key === "Enter") {
            var data_1 = [];
            if (this.filters.indexOf(filterValue) < 0) {
                this.filters.push(filterValue);
                event.target.value = '';
            }
            this.tableDataSource.filter = '';
            this.filters.forEach(function (f) {
                data_1 = data_1.concat(_this.tableDataSource.data.filter(function (d) { return _this.getDataString(d).indexOf(f.toLowerCase()) > -1; }));
            });
            this.tableDataSource.data = data_1;
        }
    };
    AssayComponent.prototype.isSelected = function (row, column) {
        if ((row && column) && this.selectedCells.length > 0) {
            return this.selectedCells.filter(function (cell) { return (cell[0] == column.columnDef && cell[1] == row.index); }).length > 0;
        }
        else if (this.selectedColumns.length == 0) {
            if (this.selectedRows.indexOf(row.index) > -1) {
                return true;
            }
        }
        else if (this.selectedRows.length == 0) {
            if (this.selectedColumns.indexOf(column.columnDef) > -1) {
                return true;
            }
        }
        return false;
    };
    AssayComponent.prototype.rowClick = function (row, event) {
        this.selectedCells = [];
        this.selectedColumns = [];
        var entryIndex = row.index;
        var rowIndex = this.selectedRows.indexOf(entryIndex);
        if (event && event.altKey) {
            if (rowIndex > -1) {
                this.selectedRows.splice(rowIndex, 1);
            }
            else {
                this.selectedRows.push(entryIndex);
            }
        }
        else if (event && event.shiftKey) {
            var lastSelectionIndex = null;
            var lastRowIndex = -1;
            var rowNamesArray = this.assayTable.assayData.rows.map(function (e) { return e.index; });
            if (this.lastRowSelection) {
                lastSelectionIndex = this.lastRowSelection.index;
                lastRowIndex = rowNamesArray.indexOf(lastSelectionIndex);
            }
            else {
                lastRowIndex = 0;
            }
            var currentRowIndex = rowNamesArray.indexOf(entryIndex);
            var currentSelection = [];
            if (lastRowIndex > currentRowIndex) {
                currentSelection = rowNamesArray.slice(currentRowIndex, lastRowIndex + 1);
            }
            else {
                currentSelection = rowNamesArray.slice(lastRowIndex, currentRowIndex + 1);
            }
            this.selectedRows = this.selectedRows.concat(currentSelection);
        }
        else {
            if (rowIndex < 0) {
                this.selectedRows = [entryIndex];
            }
            else {
                this.selectedRows = [];
            }
        }
        this.lastRowSelection = row;
    };
    AssayComponent.prototype.cellClick = function (row, column, event) {
        if (event.altKey) {
            this.selectedCells.push([column.columnDef, row.index]);
        }
        else {
            this.selectedCells = [[column.columnDef, row.index]];
        }
    };
    AssayComponent.prototype.toTitleCase = function (str) {
        return str.replace(/\w\S*/g, function (txt) {
            return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
        });
    };
    AssayComponent.prototype.formatCellTitle = function (term) {
        return term.replace("_", " ");
    };
    AssayComponent.prototype.editCell = function (row, column, event) {
        this.isCellTypeFile = false;
        this.isCellTypeProtocol = false;
        this.isEditModalOpen = true;
        this.selectedCell['row'] = row;
        this.selectedCell['column'] = column;
        if (this.fileColumns.indexOf(column.header) > -1) {
            this.isCellTypeFile = true;
        }
        else if (this.protocolColumns.indexOf(column.header) > -1) {
            this.isCellTypeProtocol = true;
        }
        this.form = this.fb.group({
            cell: [row[column.columnDef]],
        });
    };
    AssayComponent.prototype.closeEditModal = function () {
        this.isEditModalOpen = false;
    };
    AssayComponent.prototype.deSelect = function () {
        this.selectedRows = [];
        this.selectedColumns = [];
        this.selectedCells = [];
    };
    AssayComponent.prototype.headerClick = function (column, event) {
        this.selectedCells = [];
        this.selectedRows = [];
        var entryIndex = column.columnDef;
        var colIndex = this.selectedColumns.indexOf(entryIndex);
        if (event.altKey) {
            if (colIndex > -1) {
                this.selectedRows.splice(colIndex, 1);
            }
            else {
                this.selectedRows.push(entryIndex);
            }
        }
        else if (event.shiftKey) {
            var lastSelectionIndex = null;
            var lastRowIndex = -1;
            var colNamesArray = this.assayTable.displayedColumns.map(function (e) { return e.columnDef; });
            if (this.lastColSelection) {
                lastSelectionIndex = this.lastColSelection.index;
                lastRowIndex = colNamesArray.indexOf(lastSelectionIndex);
            }
            else {
                lastRowIndex = 0;
            }
            var currentRowIndex = colNamesArray.indexOf(entryIndex);
            var currentSelection = [];
            if (lastRowIndex > currentRowIndex) {
                currentSelection = colNamesArray.slice(currentRowIndex, lastRowIndex + 1);
            }
            else {
                currentSelection = colNamesArray.slice(lastRowIndex, currentRowIndex + 1);
            }
            this.selectedColumns = this.selectedColumns.concat(currentSelection);
        }
        else {
            if (colIndex < 0) {
                this.selectedColumns = [entryIndex];
            }
            else {
                this.selectedColumns = [];
            }
        }
        this.lastColSelection = column;
    };
    AssayComponent.prototype.selected = function (event) {
        this.form.get('cell').setValue(event.option.value.file);
    };
    AssayComponent.prototype.keys = function (object) {
        return Object.keys(object);
    };
    AssayComponent.prototype.removeFilter = function (filter) {
        var _this = this;
        this.filters = this.filters.filter(function (e) { return e !== filter; });
        this.tableDataSource.filter = '';
        if (this.filters.length > 0) {
            var data_2 = [];
            this.filters.forEach(function (f) {
                data_2 = data_2.concat(_this.tableDataSource.data.filter(function (d) { return _this.getDataString(d).indexOf(f.toLowerCase()) > -1; }));
            });
            this.tableDataSource.data = data_2;
        }
        else {
            this.tableDataSource.data = this.assayTable['assayData'].rows;
        }
    };
    AssayComponent.prototype.highlightFilteredRows = function (term) {
        var _this = this;
        this.selectedRows = this.selectedRows.concat(this.assayTable.assayData.rows.filter(function (f) { return _this.getDataString(f).indexOf(term.toLowerCase()) != -1; }).map(function (p) { return p.index; }));
    };
    AssayComponent.prototype.getDataString = function (row) {
        var rowString = "";
        Object.keys(row).forEach(function (prop) { return rowString = rowString + row[prop]; });
        return rowString;
    };
    AssayComponent.prototype.applyFilter = function (filterValue) {
        if (filterValue != '') {
            this.tableDataSource.data = this.assayTable['assayData'].rows;
            this.tableDataSource.filter = filterValue.trim().toLowerCase();
        }
    };
    AssayComponent.prototype.getUnique = function (arr) {
        return arr.filter(function (value, index, array) {
            return array.indexOf(value) == index;
        });
    };
    AssayComponent.prototype.getEmptyRow = function () {
        var obj = Object(tassign__WEBPACK_IMPORTED_MODULE_6__["tassign"])({}, this.assayTable.assayData.rows[0]);
        Object.keys(obj).forEach(function (prop) {
            obj[prop] = "";
        });
        return obj;
    };
    AssayComponent.prototype.save = function () {
        var _this = this;
        this.selectedCell['row'][this.selectedCell['column']['columnDef']] = this.form.get('cell').value;
        this.metabolightsService.updateAssayRow(this.assayName, { "assayData": [this.selectedCell['row']] }).subscribe(function (res) {
            toastr__WEBPACK_IMPORTED_MODULE_5__["success"]("MAF entry updated successfully", "Success", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
            _this.persistData(res, _this.assayName);
            _this.rowClick(_this.selectedCell['row'], null);
            _this.closeEditModal();
        }, function (err) {
        });
    };
    AssayComponent.prototype.addRow = function () {
        var _this = this;
        this.metabolightsService.addAssayRow(this.assayName, { "assayData": [this.getEmptyRow()] }).subscribe(function (res) {
            toastr__WEBPACK_IMPORTED_MODULE_5__["success"]("Rows add successfully to the end of the maf sheet", "Success", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
            _this.persistData(res, _this.assayName);
        }, function (err) {
        });
    };
    AssayComponent.prototype.persistData = function (data, filename) {
        var columns = [];
        Object.keys(data.assayHeader).forEach(function (key) {
            var fn = "element['" + key + "']";
            columns.push({
                "columnDef": key,
                "sticky": "false",
                "header": key,
                "cell": function (element) { return eval(fn); }
            });
        });
        var displayedColumns = columns.map(function (a) { return a.columnDef; });
        displayedColumns.unshift("Select");
        displayedColumns.sort(function (a, b) {
            return data.assayHeader[a] - data.assayHeader[b];
        });
        data['columns'] = columns;
        data['displayedColumns'] = displayedColumns;
        this.ngRedux.dispatch({ type: 'ADD_STUDY_ASSAY_TABLE', body: {
                'assayTable': data,
                'assay': filename
            } });
    };
    AssayComponent.prototype.deleteSelectedRows = function () {
        var _this = this;
        this.metabolightsService.deleteAssayRow(this.assayName, this.getUnique(this.selectedRows).join(",")).subscribe(function (res) {
            _this.isDeleteModalOpen = false;
            toastr__WEBPACK_IMPORTED_MODULE_5__["success"]("Rows delete successfully", "Success", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
            _this.persistData(res, _this.assayName);
        }, function (err) { });
    };
    AssayComponent.prototype.closeDelete = function () {
        this.isDeleteModalOpen = false;
    };
    AssayComponent.prototype.openDeleteModal = function () {
        this.isDeleteModalOpen = true;
    };
    AssayComponent.prototype.keyExists = function (object, key) {
        return object[key] != undefined;
    };
    AssayComponent.prototype.valueExists = function (array, value) {
        return array.indexOf(value) >= 0;
    };
    AssayComponent.prototype.isObject = function (item) {
        return (typeof item === "object" && !Array.isArray(item) && item !== null);
    };
    AssayComponent.prototype.formatHeader = function (term) {
        return term.replace(/\.[^/.]+$/, "");
    };
    Object.defineProperty(AssayComponent.prototype, "validation", {
        get: function () {
            if (this.validationsId.includes(".")) {
                var arr = this.validationsId.split(".");
                var tempValidations = JSON.parse(JSON.stringify(this.validations));
                ;
                while (arr.length && (tempValidations = tempValidations[arr.shift()]))
                    ;
                return tempValidations;
            }
            return this.validations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    AssayComponent.prototype.fieldValidation = function (fieldId) {
        return this.validation[fieldId];
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], AssayComponent.prototype, "validations", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('assayName'),
        __metadata("design:type", Object)
    ], AssayComponent.prototype, "assayName", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('assayTable'),
        __metadata("design:type", Object)
    ], AssayComponent.prototype, "assayTable", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])(_angular_material__WEBPACK_IMPORTED_MODULE_1__["MatPaginator"]),
        __metadata("design:type", _angular_material__WEBPACK_IMPORTED_MODULE_1__["MatPaginator"])
    ], AssayComponent.prototype, "paginator", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])(_angular_material__WEBPACK_IMPORTED_MODULE_1__["MatSort"]),
        __metadata("design:type", _angular_material__WEBPACK_IMPORTED_MODULE_1__["MatSort"])
    ], AssayComponent.prototype, "sort", void 0);
    AssayComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-assay',
            template: __webpack_require__(/*! ./assay.component.html */ "./src/app/components/study/assays/assay/assay.component.html"),
            styles: [__webpack_require__(/*! ./assay.component.css */ "./src/app/components/study/assays/assay/assay.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_4__["MetabolightsService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_2__["NgRedux"]])
    ], AssayComponent);
    return AssayComponent;
}());



/***/ }),

/***/ "./src/app/components/study/assays/assays.component.css":
/*!**************************************************************!*\
  !*** ./src/app/components/study/assays/assays.component.css ***!
  \**************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvYXNzYXlzL2Fzc2F5cy5jb21wb25lbnQuY3NzIn0= */"

/***/ }),

/***/ "./src/app/components/study/assays/assays.component.html":
/*!***************************************************************!*\
  !*** ./src/app/components/study/assays/assays.component.html ***!
  \***************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<!-- {{  | json }} -->\n<div *ngFor=\"let assay of assays\">\n\t<mtbls-assay [assayName]=\"assay.name\" [assayTable]=\"assay\" [validations]=\"validations\"></mtbls-assay>\n</div>"

/***/ }),

/***/ "./src/app/components/study/assays/assays.component.ts":
/*!*************************************************************!*\
  !*** ./src/app/components/study/assays/assays.component.ts ***!
  \*************************************************************/
/*! exports provided: AssaysComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AssaysComponent", function() { return AssaysComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var AssaysComponent = /** @class */ (function () {
    function AssaysComponent() {
        this.assays = [];
        this.validationsId = 'assays';
    }
    AssaysComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.studyAssays.subscribe(function (value) {
            Object.keys(value).forEach(function (key) {
                value[key]['name'] = key;
                _this.assays.push(value[key]);
            });
        });
    };
    Object.defineProperty(AssaysComponent.prototype, "validations", {
        get: function () {
            if (this.validationsId.includes(".")) {
                var arr = this.validationsId.split(".");
                var tempValidations = JSON.parse(JSON.stringify(this.studyValidations));
                ;
                while (arr.length && (tempValidations = tempValidations[arr.shift()]))
                    ;
                return tempValidations;
            }
            return this.studyValidations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], AssaysComponent.prototype, "studyValidations", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.assayTables; }),
        __metadata("design:type", Object)
    ], AssaysComponent.prototype, "studyAssays", void 0);
    AssaysComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-assays',
            template: __webpack_require__(/*! ./assays.component.html */ "./src/app/components/study/assays/assays.component.html"),
            styles: [__webpack_require__(/*! ./assays.component.css */ "./src/app/components/study/assays/assays.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], AssaysComponent);
    return AssaysComponent;
}());



/***/ }),

/***/ "./src/app/components/study/description/description.component.css":
/*!************************************************************************!*\
  !*** ./src/app/components/study/description/description.component.css ***!
  \************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvZGVzY3JpcHRpb24vZGVzY3JpcHRpb24uY29tcG9uZW50LmNzcyJ9 */"

/***/ }),

/***/ "./src/app/components/study/description/description.component.html":
/*!*************************************************************************!*\
  !*** ./src/app/components/study/description/description.component.html ***!
  \*************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"hover-highlight px-20 mtbls-section\">\n  <div *ngIf=\"description != undefined\">\n    <b><small class=\"has-text-grey\">ABSTRACT</small></b> <br>\n    <p [innerHTML]=\"description\" class=\"clickable\" (click)='openModal()'></p>\n  </div>\n  <!-- <br>\n  <mtbls-design-descriptors  [readOnly]=\"true\" [inline]=\"true\" [value]=\"studyDesignDescriptors | async\" [validations]=\"validations | async\"></mtbls-design-descriptors> -->\n</div>\n<div class=\"modal\" [ngClass]=\"{'is-active': isModalOpen}\">\n  <form *ngIf=\"form\" [formGroup]=\"form\">\n    <div class=\"modal-background\"></div>\n    <div class=\"modal-card vw80\">\n      <div *ngIf=\"isFormBusy\" class=\"load-bar\">\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n      </div>\n      <section class=\"modal-card-body\">\n          <div class=\"field is-horizontal\">\n            <div class=\"field-body\">\n              <div class=\"field\">\n                <div class=\"control\">\n                    <mat-label>Study description</mat-label>\n                    <ngx-wig\n                      matInput\n                      formControlName=\"description\"\n                      [placeholder]=\"validation.placeholder\" \n                    >\n                    </ngx-wig>\n                    <mat-hint><small>{{ validation.description }}</small></mat-hint><br>\n                    <mat-error\n                    *ngIf=\"form.get('description').errors &&\n                          form.get('description').dirty &&\n                          form.get('description').errors.description\">\n                          <small>{{ form.get('description').errors.description.error }}</small>\n                    </mat-error>\n                </div>\n              </div>\n            </div>\n          </div>\n      </section>\n      <footer class=\"modal-card-foot buttons is-right\">\n        <button *ngIf=\"form.get('description').dirty\" [disabled]=\"!form.valid || isFormBusy\" (click)='save()' class=\"button is-info\">\n          <mat-spinner [diameter]=\"20\" [strokeWidth]=\"3\" *ngIf=\"isFormBusy\"></mat-spinner>\n          Save\n        </button>\n        <button *ngIf=\"!form.get('description').dirty\" (click)='closeModal()' class=\"button is-info\">OK</button>\n        <button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeModal()'>Cancel</button>\n      </footer>\n    </div>\n  </form>\n</div>"

/***/ }),

/***/ "./src/app/components/study/description/description.component.ts":
/*!***********************************************************************!*\
  !*** ./src/app/components/study/description/description.component.ts ***!
  \***********************************************************************/
/*! exports provided: DescriptionComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DescriptionComponent", function() { return DescriptionComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _description_validator__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./description.validator */ "./src/app/components/study/description/description.validator.ts");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_5__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






var DescriptionComponent = /** @class */ (function () {
    function DescriptionComponent(fb, metabolightsService, ngRedux) {
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.ngRedux = ngRedux;
        this.isFormBusy = false;
        this.validationsId = 'description';
        this.isModalOpen = false;
        this.hasChanges = false;
    }
    DescriptionComponent.prototype.ngOnInit = function () {
    };
    DescriptionComponent.prototype.openModal = function () {
        this.initialiseForm();
        this.isModalOpen = true;
    };
    DescriptionComponent.prototype.initialiseForm = function () {
        // this.description = this.description.replace(new RegExp("<br />", 'g'), '\n')
        this.isFormBusy = false;
        this.form = this.fb.group({
            description: [this.description, Object(_description_validator__WEBPACK_IMPORTED_MODULE_4__["ValidateStudyDescription"])(this.validation)]
        });
    };
    DescriptionComponent.prototype.closeModal = function () {
        this.form = null;
        this.isModalOpen = false;
    };
    DescriptionComponent.prototype.save = function () {
        var _this = this;
        this.isFormBusy = true;
        this.metabolightsService.saveAbstract(this.compileBody(this.form.get('description').value.replace(new RegExp('\n', 'g'), "<br />"))).subscribe(function (res) {
            _this.ngRedux.dispatch({ type: 'SET_STUDY_ABSTRACT', body: res });
            _this.form.get('description').setValue(res.description);
            _this.form.markAsPristine();
            _this.isFormBusy = false;
            toastr__WEBPACK_IMPORTED_MODULE_5__["success"]('Abstract updated.', "Success", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
        }, function (err) {
            _this.isFormBusy = false;
        });
    };
    DescriptionComponent.prototype.compileBody = function (description) {
        return {
            'description': description,
        };
    };
    Object.defineProperty(DescriptionComponent.prototype, "validation", {
        get: function () {
            return this.studyValidations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    DescriptionComponent.prototype.ngOnChanges = function (changes) {
        if (changes.value != undefined) {
            this.description = changes.value.currentValue;
        }
        if (changes.studyValidations != undefined)
            this.studyValidations = changes.studyValidations.currentValue;
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", String)
    ], DescriptionComponent.prototype, "description", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], DescriptionComponent.prototype, "studyValidations", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.studyDesignDescriptors; }),
        __metadata("design:type", Array)
    ], DescriptionComponent.prototype, "studyDesignDescriptors", void 0);
    DescriptionComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-description',
            template: __webpack_require__(/*! ./description.component.html */ "./src/app/components/study/description/description.component.html"),
            styles: [__webpack_require__(/*! ./description.component.css */ "./src/app/components/study/description/description.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__["MetabolightsService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["NgRedux"]])
    ], DescriptionComponent);
    return DescriptionComponent;
}());



/***/ }),

/***/ "./src/app/components/study/description/description.validator.ts":
/*!***********************************************************************!*\
  !*** ./src/app/components/study/description/description.validator.ts ***!
  \***********************************************************************/
/*! exports provided: ValidateStudyDescription */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ValidateStudyDescription", function() { return ValidateStudyDescription; });
function ValidateStudyDescription(validation) {
    return function (control) {
        var value = control.value;
        var invalid = false;
        var errorMessage = "";
        validation.rules.forEach(function (rule) {
            switch (rule.condition) {
                case "min": {
                    if (value.toString().length < rule.value) {
                        invalid = true;
                        errorMessage = errorMessage + rule.error;
                    }
                    break;
                }
            }
        });
        if (invalid) {
            return { 'description': { 'error': errorMessage } };
        }
        return null;
    };
}


/***/ }),

/***/ "./src/app/components/study/design-descriptors/design-descriptor/design-descriptor.component.css":
/*!*******************************************************************************************************!*\
  !*** ./src/app/components/study/design-descriptors/design-descriptor/design-descriptor.component.css ***!
  \*******************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".tags .tag {\n    margin-bottom: 1rem;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zdHVkeS9kZXNpZ24tZGVzY3JpcHRvcnMvZGVzaWduLWRlc2NyaXB0b3IvZGVzaWduLWRlc2NyaXB0b3IuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtJQUNJLG9CQUFvQjtDQUN2QiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvZGVzaWduLWRlc2NyaXB0b3JzL2Rlc2lnbi1kZXNjcmlwdG9yL2Rlc2lnbi1kZXNjcmlwdG9yLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyIudGFncyAudGFnIHtcbiAgICBtYXJnaW4tYm90dG9tOiAxcmVtO1xufSJdfQ== */"

/***/ }),

/***/ "./src/app/components/study/design-descriptors/design-descriptor/design-descriptor.component.html":
/*!********************************************************************************************************!*\
  !*** ./src/app/components/study/design-descriptors/design-descriptor/design-descriptor.component.html ***!
  \********************************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div *ngIf=\"addNewDescriptor || descriptor == null; else showDescriptorDetails\">\n    <a class=\"button is-light is-pulled-right is-small hover-button\" (click)='openModal()'>+ Add descriptor</a>\n</div>\n<ng-template #showDescriptorDetails>\n\t<div class=\"control\">\n\t\t<div class=\"tags has-addons\">\n\t\t  <span class=\"tag is-info clickable is-small capitalize\" (click)='openModal()'>{{ descriptor.annotationValue }}</span>\n\t\t</div>\n\t</div>\t\n</ng-template>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isTimeLineModalOpen}\">\n  <div class=\"modal-background\"></div>\n  <div class=\"modal-card\">\n    <section class=\"modal-card-body\">\n      <div class=\"timeline\">\n        <div class=\"timeline-item\">\n          <div class=\"timeline-marker\"></div>\n          <div class=\"timeline-content\">\n            <p class=\"heading\">January 2016</p>\n            <p>Timeline content - Can include any HTML element</p>\n          </div>\n        </div>\n        <div class=\"timeline-item\">\n          <div class=\"timeline-marker is-image is-32x32\">\n            <img src=\"http://bulma.io/images/placeholders/32x32.png\">\n          </div>\n          <div class=\"timeline-content\">\n            <p class=\"heading\">February 2016</p>\n            <p>Timeline content - Can include any HTML element</p>\n          </div>\n        </div>\n        <header class=\"timeline-header\">\n          <span class=\"tag is-primary\">2017</span>\n        </header>\n        <div class=\"timeline-item\">\n          <div class=\"timeline-marker is-icon\">\n            <i class=\"fa fa-flag\"></i>\n          </div>\n          <div class=\"timeline-content\">\n            <p class=\"heading\">March 2017</p>\n            <p>Timeline content - Can include any HTML element</p>\n          </div>\n        </div>\n      </div>\n    </section>\n    <footer class=\"modal-card-foot\">\n      <div class=\"columns is-gapless full-width\">\n        <div class=\"column is-half\">\n        </div>\n        <div class=\"column is-half has-text-right\">\n          <button (click)='closeHistory()' class=\"button is-info\">OK</button>\n        </div>\n      </div>\n    </footer>\n  </div>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isDeleteModalOpen}\">\n  <div class=\"modal-background\"></div>\n  <div class=\"modal-card\">\n    <section class=\"modal-card-body\">\n      <p>Are you sure you want to delete?</p>\n    </section>\n    <footer class=\"modal-card-foot\">\n      <div class=\"columns is-gapless full-width\">\n        <div class=\"column is-half\">\n          <button (click)='closeDelete()' class=\"button is-info\">Cancel</button>\n        </div>\n        <div class=\"column is-half has-text-right\">\n          <button (click)='delete()' class=\"button is-danger\">OK! Delete Permanently</button>\n        </div>\n      </div>\n    </footer>\n  </div>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isModalOpen}\">\n  <form *ngIf=\"form\" [formGroup]=\"form\">\n    <div class=\"modal-background\"></div>\n    <div class=\"modal-card\">\n      <div *ngIf=\"isFormBusy\" class=\"load-bar\">\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n      </div>\n      <section class=\"modal-card-body\">\n        <mtbls-ontology #publicationDescriptor [validations]=\"validation\" (changed)=\"onChanges($event)\" [values]='[descriptor]' [inline]=\"true\"></mtbls-ontology>\n      </section>\n      <footer class=\"modal-card-foot\">\n        <div class=\"columns is-gapless full-width\">\n          <div class=\"column is-half\">\n            <button *ngIf=\"!addNewDescriptor\" class=\"button is-danger is-pulled-left\" (click)=\"confirmDelete()\"><mat-icon>delete_outline</mat-icon></button>\n            <!-- <button *ngIf=\"!addNewDescriptor\" class=\"button is-light is-pulled-left\" (click)='showHistory()' ><mat-icon>history</mat-icon></button> -->\n          </div>\n          <div class=\"column is-half has-text-right\">\n            <button *ngIf=\"!form.pristine\" [disabled]=\"!form.valid || isFormBusy\" (click)='save()' class=\"button is-info\">\n              <mat-spinner [diameter]=\"20\" [strokeWidth]=\"3\" *ngIf=\"isFormBusy\"></mat-spinner>\n              Save\n            </button>\n            <button *ngIf=\"form.pristine\" (click)='closeModal()' class=\"button is-info\">OK</button>\n            <button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeModal()'>Cancel</button>\n          </div>\n        </div>\n      </footer>\n    </div>\n  </form>\n</div>"

/***/ }),

/***/ "./src/app/components/study/design-descriptors/design-descriptor/design-descriptor.component.ts":
/*!******************************************************************************************************!*\
  !*** ./src/app/components/study/design-descriptors/design-descriptor/design-descriptor.component.ts ***!
  \******************************************************************************************************/
/*! exports provided: DesignDescriptorComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DesignDescriptorComponent", function() { return DesignDescriptorComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./../../../../models/mtbl/mtbls/common/mtbls-ontology */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_4___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_4__);
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_5__);
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_6___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_6__);
/* harmony import */ var _ontology_ontology_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./../../ontology/ontology.component */ "./src/app/components/study/ontology/ontology.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};








var DesignDescriptorComponent = /** @class */ (function () {
    function DesignDescriptorComponent(fb, metabolightsService, ngRedux) {
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.ngRedux = ngRedux;
        this.validationsId = 'studyDesignDescriptors';
        this.isModalOpen = false;
        this.isTimeLineModalOpen = false;
        this.isDeleteModalOpen = false;
        this.isFormBusy = false;
        this.addNewDescriptor = false;
    }
    DesignDescriptorComponent.prototype.ngOnInit = function () {
        if (this.descriptor == null) {
            this.addNewDescriptor = true;
        }
    };
    DesignDescriptorComponent.prototype.onChanges = function () {
        this.form.markAsDirty();
    };
    DesignDescriptorComponent.prototype.showHistory = function () {
        this.isModalOpen = false;
        this.isTimeLineModalOpen = true;
    };
    DesignDescriptorComponent.prototype.closeHistory = function () {
        this.isTimeLineModalOpen = false;
        this.isModalOpen = true;
    };
    DesignDescriptorComponent.prototype.openModal = function () {
        if (!this.readOnly) {
            this.initialiseForm();
            if (this.addNewDescriptor) {
                this.descriptor = null;
            }
            this.isModalOpen = true;
        }
    };
    DesignDescriptorComponent.prototype.initialiseForm = function () {
        this.isFormBusy = false;
        this.form = this.fb.group({});
    };
    DesignDescriptorComponent.prototype.confirmDelete = function () {
        this.isModalOpen = false;
        this.isDeleteModalOpen = true;
    };
    DesignDescriptorComponent.prototype.closeDelete = function () {
        this.isDeleteModalOpen = false;
        this.isModalOpen = true;
    };
    DesignDescriptorComponent.prototype.closeModal = function () {
        this.isModalOpen = false;
    };
    DesignDescriptorComponent.prototype.save = function () {
        var _this = this;
        this.isFormBusy = true;
        if (!this.addNewDescriptor) {
            this.metabolightsService.updateDesignDescriptor(this.descriptor.annotationValue, this.compileBody()).subscribe(function (res) {
                _this.updateDesignDescriptors(res, 'Design descriptor updated.');
            }, function (err) {
                _this.isFormBusy = false;
            });
        }
        else {
            this.metabolightsService.saveDesignDescriptor(this.compileBody()).subscribe(function (res) {
                _this.updateDesignDescriptors(res, 'Design descriptor saved.');
                _this.statusComponent.values = [];
                _this.isModalOpen = false;
            }, function (err) {
                _this.isFormBusy = false;
            });
        }
    };
    DesignDescriptorComponent.prototype.delete = function () {
        var _this = this;
        this.metabolightsService.deleteDesignDescriptor(this.descriptor.annotationValue).subscribe(function (res) {
            _this.updateDesignDescriptors(res, 'Design descriptor deleted.');
            _this.isDeleteModalOpen = false;
            _this.isModalOpen = false;
        }, function (err) {
            _this.isFormBusy = false;
        });
    };
    DesignDescriptorComponent.prototype.compileBody = function () {
        var jsonConvert = new json2typescript__WEBPACK_IMPORTED_MODULE_6__["JsonConvert"]();
        return { "studyDesignDescriptor": jsonConvert.deserialize(this.statusComponent.values[0], _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_3__["Ontology"]) };
    };
    DesignDescriptorComponent.prototype.updateDesignDescriptors = function (data, message) {
        var _this = this;
        this.metabolightsService.getDesignDescriptors().subscribe(function (res) {
            _this.ngRedux.dispatch({ type: 'UPDATE_STUDY_DESIGN_DESCRIPTORS', body: {
                    'studyDesignDescriptors': res.studyDesignDescriptors
                } });
        });
        this.form.markAsPristine();
        this.initialiseForm();
        this.isModalOpen = true;
        toastr__WEBPACK_IMPORTED_MODULE_5__["success"](message, "Success", {
            "timeOut": "2500",
            "positionClass": "toast-top-center",
            "preventDuplicates": true,
            "extendedTimeOut": 0,
            "tapToDismiss": false
        });
    };
    Object.defineProperty(DesignDescriptorComponent.prototype, "validation", {
        get: function () {
            if (this.validationsId.includes(".")) {
                var arr = this.validationsId.split(".");
                var tempValidations = JSON.parse(JSON.stringify(this.validations));
                ;
                while (arr.length && (tempValidations = tempValidations[arr.shift()]))
                    ;
                return tempValidations;
            }
            return this.validations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    DesignDescriptorComponent.prototype.fieldValidation = function (fieldId) {
        return this.validation[fieldId];
    };
    DesignDescriptorComponent.prototype.getFieldValue = function (name) {
        return this.form.get(name).value;
    };
    DesignDescriptorComponent.prototype.setFieldValue = function (name, value) {
        return this.form.get(name).setValue(value);
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_3__["Ontology"])
    ], DesignDescriptorComponent.prototype, "descriptor", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], DesignDescriptorComponent.prototype, "validations", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('readOnly'),
        __metadata("design:type", Boolean)
    ], DesignDescriptorComponent.prototype, "readOnly", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])(_ontology_ontology_component__WEBPACK_IMPORTED_MODULE_7__["OntologyComponent"]),
        __metadata("design:type", _ontology_ontology_component__WEBPACK_IMPORTED_MODULE_7__["OntologyComponent"])
    ], DesignDescriptorComponent.prototype, "statusComponent", void 0);
    DesignDescriptorComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-design-descriptor',
            template: __webpack_require__(/*! ./design-descriptor.component.html */ "./src/app/components/study/design-descriptors/design-descriptor/design-descriptor.component.html"),
            styles: [__webpack_require__(/*! ./design-descriptor.component.css */ "./src/app/components/study/design-descriptors/design-descriptor/design-descriptor.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__["MetabolightsService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_4__["NgRedux"]])
    ], DesignDescriptorComponent);
    return DesignDescriptorComponent;
}());



/***/ }),

/***/ "./src/app/components/study/design-descriptors/design-descriptors.component.css":
/*!**************************************************************************************!*\
  !*** ./src/app/components/study/design-descriptors/design-descriptors.component.css ***!
  \**************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvZGVzaWduLWRlc2NyaXB0b3JzL2Rlc2lnbi1kZXNjcmlwdG9ycy5jb21wb25lbnQuY3NzIn0= */"

/***/ }),

/***/ "./src/app/components/study/design-descriptors/design-descriptors.component.html":
/*!***************************************************************************************!*\
  !*** ./src/app/components/study/design-descriptors/design-descriptors.component.html ***!
  \***************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<span *ngIf=\"inline; else showCard\">\n  <div *ngIf=\"descriptors.length > 0;else emptyMessage\">\n      <div id=\"meta\" class=\"field is-grouped is-grouped-multiline\">\n        <div *ngFor=\"let descriptor of descriptors\">\n          <mtbls-design-descriptor [readOnly]=\"readOnly\" [value]=\"descriptor\" [validations]=\"validations\"></mtbls-design-descriptor>\n        </div>\n      </div>\n    </div>\n</span>\n<ng-template #showCard>\n  <div class=\"card\">\n    <header class=\"card-heading hover-highlight\">\n      <span>\n        <p class=\"is-pulled-left\">\n          Design descriptors\n        </p>\n        <mtbls-design-descriptor [readOnly]=\"readOnly\" [value]=\"null\" [validations]=\"validations\"></mtbls-design-descriptor>\n      </span>\n    </header>\n    <div class=\"card-content\">\n      <div class=\"content\">\n        <div *ngIf=\"descriptors.length > 0;else emptyMessage\">\n          <div id=\"meta\" class=\"field is-grouped is-grouped-multiline\">\n            <div *ngFor=\"let descriptor of descriptors\">\n              <mtbls-design-descriptor [readOnly]=\"readOnly\" [value]=\"descriptor\" [validations]=\"validations\"></mtbls-design-descriptor>\n            </div>\n          </div>\n        </div>\n        <ng-template #emptyMessage>\n          <p class=\"has-text-grey-light has-text-centered\"><small>No Study desgin descriptors defined yet.</small></p>\n        </ng-template>\n      </div>\n    </div>\n  </div>\n</ng-template>"

/***/ }),

/***/ "./src/app/components/study/design-descriptors/design-descriptors.component.ts":
/*!*************************************************************************************!*\
  !*** ./src/app/components/study/design-descriptors/design-descriptors.component.ts ***!
  \*************************************************************************************/
/*! exports provided: DesignDescriptorsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DesignDescriptorsComponent", function() { return DesignDescriptorsComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var DesignDescriptorsComponent = /** @class */ (function () {
    function DesignDescriptorsComponent() {
        this.validationsId = 'studyDesignDescriptors';
    }
    DesignDescriptorsComponent.prototype.ngOnInit = function () {
    };
    Object.defineProperty(DesignDescriptorsComponent.prototype, "validation", {
        get: function () {
            return this.validations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", String)
    ], DesignDescriptorsComponent.prototype, "descriptors", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], DesignDescriptorsComponent.prototype, "validations", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('inline'),
        __metadata("design:type", Boolean)
    ], DesignDescriptorsComponent.prototype, "inline", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('readOnly'),
        __metadata("design:type", Boolean)
    ], DesignDescriptorsComponent.prototype, "readOnly", void 0);
    DesignDescriptorsComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-design-descriptors',
            template: __webpack_require__(/*! ./design-descriptors.component.html */ "./src/app/components/study/design-descriptors/design-descriptors.component.html"),
            styles: [__webpack_require__(/*! ./design-descriptors.component.css */ "./src/app/components/study/design-descriptors/design-descriptors.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], DesignDescriptorsComponent);
    return DesignDescriptorsComponent;
}());



/***/ }),

/***/ "./src/app/components/study/factors/factor/factor.component.css":
/*!**********************************************************************!*\
  !*** ./src/app/components/study/factors/factor/factor.component.css ***!
  \**********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvZmFjdG9ycy9mYWN0b3IvZmFjdG9yLmNvbXBvbmVudC5jc3MifQ== */"

/***/ }),

/***/ "./src/app/components/study/factors/factor/factor.component.html":
/*!***********************************************************************!*\
  !*** ./src/app/components/study/factors/factor/factor.component.html ***!
  \***********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div *ngIf=\"addNewFactor || factor == null; else showFactorDetails\">\n    <span *ngIf=\"!isDropdown; else showDropdownOption\">\n      <a class=\"button is-light is-pulled-right is-small hover-button\" (click)='openModal()'>+ Add factor</a>\n    </span>\n    <ng-template #showDropdownOption>\n      <a (click)='openModal()' class=\"dropdown-item\">\n        Add new factor\n      </a>\n    </ng-template>\n</div>\n<ng-template #showFactorDetails>\n\t<div class=\"control\">\n\t\t<div class=\"tags has-addons clickable\" (click)='openModal()'>\n\t\t  <span class=\"tag is-info is-small capitalize\">{{ factor.factorName }}</span>\n\t\t  <span class=\"tag is-default is-small\">\n\t\t  \t<span *ngIf=\"factor.factorType && factor.factorType.annotationValue && factor.factorType.annotationValue != ''; else emptyFactorValue\">\n          {{ factor.factorType.annotationValue }}   \n        </span>\n        <ng-template #emptyFactorValue>\n          -\n        </ng-template>\n\t\t  </span>\n\t\t</div>\n\t</div>\t\n</ng-template>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isTimeLineModalOpen}\">\n  <div class=\"modal-background\"></div>\n  <div class=\"modal-card\">\n    <section class=\"modal-card-body\">\n      <div class=\"timeline\">\n        <div class=\"timeline-item\">\n          <div class=\"timeline-marker\"></div>\n          <div class=\"timeline-content\">\n            <p class=\"heading\">January 2016</p>\n            <p>Timeline content - Can include any HTML element</p>\n          </div>\n        </div>\n        <div class=\"timeline-item\">\n          <div class=\"timeline-marker is-image is-32x32\">\n            <img src=\"http://bulma.io/images/placeholders/32x32.png\">\n          </div>\n          <div class=\"timeline-content\">\n            <p class=\"heading\">February 2016</p>\n            <p>Timeline content - Can include any HTML element</p>\n          </div>\n        </div>\n        <header class=\"timeline-header\">\n          <span class=\"tag is-primary\">2017</span>\n        </header>\n        <div class=\"timeline-item\">\n          <div class=\"timeline-marker is-icon\">\n            <i class=\"fa fa-flag\"></i>\n          </div>\n          <div class=\"timeline-content\">\n            <p class=\"heading\">March 2017</p>\n            <p>Timeline content - Can include any HTML element</p>\n          </div>\n        </div>\n      </div>\n    </section>\n    <footer class=\"modal-card-foot\">\n      <div class=\"columns is-gapless full-width\">\n        <div class=\"column is-half\">\n        </div>\n        <div class=\"column is-half has-text-right\">\n          <button (click)='closeHistory()' class=\"button is-info\">OK</button>\n        </div>\n      </div>\n    </footer>\n  </div>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isDeleteModalOpen}\">\n  <div class=\"modal-background\"></div>\n  <div class=\"modal-card\">\n    <section class=\"modal-card-body\">\n      <p>Are you sure you want to delete?</p>\n    </section>\n    <footer class=\"modal-card-foot\">\n      <div class=\"columns is-gapless full-width\">\n        <div class=\"column is-half\">\n          <button (click)='closeDelete()' class=\"button is-info\">Cancel</button>\n        </div>\n        <div class=\"column is-half has-text-right\">\n          <button (click)='delete()' class=\"button is-danger\">OK! Delete Permanently</button>\n        </div>\n      </div>\n    </footer>\n  </div>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isModalOpen}\">\n  <form *ngIf=\"form\" [formGroup]=\"form\">\n    <div class=\"modal-background\"></div>\n    <div class=\"modal-card\">\n      <div *ngIf=\"isFormBusy\" class=\"load-bar\">\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n      </div>\n      <section class=\"modal-card-body\">\n      \t<mat-form-field class=\"full-width\">\n\t\t\t<input class=\"no-scroll\" \n\t\t\tformControlName=\"factorName\"\n\t\t\tmatInput>\n\t\t\t<mat-hint>{{ fieldValidation('factorName').description }}</mat-hint>\n\t\t\t<mat-error\n\t\t\t*ngIf=\"form.get('factorName').errors &&\n\t\t\tform.get('factorName').dirty &&\n\t\t\tform.get('factorName').errors.factorName\">\n\t\t\t{{ form.get('factorName').errors.factorName.error }}\n\t\t</mat-error>\n\t\t</mat-form-field>\n        <mtbls-ontology #factorType [validations]=\"fieldValidation('factorType')\" (changed)=\"onChanges($event)\" [values]='[factor.factorType]' [inline]=\"true\"></mtbls-ontology>\n      </section>\n      <footer class=\"modal-card-foot\">\n        <div class=\"columns is-gapless full-width\">\n          <div class=\"column is-half\">\n            <button *ngIf=\"!addNewFactor\" class=\"button is-danger is-pulled-left\" (click)=\"confirmDelete()\"><mat-icon>delete_outline</mat-icon></button>\n            <!-- <button *ngIf=\"!addNewFactor\" class=\"button is-light is-pulled-left\" (click)='showHistory()' ><mat-icon>history</mat-icon></button> -->\n          </div>\n          <div class=\"column is-half has-text-right\">\n            <button *ngIf=\"!form.pristine\" [disabled]=\"!form.valid || isFormBusy\" (click)='save()' class=\"button is-info\">\n              <mat-spinner [diameter]=\"20\" [strokeWidth]=\"3\" *ngIf=\"isFormBusy\"></mat-spinner>\n              Save\n            </button>\n            <button *ngIf=\"form.pristine\" (click)='closeModal()' class=\"button is-info\">OK</button>\n            <button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeModal()'>Cancel</button>\n          </div>\n        </div>\n      </footer>\n    </div>\n  </form>\n</div>"

/***/ }),

/***/ "./src/app/components/study/factors/factor/factor.component.ts":
/*!*********************************************************************!*\
  !*** ./src/app/components/study/factors/factor/factor.component.ts ***!
  \*********************************************************************/
/*! exports provided: FactorComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "FactorComponent", function() { return FactorComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./../../../../models/mtbl/mtbls/common/mtbls-ontology */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_4___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_4__);
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_5__);
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_6___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_6__);
/* harmony import */ var _ontology_ontology_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./../../ontology/ontology.component */ "./src/app/components/study/ontology/ontology.component.ts");
/* harmony import */ var _models_mtbl_mtbls_mtbls_factor__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./../../../../models/mtbl/mtbls/mtbls-factor */ "./src/app/models/mtbl/mtbls/mtbls-factor.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};









var FactorComponent = /** @class */ (function () {
    function FactorComponent(fb, metabolightsService, ngRedux) {
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.ngRedux = ngRedux;
        this.isDropdown = false;
        this.validationsId = 'factors.factor';
        this.isModalOpen = false;
        this.isTimeLineModalOpen = false;
        this.isDeleteModalOpen = false;
        this.isFormBusy = false;
        this.addNewFactor = false;
    }
    FactorComponent.prototype.ngOnInit = function () {
        if (this.factor == null) {
            this.addNewFactor = true;
            if (this.factorTypeComponent) {
                this.factorTypeComponent.values = [];
            }
        }
    };
    FactorComponent.prototype.onChanges = function () {
        this.form.markAsDirty();
    };
    FactorComponent.prototype.showHistory = function () {
        this.isModalOpen = false;
        this.isTimeLineModalOpen = true;
    };
    FactorComponent.prototype.closeHistory = function () {
        this.isTimeLineModalOpen = false;
        this.isModalOpen = true;
    };
    FactorComponent.prototype.openModal = function () {
        if (this.addNewFactor) {
            this.factor = new _models_mtbl_mtbls_mtbls_factor__WEBPACK_IMPORTED_MODULE_8__["MTBLSFactor"]();
            if (this.factorTypeComponent) {
                this.factorTypeComponent.values = [];
            }
        }
        this.initialiseForm();
        this.isModalOpen = true;
    };
    FactorComponent.prototype.initialiseForm = function () {
        this.isFormBusy = false;
        this.form = this.fb.group({
            factorName: [this.factor.factorName],
        });
    };
    FactorComponent.prototype.confirmDelete = function () {
        this.isModalOpen = false;
        this.isDeleteModalOpen = true;
    };
    FactorComponent.prototype.closeDelete = function () {
        this.isDeleteModalOpen = false;
        this.isModalOpen = true;
    };
    FactorComponent.prototype.closeModal = function () {
        this.isModalOpen = false;
    };
    FactorComponent.prototype.save = function () {
        var _this = this;
        this.isFormBusy = true;
        if (!this.addNewFactor) {
            this.metabolightsService.updateFactor(this.factor.factorName, this.compileBody()).subscribe(function (res) {
                _this.updateFactors(res, 'Factor updated.');
            }, function (err) {
                _this.isFormBusy = false;
            });
        }
        else {
            this.metabolightsService.saveFactor(this.compileBody()).subscribe(function (res) {
                _this.updateFactors(res, 'Factor saved.');
                _this.isModalOpen = false;
            }, function (err) {
                _this.isFormBusy = false;
            });
        }
    };
    FactorComponent.prototype.delete = function () {
        var _this = this;
        this.metabolightsService.deleteFactor(this.factor.factorName).subscribe(function (res) {
            _this.updateFactors(res, 'Factor deleted.');
            _this.isDeleteModalOpen = false;
            _this.isModalOpen = false;
        }, function (err) {
            _this.isFormBusy = false;
        });
    };
    FactorComponent.prototype.updateFactors = function (data, message) {
        var _this = this;
        this.metabolightsService.getFactors().subscribe(function (res) {
            _this.ngRedux.dispatch({ type: 'UPDATE_STUDY_FACTORS', body: {
                    'factors': res.factors
                } });
        });
        this.form.markAsPristine();
        this.initialiseForm();
        this.isModalOpen = true;
        toastr__WEBPACK_IMPORTED_MODULE_5__["success"](message, "Success", {
            "timeOut": "2500",
            "positionClass": "toast-top-center",
            "preventDuplicates": true,
            "extendedTimeOut": 0,
            "tapToDismiss": false
        });
    };
    FactorComponent.prototype.compileBody = function () {
        var mtblsFactor = new _models_mtbl_mtbls_mtbls_factor__WEBPACK_IMPORTED_MODULE_8__["MTBLSFactor"]();
        mtblsFactor.factorName = this.getFieldValue('factorName');
        mtblsFactor.comments = [];
        var jsonConvert = new json2typescript__WEBPACK_IMPORTED_MODULE_6__["JsonConvert"]();
        mtblsFactor.factorType = jsonConvert.deserialize(this.factorTypeComponent.values[0], _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_3__["Ontology"]);
        return { "factor": mtblsFactor.toJSON() };
    };
    Object.defineProperty(FactorComponent.prototype, "validation", {
        get: function () {
            if (this.validationsId.includes(".")) {
                var arr = this.validationsId.split(".");
                var tempValidations = JSON.parse(JSON.stringify(this.validations));
                ;
                while (arr.length && (tempValidations = tempValidations[arr.shift()]))
                    ;
                return tempValidations;
            }
            return this.validations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    FactorComponent.prototype.fieldValidation = function (fieldId) {
        return this.validation[fieldId];
    };
    FactorComponent.prototype.getFieldValue = function (name) {
        return this.form.get(name).value;
    };
    FactorComponent.prototype.setFieldValue = function (name, value) {
        return this.form.get(name).setValue(value);
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", _models_mtbl_mtbls_mtbls_factor__WEBPACK_IMPORTED_MODULE_8__["MTBLSFactor"])
    ], FactorComponent.prototype, "factor", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], FactorComponent.prototype, "validations", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('isDropdown'),
        __metadata("design:type", Boolean)
    ], FactorComponent.prototype, "isDropdown", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])(_ontology_ontology_component__WEBPACK_IMPORTED_MODULE_7__["OntologyComponent"]),
        __metadata("design:type", _ontology_ontology_component__WEBPACK_IMPORTED_MODULE_7__["OntologyComponent"])
    ], FactorComponent.prototype, "factorTypeComponent", void 0);
    FactorComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-factor',
            template: __webpack_require__(/*! ./factor.component.html */ "./src/app/components/study/factors/factor/factor.component.html"),
            styles: [__webpack_require__(/*! ./factor.component.css */ "./src/app/components/study/factors/factor/factor.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__["MetabolightsService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_4__["NgRedux"]])
    ], FactorComponent);
    return FactorComponent;
}());



/***/ }),

/***/ "./src/app/components/study/factors/factors.component.css":
/*!****************************************************************!*\
  !*** ./src/app/components/study/factors/factors.component.css ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvZmFjdG9ycy9mYWN0b3JzLmNvbXBvbmVudC5jc3MifQ== */"

/***/ }),

/***/ "./src/app/components/study/factors/factors.component.html":
/*!*****************************************************************!*\
  !*** ./src/app/components/study/factors/factors.component.html ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"card\">\n  <header class=\"card-heading hover-highlight\">\n    <span>\n      <p class=\"is-pulled-left\">\n        Factors\n      </p>\n      <mtbls-factor [value]=\"null\" [validations]=\"validations\"></mtbls-factor>\n    </span>\n  </header>\n  <div class=\"card-content\">\n    <div class=\"content\">\n      <div *ngIf=\"factors && factors.length > 0;else emptyMessage\">\n        <div id=\"meta\">\n          <div *ngFor=\"let factor of factors\">\n            <mtbls-factor [value]=\"factor\" [validations]=\"validations\"></mtbls-factor>\n          </div>\n        </div>\n      </div>\n      <ng-template #emptyMessage>\n        <p class=\"has-text-grey-light has-text-centered\"><small>No Study factors defined yet.</small></p>\n      </ng-template>\n    </div>\n  </div>\n</div>"

/***/ }),

/***/ "./src/app/components/study/factors/factors.component.ts":
/*!***************************************************************!*\
  !*** ./src/app/components/study/factors/factors.component.ts ***!
  \***************************************************************/
/*! exports provided: FactorsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "FactorsComponent", function() { return FactorsComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var FactorsComponent = /** @class */ (function () {
    function FactorsComponent() {
        this.validationsId = 'factors';
    }
    FactorsComponent.prototype.ngOnInit = function () {
    };
    Object.defineProperty(FactorsComponent.prototype, "validation", {
        get: function () {
            return this.validations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", String)
    ], FactorsComponent.prototype, "factors", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], FactorsComponent.prototype, "validations", void 0);
    FactorsComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-factors',
            template: __webpack_require__(/*! ./factors.component.html */ "./src/app/components/study/factors/factors.component.html"),
            styles: [__webpack_require__(/*! ./factors.component.css */ "./src/app/components/study/factors/factors.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], FactorsComponent);
    return FactorsComponent;
}());



/***/ }),

/***/ "./src/app/components/study/files/file/file.component.css":
/*!****************************************************************!*\
  !*** ./src/app/components/study/files/file/file.component.css ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvZmlsZXMvZmlsZS9maWxlLmNvbXBvbmVudC5jc3MifQ== */"

/***/ }),

/***/ "./src/app/components/study/files/file/file.component.html":
/*!*****************************************************************!*\
  !*** ./src/app/components/study/files/file/file.component.html ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<p>\n  file works!\n</p>\n"

/***/ }),

/***/ "./src/app/components/study/files/file/file.component.ts":
/*!***************************************************************!*\
  !*** ./src/app/components/study/files/file/file.component.ts ***!
  \***************************************************************/
/*! exports provided: FileComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "FileComponent", function() { return FileComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var FileComponent = /** @class */ (function () {
    function FileComponent() {
    }
    FileComponent.prototype.ngOnInit = function () {
    };
    FileComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'app-file',
            template: __webpack_require__(/*! ./file.component.html */ "./src/app/components/study/files/file/file.component.html"),
            styles: [__webpack_require__(/*! ./file.component.css */ "./src/app/components/study/files/file/file.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], FileComponent);
    return FileComponent;
}());



/***/ }),

/***/ "./src/app/components/study/files/files.component.css":
/*!************************************************************!*\
  !*** ./src/app/components/study/files/files.component.css ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "img{\n\theight: auto;\n}\n\n.panel-heading {\n    font-size: 1em;\n}\n\n.panel-block {\n    font-size: 0.9em;\n}\n\n.panel-block.is-active-file {\n    border-left: 2px solid #3273dc;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zdHVkeS9maWxlcy9maWxlcy5jb21wb25lbnQuY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0NBQ0MsYUFBYTtDQUNiOztBQUVEO0lBQ0ksZUFBZTtDQUNsQjs7QUFFRDtJQUNJLGlCQUFpQjtDQUNwQjs7QUFFRDtJQUNJLCtCQUErQjtDQUNsQyIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvZmlsZXMvZmlsZXMuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbImltZ3tcblx0aGVpZ2h0OiBhdXRvO1xufVxuXG4ucGFuZWwtaGVhZGluZyB7XG4gICAgZm9udC1zaXplOiAxZW07XG59XG5cbi5wYW5lbC1ibG9jayB7XG4gICAgZm9udC1zaXplOiAwLjllbTtcbn1cblxuLnBhbmVsLWJsb2NrLmlzLWFjdGl2ZS1maWxlIHtcbiAgICBib3JkZXItbGVmdDogMnB4IHNvbGlkICMzMjczZGM7XG59XG4iXX0= */"

/***/ }),

/***/ "./src/app/components/study/files/files.component.html":
/*!*************************************************************!*\
  !*** ./src/app/components/study/files/files.component.html ***!
  \*************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<mtbls-upload [mode]=\"'button'\" [size]=\"'is'\" ></mtbls-upload>\n<div>&nbsp;</div>\n<nav class=\"panel\">\n  <p class=\"panel-heading\">\n    <small>ISA METADATA</small>\n    <span class=\"is-pulled-right\">\n      <mtbls-download [value]=\"'meta'\"></mtbls-download>\n    </span>\n  </p>\n  <span *ngIf=\"metaFiles.length > 0; else noMetaData\">\n    <span *ngFor=\"let file of metaFiles\">\n      <a class=\"panel-block\" [ngClass]=\"file.status == 'active' ? 'is-active-file' : ''\">\n        <span class=\"panel-icon\">\n          <span *ngIf=\"isFolder(file); else fileIcon\">\n            <img src=\"assets/img/folder.png\">\n          </span>\n          <ng-template #fileIcon>\n            <img src=\"assets/img/file.png\">\n          </ng-template>\n        </span>\n        {{ file.file }}\n      </a>\n    </span>\n  </span>\n  <ng-template #noMetaData>\n    <a class=\"panel-block\">\n      No metadata files\n    </a>\n  </ng-template>\n</nav>\n<nav class=\"panel\">\n  <p class=\"panel-heading\">\n    <small>RAW FILES</small>\n  </p>\n  <span *ngIf=\"rawFiles.length > 0; else noRawFiles\">\n    <span *ngFor=\"let file of rawFiles\">\n      <a class=\"panel-block\" [ngClass]=\"file.status == 'active' ? 'is-active-file' : ''\">\n        <span class=\"panel-icon\">\n          <span *ngIf=\"isFolder(file); else fileIcon\">\n            <img src=\"assets/img/folder.png\">\n          </span>\n          <ng-template #fileIcon>\n            <img src=\"assets/img/file.png\">\n          </ng-template>\n        </span>\n        {{ file.file }}\n      </a>\n    </span>\n  </span>\n  <ng-template #noRawFiles>\n    <a class=\"panel-block\">\n      No raw files\n    </a>\n  </ng-template>\n</nav>\n<nav *ngIf=\"auditFiles.length > 0 || derivedFiles.length > 0\" class=\"panel\">\n  <p class=\"panel-heading\">\n    <small>INTERNAL AND DERIVED DATA</small>\n  </p>\n  <span *ngIf=\"auditFiles.length > 0\">\n      <span *ngFor=\"let file of auditFiles\">\n        <a class=\"panel-block\">\n          <span class=\"panel-icon\">\n            <span *ngIf=\"isFolder(file); else fileIcon\">\n              <img src=\"assets/img/folder.png\">\n            </span>\n            <ng-template #fileIcon>\n              <img src=\"assets/img/file.png\">\n            </ng-template>\n          </span>\n          {{ file.file }}\n        </a>\n      </span>\n  </span>\n  <span *ngIf=\"derivedFiles.length > 0\">\n    <span *ngFor=\"let file of derivedFiles\">\n      <a class=\"panel-block\">\n        <span class=\"panel-icon\">\n          <span *ngIf=\"isFolder(file); else fileIcon\">\n            <img src=\"assets/img/folder.png\">\n          </span>\n          <ng-template #fileIcon>\n            <img src=\"assets/img/file.png\">\n          </ng-template>\n        </span>\n        {{ file.file }}\n      </a>\n    </span>\n  </span>\n</nav>\n<nav *ngIf=\"uploadFiles.length > 0\" class=\"panel\">\n  <p class=\"panel-heading\">\n    <small>UPLOADED FILES</small>\n    <span class=\"is-pulled-right\">\n      <a target=\"_blank\" (click)=\"copyFiles()\" class=\"button is-small is-light\">\n        <mat-icon>refresh</mat-icon> Copy files to study folder\n      </a>\n    </span>\n  </p>\n  <span *ngIf=\"uploadFiles.length > 0\">\n      <span *ngFor=\"let file of uploadFiles\">\n        <a class=\"panel-block\">\n          <span class=\"panel-icon\">\n            <span *ngIf=\"isFolder(file); else fileIcon\">\n              <img src=\"assets/img/folder.png\">\n            </span>\n            <ng-template #fileIcon>\n              <img src=\"assets/img/file.png\">\n            </ng-template>\n          </span>\n          {{ file.file }}\n        </a>\n      </span>\n  </span>\n  </nav>"

/***/ }),

/***/ "./src/app/components/study/files/files.component.ts":
/*!***********************************************************!*\
  !*** ./src/app/components/study/files/files.component.ts ***!
  \***********************************************************/
/*! exports provided: FilesComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "FilesComponent", function() { return FilesComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var FilesComponent = /** @class */ (function () {
    function FilesComponent(metabolightsService) {
        this.metabolightsService = metabolightsService;
        this.rawFiles = [];
        this.metaFiles = [];
        this.auditFiles = [];
        this.derivedFiles = [];
        this.uploadFiles = [];
        this.validationsId = 'upload';
    }
    FilesComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.metabolightsService.getStudyFiles().subscribe(function (data) {
            _this.uploadFiles = data.upload;
            data.studyFiles.forEach(function (file) {
                if (file.type == 'unknown' || file.type == 'compressed' || file.type == 'derived') {
                    _this.rawFiles.push(file);
                }
                else if (file.type.indexOf('metadata') > -1) {
                    _this.metaFiles.push(file);
                }
                else if (file.type == 'audit') {
                    _this.auditFiles.push(file);
                }
                else if (file.type == 'internal_mapping' || file.type == 'spreadsheet') {
                    _this.derivedFiles.push(file);
                }
            });
        });
    };
    FilesComponent.prototype.copyFiles = function () {
        this.metabolightsService.copyFiles().subscribe(function (data) {
            console.log(data);
        });
    };
    FilesComponent.prototype.isFolder = function (file) {
        return !(file.file.indexOf(".") > -1);
    };
    Object.defineProperty(FilesComponent.prototype, "validation", {
        get: function () {
            return this.validations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], FilesComponent.prototype, "validations", void 0);
    FilesComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-files',
            template: __webpack_require__(/*! ./files.component.html */ "./src/app/components/study/files/files.component.html"),
            styles: [__webpack_require__(/*! ./files.component.css */ "./src/app/components/study/files/files.component.css")]
        }),
        __metadata("design:paramtypes", [_services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_1__["MetabolightsService"]])
    ], FilesComponent);
    return FilesComponent;
}());



/***/ }),

/***/ "./src/app/components/study/metabolites/maf/maf.component.css":
/*!********************************************************************!*\
  !*** ./src/app/components/study/metabolites/maf/maf.component.css ***!
  \********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "table {\n  -webkit-user-select: none;\n  -moz-user-select: none;\n  -o-user-select: none;\n  -ms-user-select: none;\n      user-select: none;\n}\n\ntable {\n  width: 100%;\n}\n\ntable td, table th {\n  vertical-align: middle;\n}\n\nth.mat-sort-header-sorted {\n  color: black;\n}\n\nth div{\n  display: flex;\n}\n\n.column-editor-card{\n  min-width: 80vw !important;\n  min-height: 80vh;\n}\n\n.columns.is-vcentered {\n  align-items: center;\n}\n\n.columns.is-hcentered {\n  justify-content: center;\n  text-align: center;\n}\n\n.mt20{\n  margin-top: 20px;\n}\n\n.mb0{\n  margin-bottom: 0px;\n}\n\n.tab-wrapper{\n  min-height: 80vh;\n}\n\n.modal-card-title {\n  color: #979797;\n  font-size: 1.2rem;\n}\n\n.modal-card-title .highlight {\n  color: #000;\n}\n\n.options-wrapper{\n  border-left: 1px solid #f1f1f3;\n  min-height: 70vh;\n  padding-top: 10px !important;\n}\n\n.options-title{\n  border-bottom: 1px solid #f1f1f3;\n  padding: 15px !important;\n  font-weight: bold;\n  font-size: 1.1em;\n}\n\n.options{\n  padding: 15px;\n}\n\n.wrapper{\n  width: 100%;\n  overflow: auto;\n}\n\n.mat-form-field-infix {\n  border-top: none;\n}\n\ntd.mat-cell, td.mat-footer-cell, th.mat-header-cell {\n\tpadding-left: 20px;\n\tpadding-right: 12px;\n  box-shadow: 0 0 1px #dbdbdb;\n}\n\n.mat-table-sticky {\n  background-color: #fdfdfd;\n}\n\n.dropdown-divider {\n  background-color: #dbdbdb;\n  border: none;\n  display: block;\n  height: 1px;\n  margin: 0;\n}\n\n.dropdown-content {\n padding-bottom: 0rem; \n padding-top: 0rem; \n border-radius: 2px;\n}\n\na.dropdown-item {\n  padding-right: 1rem; \n}\n\n.column{\n  padding: 0;\n}\n\n.addon-tag{\n  padding-left: 0;\n  padding-right: 0;\n  border-left: 1px dotted #eaeaea;\n}\n\n.columns{\n  margin-right: 0px;\n  margin-left: 0px;\n}\n\n.row-selector{\n\twidth: 10px;\n}\n\n.table-selector{\n\twidth: 10px;\n}\n\n.selected{\n\tbackground-color: #333;\n  color: #fff;\n}\n\n.row-options-wrapper{\n\tposition: relative;\n}\n\n.row-options{\n\tposition: absolute;\n  left: -23px;\n  top: 0px;\n  height: 48px;\n}\n\n.table-icon {\n  padding-right: 10px;\n  padding-top: 12px;\n}\n\n.button{\n  margin-left: 2px;\n  margin-right: 2px;\n}\n\n.menu-bar{\n  margin-top: 10px;\n  margin-bottom: 0 !important;\n  align-items: baseline;\n}\n\n.menu-bar:first-child { \n  margin-left: -5px;\n}\n\n.menu-bar:last-child { \n  margin-right: -5px;\n}\n\n.button.is-light {\n  border-radius: 2px;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zdHVkeS9tZXRhYm9saXRlcy9tYWYvbWFmLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7RUFDRSwwQkFBMEI7RUFFMUIsdUJBQXVCO0VBQ3ZCLHFCQUFxQjtFQUNyQixzQkFBa0I7TUFBbEIsa0JBQWtCO0NBQ25COztBQUVEO0VBQ0UsWUFBWTtDQUNiOztBQUVEO0VBQ0UsdUJBQXVCO0NBQ3hCOztBQUVEO0VBQ0UsYUFBYTtDQUNkOztBQUVEO0VBQ0UsY0FBYztDQUNmOztBQUVEO0VBQ0UsMkJBQTJCO0VBQzNCLGlCQUFpQjtDQUNsQjs7QUFFRDtFQUdFLG9CQUFvQjtDQUNyQjs7QUFFRDtFQUNFLHdCQUF3QjtFQUN4QixtQkFBbUI7Q0FDcEI7O0FBRUQ7RUFDRSxpQkFBaUI7Q0FDbEI7O0FBRUQ7RUFDRSxtQkFBbUI7Q0FDcEI7O0FBRUQ7RUFDRSxpQkFBaUI7Q0FDbEI7O0FBRUQ7RUFDRSxlQUFlO0VBQ2Ysa0JBQWtCO0NBQ25COztBQUVEO0VBQ0UsWUFBWTtDQUNiOztBQUVEO0VBQ0UsK0JBQStCO0VBQy9CLGlCQUFpQjtFQUNqQiw2QkFBNkI7Q0FDOUI7O0FBRUQ7RUFDRSxpQ0FBaUM7RUFDakMseUJBQXlCO0VBQ3pCLGtCQUFrQjtFQUNsQixpQkFBaUI7Q0FDbEI7O0FBRUQ7RUFDRSxjQUFjO0NBQ2Y7O0FBRUQ7RUFDRSxZQUFZO0VBQ1osZUFBZTtDQUNoQjs7QUFFRDtFQUNFLGlCQUFpQjtDQUNsQjs7QUFFRDtDQUNDLG1CQUFtQjtDQUNuQixvQkFBb0I7RUFFbkIsNEJBQTRCO0NBQzdCOztBQUVEO0VBQ0UsMEJBQTBCO0NBQzNCOztBQUVEO0VBQ0UsMEJBQTBCO0VBQzFCLGFBQWE7RUFDYixlQUFlO0VBQ2YsWUFBWTtFQUNaLFVBQVU7Q0FDWDs7QUFFRDtDQUNDLHFCQUFxQjtDQUNyQixrQkFBa0I7Q0FDbEIsbUJBQW1CO0NBQ25COztBQUVEO0VBQ0Usb0JBQW9CO0NBQ3JCOztBQUVEO0VBQ0UsV0FBVztDQUNaOztBQUVEO0VBQ0UsZ0JBQWdCO0VBQ2hCLGlCQUFpQjtFQUNqQixnQ0FBZ0M7Q0FDakM7O0FBRUQ7RUFDRSxrQkFBa0I7RUFDbEIsaUJBQWlCO0NBQ2xCOztBQUVEO0NBQ0MsWUFBWTtDQUNaOztBQUVEO0NBQ0MsWUFBWTtDQUNaOztBQUVEO0NBQ0MsdUJBQXVCO0VBQ3RCLFlBQVk7Q0FDYjs7QUFFRDtDQUNDLG1CQUFtQjtDQUNuQjs7QUFFRDtDQUNDLG1CQUFtQjtFQUNsQixZQUFZO0VBQ1osU0FBUztFQUNULGFBQWE7Q0FDZDs7QUFFRDtFQUNFLG9CQUFvQjtFQUNwQixrQkFBa0I7Q0FDbkI7O0FBRUQ7RUFDRSxpQkFBaUI7RUFDakIsa0JBQWtCO0NBQ25COztBQUVEO0VBQ0UsaUJBQWlCO0VBQ2pCLDRCQUE0QjtFQUM1QixzQkFBc0I7Q0FDdkI7O0FBRUQ7RUFDRSxrQkFBa0I7Q0FDbkI7O0FBRUQ7RUFDRSxtQkFBbUI7Q0FDcEI7O0FBRUQ7RUFDRSxtQkFBbUI7Q0FDcEIiLCJmaWxlIjoic3JjL2FwcC9jb21wb25lbnRzL3N0dWR5L21ldGFib2xpdGVzL21hZi9tYWYuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbInRhYmxlIHtcbiAgLXdlYmtpdC11c2VyLXNlbGVjdDogbm9uZTtcbiAgLWtodG1sLXVzZXItc2VsZWN0OiBub25lO1xuICAtbW96LXVzZXItc2VsZWN0OiBub25lO1xuICAtby11c2VyLXNlbGVjdDogbm9uZTtcbiAgdXNlci1zZWxlY3Q6IG5vbmU7XG59XG5cbnRhYmxlIHtcbiAgd2lkdGg6IDEwMCU7XG59XG5cbnRhYmxlIHRkLCB0YWJsZSB0aCB7XG4gIHZlcnRpY2FsLWFsaWduOiBtaWRkbGU7XG59XG5cbnRoLm1hdC1zb3J0LWhlYWRlci1zb3J0ZWQge1xuICBjb2xvcjogYmxhY2s7XG59XG5cbnRoIGRpdntcbiAgZGlzcGxheTogZmxleDtcbn1cblxuLmNvbHVtbi1lZGl0b3ItY2FyZHtcbiAgbWluLXdpZHRoOiA4MHZ3ICFpbXBvcnRhbnQ7XG4gIG1pbi1oZWlnaHQ6IDgwdmg7XG59XG5cbi5jb2x1bW5zLmlzLXZjZW50ZXJlZCB7XG4gIC13ZWJraXQtYm94LWFsaWduOiBjZW50ZXI7XG4gIC1tcy1mbGV4LWFsaWduOiBjZW50ZXI7XG4gIGFsaWduLWl0ZW1zOiBjZW50ZXI7XG59XG5cbi5jb2x1bW5zLmlzLWhjZW50ZXJlZCB7XG4gIGp1c3RpZnktY29udGVudDogY2VudGVyO1xuICB0ZXh0LWFsaWduOiBjZW50ZXI7XG59XG5cbi5tdDIwe1xuICBtYXJnaW4tdG9wOiAyMHB4O1xufVxuXG4ubWIwe1xuICBtYXJnaW4tYm90dG9tOiAwcHg7XG59XG5cbi50YWItd3JhcHBlcntcbiAgbWluLWhlaWdodDogODB2aDtcbn1cblxuLm1vZGFsLWNhcmQtdGl0bGUge1xuICBjb2xvcjogIzk3OTc5NztcbiAgZm9udC1zaXplOiAxLjJyZW07XG59XG5cbi5tb2RhbC1jYXJkLXRpdGxlIC5oaWdobGlnaHQge1xuICBjb2xvcjogIzAwMDtcbn1cblxuLm9wdGlvbnMtd3JhcHBlcntcbiAgYm9yZGVyLWxlZnQ6IDFweCBzb2xpZCAjZjFmMWYzO1xuICBtaW4taGVpZ2h0OiA3MHZoO1xuICBwYWRkaW5nLXRvcDogMTBweCAhaW1wb3J0YW50O1xufVxuXG4ub3B0aW9ucy10aXRsZXtcbiAgYm9yZGVyLWJvdHRvbTogMXB4IHNvbGlkICNmMWYxZjM7XG4gIHBhZGRpbmc6IDE1cHggIWltcG9ydGFudDtcbiAgZm9udC13ZWlnaHQ6IGJvbGQ7XG4gIGZvbnQtc2l6ZTogMS4xZW07XG59XG5cbi5vcHRpb25ze1xuICBwYWRkaW5nOiAxNXB4O1xufVxuXG4ud3JhcHBlcntcbiAgd2lkdGg6IDEwMCU7XG4gIG92ZXJmbG93OiBhdXRvO1xufVxuXG4ubWF0LWZvcm0tZmllbGQtaW5maXgge1xuICBib3JkZXItdG9wOiBub25lO1xufVxuXG50ZC5tYXQtY2VsbCwgdGQubWF0LWZvb3Rlci1jZWxsLCB0aC5tYXQtaGVhZGVyLWNlbGwge1xuXHRwYWRkaW5nLWxlZnQ6IDIwcHg7XG5cdHBhZGRpbmctcmlnaHQ6IDEycHg7XG4gIC13ZWJraXQtYm94LXNoYWRvdzogMCAwIDFweCAjZGJkYmRiO1xuICBib3gtc2hhZG93OiAwIDAgMXB4ICNkYmRiZGI7XG59XG5cbi5tYXQtdGFibGUtc3RpY2t5IHtcbiAgYmFja2dyb3VuZC1jb2xvcjogI2ZkZmRmZDtcbn1cblxuLmRyb3Bkb3duLWRpdmlkZXIge1xuICBiYWNrZ3JvdW5kLWNvbG9yOiAjZGJkYmRiO1xuICBib3JkZXI6IG5vbmU7XG4gIGRpc3BsYXk6IGJsb2NrO1xuICBoZWlnaHQ6IDFweDtcbiAgbWFyZ2luOiAwO1xufVxuXG4uZHJvcGRvd24tY29udGVudCB7XG4gcGFkZGluZy1ib3R0b206IDByZW07IFxuIHBhZGRpbmctdG9wOiAwcmVtOyBcbiBib3JkZXItcmFkaXVzOiAycHg7XG59XG5cbmEuZHJvcGRvd24taXRlbSB7XG4gIHBhZGRpbmctcmlnaHQ6IDFyZW07IFxufVxuXG4uY29sdW1ue1xuICBwYWRkaW5nOiAwO1xufVxuXG4uYWRkb24tdGFne1xuICBwYWRkaW5nLWxlZnQ6IDA7XG4gIHBhZGRpbmctcmlnaHQ6IDA7XG4gIGJvcmRlci1sZWZ0OiAxcHggZG90dGVkICNlYWVhZWE7XG59XG5cbi5jb2x1bW5ze1xuICBtYXJnaW4tcmlnaHQ6IDBweDtcbiAgbWFyZ2luLWxlZnQ6IDBweDtcbn1cblxuLnJvdy1zZWxlY3Rvcntcblx0d2lkdGg6IDEwcHg7XG59XG5cbi50YWJsZS1zZWxlY3Rvcntcblx0d2lkdGg6IDEwcHg7XG59XG5cbi5zZWxlY3RlZHtcblx0YmFja2dyb3VuZC1jb2xvcjogIzMzMztcbiAgY29sb3I6ICNmZmY7XG59XG5cbi5yb3ctb3B0aW9ucy13cmFwcGVye1xuXHRwb3NpdGlvbjogcmVsYXRpdmU7XG59XG5cbi5yb3ctb3B0aW9uc3tcblx0cG9zaXRpb246IGFic29sdXRlO1xuICBsZWZ0OiAtMjNweDtcbiAgdG9wOiAwcHg7XG4gIGhlaWdodDogNDhweDtcbn1cblxuLnRhYmxlLWljb24ge1xuICBwYWRkaW5nLXJpZ2h0OiAxMHB4O1xuICBwYWRkaW5nLXRvcDogMTJweDtcbn1cblxuLmJ1dHRvbntcbiAgbWFyZ2luLWxlZnQ6IDJweDtcbiAgbWFyZ2luLXJpZ2h0OiAycHg7XG59XG5cbi5tZW51LWJhcntcbiAgbWFyZ2luLXRvcDogMTBweDtcbiAgbWFyZ2luLWJvdHRvbTogMCAhaW1wb3J0YW50O1xuICBhbGlnbi1pdGVtczogYmFzZWxpbmU7XG59XG5cbi5tZW51LWJhcjpmaXJzdC1jaGlsZCB7IFxuICBtYXJnaW4tbGVmdDogLTVweDtcbn1cblxuLm1lbnUtYmFyOmxhc3QtY2hpbGQgeyBcbiAgbWFyZ2luLXJpZ2h0OiAtNXB4O1xufVxuXG4uYnV0dG9uLmlzLWxpZ2h0IHtcbiAgYm9yZGVyLXJhZGl1czogMnB4O1xufVxuIl19 */"

/***/ }),

/***/ "./src/app/components/study/metabolites/maf/maf.component.html":
/*!*********************************************************************!*\
  !*** ./src/app/components/study/metabolites/maf/maf.component.html ***!
  \*********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"card\" *ngIf=\"maf\">\n\t<header class=\"card-header\">\n\t\t<p class=\"card-header-title\">\n\t\t\tMetabolite Identification File\n\t\t</p>\n\t</header>\n\t<div class=\"card-content\">\n\t\t<div class=\"columns mb0 is-vcentered is-hcentered\">\n\t\t\t<div class=\"column is-12 is-paddingless\">\n\t\t\t\t<mat-form-field class=\"full-width bback\">\n\t\t\t\t\t<input (keydown)=\"onKeydown($event, $event.target.value)\" autocomplete=\"off\" matInput (keyup)=\"applyFilter($event.target.value)\" placeholder=\"Filter\">\n\t\t\t\t</mat-form-field>\n\t\t\t</div>\n\t\t</div>\n\t\t<div *ngIf=\"filters.length > 0\" class=\"field is-grouped is-grouped-multiline\">\n\t\t\t<div *ngFor=\"let filter of filters\" class=\"control\">\n\t\t\t\t<div class=\"tags has-addons\">\n\t\t\t\t\t<span class=\"tag is-link\">{{ filter }}</span>\n\t\t\t\t\t<a (click)=\"highlightFilteredRows(filter)\" class=\"tag addon-tag\"><mat-icon>control_camera</mat-icon></a>\n\t\t\t\t\t<a (click)=\"removeFilter(filter)\" class=\"tag is-delete\"></a>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</div>\n\t\t<div class=\"menu-bar columns\">  \n\t\t\t<span class=\"column fadeIn\">\n\t\t\t\t<a (click)=\"addRow()\" class=\"button is-small is-light\">\n\t\t\t\t\t<mat-icon>add</mat-icon> Row\n\t\t\t\t</a>&nbsp;\n\t\t\t\t<mtbls-upload></mtbls-upload>&nbsp;\n      \t\t\t<span *ngIf=\"maf.file\">\n      \t\t\t\t<mtbls-download [value]=\"maf.file\"></mtbls-download>\n      \t\t\t</span>\n\t\t\t\t<span *ngIf=\"selectedRows.length > 0\">\n\t\t\t\t\t<a (click)=\"openDeleteModal()\" class=\"button is-small is-light\">\n\t\t\t\t\t\t<mat-icon>delete</mat-icon> Delete\n\t\t\t\t\t</a>\n\t\t\t\t</span>\n\t\t\t</span>\n\t\t\t<span class=\"column fadeIn has-text-right\">\n\t\t\t\t<mat-paginator [pageSizeOptions]=\"[100, 500, 1000]\" showFirstLastButtons></mat-paginator>\n\t\t\t</span>\n\t\t</div>\n\t\t<div class=\"wrapper mat-elevation-z1\">\n\t\t\t<table class=\"mat-elevation-z1\" matSort [dataSource]=\"tableDataSource\" mat-table>\n\t\t\t\t<ng-container matColumnDef=\"Select\" sticky>\n\t\t\t\t\t<th (click)=\"deSelect()\" class=\"clickable table-selector\" mat-header-cell *matHeaderCellDef></th>\n\t\t\t\t\t<td (dblclick)=\"openRowEditModal(row)\" (click)=\"rowClick(row, $event)\" class=\"clickable row-selector hover-highlight\" mat-cell *matCellDef=\"let row\">\n\t\t\t\t\t\t<span class=\"row-options-wrapper\">\n\t\t\t\t\t\t\t<span class=\"row-options hover-button\">\n\t\t\t\t\t\t\t\t<mat-icon class=\"table-icon\">aspect_ratio</mat-icon>\n\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t</span>\n\t\t\t\t\t</td>\n\t\t\t\t</ng-container>\n\t\t\t\t<ng-container *ngFor=\"let column of maf.columns\" [sticky]=\"column.sticky\" [matColumnDef]=\"column.columnDef\">\n\t\t\t\t\t<th class=\"clickable hover-highlight\" mat-header-cell *matHeaderCellDef>\n\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t<span (click)=\"headerClick(column, $event)\">{{ formatHeader(column.header) }} </span>\n\t\t\t\t\t\t\t<span mat-sort-header>&nbsp;</span>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</th>\n\t\t\t\t\t<td (dblclick)=\"editCell(row, column, $event)\" (click)=\"cellClick(row, column, $event)\" class=\"clickable\" [ngClass]=\"{'selected': isSelected(row, column)}\" mat-cell *matCellDef=\"let row\"> \n\t\t\t\t\t\t{{ row[column.header] }} \n\t\t\t\t\t</td>\n\t\t\t\t</ng-container>\n\t\t\t\t<tr mat-header-row *matHeaderRowDef=\"maf.displayedColumns\"></tr>\n\t\t\t\t<tr mat-row *matRowDef=\"let row; columns: maf.displayedColumns;\"></tr>\n\t\t\t</table>\n\t\t</div>\n\t</div>\n</div>\n\n\n<div class=\"modal\" *ngIf=\"isRowEditModalOpen\" [ngClass]=\"{'is-active': isRowEditModalOpen}\">\n\t<form *ngIf=\"form\" [formGroup]=\"form\">\n\t\t<div class=\"modal-background\"></div>\n\t\t<div class=\"modal-card\">\n\t\t\t<div *ngIf=\"isFormBusy\" class=\"load-bar\">\n\t\t\t\t<div class=\"bar\"></div>\n\t\t\t\t<div class=\"bar\"></div>\n\t\t\t\t<div class=\"bar\"></div>\n\t\t\t</div>\n\t\t\t<section class=\"modal-card-body\">\n\t\t\t\t<div class=\"field is-horizontal\">\n\t\t\t\t\t<div class=\"field-body\">\n\t\t\t\t\t\t<div class=\"field\">\n\t\t\t\t\t\t\t<div class=\"control\">\n\t\t\t\t\t\t\t\t<label><small>Name</small></label>\n\t\t\t\t\t\t\t\t<mat-form-field class=\"full-width\">\n\t\t\t\t\t\t\t\t\t<input class=\"no-scroll\" \n\t\t\t\t\t\t\t\t\tformControlName=\"name\"\n\t\t\t\t\t\t\t\t\t(blur)=\"search('name')\"\n\t\t\t\t\t\t\t\t\tmatInput>\n\t\t\t\t\t\t\t\t\t<!-- <mat-hint>{{ fieldValidation('doi').description }}</mat-hint> -->\n\t\t\t\t\t\t\t\t\t<!-- <mat-error\n\t\t\t\t\t\t\t\t\t*ngIf=\"form.get('title').errors &&\n\t\t\t\t\t\t\t\t\tform.get('title').dirty &&\n\t\t\t\t\t\t\t\t\tform.get('title').errors.title\">\n\t\t\t\t\t\t\t\t\t{{ form.get('title').errors.title.error }}\n\t\t\t\t\t\t\t\t\t</mat-error> -->\n\t\t\t\t\t\t\t\t</mat-form-field>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div class=\"control\">\n\t\t\t\t\t\t\t\t<label><small>SMILES</small></label>\n\t\t\t\t\t\t\t\t<mat-form-field class=\"full-width\">\n\t\t\t\t\t\t\t\t\t<input class=\"no-scroll\" \n\t\t\t\t\t\t\t\t\tformControlName=\"smiles\"\n\t\t\t\t\t\t\t\t\t(blur)=\"search('smiles')\"\n\t\t\t\t\t\t\t\t\tmatInput>\n\t\t\t\t\t\t\t\t\t<!-- <mat-hint>{{ fieldValidation('doi').description }}</mat-hint> -->\n\t\t\t\t\t\t\t\t\t<!-- <mat-error\n\t\t\t\t\t\t\t\t\t*ngIf=\"form.get('title').errors &&\n\t\t\t\t\t\t\t\t\tform.get('title').dirty &&\n\t\t\t\t\t\t\t\t\tform.get('title').errors.title\">\n\t\t\t\t\t\t\t\t\t{{ form.get('title').errors.title.error }}\n\t\t\t\t\t\t\t\t\t</mat-error> -->\n\t\t\t\t\t\t\t\t</mat-form-field>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div class=\"control\">\n\t\t\t\t\t\t\t\t<label><small>InChI</small></label>\n\t\t\t\t\t\t\t\t<mat-form-field class=\"full-width\">\n\t\t\t\t\t\t\t\t\t<input class=\"no-scroll\" \n\t\t\t\t\t\t\t\t\tformControlName=\"inchi\"\n\t\t\t\t\t\t\t\t\t(blur)=\"search('inchi')\"\n\t\t\t\t\t\t\t\t\tmatInput>\n\t\t\t\t\t\t\t\t\t<!-- <mat-hint>{{ fieldValidation('doi').description }}</mat-hint> -->\n\t\t\t\t\t\t\t\t\t<!-- <mat-error\n\t\t\t\t\t\t\t\t\t*ngIf=\"form.get('title').errors &&\n\t\t\t\t\t\t\t\t\tform.get('title').dirty &&\n\t\t\t\t\t\t\t\t\tform.get('title').errors.title\">\n\t\t\t\t\t\t\t\t\t{{ form.get('title').errors.title.error }}\n\t\t\t\t\t\t\t\t\t</mat-error> -->\n\t\t\t\t\t\t\t\t</mat-form-field>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t\t<div class=\"control\">\n\t\t\t\t\t\t\t\t<label><small>Database Identifier</small></label>\n\t\t\t\t\t\t\t\t<mat-form-field class=\"full-width\">\n\t\t\t\t\t\t\t\t\t<input class=\"no-scroll\" \n\t\t\t\t\t\t\t\t\tformControlName=\"databaseId\"\n\t\t\t\t\t\t\t\t\t(blur)=\"search('databaseId')\"\n\t\t\t\t\t\t\t\t\tmatInput>\n\t\t\t\t\t\t\t\t\t<!-- <mat-hint>{{ fieldValidation('doi').description }}</mat-hint> -->\n\t\t\t\t\t\t\t\t\t<!-- <mat-error\n\t\t\t\t\t\t\t\t\t*ngIf=\"form.get('title').errors &&\n\t\t\t\t\t\t\t\t\tform.get('title').dirty &&\n\t\t\t\t\t\t\t\t\tform.get('title').errors.title\">\n\t\t\t\t\t\t\t\t\t{{ form.get('title').errors.title.error }}\n\t\t\t\t\t\t\t\t\t</mat-error> -->\n\t\t\t\t\t\t\t\t</mat-form-field>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</section>\n\t\t\t<footer class=\"modal-card-foot buttons is-right\">\n\t\t\t\t<button *ngIf=\"!form.pristine\" [disabled]=\"!form.valid || isFormBusy\" (click)='saveMultipleCells()' class=\"button is-info\">\n\t\t\t\t\t<mat-spinner [diameter]=\"20\" [strokeWidth]=\"3\" *ngIf=\"isFormBusy\"></mat-spinner>\n\t\t\t\t\tSave\n\t\t\t\t</button>\n\t\t\t\t<button *ngIf=\"form.pristine\" (click)='closeRowEditModal()' class=\"button is-info\">OK</button>\n\t\t\t\t<button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeRowEditModal()'>Cancel</button>\n\t\t\t</footer>\n\t\t</div>\n\t</form>\n</div>\n\n<div class=\"modal\" *ngIf=\"isEditModalOpen\" [ngClass]=\"{'is-active': isEditModalOpen}\">\n\t<form *ngIf=\"form\" [formGroup]=\"form\">\n\t\t<div class=\"modal-background\"></div>\n\t\t<div class=\"modal-card\">\n\t\t\t<div *ngIf=\"isFormBusy\" class=\"load-bar\">\n\t\t\t\t<div class=\"bar\"></div>\n\t\t\t\t<div class=\"bar\"></div>\n\t\t\t\t<div class=\"bar\"></div>\n\t\t\t</div>\n\t\t\t<section class=\"modal-card-body\">\n\t\t\t\t<div class=\"field is-horizontal\">\n\t\t\t\t\t<div class=\"field-body\">\n\t\t\t\t\t\t<div class=\"field\">\n\t\t\t\t\t\t\t<div class=\"control\">\n\t\t\t\t\t\t\t\t<label>{{ formatCellTitle(selectedCell['column']['columnDef']) }}</label>\n\t\t\t\t\t\t\t\t<mat-form-field class=\"full-width\">\n\t\t\t\t\t\t\t\t\t<input class=\"no-scroll\" \n\t\t\t\t\t\t\t\t\tformControlName=\"cell\"\n\t\t\t\t\t\t\t\t\tmatInput>\n\t\t\t\t\t\t\t\t\t<!-- <mat-hint>{{ fieldValidation('doi').description }}</mat-hint> -->\n\t\t\t\t\t\t\t\t\t<!-- <mat-error\n\t\t\t\t\t\t\t\t\t*ngIf=\"form.get('title').errors &&\n\t\t\t\t\t\t\t\t\tform.get('title').dirty &&\n\t\t\t\t\t\t\t\t\tform.get('title').errors.title\">\n\t\t\t\t\t\t\t\t\t{{ form.get('title').errors.title.error }}\n\t\t\t\t\t\t\t\t\t</mat-error> -->\n\t\t\t\t\t\t\t\t</mat-form-field>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</section>\n\t\t\t<footer class=\"modal-card-foot buttons is-right\">\n\t\t\t\t<button *ngIf=\"!form.pristine\" [disabled]=\"!form.valid || isFormBusy\" (click)='saveCell()' class=\"button is-info\">\n\t\t\t\t\t<mat-spinner [diameter]=\"20\" [strokeWidth]=\"3\" *ngIf=\"isFormBusy\"></mat-spinner>\n\t\t\t\t\tSave\n\t\t\t\t</button>\n\t\t\t\t<button *ngIf=\"form.pristine\" (click)='closeEditModal()' class=\"button is-info\">OK</button>\n\t\t\t\t<button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeEditModal()'>Cancel</button>\n\t\t\t</footer>\n\t\t</div>\n\t</form>\n</div>\n\n<div class=\"modal\" *ngIf=\"isDeleteModalOpen\"  [ngClass]=\"{'is-active': isDeleteModalOpen}\">\n\t<div class=\"modal-background\"></div>\n\t<div class=\"modal-card\">\n\t\t<section class=\"modal-card-body\">\n\t\t\t<p>Are you sure you want to delete the selected rows?</p>\n\t\t</section>\n\t\t<footer class=\"modal-card-foot\">\n\t\t\t<div class=\"columns is-gapless full-width\">\n\t\t\t\t<div class=\"column is-half\">\n\t\t\t\t\t<button (click)='closeDelete()' class=\"button is-info\">Cancel</button>\n\t\t\t\t</div>\n\t\t\t\t<div class=\"column is-half has-text-right\">\n\t\t\t\t\t<button (click)='deleteSelectedRows()' class=\"button is-danger\">OK! Delete Permanently</button>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</footer>\n\t</div>\n</div>"

/***/ }),

/***/ "./src/app/components/study/metabolites/maf/maf.component.ts":
/*!*******************************************************************!*\
  !*** ./src/app/components/study/metabolites/maf/maf.component.ts ***!
  \*******************************************************************/
/*! exports provided: MafComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MafComponent", function() { return MafComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_3___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_3__);
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_5__);
/* harmony import */ var tassign__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! tassign */ "./node_modules/tassign/lib/index.js");
/* harmony import */ var tassign__WEBPACK_IMPORTED_MODULE_6___default = /*#__PURE__*/__webpack_require__.n(tassign__WEBPACK_IMPORTED_MODULE_6__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var MafComponent = /** @class */ (function () {
    function MafComponent(fb, metabolightsService, ngRedux) {
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.ngRedux = ngRedux;
        this.selectedRows = [];
        this.selectedColumns = [];
        this.selectedCells = [];
        this.filter = '';
        this.filters = [];
        this.lastRowSelection = null;
        this.lastColSelection = null;
        this.selectedColumn = null;
        this.selectedColumnValues = null;
        this.selectedCell = {};
        this.selectedRow = {};
        this.isDeleteModalOpen = false;
        this.isEditModalOpen = false;
        this.isRowEditModalOpen = false;
        this.isFormBusy = false;
    }
    MafComponent.prototype.ngOnInit = function () {
        this.tableDataSource = new _angular_material__WEBPACK_IMPORTED_MODULE_2__["MatTableDataSource"](this.maf.data);
    };
    MafComponent.prototype.ngAfterViewInit = function () {
        this.tableDataSource.sort = this.sort;
        this.tableDataSource.paginator = this.paginator;
    };
    MafComponent.prototype.openRowEditModal = function (row) {
        this.isRowEditModalOpen = true;
        this.selectedRow = row;
        this.form = this.fb.group({
            name: [row['metabolite_identification']],
            smiles: [row['smiles']],
            inchi: [row['smiles']],
            databaseId: [row['database_identifier']]
        });
    };
    MafComponent.prototype.closeRowEditModal = function () {
        this.isRowEditModalOpen = false;
    };
    MafComponent.prototype.search = function (type) {
        var _this = this;
        var term = this.form.get(type).value;
        this.isFormBusy = true;
        this.metabolightsService.search(term, type).subscribe(function (res) {
            var resultObj = res.content[0];
            _this.isFormBusy = false;
            var fields = ['name', 'smiles', 'inchi', 'databaseId'];
            fields.forEach(function (field) {
                if (field != term) {
                    _this.form.get(field).setValue(resultObj[field], { emitEvent: false });
                }
            });
            _this.form.markAsDirty();
        });
    };
    MafComponent.prototype.onKeydown = function (event, filterValue) {
        var _this = this;
        if (event.key === "Enter") {
            var data_1 = [];
            if (this.filters.indexOf(filterValue) < 0) {
                this.filters.push(filterValue);
                event.target.value = '';
            }
            this.tableDataSource.filter = '';
            this.filters.forEach(function (f) {
                data_1 = data_1.concat(_this.tableDataSource.data.filter(function (d) { return _this.getDataString(d).indexOf(f.toLowerCase()) > -1; }));
            });
            this.tableDataSource.data = data_1;
        }
    };
    MafComponent.prototype.isSelected = function (row, column) {
        if ((row && column) && this.selectedCells.length > 0) {
            return this.selectedCells.filter(function (cell) { return (cell[0] == column.columnDef && cell[1] == row.index); }).length > 0;
        }
        else if (this.selectedColumns.length == 0) {
            if (this.selectedRows.indexOf(row.index) > -1) {
                return true;
            }
        }
        else if (this.selectedRows.length == 0) {
            if (this.selectedColumns.indexOf(column.columnDef) > -1) {
                return true;
            }
        }
        return false;
    };
    MafComponent.prototype.formatHeader = function (term) {
        return term.replace("_", " ");
    };
    MafComponent.prototype.rowClick = function (row, event) {
        this.selectedCells = [];
        this.selectedColumns = [];
        var entryIndex = row.index;
        var rowIndex = this.selectedRows.indexOf(entryIndex);
        if (event && event.altKey) {
            if (rowIndex > -1) {
                this.selectedRows.splice(rowIndex, 1);
            }
            else {
                this.selectedRows.push(entryIndex);
            }
        }
        else if (event && event.shiftKey) {
            var lastSelectionIndex = null;
            var lastRowIndex = -1;
            var rowNamesArray = this.maf.data.map(function (e) { return e.index; });
            if (this.lastRowSelection) {
                lastSelectionIndex = this.lastRowSelection.index;
                lastRowIndex = rowNamesArray.indexOf(lastSelectionIndex);
            }
            else {
                lastRowIndex = 0;
            }
            var currentRowIndex = rowNamesArray.indexOf(entryIndex);
            var currentSelection = [];
            if (lastRowIndex > currentRowIndex) {
                currentSelection = rowNamesArray.slice(currentRowIndex, lastRowIndex + 1);
            }
            else {
                currentSelection = rowNamesArray.slice(lastRowIndex, currentRowIndex + 1);
            }
            this.selectedRows = this.selectedRows.concat(currentSelection);
        }
        else {
            if (rowIndex < 0) {
                this.selectedRows = [entryIndex];
            }
            else {
                this.selectedRows = [];
            }
        }
        this.lastRowSelection = row;
    };
    MafComponent.prototype.cellClick = function (row, column, event) {
        if (event.altKey) {
            this.selectedCells.push([column.columnDef, row.index]);
        }
        else {
            this.selectedCells = [[column.columnDef, row.index]];
        }
    };
    MafComponent.prototype.toTitleCase = function (str) {
        return str.replace(/\w\S*/g, function (txt) {
            return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
        });
    };
    MafComponent.prototype.formatCellTitle = function (term) {
        return term.replace("_", " ");
    };
    MafComponent.prototype.editCell = function (row, column, event) {
        this.isEditModalOpen = true;
        this.selectedCell['row'] = row;
        this.selectedCell['column'] = column;
        this.form = this.fb.group({
            cell: [row[column.columnDef]],
        });
    };
    MafComponent.prototype.closeEditModal = function () {
        this.isEditModalOpen = false;
    };
    MafComponent.prototype.deSelect = function () {
        this.selectedRows = [];
        this.selectedColumns = [];
        this.selectedCells = [];
    };
    MafComponent.prototype.headerClick = function (column, event) {
        this.selectedCells = [];
        this.selectedRows = [];
        var entryIndex = column.columnDef;
        var colIndex = this.selectedColumns.indexOf(entryIndex);
        if (event.altKey) {
            if (colIndex > -1) {
                this.selectedRows.splice(colIndex, 1);
            }
            else {
                this.selectedRows.push(entryIndex);
            }
        }
        else if (event.shiftKey) {
            var lastSelectionIndex = null;
            var lastRowIndex = -1;
            var colNamesArray = this.maf.displayedColumns.map(function (e) { return e.columnDef; });
            if (this.lastColSelection) {
                lastSelectionIndex = this.lastColSelection.index;
                lastRowIndex = colNamesArray.indexOf(lastSelectionIndex);
            }
            else {
                lastRowIndex = 0;
            }
            var currentRowIndex = colNamesArray.indexOf(entryIndex);
            var currentSelection = [];
            if (lastRowIndex > currentRowIndex) {
                currentSelection = colNamesArray.slice(currentRowIndex, lastRowIndex + 1);
            }
            else {
                currentSelection = colNamesArray.slice(lastRowIndex, currentRowIndex + 1);
            }
            this.selectedColumns = this.selectedColumns.concat(currentSelection);
        }
        else {
            if (colIndex < 0) {
                this.selectedColumns = [entryIndex];
            }
            else {
                this.selectedColumns = [];
            }
        }
        this.lastColSelection = column;
    };
    MafComponent.prototype.keys = function (object) {
        return Object.keys(object);
    };
    MafComponent.prototype.removeFilter = function (filter) {
        var _this = this;
        this.filters = this.filters.filter(function (e) { return e !== filter; });
        this.tableDataSource.filter = '';
        if (this.filters.length > 0) {
            var data_2 = [];
            this.filters.forEach(function (f) {
                data_2 = data_2.concat(_this.tableDataSource.filter(f.toLowerCase()));
            });
            console.log(data_2);
            this.tableDataSource.data = data_2;
        }
        else {
            this.tableDataSource.data = this.maf.data;
        }
    };
    MafComponent.prototype.highlightFilteredRows = function (term) {
        var _this = this;
        this.selectedRows = this.selectedRows.concat(this.maf.data.filter(function (f) { return _this.getDataString(f).indexOf(term.toLowerCase()) != -1; }).map(function (p) { return p.index; }));
    };
    MafComponent.prototype.getDataString = function (row) {
        var rowString = "";
        Object.keys(row).forEach(function (prop) { if (row[prop]) {
            rowString = rowString + " " + row[prop];
        } });
        return rowString.toLowerCase();
    };
    MafComponent.prototype.applyFilter = function (filterValue) {
        if (filterValue != '') {
            this.tableDataSource.data = this.maf.data;
            this.tableDataSource.filter = filterValue.trim().toLowerCase();
        }
    };
    MafComponent.prototype.getUnique = function (arr) {
        return arr.filter(function (value, index, array) {
            return array.indexOf(value) == index;
        });
    };
    MafComponent.prototype.getEmptyRow = function () {
        var obj = Object(tassign__WEBPACK_IMPORTED_MODULE_6__["tassign"])({}, this.maf.data[0]);
        Object.keys(obj).forEach(function (prop) {
            obj[prop] = "";
        });
        return obj;
    };
    MafComponent.prototype.saveCell = function () {
        this.selectedCell['row'][this.selectedCell['column']['columnDef']] = this.form.get('cell').value;
        this.save(this.selectedCell['row']);
    };
    MafComponent.prototype.saveMultipleCells = function () {
        this.selectedRow['metabolite_identification'] = this.form.get('name').value;
        this.selectedRow['inchi'] = this.form.get('inchi').value;
        this.selectedRow['database_identifier'] = this.form.get('databaseId').value;
        this.selectedRow['smiles'] = this.form.get('smiles').value;
        this.save(this.selectedRow);
    };
    MafComponent.prototype.save = function (data) {
        var _this = this;
        this.metabolightsService.updateMAFEntry(this.maf.file, { "mafdata": [data] }).subscribe(function (res) {
            toastr__WEBPACK_IMPORTED_MODULE_5__["success"]("MAF entry updated successfully", "Success", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
            var columns = [];
            Object.keys(res.mafHeader).forEach(function (key) {
                var fn = "element['" + key + "']";
                columns.push({
                    "columnDef": key.toLowerCase().split(" ").join("_"),
                    "sticky": "false",
                    "header": key,
                    "cell": function (element) { return eval(fn); }
                });
            });
            var displayedColumns = columns.map(function (a) { return a.columnDef; });
            displayedColumns.unshift("Select");
            displayedColumns.sort(function (a, b) {
                return res.mafHeader[a] - res.mafHeader[b];
            });
            _this.getMAFData(_this.maf.file);
            _this.rowClick(_this.selectedCell['row'], null);
            _this.form.markAsPristine();
        }, function (err) {
        });
    };
    MafComponent.prototype.addRow = function () {
        var _this = this;
        this.metabolightsService.addMAFEntry(this.maf.file, { "mafdata": [this.getEmptyRow()] }).subscribe(function (res) {
            toastr__WEBPACK_IMPORTED_MODULE_5__["success"]("Rows add successfully to the end of the maf sheet", "Success", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
            var columns = [];
            Object.keys(res.mafHeader).forEach(function (key) {
                var fn = "element['" + key + "']";
                columns.push({
                    "columnDef": key.toLowerCase().split(" ").join("_"),
                    "sticky": "false",
                    "header": key,
                    "cell": function (element) { return eval(fn); }
                });
            });
            var displayedColumns = columns.map(function (a) { return a.columnDef; });
            displayedColumns.unshift("Select");
            displayedColumns.sort(function (a, b) {
                return res.mafHeader[a] - res.mafHeader[b];
            });
            _this.getMAFData(_this.maf.file);
        }, function (err) {
        });
    };
    MafComponent.prototype.deleteSelectedRows = function () {
        var _this = this;
        this.metabolightsService.deleteMAFEntries(this.maf.file, this.getUnique(this.selectedRows).join(",")).subscribe(function (res) {
            _this.isDeleteModalOpen = false;
            toastr__WEBPACK_IMPORTED_MODULE_5__["success"]("Rows delete successfully", "Success", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
            var columns = [];
            Object.keys(res.mafHeader).forEach(function (key) {
                var fn = "element['" + key + "']";
                columns.push({
                    "columnDef": key.toLowerCase().split(" ").join("_"),
                    "sticky": "false",
                    "header": key,
                    "cell": function (element) { return eval(fn); }
                });
            });
            var displayedColumns = columns.map(function (a) { return a.columnDef; });
            displayedColumns.unshift("Select");
            displayedColumns.sort(function (a, b) {
                return res.mafHeader[a] - res.mafHeader[b];
            });
            _this.getMAFData(_this.maf.file);
        }, function (err) { });
    };
    MafComponent.prototype.getMAFData = function (maf) {
        var _this = this;
        var mafData;
        return this.metabolightsService.getMAF(maf).subscribe(function (data) {
            var columns = [];
            Object.keys(data.mafHeader).forEach(function (key) {
                var fn = "element['" + key + "']";
                columns.push({
                    "columnDef": key.toLowerCase().split(" ").join("_"),
                    "sticky": "false",
                    "header": key,
                    "cell": function (element) { return eval(fn); }
                });
            });
            var displayedColumns = columns.map(function (a) { return a.columnDef; });
            displayedColumns.unshift("Select");
            displayedColumns.sort(function (a, b) {
                return parseInt(data.mafHeader[a]) - parseInt(data.mafHeader[b]);
            });
            mafData = { 'assay': _this.maf.assay, 'file': maf, 'data': data.mafData.rows, 'columnOrder': {}, 'columns': columns, 'displayedColumns': displayedColumns };
            _this.ngRedux.dispatch({ type: 'UPDATE_MAF_DATA', body: {
                    'maf': mafData
                } });
        });
    };
    MafComponent.prototype.closeDelete = function () {
        this.isDeleteModalOpen = false;
    };
    MafComponent.prototype.openDeleteModal = function () {
        this.isDeleteModalOpen = true;
    };
    MafComponent.prototype.keyExists = function (object, key) {
        return object[key] != undefined;
    };
    MafComponent.prototype.valueExists = function (array, value) {
        return array.indexOf(value) >= 0;
    };
    MafComponent.prototype.isObject = function (item) {
        return (typeof item === "object" && !Array.isArray(item) && item !== null);
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", Object)
    ], MafComponent.prototype, "maf", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], MafComponent.prototype, "studyValidations", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])(_angular_material__WEBPACK_IMPORTED_MODULE_2__["MatPaginator"]),
        __metadata("design:type", _angular_material__WEBPACK_IMPORTED_MODULE_2__["MatPaginator"])
    ], MafComponent.prototype, "paginator", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])(_angular_material__WEBPACK_IMPORTED_MODULE_2__["MatSort"]),
        __metadata("design:type", _angular_material__WEBPACK_IMPORTED_MODULE_2__["MatSort"])
    ], MafComponent.prototype, "sort", void 0);
    MafComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-maf',
            template: __webpack_require__(/*! ./maf.component.html */ "./src/app/components/study/metabolites/maf/maf.component.html"),
            styles: [__webpack_require__(/*! ./maf.component.css */ "./src/app/components/study/metabolites/maf/maf.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_4__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_1__["MetabolightsService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_3__["NgRedux"]])
    ], MafComponent);
    return MafComponent;
}());



/***/ }),

/***/ "./src/app/components/study/metabolites/metabolites.component.css":
/*!************************************************************************!*\
  !*** ./src/app/components/study/metabolites/metabolites.component.css ***!
  \************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvbWV0YWJvbGl0ZXMvbWV0YWJvbGl0ZXMuY29tcG9uZW50LmNzcyJ9 */"

/***/ }),

/***/ "./src/app/components/study/metabolites/metabolites.component.html":
/*!*************************************************************************!*\
  !*** ./src/app/components/study/metabolites/metabolites.component.html ***!
  \*************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<span *ngIf=\"mafFiles && mafFiles.length > 0\">\n\t<span *ngFor=\"let maf of mafFiles\">\n\t\t<mtbls-maf [value]=\"maf\" [validations]=\"validations\"></mtbls-maf>\n\t</span>\n</span>\n"

/***/ }),

/***/ "./src/app/components/study/metabolites/metabolites.component.ts":
/*!***********************************************************************!*\
  !*** ./src/app/components/study/metabolites/metabolites.component.ts ***!
  \***********************************************************************/
/*! exports provided: MetabolitesComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MetabolitesComponent", function() { return MetabolitesComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var MetabolitesComponent = /** @class */ (function () {
    function MetabolitesComponent() {
        var _this = this;
        this.mafFiles = [];
        this.validationsId = 'assays';
        this.studyMAFFiles.subscribe(function (value) {
            _this.mafFiles = value;
        });
    }
    MetabolitesComponent.prototype.ngOnInit = function () {
    };
    MetabolitesComponent.prototype.ngAfterViewChecked = function () {
    };
    Object.defineProperty(MetabolitesComponent.prototype, "validations", {
        get: function () {
            if (this.validationsId.includes(".")) {
                var arr = this.validationsId.split(".");
                var tempValidations = JSON.parse(JSON.stringify(this.studyValidations));
                ;
                while (arr.length && (tempValidations = tempValidations[arr.shift()]))
                    ;
                return tempValidations;
            }
            return this.studyValidations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], MetabolitesComponent.prototype, "studyValidations", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.metaboliteAnnotationFiles; }),
        __metadata("design:type", Object)
    ], MetabolitesComponent.prototype, "studyMAFFiles", void 0);
    MetabolitesComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-metabolites',
            template: __webpack_require__(/*! ./metabolites.component.html */ "./src/app/components/study/metabolites/metabolites.component.html"),
            styles: [__webpack_require__(/*! ./metabolites.component.css */ "./src/app/components/study/metabolites/metabolites.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], MetabolitesComponent);
    return MetabolitesComponent;
}());



/***/ }),

/***/ "./src/app/components/study/ontology/ontology.component.css":
/*!******************************************************************!*\
  !*** ./src/app/components/study/ontology/ontology.component.css ***!
  \******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvb250b2xvZ3kvb250b2xvZ3kuY29tcG9uZW50LmNzcyJ9 */"

/***/ }),

/***/ "./src/app/components/study/ontology/ontology.component.html":
/*!*******************************************************************!*\
  !*** ./src/app/components/study/ontology/ontology.component.html ***!
  \*******************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<span *ngIf=\"isInline\">\n\t<div>\n\t\t<mat-form-field class=\"full-width\">\n\t\t\t<mat-chip-list #chipList>\n\t\t\t\t<mat-chip\n\t\t\t\t*ngFor=\"let value of values\"\n\t\t\t\t[selectable]=\"selectable\"\n\t\t\t\t[removable]=\"removable\"\n\t\t\t\t(removed)=\"remove(value)\">\n\t\t\t\t{{ value.annotationValue }}\n\t\t\t\t<mat-icon matChipRemove *ngIf=\"removable\">cancel</mat-icon>\n\t\t\t</mat-chip>\n\t\t\t<input\n\t\t\t\t[placeholder]=\"validations.label\"\n\t\t\t\t#valueInput\n\t\t\t\t[formControl]=\"valueCtrl\"\n\t\t\t\t(keyup)=\"updateList()\"\n\t\t\t\t[matAutocomplete]=\"auto\"\n\t\t\t\t[matChipInputFor]=\"chipList\"\n\t\t\t\t[matChipInputSeparatorKeyCodes]=\"separatorKeysCodes\"\n\t\t\t\t[matChipInputAddOnBlur]=\"addOnBlur\"\n\t\t\t\t(matChipInputTokenEnd)=\"add($event)\">\n\t\t\t</mat-chip-list>\n\t\t\t<mat-autocomplete #auto=\"matAutocomplete\" (optionSelected)=\"selected($event)\">\n\t\t\t\t<mat-option *ngFor=\"let value of filteredvalues | async\" [value]=\"value\">\n\t\t\t\t\t{{ value.annotationValue }}\n\t\t\t\t</mat-option>\n\t\t\t</mat-autocomplete>\n\t\t\t<mat-hint>{{ validations.description }}</mat-hint>\n\t\t</mat-form-field>\n\t</div>\n</span>\n<span *ngIf=\"!isInline\">\n\t<div class=\"field mt-10\">\n\t\t<label class=\"bulma-label has-text-grey\"><small>{{ validations.description }}</small></label>\n\t\t<div class=\"control\">\n\t\t\t<a class=\"button is-success\">\n\t\t\t\t<mat-icon>playlist_add</mat-icon> Add\n\t\t\t</a>\n\t\t</div>\n\t</div>\n</span>"

/***/ }),

/***/ "./src/app/components/study/ontology/ontology.component.ts":
/*!*****************************************************************!*\
  !*** ./src/app/components/study/ontology/ontology.component.ts ***!
  \*****************************************************************/
/*! exports provided: OntologyComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "OntologyComponent", function() { return OntologyComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_cdk_keycodes__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/cdk/keycodes */ "./node_modules/@angular/cdk/esm5/keycodes.es5.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./../../../models/mtbl/mtbls/common/mtbls-ontology */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_6___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_6__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







/**
 * @title Chips Autocomplete
 */
var OntologyComponent = /** @class */ (function () {
    function OntologyComponent(metabolightsService) {
        this.metabolightsService = metabolightsService;
        this.values = [];
        this.changed = new _angular_core__WEBPACK_IMPORTED_MODULE_0__["EventEmitter"]();
        this.isforcedOntology = false;
        this.url = '';
        this.endPoints = [];
        this.isFormBusy = false;
        this.visible = true;
        this.selectable = true;
        this.removable = true;
        this.addOnBlur = false;
        this.separatorKeysCodes = [_angular_cdk_keycodes__WEBPACK_IMPORTED_MODULE_1__["ENTER"], _angular_cdk_keycodes__WEBPACK_IMPORTED_MODULE_1__["COMMA"]];
        this.valueCtrl = new _angular_forms__WEBPACK_IMPORTED_MODULE_2__["FormControl"]();
        this.allvalues = [];
    }
    OntologyComponent.prototype.ngOnInit = function () {
        var _this = this;
        if (this.values == null || this.values[0] == null) {
            this.values = [];
        }
        if (this.validations['data-type'] == 'ontology') {
            if (this.validations['recommended-ontologies']) {
                this.isforcedOntology = this.validations['recommended-ontologies']['is-forced-ontology'];
                this.url = this.validations['recommended-ontologies']['ontology']['url'];
                this.endPoints = this.validations['recommended-ontologies']['ontology'];
                if (this.url != '') {
                    this.metabolightsService.getOntologyTerms(this.url).subscribe(function (terms) {
                        var jsonConvert = new json2typescript__WEBPACK_IMPORTED_MODULE_6__["JsonConvert"]();
                        terms.OntologyTerm.forEach(function (term) {
                            _this.allvalues.push(jsonConvert.deserialize(term, _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_5__["Ontology"]));
                        });
                        _this.filteredvalues = _this.valueCtrl.valueChanges.pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["startWith"])(null), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["map"])(function (value) { return value ? _this._filter(value) : _this.allvalues.slice(); }));
                    });
                }
            }
        }
    };
    OntologyComponent.prototype.add = function (event) {
        var input = event.input;
        var value = event.value;
        if (this.indexOfObject(this.allvalues, 'annotationValue', value) > -1) {
            if (this.indexOfObject(this.values, 'annotationValue', value) == -1) {
                this.values.push(this.allvalues[this.indexOfObject(this.allvalues, 'annotationvalue', value)]);
            }
        }
        else {
            if (!this.isforcedOntology) {
                var ontology = new _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_5__["Ontology"]();
                ontology.annotationValue = value;
                this.values.push(ontology);
            }
        }
        if (input) {
            input.value = '';
        }
        this.valueCtrl.setValue(null);
        this.triggerChanges();
    };
    OntologyComponent.prototype.indexOfObject = function (array, key, value) {
        return array.map(function (e) { return e[key]; }).indexOf(value);
    };
    OntologyComponent.prototype.remove = function (value) {
        var index = this.indexOfObject(this.values, 'annotationValue', value.annotationValue);
        if (index >= 0) {
            this.values.splice(index, 1);
        }
        this.triggerChanges();
    };
    OntologyComponent.prototype.selected = function (event) {
        if (this.indexOfObject(this.values, 'annotationValue', event.option.value.annotationValue) == -1) {
            this.values.push(event.option.value);
        }
        this.valueInput.nativeElement.value = '';
        this.valueCtrl.setValue(null);
        this.triggerChanges();
    };
    OntologyComponent.prototype.setValue = function (value) {
        var _this = this;
        this.allvalues.forEach(function (val) {
            if (val.annotationValue == value) {
                _this.values = [val];
            }
        });
    };
    OntologyComponent.prototype.updateList = function () {
        var _this = this;
        var term = this.valueInput.nativeElement.value;
        this.metabolightsService.getOntologyTerms(this.url + term).subscribe(function (terms) {
            _this.allvalues = [];
            var jsonConvert = new json2typescript__WEBPACK_IMPORTED_MODULE_6__["JsonConvert"]();
            terms.OntologyTerm.forEach(function (term) {
                _this.allvalues.push(jsonConvert.deserialize(term, _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_5__["Ontology"]));
            });
            _this.filteredvalues = _this.valueCtrl.valueChanges.pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["startWith"])(null), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_3__["map"])(function (value) { return value ? _this._filter(value) : _this.allvalues.slice(); }));
        });
    };
    OntologyComponent.prototype.triggerChanges = function () {
        this.changed.emit();
    };
    OntologyComponent.prototype._filter = function (value) {
        if (value.annotationValue) {
            var filterValue_1 = value.annotationValue.toLowerCase();
            return this.allvalues.filter(function (value) { return value.annotationValue.toLowerCase().indexOf(filterValue_1) === 0; });
        }
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], OntologyComponent.prototype, "validations", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('values'),
        __metadata("design:type", Array)
    ], OntologyComponent.prototype, "values", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('inline'),
        __metadata("design:type", Boolean)
    ], OntologyComponent.prototype, "isInline", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('id'),
        __metadata("design:type", String)
    ], OntologyComponent.prototype, "id", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Output"])(),
        __metadata("design:type", Object)
    ], OntologyComponent.prototype, "changed", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])('valueInput'),
        __metadata("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_0__["ElementRef"])
    ], OntologyComponent.prototype, "valueInput", void 0);
    OntologyComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-ontology',
            template: __webpack_require__(/*! ./ontology.component.html */ "./src/app/components/study/ontology/ontology.component.html"),
            styles: [__webpack_require__(/*! ./ontology.component.css */ "./src/app/components/study/ontology/ontology.component.css")]
        }),
        __metadata("design:paramtypes", [_services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_4__["MetabolightsService"]])
    ], OntologyComponent);
    return OntologyComponent;
}());



/***/ }),

/***/ "./src/app/components/study/organisms/organism/organism.component.css":
/*!****************************************************************************!*\
  !*** ./src/app/components/study/organisms/organism/organism.component.css ***!
  \****************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".pointer{\n\tcursor: pointer;\n}\n\n.tag.is-info {\n    margin-right: 5px !important;\n    margin-top: 5px !important;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zdHVkeS9vcmdhbmlzbXMvb3JnYW5pc20vb3JnYW5pc20uY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtDQUNDLGdCQUFnQjtDQUNoQjs7QUFFRDtJQUNJLDZCQUE2QjtJQUM3QiwyQkFBMkI7Q0FDOUIiLCJmaWxlIjoic3JjL2FwcC9jb21wb25lbnRzL3N0dWR5L29yZ2FuaXNtcy9vcmdhbmlzbS9vcmdhbmlzbS5jb21wb25lbnQuY3NzIiwic291cmNlc0NvbnRlbnQiOlsiLnBvaW50ZXJ7XG5cdGN1cnNvcjogcG9pbnRlcjtcbn1cblxuLnRhZy5pcy1pbmZvIHtcbiAgICBtYXJnaW4tcmlnaHQ6IDVweCAhaW1wb3J0YW50O1xuICAgIG1hcmdpbi10b3A6IDVweCAhaW1wb3J0YW50O1xufSJdfQ== */"

/***/ }),

/***/ "./src/app/components/study/organisms/organism/organism.component.html":
/*!*****************************************************************************!*\
  !*** ./src/app/components/study/organisms/organism/organism.component.html ***!
  \*****************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<span class=\"tag is-info\" [ngClass]=\" (organism.parts.length > 0 || organism.variants.length > 0) ? 'pointer' : ''\" (click)=\"showModal()\">\n\t<b>{{ organism.name }}</b>\n</span>\n<div class=\"modal\" [ngClass]=\"{'is-active': isModalOpen}\">\n\t<div class=\"modal-background\"></div>\n\t<div class=\"modal-content\">\n\t\t<article class=\"message is-info\">\n\t\t\t<div class=\"message-header\">\n\t\t\t\t<p>{{ organism.name }}</p>\n\t\t\t\t<button (click)='closeModal()' class=\"delete is-small\" aria-label=\"delete\"></button>\n\t\t\t</div>\n\t\t\t<div class=\"message-body\">\n\t\t\t\t<table *ngIf=\"organism.parts.length > 0\" class=\"table is-bordered is-striped is-narrow is-hoverable is-fullwidth\">\n\t\t\t\t\t<thead>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<th>Part(s)</th>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</thead>\n\t\t\t\t\t<tbody>\n\t\t\t\t\t\t<tr *ngFor=\"let part of organism.parts\">\n\t\t\t\t\t\t\t<td>{{ part }}</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</tbody>\n\t\t\t\t</table>\n\t\t\t\t<table *ngIf=\"organism.variants.length > 0\" class=\"table is-bordered is-striped is-narrow is-hoverable is-fullwidth\">\n\t\t\t\t\t<thead>\n\t\t\t\t\t\t<tr>\n\t\t\t\t\t\t\t<th>Variant(s)</th>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</thead>\n\t\t\t\t\t<tbody>\n\t\t\t\t\t\t<tr *ngFor=\"let variant of organism.variants\">\n\t\t\t\t\t\t\t<td>{{ variant }}</td>\n\t\t\t\t\t\t</tr>\n\t\t\t\t\t</tbody>\n\t\t\t\t</table>\n\t\t\t</div>\n\t\t</article>\n\t</div>\n</div>"

/***/ }),

/***/ "./src/app/components/study/organisms/organism/organism.component.ts":
/*!***************************************************************************!*\
  !*** ./src/app/components/study/organisms/organism/organism.component.ts ***!
  \***************************************************************************/
/*! exports provided: OrganismComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "OrganismComponent", function() { return OrganismComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var OrganismComponent = /** @class */ (function () {
    function OrganismComponent() {
        this.isModalOpen = false;
    }
    OrganismComponent.prototype.ngOnInit = function () {
        this.organism.variants = this.organism.variants.filter(function (n) { return n; });
        this.organism.parts = this.organism.parts.filter(function (n) { return n; });
    };
    OrganismComponent.prototype.showModal = function () {
        if (this.organism.parts.length > 0 || this.organism.variants.length > 0) {
            this.isModalOpen = true;
        }
    };
    OrganismComponent.prototype.closeModal = function () {
        this.isModalOpen = false;
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", Object)
    ], OrganismComponent.prototype, "organism", void 0);
    OrganismComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-organism',
            template: __webpack_require__(/*! ./organism.component.html */ "./src/app/components/study/organisms/organism/organism.component.html"),
            styles: [__webpack_require__(/*! ./organism.component.css */ "./src/app/components/study/organisms/organism/organism.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], OrganismComponent);
    return OrganismComponent;
}());



/***/ }),

/***/ "./src/app/components/study/organisms/organisms.component.css":
/*!********************************************************************!*\
  !*** ./src/app/components/study/organisms/organisms.component.css ***!
  \********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvb3JnYW5pc21zL29yZ2FuaXNtcy5jb21wb25lbnQuY3NzIn0= */"

/***/ }),

/***/ "./src/app/components/study/organisms/organisms.component.html":
/*!*********************************************************************!*\
  !*** ./src/app/components/study/organisms/organisms.component.html ***!
  \*********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"px-20 mtbls-section\">\n\t<span *ngFor=\"let organism of organisms\">\n\t\t<mtbls-organism [value]=\"organism\"></mtbls-organism>\n\t</span>\n</div>\n"

/***/ }),

/***/ "./src/app/components/study/organisms/organisms.component.ts":
/*!*******************************************************************!*\
  !*** ./src/app/components/study/organisms/organisms.component.ts ***!
  \*******************************************************************/
/*! exports provided: OrganismsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "OrganismsComponent", function() { return OrganismsComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var OrganismsComponent = /** @class */ (function () {
    function OrganismsComponent() {
        this.organisms = [];
    }
    OrganismsComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.studyOrganisms.subscribe(function (value) {
            Object.keys(value).forEach(function (key) {
                value[key]['name'] = key;
                _this.organisms.push(value[key]);
            });
        });
    };
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.organisms; }),
        __metadata("design:type", Object)
    ], OrganismsComponent.prototype, "studyOrganisms", void 0);
    OrganismsComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-organisms',
            template: __webpack_require__(/*! ./organisms.component.html */ "./src/app/components/study/organisms/organisms.component.html"),
            styles: [__webpack_require__(/*! ./organisms.component.css */ "./src/app/components/study/organisms/organisms.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], OrganismsComponent);
    return OrganismsComponent;
}());



/***/ }),

/***/ "./src/app/components/study/people/people.component.css":
/*!**************************************************************!*\
  !*** ./src/app/components/study/people/people.component.css ***!
  \**************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvcGVvcGxlL3Blb3BsZS5jb21wb25lbnQuY3NzIn0= */"

/***/ }),

/***/ "./src/app/components/study/people/people.component.html":
/*!***************************************************************!*\
  !*** ./src/app/components/study/people/people.component.html ***!
  \***************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"hover-highlight px-20\">\n    <div class=\"mtbls-section bb\">\n\t\t<h6>\n\t\t\t<mat-icon>people</mat-icon>\n\t\t\t<span *ngFor=\"let person of people; let i = index\">\n\t\t\t\t<span *ngIf=\"i!=0\">,&nbsp;</span>\n\t\t\t\t<mtbls-person [value]=\"person\" [validations]=\"studyValidations\"></mtbls-person>\n\t\t\t</span>\n\t\t\t<mtbls-person [value]=\"null\" [validations]=\"studyValidations\"></mtbls-person>\n\t\t</h6>\n\t</div>\n</div>"

/***/ }),

/***/ "./src/app/components/study/people/people.component.ts":
/*!*************************************************************!*\
  !*** ./src/app/components/study/people/people.component.ts ***!
  \*************************************************************/
/*! exports provided: PeopleComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PeopleComponent", function() { return PeopleComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var PeopleComponent = /** @class */ (function () {
    function PeopleComponent(fb, metabolightsService, ngRedux) {
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.ngRedux = ngRedux;
        this.validationsId = 'people';
    }
    PeopleComponent.prototype.ngOnInit = function () { };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", Object)
    ], PeopleComponent.prototype, "people", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], PeopleComponent.prototype, "studyValidations", void 0);
    PeopleComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-people',
            template: __webpack_require__(/*! ./people.component.html */ "./src/app/components/study/people/people.component.html"),
            styles: [__webpack_require__(/*! ./people.component.css */ "./src/app/components/study/people/people.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__["MetabolightsService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["NgRedux"]])
    ], PeopleComponent);
    return PeopleComponent;
}());



/***/ }),

/***/ "./src/app/components/study/people/person/person.component.css":
/*!*********************************************************************!*\
  !*** ./src/app/components/study/people/person/person.component.css ***!
  \*********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvcGVvcGxlL3BlcnNvbi9wZXJzb24uY29tcG9uZW50LmNzcyJ9 */"

/***/ }),

/***/ "./src/app/components/study/people/person/person.component.html":
/*!**********************************************************************!*\
  !*** ./src/app/components/study/people/person/person.component.html ***!
  \**********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<span *ngIf='person == null || addNewPerson; else showPersonDetails'>\n  <a class=\"button is-light is-pulled-right is-small hover-button\" (click)='openModal()'>+ Add person</a>\n</span>\n<ng-template #showPersonDetails>\n  <span class=\"clickable\" (click)='openModal()'>\n    {{ person.firstName }}&nbsp;{{ person.midInitials }}&nbsp;{{ person.lastName }}\n  </span>\n</ng-template>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isDeleteModalOpen}\">\n  <div class=\"modal-background\"></div>\n  <div class=\"modal-card\">\n    <section class=\"modal-card-body\">\n      <p>Are you sure you want to delete?</p>\n    </section>\n    <footer class=\"modal-card-foot\">\n      <div class=\"columns is-gapless full-width\">\n        <div class=\"column is-half\">\n          <button (click)='closeDelete()' class=\"button is-info\">Cancel</button>\n        </div>\n        <div class=\"column is-half has-text-right\">\n            <button (click)='delete()' class=\"button is-danger\">OK! Delete Permanently</button>\n        </div>\n      </div>\n    </footer>\n  </div>\n</div>\n\n<div class=\"modal\" *ngIf=\"person\" [ngClass]=\"{'is-active': isTimeLineModalOpen}\">\n  <div class=\"modal-background\"></div>\n  <div class=\"modal-card\">\n    <section class=\"modal-card-body\">\n      <div class=\"timeline\">\n        <header class=\"timeline-header\">\n          <span class=\"tag is-small is-primary\">...</span>\n        </header>\n        <div *ngFor=\"let comment of person.comments\">\n          <span *ngIf=\"comment.name == 'updates'\">\n            <span *ngFor=\"let update of getObject(comment.value)\">\n              <div class=\"timeline-item\">\n                <div class=\"timeline-marker\"></div>\n                <div class=\"timeline-content\">\n                  <p>\n                    {{ update.owner }}&emsp;<span class=\"tag is-small is-light\">{{ update.ts }}</span>\n                  </p>\n                </div>\n              </div>\n            </span>\n          </span>\n        </div>\n        <div class=\"timeline-header\">\n          <span class=\"tag is-small is-primary\">...</span>\n        </div>\n      </div>\n    </section>\n    <footer class=\"modal-card-foot\">\n      <div class=\"columns is-gapless full-width\">\n        <div class=\"column is-half\">\n        </div>\n        <div class=\"column is-half has-text-right\">\n          <button (click)='closeHistory()' class=\"button is-info\">OK</button>\n        </div>\n      </div>\n    </footer>\n  </div>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isModalOpen}\">\n  <form *ngIf=\"form\" [formGroup]=\"form\">\n    <div class=\"modal-background\"></div>\n    <div class=\"modal-card\">\n      <div *ngIf=\"isFormBusy\" class=\"load-bar\">\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n      </div>\n      <section class=\"modal-card-body\">\n      \t<table class=\"full-width\" cellspacing=\"0\">\n      \t\t<tr>\n      \t\t\t<td>\n      \t\t\t\t<mat-form-field class=\"full-width\">\n                <input class=\"no-scroll\" \n                formControlName=\"lastName\"\n                matInput>\n                <mat-hint>{{ fieldValidation('lastName').description }}</mat-hint>\n                <mat-error\n                *ngIf=\"form.get('lastName').errors &&\n                form.get('lastName').dirty &&\n                form.get('lastName').errors.lastName\">\n                {{ form.get('lastName').errors.lastName.error }}\n              </mat-error>\n            </mat-form-field>\n          </td>\n          <td>\n            <mat-form-field class=\"full-width\">\n              <input class=\"no-scroll\" \n              formControlName=\"midInitials\"\n              matInput>\n              <mat-hint>{{ fieldValidation('midInitials').description }}</mat-hint>\n              <mat-error\n              *ngIf=\"form.get('midInitials').errors &&\n              form.get('midInitials').dirty &&\n              form.get('midInitials').errors.midInitials\">\n              {{ form.get('midInitials').errors.midInitials.error }}\n            </mat-error>\n          </mat-form-field>\n        </td>\n        <td>\n          <mat-form-field class=\"full-width\">\n            <input class=\"no-scroll\" \n            formControlName=\"firstName\"\n            matInput>\n            <mat-hint>{{ fieldValidation('firstName').description }}</mat-hint>\n            <mat-error\n            *ngIf=\"form.get('firstName').errors &&\n            form.get('firstName').dirty &&\n            form.get('firstName').errors.firstName\">\n            {{ form.get('firstName').errors.firstName.error }}\n          </mat-error>\n        </mat-form-field>\n      </td>\n    </tr>\n  </table>\n  <mat-form-field class=\"full-width\">\n    <input class=\"no-scroll\" \n    formControlName=\"email\"\n    matInput>\n    <mat-hint>{{ fieldValidation('email').description }}</mat-hint>\n    <mat-error\n    *ngIf=\"form.get('email').errors &&\n    form.get('email').dirty &&\n    form.get('email').errors.email\">\n    {{ form.get('email').errors.email.error }}\n  </mat-error>\n</mat-form-field>\n\n<table class=\"full-width\" cellspacing=\"0\">\n  <tr>\n   <td>\n    <mat-form-field class=\"full-width\">\n      <input class=\"no-scroll\" \n      formControlName=\"phone\"\n      matInput>\n      <mat-hint>{{ fieldValidation('phone').description }}</mat-hint>\n      <mat-error\n      *ngIf=\"form.get('phone').errors &&\n      form.get('phone').dirty &&\n      form.get('phone').errors.phone\">\n      {{ form.get('phone').errors.phone.error }}\n    </mat-error>\n  </mat-form-field>\n</td>\n<td>\n  <mat-form-field class=\"full-width\">\n    <input class=\"no-scroll\" \n    formControlName=\"fax\"\n    matInput>\n    <mat-hint>{{ fieldValidation('fax').description }}</mat-hint>\n    <mat-error\n    *ngIf=\"form.get('fax').errors &&\n    form.get('fax').dirty &&\n    form.get('fax').errors.fax\">\n    {{ form.get('fax').errors.fax.error }}\n  </mat-error>\n</mat-form-field>\n</td>\n</tr>\n</table>\n<mat-form-field class=\"full-width\">\n <textarea class=\"no-scroll\" \n formControlName=\"address\"\n matInput\n cdkAutosizeMinRows=\"1\"\n cdkAutosizeMaxRows=\"4\"\n cdkTextareaAutosize>\n</textarea>\n<mat-hint>{{ fieldValidation('address').description }}</mat-hint>\n<mat-error\n*ngIf=\"form.get('address').errors &&\nform.get('address').dirty &&\nform.get('address').errors.address\">\n{{ form.get('address').errors.address.error }}\n</mat-error>\n</mat-form-field>\n<mat-form-field class=\"full-width\">\n <input class=\"no-scroll\" \n formControlName=\"affiliation\"\n matInput>\n <mat-hint>{{ fieldValidation('affiliation').description }}</mat-hint>\n <mat-error\n *ngIf=\"form.get('affiliation').errors &&\n form.get('affiliation').dirty &&\n form.get('affiliation').errors.affiliation\">\n {{ form.get('affiliation').errors.affiliation.error }}\n</mat-error>\n</mat-form-field>\n<mtbls-ontology class=\"mt-20\" #personRoles [validations]=\"fieldValidation('roles')\" (changed)=\"onChanges($event)\" [values]='person.roles' [inline]=\"true\"></mtbls-ontology>\n<!-- <span class=\"full-width mat-form-field-infix\" (click)=\"toogleShowAdvanced()\">\n  <small>\n    <a>\n      <mat-icon *ngIf=\"!showAdvanced; else downIcon\">\n        arrow_right\n      </mat-icon>\n      <ng-template #downIcon>\n        <mat-icon>arrow_drop_down</mat-icon>\n      </ng-template>\n      Advanced\n    </a>\n  </small>\n</span>\n<span *ngIf=\"showAdvanced\" [@enterAnimation]>\n  <mat-form-field class=\"full-width\">\n    <textarea class=\"no-scroll\" \n    formControlName=\"comment\"\n    matInput\n    cdkAutosizeMinRows=\"1\"\n    placeholder=\"comment\" \n    cdkAutosizeMaxRows=\"4\"\n    cdkTextareaAutosize>\n  </textarea>\n</mat-form-field>\n</span> -->\n</section>\n<footer class=\"modal-card-foot\">\n  <div class=\"columns is-gapless full-width\">\n    <div class=\"column is-half\">\n      <button *ngIf=\"!addNewPerson\" class=\"button is-danger is-pulled-left\" (click)=\"confirmDelete()\"><mat-icon>delete_outline</mat-icon></button>\n      <!-- <button class=\"button is-light is-pulled-left\" (click)='showHistory()' ><mat-icon>history</mat-icon></button> -->\n    </div>\n    <div class=\"column is-half has-text-right\">\n      <button *ngIf=\"!form.pristine\" [disabled]=\"!form.valid || isFormBusy\" (click)='save()' class=\"button is-info\">\n        <mat-spinner [diameter]=\"20\" [strokeWidth]=\"3\" *ngIf=\"isFormBusy\"></mat-spinner>\n        Save\n      </button>\n      <button *ngIf=\"form.pristine\" (click)='closeModal()' class=\"button is-info\">OK</button>\n      <button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeModal()'>Cancel</button>\n    </div>\n  </div>\n</footer>\n</div>\n</form>\n</div>"

/***/ }),

/***/ "./src/app/components/study/people/person/person.component.ts":
/*!********************************************************************!*\
  !*** ./src/app/components/study/people/person/person.component.ts ***!
  \********************************************************************/
/*! exports provided: PersonComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PersonComponent", function() { return PersonComponent; });
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./../../../../models/mtbl/mtbls/common/mtbls-ontology */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts");
/* harmony import */ var _models_mtbl_mtbls_mtbls_person__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./../../../../models/mtbl/mtbls/mtbls-person */ "./src/app/models/mtbl/mtbls/mtbls-person.ts");
/* harmony import */ var _angular_animations__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/animations */ "./node_modules/@angular/animations/fesm5/animations.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _person_validator__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./person.validator */ "./src/app/components/study/people/person/person.validator.ts");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_7___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_7__);
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_8___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_8__);
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_9___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_9__);
/* harmony import */ var _ontology_ontology_component__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! ./../../ontology/ontology.component */ "./src/app/components/study/ontology/ontology.component.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};











var PersonComponent = /** @class */ (function () {
    function PersonComponent(fb, metabolightsService, ngRedux) {
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.ngRedux = ngRedux;
        this.showAdvanced = false;
        this.isFormBusy = false;
        this.isModalOpen = false;
        this.addNewPerson = false;
        this.isTimeLineModalOpen = false;
        this.initialEmail = '';
        this.isDeleteModalOpen = false;
        this.options = ['One', 'Two', 'Three'];
        this.validationsId = 'people.person';
    }
    PersonComponent.prototype.ngOnInit = function () {
        if (this.person == null) {
            this.addNewPerson = true;
        }
    };
    PersonComponent.prototype.initialiseForm = function () {
        this.isFormBusy = false;
        if (this.person == null) {
            var mtblsPerson = new _models_mtbl_mtbls_mtbls_person__WEBPACK_IMPORTED_MODULE_3__["MTBLSPerson"]();
            this.person = mtblsPerson;
        }
        this.form = this.fb.group({
            lastName: [this.person.lastName, Object(_person_validator__WEBPACK_IMPORTED_MODULE_6__["ValidateRules"])('lastName', this.fieldValidation('lastName'))],
            firstName: [this.person.firstName, Object(_person_validator__WEBPACK_IMPORTED_MODULE_6__["ValidateRules"])('firstName', this.fieldValidation('firstName'))],
            midInitials: [this.person.midInitials, Object(_person_validator__WEBPACK_IMPORTED_MODULE_6__["ValidateRules"])('midInitials', this.fieldValidation('midInitials'))],
            email: [this.person.email, Object(_person_validator__WEBPACK_IMPORTED_MODULE_6__["ValidateRules"])('email', this.fieldValidation('email'))],
            phone: [this.person.phone, Object(_person_validator__WEBPACK_IMPORTED_MODULE_6__["ValidateRules"])('phone', this.fieldValidation('phone'))],
            fax: [this.person.fax, Object(_person_validator__WEBPACK_IMPORTED_MODULE_6__["ValidateRules"])('fax', this.fieldValidation('fax'))],
            address: [this.person.address, Object(_person_validator__WEBPACK_IMPORTED_MODULE_6__["ValidateRules"])('address', this.fieldValidation('address'))],
            affiliation: [this.person.affiliation, Object(_person_validator__WEBPACK_IMPORTED_MODULE_6__["ValidateRules"])('affiliation', this.fieldValidation('affiliation'))],
            comment: []
        });
    };
    PersonComponent.prototype.showHistory = function () {
        this.isModalOpen = false;
        this.isTimeLineModalOpen = true;
    };
    PersonComponent.prototype.closeHistory = function () {
        this.isTimeLineModalOpen = false;
        this.isModalOpen = true;
    };
    PersonComponent.prototype.openModal = function () {
        this.initialiseForm();
        this.isModalOpen = true;
    };
    PersonComponent.prototype.toogleShowAdvanced = function () {
        this.showAdvanced = !this.showAdvanced;
    };
    PersonComponent.prototype.closeModal = function () {
        this.isModalOpen = false;
    };
    PersonComponent.prototype.confirmDelete = function () {
        this.isModalOpen = false;
        this.isDeleteModalOpen = true;
    };
    PersonComponent.prototype.closeDelete = function () {
        this.isDeleteModalOpen = false;
        this.isModalOpen = true;
    };
    PersonComponent.prototype.delete = function () {
        var _this = this;
        this.metabolightsService.deletePerson(this.person.email).subscribe(function (res) {
            _this.updateContacts(res, 'Person deleted.');
            _this.isDeleteModalOpen = false;
            _this.isModalOpen = false;
        }, function (err) {
            _this.isFormBusy = false;
        });
    };
    Object.defineProperty(PersonComponent.prototype, "validation", {
        get: function () {
            if (this.validationsId.includes(".")) {
                var arr = this.validationsId.split(".");
                var tempValidations = JSON.parse(JSON.stringify(this.studyValidations));
                ;
                while (arr.length && (tempValidations = tempValidations[arr.shift()]))
                    ;
                return tempValidations;
            }
            return this.studyValidations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    PersonComponent.prototype.fieldValidation = function (fieldId) {
        return this.validation[fieldId];
    };
    PersonComponent.prototype.save = function () {
        var _this = this;
        this.isFormBusy = true;
        if (!this.addNewPerson) {
            this.metabolightsService.updatePerson(this.person.email, this.compileBody()).subscribe(function (res) {
                _this.updateContacts(res, "Person updated");
            }, function (err) {
                _this.isFormBusy = false;
            });
        }
        else {
            this.metabolightsService.savePerson(this.compileBody()).subscribe(function (res) {
                _this.updateContacts(res, "Person saved");
                _this.closeModal();
            }, function (err) {
                _this.isFormBusy = false;
            });
        }
    };
    PersonComponent.prototype.updateContacts = function (res, message) {
        var _this = this;
        this.metabolightsService.getPeople().subscribe(function (res) {
            _this.ngRedux.dispatch({ type: 'UPDATE_STUDY_PEOPLE', body: {
                    'people': res.contacts
                } });
        });
        this.form.markAsPristine();
        this.initialiseForm();
        this.isModalOpen = true;
        toastr__WEBPACK_IMPORTED_MODULE_8__["success"](message, "Success", {
            "timeOut": "2500",
            "positionClass": "toast-top-center",
            "preventDuplicates": true,
            "extendedTimeOut": 0,
            "tapToDismiss": false
        });
    };
    PersonComponent.prototype.onChanges = function () {
        this.form.markAsDirty();
    };
    PersonComponent.prototype.getObject = function (data) {
        return JSON.parse(data);
    };
    PersonComponent.prototype.compileBody = function () {
        var jsonConvert = new json2typescript__WEBPACK_IMPORTED_MODULE_9__["JsonConvert"]();
        var mtblPerson = new _models_mtbl_mtbls_mtbls_person__WEBPACK_IMPORTED_MODULE_3__["MTBLSPerson"]();
        mtblPerson.lastName = this.getFieldValue('lastName');
        mtblPerson.firstName = this.getFieldValue('firstName');
        mtblPerson.midInitials = this.getFieldValue('midInitials');
        mtblPerson.email = this.getFieldValue('email');
        mtblPerson.phone = this.getFieldValue('phone');
        mtblPerson.fax = this.getFieldValue('fax');
        mtblPerson.address = this.getFieldValue('address');
        mtblPerson.affiliation = this.getFieldValue('affiliation');
        // let hasUpdates = false
        // var dt = (new Date()).toLocaleString();
        // let currentUpdate = { "ts" : dt, "owner" : "metabolights-user", "value": "changes" }
        // let currentValues = []; 
        // let comments = this.person.comments
        // comments.forEach( comment => {
        // 	if(comment['name'] == 'updates'){
        // 		hasUpdates = true
        // 		currentValues = JSON.parse(comment['value'])
        // 		currentValues.push(currentUpdate)
        // 	}
        // })
        // let newComment = new MTBLSComment()
        // newComment.name = 'updates'
        // if(!hasUpdates){
        // 	currentValues.push(currentUpdate)
        // }
        // newComment.value = currentValues
        // mtblPerson.comments.push(newComment)
        this.rolesComponent.values.forEach(function (role) {
            mtblPerson.roles.push(jsonConvert.deserialize(role, _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_2__["Ontology"]));
        });
        return { "contact": mtblPerson.toJSON() };
    };
    PersonComponent.prototype.getFieldValue = function (name) {
        return this.form.get(name).value;
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])('value'),
        __metadata("design:type", _models_mtbl_mtbls_mtbls_person__WEBPACK_IMPORTED_MODULE_3__["MTBLSPerson"])
    ], PersonComponent.prototype, "person", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Input"])('validations'),
        __metadata("design:type", Object)
    ], PersonComponent.prototype, "studyValidations", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["ViewChild"])(_ontology_ontology_component__WEBPACK_IMPORTED_MODULE_10__["OntologyComponent"]),
        __metadata("design:type", _ontology_ontology_component__WEBPACK_IMPORTED_MODULE_10__["OntologyComponent"])
    ], PersonComponent.prototype, "rolesComponent", void 0);
    PersonComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Component"])({
            selector: 'mtbls-person',
            template: __webpack_require__(/*! ./person.component.html */ "./src/app/components/study/people/person/person.component.html"),
            styles: [__webpack_require__(/*! ./person.component.css */ "./src/app/components/study/people/person/person.component.css")],
            animations: [
                Object(_angular_animations__WEBPACK_IMPORTED_MODULE_4__["trigger"])('enterAnimation', [
                    Object(_angular_animations__WEBPACK_IMPORTED_MODULE_4__["transition"])(':enter', [
                        Object(_angular_animations__WEBPACK_IMPORTED_MODULE_4__["style"])({ transform: 'translateX(100%)', opacity: 0 }),
                        Object(_angular_animations__WEBPACK_IMPORTED_MODULE_4__["animate"])('500ms', Object(_angular_animations__WEBPACK_IMPORTED_MODULE_4__["style"])({ transform: 'translateX(0)', opacity: 1 }))
                    ]),
                    Object(_angular_animations__WEBPACK_IMPORTED_MODULE_4__["transition"])(':leave', [
                        Object(_angular_animations__WEBPACK_IMPORTED_MODULE_4__["style"])({ transform: 'translateX(0)', opacity: 1 }),
                        Object(_angular_animations__WEBPACK_IMPORTED_MODULE_4__["animate"])('500ms', Object(_angular_animations__WEBPACK_IMPORTED_MODULE_4__["style"])({ transform: 'translateX(100%)', opacity: 0 }))
                    ])
                ])
            ]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_5__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_0__["MetabolightsService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_7__["NgRedux"]])
    ], PersonComponent);
    return PersonComponent;
}());



/***/ }),

/***/ "./src/app/components/study/people/person/person.validator.ts":
/*!********************************************************************!*\
  !*** ./src/app/components/study/people/person/person.validator.ts ***!
  \********************************************************************/
/*! exports provided: ValidateRules */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ValidateRules", function() { return ValidateRules; });
function ValidateRules(field, validation) {
    return function (control) {
        var _a;
        var value = control.value;
        var invalid = false;
        var errorMessage = "";
        validation.rules.forEach(function (rule) {
            switch (rule.condition) {
                case "min":
                    {
                        if (value.toString().length < rule.value && JSON.parse(validation['is-required'])) {
                            invalid = true;
                            errorMessage = errorMessage + rule.error;
                        }
                        break;
                    }
                    ;
                case "pattern":
                    {
                        var re = new RegExp(rule.value, "i");
                        if (!re.test(value)) {
                            invalid = true;
                            errorMessage = errorMessage + rule.error;
                        }
                        break;
                    }
                    ;
            }
        });
        if (invalid) {
            return _a = {}, _a[field] = { 'error': errorMessage }, _a;
        }
        return null;
    };
}


/***/ }),

/***/ "./src/app/components/study/protocols/protocol/protocol.component.css":
/*!****************************************************************************!*\
  !*** ./src/app/components/study/protocols/protocol/protocol.component.css ***!
  \****************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".card-footer {\n    padding: 10px 20px;\n}\n\nlabel{\n\tfont-size: 0.73em;\n    color: #666;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zdHVkeS9wcm90b2NvbHMvcHJvdG9jb2wvcHJvdG9jb2wuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtJQUNJLG1CQUFtQjtDQUN0Qjs7QUFFRDtDQUNDLGtCQUFrQjtJQUNmLFlBQVk7Q0FDZiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvcHJvdG9jb2xzL3Byb3RvY29sL3Byb3RvY29sLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyIuY2FyZC1mb290ZXIge1xuICAgIHBhZGRpbmc6IDEwcHggMjBweDtcbn1cblxubGFiZWx7XG5cdGZvbnQtc2l6ZTogMC43M2VtO1xuICAgIGNvbG9yOiAjNjY2O1xufSJdfQ== */"

/***/ }),

/***/ "./src/app/components/study/protocols/protocol/protocol.component.html":
/*!*****************************************************************************!*\
  !*** ./src/app/components/study/protocols/protocol/protocol.component.html ***!
  \*****************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<span *ngIf='protocol == null || addNewProtocol; else showProtocolDetails'>\n  <a class=\"button is-primary is-pulled-right is-small\" (click)='openModal(protocol)'>+ Add New Protocol</a>\n</span>\n<br>\n<ng-template #showProtocolDetails>\n  <div class=\"card\">\n    <header class=\"card-heading hover-highlight\">\n      <span>\n        <p class=\"is-pulled-left\">\n          {{ protocol.name }}\n        </p>\n        <a class=\"button is-light is-pulled-right is-small hover-button\" (click)='openModal(protocol)'><mat-icon>edit</mat-icon> Edit Protocol</a>\n      </span>\n    </header>\n    <div class=\"card-content\">\n      <div class=\"content\">\n        <mtbls-content [count]=\"500\" [message]=\"'Protocol description not found.'\" [value]=\"protocol.description\"></mtbls-content>\n      </div>\n    </div>\n    <footer *ngIf=\"protocol.parameters.length > 0 && protocol.parameters[0].parameterName.annotationValue != ''\" class=\"card-footer\">\n      <span>\n        <div id=\"meta\" class=\"field is-grouped is-grouped-multiline\">\n          <div *ngFor=\"let parameter of protocol.parameters\">\n            <div *ngIf=\"parameter.parameterName.annotationValue != ''\" class=\"control\">\n              <div class=\"tags is-info has-addons\">\n                <span class=\"tag\">{{ parameter.parameterName.annotationValue }}</span>\n              </div>\n            </div>\n          </div>\n        </div>\n      </span>\n    </footer>\n  </div>\n  <br>\n</ng-template>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isDeleteModalOpen}\">\n  <div class=\"modal-background\"></div>\n  <div class=\"modal-card\">\n    <section class=\"modal-card-body\">\n      <p>Are you sure you want to delete?</p>\n    </section>\n    <footer class=\"modal-card-foot\">\n      <div class=\"columns is-gapless full-width\">\n        <div class=\"column is-half\">\n          <button (click)='closeDelete()' class=\"button is-info\">Cancel</button>\n        </div>\n        <div class=\"column is-half has-text-right\">\n          <button (click)='delete()' class=\"button is-danger\">OK! Delete Permanently</button>\n        </div>\n      </div>\n    </footer>\n  </div>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isModalOpen}\">\n  <form *ngIf=\"form\" [formGroup]=\"form\">\n    <div class=\"modal-background\"></div>\n    <div class=\"modal-card vw80\">\n      <div *ngIf=\"isFormBusy\" class=\"load-bar\">\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n      </div>\n      <section class=\"modal-card-body\">\n          <div class=\"field is-horizontal\">\n            <div class=\"field-body\">\n              <div class=\"field\">\n                <div class=\"control\">\n                  <mat-form-field class=\"full-width\">\n                        <textarea class=\"no-scroll\" \n                        formControlName=\"name\"\n                        [placeholder]=\"fieldValidation('name').placeholder\" \n                        matInput\n                        cdkAutosizeMinRows=\"1\"\n                        cdkAutosizeMaxRows=\"4\"\n                        cdkTextareaAutosize>\n                      </textarea>\n                      <mat-hint>{{ fieldValidation('name').description }}</mat-hint>\n                      <mat-error\n                      *ngIf=\"form.get('name').errors &&\n                      form.get('name').dirty &&\n                      form.get('name').errors.name\">\n                      {{ form.get('name').errors.name.error }}\n                    </mat-error>\n                  </mat-form-field>\n                  <div>&nbsp;</div>\n                  <span *ngIf=\"form.get('description')\">\n                  <!-- <mat-form-field class=\"full-width\"> -->\n                   <ngx-wig\n                   formControlName=\"description\"\n                   matInput\n                   [placeholder]=\"fieldValidation('description').placeholder\" \n                   >\n                   </ngx-wig>\n                    <!-- <textarea class=\"no-scroll\" \n                    formControlName=\"description\"\n                    [placeholder]=\"fieldValidation('description').placeholder\" \n                    matInput\n                    cdkAutosizeMinRows=\"12\"\n                    cdkAutosizeMaxRows=\"12\"\n                    cdkTextareaAutosize>\n                    </textarea> -->\n                    <mat-hint>{{ fieldValidation('description').description }}</mat-hint>\n                    <mat-error\n                    *ngIf=\"form.get('description').errors &&\n                    form.get('description').dirty &&\n                    form.get('description').errors.description\">\n                    {{ form.get('description').errors.description.error }}\n                    </mat-error> \n                    </span>\n                   <!-- </mat-form-field> -->\n                </div>\n              </div>\n            </div>\n          </div>\n          <span *ngIf=\"!required\">\n            <div *ngIf=\"form && form.get('parameters').value && form.get('parameters').value.length > 0 && form.get('parameters').value[0].parameterName.annotationValue != ''; else newParameter\">\n              <span>\n                <div>\n                   <label>Protocol parameters</label>\n                </div>\n                <div class=\"field is-grouped is-grouped-multiline\">\n                  <div *ngFor=\"let parameter of form.get('parameters').value\">\n                    <div *ngIf=\"parameter.parameterName.annotationValue != ''\" class=\"control\">\n                      <div class=\"tags is-info has-addons\">\n                        <span class=\"tag is-link\">{{ parameter.parameterName.annotationValue }}</span>\n                        <a (click)=\"deleteParameter(parameter)\" class=\"tag is-delete\"></a>\n                      </div>\n                    </div>\n                  </div>\n                  <div class=\"tags is-info has-addons\">\n                    <span class=\"tag\">Add parameter</span>\n                    <a (click)=\"openParameterModal()\" class=\"tag is-success\"> \n                        <mat-icon class=\"mat-icon material-icons\" role=\"img\" aria-hidden=\"true\">library_add</mat-icon>\n                    </a>\n                  </div>\n                </div>\n              </span>\n            </div>\n            <ng-template #newParameter>\n              <div class=\"tags is-info has-addons\">\n                <span class=\"tag\">Add parameter</span>\n                <a (click)=\"openParameterModal()\" class=\"tag is-success\"> <mat-icon class=\"mat-icon material-icons\" role=\"img\" aria-hidden=\"true\">library_add</mat-icon> </a>\n              </div>\n            </ng-template>\n          </span>\n      </section>\n      <footer class=\"modal-card-foot buttons is-right\">\n        <div class=\"columns is-gapless full-width\">\n          <div class=\"column is-half\">\n            <button *ngIf=\"!addNewProtocol && !required\" class=\"button is-danger is-pulled-left\" (click)=\"confirmDelete()\"><mat-icon>delete_outline</mat-icon></button>\n          </div>\n          <div class=\"column is-half has-text-right\">\n            <button *ngIf=\"!form.pristine\" [disabled]=\"!form.valid || isFormBusy\" (click)='save()' class=\"button is-info\">\n              <mat-spinner [diameter]=\"20\" [strokeWidth]=\"3\" *ngIf=\"isFormBusy\"></mat-spinner>\n              Save\n            </button>\n            <button *ngIf=\"form.pristine\" (click)='closeModal()' class=\"button is-info\">OK</button>\n            <button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeModal()'>Cancel</button>\n          </div>\n        </div>\n      </footer>\n    </div>\n  </form>\n</div>\n\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isParameterModalOpen}\">\n  <div class=\"modal-background\"></div>\n  <div class=\"modal-card\">\n    <section class=\"modal-card-body\">\n      <mtbls-ontology #parameterName [validations]=\"fieldValidation('parameterName')\" [values]='[]' [inline]=\"true\"></mtbls-ontology>\n    </section>\n    <footer class=\"modal-card-foot\">\n      <div class=\"columns is-gapless full-width\">\n        <div class=\"column is-half\">\n          <button (click)='closeParameterModal()' class=\"button is-default\">Cancel</button>\n        </div>\n        <div class=\"column is-half has-text-right\">\n          <button [disabled]=\"!(parameterName.values.length > 0)\" (click)='addParameter()' class=\"button is-success\">Add parameter</button>\n        </div>\n      </div>\n    </footer>\n  </div>\n</div>"

/***/ }),

/***/ "./src/app/components/study/protocols/protocol/protocol.component.ts":
/*!***************************************************************************!*\
  !*** ./src/app/components/study/protocols/protocol/protocol.component.ts ***!
  \***************************************************************************/
/*! exports provided: ProtocolComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProtocolComponent", function() { return ProtocolComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _models_mtbl_mtbls_mtbls_protocol__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./../../../../models/mtbl/mtbls/mtbls-protocol */ "./src/app/models/mtbl/mtbls/mtbls-protocol.ts");
/* harmony import */ var _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./../../../../models/mtbl/mtbls/common/mtbls-ontology */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_5__);
/* harmony import */ var _protocol_validator__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! ./protocol.validator */ "./src/app/components/study/protocols/protocol/protocol.validator.ts");
/* harmony import */ var _ontology_ontology_component__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./../../ontology/ontology.component */ "./src/app/components/study/ontology/ontology.component.ts");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_8___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_8__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};









var ProtocolComponent = /** @class */ (function () {
    function ProtocolComponent(fb, metabolightsService, ngRedux) {
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.ngRedux = ngRedux;
        this.required = false;
        this.isModalOpen = false;
        this.isDeleteModalOpen = false;
        this.isParameterModalOpen = false;
        this.isFormBusy = false;
        this.selectedProtocol = null;
        this.addNewProtocol = false;
        this.validationsId = 'protocols.protocol';
    }
    ProtocolComponent.prototype.ngOnInit = function () {
        if (this.protocol == null) {
            this.addNewProtocol = true;
        }
    };
    ProtocolComponent.prototype.confirmDelete = function () {
        this.isModalOpen = false;
        this.isDeleteModalOpen = true;
    };
    ProtocolComponent.prototype.closeDelete = function () {
        this.isDeleteModalOpen = false;
        this.isModalOpen = true;
    };
    ProtocolComponent.prototype.openModal = function (protocol) {
        this.initialiseForm();
        if (this.protocol.parameters.length > 0) {
            this.form.get('parameters').setValue(this.protocol.parameters);
        }
        else {
            this.form.get('parameters').setValue([]);
        }
        this.selectedProtocol = protocol;
        this.isModalOpen = true;
    };
    ProtocolComponent.prototype.closeModal = function () {
        this.isModalOpen = false;
        this.form.removeControl('description');
    };
    ProtocolComponent.prototype.openParameterModal = function () {
        this.parameterName.values = [];
        this.isParameterModalOpen = true;
    };
    ProtocolComponent.prototype.closeParameterModal = function () {
        this.isParameterModalOpen = false;
    };
    ProtocolComponent.prototype.deleteParameter = function (parameter) {
        var filteredParameters = this.form.get('parameters').value.filter(function (obj) {
            return obj.parameterName.annotationValue !== parameter.parameterName.annotationValue;
        });
        if (filteredParameters) {
            this.form.get('parameters').setValue(filteredParameters);
            this.form.markAsDirty();
        }
    };
    ProtocolComponent.prototype.addParameter = function () {
        var parameter = new _models_mtbl_mtbls_mtbls_protocol__WEBPACK_IMPORTED_MODULE_3__["ProtocolParameter"]();
        parameter.parameterName = this.parameterName.values[0];
        if (this.form.get('parameters').value.length == 1 && this.form.get('parameters').value[0].parameterName.annotationValue == '') {
            this.form.get('parameters').setValue([parameter]);
        }
        else if (this.form.get('parameters').value.length == 1 && this.form.get('parameters').value[0].parameterName.annotationValue != '') {
            this.form.get('parameters').setValue(this.form.get('parameters').value.concat(parameter));
        }
        else if (this.form.get('parameters').value.length > 1) {
            this.form.get('parameters').setValue(this.form.get('parameters').value.concat(parameter));
        }
        else {
            this.form.get('parameters').setValue([parameter]);
        }
        this.isParameterModalOpen = false;
        this.form.markAsDirty();
    };
    ProtocolComponent.prototype.initialiseForm = function () {
        this.isFormBusy = false;
        this.form = null;
        if (this.protocol == null) {
            var mtblsProtocol = new _models_mtbl_mtbls_mtbls_protocol__WEBPACK_IMPORTED_MODULE_3__["MTBLSProtocol"]();
            mtblsProtocol.parameters = [];
            this.protocol = mtblsProtocol;
        }
        this.form = this.fb.group({
            name: [{ value: this.protocol.name, disabled: this.required }, Object(_protocol_validator__WEBPACK_IMPORTED_MODULE_6__["ValidateRules"])('name', this.fieldValidation('name'))],
            parameters: [this.protocol.parameters],
            description: [this.protocol.description, Object(_protocol_validator__WEBPACK_IMPORTED_MODULE_6__["ValidateRules"])('description', this.fieldValidation('description'))]
        });
    };
    ProtocolComponent.prototype.save = function () {
        var _this = this;
        this.isFormBusy = true;
        if (!this.addNewProtocol) {
            this.metabolightsService.updateProtocol(this.protocol.name, this.compileBody()).subscribe(function (res) {
                _this.updateProtocols(res, 'Protocol updated.');
                _this.form.removeControl('description');
                _this.isModalOpen = false;
            }, function (err) {
                _this.isFormBusy = false;
            });
        }
        else {
            this.metabolightsService.saveProtocol(this.compileBody()).subscribe(function (res) {
                _this.updateProtocols(res, 'Protocol saved.');
                _this.form.removeControl('description');
                _this.isModalOpen = false;
            }, function (err) {
                _this.isFormBusy = false;
            });
        }
    };
    ProtocolComponent.prototype.delete = function () {
        var _this = this;
        if (!this.required) {
            this.metabolightsService.deleteProtocol(this.protocol.name).subscribe(function (res) {
                _this.updateProtocols(res, 'Protocol deleted.');
                _this.form.removeControl('description');
                _this.isDeleteModalOpen = false;
                _this.isModalOpen = false;
            }, function (err) {
                _this.isFormBusy = false;
            });
        }
        else {
            toastr__WEBPACK_IMPORTED_MODULE_8__["error"]("Cannot delete a default protocol", "Error", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
        }
    };
    ProtocolComponent.prototype.updateProtocols = function (data, message) {
        var _this = this;
        this.metabolightsService.getProtocols().subscribe(function (res) {
            _this.form.reset();
            _this.ngRedux.dispatch({ type: 'UPDATE_STUDY_PROTOCOLS', body: {
                    'protocols': res.protocols
                } });
        });
        this.form.markAsPristine();
        this.initialiseForm();
        this.isModalOpen = true;
        toastr__WEBPACK_IMPORTED_MODULE_8__["success"](message, "Success", {
            "timeOut": "2500",
            "positionClass": "toast-top-center",
            "preventDuplicates": true,
            "extendedTimeOut": 0,
            "tapToDismiss": false
        });
    };
    ProtocolComponent.prototype.compileBody = function () {
        var mtblProtocol = new _models_mtbl_mtbls_mtbls_protocol__WEBPACK_IMPORTED_MODULE_3__["MTBLSProtocol"]();
        mtblProtocol.name = this.getFieldValue('name');
        mtblProtocol.description = this.getFieldValue('description');
        mtblProtocol.protocolType = new _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_4__["Ontology"]();
        mtblProtocol.protocolType.annotationValue = this.getFieldValue('name');
        mtblProtocol.parameters = this.getFieldValue('parameters');
        return { "protocol": mtblProtocol.toJSON() };
    };
    Object.defineProperty(ProtocolComponent.prototype, "validation", {
        get: function () {
            if (this.validationsId.includes(".")) {
                var arr = this.validationsId.split(".");
                var tempValidations = JSON.parse(JSON.stringify(this.validations));
                ;
                while (arr.length && (tempValidations = tempValidations[arr.shift()]))
                    ;
                return tempValidations;
            }
            return this.validations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    ProtocolComponent.prototype.fieldValidation = function (fieldId) {
        return this.validation[fieldId];
    };
    ProtocolComponent.prototype.getFieldValue = function (name) {
        return this.form.get(name).value;
    };
    ProtocolComponent.prototype.setFieldValue = function (name, value) {
        return this.form.get(name).setValue(value);
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", Object)
    ], ProtocolComponent.prototype, "protocol", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('required'),
        __metadata("design:type", Boolean)
    ], ProtocolComponent.prototype, "required", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], ProtocolComponent.prototype, "validations", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])(_ontology_ontology_component__WEBPACK_IMPORTED_MODULE_7__["OntologyComponent"]),
        __metadata("design:type", _ontology_ontology_component__WEBPACK_IMPORTED_MODULE_7__["OntologyComponent"])
    ], ProtocolComponent.prototype, "parameterName", void 0);
    ProtocolComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-protocol',
            template: __webpack_require__(/*! ./protocol.component.html */ "./src/app/components/study/protocols/protocol/protocol.component.html"),
            styles: [__webpack_require__(/*! ./protocol.component.css */ "./src/app/components/study/protocols/protocol/protocol.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_1__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__["MetabolightsService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_5__["NgRedux"]])
    ], ProtocolComponent);
    return ProtocolComponent;
}());



/***/ }),

/***/ "./src/app/components/study/protocols/protocol/protocol.validator.ts":
/*!***************************************************************************!*\
  !*** ./src/app/components/study/protocols/protocol/protocol.validator.ts ***!
  \***************************************************************************/
/*! exports provided: ValidateRules */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ValidateRules", function() { return ValidateRules; });
function ValidateRules(field, validation) {
    return function (control) {
        var _a;
        var value = control.value;
        var invalid = false;
        var errorMessage = "";
        validation.rules.forEach(function (rule) {
            if (value != null) {
                switch (rule.condition) {
                    case "min":
                        {
                            if (value.toString().length < rule.value && JSON.parse(validation['is-required'])) {
                                invalid = true;
                                errorMessage = errorMessage + rule.error;
                            }
                            break;
                        }
                        ;
                    case "pattern":
                        {
                            var re = new RegExp(rule.value, "i");
                            if (!re.test(value)) {
                                invalid = true;
                                errorMessage = errorMessage + rule.error;
                            }
                            break;
                        }
                        ;
                }
            }
        });
        if (invalid) {
            return _a = {}, _a[field] = { 'error': errorMessage }, _a;
        }
        return null;
    };
}


/***/ }),

/***/ "./src/app/components/study/protocols/protocols.component.css":
/*!********************************************************************!*\
  !*** ./src/app/components/study/protocols/protocols.component.css ***!
  \********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".mb10{\n\tmargin-bottom: 10px;\n}\n\n.mt5{\n\tmargin-top: 5px;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zdHVkeS9wcm90b2NvbHMvcHJvdG9jb2xzLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7Q0FDQyxvQkFBb0I7Q0FDcEI7O0FBRUQ7Q0FDQyxnQkFBZ0I7Q0FDaEIiLCJmaWxlIjoic3JjL2FwcC9jb21wb25lbnRzL3N0dWR5L3Byb3RvY29scy9wcm90b2NvbHMuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbIi5tYjEwe1xuXHRtYXJnaW4tYm90dG9tOiAxMHB4O1xufVxuXG4ubXQ1e1xuXHRtYXJnaW4tdG9wOiA1cHg7XG59Il19 */"

/***/ }),

/***/ "./src/app/components/study/protocols/protocols.component.html":
/*!*********************************************************************!*\
  !*** ./src/app/components/study/protocols/protocols.component.html ***!
  \*********************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div>\n\t<div class=\"mtbls-section\">\n\t\t<h6 class=\"mb10\">\n\t\t\t<mtbls-protocol [value]=\"null\" [validations]=\"validations\"></mtbls-protocol>\n\t\t</h6>\n\t</div>\n</div>\n\n<div *ngFor=\"let protocol of defaultProtocols\">\n\t<span *ngIf=\"getProtocol(protocol) != null\">\n\t\t<mtbls-protocol [value]=\"getProtocol(protocol)\" [required]=\"true\" [validations]=\"validations\"></mtbls-protocol>\n\t</span>\n</div>\n\n<div *ngFor=\"let protocol of customProtocols\">\n\t<span *ngIf=\"getProtocol(protocol) != null\">\n\t\t<mtbls-protocol [value]=\"getProtocol(protocol)\" [validations]=\"validations\"></mtbls-protocol>\n\t</span>\n</div>\n"

/***/ }),

/***/ "./src/app/components/study/protocols/protocols.component.ts":
/*!*******************************************************************!*\
  !*** ./src/app/components/study/protocols/protocols.component.ts ***!
  \*******************************************************************/
/*! exports provided: ProtocolsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProtocolsComponent", function() { return ProtocolsComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var ProtocolsComponent = /** @class */ (function () {
    function ProtocolsComponent() {
        this.protocols = [];
        this.customProtocols = [];
        this.defaultProtocols = [];
        this.validationsId = 'protocols';
    }
    ProtocolsComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.studyProtocols.subscribe(function (value) {
            _this.customProtocols = [];
            _this.defaultProtocols = [];
            _this.protocols = value;
            _this.validation.default.sort(function (a, b) {
                return a['sort-order'] - b['sort-order'];
            });
            _this.defaultProtocols = _this.validation.default.map(function (protocol) { return protocol.title; });
            value.forEach(function (p) {
                if (_this.defaultProtocols.indexOf(p.name) < 0) {
                    _this.customProtocols.push(p.name);
                }
            });
        });
    };
    ProtocolsComponent.prototype.getProtocol = function (name) {
        var selectedProtocol = null;
        this.protocols.forEach(function (p) {
            if (p.name == name) {
                selectedProtocol = p;
            }
        });
        return selectedProtocol;
    };
    Object.defineProperty(ProtocolsComponent.prototype, "validation", {
        get: function () {
            return this.validations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.protocols; }),
        __metadata("design:type", Object)
    ], ProtocolsComponent.prototype, "studyProtocols", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], ProtocolsComponent.prototype, "validations", void 0);
    ProtocolsComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-protocols',
            template: __webpack_require__(/*! ./protocols.component.html */ "./src/app/components/study/protocols/protocols.component.html"),
            styles: [__webpack_require__(/*! ./protocols.component.css */ "./src/app/components/study/protocols/protocols.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], ProtocolsComponent);
    return ProtocolsComponent;
}());



/***/ }),

/***/ "./src/app/components/study/publications/publication/publication.component.css":
/*!*************************************************************************************!*\
  !*** ./src/app/components/study/publications/publication/publication.component.css ***!
  \*************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".publication-title{\n\tmin-height: 3.5em;\n\tfont-size: 1em;\n\tline-height: 1.55em;\n}\n\n.publication-authors{\n    min-height: 2.6em;\n}\n\n.custom-button{\n\tfloat: right;\n    right: 0;\n    height: 30px;\n    background-color: transparent !important;\n    border-color: transparent;\n    margin-right: -8px;\n}\n\n.box {\n    padding: 1.2rem 1.9rem 1.2rem 1.2rem;\n}\n\n.mat-icon {\n    height: 1em; \n}\n\na {\n    color: #000;\n    cursor: pointer;\n    text-decoration: none;\n}\n\na:hover {\n    color: #3273dc;\n    cursor: pointer;\n    text-decoration: none;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zdHVkeS9wdWJsaWNhdGlvbnMvcHVibGljYXRpb24vcHVibGljYXRpb24uY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtDQUNDLGtCQUFrQjtDQUNsQixlQUFlO0NBQ2Ysb0JBQW9CO0NBQ3BCOztBQUVEO0lBQ0ksa0JBQWtCO0NBQ3JCOztBQUVEO0NBQ0MsYUFBYTtJQUNWLFNBQVM7SUFDVCxhQUFhO0lBQ2IseUNBQXlDO0lBQ3pDLDBCQUEwQjtJQUMxQixtQkFBbUI7Q0FDdEI7O0FBRUQ7SUFDSSxxQ0FBcUM7Q0FDeEM7O0FBRUQ7SUFDSSxZQUFZO0NBQ2Y7O0FBRUQ7SUFDSSxZQUFZO0lBQ1osZ0JBQWdCO0lBQ2hCLHNCQUFzQjtDQUN6Qjs7QUFFRDtJQUNJLGVBQWU7SUFDZixnQkFBZ0I7SUFDaEIsc0JBQXNCO0NBQ3pCIiwiZmlsZSI6InNyYy9hcHAvY29tcG9uZW50cy9zdHVkeS9wdWJsaWNhdGlvbnMvcHVibGljYXRpb24vcHVibGljYXRpb24uY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbIi5wdWJsaWNhdGlvbi10aXRsZXtcblx0bWluLWhlaWdodDogMy41ZW07XG5cdGZvbnQtc2l6ZTogMWVtO1xuXHRsaW5lLWhlaWdodDogMS41NWVtO1xufVxuXG4ucHVibGljYXRpb24tYXV0aG9yc3tcbiAgICBtaW4taGVpZ2h0OiAyLjZlbTtcbn1cblxuLmN1c3RvbS1idXR0b257XG5cdGZsb2F0OiByaWdodDtcbiAgICByaWdodDogMDtcbiAgICBoZWlnaHQ6IDMwcHg7XG4gICAgYmFja2dyb3VuZC1jb2xvcjogdHJhbnNwYXJlbnQgIWltcG9ydGFudDtcbiAgICBib3JkZXItY29sb3I6IHRyYW5zcGFyZW50O1xuICAgIG1hcmdpbi1yaWdodDogLThweDtcbn1cblxuLmJveCB7XG4gICAgcGFkZGluZzogMS4ycmVtIDEuOXJlbSAxLjJyZW0gMS4ycmVtO1xufVxuXG4ubWF0LWljb24ge1xuICAgIGhlaWdodDogMWVtOyBcbn1cblxuYSB7XG4gICAgY29sb3I6ICMwMDA7XG4gICAgY3Vyc29yOiBwb2ludGVyO1xuICAgIHRleHQtZGVjb3JhdGlvbjogbm9uZTtcbn1cblxuYTpob3ZlciB7XG4gICAgY29sb3I6ICMzMjczZGM7XG4gICAgY3Vyc29yOiBwb2ludGVyO1xuICAgIHRleHQtZGVjb3JhdGlvbjogbm9uZTtcbn0iXX0= */"

/***/ }),

/***/ "./src/app/components/study/publications/publication/publication.component.html":
/*!**************************************************************************************!*\
  !*** ./src/app/components/study/publications/publication/publication.component.html ***!
  \**************************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<span *ngIf='publication == null || addNewPublication; else showPublicationDetails'>\n\t<a class=\"button is-light is-pulled-right is-small hover-button\" (click)='openModal()'>+ Add Publication</a>\n</span>\n<ng-template #showPublicationDetails>\n\t<a class=\"button is-small custom-button hover-button\" (click)='openModal()'>\n\t\t<mat-icon>edit</mat-icon>\n\t</a>\n\t<div>\n\t\t<article class=\"tile is-child box\">\n\t\t\t<div class=\"content\">\n\t\t\t\t<h4 class=\"publication-title\">\n\t\t\t\t\t<b>\n\t\t\t\t\t\t<a [href]=\"'//dx.doi.org/' + publication.doi\" target=\"_blank\">{{ publication.title | slice : 0 : 90 }}<span *ngIf=\"publication.title.length > 90\">...</span>\n\t\t\t\t\t\t</a>\n\t\t\t\t\t</b>\n\t\t\t\t</h4>\n\t\t\t\t<h6 class=\"publication-authors\">\n\t\t\t\t\t<small>\n\t\t\t\t\t\t<mat-icon>people</mat-icon> {{ publication.authorList | slice : 0 : 60 }}<span *ngIf=\"publication.authorList.length > 60\">...</span>\n\t\t\t\t\t</small>\n\t\t\t\t</h6>\n\t\t\t</div>\n\t\t</article>\n\t</div>\n</ng-template>\n<div class=\"modal\" [ngClass]=\"{'is-active': isDeleteModalOpen}\">\n\t<div class=\"modal-background\"></div>\n\t<div class=\"modal-card\">\n\t\t<section class=\"modal-card-body\">\n\t\t\t<p>Are you sure you want to delete?</p>\n\t\t</section>\n\t\t<footer class=\"modal-card-foot\">\n\t\t\t<div class=\"columns is-gapless full-width\">\n\t\t\t\t<div class=\"column is-half\">\n\t\t\t\t\t<button (click)='closeDelete()' class=\"button is-info\">Cancel</button>\n\t\t\t\t</div>\n\t\t\t\t<div class=\"column is-half has-text-right\">\n\t\t\t\t\t<button (click)='delete()' class=\"button is-danger\">OK! Delete Permanently</button>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</footer>\n\t</div>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isUpdateTitleModalOpen}\">\n\t<div class=\"modal-background\"></div>\n\t<div class=\"modal-card\">\n\t\t<section class=\"modal-card-body\">\n\t\t\t<p>Are you sure you want to set publication title to be the study title?</p>\n\t\t</section>\n\t\t<footer class=\"modal-card-foot\">\n\t\t\t<div class=\"columns is-gapless full-width\">\n\t\t\t\t<div class=\"column is-half\">\n\t\t\t\t\t<button (click)='closeUpdateTitleModal()' class=\"button\">Cancel</button>\n\t\t\t\t</div>\n\t\t\t\t<div class=\"column is-half has-text-right\">\n\t\t\t\t\t<button (click)='updateStudyTitle()' class=\"button is-success\">OK</button>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</footer>\n\t</div>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isUpdateAbstractModalOpen}\">\n\t<div class=\"modal-background\"></div>\n\t<div class=\"modal-card\">\n\t\t<section class=\"modal-card-body\">\n\t\t\t<p>Are you sure you want to set publication abstract to be the study abstract?</p>\n\t\t</section>\n\t\t<footer class=\"modal-card-foot\">\n\t\t\t<div class=\"columns is-gapless full-width\">\n\t\t\t\t<div class=\"column is-half\">\n\t\t\t\t\t<button (click)='closeUpdateTitleModal()' class=\"button\">Cancel</button>\n\t\t\t\t</div>\n\t\t\t\t<div class=\"column is-half has-text-right\">\n\t\t\t\t\t<button (click)='updateStudyAbstract()' class=\"button is-success\">OK</button>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</footer>\n\t</div>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isModalOpen}\">\n\t<form *ngIf=\"form\" [formGroup]=\"form\">\n\t\t<div class=\"modal-background\"></div>\n\t\t<div class=\"modal-card vw80\">\n\t\t\t<div *ngIf=\"isFormBusy\" class=\"load-bar\">\n\t\t\t\t<div class=\"bar\"></div>\n\t\t\t\t<div class=\"bar\"></div>\n\t\t\t\t<div class=\"bar\"></div>\n\t\t\t</div>\n\t\t\t<section class=\"modal-card-body\">\n\t\t\t\t<mat-form-field class=\"full-width\">\n\t\t\t\t\t<textarea class=\"no-scroll\" \n\t\t\t\t\tformControlName=\"title\"\n\t\t\t\t\tmatInput\n\t\t\t\t\t[tabIndex]=\"1\"\n\t\t\t\t\tcdkAutosizeMinRows=\"3\"\n\t\t\t\t\tcdkAutosizeMaxRows=\"3\"\n\t\t\t\t\tcdkTextareaAutosize>\n\t\t\t\t</textarea>\n\t\t\t\t<mat-hint>{{ fieldValidation('title').description }}</mat-hint>\n\t\t\t\t<mat-error\n\t\t\t\t*ngIf=\"form.get('title').errors &&\n\t\t\t\tform.get('title').dirty &&\n\t\t\t\tform.get('title').errors.title\">\n\t\t\t\t{{ form.get('title').errors.title.error }}\n\t\t\t</mat-error>\n\t\t</mat-form-field>\n\t\t<mat-form-field class=\"full-width\">\n\t\t\t<textarea class=\"no-scroll\" \n\t\t\tformControlName=\"authorList\"\n\t\t\tmatInput\n\t\t\t[tabIndex]=\"2\"\n\t\t\tcdkAutosizeMinRows=\"1\"\n\t\t\tcdkAutosizeMaxRows=\"4\"\n\t\t\tcdkTextareaAutosize>\n\t\t</textarea>\n\t\t<mat-hint>{{ fieldValidation('authorList').description }}</mat-hint>\n\t\t<mat-error\n\t\t*ngIf=\"form.get('authorList').errors &&\n\t\tform.get('authorList').dirty &&\n\t\tform.get('authorList').errors.authorList\">\n\t\t{{ form.get('authorList').authorList.title.error }}\n\t</mat-error>\n</mat-form-field>\n<mat-form-field class=\"full-width\">\n\t<input class=\"no-scroll\" \n\tformControlName=\"doi\"\n\t[tabIndex]=\"3\"\n\t(blur)=\"getArticleFromDOI()\"\n\tmatInput>\n\t<mat-hint>{{ fieldValidation('doi').description }}</mat-hint>\n\t<mat-error\n\t*ngIf=\"form.get('doi').errors &&\n\tform.get('doi').dirty &&\n\tform.get('doi').errors.doi\">\n\t{{ form.get('doi').errors.doi.error }}\n</mat-error>\n</mat-form-field>\n<mat-form-field class=\"full-width\">\n\t<input class=\"no-scroll\" \n\t[tabIndex]=\"4\"\n\tformControlName=\"pubMedID\"\n\t(blur)=\"getArticleFromPubMedID()\"\n\tmatInput>\n\t<mat-hint>{{ fieldValidation('pubMedID').description }}</mat-hint>\n\t<mat-error\n\t*ngIf=\"form.get('pubMedID').errors &&\n\tform.get('pubMedID').dirty &&\n\tform.get('pubMedID').errors.pubMedID\">\n\t{{ form.get('pubMedID').errors.pubMedID.error }}\n</mat-error>\n</mat-form-field>\n<mtbls-ontology class=\"mt-20\" #publicationStatus [validations]=\"fieldValidation('status')\" (changed)=\"onChanges($event)\" [values]='[publication.status]' [inline]=\"true\"></mtbls-ontology>\n<div class=\"control\" *ngIf=\"publicationAbstract != ''\">\n\t<br>\n\t<label><small><b>Publication abstract</b></small></label><br>\n\t<p>\n\t<small>{{ publicationAbstract }}</small>\n\t</p>\n</div>\n</section>\n<footer class=\"modal-card-foot\">\n\t<div class=\"columns is-gapless full-width\">\n\t\t<div class=\"column is-two-third\">\n\t\t\t<button *ngIf=\"!addNewPublication\" [tabIndex]=\"-1\" class=\"button is-danger is-pulled-left\" (click)=\"confirmDelete()\"><mat-icon>delete_outline</mat-icon></button>\n\t\t\t<!-- <button class=\"button is-light is-pulled-left\" (click)='showHistory()' ><mat-icon>history</mat-icon></button> -->\n\t\t\t<button *ngIf=\"!addNewPublication\" [tabIndex]=\"-1\" class=\"button is-light is-pulled-left\" (click)='confirmTitleUpdate()'><small>Update title</small></button>\n\t\t\t<button *ngIf=\"!addNewPublication\" [tabIndex]=\"-1\" class=\"button is-light is-pulled-left\" (click)='confirmAbstractUpdate()'><small>Update abstract</small></button>\n\t\t</div>\n\t\t<div class=\"column is-one-third has-text-right\">\n\t\t\t<button *ngIf=\"!form.pristine\" [disabled]=\"!form.valid || isFormBusy\" (click)='save()' class=\"button is-info\">\n\t\t\t\t<mat-spinner [diameter]=\"20\" [strokeWidth]=\"3\" *ngIf=\"isFormBusy\"></mat-spinner>\n\t\t\t\tSave\n\t\t\t</button>\n\t\t\t<button *ngIf=\"form.pristine\" (click)='closeModal()' [tabIndex]=\"6\" class=\"button is-info\">OK</button>\n\t\t\t<button class=\"button\" [disabled]=\"isFormBusy\" [tabIndex]=\"7\" (click)='closeModal()'>Cancel</button>\n\t\t</div>\n\t</div>\n</footer>\n</div>\n</form>\n</div>"

/***/ }),

/***/ "./src/app/components/study/publications/publication/publication.component.ts":
/*!************************************************************************************!*\
  !*** ./src/app/components/study/publications/publication/publication.component.ts ***!
  \************************************************************************************/
/*! exports provided: PublicationComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PublicationComponent", function() { return PublicationComponent; });
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ../../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _services_publications_doi_service__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../../../services/publications/doi.service */ "./src/app/services/publications/doi.service.ts");
/* harmony import */ var _services_publications_europePMC_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../../services/publications/europePMC.service */ "./src/app/services/publications/europePMC.service.ts");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./../../../../models/mtbl/mtbls/common/mtbls-ontology */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts");
/* harmony import */ var _models_mtbl_mtbls_mtbls_publication__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./../../../../models/mtbl/mtbls/mtbls-publication */ "./src/app/models/mtbl/mtbls/mtbls-publication.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _publication_validator__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./publication.validator */ "./src/app/components/study/publications/publication/publication.validator.ts");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_8___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_8__);
/* harmony import */ var _ontology_ontology_component__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ./../../ontology/ontology.component */ "./src/app/components/study/ontology/ontology.component.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_10___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_10__);
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_11___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_11__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};












var PublicationComponent = /** @class */ (function () {
    function PublicationComponent(fb, doiService, europePMCService, metabolightsService, ngRedux) {
        this.fb = fb;
        this.doiService = doiService;
        this.europePMCService = europePMCService;
        this.metabolightsService = metabolightsService;
        this.ngRedux = ngRedux;
        this.isFormBusy = false;
        this.addNewPublication = false;
        this.validationsId = 'publications.publication';
        this.isModalOpen = false;
        this.isTimeLineModalOpen = false;
        this.isDeleteModalOpen = false;
        this.isUpdateTitleModalOpen = false;
        this.isUpdateAbstractModalOpen = false;
        this.publicationAbstract = '';
    }
    PublicationComponent.prototype.ngOnInit = function () {
        if (this.publication == null) {
            this.addNewPublication = true;
        }
    };
    PublicationComponent.prototype.onChanges = function () {
        this.form.markAsDirty();
    };
    PublicationComponent.prototype.showHistory = function () {
        this.isModalOpen = false;
        this.isTimeLineModalOpen = true;
    };
    PublicationComponent.prototype.closeHistory = function () {
        this.isTimeLineModalOpen = false;
        this.isModalOpen = true;
    };
    PublicationComponent.prototype.openModal = function () {
        this.initialiseForm();
        this.isModalOpen = true;
        this.publicationAbstract = '';
        this.getAbstract();
    };
    PublicationComponent.prototype.confirmDelete = function () {
        this.isModalOpen = false;
        this.isDeleteModalOpen = true;
    };
    PublicationComponent.prototype.closeDelete = function () {
        this.isDeleteModalOpen = false;
        this.isModalOpen = true;
    };
    PublicationComponent.prototype.confirmTitleUpdate = function () {
        this.isModalOpen = false;
        this.isUpdateTitleModalOpen = true;
    };
    PublicationComponent.prototype.confirmAbstractUpdate = function () {
        this.isModalOpen = false;
        this.isUpdateAbstractModalOpen = true;
    };
    PublicationComponent.prototype.closeUpdateTitleModal = function () {
        this.isUpdateTitleModalOpen = false;
        this.isModalOpen = true;
    };
    PublicationComponent.prototype.closeUpdateAbstractModal = function () {
        this.isUpdateAbstractModalOpen = false;
        this.isModalOpen = true;
    };
    PublicationComponent.prototype.updateStudyTitle = function () {
        var _this = this;
        this.metabolightsService.saveTitle({ 'title': this.publication.title }).subscribe(function (res) {
            _this.ngRedux.dispatch({ type: 'SET_STUDY_TITLE', body: res });
            toastr__WEBPACK_IMPORTED_MODULE_11__["success"]('Title updated.', "Success", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
            _this.closeUpdateTitleModal();
        });
    };
    PublicationComponent.prototype.getAbstract = function () {
        var _this = this;
        var doi = this.getFieldValue('doi').replace('http://dx.doi.org/', '');
        if (doi != '') {
            this.europePMCService.getArticleInfo(doi.replace('http://dx.doi.org/', '')).subscribe(function (article) {
                if (article.doi == doi) {
                    _this.publicationAbstract = article.abstract;
                }
            });
        }
        else {
            var pubMedID = this.getFieldValue('pubMedID');
            if (pubMedID != '') {
                this.europePMCService.getArticleInfo(pubMedID).subscribe(function (article) {
                    _this.publicationAbstract = article.abstract;
                });
            }
        }
    };
    PublicationComponent.prototype.getArticleFromDOI = function () {
        var _this = this;
        this.publicationAbstract = '';
        var doi = this.getFieldValue('doi').replace('http://dx.doi.org/', '');
        this.setFieldValue('doi', doi);
        var doiURL = 'http://dx.doi.org/' + doi;
        if (doi != '') {
            this.doiService.getArticleInfo(doiURL).subscribe(function (article) {
                _this.setFieldValue('title', article.title.trim());
                _this.setFieldValue('authorList', article.authorList.trim());
                _this.statusComponent.setValue("Published");
            });
            this.europePMCService.getArticleInfo(doi.replace('http://dx.doi.org/', '')).subscribe(function (article) {
                if (article.doi == doi) {
                    _this.setFieldValue('pubMedID', article.pubMedID.trim());
                    _this.publicationAbstract = article.abstract;
                }
            });
        }
    };
    PublicationComponent.prototype.getArticleFromPubMedID = function () {
        var _this = this;
        this.publicationAbstract = '';
        var pubMedID = this.getFieldValue('pubMedID');
        if (pubMedID != '') {
            this.europePMCService.getArticleInfo(pubMedID).subscribe(function (article) {
                _this.setFieldValue('title', article.title.trim());
                _this.setFieldValue('authorList', article.authorList.trim());
                _this.setFieldValue('doi', article.doi.trim());
                _this.publicationAbstract = article.abstract;
            });
        }
    };
    PublicationComponent.prototype.initialiseForm = function () {
        this.isFormBusy = false;
        if (this.publication == null) {
            var mtblsPublication = new _models_mtbl_mtbls_mtbls_publication__WEBPACK_IMPORTED_MODULE_5__["MTBLSPublication"]();
            this.publication = mtblsPublication;
        }
        this.form = this.fb.group({
            pubMedID: [this.publication.pubMedID, Object(_publication_validator__WEBPACK_IMPORTED_MODULE_7__["ValidateRules"])('pubMedID', this.fieldValidation('pubMedID'))],
            doi: [this.publication.doi, Object(_publication_validator__WEBPACK_IMPORTED_MODULE_7__["ValidateRules"])('doi', this.fieldValidation('doi'))],
            authorList: [this.publication.authorList, Object(_publication_validator__WEBPACK_IMPORTED_MODULE_7__["ValidateRules"])('authorList', this.fieldValidation('authorList'))],
            title: [this.publication.title, Object(_publication_validator__WEBPACK_IMPORTED_MODULE_7__["ValidateRules"])('title', this.fieldValidation('title'))]
        });
    };
    PublicationComponent.prototype.updateStudyAbstract = function () {
        var _this = this;
        this.metabolightsService.saveAbstract({ 'description': this.publicationAbstract }).subscribe(function (res) {
            _this.ngRedux.dispatch({ type: 'SET_STUDY_ABSTRACT', body: res });
            toastr__WEBPACK_IMPORTED_MODULE_11__["success"]('Study abstract updated.', "Success", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
            _this.closeUpdateAbstractModal();
        });
    };
    PublicationComponent.prototype.save = function () {
        var _this = this;
        if (this.statusComponent.values[0] == undefined) {
            toastr__WEBPACK_IMPORTED_MODULE_11__["warning"]('Publication status cannot be empty', "Warning", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
        }
        else {
            this.isFormBusy = true;
            if (!this.addNewPublication) {
                this.metabolightsService.updatePublication(this.publication.title, this.compileBody()).subscribe(function (res) {
                    _this.updatePublications(res, 'Publication updated.');
                }, function (err) {
                    _this.isFormBusy = false;
                });
            }
            else {
                this.metabolightsService.savePublication(this.compileBody()).subscribe(function (res) {
                    _this.updatePublications(res, 'Publication saved.');
                    _this.isModalOpen = false;
                }, function (err) {
                    _this.isFormBusy = false;
                });
            }
        }
    };
    PublicationComponent.prototype.delete = function () {
        var _this = this;
        this.metabolightsService.deletePublication(this.publication.title).subscribe(function (res) {
            _this.updatePublications(res, 'Publication deleted.');
            _this.isDeleteModalOpen = false;
            _this.isModalOpen = false;
        }, function (err) {
            _this.isFormBusy = false;
        });
    };
    PublicationComponent.prototype.updatePublications = function (data, message) {
        var _this = this;
        this.metabolightsService.getPublications().subscribe(function (res) {
            _this.ngRedux.dispatch({ type: 'UPDATE_STUDY_PUBLICATIONS', body: {
                    'publications': res.publications
                } });
        });
        this.form.markAsPristine();
        this.initialiseForm();
        this.isModalOpen = true;
        toastr__WEBPACK_IMPORTED_MODULE_11__["success"](message, "Success", {
            "timeOut": "2500",
            "positionClass": "toast-top-center",
            "preventDuplicates": true,
            "extendedTimeOut": 0,
            "tapToDismiss": false
        });
    };
    PublicationComponent.prototype.compileBody = function () {
        var mtblPublication = new _models_mtbl_mtbls_mtbls_publication__WEBPACK_IMPORTED_MODULE_5__["MTBLSPublication"]();
        mtblPublication.title = this.getFieldValue('title');
        mtblPublication.authorList = this.getFieldValue('authorList');
        mtblPublication.doi = this.getFieldValue('doi');
        mtblPublication.pubMedID = this.getFieldValue('pubMedID');
        mtblPublication.comments = [];
        var jsonConvert = new json2typescript__WEBPACK_IMPORTED_MODULE_10__["JsonConvert"]();
        mtblPublication.status = jsonConvert.deserialize(this.statusComponent.values[0], _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_4__["Ontology"]);
        return { "publication": mtblPublication.toJSON() };
    };
    PublicationComponent.prototype.closeModal = function () {
        this.isModalOpen = false;
    };
    Object.defineProperty(PublicationComponent.prototype, "validation", {
        get: function () {
            if (this.validationsId.includes(".")) {
                var arr = this.validationsId.split(".");
                var tempValidations = JSON.parse(JSON.stringify(this.studyValidations));
                ;
                while (arr.length && (tempValidations = tempValidations[arr.shift()]))
                    ;
                return tempValidations;
            }
            return this.studyValidations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    PublicationComponent.prototype.fieldValidation = function (fieldId) {
        return this.validation[fieldId];
    };
    PublicationComponent.prototype.getFieldValue = function (name) {
        return this.form.get(name).value;
    };
    PublicationComponent.prototype.setFieldValue = function (name, value) {
        return this.form.get(name).setValue(value);
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_3__["Input"])('value'),
        __metadata("design:type", Object)
    ], PublicationComponent.prototype, "publication", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_3__["Input"])('validations'),
        __metadata("design:type", Object)
    ], PublicationComponent.prototype, "studyValidations", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_3__["ViewChild"])(_ontology_ontology_component__WEBPACK_IMPORTED_MODULE_9__["OntologyComponent"]),
        __metadata("design:type", _ontology_ontology_component__WEBPACK_IMPORTED_MODULE_9__["OntologyComponent"])
    ], PublicationComponent.prototype, "statusComponent", void 0);
    PublicationComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_3__["Component"])({
            selector: 'mtbls-publication',
            template: __webpack_require__(/*! ./publication.component.html */ "./src/app/components/study/publications/publication/publication.component.html"),
            styles: [__webpack_require__(/*! ./publication.component.css */ "./src/app/components/study/publications/publication/publication.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_6__["FormBuilder"], _services_publications_doi_service__WEBPACK_IMPORTED_MODULE_1__["DOIService"], _services_publications_europePMC_service__WEBPACK_IMPORTED_MODULE_2__["EuropePMCService"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_0__["MetabolightsService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_8__["NgRedux"]])
    ], PublicationComponent);
    return PublicationComponent;
}());



/***/ }),

/***/ "./src/app/components/study/publications/publication/publication.validator.ts":
/*!************************************************************************************!*\
  !*** ./src/app/components/study/publications/publication/publication.validator.ts ***!
  \************************************************************************************/
/*! exports provided: ValidateRules */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ValidateRules", function() { return ValidateRules; });
function ValidateRules(field, validation) {
    return function (control) {
        var _a;
        var value = control.value;
        var invalid = false;
        var errorMessage = "";
        validation.rules.forEach(function (rule) {
            switch (rule.condition) {
                case "min":
                    {
                        if (value.toString().length < rule.value && JSON.parse(validation['is-required'])) {
                            invalid = true;
                            errorMessage = errorMessage + rule.error;
                        }
                        break;
                    }
                    ;
                case "pattern":
                    {
                        var re = new RegExp(rule.value, "i");
                        if (!re.test(value)) {
                            invalid = true;
                            errorMessage = errorMessage + rule.error;
                        }
                        break;
                    }
                    ;
            }
        });
        if (invalid) {
            return _a = {}, _a[field] = { 'error': errorMessage }, _a;
        }
        return null;
    };
}


/***/ }),

/***/ "./src/app/components/study/publications/publications.component.css":
/*!**************************************************************************!*\
  !*** ./src/app/components/study/publications/publications.component.css ***!
  \**************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".mb10{\n\tmargin-bottom: 15px;\n}\n\n.mt5{\n\tmargin-top: 10px;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zdHVkeS9wdWJsaWNhdGlvbnMvcHVibGljYXRpb25zLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7Q0FDQyxvQkFBb0I7Q0FDcEI7O0FBRUQ7Q0FDQyxpQkFBaUI7Q0FDakIiLCJmaWxlIjoic3JjL2FwcC9jb21wb25lbnRzL3N0dWR5L3B1YmxpY2F0aW9ucy9wdWJsaWNhdGlvbnMuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbIi5tYjEwe1xuXHRtYXJnaW4tYm90dG9tOiAxNXB4O1xufVxuXG4ubXQ1e1xuXHRtYXJnaW4tdG9wOiAxMHB4O1xufSJdfQ== */"

/***/ }),

/***/ "./src/app/components/study/publications/publications.component.html":
/*!***************************************************************************!*\
  !*** ./src/app/components/study/publications/publications.component.html ***!
  \***************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"hover-highlight px-20\">\n    <div class=\"mtbls-section\">\n\t\t<h6 class=\"mb10\">\n\t\t\t<a><b><small class=\"has-text-grey\">PUBLICATIONS</small></b></a>\n\t\t\t<mtbls-publication [value]=\"null\" [validations]=\"studyValidations\"></mtbls-publication>\n\t\t</h6>\n\t\t<div *ngIf=\"publications && publications.length > 0\" class=\"tile is-ancestor mt1\">\n\t\t\t<div class=\"tile is-parent column is-4\" *ngFor=\"let publication of publications; let i = index\">\n\t\t\t\t<mtbls-publication [value]=\"publication\" [validations]=\"studyValidations\"></mtbls-publication>\n\t\t\t</div>\n\t\t</div>\n\t</div>\n</div>"

/***/ }),

/***/ "./src/app/components/study/publications/publications.component.ts":
/*!*************************************************************************!*\
  !*** ./src/app/components/study/publications/publications.component.ts ***!
  \*************************************************************************/
/*! exports provided: PublicationsComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "PublicationsComponent", function() { return PublicationsComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var PublicationsComponent = /** @class */ (function () {
    function PublicationsComponent(fb, metabolightsService, ngRedux) {
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.ngRedux = ngRedux;
    }
    PublicationsComponent.prototype.ngOnInit = function () { };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", Object)
    ], PublicationsComponent.prototype, "publications", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], PublicationsComponent.prototype, "studyValidations", void 0);
    PublicationsComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-publications',
            template: __webpack_require__(/*! ./publications.component.html */ "./src/app/components/study/publications/publications.component.html"),
            styles: [__webpack_require__(/*! ./publications.component.css */ "./src/app/components/study/publications/publications.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__["MetabolightsService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["NgRedux"]])
    ], PublicationsComponent);
    return PublicationsComponent;
}());



/***/ }),

/***/ "./src/app/components/study/release-date/release-date.component.css":
/*!**************************************************************************!*\
  !*** ./src/app/components/study/release-date/release-date.component.css ***!
  \**************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".mat-calendar{ \n\tzoom:\"0.5\" \n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zdHVkeS9yZWxlYXNlLWRhdGUvcmVsZWFzZS1kYXRlLmNvbXBvbmVudC5jc3MiXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IkFBQUE7Q0FDQyxVQUFVO0NBQ1YiLCJmaWxlIjoic3JjL2FwcC9jb21wb25lbnRzL3N0dWR5L3JlbGVhc2UtZGF0ZS9yZWxlYXNlLWRhdGUuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbIi5tYXQtY2FsZW5kYXJ7IFxuXHR6b29tOlwiMC41XCIgXG59Il19 */"

/***/ }),

/***/ "./src/app/components/study/release-date/release-date.component.html":
/*!***************************************************************************!*\
  !*** ./src/app/components/study/release-date/release-date.component.html ***!
  \***************************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"control\">\n\t<div class=\"tags has-addons\">\n\t\t<span class=\"tag\">Published</span>\n\t\t<span class=\"tag is-success clickable\" (click)='openModal()'>\n\t\t{{ releaseDate | date : 'MMMM d, y' }}\n\t\t</span>\n\t</div>\n</div>\n<div class=\"modal\" [ngClass]=\"{'is-active': isModalOpen}\">\n\t<div class=\"modal-background\"></div>\n\t<div class=\"modal-card\">\n\t\t<section class=\"modal-card-body\">\n\t\t\t<mat-calendar></mat-calendar>\n\t\t</section>\n\t\t<footer class=\"modal-card-foot buttons is-center\">\n\t\t\t<button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeModal()'>OK</button>\n\t\t</footer>\n\t</div>\n</div>"

/***/ }),

/***/ "./src/app/components/study/release-date/release-date.component.ts":
/*!*************************************************************************!*\
  !*** ./src/app/components/study/release-date/release-date.component.ts ***!
  \*************************************************************************/
/*! exports provided: ReleaseDateComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ReleaseDateComponent", function() { return ReleaseDateComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var ReleaseDateComponent = /** @class */ (function () {
    function ReleaseDateComponent() {
        this.isModalOpen = false;
        this.isFormBusy = false;
    }
    ReleaseDateComponent.prototype.ngOnInit = function () {
    };
    ReleaseDateComponent.prototype.openModal = function () {
        this.isModalOpen = true;
    };
    ReleaseDateComponent.prototype.closeModal = function () {
        this.isModalOpen = false;
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", String)
    ], ReleaseDateComponent.prototype, "releaseDate", void 0);
    ReleaseDateComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-release-date',
            template: __webpack_require__(/*! ./release-date.component.html */ "./src/app/components/study/release-date/release-date.component.html"),
            styles: [__webpack_require__(/*! ./release-date.component.css */ "./src/app/components/study/release-date/release-date.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], ReleaseDateComponent);
    return ReleaseDateComponent;
}());



/***/ }),

/***/ "./src/app/components/study/samples/samples.component.css":
/*!****************************************************************!*\
  !*** ./src/app/components/study/samples/samples.component.css ***!
  \****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "table {\n  -webkit-user-select: none;\n  -moz-user-select: none;\n  -o-user-select: none;\n  -ms-user-select: none;\n      user-select: none;\n}\n\ntable {\n  width: 100%;\n}\n\ntable td, table th {\n  vertical-align: middle;\n}\n\nth.mat-sort-header-sorted {\n  color: black;\n}\n\nth div{\n  display: flex;\n}\n\n.mat-sort-header-arrow{\n    margin: 0 5px 0 15px !important;\n}\n\n.column-editor-card{\n  min-width: 80vw !important;\n  min-height: 80vh;\n}\n\n.columns.is-vcentered {\n  align-items: center;\n}\n\n.columns.is-hcentered {\n  justify-content: center;\n  text-align: center;\n}\n\n.mt20{\n  margin-top: 20px;\n}\n\n.mb0{\n  margin-bottom: 0px;\n}\n\n.tab-wrapper{\n  min-height: 80vh;\n}\n\n.modal-card-title {\n  color: #979797;\n  font-size: 1.2rem;\n}\n\n.modal-card-title .highlight {\n  color: #000;\n}\n\n.options-wrapper{\n  border-left: 1px solid #f1f1f3;\n  min-height: 70vh;\n  padding-top: 10px !important;\n}\n\n.options-title{\n  border-bottom: 1px solid #f1f1f3;\n  padding: 15px !important;\n  font-weight: bold;\n  font-size: 1.1em;\n}\n\n.options{\n  padding: 15px;\n}\n\n.wrapper{\n  width: 100%;\n  overflow: auto;\n}\n\n.mat-form-field-infix {\n  border-top: none;\n}\n\ntd.mat-cell, td.mat-footer-cell, th.mat-header-cell {\n\tpadding-left: 20px;\n\tpadding-right: 12px;\n  box-shadow: 0 0 1px #dbdbdb;\n}\n\n.mat-table-sticky {\n  background-color: #fdfdfd;\n}\n\n.dropdown-divider {\n  background-color: #dbdbdb;\n  border: none;\n  display: block;\n  height: 1px;\n  margin: 0;\n}\n\n.dropdown-content {\n padding-bottom: 0rem; \n padding-top: 0rem; \n border-radius: 2px;\n}\n\na.dropdown-item {\n  padding-right: 1rem; \n}\n\n.column{\n  padding: 0;\n}\n\n.addon-tag{\n  padding-left: 0;\n  padding-right: 0;\n  border-left: 1px dotted #eaeaea;\n}\n\n.columns{\n  margin-right: 0px;\n  margin-left: 0px;\n}\n\n.row-selector{\n\twidth: 10px;\n}\n\n.table-selector{\n\twidth: 10px;\n}\n\n.selected{\n\tbackground-color: #333;\n  color: #fff;\n}\n\n.row-options-wrapper{\n\tposition: relative;\n}\n\n.row-options{\n\tposition: absolute;\n  left: -23px;\n  top: 0px;\n  height: 48px;\n}\n\n.table-icon {\n  padding-right: 10px;\n  padding-top: 12px;\n}\n\n.button{\n  margin-left: 2px;\n  margin-right: 2px;\n}\n\n.menu-bar{\n  margin-top: 10px;\n  margin-bottom: 0 !important;\n  align-items: baseline;\n}\n\n.menu-bar:first-child { \n  margin-left: -5px;\n}\n\n.menu-bar:last-child { \n  margin-right: -5px;\n}\n\n.button.is-light {\n  border-radius: 2px;\n}\n\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zdHVkeS9zYW1wbGVzL3NhbXBsZXMuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtFQUNFLDBCQUEwQjtFQUUxQix1QkFBdUI7RUFDdkIscUJBQXFCO0VBQ3JCLHNCQUFrQjtNQUFsQixrQkFBa0I7Q0FDbkI7O0FBRUQ7RUFDRSxZQUFZO0NBQ2I7O0FBRUQ7RUFDRSx1QkFBdUI7Q0FDeEI7O0FBRUQ7RUFDRSxhQUFhO0NBQ2Q7O0FBRUQ7RUFDRSxjQUFjO0NBQ2Y7O0FBRUQ7SUFDSSxnQ0FBZ0M7Q0FDbkM7O0FBRUQ7RUFDRSwyQkFBMkI7RUFDM0IsaUJBQWlCO0NBQ2xCOztBQUVEO0VBR0Usb0JBQW9CO0NBQ3JCOztBQUVEO0VBQ0Usd0JBQXdCO0VBQ3hCLG1CQUFtQjtDQUNwQjs7QUFFRDtFQUNFLGlCQUFpQjtDQUNsQjs7QUFFRDtFQUNFLG1CQUFtQjtDQUNwQjs7QUFFRDtFQUNFLGlCQUFpQjtDQUNsQjs7QUFFRDtFQUNFLGVBQWU7RUFDZixrQkFBa0I7Q0FDbkI7O0FBRUQ7RUFDRSxZQUFZO0NBQ2I7O0FBRUQ7RUFDRSwrQkFBK0I7RUFDL0IsaUJBQWlCO0VBQ2pCLDZCQUE2QjtDQUM5Qjs7QUFFRDtFQUNFLGlDQUFpQztFQUNqQyx5QkFBeUI7RUFDekIsa0JBQWtCO0VBQ2xCLGlCQUFpQjtDQUNsQjs7QUFFRDtFQUNFLGNBQWM7Q0FDZjs7QUFFRDtFQUNFLFlBQVk7RUFDWixlQUFlO0NBQ2hCOztBQUVEO0VBQ0UsaUJBQWlCO0NBQ2xCOztBQUVEO0NBQ0MsbUJBQW1CO0NBQ25CLG9CQUFvQjtFQUVuQiw0QkFBNEI7Q0FDN0I7O0FBRUQ7RUFDRSwwQkFBMEI7Q0FDM0I7O0FBRUQ7RUFDRSwwQkFBMEI7RUFDMUIsYUFBYTtFQUNiLGVBQWU7RUFDZixZQUFZO0VBQ1osVUFBVTtDQUNYOztBQUVEO0NBQ0MscUJBQXFCO0NBQ3JCLGtCQUFrQjtDQUNsQixtQkFBbUI7Q0FDbkI7O0FBRUQ7RUFDRSxvQkFBb0I7Q0FDckI7O0FBRUQ7RUFDRSxXQUFXO0NBQ1o7O0FBRUQ7RUFDRSxnQkFBZ0I7RUFDaEIsaUJBQWlCO0VBQ2pCLGdDQUFnQztDQUNqQzs7QUFFRDtFQUNFLGtCQUFrQjtFQUNsQixpQkFBaUI7Q0FDbEI7O0FBRUQ7Q0FDQyxZQUFZO0NBQ1o7O0FBRUQ7Q0FDQyxZQUFZO0NBQ1o7O0FBRUQ7Q0FDQyx1QkFBdUI7RUFDdEIsWUFBWTtDQUNiOztBQUVEO0NBQ0MsbUJBQW1CO0NBQ25COztBQUVEO0NBQ0MsbUJBQW1CO0VBQ2xCLFlBQVk7RUFDWixTQUFTO0VBQ1QsYUFBYTtDQUNkOztBQUVEO0VBQ0Usb0JBQW9CO0VBQ3BCLGtCQUFrQjtDQUNuQjs7QUFFRDtFQUNFLGlCQUFpQjtFQUNqQixrQkFBa0I7Q0FDbkI7O0FBRUQ7RUFDRSxpQkFBaUI7RUFDakIsNEJBQTRCO0VBQzVCLHNCQUFzQjtDQUN2Qjs7QUFFRDtFQUNFLGtCQUFrQjtDQUNuQjs7QUFFRDtFQUNFLG1CQUFtQjtDQUNwQjs7QUFFRDtFQUNFLG1CQUFtQjtDQUNwQiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvc2FtcGxlcy9zYW1wbGVzLmNvbXBvbmVudC5jc3MiLCJzb3VyY2VzQ29udGVudCI6WyJ0YWJsZSB7XG4gIC13ZWJraXQtdXNlci1zZWxlY3Q6IG5vbmU7XG4gIC1raHRtbC11c2VyLXNlbGVjdDogbm9uZTtcbiAgLW1vei11c2VyLXNlbGVjdDogbm9uZTtcbiAgLW8tdXNlci1zZWxlY3Q6IG5vbmU7XG4gIHVzZXItc2VsZWN0OiBub25lO1xufVxuXG50YWJsZSB7XG4gIHdpZHRoOiAxMDAlO1xufVxuXG50YWJsZSB0ZCwgdGFibGUgdGgge1xuICB2ZXJ0aWNhbC1hbGlnbjogbWlkZGxlO1xufVxuXG50aC5tYXQtc29ydC1oZWFkZXItc29ydGVkIHtcbiAgY29sb3I6IGJsYWNrO1xufVxuXG50aCBkaXZ7XG4gIGRpc3BsYXk6IGZsZXg7XG59XG5cbi5tYXQtc29ydC1oZWFkZXItYXJyb3d7XG4gICAgbWFyZ2luOiAwIDVweCAwIDE1cHggIWltcG9ydGFudDtcbn1cblxuLmNvbHVtbi1lZGl0b3ItY2FyZHtcbiAgbWluLXdpZHRoOiA4MHZ3ICFpbXBvcnRhbnQ7XG4gIG1pbi1oZWlnaHQ6IDgwdmg7XG59XG5cbi5jb2x1bW5zLmlzLXZjZW50ZXJlZCB7XG4gIC13ZWJraXQtYm94LWFsaWduOiBjZW50ZXI7XG4gIC1tcy1mbGV4LWFsaWduOiBjZW50ZXI7XG4gIGFsaWduLWl0ZW1zOiBjZW50ZXI7XG59XG5cbi5jb2x1bW5zLmlzLWhjZW50ZXJlZCB7XG4gIGp1c3RpZnktY29udGVudDogY2VudGVyO1xuICB0ZXh0LWFsaWduOiBjZW50ZXI7XG59XG5cbi5tdDIwe1xuICBtYXJnaW4tdG9wOiAyMHB4O1xufVxuXG4ubWIwe1xuICBtYXJnaW4tYm90dG9tOiAwcHg7XG59XG5cbi50YWItd3JhcHBlcntcbiAgbWluLWhlaWdodDogODB2aDtcbn1cblxuLm1vZGFsLWNhcmQtdGl0bGUge1xuICBjb2xvcjogIzk3OTc5NztcbiAgZm9udC1zaXplOiAxLjJyZW07XG59XG5cbi5tb2RhbC1jYXJkLXRpdGxlIC5oaWdobGlnaHQge1xuICBjb2xvcjogIzAwMDtcbn1cblxuLm9wdGlvbnMtd3JhcHBlcntcbiAgYm9yZGVyLWxlZnQ6IDFweCBzb2xpZCAjZjFmMWYzO1xuICBtaW4taGVpZ2h0OiA3MHZoO1xuICBwYWRkaW5nLXRvcDogMTBweCAhaW1wb3J0YW50O1xufVxuXG4ub3B0aW9ucy10aXRsZXtcbiAgYm9yZGVyLWJvdHRvbTogMXB4IHNvbGlkICNmMWYxZjM7XG4gIHBhZGRpbmc6IDE1cHggIWltcG9ydGFudDtcbiAgZm9udC13ZWlnaHQ6IGJvbGQ7XG4gIGZvbnQtc2l6ZTogMS4xZW07XG59XG5cbi5vcHRpb25ze1xuICBwYWRkaW5nOiAxNXB4O1xufVxuXG4ud3JhcHBlcntcbiAgd2lkdGg6IDEwMCU7XG4gIG92ZXJmbG93OiBhdXRvO1xufVxuXG4ubWF0LWZvcm0tZmllbGQtaW5maXgge1xuICBib3JkZXItdG9wOiBub25lO1xufVxuXG50ZC5tYXQtY2VsbCwgdGQubWF0LWZvb3Rlci1jZWxsLCB0aC5tYXQtaGVhZGVyLWNlbGwge1xuXHRwYWRkaW5nLWxlZnQ6IDIwcHg7XG5cdHBhZGRpbmctcmlnaHQ6IDEycHg7XG4gIC13ZWJraXQtYm94LXNoYWRvdzogMCAwIDFweCAjZGJkYmRiO1xuICBib3gtc2hhZG93OiAwIDAgMXB4ICNkYmRiZGI7XG59XG5cbi5tYXQtdGFibGUtc3RpY2t5IHtcbiAgYmFja2dyb3VuZC1jb2xvcjogI2ZkZmRmZDtcbn1cblxuLmRyb3Bkb3duLWRpdmlkZXIge1xuICBiYWNrZ3JvdW5kLWNvbG9yOiAjZGJkYmRiO1xuICBib3JkZXI6IG5vbmU7XG4gIGRpc3BsYXk6IGJsb2NrO1xuICBoZWlnaHQ6IDFweDtcbiAgbWFyZ2luOiAwO1xufVxuXG4uZHJvcGRvd24tY29udGVudCB7XG4gcGFkZGluZy1ib3R0b206IDByZW07IFxuIHBhZGRpbmctdG9wOiAwcmVtOyBcbiBib3JkZXItcmFkaXVzOiAycHg7XG59XG5cbmEuZHJvcGRvd24taXRlbSB7XG4gIHBhZGRpbmctcmlnaHQ6IDFyZW07IFxufVxuXG4uY29sdW1ue1xuICBwYWRkaW5nOiAwO1xufVxuXG4uYWRkb24tdGFne1xuICBwYWRkaW5nLWxlZnQ6IDA7XG4gIHBhZGRpbmctcmlnaHQ6IDA7XG4gIGJvcmRlci1sZWZ0OiAxcHggZG90dGVkICNlYWVhZWE7XG59XG5cbi5jb2x1bW5ze1xuICBtYXJnaW4tcmlnaHQ6IDBweDtcbiAgbWFyZ2luLWxlZnQ6IDBweDtcbn1cblxuLnJvdy1zZWxlY3Rvcntcblx0d2lkdGg6IDEwcHg7XG59XG5cbi50YWJsZS1zZWxlY3Rvcntcblx0d2lkdGg6IDEwcHg7XG59XG5cbi5zZWxlY3RlZHtcblx0YmFja2dyb3VuZC1jb2xvcjogIzMzMztcbiAgY29sb3I6ICNmZmY7XG59XG5cbi5yb3ctb3B0aW9ucy13cmFwcGVye1xuXHRwb3NpdGlvbjogcmVsYXRpdmU7XG59XG5cbi5yb3ctb3B0aW9uc3tcblx0cG9zaXRpb246IGFic29sdXRlO1xuICBsZWZ0OiAtMjNweDtcbiAgdG9wOiAwcHg7XG4gIGhlaWdodDogNDhweDtcbn1cblxuLnRhYmxlLWljb24ge1xuICBwYWRkaW5nLXJpZ2h0OiAxMHB4O1xuICBwYWRkaW5nLXRvcDogMTJweDtcbn1cblxuLmJ1dHRvbntcbiAgbWFyZ2luLWxlZnQ6IDJweDtcbiAgbWFyZ2luLXJpZ2h0OiAycHg7XG59XG5cbi5tZW51LWJhcntcbiAgbWFyZ2luLXRvcDogMTBweDtcbiAgbWFyZ2luLWJvdHRvbTogMCAhaW1wb3J0YW50O1xuICBhbGlnbi1pdGVtczogYmFzZWxpbmU7XG59XG5cbi5tZW51LWJhcjpmaXJzdC1jaGlsZCB7IFxuICBtYXJnaW4tbGVmdDogLTVweDtcbn1cblxuLm1lbnUtYmFyOmxhc3QtY2hpbGQgeyBcbiAgbWFyZ2luLXJpZ2h0OiAtNXB4O1xufVxuXG4uYnV0dG9uLmlzLWxpZ2h0IHtcbiAgYm9yZGVyLXJhZGl1czogMnB4O1xufVxuIl19 */"

/***/ }),

/***/ "./src/app/components/study/samples/samples.component.html":
/*!*****************************************************************!*\
  !*** ./src/app/components/study/samples/samples.component.html ***!
  \*****************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"card\" *ngIf=\"samplesTable\">\n\t<header class=\"card-header\">\n\t\t<p class=\"card-header-title\">\n\t\t\tSamples data\n\t\t</p>\n\t</header>\n\t<div class=\"card-content\">\n\t\t<div class=\"columns mb0 is-vcentered is-hcentered\">\n\t\t\t<div class=\"column is-12 is-paddingless\">\n\t\t\t\t<mat-form-field class=\"full-width bback\">\n\t\t\t\t\t<input autocomplete=\"off\" matInput (keyup)=\"applyFilter($event.target.value)\" (keydown)=\"onKeydown($event, $event.target.value)\" placeholder=\"Filter\">\n\t\t\t\t</mat-form-field>\n\t\t\t</div>\n\t\t</div>\n\t\t<span>\n\t\t\t<div *ngIf=\"filters.length > 0\" class=\"field is-grouped is-grouped-multiline\">\n\t\t\t\t<div *ngFor=\"let filter of filters\" class=\"control\">\n\t\t\t\t\t<div class=\"tags has-addons\">\n\t\t\t\t\t\t<span class=\"tag is-link\">{{ filter }}</span>\n\t\t\t\t\t\t<a (click)=\"highlightFilteredRows(filter)\" class=\"tag addon-tag\"><mat-icon>control_camera</mat-icon></a>\n\t\t\t\t\t\t<a (click)=\"removeFilter(filter)\" class=\"tag is-delete\"></a>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t</div>\n\t\t</span>\n\t\t<div class=\"menu-bar columns\">  \n\t\t\t<span class=\"column fadeIn\">\n\t\t\t\t<mtbls-upload></mtbls-upload>&nbsp;\n\t\t\t\t<span *ngIf=\"tableData['file']\">\n          <mtbls-download [value]=\"tableData['file']\"></mtbls-download>    \n        </span>\n\t\t\t\t<span *ngIf=\"selectedRows.length > 0\">\n<!-- \t\t\t\t\t<div class=\"dropdown is-hoverable\">\n\t\t\t\t\t\t<div class=\"dropdown-trigger\">\n\t\t\t\t\t\t\t<button class=\"button is-light is-small\" aria-haspopup=\"true\" aria-controls=\"dropdown-menu\"><mat-icon>file_copy</mat-icon>Copy</button>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t\t<div class=\"dropdown-menu\" id=\"dropdown-menu\" role=\"menu\">\n\t\t\t\t\t\t\t<div class=\"dropdown-content\">\n\t\t\t\t\t\t\t\t<a href=\"#\" class=\"dropdown-item\">\n\t\t\t\t\t\t\t\t\tPaste inplace\n\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t<hr class=\"dropdown-divider\">\n\t\t\t\t\t\t\t\t<a href=\"#\" class=\"dropdown-item\">\n\t\t\t\t\t\t\t\t\tPaste at the end\n\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t</div>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div> -->\n\t\t\t\t\t<a (click)=\"openDeleteModal()\" class=\"button is-small is-light\">\n\t\t\t\t\t\t<mat-icon>delete</mat-icon> Delete\n\t\t\t\t\t</a>\n\t\t\t\t</span>\n\t\t\t</span>\n\t\t\t<span class=\"column fadeIn has-text-right\">\n\t\t\t\t<div class=\"dropdown\" [ngClass]=\"{'is-active': isFactorDropdownActive}\">\n\t\t\t\t\t<div class=\"dropdown-trigger\">\n\t\t\t\t\t\t<button (click)=\"toggleDropdown()\" class=\"button is-light is-small\"><span><mat-icon>add</mat-icon>Factor</span></button>\n\t\t\t\t\t</div>\n\t\t\t\t\t<div class=\"dropdown-menu has-text-left\" role=\"menu\">\n\t\t\t\t\t\t<div class=\"dropdown-content\">\n\t\t\t\t\t\t\t<span *ngIf=\"unSelectedFactors && unSelectedFactors.length > 0\">\n\t\t\t\t\t\t\t\t<span *ngFor=\"let factor of unSelectedFactors\">\n\t\t\t\t\t\t\t\t\t<a (click)=\"openAddColumnModal('factor', factor)\" class=\"dropdown-item\">\n\t\t\t\t\t\t\t\t\t\t{{ factor.factorName }} \n\t\t\t\t\t\t\t\t\t</a>\n\t\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t\t<hr class=\"dropdown-divider\">\n\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t\t<mtbls-factor [isDropdown]=\"true\" [value]=\"null\" [validations]=\"validations\"></mtbls-factor>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</div>\n\t\t\t\t</div>\n\t\t\t\t<a (click)=\"openAddColumnModal('characterstic', null)\" class=\"button is-small is-light\">\n\t\t\t\t\t<span><mat-icon>add</mat-icon>Characterstic</span>\n\t\t\t\t</a>\n\t\t\t\t<a (click)=\"openAddSamplesModal()\" class=\"button is-small is-light\">\n\t\t\t\t\t<mat-icon>view_day</mat-icon> Add Samples\n\t\t\t\t</a>\n\t\t\t    <!-- <a class=\"button is-small is-light\">\n\t\t\t      <mat-icon>sort</mat-icon> Sort columns\n\t\t\t  </a> -->\n\t\t\t</span>\n\t\t</div>\n\t\t<div class=\"menu-bar columns\">\n\t\t\t<span class=\"column fadeIn has-text-right nmrl\">\n\t\t\t\t<mat-paginator [pageSizeOptions]=\"[500, 1000, 2000]\" showFirstLastButtons></mat-paginator>\n\t\t\t</span>\n\t\t</div>\n\t\t<div class=\"wrapper mat-elevation-z1\">\n\t\t\t<table class=\"mat-elevation-z1\" [dataSource]=\"tableDataSource\" matSort mat-table>\n\t\t\t\t<ng-container matColumnDef=\"Select\" sticky>\n\t\t\t\t\t<th (click)=\"deSelect()\" class=\"clickable table-selector\" mat-header-cell *matHeaderCellDef></th>\n\t\t\t\t\t<td (click)=\"rowClick(row, $event)\" class=\"clickable row-selector hover-highlight\" mat-cell *matCellDef=\"let row\">\n\t\t\t\t\t\t<span class=\"row-options-wrapper\">\n\t\t\t\t\t\t\t<span class=\"row-options hover-button\">\n\t\t\t\t\t\t\t\t<mat-icon class=\"table-icon\">aspect_ratio</mat-icon>\n\t\t\t\t\t\t\t</span>\n\t\t\t\t\t\t</span>\n\t\t\t\t\t</td>\n\t\t\t\t</ng-container>\n\t\t\t\t<ng-container *ngFor=\"let column of tableData.columns\" [sticky]=\"column.sticky\" [matColumnDef]=\"column.columnDef\">\n\t\t\t\t\t<th class=\"clickable hover-highlight\" mat-header-cell *matHeaderCellDef> \n\t\t\t\t\t\t<div>\n\t\t\t\t\t\t\t<span (click)=\"headerClick(column, $event)\">{{ formatHeader(column.header) }}</span>\n\t\t\t\t\t\t\t<span mat-sort-header>&nbsp;</span>\n\t\t\t\t\t\t</div>\n\t\t\t\t\t</th>\n\t\t\t\t\t<td (dblclick)=\"editCell(row, column, $event)\" (click)=\"cellClick(row, column, $event)\" class=\"clickable\" [ngClass]=\"{'selected': isSelected(row, column)}\" mat-cell *matCellDef=\"let row\"> \n\t\t\t\t\t\t{{ row[column.header] }} \n\t\t\t\t\t</td>\n\t\t\t\t</ng-container>\n\t\t\t\t<tr mat-header-row *matHeaderRowDef=\"tableData.displayedColumns\"></tr>\n\t\t\t\t<tr mat-row *matRowDef=\"let row; columns: tableData.displayedColumns;\"></tr>\n\t\t\t</table>\n\t\t</div>\n\t</div>\n</div>\n\n\n\n<div class=\"modal\" [ngClass]=\"{'is-active': addColumnModalOpen}\">\n  <div class=\"modal-background\"></div>\n  <div *ngIf=\"addColumnType && addColumnType == 'characterstic'\" class=\"modal-card\">\n    <div *ngIf=\"isFormBusy\" class=\"load-bar\">\n      <div class=\"bar\"></div>\n      <div class=\"bar\"></div>\n      <div class=\"bar\"></div>\n    </div>\n    <form *ngIf=\"form\" [formGroup]=\"form\">\n      <header class=\"modal-card-head\">\n        <p class=\"modal-card-title\">Add characterstic column</p>\n      </header>\n      <section class=\"modal-card-body\">\n        <div>\n          <mtbls-ontology [id]=\"'charactersticCategory'\" [validations]=\"fieldValidation('category')\" (changed)=\"onChanges($event)\" [values]='[]' [inline]=\"true\"></mtbls-ontology>\n        </div>\n        <!-- <br>\n        <div>\n          <mtbls-ontology [id]=\"'charactersticUnit'\" [validations]=\"fieldValidation('unit')\" [values]='[]' [inline]=\"true\"></mtbls-ontology>\n        </div> -->\n      </section>\n      <footer class=\"modal-card-foot\">\n        <div class=\"columns is-gapless full-width\">\n          <div class=\"column is-one-third\">\n          </div>\n          <div class=\"column is-two-third has-text-right\">\n            <button (click)='closeAddColumnModal()' class=\"button\">Cancel</button>\n            <button (click)=\"addColumn('characterstic')\" class=\"button is-info\">Add Characterstic Column</button>\n          </div>\n        </div>\n      </footer>\n    </form>\n  </div>\n  <div *ngIf=\"addColumnType && addColumnType == 'factor'\" class=\"modal-card\">\n    <div *ngIf=\"isFormBusy\" class=\"load-bar\">\n      <div class=\"bar\"></div>\n      <div class=\"bar\"></div>\n      <div class=\"bar\"></div>\n    </div>\n    <form *ngIf=\"form\" [formGroup]=\"form\">\n      <header class=\"modal-card-head\">\n        <h6 class=\"modal-card-title\">Add factor column - <span class=\"highlight\">{{ selectedFactor.factorName }}</span></h6>\n      </header>\n      <section class=\"modal-card-body\">\n        <mtbls-ontology [id]=\"'factorUnit'\" class=\"mt-20\" #factorUnit [validations]=\"fieldValidation('unit')\" (changed)=\"onChanges($event)\" [values]='[]' [inline]=\"true\"></mtbls-ontology>\n      </section>\n      <footer class=\"modal-card-foot\">\n        <div class=\"columns is-gapless full-width\">\n          <div class=\"column is-half\">\n          </div>\n          <div class=\"column is-half has-text-right\">\n            <button (click)='closeAddColumnModal()' class=\"button\">Cancel</button>\n            <button (click)=\"addColumn('factor')\" class=\"button is-info\">Add Factor Column</button>\n          </div>\n        </div>\n      </footer>\n    </form>\n  </div>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isDeleteModalOpen}\">\n  <div class=\"modal-background\"></div>\n  <div class=\"modal-card\">\n    <section class=\"modal-card-body\">\n      <p>Are you sure you want to delete the selected samples?</p>\n    </section>\n    <footer class=\"modal-card-foot\">\n      <div class=\"columns is-gapless full-width\">\n        <div class=\"column is-half\">\n          <button (click)='closeDelete()' class=\"button is-info\">Cancel</button>\n        </div>\n        <div class=\"column is-half has-text-right\">\n          <button (click)='deleteSamples()' class=\"button is-danger\">OK! Delete Permanently</button>\n        </div>\n      </div>\n    </footer>\n  </div>\n</div>\n\n<!-- <div class=\"modal\" [ngClass]=\"{'is-active': isEditColumnModalOpen}\">\n  <form *ngIf=\"form\" [formGroup]=\"form\">\n    <div class=\"modal-background\"></div>\n    <div class=\"modal-card column-editor-card\">\n      <div *ngIf=\"isFormBusy\" class=\"load-bar\">\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n      </div>\n      <section class=\"modal-card-body\">\n        <div class=\"columns\">\n          <div class=\"column is-two-thirds\">is-two-thirds</div>\n          <div class=\"column options-wrapper\">\n            <h5 class=\"options-title\">{{ selectedColumn.header }}</h5>\n            <div class=\"options\">\n              <span *ngFor=\"let key of keys(selectedColumnValues)\">\n                <div class=\"tags has-addons\">\n                  <span class=\"tag is-success\">{{ key }}</span>\n                  <a class=\"tag is-delete\"></a>\n                </div>\n              </span>\n            </div>\n          </div>\n        </div>\n      </section>\n      <footer class=\"modal-card-foot buttons is-right\">\n        <button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeEditColumnModal()'>Cancel</button>\n      </footer>\n    </div>\n  </form>\n</div> -->\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isAddSamplesModalOpen}\">\n  <form *ngIf=\"form\" [formGroup]=\"form\">\n    <div class=\"modal-background\"></div>\n    <div class=\"modal-card\">\n      <div *ngIf=\"isFormBusy\" class=\"load-bar\">\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n      </div>\n      <section class=\"modal-card-body\">\n        <div class=\"field is-horizontal\">\n          <div class=\"field-body\">\n            <div class=\"field\">\n              <div class=\"control\">\n                <mat-form-field class=\"full-width\">\n                  <mat-label>{{ validation['samples'].label }}</mat-label>\n                  <textarea class=\"no-scroll\" \n                  formControlName=\"samples\"\n                  matInput\n                  (blur)=\"formatSampleNames()\"\n                  [placeholder]=\"validation['samples'].placeholder\" \n                  cdkAutosizeMinRows=\"20\"\n                  cdkAutosizeMaxRows=\"10\"\n                  cdkTextareaAutosize>\n                </textarea>\n                <mat-hint>{{ validation.samples.description }}</mat-hint>\n                <mat-error\n                *ngIf=\"form.get('samples').errors &&\n                form.get('samples').dirty &&\n                form.get('samples').errors.samples\">\n                {{ form.get('samples').errors.samples.error }}\n              </mat-error>\n            </mat-form-field>\n          </div>\n        </div>\n      </div>\n    </div>\n  </section>\n  <footer class=\"modal-card-foot buttons is-right\">\n    <button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeAddSamplesModal()'>Cancel</button>\n    <button (click)=\"addSamples()\" class=\"button is-info\">Add</button>\n  </footer>\n</div>\n</form>\n</div>\n\n<div class=\"modal\" [ngClass]=\"{'is-active': isAddSamplesModalOpen}\">\n  <form *ngIf=\"form\" [formGroup]=\"form\">\n    <div class=\"modal-background\"></div>\n    <div class=\"modal-card\">\n      <div *ngIf=\"isFormBusy\" class=\"load-bar\">\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n      </div>\n      <section class=\"modal-card-body\">\n        <div class=\"field is-horizontal\">\n          <div class=\"field-body\">\n            <div class=\"field\">\n              <div class=\"control\">\n                <mat-form-field class=\"full-width\">\n                  <mat-label>{{ validation['samples'].label }}</mat-label>\n                  <textarea class=\"no-scroll\" \n                  formControlName=\"samples\"\n                  matInput\n                  (blur)=\"formatSampleNames()\"\n                  [placeholder]=\"validation['samples'].placeholder\" \n                  cdkAutosizeMinRows=\"20\"\n                  cdkAutosizeMaxRows=\"10\"\n                  cdkTextareaAutosize>\n                </textarea>\n                <mat-hint>{{ validation.samples.description }}</mat-hint>\n                <mat-error\n                *ngIf=\"form.get('samples').errors &&\n                form.get('samples').dirty &&\n                form.get('samples').errors.samples\">\n                {{ form.get('samples').errors.samples.error }}\n              </mat-error>\n            </mat-form-field>\n          </div>\n        </div>\n      </div>\n    </div>\n  </section>\n  <footer class=\"modal-card-foot buttons is-right\">\n    <button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeAddSamplesModal()'>Cancel</button>\n    <button (click)=\"addSamples()\" class=\"button is-info\">Add</button>\n  </footer>\n</div>\n</form>\n</div>"

/***/ }),

/***/ "./src/app/components/study/samples/samples.component.ts":
/*!***************************************************************!*\
  !*** ./src/app/components/study/samples/samples.component.ts ***!
  \***************************************************************/
/*! exports provided: SamplesComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "SamplesComponent", function() { return SamplesComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var tassign__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! tassign */ "./node_modules/tassign/lib/index.js");
/* harmony import */ var tassign__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(tassign__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _models_mtbl_mtbls_common_mtbls_column__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./../../../models/mtbl/mtbls/common/mtbls-column */ "./src/app/models/mtbl/mtbls/common/mtbls-column.ts");
/* harmony import */ var _models_mtbl_mtbls_mtbls_factor_value__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./../../../models/mtbl/mtbls/mtbls-factor-value */ "./src/app/models/mtbl/mtbls/mtbls-factor-value.ts");
/* harmony import */ var _models_mtbl_mtbls_mtbls_characterstic__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./../../../models/mtbl/mtbls/mtbls-characterstic */ "./src/app/models/mtbl/mtbls/mtbls-characterstic.ts");
/* harmony import */ var _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ./../../../models/mtbl/mtbls/common/mtbls-ontology */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_7___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_7__);
/* harmony import */ var _ontology_ontology_component__WEBPACK_IMPORTED_MODULE_8__ = __webpack_require__(/*! ./../ontology/ontology.component */ "./src/app/components/study/ontology/ontology.component.ts");
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_9__ = __webpack_require__(/*! ../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _angular_material__WEBPACK_IMPORTED_MODULE_10__ = __webpack_require__(/*! @angular/material */ "./node_modules/@angular/material/esm5/material.es5.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_11__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_11___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_11__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};












var SamplesComponent = /** @class */ (function () {
    function SamplesComponent(fb, metabolightsService, ngRedux) {
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.ngRedux = ngRedux;
        this.factors = [];
        this.tableData = {};
        this.selectedRows = [];
        this.selectedColumns = [];
        this.selectedCells = [];
        this.filter = '';
        this.filters = [];
        this.lastRowSelection = null;
        this.lastColSelection = null;
        this.selectedColumn = null;
        this.selectedColumnValues = null;
        this.selectedCell = {};
        this.fileColumns = [];
        this.files = [];
        this.isDeleteModalOpen = false;
        this.isEditModalOpen = false;
        this.isCellTypeFile = false;
        this.addColumnModalOpen = false;
        this.addColumnType = null;
        this.selectedFactor = null;
        this.isFactorDropdownActive = false;
        this.isAddSamplesModalOpen = false;
        this.validationsId = 'samples';
    }
    SamplesComponent.prototype.ngOnInit = function () {
        var _this = this;
        this.samplesTable.subscribe(function (value) {
            if (value['sampleData']) {
                _this.tableDataSource = new _angular_material__WEBPACK_IMPORTED_MODULE_10__["MatTableDataSource"](value['sampleData'].rows);
                _this.tableDataSource.sort = _this.sort;
                _this.tableData = value;
                _this.tableDataSource.filterPredicate = (function (data, filter) {
                    return _this.getDataString(data).indexOf(filter.toLowerCase()) > -1;
                });
            }
        });
        this.studyFactors.subscribe(function (value) {
            _this.factors = value;
        });
        this.validations.samples.default_order.forEach(function (col) {
            if (col.dataType == 'file') {
                _this.fileColumns.push(col.header);
            }
        });
    };
    Object.defineProperty(SamplesComponent.prototype, "unSelectedFactors", {
        get: function () {
            var uniqueSelectedFactors = [];
            var unselectedFactors = [];
            if (this.tableData['sampleHeader']) {
                this.keys(this.tableData['sampleHeader']).forEach(function (header) {
                    if (header.indexOf('Factor Value') > -1) {
                        var factorName = header.replace("Factor Value", "").replace("[", "").replace("]", "").replace(/^[ ]+|[ ]+$/g, '');
                        if (uniqueSelectedFactors.indexOf(factorName) < 0) {
                            uniqueSelectedFactors.push(factorName);
                        }
                    }
                });
            }
            this.factors.forEach(function (f) {
                if (uniqueSelectedFactors.indexOf(f.factorName) < 0) {
                    unselectedFactors.push(f);
                }
            });
            return unselectedFactors;
        },
        enumerable: true,
        configurable: true
    });
    SamplesComponent.prototype.openAddSamplesModal = function () {
        this.isAddSamplesModalOpen = true;
        this.form = this.fb.group({
            samples: []
        });
    };
    SamplesComponent.prototype.formatSampleNames = function () {
        if (this.form.get('samples').value == null)
            this.form.get('samples').setValue('');
        this.form.get('samples').setValue(this.form.get('samples').value.replace(/,/g, "\n").split("\n").filter(function (n) { return (n != undefined && n != ''); }).map(function (v) { return v.replace(/^[ ]+|[ ]+$/g, ''); }).join("\n"));
    };
    SamplesComponent.prototype.closeAddSamplesModal = function () {
        this.isAddSamplesModalOpen = false;
    };
    SamplesComponent.prototype.openAddColumnModal = function (type, selection) {
        if (type == 'factor') {
            this.selectedFactor = selection;
        }
        if (type == 'characterstic') {
        }
        this.addColumnModalOpen = true;
        this.addColumnType = type;
        this.form = this.fb.group({
            title: [''],
            samples: []
        });
    };
    SamplesComponent.prototype.onKeydown = function (event, filterValue) {
        var _this = this;
        var data = [];
        if (event.key === "Enter") {
            if (this.filters.indexOf(filterValue) < 0) {
                this.filters.push(filterValue);
                event.target.value = '';
            }
            this.tableDataSource.filter = '';
            this.filters.forEach(function (f) {
                data = data.concat(_this.tableDataSource.data.filter(function (d) { return _this.getDataString(d).indexOf(f.toLowerCase()) > -1; }));
            });
            this.tableDataSource.data = data;
        }
    };
    SamplesComponent.prototype.applyFilter = function (filterValue) {
        if (filterValue != '') {
            this.tableDataSource.data = this.tableData['sampleData'].rows;
            this.tableDataSource.filter = filterValue.trim().toLowerCase();
        }
    };
    SamplesComponent.prototype.formatHeader = function (term) {
        return term.replace(/\.[^/.]+$/, "");
    };
    SamplesComponent.prototype.isSelected = function (row, column) {
        if ((row && column) && this.selectedCells.length > 0) {
            return this.selectedCells.filter(function (cell) { return (cell[0] == column.columnDef && cell[1] == row.index); }).length > 0;
        }
        else if (this.selectedColumns.length == 0) {
            if (this.selectedRows.indexOf(row.index) > -1) {
                return true;
            }
        }
        else if (this.selectedRows.length == 0) {
            if (this.selectedColumns.indexOf(column.columnDef) > -1) {
                return true;
            }
        }
        return false;
    };
    SamplesComponent.prototype.closeAddColumnModal = function () {
        this.addColumnModalOpen = false;
    };
    SamplesComponent.prototype.rowClick = function (row, event) {
        this.selectedCells = [];
        this.selectedColumns = [];
        var entryIndex = row.index;
        var rowIndex = this.selectedRows.indexOf(entryIndex);
        if (event && event.altKey) {
            if (rowIndex > -1) {
                this.selectedRows.splice(rowIndex, 1);
            }
            else {
                this.selectedRows.push(entryIndex);
            }
        }
        else if (event && event.shiftKey) {
            var lastSelectionIndex = null;
            var lastRowIndex = -1;
            var rowNamesArray = this.samplesTable.samplesData.rows.map(function (e) { return e.index; });
            if (this.lastRowSelection) {
                lastSelectionIndex = this.lastRowSelection.index;
                lastRowIndex = rowNamesArray.indexOf(lastSelectionIndex);
            }
            else {
                lastRowIndex = 0;
            }
            var currentRowIndex = rowNamesArray.indexOf(entryIndex);
            var currentSelection = [];
            if (lastRowIndex > currentRowIndex) {
                currentSelection = rowNamesArray.slice(currentRowIndex, lastRowIndex + 1);
            }
            else {
                currentSelection = rowNamesArray.slice(lastRowIndex, currentRowIndex + 1);
            }
            this.selectedRows = this.selectedRows.concat(currentSelection);
        }
        else {
            if (rowIndex < 0) {
                this.selectedRows = [entryIndex];
            }
            else {
                this.selectedRows = [];
            }
        }
        this.lastRowSelection = row;
    };
    SamplesComponent.prototype.onChanges = function () { };
    SamplesComponent.prototype.addColumn = function (type) {
        if (type == 'factor') {
            var mtblsFactorValue = new _models_mtbl_mtbls_mtbls_factor_value__WEBPACK_IMPORTED_MODULE_3__["MTBLSFactorValue"]();
            mtblsFactorValue.category = this.selectedFactor;
            var columns = [];
            var newFactorIndex = this.keys(this.tableData.sampleHeader).length;
            var factorValueColumn = new _models_mtbl_mtbls_common_mtbls_column__WEBPACK_IMPORTED_MODULE_2__["MTBLSColumn"]("Factor Value[" + mtblsFactorValue.category.factorName + "]", '', newFactorIndex);
            var factorUnitValue = this.getOntologyComponentValue('factorUnit').values[0];
            var factorUnitColumn = void 0;
            var factorSourceColumn = void 0;
            var factorAccessionColumn = void 0;
            if (factorUnitValue && factorUnitValue != null && factorUnitValue.annotationValue != '') {
                factorUnitColumn = new _models_mtbl_mtbls_common_mtbls_column__WEBPACK_IMPORTED_MODULE_2__["MTBLSColumn"]("Unit", '', newFactorIndex + 1);
                factorSourceColumn = new _models_mtbl_mtbls_common_mtbls_column__WEBPACK_IMPORTED_MODULE_2__["MTBLSColumn"]("Term Source REF", '', newFactorIndex + 2);
                factorAccessionColumn = new _models_mtbl_mtbls_common_mtbls_column__WEBPACK_IMPORTED_MODULE_2__["MTBLSColumn"]("Term Accession Number", '', newFactorIndex + 3);
            }
            else {
                factorSourceColumn = new _models_mtbl_mtbls_common_mtbls_column__WEBPACK_IMPORTED_MODULE_2__["MTBLSColumn"]("Term Source REF", '', newFactorIndex + 1);
                factorAccessionColumn = new _models_mtbl_mtbls_common_mtbls_column__WEBPACK_IMPORTED_MODULE_2__["MTBLSColumn"]("Term Accession Number", '', newFactorIndex + 2);
            }
            columns.push(factorValueColumn.toJSON());
            if (factorUnitColumn != null) {
                columns.push(factorUnitColumn.toJSON());
            }
            columns.push(factorSourceColumn.toJSON());
            columns.push(factorAccessionColumn.toJSON());
            this.metabolightsService.addColumns(this.tableData.file, { "data": columns }).subscribe(function (res) {
                toastr__WEBPACK_IMPORTED_MODULE_11__["success"]("Characterstic added successfully", "Success", {
                    "timeOut": "2500",
                    "positionClass": "toast-top-center",
                    "preventDuplicates": true,
                    "extendedTimeOut": 0,
                    "tapToDismiss": false
                });
            }, function (err) {
                console.log(err);
            });
            this.toggleDropdown();
        }
        else {
            var mtblsCharacterstic = new _models_mtbl_mtbls_mtbls_characterstic__WEBPACK_IMPORTED_MODULE_4__["MTBLSCharacterstic"]();
            mtblsCharacterstic.category = this.getOntologyComponentValue('charactersticCategory').values[0];
            mtblsCharacterstic.value = new _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_5__["Ontology"]();
            var charactersticsCount_1 = 0;
            this.keys(this.tableData.sampleHeader).forEach(function (key) {
                if (key.indexOf("Characterstics") > -1) {
                    charactersticsCount_1 = charactersticsCount_1 + 1;
                }
            });
            var protocolRefIndex = this.tableData.sampleHeader['Protocol REF'];
            var columns = [];
            var charactersticsColumn = new _models_mtbl_mtbls_common_mtbls_column__WEBPACK_IMPORTED_MODULE_2__["MTBLSColumn"]("Characteristics[" + mtblsCharacterstic.category.annotationValue + "]", '', protocolRefIndex);
            var charactersticsSourceColumn = new _models_mtbl_mtbls_common_mtbls_column__WEBPACK_IMPORTED_MODULE_2__["MTBLSColumn"]("Term Source REF", '', protocolRefIndex + 1);
            var charactersticsAccessionColumn = new _models_mtbl_mtbls_common_mtbls_column__WEBPACK_IMPORTED_MODULE_2__["MTBLSColumn"]("Term Accession Number", '', protocolRefIndex + 2);
            columns.push(charactersticsColumn.toJSON());
            columns.push(charactersticsSourceColumn.toJSON());
            columns.push(charactersticsAccessionColumn.toJSON());
            this.metabolightsService.addColumns(this.tableData.file, { "data": columns }).subscribe(function (res) {
                toastr__WEBPACK_IMPORTED_MODULE_11__["success"]("Characterstic added successfully", "Success", {
                    "timeOut": "2500",
                    "positionClass": "toast-top-center",
                    "preventDuplicates": true,
                    "extendedTimeOut": 0,
                    "tapToDismiss": false
                });
            }, function (err) {
                console.log(err);
            });
        }
        this.closeAddColumnModal();
    };
    SamplesComponent.prototype.getOntologyComponentValue = function (id) {
        return this.ontologyComponents.filter(function (component) {
            return component.id === id;
        })[0];
    };
    SamplesComponent.prototype.toggleDropdown = function () {
        this.isFactorDropdownActive = !this.isFactorDropdownActive;
    };
    SamplesComponent.prototype.cellClick = function (row, column, event) {
        if (event.altKey) {
            this.selectedCells.push([column.columnDef, row.index]);
        }
        else {
            this.selectedCells = [[column.columnDef, row.index]];
        }
    };
    SamplesComponent.prototype.toTitleCase = function (str) {
        return str.replace(/\w\S*/g, function (txt) {
            return txt.charAt(0).toUpperCase() + txt.substr(1).toLowerCase();
        });
    };
    SamplesComponent.prototype.formatCellTitle = function (term) {
        return term.replace("_", " ");
    };
    SamplesComponent.prototype.editCell = function (row, column, event) {
        // this.isCellTypeFile = false;
        // this.isEditModalOpen = true;
        // this.selectedCell['row'] = row
        // this.selectedCell['column'] = column
        // if(this.fileColumns.indexOf(column.header) > -1){
        // 	this.isCellTypeFile = true;
        // }
        // this.form = this.fb.group({
        // 	cell:  [ row[column.columnDef] ],
        // });
    };
    SamplesComponent.prototype.closeEditModal = function () {
        this.isEditModalOpen = false;
    };
    SamplesComponent.prototype.deSelect = function () {
        this.selectedRows = [];
        this.selectedColumns = [];
        this.selectedCells = [];
    };
    SamplesComponent.prototype.headerClick = function (column, event) {
        this.selectedCells = [];
        this.selectedRows = [];
        var entryIndex = column.columnDef;
        var colIndex = this.selectedColumns.indexOf(entryIndex);
        if (event.altKey) {
            if (colIndex > -1) {
                this.selectedRows.splice(colIndex, 1);
            }
            else {
                this.selectedRows.push(entryIndex);
            }
        }
        else if (event.shiftKey) {
            var lastSelectionIndex = null;
            var lastRowIndex = -1;
            var colNamesArray = this.samplesTable.displayedColumns.map(function (e) { return e.columnDef; });
            if (this.lastColSelection) {
                lastSelectionIndex = this.lastColSelection.index;
                lastRowIndex = colNamesArray.indexOf(lastSelectionIndex);
            }
            else {
                lastRowIndex = 0;
            }
            var currentRowIndex = colNamesArray.indexOf(entryIndex);
            var currentSelection = [];
            if (lastRowIndex > currentRowIndex) {
                currentSelection = colNamesArray.slice(currentRowIndex, lastRowIndex + 1);
            }
            else {
                currentSelection = colNamesArray.slice(lastRowIndex, currentRowIndex + 1);
            }
            this.selectedColumns = this.selectedColumns.concat(currentSelection);
        }
        else {
            if (colIndex < 0) {
                this.selectedColumns = [entryIndex];
            }
            else {
                this.selectedColumns = [];
            }
        }
        this.lastColSelection = column;
    };
    SamplesComponent.prototype.selected = function (event) {
        this.form.get('cell').setValue(event.option.value.file);
    };
    SamplesComponent.prototype.keys = function (object) {
        return Object.keys(object);
    };
    SamplesComponent.prototype.removeFilter = function (filter) {
        var _this = this;
        this.filters = this.filters.filter(function (e) { return e !== filter; });
        this.tableDataSource.filter = '';
        if (this.filters.length > 0) {
            var data_1 = [];
            this.filters.forEach(function (f) {
                data_1 = data_1.concat(_this.tableDataSource.data.filter(function (d) { return _this.getDataString(d).indexOf(f.toLowerCase()) > -1; }));
            });
            this.tableDataSource.data = data_1;
        }
        else {
            this.tableDataSource.data = this.tableData['sampleData'].rows;
        }
    };
    SamplesComponent.prototype.highlightFilteredRows = function (term) {
        var _this = this;
        this.selectedRows = this.selectedRows.concat(this.tableDataSource.data.filter(function (f) { return _this.getDataString(f).indexOf(term.toLowerCase()) != -1; }).map(function (p) { return p.index; }));
    };
    SamplesComponent.prototype.getDataString = function (row) {
        var rowString = "";
        Object.keys(row).forEach(function (prop) { return rowString = rowString + row[prop]; });
        return rowString;
    };
    SamplesComponent.prototype.getUnique = function (arr) {
        return arr.filter(function (value, index, array) {
            return array.indexOf(value) == index;
        });
    };
    SamplesComponent.prototype.getEmptyRow = function () {
        var obj = Object(tassign__WEBPACK_IMPORTED_MODULE_1__["tassign"])({}, this.tableData.sampleData.rows[0]);
        Object.keys(obj).forEach(function (prop) {
            obj[prop] = "";
        });
        return obj;
    };
    // save(){
    // 	this.selectedCell['row'][this.selectedCell['column']['columnDef']] = this.form.get('cell').value
    // 	this.metabolightsService.updatesamplesRow(this.samplesName, { "samplesData": [ this.selectedCell['row'] ]}).subscribe( res => {
    // 			toastr.success( "MAF entry updated successfully", "Success", {
    // 				"timeOut": "2500",
    // 				"positionClass": "toast-top-center",
    // 				"preventDuplicates": true,
    // 				"extendedTimeOut": 0,
    // 				"tapToDismiss": false
    // 			});
    // 			this.persistData(res, this.samplesName)
    // 			this.rowClick(this.selectedCell['row'], null)
    // 			this.closeEditModal();
    // 		}, err => {
    // 	});
    // }
    SamplesComponent.prototype.addSamples = function () {
        this.metabolightsService.addSamples(this.tableData.file, { "data": [this.getEmptyRow()] }).subscribe(function (res) {
            toastr__WEBPACK_IMPORTED_MODULE_11__["success"]("Rows add successfully to the end of the maf sheet", "Success", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
        }, function (err) {
        });
    };
    SamplesComponent.prototype.persistData = function (data, filename) {
        var columns = [];
        Object.keys(data.samplesHeader).forEach(function (key) {
            var fn = "element['" + key + "']";
            columns.push({
                "columnDef": key,
                "sticky": "false",
                "header": key,
                "cell": function (element) { return eval(fn); }
            });
        });
        var displayedColumns = columns.map(function (a) { return a.columnDef; });
        displayedColumns.unshift("Select");
        displayedColumns.sort(function (a, b) {
            return data.samplesHeader[a] - data.samplesHeader[b];
        });
        data['columns'] = columns;
        data['displayedColumns'] = displayedColumns;
        this.ngRedux.dispatch({ type: 'ADD_STUDY_samples_TABLE', body: {
                'samplesTable': data,
                'samples': filename
            } });
    };
    SamplesComponent.prototype.deleteSamples = function () {
        var _this = this;
        this.metabolightsService.deleteSamples(this.tableData.file, this.getUnique(this.selectedRows).join(",")).subscribe(function (res) {
            _this.isDeleteModalOpen = false;
            toastr__WEBPACK_IMPORTED_MODULE_11__["success"]("Rows delete successfully", "Success", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
        }, function (err) { });
    };
    SamplesComponent.prototype.closeDelete = function () {
        this.isDeleteModalOpen = false;
    };
    SamplesComponent.prototype.openDeleteModal = function () {
        this.isDeleteModalOpen = true;
    };
    SamplesComponent.prototype.keyExists = function (object, key) {
        return object[key] != undefined;
    };
    SamplesComponent.prototype.valueExists = function (array, value) {
        return array.indexOf(value) >= 0;
    };
    SamplesComponent.prototype.isObject = function (item) {
        return (typeof item === "object" && !Array.isArray(item) && item !== null);
    };
    Object.defineProperty(SamplesComponent.prototype, "validation", {
        get: function () {
            if (this.validationsId.includes(".")) {
                var arr = this.validationsId.split(".");
                var tempValidations = JSON.parse(JSON.stringify(this.validations));
                ;
                while (arr.length && (tempValidations = tempValidations[arr.shift()]))
                    ;
                return tempValidations;
            }
            return this.validations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    SamplesComponent.prototype.fieldValidation = function (fieldId) {
        return this.validation[fieldId];
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('validations'),
        __metadata("design:type", Object)
    ], SamplesComponent.prototype, "validations", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_7__["select"])(function (state) { return state.study.factors; }),
        __metadata("design:type", Object)
    ], SamplesComponent.prototype, "studyFactors", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_7__["select"])(function (state) { return state.study.samplesTable; }),
        __metadata("design:type", Object)
    ], SamplesComponent.prototype, "samplesTable", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChildren"])(_ontology_ontology_component__WEBPACK_IMPORTED_MODULE_8__["OntologyComponent"]),
        __metadata("design:type", _angular_core__WEBPACK_IMPORTED_MODULE_0__["QueryList"])
    ], SamplesComponent.prototype, "ontologyComponents", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])(_angular_material__WEBPACK_IMPORTED_MODULE_10__["MatPaginator"]),
        __metadata("design:type", _angular_material__WEBPACK_IMPORTED_MODULE_10__["MatPaginator"])
    ], SamplesComponent.prototype, "paginator", void 0);
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["ViewChild"])(_angular_material__WEBPACK_IMPORTED_MODULE_10__["MatSort"]),
        __metadata("design:type", _angular_material__WEBPACK_IMPORTED_MODULE_10__["MatSort"])
    ], SamplesComponent.prototype, "sort", void 0);
    SamplesComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-samples',
            template: __webpack_require__(/*! ./samples.component.html */ "./src/app/components/study/samples/samples.component.html"),
            styles: [__webpack_require__(/*! ./samples.component.css */ "./src/app/components/study/samples/samples.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_6__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_9__["MetabolightsService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_7__["NgRedux"]])
    ], SamplesComponent);
    return SamplesComponent;
}());



/***/ }),

/***/ "./src/app/components/study/status/status.component.css":
/*!**************************************************************!*\
  !*** ./src/app/components/study/status/status.component.css ***!
  \**************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvc3RhdHVzL3N0YXR1cy5jb21wb25lbnQuY3NzIn0= */"

/***/ }),

/***/ "./src/app/components/study/status/status.component.html":
/*!***************************************************************!*\
  !*** ./src/app/components/study/status/status.component.html ***!
  \***************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"control\">\n\t<div class=\"tags has-addons\">\n\t\t<span class=\"tag\">Status</span>\n\t\t<span class=\"tag is-link clickable\" (click)='openModal()'>{{ status }}</span>\n\t</div>\n</div>\n<div class=\"modal\" [ngClass]=\"{'is-active': isModalOpen}\">\n\t<div class=\"modal-background\"></div>\n\t<div class=\"modal-card\">\n\t\t<section class=\"modal-card-body\">\n\t\t\t<span *ngIf=\"status == 'public'\">\n\t\t\t\t<h4>\n\t\t\t\t\t<mat-icon>public</mat-icon>\n\t\t\t\t</h4>\n\t\t\t\t<p>\n\t\t\t\t\t<b>ACCESSIBILITY</b><br>\n\t\t\t\t\tAnyone can view or download your study. Please ensure you have provided your literature publication reference (DOI or PubMed ID) if applicable to improve your visibility.\n\t\t\t\t\t<br><br>\n\t\t\t\t\t<b>Whats next?</b><br>\n\t\t\t\t\tYour study is searchable and will be exported to other search engines, such as <a target=\"_blank\" href=\"http://metabolomexchange.org/\">Metabolomexchange</a> and <a href=\"https://www.omicsdi.org/\" target=\"_blank\">OmicsDI</a>\n\t\t\t\t</p>\n\t\t\t</span>\n\t\t\t<span *ngIf=\"status == 'inreview'\">\n\t\t\t\t<h4>\n\t\t\t\t\t<mat-icon>visibility</mat-icon>\n\t\t\t\t</h4>\n\t\t\t\t<p>\n\t\t\t\t\t<b>ACCESSIBILITY</b><br>\n\t\t\t\t\tCuration of your study is complete. You can find a private link on your study page to share your study directly with collaborators, journals or reviewers.\n\t\t\t\t\t<br><br>\n\t\t\t\t\t<b>Whats next?</b><br>\n\t\t\t\t\tUpon reaching the release date your study will automatically be published to allow open access. Please ensure to provide your literature publication reference (DOI or PubMed ID) if applicable to improve your visibility.\n\t\t\t\t</p>\n\t\t\t</span>\n\t\t\t<span *ngIf=\"status == 'curation'\">\n\t\t\t\t<h4>\n\t\t\t\t\t<mat-icon>visibility_off</mat-icon>\n\t\t\t\t</h4>\n\t\t\t\t<p>\n\t\t\t\t\t<b>ACCESSIBILITY</b><br>\n\t\t\t\t\tYour study is currently in curation and therefore you cannot make any changes. Please be advised that we may contact you if further information is required to complete this process. If you would like to update you study at this stage please contact <a href=\"mailto:metabolights-curation@ebi.ac.uk\">metabolights-curation@ebi.ac.uk</a>\n\t\t\t\t\t<br><br>\n\t\t\t\t\t<b>Whats next?</b><br>\n\t\t\t\t\t Once curation of your study has been completed you will receive an update from the MetaboLights team. \n\t\t\t\t</p>\n\t\t\t</span>\n\t\t\t<span *ngIf=\"status == 'submitted'\">\n\t\t\t\t<h4>\n\t\t\t\t\t<mat-icon>visibility_off</mat-icon>\n\t\t\t\t</h4>\n\t\t\t\t<p>\n\t\t\t\t\t<b>ACCESSIBILITY</b><br>\n\t\t\t\t\tYour study is private. Only the study submitters are able to view and make changes to the study.\n\t\t\t\t\t<br><br>\n\t\t\t\t\t<b>Whats next?</b><br>\n\t\t\t\t\tCheck your study passes the validation checks (see the validation tab in your study). Once you have completed your study and it has met the validation criteria, please change the study status to ‘Curation’ / ‘Submission complete’\n\t\t\t\t</p>\n\t\t\t</span>\n\t\t</section>\n\t\t<footer class=\"modal-card-foot buttons is-center\">\n\t\t\t<button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeModal()'>OK</button>\n\t\t</footer>\n\t</div>\n</div>"

/***/ }),

/***/ "./src/app/components/study/status/status.component.ts":
/*!*************************************************************!*\
  !*** ./src/app/components/study/status/status.component.ts ***!
  \*************************************************************/
/*! exports provided: StatusComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "StatusComponent", function() { return StatusComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var StatusComponent = /** @class */ (function () {
    function StatusComponent() {
        this.isModalOpen = false;
        this.isFormBusy = false;
    }
    StatusComponent.prototype.ngOnInit = function () {
    };
    StatusComponent.prototype.openModal = function () {
        this.isModalOpen = true;
    };
    StatusComponent.prototype.closeModal = function () {
        this.isModalOpen = false;
    };
    __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Input"])('value'),
        __metadata("design:type", String)
    ], StatusComponent.prototype, "status", void 0);
    StatusComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-status',
            template: __webpack_require__(/*! ./status.component.html */ "./src/app/components/study/status/status.component.html"),
            styles: [__webpack_require__(/*! ./status.component.css */ "./src/app/components/study/status/status.component.css")]
        }),
        __metadata("design:paramtypes", [])
    ], StatusComponent);
    return StatusComponent;
}());



/***/ }),

/***/ "./src/app/components/study/store.ts":
/*!*******************************************!*\
  !*** ./src/app/components/study/store.ts ***!
  \*******************************************/
/*! exports provided: STUDY_INITIAL_STATE, studyReducer */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "STUDY_INITIAL_STATE", function() { return STUDY_INITIAL_STATE; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "studyReducer", function() { return studyReducer; });
/* harmony import */ var tassign__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! tassign */ "./node_modules/tassign/lib/index.js");
/* harmony import */ var tassign__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(tassign__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _models_mtbl_mtbls_mtbls_study__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ../../models/mtbl/mtbls/mtbls-study */ "./src/app/models/mtbl/mtbls/mtbls-study.ts");
/* harmony import */ var _models_mtbl_mtbls_mtbls_protocol__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../models/mtbl/mtbls/mtbls-protocol */ "./src/app/models/mtbl/mtbls/mtbls-protocol.ts");
/* harmony import */ var _models_mtbl_mtbls_mtbls_factor__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../models/mtbl/mtbls/mtbls-factor */ "./src/app/models/mtbl/mtbls/mtbls-factor.ts");
/* harmony import */ var _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ../../models/mtbl/mtbls/common/mtbls-ontology */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts");
/* harmony import */ var _models_mtbl_mtbls_mtbls_process_sequence__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! ../../models/mtbl/mtbls/mtbls-process-sequence */ "./src/app/models/mtbl/mtbls/mtbls-process-sequence.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_6___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_6__);
/* harmony import */ var _actions__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! ./actions */ "./src/app/components/study/actions.ts");








var STUDY_INITIAL_STATE = new _models_mtbl_mtbls_mtbls_study__WEBPACK_IMPORTED_MODULE_1__["MTBLSStudy"]();
function setStudyIdentifier(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { identifier: action.body.study.isaInvestigation.studies[0].identifier });
}
function setStudyTitle(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { title: action.body.title });
}
function setStudyAbstract(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { abstract: action.body.description });
}
function setStudySubmissionDate(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { submissionDate: action.body.study.isaInvestigation.studies[0].submissionDate });
}
function setStudyReleaseDate(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { releaseDate: action.body.study.isaInvestigation.studies[0].publicReleaseDate });
}
function setStudyPublications(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { publications: action.body.study.isaInvestigation.studies[0].publications });
}
function updateStudyPublications(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { publications: action.body.publications });
}
function setStudyPeople(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { people: action.body.study.isaInvestigation.studies[0].people });
}
function setStudyDesignDescriptors(state, action) {
    var designDescriptors = [];
    var jsonConvert = new json2typescript__WEBPACK_IMPORTED_MODULE_6__["JsonConvert"]();
    action.body.studyDesignDescriptors.forEach(function (descriptor) {
        designDescriptors.push(jsonConvert.deserialize(descriptor, _models_mtbl_mtbls_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_4__["Ontology"]));
    });
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { studyDesignDescriptors: designDescriptors });
}
function updateStudyPeople(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { people: action.body.people });
}
function loadValidationRules(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { validations: action.body.validations.study });
}
function setStudyFactors(state, action) {
    var studyFactors = [];
    var jsonConvert = new json2typescript__WEBPACK_IMPORTED_MODULE_6__["JsonConvert"]();
    action.body.factors.forEach(function (protocol) {
        studyFactors.push(jsonConvert.deserialize(protocol, _models_mtbl_mtbls_mtbls_factor__WEBPACK_IMPORTED_MODULE_3__["MTBLSFactor"]));
    });
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { factors: studyFactors });
}
function setStudyProtocols(state, action) {
    var studyProtocols = [];
    var jsonConvert = new json2typescript__WEBPACK_IMPORTED_MODULE_6__["JsonConvert"]();
    action.body.protocols.forEach(function (protocol) {
        studyProtocols.push(jsonConvert.deserialize(protocol, _models_mtbl_mtbls_mtbls_protocol__WEBPACK_IMPORTED_MODULE_2__["MTBLSProtocol"]));
    });
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { protocols: studyProtocols });
}
function setStudySamplesTable(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { samplesTable: action.body.samplesTable });
}
function setStudyProcessSequence(state, action) {
    var studyProcessSequences = [];
    var jsonConvert = new json2typescript__WEBPACK_IMPORTED_MODULE_6__["JsonConvert"]();
    action.body.processSequence.forEach(function (process) {
        studyProcessSequences.push(jsonConvert.deserialize(process, _models_mtbl_mtbls_mtbls_process_sequence__WEBPACK_IMPORTED_MODULE_5__["MTBLSProcessSequence"]));
    });
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { processSequence: studyProcessSequences });
}
function addStudyAssay(state, action) {
    var jsonConvert = new json2typescript__WEBPACK_IMPORTED_MODULE_6__["JsonConvert"]();
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { assays: state.assays.concat(action.body.assay) });
}
function addStudyAssayTable(state, action) {
    var tempAssayTables = Object.assign({}, state.assayTables);
    tempAssayTables[action.body.assay] = action.body.assayTable;
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { assayTables: tempAssayTables });
}
function addMAF(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { metaboliteAnnotationFiles: action.body.maf });
}
function updateMAF(state, action) {
    var index = null;
    if (state.metaboliteAnnotationFiles) {
        var i_1 = 0;
        state.metaboliteAnnotationFiles.forEach(function (f) {
            if (f.file == action.body.maf.file && f.assay == action.body.maf.assay) {
                index = i_1;
            }
            i_1 = i_1 + 1;
        });
    }
    if (index != null) {
        state.metaboliteAnnotationFiles[index] = action.body.maf;
    }
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { metaboliteAnnotationFiles: state.metaboliteAnnotationFiles });
}
function addStudyProcessSequence(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { processSequence: state.processSequence.concat(action.body.processSequence) });
}
function setStudyOrganisms(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { organisms: action.body.organisms });
}
function setUploadLocation(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { uploadLocation: action.body.uploadLocation });
}
function setObfuscationCode(state, action) {
    return Object(tassign__WEBPACK_IMPORTED_MODULE_0__["tassign"])(state, { obfuscationCode: action.body.obfuscationCode });
}
function studyReducer(state, action) {
    if (state === void 0) { state = STUDY_INITIAL_STATE; }
    switch (action.type) {
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_STUDY_IDENTIFIER"]: return setStudyIdentifier(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_STUDY_TITLE"]: return setStudyTitle(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_STUDY_ABSTRACT"]: return setStudyAbstract(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_STUDY_SUBMISSION_DATE"]: return setStudySubmissionDate(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_STUDY_RELEASE_DATE"]: return setStudyReleaseDate(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_STUDY_PUBLICATIONS"]: return setStudyPublications(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["UPDATE_STUDY_PUBLICATIONS"]: return updateStudyPublications(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_STUDY_PEOPLE"]: return setStudyPeople(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["UPDATE_STUDY_PEOPLE"]: return updateStudyPeople(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["LOAD_VALIDATION_RULES"]: return loadValidationRules(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_STUDY_DESIGN_DESCRIPTORS"]: return setStudyDesignDescriptors(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["UPDATE_STUDY_DESIGN_DESCRIPTORS"]: return setStudyDesignDescriptors(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_STUDY_FACTORS"]: return setStudyFactors(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_STUDY_ORGANISMS"]: return setStudyOrganisms(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_STUDY_PROTOCOLS"]: return setStudyProtocols(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["UPDATE_STUDY_PROTOCOLS"]: return setStudyProtocols(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["UPDATE_STUDY_FACTORS"]: return setStudyFactors(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_STUDY_SAMPLES_TABLE"]: return setStudySamplesTable(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_STUDY_PROCESS_SEQUENCE"]: return setStudyProcessSequence(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["ADD_STUDY_PROCESS_SEQUENCE"]: return addStudyProcessSequence(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["ADD_STUDY_ASSAY"]: return addStudyAssay(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["ADD_MAF"]: return addMAF(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["UPDATE_MAF_DATA"]: return updateMAF(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["ADD_STUDY_ASSAY_TABLE"]: return addStudyAssayTable(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_UPLOAD_LOCATION"]: return setUploadLocation(state, action);
        case _actions__WEBPACK_IMPORTED_MODULE_7__["SET_OBFUSCATION_CODE"]: return setObfuscationCode(state, action);
    }
    return state;
}


/***/ }),

/***/ "./src/app/components/study/study.component.css":
/*!******************************************************!*\
  !*** ./src/app/components/study/study.component.css ***!
  \******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".tab-content{\n\tmin-height: 50vh;\n}\n\n.tabs li.is-active a {\n    background-color: #3273dc;\n    color: #fff;\n}\n\n.npt{\n\tpadding-top: 0;\n}\n\n.logout-link{\n\tfont-size: 0.8em;\n\tfloat: right;\n\tpadding: 3px 10px;\n\tbackground-color: #3676D9;\n\tmargin: 1px;\n\tcolor: #fff;\n\tmargin-top: -2em;\n}\n\n.logout-link:hover{\n\tbackground-color: #1f58ad;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy9zdHVkeS9zdHVkeS5jb21wb25lbnQuY3NzIl0sIm5hbWVzIjpbXSwibWFwcGluZ3MiOiJBQUFBO0NBQ0MsaUJBQWlCO0NBQ2pCOztBQUVEO0lBQ0ksMEJBQTBCO0lBQzFCLFlBQVk7Q0FDZjs7QUFFRDtDQUNDLGVBQWU7Q0FDZjs7QUFFRDtDQUNDLGlCQUFpQjtDQUNqQixhQUFhO0NBQ2Isa0JBQWtCO0NBQ2xCLDBCQUEwQjtDQUMxQixZQUFZO0NBQ1osWUFBWTtDQUNaLGlCQUFpQjtDQUNqQjs7QUFFRDtDQUNDLDBCQUEwQjtDQUMxQiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvc3R1ZHkuY29tcG9uZW50LmNzcyIsInNvdXJjZXNDb250ZW50IjpbIi50YWItY29udGVudHtcblx0bWluLWhlaWdodDogNTB2aDtcbn1cblxuLnRhYnMgbGkuaXMtYWN0aXZlIGEge1xuICAgIGJhY2tncm91bmQtY29sb3I6ICMzMjczZGM7XG4gICAgY29sb3I6ICNmZmY7XG59XG5cbi5ucHR7XG5cdHBhZGRpbmctdG9wOiAwO1xufVxuXG4ubG9nb3V0LWxpbmt7XG5cdGZvbnQtc2l6ZTogMC44ZW07XG5cdGZsb2F0OiByaWdodDtcblx0cGFkZGluZzogM3B4IDEwcHg7XG5cdGJhY2tncm91bmQtY29sb3I6ICMzNjc2RDk7XG5cdG1hcmdpbjogMXB4O1xuXHRjb2xvcjogI2ZmZjtcblx0bWFyZ2luLXRvcDogLTJlbTtcbn1cblxuLmxvZ291dC1saW5rOmhvdmVye1xuXHRiYWNrZ3JvdW5kLWNvbG9yOiAjMWY1OGFkO1xufSJdfQ== */"

/***/ }),

/***/ "./src/app/components/study/study.component.html":
/*!*******************************************************!*\
  !*** ./src/app/components/study/study.component.html ***!
  \*******************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<nav-bar></nav-bar>\n<mtbls-tour></mtbls-tour>\n<div class=\"container\">\n  <!-- <a class=\"logout-link\" (click)=\"logOut()\">Logout</a>\n  <a class=\"logout-link\" [routerLink]=\"['/console']\">Console</a> -->\n  <div class=\"main-wrapper\">\n    <span *ngIf=\"(studyIdentifier | async) != undefined\">\n      <div id=\"meta\" class=\"field is-grouped is-grouped-multiline px-20 mb-10\">\n        <mtbls-status [value]=\"'public'\"></mtbls-status>&nbsp;&nbsp;\n        <mtbls-release-date [value]=\"releaseDate | async\"></mtbls-release-date>\n      </div>\n      <mtbls-title></mtbls-title>\n    </span>\n    <mtbls-people [value]=\"people | async\" [validations]=\"validations | async\"></mtbls-people> \n    <mtbls-description [value]=\"studyAbstract | async\" [validations]=\"validations | async\"></mtbls-description>\n    <mtbls-organisms></mtbls-organisms>\n    <mtbls-publications [value]=\"publications | async\" [validations]=\"validations | async\"></mtbls-publications> \n    <div class=\"px-20 mt-20\">\n      <div class=\"card\">\n        <div class=\"columns\">\n          <div class=\"column is-full tabs npt\">\n           <ul>\n            <li (click)=\"selectCurrentTab(0, 'descriptors')\" [ngClass]=\"{'is-active': (currentIndex | async) == 0 }\"><a>Descriptors</a></li>\n            <li (click)=\"selectCurrentTab(1, 'protocols')\" [ngClass]=\"{'is-active': (currentIndex | async) == 1 }\"><a>Protocols</a></li>\n            <li (click)=\"selectCurrentTab(2, 'samples')\" [ngClass]=\"{'is-active': (currentIndex | async) == 2 }\"><a>Samples</a></li>\n            <li (click)=\"selectCurrentTab(3, 'assays')\" [ngClass]=\"{'is-active': (currentIndex | async) == 3 }\"><a>Assays</a></li>\n            <li (click)=\"selectCurrentTab(4, 'metabolites')\" [ngClass]=\"{'is-active': (currentIndex | async) == 4 }\"><a>Metabolites</a></li>\n            <li (click)=\"selectCurrentTab(5, 'files')\" [ngClass]=\"{'is-active': (currentIndex | async) == 5 }\"><a>Files</a></li>\n            <!-- <li (click)=\"selectCurrentTab(6)\" [ngClass]=\"{'is-active': (currentIndex | async) == 6 }\"><a>Pathway Analysis</a></li> -->\n          </ul>\n        </div>\n      </div>\n      <div class=\"card-content npt\">\n        <div class=\"columns\">\n          <div class=\"column is-full\">\n            <div class=\"tab-content fadeIn animated\" *ngIf=\"(currentIndex | async) == 0\">\n              <mtbls-design-descriptors [value]=\"studyDesignDescriptors | async\" [validations]=\"validations | async\"></mtbls-design-descriptors>\n              <br>  \n              <mtbls-factors [value]=\"factors | async\" [validations]=\"validations | async\"></mtbls-factors>  \n            </div>\n            <div class=\"tab-content fadeIn animated\" *ngIf=\"(currentIndex | async) == 1\">\n              <mtbls-protocols [validations]=\"validations | async\"></mtbls-protocols>\n            </div>\n            <div class=\"tab-content fadeIn animated\" *ngIf=\"(currentIndex | async) == 2\">\n              <mtbls-samples [validations]=\"validations | async\"></mtbls-samples>\n            </div>\n            <div class=\"tab-content fadeIn animated\" *ngIf=\"(currentIndex | async) == 3\">\n              <mtbls-assays [validations]=\"validations | async\"></mtbls-assays>\n            </div>\n            <div class=\"tab-content fadeIn animated\" *ngIf=\"(currentIndex | async) == 4\">\n              <mtbls-metabolites [validations]=\"validations | async\"></mtbls-metabolites>\n            </div>\n            <div class=\"tab-content fadeIn animated\" *ngIf=\"(currentIndex | async) == 5\">\n              <mtbls-files [validations]=\"validations | async\"></mtbls-files>\n            </div>\n            <div class=\"tab-content fadeIn animated\" *ngIf=\"(currentIndex | async) == 6\">\n              <div>Pathway Analysis</div>\n            </div>\n          </div>\n        </div>\n      </div>        \n    </div>\n  </div>\n</div>\n</div>"

/***/ }),

/***/ "./src/app/components/study/study.component.ts":
/*!*****************************************************!*\
  !*** ./src/app/components/study/study.component.ts ***!
  \*****************************************************/
/*! exports provided: StudyComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "StudyComponent", function() { return StudyComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _services_metabolights_auth_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ../../services/metabolights/auth.service */ "./src/app/services/metabolights/auth.service.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var StudyComponent = /** @class */ (function () {
    function StudyComponent(metabolightsService, authService, ngRedux, route) {
        var _this = this;
        this.metabolightsService = metabolightsService;
        this.authService = authService;
        this.ngRedux = ngRedux;
        this.route = route;
        this.requestedTab = 0;
        this.tab = "descriptors";
        this.route.params.subscribe(function (params) {
            _this.requestedStudy = params['id'];
            if (params['tab'] == 'files') {
                _this.requestedTab = 5;
                _this.tab = "files";
            }
            else if (params['tab'] == 'metabolites') {
                _this.requestedTab = 4;
                _this.tab = "metabolites";
            }
            else if (params['tab'] == 'assays') {
                _this.requestedTab = 3;
                _this.tab = "assays";
            }
            else if (params['tab'] == 'samples') {
                _this.requestedTab = 2;
                _this.tab = "samples";
            }
            else if (params['tab'] == 'protocols') {
                _this.requestedTab = 1;
                _this.tab = "protocols";
            }
            else {
                _this.requestedTab = 0;
                _this.tab = "descriptors";
            }
        });
    }
    StudyComponent.prototype.ngOnInit = function () {
        var _this = this;
        if (this.ngRedux.getState().status['user'] != null) {
            this.ngRedux.dispatch({ type: 'ENABLE_LOADING' });
            this.ngRedux.dispatch({ type: 'SET_LOADING_INFO', body: {
                    'info': 'Loading study validations'
                } });
            // Synchronous
            this.metabolightsService.getValidations()
                .subscribe(function (validations) {
                _this.ngRedux.dispatch({ type: 'SET_LOADING_INFO', body: {
                        'info': 'Loading study details'
                    } });
                _this.metabolightsService.getStudy(_this.requestedStudy)
                    .subscribe(function (study) {
                    _this.ngRedux.dispatch({ type: 'SET_CONFIGURATION', body: {
                            'configuration': study.isaInvestigation.comments
                        } });
                    _this.ngRedux.dispatch({ type: 'LOAD_VALIDATION_RULES', body: {
                            'study': study,
                            'validations': validations
                        } });
                    _this.ngRedux.dispatch({ type: 'SET_STUDY_IDENTIFIER', body: {
                            'study': study
                        } });
                    _this.ngRedux.dispatch({ type: 'SET_STUDY_TITLE', body: {
                            'title': study.isaInvestigation.studies[0].title
                        } });
                    _this.ngRedux.dispatch({ type: 'SET_STUDY_ABSTRACT', body: {
                            'description': study.isaInvestigation.studies[0].description
                        } });
                    _this.ngRedux.dispatch({ type: 'SET_STUDY_SUBMISSION_DATE', body: {
                            'study': study
                        } });
                    _this.ngRedux.dispatch({ type: 'SET_STUDY_RELEASE_DATE', body: {
                            'study': study
                        } });
                    _this.ngRedux.dispatch({ type: 'SET_STUDY_PUBLICATIONS', body: {
                            'study': study
                        } });
                    _this.ngRedux.dispatch({ type: 'SET_STUDY_PEOPLE', body: {
                            'study': study
                        } });
                    _this.ngRedux.dispatch({ type: 'SET_STUDY_DESIGN_DESCRIPTORS', body: {
                            'studyDesignDescriptors': study.isaInvestigation.studies[0].studyDesignDescriptors
                        } });
                    _this.ngRedux.dispatch({ type: 'SET_STUDY_FACTORS', body: {
                            'factors': study.isaInvestigation.studies[0].factors
                        } });
                    _this.ngRedux.dispatch({ type: 'SET_STUDY_PROTOCOLS', body: {
                            'protocols': study.isaInvestigation.studies[0].protocols
                        } });
                    _this.ngRedux.dispatch({ type: 'SET_STUDY_PROCESS_SEQUENCE', body: {
                            'processSequence': study.isaInvestigation.studies[0].processSequence
                        } });
                    var organisms = [];
                    _this.metabolightsService.getStudyFiles().subscribe(function (data) {
                        _this.ngRedux.dispatch({ type: 'SET_UPLOAD_LOCATION', body: {
                                'uploadLocation': data.upload_location
                            } });
                        _this.ngRedux.dispatch({ type: 'SET_OBFUSCATION_CODE', body: {
                                'obfuscationCode': data.obfuscation_code
                            } });
                        data.studyFiles.forEach(function (file) {
                            if (file.type.indexOf('metadata') > -1) {
                                if (file.file.indexOf('s_') == 0 && file.status == 'active') {
                                    _this.metabolightsService.getSamplesTable(file.file).subscribe(function (data) {
                                        var columns = [];
                                        Object.keys(data.sampleHeader).forEach(function (key) {
                                            if (key.indexOf("Term Accession Number") < 0 && key.indexOf("Term Source REF") < 0) {
                                                var fn_1 = "element['" + key + "']";
                                                columns.push({
                                                    "columnDef": key,
                                                    "sticky": "false",
                                                    "header": key,
                                                    "cell": function (element) { return eval(fn_1); }
                                                });
                                            }
                                        });
                                        var displayedColumns = columns.map(function (a) { return a.columnDef; });
                                        displayedColumns.unshift("Select");
                                        displayedColumns.sort(function (a, b) {
                                            return parseInt(data.sampleHeader[a]) - parseInt(data.sampleHeader[b]);
                                        });
                                        data['columns'] = columns;
                                        data['displayedColumns'] = displayedColumns;
                                        data['file'] = file.file;
                                        var organisms = {};
                                        data.sampleData.rows.forEach(function (row) {
                                            var organismName = row['Characteristics[Organism]'].replace(/^[ ]+|[ ]+$/g, '');
                                            var organismPart = row['Characteristics[Organism part]'];
                                            var organismVariant = row['Characteristics[Variant]'];
                                            if (organismName != '' && organismName.replace(" ", '') != '') {
                                                if (organisms[organismName] == null) {
                                                    organisms[organismName] = {
                                                        "parts": [],
                                                        "variants": []
                                                    };
                                                }
                                                else {
                                                    if (organisms[organismName]['parts'].indexOf(organismPart) < 0) {
                                                        organisms[organismName]['parts'].push(organismPart);
                                                    }
                                                    if (organisms[organismName]['variants'].indexOf(organismVariant) < 0) {
                                                        organisms[organismName]['variants'].push(organismVariant);
                                                    }
                                                }
                                            }
                                        });
                                        _this.ngRedux.dispatch({ type: 'SET_STUDY_ORGANISMS', body: {
                                                'organisms': organisms
                                            } });
                                        _this.ngRedux.dispatch({ type: 'SET_STUDY_SAMPLES_TABLE', body: {
                                                'samplesTable': data
                                            } });
                                    });
                                }
                            }
                            if (file.file.indexOf('a_') == 0 && file.status == 'active') {
                                _this.metabolightsService.getAssayTable(file.file).subscribe(function (data) {
                                    var columns = [];
                                    Object.keys(data.assayHeader).forEach(function (key) {
                                        var fn = "element['" + key + "']";
                                        columns.push({
                                            "columnDef": key,
                                            "sticky": "false",
                                            "header": key,
                                            "cell": function (element) { return eval(fn); }
                                        });
                                    });
                                    var displayedColumns = columns.map(function (a) { return a.columnDef; });
                                    displayedColumns.unshift("Select");
                                    displayedColumns.sort(function (a, b) {
                                        return parseInt(data.assayHeader[a]) - parseInt(data.assayHeader[b]);
                                    });
                                    data['columns'] = columns;
                                    data['displayedColumns'] = displayedColumns;
                                    _this.ngRedux.dispatch({ type: 'ADD_STUDY_ASSAY_TABLE', body: {
                                            'assayTable': data,
                                            'assay': file.file
                                        } });
                                    _this.ngRedux.dispatch({ type: 'ADD_MAF', body: {
                                            'maf': _this.getMAFData(data, file.file)
                                        } });
                                });
                            }
                        });
                    });
                    _this.ngRedux.dispatch({ type: 'TOGGLE_LOADING' });
                    _this.selectCurrentTab(_this.requestedTab, _this.tab);
                });
            });
        }
    };
    StudyComponent.prototype.selectCurrentTab = function (index, tab) {
        this.ngRedux.dispatch({ type: 'SET_TAB_INDEX', body: {
                'currentTabIndex': index
            } });
        var urlSplit = window.location.pathname.replace(/\/$/, "").split("/").filter(function (n) { return n; });
        if (urlSplit.length >= 3) {
            if (urlSplit[urlSplit.length - 1].indexOf("MTBLS") < 0) {
                urlSplit.pop();
            }
        }
        window.history.pushState("", "", window.location.origin + "/" + urlSplit.join("/") + "/" + tab);
    };
    StudyComponent.prototype.getMAFData = function (assay, assayFileName) {
        var _this = this;
        var mafFiles = [];
        assay.assayData.rows.forEach(function (row) {
            var mafFile = row['Metabolite Assignment File'].replace(/^[ ]+|[ ]+$/g, '');
            if (mafFile != "" && mafFiles.indexOf(mafFile) < 0) {
                mafFiles.push(mafFile);
            }
        });
        var mafData = [];
        mafFiles.forEach(function (f) {
            _this.metabolightsService.getMAF(f).subscribe(function (data) {
                var columns = [];
                Object.keys(data.mafHeader).forEach(function (key) {
                    var fn = "element['" + key + "']";
                    columns.push({
                        "columnDef": key.toLowerCase().split(" ").join("_"),
                        "sticky": "false",
                        "header": key,
                        "cell": function (element) { return eval(fn); }
                    });
                });
                var displayedColumns = columns.map(function (a) { return a.columnDef; });
                displayedColumns.unshift("Select");
                displayedColumns.sort(function (a, b) {
                    return parseInt(data.mafHeader[a]) - parseInt(data.mafHeader[b]);
                });
                mafData.push({ 'assay': assayFileName, 'file': f, 'data': data.mafData.rows, 'columnOrder': {}, 'columns': columns, 'displayedColumns': displayedColumns });
            });
        });
        return mafData;
    };
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.status.user; }),
        __metadata("design:type", Object)
    ], StudyComponent.prototype, "user", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.identifier; }),
        __metadata("design:type", String)
    ], StudyComponent.prototype, "studyIdentifier", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.title; }),
        __metadata("design:type", String)
    ], StudyComponent.prototype, "studyTitle", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.abstract; }),
        __metadata("design:type", String)
    ], StudyComponent.prototype, "studyAbstract", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.status.currentTabIndex; }),
        __metadata("design:type", Number)
    ], StudyComponent.prototype, "currentIndex", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.submissionDate; }),
        __metadata("design:type", Date)
    ], StudyComponent.prototype, "submissionDate", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.releaseDate; }),
        __metadata("design:type", Date)
    ], StudyComponent.prototype, "releaseDate", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.publications; }),
        __metadata("design:type", Object)
    ], StudyComponent.prototype, "publications", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.people; }),
        __metadata("design:type", Object)
    ], StudyComponent.prototype, "people", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.studyDesignDescriptors; }),
        __metadata("design:type", Array)
    ], StudyComponent.prototype, "studyDesignDescriptors", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.factors; }),
        __metadata("design:type", Array)
    ], StudyComponent.prototype, "factors", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.validations; }),
        __metadata("design:type", Object)
    ], StudyComponent.prototype, "validations", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.protocols; }),
        __metadata("design:type", Array)
    ], StudyComponent.prototype, "protocols", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.processSequence; }),
        __metadata("design:type", Array)
    ], StudyComponent.prototype, "processSequence", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.assays; }),
        __metadata("design:type", Array)
    ], StudyComponent.prototype, "assays", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.status.configuration; }),
        __metadata("design:type", Object)
    ], StudyComponent.prototype, "configuration", void 0);
    StudyComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-study',
            template: __webpack_require__(/*! ./study.component.html */ "./src/app/components/study/study.component.html"),
            styles: [__webpack_require__(/*! ./study.component.css */ "./src/app/components/study/study.component.css")]
        }),
        __metadata("design:paramtypes", [_services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__["MetabolightsService"], _services_metabolights_auth_service__WEBPACK_IMPORTED_MODULE_3__["AuthService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["NgRedux"], _angular_router__WEBPACK_IMPORTED_MODULE_4__["ActivatedRoute"]])
    ], StudyComponent);
    return StudyComponent;
}());



/***/ }),

/***/ "./src/app/components/study/title/title.component.css":
/*!************************************************************!*\
  !*** ./src/app/components/study/title/title.component.css ***!
  \************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbXSwibmFtZXMiOltdLCJtYXBwaW5ncyI6IiIsImZpbGUiOiJzcmMvYXBwL2NvbXBvbmVudHMvc3R1ZHkvdGl0bGUvdGl0bGUuY29tcG9uZW50LmNzcyJ9 */"

/***/ }),

/***/ "./src/app/components/study/title/title.component.html":
/*!*************************************************************!*\
  !*** ./src/app/components/study/title/title.component.html ***!
  \*************************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"hover-highlight px-20 mtbls-section\">\n  <b><small class=\"has-text-grey\">TITLE</small></b>\n  <h2 class=\"title is-3\">\n    <b>\n      <!-- <span class=\"identifier\">\n        {{ studyIdentifier | async }}:\n      </span>  -->\n      <span [innerHTML]='title' class=\"clickable\" (click)='openModal()'></span>\n   </b>\n </h2>\n</div>\n<div class=\"modal\" [ngClass]=\"{'is-active': isModalOpen}\">\n  <form *ngIf=\"form\" [formGroup]=\"form\">\n    <div class=\"modal-background\"></div>\n    <div class=\"modal-card vw80\">\n      <div *ngIf=\"isFormBusy\" class=\"load-bar\">\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n        <div class=\"bar\"></div>\n      </div>\n      <section class=\"modal-card-body\">\n        <div class=\"field is-horizontal\">\n          <div class=\"field-body\">\n            <div class=\"field\">\n              <div class=\"control\">\n                <mat-form-field class=\"full-width\">\n                  <mat-label>Study title</mat-label>\n                  <textarea class=\"no-scroll\" \n                  formControlName=\"title\"\n                  matInput\n                  [placeholder]=\"validation.placeholder\" \n                  cdkAutosizeMinRows=\"20\"\n                  cdkAutosizeMaxRows=\"30\"\n                  cdkTextareaAutosize>\n                </textarea>\n                <mat-hint>{{ validation.description }}</mat-hint>\n                <mat-error\n                *ngIf=\"form.get('title').errors &&\n                form.get('title').dirty &&\n                form.get('title').errors.title\">\n                {{ form.get('title').errors.title.error }}\n              </mat-error>\n            </mat-form-field>\n          </div>\n        </div>\n      </div>\n    </div>\n  </section>\n  <footer class=\"modal-card-foot buttons is-right\">\n    <button *ngIf=\"form.get('title').dirty\" [disabled]=\"!form.valid || isFormBusy\" (click)='save()' class=\"button is-info\">\n      <mat-spinner [diameter]=\"20\" [strokeWidth]=\"3\" *ngIf=\"isFormBusy\"></mat-spinner>\n      Save\n    </button>\n    <button *ngIf=\"!form.get('title').dirty\" (click)='closeModal()' class=\"button is-info\">OK</button>\n    <button class=\"button\" [disabled]=\"isFormBusy\" (click)='closeModal()'>Cancel</button>\n  </footer>\n</div>\n</form>\n</div>"

/***/ }),

/***/ "./src/app/components/study/title/title.component.ts":
/*!***********************************************************!*\
  !*** ./src/app/components/study/title/title.component.ts ***!
  \***********************************************************/
/*! exports provided: TitleComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TitleComponent", function() { return TitleComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ../../../services/metabolights/metabolights.service */ "./src/app/services/metabolights/metabolights.service.ts");
/* harmony import */ var _angular_forms__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/forms */ "./node_modules/@angular/forms/fesm5/forms.js");
/* harmony import */ var _title_validator__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./title.validator */ "./src/app/components/study/title/title.validator.ts");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_5__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};






var TitleComponent = /** @class */ (function () {
    function TitleComponent(fb, metabolightsService, ngRedux) {
        var _this = this;
        this.fb = fb;
        this.metabolightsService = metabolightsService;
        this.ngRedux = ngRedux;
        this.title = '';
        this.isFormBusy = false;
        this.validationsId = 'title';
        this.isModalOpen = false;
        this.studyTitle.subscribe(function (value) {
            _this.title = value;
        });
        this.studyValidations.subscribe(function (value) {
            _this.validations = value;
        });
    }
    TitleComponent.prototype.ngOnInit = function () {
    };
    TitleComponent.prototype.openModal = function () {
        this.initialiseForm();
        this.isModalOpen = true;
    };
    TitleComponent.prototype.initialiseForm = function () {
        this.isFormBusy = false;
        this.form = this.fb.group({
            title: [this.title, Object(_title_validator__WEBPACK_IMPORTED_MODULE_4__["ValidateStudyTitle"])(this.validation)]
        });
    };
    TitleComponent.prototype.closeModal = function () {
        this.isModalOpen = false;
    };
    TitleComponent.prototype.save = function () {
        var _this = this;
        this.isFormBusy = true;
        this.metabolightsService.saveTitle(this.compileBody(this.form.get('title').value)).subscribe(function (res) {
            _this.ngRedux.dispatch({ type: 'SET_STUDY_TITLE', body: res });
            _this.form.get('title').setValue(res.title);
            _this.form.markAsPristine();
            _this.isFormBusy = false;
            toastr__WEBPACK_IMPORTED_MODULE_5__["success"]('Title updated.', "Success", {
                "timeOut": "2500",
                "positionClass": "toast-top-center",
                "preventDuplicates": true,
                "extendedTimeOut": 0,
                "tapToDismiss": false
            });
        }, function (err) {
            _this.isFormBusy = false;
        });
    };
    TitleComponent.prototype.compileBody = function (title) {
        return {
            'title': title,
        };
    };
    Object.defineProperty(TitleComponent.prototype, "validation", {
        get: function () {
            return this.validations[this.validationsId];
        },
        enumerable: true,
        configurable: true
    });
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.title; }),
        __metadata("design:type", Object)
    ], TitleComponent.prototype, "studyTitle", void 0);
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.validations; }),
        __metadata("design:type", Object)
    ], TitleComponent.prototype, "studyValidations", void 0);
    TitleComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-title',
            template: __webpack_require__(/*! ./title.component.html */ "./src/app/components/study/title/title.component.html"),
            styles: [__webpack_require__(/*! ./title.component.css */ "./src/app/components/study/title/title.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_forms__WEBPACK_IMPORTED_MODULE_3__["FormBuilder"], _services_metabolights_metabolights_service__WEBPACK_IMPORTED_MODULE_2__["MetabolightsService"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["NgRedux"]])
    ], TitleComponent);
    return TitleComponent;
}());



/***/ }),

/***/ "./src/app/components/study/title/title.validator.ts":
/*!***********************************************************!*\
  !*** ./src/app/components/study/title/title.validator.ts ***!
  \***********************************************************/
/*! exports provided: ValidateStudyTitle */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ValidateStudyTitle", function() { return ValidateStudyTitle; });
function ValidateStudyTitle(validation) {
    return function (control) {
        var value = control.value;
        var invalid = false;
        var errorMessage = "";
        validation.rules.forEach(function (rule) {
            switch (rule.condition) {
                case "min": {
                    if (value.toString().length < rule.value) {
                        invalid = true;
                        errorMessage = errorMessage + rule.error;
                    }
                    break;
                }
            }
        });
        if (invalid) {
            return { 'title': { 'error': errorMessage } };
        }
        return null;
    };
}


/***/ }),

/***/ "./src/app/components/tour/tour.component.css":
/*!****************************************************!*\
  !*** ./src/app/components/tour/tour.component.css ***!
  \****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = ".modal-background {\n    background-color: rgba(10, 10, 10, 0.36);\n}\n\n.vh60{\n\tmin-height: 60vh;\n}\n\n.tour-wrapper{\n\tmin-height: 60vh;\n\tpadding: 30px 20px 0 20px;\n}\n\n.slide-icon{\n\theight: 100px;\n}\n\n.slide-title{\n\ttext-align: center;\n}\n\n.slide-content{\n\tpadding-top: 20px;\n}\n\n.slide-content p{\n\tpadding-bottom: 10px;\n}\n/*# sourceMappingURL=data:application/json;base64,eyJ2ZXJzaW9uIjozLCJzb3VyY2VzIjpbInNyYy9hcHAvY29tcG9uZW50cy90b3VyL3RvdXIuY29tcG9uZW50LmNzcyJdLCJuYW1lcyI6W10sIm1hcHBpbmdzIjoiQUFBQTtJQUNJLHlDQUF5QztDQUM1Qzs7QUFFRDtDQUNDLGlCQUFpQjtDQUNqQjs7QUFFRDtDQUNDLGlCQUFpQjtDQUNqQiwwQkFBMEI7Q0FDMUI7O0FBRUQ7Q0FDQyxjQUFjO0NBQ2Q7O0FBRUQ7Q0FDQyxtQkFBbUI7Q0FDbkI7O0FBRUQ7Q0FDQyxrQkFBa0I7Q0FDbEI7O0FBRUQ7Q0FDQyxxQkFBcUI7Q0FDckIiLCJmaWxlIjoic3JjL2FwcC9jb21wb25lbnRzL3RvdXIvdG91ci5jb21wb25lbnQuY3NzIiwic291cmNlc0NvbnRlbnQiOlsiLm1vZGFsLWJhY2tncm91bmQge1xuICAgIGJhY2tncm91bmQtY29sb3I6IHJnYmEoMTAsIDEwLCAxMCwgMC4zNik7XG59XG5cbi52aDYwe1xuXHRtaW4taGVpZ2h0OiA2MHZoO1xufVxuXG4udG91ci13cmFwcGVye1xuXHRtaW4taGVpZ2h0OiA2MHZoO1xuXHRwYWRkaW5nOiAzMHB4IDIwcHggMCAyMHB4O1xufVxuXG4uc2xpZGUtaWNvbntcblx0aGVpZ2h0OiAxMDBweDtcbn1cblxuLnNsaWRlLXRpdGxle1xuXHR0ZXh0LWFsaWduOiBjZW50ZXI7XG59XG5cbi5zbGlkZS1jb250ZW50e1xuXHRwYWRkaW5nLXRvcDogMjBweDtcbn1cblxuLnNsaWRlLWNvbnRlbnQgcHtcblx0cGFkZGluZy1ib3R0b206IDEwcHg7XG59Il19 */"

/***/ }),

/***/ "./src/app/components/tour/tour.component.html":
/*!*****************************************************!*\
  !*** ./src/app/components/tour/tour.component.html ***!
  \*****************************************************/
/*! no static exports found */
/***/ (function(module, exports) {

module.exports = "<div class=\"modal\" [ngClass]=\"{'is-active': isModalOpen}\">\n  <div class=\"modal-background\"></div>\n  <div class=\"modal-card vw80 vh60\">\n    <!-- <header class=\"modal-card-head\">\n      <small>MetaboLights - Study editor tour</small>\n      <p class=\"modal-card-title\">Modal title</p>\n      <button class=\"delete\" aria-label=\"close\"></button>\n    </header> -->\n    <section class=\"modal-card-body\">\n      <div class=\"tour-wrapper\">\n        <span *ngIf=\"currentSlide == 0\">\n          <h1 class=\"slide-title\">\n             <img class=\"slide-icon\" src=\"assets/img/icons/007-touch.png\">\n          </h1>\n          <div class=\"slide-content\">\n            <h2 class=\"has-text-centered title is-1\">\n              <b>Study Title and description</b>\n            </h2>\n            <p>\n              Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n              tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n              quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n              consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n              cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n              proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n            </p>\n            <p>\n              Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n              tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n              quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n              consequat.\n            </p> \n            <p>\n              Duis aute irure dolor in reprehenderit in voluptate velit esse\n              cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n              proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n            </p>\n          </div>\n        </span>\n        <span *ngIf=\"currentSlide == 1\">\n           <h1 class=\"slide-title\">\n             <img class=\"slide-icon\" src=\"assets/img/icons/002-scientific.png\">\n          </h1>\n          <div class=\"slide-content\">\n            <h2 class=\"has-text-centered title is-1\">\n              <b>Publications</b>\n            </h2>\n            <p>\n              Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n              tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,\n              quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n              consequat.\n            </p> \n            <p>\n              Duis aute irure dolor in reprehenderit in voluptate velit esse\n              cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n              proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n            </p>\n            <p>\n              Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n              tempor incididunt.\n            </p>\n          </div>\n        </span>\n        <span *ngIf=\"currentSlide == 2\">\n           <h1 class=\"slide-title\">\n             <img class=\"slide-icon\" src=\"assets/img/icons/003-cell-division.png\">\n          </h1>\n          <div class=\"slide-content\">\n            <h2 class=\"has-text-centered title is-1\">\n              <b>Protocols and Sample Information</b>\n            </h2>\n            <p>\n              Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod\n              tempor incididunt ut labore et dolore magna aliqua. \n            </p> \n            <p>\n              Duis aute irure dolor in reprehenderit in voluptate velit esse\n              cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n              proident, sunt in culpa qui officia deserunt mollit anim id est laborum.Ut enim ad minim veniam,\n              quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo\n              consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse\n              cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non\n              proident, sunt in culpa qui officia deserunt mollit anim id est laborum.\n            </p>\n          </div>\n        </span>\n      </div>\n    </section>\n    <footer class=\"modal-card-foot buttons is-right\">\n      <div class=\"columns is-gapless full-width\">\n        <div class=\"column is-half\">\n          <button *ngIf=\"currentSlide > 0\" (click)=\"previousSlide()\" class=\"button is-outlined is-success is-small\">Previous Slide</button>            \n        </div>\n        <div class=\"column is-half has-text-right\">\n          <span>\n            <button (click)=\"skipTour()\" class=\"button is-small\">Skip tour now!</button>\n            <button (click)=\"nextSlide()\" class=\"button is-outlined is-success is-small\">Next Slide</button>  \n          </span>\n        </div>\n      </div>\n    </footer>\n  </div>\n</div>"

/***/ }),

/***/ "./src/app/components/tour/tour.component.ts":
/*!***************************************************!*\
  !*** ./src/app/components/tour/tour.component.ts ***!
  \***************************************************/
/*! exports provided: TourComponent */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "TourComponent", function() { return TourComponent; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var TourComponent = /** @class */ (function () {
    function TourComponent(ngRedux) {
        var _this = this;
        this.ngRedux = ngRedux;
        this.currentSlide = 0;
        this.isModalOpen = false;
        this.studyTitle.subscribe(function (value) {
            if (value && value.indexOf("Please add your study title") > -1) {
                _this.isModalOpen = true;
            }
        });
    }
    TourComponent.prototype.ngOnInit = function () {
    };
    TourComponent.prototype.nextSlide = function () {
        this.currentSlide = this.currentSlide + 1;
    };
    TourComponent.prototype.previousSlide = function () {
        this.currentSlide = this.currentSlide - 1;
    };
    TourComponent.prototype.skipTour = function () {
        this.isModalOpen = false;
    };
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["select"])(function (state) { return state.study.title; }),
        __metadata("design:type", Object)
    ], TourComponent.prototype, "studyTitle", void 0);
    TourComponent = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Component"])({
            selector: 'mtbls-tour',
            template: __webpack_require__(/*! ./tour.component.html */ "./src/app/components/tour/tour.component.html"),
            styles: [__webpack_require__(/*! ./tour.component.css */ "./src/app/components/tour/tour.component.css")]
        }),
        __metadata("design:paramtypes", [_angular_redux_store__WEBPACK_IMPORTED_MODULE_1__["NgRedux"]])
    ], TourComponent);
    return TourComponent;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/common/mtbls-column.ts":
/*!**********************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/common/mtbls-column.ts ***!
  \**********************************************************/
/*! exports provided: MTBLSColumn */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MTBLSColumn", function() { return MTBLSColumn; });
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_0__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var MTBLSColumn = /** @class */ (function () {
    function MTBLSColumn(name, value, index) {
        this.name = '';
        this.value = '';
        this.index = null;
        this.name = name;
        this.value = value;
        this.index = index;
    }
    MTBLSColumn.prototype.toJSON = function () {
        return {
            "name": this.name,
            "value": this.value,
            "index": this.index
        };
    };
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_0__["JsonProperty"])("name", String),
        __metadata("design:type", String)
    ], MTBLSColumn.prototype, "name", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_0__["JsonProperty"])("value"),
        __metadata("design:type", Object)
    ], MTBLSColumn.prototype, "value", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_0__["JsonProperty"])("index", Number),
        __metadata("design:type", Number)
    ], MTBLSColumn.prototype, "index", void 0);
    MTBLSColumn = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_0__["JsonObject"],
        __metadata("design:paramtypes", [Object, Object, Object])
    ], MTBLSColumn);
    return MTBLSColumn;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/common/mtbls-comment.ts":
/*!***********************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/common/mtbls-comment.ts ***!
  \***********************************************************/
/*! exports provided: MTBLSComment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MTBLSComment", function() { return MTBLSComment; });
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_0__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};

var MTBLSComment = /** @class */ (function () {
    function MTBLSComment() {
        this.name = '';
        this.value = '';
    }
    MTBLSComment.prototype.toJSON = function () {
        if (this.name != '' || this.name != null) {
            return {
                "name": this.name,
                "value": JSON.stringify(this.value)
            };
        }
    };
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_0__["JsonProperty"])("name", String),
        __metadata("design:type", String)
    ], MTBLSComment.prototype, "name", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_0__["JsonProperty"])("value"),
        __metadata("design:type", Object)
    ], MTBLSComment.prototype, "value", void 0);
    MTBLSComment = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_0__["JsonObject"]
    ], MTBLSComment);
    return MTBLSComment;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/common/mtbls-ontology-reference.ts":
/*!**********************************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/common/mtbls-ontology-reference.ts ***!
  \**********************************************************************/
/*! exports provided: OntologySourceReference */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "OntologySourceReference", function() { return OntologySourceReference; });
/* harmony import */ var _mtbls_comment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./mtbls-comment */ "./src/app/models/mtbl/mtbls/common/mtbls-comment.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_1__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var OntologySourceReference = /** @class */ (function () {
    function OntologySourceReference() {
        this.description = '';
        this.file = '';
        this.name = '';
        this.version = '';
        this.comments = [];
    }
    OntologySourceReference.prototype.toJSON = function () {
        if (this.name == '' || this.name == null) {
            return null;
        }
        else {
            return {
                "comments": this.comments.map(function (a) { return a.toJSON(); }),
                "description": this.description,
                "file": this.file,
                "name": this.name,
                "version": this.version
            };
        }
    };
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("description", String),
        __metadata("design:type", String)
    ], OntologySourceReference.prototype, "description", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("file", String),
        __metadata("design:type", String)
    ], OntologySourceReference.prototype, "file", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("name", String),
        __metadata("design:type", String)
    ], OntologySourceReference.prototype, "name", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("version", String),
        __metadata("design:type", String)
    ], OntologySourceReference.prototype, "version", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("comments", [_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__["MTBLSComment"]]),
        __metadata("design:type", Array)
    ], OntologySourceReference.prototype, "comments", void 0);
    OntologySourceReference = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonObject"]
    ], OntologySourceReference);
    return OntologySourceReference;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts":
/*!************************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts ***!
  \************************************************************/
/*! exports provided: Ontology */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "Ontology", function() { return Ontology; });
/* harmony import */ var _mtbls_comment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./mtbls-comment */ "./src/app/models/mtbl/mtbls/common/mtbls-comment.ts");
/* harmony import */ var _mtbls_ontology_reference__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./mtbls-ontology-reference */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology-reference.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_2__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var Ontology = /** @class */ (function () {
    function Ontology() {
        this.comments = [];
        this.termAccession = '';
        this.annotationValue = '';
        this.termSource = undefined;
    }
    Ontology.prototype.toJSON = function () {
        return {
            "comments": this.comments.map(function (a) { return a.toJSON(); }),
            "termAccession": this.termAccession,
            "annotationValue": this.annotationValue,
            "termSource": this.termSource ? this.termSource.toJSON() : null
        };
    };
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("comments", [_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__["MTBLSComment"]]),
        __metadata("design:type", Array)
    ], Ontology.prototype, "comments", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("termAccession", String),
        __metadata("design:type", String)
    ], Ontology.prototype, "termAccession", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("annotationValue", String),
        __metadata("design:type", String)
    ], Ontology.prototype, "annotationValue", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("termSource", _mtbls_ontology_reference__WEBPACK_IMPORTED_MODULE_1__["OntologySourceReference"]),
        __metadata("design:type", _mtbls_ontology_reference__WEBPACK_IMPORTED_MODULE_1__["OntologySourceReference"])
    ], Ontology.prototype, "termSource", void 0);
    Ontology = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonObject"]
    ], Ontology);
    return Ontology;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/mtbls-characterstic.ts":
/*!**********************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/mtbls-characterstic.ts ***!
  \**********************************************************/
/*! exports provided: MTBLSCharacterstic */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MTBLSCharacterstic", function() { return MTBLSCharacterstic; });
/* harmony import */ var _common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./common/mtbls-comment */ "./src/app/models/mtbl/mtbls/common/mtbls-comment.ts");
/* harmony import */ var _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./common/mtbls-ontology */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_2__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var MTBLSCharacterstic = /** @class */ (function () {
    function MTBLSCharacterstic() {
        this.comments = [];
        this.unit = null;
        this.category = null;
        this.value = null;
    }
    MTBLSCharacterstic.prototype.toJSON = function () {
        return {
            "comments": this.comments.map(function (a) { return a.toJSON(); }),
            "unit": this.unit,
            "category": this.category ? this.category.toJSON() : null,
            "value": this.value ? this.value : "",
        };
    };
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("comments", [_common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__["MTBLSComment"]]),
        __metadata("design:type", Array)
    ], MTBLSCharacterstic.prototype, "comments", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("unit"),
        __metadata("design:type", Object)
    ], MTBLSCharacterstic.prototype, "unit", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("category", _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__["Ontology"]),
        __metadata("design:type", _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__["Ontology"])
    ], MTBLSCharacterstic.prototype, "category", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("value"),
        __metadata("design:type", Object)
    ], MTBLSCharacterstic.prototype, "value", void 0);
    MTBLSCharacterstic = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonObject"]
    ], MTBLSCharacterstic);
    return MTBLSCharacterstic;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/mtbls-factor-value.ts":
/*!*********************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/mtbls-factor-value.ts ***!
  \*********************************************************/
/*! exports provided: MTBLSFactorValue */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MTBLSFactorValue", function() { return MTBLSFactorValue; });
/* harmony import */ var _common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./common/mtbls-comment */ "./src/app/models/mtbl/mtbls/common/mtbls-comment.ts");
/* harmony import */ var _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./common/mtbls-ontology */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts");
/* harmony import */ var _mtbls_factor__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./mtbls-factor */ "./src/app/models/mtbl/mtbls/mtbls-factor.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_3___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_3__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};




var MTBLSFactorValue = /** @class */ (function () {
    function MTBLSFactorValue() {
        this.comments = [];
        this.unit = null;
        this.category = null;
        this.value = null;
    }
    MTBLSFactorValue.prototype.toJSON = function () {
        return {
            "comments": this.comments.map(function (a) { return a.toJSON(); }),
            "unit": this.unit ? this.unit : null,
            "category": this.category ? this.category.toJSON() : null,
            "value": this.value ? this.value : null,
        };
    };
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_3__["JsonProperty"])("comments", [_common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__["MTBLSComment"]]),
        __metadata("design:type", Array)
    ], MTBLSFactorValue.prototype, "comments", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_3__["JsonProperty"])("unit", _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__["Ontology"]),
        __metadata("design:type", _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__["Ontology"])
    ], MTBLSFactorValue.prototype, "unit", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_3__["JsonProperty"])("category", _mtbls_factor__WEBPACK_IMPORTED_MODULE_2__["MTBLSFactor"]),
        __metadata("design:type", _mtbls_factor__WEBPACK_IMPORTED_MODULE_2__["MTBLSFactor"])
    ], MTBLSFactorValue.prototype, "category", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_3__["JsonProperty"])("value"),
        __metadata("design:type", Object)
    ], MTBLSFactorValue.prototype, "value", void 0);
    MTBLSFactorValue = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_3__["JsonObject"]
    ], MTBLSFactorValue);
    return MTBLSFactorValue;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/mtbls-factor.ts":
/*!***************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/mtbls-factor.ts ***!
  \***************************************************/
/*! exports provided: MTBLSFactor */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MTBLSFactor", function() { return MTBLSFactor; });
/* harmony import */ var _common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./common/mtbls-comment */ "./src/app/models/mtbl/mtbls/common/mtbls-comment.ts");
/* harmony import */ var _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./common/mtbls-ontology */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_2__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var MTBLSFactor = /** @class */ (function () {
    function MTBLSFactor() {
        this.comments = [];
        this.factorName = '';
        this.factorType = null;
    }
    MTBLSFactor.prototype.toJSON = function () {
        return {
            "comments": this.comments.map(function (a) { return a.toJSON(); }),
            "factorName": this.factorName,
            "factorType": this.factorType ? this.factorType.toJSON() : ""
        };
    };
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("comments", [_common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__["MTBLSComment"]]),
        __metadata("design:type", Array)
    ], MTBLSFactor.prototype, "comments", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("factorName", String),
        __metadata("design:type", String)
    ], MTBLSFactor.prototype, "factorName", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("factorType", _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__["Ontology"]),
        __metadata("design:type", _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__["Ontology"])
    ], MTBLSFactor.prototype, "factorType", void 0);
    MTBLSFactor = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonObject"]
    ], MTBLSFactor);
    return MTBLSFactor;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/mtbls-person.ts":
/*!***************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/mtbls-person.ts ***!
  \***************************************************/
/*! exports provided: MTBLSPerson */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MTBLSPerson", function() { return MTBLSPerson; });
/* harmony import */ var _common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./common/mtbls-comment */ "./src/app/models/mtbl/mtbls/common/mtbls-comment.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_1__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var MTBLSPerson = /** @class */ (function () {
    function MTBLSPerson() {
        this.comments = [];
        this.firstName = '';
        this.lastName = '';
        this.email = '';
        this.affiliation = '';
        this.address = '';
        this.fax = '';
        this.midInitials = '';
        this.phone = '';
        this.roles = [];
    }
    MTBLSPerson.prototype.toJSON = function () {
        return {
            "comments": this.comments.map(function (a) { return a.toJSON(); }),
            "firstName": this.firstName,
            "lastName": this.lastName,
            "email": this.email,
            "affiliation": this.affiliation,
            "address": this.address,
            "fax": this.fax,
            "midInitials": this.midInitials,
            "phone": this.phone,
            "roles": this.roles.map(function (a) { return a.toJSON(); })
        };
    };
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("comments", [_common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__["MTBLSComment"]]),
        __metadata("design:type", Array)
    ], MTBLSPerson.prototype, "comments", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("firstName", String),
        __metadata("design:type", String)
    ], MTBLSPerson.prototype, "firstName", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("lastName", String),
        __metadata("design:type", String)
    ], MTBLSPerson.prototype, "lastName", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("email", String),
        __metadata("design:type", String)
    ], MTBLSPerson.prototype, "email", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("affiliation", String),
        __metadata("design:type", String)
    ], MTBLSPerson.prototype, "affiliation", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("address", String),
        __metadata("design:type", String)
    ], MTBLSPerson.prototype, "address", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("fax", String),
        __metadata("design:type", String)
    ], MTBLSPerson.prototype, "fax", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("midInitials", String),
        __metadata("design:type", String)
    ], MTBLSPerson.prototype, "midInitials", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("phone", String),
        __metadata("design:type", String)
    ], MTBLSPerson.prototype, "phone", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("roles", String),
        __metadata("design:type", Array)
    ], MTBLSPerson.prototype, "roles", void 0);
    MTBLSPerson = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonObject"]
    ], MTBLSPerson);
    return MTBLSPerson;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/mtbls-process-sequence.ts":
/*!*************************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/mtbls-process-sequence.ts ***!
  \*************************************************************/
/*! exports provided: MTBLSProcessSequence */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MTBLSProcessSequence", function() { return MTBLSProcessSequence; });
/* harmony import */ var _common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./common/mtbls-comment */ "./src/app/models/mtbl/mtbls/common/mtbls-comment.ts");
/* harmony import */ var _mtbls_protocol__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./mtbls-protocol */ "./src/app/models/mtbl/mtbls/mtbls-protocol.ts");
/* harmony import */ var _mtbls_source__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./mtbls-source */ "./src/app/models/mtbl/mtbls/mtbls-source.ts");
/* harmony import */ var _mtbls_sample__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./mtbls-sample */ "./src/app/models/mtbl/mtbls/mtbls-sample.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_4___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_4__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var MTBLSProcessSequence = /** @class */ (function () {
    function MTBLSProcessSequence() {
        this.comments = [];
        this.name = '';
        this.inputs = [];
        this.outputs = [];
        this.executesProtocol = null;
        this.parameterValues = [];
        this.performer = null;
        this.previousProcess = null;
        this.nextProcess = null;
    }
    MTBLSProcessSequence_1 = MTBLSProcessSequence;
    MTBLSProcessSequence.prototype.toJSON = function () {
        return {
            "comments": this.comments.map(function (a) { return a.toJSON(); }),
            "name": this.name,
            "inputs": this.inputs.map(function (a) { return a.toJSON(); }),
            "outputs": this.outputs.map(function (a) { return a.toJSON(); }),
            "executesProtocol": this.executesProtocol ? this.executesProtocol.toJSON() : null,
            "parameterValues": this.parameterValues.map(function (a) { return a.toJSON(); }),
            "performer": this.performer ? this.performer.toJSON() : null,
            "previousProcess": this.previousProcess ? this.previousProcess.toJSON() : null,
            "nextProcess": this.nextProcess ? this.nextProcess.toJSON() : null,
        };
    };
    var MTBLSProcessSequence_1;
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_4__["JsonProperty"])("comments", [_common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__["MTBLSComment"]]),
        __metadata("design:type", Array)
    ], MTBLSProcessSequence.prototype, "comments", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_4__["JsonProperty"])("name", String),
        __metadata("design:type", String)
    ], MTBLSProcessSequence.prototype, "name", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_4__["JsonProperty"])("inputs", [_mtbls_source__WEBPACK_IMPORTED_MODULE_2__["MTBLSSource"]]),
        __metadata("design:type", Array)
    ], MTBLSProcessSequence.prototype, "inputs", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_4__["JsonProperty"])("outputs", [_mtbls_sample__WEBPACK_IMPORTED_MODULE_3__["MTBLSSample"]]),
        __metadata("design:type", Array)
    ], MTBLSProcessSequence.prototype, "outputs", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_4__["JsonProperty"])("executesProtocol", _mtbls_protocol__WEBPACK_IMPORTED_MODULE_1__["MTBLSProtocol"]),
        __metadata("design:type", _mtbls_protocol__WEBPACK_IMPORTED_MODULE_1__["MTBLSProtocol"])
    ], MTBLSProcessSequence.prototype, "executesProtocol", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_4__["JsonProperty"])("parameterValues"),
        __metadata("design:type", Array)
    ], MTBLSProcessSequence.prototype, "parameterValues", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_4__["JsonProperty"])("performer"),
        __metadata("design:type", Object)
    ], MTBLSProcessSequence.prototype, "performer", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_4__["JsonProperty"])("previousProcess", MTBLSProcessSequence_1),
        __metadata("design:type", MTBLSProcessSequence)
    ], MTBLSProcessSequence.prototype, "previousProcess", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_4__["JsonProperty"])("nextProcess", MTBLSProcessSequence_1),
        __metadata("design:type", MTBLSProcessSequence)
    ], MTBLSProcessSequence.prototype, "nextProcess", void 0);
    MTBLSProcessSequence = MTBLSProcessSequence_1 = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_4__["JsonObject"]
    ], MTBLSProcessSequence);
    return MTBLSProcessSequence;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/mtbls-protocol.ts":
/*!*****************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/mtbls-protocol.ts ***!
  \*****************************************************/
/*! exports provided: ProtocolParameter, MTBLSProtocol */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ProtocolParameter", function() { return ProtocolParameter; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MTBLSProtocol", function() { return MTBLSProtocol; });
/* harmony import */ var _common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./common/mtbls-comment */ "./src/app/models/mtbl/mtbls/common/mtbls-comment.ts");
/* harmony import */ var _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./common/mtbls-ontology */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_2__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var ProtocolParameter = /** @class */ (function () {
    function ProtocolParameter() {
        this.comments = [];
        this.parameterName = null;
    }
    ProtocolParameter.prototype.toJSON = function () {
        return {
            "comments": this.comments.map(function (a) { return a.toJSON(); }),
            "parameterName": this.parameterName ? this.parameterName.toJSON() : null
        };
    };
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("comments", [_common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__["MTBLSComment"]]),
        __metadata("design:type", Array)
    ], ProtocolParameter.prototype, "comments", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("parameterName", _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__["Ontology"]),
        __metadata("design:type", _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__["Ontology"])
    ], ProtocolParameter.prototype, "parameterName", void 0);
    ProtocolParameter = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonObject"]
    ], ProtocolParameter);
    return ProtocolParameter;
}());

var MTBLSProtocol = /** @class */ (function () {
    function MTBLSProtocol() {
        this.comments = [];
        this.name = '';
        this.description = '';
        this.uri = '';
        this.version = '';
        this.protocolType = null;
        this.parameters = [];
        this.components = [];
    }
    MTBLSProtocol.prototype.toJSON = function () {
        return {
            "comments": this.comments.map(function (a) { return a.toJSON(); }),
            "name": this.name,
            "description": this.description,
            "uri": this.uri,
            "version": this.version,
            "protocolType": this.protocolType ? this.protocolType.toJSON() : null,
            "parameters": this.parameters.map(function (a) { return a.toJSON(); }),
            "components": this.components.map(function (a) { return a.toJSON(); }),
        };
    };
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("comments", [_common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__["MTBLSComment"]]),
        __metadata("design:type", Array)
    ], MTBLSProtocol.prototype, "comments", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("name", String),
        __metadata("design:type", String)
    ], MTBLSProtocol.prototype, "name", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("description", String),
        __metadata("design:type", String)
    ], MTBLSProtocol.prototype, "description", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("uri", String),
        __metadata("design:type", String)
    ], MTBLSProtocol.prototype, "uri", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("version", String),
        __metadata("design:type", String)
    ], MTBLSProtocol.prototype, "version", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("protocolType", _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__["Ontology"]),
        __metadata("design:type", _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__["Ontology"])
    ], MTBLSProtocol.prototype, "protocolType", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("parameters", [ProtocolParameter]),
        __metadata("design:type", Array)
    ], MTBLSProtocol.prototype, "parameters", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("components", [_common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__["Ontology"]]),
        __metadata("design:type", Array)
    ], MTBLSProtocol.prototype, "components", void 0);
    MTBLSProtocol = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonObject"]
    ], MTBLSProtocol);
    return MTBLSProtocol;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/mtbls-publication.ts":
/*!********************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/mtbls-publication.ts ***!
  \********************************************************/
/*! exports provided: MTBLSPublication */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MTBLSPublication", function() { return MTBLSPublication; });
/* harmony import */ var _common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./common/mtbls-comment */ "./src/app/models/mtbl/mtbls/common/mtbls-comment.ts");
/* harmony import */ var _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./common/mtbls-ontology */ "./src/app/models/mtbl/mtbls/common/mtbls-ontology.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_2__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var MTBLSPublication = /** @class */ (function () {
    function MTBLSPublication() {
        this.title = '';
        this.authorList = '';
        this.doi = '';
        this.pubMedID = '';
        this.status = null;
    }
    MTBLSPublication.prototype.toJSON = function () {
        return {
            "comments": this.comments.map(function (a) { return a.toJSON(); }),
            "title": this.title,
            "authorList": this.authorList,
            "doi": this.doi,
            "pubMedID": this.pubMedID,
            "status": this.status ? this.status.toJSON() : null
        };
    };
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("comments", [_common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__["MTBLSComment"]]),
        __metadata("design:type", Array)
    ], MTBLSPublication.prototype, "comments", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("title", String),
        __metadata("design:type", String)
    ], MTBLSPublication.prototype, "title", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("authorList", String),
        __metadata("design:type", String)
    ], MTBLSPublication.prototype, "authorList", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("doi", String),
        __metadata("design:type", String)
    ], MTBLSPublication.prototype, "doi", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("pubMedID", String),
        __metadata("design:type", String)
    ], MTBLSPublication.prototype, "pubMedID", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("status", _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__["Ontology"]),
        __metadata("design:type", _common_mtbls_ontology__WEBPACK_IMPORTED_MODULE_1__["Ontology"])
    ], MTBLSPublication.prototype, "status", void 0);
    MTBLSPublication = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonObject"]
    ], MTBLSPublication);
    return MTBLSPublication;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/mtbls-sample.ts":
/*!***************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/mtbls-sample.ts ***!
  \***************************************************/
/*! exports provided: MTBLSSample */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MTBLSSample", function() { return MTBLSSample; });
/* harmony import */ var _common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./common/mtbls-comment */ "./src/app/models/mtbl/mtbls/common/mtbls-comment.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _mtbls_source__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./mtbls-source */ "./src/app/models/mtbl/mtbls/mtbls-source.ts");
/* harmony import */ var _mtbls_factor_value__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./mtbls-factor-value */ "./src/app/models/mtbl/mtbls/mtbls-factor-value.ts");
/* harmony import */ var _mtbls_characterstic__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./mtbls-characterstic */ "./src/app/models/mtbl/mtbls/mtbls-characterstic.ts");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var MTBLSSample = /** @class */ (function () {
    function MTBLSSample() {
        this.comments = [];
        this.name = '';
        this.characteristics = [];
        this.derivesFrom = [];
        this.factorValues = [];
    }
    MTBLSSample.prototype.toJSON = function () {
        return {
            "comments": this.comments.map(function (a) { return a.toJSON(); }),
            "name": this.name,
            "characteristics": this.characteristics.map(function (a) { return a.toJSON(); }),
            "derivesFrom": this.derivesFrom.map(function (a) { return a.toJSON(); }),
            "factorValues": this.factorValues.map(function (a) { return a.toJSON(); })
        };
    };
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("comments", [_common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__["MTBLSComment"]]),
        __metadata("design:type", Array)
    ], MTBLSSample.prototype, "comments", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("name", String),
        __metadata("design:type", String)
    ], MTBLSSample.prototype, "name", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("characteristics", [_mtbls_characterstic__WEBPACK_IMPORTED_MODULE_4__["MTBLSCharacterstic"]]),
        __metadata("design:type", Array)
    ], MTBLSSample.prototype, "characteristics", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("derivesFrom", [_mtbls_source__WEBPACK_IMPORTED_MODULE_2__["MTBLSSource"]]),
        __metadata("design:type", Array)
    ], MTBLSSample.prototype, "derivesFrom", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonProperty"])("factorValues", [_mtbls_factor_value__WEBPACK_IMPORTED_MODULE_3__["MTBLSFactorValue"]]),
        __metadata("design:type", Array)
    ], MTBLSSample.prototype, "factorValues", void 0);
    MTBLSSample = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_1__["JsonObject"]
    ], MTBLSSample);
    return MTBLSSample;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/mtbls-source.ts":
/*!***************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/mtbls-source.ts ***!
  \***************************************************/
/*! exports provided: MTBLSSource */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MTBLSSource", function() { return MTBLSSource; });
/* harmony import */ var _common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./common/mtbls-comment */ "./src/app/models/mtbl/mtbls/common/mtbls-comment.ts");
/* harmony import */ var _mtbls_characterstic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./mtbls-characterstic */ "./src/app/models/mtbl/mtbls/mtbls-characterstic.ts");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_2___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_2__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var MTBLSSource = /** @class */ (function () {
    function MTBLSSource() {
        this.comments = [];
        this.name = '';
        this.characteristics = [];
    }
    MTBLSSource.prototype.toJSON = function () {
        return {
            "comments": this.comments.map(function (a) { return a.toJSON(); }),
            "name": this.name,
            "characteristics": this.characteristics.map(function (a) { return a.toJSON(); })
        };
    };
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("comments", [_common_mtbls_comment__WEBPACK_IMPORTED_MODULE_0__["MTBLSComment"]]),
        __metadata("design:type", Array)
    ], MTBLSSource.prototype, "comments", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("name", String),
        __metadata("design:type", String)
    ], MTBLSSource.prototype, "name", void 0);
    __decorate([
        Object(json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonProperty"])("characteristics", [_mtbls_characterstic__WEBPACK_IMPORTED_MODULE_1__["MTBLSCharacterstic"]]),
        __metadata("design:type", Array)
    ], MTBLSSource.prototype, "characteristics", void 0);
    MTBLSSource = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_2__["JsonObject"]
    ], MTBLSSource);
    return MTBLSSource;
}());



/***/ }),

/***/ "./src/app/models/mtbl/mtbls/mtbls-study.ts":
/*!**************************************************!*\
  !*** ./src/app/models/mtbl/mtbls/mtbls-study.ts ***!
  \**************************************************/
/*! exports provided: MTBLSStudy */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MTBLSStudy", function() { return MTBLSStudy; });
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! json2typescript */ "./node_modules/json2typescript/index.js");
/* harmony import */ var json2typescript__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(json2typescript__WEBPACK_IMPORTED_MODULE_0__);
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var MTBLSStudy = /** @class */ (function () {
    function MTBLSStudy() {
        this.organisms = {};
        this.studyDesignDescriptors = [];
        this.assays = [];
        this.assayTables = {};
        this.samplesTable = {};
        this.metaboliteAnnotationFiles = [];
        this.uploadLocation = null;
        this.obfuscationCode = null;
    }
    MTBLSStudy = __decorate([
        json2typescript__WEBPACK_IMPORTED_MODULE_0__["JsonObject"]
    ], MTBLSStudy);
    return MTBLSStudy;
}());



/***/ }),

/***/ "./src/app/pipes/reverse.pipe.ts":
/*!***************************************!*\
  !*** ./src/app/pipes/reverse.pipe.ts ***!
  \***************************************/
/*! exports provided: ReversePipe */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ReversePipe", function() { return ReversePipe; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};

var ReversePipe = /** @class */ (function () {
    function ReversePipe() {
    }
    ReversePipe.prototype.transform = function (value) {
        return value.slice().reverse();
    };
    ReversePipe = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Pipe"])({
            name: 'reverse'
        })
    ], ReversePipe);
    return ReversePipe;
}());



/***/ }),

/***/ "./src/app/services/data.service.ts":
/*!******************************************!*\
  !*** ./src/app/services/data.service.ts ***!
  \******************************************/
/*! exports provided: DataService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DataService", function() { return DataService; });
/* harmony import */ var _error_bad_input__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! ./error/bad-input */ "./src/app/services/error/bad-input.ts");
/* harmony import */ var _error_not_found_error__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./error/not-found-error */ "./src/app/services/error/not-found-error.ts");
/* harmony import */ var _error_forbidden_error__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./error/forbidden-error */ "./src/app/services/error/forbidden-error.ts");
/* harmony import */ var _error_internal_server_error__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./error/internal-server-error */ "./src/app/services/error/internal-server-error.ts");
/* harmony import */ var _error_app_error__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! ./error/app-error */ "./src/app/services/error/app-error.ts");
/* harmony import */ var rxjs__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! rxjs */ "./node_modules/rxjs/_esm5/index.js");






var DataService = /** @class */ (function () {
    function DataService(url, http) {
        this.url = url;
        this.http = http;
    }
    DataService.prototype.handleError = function (error) {
        if (error.status === 400)
            return Object(rxjs__WEBPACK_IMPORTED_MODULE_5__["throwError"])(new _error_bad_input__WEBPACK_IMPORTED_MODULE_0__["BadInput"](error));
        if (error.status === 403)
            return Object(rxjs__WEBPACK_IMPORTED_MODULE_5__["throwError"])(new _error_forbidden_error__WEBPACK_IMPORTED_MODULE_2__["ForbiddenError"](error));
        if (error.status === 404)
            return Object(rxjs__WEBPACK_IMPORTED_MODULE_5__["throwError"])(new _error_not_found_error__WEBPACK_IMPORTED_MODULE_1__["NotFoundError"](error));
        if (error.status === 500) {
            return Object(rxjs__WEBPACK_IMPORTED_MODULE_5__["throwError"])(new _error_internal_server_error__WEBPACK_IMPORTED_MODULE_3__["InternalServerError"](error));
        }
        return Object(rxjs__WEBPACK_IMPORTED_MODULE_5__["throwError"])(new _error_app_error__WEBPACK_IMPORTED_MODULE_4__["AppError"](error));
    };
    return DataService;
}());



/***/ }),

/***/ "./src/app/services/error/app-error.ts":
/*!*********************************************!*\
  !*** ./src/app/services/error/app-error.ts ***!
  \*********************************************/
/*! exports provided: AppError */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AppError", function() { return AppError; });
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_0__);

var AppError = /** @class */ (function () {
    function AppError(error) {
        console.log(error);
        var errorMessage = error.json().message ? error.json().message : 'Could not connect to MetaboLights. We will keep trying to connect but there may be a problem with your connection.';
        var errorStatus = error.statusText ? error.statusText : '';
        toastr__WEBPACK_IMPORTED_MODULE_0__["warning"](errorMessage, errorStatus, {
            "timeOut": "0",
            "positionClass": "toast-top-center",
            "preventDuplicates": true,
            "extendedTimeOut": 0
        });
    }
    return AppError;
}());



/***/ }),

/***/ "./src/app/services/error/bad-input.ts":
/*!*********************************************!*\
  !*** ./src/app/services/error/bad-input.ts ***!
  \*********************************************/
/*! exports provided: BadInput */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "BadInput", function() { return BadInput; });
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_0__);

var BadInput = /** @class */ (function () {
    function BadInput(error) {
        var errorMessage = error.json().message ? error.json().message : 'Bad request input error';
        var errorStatus = error.statusText ? error.statusText : '';
        toastr__WEBPACK_IMPORTED_MODULE_0__["warning"](errorMessage, errorStatus, {
            "timeOut": "0",
            "positionClass": "toast-top-center",
            "preventDuplicates": true,
            "extendedTimeOut": 0,
            "tapToDismiss": true
        });
    }
    return BadInput;
}());



/***/ }),

/***/ "./src/app/services/error/forbidden-error.ts":
/*!***************************************************!*\
  !*** ./src/app/services/error/forbidden-error.ts ***!
  \***************************************************/
/*! exports provided: ForbiddenError */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "ForbiddenError", function() { return ForbiddenError; });
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_0__);

var ForbiddenError = /** @class */ (function () {
    function ForbiddenError(error) {
        toastr__WEBPACK_IMPORTED_MODULE_0__["warning"](error.json().message, '', {
            "timeOut": "0",
            "positionClass": "toast-top-center",
            "preventDuplicates": true,
            "extendedTimeOut": 0,
            "tapToDismiss": true
        });
    }
    return ForbiddenError;
}());



/***/ }),

/***/ "./src/app/services/error/internal-server-error.ts":
/*!*********************************************************!*\
  !*** ./src/app/services/error/internal-server-error.ts ***!
  \*********************************************************/
/*! exports provided: InternalServerError */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "InternalServerError", function() { return InternalServerError; });
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_0__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};


var InternalServerError = /** @class */ (function () {
    function InternalServerError(error) {
        toastr__WEBPACK_IMPORTED_MODULE_0__["warning"](error.json().message + ' <a href="mailto:metabolights-help@ebi.ac.uk">Contact us</a> if the problem persist.', "", {
            "timeOut": "0",
            "positionClass": "toast-bottom-right",
            "preventDuplicates": true,
            "extendedTimeOut": 0
        });
    }
    InternalServerError = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_1__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [Object])
    ], InternalServerError);
    return InternalServerError;
}());



/***/ }),

/***/ "./src/app/services/error/not-found-error.ts":
/*!***************************************************!*\
  !*** ./src/app/services/error/not-found-error.ts ***!
  \***************************************************/
/*! exports provided: NotFoundError */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "NotFoundError", function() { return NotFoundError; });
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! toastr */ "./node_modules/toastr/toastr.js");
/* harmony import */ var toastr__WEBPACK_IMPORTED_MODULE_0___default = /*#__PURE__*/__webpack_require__.n(toastr__WEBPACK_IMPORTED_MODULE_0__);

var NotFoundError = /** @class */ (function () {
    function NotFoundError(error) {
        toastr__WEBPACK_IMPORTED_MODULE_0__["warning"](error.json().message + ' <a href="mailto:metabolights-help@ebi.ac.uk">Contact us</a> if the problem persist.', "", {
            "timeOut": "0",
            "positionClass": "toast-top-center",
            "preventDuplicates": true,
            "extendedTimeOut": 0,
            "tapToDismiss": false
        });
    }
    return NotFoundError;
}());



/***/ }),

/***/ "./src/app/services/globals.ts":
/*!*************************************!*\
  !*** ./src/app/services/globals.ts ***!
  \*************************************/
/*! exports provided: MetaboLightsWSURL, DOIWSURL, EuropePMCURL, AuthenticationURL */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MetaboLightsWSURL", function() { return MetaboLightsWSURL; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DOIWSURL", function() { return DOIWSURL; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "EuropePMCURL", function() { return EuropePMCURL; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthenticationURL", function() { return AuthenticationURL; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");

var metaboLightsDomain = "https://wwwdev.ebi.ac.uk/metabolights";
var metaboLightsWSDomain = "";
if (!Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["isDevMode"])()) {
    metaboLightsWSDomain = "https://wwwdev.ebi.ac.uk/";
}
else {
    // metaboLightsDomain = "https://wwwdev.ebi.ac.uk/";
    metaboLightsWSDomain = "http://ves-ebi-90.ebi.ac.uk:5000/";
}
var MetaboLightsWSURL = {};
MetaboLightsWSURL['domain'] = metaboLightsWSDomain + "metabolights/";
// MetaboLightsWSURL['baseURL']			= metaboLightsDomain + "metabolights/swagger/ws"
MetaboLightsWSURL['baseURL'] = metaboLightsWSDomain + "metabolights/ws";
MetaboLightsWSURL['studiesList'] = MetaboLightsWSURL['baseURL'] + '/studies';
MetaboLightsWSURL['study'] = MetaboLightsWSURL['baseURL'] + '/studies';
MetaboLightsWSURL['validations'] = "assets/configs/config20180618/validations.json";
MetaboLightsWSURL['download'] = metaboLightsDomain + "/<study>/files";
var DOIWSURL = {};
DOIWSURL['article'] = "https://api.crossref.org/works/";
var EuropePMCURL = {};
EuropePMCURL['article'] = "https://www.ebi.ac.uk/europepmc/webservices/rest/search?query=<term>&format=json&resultType=core";
var AuthenticationURL = {};
AuthenticationURL['login'] = metaboLightsDomain + "/webservice/labs/authenticate";
AuthenticationURL['initialise'] = metaboLightsDomain + "/webservice/labs-workspace/initialise";
AuthenticationURL['studiesList'] = metaboLightsDomain + "/webservice/study/myStudies";


/***/ }),

/***/ "./src/app/services/headers.ts":
/*!*************************************!*\
  !*** ./src/app/services/headers.ts ***!
  \*************************************/
/*! exports provided: contentHeaders */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "contentHeaders", function() { return contentHeaders; });
/* harmony import */ var _angular_http__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/http */ "./node_modules/@angular/http/fesm5/http.js");

var contentHeaders = new _angular_http__WEBPACK_IMPORTED_MODULE_0__["Headers"]();
contentHeaders.append('Accept', 'application/json');
// contentHeaders.append('Content-Type', 'application/json');
contentHeaders.append('user_token', '');


/***/ }),

/***/ "./src/app/services/metabolights/auth.service.ts":
/*!*******************************************************!*\
  !*** ./src/app/services/metabolights/auth.service.ts ***!
  \*******************************************************/
/*! exports provided: AuthService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "AuthService", function() { return AuthService; });
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _headers__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./../headers */ "./src/app/services/headers.ts");
/* harmony import */ var _globals__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./../globals */ "./src/app/services/globals.ts");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
/* harmony import */ var _angular_http__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/http */ "./node_modules/@angular/http/fesm5/http.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_5___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_5__);
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};







var AuthService = /** @class */ (function () {
    function AuthService(router, http, ngRedux) {
        this.router = router;
        this.http = http;
        this.ngRedux = ngRedux;
        this.isLoggedIn = false;
        this.redirectUrl = '/console';
    }
    AuthService.prototype.login = function (body) {
        var _this = this;
        this.http.post(_globals__WEBPACK_IMPORTED_MODULE_2__["AuthenticationURL"]['login'], body, { headers: _headers__WEBPACK_IMPORTED_MODULE_1__["contentHeaders"] })
            .subscribe(function (response) {
            var body = {
                "jwt": response.headers.get("jwt"),
                "user": response.headers.get("user")
            };
            _this.http.post(_globals__WEBPACK_IMPORTED_MODULE_2__["AuthenticationURL"]['initialise'], body, { headers: _headers__WEBPACK_IMPORTED_MODULE_1__["contentHeaders"] })
                .subscribe(function (response) {
                var body = JSON.parse(response.json().content);
                var user = body.owner;
                _this.ngRedux.dispatch({ type: 'SET_USER', body: {
                        'user': user
                    } });
                localStorage.setItem('user', JSON.stringify(user));
                _headers__WEBPACK_IMPORTED_MODULE_1__["contentHeaders"].set('user_token', user.apiToken);
                _this.router.navigate([_this.redirectUrl]);
            }, function (error) {
                console.log(error.text());
            });
        }, function (error) {
            console.log(error.text());
        });
    };
    AuthService.prototype.logout = function () {
        localStorage.removeItem('user');
        this.router.navigate(['login']);
    };
    // Studies list
    AuthService.prototype.getUserStudies = function () {
        if (_headers__WEBPACK_IMPORTED_MODULE_1__["contentHeaders"]['api_token'] != '') {
            return this.http.get(_globals__WEBPACK_IMPORTED_MODULE_2__["AuthenticationURL"]['studiesList'], { headers: _headers__WEBPACK_IMPORTED_MODULE_1__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_6__["map"])(function (res) { return res.json(); }));
        }
    };
    AuthService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_router__WEBPACK_IMPORTED_MODULE_3__["Router"], _angular_http__WEBPACK_IMPORTED_MODULE_4__["Http"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_5__["NgRedux"]])
    ], AuthService);
    return AuthService;
}());



/***/ }),

/***/ "./src/app/services/metabolights/metabolights.service.ts":
/*!***************************************************************!*\
  !*** ./src/app/services/metabolights/metabolights.service.ts ***!
  \***************************************************************/
/*! exports provided: MetabolightsService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "MetabolightsService", function() { return MetabolightsService; });
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
/* harmony import */ var _globals__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./../globals */ "./src/app/services/globals.ts");
/* harmony import */ var _headers__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./../headers */ "./src/app/services/headers.ts");
/* harmony import */ var _data_service__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./../data.service */ "./src/app/services/data.service.ts");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular-redux/store */ "./node_modules/@angular-redux/store/lib/src/index.js");
/* harmony import */ var _angular_redux_store__WEBPACK_IMPORTED_MODULE_4___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_store__WEBPACK_IMPORTED_MODULE_4__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_5__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_http__WEBPACK_IMPORTED_MODULE_6__ = __webpack_require__(/*! @angular/http */ "./node_modules/@angular/http/fesm5/http.js");
/* harmony import */ var _angular_router__WEBPACK_IMPORTED_MODULE_7__ = __webpack_require__(/*! @angular/router */ "./node_modules/@angular/router/fesm5/router.js");
var __extends = (undefined && undefined.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};








var MetabolightsService = /** @class */ (function (_super) {
    __extends(MetabolightsService, _super);
    function MetabolightsService(http, ngRedux, router) {
        var _this = _super.call(this, _globals__WEBPACK_IMPORTED_MODULE_1__["MetaboLightsWSURL"], http) || this;
        _this.studyIdentifier.subscribe(function (value) { return _this.id = value; });
        return _this;
    }
    MetabolightsService.prototype.info = function () {
        return this.http.get(this.url.baseURL, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Studies list
    MetabolightsService.prototype.getAllStudies = function () {
        return this.http.get(this.url.studiesList, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study ISA object
    MetabolightsService.prototype.getStudy = function (id) {
        return this.http.get(this.url.studiesList + "/" + id, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study files
    MetabolightsService.prototype.getStudyFiles = function () {
        return this.http.get(this.url.studiesList + "/" + this.id + "/isa-tab/study_files", { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.downloadFile = function (name, code) {
        return this.http.get(this.url.download.replace('<study>', this.id) + "/" + name + "?token=" + code).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.copyFiles = function () {
        return this.http.get(this.url.studiesList + "/" + this.id + "/copy_from_upload_folder", { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study validation details
    MetabolightsService.prototype.getValidations = function () {
        return this.http.get(this.url.validations).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study title
    MetabolightsService.prototype.saveTitle = function (body) {
        return this.http.put(this.url.studiesList + "/" + this.id + "/title", body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study abstract
    MetabolightsService.prototype.saveAbstract = function (body) {
        return this.http.put(this.url.studiesList + "/" + this.id + "/description", body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study people
    MetabolightsService.prototype.getPeople = function () {
        return this.http.get(this.url.studiesList + "/" + this.id + "/contacts", { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.savePerson = function (body) {
        return this.http.post(this.url.studiesList + "/" + this.id + "/contacts", body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.updatePerson = function (email, body) {
        return this.http.put(this.url.studiesList + "/" + this.id + "/contacts?email=" + email, body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.deletePerson = function (email) {
        return this.http.delete(this.url.studiesList + "/" + this.id + "/contacts?email=" + email, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study Ontology
    MetabolightsService.prototype.getOntologyTerms = function (url) {
        return this.http.get(url, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study Publications
    MetabolightsService.prototype.getPublications = function () {
        return this.http.get(this.url.studiesList + "/" + this.id + "/publications", { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.savePublication = function (body) {
        return this.http.post(this.url.studiesList + "/" + this.id + "/publications", body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.updatePublication = function (title, body) {
        return this.http.put(this.url.studiesList + "/" + this.id + "/publications?title=" + title, body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.deletePublication = function (title) {
        return this.http.delete(this.url.studiesList + "/" + this.id + "/publications?title=" + title, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study Protocols
    MetabolightsService.prototype.getProtocols = function () {
        return this.http.get(this.url.studiesList + "/" + this.id + "/protocols", { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.saveProtocol = function (body) {
        return this.http.post(this.url.studiesList + "/" + this.id + "/protocols", body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.updateProtocol = function (title, body) {
        return this.http.put(this.url.studiesList + "/" + this.id + "/protocols?name=" + title, body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.deleteProtocol = function (title) {
        return this.http.delete(this.url.studiesList + "/" + this.id + "/protocols?name=" + title, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study design descriptors
    MetabolightsService.prototype.getDesignDescriptors = function () {
        return this.http.get(this.url.studiesList + "/" + this.id + "/descriptors", { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.saveDesignDescriptor = function (body) {
        return this.http.post(this.url.studiesList + "/" + this.id + "/descriptors", body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.updateDesignDescriptor = function (annotationValue, body) {
        return this.http.put(this.url.studiesList + "/" + this.id + "/descriptors?term=" + annotationValue, body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.deleteDesignDescriptor = function (annotationValue) {
        return this.http.delete(this.url.studiesList + "/" + this.id + "/descriptors?term=" + annotationValue, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study factors
    MetabolightsService.prototype.getFactors = function () {
        return this.http.get(this.url.studiesList + "/" + this.id + "/factors", { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.saveFactor = function (body) {
        return this.http.post(this.url.studiesList + "/" + this.id + "/factors", body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.updateFactor = function (factorName, body) {
        return this.http.put(this.url.studiesList + "/" + this.id + "/factors?name=" + factorName, body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.deleteFactor = function (factorName) {
        return this.http.delete(this.url.studiesList + "/" + this.id + "/factors?name=" + factorName, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study process sequences / Samples
    MetabolightsService.prototype.getProcessSequences = function () {
        return this.http.get(this.url.studiesList + "/" + this.id + "/processSequence?list_only=false", { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.getSamplesTable = function (samples_file_name) {
        return this.http.get(this.url.studiesList + "/" + this.id + "/samples/" + samples_file_name, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.saveSample = function (body) {
        return this.http.post(this.url.studiesList + "/" + this.id + "/samples", body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.addSamples = function (file, body) {
        return this.http.post(this.url.studiesList + "/" + this.id + "/samples/" + file, body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.deleteSamples = function (file, rows) {
        return this.http.delete(this.url.studiesList + "/" + this.id + "/samples/" + file + "?row_num=" + rows, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study assays
    MetabolightsService.prototype.getAssayTable = function (assay_file_name) {
        return this.http.get(this.url.studiesList + "/" + this.id + "/assay/" + assay_file_name, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.addAssayRow = function (assay_file_name, body) {
        return this.http.post(this.url.studiesList + "/" + this.id + "/assay/" + assay_file_name, body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.updateAssayRow = function (assay_file_name, body) {
        return this.http.put(this.url.studiesList + "/" + this.id + "/assay/" + assay_file_name, body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.deleteAssayRow = function (assay_file_name, rowIds) {
        return this.http.delete(this.url.studiesList + "/" + this.id + "/assay/" + assay_file_name + "?row_num=" + rowIds, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study MAF
    MetabolightsService.prototype.getMAF = function (annotation_file_name) {
        return this.http.get(this.url.studiesList + "/" + this.id + "/maf/" + annotation_file_name, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.getValidatedMAF = function (annotation_file_name, assay_file_name, technology) {
        return this.http.get(this.url.studiesList + "/" + this.id + "/maf/validated/" + annotation_file_name + "?assay_file_name=" + assay_file_name + "&technology=" + technology, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.updateMAFEntry = function (annotation_file_name, body) {
        return this.http.put(this.url.studiesList + "/" + this.id + "/maf/" + annotation_file_name, body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.addMAFEntry = function (annotation_file_name, body) {
        return this.http.post(this.url.studiesList + "/" + this.id + "/maf/" + annotation_file_name, body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.deleteMAFEntries = function (annotation_file_name, rowIds) {
        return this.http.delete(this.url.studiesList + "/" + this.id + "/maf/" + annotation_file_name + "?row_num=" + rowIds, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    MetabolightsService.prototype.search = function (term, type) {
        return this.http.get(this.url.studiesList.replace("/studies", "") + "/maf/search/" + type + "?search_value=" + term, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    // Study MAF
    MetabolightsService.prototype.addColumns = function (filename, body) {
        return this.http.post(this.url.studiesList + "/" + this.id + "/addColumns/" + filename, body, { headers: _headers__WEBPACK_IMPORTED_MODULE_2__["contentHeaders"] }).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return res.json(); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    __decorate([
        Object(_angular_redux_store__WEBPACK_IMPORTED_MODULE_4__["select"])(function (state) { return state.study.identifier; }),
        __metadata("design:type", Object)
    ], MetabolightsService.prototype, "studyIdentifier", void 0);
    MetabolightsService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_5__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_http__WEBPACK_IMPORTED_MODULE_6__["Http"], _angular_redux_store__WEBPACK_IMPORTED_MODULE_4__["NgRedux"], _angular_router__WEBPACK_IMPORTED_MODULE_7__["Router"]])
    ], MetabolightsService);
    return MetabolightsService;
}(_data_service__WEBPACK_IMPORTED_MODULE_3__["DataService"]));



/***/ }),

/***/ "./src/app/services/publications/doi.service.ts":
/*!******************************************************!*\
  !*** ./src/app/services/publications/doi.service.ts ***!
  \******************************************************/
/*! exports provided: DOIService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "DOIService", function() { return DOIService; });
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
/* harmony import */ var _globals__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./../globals */ "./src/app/services/globals.ts");
/* harmony import */ var _data_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./../data.service */ "./src/app/services/data.service.ts");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_http__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/http */ "./node_modules/@angular/http/fesm5/http.js");
var __extends = (undefined && undefined.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var DOIService = /** @class */ (function (_super) {
    __extends(DOIService, _super);
    function DOIService(http) {
        return _super.call(this, _globals__WEBPACK_IMPORTED_MODULE_1__["DOIWSURL"], http) || this;
    }
    DOIService.prototype.getArticleInfo = function (doi) {
        var _this = this;
        return this.http.get(this.url.article + doi).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return _this.extractArticleDetails(res.json()); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    DOIService.prototype.extractArticleDetails = function (data) {
        return {
            'title': data.message.title[0],
            'authorList': data.message.author.map(function (author) { return author.family + ' ' + author.given; }).join(', ')
        };
    };
    DOIService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_3__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_http__WEBPACK_IMPORTED_MODULE_4__["Http"]])
    ], DOIService);
    return DOIService;
}(_data_service__WEBPACK_IMPORTED_MODULE_2__["DataService"]));



/***/ }),

/***/ "./src/app/services/publications/europePMC.service.ts":
/*!************************************************************!*\
  !*** ./src/app/services/publications/europePMC.service.ts ***!
  \************************************************************/
/*! exports provided: EuropePMCService */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "EuropePMCService", function() { return EuropePMCService; });
/* harmony import */ var rxjs_operators__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! rxjs/operators */ "./node_modules/rxjs/_esm5/operators/index.js");
/* harmony import */ var _globals__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! ./../globals */ "./src/app/services/globals.ts");
/* harmony import */ var _data_service__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./../data.service */ "./src/app/services/data.service.ts");
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_http__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! @angular/http */ "./node_modules/@angular/http/fesm5/http.js");
var __extends = (undefined && undefined.__extends) || (function () {
    var extendStatics = function (d, b) {
        extendStatics = Object.setPrototypeOf ||
            ({ __proto__: [] } instanceof Array && function (d, b) { d.__proto__ = b; }) ||
            function (d, b) { for (var p in b) if (b.hasOwnProperty(p)) d[p] = b[p]; };
        return extendStatics(d, b);
    }
    return function (d, b) {
        extendStatics(d, b);
        function __() { this.constructor = d; }
        d.prototype = b === null ? Object.create(b) : (__.prototype = b.prototype, new __());
    };
})();
var __decorate = (undefined && undefined.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (undefined && undefined.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var EuropePMCService = /** @class */ (function (_super) {
    __extends(EuropePMCService, _super);
    function EuropePMCService(http) {
        return _super.call(this, _globals__WEBPACK_IMPORTED_MODULE_1__["EuropePMCURL"], http) || this;
    }
    EuropePMCService.prototype.getArticleInfo = function (doi) {
        var _this = this;
        return this.http.get(this.url.article.replace('<term>', doi)).pipe(Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["map"])(function (res) { return _this.extractArticleDetails(res.json(), doi); }), Object(rxjs_operators__WEBPACK_IMPORTED_MODULE_0__["catchError"])(this.handleError));
    };
    EuropePMCService.prototype.extractArticleDetails = function (data, doi) {
        var article = data.resultList.result[0];
        return {
            'title': article.title,
            'authorList': article.authorString,
            'pubMedID': article.pmid,
            'doi': article.doi,
            'abstract': article.abstractText
        };
    };
    EuropePMCService = __decorate([
        Object(_angular_core__WEBPACK_IMPORTED_MODULE_3__["Injectable"])({
            providedIn: 'root'
        }),
        __metadata("design:paramtypes", [_angular_http__WEBPACK_IMPORTED_MODULE_4__["Http"]])
    ], EuropePMCService);
    return EuropePMCService;
}(_data_service__WEBPACK_IMPORTED_MODULE_2__["DataService"]));



/***/ }),

/***/ "./src/app/store.ts":
/*!**************************!*\
  !*** ./src/app/store.ts ***!
  \**************************/
/*! exports provided: INITIAL_STATE, rootReducer */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "INITIAL_STATE", function() { return INITIAL_STATE; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "rootReducer", function() { return rootReducer; });
/* harmony import */ var redux__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! redux */ "./node_modules/redux/es/redux.js");
/* harmony import */ var _angular_redux_router__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular-redux/router */ "./node_modules/@angular-redux/router/lib/es5/src/index.js");
/* harmony import */ var _angular_redux_router__WEBPACK_IMPORTED_MODULE_1___default = /*#__PURE__*/__webpack_require__.n(_angular_redux_router__WEBPACK_IMPORTED_MODULE_1__);
/* harmony import */ var _components_study_store__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./components/study/store */ "./src/app/components/study/store.ts");
/* harmony import */ var _components_shared_store__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./components/shared/store */ "./src/app/components/shared/store.ts");




var INITIAL_STATE = {
    study: _components_study_store__WEBPACK_IMPORTED_MODULE_2__["STUDY_INITIAL_STATE"],
    status: _components_shared_store__WEBPACK_IMPORTED_MODULE_3__["SHARED_INITIAL_STATE"]
};
var rootReducer = Object(redux__WEBPACK_IMPORTED_MODULE_0__["combineReducers"])({
    study: _components_study_store__WEBPACK_IMPORTED_MODULE_2__["studyReducer"],
    status: _components_shared_store__WEBPACK_IMPORTED_MODULE_3__["sharedReducer"],
    router: _angular_redux_router__WEBPACK_IMPORTED_MODULE_1__["routerReducer"]
});


/***/ }),

/***/ "./src/environments/environment.ts":
/*!*****************************************!*\
  !*** ./src/environments/environment.ts ***!
  \*****************************************/
/*! exports provided: environment */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "environment", function() { return environment; });
// This file can be replaced during build by using the `fileReplacements` array.
// `ng build ---prod` replaces `environment.ts` with `environment.prod.ts`.
// The list of file replacements can be found in `angular.json`.
var environment = {
    production: false
};
/*
 * In development mode, to ignore zone related error stack frames such as
 * `zone.run`, `zoneDelegate.invokeTask` for easier debugging, you can
 * import the following file, but please comment it out in production mode
 * because it will have performance impact when throw error
 */
// import 'zone.js/dist/zone-error';  // Included with Angular CLI.


/***/ }),

/***/ "./src/main.ts":
/*!*********************!*\
  !*** ./src/main.ts ***!
  \*********************/
/*! no exports provided */
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
__webpack_require__.r(__webpack_exports__);
/* harmony import */ var _angular_core__WEBPACK_IMPORTED_MODULE_0__ = __webpack_require__(/*! @angular/core */ "./node_modules/@angular/core/fesm5/core.js");
/* harmony import */ var _angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__ = __webpack_require__(/*! @angular/platform-browser-dynamic */ "./node_modules/@angular/platform-browser-dynamic/fesm5/platform-browser-dynamic.js");
/* harmony import */ var _app_app_module__WEBPACK_IMPORTED_MODULE_2__ = __webpack_require__(/*! ./app/app.module */ "./src/app/app.module.ts");
/* harmony import */ var _environments_environment__WEBPACK_IMPORTED_MODULE_3__ = __webpack_require__(/*! ./environments/environment */ "./src/environments/environment.ts");
/* harmony import */ var hammerjs__WEBPACK_IMPORTED_MODULE_4__ = __webpack_require__(/*! hammerjs */ "./node_modules/hammerjs/hammer.js");
/* harmony import */ var hammerjs__WEBPACK_IMPORTED_MODULE_4___default = /*#__PURE__*/__webpack_require__.n(hammerjs__WEBPACK_IMPORTED_MODULE_4__);





if (_environments_environment__WEBPACK_IMPORTED_MODULE_3__["environment"].production) {
    Object(_angular_core__WEBPACK_IMPORTED_MODULE_0__["enableProdMode"])();
}
Object(_angular_platform_browser_dynamic__WEBPACK_IMPORTED_MODULE_1__["platformBrowserDynamic"])().bootstrapModule(_app_app_module__WEBPACK_IMPORTED_MODULE_2__["AppModule"])
    .catch(function (err) { return console.log(err); });


/***/ }),

/***/ 0:
/*!***************************!*\
  !*** multi ./src/main.ts ***!
  \***************************/
/*! no static exports found */
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(/*! /Users/venkata/Development/metabolomics/metabolights-client/src/main.ts */"./src/main.ts");


/***/ })

},[[0,"runtime","vendor"]]]);
//# sourceMappingURL=main.js.map