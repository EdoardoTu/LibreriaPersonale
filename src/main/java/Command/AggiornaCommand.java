package Command;

import Template.Repository2;

public class AggiornaCommand extends undoComune {

    private Repository2 repo;
    public AggiornaCommand(Repository2 r) {
        super(r);
    }

    @Override
    public void execute() {
        repo.delete(repo.getFilepath());
    }
}
