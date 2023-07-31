package ma.sir.ged.zynerator.service;

import ma.sir.ged.zynerator.audit.AuditBusinessObjectEnhanced;
import ma.sir.ged.zynerator.criteria.BaseCriteria;
import ma.sir.ged.zynerator.history.HistBusinessObject;
import ma.sir.ged.zynerator.history.HistCriteria;
import ma.sir.ged.zynerator.repository.AbstractHistoryRepository;
import ma.sir.ged.zynerator.repository.AbstractRepository;

public abstract class AbstractReferentielServiceImpl<T extends AuditBusinessObjectEnhanced, H extends HistBusinessObject, CRITERIA extends BaseCriteria, HC extends HistCriteria, REPO extends AbstractRepository<T, Long>, HISTREPO extends AbstractHistoryRepository<H, Long>> extends AbstractServiceImpl<T, H, CRITERIA, HC, REPO, HISTREPO> {

    public AbstractReferentielServiceImpl(REPO dao, HISTREPO historyRepository) {
    super(dao, historyRepository);
    }

}