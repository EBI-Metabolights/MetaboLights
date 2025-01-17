/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 8/13/13 2:24 PM
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

package uk.ac.ebi.metabolights.service;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Provides a map of country codes and countries. Can be used in a select tag in HTML.  
 * @author markr
 *
 */
public class CountryService {

	
	private final static Map<String, String> countries;
	static { 
		countries = new LinkedHashMap<String,String>();

        countries.put ("","                  ");
		countries.put ("AF","Afghanistan");
        //countries.put ("AX","Åland Islands");
        countries.put ("AL","Albania");
        countries.put ("DZ","Algeria");
        countries.put ("AS","American Samoa");
        countries.put ("AD","Andorra");
        countries.put ("AO","Angola");
        countries.put ("AI","Anguilla");
        countries.put ("AQ","Antarctica");
        countries.put ("AG","Antigua and Barbuda");
        countries.put ("AR","Argentina");
        countries.put ("AM","Armenia");
        countries.put ("AW","Aruba");
        countries.put ("AU","Australia");
        countries.put ("AT","Austria");
        countries.put ("AZ","Azerbaijan");
        countries.put ("BS","Bahamas");
        countries.put ("BH","Bahrain");
        countries.put ("BD","Bangladesh");
        countries.put ("BB","Barbados");
        countries.put ("BY","Belarus");
        countries.put ("BE","Belgium");
        countries.put ("BZ","Belize");
        countries.put ("BJ","Benin");
        countries.put ("BM","Bermuda");
        countries.put ("BT","Bhutan");
        countries.put ("BO","Bolivia");
        countries.put ("BA","Bosnia and Herzegovina");
        countries.put ("BW","Botswana");
        countries.put ("BV","Bouvet Island");
        countries.put ("BR","Brazil");
        //countries.put ("IO","British Indian Ocean Territory");
        countries.put ("BN","Brunei Darussalam");
        countries.put ("BG","Bulgaria");
        countries.put ("BF","Burkina Faso");
        countries.put ("BI","Burundi");
        countries.put ("KH","Cambodia");
        countries.put ("CM","Cameroon");
        countries.put ("CA","Canada");
        countries.put ("CV","Cape Verde");
        countries.put ("KY","Cayman Islands");
        countries.put ("CF","Central African Republic");
        countries.put ("TD","Chad");
        countries.put ("CL","Chile");
        countries.put ("CN","China");
        countries.put ("CX","Christmas Island");
        countries.put ("CC","Cocos (Keeling) Islands");
        countries.put ("CO","Colombia");
        countries.put ("KM","Comoros");
        countries.put ("CG","Congo, Republic of The");
        countries.put ("CD","Congo, The Democratic Republic of The");
        countries.put ("CK","Cook Islands");
        countries.put ("CR","Costa Rica");
        countries.put ("CI","Cote D'ivoire");
        countries.put ("HR","Croatia");
        countries.put ("CU","Cuba");
        countries.put ("CY","Cyprus");
        countries.put ("CZ","Czech Republic");
        countries.put ("DK","Denmark");
        countries.put ("DJ","Djibouti");
        countries.put ("DM","Dominica");
        countries.put ("DO","Dominican Republic");
        countries.put ("EC","Ecuador");
        countries.put ("EG","Egypt");
        countries.put ("SV","El Salvador");
        countries.put ("GQ","Equatorial Guinea");
        countries.put ("ER","Eritrea");
        countries.put ("EE","Estonia");
        countries.put ("ET","Ethiopia");
        countries.put ("FK","Falkland Islands (Malvinas)");
        countries.put ("FO","Faroe Islands");
        countries.put ("FJ","Fiji");
        countries.put ("FI","Finland");
        countries.put ("FR","France");
        countries.put ("GF","French Guiana");
        countries.put ("PF","French Polynesia");
        countries.put ("TF","French Southern Territories");
        countries.put ("GA","Gabon");
        countries.put ("GM","Gambia");
        countries.put ("GE","Georgia");
        countries.put ("DE","Germany");
        countries.put ("GH","Ghana");
        countries.put ("GI","Gibraltar");
        countries.put ("GR","Greece");
        countries.put ("GL","Greenland");
        countries.put ("GD","Grenada");
        countries.put ("GP","Guadeloupe");
        countries.put ("GU","Guam");
        countries.put ("GT","Guatemala");
        countries.put ("GG","Guernsey");
        countries.put ("GN","Guinea");
        countries.put ("GW","Guinea-bissau");
        countries.put ("GY","Guyana");
        countries.put ("HT","Haiti");
        //countries.put ("HM","Heard Island and Mcdonald Islands");
        //countries.put ("VA","Holy See (Vatican City State)");
        countries.put ("HN","Honduras");
        countries.put ("HK","Hong Kong");
        countries.put ("HU","Hungary");
        countries.put ("IS","Iceland");
        countries.put ("IN","India");
        countries.put ("ID","Indonesia");
        countries.put ("IR","Iran, Islamic Republic of");
        countries.put ("IQ","Iraq");
        countries.put ("IE","Ireland");
        countries.put ("IM","Isle of Man");
        countries.put ("IL","Israel");
        countries.put ("IT","Italy");
        countries.put ("JM","Jamaica");
        countries.put ("JP","Japan");
        countries.put ("JE","Jersey");
        countries.put ("JO","Jordan");
        countries.put ("KZ","Kazakhstan");
        countries.put ("KE","Kenya");
        countries.put ("KI","Kiribati");
        countries.put ("KP","Korea, Democratic People's Republic of");
        countries.put ("KR","Korea, Republic of");
        countries.put ("KW","Kuwait");
        countries.put ("KG","Kyrgyzstan");
        countries.put ("LA","Lao People's Democratic Republic");
        countries.put ("LV","Latvia");
        countries.put ("LB","Lebanon");
        countries.put ("LS","Lesotho");
        countries.put ("LR","Liberia");
        countries.put ("LY","Libyan Arab Jamahiriya");
        countries.put ("LI","Liechtenstein");
        countries.put ("LT","Lithuania");
        countries.put ("LU","Luxembourg");
        countries.put ("MO","Macao");
        countries.put ("MK","Macedonia, The Former Yugoslav Republic of");
        countries.put ("MG","Madagascar");
        countries.put ("MW","Malawi");
        countries.put ("MY","Malaysia");
        countries.put ("MV","Maldives");
        countries.put ("ML","Mali");
        countries.put ("MT","Malta");
        countries.put ("MH","Marshall Islands");
        countries.put ("MQ","Martinique");
        countries.put ("MR","Mauritania");
        countries.put ("MU","Mauritius");
        countries.put ("YT","Mayotte");
        countries.put ("MX","Mexico");
        countries.put ("FM","Micronesia, Federated States of");
        countries.put ("MD","Moldova, Republic of");
        countries.put ("MC","Monaco");
        countries.put ("MN","Mongolia");
        countries.put ("ME","Montenegro");
        countries.put ("MS","Montserrat");
        countries.put ("MA","Morocco");
        countries.put ("MZ","Mozambique");
        countries.put ("MM","Myanmar");
        countries.put ("NA","Namibia");
        countries.put ("NR","Nauru");
        countries.put ("NP","Nepal");
        countries.put ("NL","Netherlands");
        countries.put ("AN","Netherlands Antilles");
        countries.put ("NC","New Caledonia");
        countries.put ("NZ","New Zealand");
        countries.put ("NI","Nicaragua");
        countries.put ("NE","Niger");
        countries.put ("NG","Nigeria");
        countries.put ("NU","Niue");
        countries.put ("NF","Norfolk Island");
        //countries.put ("MP","Northern Mariana Islands");
        countries.put ("NO","Norway");
        countries.put ("OM","Oman");
        countries.put ("PK","Pakistan");
        countries.put ("PW","Palau");
        countries.put ("PS","State of Palestine");
        countries.put ("PA","Panama");
        countries.put ("PG","Papua New Guinea");
        countries.put ("PY","Paraguay");
        countries.put ("PE","Peru");
        countries.put ("PH","Philippines");
        countries.put ("PN","Pitcairn");
        countries.put ("PL","Poland");
        countries.put ("PT","Portugal");
        countries.put ("PR","Puerto Rico");
        countries.put ("QA","Qatar");
        countries.put ("RE","Reunion");
        countries.put ("RO","Romania");
        countries.put ("RU","Russian Federation");
        countries.put ("RW","Rwanda");
        countries.put ("SH","Saint Helena");
        countries.put ("KN","Saint Kitts and Nevis");
        countries.put ("LC","Saint Lucia");
        countries.put ("PM","Saint Pierre and Miquelon");
        countries.put ("VC","Saint Vincent and The Grenadines");
        countries.put ("WS","Samoa");
        countries.put ("SM","San Marino");
        countries.put ("ST","Sao Tome and Principe");
        countries.put ("SA","Saudi Arabia");
        countries.put ("SN","Senegal");
        countries.put ("RS","Serbia");
        countries.put ("SC","Seychelles");
        countries.put ("SL","Sierra Leone");
        countries.put ("SG","Singapore");
        countries.put ("SK","Slovakia");
        countries.put ("SI","Slovenia");
        countries.put ("SB","Solomon Islands");
        countries.put ("SO","Somalia");
        countries.put ("ZA","South Africa");
        countries.put ("GS","South Georgia and The South Sandwich Islands");
        countries.put ("ES","Spain");
        countries.put ("LK","Sri Lanka");
        countries.put ("SD","Sudan");
        countries.put ("SR","Suriname");
        countries.put ("SJ","Svalbard and Jan Mayen");
        countries.put ("SZ","Swaziland");
        countries.put ("SE","Sweden");
        countries.put ("CH","Switzerland");
        countries.put ("SY","Syrian Arab Republic");
//        countries.put ("TW","Taiwan, Province of China");
        countries.put ("TW","Taiwan");
        countries.put ("TJ","Tajikistan");
        countries.put ("TZ","Tanzania, United Republic of");
        countries.put ("TH","Thailand");
        countries.put ("TL","Timor-leste");
        countries.put ("TG","Togo");
        countries.put ("TK","Tokelau");
        countries.put ("TO","Tonga");
        countries.put ("TT","Trinidad and Tobago");
        countries.put ("TN","Tunisia");
        countries.put ("TR","Turkey");
        countries.put ("TM","Turkmenistan");
        countries.put ("TC","Turks and Caicos Islands");
        countries.put ("TV","Tuvalu");
        countries.put ("UG","Uganda");
        countries.put ("UA","Ukraine");
        countries.put ("AE","United Arab Emirates");
        countries.put ("GB","United Kingdom");
        countries.put ("US","United States");
        //countries.put ("UM","United States Minor Outlying Islands");
        countries.put ("UY","Uruguay");
        countries.put ("UZ","Uzbekistan");
        countries.put ("VU","Vanuatu");
        countries.put ("VE","Venezuela");
        countries.put ("VN","Viet Nam");
        countries.put ("VG","Virgin Islands, British");
        countries.put ("VI","Virgin Islands, U.S.");
        countries.put ("WF","Wallis and Futuna");
        countries.put ("EH","Western Sahara");
        countries.put ("YE","Yemen");
        countries.put ("ZM","Zambia");
        countries.put ("ZW","Zimbabwe");
	}

	public static Map<String, String> getCountries() {
		return countries;
	}

	public static String lookupCountry (String countryCode ) {
		return countries.get(countryCode);
	}
}
