package Command;

import Template.Repository2;
import Template.Template;

public class rimuoviCommand extends undoComune{

    public rimuoviCommand(Repository2 r) {
        super(r);
    }

    public rimuoviCommand(Template repo) {
        super(repo);

    }

    @Override
    public void execute() {
        vecchioStato = repo.readData(repo.getFilepath());
        repo.delete(repo.getFilepath());
    }
}
