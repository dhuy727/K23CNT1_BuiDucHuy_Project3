package com.project_3.studymart.service;

import com.project_3.studymart.dto.BdhCartItemRequest;
import com.project_3.studymart.dto.BdhCheckoutRequest;
import com.project_3.studymart.entity.BdhOrder;
import com.project_3.studymart.entity.BdhOrderDetail;
import com.project_3.studymart.entity.BdhUser;
import com.project_3.studymart.enums.OrderStatus;
import com.project_3.studymart.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BdhCheckoutService {

    private final BdhOrderRepository orderRepo;
    private final BdhOrderDetailRepository detailRepo; // (nếu cascade persist tốt thì có thể chưa cần dùng)
    private final BdhProductRepository productRepo;
    private final BdhPaymentMethodRepository paymentRepo;
    private final BdhTransportMethodRepository transportRepo;

    @Transactional
    public BdhOrder createOrder(BdhUser user, BdhCheckoutRequest req, List<BdhCartItemRequest> items) {

        if (items == null || items.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng trống!");
        }

        BdhOrder order = new BdhOrder();
        order.setUser(user);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);
        order.setShippingAddress(req.getShippingAddress());
        order.setPhone(req.getPhone());
        order.setNote(req.getNote());

        if (req.getPaymentMethodId() != null) {
            order.setPaymentMethod(
                    paymentRepo.findById(req.getPaymentMethodId()).orElse(null)
            );
        }

        if (req.getTransportMethodId() != null) {
            order.setTransportMethod(
                    transportRepo.findById(req.getTransportMethodId()).orElse(null)
            );
        }

        double shippingFee = 0.0;
        order.setShippingFee(shippingFee);

        double totalAmount = 0.0;
        List<BdhOrderDetail> details = new ArrayList<>();

        for (var it : items) {
            Integer q = it.getQuantity();
            int qty = (q == null) ? 0 : q;
            if (qty <= 0) continue;

            var product = productRepo.findById(it.getProductId())
                    .orElseThrow(() -> new IllegalArgumentException(
                            "Không tìm thấy productId=" + it.getProductId()
                    ));

            double unitPrice = product.getPrice();
            double subtotal = unitPrice * qty;

            BdhOrderDetail d = new BdhOrderDetail();
            d.setOrder(order);
            d.setProduct(product);
            d.setQuantity(qty);
            d.setUnitPrice(unitPrice);
            d.setSubtotal(subtotal);

            details.add(d);
            totalAmount += subtotal;
        }

        if (details.isEmpty()) {
            throw new IllegalArgumentException("Giỏ hàng không hợp lệ!");
        }

        order.setTotalAmount(totalAmount);
        order.setTotalPrice(totalAmount + shippingFee);
        order.setDetails(details);

        return orderRepo.save(order);
    }
}
