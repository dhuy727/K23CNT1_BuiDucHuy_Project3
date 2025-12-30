package com.project_3.studymart.service;

import com.project_3.studymart.dto.BdhRegisterRequest;
import com.project_3.studymart.entity.BdhUser;
import com.project_3.studymart.repository.BdhOrderRepository;
import com.project_3.studymart.repository.BdhUserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BdhUserService {

    private final BdhUserRepository repo;
    private final BdhOrderRepository orderRepo;
    private final PasswordEncoder passwordEncoder;

    public BdhUser register(BdhRegisterRequest req) {
        if (repo.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        BdhUser c = new BdhUser();
        c.setUsername(req.getUsername());
        c.setPassword(passwordEncoder.encode(req.getPassword()));
        c.setFullName(req.getFullName());
        c.setEmail(req.getEmail());
        c.setPhone(req.getPhone());
        c.setAddress(req.getAddress());
        c.setRole("USER");
        c.setActive(true);

        return repo.save(c);
    }

    public BdhUser getByUsername(String username) {
        return repo.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
    public BdhUser createAdmin(BdhRegisterRequest req) {
        if (repo.existsByUsername(req.getUsername())) {
            throw new RuntimeException("Username already exists");
        }
        if (repo.existsByEmail(req.getEmail())) {
            throw new RuntimeException("Email already exists");
        }

        BdhUser u = new BdhUser();
        u.setUsername(req.getUsername().trim());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setFullName(req.getFullName());
        u.setEmail(req.getEmail().trim());
        u.setPhone(req.getPhone());
        u.setAddress(req.getAddress());
        u.setRole("ADMIN");
        u.setActive(true);

        return repo.save(u);
    }
    public Page<BdhUser> adminListUsers(String q, int page, int size) {
        var pageable = PageRequest.of(page, size, Sort.by(Sort.Direction.DESC, "id"));
        if (q == null || q.trim().isEmpty()) {
            return repo.findAll(pageable);
        }
        String kw = q.trim();
        return repo.findByUsernameContainingIgnoreCaseOrFullNameContainingIgnoreCaseOrEmailContainingIgnoreCase(
                kw, kw, kw, pageable
        );
    }

    public void adminSetActive(Long userId, boolean active, String currentUsername) {
        var u = repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        if (u.getUsername().equalsIgnoreCase(currentUsername)) {
            throw new RuntimeException("Không thể tự khóa chính mình");
        }
        u.setActive(active);
        repo.save(u);
    }

    public void adminSetRole(Long userId, String role, String currentUsername) {
        var u = repo.findById(userId).orElseThrow(() -> new RuntimeException("User not found"));
        String normalized = normalizeRole(role);

        if (u.getUsername().equalsIgnoreCase(currentUsername) && !"ADMIN".equals(normalized)) {
            throw new RuntimeException("Không thể tự hạ quyền của chính mình");
        }

        u.setRole(normalized);
        repo.save(u);
    }


    private String normalizeRole(String role) {
        if (role == null) throw new RuntimeException("Role invalid");
        role = role.trim().toUpperCase();

        if (role.equals("ADMIN") || role.equals("ROLE_ADMIN")) return "ADMIN";
        if (role.equals("USER")  || role.equals("ROLE_USER"))  return "USER";

        throw new RuntimeException("Role không hợp lệ");
    }

    public BdhUser adminGetUser(Long id) {
        return repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));
    }

    public BdhUser adminCreateUser(BdhRegisterRequest req) {
        if (repo.existsByUsername(req.getUsername())) throw new RuntimeException("Username đã tồn tại");
        if (repo.existsByEmail(req.getEmail())) throw new RuntimeException("Email đã tồn tại");

        BdhUser u = new BdhUser();
        u.setUsername(req.getUsername().trim());
        u.setPassword(passwordEncoder.encode(req.getPassword()));
        u.setFullName(req.getFullName());
        u.setEmail(req.getEmail().trim());
        u.setPhone(req.getPhone());
        u.setAddress(req.getAddress());
        u.setRole("USER");
        u.setActive(true);
        return repo.save(u);
    }

    public BdhUser adminUpdateUser(Long id, BdhRegisterRequest req) {
        BdhUser u = adminGetUser(id);

        u.setFullName(req.getFullName());
        u.setEmail(req.getEmail().trim());
        u.setPhone(req.getPhone());
        u.setAddress(req.getAddress());

        if (req.getPassword() != null && !req.getPassword().isBlank()) {
            u.setPassword(passwordEncoder.encode(req.getPassword()));
        }
        return repo.save(u);
    }

    @Transactional
    public void adminDeleteUserHardA(Long id, String currentUsername) {
        BdhUser u = repo.findById(id).orElseThrow(() -> new RuntimeException("User not found"));

        if (u.getUsername().equalsIgnoreCase(currentUsername)) {
            throw new RuntimeException("Không thể tự xóa chính mình");
        }

        if (orderRepo.existsByUser_Id(id)) {
            long n = orderRepo.countByUser_Id(id);
            throw new RuntimeException("Không thể xóa: tài khoản đã có " + n + " đơn hàng.");
        }

        try {
            repo.delete(u);
            repo.flush();
        } catch (DataIntegrityViolationException e) {
            throw new RuntimeException("Không thể xóa do ràng buộc dữ liệu liên quan.");
        }
    }
}
