/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 8/21/14 1:32 PM
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

function color_for_atom(formulaChar) {
    // based on jmol colors: http://jmol.sourceforge.net/jscolors/
    if (formulaChar == 'C') color = "909090";
    else if (formulaChar == 'N') color = "3050F8";
    else if (formulaChar == 'O') color = "FF0D0D";
    else color = "000000";

    return color;
}

(function ($) {

    $.fn.formularize = function () {
        return this.each(
            function () {
                var formulaetext = '';

                // get the current text inside element
                var text = $(this).text();

                // iterate the whole 360 degrees
                for (var i = 0; i < text.length; i++) {
                    formulaetext = formulaetext + '<span style="color:#' + color_for_atom(text.charAt(i)) + '">' + text.charAt(i) + '</span>';

                }

                $(this).html(formulaetext);
            });
    };
})(jQuery);
