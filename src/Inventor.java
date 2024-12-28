import java.util.Objects;

public class Inventor implements Comparable<Inventor> {
    private int id;
    private String name;
    private int score;

    // Default Constructor
    public Inventor() {
        this.id = 0;
        this.name = "";
        this.score = 0;
    }

    // Parameterized Constructor
    public Inventor(int id, String name, int score) {
        this.id = id;
        this.name = name;
        this.score = score;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getScore() {
        return score;
    }

    // Setters with Validation
    public void setName(String name) {
        if (name == null || name.trim().isEmpty()) {
            throw new IllegalArgumentException("Name cannot be null or empty.");
        }
        this.name = name;
    }

    public void setScore(int score) {
        if (score < 1 || score > 100) {
            throw new IllegalArgumentException("Score must be between 1 and 100.");
        }
        this.score = score;
    }

    // toString Method for User-Friendly Output
    @Override
    public String toString() {
        return "Inventor{" +
                "ID=" + id +
                ", Name='" + name + '\'' +
                ", Score=" + score +
                '}';
    }

    // Equals and HashCode for Object Comparison
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        Inventor inventor = (Inventor) obj;
        return id == inventor.id &&
                score == inventor.score &&
                name.equals(inventor.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, score);
    }

    // Implement Comparable for Sorting by Score (Descending)
    @Override
    public int compareTo(Inventor other) {
        return Integer.compare(other.score, this.score); // Descending order
    }
}
