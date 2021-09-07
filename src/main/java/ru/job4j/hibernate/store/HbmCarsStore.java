package ru.job4j.hibernate.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.CarBrand;
import ru.job4j.hibernate.model.CarModel;

import java.util.ArrayList;
import java.util.List;

public class HbmCarsStore {
    public static void main(String[] args) {
        List<CarBrand> carBrandList = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            carBrandList = session.createQuery(
                    "select distinct b from CarBrand b join fetch b.carModelList"
            ).list();

            /*CarBrand firstBrand = CarBrand.of("Mercedes");
            CarBrand secondBrand = CarBrand.of("Audi");

            CarModel firstModel = CarModel.of("E 350", firstBrand);
            CarModel secondModel = CarModel.of("GL 63", firstBrand);
            CarModel thirdModel = CarModel.of("S 500", firstBrand);

            CarModel fourthModel = CarModel.of("Q7", secondBrand);
            CarModel fifthModel = CarModel.of("A6", secondBrand);
            CarModel sixthModel = CarModel.of("Q5", secondBrand);

            session.save(firstBrand);
            session.save(secondBrand);
            session.save(firstModel);
            session.save(secondModel);
            session.save(thirdModel);
            session.save(fourthModel);
            session.save(fifthModel);
            session.save(sixthModel);*/

            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (CarBrand brand : carBrandList) {
            for (CarModel model : brand.getCarModelList()) {
                System.out.println(model);
            }
        }
    }
}
