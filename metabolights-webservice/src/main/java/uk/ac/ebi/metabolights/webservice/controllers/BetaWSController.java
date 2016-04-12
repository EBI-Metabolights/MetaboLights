package uk.ac.ebi.metabolights.webservice.controllers;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import uk.ac.ebi.metabolights.referencelayer.IDAO.DAOException;
import uk.ac.ebi.metabolights.repository.dao.filesystem.MetCompoundDAO;

/**
 *
 * Created by venkata on 06/04/2016.
 */
@Controller
@RequestMapping("beta")
public class BetaWSController extends BasicController {

    private static Logger logger = LoggerFactory.getLogger(CompoundController.class);

    public static final String METABOLIGHTS_COMPOUND_ID_REG_EXP = "(?:MTBLC|mtblc).+";
    public static final String COMPOUND_VAR = "compoundId";
    public static final String COMPOUND_MAPPING = "/compound/{" + COMPOUND_VAR + ":" + METABOLIGHTS_COMPOUND_ID_REG_EXP + "}";

    @RequestMapping(value = COMPOUND_MAPPING)
    @ResponseBody
    public String getCompound(@PathVariable(COMPOUND_VAR) String compoundId) throws DAOException {

        MetCompoundDAO metCompoundDAO = new MetCompoundDAO();

        return metCompoundDAO.getCompoundData(compoundId);

    }


}
