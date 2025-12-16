package com.project_3.studymart.service;

import com.project_3.studymart.dto.BdhCartItemRequest;
import com.project_3.studymart.dto.BdhCreateOrderRequest;
import com.project_3.studymart.entity.BdhUser;
import com.project_3.studymart.entity.BdhOrder;
import com.project_3.studymart.entity.BdhOrderDetail;
import com.project_3.studymart.entity.BdhProduct;
import com.project_3.studymart.repository.BdhOrderDetailRepository;
import com.project_3.studymart.repository.BdhOrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BdhOrderService {

    private final BdhOrderRepository orderRepository;
    private final BdhOrderDetailRepository detailRepository;
    private final BdhUserService customerService;
    private final BdhProductService productService;
    private final BdhPaymentMethodService paymentMethodService;
    private final BdhTransportMethodService transportMethodService;


    /**
     * Tạo đơn hàng cho user (username lấy từ Authentication)
     */
    @Transactional
    public BdhOrder createOrder(String username, BdhCreateOrderRequest req) {

        BdhUser user = customerService.getByUsername(username);

        if (req.getItems() == null || req.getItems().isEmpty()) {
            throw new RuntimeException("Order must have at least 1 item");
        }

        var paymentMethod = paymentMethodService.getById(req.getPaymentMethodId());
        var transportMethod = transportMethodService.getById(req.getTransportMethodId());

        BdhOrder order = new BdhOrder();
        order.setUser(user); // ✅ FIX
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setShippingAddress(req.getShippingAddress());
        order.setPhone(req.getPhone());
        order.setNote(req.getNote());

        order.setPaymentMethod(paymentMethod);
        order.setTransportMethod(transportMethod);

        double productTotal = 0.0;
        List<BdhOrderDetail> details = new ArrayList<>();

        for (BdhCartItemRequest itemReq : req.getItems()) {
            BdhProduct product = productService.getById(itemReq.getProductId());

            if (product.getQuantity() < itemReq.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            BdhOrderDetail detail = new BdhOrderDetail();
            detail.setOrder(order);
            detail.setProduct(product);
            detail.setQuantity(itemReq.getQuantity());
            detail.setUnitPrice(product.getPrice());

            double subtotal = product.getPrice() * itemReq.getQuantity();
            detail.setSubtotal(subtotal);
            productTotal += subtotal;

            product.setQuantity(product.getQuantity() - itemReq.getQuantity());
            productService.update(product.getId(), product);

            details.add(detail);
        }

        double shippingFee = transportMethod.getPrice() != null ? transportMethod.getPrice() : 0.0;
        double totalPrice = productTotal + shippingFee;

        order.setTotalAmount(productTotal);
        order.setShippingFee(shippingFee);
        order.setTotalPrice(totalPrice);

        BdhOrder savedOrder = orderRepository.save(order);

        for (BdhOrderDetail d : details) d.setOrder(savedOrder);
        detailRepository.saveAll(details);

        savedOrder.setDetails(details);
        return savedOrder;
    }


    /**
     * Lấy danh sách đơn của 1 khách hàng (dùng cho /api/orders/my)
     */
    public List<BdhOrder> getOrdersOfCustomer(String username) {
        BdhUser u = customerService.getByUsername(username);
        return orderRepository.findByUser_Id(u.getId());
    }


    /**
     * Lấy tất cả đơn (dùng cho admin)
     */
    public List<BdhOrder> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Lấy 1 đơn theo id (admin)
     */
    public BdhOrder getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    /**
     * Cập nhật trạng thái đơn (admin)
     */
    public BdhOrder updateStatus(Long id, String status) {
        BdhOrder order = getById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
