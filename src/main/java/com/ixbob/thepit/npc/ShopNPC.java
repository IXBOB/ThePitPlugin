package com.ixbob.thepit.npc;

import com.ixbob.thepit.citizens.trait.CommandShopTrait;
import com.ixbob.thepit.config.PitConfig;
import com.ixbob.thepit.enums.NPCNamesEnum;

public class ShopNPC extends LobbyBasicNPCImpl {

    private static ShopNPC instance;

    private ShopNPC() {
        super(NPCNamesEnum.NPC_SHOP.getName(), PitConfig.getInstance().getNPCShopLoc(), new CommandShopTrait());
    }

    public static ShopNPC spawn() {
        if (instance == null) {
            instance = new ShopNPC();
            return instance;
        }
        throw new IllegalStateException("You cannot spawn more than one ShopNPC!");
    }

    public static ShopNPC getInstance() {
        return instance;
    }
}
