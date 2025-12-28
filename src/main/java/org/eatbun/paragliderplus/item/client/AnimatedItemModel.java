package org.eatbun.paragliderplus.item.client;

import net.minecraft.util.Identifier;
import org.eatbun.paragliderplus.item.custom.ParagliderDefaultItem;
import software.bernie.geckolib.core.animatable.GeoAnimatable;
import software.bernie.geckolib.model.GeoModel;

//
// КЛАСС УКАЗЫВАЮЩИЙ НА ФАЙЛЫ ДЛЯ АНИМАЦИИ
//

public class AnimatedItemModel extends GeoModel<ParagliderDefaultItem> {
    @Override
    public Identifier getModelResource(ParagliderDefaultItem animatable) {
        return new Identifier("paragliderplus", "geo/paraglider.geo.json");
    }

    @Override
    public Identifier getTextureResource(ParagliderDefaultItem animatable) {
        return new Identifier("paragliderplus", "textures/item/glider.png");
    }

    @Override
    public Identifier getAnimationResource(ParagliderDefaultItem animatable) {
        return new Identifier("paragliderplus", "animations/model/paraglider.animation.json");
    }
}
