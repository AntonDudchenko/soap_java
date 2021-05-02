package soap.service;

import javax.jws.WebMethod;
import javax.jws.WebService;


@WebService
public interface GService {
    @WebMethod
    public int auth();

    @WebMethod
    public int getPlayersNum();

    @WebMethod
    public void printField();

    @WebMethod
    public Boolean isGameEnd();

    @WebMethod
    public Boolean currentTurn(int id);

    @WebMethod
    public void makeTurn(int x, int y, int border, int id);

    @WebMethod
    public int[] calculateResults();
}
