package soap.cilent;

import soap.service.GService;

import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;
import java.util.concurrent.TimeUnit;


class Utils {
    public static void waitAnim() throws InterruptedException {
        for (int i = 0; i < 3; i++) {
            System.out.print(". ");
            TimeUnit.SECONDS.sleep(1);
        }
        for (int i = 0; i < 6; i++) {
            System.out.print("\b");
        }
        TimeUnit.SECONDS.sleep(1);
        for (int i = 0; i < 24; i++) {
            System.out.print("\b");
        }
    }
}


public class Client {

    private static javax.xml.ws.Service getService() throws MalformedURLException {
        QName qName = new QName("http://service.soap/", "GServiceImplService");
        URL url = new URL("http://localhost:8080/ws/?wsdl");
        return javax.xml.ws.Service.create(url, qName);
    }


    private static void clientAuth(GService gService) throws IOException, InterruptedException {
        int id = gService.auth();
        if (id < 2) {
            System.out.println("Hello, you are " + (id + 1) + " player!");
            start(id, gService);
        } else {
            System.out.println("Server is full :(");
            System.exit(-1);
        }
    }


    public static int[] getInput() {
        Scanner scanner = new Scanner(System.in);
        System.out.println();
        System.out.print("Enter x: ");
        int x = scanner.nextInt();
        System.out.println();
        System.out.print("Enter y: ");
        int y = scanner.nextInt();
        System.out.println();
        System.out.print("Enter border id\n(0 - left, 1 - right, 2 - up, 3 - down): ");
        int border = scanner.nextInt();
        return new int[] {x, y, border};
    }


    private static void start(int id, GService gService) throws IOException, InterruptedException {
        while (gService.getPlayersNum() < 2) {
            System.out.print("Waiting a second player ");
            Utils.waitAnim();
        }
        System.out.print("\nGame started!\n");
        while (!gService.isGameEnd()) {
            while (!gService.currentTurn(id)) {
                System.out.print("You turn");
                int[] input = getInput();
                gService.makeTurn(input[0], input[1], input[2], id);
                System.out.println("Turn taken, waiting a second player");
                gService.printField();
            }
        }
        int[] results = gService.calculateResults();
        int winnerId = results[0] > results[1] ? 0 : 1;
        if (id == winnerId) {
            System.out.println(String.format("You are win with a score %d - %d!!!", results[id], results[1 - id]));
        } else {
            System.out.println(String.format("You are lose with a score %d - %d :(", results[id], results[1 - id]));
        }
    }


    public static void main(String[] args) throws IOException, InterruptedException {
        Service service = getService();
        GService corridorsService = service.getPort(new QName("http://service.soap/", "GServiceImplPort"), GService.class);
        clientAuth(corridorsService);
    }
}
