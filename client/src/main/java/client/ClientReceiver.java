package client;

import input.Creator;
import input.Typer;
import input.Validator;
import listening.Request;

import java.util.Optional;
import java.util.ResourceBundle;

public class ClientReceiver {

    private final Creator creator;
    private final ResourceBundle RB = ResourceBundle.getBundle("client");

    public ClientReceiver() {
        this.creator = new Creator(new Typer());
    }

    public Optional<Request> removeLower() {
        System.out.println(RB.getString("askRL"));
        return Optional.of(new Request("remove_lower", creator.createCity()));
    }

    public Optional<Request> removeGreater() {
        System.out.println(RB.getString("askRG"));
        return Optional.of(new Request("remove_greater", creator.createCity()));
    }

    public Optional<Request> addIfMin() {
        System.out.println(RB.getString("askAIM"));
        return Optional.of(new Request("add_if_min", creator.createCity()));
    }

    public Optional<Request> add() {
        return Optional.of(new Request("add", creator.createCity()));
    }

    public Optional<Request> update(String arg) {
        Long id = Validator.validateId(arg);
        System.out.println(RB.getString("askUpd"));
        return Optional.of(new Request("update", arg, creator.createCity()));
    }

    public Optional<Request> removeById(String arg) {
        Long id = Validator.validateId(arg);
        if (id == null) {
            System.out.println(RB.getString("invalidId"));
            return Optional.empty();
        }
        return Optional.of(new Request("remove_by_id", arg));
    }

}
