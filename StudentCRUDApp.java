package com.example.hibernatecrud;

import org.hibernate.Session;
import org.hibernate.Transaction;
import java.util.List;

public class StudentCRUDApp {
    public static void main(String[] args) {
        createStudent(new Student("John Doe", "john@example.com", "CSE"));
        readStudents();
        updateStudent(1, "John Smith");
        deleteStudent(1);
        HibernateUtil.shutdown();
    }

    public static void createStudent(Student student) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        session.save(student);
        tx.commit();
        session.close();
        System.out.println("✅ Student saved: " + student);
    }

    public static void readStudents() {
        Session session = HibernateUtil.getSessionFactory().openSession();
        List<Student> students = session.createQuery("from Student", Student.class).list();
        for (Student s : students) {
            System.out.println(s);
        }
        session.close();
    }

    public static void updateStudent(int id, String newName) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Student student = session.get(Student.class, id);
        if (student != null) {
            student.setName(newName);
            session.update(student);
            tx.commit();
            System.out.println("✏️ Updated: " + student);
        } else {
            System.out.println("⚠️ Student not found with ID: " + id);
        }
        session.close();
    }

    public static void deleteStudent(int id) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction tx = session.beginTransaction();
        Student student = session.get(Student.class, id);
        if (student != null) {
            session.delete(student);
            tx.commit();
            System.out.println("🗑️ Deleted Student with ID: " + id);
        } else {
            System.out.println("⚠️ Student not found with ID: " + id);
        }
        session.close();
    }
}
