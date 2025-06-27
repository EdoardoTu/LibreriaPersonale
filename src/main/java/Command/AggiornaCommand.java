package Command;

import Template.Repository2;

public class AggiornaCommand implements Command {

    private Repository2 repo;
    public AggiornaCommand(Repository2 r) {
        this.repo = r;

    }
    @Override
    public void execute() {
        repo.delete(repo.getFilepath());
    }
}
