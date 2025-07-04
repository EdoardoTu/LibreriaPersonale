package Command;

import Template.Repository2;

public class AggiornaCommand extends undoComune {


    public AggiornaCommand(Repository2 r) {
        super(r);
    }

    @Override
    public void execute() {
        vecchioStato = repo.readData(repo.getFilepath());
        repo.update(repo.getFilepath());
    }
}
