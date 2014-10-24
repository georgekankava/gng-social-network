package com.gng.network.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gng.network.enities.FriendRequest;
import com.gng.network.enities.User;
import com.gng.network.exceptions.UserNotFoundException;


@Repository("friendRequestDao")
public class FriendRequestJPA implements FriendRequestDao {
	
	@PersistenceContext
	private EntityManager em;
	
	public void addFriendRequest(User from, User to) throws UserNotFoundException {
		FriendRequest friendRequest = new FriendRequest(to, from);
		em.persist(friendRequest);
	}
	
	@SuppressWarnings("unchecked")
	public List<FriendRequest> getPendingRequests(Integer userId) {
		return em.createNamedQuery("FriendRequest.getPendingRequests").setParameter("id", userId).getResultList();
	}

	@Transactional
	public void removeFriendRequest(Integer requestId) {
		FriendRequest fr = em.find(FriendRequest.class, requestId);
		em.remove(fr);
	}

	public FriendRequest getPendingRequest(Integer requestId) {
		FriendRequest fr = em.find(FriendRequest.class, requestId);
		return fr;
	}

}
