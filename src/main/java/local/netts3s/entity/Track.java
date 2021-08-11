package local.netts3s.entity;

public class Track {
    private String name = "unknown";

    public Track() { }

    public Track(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Track: " + name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
