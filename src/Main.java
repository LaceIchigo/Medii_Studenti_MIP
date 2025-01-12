
// Lab1 + Lab2: Introducere în Java
// Clase și metode simple pentru a lucra cu date despre studenți

import java.io.*;
import java.util.*;

// Lab4: Clase Java
abstract class Persoana {
    protected String nume;
    protected int varsta;

    public Persoana(String nume, int varsta) {
        this.nume = nume;
        this.varsta = varsta;
    }

    public String getNume() {
        return nume;
    }

    public int getVarsta() {
        return varsta;
    }

    public abstract void afiseazaDetalii();
}

class Student extends Persoana {
    private List<Integer> note;

    public Student(String nume, int varsta) {
        super(nume, varsta);
        this.note = new ArrayList<>();
    }

    public void adaugaNota(int nota) {
        note.add(nota);
    }

    public List<Integer> getNote() {
        return note;
    }

    public double calculeazaMedie() {
        return note.stream().mapToInt(Integer::intValue).average().orElse(0);
    }

    @Override
    public void afiseazaDetalii() {
        System.out.println("Student: " + nume + ", Varsta: " + varsta + ", Medie: " + calculeazaMedie());
    }
}

// Lab6: Interfață pentru salvare și încărcare
interface Persistenta {
    void salveaza(String fisier) throws IOException;
    void incarca(String fisier) throws IOException;
}

class GestionareStudenti implements Persistenta {
    private List<Student> studenti;

    public GestionareStudenti() {
        studenti = new ArrayList<>();
    }

    public void adaugaStudent(Student student) {
        studenti.add(student);
    }

    public List<Student> getStudenti() {
        return studenti;
    }

    @Override
    public void salveaza(String fisier) throws IOException {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(fisier))) {
            for (Student student : studenti) {
                writer.write(student.getNume() + "," + student.getVarsta() + "," + student.getNote().toString());
                writer.newLine();
            }
        }
    }

    @Override
    public void incarca(String fisier) throws IOException {
        studenti.clear();
        try (BufferedReader reader = new BufferedReader(new FileReader(fisier))) {
            String linie;
            while ((linie = reader.readLine()) != null) {
                String[] date = linie.split(",");
                Student student = new Student(date[0], Integer.parseInt(date[1]));
                String[] note = date[2].replace("[", "").replace("]", "").split(" ");
                for (String nota : note) {
                    if (!nota.isBlank()) {
                        student.adaugaNota(Integer.parseInt(nota));
                    }
                }
                studenti.add(student);
            }
        }
    }
}

public class Main {
    public static void main(String[] args) {
        GestionareStudenti gestionare = new GestionareStudenti();

        // Creăm câțiva studenți
        Student s1 = new Student("Ion Popescu", 20);
        s1.adaugaNota(8);
        s1.adaugaNota(9);

        Student s2 = new Student("Maria Ionescu", 22);
        s2.adaugaNota(10);
        s2.adaugaNota(9);

        gestionare.adaugaStudent(s1);
        gestionare.adaugaStudent(s2);

        // Afisăm detalii
        for (Student student : gestionare.getStudenti()) {
            student.afiseazaDetalii();
        }

        // Persistența datelor
        try {
            gestionare.salveaza("studenti.txt");
            System.out.println("Datele au fost salvate.");

            gestionare.incarca("studenti.txt");
            System.out.println("Datele au fost încărcate.");

            for (Student student : gestionare.getStudenti()) {
                student.afiseazaDetalii();
            }
        } catch (IOException e) {
            System.out.println("Eroare la salvare/încărcare: " + e.getMessage());
        }
    }
}