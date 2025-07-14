package Command;

import BackEnd.Libro;
import BackEnd.Template;

public class rimuoviCommand extends undoComune{

    private Libro daRimuovere;

    public rimuoviCommand(Template repo, Libro daRimuovere) {
        super(repo);
        this.daRimuovere = daRimuovere;
    }

    @Override
    public void execute() {
        vecchioStato = repo.readData(repo.getFilepath());
        repo.delete(repo.getFilepath(),daRimuovere);
    }
}
