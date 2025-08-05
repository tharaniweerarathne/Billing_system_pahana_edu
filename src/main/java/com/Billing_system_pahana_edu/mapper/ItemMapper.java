package com.Billing_system_pahana_edu.mapper;

import com.Billing_system_pahana_edu.dto.ItemDTO;
import com.Billing_system_pahana_edu.model.Item;

public class ItemMapper {
    public static Item fromDTO(ItemDTO dto) {
        Item item = new Item();
        item.setItemId(dto.getItemId());
        item.setItemName(dto.getItemName());
        item.setCategory(dto.getCategory());
        item.setPrice(dto.getPrice());
        item.setUnit(dto.getUnit());
        return item;
    }

}

