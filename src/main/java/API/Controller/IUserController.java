package API.Controller;

import API.DataLayer.IUserDTO;
import org.json.JSONArray;
import org.json.JSONObject;

public interface IUserController {
    /** @author Claes and Simon
     *  The purpose of this class is error handeling,
     *  and as a controller it is naturally the thing responsable for
     *  the communication between 'view'part(the part that the User of the API sees) and
     *  the model part. It have holds the overview and directs data as a
     *  crazy control freak, who does not want any of its emploiess to talk
     *  to anyone else of its emploiess. So that it have full control of all
     *  communication thi-hii #writingDocumentationCanBeFun
     * @param username The Id of the User we desire userdata from
     * @return a User convert to a object that can be used in the local code
     */
    JSONObject convertUser(String username);

    JSONObject createUser(JSONObject input);

    JSONObject userValidation(JSONObject input);

    JSONObject setElo(JSONObject input);

    JSONArray getTopTen();

    JSONObject deleteUser(JSONObject input);
}
