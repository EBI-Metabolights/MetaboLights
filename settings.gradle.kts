/*
 * This file was generated by the Gradle 'init' task.
 *
 * This project uses @Incubating APIs which are subject to change.
 */

rootProject.name = "metabolights-main"
include(":species-model")
include(":metabolights-search")
include(":metabolights-referencelayer-dao")
include(":service-es")
// include(":metabolights-mztab")
include(":metabolights-species")
// include(":metabolights-sampledb")
include(":metabolights-domain")
include(":species-service")
include(":metabolights-dao")
include(":species-core")
include(":globalnames-client")
include(":search-service")
include(":WoRMS-client")
include(":metabolights-webapp")
include(":metabolights-isatab-utils")
include(":metabolights-referencelayer")
include(":species-dao")
include(":metabolights-export")
include(":metabolights-webservice")
include(":referencelayer-importer")
include(":metabolights-referencelayer-domain")
include(":metabolights-ws-client")
include(":metabolights-utils")
include(":species-search")
project(":species-model").projectDir = file("metabolights-species/species-model")
project(":metabolights-referencelayer-dao").projectDir = file("metabolights-referencelayer/dao")
project(":service-es").projectDir = file("metabolights-search/service-es")
// project(":metabolights-mztab").projectDir = file("metabolights-utils/metabolights-mztab")
// project(":metabolights-sampledb").projectDir = file("metabolights-utils/metabolights-sampledb")
project(":species-service").projectDir = file("metabolights-species/species-service")
project(":species-core").projectDir = file("metabolights-species/species-core")
project(":globalnames-client").projectDir = file("metabolights-species/globalnames-client")
project(":search-service").projectDir = file("metabolights-search/service")
project(":WoRMS-client").projectDir = file("metabolights-referencelayer/WoRMS-client")
project(":metabolights-webapp").projectDir = file("metabolights-webapps")
project(":metabolights-isatab-utils").projectDir = file("metabolights-utils/metabolights-isatab-utils")
project(":species-dao").projectDir = file("metabolights-species/species-dao")
project(":metabolights-export").projectDir = file("metabolights-utils/metabolights-export")
project(":referencelayer-importer").projectDir = file("metabolights-referencelayer/importer")
project(":metabolights-referencelayer-domain").projectDir = file("metabolights-referencelayer/domain")
project(":species-search").projectDir = file("metabolights-species/species-search")