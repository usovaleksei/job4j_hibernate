package ru.job4j.hibernate.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.Brand;
import ru.job4j.hibernate.model.Car;

public class HbmStore {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Car carOne = Car.of("Solaris");
            Car carTwo = Car.of("Getz");
            Car carThree = Car.of("Genesis");
            Car carFour = Car.of("Creta");
            Car carFive = Car.of("Tucson");

            session.save(carOne);
            session.save(carTwo);
            session.save(carThree);
            session.save(carFour);
            session.save(carFive);

            Brand brand = Brand.of("Hynday");

            brand.addCar(session.load(Car.class, 1));
            brand.addCar(session.load(Car.class, 2));
            brand.addCar(session.load(Car.class, 3));
            brand.addCar(session.load(Car.class, 4));
            brand.addCar(session.load(Car.class, 5));

            session.save(brand);

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
