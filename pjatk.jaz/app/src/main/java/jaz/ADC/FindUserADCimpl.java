package jaz.ADC;

import jaz.entity.ProfileEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;

@Named
@RequestScoped
public class FindUserADCimpl implements FindUserADC
{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public Long getUserByUsername(String username) {
        return entityManager.createQuery("from ProfileEntity where username = : username", ProfileEntity.class)
                .setParameter("username", username).getSingleResult().getId();
    }
}