package ru.job4j.tracker.store;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import ru.job4j.tracker.model.Item;

import java.util.List;
import java.util.function.Function;

public class HbmTracker implements Store {

    private final StandardServiceRegistry registry = new StandardServiceRegistryBuilder()
            .configure().build();

    private final SessionFactory sf = new MetadataSources(registry)
            .buildMetadata().buildSessionFactory();

    @Override
    public void init() {
    }

    @Override
    public Item add(Item item) {
        return executeTransaction(session -> {
            session.save(item);
            return item;
        });
    }

    @Override
    public boolean replace(int id, Item item) {
        return executeTransaction(session -> {
            item.setId(id);
            session.update(item);
            return true;
        });
    }

    @Override
    public boolean delete(int id) {
        return executeTransaction(session -> {
            Item item = new Item();
            item.setId(id);
            session.delete(item);
            return true;
        });
    }

    @Override
    public List<Item> findAll() {
        String hql = "from Item";
        return executeTransaction(session -> session.createQuery(hql)
                .list());
    }

    @Override
    public List<Item> findByName(String key) {
        String hql = "from Item i where i.name = :pName";
        return executeTransaction(session -> session.createQuery(hql)
                .setParameter("pName", key)
                .list());
    }

    @Override
    public Item findById(int id) {
        return executeTransaction(session -> session.get(Item.class, id));
    }

    @Override
    public void close() throws Exception {
        StandardServiceRegistryBuilder.destroy(registry);
    }

    private <T> T executeTransaction(Function<Session, T> f) {
        T result;
        Session session = sf.openSession();
        session.beginTransaction();
        result = f.apply(session);
        session.getTransaction().commit();
        session.close();
        return result;
    }
}
