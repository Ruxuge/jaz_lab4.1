package jaz.ADC;

import jaz.entity.SectionEntity;
import jaz.request.SectionRequest;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Named
@ApplicationScoped
public class SectionADCimpl implements SectionADC
{
    @PersistenceContext
    EntityManager entityManager;

    @Override
    @Transactional
    public List<SectionEntity> getSectionList()
    {
        return entityManager.createQuery("FROM SectionEntity ORDER BY id ASC").getResultList();
    }

    @Override
    @Transactional
    public SectionRequest getSection()
    {
        return null;
    }

    @Override
    @Transactional
    public Optional<SectionEntity> getSectionById(Long sectionId)
    {
        var section = entityManager.find(SectionEntity.class, sectionId);
        return Optional.ofNullable(section);
    }

    @Override
    @Transactional
    public void save(SectionEntity sectionEntity)
    {
        if (sectionEntity.getId() == null)
        {
            entityManager.persist(sectionEntity);
        } else {
            entityManager.merge(sectionEntity);
        }
    }

    @Override
    @Transactional
    public void delete(SectionEntity sectionEntity)
    {
        if (sectionEntity.getId() != null)
        {
            var section = entityManager.find(SectionEntity.class,sectionEntity.getId());
            entityManager.remove(section);
        }
        else
        {
            System.out.println("Deleted");
        }
    }
}