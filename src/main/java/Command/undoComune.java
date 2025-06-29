package Command;

import Template.Libro;
import Template.Repository2;

import java.util.ArrayList;

public abstract class undoComune implements Command {
    protected final Repository2 repo;
    protected ArrayList<Libro> vecchioStato;


    public undoComune(Repository2 repo) {
        this.repo = repo;
    }

    @Override
    public void undo() {
        if (!vecchioStato.isEmpty()) {
            System.out.println(vecchioStato);
            repo.writeData(vecchioStato);
        }
    }
}
