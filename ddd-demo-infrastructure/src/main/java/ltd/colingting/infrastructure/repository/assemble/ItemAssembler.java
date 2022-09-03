package ltd.colingting.infrastructure.repository.assemble;

import ltd.colingting.domain.domain.entity.Item;
import ltd.colingting.infrastructure.persistence.po.ItemDetailPO;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;

/**
 * @author 丁浩
 * @date 2022年09月01日 14:25
 */
@Mapper
public interface ItemAssembler {

    ItemAssembler INSTANCE = Mappers.getMapper(ItemAssembler.class);


    @Mappings({
        @Mapping(target = "id", source = "itemId")
    })
    Item toItem(ItemDetailPO itemDetailPO);
}
