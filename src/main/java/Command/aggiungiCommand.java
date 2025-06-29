package Command;

import Template.Repository2;

public class aggiungiCommand extends undoComune {

    private Repository2 repo;
    public aggiungiCommand(Repository2 r) {
        super(r);
    }
    @Override
    public void execute() {
        repo.delete(repo.getFilepath());
    }
}
