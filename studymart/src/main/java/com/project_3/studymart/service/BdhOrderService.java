package com.project_3.studymart.service;

import com.project_3.studymart.dto.BdhCartItemRequest;
import com.project_3.studymart.dto.BdhCreateOrderRequest;
import com.project_3.studymart.entity.BdhCustomer;
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
    private final BdhCustomerService customerService;
    private final BdhProductService productService;

    /**
     * Tạo đơn hàng cho user (username lấy từ Authentication)
     */
    @Transactional
    public BdhOrder createOrder(String username, BdhCreateOrderRequest req) {

        BdhCustomer customer = customerService.getByUsername(username);

        if (req.getItems() == null || req.getItems().isEmpty()) {
            throw new RuntimeException("Order must have at least 1 item");
        }

        BdhOrder order = new BdhOrder();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setShippingAddress(req.getShippingAddress());
        order.setPhone(req.getPhone());
        order.setPaymentMethod(req.getPaymentMethod());
        order.setNote(req.getNote());

        double total = 0.0;
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
            total += subtotal;

            // trừ kho
            product.setQuantity(product.getQuantity() - itemReq.getQuantity());
            productService.update(product.getId(), product);

            details.add(detail);
        }

        order.setTotalAmount(total);

        // lưu order
        BdhOrder savedOrder = orderRepository.save(order);

        // gán order cho từng detail rồi lưu
        for (BdhOrderDetail d : details) {
            d.setOrder(savedOrder);
        }
        detailRepository.saveAll(details);

        savedOrder.setDetails(details);
        return savedOrder;
    }

    /**
     * Lấy danh sách đơn của 1 khách hàng (dùng cho /api/orders/my)
     */
    public List<BdhOrder> getOrdersOfCustomer(String username) {
        BdhCustomer c = customerService.getByUsername(username);
        return orderRepository.findByCustomer_Id(c.getId());
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
