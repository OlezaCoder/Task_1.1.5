package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;

import jm.task.core.jdbc.util.Util;
import org.hibernate.Session;

import java.util.List;

import org.hibernate.query.NativeQuery;

public class UserDaoHibernateImpl extends Util implements UserDao {
    public UserDaoHibernateImpl() {

    }

    private boolean isTableExists() {
        openTransactionSession();
        String sql = "SELECT table_name FROM information_schema.tables " +
                     "WHERE table_schema = 'db_task1.1.4' AND table_name = 'users'";
        Session session = getSession();
        Object result = session.createNativeQuery(sql).uniqueResult();
        closeTransactionSession();
        return result != null;
    }


    @Override
    public void createUsersTable() {
        if (!isTableExists()) {
            openTransactionSession();
            String sql = "CREATE TABLE users (id BIGINT PRIMARY KEY AUTO_INCREMENT, name VARCHAR(45), " +
                    "lastName VARCHAR(45), age TINYINT)";
            Session session = getSession();
            NativeQuery<User> query = session.createNativeQuery(sql, User.class);
            query.executeUpdate();
            closeTransactionSession();
        }
    }

    @Override
    public void dropUsersTable() {
        if (isTableExists()) {
            openTransactionSession();
            String sql = "DROP TABLE users";
            Session session = getSession();
            NativeQuery<User> query = session.createNativeQuery(sql, User.class);
            query.executeUpdate();
            closeTransactionSession();
        }
    }

    @Override
    public void saveUser(String name, String lastName, byte age) {
        openTransactionSession();
        String sql = "INSERT INTO users (name, lastName, age) VALUES(:name, :lastName, :age)";
        Session session = getSession();
        NativeQuery<User> query = session.createNativeQuery(sql, User.class);
        query.setParameter("name", name);
        query.setParameter("lastName", lastName);
        query.setParameter("age", age);
        query.executeUpdate();
        closeTransactionSession();
    }

    @Override
    public void removeUserById(long id) {
        openTransactionSession();
        String sql = "DELETE FROM users WHERE id=:id";
        Session session = getSession();
        NativeQuery<User> query = session.createNativeQuery(sql, User.class);
        query.setParameter("id", id);
        query.executeUpdate();
        closeTransactionSession();
    }

    @Override
    public List<User> getAllUsers() {
        openTransactionSession();
        String sql = "SELECT * FROM users";
        Session session = getSession();
        NativeQuery<User> query = session.createNativeQuery(sql, User.class);
        List<User> usersList = query.getResultList();
        closeTransactionSession();
        return usersList;
    }

    @Override
    public void cleanUsersTable() {
        openTransactionSession();
        String sql = "DELETE FROM users";
        Session session = getSession();
        session.createNativeQuery(sql, User.class).executeUpdate();
        closeTransactionSession();
    }
}
