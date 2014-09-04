/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 9/3/14 5:44 PM
 * Modified by:   conesa
 *
 *
 * ©, EMBL, European Bioinformatics Institute, 2014.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software distributed under the License is distributed on an "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the License for the specific language governing permissions and limitations under the License.
 */

/**
 * Created by conesa on 03/09/2014.
 */

function lazyload() {

    // Search for elements with lazyload attributte to add icons
    $("[lazyload]").append('<span class="icon icon-functional" data-icon="8">');

    // Search for elements with lazyload attributte
    $("[lazyload]").on ("click", function (e){

        // Get the target
        var target = e.currentTarget;

        // Get the url to load
        var url = $(target).attr("lazyload");

        // If it's empty
        if (url == "") return;

        // If not empty...
        // Get the data at the url
        $.ajax(url, {dataType:"html"}).done
            ( function(data, textStatus, jqXHR)
              {

                  $(target).find("span").remove();
                  $(target).after(data);
              }
            );

        // remove the atribute
        $(target).attr("lazyload", "");

    })


}
