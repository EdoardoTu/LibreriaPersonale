package Command;

import BackEnd.Libro;
import BackEnd.Template;

public class AggiornaCommand extends undoComune {


    private Libro aggiornato;

    public AggiornaCommand(Template r, Libro aggiornato) {
        super(r);
        this.aggiornato = aggiornato;
    }

    @Override
    public void execute() {
        vecchioStato = repo.readData(repo.getFilepath());
        repo.update(repo.getFilepath(),aggiornato);
    }
}
