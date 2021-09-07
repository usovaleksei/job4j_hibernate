package ru.job4j.hibernate.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.Author;
import ru.job4j.hibernate.model.Book;

public class HbmBookStore {

    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Book firstBook = Book.of("Война и мир");
            Book secondBook = Book.of("Java Core");
            Book fourthBook = Book.of("Анна Каренина");

            Author firstAuthor = Author.of("Лев Толстой");
            Author secondAuthor = Author.of("Герберт Шилдт");
            Author thirdAuthor = Author.of("Кэтти Сьерра");

            firstAuthor.getBookList().add(firstBook);
            firstAuthor.getBookList().add(fourthBook);

            secondAuthor.getBookList().add(secondBook);
            thirdAuthor.getBookList().add(secondBook);

            session.persist(firstAuthor);
            session.persist(secondAuthor);
            session.persist(thirdAuthor);

            Author author = session.get(Author.class, 3);
            session.remove(author);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
