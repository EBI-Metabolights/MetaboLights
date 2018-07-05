webpackJsonp([1,4],{

/***/ 144:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(30)(false);
// imports


// module
exports.push([module.i, "a {color:green;}\n", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ 145:
/***/ (function(module, exports, __webpack_require__) {

exports = module.exports = __webpack_require__(30)(false);
// imports


// module
exports.push([module.i, "", ""]);

// exports


/*** EXPORTS FROM exports-loader ***/
module.exports = module.exports.toString();

/***/ }),

/***/ 146:
/***/ (function(module, exports) {

module.exports = "<div class=\"panel panel-info\">\n  <div class=\"panel-heading\">\n    <h5 class=\"panel-title\">ORCID search\n    </h5>\n  </div>\n  <div class=\"panel-body\">\n    <div class=\"row form-group\">\n      <div class=\"col-md-6 col-xs-8\">\n        <input type=\"text\" class=\"form-control\" [formControl]=\"searchEditForm.controls['searchterm']\"\n               [(ngModel)]=\"freeText\"\n               name=\"freeText\"\n               placeholder=\"Enter ORCID. Example: 0000-0001-8604-1732\"\n               (keyup.enter)=\"onSearch(freeText)\"\n               (keydown)=\"doUpdate()\" (keyup)=\"doUpdate()\" pattern=\"\\d\\d\\d\\d-\\d\\d\\d\\d-\\d\\d\\d\\d-\\d\\d\\d\\d\">\n        <div *ngIf=\"searchEditForm.controls['searchterm'].hasError('pattern')\" class=\"alert alert-danger\">Invalid ORCID - Example: 0000-0001-8604-1732\n        </div>\n      </div>\n      <div class=\"col-md-6 col-xs-4\">\n        <button type=\"button\" class=\"btn btn-primary\" (click)=\"onSearch(freeText)\" [disabled]=\"!searchEditForm.valid\">\n          Search <i\n          class=\"fa fa-search\"></i>\n        </button>\n      </div>\n  </div>\n    <!--<br>-->\n    <!--Batch claiming link-->\n    <!--<div class=\"col-md-4 col-xs-4\">-->\n      <!--<a href=\"{{batchClaimURL}}\" class=\"btn btn-default pull-center\"-->\n         <!--role=\"button\" target=\"_blank\"><span style=\"color:green;\"><i style=\"height: 15px;-->\n    <!--width: 15px;\">-->\n                            <!--<img src=\"//www.ebi.ac.uk/europepmc/thor/resources/orcid-id.png\" value=\"What is ORCID?\"-->\n                                 <!--width=\"15\" height=\"15\" data-pin-nopin=\"true\">-->\n                        <!--</i>&nbsp;Batch Claim studies to ORCID</span></a>-->\n    <!--</div>-->\n\n    <br>\n\n    <div class=\"row\" *ngIf=\"searching && !resolved\">\n      <p align=\"center\" class=\"text-primary\"><label>Loading please wait </label> &nbsp;&nbsp; <i\n        class=\"fa fa-spinner fa-spin\"\n        style=\"font-size:24px\"></i>\n      </p>\n    </div>\n\n    <div class=\"row\" *ngIf=\"noMTBLSButOtherResults\">\n      <p align=\"center\" class=\"text-primary\"><label>No matching results found in MetaboLights.</label>\n      </p>\n      <br>\n      <p align=\"center\">\n        <a href=\"//www.ebi.ac.uk/ebisearch/search.ebi?db=allebi&query={{freeText}}&requestFrom=searchBox\"\n           class=\"btn btn-primary pull-center\"\n           role=\"button\" target=\"_blank\"><span style=\"color:white;\">Look up EMBL-EBI wide results</span></a>\n      </p>\n    </div>\n\n    <div class=\"row\" *ngIf=\"noResults\">\n      <p align=\"center\" class=\"text-primary\"><label>No results found. Please check your input!</label>\n      </p>\n    </div>\n\n    <div *ngIf=\"mtbls_ids.length > 0\">\n      <template ngFor let-mtblsID [ngForOf]=\"mtbls_ids\" let-i=\"index\">\n        <app-mtbls-entry [studyID]='mtblsID'></app-mtbls-entry>\n      </template>\n    </div>\n\n  </div>\n</div>\n\n\n"

/***/ }),

/***/ 147:
/***/ (function(module, exports) {

module.exports = "<div class=\"row\" style=\"padding: 30px 30px 30px 30px;\">\n  <div class=\"panel panel-default\">\n    <div class=\"panel-heading\">\n      <h6 class=\"panel-title\">\n        <strong><span style=\"color:grey\">{{studyID}} - </span></strong>\n        <span style=\"color:grey\">{{title}}</span>\n      </h6>\n    </div>\n    <div class=\"panel-body\">\n      <span style=\"color:grey\">{{description}}</span>\n    </div>\n    <div class=\"panel-footer\">\n      <a href=\"//www.ebi.ac.uk/metabolights/{{studyID}}#orcidPopover\" class=\"btn btn-default pull-center\"\n         role=\"button\" target=\"_blank\"><span style=\"color:green;\"><i style=\"height: 15px;\n    width: 15px;\">\n                            <img src=\"//www.ebi.ac.uk/europepmc/thor/resources/orcid-id.png\" value=\"What is ORCID?\"\n                                 width=\"15\" height=\"15\" data-pin-nopin=\"true\">\n                        </i><span *ngIf=\"claimed; else elseBlock\"><i class=\"fa fa-check\" aria-hidden=\"true\"></i>&nbsp;Claimed</span>\n        <ng-template #elseBlock>&nbsp;Claim {{studyID}} to ORCID</ng-template>\n      </span></a>\n    </div>\n  </div>\n</div>\n"

/***/ }),

/***/ 178:
/***/ (function(module, exports, __webpack_require__) {

module.exports = __webpack_require__(82);


/***/ }),

/***/ 55:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_http__ = __webpack_require__(23);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "b", function() { return contentHeaders; });
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return simpleHeaders; });
/**
 * Created by kalai on 25/10/2017.
 */

var contentHeaders = new __WEBPACK_IMPORTED_MODULE_0__angular_http__["d" /* Headers */]();
contentHeaders.append('Accept', 'application/json');
contentHeaders.append('Content-Type', 'application/json');
contentHeaders.append('user_token', '');
var simpleHeaders = new __WEBPACK_IMPORTED_MODULE_0__angular_http__["d" /* Headers */]();
simpleHeaders.append('Accept', 'application/json');
//# sourceMappingURL=headers.js.map

/***/ }),

