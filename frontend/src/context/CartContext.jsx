/* eslint-disable react-refresh/only-export-components */
import React, { createContext, useContext, useEffect, useReducer } from 'react';
import { useToast } from './ToastContext';

const CartContext = createContext();
export const useCart = () => useContext(CartContext);

const initialState = {
    items: [],
    restaurantId: null,
    restaurantName: null,
};

function cartReducer(state, action) {
    switch (action.type) {
        case 'ADD_ITEM': {
            const { menuId, itemName, price, quantity, restaurantId, restaurantName } = action.payload;
            
            // Handle cross-restaurant add (should be prevented/confirmed in component)
            if (state.items.length > 0 && state.restaurantId !== restaurantId) {
                return {
                    items: [{ menuId, itemName, price, quantity }],
                    restaurantId,
                    restaurantName
                };
            }

            const existingItemIndex = state.items.findIndex(item => item.menuId === menuId);
            let newItems = [...state.items];
            
            if (existingItemIndex > -1) {
                newItems[existingItemIndex].quantity += quantity;
            } else {
                newItems.push({ menuId, itemName, price, quantity });
            }

            return {
                ...state,
                items: newItems,
                restaurantId,
                restaurantName
            };
        }
        case 'REMOVE_ITEM': {
            const newItems = state.items.filter(item => item.menuId !== action.payload.menuId);
            return {
                ...state,
                items: newItems,
                ...(newItems.length === 0 ? { restaurantId: null, restaurantName: null } : {})
            };
        }
        case 'UPDATE_QUANTITY': {
            const { menuId, delta } = action.payload;
            let newItems = [...state.items];
            const itemIndex = newItems.findIndex(item => item.menuId === menuId);
            
            if (itemIndex > -1) {
                newItems[itemIndex].quantity += delta;
                if (newItems[itemIndex].quantity <= 0) {
                    newItems.splice(itemIndex, 1);
                }
            }

            return {
                ...state,
                items: newItems,
                ...(newItems.length === 0 ? { restaurantId: null, restaurantName: null } : {})
            };
        }
        case 'CLEAR_CART':
            return initialState;
        case 'RESTORE_CART':
            return action.payload;
        default:
            return state;
    }
}

const getInitialState = () => {
    try {
        const savedCart = localStorage.getItem('zyra_cart');
        if (savedCart) {
            return JSON.parse(savedCart);
        }
    } catch (e) {
        console.error("Failed to parse cart from local storage", e);
    }
    return {
        items: [],
        restaurantId: null,
        restaurantName: null,
    };
};

export const CartProvider = ({ children }) => {
    const [state, dispatch] = useReducer(cartReducer, getInitialState());
    const { showToast } = useToast();

    // Save to local storage on change
    useEffect(() => {
        localStorage.setItem('zyra_cart', JSON.stringify(state));
    }, [state]);

    const addItem = (item) => {
        if (state.items.length > 0 && state.restaurantId !== item.restaurantId) {
            if (!window.confirm('Your cart has items from another restaurant. Clear cart and add this item?')) {
                return;
            }
        }
        dispatch({ type: 'ADD_ITEM', payload: item });
        showToast('success', `${item.itemName} added to cart`);
    };

    const removeItem = (menuId) => {
        dispatch({ type: 'REMOVE_ITEM', payload: { menuId } });
    };

    const updateQuantity = (menuId, delta) => {
        dispatch({ type: 'UPDATE_QUANTITY', payload: { menuId, delta } });
    };

    const clearCart = () => {
        dispatch({ type: 'CLEAR_CART' });
    };

    const getTotal = () => {
        return state.items.reduce((sum, item) => sum + item.price * item.quantity, 0);
    };

    const getTotalItems = () => {
        return state.items.reduce((sum, item) => sum + item.quantity, 0);
    };

    const value = {
        cart: state,
        addItem,
        removeItem,
        updateQuantity,
        clearCart,
        getTotal,
        getTotalItems
    };

    return (
        <CartContext.Provider value={value}>
            {children}
        </CartContext.Provider>
    );
};
