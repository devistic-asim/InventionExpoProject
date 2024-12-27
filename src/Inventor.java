public class Inventor {
    private int id;
    private String name;
    private int score;

    // Constructor
    public Inventor(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    // Getters and Setters
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
        return "Inventor{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", score=" + score +
                '}';
    }
}