/***/ 81:
/***/ (function(module, exports) {

function webpackEmptyContext(req) {
	throw new Error("Cannot find module '" + req + "'.");
}
webpackEmptyContext.keys = function() { return []; };
webpackEmptyContext.resolve = webpackEmptyContext;
module.exports = webpackEmptyContext;
webpackEmptyContext.id = 81;


/***/ }),

/***/ 82:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
Object.defineProperty(__webpack_exports__, "__esModule", { value: true });
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__ = __webpack_require__(86);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__app_app_module__ = __webpack_require__(88);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__environments_environment__ = __webpack_require__(90);




if (__WEBPACK_IMPORTED_MODULE_3__environments_environment__["a" /* environment */].production) {
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["a" /* enableProdMode */])();
}
__webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__angular_platform_browser_dynamic__["a" /* platformBrowserDynamic */])().bootstrapModule(__WEBPACK_IMPORTED_MODULE_2__app_app_module__["a" /* AppModule */]);
//# sourceMappingURL=main.js.map

/***/ }),

/***/ 87:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__(23);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__ = __webpack_require__(150);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map___default = __webpack_require__.n(__WEBPACK_IMPORTED_MODULE_2_rxjs_add_operator_map__);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_forms__ = __webpack_require__(54);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__common_headers__ = __webpack_require__(55);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};





