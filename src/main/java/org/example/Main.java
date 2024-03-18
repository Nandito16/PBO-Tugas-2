package org.example;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static ArrayList<Student> students = new ArrayList<>();
    private static ArrayList<Book> books = new ArrayList<>();
    private static Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        // Menambahkan buku ke dalam daftar buku
        books.add(new Book("388c-e681-9152", "Kento", "Vsauce", "Samurai", 3));
        books.add(new Book("ed90-be30-5cdb", "Goku", "Icewallowcome", "Fantasy", 2));
        books.add(new Book("d95e-0c4a-9523", "Naruto", "alul", "Shinobi", 3));

        while (true) {
            System.out.println("===== Library System =====");
            System.out.println("1. Login sebagai Student");
            System.out.println("2. Login sebagai Admin");
            System.out.println("3. Keluar");
            System.out.print("Pilih opsi (1-3): ");
            int userInput = scanner.nextInt();

            switch (userInput) {
                case 1:
                    loginStudent();
                    break;
                case 2:
                    AdminLibrary();
                    break;
                case 3:
                    System.out.println("Terima kasih. Keluar dari program.");
                    scanner.close();
                    System.exit(0);
                default:
                    System.out.println("Input tidak valid. Silakan coba lagi.");
            }
        }
    }
    public static void loginStudent() {
        System.out.println("===== Student Menu ====");
        System.out.print("Masukkan NIM Anda (masukkan 99 untuk kembali): ");
        String nim = scanner.next();

        if (nim.equals("99")) {
            System.out.println("Kembali ke menu utama...");
            return;
        }

        Student student = findStudentByNim(nim);

        if (student == null) {
            System.out.println("Mahasiswa tidak ditemukan. Kembali ke menu utama...");
            return;
        }

        while (true) {
            System.out.println("\n===== Student Menu ====");
            System.out.println("1. Buku Terpinjam");
            System.out.println("2. Pinjam Buku");
            System.out.println("3. Keluar");
            System.out.print("Pilih opsi (1-3): ");
            int userInput = scanner.nextInt();

            switch (userInput) {
                case 1:
                    student.viewBorrowedBooks();
                    break;
                case 2:
                    student.borrowBook(books, scanner);
                    break;
                case 3:
                    System.out.println("Keluar dari akun mahasiswa...");
                    return;
                default:
                    System.out.println("Input tidak valid. Silakan coba lagi.");
            }
        }
    }
    public static Student findStudentByNim(String nim) {
        for (Student s : students) {
            if (s.getNim().equals(nim)) {
                return s;
            }
        }
        return null;
    }

    public static void AdminLibrary() {
        Admin objadmin = new Admin();
        scanner.nextLine();
        System.out.print("Enter your admin username : ");
        String UserInput = scanner.nextLine();
        System.out.print("Enter your admin password : ");
        String PassInput = scanner.nextLine();

        if (UserInput.equals(objadmin.adminUser) && PassInput.equals(objadmin.adminPass)) {
            System.out.println("Succesfull login as Admin");
        } else {
            System.out.println("Failed login");
        }
        System.out.println("===== Admin Menu =====");
        System.out.println("1. Tambah Mahasiswa");
        System.out.println("2. Tampilkan Mahasiswa Terdaftar");
        System.out.println("3. Keluar");
        System.out.print("Pilih opsi (1-3): ");
        int userInput = scanner.nextInt();

        switch (userInput) {
            case 1:
                addStudent();
                break;
            case 2:
                displayStudents();
                break;
            case 3:
                System.out.println("Keluar dari akun admin...");
                break;
            default:
                System.out.println("Input tidak valid. Silakan coba lagi.");
        }
    }
    public static void addStudent() {
        System.out.print("Nama: ");
        String name = scanner.next();

        String nim;
        do {
            System.out.print("NIM: ");
            nim = scanner.next();
            if (nim.length() == 15) {
            } else {
                System.out.println("NIM harus 15 digit");
            }
        }while (nim.length() != 15);
        System.out.print("fakultas: ");
        String faculty = scanner.next();
        System.out.print("program: ");
        String program = scanner.next();

        students.add(new Student(name, faculty, nim, program));
        System.out.println("Mahasiswa berhasil ditambahkan!");
    }
    public static void displayStudents() {
        System.out.println("\n===== Mahasiswa Terdaftar =====");
        System.out.printf("%-20s %-20s %-15s %-20s\n", "Nama", "NIM", "Faculty", "Program");
        for (Student student : students) {
            System.out.printf("%-20s %-20s %-15s %-20s\n", student.getName(), student.getFaculty(), student.getNim(), student.getProgram());
        }
    }
}
class Student {
    String name;
    String faculty;
    String nim;
    String program;
    ArrayList<Book> borrowedBooks = new ArrayList<>();
    public Student(String name, String faculty, String nim, String program) {
        this.name = name;
        this.faculty = faculty;
        this.nim = nim;
        this.program = program;
    }
    String getName() {
        return name;
    }
    String getFaculty() {
        return faculty;
    }
    String getNim() {
        return nim;
    }
    String getProgram() {
        return program;
    }
    void viewBorrowedBooks() {
        if (borrowedBooks.isEmpty()) {
            System.out.println("Anda belum meminjam buku apapun.");
            return;
        }
        System.out.println("\n===== Buku Terpinjam =====");
        System.out.printf("%-10s %-20s %-20s %-10s\n", "ID", "Judul", "Penulis", "Stok");
        for (Book book : borrowedBooks) {
            System.out.printf("%-10s %-20s %-20s %-10d\n", book.getId(), book.getTitle(), book.getAuthor(), book.getStock());
        }
    }
    public void borrowBook(ArrayList<Book> books, Scanner scanner) {
        System.out.print("Masukkan ID buku yang ingin dipinjam: ");
        String id = scanner.next();

        if (id.isEmpty()) {
            System.out.println("ID buku tidak boleh kosong. Kembali ke menu mahasiswa...");
            return;
        }

        Book book = null;
        for (Book b : books) {
            if (b.getId().equals(id)) {
                book = b;
                break;
            }
        }

        if (book == null) {
            System.out.println("Buku tidak ditemukan. Kembali ke menu mahasiswa...");
            return;
        }

        if (book.getStock() > 0) {
            borrowedBooks.add(book);
            book.setStock(book.getStock() - 1);
            System.out.println("Buku berhasil dipinjam!");
        } else {
            System.out.println("Maaf, stok buku habis.");
        }
    }
}

class Admin {

    String adminUser = "admin";
    String adminPass = "admin";

    public void Admin(String adminUser, String adminPass) {
        this.adminUser = adminUser;
        this.adminPass = adminPass;
    }

    public static void main(String[] args) {
        Admin objadmin = new Admin();
    }
}

class Book {
    String id;
    String title;
    String author;
    String category;
    int stock;
    public Book(String id, String title, String author, String category, int stock) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.category = category;
        this.stock = stock;
    }
    String getId() {
        return id;
    }
    String getTitle() {
        return title;
    }
    String getAuthor() {
        return author;
    }
    String getCategory() {
        return category;
    }
    int getStock() {
        return stock;
    }
    void setStock(int stock) {
        this.stock = stock;
    }
}
