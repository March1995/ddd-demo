package ltd.colingting.infrastructure.repository;

import ltd.colingting.domain.entity.SalesRep;
import ltd.colingting.domain.repository.SalesRepRepository;
import ltd.colingting.infrastructure.assemble.EntityAssemble;
import ltd.colingting.infrastructure.persistence.mapper.SalesRepMapper;
import ltd.colingting.infrastructure.persistence.po.SalesRepPO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author 丁浩
 * @date 2022年07月24日 13:20
 */
@Service
public class SalesRepRepositoryImpl implements SalesRepRepository {

    @Autowired
    private SalesRepMapper salesRepMapper;

    @Autowired
    private EntityAssemble entityAssemble;

    @Override
    public SalesRep findRep(String areaCode) {
        SalesRepPO salesRepPO = salesRepMapper.selectById(areaCode);
        return entityAssemble.toSalesRep(salesRepPO);
    }
}
