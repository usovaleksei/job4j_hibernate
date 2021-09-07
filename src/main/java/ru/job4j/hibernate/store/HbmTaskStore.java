package ru.job4j.hibernate.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.hibernate.model.Category;
import ru.job4j.hibernate.model.Task;

import java.util.ArrayList;
import java.util.List;

public class HbmTaskStore {

    public static void main(String[] args) {

        List<Category> categoryList = new ArrayList<>();
        final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
                .configure().build();
        try {
            SessionFactory sf = new MetadataSources(registry).buildMetadata().buildSessionFactory();
            Session session = sf.openSession();
            session.beginTransaction();

            /*Category firstCategory = Category.of("Consulting");

            Task firstTask = Task.of("Consultation on Hibernate", firstCategory);
            Task secondTask = Task.of("Consultation on Spring", firstCategory);
            Task thirdTask = Task.of("Consultation on Servlet", firstCategory);

            session.save(firstCategory);
            session.save(firstTask);
            session.save(secondTask);
            session.save(thirdTask);*/

            categoryList = session.createQuery(
                    "select distinct c from Category  c join  fetch c.taskList")
                    .list();

            /*for (Category category : categoryList) {
                for (Task task : category.getTaskList()) {
                    System.out.println(task);
                }
            }*/

            session.getTransaction().commit();
            session.close();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            StandardServiceRegistryBuilder.destroy(registry);
        }
        for (Task task : categoryList.get(0).getTaskList()) {
            System.out.println(task);
        }
    }
}
