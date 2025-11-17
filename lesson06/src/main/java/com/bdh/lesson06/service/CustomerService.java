package com.bdh.lesson06.service;

import com.bdh.lesson06.dto.CustomerDTO;
import com.bdh.lesson06.entity.Customer;
import com.bdh.lesson06.repository.CustomerRepository;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@NoArgsConstructor
public class CustomerService {

    private CustomerRepository customerRepository;

    @Autowired
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }

    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    public Optional<CustomerDTO> findById(Long id) {
        Customer customer = customerRepository.findById(id).orElse(null);
        if (customer == null) return Optional.empty();

        CustomerDTO dto = new CustomerDTO();
        dto.setId(customer.getId());
        dto.setUsername(customer.getUsername());
        dto.setPassword(customer.getPassword());
        dto.setFullName(customer.getFullName());
        dto.setAddress(customer.getAddress());
        dto.setPhone(customer.getPhone());
        dto.setEmail(customer.getEmail());
        dto.setBirthDay(customer.getBirthDay());
        dto.setActive(customer.getActive());

        return Optional.of(dto);
    }

    public Boolean save(CustomerDTO dto) {
        Customer c = new Customer();
        c.setUsername(dto.getUsername());
        c.setPassword(dto.getPassword());
        c.setFullName(dto.getFullName());
        c.setAddress(dto.getAddress());
        c.setPhone(dto.getPhone());
        c.setEmail(dto.getEmail());
        c.setBirthDay(dto.getBirthDay());
        c.setActive(dto.getActive() != null ? dto.getActive() : Boolean.TRUE);
        try {
            customerRepository.save(c);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public Customer updateCustomer(Long id, CustomerDTO dto) {
        return customerRepository.findById(id)
                .map(c -> {
                    c.setUsername(dto.getUsername());
                    c.setPassword(dto.getPassword());
                    c.setFullName(dto.getFullName());
                    c.setAddress(dto.getAddress());
                    c.setPhone(dto.getPhone());
                    c.setEmail(dto.getEmail());
                    c.setBirthDay(dto.getBirthDay());
                    c.setActive(dto.getActive());
                    return customerRepository.save(c);
                })
                .orElseThrow(() ->
                        new IllegalArgumentException("Invalid customer ID: " + id));
    }

    public void deleteCustomer(Long id) {
        customerRepository.deleteById(id);
    }
}
