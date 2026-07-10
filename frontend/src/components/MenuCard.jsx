import React, { useState } from 'react';
import { useCart } from '../context/CartContext';

const MenuCard = ({ item, restaurantId, restaurantName }) => {
    const { addItem, cart, updateQuantity } = useCart();
    const [quantity, setQuantity] = useState(1);

    // Check if this item is already in the cart to show a different UI
    const cartItem = cart.items.find(i => i.menuId === item.menuId);

    const fallbackImage = '/images/m_burger.jpg';

    const handleAdd = () => {
        addItem({
            menuId: item.menuId,
            itemName: item.itemName,
            price: item.price,
            quantity: quantity,
            restaurantId,
            restaurantName
        });
        setQuantity(1); // Reset local quantity after adding
    };

    return (
        <div className={`menu-card ${!item.isAvailable ? 'unavailable' : ''}`}>
            <div className="menu-image-container">
                <img 
                    src={item.imagePath || fallbackImage} 
                    alt={item.itemName} 
                    className="menu-image"
                    loading="lazy"
                    onError={(e) => { e.target.src = fallbackImage; }}
                />
                {!item.isAvailable && <div className="unavailable-overlay">Sold Out</div>}
            </div>
            <div className="menu-card-content">
                <h3 className="menu-card-title">{item.itemName}</h3>
                <p className="menu-card-price">₹{item.price.toFixed(2)}</p>
                <p className="menu-card-desc">{item.description}</p>
                
                {item.isAvailable && (
                    <div className="menu-card-actions">
                        {cartItem ? (
                            <>
                                <div className="qty-control">
                                    <button className="qty-btn" onClick={() => updateQuantity(item.menuId, -1)}>−</button>
                                    <span className="qty-display">{cartItem.quantity}</span>
                                    <button className="qty-btn" onClick={() => updateQuantity(item.menuId, 1)}>+</button>
                                </div>
                                <div className="btn-add-cart" style={{background: 'var(--bg-surface-2)', color: 'var(--color-primary-light)', cursor: 'default', boxShadow: 'none', textAlign: 'center'}}>
                                    In Cart ✓
                                </div>
                            </>
                        ) : (
                            <>
                                <div className="qty-control">
                                    <button className="qty-btn" onClick={() => setQuantity(Math.max(1, quantity - 1))}>−</button>
                                    <span className="qty-display">{quantity}</span>
                                    <button className="qty-btn" onClick={() => setQuantity(quantity + 1)}>+</button>
                                </div>
                                <button className="btn-add-cart" onClick={handleAdd}>Add to Cart</button>
                            </>
                        )}
                    </div>
                )}
            </div>
        </div>
    );
};

export default MenuCard;