var AppComponent = (function () {
    function AppComponent(http, fb) {
        this.http = http;
        this.searching = false;
        this.resolved = false;
        this.somethingWrong = false;
        this.noResults = false;
        this.noMTBLSButOtherResults = false;
        this.orcidDevSearchURL = "//www.ebi.ac.uk/ebisearch/ws/rest/europepmc?query=ORCID:";
        this.orcidSearchFormat = "&format=json";
        this.pmidDevSearchURL = "//www.ebi.ac.uk/ebisearch/ws/rest/europepmc/entry/";
        this.pmidSearchFormat = "/xref/metabolights?format=json";
        this.batchClaimURL = "//www.ebi.ac.uk/ebisearch/search.ebi?db=metabolights&query=domain_source:metabolights";
        this.mtblsISAEndpoint = "//www.ebi.ac.uk/metabolights/SwaggerProxy?url=mtbls/ws/studies/";
        this.apiToken = localStorage.getItem("apiToken");
        this.mtbls_ids = [];
        this.title = "";
        this.description = "";
        this.freeText = "";
        this.orcidPatternRegex = "\d\d\d\d-\d\d\d\d-\d\d\d\d-\d\d\d\d";
        this.ebiSearchResponse = "";
        this.formBuilder = fb;
        this.searchEditForm = this.formBuilder.group({
            'searchterm': [this.freeText, __WEBPACK_IMPORTED_MODULE_3__angular_forms__["c" /* Validators */].compose([__WEBPACK_IMPORTED_MODULE_3__angular_forms__["c" /* Validators */].required, __WEBPACK_IMPORTED_MODULE_3__angular_forms__["c" /* Validators */].minLength(19)])]
        });
    }
    AppComponent.prototype.onSearch = function (freeText) {
        var _this = this;
        this.searching = true;
        this.isValid(freeText);
        var ebiORCIDsearchQuery = this.orcidDevSearchURL + freeText + "&start=0&size=99" + this.orcidSearchFormat;
        this.getSimpleRequest(ebiORCIDsearchQuery)
            .subscribe(function (response) {
            console.log(response);
            if (response.length != 0) {
                if (response['hitCount'] > 0) {
                    var pmids = _this.extractPMIDS(response);
                    _this.searchForPMIDS(pmids, freeText);
                }
                else {
                    _this.noResults = true;
                    _this.resolved = true;
                }
            }
        }, function (error) {
            console.log(error);
            _this.somethingWrong = true;
        });
    };
    AppComponent.prototype.searchForPMIDS = function (pmids, freetext) {
        var _this = this;
        var searchString = this.constructSearchString(pmids);
        var ebiPMIDsearchQuery = this.pmidDevSearchURL + searchString + this.pmidSearchFormat;
        this.getSimpleRequest(ebiPMIDsearchQuery)
            .subscribe(function (response) {
            console.log(response);
            if (response.length != 0) {
                _this.ebiSearchResponse = response;
                //todo place extract mtbls method here
                _this.queryMetabolights(freetext);
            }
            else {
                _this.resolved = true;
            }
        }, function (error) {
            console.log(error);
            _this.somethingWrong = true;
            _this.resolved = true;
        });
    };
    AppComponent.prototype.doUpdate = function () {
        this.somethingWrong = false;
        this.searching = false;
        this.resolved = false;
        this.mtbls_ids = [];
        this.noResults = false;
        this.noMTBLSButOtherResults = false;
    };
    AppComponent.prototype.getSimpleRequest = function (url) {
        return this.http.get(url, { headers: __WEBPACK_IMPORTED_MODULE_4__common_headers__["a" /* simpleHeaders */] }).map(function (res) { return res.json(); });
    };
    AppComponent.prototype.getRequest = function (url, apiToken) {
        __WEBPACK_IMPORTED_MODULE_4__common_headers__["b" /* contentHeaders */].delete('user_token');
        __WEBPACK_IMPORTED_MODULE_4__common_headers__["b" /* contentHeaders */].append('user_token', apiToken);
        return this.http.get(url, { headers: __WEBPACK_IMPORTED_MODULE_4__common_headers__["b" /* contentHeaders */] }).map(function (res) { return res.json(); });
    };
    AppComponent.prototype.extractPMIDS = function (searchResponse) {
        var entries = searchResponse["entries"];
        var pmids = [];
        if (entries.length > 0) {
            for (var i in entries) {
                var id = entries[i]["id"];
                pmids.push(id);
            }
        }
        return pmids;
    };
    AppComponent.prototype.constructSearchString = function (pmids) {
        var searchString = "";
        if (pmids.length == 1) {
            return pmids[0];
        }
        for (var i in pmids) {
            searchString += pmids[i] + ",";
        }
        searchString = searchString.slice(0, -1);
        return searchString;
    };
    AppComponent.prototype.extractMtblsIDs = function (searchResponse) {
        var entries = searchResponse["entries"];
        for (var i in entries) {
            var references = entries[i]["references"];
            if (references.length > 0) {
                for (var j in references) {
                    var mtbls_id = references[j]["id"];
                    this.mtbls_ids.push(mtbls_id);
                }
            }
        }
        if (this.mtbls_ids.length == 0) {
            this.noMTBLSButOtherResults = true;
        }
        this.resolved = true;
    };
    AppComponent.prototype.getStudyTitle = function (studyID) {
        var _this = this;
        var fetchTitleUrl = this.mtblsISAEndpoint + studyID + "/title";
        var title = "";
        this.getRequest(fetchTitleUrl, this.apiToken).subscribe(function (response) {
            _this.title = response['Study-title'];
        }, function (error) {
            console.log(error);
        });
    };
    AppComponent.prototype.getStudyDescription = function (studyID) {
        var _this = this;
        var fetchDescriptionUrl = this.mtblsISAEndpoint + studyID + "/description";
        var description = "";
        this.getRequest(fetchDescriptionUrl, this.apiToken).subscribe(function (response) {
            _this.description = response['Study-description'];
        }, function (error) {
            console.log(error);
        });
    };
    AppComponent.prototype.isValid = function (orcid) {
        return orcid.match(this.orcidPatternRegex);
    };
    AppComponent.prototype.queryMetabolights = function (orcid) {
        var _this = this;
        var fetchIDsUrl = "//www.ebi.ac.uk/ebisearch/ws/rest/metabolights?query=ORCID:" + orcid + "&format=json";
        this.getSimpleRequest(fetchIDsUrl).subscribe(function (response) {
            _this.extractMtblsIDs(_this.ebiSearchResponse);
            if (response['hitCount'] != 0) {
                var entries = response['entries'];
                for (var i in entries) {
                    var mtbls_id = entries[i]['id'];
                    console.log("from ML search = " + mtbls_id);
                    var foundAlready = _this.mtbls_ids.indexOf(mtbls_id) > -1;
                    if (!foundAlready) {
                        _this.mtbls_ids.push(mtbls_id);
                    }
                }
            }
        }, function (error) {
            console.log(error);
        });
    };
    return AppComponent;
}());
AppComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_5" /* Component */])({
        selector: 'mtbls-adv-search',
        template: __webpack_require__(146),
        styles: [__webpack_require__(144)]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_http__["c" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_http__["c" /* Http */]) === "function" && _a || Object, typeof (_b = typeof __WEBPACK_IMPORTED_MODULE_3__angular_forms__["d" /* FormBuilder */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_3__angular_forms__["d" /* FormBuilder */]) === "function" && _b || Object])
], AppComponent);

