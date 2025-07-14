package Command;

import BackEnd.Libro;
import BackEnd.Template;

import java.util.ArrayList;

public abstract class undoComune implements Command {
    protected final Template repo;
    protected ArrayList<Libro> vecchioStato;


    public undoComune(Template repo) {

        this.repo = repo;
    }

    @Override
    public void undo() {
        if (!vecchioStato.isEmpty()) {
            System.out.println(vecchioStato);
            repo.writeData(repo.getFilepath(), vecchioStato);
        }
    }
}
