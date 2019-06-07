package Controller;

import DataLayer.IUserDTO;

public interface IUserController {
    /** @author Claes
     *  The purpose of this class is error handeling,
     * @param id The Id of the User we desire userdata from
     * @return a User convert to a object that can be used in the local code
     */
    public IUserDTO convertUser(int id);

    public String checkScore(int id, int score);
}
