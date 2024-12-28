public class Inventor implements Comparable<Inventor> {
    private final int id;
    private String name;
    private int score;

    public Inventor() {
        this.id = 0;
        this.name = "";
        this.score = 0;
    }

    public Inventor(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setScore(int score) {
        this.score = score;
    }

    @Override
    public String toString() {
        return "Inventor{" + "ID=" + id + ", Name='" + name + '\'' + ", Score=" + score + '}';
    }

    @Override
    public int compareTo(Inventor other) {
        return Integer.compare(other.score, this.score);
    }
}
