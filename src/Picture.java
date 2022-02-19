import java.io.File;

public class Picture {
    private File file;
    private int id;
    public Picture(final File file, final int id) {
        this.file = file;
        this.id = id;
    }
    public File getPlik() {
        return this.file;
    }

    public int getId() {
        return this.id;
    }
}
