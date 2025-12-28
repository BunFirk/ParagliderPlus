package org.eatbun.paragliderplus.item.client;

import org.eatbun.paragliderplus.item.custom.ParagliderDefaultItem;
import software.bernie.geckolib.renderer.GeoItemRenderer;

public class AnimatedItemRender extends GeoItemRenderer<ParagliderDefaultItem> {
    public AnimatedItemRender() {
        super(new AnimatedItemModel());
    }
}