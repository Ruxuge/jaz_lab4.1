package jaz.ADC;

import jaz.entity.CategoryEntity;
import jaz.entity.SectionEntity;

import javax.enterprise.context.RequestScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Named
@RequestScoped
public class CategoryADCimpl implements CategoryADC
{
    @PersistenceContext
    EntityManager entityManager;


    @Override
    @Transactional
    public List<CategoryEntity> getCategoryList()
    {
        return entityManager.createQuery("FROM CategoryEntity ORDER BY id ASC ").getResultList();
    }

    @Override
    @Transactional
    public Optional<CategoryEntity> getCategoryById(Long categoryId)
    {
        var category = entityManager.find(CategoryEntity.class,categoryId);
        return Optional.ofNullable(category);
    }

    @Override
    @Transactional
    public void save(CategoryEntity categoryEntity)
    {
        if (categoryEntity.getId() == null)
        {
            entityManager.persist(categoryEntity);
        } else {
            entityManager.merge(categoryEntity);
        }
    }

    @Override
    @Transactional
    public void delete(CategoryEntity categoryEntity)
    {
        if (categoryEntity.getId() != null)
        {
            var category = entityManager.find(CategoryEntity.class, categoryEntity.getId());
            entityManager.remove(category);
        }
        else
        {
            System.out.println("lel");
        }
    }
}