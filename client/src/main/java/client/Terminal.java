package client;

import clientLogger.ClientLogger;
import commands.ExecuteScript;
import listening.Request;
import listening.Response;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.logging.*;

public class Terminal {

    private final Logger LOGGER = ClientLogger.getLogger();
    private final ResourceBundle RB = ResourceBundle.getBundle("client");

    Scanner scanner;
    private final ClientInvoker clientInvoker;
    private final Client client;
    private String login = "";
    private String password = "";

    public Terminal(ClientInvoker clientInvoker, Client client) {
        this.clientInvoker = clientInvoker;
        this.client = client;
    }

    public void startFile(String filename) {
        System.out.println(RB.getString("startFile") + filename);
        setScanner(filename);
        if (scanner == null) {
            System.out.println(RB.getString("fileNotFound"));
            return;
        }

        while (scanner.hasNext()) {
            String line = scanner.nextLine();
            Optional<Request> optRequest = lineHandler(line);
            if (!optRequest.isPresent()) {
                System.out.println(
                        RB.getString("executing") + filename + " " + RB.getString("recoursiveLine") + " " + line);
                return;
            } else {
                Request request = optRequest.get();
                if (request.getCommandName().equals("execute_script")) {
                    startFile(request.getArgument());
                    continue;
                }
                request.setLogin(login);
                request.setPassword(password);
                client.send(request);
                Optional<Response> optResponse = client.recieve();
                if (!optResponse.isPresent()) {
                    LOGGER.log(Level.SEVERE, RB.getString("brokenExecute"), new RuntimeException());
                } else {
                    Response response = optResponse.get();
                    responseProcessing(response);
                }
            }
        }
        System.out.println(RB.getString("executing") + filename + RB.getString("ended"));
    }

    public void startKeyboard() {

        scanner = new Scanner(System.in);

        greeting();

        while (true) {
            System.out.print(RB.getString("inputCommand") + "\n>");
            String line = scanner.nextLine();

            Optional<Request> optRequest = lineHandler(line);
            if (optRequest.isPresent()) {

                Request request = optRequest.get();
                if (request.getCommandName().equals("execute_script")) {
                    startFile(request.getArgument());
                    ExecuteScript.clearPaths();
                    scanner = new Scanner(System.in);
                    continue;
                }

                request.setLogin(login);
                request.setPassword(password);
                client.send(request);

                Optional<Response> optResponse = client.recieve();
                if (optResponse.isPresent()) {
                    Response response = optResponse.get();
                    responseProcessing(response);
                }

            }
        }
    }

    private Optional<Request> lineHandler(String line) throws NullPointerException {

        while (line.contains("  ")) {
            line = line.replace("  ", " ");
        }
        String[] commandLine = line.split(" ");
        String command = commandLine[0].trim();
        if (command.equals("authorization")) {
            authorization();
            System.out.println(RB.getString("auth") + login);
            return Optional.empty();
        }
        if (commandLine.length == 1) {
            return clientInvoker.check(command, null);
        }
        if (commandLine.length == 2) {
            return clientInvoker.check(command, commandLine[1]);
        }
        return Optional.empty();
    }

    private void authorization() {
        System.out.print(RB.getString("inpLogin") + "\n>");
        login = scanner.nextLine();
        System.out.print(RB.getString("inpPassword") + "\n>");
        password = scanner.nextLine();
        Request authRequest = new Request("authorization");
        authRequest.setLogin(login);
        authRequest.setPassword(password);
        client.send(authRequest);
        Optional<Response> optResponse = client.recieve();
        if (optResponse.isPresent()) {
            Response response = optResponse.get();
            if (!response.getMessage().isEmpty()) {
                System.out.println(response.getMessage());
                authorization();
            }
        }
    }

    private void greeting() {
        System.out.println(RB.getString("greeting"));
        while (true) {
            System.out.print(">");
            String ans = scanner.nextLine().trim().toLowerCase(Locale.ROOT);
            if (ans.equals(RB.getString("yes"))) {
                authorization();
                System.out.println(RB.getString("auth") + login);
                return;
            } else if (ans.equals(RB.getString("no"))) {
                System.out.println(RB.getString("auth") + RB.getString("guest"));
                return;
            }
            System.out.println(RB.getString("yes") + "/" + RB.getString("no"));
        }
    }

    private void setScanner(String filename) {
        File file = new File(filename).getAbsoluteFile();
        try {
            scanner = new Scanner(file);
        } catch (FileNotFoundException ignored) {
            scanner = null;
        }
    }

    private void responseProcessing(Response response) {
        if (response.getAnswer() == null) {
            System.out.println(response.getMessage());
        } else {
            for (String ans : response.getAnswer()) {
                System.out.println(ans);
            }
        }
    }

}
