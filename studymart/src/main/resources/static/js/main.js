
const BDH_CART_KEY = "BDH_CART";

function getCart() {
  try {
    return JSON.parse(localStorage.getItem(BDH_CART_KEY)) || [];
  } catch (e) {
    return [];
  }
}

function saveCart(cart) {
  localStorage.setItem(BDH_CART_KEY, JSON.stringify(cart));
}

function addToCart(product, qty = 1) {
  if (!product) return;

  const productId = Number(product.productId ?? product.id);
  if (!productId) return;

  const cart = getCart();
  const addQty = Number(qty) || 1;

  const idx = cart.findIndex(i => Number(i.productId) === productId);

  if (idx >= 0) {
    cart[idx].quantity = (Number(cart[idx].quantity) || 0) + addQty;
    if (!cart[idx].name && product.name) cart[idx].name = product.name;
    if ((cart[idx].price == null || cart[idx].price === 0) && product.price != null) cart[idx].price = Number(product.price);
    if (!cart[idx].imageUrl && product.imageUrl) cart[idx].imageUrl = product.imageUrl;
  } else {
    cart.push({
      productId,
      name: product.name || "",
      price: Number(product.price || 0),
      imageUrl: product.imageUrl || null,
      quantity: addQty
    });
  }

  saveCart(cart);
  updateCartCount();
}


function getCartCount() {
  return getCart().reduce((sum, i) => sum + (Number(i.quantity) || 0), 0);
}

function updateCartCount(){
  const cart = getCart();
  const total = cart.reduce((s, x) => s + Number(x.quantity || 0), 0);
  const el = document.getElementById("cart-count");
  if (el) el.textContent = total;
}


function clearCart() {
  localStorage.removeItem(BDH_CART_KEY);
  updateCartCount();
}

document.addEventListener("DOMContentLoaded", () => updateCartCount());

function resolveImage(item){
  const u = item.imageUrl;
  if (!u) return "/images/no-image.png";

  if (u.startsWith("http://") || u.startsWith("https://")) return u;

  if (u.startsWith("/")) return u;

  return "/images/products/" + u;
}

window.api = async function (url, options = {}) {
  const res = await fetch(url, {
    headers: { "Content-Type": "application/json", ...(options.headers || {}) },
    ...options
  });

  const text = await res.text();
  let data = null;
  try { data = text ? JSON.parse(text) : null; } catch (e) {}

  if (!res.ok) {
    const msg = (data && (data.message || data.error)) ? (data.message || data.error) : (text || res.statusText);
    throw new Error(msg);
  }
  return data;
};

    function addFromButton(btn) {
  const product = {
    productId: Number(btn.dataset.id),
    name: btn.dataset.name,
    price: Number(btn.dataset.price),
    imageUrl: btn.dataset.image || null
  };

  addToCart(product, 1);

  if (typeof showAddCartToast === "function") {
    showAddCartToast();
  }
}

function showAddCartToast() {
  const el = document.getElementById("cartToast");
  if (!el) return;
  const toast = bootstrap.Toast.getOrCreateInstance(el, { delay: 2000 });
  toast.hide();
  toast.show();
}



