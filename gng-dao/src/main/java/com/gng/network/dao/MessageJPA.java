package com.gng.network.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import org.springframework.stereotype.Repository;

import com.gng.network.enities.Message;
import com.gng.network.enities.User;

@Repository("messageDao")
public class MessageJPA implements MessageDao {

	@PersistenceContext
	private EntityManager entityManager;
	
	public void addMessage(User userFrom, User userTo, String message, long time) {
		Message msg = new Message(userFrom, userTo, message, time);
		entityManager.persist(msg);
	}
	
	public void removeMessage(int messageId) {
		Message msg = entityManager.find(Message.class, messageId);
		assert msg != null;
		entityManager.remove(msg);
	}

	@SuppressWarnings("unchecked")
	public List<Message> getMessages(User userFrom, User userTo, Long fromMillies, Long toMillies) {
		Query query = entityManager.createNamedQuery("Message.getMessages").setParameter("userFrom", userFrom).setParameter("userTo", userTo)
		.setParameter("fromTime", fromMillies).setParameter("toTime", toMillies);
		return query.getResultList();
	}
	
	public Long getMessageMaxTime(User userFrom, User userTo) {
		return (Long)entityManager.createNamedQuery("Message.getMessageMaxMillies").setParameter("userFrom", userFrom).setParameter("userTo", userTo).getSingleResult();
	}
	
	public Long getMessageMaxTimeLessThan(User userFrom, User userTo, Long messageMillies) {
		try {
			return (Long)entityManager.createNamedQuery("Message.getMessageMaxMilliesLessThen").setParameter("userFrom", userFrom).setParameter("userTo", userTo).setParameter("time", messageMillies).getSingleResult();
		} catch(NoResultException ex) {
			return null;
		}
	}
	
}
