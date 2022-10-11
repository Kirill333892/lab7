package input;

import base.*;

public class Creator {

    private final Typer typer;

    public Creator(Typer typer) {
        this.typer = typer;
    }

    public City createCity() {

        String name = typer.typeName();

        Coordinates coordinates = typer.typeCoordinates();

        float area = typer.typeArea();

        int population = typer.typePopulation();

        float masl = typer.typeMetersAboveSeaLevel();

        StandardOfLiving standardOfLiving = typer.typeStandardOfLiving();

        Human governor = typer.typeHuman();

        return new City(name, coordinates, area, population, masl, standardOfLiving, governor);
    }

}
