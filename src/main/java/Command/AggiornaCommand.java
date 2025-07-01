package Command;

import Template.Repository2;

public class AggiornaCommand extends undoComune {


    public AggiornaCommand(Repository2 r) {
        super(r);
    }

    @Override
    public void execute() {
        repo.update(repo.getFilepath());
    }
}
