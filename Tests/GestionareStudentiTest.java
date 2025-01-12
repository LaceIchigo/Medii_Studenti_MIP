import org.junit.jupiter.api.*;
import java.io.*;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

public class GestionareStudentiTest {

    @Test
    public void testAdaugaNotaSiCalculeazaMedie() {
        Student student = new Student("Ion Popescu", 20);
        student.adaugaNota(8);
        student.adaugaNota(9);
        student.adaugaNota(10);

        assertEquals(9.0, student.calculeazaMedie(), 0.001);
    }

    @Test
    public void testAdaugaStudent() {
        GestionareStudenti gestionare = new GestionareStudenti();
        Student student = new Student("Maria Ionescu", 22);
        gestionare.adaugaStudent(student);

        assertEquals(1, gestionare.getStudenti().size());
        assertEquals("Maria Ionescu", gestionare.getStudenti().get(0).getNume());
    }


    @Test
    public void testAfiseazaDetalii() {
        Student student = new Student("Ion Popescu", 20);
        student.adaugaNota(8);
        student.adaugaNota(9);

        // Capturăm ieșirea consolei pentru a verifica afișarea detaliilor
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));

        student.afiseazaDetalii();
        String output = outputStream.toString().trim();

        assertTrue(output.contains("Ion Popescu"));
        assertTrue(output.contains("20"));
        assertTrue(output.contains("8.5")); // Media
    }
}