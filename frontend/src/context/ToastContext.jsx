/* eslint-disable react-refresh/only-export-components */
import React, { createContext, useContext, useState, useEffect } from 'react';

const ToastContext = createContext();

export const useToast = () => useContext(ToastContext);

export const ToastProvider = ({ children }) => {
    const [toasts, setToasts] = useState([]);

    const showToast = (type, message, duration = 3500) => {
        const id = Date.now() + Math.random().toString(36).substring(2, 9);
        setToasts((prev) => [...prev, { id, type, message, duration }]);
    };

    const removeToast = (id) => {
        setToasts((prev) => prev.filter((toast) => toast.id !== id));
    };

    return (
        <ToastContext.Provider value={{ showToast, removeToast }}>
            {children}
            {/* The Toast container is rendered at the root level via App.jsx or here */}
            <div className="toast-container">
                {toasts.map((toast) => (
                    <ToastItem key={toast.id} toast={toast} onRemove={removeToast} />
                ))}
            </div>
        </ToastContext.Provider>
    );
};

const ToastItem = ({ toast, onRemove }) => {
    const [isExiting, setIsExiting] = useState(false);

    useEffect(() => {
        const timer = setTimeout(() => {
            setIsExiting(true);
        }, toast.duration);

        return () => clearTimeout(timer);
    }, [toast.duration]);

    const handleAnimationEnd = () => {
        if (isExiting) {
            onRemove(toast.id);
        }
    };

    const icons = {
        success: '✅',
        error: '❌',
        info: 'ℹ️'
    };

    return (
        <div 
            className={`toast toast-${toast.type} ${isExiting ? 'toast-exit' : ''}`}
            onAnimationEnd={handleAnimationEnd}
        >
            <span>{icons[toast.type]}</span>
            <span>{toast.message}</span>
        </div>
    );
};
