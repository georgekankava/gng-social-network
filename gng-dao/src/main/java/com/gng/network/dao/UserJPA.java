package com.gng.network.dao;

import java.util.List;
import java.util.logging.Level;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

import com.gng.network.enities.Message;
import com.gng.network.exceptions.DaoException;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j;
import lombok.extern.log4j.Log4j2;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.gng.network.enities.Image;
import com.gng.network.enities.User;

@Repository(value="userDao")
@Slf4j
public class UserJPA implements UserDao {

	@PersistenceContext
	private EntityManager em;
	
	@Transactional
	public void persistUser(User user) {
		em.persist(user);
		em.refresh(user);
	}
	
	public void updateUser(User user) {
		em.merge(user);
		em.flush();
	}

	@SuppressWarnings("unchecked")
	public List<User> findUsersByFirstname(String firstname) {
		return (List<User>)em.createNamedQuery("User.findUserByFirstname").setParameter("firstname", firstname).getResultList();
	}

	@SuppressWarnings("unchecked")
	public List<User> findUsersByLastname(String lastname) {
		return (List<User>)em.createNamedQuery("User.findUserByLastname").setParameter("lastname", lastname).getResultList();
	}

	public User findUserByUsername(String username) {
		try {
			return (User)em.createNamedQuery("User.findUserByUsername").setParameter("username", username).getSingleResult();
		} catch(NoResultException exception) {
			return null;
		}
	}
	
	@SuppressWarnings("unchecked")
	public List<User>findUserByFullname(String fullname) {
		return (List<User>)em.createNamedQuery("User.findUserByFullname").setParameter("fullname", "%" + fullname + "%").getResultList();
	}
	
	@SuppressWarnings("unchecked")
	public List<User> searchUsersBySearchString(String searchString) {
		return (List<User>)em.createNamedQuery("User.findUserBySearchString").setParameter("searchString", "%" + searchString.trim() + "%").getResultList();
	}

	public User findUserById(Integer userId) {
		return em.find(User.class, userId);
	}

	public User mergeUser(User user) {
		return em.merge(user);
	}
	
	@Transactional
	public void persistImage(Image img) {
		img.setUser(em.merge(img.getUser()));
		em.persist(img);
	}

	@Override
	public Image findUserProfileImage(Integer userId) {
		try {
			return (Image)em.createNamedQuery("Image.findUserProfileImage").setParameter("userId", userId).setParameter("profileImage", Boolean.TRUE).getSingleResult();
		} catch(NoResultException exception) {
			return null;
		}
	}

	@Override
	@SuppressWarnings("unchecked")
	public List<Image> getUserImagesById(Integer userId) {
		return (List<Image>)em.createNamedQuery("Image.getUserImagesByUserId").setParameter("userId", userId).getResultList();
	}

	@Override
	public Image findUserImageByImageId(Integer imageId) {
		return (Image)em.createNamedQuery("Image.getImageByImageId").setParameter("imageId", imageId).getSingleResult();
	}

	@Override
	public List<User> loadUserMessageList(Integer userId) throws DaoException {
		try {
			return em.createNamedQuery("User.getUsersMessagedWith").setParameter("id", userId).getResultList();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			throw new DaoException(e.getMessage(), "dao.expeption", e);
		}
	}
}
