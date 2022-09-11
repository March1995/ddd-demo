package ltd.colingting.application.service.impl;

import junit.framework.TestCase;
import org.springframework.stereotype.Service;

@Service
public class CombatServiceImplTest extends TestCase {

//
//    @Autowired
//    private CombatService combatService;
//
//    @Autowired
//    private EquipmentService equipmentService;
//
//    @Autowired
//    private SwordProto swordProto;
//
//    @Test
//    @DisplayName("Dragoon attack dragon doubles damage")
//    public void testDragoonSpecial() {
//        // Given
//        Player dragoon = PlayerFactory.createPlayer(PlayerClass.Dragoon, "Dart");
//        Weapon sword = WeaponFactory.createWeaponFromPrototype(swordProto, "Soul Eater", 60);
//        ((WeaponRepositoryMock)weaponRepository).cache(sword);
//        dragoon.equip(sword, equipmentService);
//        Monster dragon = MonsterFactory.createMonster(MonsterClass.Dragon, 100);
//
//        // When
//        combatService.performAttack(dragoon, dragon);
//
//        // Then
//        assertThat(dragon.getHealth()).isEqualTo(Health.ZERO);
//        assertThat(dragon.isAlive()).isFalse();
//    }
}