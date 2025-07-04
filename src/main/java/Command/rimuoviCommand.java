package Command;

import Template.Repository2;

public class rimuoviCommand extends undoComune{

    public rimuoviCommand(Repository2 r) {
        super(r);
    }

    @Override
    public void execute() {
        vecchioStato = repo.readData(repo.getFilepath());
        repo.delete(repo.getFilepath());
    }
}