var _a, _b;
//# sourceMappingURL=app.component.js.map

/***/ }),

/***/ 88:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__ = __webpack_require__(24);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_core__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__angular_forms__ = __webpack_require__(54);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_3__angular_http__ = __webpack_require__(23);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_4__app_component__ = __webpack_require__(87);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_5__mtbls_entry_mtbls_entry_component__ = __webpack_require__(89);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return AppModule; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};






var AppModule = (function () {
    function AppModule() {
    }
    return AppModule;
}());
AppModule = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_1__angular_core__["b" /* NgModule */])({
        declarations: [
            __WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */],
            __WEBPACK_IMPORTED_MODULE_5__mtbls_entry_mtbls_entry_component__["a" /* MtblsEntryComponent */]
        ],
        imports: [
            __WEBPACK_IMPORTED_MODULE_0__angular_platform_browser__["a" /* BrowserModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["a" /* FormsModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_http__["a" /* HttpModule */],
            __WEBPACK_IMPORTED_MODULE_2__angular_forms__["b" /* ReactiveFormsModule */],
            __WEBPACK_IMPORTED_MODULE_3__angular_http__["b" /* JsonpModule */]
        ],
        providers: [],
        bootstrap: [__WEBPACK_IMPORTED_MODULE_4__app_component__["a" /* AppComponent */]]
    })
], AppModule);

//# sourceMappingURL=app.module.js.map

/***/ }),

