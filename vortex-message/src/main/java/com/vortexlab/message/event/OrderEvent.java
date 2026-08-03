package com.vortexlab.message.event;

import com.vortexlab.message.base.BaseEvent;
import com.vortexlab.message.dto.OrderMessageDTO;
import lombok.Data;

@Data
public class OrderEvent extends BaseEvent<OrderMessageDTO> {
}
