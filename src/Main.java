import java.util.List;

public class Main {
    public static void main(String[] args) {
        try {
            // Adding a new inventor to the database
            DbHelper.addInventor("Nikola Tesla", 100);

            // Retrieving and displaying all inventors
            List<Inventor> inventors = DbHelper.getInventors();
            for (Inventor inventor : inventors) {
                System.out.println(inventor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}