/***/ 89:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_0__angular_core__ = __webpack_require__(5);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_1__angular_http__ = __webpack_require__(23);
/* harmony import */ var __WEBPACK_IMPORTED_MODULE_2__common_headers__ = __webpack_require__(55);
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return MtblsEntryComponent; });
var __decorate = (this && this.__decorate) || function (decorators, target, key, desc) {
    var c = arguments.length, r = c < 3 ? target : desc === null ? desc = Object.getOwnPropertyDescriptor(target, key) : desc, d;
    if (typeof Reflect === "object" && typeof Reflect.decorate === "function") r = Reflect.decorate(decorators, target, key, desc);
    else for (var i = decorators.length - 1; i >= 0; i--) if (d = decorators[i]) r = (c < 3 ? d(r) : c > 3 ? d(target, key, r) : d(target, key)) || r;
    return c > 3 && r && Object.defineProperty(target, key, r), r;
};
var __metadata = (this && this.__metadata) || function (k, v) {
    if (typeof Reflect === "object" && typeof Reflect.metadata === "function") return Reflect.metadata(k, v);
};



var MtblsEntryComponent = (function () {
    function MtblsEntryComponent(http) {
        this.http = http;
        this.claimed = false;
    }
    MtblsEntryComponent.prototype.ngOnInit = function () {
        this.getTitle(this.studyID);
        this.getDescription(this.studyID);
        this.isClaimedToORCID(this.studyID);
    };
    MtblsEntryComponent.prototype.getTitle = function (studyID) {
        var _this = this;
        var fetchTitleUrl = "//www.ebi.ac.uk/ebisearch/ws/rest/metabolights?query=id:" + studyID + "&fields=id,name&format=json";
        this.getSimpleRequest(fetchTitleUrl).subscribe(function (response) {
            if (response['hitCount'] == 1) {
                var entries = response["entries"];
                for (var i in entries) {
                    var entry = entries[i];
                    var titles = entry['fields']['name'];
                    if (titles.length > 0) {
                        _this.title = titles[0];
                    }
                }
            }
        }, function (error) {
            console.log(error);
        });
    };
    MtblsEntryComponent.prototype.getDescription = function (studyID) {
        var _this = this;
        var fetchTitleUrl = "//www.ebi.ac.uk/ebisearch/ws/rest/metabolights?query=id:" + studyID + "&fields=id,description&format=json";
        this.getSimpleRequest(fetchTitleUrl).subscribe(function (response) {
            if (response['hitCount'] == 1) {
                var entries = response["entries"];
                for (var i in entries) {
                    var entry = entries[i];
                    var descriptions = entry['fields']['description'];
                    if (descriptions.length > 0) {
                        _this.description = descriptions[0];
                    }
                }
            }
        }, function (error) {
            console.log(error);
        });
    };
    MtblsEntryComponent.prototype.getSimpleRequest = function (url) {
        return this.http.get(url, { headers: __WEBPACK_IMPORTED_MODULE_2__common_headers__["a" /* simpleHeaders */] }).map(function (res) { return res.json(); });
    };
    MtblsEntryComponent.prototype.isClaimedToORCID = function (studyID) {
        var _this = this;
        var claimCheckUrl = "//www.ebi.ac.uk/ebisearch/ws/rest/orcid_data_claims?query=METABOLIGHTS:" + this.studyID + "&format=JSON";
        this.getSimpleRequest(claimCheckUrl).subscribe(function (response) {
            if (response['hitCount'] != 0) {
                _this.claimed = true;
            }
        }, function (error) {
            console.log(error);
        });
    };
    return MtblsEntryComponent;
}());
__decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["O" /* Input */])(),
    __metadata("design:type", String)
], MtblsEntryComponent.prototype, "studyID", void 0);
MtblsEntryComponent = __decorate([
    __webpack_require__.i(__WEBPACK_IMPORTED_MODULE_0__angular_core__["_5" /* Component */])({
        selector: 'app-mtbls-entry',
        template: __webpack_require__(147),
        styles: [__webpack_require__(145)]
    }),
    __metadata("design:paramtypes", [typeof (_a = typeof __WEBPACK_IMPORTED_MODULE_1__angular_http__["c" /* Http */] !== "undefined" && __WEBPACK_IMPORTED_MODULE_1__angular_http__["c" /* Http */]) === "function" && _a || Object])
], MtblsEntryComponent);

var _a;
//# sourceMappingURL=mtbls-entry.component.js.map

/***/ }),

/***/ 90:
/***/ (function(module, __webpack_exports__, __webpack_require__) {

"use strict";
/* harmony export (binding) */ __webpack_require__.d(__webpack_exports__, "a", function() { return environment; });
var environment = {
    production: true
};
//# sourceMappingURL=environment.js.map

/***/ })

},[178]);
//# sourceMappingURL=main.bundle.js.map