package Controller;

import DataLayer.IUserDTO;

public interface IUserController {
    /** @author Claes
     *  The purpose of this class is error handeling,
     *  and as a controller it is naturally the thing responsable for
     *  the communication between 'view'part(the part that the User of the API sees) and
     *  the model part. It have holds the overview and directs data as a
     *  crazy control freak, who does not want any of its emploiess to talk
     *  to anyone else of its emploiess. So that it have full control of all
     *  communication thi-hii #writingDocumentationCanBeFun
     * @param id The Id of the User we desire userdata from
     * @return a User convert to a object that can be used in the local code
     */
    public IUserDTO convertUser(int id);

    public String checkScore(int id, int score);

    public String createUser(String username, String password);
}
