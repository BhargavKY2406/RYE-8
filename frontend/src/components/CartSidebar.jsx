import React, { useState } from 'react';
import { X, ShoppingBag, CreditCard, Wallet, Banknote } from 'lucide-react';
import { useCart } from '../context/CartContext';
import { useAuth } from '../context/AuthContext';
import { useToast } from '../context/ToastContext';
import { useNavigate } from 'react-router-dom';
import API from '../api';

const CartSidebar = ({ isOpen, onClose, onOpenAuth }) => {
    const { cart, removeItem, updateQuantity, clearCart, getTotal } = useCart();
    const { user } = useAuth();
    const { showToast } = useToast();
    const navigate = useNavigate();
    const [paymentMethod, setPaymentMethod] = useState('CASH_ON_DELIVERY');
    const [isSubmitting, setIsSubmitting] = useState(false);

    if (!isOpen) return null;

    const handleCheckout = async () => {
        if (!user) {
            showToast('info', 'Please sign in to place an order.');
            onClose();
            onOpenAuth();
            return;
        }

        if (cart.items.length === 0) return;

        setIsSubmitting(true);
        const payload = {
            restaurantId: cart.restaurantId,
            paymentMethod,
            items: cart.items.map(item => ({
                menuId: item.menuId,
                quantity: item.quantity
            }))
        };

        try {
            const res = await API.placeOrder(payload);
            clearCart();
            showToast('success', `Order #${res.data.orderId} placed successfully! 🎉`);
            onClose();
            navigate('/orders');
        } catch (error) {
            showToast('error', error.message || 'Failed to place order.');
        } finally {
            setIsSubmitting(false);
        }
    };

    return (
        <>
            <div className="cart-sidebar-overlay" onClick={onClose}></div>
            <aside className="cart-sidebar open">
                <div className="cart-header">
                    <h2>Your Cart</h2>
                    <button className="modal-close" onClick={onClose}><X size={24} /></button>
                </div>
                
                <div className="cart-items">
                    {cart.items.length === 0 ? (
                        <div className="cart-empty">
                            <ShoppingBag size={56} className="empty-icon" style={{margin: '0 auto', marginBottom: '16px', opacity: 0.5}} />
                            <p>Your cart is empty</p>
                            <p className="empty-sub">Add some delicious items!</p>
                        </div>
                    ) : (
                        cart.items.map(item => (
                            <div className="cart-item" key={item.menuId}>
                                <div className="cart-item-info">
                                    <div className="cart-item-name">{item.itemName}</div>
                                    <div className="cart-item-price">₹{item.price.toFixed(2)} × {item.quantity}</div>
                                </div>
                                <div className="qty-control">
                                    <button className="qty-btn" onClick={() => updateQuantity(item.menuId, -1)}>−</button>
                                    <span className="qty-display">{item.quantity}</span>
                                    <button className="qty-btn" onClick={() => updateQuantity(item.menuId, 1)}>+</button>
                                </div>
                                <span className="cart-item-total">₹{(item.price * item.quantity).toFixed(2)}</span>
                                <button className="cart-item-remove" onClick={() => removeItem(item.menuId)} title="Remove">
                                    <X size={18} />
                                </button>
                            </div>
                        ))
                    )}
                </div>

                {cart.items.length > 0 && (
                    <div className="cart-footer">
                        <div className="cart-total-breakdown">
                            <div className="cart-subtotal">
                                <span>Subtotal</span>
                                <span>₹{getTotal().toFixed(2)}</span>
                            </div>
                            <div className="cart-fee">
                                <span>GST (5%)</span>
                                <span>₹{(getTotal() * 0.05).toFixed(2)}</span>
                            </div>
                            <div className="cart-fee">
                                <span>Delivery Fee</span>
                                <span>₹150.00</span>
                            </div>
                            <div className="cart-fee">
                                <span>App Fee</span>
                                <span>₹50.00</span>
                            </div>
                            <div className="cart-total">
                                <span>Grand Total</span>
                                <span id="cart-total-amount">₹{(getTotal() + (getTotal() * 0.05) + 150 + 50).toFixed(2)}</span>
                            </div>
                        </div>
                        <div className="form-group" style={{marginTop: '16px'}}>
                            <label>Payment Method</label>
                            <select value={paymentMethod} onChange={(e) => setPaymentMethod(e.target.value)}>
                                <option value="CASH_ON_DELIVERY">Cash on Delivery</option>
                                <option value="CREDIT_CARD">Credit Card</option>
                                <option value="DEBIT_CARD">Debit Card</option>
                                <option value="UPI">UPI</option>
                                <option value="NET_BANKING">Net Banking</option>
                            </select>
                        </div>
                        <button 
                            className="btn-primary btn-checkout" 
                            onClick={handleCheckout}
                            disabled={isSubmitting}
                        >
                            {isSubmitting ? 'Placing Order...' : 'Place Order'}
                        </button>
                    </div>
                )}
            </aside>
        </>
    );
};

export default CartSidebar;
