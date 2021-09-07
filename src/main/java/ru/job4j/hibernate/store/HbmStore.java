package ru.job4j.hibernate.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.Address;
import ru.job4j.hibernate.model.Brand;
import ru.job4j.hibernate.model.Car;
import ru.job4j.hibernate.model.Person;

public class HbmStore {
    public static void main(String[] args) {
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            Address addressOne = Address.of("Chistopolskaye", "30");
            Address addressTwo = Address.of("Zarechnaya", "25");

            Person personOne = Person.of("Aleksei");

            personOne.getAddressList().add(addressOne);
            personOne.getAddressList().add(addressTwo);

            Person personTwo = Person.of("Roman");

            personTwo.getAddressList().add(addressTwo);

            session.persist(personOne);
            session.persist(personTwo);

            Person person = session.get(Person.class, 1);
            session.remove(person);

            /*Car carOne = Car.of("Solaris");
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

            session.save(brand);*/

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
