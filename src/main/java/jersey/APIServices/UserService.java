package jersey.APIServices;

import Controller.IUserController;
import Controller.UserController;
import DataLayer.IUserDAO;
import DataLayer.IUserDTO;
import DataLayer.UserDTO;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;

/**
 * @author claes
 * ======================================================
 *          IF(YOU ARE A USER){WELCOME!!!!}
 * ======================================================
 *  - As a User of the API this is
 *  the the most usefull part for you
 *  - Here you fint the services the API provides
 */
@Path("/service")
public class UserService {

    @GET
    @Produces("TEXT/HTML")
    public String publicServiceMessage(){
        return "|||||||||||||||||||||<br>\n" +
                "||API User Manual:||<br>\n" +
                "|||||||||||||||||||||<br>\n" +
                "||<br>\n" +
                "=================================||<br>\n" +
                "Things are working, DON'T WORRY!,||<br>\n" +
                "=================================||<br>\n" +
                "||<br>\n" +
                "However you have couple of options ||<br>\n" +
                "(1.) [GET DATA FROM A USER]: ||<br>\n " +
                "you need to enter a number in the URL. \r Try for example 'service/1 ||<br>\n" +
                "(2.) {INSERT A NEW SCORE MADE BY A USER} ||<br>\n " +
                "Try for example 'service/1/[the score you want to insert] ||<br>\n" +
                "(3.) [CREATE USER] <br>\n" +
                "Protocol note made yet.<br>\n" +
                "<br>\n" +
                "CONGRATS YOU ALSO FOUND AN EASTER EGG, MAY KING TUT BE WITH YOU!!!!!!"  +
                " _<br>\n" +
                " ------------------------------,d8b,<br>\n" +
                " --------------------- _,,aadd8888888bbaa,,_<br>\n" +
                " ------------------_,ad88P\"\"\"8,  I8I  ,8\"\"\"Y88ba,_<br>\n" +
                " -----------------,ad88P\" `Ya  `8, `8' ,8'  aP' \"Y88ba,<br>\n" +
                " --------------,d8\"' \"Yb   \"b, `b  8  d' ,d\"   dP\" `\"8b,<br>\n" +
                " ------------ dP\"Yb,  `Yb,  `8, 8  8  8 ,8'  ,dP'  ,dP\"Yb<br>\n" +
                "-----------,ad8b, `Yb,  \"Ya  `b Y, 8 ,P d'  aP\"  ,dP' ,d8ba,<br>\n" +
                " --------- dP\" `Y8b, `Yb, `Yb, Y,`8 8 8',P ,dP' ,dP' ,d8P' \"Yb<br>\n" +
                " ---------,88888888Yb, `Yb,`Yb,`8 8 8 8 8',dP',dP' ,dY88888888,<br>\n" +
                " ---------dP     `Yb`Yb, Yb,`8b 8 8 8 8 8 d8',dP ,dP'dP'     Yb<br>\n" +
                "---------,8888888888b \"8, Yba888888888888888adP ,8\" d8888888888,<br>\n" +
                " --------dP        `Yb,`Y8P\"\"' ---------`\"\"Y8P',dP'        Yb<br>\n" +
                " ------,88888888888P\"Y8P'_.---.._     _..---._`Y8P\"Y88888888888,<br>\n" +
                "       dP         d'  8 '  ____  `. .'  ____  ` 8  `b         Yb<br>\n" +
                "      ,888888888888   8   <(@@)>  | |  <(@@)>   8   888888888888,<br>\n" +
                "      dP--------- 8   8    `\"\"\" ----- \"\"\"'    8   8          Yb<br>\n" +
                "     ,8888888888888,  8 ----------,---,--------- 8  ,8888888888888,<br>\n" +
                "     dP -----------`b  8,---------(.-_-.)--------,8  d'-----------Yb<br>\n" +
                "    ,88888888888888Yaa8b-------.' ------`.-------d8aaP88888888888888,<br>\n" +
                "    dP               \"\"8b     _,gd888bg,_     d8\"\"               Yb<br>\n" +
                "   ,888888888888888888888b,    \"\"Y888P\"\"    ,d888888888888888888888,<br>\n" +
                "   dP                   \"8\"b,------------ ,d\"8\"                   Yb<br>\n" +
                "  ,888888888888888888888888,\"Ya,_,ggg,_,aP\",888888888888888888888888,<br>\n" +
                "  dP                      \"8,  \"8\"\\x/\"8\"  ,8\"                      Yb<br>\n" +
                " ,88888888888888888888888888b   8\\\\x//8   d88888888888888888888888888,<br>\n" +
                " 8888bgg,_                  8   8\\\\x//8   8                  _,ggd8888<br>\n" +
                "  `\"Yb, \"\"8888888888888888888   Y\\\\x//P   8888888888888888888\"\" ,dP\"'<br>\n" +
                "    _d8bg,_\"8,              8   `b\\x/d'   8              ,8\"_,gd8b_<br>\n" +
                "  ,iP\"   \"Yb,8888888888888888    8\\x/8    8888888888888888,dP\"  `\"Yi,<br>\n" +
                " ,P\"    __,888              8    8\\x/8    8              888,__    \"Y,<br>\n" +
                ",8baaad8P\"\":Y8888888888888888 aaa8\\x/8aaa 8888888888888888P:\"\"Y8baaad8,<br>\n" +
                "dP\"':::::::::8              8 8::8\\x/8::8 8              8:::::::::`\"Yb<br>\n" +
                "8::::::::::::8888888888888888 8::88888::8 8888888888888888::::::::::::8<br>\n" +
                "8::::::::::::8,             8 8:::::::::8 8             ,8::::::::::::8<br>\n" +
                "8::::::::::::8888888888888888 8:::::::::8 8888888888888888::::::::::::8<br>\n" +
                "8::::::::::::Ya             8 8:::::::::8 8             aP::::::::::::8<br>\n" +
                "8:::::::::::::888888888888888 8:::::::::8 888888888888888:::::::::::::8<br>\n" +
                "8:::::::::::::Ya            8 8:::::::::8 8            aP:::::::::::::8<br>\n" +
                "Ya:::::::::::::88888888888888 8:::::::::8 88888888888888:::::::::::::aP<br>\n" +
                "`8;:::::::::::::Ya,         8 8:::::::::8 8         ,aP:::::::::::::;8'<br>\n" +
                " Ya:::::::::::::::\"Y888888888 8:::::::::8 888888888P\":::::::::::::::aP<br>\n" +
                " `8;::::::::::::::::::::\"\"\"\"Y8888888888888P\"\"\"\"::::::::::::::::::::;8'<br>\n" +
                "  Ya:::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::::aP<br>\n" +
                "   \"b;::::::::::::::::::::::::::::::::::::::::::: Normand  ::::::;d\"<br>\n" +
                "    `Ya;::::::::::::::::::::::::::::::::::::::::: Veilleux ::::;aP'<br>\n" +
                "      `Ya;:::::::::::::::::::::::::::::::::::::::::::::::::::;aP'<br>\n" +
                "         \"Ya;:::::::::::::::::::::::::::::::::::::::::::::;aP\"<br>\n" +
                "            \"Yba;;;:::::::::::::::::::::::::::::::::;;;adP\"<br>\n" +
                "                `\"\"\"\"\"\"\"Y888888888888888888888P\"\"\"\"\"\"\"'";
    }

    /**This function sendes'rest-request'.
     * This returns data on a desired user
     * @param id the user whos data you want
     * @return All the data there is on a given user.
     */
    @Path("/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public IUserDTO requestUser(@PathParam("id") int id){
        IUserController userController = new UserController();
        IUserDTO user = userController.convertUser(id);
        return user;
    }

}
