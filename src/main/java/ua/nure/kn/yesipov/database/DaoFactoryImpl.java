package main.java.ua.nure.kn.yesipov.database;

public class DaoFactoryImpl extends DaoFactory {

    @Override
    public UserDao getUserDao(){

        try {
            Class<?> daoClass = Class.forName(properties.getProperty(USER_DAO));
            UserDao userDao = (UserDao) daoClass.newInstance();
            userDao.setConnectionFactory(getConnectionFactory());
            return userDao;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    protected ConnectionFactory getConnectionFactory() {
        return new ConnectionFactoryImpl(properties);
    }
}
