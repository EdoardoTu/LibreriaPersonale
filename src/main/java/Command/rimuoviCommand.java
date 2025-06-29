package Command;

import Template.Repository2;

public class rimuoviCommand extends undoComune{
    private Repository2 repo;
    public rimuoviCommand(Repository2 r) {
        super(r);
    }

    @Override
    public void execute() {
        repo.delete(repo.getFilepath());
    }
}
