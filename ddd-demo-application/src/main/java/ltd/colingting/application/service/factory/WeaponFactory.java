package ltd.colingting.application.service.factory;

import ltd.colingting.application.config.SwordProto;
import ltd.colingting.domain.domain.entity.Weapon;

/**
 * @author 丁浩
 * @date 2022年09月08日 16:38
 */

public class WeaponFactory {


    public static Weapon createWeaponFromPrototype(SwordProto swordProto, String name, int damage){
        Weapon weapon = new Weapon();

        return weapon;
    }

}
