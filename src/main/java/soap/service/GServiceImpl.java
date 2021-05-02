package soap.service;

import soap.game.Corridors;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.xml.ws.Endpoint;

@WebService
public class GServiceImpl implements GService {
    public static final int port = 8080;
    private int currUserId = 0;
    private Corridors game = new Corridors(2);
    private int turnId = 1;


    @WebMethod
    @Override
    public int auth() {
        if (currUserId == 2) {
            System.out.println("Attempt to connect to a full server");
            return 100;
        }
        System.out.println((currUserId + 1) + " player has been connected");
        return currUserId++;
    }


    @WebMethod
    @Override
    public int getPlayersNum() {
        return currUserId;
    }


    @WebMethod
    @Override
    public void printField() {
        game.printField();
    }


    @WebMethod
    @Override
    public Boolean currentTurn(int id) {
        return turnId == id;
    }


    @WebMethod
    @Override
    public void makeTurn(int x, int y, int border, int id) {
        Boolean startStatus = game.isDoneCheck(x, y);
        if (game.setBorder(x, y, border) != startStatus) {
            game.setPlayer(x, y, id);
        }
        switch (border) {
            case 0:
                if (y != 0) {
                    startStatus = game.isDoneCheck(x, y - 1);
                    if (game.setBorder(x, y - 1, 1) != startStatus) {
                        game.setPlayer(x, y - 1, id);
                    }
                }
                break;
            case 1:
                if (y != game.getSize() - 1) {
                    startStatus = game.isDoneCheck(x, y + 1);
                    if (game.setBorder(x, y + 1, 0) != startStatus) {
                        game.setPlayer(x, y + 1, id);
                    }
                }
                break;
            case 2:
                if (x != 0) {
                    startStatus = game.isDoneCheck(x - 1, y);
                    if (game.setBorder(x - 1, y, 3) != startStatus) {
                        game.setPlayer(x - 1, y, id);
                    }
                }
                break;
            case 3:
                if (x != game.getSize() - 1) {
                    startStatus = game.isDoneCheck(x + 1, y);
                    if (game.setBorder(x + 1, y, 2) != startStatus) {
                        game.setPlayer(x + 1, y, id);
                    }
                }
                break;
        }
        turnId = 1 - turnId;
    }


    @WebMethod
    @Override
    public Boolean isGameEnd() {
        return game.isGameEnd();
    }


    public int[] calculateResults() {
        int[] res = new int[] {0, 0};
        for (int i = 0; i < game.getSize(); i++) {
            for (int j = 0; j < game.getSize(); j++) {
                res[game.getPlayer(i, j)] += 1;
            }
        }
        return res;
    }


    public static void main(String[] args) {
        GServiceImpl service = new GServiceImpl();
        String url = String.format("http://localhost:%d/", port);
        Endpoint.publish(url, service);
        System.out.println("\nWSDL: " + "http://localhost:8080/ws/?wsdl");
    }
}
