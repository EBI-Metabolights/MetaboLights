/*
 * EBI MetaboLights - http://www.ebi.ac.uk/metabolights
 * Cheminformatics and Metabolism group
 *
 * European Bioinformatics Institute (EMBL-EBI), European Molecular Biology Laboratory, Wellcome Trust Genome Campus, Hinxton, Cambridge CB10 1SD, United Kingdom
 *
 * Last modified: 7/8/14 4:27 PM
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

package uk.ac.ebi.metabolights.controller;


import com.fasterxml.jackson.databind.util.JSONPObject;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.xmlsoap.schemas.soap.encoding.Integer;
import uk.ac.ebi.metabolights.model.MLStats;
import uk.ac.ebi.metabolights.service.AppContext;
import uk.ac.ebi.metabolights.service.MetaboLightsStatsService;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Controller for login and related actions.
 *
 * @author Pablo
 */
@Controller
public class StatisticsController extends AbstractController {

    @Autowired
    private MetaboLightsStatsService metaboLightsStatsService;

    @RequestMapping({"/statistics", "/stats"})
    public ModelAndView showStatistics() {

        ModelAndView mav = AppContext.getMAVFactory().getFrontierMav("statistics"); //name of jsp page must be same as this

        //General stats, number of....
        List<MLStats> dataList = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.DATA.getValue());
        //Which databases the compound/database acc was identified in
        List<MLStats> idList = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.IDENTIFIED.getValue());
        //How many submitters, reviewers, curators
        List<MLStats> subList = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.SUBMITTERS.getValue());
        List<MLStats> topSubList = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.TOPSUBM.getValue());

        //General info
        List<MLStats> infoList = metaboLightsStatsService.getByPageCategory(MLStats.PageCategory.INFO.getValue());


        mav.addObject("dataList", dataList);
        mav.addObject("identifierList", idList);
        mav.addObject("submittersList", subList);
        mav.addObject("topSubList", topSubList);
        mav.addObject("infoList", infoList);
        mav.addObject("statsJson",constructJSONForStatsGraph());

        return mav;
    }


    private JSONArray constructJSONForStatsGraph() {
        JSONArray statsArray = new JSONArray();

        JSONObject barChartValues = new JSONObject();
        JSONObject lineChartValues = new JSONObject();

        JSONArray array1 = getDataArray(MLStats.PageCategory.STATNUMBER.getValue(),false);
        JSONArray array2 = getDataArray(MLStats.PageCategory.STATSIZE.getValue(),true);


        try {
            barChartValues.put("key", "Study size");
            barChartValues.put("bar", true);
            barChartValues.put("values",array2);

            lineChartValues.put("key", "Number of studies");
            lineChartValues.put("values",array1);

            statsArray.put(barChartValues);
            statsArray.put(lineChartValues);

        } catch (JSONException e) {
            e.printStackTrace();
        }
        return statsArray;

    }

    private JSONArray getDataArray(String pageCategory, boolean convert) {

        JSONArray array = new JSONArray();
        List<MLStats> list = metaboLightsStatsService.getByPageCategory(pageCategory);
        for (MLStats mlStats : list) {
            JSONArray internalArray = new JSONArray();
            DateFormat formatter = new SimpleDateFormat("yyyy-MM");                   //yyyy-MM-dd HH:mm:ss
            try {
                Date date = formatter.parse(mlStats.getDisplayName());
                internalArray.put(date.getTime());

                if(convert){
                    int size = java.lang.Integer.parseInt(mlStats.getDisplayValue());
                    internalArray.put(size/1048576);
                }
                else{
                    internalArray.put(java.lang.Integer.parseInt(mlStats.getDisplayValue()));
                }

            } catch (ParseException e) {
                e.printStackTrace();
            }

            array.put(internalArray);
        }
        return array;
    }
}
