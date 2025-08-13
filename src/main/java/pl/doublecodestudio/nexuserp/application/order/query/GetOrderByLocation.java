package pl.doublecodestudio.nexuserp.application.order.query;

import org.springframework.data.domain.Pageable;

public record GetOrderByLocation(String location, Pageable pageable) {
}
