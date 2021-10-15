package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.query.Query;

import java.util.ArrayList;
import java.util.List;

public class UserDaoHibernateImpl implements UserDao {
    private final String tableName = "User";

    public UserDaoHibernateImpl() {
    }

    @Override
    public void createUsersTable() {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        String sql = "CREATE TABLE IF NOT EXISTS `users`.`" +
                tableName + "` (\n" +
                "  `id` BIGINT NOT NULL AUTO_INCREMENT,\n" +
                "  `name` VARCHAR(45) NOT NULL,\n" +
                "  `lastname` VARCHAR(45) NOT NULL,\n" +
                "  `age` tinyint(3) NULL,\n" +
                "  PRIMARY KEY (`id`))\n" +
                "ENGINE = InnoDB\n" +
                "DEFAULT CHARACTER SET = utf8;\n";

        Query<User> query = session.createSQLQuery(sql).addEntity(User.class);
        query.executeUpdate();
        tx.commit();
        session.close();
    }

    @Override
    public void dropUsersTable() {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            String sql = "DROP TABLE IF EXISTS `users`.`" + tableName + "`";
            Query<User> query = session.createSQLQuery(sql).addEntity(User.class);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        Session session = HibernateUtil.getSession();
        Transaction tx = session.beginTransaction();
        User user = new User(name, lastName, age);
        session.persist(user);
        tx.commit();
        session.close();
    }

    @Override
    public void removeUserById(long id) {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            String sql = "DELETE FROM " + tableName + " WHERE id =" + (int) id;
            Query<User> query = session.createSQLQuery("DELETE FROM " + tableName +
                    " WHERE id =" + (int) id).addEntity(User.class);
            query.executeUpdate();
            tx.commit();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<User> getAllUsers() {
        List<User> list = new ArrayList<>();
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            list = session.createQuery(" FROM User").list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            e.printStackTrace();
              } finally {
                session.close();
             }
            return list;
        }

    @Override
    public void cleanUsersTable() {
        try (Session session = HibernateUtil.getSession()) {
            Transaction tx = session.beginTransaction();
            Query<User> deleteQuery = session.createQuery("delete from " + tableName);
            int deleteResponse = deleteQuery.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
