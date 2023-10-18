
plugins {
    id("uk.ac.ebi.metabolights.java-conventions")
}


dependencies {
    api(fileTree("legacy"))
}
repositories {
    flatDir {
        dirs("legacy")
    }
}

description = "MetaboLights Legacy Libs"
