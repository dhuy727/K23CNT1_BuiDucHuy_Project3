package com.project_3.studymart.service;

import com.project_3.studymart.dto.CartItemRequest;
import com.project_3.studymart.dto.CreateOrderRequest;
import com.project_3.studymart.entity.Customer;
import com.project_3.studymart.entity.Order;
import com.project_3.studymart.entity.OrderDetail;
import com.project_3.studymart.entity.Product;
import com.project_3.studymart.repository.OrderDetailRepository;
import com.project_3.studymart.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderService {

    private final OrderRepository orderRepository;
    private final OrderDetailRepository detailRepository;
    private final CustomerService customerService;
    private final ProductService productService;

    /**
     * Tạo đơn hàng cho user (username lấy từ Authentication)
     */
    @Transactional
    public Order createOrder(String username, CreateOrderRequest req) {

        Customer customer = customerService.getByUsername(username);

        if (req.getItems() == null || req.getItems().isEmpty()) {
            throw new RuntimeException("Order must have at least 1 item");
        }

        Order order = new Order();
        order.setCustomer(customer);
        order.setOrderDate(LocalDateTime.now());
        order.setStatus("PENDING");
        order.setShippingAddress(req.getShippingAddress());
        order.setPhone(req.getPhone());
        order.setPaymentMethod(req.getPaymentMethod());
        order.setNote(req.getNote());

        double total = 0.0;
        List<OrderDetail> details = new ArrayList<>();

        for (CartItemRequest itemReq : req.getItems()) {

            Product product = productService.getById(itemReq.getProductId());

            if (product.getQuantity() < itemReq.getQuantity()) {
                throw new RuntimeException("Not enough stock for product: " + product.getName());
            }

            OrderDetail detail = new OrderDetail();
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
        Order savedOrder = orderRepository.save(order);

        // gán order cho từng detail rồi lưu
        for (OrderDetail d : details) {
            d.setOrder(savedOrder);
        }
        detailRepository.saveAll(details);

        savedOrder.setDetails(details);
        return savedOrder;
    }

    /**
     * Lấy danh sách đơn của 1 khách hàng (dùng cho /api/orders/my)
     */
    public List<Order> getOrdersOfCustomer(String username) {
        Customer c = customerService.getByUsername(username);
        return orderRepository.findByCustomer_Id(c.getId());
    }

    /**
     * Lấy tất cả đơn (dùng cho admin)
     */
    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }

    /**
     * Lấy 1 đơn theo id (admin)
     */
    public Order getById(Long id) {
        return orderRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Order not found"));
    }

    /**
     * Cập nhật trạng thái đơn (admin)
     */
    public Order updateStatus(Long id, String status) {
        Order order = getById(id);
        order.setStatus(status);
        return orderRepository.save(order);
    }
}
