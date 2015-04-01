package com.javamonkeys.dao.game;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.javamonkeys.dao.user.User;
import org.springframework.transaction.annotation.Transactional;

@Repository
public class GameDao implements IGameDao {

    @Autowired
    private SessionFactory hibernateSessionFactory;

    private Session getSession() {
        return hibernateSessionFactory.getCurrentSession();
    }

    private void save(Object entity) {
        getSession().save(entity);
    }

    private void update(Object entity) {
        getSession().update(entity);
    }

    private void delete(Object entity) {
        getSession().delete(entity);
    }

    @Transactional
    public Game getGame(int id) {

        Query query = getSession().createQuery("from Game where id = :id");
        query.setParameter("id", id);
        Game currentGame = (Game) query.uniqueResult();

        return currentGame;
    }

    @Transactional
    public Game createGame(User author) {

        Game newGame = new Game(author);
        save(newGame);

        return newGame;
    }

    @Transactional
    public Game updateGame(Game game) {

        update(game);

        return game;
    }

    @Transactional
    public void saveTurn(int id, String turn) {

        Game currentGame = getGame(id);
        String newMoveText = currentGame.getMoveText() + turn;
        currentGame.setMoveText(newMoveText);
        updateGame(currentGame);
    }
}
