package Command;

import Template.Repository2;

public class rimuoviCommand implements Command {
    private Repository2 repo;
    public rimuoviCommand(Repository2 r) {
        this.repo = r;

    }

    @Override
    public void execute() {
        repo.delete(repo.getFilepath());
    }
}
