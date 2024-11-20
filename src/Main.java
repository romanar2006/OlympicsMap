import java.io.*;
import java.util.*;

class Athlete implements Comparable<Athlete> {
    private String name;
    private String surname;
    private int diploma;

    public Athlete(String surname, String name, int diploma) {
        this.name = name;
        this.surname = surname;
        this.diploma = diploma;
    }

    @Override
    public String toString() {
        return surname + " " + name + ", диплом: " + diploma;
    }

    @Override
    public int compareTo(Athlete other) {
        if (this.diploma != other.diploma) {
            return Integer.compare(this.diploma, other.diploma);
        }
        int surnameComparison = this.surname.compareTo(other.surname);
        if (surnameComparison != 0) {
            return surnameComparison;
        }
        return this.name.compareTo(other.name);
    }
}

public class Main {
    public static void main(String[] args) {
        String[] competitionOrder = {"Город", "Область", "Респа", "Межнар"};

        Map<String, List<Athlete>> competitions = new HashMap<>();

        try (BufferedReader br = new BufferedReader(new FileReader("input.txt"))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] data = line.split(" ");
                String surname = data[0];
                String name = data[1];
                int diploma = Integer.parseInt(data[2]);
                String competition = data[3];

                Athlete athlete = new Athlete(surname, name, diploma);

                competitions.putIfAbsent(competition, new ArrayList<>());
                competitions.get(competition).add(athlete);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        for (List<Athlete> athletes : competitions.values()) {
            Collections.sort(athletes);
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter("output.txt"))) {
            for (String competition : competitionOrder) {
                if (competitions.containsKey(competition)) {
                    bw.write("Уровень: " + competition + "\n");
                    for (Athlete athlete : competitions.get(competition)) {
                        bw.write(athlete + "\n");
                    }
                    bw.write("\n");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
