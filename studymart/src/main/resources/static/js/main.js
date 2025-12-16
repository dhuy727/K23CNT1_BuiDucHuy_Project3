
const API_BASE = "/api";

async function api(path, options = {}) {
  const res = await fetch(`${API_BASE}${path}`, {
    headers: { "Content-Type": "application/json", ...(options.headers || {}) },
    ...options,
  });
  const text = await res.text();
  const data = text ? JSON.parse(text) : null;
  if (!res.ok) throw new Error((data && data.message) || `HTTP ${res.status}`);
  return data;
}


// key lưu cart
const CART_KEY = "bdh_cart";

// ========== CART HELPER ==========
function loadCart() {
    const data = localStorage.getItem(CART_KEY);
    return data ? JSON.parse(data) : [];
}

function saveCart(cart) {
    localStorage.setItem(CART_KEY, JSON.stringify(cart));
    updateCartCount();
}

function addToCart(product) {
    let cart = loadCart();
    const existing = cart.find(i => i.productId === product.id);

    if (existing) {
        existing.quantity += 1;
    } else {
        cart.push({
            productId: product.id,
            name: product.name,
            price: product.price,
            quantity: 1
        });
    }
    saveCart(cart);
}


function updateCartCount() {
    const el = document.getElementById("cart-count");
    if (!el) return;
    const cart = loadCart();
    const count = cart.reduce((sum, item) => sum + item.quantity, 0);
    el.textContent = count;
}

function getAuthHeader() {
    const auth = localStorage.getItem("auth");
    if (!auth) return {};
    const { username, password } = JSON.parse(auth);
    return {
        "Authorization": "Basic " + btoa(username + ":" + password)
    };
}

function logout() {
    localStorage.removeItem("auth");
    window.location.href = "login.html";
}



// ========== UTIL ==========
function formatCurrency(v) {
    return new Intl.NumberFormat("vi-VN", {
        style: "currency",
        currency: "VND"
    }).format(v || 0);
}

function escapeHtml(str) {
    return str
        .replace(/&/g, "&amp;")
        .replace(/"/g, "&quot;")
        .replace(/'/g, "&#39;")
        .replace(/</g, "&lt;")
        .replace(/>/g, "&gt;");
}
<div class="row" id="productList"></div>
<script src="main.js"></script>
<script>loadProducts();</script>

async function loadProducts() {
  const products = await api("/products");
  const el = document.getElementById("productList");
  el.innerHTML = products.map(p => `
    <div class="col-md-3 mb-3">
      <div class="card h-100">
        <img src="${p.imageUrl || 'https://via.placeholder.com/400x250'}" class="card-img-top">
        <div class="card-body">
          <h6 class="card-title">${p.name}</h6>
          <div class="fw-bold">${(p.price || 0).toLocaleString()}đ</div>
          <button class="btn btn-sm btn-primary mt-2" onclick='onAddToCart(${JSON.stringify(p)})'>
            Thêm vào giỏ
          </button>
        </div>
      </div>
    </div>
  `).join("");
}

function onAddToCart(p) {
    addToCart(p);
    alert("Đã thêm vào giỏ hàng!");
}

document.addEventListener("DOMContentLoaded", () => {
  const cart = getCart();
  renderSummary(cart);

  const btn = document.getElementById("btnPlaceOrder");
  if (!cart.length) btn.disabled = true;

  document.getElementById("checkoutForm").addEventListener("submit", (e) => {
    const cartNow = getCart();
    if (!cartNow.length) {
      e.preventDefault();
      alert("Giỏ hàng trống!");
      return;
    }
    document.getElementById("cartJson").value = JSON.stringify(cartNow);
  });
});



